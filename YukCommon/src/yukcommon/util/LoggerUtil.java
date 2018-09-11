package yukcommon.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class LoggerUtil {
	private static Logger logger;
	private LoggerUtil(){}

	public static void trace(Class<?> t, String log, Exception e) {
		logger = LoggerFactory.getLogger(t);
		logger.trace(log ,e);	
	}
	
	public static void traceTime(Class<?> t, String log, long start) {
		logger = LoggerFactory.getLogger(t);
		if(logger.isTraceEnabled())
			logger.trace(log,System.currentTimeMillis()-start);
	}

	public static void debug(Class<?> t, String log, Exception e) {
		logger = LoggerFactory.getLogger(t);
		logger.debug(log,e);	
	}
	
	public static void debugTime(Class<?> t, String log,  long start) {
		logger = LoggerFactory.getLogger(t);
		if(logger.isDebugEnabled())
			logger.debug(log,System.currentTimeMillis()-start);
	}

	public static void info(Class<?> t, String log, Exception e) {
		logger = LoggerFactory.getLogger(t);
		logger.info(log,e);
	}

	public static void warn(Class<?> t, String log, Exception e) {
		logger = LoggerFactory.getLogger(t);
		logger.warn(log,e);
	}

	public static void error(Class<?> t, String log, Exception e) {
		logger = LoggerFactory.getLogger(t);
		logger.error(log,e);
	}
	
	public static void errorTime(Class<?> t, String log,  long start, Exception e) {
		logger = LoggerFactory.getLogger(t);
		logger.error(log,System.currentTimeMillis()-start,e);
	}
}
