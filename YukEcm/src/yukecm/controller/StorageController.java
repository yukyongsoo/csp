package yukecm.controller;

import java.sql.SQLException;
import java.util.List;

import yukcommon.dic.type.OnErrorType;
import yukcommon.model.Storage;
import yukecm.etc.EcmUtil;
import yukecm.injecter.storage.StorageInjector;

public class StorageController {
	
	private static class LazyHolder {
	    private static final StorageController storage = new StorageController();
	}

	public static StorageController getInstance(){
		return LazyHolder.storage;
	}

	private StorageController(){}

	public Storage getStorage(String id) throws SQLException, InterruptedException {
		return StorageInjector.getInstance().getStorage(id,OnErrorType.NONE);
	}

	public Storage getStorageByName(String name) throws SQLException, InterruptedException {
		return StorageInjector.getInstance().getStorageByName(name,OnErrorType.NONE);
	}

	public String addStorage(Storage storage) throws SQLException, InterruptedException  {
		StorageInjector.getInstance().getStorageByName(storage.getName(),OnErrorType.EXIST);
		storage.setId(EcmUtil.getId()); 
		EcmUtil.setStorageAdaptor(storage);
		StorageInjector.getInstance().addStorage(storage.getId(), storage);
		return storage.getId();
	}

	public void delStorage(Storage storage) throws SQLException, InterruptedException  {
		Storage str = StorageInjector.getInstance().getStorage(storage.getId(), OnErrorType.NOTEXIST);
		str.stopSizeChecker();
		str.stopExcutor();
		StorageInjector.getInstance().removeStorage(storage.getId());
	}

	public void updStorage(Storage storage) throws SQLException, InterruptedException  {
		Storage str = StorageInjector.getInstance().getStorage(storage.getId(), OnErrorType.NOTEXIST);
		str.stopSizeChecker();
		str.stopExcutor();
		EcmUtil.setStorageAdaptor(storage);
		StorageInjector.getInstance().updStorage(storage.getId(), storage);
	}

	public void changeReadOnly(String strId, boolean readOnly) throws InterruptedException, SQLException  {
		Storage storage = StorageInjector.getInstance().getStorage(strId, OnErrorType.NOTEXIST);
		if(readOnly != storage.isReadOnly()){
			storage.setReadOnly(readOnly);
			StorageInjector.getInstance().updStorage(storage.getId(), storage);
		}
	}

	public List<Storage> getStorageList() throws SQLException, InterruptedException {
		return StorageInjector.getInstance().getStorageList();
	}
}
