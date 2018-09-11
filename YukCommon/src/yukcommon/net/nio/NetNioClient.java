package yukcommon.net.nio;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.conn.NHttpClientConnectionManager;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;

import yukcommon.exception.NotExcutedException;
import yukcommon.net.NetClient;
import yukcommon.net.NetWrapper;

public class NetNioClient implements NetClient{
	private static int poolSize = 0;
	private static boolean init = false;
	private static boolean reuse = false;
	private static int timeOut = 10000;
	private static SSLContext context;
	
	private static class LazyHolder {
	    private static final NetClient INSTANCE;
	    static {
	    	try {
				INSTANCE = new NetNioClient();
			} catch (IOReactorException e) {
				throw new ExceptionInInitializerError(e);
			}  
	    }
	}
	
	public static void prepare(int poolSize, boolean reuseAddr, int timeOut, SSLContext context){
		if(!init){
			NetNioClient.poolSize = poolSize;
			NetNioClient.reuse = reuseAddr;
			NetNioClient.timeOut = timeOut;			
			NetNioClient.context = context;
			init = true;
		}
	}

	public static NetClient getInstance(){
		if(!init)
			throw new NotExcutedException("you need call prepare function before use it");
		return LazyHolder.INSTANCE;
	}
	
	CloseableHttpAsyncClient client;
	NHttpClientConnectionManager manager;
	
	private  NetNioClient() throws IOReactorException{
		IOReactorConfig config = IOReactorConfig.custom().setSoReuseAddress(reuse).setSoLinger(1000).
				setTcpNoDelay(false).setIoThreadCount(poolSize).setSoTimeout(timeOut).setConnectTimeout(timeOut).build();
		ConnectingIOReactor ioreactor = new DefaultConnectingIOReactor(config);		
		if(context != null) {
			SSLIOSessionStrategy strategy = new SSLIOSessionStrategy(context, NoopHostnameVerifier.INSTANCE);
			Registry<SchemeIOSessionStrategy> registry = RegistryBuilder.<SchemeIOSessionStrategy>create().
					register("http", NoopIOSessionStrategy.INSTANCE).register("https", strategy).build();
			manager = new PoolingNHttpClientConnectionManager(ioreactor,registry);	
		}
		else
			manager = new PoolingNHttpClientConnectionManager(ioreactor);	
		client = HttpAsyncClients.custom().setConnectionManager(manager).setMaxConnPerRoute(poolSize).setMaxConnTotal(poolSize).build();
		client.start();
	}

	public void close() throws IOException  {
		client.close();
	}

	@Override
	public void excute(NetWrapper wrap) throws URISyntaxException, IOException, InterruptedException, ExecutionException {
		try{
			Future<HttpResponse> future = client.execute(wrap.getRequest(),null);
			
			wrap.setResponse(future.get());
		}finally{
			wrap.close();
		}
	}

	@Override
	public void excuteNonClose(NetWrapper wrap) throws IOException, URISyntaxException, InterruptedException, ExecutionException {
		Future<HttpResponse> future = client.execute(wrap.getRequest(),null);
		wrap.setResponse(future.get());
	}
}
