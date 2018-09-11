package yukecm.mount;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.joda.time.LocalDate;

import yukcommon.adaptor.StorageAdaptor;
import yukcommon.dic.EtcDic;
import yukcommon.exception.NotExcutedException;
import yukcommon.model.Content;
import yukcommon.model.Storage;
import yukecm.config.BaseProperty;
import yukecm.controller.StorageController;

public class DiskAdaptor extends StorageAdaptor{
	String baseDir = "";
	int maxHash = 300;
	int current = 1;
	
	@Override
	protected String addFileImpl(Content content) throws Exception{
		String loc = setLocImpl();
		loc = loc + EtcDic.PATHSEP + content.getDocId();
		content.setLoc(loc);
		loc = baseDir + EtcDic.PATHSEP + loc;
		File locFile = new File(loc);
		String tempLoc = content.getItem().getStoreLocation();
		if(tempLoc != null)
			FileUtils.moveFile(new File(tempLoc), locFile);
		else
			FileUtils.copyInputStreamToFile(content.getItem().getInputStream(), locFile);
		return loc;
	}

	@Override
	protected InputStream getFileImpl(Content content) throws Exception {
		String loc = baseDir + EtcDic.PATHSEP  + content.getLoc();
		return new FileInputStream(loc);
	}

	@Override
	protected void deleteFileImpl(Content content) throws Exception {
		String loc = baseDir + EtcDic.PATHSEP  + content.getLoc();
		File locFile = new File(loc);
		boolean deleted = locFile.delete();
		if(!deleted)
			throw new NotExcutedException("File Not Deleted.docid = " + content.getDocId() 
				+ "strId = " + content.getStrId());
	}

	@Override
	protected long setRetentionImpl(Content content, long time) throws Exception {
		// TODO impl retention class need to be db column
		return 0;
	}

	@Override
	protected String setLocImpl() throws Exception {
		LocalDate now = LocalDate.now();
		String path = Integer.toString(BaseProperty.getInstance().apNum) +  EtcDic.PATHSEP  
				+ now.getYear() +  EtcDic.PATHSEP
				+ now.getMonthOfYear() + EtcDic.PATHSEP
				+ now.getDayOfMonth() +  EtcDic.PATHSEP
				+ current;
		File file = new File(this.baseDir +  EtcDic.PATHSEP  + path);
		FileUtils.forceMkdir(file);
		if(current++ > maxHash)
			current = 1;
		return path;
	}

	@Override
	protected long getStorageSizeImpl() throws Exception{
		File file = new File(this.baseDir);
		float temp = (float)file.getUsableSpace() / (float)file.getTotalSpace();
		return 	(long) (temp * 100);
	}

	@Override
	protected void setInitOptionImpl(Storage storage) throws Exception{
		this.baseDir = storage.getBaseDir();
		FileUtils.forceMkdir(new File(baseDir));
	}

	@Override
	protected void storageSizeFullHandler() throws Exception {
		StorageController.getInstance().changeReadOnly(id, true);
	}


}
