import org.junit.Test;

import yukcommon.dic.type.StorageType;
import yukcommon.model.Storage;

public class StorageTest extends AbsTest {

	public StorageTest() throws Exception {
		super();
	}

	@Test
	public void addStorage() throws Exception {
		Storage storage = new Storage();
		storage.setName("Disk3");
		storage.setType( StorageType.DISK);
		storage.setBaseDir("D:/fff");
		storage.setUsed(true);
		storage.setReadOnly(false);
		String a = storageApi.addStorage(storage);
		System.out.println(a);
	}

	@Test
	public void delStorage() throws Exception {
		Storage storage = new Storage();
		storage.setName("Disk3");
		storage.setType( StorageType.DISK);
		storage.setBaseDir("D:/ppp");
		storage.setUsed(true);
		storage.setReadOnly(false);
		String a = storageApi.addStorage(storage);
		storageApi.delStorage(a);
	}

	@Test
	public void updStorage() throws Exception {
		Storage storage = new Storage();
		storage.setId("aa");
		storage.setName("Disk3");
		storage.setType( StorageType.DISK);
		storage.setBaseDir("D:/ppp");
		storage.setUsed(true);
		storage.setReadOnly(false);
		storageApi.updStorage(storage);
	}
}
