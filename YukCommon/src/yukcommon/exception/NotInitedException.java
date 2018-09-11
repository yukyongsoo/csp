package yukcommon.exception;

public class NotInitedException extends EcmNormalError{
	public NotInitedException(String msg){
		super(msg);
	}

	public NotInitedException(String msg, Exception t){
		super(msg,t);
	}
}
