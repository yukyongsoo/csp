package yukcommon.net.io;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLContext;

import org.apache.http.ExceptionLogger;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;

import yukcommon.net.AbsNetHandler;
import yukcommon.net.NetServer;
import yukcommon.util.LoggerUtil;

public class NetIoServer implements NetServer {
	private static class LazyHolder {
	    private static final NetServer server = new NetIoServer();
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
	private SSLContext context;

	HttpServer httpServer;

	private NetIoServer(){
		boot = ServerBootstrap.bootstrap();
	}
	
	@Override
	public void setSSL(SSLContext context) {
		this.context = context;
		boot.setSslContext(this.context);
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
		if(context != null) {
			boot.setServerSocketFactory(context.getServerSocketFactory());				
		}
		else {
			boot.setServerSocketFactory(new ServerSocketFactory() {
				@Override
				public ServerSocket createServerSocket(int port, int backlog,InetAddress ifAddress) throws IOException {
					return new NetIoServerSocket(port,backlog,ifAddress,count);
				}
				@Override
				public ServerSocket createServerSocket(int port, int backlog) throws IOException {
					return new NetIoServerSocket(port,backlog,count);
				}
				@Override
				public ServerSocket createServerSocket(int port) throws IOException {
					return new NetIoServerSocket(port,count);
				}
			});
		}
		SocketConfig sc = SocketConfig.custom().setBacklogSize(backlog).setTcpNoDelay(tcpNo).
				setSoTimeout(timeOut).setSoReuseAddress(true).build();
		boot.setSocketConfig(sc);
		ConnectionConfig cc = ConnectionConfig.custom().setBufferSize(bufferSize).build();
		boot.setConnectionConfig(cc);
		boot.setExceptionLogger(new ExceptionLogger() {
			@Override
			public void log(Exception e) {
				NetIoServerSocket.poll();
				LoggerUtil.warn(getClass(), "http comunication error.maybe closed by client", e);
			}
		});
		
		for(Entry<String, AbsNetHandler> entry: handleMap.entrySet())
			boot.registerHandler(entry.getKey(), new NetIoHandler(handleMap.get(entry.getKey())));
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
		if(now)
			httpServer.stop();
		else
			httpServer.shutdown(10, TimeUnit.SECONDS);
	}

	
	




}
