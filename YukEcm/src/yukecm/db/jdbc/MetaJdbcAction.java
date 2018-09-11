package yukecm.db.jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yukcommon.dic.type.SubMetaSettingType;
import yukcommon.exception.NotSupportException;
import yukcommon.model.meta.MetaData;
import yukecm.db.MetaDbAction;

public class MetaJdbcAction extends AbsJdbcAction implements MetaDbAction{
	private PreparedStatement statement;

	protected MetaJdbcAction() throws SQLException {
		super();
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.MetaDbAction#makePrepare(java.lang.String)
	 */
	@Override
	public void makePrepare(String query) throws SQLException {
		statement = connection.prepareStatement(query);
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.MetaDbAction#excute()
	 */
	@Override
	public void excute() throws SQLException {
		try{
			statement.executeUpdate();
			connection.commit();
		}
		finally{
			statement.close();
		}
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.MetaDbAction#addParam(int, java.lang.Object, yukcommon.dic.type.SubMetaSettingType)
	 */
	@Override
	public void addParam(int order, Object value, SubMetaSettingType type) throws SQLException {
		if(SubMetaSettingType.STRING == type)
			statement.setString(order, (String) value);
		else if(SubMetaSettingType.BYTE == type)
			statement.setByte(order, (Byte)value);
		else if(SubMetaSettingType.BOOLEAN == type)
			statement.setBoolean(order, (Boolean) value);
		else if(SubMetaSettingType.DATE == type)
			statement.setDate(order, (Date) value);
		else if(SubMetaSettingType.FLOAT == type)
			statement.setFloat(order, (Float) value);
		else if(SubMetaSettingType.INT == type)
			statement.setInt(order, (Integer) value);
		else if(SubMetaSettingType.LONG == type)
			statement.setLong(order, (Long) value);
		else
			throw new NotSupportException("This Type Not Supported." + type.toString());
	}

	/* (non-Javadoc)
	 * @see yukecm.db.jdbc.MetaDbAction#mapping()
	 */
	@Override
	public List<MetaData> mapping() throws SQLException {
		ResultSet resultSet = null;
		List<MetaData> list = new ArrayList<MetaData>();
		try{
			resultSet = statement.executeQuery();
			//check mem leak~~~~!!
			ResultSetMetaData rMeta = resultSet.getMetaData();
			int count = rMeta.getColumnCount();
			while(resultSet.next()){
				MetaData data = new MetaData();
				for(int i = 1 ; i <= count; i++){
					data.addMeta(rMeta.getColumnName(i), resultSet.getString(i));
				}
				list.add(data);
			}
			return list;
		}finally{
			closeResouce(resultSet, statement);
		}
	}
}
