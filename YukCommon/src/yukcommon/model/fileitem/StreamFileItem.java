package yukcommon.model.fileitem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.NotSupportException;
import yukcommon.exception.WrongOperationException;

public class StreamFileItem implements IFileItem{
	private InputStream stream;
	private File lastSaved = null;

	public StreamFileItem(InputStream stream) {
		if(stream == null)
			throw new NotNullAllowedException("Stream is Null");
		this.stream = stream;
	}

	@Override
	public boolean isInMemory()  {
		return true;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		if(stream == null)
			throw new WrongOperationException("stream not setted");
		return stream;
	}

	@Override
	public String getStoreLocation() {
		if(lastSaved != null)
			return lastSaved.getAbsolutePath();
		return null;
	}

	@Override
	public void write(String path) throws IOException   {
		FileUtils.copyInputStreamToFile(getInputStream(), lastSaved);
		lastSaved = new File(path);
	}

	@Override
	public long getSize() throws IOException {
		return stream.available();
	}

	@Override
	public String getName() {
		if(lastSaved != null)
			return lastSaved.getName();
		throw new NotSupportException("stream Item can't be saved. stream not have any Name");
	}
}
