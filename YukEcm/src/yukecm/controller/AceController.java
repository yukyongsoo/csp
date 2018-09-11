package yukecm.controller;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.exception.AdpatorException;
import yukcommon.model.Ace;
import yukcommon.spi.AceProvider;
import yukecm.config.BaseProperty;
import yukecm.etc.EcmUtil;

public class AceController {
	private static class LazyHolder {
	    private static final AceController ace; 
	    static {
	    	try {
	    		ace = new AceController();
			} catch (AdpatorException e) {
				throw new ExceptionInInitializerError(e);
			}
	    }
	}

	public static AceController getInstance(){
		return LazyHolder.ace;
	}

	private AceProvider provider;
	
	private AceController() {
		provider = (AceProvider)EcmUtil.makeClass(BaseProperty.getInstance().aceProvider);
	}

	public void addAce(HttpRequest request, HttpResponse response) throws Exception {
		provider.addAce(request,response);
	}

	public List<Ace> getAce(HttpRequest request, HttpResponse response) throws Exception {
		return provider.getAceList(request,response);
	}

	public void delAce(HttpRequest request, HttpResponse response) throws Exception {
		provider.delAce(request,response);
	}

	public void updAce(HttpRequest request, HttpResponse response) throws Exception {
		provider.updAce(request,response);
	}

	public Ace getAce(String aclId, String aceId) throws Exception {
		return provider.getAce(aclId, aceId);
	}
}
