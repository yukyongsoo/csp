import org.junit.Test;


public class SecureTest extends AbsTest{


	public SecureTest() throws Exception {
		super();
	}

	@Test
	public void checkUser() throws Exception{
		try {
			userGroupApi.checkUser("admin","admin");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	
}
