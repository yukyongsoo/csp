package YukEcmWeb;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import yukcommon.net.SSLProviderUtil;
import yukecmapi.EcmApiFactory;
import yukecmapi.EcmConnection;
import yukecmapi.UserGroupApi;

@Component
public class SecurityProvider implements AuthenticationProvider {
	@Autowired(required = true)
    private HttpServletRequest request;
	@Autowired(required = true)
    private SessionBean session;
	@Value("${ecm.ssl.trust-store}")
	private String storePath;
	@Value("${ecm.ssl.trust-store-password}")
	private String storePass;
	
	@Override
	public Authentication authenticate(Authentication authentication)  {
		String addr = request.getParameter("address");
		String id = request.getParameter("username");
		String password = request.getParameter("password");
		
		try {
			/*EcmApiFactory.init(1, true,false,10000,SSLProviderUtil.getClientSSL(storePath, storePass));
			EcmConnection conn = EcmApiFactory.getConnection(addr, id, password);
			session.setConn(conn);
			UserGroupApi api = new UserGroupApi(session.getConn());
			api.checkUser(id, password);*/
			List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
			grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
			return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(),grantedAuths);
		} 
		catch (Exception e) {
			throw new AuthenticationServiceException(e.getMessage());
		} 
	}

	@Override
	public boolean supports(Class<?> authentication) {
		 return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
