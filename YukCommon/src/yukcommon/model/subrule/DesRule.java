package yukcommon.model.subrule;

import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;

public class DesRule extends AbsSubRule{
	
	private String strId = "";
	private long limitTime = 0;
	
	public String getStrId() {
		if(strId.isEmpty())
			throw new WrongOperationException("des rule strId is empty");
		return strId;
	}
	public void setStrId(String strId) {
		if(strId == null || strId.isEmpty())
			throw new NotNullAllowedException("des rule strId can't be empty");
		this.strId = strId;
	}
	public long getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(long limitTime) {
		this.limitTime = limitTime;
	}
	
	
}
