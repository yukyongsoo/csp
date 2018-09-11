package yukecm.mount;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

import com.filepool.fplibrary.FPClip;
import com.filepool.fplibrary.FPFileInputStream;
import com.filepool.fplibrary.FPLibraryConstants;
import com.filepool.fplibrary.FPPool;
import com.filepool.fplibrary.FPPool.PoolInfo;
import com.filepool.fplibrary.FPTag;

import yukcommon.adaptor.StorageAdaptor;
import yukcommon.dic.EtcDic;
import yukcommon.model.Content;
import yukcommon.model.Storage;
import yukecm.config.BaseProperty;
import yukecm.controller.StorageController;

public class CenteraAdaptor extends StorageAdaptor{
	String poolAddress = "168.159.214.23,168.159.214.24,168.159.214.25,168.159.214.26?/c2profile1.pea";
	String tagName = "StoreContentObject";
	String clipName = "Store-Content-File";
	FPPool pool;

	@Override
	protected String addFileImpl(Content content) throws Exception {
		FPClip clip = null;
		FPFileInputStream fis = null;
		FPTag tag = null;
		FPTag newer = null;
		try {
			clip = new FPClip(pool,clipName);
			if(content.getItem().isInMemory()){
				String tempPath = BaseProperty.getInstance().tempDir + EtcDic.PATHSEP + UUID.randomUUID().toString();
				content.getItem().write(tempPath);
				fis = new FPFileInputStream(new File(tempPath));
			}
			else
				fis = new FPFileInputStream(new File(content.getItem().getStoreLocation()));
			tag = clip.getTopTag();
			newer = new FPTag(tag, tagName);
			newer.setAttribute("filename", content.getName());
			newer.BlobWrite(fis);
			return clip.Write();
		} finally {
			if(fis != null)
				fis.close();
			if(newer != null)
				newer.Close();
			if(tag != null)
				tag.Close();
			if(clip != null)
				clip.Close();
		}
	}

	@Override
	protected InputStream getFileImpl(Content content) throws Exception {
		FPClip clip = new FPClip(pool, content.getLoc(),FPLibraryConstants.FP_OPEN_FLAT);
		FPTag topTag = clip.getTopTag();
		String temp = BaseProperty.getInstance().tempDir + "/" + UUID.randomUUID().toString();
		FileOutputStream outFile = new FileOutputStream(temp);
		topTag.BlobRead(outFile);	
		outFile.close();
		topTag.Close();
		clip.Close();
		return new FileInputStream(temp);
	}

	@Override
	protected void deleteFileImpl(Content content) throws Exception {
		FPClip.Delete(pool, content.getLoc());
	}

	@Override
	protected long setRetentionImpl(Content content, long time) throws Exception {
		FPClip clip = new FPClip(pool,content.getLoc());
		clip.setRetentionPeriod(time);
		clip.Write();
		return time;
	}

	@Override
	protected void setInitOptionImpl(Storage storage) throws Exception {
		this.poolAddress = storage.getAddress();
		this.clipName = storage.getClip();
		this.tagName = storage.getTag();
		pool = new FPPool(poolAddress);
	}

	@Override
	protected String setLocImpl() throws Exception {
		// Not Use.
		return "";
	}

	@Override
	protected long getStorageSizeImpl() throws Exception {
		PoolInfo poolInfo = pool.getPoolInfo();
		float temp = (float)poolInfo.getFreeSpace() / (float)poolInfo.getCapacity();
		return  (long) (temp * 100) ;
	}

	@Override
	protected void storageSizeFullHandler() throws Exception {
		StorageController.getInstance().changeReadOnly(id, true);
	}
}
