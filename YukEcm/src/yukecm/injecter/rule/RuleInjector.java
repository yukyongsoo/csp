package yukecm.injecter.rule;

import java.sql.SQLException;
import java.util.List;

import yukcommon.dic.type.OnErrorType;
import yukcommon.model.Rule;
import yukecm.cache.Cache;
import yukecm.cache.YLC;
import yukecm.config.BaseProperty;
import yukecm.db.DbConnFac;
import yukecm.db.RuleDbAction;
import yukecm.injecter.InjecterUtil;

public class RuleInjector {
	private static RuleInjector injecter;

	public static RuleInjector getInstance() throws InterruptedException, SQLException{
		if(injecter == null)
			injecter = new RuleInjector();
		return injecter;
	}

	private Cache<String, Rule> ruleMap;

	private RuleInjector() throws InterruptedException, SQLException{
		if(BaseProperty.getInstance().inMem)
			ruleMap = YLC.makeCache("RULE", new RuleJson());
		initRule();
	}

	public void initRule() throws InterruptedException, SQLException {
		ruleMap.clear();
		List<Rule> list = getRuleList(OnErrorType.NONE);
		for (Rule rule : list) {
			if(BaseProperty.getInstance().inMem)
				ruleMap.put(rule.getId(), rule);
		}
	}

	public List<Rule> getRuleList(OnErrorType type) throws SQLException {
		//Always check db in this
		RuleDbAction action = null;
		List<Rule> list;
		try {
			action = DbConnFac.getInstance().getRuleDbAction();
			list = action.getRuleList();
			InjecterUtil.onErrorListException(list, type);
			return list;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public Rule getRuleByName(String ruleName,OnErrorType type) throws SQLException {
		RuleDbAction action = null;
		Rule nRule = null;
		try{
			if (BaseProperty.getInstance().inMem) {
				for(Rule rule : ruleMap.values()){
					if(rule.getName().equals(ruleName)) {
						nRule = rule;
					}
					
				}
				InjecterUtil.onErrorException(nRule, type);
				return nRule;
			}
			action = DbConnFac.getInstance().getRuleDbAction();
			nRule = action.getRuleByName(ruleName); 
			InjecterUtil.onErrorException(nRule, type);
			return nRule;
		}
		finally{
			DbConnFac.staticClose(action);
		}
	}

	public Rule getRule(String ruleId,OnErrorType type) throws SQLException {
		RuleDbAction action = null;
		Rule rule = null;
		try{
			if (BaseProperty.getInstance().inMem) {
				rule = ruleMap.get(ruleId);
				InjecterUtil.onErrorException(rule, type);
				return rule;
			}
			action = DbConnFac.getInstance().getRuleDbAction();
			rule = action.getRule(ruleId);
			InjecterUtil.onErrorException(rule, type);
			return rule;
		}
		finally{
			DbConnFac.staticClose(action);
		}
	}

	public void addRule(Rule rule, String subJson) throws SQLException, InterruptedException {
		RuleDbAction action = null;
		try {
			if (BaseProperty.getInstance().inMem)
				ruleMap.put(rule.getId(), rule);
			action = DbConnFac.getInstance().getRuleDbAction();
			action.addRule(rule,subJson);
			action.commits();
		} catch (SQLException e) {
			if (BaseProperty.getInstance().inMem)
				ruleMap.remove(rule.getId());
			DbConnFac.staticRollBack(action);
			throw e;
		}
		finally{
			DbConnFac.staticClose(action);
		}
	}

	public void updRule(Rule rule, String subJson) throws InterruptedException,  SQLException {
		RuleDbAction action = null;
		Rule remove = null;
		try {
			if (BaseProperty.getInstance().inMem){
				remove = ruleMap.remove(rule.getId());
				ruleMap.put(rule.getId(), rule);
			}
			action = DbConnFac.getInstance().getRuleDbAction();
			action.updRule(rule,subJson);
			action.commits();
		} catch (SQLException e) {
			if (BaseProperty.getInstance().inMem)
				ruleMap.put(rule.getId(), remove);
			DbConnFac.staticRollBack(action);
			throw e;
		}
		finally{
			DbConnFac.staticClose(action);
		}
	}

	public void delRule(Rule rule) throws SQLException, InterruptedException {
		RuleDbAction action = null;
		Rule remove = null;
		try {
			if (BaseProperty.getInstance().inMem)
				remove = ruleMap.remove(rule.getId());
			action = DbConnFac.getInstance().getRuleDbAction();
			action.delRule(rule);
			action.commits();
		} catch (SQLException e) {
			if (BaseProperty.getInstance().inMem)
				ruleMap.put(rule.getId(), remove);
			DbConnFac.staticRollBack(action);
			throw e;
		}
		finally{
			DbConnFac.staticClose(action);
		}
	}
}
