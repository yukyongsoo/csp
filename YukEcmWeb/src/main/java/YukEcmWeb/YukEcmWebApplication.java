package YukEcmWeb;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContextListener;

import org.apache.chemistry.opencmis.server.impl.atompub.CmisAtomPubServlet;
import org.apache.chemistry.opencmis.server.impl.browser.CmisBrowserBindingServlet;
import org.apache.chemistry.opencmis.server.impl.endpoints.SimpleCmisEndpointsDocumentServlet;
import org.apache.chemistry.opencmis.server.impl.webservices.CmisWebServicesServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import YukEcmWeb.cmis.YukCmisServiceListener;

@SpringBootApplication
public class YukEcmWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(YukEcmWebApplication.class, args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(YukEcmWebApplication.class);
	}

	@Bean
	public ServletListenerRegistrationBean<ServletContextListener> listenerRegistrationBean() {
		ServletListenerRegistrationBean<ServletContextListener> bean = new ServletListenerRegistrationBean<ServletContextListener>();
		bean.setListener(new YukCmisServiceListener());
		return bean;
	}
	
	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("2048MB");
		factory.setMaxRequestSize("2048MB");
		return factory.createMultipartConfig();
	}

	/*@Bean
	public ServletRegistrationBean getCmisWebService10() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new CmisWebServicesServlet());
		registrationBean.addInitParameter("cmisVersion", "1.0");
		registrationBean.addUrlMappings("/services/*");
		return registrationBean;
	}*/

	@Bean
	public ServletRegistrationBean getCmisWebService11() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new CmisWebServicesServlet());
		registrationBean.addInitParameter("cmisVersion", "1.1");		
		registrationBean.addUrlMappings("/services11/*");
		registrationBean.setLoadOnStartup(1);
		return registrationBean;
	}

	/*@Bean
	public ServletRegistrationBean getCmisAtomPub10() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new CmisAtomPubServlet());
		registrationBean.addInitParameter("cmisVersion", "1.0");
		registrationBean.addInitParameter("callContextHandler",
				"org.apache.chemistry.opencmis.server.shared.BasicAuthCallContextHandler");
		registrationBean.addUrlMappings("/atom/*");
		return registrationBean;
	}*/

	@Bean
	public ServletRegistrationBean getCmisAtomPub11() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new CmisAtomPubServlet());
		registrationBean.addInitParameter("cmisVersion", "1.1");
		registrationBean.addInitParameter("callContextHandler",
				"org.apache.chemistry.opencmis.server.shared.BasicAuthCallContextHandler");
		registrationBean.addUrlMappings("/atom11/*");
		registrationBean.setLoadOnStartup(2);
		return registrationBean;
	}

	@Bean
	public ServletRegistrationBean getCmisbrowser() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new CmisBrowserBindingServlet());
		registrationBean.addInitParameter("callContextHandler",
				"org.apache.chemistry.opencmis.server.impl.browser.token.TokenCallContextHandler");
		registrationBean.addUrlMappings("/browser/*");
		registrationBean.setLoadOnStartup(2);
		return registrationBean;
	}

	@Bean
	public ServletRegistrationBean getCmisEndpointsDocumentServlet() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(
				new SimpleCmisEndpointsDocumentServlet());
		registrationBean.addInitParameter("template", "/WEB-INF/cmis-endpoints.json");
		registrationBean.addUrlMappings("/cmis-endpoints.json");
		registrationBean.setLoadOnStartup(3);
		return registrationBean;
	}

}
