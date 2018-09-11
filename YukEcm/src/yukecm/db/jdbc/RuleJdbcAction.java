package yukecm.db.jdbc;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yukcommon.dic.type.RuleType;
import yukcommon.model.Rule;
import yukcommon.model.subrule.DesRule;
import yukcommon.model.subrule.InitRule;
import yukcommon.model.subrule.MigRule;
import yukcommon.util.JsonUtil;
import yukecm.config.BaseProperty;
import yukecm.db.RuleDbAction;

public class RuleJdbcAction extends AbsJdbcAction implements RuleDbAction{

	protected RuleJdbcAction() throws SQLException   {
		super();
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.RuleDbAction#addRule(yukcommon.model.Rule, java.lang.String)
	 */
	@Override
	public void addRule(Rule rule, String subJson) throws SQLException   {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukAddRule(?,?,?,?)}");
			statement.setString(1, rule.getId());
			statement.setString(2, rule.getName());
			statement.setInt(3, rule.getType().ordinal());
			statement.setString(4, subJson);
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.RuleDbAction#updRule(yukcommon.model.Rule, java.lang.String)
	 */
	@Override
	public void updRule(Rule rule, String subJson) throws SQLException   {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukUpdRule(?,?,?,?)}");
			statement.setString(1, rule.getId());
			statement.setString(2, rule.getName());
			statement.setInt(3, rule.getType().ordinal());
			statement.setString(4, subJson);
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.RuleDbAction#delRule(yukcommon.model.Rule)
	 */
	@Override
	public void delRule(Rule rule) throws SQLException  {
		CallableStatement statement = null;
		try {
			statement = connection.prepareCall("{call YukDelRule(?)}");
			statement.setString(1, rule.getId());
			statement.executeUpdate();
		}
		finally {
			closeResouce(null, statement);
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.RuleDbAction#getRuleByName(java.lang.String)
	 */
	@Override
	public Rule getRuleByName(String ruleName) throws SQLException   {
		Rule rule = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetRuleByName(?,?)}");
			statement.setString(1, ruleName);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				rule = new Rule();
				rule.setId(resultSet.getString(1)); 
				rule.setName(resultSet.getString(2)); 
				rule.setType( RuleType.values()[resultSet.getInt(3)]);
				String temp = resultSet.getString(4);
				if(RuleType.INITRULE == rule.getType())
					rule.setSubRule(JsonUtil.fromJson(temp, InitRule.class));
				else if(RuleType.MIGRULE == rule.getType())
					rule.setSubRule(JsonUtil.fromJson(temp, MigRule.class));
				else if(RuleType.DESRULE == rule.getType())
					rule.setSubRule(JsonUtil.fromJson(temp, DesRule.class)); 
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return rule;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.RuleDbAction#getRule(java.lang.String)
	 */
	@Override
	public Rule getRule(String ruleId) throws SQLException   {
		Rule rule = null;
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetRule(?,?)}");
			statement.setString(1, ruleId);
			statement.registerOutParameter(2,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(2);
			while (resultSet.next()){
				rule = new Rule();
				rule.setId(resultSet.getString(1)); 
				rule.setName(resultSet.getString(2)); 
				rule.setType( RuleType.values()[resultSet.getInt(3)]);
				String temp = resultSet.getString(4);
				if(RuleType.INITRULE == rule.getType())
					rule.setSubRule(JsonUtil.fromJson(temp, InitRule.class));
				else if(RuleType.MIGRULE == rule.getType())
					rule.setSubRule(JsonUtil.fromJson(temp, MigRule.class));
				else if(RuleType.DESRULE == rule.getType())
					rule.setSubRule(JsonUtil.fromJson(temp, DesRule.class)); 
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return rule;
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.RuleDbAction#getRuleList()
	 */
	@Override
	public List<Rule> getRuleList() throws SQLException {
		List<Rule> list = new ArrayList<Rule>();
		CallableStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareCall("{call YukGetRuleList(?)}");
			statement.registerOutParameter(1,BaseProperty.getInstance().cursor);
			statement.execute();
			resultSet = (ResultSet) statement.getObject(1);
			while (resultSet.next()){
				Rule rule = new Rule();
				rule.setId(resultSet.getString(1)); 
				rule.setName(resultSet.getString(2)); 
				rule.setType( RuleType.values()[resultSet.getInt(3)]);
				String temp = resultSet.getString(4);
				if(RuleType.INITRULE == rule.getType())
					rule.setSubRule(JsonUtil.fromJson(temp, InitRule.class));
				else if(RuleType.MIGRULE == rule.getType())
					rule.setSubRule(JsonUtil.fromJson(temp, MigRule.class));
				else if(RuleType.DESRULE == rule.getType())
					rule.setSubRule(JsonUtil.fromJson(temp, DesRule.class)); 
				list.add(rule);
			}
		}
		finally {
			closeResouce(resultSet, statement);
		}
		return list;
	}
}
