package yukcommon.net.io;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import yukcommon.exception.NotExcutedException;
import yukcommon.net.NetClient;
import yukcommon.net.NetWrapper;

public class NetIoClient implements NetClient {
	private static int poolSize = 0;
	private static boolean init = false;
	private static boolean reuse = false;
	private static int timeOut = 10000;
	private static SSLContext context;

	private static class LazyHolder {
	    private static final NetClient INSTANCE = new NetIoClient();  
	}
	
	public static void prepare(int poolSize, boolean reuseAddr, int timeOut,SSLContext context){
		if(!init){
			NetIoClient.poolSize = poolSize;
			NetIoClient.reuse = reuseAddr;
			NetIoClient.timeOut = timeOut;			
			NetIoClient.context = context;
			init = true;
		}
	}
	
	public static NetClient getInstance() {
		if(!init)
			throw new NotExcutedException("you need call prepare function before use it");
		return LazyHolder.INSTANCE;
	}
	

	CloseableHttpClient client;
	PoolingHttpClientConnectionManager manager;

	private  NetIoClient(){
		if(context != null) {
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(context,NoopHostnameVerifier.INSTANCE);
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create().
					register("http", PlainConnectionSocketFactory.INSTANCE).register("https", sslsf).build();
			manager = new PoolingHttpClientConnectionManager(registry);
		}
		else	
			manager = new PoolingHttpClientConnectionManager();
		SocketConfig sc = SocketConfig.custom().setSoReuseAddress(reuse).setTcpNoDelay(false).setSoLinger(1000).setSoTimeout(timeOut).build();
		manager.setMaxTotal(poolSize);
		manager.setDefaultMaxPerRoute(poolSize);
		manager.setDefaultSocketConfig(sc);
		RequestConfig rc = RequestConfig.custom().setConnectionRequestTimeout(timeOut).setConnectTimeout(timeOut).build();
		HttpClientBuilder builder = HttpClients.custom();
		builder.setConnectionManager(manager);
		builder.setDefaultRequestConfig(rc);
		client = builder.build();
	}
	
	@Override
	public void excute(NetWrapper wrap) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		try{
			CloseableHttpResponse response = client.execute(wrap.getRequest());
			wrap.setResponse(response);
		}finally{
			wrap.close();
		}
	}
	
	@Override
	public void excuteNonClose(NetWrapper wrap) throws IOException, URISyntaxException, InterruptedException, ExecutionException {
		CloseableHttpResponse response = client.execute(wrap.getRequest());
		wrap.setResponse(response);
	}

	@Override
	public void close() throws IOException  {
		client.close();
	}



	
}
