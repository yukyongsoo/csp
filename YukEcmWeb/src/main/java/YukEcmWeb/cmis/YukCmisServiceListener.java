 package YukEcmWeb.cmis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.chemistry.opencmis.commons.impl.ClassLoaderUtil;
import org.apache.chemistry.opencmis.commons.impl.IOUtils;
import org.apache.chemistry.opencmis.commons.server.CmisServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YukCmisServiceListener implements ServletContextListener{
	   public static final String SERVICES_FACTORY = "org.apache.chemistry.opencmis.servicesfactory";
	    private static final Logger LOG = LoggerFactory.getLogger(YukCmisServiceListener.class.getName());
	    private static final String CONFIG_INIT_PARAM = "org.apache.chemistry.opencmis.REPOSITORY_CONFIG_FILE";
	    private static final String CONFIG_FILENAME = "/repository.properties";
	    private static final String PROPERTY_CLASS = "class";

	    @Override
	    public void contextInitialized(ServletContextEvent sce) {
	        // get config file name or use default
	        String configFilename = sce.getServletContext().getInitParameter(CONFIG_INIT_PARAM);
	        if (configFilename == null) {
	            configFilename = CONFIG_FILENAME;
	        }
	        // create services factory
	        CmisServiceFactory factory = null;
	        try {
	            factory = createServiceFactory(configFilename);
	        } catch (Exception e) {
	            LOG.error("Service factory couldn't be created: {}", e.toString(), e);
	            return;
	        }
	        // set the services factory into the servlet context
	        sce.getServletContext().setAttribute(SERVICES_FACTORY, factory);
	    }

	    @Override
	    public void contextDestroyed(ServletContextEvent sce) {
	        // destroy services factory
	        CmisServiceFactory factory = (CmisServiceFactory) sce.getServletContext().getAttribute(SERVICES_FACTORY);
	        if (factory != null) {
	            try {
	                factory.destroy();
	            } catch (Exception e) {
	                LOG.error("Service factory couldn't be destroyed: {}", e.toString(), e);
	                return;
	            } 
	        }
	    }

	    /**
	     * Gets the service factory from the servlet context.
	     */
	    public static CmisServiceFactory getServiceFactory(final ServletContext servletContext) {
	        return (CmisServiceFactory) servletContext.getAttribute(SERVICES_FACTORY);
	    }

	    /**
	     * Creates a service factory.
	     */
	    private CmisServiceFactory createServiceFactory(String filename) {
	        // load properties
	        InputStream stream = this.getClass().getResourceAsStream(filename);

	        if (stream == null) {
	            LOG.warn("Cannot find configuration!");
	            return null;
	        }

	        Properties props = new Properties();
	        try {
	            props.load(stream);
	        } catch (IOException e) {
	            LOG.warn("Cannot load configuration: {}", e.toString(), e);
	            return null;
	        } finally {
	            IOUtils.closeQuietly(stream);
	        }

	        // get 'class' property
	        String className = props.getProperty(PROPERTY_CLASS);
	        if (className == null) {
	            LOG.warn("Configuration doesn't contain the property 'class'!");
	            return null;
	        }

	        // create a factory instance
	        Object object = null;
	        try {
	            object = ClassLoaderUtil.loadClass(className).newInstance();
	        } catch (Exception e) {
	            LOG.warn("Could not create a services factory instance: {}", e.toString(), e);
	            return null;
	        }

	        if (!(object instanceof CmisServiceFactory)) {
	            LOG.warn("The provided class is not an instance of CmisServiceFactory!");
	        }

	        CmisServiceFactory factory = (CmisServiceFactory) object;

	        // initialize factory instance
	        Map<String, String> parameters = new HashMap<String, String>(props.size());

	        for (Enumeration<?> e = props.propertyNames(); e.hasMoreElements();) {
	            String key = (String) e.nextElement();
	            String value = props.getProperty(key);
	            parameters.put(key, value);
	        }

	        factory.init(parameters);

	        if (LOG.isInfoEnabled()) {
	            LOG.info("Initialized Services Factory: {}", factory.getClass().getName());
	        }
	        return factory;
	    }
}
