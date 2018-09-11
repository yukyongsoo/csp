package yukcommon.exception;

public class JsonException extends EcmNormalError{
	public JsonException(String msg, Exception t){
		super(msg, t);
	}
}
