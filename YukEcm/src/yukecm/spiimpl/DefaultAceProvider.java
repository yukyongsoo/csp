package yukecm.spiimpl;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.dic.EtcDic;
import yukcommon.dic.type.OnErrorType;
import yukcommon.exception.NotExistException;
import yukcommon.model.Ace;
import yukcommon.model.Acl;
import yukcommon.spi.AceProvider;
import yukcommon.util.JsonUtil;
import yukecm.controller.AclController;
import yukecm.injecter.ace.AceInjector;

public class DefaultAceProvider implements AceProvider{

	@Override
	public void addAce(HttpRequest request, HttpResponse response) throws Exception {
		String json = request.getFirstHeader(EtcDic.ACE).getValue();
		Ace ace = JsonUtil.fromJson(json, Ace.class);
		AceInjector.getInstance().getAce(ace,OnErrorType.EXIST);
		Acl acl = AclController.getInstance().getAcl(ace.getId());
		if(acl == null)
			throw new NotExistException("target acl not existed." + ace.getId());
		AceInjector.getInstance().addAce(ace);
	}

	@Override
	public void delAce(HttpRequest request, HttpResponse response) throws Exception {
		String json = request.getFirstHeader(EtcDic.ACE).getValue();
		Ace ace = JsonUtil.fromJson(json, Ace.class);
		AceInjector.getInstance().getAce(ace,OnErrorType.NOTEXIST);
		AceInjector.getInstance().delAce(ace);
	}

	@Override
	public void updAce(HttpRequest request, HttpResponse response) throws Exception {
		String json = request.getFirstHeader(EtcDic.ACE).getValue();
		Ace ace = JsonUtil.fromJson(json, Ace.class);
		AceInjector.getInstance().getAce(ace,OnErrorType.NOTEXIST);
		AceInjector.getInstance().updAce(ace);
	}

	@Override
	public Ace getAce(String aclId, String aceId) throws Exception {
		Ace ace = new Ace();
		ace.setId(aclId);
		ace.setChildId(aceId);
		return  AceInjector.getInstance().getAce(ace, OnErrorType.NONE);
	}

	@Override
	public List<Ace> getAceList(HttpRequest request, HttpResponse response) throws Exception {
		String json = request.getFirstHeader(EtcDic.ACE).getValue();
		Ace ace = JsonUtil.fromJson(json, Ace.class);
		return AceInjector.getInstance().getAceList(ace, OnErrorType.NONE);
	}
}
