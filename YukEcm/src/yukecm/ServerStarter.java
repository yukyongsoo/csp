 package yukecm;

import java.net.InetAddress;

import yukcommon.net.NetServer;
import yukcommon.net.SSLProviderUtil;
import yukcommon.net.io.NetIoClient;
import yukcommon.net.io.NetIoServer;
import yukcommon.net.nio.NetNioClient;
import yukcommon.net.nio.NetNioServer;
import yukecm.cache.YLC;
import yukecm.cache.inner.CacheProperty;
import yukecm.config.BaseProperty;
import yukecm.config.ConfigReader;
import yukecm.config.XmlReader;
import yukecm.controller.AceController;
import yukecm.controller.AclController;
import yukecm.controller.GroupController;
import yukecm.controller.SecureController;
import yukecm.controller.UserController;
import yukecm.db.DbConnFac;
import yukecm.etc.License;
import yukecm.handler.AclAceHandler;
import yukecm.handler.CacheHandler;
import yukecm.handler.ClusterHandler;
import yukecm.handler.DocHandler;
import yukecm.handler.FolderHandler;
import yukecm.handler.LifeCycleHandler;
import yukecm.handler.LifeCycleInnerHandler;
import yukecm.handler.MetaHandler;
import yukecm.handler.PipeHandler;
import yukecm.handler.RepositoryHandler;
import yukecm.handler.RuleHandler;
import yukecm.handler.SearchHandler;
import yukecm.handler.StorageHandler;
import yukecm.handler.UserGroupHandler;
import yukecm.handler.VersionHandler;
import yukecm.handler.WorkingGroupHandler;
import yukecm.injecter.CacheSender;
import yukecm.injecter.InjecterUtil;

public class ServerStarter {
	public static void main(String[] args) {
		try {	
			XmlReader reader;
			if(args != null && args.length > 0)
				reader = new XmlReader(args[0],new ConfigReader());
			else {
				System.out.println("can't find config file path. find default..");
				reader = new XmlReader("config/Config.Xml",new ConfigReader());
			}
				
			reader.parse();
			
			System.out.println("Your address is " + InetAddress.getLocalHost().getHostAddress());
			System.out.println("Your port is " + BaseProperty.getInstance().port);
			System.out.println("Your hostName is " + InetAddress.getLocalHost().getHostName());
			
			License license = License.getInstance();
			license.check(BaseProperty.getInstance().licKey, BaseProperty.getInstance().port);
			
			DbConnFac.getInstance().test(BaseProperty.getInstance().validQuery);

			NetServer server;
			if(BaseProperty.getInstance().type.equalsIgnoreCase("nio")) {
				NetNioClient.prepare(BaseProperty.getInstance().clientPoolSize, false,BaseProperty.getInstance().timeOut
						,SSLProviderUtil.getClientSSL(BaseProperty.getInstance().trustfile, BaseProperty.getInstance().trustpassword));
				server = NetNioServer.getInstance();
			}
			else {
				NetIoClient.prepare(BaseProperty.getInstance().clientPoolSize, false,BaseProperty.getInstance().timeOut
						,SSLProviderUtil.getClientSSL(BaseProperty.getInstance().trustfile, BaseProperty.getInstance().trustpassword));
				server = NetIoServer.getInstance();
			}
			server.setSSL(SSLProviderUtil.getServerSSL(BaseProperty.getInstance().keyfile, 
					BaseProperty.getInstance().storepassword, BaseProperty.getInstance().keypassword));
			YLC.init(CacheProperty.type, new CacheSender());
			//check provider error
			UserController.getInstance();
			GroupController.getInstance();
			AclController.getInstance();
			AceController.getInstance();
			SecureController.getInstance();
			//use inner injecter
			InjecterUtil.initAll();
			
			server.addHandler("/document/*", new DocHandler());
			server.addHandler("/folder/*", new FolderHandler());
			server.addHandler("/user/*", new UserGroupHandler()); 
			server.addHandler("/acl/*", new AclAceHandler());
			server.addHandler("/search/*", new SearchHandler());
			server.addHandler("/storage/*", new StorageHandler());
			server.addHandler("/working/*", new WorkingGroupHandler());
			server.addHandler("/rule/*", new RuleHandler());
			server.addHandler("/pipe/*", new PipeHandler());
			server.addHandler("/repository/*", new RepositoryHandler());
			server.addHandler("/cache/*", new CacheHandler());
			server.addHandler("/lifecycle/*", new LifeCycleHandler());
			server.addHandler("/innerlifecycle/*", new LifeCycleInnerHandler());
			server.addHandler("/meta/*", new MetaHandler());
			server.addHandler("/cluster/*", new ClusterHandler());
			server.addHandler("/versionr/*", new VersionHandler());
			server.setBacklog(BaseProperty.getInstance().backlog);
			server.setTcpNo(BaseProperty.getInstance().tcpnodelay);
			server.setBufferSize(BaseProperty.getInstance().bufferSize);
			server.setTimeOut(BaseProperty.getInstance().timeOut);
			server.start(BaseProperty.getInstance().port,BaseProperty.getInstance().useInet,
					BaseProperty.getInstance().netMaxActive);
			System.out.println("Server Started.");
		} catch (Exception e) {
			System.out.println("Server Start Fail.");
			e.printStackTrace();
			System.exit(0);
		}
	}
}
