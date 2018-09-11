package ecmTest;

import java.io.File;
import java.net.URLEncoder;

import yukcommon.util.EncryptUtil;

public class LicenseMaker {
	public void makeSign(String ip, String hostName,String setupDir,int port) {
		try {
			String uCode = URLEncoder.encode(setupDir, "UTF-8");
			String temp = ip+hostName+port;
			String lcKey = EncryptUtil.getInstance().encryptLcKey(uCode, temp);
			System.out.println(lcKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			LicenseMaker m = new LicenseMaker();
			//m.makeSign("192.168.219.105", "DESKTOP-HLB73T3", new File("").getAbsolutePath(), 4400);
			m.makeSign("192.168.219.105", "DESKTOP-HLB73T3", new File("").getAbsolutePath(), 4400);
			//m.makeSign("61.82.152.68", "M-PC", new File("").getAbsolutePath(), 4400);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
