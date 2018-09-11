package yukecm.db;

import java.sql.SQLException;
import java.util.List;

import yukecm.lifecycle.LifeCycleCallable;

public interface LCDbAction extends AbsDbAction{

	//Docid,DocName,WorkId,createDate,DocLastVersion,IsCheckOut,MigId,MetaName,ParentFolderId,AclId,IsDerived
	List<LifeCycleCallable> getRangeTarget(String workId, String migId, String start, String end) throws SQLException;

	List<LifeCycleCallable> getRangeTargetPaging(String workId, String migId, String start, String end, long paging)
			throws SQLException;

	long getRangeTargetCount(String workId, String migId, String start, String end) throws SQLException;

	long getDesTargetCount(String workId, String dateTime) throws SQLException;

	List<LifeCycleCallable> getDesTarget(String workId, String dateTime) throws SQLException;

	List<LifeCycleCallable> getDesTargetPaging(String workId, String dateTime, long paging) throws SQLException;

}