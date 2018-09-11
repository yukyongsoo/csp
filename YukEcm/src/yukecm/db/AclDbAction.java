package yukecm.db;

import java.sql.SQLException;
import java.util.List;

import yukcommon.model.Acl;

public interface AclDbAction extends AbsDbAction{

	Acl getAcl(String aclid) throws SQLException;

	void addAcl(Acl acl) throws SQLException;

	void delAcl(Acl acl) throws SQLException;

	void updAcl(Acl acl) throws SQLException;

	Acl getAclByName(String aclName) throws SQLException;

	List<Acl> getAclList() throws SQLException;

}