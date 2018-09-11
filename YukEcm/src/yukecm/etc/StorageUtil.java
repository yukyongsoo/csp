package yukecm.etc;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yukcommon.exception.NotExistException;
import yukcommon.model.Content;
import yukcommon.model.Pair;
import yukcommon.model.Repository;
import yukcommon.model.Storage;
import yukcommon.model.subrepo.SubRepoStr;
import yukecm.controller.RepositoryController;
import yukecm.controller.StorageController;

public abstract class StorageUtil {
	private StorageUtil() {}
	
	public static Map<Content, Pair<Repository, Storage>> findRepositorys(List<Content> contents) throws SQLException, InterruptedException {
		Map<Content, Pair<Repository, Storage>> map = new HashMap<Content,  Pair<Repository, Storage>>();
		for(Content content : contents){
			Pair<Repository, Storage> pair = RepositoryController.getInstance().findRepo(content.getStrId());
			if(pair != null)
				map.put(content, pair);
		}
		if (map.isEmpty())
			throw new NotExistException("can't find any Repositoy to doc");
		return map;
	}
	
	public static void addContentToStr(String repoId, Content content) throws InterruptedException, SQLException  {
		Storage storage = getUsablePutStorage(repoId);
		if(storage == null)
			throw new NotExistException("can't find any usable Storage.repoId is " + repoId);
		content.setStrId(storage.getId());
		storage.addFile(content);
	}
	
	private static Storage getUsablePutStorage(String repoId) throws SQLException, InterruptedException {
		Repository temp = new Repository();
		temp.setId(repoId);
		Repository repoStr = RepositoryController.getInstance().getRepoStr(temp);
		if(repoStr == null)
			throw new NotExistException(repoId + " not have any storage");
		for(Integer I : repoStr.getPutOrderList().keySet()){
			SubRepoStr subRepoStr = repoStr.getPutOrderList().get(I);
			Storage storage = StorageController.getInstance().getStorage(subRepoStr.getStrId());
			if(!storage.isReadOnly() && storage.isUsed())
				return  storage;
		}
		return null;
	}
}
