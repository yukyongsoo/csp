package yukcommon.model;

import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.WrongOperationException;
import yukcommon.model.meta.MetaData;


public class Doc extends AbsModel{
	private String id = "";
	private int lastVersion = 10;
	private String name = "";
	private String createDate = "";
	private String workId = "";
	private boolean checkOut = false;
	private String migId = "0";
	private Content content = new Content();
	private MetaData meta;
	private String aclId = "";
	private String folderId = "";
	private boolean isDerived = true;
	
	public String getName() {
		if(name.isEmpty())
			throw new WrongOperationException("document name is empty");
		return name;
	}
	public void setName(String name) {
		if(name == null || name.isEmpty())
			throw new NotNullAllowedException("document name can't be empty");
		this.name = name;
	}
	
	public String getId() {
		if(id.isEmpty())
			throw new WrongOperationException("document id is empty");
		return id;
	}
	public void setId(String id) {
		if(id == null || id.isEmpty())
			throw new NotNullAllowedException("document id can't be empty");
		this.id = id;
	}
	public int getLastVersion() {
		return lastVersion;
	}
	public void setLastVersion(int lastVersion) {
		if(lastVersion < 10)
			throw new WrongOperationException("document last version can't be under 1.0");
		this.lastVersion = lastVersion;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		if(createDate == null || createDate.isEmpty())
			throw new WrongOperationException("document createDate can't be empty");
		this.createDate = createDate;
	}
	public String getWorkId() {
		if(workId.isEmpty())
			throw new WrongOperationException("document workId is empty");
		return workId;
	}
	public void setWorkId(String workId) {
		if(workId == null || workId.isEmpty())
			throw new NotNullAllowedException("document workId can't be empty");
		this.workId = workId;
	}
	public boolean isCheckOut() {
		return checkOut;
	}
	public void setCheckOut(boolean locked) {
		this.checkOut = locked;
	}
	public String getMigId() {
		return migId;
	}
	public void setMigId(String migId) {
		if(migId == null || migId.isEmpty())
			throw new NotNullAllowedException("document migId can't be empty");
		this.migId = migId;
	}
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		if(content == null)
			throw new NotNullAllowedException("document content can't be empty");
		this.content = content;
	}
	public MetaData getMeta() {
		if(meta == null)
			throw new WrongOperationException("document meta is empty");
		return meta;
	}
	public void setMeta(MetaData meta) {
		if(meta == null)
			throw new NotNullAllowedException("document meta Data can't be empty");
		this.meta = meta;
	}
	public String getAclid() {
		return aclId;
	}
	
	public void setAclid(String aclId) {
		this.aclId = aclId;
		if(aclId == null || aclId.isEmpty())
			this.isDerived = true;
		else
			this.isDerived = false;
	}
	public String getFolderId() {
		return folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	
	public String getMetaName() {
		if(meta == null)
			return "";
		return meta.getMetaName();
	}
	public void setMetaName(String metaName) {
		if(metaName == null || metaName.isEmpty())
			return;
		MetaData meta = new MetaData();
		meta.setMetaName(metaName);
		setMeta(meta);
	}
	
	public boolean isDerived() {
		return isDerived;
	}

	public void setDerived(boolean isDerived) {
		this.isDerived = isDerived;
	}

	@Override
	public String toString() {		
		return "Current Doc Data: id = " + id + ".lastVersion=" + lastVersion + ".name=" + name +
				".createDate=" + createDate + ".workId=" + workId + ".checkOut=" + checkOut+
				".migId=" + migId + ".aclId=" + aclId +  ".folderId="+ folderId + "isDerived" + isDerived;
	}
}
