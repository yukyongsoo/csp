package ecmTest.SPITest;

import java.io.InputStream;

import yukcommon.adaptor.StorageAdaptor;
import yukcommon.model.Content;
import yukcommon.model.Storage;

public class TestStorage extends StorageAdaptor{

	@Override
	protected String addFileImpl(Content content) throws Exception {
		return null;
	}

	@Override
	protected InputStream getFileImpl(Content content) throws Exception {
		return null;
	}

	@Override
	protected void deleteFileImpl(Content content) throws Exception {

	}

	@Override
	protected long setRetentionImpl(Content content, long time) throws Exception {
		return 0;
	}

	@Override
	protected void setInitOptionImpl(Storage storage) throws Exception {

	}

	@Override
	protected String setLocImpl() throws Exception {
		return null;
	}

	@Override
	protected long getStorageSizeImpl() throws Exception {
		return 0;
	}

	@Override
	protected void storageSizeFullHandler() throws Exception {
		
	}

}
