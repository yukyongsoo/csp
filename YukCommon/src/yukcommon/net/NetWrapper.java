package yukcommon.net;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

import yukcommon.model.fileitem.IFileItem;

public interface NetWrapper {
	void setFile(String localFile) throws FileNotFoundException;
	void setFile(InputStream stream);
	void addHeader(String name, String value);
	IFileItem getFiles();
	String getHeaderValue(String headerName) ;
	String getHeaderValueNotException(String headerName);
	void setResponse(HttpResponse response) throws IOException, InterruptedException, ExecutionException;
	HttpUriRequest getRequest() throws URISyntaxException;
	void close() throws IOException;
}