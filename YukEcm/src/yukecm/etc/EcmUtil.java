package yukecm.etc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.entity.InputStreamEntity;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import yukcommon.adaptor.PipeAdaptor;
import yukcommon.adaptor.StorageAdaptor;
import yukcommon.dic.type.OnErrorType;
import yukcommon.exception.AdpatorException;
import yukcommon.exception.MandatoryException;
import yukcommon.exception.NotSupportException;
import yukcommon.model.Pipe;
import yukcommon.model.Repository;
import yukcommon.model.Rule;
import yukcommon.model.Storage;
import yukcommon.model.WorkingGroup;
import yukcommon.model.subrepo.SubRepoPipe;
import yukcommon.model.subrule.InitRule;
import yukecm.controller.PipeController;
import yukecm.controller.RepositoryController;
import yukecm.db.jdbc.AbsJdbcAction;
import yukecm.db.jdbc.JdbcConnMngr;
import yukecm.injecter.InjecterUtil;
import yukecm.injecter.rule.RuleInjector;
import yukecm.injecter.workgroup.WorkInjector;

public abstract class EcmUtil {
	private EcmUtil(){}

	public static final DateTimeFormatter shortDate =  DateTimeFormat.forPattern("yyyyMMddHHmmss");
	public static final DateTimeFormatter shortDateReadable =  DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

	public static String makeDate(){
		return DateTime.now().toString(shortDate);
	}

	public static String makeDateReadable(){
		return DateTime.now().toString(shortDateReadable);
	}
	
	public static String makeDate(long time) {
		return new DateTime(time).toString(shortDate);
	}
	
	public static String getRunTime(String end,String start) {
		if(!(start != null && start.length() > 0))
			return "0 Second";
		DateTime startTime = DateTime.parse(start, shortDateReadable);
		DateTime endTime;
		if(end != null && end.length() > 0)
			endTime = DateTime.parse(end, shortDateReadable);
		else
			endTime = DateTime.now();
		Duration dur = new Duration(startTime, endTime);
		PeriodFormatter periodFormatter = new PeriodFormatterBuilder()
				    .appendDays()
				    .appendSuffix(" day", " days")
				    .appendSeparator(" ")
				    .appendHours()
				    .appendSuffix(" hours", " hours")
				    .appendSeparator(" ")
				    .appendMinutes()
				    .appendSuffix(" minute", " minutes")
				    .appendSeparator(" ")
				    .appendSeconds()
				    .appendSuffix(" second", " seconds")
				    .toFormatter();
		return dur.toPeriod().toString(periodFormatter);
	}

	public static void setItem(HttpResponse response, InputStream stream) throws IOException {
		InputStreamEntity body = new InputStreamEntity(stream, stream.available());
		body.setChunked(false);
		response.setEntity(body);
	}

	public static String getId(){
		return UUID.randomUUID().toString();
	}

	public static List<Pipe> getRepoPipe(String repoId) throws SQLException, InterruptedException {
		Repository repo = new Repository();
		repo.setId(repoId); 
		RepositoryController.getInstance().getRepoPipeLists(repo);
		List<Pipe> list = new ArrayList<Pipe>();
		for(SubRepoPipe subRepoPipe : repo.getPipeMap().values()){
			Pipe pipe = PipeController.getInstance().getPipe(subRepoPipe.getPipeId());
			list.add(pipe);
		}
		return list;
	}

	public static int getMax(Set<Integer> set, boolean plus){
		int max = 0;
		for(int i : set)
			max = Math.max(max, i);
		if(plus)
			max = max + 1;		
		return max;
	}

	public static Object makeClass(String className) {
		Object newInstance = null;
		try {
			Class<?> c = Class.forName(className);
			newInstance = c.newInstance();
		} catch (Exception e) {
			throw new AdpatorException("Make Adaptor Class Has Error. className is " + className , e);
		}
		return newInstance;
	}

	public static void setStorageAdaptor(Storage storage)  {
		Object newInstance = EcmUtil.makeClass(storage.getClassName());
		if(!(newInstance instanceof StorageAdaptor))
			throw new NotSupportException("This is Not Sub Class Of  yukCommon.adaptor.StorageAdaptor."  + storage.getClassName());
		((StorageAdaptor)newInstance).setInitOption(storage);
		storage.setAdt((StorageAdaptor) newInstance);
	}

	public static void setPipeAdaptor(Pipe pipe) {
		Object newInstance = EcmUtil.makeClass(pipe.getClassName());
		if(!(newInstance instanceof PipeAdaptor))
			throw new NotSupportException("This is Not Sub Class Of yukCommon.adaptor.PipeAdaptor." + pipe.getClassName());
		((PipeAdaptor) newInstance).setPassOnError(pipe.isPassOnError());
		pipe.setAdt((PipeAdaptor) newInstance);
	}
	
	public static void checkForbiddenWord(String src, String [] words) {
		String lower = src.toLowerCase();
		for(String forbidden : words) {
			if(lower.contains(forbidden)) {
				throw new NotSupportException("this query has ForbiddenWord.");
			}
		}
	}
	
	public static void checkContainWord(String src, String [] words) {
		String lower = src.toLowerCase();
		for(String contain : words) {
			if(!lower.contains(contain)) {
				throw new MandatoryException("this query has not Mandatory Word.");
			}
		}
	}
	
	public static Class<?> getCaller() throws ClassNotFoundException {
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		for (StackTraceElement ele : elements) {
			if (!ele.getClassName().equals(EcmUtil.class.getName()) && !ele.getClassName().equals(JdbcConnMngr.class.getName())
					&& ele.getClassName().indexOf("java.lang.Thread") != 0 && !ele.getClassName().equals(AbsJdbcAction.class.getName())) {
				return Class.forName(ele.getClassName());
			}
		}
		return null;
	}

	public static List<InitRule> getInitRule(String targetWorkId) throws SQLException, InterruptedException {
		WorkingGroup workingGroup = WorkInjector.getInstance().getWorkRuleList(targetWorkId, OnErrorType.NOTEXIST);
		List<InitRule> list = new ArrayList<InitRule>();
		for(String initId : workingGroup.getInitList()) {
			Rule rule = RuleInjector.getInstance().getRule(initId, OnErrorType.NOTEXIST);
			list.add((InitRule) rule.getSubRule());
		}
		InjecterUtil.onErrorListException(list, OnErrorType.NOTEXIST);
		return list;
	}
}
