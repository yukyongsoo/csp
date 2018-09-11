package yukcommon.exception;

public class NotSupportException extends EcmNormalError{

	public NotSupportException(String msg) {
		super(msg);
	}

	public NotSupportException(String msg, Exception e) {
		super(msg, e);
	}

}
