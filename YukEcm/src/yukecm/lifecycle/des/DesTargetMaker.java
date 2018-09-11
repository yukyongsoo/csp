package yukecm.lifecycle.des;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import yukcommon.dic.type.OnErrorType;
import yukcommon.model.Rule;
import yukcommon.model.WorkingGroup;
import yukcommon.model.lifecycle.LifeCycleInfo;
import yukcommon.model.lifecycle.LifeCycleSetting;
import yukcommon.model.subrule.DesRule;
import yukcommon.util.LoggerUtil;
import yukecm.controller.ClusterController;
import yukecm.db.DbConnFac;
import yukecm.db.LCDbAction;
import yukecm.etc.EcmUtil;
import yukecm.injecter.lifecycle.LCInfoInjector;
import yukecm.injecter.rule.RuleInjector;
import yukecm.injecter.workgroup.WorkInjector;
import yukecm.lifecycle.LifeCycleCallable;

public class DesTargetMaker extends Thread{
	private BlockingQueue<List<LifeCycleCallable>> targetQueue;
	private boolean stop = false;
	private LifeCycleSetting setting;
	
	protected DesTargetMaker(LifeCycleSetting setting) {
		setName(setting.getName()+"_DesTargetMaker");
		targetQueue = new ArrayBlockingQueue<List<LifeCycleCallable>>(30, true);
		this.setting = setting;
	}
	
	public List<LifeCycleCallable> getList() {
		return targetQueue.poll();
	}
	
	public void stopMakeTarget() {
		stop =true;
	}
		
	@Override
	public void run() {
		while(!stop){
			LCDbAction action = null;
			try {
				action = DbConnFac.getInstance().getLCDbAction();
				LifeCycleInfo info = new LifeCycleInfo();
				info.setId(setting.getId());
				WorkingGroup workRuleList = WorkInjector.getInstance().getWorkRuleList(setting.getWorkId(),OnErrorType.NOTEXIST);
				List<LifeCycleCallable> list = null;
				if(workRuleList.getDesId() != null  && !workRuleList.getDesId().isEmpty()) {
					long start = System.currentTimeMillis();
					Rule rule = RuleInjector.getInstance().getRule(workRuleList.getDesId(),OnErrorType.NOTEXIST);
					DesRule desRule = (DesRule) rule.getSubRule();
					long t = System.currentTimeMillis() - desRule.getLimitTime();
					LCInfoInjector.getInstance().updLifeCycleInfoRowNum(info);
					long count = LCInfoInjector.getInstance().getLifeCycleInfo(info.getId(),OnErrorType.NOTEXIST).getNextRowNum();
					LoggerUtil.debug(getClass(), "current rownum is " + (count-1000) + " to " + count , null);
					list = action.getDesTargetPaging(setting.getWorkId(), EcmUtil.makeDate(t), (count-1000));
					LoggerUtil.debugTime(getClass(), "make DesTarget Time is {}Ms.Size is" + list.size() , start);
				}
				if(list == null ||  list.isEmpty())
					stop = true;
				else
					targetQueue.put(list);
				int liveSize = ClusterController.getInstance().getList().size();
				sleep((long)liveSize * 100);
			} catch (Exception e) {
				stop = true;
				LoggerUtil.error(getClass(), "Des Target Maker Has Error." + setting.getId(), e);
			} finally {
				DbConnFac.staticClose(action);
			}
		}
	}
}
