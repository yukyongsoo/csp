import org.junit.FixMethodOrder;
import org.junit.Test;

import yukcommon.model.Repository;


@FixMethodOrder()
public class RepositoryTest extends AbsTest{
	public RepositoryTest() throws Exception {
		super();
	}

	@Test
	public void addRepository() throws Exception{
		String a = repoApi.addRepository("tttt2");
		System.out.println(a);
	}

	@Test
	public void delRepository() throws Exception{
		String a= repoApi.addRepository("tttt2");
		repoApi.delRepository(a);
	}

	@Test
	public void updRepository() throws Exception{
		String a= repoApi.addRepository("tttt3");
		repoApi.updRepository(a,"tt2tt");
	}

	@Test
	public void addRepoStr() throws Exception{
		String a= repoApi.addRepository("tttt4");
		repoApi.addRepoStr(a,"f2ffda6c-ce79-406e-832c-ed22a55a4224");
	}

	@Test
	public void delRepoStr() throws Exception{
		String a= repoApi.addRepository("tttt4");
		repoApi.addRepoStr(a,"a1038742-74f4-42db-b964-4dc06575f2d6");
		repoApi.delRepoStr(a,"a1038742-74f4-42db-b964-4dc06575f2d6");
	}

	@Test
	public void addRepoPipe() throws Exception{
		String a= repoApi.addRepository("tttt4");
		repoApi.addRepoPipe(a, "9256699b-e95d-43e7-a35f-fff476718618");
	}

	@Test
	public void delRepoPipe() throws Exception{
		String a= repoApi.addRepository("tttt4");
		Repository addRepoPipe = repoApi.addRepoPipe(a, "9256699b-e95d-43e7-a35f-fff476718618");
		repoApi.delRepoPipe(a, addRepoPipe.getPipeMap().get(0));
	}
}
