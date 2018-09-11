package yukcommon.model.subrule;

import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;
import yukcommon.model.AbsModel;

/**
 * 
 * @author admin
 * do not use .. please using subRule..
 */
public abstract class AbsSubRule extends AbsModel{
	protected String id = "";

	public String getId() {
		if(id.isEmpty())
			throw new WrongOperationException("sub rule id is empty");
		return id;
	}

	public void setId(String id) {
		if(id == null || id.isEmpty())
			throw new NotNullAllowedException("sub rule id can't be empty");
		this.id = id;
	}
}
