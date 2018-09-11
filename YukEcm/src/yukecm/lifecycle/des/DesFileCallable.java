package yukecm.lifecycle.des;

import java.util.List;

import yukcommon.dic.type.OnErrorType;
import yukcommon.model.Content;
import yukcommon.model.Doc;
import yukcommon.model.Rule;
import yukcommon.model.Storage;
import yukcommon.model.WorkingGroup;
import yukcommon.model.subrule.DesRule;
import yukecm.controller.ContentController;
import yukecm.controller.StorageController;
import yukecm.injecter.docNcontent.DCinjetor;
import yukecm.injecter.rule.RuleInjector;
import yukecm.injecter.workgroup.WorkInjector;
import yukecm.lifecycle.LifeCycleCallable;

public class DesFileCallable extends LifeCycleCallable{
	public DesFileCallable(Doc doc) {
		super(doc);
	}

	@Override
	public Boolean call() throws Exception {
		WorkingGroup working = WorkInjector.getInstance().getWorkRuleList(doc.getWorkId(),OnErrorType.NOTEXIST);
		Rule rule = RuleInjector.getInstance().getRule(working.getDesId(),OnErrorType.NOTEXIST);
		DesRule desRule = (DesRule) rule.getSubRule();
		if(desRule.getStrId() != null) {
			Content content = doc.getContent();
			content.setDocId(doc.getId()); 
			List<Content> contents = DCinjetor.getInstance().getContent(content,OnErrorType.NONE);
			for(Content gContent : contents) {
				if(gContent.getStrId().equals(desRule.getStrId())) {
					Storage storage = StorageController.getInstance().getStorage(gContent.getStrId());
					if(storage.isUsed()) {
						storage.deleteFile(gContent);
						DCinjetor.getInstance().deleteContent(gContent);
						StorageController.getInstance().changeReadOnly(gContent.getStrId(),false);
					}
				}
			}
			List<Content> remains = DCinjetor.getInstance().getContent(content,OnErrorType.NONE);
			if(remains.isEmpty()) {
				DCinjetor.getInstance().delDoc(doc);
			}
		}			
		else {
			ContentController.getInstance().delCon(doc);
			DCinjetor.getInstance().delDoc(doc);
		}
		return true;
	}
}
