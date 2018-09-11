package YukEcmWeb;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import yukcommon.exception.NotExistException;
import yukecmapi.EcmConnection;

@Scope(value="session", proxyMode= ScopedProxyMode.TARGET_CLASS)
@Component
public class SessionBean {
	private EcmConnection conn;

	public EcmConnection getConn() {
		if(conn == null)
			throw new NotExistException("current session can't get CSP connection.Please Logout and retry.");
		return conn;
	}

	public void setConn(EcmConnection conn) {
		this.conn = conn;
	}
}
