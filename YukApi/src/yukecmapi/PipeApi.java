package yukecmapi;

import java.util.List;

import yukcommon.dic.UriDic;
import yukcommon.model.Pipe;

public class PipeApi extends AbsApi{
	public PipeApi(EcmConnection conn) {
		super(conn);
	}

	public String addPipe(String name, String className, boolean passOnError) throws Exception{
		PipeApiImpl impl = EcmApiFactory.getPipeApi(conn,UriDic.ADDPIPE);
		return impl.addPipe(name, className, passOnError);
	}

	public void delPipe(String id) throws Exception{
		PipeApiImpl impl = EcmApiFactory.getPipeApi(conn,UriDic.DELPIPE);
		impl.delPipe(id);
	}
	
	public void updPipe(String id, String name, String className,boolean passOnError) throws Exception{
		PipeApiImpl impl = EcmApiFactory.getPipeApi(conn,UriDic.UPDPIPE);
		impl.updPipe(id, name, className, passOnError);
	}

	public List<Pipe> getPipe() throws Exception {
		PipeApiImpl impl = EcmApiFactory.getPipeApi(conn,UriDic.GETPIPE);
		return impl.getPipe();
	}
}
