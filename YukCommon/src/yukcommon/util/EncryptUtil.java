package yukcommon.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;


public class EncryptUtil {
	private static String dummy = "THISISENCRYPTEDPASSWORD";
	private static String dummy1 = "SHA-256";
	private static String dummy2 = "PBEWithSHA1AndDESede";
	
	private static class LazyHolder {
	    private static final EncryptUtil util =  new EncryptUtil();
	}

	public static EncryptUtil getInstance(){
		return LazyHolder.util;
	}

	ConfigurablePasswordEncryptor digester;
	StandardPBEStringEncryptor cryto;

	private EncryptUtil(){
		digester = new ConfigurablePasswordEncryptor();
		digester.setAlgorithm(dummy1);
		digester.setPlainDigest(true);
		cryto = new StandardPBEStringEncryptor();
		cryto.setAlgorithm(dummy2);
		cryto.setPassword(dummy);
	}

	public String encryptDBKey(String pwd){
		return digester.encryptPassword(pwd);
	}

	public boolean checkDBKey(String input,String encryptKey){
		return digester.checkPassword(input, encryptKey);
	}

	public String encryptConfigKey(String pwd){
		return cryto.encrypt(pwd);
	}

	public String decryptConfigKey(String encryptKey){
		return cryto.decrypt(encryptKey);
	}
	
	public String encryptLcKey(String path,String word){
		digester = new ConfigurablePasswordEncryptor();
		digester.setAlgorithm(dummy1);
		digester.setPlainDigest(false);
		String temp = digester.encryptPassword(word);
		
		cryto = new StandardPBEStringEncryptor();
		cryto.setAlgorithm(dummy2);
		cryto.setPassword(path);
		
		return cryto.encrypt(temp);
	}

	public boolean decryptLcKey(String path,String encryptKey, String licKey){
		cryto = new StandardPBEStringEncryptor();
		cryto.setAlgorithm(dummy2);
		cryto.setPassword(path);
		String temp = cryto.decrypt(licKey);
		
		digester = new ConfigurablePasswordEncryptor();
		digester.setAlgorithm(dummy1);
		digester.setPlainDigest(false);
		return digester.checkPassword(encryptKey, temp);
	}
	
	public static void main(String[] args) {
		try {
			
			if(args != null && args.length > 0){							
				EncryptUtil instance = EncryptUtil.getInstance();
				
				System.out.println("Make DB key");
				String a = instance.encryptDBKey(args[0]);
				System.out.println(a);
				System.out.println(instance.checkDBKey(args[0],a));
				System.out.println("");

				System.out.println("Make Config key");
				a = instance.encryptConfigKey(args[0]);
				System.out.println(a);
				System.out.println(instance.decryptConfigKey(a));
				System.out.println("");
				
			}
			else
				System.out.println("Please Input Your Passward");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
