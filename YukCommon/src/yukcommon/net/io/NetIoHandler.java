package yukcommon.net.io;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import yukcommon.net.AbsNetHandler;

public class NetIoHandler implements HttpRequestHandler{
	private AbsNetHandler handler;

	public NetIoHandler(AbsNetHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public void handle(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
		String uri = request.getRequestLine().getUri();
		handler.handling(request, response, uri);
		NetIoServerSocket.poll();
	}
}
