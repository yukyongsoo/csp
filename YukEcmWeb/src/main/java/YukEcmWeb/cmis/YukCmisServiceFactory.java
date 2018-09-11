package YukEcmWeb.cmis;

import org.apache.chemistry.opencmis.commons.impl.server.AbstractServiceFactory;
import org.apache.chemistry.opencmis.commons.server.CallContext;
import org.apache.chemistry.opencmis.commons.server.CmisService;
import org.apache.chemistry.opencmis.server.support.wrapper.ConformanceCmisServiceWrapper;

public class YukCmisServiceFactory extends AbstractServiceFactory {
	@Override
	public CmisService getService(CallContext callContext) {
        YukCmisService service = new YukCmisService();  
        service.setCallContext(callContext);       
        return new ConformanceCmisServiceWrapper(service);
	}
}
