package yukecm.db;

import java.sql.SQLException;
import java.util.List;

import yukcommon.model.Ace;

public interface AceDbAction extends AbsDbAction{

	List<Ace> getAceAllList() throws SQLException;

	Ace getAce(Ace ace) throws SQLException;

	List<Ace> getAceList(Ace ace) throws SQLException;

	void addAce(Ace ace) throws SQLException;

	void delAce(Ace ace) throws SQLException;

	void updAce(Ace ace) throws SQLException;

}