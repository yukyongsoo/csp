package yukecmapi;

import javax.net.ssl.SSLContext;

import yukcommon.dic.EtcDic;
import yukcommon.exception.NotExcutedException;
import yukcommon.net.NetClient;
import yukcommon.net.NetWrapper;
import yukcommon.net.io.NetIoClient;
import yukcommon.net.io.NetIoWrapper;
import yukcommon.net.nio.NetNioClient;
import yukcommon.net.nio.NetNioWrapper;

public abstract class EcmApiFactory {
	private static boolean init = false;
	private static boolean nio = false;

	private EcmApiFactory() {}
	
	public static void init(int poolSize,boolean reuseAddr,boolean useNio,int timeOut,SSLContext context){
		nio = useNio;
		if(nio)
			NetNioClient.prepare(poolSize, reuseAddr,timeOut,context);
		else	
			NetIoClient.prepare(poolSize,reuseAddr,timeOut,context);
		init = true;
	}
	
	public static EcmConnection getConnection(String serverUrl,  String id, String password) {
		return new EcmConnection(serverUrl,id,password,false);
		
	}
	
	public static EcmConnection getEncryptedConnection(String serverUrl,  String id, String password) {
		return new EcmConnection(serverUrl,id,password,true);
		
	}
	
	protected static DocApiImpl getDocApi(EcmConnection conn,String url) {
		return new DocApiImpl(checkClient(), checkUrl(conn, url));
		
	}

	protected static FolderApiImpl getFolderApi(EcmConnection conn, String url)  {
		return new FolderApiImpl(checkClient(), checkUrl(conn,url));
	}

	protected static PipeApiImpl getPipeApi(EcmConnection conn, String url)  {
		return new PipeApiImpl(checkClient(), checkUrl(conn,url));
	}

	protected static RepoApiImpl getRepoApi(EcmConnection conn, String url) {
		return new RepoApiImpl(checkClient(), checkUrl(conn,url));
	}

	protected static RuleApiImpl getRuleApi(EcmConnection conn, String url) {
		return new RuleApiImpl(checkClient(), checkUrl(conn,url));
	}

	protected static StrApiImpl getStorageApi(EcmConnection conn, String url){
		return new StrApiImpl(checkClient(), checkUrl(conn,url));
	}

	protected static UserGroupApiImpl getUserGroupApi(EcmConnection conn, String url) {
		return new UserGroupApiImpl(checkClient(), checkUrl(conn,url));
	}

	protected static WorkingApiImpl getWoringApi(EcmConnection conn,String url) {
		return new WorkingApiImpl(checkClient(), checkUrl(conn,url));
	}

	protected static LifeCycleApiImpl getLifeCycleApi(EcmConnection conn,String url) {
		return new LifeCycleApiImpl(checkClient(), checkUrl(conn,url));
	}

	protected static MetaApiImpl getMetaApi(EcmConnection conn,String url) {
		return new MetaApiImpl(checkClient(), checkUrl(conn,url));
	}
	
	protected static ClusterApiImpl getClusterApi(EcmConnection conn,String url) {
		return new ClusterApiImpl(checkClient(), checkUrl(conn,url));
	}
	
	protected static ETCApiImpl getETCApi(EcmConnection conn, String url){
		return new ETCApiImpl(checkClient(), checkUrl(conn,url));
	}
	
	protected static AclAceApiImpl getAclAceApi(EcmConnection conn, String url){
		return new AclAceApiImpl(checkClient(), checkUrl(conn,url));
	}


	private static NetClient checkClient() {
		if(!init)
			throw new NotExcutedException("You Need Call EcmApiFactory.init function before Use this.");
		NetClient client;
		if(nio)
			client = NetNioClient.getInstance();
		else	
			client = NetIoClient.getInstance();
		return client;
	}

	private static NetWrapper checkUrl(EcmConnection conn, String url){
		NetWrapper wrap;
		if(nio)
			wrap = new NetNioWrapper(conn.getServerUrl(),url);
		else
			wrap = new NetIoWrapper(conn.getServerUrl(),url);
		wrap.addHeader(EtcDic.CREDENTIAL, conn.getCreJson());
		return wrap;
	}


}
