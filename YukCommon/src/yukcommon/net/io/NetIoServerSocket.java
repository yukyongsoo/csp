package yukcommon.net.io;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import yukcommon.util.LoggerUtil;

public class NetIoServerSocket extends ServerSocket{
	private static BlockingQueue<String> queue;

	public static void poll(){
		if(queue != null)
			queue.poll();
	}

	public NetIoServerSocket(int port, int backlog, InetAddress bindAddr,int count) throws IOException {
		super(port, backlog, bindAddr);
		queue = new ArrayBlockingQueue<String>(count, true);
	}

	public NetIoServerSocket(int port, int backlog,int count) throws IOException {
		super(port, backlog);
		queue = new ArrayBlockingQueue<String>(count, true);
	}

	public NetIoServerSocket(int port,int count) throws IOException {
		super(port);
		queue = new ArrayBlockingQueue<String>(count, true);
	}

	@Override
	public Socket accept() throws IOException {
		try {
			queue.put("");
		} catch (InterruptedException e) {
			LoggerUtil.warn(getClass(), "network socket queue error.", e);
			Thread.currentThread().interrupt();
		}
		return super.accept();
	}
}
