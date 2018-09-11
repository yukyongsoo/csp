package yukecmapi;

import java.sql.SQLException;

import yukcommon.dic.type.OnErrorType;
import yukcommon.exception.WrongOperationException;
import yukcommon.model.Doc;
import yukcommon.model.Version;
import yukcommon.model.fileitem.IFileItem;
import yukecm.injecter.docNcontent.DCinjetor;

public class VersionApi extends AbsApi{

	public VersionApi(EcmConnection conn) {
		super(conn);
		adfdsaafsd
	}
	
	public void checkOut(Version version) throws Exception {
	
	}

	public void checkIn(Version version, IFileItem item) throws Exception {
		
	}

	public void cancleCheckOut(Version version)throws Exception {
	
	}

	public void reverseVersion(Version version) throws Exception {
		
	}

}
