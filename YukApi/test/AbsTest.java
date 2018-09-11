import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import yukecmapi.AclAceApi;
import yukecmapi.ClusterApi;
import yukecmapi.DocApi;
import yukecmapi.EcmApiFactory;
import yukecmapi.EcmConnection;
import yukecmapi.FolderApi;
import yukecmapi.LifeCycleApi;
import yukecmapi.MetaApi;
import yukecmapi.PipeApi;
import yukecmapi.RepoApi;
import yukecmapi.RuleApi;
import yukecmapi.StorageApi;
import yukecmapi.UserGroupApi;
import yukecmapi.WorkingApi;


public class AbsTest {
	protected DocApi docApi;
	protected UserGroupApi userGroupApi;
	protected WorkingApi workingApi;
	protected FolderApi folderApi;
	protected PipeApi pipeApi;
	protected RepoApi repoApi;
	protected RuleApi ruleApi;
	protected StorageApi storageApi;
	protected LifeCycleApi lcApi;
	protected MetaApi metaApi;
	protected ClusterApi clusterApi;
	protected AclAceApi aclApi;

	public AbsTest() throws Exception{
		SSLContext context = null;
		//context = SSLProviderUtil.getClientSSL("D:\\yong\\yukecm\\yukecm\\config\\client-truststore.jks", "clientStorePass");
		EcmApiFactory.init(40,true,true,10000,context);
		EcmConnection conn = EcmApiFactory.getConnection("M-PC:4400", "ADMIN", "admin");
		docApi = new DocApi(conn);
		folderApi = new FolderApi(conn);
		pipeApi = new PipeApi(conn);
		repoApi = new RepoApi(conn);
		ruleApi = new RuleApi(conn);
		storageApi = new StorageApi(conn);
		userGroupApi = new UserGroupApi(conn);
		workingApi = new WorkingApi(conn);
		lcApi = new LifeCycleApi(conn);
		metaApi = new MetaApi(conn);
		clusterApi = new ClusterApi(conn);
		aclApi = new AclAceApi(conn);
	}

	public void concurrentTest(Runnable r,int count) throws InterruptedException{
		List<Thread> list = new ArrayList<Thread>();
		for(int i =0 ; i < count; i++)
			list.add(new Thread(r));
		for(Thread t : list)
			t.start();
		for(Thread t : list)
			t.join();
	}
}
