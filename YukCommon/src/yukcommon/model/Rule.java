package yukcommon.model;

import com.google.gson.annotations.SerializedName;

import yukcommon.dic.type.RuleType;
import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;
import yukcommon.model.subrule.AbsSubRule;
import yukcommon.model.subrule.DesRule;
import yukcommon.model.subrule.InitRule;
import yukcommon.model.subrule.MigRule;

public class Rule extends AbsModel{
	private String id = "";
	private String name = "";
	@SerializedName("ruleType")
	private RuleType type;
	private transient AbsSubRule subRule;
	private InitRule initRule;
	private MigRule	migRule;
	private DesRule desRule; 
	
	public String getId() {
		if(id.isEmpty())
			throw new WrongOperationException("rule id is empty");
		return id;
	}
	public void setId(String id) {
		if(id == null || id.isEmpty())
			throw new NotNullAllowedException("rule id can't be null");
		this.id = id;
	}
	public String getName() {
		if(name.isEmpty())
			throw new WrongOperationException("rule name is empty");
		return name;
	}
	public void setName(String name) {
		if(name == null || name.isEmpty())
			throw new NotNullAllowedException("rule name can't be null");
		this.name = name;
	}
	public RuleType getType() {
		if(type == null)
			throw new WrongOperationException("rule type is null");
		return type;
	}
	public void setType(RuleType type) {
		if(type == null)
			throw new NotNullAllowedException("rule type can't be null");
		this.type = type;
	}
	public AbsSubRule getSubRule() {
		if(subRule == null) {
			if(RuleType.INITRULE == getType())
				subRule = initRule;
			else if(RuleType.MIGRULE == getType())
				subRule = migRule;
			else if(RuleType.DESRULE == getType())
				subRule = desRule;
			else
				throw new WrongOperationException("sub Rule is null");
		}
		return subRule;
	}
	
	public void setSubRule(AbsSubRule subRule) {
		if(subRule == null)
			throw new NotNullAllowedException("sub Rule can't be null");
		if(RuleType.INITRULE == getType())
			initRule= (InitRule) subRule;
		else if(RuleType.MIGRULE == getType())
			migRule = (MigRule) subRule;
		else if(RuleType.DESRULE == getType())
			desRule  = (DesRule) subRule;
		this.subRule = subRule;
	}
	
	@Override
	public String toString() {		
		return "Current Rule Data: id = " + id + ".name=" + name + ".type=" + type.toString();
	}
}
