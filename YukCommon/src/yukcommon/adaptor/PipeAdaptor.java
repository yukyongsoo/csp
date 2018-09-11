package yukcommon.adaptor;

import yukcommon.exception.AdpatorException;
import yukcommon.model.Content;
import yukcommon.util.LoggerUtil;

public abstract class PipeAdaptor {
	private boolean passOnError = false;
	
	public void setPassOnError(boolean passOnError) {
		this.passOnError = passOnError;
	}
	
	public boolean inbound(Content content){
		long start = System.currentTimeMillis();
		try {
			boolean check = inboundImpl(content);
			LoggerUtil.debugTime(getClass(), "inbound pipe time is {}Ms.status:" + check, start);
			return check;
		} catch (Exception e) {
			if(passOnError) {
				LoggerUtil.errorTime(getClass(), "inbound pipe error. time is {}Ms.contentid is" + content.getDocId(), start,e);
				return false;
			}
			else
				throw new AdpatorException("Inbound Pipe Has Error." + content.getDocId(),e);
		}
	}
	
	public boolean outbound(Content content) {
		long start = System.currentTimeMillis();
		try {
			boolean check =  outboudImpl(content);
			LoggerUtil.debugTime(getClass(), "outbound pipe time is {}Ms.status:" + check, start);
			return check;
		} catch (Exception e) {
			if(passOnError) {
				LoggerUtil.errorTime(getClass(), "outbound pipe error. time is {}Ms.contentid is " + content.getDocId() + 
												" strId is " + content.getStrId(), start,e);
				return false;
			}
			else
				throw new AdpatorException("outbound Pipe Has Error." + content.getDocId(),e);
		}
	}

	protected abstract boolean inboundImpl(Content content) throws Exception;
	protected abstract boolean outboudImpl(Content content) throws Exception;
}
