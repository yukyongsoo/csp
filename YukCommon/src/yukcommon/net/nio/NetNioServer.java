package yukcommon.net.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.http.ExceptionLogger;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.nio.bootstrap.HttpServer;
import org.apache.http.impl.nio.bootstrap.ServerBootstrap;
import org.apache.http.impl.nio.reactor.IOReactorConfig;

import yukcommon.net.AbsNetHandler;
import yukcommon.net.NetServer;
import yukcommon.util.LoggerUtil;

public class NetNioServer implements NetServer{
	private static class LazyHolder {
	    private static final NetServer server = new NetNioServer();
	}

	public static NetServer getInstance(){
		return LazyHolder.server;
	}
	
	private int port = 0;
	private ServerBootstrap boot;
	private Map<String,AbsNetHandler> handleMap = new HashMap<String, AbsNetHandler>();
	private boolean tcpNo = false;
	private int backlog = 5000;
	private int bufferSize = 4096;
	private int timeOut = 9999999;

	HttpServer httpServer;

	private NetNioServer(){
		boot = ServerBootstrap.bootstrap();
	}
	
	@Override
	public void setSSL(SSLContext context) {
		boot.setSslContext(context);
	}


	@Override
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	@Override
	public void setTcpNo(boolean tcpNo) {
		this.tcpNo = tcpNo;
	}

	@Override
	public void setBacklog(int backlog) {
		this.backlog = backlog;
	}

	@Override
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	@Override
	public int getPort() {
		return port;
	}

	@Override
	public void addHandler(String pattern, AbsNetHandler handler) {
		handleMap.put(pattern, handler);
	}

	@Override
	public void start(int port, boolean useInet,final int count) throws IOException{
		this.port = port;
		if(useInet){
			boot.setLocalAddress(InetAddress.getLocalHost());
		}
		boot.setListenerPort(port);
		IOReactorConfig config = IOReactorConfig.custom().setBacklogSize(backlog).setIoThreadCount(count)
	            .setSoTimeout(timeOut).setTcpNoDelay(tcpNo).setSoReuseAddress(true).build();
		boot.setIOReactorConfig(config);
		ConnectionConfig cc = ConnectionConfig.custom().setBufferSize(bufferSize).build();
		boot.setConnectionConfig(cc);
		boot.setExceptionLogger(new ExceptionLogger() {
			@Override
			public void log(Exception e) {
				LoggerUtil.warn(getClass(), "http comunication error.maybe closed by client", e);
			}
		});
		for(Entry<String, AbsNetHandler> entry : handleMap.entrySet())
			boot.registerHandler(entry.getKey(), new NetNioHandler(handleMap.get(entry.getKey())));
		httpServer = boot.create();
		httpServer.start();
		Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
            	httpServer.shutdown(10, TimeUnit.SECONDS);
            }
        });
		
	}

	@Override
	public void stop(boolean now){
		httpServer.shutdown(10, TimeUnit.SECONDS);
	}

	
	
}
