import org.junit.Test;

import yukcommon.dic.type.RuleType;


public class WorkingTest extends AbsTest{

	public WorkingTest() throws Exception {
		super();
	}

	@Test
	public void addWork() throws Exception{
		String a =workingApi.addWork("test2", false);
		System.out.println(a);
	}

	@Test
	public void delWork() throws Exception{
		String addWork = workingApi.addWork("test", false);
		workingApi.delWork(addWork);
	}

	@Test
	public void updWork() throws Exception{
		String addWork = workingApi.addWork("tttt3", false);
		workingApi.updWork(addWork,"tt2tt",false);
	}

	@Test
	public void addWorkRule() throws Exception{
		//String addWork = workingApi.addWork("tttt4", false, false);
		workingApi.addWorkRule("fd663723-3824-4a6f-bac9-5fc9f39f2057",
				"5f8c0398-d756-4c52-b2a1-46e6e2f18565",RuleType.DESRULE);
	}

	@Test
	public void delWorkRule() throws Exception{
		String addWork = workingApi.addWork("tttt4", false);
		workingApi.addWorkRule(addWork,"5946ed9a-4a06-4f23-b81a-091a7d77726d",RuleType.INITRULE);
		workingApi.delWorkRule(addWork,"5946ed9a-4a06-4f23-b81a-091a7d77726d",RuleType.INITRULE);
	}
}
