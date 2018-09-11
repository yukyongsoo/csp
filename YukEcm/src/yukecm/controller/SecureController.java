package yukecm.controller;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.exception.AdpatorException;
import yukcommon.model.Doc;
import yukcommon.model.Folder;
import yukcommon.model.User;
import yukcommon.spi.SecureProvider;
import yukcommon.util.LoggerUtil;
import yukecm.config.BaseProperty;
import yukecm.etc.EcmUtil;

public class SecureController {
	private static class LazyHolder {
	    private static final SecureController secure;
	    static{
	    	try {
				secure = new SecureController();
			} catch (AdpatorException e) {
				throw new ExceptionInInitializerError(e);
			}
	    }
	}

	public static SecureController getInstance() {
		return LazyHolder.secure;
	}

	SecureProvider provider;

	private SecureController() {
		provider = (SecureProvider)EcmUtil.makeClass(BaseProperty.getInstance().secureProvider);
	}

	public User checkUser(HttpRequest request, HttpResponse response, String uri) throws Exception  {
		long start = System.currentTimeMillis();
		try {
			User user = provider.checkUser(request, response,uri);
			LoggerUtil.traceTime(getClass(), "Secure Check Time is {}Ms", start);
			return user;
		} catch (Exception e) {
			LoggerUtil.traceTime(getClass(), "Secure Check Fail.Time is {}Ms", start);
			throw e;
		}
	}

	public void checkSystemPermission(HttpRequest request, HttpResponse response, User user, String uri) throws Exception {
		long start = System.currentTimeMillis();
		try {
			provider.checkSystemPermission(request,response,user,uri);
			LoggerUtil.traceTime(getClass(), "Secure Check Time is {}Ms", start);
		} catch (Exception e) {
			LoggerUtil.traceTime(getClass(), "Secure Check Fail.Time is {}Ms", start);
			throw e;
		}
		
	}

	public void checkFolderPermission(Folder folder, User user, String uri) throws Exception {
		long start = System.currentTimeMillis();
		try {
			provider.checkFolderPermission(folder,user,uri);
			LoggerUtil.traceTime(getClass(), "Secure Check Time is {}Ms", start);
		} catch (Exception e) {
			LoggerUtil.traceTime(getClass(), "Secure Check Fail.Time is {}Ms", start);
			throw e;
		}
		
	}

	public void checkDocPermission(Doc doc, User user, String uri) throws Exception {
		long start = System.currentTimeMillis();
		try {
			provider.checkDocPermission(doc,user,uri);
			LoggerUtil.traceTime(getClass(), "Secure Check Time is {}Ms", start);
		} catch (Exception e) {
			LoggerUtil.traceTime(getClass(), "Secure Check Fail.Time is {}Ms", start);
			throw e;
		}
	}
}
