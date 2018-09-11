package yukcommon.adaptor;

import yukcommon.util.LoggerUtil;

public class StorageSizeChecker extends Thread{
	private StorageAdaptor sat;
	private boolean stop = false; 

	public StorageSizeChecker(StorageAdaptor storageAdaptor) {
		this.sat = storageAdaptor;
		setName(sat.id + "_StrSizeChecker");
	}

	public void stopChcking() {
		stop = true;
		interrupt();
	}
	
	@Override
	public void run() {
		while(!stop) {
			try {
				long per = sat.getStorageSizeImpl();
				if(per < 10)
					sat.storageSizeFullHandler();
				sleep(600000);
			}catch (InterruptedException e) {
				stop = true;
				LoggerUtil.info(getClass(), "Storage size checker Interupted by User",null);
				interrupt();
			} 			
			catch (Exception e) {
				stop = true;
				LoggerUtil.error(getClass(), "Storage " + sat.id + " size check Error", e);
			}
		}
	}
}
