package yukecm.controller;

import java.sql.SQLException;
import java.util.List;

import yukcommon.dic.type.OnErrorType;
import yukcommon.dic.type.RuleType;
import yukcommon.exception.NotSupportException;
import yukcommon.model.Rule;
import yukcommon.model.subrule.DesRule;
import yukcommon.model.subrule.InitRule;
import yukcommon.model.subrule.MigRule;
import yukcommon.util.JsonUtil;
import yukecm.etc.EcmUtil;
import yukecm.injecter.repository.RepositoryInjector;
import yukecm.injecter.rule.RuleInjector;

public class RuleController {
	
	private static class LazyHolder {
	    private static final RuleController rule = new RuleController();
	}
	
	public static RuleController getInstance(){
		return LazyHolder.rule;
	}

	private RuleController(){

	}

	public Rule getRuleByName(String ruleName) throws SQLException, InterruptedException {
		return RuleInjector.getInstance().getRuleByName(ruleName,OnErrorType.NONE);
	}

	public Rule getRule(String ruleId) throws SQLException, InterruptedException {
		return RuleInjector.getInstance().getRule(ruleId,OnErrorType.NONE);
	}

	public String addRule(Rule rule) throws SQLException,InterruptedException   {
		RuleInjector.getInstance().getRuleByName(rule.getName(),OnErrorType.EXIST);
		rule.setId(EcmUtil.getId()); 
		String subJson = validateSubRule(rule);
		RuleInjector.getInstance().addRule(rule,subJson);
		return rule.getId();
	}

	public void updRule(Rule rule) throws  SQLException, InterruptedException   {
		RuleInjector.getInstance().getRule(rule.getId(),OnErrorType.NOTEXIST);
		String subJson = validateSubRule(rule);
		RuleInjector.getInstance().updRule(rule,subJson);
	}

	public void delRule(Rule rule) throws SQLException, InterruptedException   {
		RuleInjector.getInstance().getRule(rule.getId(),OnErrorType.NOTEXIST);
		RuleInjector.getInstance().delRule(rule);
	}

	public List<Rule> getRuleList() throws  SQLException, InterruptedException {
		return RuleInjector.getInstance().getRuleList(OnErrorType.NONE);
	}

	private String validateSubRule(Rule rule) throws SQLException, InterruptedException  {
		if(RuleType.INITRULE == rule.getType()){
			InitRule temp =  (InitRule) rule.getSubRule();
			temp.setId(rule.getId());
			RepositoryInjector.getInstance().getRepo(temp.getRepoId(), OnErrorType.NOTEXIST);
			return JsonUtil.toJson(temp);
		}
		else if(RuleType.MIGRULE == rule.getType()){
			MigRule temp =  (MigRule) rule.getSubRule();
			temp.setId(rule.getId()); 
			return JsonUtil.toJson(temp);
		}
		else if(RuleType.DESRULE == rule.getType()){
			DesRule temp =  (DesRule) rule.getSubRule();
			temp.setId(rule.getId()); 
			return JsonUtil.toJson(temp);
		}
		else
			throw new NotSupportException("this type rule is not supported");
	}



}
