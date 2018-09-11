package yukecmapi;

import yukcommon.model.Credential;
import yukcommon.util.JsonUtil;

public class EcmConnection {
	
	private String creJson = "";
	private String serverUrl = "";
	
	protected String getServerUrl() {
		return serverUrl;
	}
	
	protected String getCreJson() {
		return creJson;
	}

	protected EcmConnection(String serverUrl, String id, String password,boolean encrypted) {
		this.serverUrl = serverUrl;
		Credential cre = new Credential(id, password,encrypted);
		creJson = JsonUtil.toJson(cre);
	}

	public void setUser(String id, String password,boolean encrypted)  {
		Credential cre = new Credential(id, password,encrypted);
		creJson = JsonUtil.toJson(cre);
	}
}
