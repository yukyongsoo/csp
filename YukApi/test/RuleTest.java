import org.junit.Test;

import yukcommon.dic.type.RuleType;
import yukcommon.model.Rule;
import yukcommon.model.subrule.DesRule;
import yukcommon.model.subrule.InitRule;
import yukcommon.model.subrule.MigRule;
import yukcommon.model.subrule.MigSubRule;


public class RuleTest extends AbsTest{
	public RuleTest() throws Exception {
		super();
	}

	@Test
	public void addRule() throws Exception{
		Rule rule = new Rule();
		rule.setName("initRule");
		rule.setType(RuleType.INITRULE);
		InitRule init = new InitRule();
		init.setRepoId("36a10c15-f808-42d6-be34-369d8ba46a68");
		rule.setSubRule(init);
		String a = ruleApi.addRule(rule);
		System.out.println(a);
	}

	@Test
	public void addMigRule() throws Exception{
		Rule rule = new Rule();
		rule.setName("migRule");
		rule.setType(RuleType.MIGRULE);
		MigRule mig = new MigRule();
		mig.setLimitTime(0);
		mig.setCopyed(true);
		MigSubRule subRule = new MigSubRule();
		subRule.setServerAddress("127.0.0.1:4400");
		subRule.setTargetRepoId("bf41c379-fe54-4a57-a7c3-38517722e600");
		subRule.setTargetWorkId("0656ee66-81b7-4cc8-86c6-765433a789c5"); 
		mig.getTargetList().add(subRule);
		rule.setSubRule(mig);
		String a = ruleApi.addRule(rule);
		System.out.println(a);
	}

	@Test
	public void addDesRule() throws Exception{
		Rule rule = new Rule();
		rule.setName("desRule");
		rule.setType(RuleType.DESRULE);
		DesRule des = new DesRule();
		des.setLimitTime(0);
		rule.setSubRule(des);
		String a = ruleApi.addRule(rule);
		System.out.println(a);
	}


	@Test
	public void delRule() throws Exception{
		try {
			ruleApi.delRule("cbdd7612-9b0c-4af3-b250-dadd369201dd");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void updRule() throws Exception{
		try {
			Rule rule = new Rule();
			rule.setId("e6aa8d91-31a5-4d33-be50-2bd5388c0f10");
			rule.setName("initRule");
			rule.setType(RuleType.INITRULE);
			InitRule init = new InitRule();
			init.setRepoId("8bce2900-4258-429f-ada0-fd2143f83010");
			init.setRequired(true);
			rule.setSubRule(init);
			ruleApi.updRule(rule);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
