package ecmTest.SPITest;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.model.Doc;
import yukcommon.model.Folder;
import yukcommon.model.User;
import yukcommon.spi.SecureProvider;

public class TestSecureProvider implements SecureProvider{

	@Override
	public User checkUser(HttpRequest request, HttpResponse response, String uri) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkSystemPermission(HttpRequest request, HttpResponse response, User user, String uri)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkFolderPermission(Folder folder, User user, String uri) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkDocPermission(Doc doc, User user, String uri) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
