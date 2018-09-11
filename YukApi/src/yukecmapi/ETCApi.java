package yukecmapi;

import yukcommon.dic.UriDic;

public class ETCApi  extends AbsApi{

	public ETCApi(EcmConnection conn) {
		super(conn);
	}
	
	public void reinitCache() throws Exception{
		ETCApiImpl impl = EcmApiFactory.getETCApi(conn,UriDic.REINITCACHE);
		impl.reinitCache();
	}

}
