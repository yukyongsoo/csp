import java.util.List;

import org.junit.Test;

import yukcommon.dic.type.MetaSettingType;
import yukcommon.dic.type.SubMetaSettingType;
import yukcommon.model.meta.MetaData;
import yukcommon.model.meta.MetaSetting;
import yukcommon.model.meta.SubMetaSettingData;


public class MetaTest extends AbsTest{

	public MetaTest() throws Exception {
		super();
	}

	@Test
	public void addDocMeta() throws Exception{
		MetaData meta = new MetaData();
		meta.setMetaName("addTest");
		meta.addMeta("docId", "1");
		meta.addMeta("meta1", "2");
		meta.addMeta("meta2", "3");
		metaApi.addDocMeta(meta);
	}

	@Test
	public void delDocMeta() throws Exception{
		MetaData meta = new MetaData();
		meta.setMetaName( "delTest");
		meta.addMeta("docId", "1");
		metaApi.delDocMeta(meta);
	}

	@Test
	public void getDocMeta() throws Exception{
		MetaData meta = new MetaData();
		meta.setMetaName("getTest");
		meta.addMeta("docId", "1");
		List<MetaData> docMeta = metaApi.getDocMeta(meta);
		for(MetaData data : docMeta){
			System.out.println((String)data.getMeta("DOCID"));
			System.out.println((String)data.getMeta("META1"));
			System.out.println((String)data.getMeta("META2"));
		}
	}

	@Test
	public void addMetaSettingTypeAdd() throws Exception{
		MetaSetting setting = new MetaSetting();
		setting.setName("addTest");
		setting.setQuery("insert into TESTMETA (docId,meta1,meta2) VALUES (?,?,?)"); 
		setting.setType(MetaSettingType.ADD);
		metaApi.addMetaSetting(setting);
	}
	
	@Test
	public void addMetaSettingTypeGet() throws Exception{
		MetaSetting setting = new MetaSetting();
		setting.setName("getTest"); 
		setting.setQuery("select docId,meta1,meta2 from TESTMETA where docid = ?");
		setting.setType(MetaSettingType.GET); 
		metaApi.addMetaSetting(setting);
	}
	
	@Test
	public void addMetaSettingTypeDel() throws Exception{
		MetaSetting setting = new MetaSetting();
		setting.setName("delTest");
		setting.setQuery("delete from TESTMETA where docId = ?"); 
		setting.setType(MetaSettingType.DEL);
		metaApi.addMetaSetting(setting);
	}

	@Test
	public void delMetaSetting() throws Exception{
		metaApi.delMetaSetting("addTest");
	}

	@Test
	public void getMetaSetting() throws Exception{
		MetaSetting metaSetting = metaApi.getMetaSetting("addTest");
		System.out.println(metaSetting.getName());
	}

	@Test
	public void addMetaSubSettingTypeAdd() throws Exception{
		MetaSetting setting = new MetaSetting();
		setting.setName("addTest");

		SubMetaSettingData sub = new SubMetaSettingData();
		sub.setOrder(1);
		sub.setType(SubMetaSettingType.STRING); 
		setting.getMap().put("docId", sub);

		SubMetaSettingData sub2 = new SubMetaSettingData();
		sub2.setOrder(2);
		sub2.setType(SubMetaSettingType.STRING);
		setting.getMap().put("meta1", sub2);

		SubMetaSettingData sub3 = new SubMetaSettingData();
		sub3.setOrder(3);
		sub3.setType(SubMetaSettingType.STRING);
		setting.getMap().put("meta2", sub3);

		metaApi.addMetaSubSetting(setting);
	}
	
	@Test
	public void addMetaSubSettingTypeDel() throws Exception{
		MetaSetting setting = new MetaSetting();
		setting.setName("delTest");

		SubMetaSettingData sub = new SubMetaSettingData();
		sub.setOrder(1);
		sub.setType(SubMetaSettingType.STRING); 
		setting.getMap().put("docId", sub);

		metaApi.addMetaSubSetting(setting);
	}
	
	@Test
	public void addMetaSubSettingTypeGet() throws Exception{
		MetaSetting setting = new MetaSetting();
		setting.setName("getTest");

		SubMetaSettingData sub = new SubMetaSettingData();
		sub.setOrder(1);
		sub.setType(SubMetaSettingType.STRING); 
		setting.getMap().put("docId", sub);

		metaApi.addMetaSubSetting(setting);
	}

	@Test
	public void delMetaSubSetting() throws Exception{
		MetaSetting setting = new MetaSetting();
		setting.setName("addTest");

		SubMetaSettingData sub2 = new SubMetaSettingData();
		sub2.setOrder(2);
		sub2.setType(SubMetaSettingType.STRING);
		setting.getMap().put("meta1", sub2);

		metaApi.delMetaSubSetting(setting);
	}
}
