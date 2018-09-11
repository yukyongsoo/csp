package yukecm.injecter.storage;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import yukcommon.adaptor.StorageAdaptor;
import yukcommon.dic.type.OnErrorType;
import yukcommon.model.Storage;
import yukecm.cache.Cache;
import yukecm.cache.YLC;
import yukecm.config.BaseProperty;
import yukecm.db.DbConnFac;
import yukecm.db.StorageDbAction;
import yukecm.etc.EcmUtil;
import yukecm.injecter.InjecterUtil;

public class StorageInjector {
	private static StorageInjector injecter;

	public static StorageInjector getInstance() throws InterruptedException, SQLException {
		if(injecter == null)
			injecter = new StorageInjector();
		return injecter;
	}

	private Cache<String, Storage> storageMap;
	//map used when memory cache not working
	private ConcurrentHashMap<String, StorageAdaptor> adaptorMap = new ConcurrentHashMap<String, StorageAdaptor>();

	private StorageInjector() throws InterruptedException, SQLException {
		if(BaseProperty.getInstance().inMem)
			storageMap = YLC.makeCache("STR", new StorageJson(), new StorageInjectorHandler());
		initStorage();
	}

	public void initStorage() throws InterruptedException, SQLException {
		storageMap.clear();
		List<Storage> list = getStorageList();
		for(Storage str : storageMap.values()) {
			str.stopSizeChecker();
		}
		for (Storage storage : list) {
			EcmUtil.setStorageAdaptor(storage);
			if(BaseProperty.getInstance().inMem)
				storageMap.put(storage.getId(), storage);
			else
				adaptorMap.put(storage.getId(), storage.getAdt());
		}
	}


	public void addStorage(String id, Storage storage) throws InterruptedException, SQLException   {
		StorageDbAction action = null;
		try {
			if(BaseProperty.getInstance().inMem)
				storageMap.put(id, storage);
			else
				adaptorMap.put(storage.getId(), storage.getAdt());
			action = DbConnFac.getInstance().getStorageDbAction();
			action.addStorage(storage);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem)
				storageMap.remove(id);
			else
				adaptorMap.remove(id);
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void removeStorage(String id) throws InterruptedException, SQLException   {
		Storage backup = null;
		StorageDbAction action = null;
		StorageAdaptor remove = null;
		try {
			if(BaseProperty.getInstance().inMem)
				backup = storageMap.remove(id);
			else
				remove = adaptorMap.remove(id);
			action = DbConnFac.getInstance().getStorageDbAction();
			action.delStorage(id);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem)
				storageMap.put(id, backup);
			else
				adaptorMap.put(id, remove);
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public Storage getStorage(String id,OnErrorType type) throws SQLException{
		StorageDbAction action = null;
		Storage str;
		try {
			if(BaseProperty.getInstance().inMem) {
				str = storageMap.get(id);
				InjecterUtil.onErrorException(str, type);
				return str;
			}
			action = DbConnFac.getInstance().getStorageDbAction();
			str = action.getStorage(id);
			InjecterUtil.onErrorException(str, type);
			str.setAdt(adaptorMap.get(str.getId()));
			return str;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void updStorage(String id, Storage storage) throws InterruptedException, SQLException {
		Storage backup = null;
		StorageDbAction action = null;
		StorageAdaptor remove = null;
		try {
			if(BaseProperty.getInstance().inMem){
				backup = storageMap.remove(id);
				storageMap.put(id, storage);
			}
			else{
				remove = adaptorMap.remove(id);
				adaptorMap.put(id, storage.getAdt());
			}
			action = DbConnFac.getInstance().getStorageDbAction();
			action.updStorage(storage);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem)
				storageMap.put(id, backup);
			else
				adaptorMap.put(id, remove);
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public Storage getStorageByName(String name,OnErrorType type) throws SQLException {
		StorageDbAction action = null;
		Storage str = null;
		try {
			if(BaseProperty.getInstance().inMem){
				for(Storage storage : storageMap.values()){
					if(storage.getName().equals(name)) {
						str = storage;
						break;
					}
				}
				InjecterUtil.onErrorException(str, type);
				return str;
			}
			action = DbConnFac.getInstance().getStorageDbAction();
			str = action.getStorageByName(name);
			InjecterUtil.onErrorException(str, type);
			str.setAdt(adaptorMap.get(str.getId()));
			return str;
		}
		finally {
			DbConnFac.staticClose(action);
		}
	}

	public List<Storage> getStorageList() throws SQLException  {
		//Always check db in this
		StorageDbAction action = null;
		try {
			action = DbConnFac.getInstance().getStorageDbAction();
			return action.getStorageList();
		} finally {
			DbConnFac.staticClose(action);
		}
	}
}
