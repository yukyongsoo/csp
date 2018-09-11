package yukecm.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface AbsDbAction {

	public abstract void commits() throws SQLException;

	public abstract void closeResouce(ResultSet resultSet, Statement statement);

	public abstract void close();

	public abstract void rollBack();
}