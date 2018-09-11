package yukcommon.model;

import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;

public class Version {
	private boolean isMajor = true;
	private boolean autoIncrease = false;
	private String docId = "";
	private int versionValue = 0;
	private Content content;
	
	public boolean isMajor() {
		return isMajor;
	}
	
	public void setMajor(boolean isMajor) {
		this.isMajor = isMajor;
	}
	
	public boolean isAutoIncrease() {
		return autoIncrease;
	}
	
	public void setAutoIncrease(boolean autoIncrease) {
		this.autoIncrease = autoIncrease;
	}
	
	public String getDocId() {
		if(docId.isEmpty())
			throw new WrongOperationException("version doc Id is null.");
		return docId;
	}
	
	public void setDocId(String docId) {
		if(docId == null || docId.isEmpty())
			throw new NotNullAllowedException("version doc Id can't be null");
		this.docId = docId;
	}
	public int getVersionValue() {
		return versionValue;
	}
	public void setVersionValue(int versionValue) {
		if(versionValue < 1)
			throw new WrongOperationException("version value can't be under 0");
		this.versionValue = versionValue;
	}
	public Content getContent() {
		if(content == null)
			throw new WrongOperationException("version content is null");
		return content;
	}
	public void setContent(Content content) {
		if(content == null)
			throw new NotNullAllowedException("version content can't be null");
		this.content = content;
	}
	
	
}
