package ecmTest.SPITest;

import yukcommon.adaptor.PipeAdaptor;
import yukcommon.model.Content;

public class TestPipe extends PipeAdaptor{

	@Override
	protected boolean inboundImpl(Content content) throws Exception {
		System.out.println("Check Inbound");
		return true;
	}

	@Override
	protected boolean outboudImpl(Content content) throws Exception {
		System.out.println("Check OutBound");
		return true;
	}

}
