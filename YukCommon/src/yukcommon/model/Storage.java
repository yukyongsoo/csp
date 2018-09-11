package yukcommon.model;

import java.io.InputStream;

import com.google.gson.annotations.SerializedName;

import yukcommon.adaptor.StorageAdaptor;
import yukcommon.dic.EtcDic;
import yukcommon.dic.type.StorageType;
import yukcommon.exception.NotNullAllowedException;
import yukcommon.exception.NotSupportException;
import yukcommon.exception.WrongOperationException;

public class Storage extends AbsModel{
	private String id = "";
	private String name = "";
	@SerializedName("type")
	private StorageType type;
	private String className = "";
	private String baseDir = "";
	private boolean used = true;
	private boolean readOnly = false;
	private int threadCount = 1;
	private transient StorageAdaptor adt;
	
	//centera & hcp
	private String address = "";
	//centera
	private String tag = "";
	private String clip = "";
	//hcp,netapp
	private String wormId ="";
	private String wormPass = "";
	

	public int getThreadCount() {
		if(threadCount < 1)
			threadCount = 1;
		return threadCount;
	}
	
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	
	
	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	public String getId() {
		if(id.isEmpty())
			throw new WrongOperationException("storage id is null");
		return id;
	}

	public void setId(String id) {
		if(id == null || id.isEmpty())
			throw new NotNullAllowedException("storage id can't be null");
		this.id = id;
	}

	public String getName() {
		if(name.isEmpty())
			throw new WrongOperationException("storage name is null");
		return name;
	}

	public void setName(String name) {
		if(name == null || name.isEmpty())
			throw new NotNullAllowedException("storage name can't be null");
		this.name = name;
	}

	public StorageType getType() {
		if(type == null)
			throw new WrongOperationException("storage type is null");
		return type;
	}

	public void setType(StorageType type) {
		if(type == null)
			throw new NotNullAllowedException("storage type can't be null");
		if(!(StorageType.CENTERA == type || StorageType.DISK == type
				|| StorageType.HCP == type|| StorageType.NETAPP == type))
			throw new NotSupportException("Not Supported Disk Type");
		this.type = type;
	}

	public String getClassName() {
		if(className.isEmpty())
			setClass();
		return className;
	}

	public void setClassName(String className) {
		if(className == null || className.isEmpty())
			throw new NotNullAllowedException("storage className can't be null");
		this.className = className;
	}
	
	public String getBaseDir() {
		if(StorageType.DISK == getType() ||  StorageType.NETAPP == getType()
				|| StorageType.HCP == getType()) {
			if(baseDir.isEmpty())
				throw new WrongOperationException("storage baseDir is null");
		}
		return baseDir;
	}

	public void setBaseDir(String baseDir) {
		if(baseDir == null || baseDir.isEmpty())
			throw new NotNullAllowedException("storage baseDir can't be null");
		this.baseDir = baseDir;
	}

	public String getAddress() {
		if(StorageType.HCP == getType() ||  StorageType.NETAPP == getType()
				|| StorageType.CENTERA == getType()){
			if(address.isEmpty())
				throw new WrongOperationException("storage address is null");
		}
		return address;
	}

	public void setAddress(String address) {
		if(StorageType.HCP == getType() ||  StorageType.NETAPP == getType()
				|| StorageType.CENTERA == getType()){
			if(address == null || address.isEmpty())
				throw new NotNullAllowedException("storage address can't be null");
		}
		this.address = address;
	}

	public String getTag() {
		if(StorageType.CENTERA == getType()){
			if(tag.isEmpty())
				throw new WrongOperationException("storage tag is null");
		}
		return tag;
	}

	public void setTag(String tag) {
		if(StorageType.CENTERA == getType()){
			if(tag == null || tag.isEmpty())
				throw new NotNullAllowedException("storage tag can't be null");
		}
		this.tag = tag;
	}

	public String getClip() {
		if(StorageType.CENTERA == getType()){
			if(clip.isEmpty())
				throw new WrongOperationException("storage clip is null");
		}
		return clip;
	}

	public void setClip(String clip) {
		if(StorageType.CENTERA == getType()){
			if(clip == null || clip.isEmpty())
				throw new NotNullAllowedException("storage clip can't be null");
		}
		this.clip = clip;
	}

	public String getWormId() {
		if( StorageType.HCP == getType()  || StorageType.NETAPP == getType()){
			if(wormId.isEmpty())
				throw new WrongOperationException("storage wormId is null");
		}
		return wormId;
	}

	public void setWormId(String wormId) {
		if( StorageType.HCP == getType()  || StorageType.NETAPP == getType()){
			if(wormId == null || wormId.isEmpty())
				throw new NotNullAllowedException("storage wormId can't be null");
		}
		this.wormId = wormId;
	}

	public String getWormPass() {
		if( StorageType.HCP == getType()  || StorageType.NETAPP == getType()){
			if(wormPass.isEmpty())
				throw new WrongOperationException("storage wormPass is null");
		}
		return wormPass;
	}

	public void setWormPass(String wormPass) {
		if( StorageType.HCP == getType()  || StorageType.NETAPP == getType()){
			if(wormPass == null || wormPass.isEmpty())
				throw new NotNullAllowedException("storage wormPass can't be null");
		}
		this.wormPass = wormPass;
	}

	public void setAdt(StorageAdaptor adt)  {
		if(adt == null)
			throw new NotNullAllowedException("StorageAdaptor is null." + id);
		this.adt = adt;
	}
	
	public StorageAdaptor getAdt() {
		if(adt == null)
			throw new NotNullAllowedException("StorageAdaptor is null." + id);
		return adt;
	}
	
	public void addFile(Content content)  {
		if(content == null)
			throw new NotNullAllowedException("storage added file content can't be null");
		getAdt().addFile(content);
	}
	
	public void deleteFile(Content content) {
		if(content == null)
			throw new NotNullAllowedException("storage deleted file content can't be null");
		getAdt().deleteFile(content);
	}
	
	public InputStream getFile(Content content) {
		if(content == null)
			throw new NotNullAllowedException("storage get file content can't be null");
		return getAdt().getFile(content);
	}

	public void stopSizeChecker()  {
		getAdt().stopSizeChecker();
	}
	
	public void stopExcutor()  {
		getAdt().stopExcutor();
	}
	
	private void setClass() {
		if (className == null || className.isEmpty()) {
			if (StorageType.DISK == getType())
				setClassName(EtcDic.DISKCLASS);
			else if (StorageType.CENTERA == getType())
				setClassName(EtcDic.CENTERACLASS);
			else if (StorageType.NETAPP == getType())
				setClassName(EtcDic.NETAPPCLASS);
			else if (StorageType.HCP == getType())
				setClassName(EtcDic.HCPCLASS);
		}
	}

	@Override
	public String toString() {		
		return "Current Storage Data: id = " + id + ".name=" + name + ".type=" + type.toString() + 
		".className=" + className + ".baseDir=" + baseDir + ".used=" + used + ".readOnly=" + readOnly
		+ ".threadCount=" + threadCount;
	}
}
