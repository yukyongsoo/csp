package yukcommon.spi;

import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import yukcommon.model.Ace;

public interface AceProvider {

	void addAce(HttpRequest request, HttpResponse response) throws Exception;

	List<Ace> getAceList(HttpRequest request, HttpResponse response) throws Exception;

	void delAce(HttpRequest request, HttpResponse response) throws Exception;

	void updAce(HttpRequest request, HttpResponse response) throws Exception;

	Ace getAce(String aclId, String aceId) throws Exception;
}
