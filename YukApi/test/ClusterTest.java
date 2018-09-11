import org.junit.Test;

public class ClusterTest extends AbsTest{

	public ClusterTest() throws Exception {
		super();
	}

	@Test
	public void addCluster() throws Exception{
		clusterApi.addCluster(0, "localhost:4400", true);
	}
	
	@Test
	public void removeCluster() throws Exception{
		clusterApi.removeCluster(0);
	}
}
