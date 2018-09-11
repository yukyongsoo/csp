package yukcommon.model.fileitem;

import java.io.IOException;
import java.io.InputStream;

public interface IFileItem {
	public boolean isInMemory();
	public InputStream getInputStream() throws IOException;
	public String getStoreLocation();
	public void write(String path) throws IOException;
	public long getSize() throws IOException;
	public String getName();
}
