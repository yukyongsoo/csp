package yukcommon.net;

import java.io.IOException;

import javax.net.ssl.SSLContext;

public interface NetServer {
	void setTimeOut(int timeOut);
	void setTcpNo(boolean tcpNo);
	void setBacklog(int backlog);
	void setBufferSize(int bufferSize);
	int getPort();
	void addHandler(String pattern, AbsNetHandler handler);
	void start(int port, boolean useInet, int count) throws IOException;
	void stop(boolean now);
	void setSSL(SSLContext context);
}