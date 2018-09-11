package yukecmapi;

public abstract class AbsApi {
	protected EcmConnection conn;
	
	public AbsApi(EcmConnection conn) {
		this.conn = conn;
	}
}
