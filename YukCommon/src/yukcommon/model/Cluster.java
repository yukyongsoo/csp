package yukcommon.model;

import yukcommon.exception.NotExistException;
import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.NotSupportException;

public class Cluster extends AbsModel{
	private int apNum = 0;
	private String address = "";
	private boolean state = true;
	private boolean me = false;
	
	public int getApNum() {
		return apNum;
	}
	public void setApNum(int apNum) {
		if(apNum < 0)
			throw new NotSupportException("ap num needed zero or more value");
		this.apNum = apNum;
	}
	public String getAddress() {
		if(address.isEmpty())
			throw new NotExistException("cluster address is empty");
		return address;
	}
	public void setAddress(String address) {
		if(address == null || address.isEmpty())
			throw new NotNullAllowedException("cluster address can't be empty");
		this.address = address;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public boolean isMe() {
		return me;
	}
	public void setMe(boolean me) {
		this.me = me;
	}
	
	@Override
	public String toString() {
		return "Current Cluster Data: apNum = " + apNum + ".address=" + address + ".state=" + state + ".isMine=" + me;
	}
}
