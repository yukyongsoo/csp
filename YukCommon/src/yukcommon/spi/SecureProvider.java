package yukcommon.spi;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.model.Doc;
import yukcommon.model.Folder;
import yukcommon.model.User;

public interface SecureProvider {
	public User checkUser(HttpRequest request, HttpResponse response, String uri) throws Exception;
	public void checkSystemPermission(HttpRequest request, HttpResponse response, User user, String uri) throws Exception;
	public void checkFolderPermission(Folder folder, User user, String uri) throws Exception;
	public void checkDocPermission(Doc doc, User user, String uri) throws Exception;
}