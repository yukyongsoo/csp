package yukecm.lifecycle.mig;

import java.io.InputStream;
import java.util.List;

import yukcommon.dic.type.OnErrorType;
import yukcommon.model.Content;
import yukcommon.model.Doc;
import yukcommon.model.Pair;
import yukcommon.model.Pipe;
import yukcommon.model.Repository;
import yukcommon.model.Rule;
import yukcommon.model.Storage;
import yukcommon.model.WorkingGroup;
import yukcommon.model.fileitem.StreamFileItem;
import yukcommon.model.lifecycle.Mig;
import yukcommon.model.subrule.MigRule;
import yukcommon.model.subrule.MigSubRule;
import yukcommon.util.LoggerUtil;
import yukecm.controller.LifeCycleController;
import yukecm.controller.RepositoryController;
import yukecm.controller.StorageController;
import yukecm.etc.EcmUtil;
import yukecm.etc.InnerClient;
import yukecm.injecter.cluster.ClusterInjector;
import yukecm.injecter.docNcontent.DCinjetor;
import yukecm.injecter.rule.RuleInjector;
import yukecm.injecter.workgroup.WorkInjector;
import yukecm.lifecycle.LifeCycleCallable;

public class MigFileCallable extends LifeCycleCallable{

	public MigFileCallable(Doc doc) {
		super(doc);
	}

	@Override
	public Boolean call() throws Exception {
		long start= System.currentTimeMillis();
		WorkingGroup working = WorkInjector.getInstance().getWorkRuleList(doc.getWorkId(),OnErrorType.NOTEXIST);
		Rule rule = RuleInjector.getInstance().getRule(working.getMigId(),OnErrorType.NOTEXIST);
		MigRule migRule = (MigRule) rule.getSubRule();
		Content content = new Content();
		content.setDocId(doc.getId());
		List<Content> contents;
		if(migRule.getStrId() == null || migRule.getStrId().isEmpty()) {
			contents = DCinjetor.getInstance().getContent(content,OnErrorType.NONE);
		}
		else {
			content.setStrId(migRule.getStrId()); 
			contents = DCinjetor.getInstance().getStorageContent(content,OnErrorType.NONE);	
		}
		for(MigSubRule subRule : migRule.getTargetList()){
			for(Content con : contents){
				Mig mig = new Mig();
				mig.setDoc(doc);
				mig.getDoc().setContent(con.copy());
				mig.getDoc().setMigId(working.getMigId());
				mig.setDbUpdate(migRule.isDbUpdate());
				mig.setTargetRepoId(subRule.getTargetRepoId()); 
				mig.setTargetWorkId(subRule.getTargetWorkId());
				if(ClusterInjector.getInstance().getCluster(subRule.getServerAddress(),OnErrorType.NONE) == null) {
					Pair<Repository, Storage> pair = RepositoryController.getInstance().findRepo(con.getStrId());
					InputStream stream = pair.getValue().getFile(con);
					mig.getDoc().getContent().setItem(new StreamFileItem(stream));
					List<Pipe> pipes = EcmUtil.getRepoPipe(pair.getKey().getId());
					for (Pipe pipe : pipes) {
						pipe.getAdt().outbound(mig.getDoc().getContent());
					}
					InnerClient.getInstance().migFile(subRule.getServerAddress(),mig);
				}
				else {
					mig.setDirect(true);
					LifeCycleController.getInstance().migFile(mig, null);
				}
				
				if (migRule.isDbUpdate()) {
					Storage storage = StorageController.getInstance().getStorage(con.getStrId());
					storage.deleteFile(con);
				} else if (!migRule.isCopyed()) {
					Storage storage = StorageController.getInstance().getStorage(con.getStrId());
					storage.deleteFile(con);
					DCinjetor.getInstance().deleteContent(con);
				}
			}	
		}
		if(migRule.isDbUpdate() || migRule.isCopyed()) {
			doc.setMigId(working.getMigId());
			DCinjetor.getInstance().updDoc(doc);
		}
		else {
			List<Content> remains = DCinjetor.getInstance().getContent(content,OnErrorType.NONE);
			if(remains.isEmpty())
				DCinjetor.getInstance().delDoc(doc);
		}
		LoggerUtil.traceTime(getClass(), "Single Mig File Spend Time is {}Ms", start);
		return true;
	}
}
