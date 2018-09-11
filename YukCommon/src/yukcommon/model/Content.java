package yukcommon.model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.NotSupportException;
import yukcommon.exception.WrongOperationException;
import yukcommon.model.fileitem.IFileItem;

public class Content extends AbsModel{
	private int apNum = 0;
	private String docId = "";
	private int docVersion = 10;
	//not need to be utf-8
	private String type = "";
	private String strId = "";
	private String loc = "";
	private long size = 0;
	private String name = "";
	private String creator = "";
	private transient IFileItem item;

	public IFileItem getItem() {
		if(item == null)
			throw new WrongOperationException("content item is empty");
		return item;
	}

	public void setItem(IFileItem item)  {
		if(item == null)
			throw new NotNullAllowedException("content file can't be empty");
		this.item = item;
	}

	public void setLoc(String loc) {
		if(loc == null || loc.isEmpty())
			throw new NotNullAllowedException("content location can't be empty");
		this.loc = loc;
	}

	public void setSize(long size) {
		if(size < 1)
			throw new NotSupportException("content size 0 is not allowed");
		this.size = size;
	}

	public String getLoc() {
		if(loc.isEmpty())
			throw new WrongOperationException("file location is empty");
		return loc;
	}

	public long getSize() {
		if(size < 1)
			throw new WrongOperationException("file size lower than 1 byte");
		return size;
	}

	public String getName() throws UnsupportedEncodingException {
		return URLDecoder.decode(getEncodedName(),"UTF-8");
	}

	public String getEncodedName() {
		if(name.isEmpty())
			throw new WrongOperationException("content name is empty");
		return name;
	}

	public void setName(String name) throws UnsupportedEncodingException {
		if(name == null || name.isEmpty())
			throw new NotNullAllowedException("content name can't be empty");
		this.name = URLEncoder.encode(name,"UTF-8");
	}
	
	public int getApNum() {
		return apNum;
	}

	public void setApNum(int apNum) {
		if(apNum < 0)
			throw new WrongOperationException("content apnum can't be minus");
		this.apNum = apNum;
	}

	public String getDocId() {
		if(docId.isEmpty())
			throw new WrongOperationException("content docId is empty");
		return docId;
	}

	public void setDocId(String docId) {
		if(docId == null || docId.isEmpty())
			throw new NotNullAllowedException("content docId can't be empty");
		this.docId = docId;
	}

	public int getDocVersion() {
		return docVersion;
	}

	public void setDocVersion(int docVersion) {
		if(docVersion < 10)
			throw new WrongOperationException("doc version not allowed only miner version");
		this.docVersion = docVersion;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if(type == null)
			return;
		this.type = type;
	}

	public String getStrId() {
		if(strId.isEmpty())
			throw new WrongOperationException("content strId is empty");
		return strId;
	}

	public void setStrId(String strId) {
		if(strId == null || strId.isEmpty())
			throw new NotNullAllowedException("content strId can't be empty");
		this.strId = strId;
	}

	public Content copy() {
		Content con = new Content();
		con.apNum = this.apNum;
		con.docId = this.docId;
		con.docVersion = this.docVersion;
		con.type = this.type;
		con.creator = this.creator;
		con.strId = this.strId;
		con.loc = this.loc;
		con.size = this.size;
		con.name = this.name;
		return con;
	}

	public String getCreator() {
		if(creator.isEmpty())
			throw new WrongOperationException("content creator is null");
		return creator;
	}

	public void setCreator(String creator) {
		if(creator == null || creator.isEmpty())
			throw new NotNullAllowedException("content creator can't be null");
		this.creator = creator;
	}
	
	@Override
	public String toString() {
		return "Current Content Data: apNum = " + apNum + ".docId=" + docId +
				".docVersion=" + docVersion + ".type=" + type + ".strId=" + strId +
				".loc=" + loc + ".size=" + size + ".name=" + name +
				".creator=" + creator + ".item=" + item;
	}
}
