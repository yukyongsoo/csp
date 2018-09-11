package yukecm.injecter.pipe;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import yukcommon.adaptor.PipeAdaptor;
import yukcommon.dic.type.OnErrorType;
import yukcommon.model.Pipe;
import yukecm.cache.Cache;
import yukecm.cache.YLC;
import yukecm.config.BaseProperty;
import yukecm.db.DbConnFac;
import yukecm.db.PipeDbAction;
import yukecm.etc.EcmUtil;
import yukecm.injecter.InjecterUtil;

public class PipeInjector {
	private static PipeInjector injecter;

	public static PipeInjector getInstance() throws InterruptedException, SQLException {
		if(injecter == null)
			injecter = new PipeInjector();
		return injecter;
	}

	private Cache<String, Pipe> pipeMap;
	//while use not use memory cache
	private ConcurrentHashMap<String, PipeAdaptor> adaptorMap = new ConcurrentHashMap<String, PipeAdaptor>();

	private PipeInjector() throws InterruptedException, SQLException {
		if(BaseProperty.getInstance().inMem)
			pipeMap = YLC.makeCache("PIPE", new PipeJson(), new PipeInjectorHandler());
		initPipe();
	}

	public void initPipe() throws InterruptedException,  SQLException {
		pipeMap.clear();
		List<Pipe> list = getPipeList();
		for (Pipe pipe : list) {
			EcmUtil.setPipeAdaptor(pipe);
			if(BaseProperty.getInstance().inMem)
				pipeMap.put(pipe.getId(), pipe);
			else
				adaptorMap.put(pipe.getId(), pipe.getAdt());
		}
	}

	public Pipe getPipe(String pipeId,OnErrorType type) throws SQLException {
		PipeDbAction action = null;
		Pipe pipe = null;
		try {
			if (BaseProperty.getInstance().inMem) {
				pipe = pipeMap.get(pipeId);
				InjecterUtil.onErrorException(pipe, type);
				return pipe;
			}
			action = DbConnFac.getInstance().getPipeDbAction();
			pipe = action.getPipe(pipeId);
			InjecterUtil.onErrorException(pipe, type);
			pipe.setAdt(adaptorMap.get(pipeId));
			return pipe;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public List<Pipe> getPipeList() throws SQLException {
		//Always check db in this method
		PipeDbAction action = null;
		try {
			action =  DbConnFac.getInstance().getPipeDbAction();
			return action.getPipeList();
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void addPipe(Pipe pipe) throws SQLException, InterruptedException {
		PipeDbAction action = null;
		try {
			if (BaseProperty.getInstance().inMem)
				pipeMap.put(pipe.getId(), pipe);
			else
				adaptorMap.put(pipe.getId(), pipe.getAdt());
			action =  DbConnFac.getInstance().getPipeDbAction();
			action.addPipe(pipe);
			action.commits();
		} catch (SQLException e) {
			if (BaseProperty.getInstance().inMem)
				pipeMap.remove(pipe.getId());
			else
				adaptorMap.remove(pipe.getId());
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void delPipe(Pipe pipe) throws InterruptedException, SQLException {
		PipeDbAction action = null;
		Pipe rollback = null;
		PipeAdaptor remove = null;
		try {
			if (BaseProperty.getInstance().inMem)
				rollback = pipeMap.remove(pipe.getId());
			else
				remove = adaptorMap.remove(pipe.getId());
			action =  DbConnFac.getInstance().getPipeDbAction();
			action.delPipe(pipe);
			action.commits();
		} catch (SQLException e) {
			if (BaseProperty.getInstance().inMem)
				pipeMap.put(pipe.getId(), rollback);
			else
				adaptorMap.put(pipe.getId(), remove);
			DbConnFac.staticRollBack(action);
			throw e;
		}
		finally {
			DbConnFac.staticClose(action);
		}
	}

	public void updPipe(Pipe pipe) throws SQLException, InterruptedException {
		PipeDbAction action = null;
		Pipe rollback = null;
		PipeAdaptor remove = null;
		try {
			if (BaseProperty.getInstance().inMem){
				rollback = pipeMap.remove(pipe.getId());
				pipeMap.put(pipe.getId(), pipe);
			}
			else{
				remove = adaptorMap.remove(pipe.getId());
				adaptorMap.put(pipe.getId(), pipe.getAdt());
			}
			action =  DbConnFac.getInstance().getPipeDbAction();
			action.updPipe(pipe);
			action.commits();
		}
		catch (SQLException e) {
			if (BaseProperty.getInstance().inMem)
				pipeMap.put(pipe.getId(), rollback);
			else
				adaptorMap.put(pipe.getId(), remove);
			DbConnFac.staticRollBack(action);
			throw e;
		}
		finally {
			DbConnFac.staticClose(action);
		}
	}


























}
