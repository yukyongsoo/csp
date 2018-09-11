package yukcommon.model.fileitem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import yukcommon.exception.ZeroFileSizeException;

public class PathFileItem implements IFileItem{
	private File file;

	public PathFileItem(String filePath) throws FileNotFoundException {
		file = new File(filePath);
		if(!file.exists())
			throw new FileNotFoundException("current file Path : " + filePath + " Not existed");
		if(!file.isFile())
			throw new FileNotFoundException("current file Path : " + filePath + " Not File");
		if(file.length() < 1)
			throw new ZeroFileSizeException("path File Item size is 0");
	}

	@Override
	public boolean isInMemory(){
		return false;
	}

	@Override
	public InputStream getInputStream() throws FileNotFoundException{
		return new FileInputStream(file);
	}

	@Override
	public String getStoreLocation(){
		return file.getAbsolutePath();
	}

	@Override
	public void write(String path) throws IOException {
		FileUtils.copyFile(file, new File(path));
	}

	@Override
	public long getSize() {
		return file.length();
	}

	@Override
	public String getName() {
		return file.getName();
	}
}
