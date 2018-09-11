package yukecm.mount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import yukcommon.adaptor.StorageAdaptor;
import yukcommon.dic.EtcDic;
import yukcommon.exception.AdpatorException;
import yukcommon.exception.NotSupportException;
import yukcommon.model.Content;
import yukcommon.model.Storage;
import yukecm.config.BaseProperty;
import yukecm.controller.StorageController;

public class HcpAdaptor extends StorageAdaptor{
	private static final String HCPAUTHHEADERKEY = "Authorization";

	private String address = "";
	private String baseDir = "";
	private String auth = "";
	private HttpClient client;
	private SAXParser parser;
	private HcpStorage left = new HcpStorage();

	@Override
	protected String addFileImpl(Content content) throws Exception {
		String path = address + baseDir + content.getDocId();
		HttpPut request = new HttpPut(path);
		request.addHeader(HCPAUTHHEADERKEY, auth);
		InputStreamEntity entity = new InputStreamEntity(content.getItem().getInputStream());
		request.setEntity(entity);
		HttpResponse response = client.execute(request);
		EntityUtils.consume(response.getEntity());
		if(response.getStatusLine().getStatusCode() != HttpStatus.SC_CREATED)
			throw new AdpatorException("Hcp File Input Has Error. return Code is " + response.getStatusLine().getStatusCode(), null);
		return content.getDocId();
	}

	@Override
	protected InputStream getFileImpl(Content content) throws Exception {
		String path = address + baseDir + content.getDocId();
		HttpGet request = new HttpGet(path);
		request.addHeader(HCPAUTHHEADERKEY, auth);
		HttpResponse response = client.execute(request);
		if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
			throw new AdpatorException("Hcp get File Has Error. return Code is " + response.getStatusLine().getStatusCode(), null);
		File tempDir = new File(BaseProperty.getInstance().tempDir + EtcDic.PATHSEP  + UUID.randomUUID().toString());
		FileUtils.copyInputStreamToFile(response.getEntity().getContent(), tempDir);
		EntityUtils.consume(response.getEntity());
		return new FileInputStream(tempDir);
	}

	@Override
	protected void deleteFileImpl(Content content) throws Exception {
		String path = address + baseDir + content.getDocId();
		HttpDelete request = new HttpDelete(path);
		request.addHeader(HCPAUTHHEADERKEY, auth);
		HttpResponse response = client.execute(request);
		EntityUtils.consume(response.getEntity());
		if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
			throw new AdpatorException("Hcp delete File Has Error. return Code is " + response.getStatusLine().getStatusCode(), null);
	}

	@Override
	protected long setRetentionImpl(Content content, long time) throws Exception {
		throw new NotSupportException("Not Implimentaion Now!!");
		/*String url = "http://example-ns01.example-tn01.hcp01.hitachi.com/rest/examples/world.txt" +"?retention=1279141446";
		HttpPut request = new HttpPut(url);
		request.addHeader(HCPAuthHeaderKey, auth);
		HttpResponse response = client.execute(request);
		EntityUtils.consume(response.getEntity());
		return time;*/
	}

	@Override
	protected void setInitOptionImpl(Storage storage) throws Exception {
		this.auth = "HCP " + getBase64Value(storage.getWormId()) + ":" + getMD5Value(storage.getWormPass());
		this.address = storage.getAddress();
		if(!this.address.endsWith("/"))
			this.address = this.address + "/";
		this.baseDir = storage.getBaseDir();
		if(!this.baseDir.endsWith("/"))
			this.baseDir = this.baseDir + "/";
		this.client = HttpClients.createDefault();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		parser = factory.newSAXParser();
	}

	@Override
	protected String setLocImpl() throws Exception {
		//not use
		return "";
	}

	@Override
	protected long getStorageSizeImpl() throws Exception {
		String url = address + "proc/statistics";
		HttpGet request = new HttpGet(url);
		request.addHeader(HCPAUTHHEADERKEY, auth);
		HttpResponse response = client.execute(request);
		EntityUtils.consume(response.getEntity());
		if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
			throw new AdpatorException("Hcp get Total Infomation Has Error. return Code is " + response.getStatusLine().getStatusCode(), null);
		InputStream content = response.getEntity().getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(content));
		parser.parse(new InputSource(reader), new HcpStatReader(left));
		float temp =  (float)left.used / (float)left.total;
		return (long) (temp * 100);
	}


	private String getBase64Value(String input) throws UnsupportedEncodingException   {
		return DatatypeConverter.printBase64Binary(input.getBytes("UTF-8"));
	}

	private String getMD5Value(String input) throws NoSuchAlgorithmException   {
		StringBuilder buffer = new StringBuilder();
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] mdByte = md.digest(input.getBytes());
		for (int i = 0; i < mdByte.length; i++) {
			if (0 == (0xF0 & mdByte[i]))
				buffer.append("0");
			buffer.append(Integer.toHexString(0xFF & mdByte[i]));
		}
	    return buffer.toString();
	}

	private static class HcpStatReader extends DefaultHandler{
		HcpStorage left;
		public HcpStatReader(HcpStorage left) {
			this.left = left;
		}

		@Override
		public void startDocument() throws SAXException {

		}

		@Override
		public void endDocument() throws SAXException {

		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			HashMap<String,String> attrs = new HashMap<String, String>();
			for (int i = 0; i < attributes.getLength(); i++) {
				attrs.put(attributes.getQName(i).toUpperCase(),	attributes.getValue(i));
			}
			startParms(qName.toUpperCase(), attrs);
		}

		private void startParms(String szName,HashMap<String, String> attrs) {
			if(szName.equals("STATISTICS")){
				left.total = Long.parseLong(attrs.get("TOTALCAPACITY"));
				left.used  = Long.parseLong(attrs.get("USEDCAPACITY"));

			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			
		}
	}

	private static class HcpStorage{
		public long used = 0;
		public long total= 0;
	}

	@Override
	protected void storageSizeFullHandler() throws Exception {
		StorageController.getInstance().changeReadOnly(id, true);	
	}
}
