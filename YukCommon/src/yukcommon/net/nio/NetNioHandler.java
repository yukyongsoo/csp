package yukcommon.net.nio;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.nio.protocol.BasicAsyncRequestConsumer;
import org.apache.http.nio.protocol.BasicAsyncResponseProducer;
import org.apache.http.nio.protocol.HttpAsyncExchange;
import org.apache.http.nio.protocol.HttpAsyncRequestConsumer;
import org.apache.http.nio.protocol.HttpAsyncRequestHandler;
import org.apache.http.protocol.HttpContext;

import yukcommon.net.AbsNetHandler;

public class NetNioHandler implements HttpAsyncRequestHandler<HttpRequest> {
	private AbsNetHandler handler;

	public NetNioHandler(AbsNetHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public HttpAsyncRequestConsumer<HttpRequest> processRequest(HttpRequest arg0, HttpContext arg1) throws HttpException, IOException {
		 return new BasicAsyncRequestConsumer();
	}
	
	@Override
	public void handle(HttpRequest request, HttpAsyncExchange exchange, HttpContext context) throws HttpException, IOException {
		HttpResponse response = exchange.getResponse();
		String uri = request.getRequestLine().getUri();
		handler.handling(request, response, uri);
		exchange.submitResponse(new BasicAsyncResponseProducer(response));
	}
}
