import java.util.EnumMap;
import java.util.Map;

import org.junit.Test;

import yukcommon.dic.type.PermissionType;
import yukcommon.dic.type.UserManageType;

public class AclAceTest extends AbsTest{

	

	public AclAceTest() throws Exception {
		super();
	}

	@Test
	public void addAcl() throws Exception{
		try {
			aclApi.addAcl("testAcl");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void delAcl() throws Exception{
		try {
			String id = aclApi.addAcl("testAcl");
			aclApi.delAcl(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void updAcl() throws Exception{
		try {
			aclApi.updAcl("0d7e748b-b6e3-4383-b807-9fdfdd7194b0","renamed");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void addAce() throws Exception{
		try {
			String id = aclApi.addAcl("testAcl");
			userGroupApi.addUser("TTTE", "TT", "asdfdsf", UserManageType.PUBLIC);
			Map<PermissionType, Boolean> map = new EnumMap<PermissionType, Boolean>(PermissionType.class);
			map.put(PermissionType.ADDFILE, true);
			aclApi.addAce(id, "TTTE",map);			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void getAce() throws Exception{
		try {
			String id = aclApi.addAcl("testAcl2");
			userGroupApi.addUser("TTTE2", "TT", "asdfdsf", UserManageType.PUBLIC);
			Map<PermissionType, Boolean> map = new EnumMap<PermissionType, Boolean>(PermissionType.class);
			map.put(PermissionType.ADDFILE, true);
			aclApi.addAce(id, "TTTE2",map);
			aclApi.getAce(id);	
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void delAce() throws Exception{
		try {
			String id = aclApi.addAcl("testAcl3");
			userGroupApi.addUser("TTTE3", "TT", "asdfdsf", UserManageType.PUBLIC);
			Map<PermissionType, Boolean> map = new EnumMap<PermissionType, Boolean>(PermissionType.class);
			map.put(PermissionType.ADDFILE, true);
			aclApi.addAce(id, "TTTE3",map);
			aclApi.delAce(id, "TTTE3");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void updAce() throws Exception{
		try {
			String id = aclApi.addAcl("testAcl3");
			userGroupApi.addUser("TTTE3", "TT", "asdfdsf", UserManageType.PUBLIC);
			Map<PermissionType, Boolean> map = new EnumMap<PermissionType, Boolean>(PermissionType.class);
			aclApi.addAce(id, "TTTE3",map);
			map.put(PermissionType.ADDFILE, true);
			map.put(PermissionType.DELFILE, true);
			aclApi.updAce(id, "TTTE3", map);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
