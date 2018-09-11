package yukcommon.exception;

public class WrongOperationException extends EcmNormalError{

	public WrongOperationException(String msg) {
		super(msg);
	}
	
	public WrongOperationException(String msg, Exception t) {
		super(msg,t);
	}

}
