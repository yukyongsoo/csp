package test;

import yukcommon.dic.type.RuleType;
import yukcommon.dic.type.SubMetaSettingType;
import yukcommon.model.Ace;
import yukcommon.model.Acl;
import yukcommon.model.Cluster;
import yukcommon.model.Doc;
import yukcommon.model.Folder;
import yukcommon.model.Group;
import yukcommon.model.Pipe;
import yukcommon.model.Repository;
import yukcommon.model.Rule;
import yukcommon.model.Storage;
import yukcommon.model.User;
import yukcommon.model.WorkingGroup;
import yukcommon.model.lifecycle.LifeCycleInfo;
import yukcommon.model.lifecycle.LifeCycleSetting;
import yukcommon.model.meta.MetaData;
import yukcommon.model.meta.MetaSetting;
import yukcommon.model.meta.SubMetaSettingData;
import yukcommon.model.subrepo.SubRepoPipe;
import yukcommon.model.subrepo.SubRepoStr;
import yukcommon.model.subrule.DesRule;
import yukcommon.model.subrule.InitRule;
import yukcommon.model.subrule.MigRule;
import yukcommon.model.subrule.MigSubRule;
import yukcommon.util.JsonUtil;

public class JavaToJson {

	public static void main(String[] args) {
		Doc doc = new Doc();
		print(doc);
		
		Folder folder = new Folder();
		print(folder);
		
		Acl acl = new Acl();
		print(acl);
		
		Ace ace = new Ace();
		print(ace);

		Cluster cluster = new Cluster();
		print(cluster);
		
		Pipe pipe = new Pipe();
		print(pipe);
		
		Repository repo = new Repository();
		repo.getGetOrderList().put(1, new SubRepoStr());
		repo.getGetOrderList().put(2, new SubRepoStr());
		repo.getPutOrderList().put(1, new SubRepoStr());
		repo.getPutOrderList().put(2, new SubRepoStr());
		repo.getPipeMap().put(1, new SubRepoPipe());
		repo.getPipeMap().put(2, new SubRepoPipe());
		print(repo);
		
		print(new SubRepoStr());
		print(new SubRepoPipe());
		
		InitRule init = new InitRule();
		print(init);
		
		DesRule des = new DesRule();
		print(des);
		
		MigSubRule sub = new MigSubRule();
		print(sub);
		
		MigRule mig = new MigRule();
		mig.getTargetList().add(sub);
		print(mig);
		
		Rule rule = new Rule();
		rule.setType(RuleType.INITRULE);
		rule.setSubRule(des);
		print(rule);
		rule.setSubRule(mig);
		print(rule);
		rule.setSubRule(init);
		print(rule);
		
		Storage str= new Storage();
		print(str);
		
		User user = new User();
		print(user);
		
		Group group = new Group();
		print(group);
		
		WorkingGroup workGroup = new WorkingGroup();
		print(workGroup);
		
		MetaData data = new MetaData();
		print(data);
		
		MetaSetting setting = new MetaSetting();
		setting.setName("getTest");

		SubMetaSettingData subset = new SubMetaSettingData();
		subset.setOrder(1);
		subset.setType(SubMetaSettingType.STRING);
		setting.getMap().put("docId", subset);

		SubMetaSettingData subset2 = new SubMetaSettingData();
		subset2.setOrder(2);
		subset2.setType(SubMetaSettingType.STRING);
		setting.getMap().put("meta1", subset2);

		SubMetaSettingData sub3 = new SubMetaSettingData();
		sub3.setKeyName("meta2");
		sub3.setOrder(3);
		sub3.setType(SubMetaSettingType.STRING);
		setting.getMap().put("meta2", sub3);
		print(setting);
		
		print(sub3);
	
		LifeCycleInfo info = new LifeCycleInfo();
		print(info);
		
		LifeCycleSetting lcsetting = new LifeCycleSetting();
		print(lcsetting);
	}
	
	private static void print(Object obj) {
		System.out.println("-------START---------");
		System.out.println(obj.getClass().getName());
		System.out.println(JsonUtil.toJson(obj));
		System.out.println("-------END-----------");
	}

}
