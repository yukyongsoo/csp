package yukcommon.adaptor;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import yukcommon.exception.AdpatorException;
import yukcommon.model.Content;
import yukcommon.model.Storage;
import yukcommon.util.LoggerUtil;

public abstract class StorageAdaptorBackUp {
	private ExecutorService executor;
	
	public String addFile(final Content content) {
		long start = System.currentTimeMillis();
		try {
			String loc =  addFileImpl(content);
			LoggerUtil.traceTime(getClass(), "addFile to Storage Time is {}Ms", start);
			return loc;
		}
		catch (Exception e) {
			LoggerUtil.traceTime(getClass(), "addFile to Storage fail.Time is {}Ms", start);
			throw new AdpatorException("Storage Adapator Add File Has Error.",e);
		}
	}

	public InputStream getFile(Content content) {
		long start = System.currentTimeMillis();
		try {
			InputStream is =  getFileImpl(content);
			LoggerUtil.traceTime(getClass(), "getFile to Storage Time is {}Ms", start);
			return is;
		} catch (Exception e) {
			LoggerUtil.traceTime(getClass(), "getFile to Storage fail.Time is {}Ms", start);
			throw new AdpatorException("Storage Adapator get File Has Error.",e);
		}
	}

	public void deleteFile(Content content) {
		long start = System.currentTimeMillis();
		try {
			deleteFileImpl(content);
			LoggerUtil.traceTime(getClass(), "deleteFile to Storage Time is {}Ms", start);
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
			setInitOptionImpl(storage);
		} catch (Exception e) {
			throw new AdpatorException("Storage Adapator set Init Option Has Error.",e);
		}
	}

	protected abstract  String addFileImpl(Content content) throws Exception ; //return Loc
	protected abstract InputStream getFileImpl(Content content) throws Exception ; //return Mem Or FileStream
	protected abstract void deleteFileImpl(Content content) throws Exception;
	protected abstract long setRetentionImpl(Content content,long time) throws Exception ;
	protected abstract void setInitOptionImpl(Storage storage) throws Exception;
	//inner use
	protected abstract String setLocImpl() throws Exception; //input is FileName.
	protected abstract long getStorageSizeImpl() throws Exception;

}
