import java.util.List;

import org.junit.Test;

import yukcommon.dic.type.RuleType;
import yukcommon.model.lifecycle.LifeCycleInfo;
import yukcommon.model.lifecycle.LifeCycleSetting;


public class LifeCycleTest extends AbsTest{

	public LifeCycleTest() throws Exception {
		super();
	}

	@Test
	public void addLifeCycleSch() throws Exception{
		LifeCycleSetting setting = new LifeCycleSetting();
		setting.setName("test3");
		setting.setType(RuleType.DESRULE);
		setting.setWorkId("fd663723-3824-4a6f-bac9-5fc9f39f2057");
		String id = lcApi.addLifeCycleSch(setting);
		System.out.println(id);
	}

	@Test
	public void delLifeCycleSch() throws Exception{
		lcApi.delLifeCycleSch("201431f6-bed0-4865-ae24-4f0aa3adb678");
	}

	@Test
	public void getLifeCycleSch() throws Exception{
		List<LifeCycleSetting> lifeCycleSch = lcApi.getLifeCycleSch();
		for(LifeCycleSetting se : lifeCycleSch)
			System.out.println(se);
	}

	@Test
	public void startLifeCycle() throws Exception{
		lcApi.startLifeCycle("d30fb539-891b-4374-ab32-26e8b9f712a6");
	}

	@Test
	public void stopLifeCycle() throws Exception{
		lcApi.stopLifeCycle("201431f6-bed0-4865-ae24-4f0aa3adb678");
	}

	@Test
	public void getLifeCycleInfo() throws Exception{
		LifeCycleInfo lifeCycleInfo = lcApi.getLifeCycleInfo("201431f6-bed0-4865-ae24-4f0aa3adb678");
		System.out.println(lifeCycleInfo.toString());
	}
}
