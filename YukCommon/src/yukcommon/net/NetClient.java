package yukcommon.net;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public interface NetClient {
	void excute(NetWrapper wrap) throws URISyntaxException, IOException, InterruptedException, ExecutionException;
	void excuteNonClose(NetWrapper wrap) throws IOException, URISyntaxException, InterruptedException, ExecutionException;
	void close() throws IOException;
}