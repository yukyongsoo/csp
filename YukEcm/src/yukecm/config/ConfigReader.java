package yukecm.config;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import yukcommon.dic.type.CacheType;
import yukcommon.dic.type.DbCursorType;
import yukcommon.exception.NotSupportException;
import yukcommon.util.EncryptUtil;
import yukecm.cache.inner.CacheProperty;

public class ConfigReader extends DefaultHandler{
	@Override
	public void startDocument() throws SAXException {

	}

	@Override
	public void endDocument() throws SAXException {

	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		Map<String,String> attrs = new HashMap<String, String>();
		for (int i = 0; i < attributes.getLength(); i++) {
			attrs.put(attributes.getQName(i).toUpperCase(),	attributes.getValue(i));
		}
		startParms(qName.toUpperCase(), attrs);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
	}

	public void startParms(String szName, Map<String, String> attrs) {
		if(szName.equals("LICENSE")) {
			BaseProperty.getInstance().licKey = attrs.get("KEY");
		}		
		else if(szName.equals("SERVERSSL")) {
			BaseProperty.getInstance().keyfile = attrs.get("KEYFILE");
			BaseProperty.getInstance().storepassword = attrs.get("STOREPASSWORD");
			BaseProperty.getInstance().keypassword = attrs.get("KEYPASSWORD");
		}
		else if(szName.equals("CLIENTSSL")) {
			BaseProperty.getInstance().trustfile = attrs.get("TRUSTFILE");
			BaseProperty.getInstance().trustpassword = attrs.get("TRUSTPASSWORD");
		}
		else if(szName.equals("INMEM")){
			BaseProperty.getInstance().inMem = Boolean.parseBoolean(attrs.get("USE"));
			CacheProperty.type = CacheType.valueOf(attrs.get("TYPE").toUpperCase());
		}
		else if(szName.equals("NETWORK")){
			BaseProperty.getInstance().apNum = Integer.parseInt(attrs.get("APNUM"));
			BaseProperty.getInstance().useInet = Boolean.parseBoolean(attrs.get("USEINET"));
			BaseProperty.getInstance().port =  Integer.parseInt(attrs.get("PORT"));
			BaseProperty.getInstance().tcpnodelay = Boolean.parseBoolean(attrs.get("TCPNODELAY"));
			BaseProperty.getInstance().backlog =  Integer.parseInt(attrs.get("BACKLOG"));
			BaseProperty.getInstance().bufferSize =  Integer.parseInt(attrs.get("BUFFERSIZE"));
			BaseProperty.getInstance().timeOut =  Integer.parseInt(attrs.get("TIMEOUT"));
			BaseProperty.getInstance().netMaxActive = Integer.parseInt(attrs.get("MAXACTIVE"));
			BaseProperty.getInstance().reuseaddr = Boolean.parseBoolean(attrs.get("REUSEADDR"));
			BaseProperty.getInstance().clientPoolSize =  Integer.parseInt(attrs.get("CLIENTPOOLSIZE"));
			BaseProperty.getInstance().type = attrs.get("TYPE");
		}
		else if(szName.equals("DB")){
			BaseProperty.getInstance().driver = attrs.get("DRIVER");
			BaseProperty.getInstance().url = attrs.get("URL");
			BaseProperty.getInstance().user = attrs.get("USER");
			BaseProperty.getInstance().pawd = attrs.get("PAWD");
			BaseProperty.getInstance().encrypt = Boolean.parseBoolean(attrs.get("ENCRYPT"));
			BaseProperty.getInstance().maxActive =  Integer.parseInt(attrs.get("MAXACTIVE"));
			BaseProperty.getInstance().validQuery = attrs.get("VALIDQUERY");
			if(BaseProperty.getInstance().encrypt)
				BaseProperty.getInstance().pawd = EncryptUtil.getInstance().decryptConfigKey(BaseProperty.getInstance().pawd);
			String dbType = attrs.get("CURSOR").toUpperCase();
			if("ORA".equals(dbType) || "ORACLE".equals(dbType))
				BaseProperty.getInstance().cursor = DbCursorType.Oracle;		
			else
				throw new NotSupportException("current dbtype not support");
			
		}
		else if(szName.equals("FILE")){
			BaseProperty.getInstance().tempDir = attrs.get("TEMPDIR");
			BaseProperty.getInstance().workDir = attrs.get("WORKDIR");
		}
		else if(szName.equals("ACL")){
			String temp = attrs.get("PROVIDER");
			if(temp != null && !temp.isEmpty())
				BaseProperty.getInstance().aclProvider = temp;
		}
		else if(szName.equals("USER")){
			String temp = attrs.get("PROVIDER");
			if(temp != null && !temp.isEmpty())
				BaseProperty.getInstance().userProvider = temp;
		}
		else if(szName.equals("SECURE")){
			String temp = attrs.get("PROVIDER");
			if(temp != null && !temp.isEmpty())
				BaseProperty.getInstance().secureProvider = temp;
		}
		else if(szName.equals("ACE")){
			String temp = attrs.get("PROVIDER");
			if(temp != null && !temp.isEmpty())
				BaseProperty.getInstance().aceProvider = temp;
		}
		else if(szName.equals("GROUP")){
			String temp = attrs.get("PROVIDER");
			if(temp != null && !temp.isEmpty())
				BaseProperty.getInstance().groupProvider = temp;
		}
	}
}
