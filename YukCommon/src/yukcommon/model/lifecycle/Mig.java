package yukcommon.model.lifecycle;

import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;
import yukcommon.model.AbsModel;
import yukcommon.model.Doc;

public class Mig extends AbsModel{
	private Doc doc;
	private boolean dbUpdate = false;
	private boolean direct = false;
	private String targetWorkId = "";
	private String targetRepoId = "";
	
	public Doc getDoc() {
		if(doc == null)
			throw new WrongOperationException("miged doc is empty");
		return doc;
	}
	public void setDoc(Doc doc) {
		if(doc == null)
			throw new NotNullAllowedException("miged doc needed doc");
		this.doc = doc;
	}
	public boolean isDbUpdate() {
		return dbUpdate;
	}
	public void setDbUpdate(boolean dbUpdate) {
		this.dbUpdate = dbUpdate;
	}
	public boolean isDirect() {
		return direct;
	}
	public void setDirect(boolean direct) {
		this.direct = direct;
	}
	public String getTargetWorkId() {
		if(targetWorkId.isEmpty())
			throw new WrongOperationException("miged targetWorkId is empty");
		return targetWorkId;
	}
	public void setTargetWorkId(String targetWorkId) {
		if(targetWorkId == null || targetWorkId.isEmpty())
			throw new NotNullAllowedException("miged doc needed targetWorkId");
		this.targetWorkId = targetWorkId;
	}
	public String getTargetRepoId() {
		if(targetRepoId.isEmpty())
			throw new WrongOperationException("miged targetRepoId is empty");
		return targetRepoId;
	}
	public void setTargetRepoId(String targetRepoId) {
		if(targetRepoId == null || targetRepoId.isEmpty())
			throw new NotNullAllowedException("miged doc needed targetRepoId");
		this.targetRepoId = targetRepoId;
	}
	
	
}
