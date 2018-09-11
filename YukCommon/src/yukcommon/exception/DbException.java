package yukcommon.exception;

public class DbException extends EcmNormalError{

	public DbException(String msg, Exception t) {
		super(msg,t);
	} 

}
