import org.junit.Test;

public class DocTest extends AbsTest{
	
	public DocTest() throws Exception {
		super();
	}

	@Test
	public void addDoc() throws Exception{
		for(int i =0;i<1;i++) {
			String id = docApi.addDoc("c60681c1-9fce-4e06-adef-03f26ed2c0ac","test","D:/.MediaID.bin","");
			System.out.println(id);
		}
	}

	@Test
	public void getDoc() throws Exception{
		String id = docApi.addDoc("c60681c1-9fce-4e06-adef-03f26ed2c0ac","test","D:/.MediaID.bin","");
		docApi.getDoc(id,"D:/test/temp",".MediaID.bin"); // file path + folder on
	}

	@Test
	public void delDoc() throws Exception{
		String id = docApi.addDoc("8a110690-2605-43da-8c10-6109aa8b98cc","test","D:/install.res.3082.dll","");
		docApi.delDoc(id);
	}

	int count = 50;
	int loopCount = 10;
	Exception ex;
	@Test
	public void addDocMulti() throws Exception{
		Runnable r = new Runnable() {
			public void run() {
				try {
					for(int i = 0 ; i < loopCount; i++) {
						String id = docApi.addDoc("c60681c1-9fce-4e06-adef-03f26ed2c0ac","test","D:/MediaID.bin","");
						//docApi.getDoc(id, "D:/test2",id);
						//docApi.delDoc(id);
					}
				} catch (Exception e) {
					e.printStackTrace();
					ex = e;
				}
			}
		};		
		concurrentTest(r, count);
		if(ex != null)
			throw ex;
	}

	Exception ex2;
	@Test
	public void getDocMulti() throws Exception{
		Runnable r = new Runnable() {
			public void run() {
				try {
					String id = docApi.addDoc("fd663723-3824-4a6f-bac9-5fc9f39f2057","test","D:/Using a Namespace.pdf","");
					docApi.getDoc(id,"D:/test/",id);
					docApi.delDoc(id);
				} catch (Exception e) {
					e.printStackTrace();
					ex2 = e;
				}
			}
		};

			concurrentTest(r, count);

		if(ex2 != null)
			throw ex2;
	}

	Exception ex3;
	@Test
	public void delDocMulti() throws Exception{
		Runnable r = new Runnable() {
			public void run() {
				try {
					String id = docApi.addDoc("8a110690-2605-43da-8c10-6109aa8b98cc","test","D:/MediaID.bin","");
					docApi.delDoc(id);
				} catch (Exception e) {
					e.printStackTrace();
					ex3 = e;
				}
			}
		};
		concurrentTest(r, count);
		if(ex3 != null)
			throw ex3;
	}
	
	@Test
	public void addDocToFolder() throws Exception{
		String addFolder = folderApi.addFolder("Æ®·Ñ·¯", "", "", "");
		String addDoc = docApi.addDoc("8a110690-2605-43da-8c10-6109aa8b98cc","test","D:/MediaID.bin","");
		docApi.addDocToFolder(addDoc, addFolder);
	}
	
	@Test
	public void updDoc() throws Exception{
		docApi.updDoc("8a110690-2605-43da-8c10-6109aa8b98cc", "test", "D:/MediaID.bin","");
	}
}
