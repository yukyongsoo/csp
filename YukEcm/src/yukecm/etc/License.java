package yukecm.etc;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import yukcommon.exception.NotSupportException;
import yukcommon.util.EncryptUtil;

public class License {
	private static class LazyHolder {
	    private static final License license = new License();
	}

	public static License getInstance() {
		return LazyHolder.license;
	}
	
	public void check(String licKey, int port) throws UnknownHostException, UnsupportedEncodingException {
		boolean check;
		try {
			String temp = InetAddress.getLocalHost().getHostAddress()+InetAddress.getLocalHost().getHostName()+port;
			String a = URLEncoder.encode(new File("").getAbsolutePath(), "UTF-8");
			check = EncryptUtil.getInstance().decryptLcKey(a, temp , licKey);
			if(!check)
				throw new NotSupportException("Your License is not right");
		} catch (Exception e) {
			throw new NotSupportException("Your License is not right",e); 
		}
		
	}
}
