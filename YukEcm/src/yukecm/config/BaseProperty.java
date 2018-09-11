package yukecm.config;

public class BaseProperty {
	private static class LazyHolder {
	    private static final BaseProperty base = new BaseProperty();
	}
	
	public static BaseProperty getInstance() {
		return LazyHolder.base;
	}
	//setting
	public String licKey = "";
	public boolean inMem = false;
	public int apNum=0;
	//Network
	public  boolean useInet = false;
	public  int port = 4400;
	public  boolean tcpnodelay = false;
	public  int backlog = 5000;
	public  int bufferSize = 4096;
	public  int timeOut = 9999999;
	public  int netMaxActive = 20;
	public  boolean reuseaddr = true;
	public  int clientPoolSize = 10;
	public  String type ="IO";
	//db
	public  String driver = "";
	public  String url= "";
	public  String user= "";
	public  String pawd= "";
	public  boolean encrypt = false;
	public  int maxActive= 5;
	public  int cursor = 9999;
	public  String validQuery = "select 1 from dual";
	//file
	public  String tempDir= "";
	public  String workDir= "";
	//providerClassName
	public  String aclProvider= "yukecm.spiimpl.DefaultAclProvider";
	public  String userProvider= "yukecm.spiimpl.DefaultUserProvider";
	public  String secureProvider= "yukecm.spiimpl.DefaultSecureProvider";
	public  String aceProvider="yukecm.spiimpl.DefaultAceProvider";
	public  String groupProvider="yukecm.spiimpl.DefaultGroupProvider";
	//serverSSL
	public  String keyfile="";
	public  String storepassword="";
	public  String keypassword="";
	//clientSSL
	public  String trustfile="";
	public  String trustpassword="";
	
	
}
