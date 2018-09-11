package yukecm.injecter;

import java.sql.SQLException;
import java.util.Collection;

import org.quartz.SchedulerException;

import yukcommon.dic.type.OnErrorType;
import yukcommon.exception.DuplicatedException;
import yukcommon.exception.NotExistException;
import yukcommon.exception.WrongOperationException;
import yukecm.injecter.lifecycle.LCInfoInjector;
import yukecm.injecter.lifecycle.LCSettingInjector;
import yukecm.injecter.meta.MetaSettingInjecter;
import yukecm.injecter.pipe.PipeInjector;
import yukecm.injecter.repository.RepositoryInjector;
import yukecm.injecter.rule.RuleInjector;
import yukecm.injecter.storage.StorageInjector;
import yukecm.injecter.workgroup.WorkInjector;

public abstract class InjecterUtil {
	private InjecterUtil() {}

	public static void initAll() throws InterruptedException, SQLException, SchedulerException {
		PipeInjector.getInstance();
		RepositoryInjector.getInstance();
		RuleInjector.getInstance();
		StorageInjector.getInstance();
		WorkInjector.getInstance();
		LCSettingInjector.getInstance();
		LCInfoInjector.getInstance();
		MetaSettingInjecter.getInstance();
	}

	public static void reinitAll() throws InterruptedException, SQLException {
		PipeInjector.getInstance().initPipe();
		RepositoryInjector.getInstance().initRepository();
		RuleInjector.getInstance().initRule();
		StorageInjector.getInstance().initStorage();
		WorkInjector.getInstance().initWork();
		MetaSettingInjecter.getInstance().initMeta();	
	}
	
	public static void onErrorException(Object obj,OnErrorType type) {
		if(type == null)
			throw new WrongOperationException("injecter error type is null");
		if(OnErrorType.EXIST == type && obj != null)
			throw new DuplicatedException("current object duplicated." + obj.toString());
		else if(OnErrorType.NOTEXIST == type && obj == null)
			throw new NotExistException("current object is not existed");
	}
	
	public static void onErrorListException(Collection<?> obj,OnErrorType type) {
		if(type == null || obj == null)
			throw new WrongOperationException("injecter error type or Target List are null");
		else if(OnErrorType.EXIST == type && !obj.isEmpty())
			throw new DuplicatedException("current object duplicated" + obj.toString());
		else if(OnErrorType.NOTEXIST == type && obj.isEmpty())
			throw new NotExistException("current object is not existed." + obj.toString());
	}
}
