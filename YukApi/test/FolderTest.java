import org.junit.Test;


public class FolderTest extends AbsTest{
	public FolderTest() throws Exception {
		super();
	}

	@Test
	public void addFolder() throws Exception{		
		folderApi.addFolder("TESTROOT", "", "", "");
	}

	
	@Test
	public void delFolder() throws Exception{
		String addFolder = folderApi.addFolder("TESTROOT", "", "", "");		
		folderApi.delFolder(addFolder);
	}

	@Test
	public void updFolder() throws Exception{
		String id = folderApi.addFolder("TESTROOT2", "", "", "");		
		folderApi.updFolder(id, "NEWNAME2", "", "NEWDESCR");
	}
	
	@Test
	public void getFolderList() throws Exception{
		folderApi.getFolderList();
	}
	
	@Test
	public void getFolderChildList() throws Exception{
		String addFolder = folderApi.addFolder("TESTROOT", "", "", "");		
		folderApi.addFolder("TESTROOT", addFolder, "", "");		
		folderApi.addFolder("TESTROOT2", addFolder, "", "");	
		folderApi.addFolder("TESTROOT3", addFolder, "", "");	
		folderApi.addFolder("TESTROOT2", addFolder, "", "");	
		folderApi.addFolder("TESTROOT1", addFolder, "", "");	
		folderApi.getFolderChildList(addFolder);
	}

}
