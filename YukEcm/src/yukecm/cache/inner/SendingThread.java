package yukecm.cache.inner;

import java.sql.SQLException;
import java.util.List;

import yukcommon.model.Cluster;
import yukcommon.util.LoggerUtil;
import yukecm.cache.CacheModel;
import yukecm.cache.RepliPolicyWrapper;
import yukecm.cache.YLC;
import yukecm.controller.ClusterController;

public class SendingThread extends Thread{
	protected SendingThread() {
		setName("CacheSender");
	}
	
	@Override
	public void run() {
		while(true){
			CacheModel mayFail = null;
			try {
				mayFail = QueueManager.getInstance().getTarget();
				RepliPolicyWrapper repl = YLC.getRepl();
				List<Cluster> list = ClusterController.getInstance().getLiveList();
				for(Cluster model : list){
					send(model,repl,mayFail);
				} 
			} catch (Exception e) {
				LoggerUtil.warn(getClass(), "Cache Replicate Fail", e);
			}
		}
	}
	
	private void send(Cluster model, RepliPolicyWrapper repl, CacheModel mayFail) throws SQLException, InterruptedException {
		try {
			if(!model.isMe()) {
				LoggerUtil.debug(getClass(), "Cluster Replicated Sended.address : " + model.getAddress() + ".cache Name : " + mayFail.getName(),null);
				repl.sendReplicate(mayFail,model.getAddress());
			}
		} catch (Exception e) {
			if(mayFail.getFailCount() < 1)
				retryFailModel(mayFail);
			else {
				LoggerUtil.error(getClass(), "Cluster Disable. Address is " + model.getAddress(),e);
				ClusterController.getInstance().updClusterState(model.getAddress(),false);
			}
		}
	}
	
	private void retryFailModel(CacheModel mayFail){
		if(mayFail != null){
			try {
				mayFail.addFailCount();
				QueueManager.getInstance().putTarget(mayFail);
			} catch (InterruptedException e) {
				LoggerUtil.warn(getClass(), "Cache Replicate Fail.cause queue interrupted.", e);
				interrupt();
			}	
		}
	}
}
