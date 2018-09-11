package ecmTest.SPITest;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.model.Ace;
import yukcommon.spi.AceProvider;

public class TestAceProvider implements AceProvider{

	@Override
	public void addAce(HttpRequest request, HttpResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Ace> getAceList(HttpRequest request, HttpResponse response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delAce(HttpRequest request, HttpResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updAce(HttpRequest request, HttpResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Ace getAce(String aclId, String aceId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
