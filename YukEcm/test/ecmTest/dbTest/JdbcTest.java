package ecmTest.dbTest;

import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import ecmTest.TestConfigReader;
import yukcommon.model.Ace;
import yukecm.db.AceDbAction;
import yukecm.db.DbConnFac;
import yukecm.etc.EcmUtil;

public class JdbcTest extends TestConfigReader{
	private DbConnFac instance;
	
	public JdbcTest() throws ParserConfigurationException, SAXException, IOException {
		super();
		instance = DbConnFac.getInstance();
	}

	@Test
	public void addAceTest() throws SQLException {
		AceDbAction aceDbAction = instance.getAceDbAction();
		Ace ace = new Ace();
		ace.setId(EcmUtil.getId());
		ace.setChildId(EcmUtil.getId());
		aceDbAction.addAce(ace);
	}
	
	@Test
	public void getAceAllListTest() throws SQLException {
		AceDbAction aceDbAction = instance.getAceDbAction();
		aceDbAction.getAceAllList();
	}
	
	@Test
	public void getAceTest() throws SQLException {
		AceDbAction aceDbAction = instance.getAceDbAction();
		Ace ace = new Ace();
		ace.setChildId(EcmUtil.getId());
		aceDbAction.getAce(ace);
	}
	
	@Test
	public void getAceListTest() throws SQLException {
		AceDbAction aceDbAction = instance.getAceDbAction();
		Ace ace = new Ace();
		ace.setId(EcmUtil.getId());
		aceDbAction.getAceList(ace);
	}
	
	@Test
	public void delAce() throws SQLException {
		AceDbAction aceDbAction = instance.getAceDbAction();
		Ace ace = new Ace();
		ace.setId(EcmUtil.getId());
		aceDbAction.delAce(ace);
	}	

	@Test
	public void updAce() throws SQLException {
		AceDbAction aceDbAction = instance.getAceDbAction();
		Ace ace = new Ace();
		ace.setId(EcmUtil.getId());
		ace.setChildId(EcmUtil.getId());
		aceDbAction.updAce(ace);
	}
}
