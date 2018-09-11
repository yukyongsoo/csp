package yukecm.injecter.lifecycle;

import java.sql.SQLException;

import yukcommon.dic.type.OnErrorType;
import yukcommon.model.lifecycle.LifeCycleInfo;
import yukecm.db.DbConnFac;
import yukecm.db.LCInfoDbAction;
import yukecm.injecter.InjecterUtil;

public class LCInfoInjector {
	private static LCInfoInjector injecter;

	public static LCInfoInjector getInstance()  {
		if(injecter == null)
			injecter = new LCInfoInjector();
		return injecter;
	}

	private LCInfoInjector(){

	}

	public void addLifeCycleInfo(LifeCycleInfo info) throws SQLException{
		LCInfoDbAction action = null;
		try{
			action = DbConnFac.getInstance().getLCInfoDbAction();
			action.addLifeCycleInfo(info);
			action.commits();
		}
		catch(SQLException e){
			DbConnFac.staticRollBack(action);
			throw e;
		}
		finally{
			DbConnFac.staticClose(action);
		}
	}

	public void delLifeCycleInfo(String id) throws SQLException{
		LCInfoDbAction action = null;
		try{
			action = DbConnFac.getInstance().getLCInfoDbAction();
			action.delLifeCycleInfo(id);
			action.commits();
		}
		catch(SQLException e){
			DbConnFac.staticRollBack(action);
			throw e;
		}
		finally{
			DbConnFac.staticClose(action);
		}
	}

	public LifeCycleInfo getLifeCycleInfo(String id,OnErrorType type) throws SQLException{
		LCInfoDbAction action = null;
		try{
			action = DbConnFac.getInstance().getLCInfoDbAction();
			LifeCycleInfo lifeCycleInfo = action.getLifeCycleInfo(id);
			InjecterUtil.onErrorException(lifeCycleInfo, type);
			return lifeCycleInfo;
		}
		finally{
			DbConnFac.staticClose(action);
		}
	}

	public void updLifeCyclenfo(LifeCycleInfo info) throws SQLException{
		LCInfoDbAction action = null;
		try{
			action = DbConnFac.getInstance().getLCInfoDbAction();
			action.updLifeCycleInfo(info);
			action.commits();
		}
		catch(SQLException e){
			DbConnFac.staticRollBack(action);
			throw e;
		}
		finally{
			DbConnFac.staticClose(action);
		}
	}

	public void updCycleInfoAddData(LifeCycleInfo info) throws SQLException{
		LCInfoDbAction action = null;
		try{
			action = DbConnFac.getInstance().getLCInfoDbAction();
			action.updLifeCycleInfoAddData(info);
			action.commits();
		}
		catch(SQLException e){
			DbConnFac.staticRollBack(action);
			throw e;
		}
		finally{
			DbConnFac.staticClose(action);
		}
	}
	
	public long updLifeCycleInfoRowNum(LifeCycleInfo info) throws SQLException{
		LCInfoDbAction action = null;
		try{
			action = DbConnFac.getInstance().getLCInfoDbAction();
			long count = action.updLifeCycleInfoRowNum(info);
			action.commits();
			return count;
		}
		catch(SQLException e){
			DbConnFac.staticRollBack(action);
			throw e;
		}
		finally{
			DbConnFac.staticClose(action);
		}
	}
}
