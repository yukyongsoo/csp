package yukecm.db;

import java.sql.SQLException;
import java.util.List;

import yukcommon.model.Pipe;

public interface PipeDbAction extends AbsDbAction{

	Pipe getPipe(String pipeId) throws SQLException;

	void addPipe(Pipe pipe) throws SQLException;

	void delPipe(Pipe pipe) throws SQLException;

	void updPipe(Pipe pipe) throws SQLException;

	List<Pipe> getPipeList() throws SQLException;

}