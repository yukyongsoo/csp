package yukecm.controller;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import yukcommon.dic.type.OnErrorType;
import yukcommon.exception.NotExcutedException;
import yukcommon.model.Content;
import yukcommon.model.Doc;
import yukcommon.model.Pair;
import yukcommon.model.Pipe;
import yukcommon.model.Repository;
import yukcommon.model.Rule;
import yukcommon.model.Storage;
import yukcommon.model.WorkingGroup;
import yukcommon.model.fileitem.IFileItem;
import yukcommon.model.fileitem.StreamFileItem;
import yukcommon.model.subrule.InitRule;
import yukcommon.util.LoggerUtil;
import yukecm.config.BaseProperty;
import yukecm.etc.EcmUtil;
import yukecm.etc.StorageUtil;
import yukecm.injecter.docNcontent.DCinjetor;
import yukecm.injecter.workgroup.WorkInjector;


public class ContentController {
	private static class LazyHolder {
	    private static final ContentController con = new ContentController();
	}

	public static ContentController getInstance(){
		return LazyHolder.con;
	}

	private ContentController(){

	}

	public void addCon(Doc doc, IFileItem item) throws Exception   {
		Content content = doc.getContent();
		doc.getContent().setItem(item);
		content.setApNum(BaseProperty.getInstance().apNum);
		content.setDocId(doc.getId());
		content.setDocVersion(10);
		WorkingGroup workRuleList = WorkInjector.getInstance().getWorkRuleList(doc.getWorkId(),OnErrorType.NOTEXIST);
		List<InitRule> initRules = new ArrayList<InitRule>();
		for(String ruleId : workRuleList.getInitList()){
			Rule rule = RuleController.getInstance().getRule(ruleId);
			initRules.add((InitRule)rule.getSubRule());
		}
		for (InitRule initRule : initRules) {
			List<Pipe> pipes = EcmUtil.getRepoPipe(initRule.getRepoId());
			for (Pipe pipe : pipes)
				pipe.getAdt().inbound(content);
			try {
				StorageUtil.addContentToStr(initRule.getRepoId(), content);
				DCinjetor.getInstance().addContent(content);
			} catch (Exception e) {
				if (initRule.isRequired())
					throw e;
				LoggerUtil.warn(getClass(), "add Content Error."
						+ "docid : " + content.getDocId() + "strid : " + content.getStrId(), e);	
			}
		}
	}
	
	public void updCon(Doc doc, IFileItem item) throws Exception {
		Content content = new Content();
		content.setDocId(doc.getId());
		content.setDocVersion(doc.getLastVersion());
		List<Content> list = DCinjetor.getInstance().getVersionContent(content, OnErrorType.NOTEXIST);
		List<Pair<Content, Content>> sameContentList = new ArrayList<Pair<Content,Content>>(); 
		for(Content old : list) {
			Pair<Content, Content> pair = new Pair<Content, Content>();
			pair.set(old, old.copy());
		}
		content.setItem(item);
		//TODO remove below
		/*WorkingGroup workRuleList = WorkInjector.getInstance().getWorkRuleList(doc.getWorkId(),OnErrorType.NOTEXIST);
		List<InitRule> initRules = new ArrayList<InitRule>();
		for(String ruleId : workRuleList.getInitList()){
			Rule rule = RuleController.getInstance().getRule(ruleId);
			initRules.add((InitRule)rule.getSubRule());
		}
		
		for (InitRule initRule : initRules) {
			List<Pipe> pipes = EcmUtil.getRepoPipe(initRule.getRepoId());
			for (Pipe pipe : pipes)
				pipe.getAdt().inbound(content);
			for(Pair<Content, Content> pair : sameContentList) {
				try {
					StorageUtil.addContentToStr(initRule.getRepoId(), content);
					DCinjetor.getInstance().updateContent(content);
					StorageInjector.getInstance().getStorage(id, type)
				} catch (Exception e) {
					if (initRule.isRequired())
						throw e;
					LoggerUtil.warn(getClass(), "add Content Error."
							+ "docid : " + content.getDocId() + "strid : " + content.getStrId(), e);	
				}
			}
		}*/
	}


	public Content getCon(Doc doc) throws UnsupportedEncodingException, SQLException, InterruptedException  {
		Content content = doc.getContent();
		content.setDocId(doc.getId());
		List<Content> contents = DCinjetor.getInstance().getContent(content,OnErrorType.NOTEXIST);
		Map<Content, Pair<Repository, Storage>> finded = StorageUtil.findRepositorys(contents);
		int max = 0;
		Content maxContent = null;
		for (Content nContent : finded.keySet()) {
			if(max < nContent.getDocVersion())
				maxContent = nContent;
		}
		try {
			Pair<Repository, Storage> pair = finded.get(maxContent);
			InputStream stream = pair.getValue().getFile(maxContent);
			maxContent.setItem(new StreamFileItem(stream));
			List<Pipe> pipes = EcmUtil.getRepoPipe(pair.getKey().getId());
			for (Pipe pipe : pipes) {
				pipe.getAdt().outbound(maxContent);
			}
			return maxContent; 
		} catch (Exception e) {
			LoggerUtil.warn(getClass(), "can't get file from docid : " + maxContent.getDocId() + "strid : " 
					+ maxContent.getStrId() + ".DocVersion :" + maxContent.getDocVersion(), e);
		}
		throw new NotExcutedException("File and Storage Founded. but can't get File. Abnormal State");
	}

	public void delCon(Doc doc) throws UnsupportedEncodingException, SQLException, InterruptedException   {
		Content content = doc.getContent();
		content.setDocId(doc.getId());
		List<Content> contents = DCinjetor.getInstance().getContent(content,OnErrorType.NOTEXIST);
		for (Content gContent : contents) {
			Storage storage = StorageController.getInstance().getStorage(gContent.getStrId());
			if(storage != null && storage.isUsed()) {
				storage.deleteFile(gContent);
				DCinjetor.getInstance().deleteContent(gContent);
				StorageController.getInstance().changeReadOnly(gContent.getStrId(),false);
			}
		}
		DCinjetor.getInstance().getContent(content,OnErrorType.EXIST);
	}

	
}
