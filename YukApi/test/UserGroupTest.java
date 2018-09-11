import org.junit.Test;

import yukcommon.dic.type.UserManageType;

public class UserGroupTest extends AbsTest{

	public UserGroupTest() throws Exception {
		super();
	}

	@Test
	public void addUser() throws Exception{
		try {
			userGroupApi.addUser("test","estName", "test", UserManageType.PUBLIC);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void delUser() throws Exception{
		try {
			userGroupApi.addUser("test2","estName", "test", UserManageType.ADMIN);
			
			userGroupApi.delUser("test2");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void updUser() throws Exception{
		try {
			userGroupApi.addUser("test3","estName", "test", UserManageType.ADMIN);
			userGroupApi.updUser("test3", "estName", "test2", UserManageType.PUBLIC);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void getUserList() throws Exception{
		try {
			userGroupApi.getUserList();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void addGroup() throws Exception{
		try {
			String id = userGroupApi.addGroup("TTTT", "");
			userGroupApi.addGroup("TTTT2", id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void getGroup() throws Exception{
		try {
			String id = userGroupApi.addGroup("TTTT3", "");
			userGroupApi.getGroup(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void delGroup() throws Exception{
		try {
			String id = userGroupApi.addGroup("TTTT4", "");
			userGroupApi.delGroup(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void updGroup() throws Exception{
		try {
			String p = userGroupApi.addGroup("TTTT6", "");
			String id = userGroupApi.addGroup("TTTT5", "");
			userGroupApi.updGroup(id, "TESTUPD", p);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void getGroupList() throws Exception{
		try {
			userGroupApi.getGroupList();			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void getGroupChildList() throws Exception{
		try {
			String id = userGroupApi.addGroup("TTTT", "");
			userGroupApi.addGroup("TTTT", id);
			userGroupApi.addGroup("TTTT", id);
			userGroupApi.addGroup("TTTT", id);
			userGroupApi.addGroup("TTTT", id);
			userGroupApi.addGroup("TTTT", id);
			userGroupApi.getGroupChildList(id);		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
}
