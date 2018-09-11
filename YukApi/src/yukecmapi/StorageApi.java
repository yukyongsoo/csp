package yukecmapi;

import java.util.List;

import yukcommon.dic.UriDic;
import yukcommon.exception.NotNullAllowedException;
import yukcommon.model.Storage;

public class StorageApi extends AbsApi{
	public StorageApi(EcmConnection conn) {
		super(conn);
	}

	public String addStorage(Storage storage) throws Exception{
		if(storage == null)
			throw new NotNullAllowedException("add storage needed Storage Object");
		StrApiImpl impl = EcmApiFactory.getStorageApi(conn,UriDic.ADDSTORAGE);
		return impl.addStorage(storage);
	}

	public void delStorage(String id) throws Exception{
		StrApiImpl impl = EcmApiFactory.getStorageApi(conn,UriDic.DELSTORAGE);
		impl.delStorage(id);
	}

	public void updStorage(Storage storage) throws Exception{
		if(storage == null)
			throw new NotNullAllowedException("upd storage needed Storage Object");
		StrApiImpl impl = EcmApiFactory.getStorageApi(conn,UriDic.UPDSTORAGE);
		impl.updStorage(storage);
	}

	public List<Storage> getStorage() throws Exception{
		StrApiImpl impl = EcmApiFactory.getStorageApi(conn,UriDic.GETSTORAGE);
		return impl.getStorage();
	}

}
