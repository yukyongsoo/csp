package yukcommon.exception;

public class EcmNormalError extends RuntimeException{
	public EcmNormalError(String msg){
		super(msg);
	}

	public EcmNormalError(String msg, Exception e){
		super(msg,e);
	}
}
