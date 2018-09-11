package yukcommon.model.subrepo;

import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;
import yukcommon.model.AbsModel;

public class SubRepoStr  extends AbsModel{
	private String strId = "";
	private int putOrder = 0;
	private int getOrder = 0;
	
	public String getStrId() {
		if(strId.isEmpty())
			throw new WrongOperationException("sub repo strId is empty");
		return strId;
	}
	public void setStrId(String strId) {
		if(strId == null || strId.isEmpty())
			throw new NotNullAllowedException("sub repo strId can't be null");
		this.strId = strId;
	}
	public int getPutOrder() {
		return putOrder;
	}
	public void setPutOrder(int putOrder) {
		this.putOrder = putOrder;
	}
	public int getGetOrder() {
		return getOrder;
	}
	public void setGetOrder(int getOrder) {
		this.getOrder = getOrder;
	}
	
	
}
