package yukcommon.model;

import yukcommon.exception.NotNullAllowedException;
import yukcommon.util.EncryptUtil;

public class Credential extends AbsModel{
	private String id = "";
	private String password = "";

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public Credential(String id, String password,boolean encrypted) {
		if(id == null || id.isEmpty())
			throw new NotNullAllowedException("credential id can't be null");
		if(password == null || password.isEmpty())
			throw new NotNullAllowedException("credential password can't be null");
		this.id = id;
		if(password.length() > 0 && !encrypted)
			this.password = EncryptUtil.getInstance().encryptDBKey(password);
	}

	/**
	 * Do not USE!!!!!!!!!!
	 */
	public Credential(){

	}
}
