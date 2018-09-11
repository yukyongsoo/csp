package yukecm.injecter.storage;

import yukcommon.dic.type.CacheWorkType;
import yukcommon.model.Storage;
import yukecm.cache.inter.ReciveHandler;
import yukecm.controller.StorageController;
import yukecm.etc.EcmUtil;

public class StorageInjectorHandler implements ReciveHandler<String, Storage>{
	@Override
	public void handle(String k, Storage v, CacheWorkType workType) throws Exception {
		if(v.getId() != null && v.getId().length() > 0) {
			Storage str = StorageController.getInstance().getStorage(v.getId());
			if(str != null) {
				str.stopSizeChecker();
				str.stopExcutor();
			}
		}
		if(CacheWorkType.ADD == workType) {
			EcmUtil.setStorageAdaptor(v);
		}
	}
}
