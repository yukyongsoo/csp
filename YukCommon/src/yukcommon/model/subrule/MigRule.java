package yukcommon.model.subrule;

import java.util.ArrayList;
import java.util.List;

import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;

public class MigRule extends AbsSubRule{
	private String strId = "";
	private String fileType = "";
	private boolean copyed = false;
	private long limitTime = 0;
	private boolean dbUpdate = false;
	private List<MigSubRule> targetList = new ArrayList<MigSubRule>();
	
	public String getStrId() {
		return strId;
	}
	public void setStrId(String strId) {
		if(strId == null || strId.isEmpty())
			throw new NotNullAllowedException("mig rule strId can't be null");
		this.strId = strId;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		if(fileType == null || fileType.isEmpty())
			throw new NotNullAllowedException("mig rule fileType can't be null");
		this.fileType = fileType;
	}
	public boolean isCopyed() {
		return copyed;
	}
	public void setCopyed(boolean copyed) {
		this.copyed = copyed;
	}
	public long getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(long limitTime) {
		this.limitTime = limitTime;
	}
	public boolean isDbUpdate() {
		return dbUpdate;
	}
	public void setDbUpdate(boolean dbUpdate) {
		this.dbUpdate = dbUpdate;
	}
	public List<MigSubRule> getTargetList() {
		if(targetList == null)
			throw new WrongOperationException("mig rule targetList is empty");
		return targetList;
	}
	public void setTargetList(List<MigSubRule> targetList) {
		if(targetList == null || targetList.isEmpty())
			throw new NotNullAllowedException("mig rule targetList can't be null");
		this.targetList = targetList;
	}
	
	
}
