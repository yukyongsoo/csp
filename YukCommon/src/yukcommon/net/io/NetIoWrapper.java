package yukcommon.net.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.io.EmptyInputStream;
import org.apache.http.util.EntityUtils;

import yukcommon.exception.EcmNormalError;
import yukcommon.exception.EmptySizeException;
import yukcommon.exception.NotExcutedException;
import yukcommon.exception.NotExistException;
import yukcommon.model.fileitem.IFileItem;
import yukcommon.model.fileitem.StreamFileItem;
import yukcommon.net.NetWrapper;
import yukcommon.util.NormalUtil;

public class NetIoWrapper implements NetWrapper {
	String serverUrl = "";
	String contextUrl = "";
	HttpPost requestType;
	HttpResponse response;
	IFileItem item;
	HttpEntity entity;

	public NetIoWrapper(String serverUrl, String contextUrl){
		this.serverUrl = serverUrl.toLowerCase();
		int temp = 0;		
		if (!(this.serverUrl.contains("http://") || this.serverUrl.contains("https://")))
			this.serverUrl = "http://" +this.serverUrl;
		if ((temp = this.serverUrl.lastIndexOf('/')) == (this.serverUrl.length() - 1))
			this.serverUrl = this.serverUrl.substring(0, temp);
		requestType = new HttpPost();
		requestType.addHeader("Connection", "Close");
		this.contextUrl = contextUrl;
	}

	@Override
	public void setFile(String localFile)  {
		File file = new File(localFile);
		entity = new FileEntity(file);
	}

	@Override
	public void setFile(InputStream stream) {
		entity = new InputStreamEntity(stream);
	}

	@Override
	public void addHeader(String name, String value)  {
		requestType.addHeader(name, value);
	}

	@Override
	public IFileItem getFiles() {
		if(item != null)
			return item;
		else
			throw new EmptySizeException("this request not have any file");
	}

	@Override
	public String getHeaderValue(String headerName) {
		String value = getHeaderValueNotException(headerName);
		if(value == null)
			throw new NotExistException("Can't find naming header." + headerName);
		return value;
	}

	@Override
	public String getHeaderValueNotException(String headerName) {
		if(response != null){
			Header header = response.getFirstHeader(headerName);
			if(header == null)
				return null;
			return header.getValue();
		}
		throw new NotExcutedException("response not Setted");
	}

	@Override
	public void setResponse(HttpResponse response) throws IOException {
		this.response = response;
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			entity = response.getEntity();
			throw new EcmNormalError(NormalUtil.parseError(entity));
		}
		entity = response.getEntity();
		if (!(entity.getContent() instanceof EmptyInputStream)) {
			item = new StreamFileItem(entity.getContent());
		}
	}

	@Override
	public HttpUriRequest getRequest() throws URISyntaxException {
		requestType.setURI(new URI(serverUrl + contextUrl));
		if(entity != null)
			requestType.setEntity(entity);
		return requestType;
	}

	@Override
	public void close() throws IOException {
		if (entity != null)
			EntityUtils.consume(entity);
		if (requestType != null)
			requestType.releaseConnection();
	}	
}
