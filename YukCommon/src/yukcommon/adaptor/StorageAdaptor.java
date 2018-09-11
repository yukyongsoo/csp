package yukcommon.adaptor;

import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import yukcommon.exception.AdpatorException;
import yukcommon.model.Content;
import yukcommon.model.Storage;
import yukcommon.util.LoggerUtil;

public abstract class StorageAdaptor {
	private ExecutorService executor;
	private StorageSizeChecker checker;
	protected String id = "";
	
	public String addFile(final Content content) {
		final long start = System.currentTimeMillis();
		try {
			Callable<String> temp = new Callable<String>() {
				@Override
				public String call() throws Exception {
					String loc =  addFileImpl(content);
					LoggerUtil.traceTime(getClass(), "addFile to Storage Time is {}Ms", start);
					return loc;
				}
			};
			Future<String> submit = executor.submit(temp);
			return submit.get();
		}
		catch (Exception e) {
			LoggerUtil.traceTime(getClass(), "addFile to Storage fail.Time is {}Ms", start);
			throw new AdpatorException("Storage Adapator Add File Has Error.",e);
		}
	}

	public InputStream getFile(final Content content)   {
		final long start = System.currentTimeMillis();
		try {
			Callable<InputStream> temp = new Callable<InputStream>() {
				@Override
				public InputStream call() throws Exception {
					InputStream is =  getFileImpl(content);
					LoggerUtil.traceTime(getClass(), "getFile to Storage Time is {}Ms", start);
					return is;
				}
			};
			Future<InputStream> submit = executor.submit(temp);
			return submit.get();
		} catch (Exception e) {
			LoggerUtil.traceTime(getClass(), "getFile to Storage fail.Time is {}Ms", start);
			throw new AdpatorException("Storage Adapator get File Has Error.",e);
		}
	}

	public void deleteFile(final Content content) {
		final long start = System.currentTimeMillis();
		try {
			Callable<Void> temp = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					deleteFileImpl(content);
					LoggerUtil.traceTime(getClass(), "deleteFile to Storage Time is {}Ms", start);
					return null;
				}
			};
			Future<Void> submit = executor.submit(temp);
			submit.get();
		} catch (Exception e) {
			LoggerUtil.traceTime(getClass(), "deleteFile to Storage fail.Time is {}Ms", start);
			throw new AdpatorException("Storage Adapator delete File Has Error.",e);
		}
	}

	public long setRetention(Content content,long time) {
		try {
			return setRetentionImpl(content,time);
		} catch (Exception e) {
			throw new AdpatorException("Storage Adapator set Retension Has Error.",e);
		}
	}

	public void setInitOption(final Storage storage) {
		try {
			if(executor != null) {
				executor.shutdown();
			}
			executor = Executors.newFixedThreadPool(storage.getThreadCount(), new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					return new Thread(r, storage.getName() + "-WorkThread");
				}
			});
			this.id = storage.getId();
			setInitOptionImpl(storage);
			if(checker == null) {
				checker = new StorageSizeChecker(this);
			}
			if(!checker.isAlive())
				checker.start();
		} catch (Exception e) {
			throw new AdpatorException("Storage Adapator set Init Option Has Error.",e);
		}
	}
	
	public void stopSizeChecker() {
		if(checker != null)
			checker.stopChcking();
	}
	
	public void stopExcutor() {
		if(executor != null)
			executor.shutdown();
	}

	protected abstract  String addFileImpl(Content content) throws Exception ; //return Loc
	protected abstract  InputStream getFileImpl(Content content) throws Exception ; //return Mem Or FileStream
	protected abstract  void deleteFileImpl(Content content) throws Exception;
	protected abstract  long setRetentionImpl(Content content,long time) throws Exception ;
	protected abstract  void setInitOptionImpl(Storage storage) throws Exception;
	//inner use
	protected abstract  String setLocImpl() throws Exception; //input is FileName.
	protected abstract  long getStorageSizeImpl() throws Exception;
	protected abstract  void storageSizeFullHandler() throws Exception;
}
