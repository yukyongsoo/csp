package yukcommon.model.subrepo;

import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;
import yukcommon.model.AbsModel;

public class SubRepoPipe extends AbsModel{
	private String pipeId = "";
	private int order = 0;
	
	public String getPipeId() {
		if(pipeId.isEmpty())
			throw new WrongOperationException("sub repo pipe Id is empty");
		return pipeId;
	}
	public void setPipeId(String pipeId) {
		if(pipeId == null || pipeId.isEmpty())
			throw new NotNullAllowedException("sub repo pipe Id can't be null");
		this.pipeId = pipeId;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	
}
