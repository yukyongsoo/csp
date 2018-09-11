package yukecm.injecter.acl;

import java.sql.SQLException;
import java.util.List;

import yukcommon.dic.type.OnErrorType;
import yukcommon.model.Acl;
import yukecm.cache.Cache;
import yukecm.cache.YLC;
import yukecm.config.BaseProperty;
import yukecm.db.AclDbAction;
import yukecm.db.DbConnFac;
import yukecm.injecter.InjecterUtil;

public class AclInjector {
	private static AclInjector injecter;

	public static AclInjector getInstance() throws InterruptedException, SQLException {
		if(injecter == null)
			injecter = new AclInjector();
		return injecter;
	}

	private Cache<String, Acl> aclMap;

	private AclInjector() throws InterruptedException, SQLException {
		if(BaseProperty.getInstance().inMem){
			aclMap = YLC.makeCache("ACL", new AclJson());
			initAcl();
		}
	}

	public void initAcl() throws InterruptedException, SQLException {
		List<Acl> aclList = getAclList();
		for(Acl acl : aclList)
			aclMap.put(acl.getId(), acl);
	}

	public Acl getAcl(String aclid, OnErrorType type) throws SQLException {
		AclDbAction action = null;
		Acl acl = null;
		try {
			if (BaseProperty.getInstance().inMem) {
				acl = aclMap.get(aclid);
				InjecterUtil.onErrorException(acl, type);
				return acl;
			}
			action = DbConnFac.getInstance().getAclDbAction();
			acl = action.getAcl(aclid);
			InjecterUtil.onErrorException(acl, type);
			return acl;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public Acl getAclByName(String aclName, OnErrorType type) throws SQLException {
		AclDbAction action = null;
		Acl nAcl = null;
		try {
			if (BaseProperty.getInstance().inMem) {
				for(Acl acl : aclMap.values()){
					if(acl.getName().equals(aclName)) {
						nAcl = acl;
						break;
					}
					
				}
				InjecterUtil.onErrorException(nAcl, type);
				return nAcl;
			}
			action = DbConnFac.getInstance().getAclDbAction();
			nAcl = action.getAclByName(aclName);
			InjecterUtil.onErrorException(nAcl, type);
			return nAcl;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void addAcl(Acl acl) throws InterruptedException, SQLException {
		AclDbAction action = null;
		try {
			if (BaseProperty.getInstance().inMem)
				aclMap.put(acl.getId(), acl);
			action = DbConnFac.getInstance().getAclDbAction();
			action.addAcl(acl);
			action.commits();
		} catch (SQLException e) {
			if (BaseProperty.getInstance().inMem)
				aclMap.remove(acl.getId());
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void delAcl(Acl acl) throws InterruptedException, SQLException {
		AclDbAction action = null;
		Acl rollback = null;
		try {
			if (BaseProperty.getInstance().inMem)
				rollback = aclMap.remove(acl.getId());
			action = DbConnFac.getInstance().getAclDbAction();
			action.delAcl(acl);
			action.commits();
		} catch (SQLException e) {
			if (BaseProperty.getInstance().inMem)
				aclMap.put(acl.getId(), rollback);
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public void updAcl(Acl acl) throws SQLException, InterruptedException {
		AclDbAction action = null;
		Acl rollback = null;
		try {
			if(BaseProperty.getInstance().inMem){
				rollback = aclMap.remove(acl.getId());
				aclMap.put(acl.getId(), acl);
			}
			action = DbConnFac.getInstance().getAclDbAction();
			action.updAcl(acl);
			action.commits();
		} catch (SQLException e) {
			if(BaseProperty.getInstance().inMem)
				aclMap.put(acl.getId(), rollback);
			DbConnFac.staticRollBack(action);
			throw e;
		} finally {
			DbConnFac.staticClose(action);
		}
	}

	public List<Acl> getAclList() throws SQLException {
		AclDbAction action = null;
		try {
			action = DbConnFac.getInstance().getAclDbAction();
			return action.getAclList();
		} finally {
			DbConnFac.staticClose(action);
		}
	}
}
