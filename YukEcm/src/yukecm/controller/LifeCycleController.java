package yukecm.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.quartz.SchedulerException;

import yukcommon.dic.EtcDic;
import yukcommon.dic.type.OnErrorType;
import yukcommon.dic.type.RuleType;
import yukcommon.exception.NotSupportException;
import yukcommon.model.Cluster;
import yukcommon.model.Pair;
import yukcommon.model.Pipe;
import yukcommon.model.Repository;
import yukcommon.model.Storage;
import yukcommon.model.fileitem.IFileItem;
import yukcommon.model.fileitem.StreamFileItem;
import yukcommon.model.lifecycle.LifeCycleInfo;
import yukcommon.model.lifecycle.LifeCycleSetting;
import yukcommon.model.lifecycle.Mig;
import yukcommon.model.subrule.InitRule;
import yukecm.etc.EcmUtil;
import yukecm.etc.InnerClient;
import yukecm.etc.StorageUtil;
import yukecm.injecter.docNcontent.DCinjetor;
import yukecm.injecter.lifecycle.LCInfoInjector;
import yukecm.injecter.lifecycle.LCSettingInjector;
import yukecm.injecter.workgroup.WorkInjector;
import yukecm.lifecycle.LifeCycleCallable;
import yukecm.lifecycle.LifeCycleManager;
import yukecm.lifecycle.LifeCycleWorker;

public class LifeCycleController {
	
	private static class LazyHolder {
	    private static final LifeCycleController lifeCycle = new LifeCycleController();
	}

	public static LifeCycleController getInstance() {
		return LazyHolder.lifeCycle;
	}

	private LifeCycleController() {

	}

	public String addLifeCycleSch(LifeCycleSetting setting) throws SQLException, InterruptedException, SchedulerException{
		LCSettingInjector.getInstance().getLifeCycleSchByName(setting,OnErrorType.EXIST);
		LCSettingInjector.getInstance().getLifeCycleSchByWork(setting,OnErrorType.EXIST);
		if(!(RuleType.MIGRULE == setting.getType() || RuleType.DESRULE == setting.getType()))
			throw new NotSupportException("This Rule Type Not Supported." + setting.getType());
		setting.setId(EcmUtil.getId()); 
		LifeCycleInfo info = new LifeCycleInfo();
		info.setId(setting.getId()); 
		info.setState(EtcDic.NOTSTARTED); 
		LCSettingInjector.getInstance().addLifeCycleSch(setting);
		LCInfoInjector.getInstance().addLifeCycleInfo(info);
		LifeCycleManager.getInstance().addLifeCycleSch(setting);
		return setting.getId();
	}

	public void delLifeCycleSch(LifeCycleSetting setting) throws  SQLException, InterruptedException, SchedulerException {
		LCSettingInjector.getInstance().getLifeCycleSch(setting,OnErrorType.NOTEXIST); 
		LifeCycleInfo info = LCInfoInjector.getInstance().getLifeCycleInfo(setting.getId(),OnErrorType.NOTEXIST);
		if(!info.getState().equals(EtcDic.NOTSTARTED))
			throw new NotSupportException("This Cycle Not Ready State. Please Stop Cycle and Retry");
		LCInfoInjector.getInstance().delLifeCycleInfo(setting.getId());
		LCSettingInjector.getInstance().delLifeCycleSch(setting);
		LifeCycleManager.getInstance().delLifeCycleSch(setting);
	}

	public void startLifeCycle(LifeCycleSetting setting) throws SQLException, IOException, InterruptedException, ExecutionException, URISyntaxException {
		LifeCycleInfo cycleInfo = new LifeCycleInfo();
		cycleInfo.setState(EtcDic.STARTED); 
		cycleInfo.setId(setting.getId()); 
		cycleInfo.setTotalTarget(0);
		cycleInfo.setExcuted(0);
		cycleInfo.setError(0);
		cycleInfo.setNextRowNum(0);
		cycleInfo.setStart(EcmUtil.makeDateReadable()); 
		LCInfoInjector.getInstance().updLifeCyclenfo(cycleInfo);
		LifeCycleManager.getInstance().startLifeCycle(setting.getId());
		for(Cluster cluster : ClusterController.getInstance().getLiveList()) {
			if(!cluster.isMe())
				InnerClient.getInstance().startMig(cluster.getAddress(), setting.getId());
		}
	}

	public void stopLifeCycle(LifeCycleSetting setting) throws InterruptedException, SQLException, URISyntaxException, IOException, ExecutionException {
		LifeCycleManager.getInstance().stopLifeCycle(setting.getId());
		for(Cluster cluster : ClusterController.getInstance().getLiveList()) {
			if(!cluster.isMe())
				InnerClient.getInstance().stopMig(cluster.getAddress(), setting.getId());
		}
	}
	

	public void startLifeCycleNotRepl(LifeCycleSetting setting) throws SQLException, IOException, InterruptedException {
		LifeCycleManager.getInstance().startLifeCycle(setting.getId());
	}

	public void stopLifeCycleNotRepl(LifeCycleSetting setting) throws InterruptedException, SQLException {
		LifeCycleManager.getInstance().stopLifeCycle(setting.getId());
	}

	public List<LifeCycleSetting> getLifeCycleSch() throws SQLException, InterruptedException, SchedulerException {
		return LCSettingInjector.getInstance().getLifeCycleList();
	}

	public LifeCycleInfo getLifeCycleInfo(LifeCycleSetting setting) throws SQLException {
		LifeCycleInfo cycleInfo = LCInfoInjector.getInstance().getLifeCycleInfo(setting.getId(),OnErrorType.NOTEXIST);
		cycleInfo.setRunTime(EcmUtil.getRunTime(cycleInfo.getEnd(), cycleInfo.getStart()));
		return cycleInfo;
	}

	public List<LifeCycleCallable> getLifeCycleFileList(LifeCycleSetting setting) throws InterruptedException {
		LifeCycleWorker worker = LifeCycleManager.getInstance().getWorker(setting.getId(),OnErrorType.NOTEXIST);
		return worker.getTargetList();
	}

	public void migFile(Mig mig, IFileItem item) throws Exception {
		WorkInjector.getInstance().getWorking(mig.getTargetWorkId(),OnErrorType.NOTEXIST);
		if(DCinjetor.getInstance().getDoc(mig.getDoc(),OnErrorType.NONE) == null)
			DCinjetor.getInstance().addDoc(mig.getDoc());
		String oldStrId = mig.getDoc().getContent().getStrId();
		if(mig.isDirect()) {
			Pair<Repository, Storage> pair = RepositoryController.getInstance().findRepo(mig.getDoc().getContent().getStrId());
			InputStream file = pair.getValue().getFile(mig.getDoc().getContent());
			mig.getDoc().getContent().setItem(new StreamFileItem(file));
			List<Pipe> pipes = EcmUtil.getRepoPipe(pair.getKey().getId());
			for (Pipe pipe : pipes) {
				pipe.getAdt().outbound(mig.getDoc().getContent());
			}
		}
		else {
			mig.getDoc().getContent().setItem(item);
		}
		migStorageFile(mig);
		if(mig.isDbUpdate()) 
			DCinjetor.getInstance().updateContent(mig.getDoc().getContent(), oldStrId);
		else
			DCinjetor.getInstance().addContent(mig.getDoc().getContent());
	}
	
	private void migStorageFile(Mig mig) throws Exception  {
		if(mig.getTargetRepoId() != null && !mig.getTargetRepoId().isEmpty()) {
			Repository repo = RepositoryController.getInstance().getRepo(mig.getTargetRepoId());
			List<Pipe> pipes = EcmUtil.getRepoPipe(repo.getId());
			for (Pipe pipe : pipes)
				pipe.getAdt().inbound(mig.getDoc().getContent());
			StorageUtil.addContentToStr(repo.getId(), mig.getDoc().getContent());
		}
		else {
			List<InitRule> initRules = EcmUtil.getInitRule(mig.getTargetWorkId());
			for (InitRule initRule : initRules) {
				Repository repo = RepositoryController.getInstance().getRepo(initRule.getRepoId());
				List<Pipe> pipes = EcmUtil.getRepoPipe(repo.getId());
				for (Pipe pipe : pipes)
					pipe.getAdt().inbound(mig.getDoc().getContent());
				try {
					StorageUtil.addContentToStr(repo.getId(), mig.getDoc().getContent());
				}
				catch (Exception e) {
					if (initRule.isRequired())
						throw e;
				}
			}
		}
	}
}
