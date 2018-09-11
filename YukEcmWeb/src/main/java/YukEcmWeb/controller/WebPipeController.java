package YukEcmWeb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import YukEcmWeb.SessionBean;
import yukcommon.dic.EtcDic;
import yukcommon.dic.UriDic;
import yukcommon.model.Pipe;
import yukcommon.util.JsonUtil;
import yukecmapi.PipeApi;

@RestController
public class WebPipeController {
	@Autowired(required = true)
    private SessionBean session;
	
	@RequestMapping(UriDic.GETPIPE)
	public List<Pipe> getPipe() throws Exception {
		PipeApi api = new PipeApi(session.getConn());
		return api.getPipe();
	}
	
	@RequestMapping(UriDic.ADDPIPE)
	public String addPipe(@RequestHeader(EtcDic.PIPE) String json) throws Exception {
		Pipe pipe = JsonUtil.fromJson(json, Pipe.class);
		PipeApi api = new PipeApi(session.getConn());
		return api.addPipe(pipe.getName(), pipe.getClassName(), pipe.isPassOnError());
	}
	
	@RequestMapping(UriDic.DELPIPE)
	public void delPipe(@RequestHeader(EtcDic.PIPE) String json) throws Exception {
		Pipe pipe = JsonUtil.fromJson(json, Pipe.class);
		PipeApi api = new PipeApi(session.getConn());
		api.delPipe(pipe.getId());
	}
	
	@RequestMapping(UriDic.UPDPIPE)
	public void updPipe(@RequestHeader(EtcDic.PIPE) String json) throws Exception {
		Pipe pipe = JsonUtil.fromJson(json, Pipe.class);
		PipeApi api = new PipeApi(session.getConn());
		api.updPipe(pipe.getId(), pipe.getName(), pipe.getClassName(), pipe.isPassOnError());
	}
}
