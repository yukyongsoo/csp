package yukcommon.net;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.http.ssl.SSLContexts;

import yukcommon.util.LoggerUtil;

public abstract class SSLProviderUtil {
	private SSLProviderUtil() {}
	
	public static SSLContext getServerSSL(String keyFile,String store,String key) throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
		File file = new File(keyFile);
		if(file.exists() && file.isFile()) {
			SSLContext context = SSLContexts.custom().loadKeyMaterial(file, store.toCharArray(), key.toCharArray()).build();
			LoggerUtil.info(SSLProviderUtil.class, "Server SSL Setted", null);
			return context;
		}
		return null;
	}
	
	public static SSLContext getClientSSL(String trustFile, String trust) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
		File file = new File(trustFile);
		if(file.exists() && file.isFile()) {
			SSLContext context = SSLContexts.custom().loadTrustMaterial(file, trust.toCharArray()).build();
			LoggerUtil.info(SSLProviderUtil.class, "Client SSL Setted", null);
			return context;
		}
		return null;
	}
}
