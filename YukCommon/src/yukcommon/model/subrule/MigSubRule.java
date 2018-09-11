package yukcommon.model.subrule;

import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;
import yukcommon.model.AbsModel;

public class MigSubRule extends AbsModel{
	private String serverAddress = "";
	private String targetWorkId = "";
	private String targetRepoId = "";
	
	public String getServerAddress() {
		if(serverAddress.isEmpty())
			throw new WrongOperationException("mig sub rule server address is empty");
		return serverAddress;
	}
	public void setServerAddress(String serverAddress) {
		if(serverAddress == null || serverAddress.isEmpty())
			throw new NotNullAllowedException("mig sub rule server address can't be empty");
		this.serverAddress = serverAddress;
	}
	public String getTargetWorkId() {
		if(targetWorkId.isEmpty())
			throw new WrongOperationException("mig sub rule targetWorkId is empty");
		return targetWorkId;
	}
	public void setTargetWorkId(String targetWorkId) {
		if(targetWorkId == null || targetWorkId.isEmpty())
			throw new NotNullAllowedException("mig sub rule targetWorkId can't be empty");
		this.targetWorkId = targetWorkId;
	}
	public String getTargetRepoId() {
		return targetRepoId;
	}
	public void setTargetRepoId(String targetRepoId) {
		if(targetRepoId == null || targetRepoId.isEmpty())
			throw new NotNullAllowedException("mig sub rule targetRepoId can't be empty");
		this.targetRepoId = targetRepoId;
	}
	
	
}
