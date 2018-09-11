package YukEcmWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import YukEcmWeb.SessionBean;
import yukcommon.dic.UriDic;
import yukecmapi.ETCApi;

@RestController
public class WebETCController {
	@Autowired(required = true)
    private SessionBean session;
	
	@RequestMapping(UriDic.REINITCACHE)
	public void reinitCache() throws Exception {
		ETCApi api = new ETCApi(session.getConn());
		api.reinitCache();		
	}
}
