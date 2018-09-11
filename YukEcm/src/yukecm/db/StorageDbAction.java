package yukecm.db;

import java.sql.SQLException;
import java.util.List;

import yukcommon.model.Storage;

public interface StorageDbAction extends AbsDbAction{

	void addStorage(Storage storage) throws SQLException;

	void delStorage(String id) throws SQLException;

	void updStorage(Storage storage) throws SQLException;

	Storage getStorageByName(String name) throws SQLException;

	Storage getStorage(String id) throws SQLException;

	List<Storage> getStorageList() throws SQLException;

}