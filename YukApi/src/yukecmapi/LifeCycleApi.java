package yukecmapi;

import java.util.List;

import yukcommon.dic.UriDic;
import yukcommon.exception.NotNullAllowedException;
import yukcommon.model.lifecycle.LifeCycleInfo;
import yukcommon.model.lifecycle.LifeCycleSetting;


public class LifeCycleApi extends AbsApi{
	public LifeCycleApi(EcmConnection conn) {
		super(conn);
	}

	public String addLifeCycleSch(LifeCycleSetting setting) throws Exception{
		if(setting == null)
			throw new NotNullAllowedException("add lifecycle needed LifeCycleSetting Object");
		LifeCycleApiImpl impl = EcmApiFactory.getLifeCycleApi(conn,UriDic.ADDLCSCH);
		return impl.addLifeCycleSch(setting);
	}

	public void delLifeCycleSch(String id) throws Exception{
		LifeCycleApiImpl impl = EcmApiFactory.getLifeCycleApi(conn,UriDic.DELLCSCH);
		impl.delLifeCycleSch(id);
	}

	public List<LifeCycleSetting> getLifeCycleSch() throws Exception{
		LifeCycleApiImpl impl = EcmApiFactory.getLifeCycleApi(conn,UriDic.GETLCSCH);
		return impl.getLifeCycleSch();
	}

	public void startLifeCycle(String id) throws Exception{
		LifeCycleApiImpl impl = EcmApiFactory.getLifeCycleApi(conn,UriDic.STARTLC);
		impl.startLifeCycle(id);
	}

	public void stopLifeCycle(String id) throws Exception{
		LifeCycleApiImpl impl = EcmApiFactory.getLifeCycleApi(conn,UriDic.STOPLC);
		impl.stopLifeCycle(id);
	}

	public LifeCycleInfo getLifeCycleInfo(String id) throws Exception{
		LifeCycleApiImpl impl = EcmApiFactory.getLifeCycleApi(conn,UriDic.GETLCINFO);
		return impl.getLifeCycleInfo(id);
	}
}
