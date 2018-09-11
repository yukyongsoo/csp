package yukcommon.model.subrule;

import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;

public class InitRule extends AbsSubRule{
	
	private String repoId = "";
	private boolean required = true;
	
	public String getRepoId() {
		if(repoId.isEmpty())
			throw new WrongOperationException("init rule repository id is empty");
		return repoId;
	}
	public void setRepoId(String repoId) {
		if(repoId == null || repoId.isEmpty())
			throw new NotNullAllowedException("init rule repository id can't be empty");
		this.repoId = repoId;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	
	
}
