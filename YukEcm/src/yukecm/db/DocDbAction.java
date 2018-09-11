package yukecm.db;

import java.sql.SQLException;
import java.util.List;

import yukcommon.model.Doc;

public interface DocDbAction extends AbsDbAction{

	void addDoc(Doc doc) throws SQLException;

	Doc getDoc(Doc doc) throws SQLException;

	void delDoc(Doc doc) throws SQLException;

	void updDoc(Doc doc) throws SQLException;

	List<Doc> getFolderDocList(Doc doc) throws SQLException;

}