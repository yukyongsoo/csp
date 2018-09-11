package yukcommon.exception;

public class ReplicateException extends EcmNormalError{
	public ReplicateException(String msg, Exception t){
		super(msg,t);
	}
}
