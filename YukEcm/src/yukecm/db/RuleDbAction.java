package yukecm.db;

import java.sql.SQLException;
import java.util.List;

import yukcommon.model.Rule;

public interface RuleDbAction extends AbsDbAction{

	void addRule(Rule rule, String subJson) throws SQLException;

	void updRule(Rule rule, String subJson) throws SQLException;

	void delRule(Rule rule) throws SQLException;

	Rule getRuleByName(String ruleName) throws SQLException;

	Rule getRule(String ruleId) throws SQLException;

	List<Rule> getRuleList() throws SQLException;

}