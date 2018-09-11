import org.junit.Test;


public class PipeTest extends AbsTest{
	public PipeTest() throws Exception {
		super();
	}

	@Test
	public void addPipe() throws Exception{
		String a = pipeApi.addPipe("tttt", "ecmTest.TestPipe", false);
		System.out.println(a);
	}

	@Test
	public void delPipe() throws Exception{
		String a =pipeApi.addPipe("tttt2", "ecmTest.TestPipe", false);
		pipeApi.delPipe(a);
	}

	@Test
	public void updPipe() throws Exception{
		String a =pipeApi.addPipe("tttt3", "ecmTest.TestPipe", false);
		pipeApi.updPipe(a,"tt2tt3","ecmTest.TestPipe",true);
		
	}
}
