package yukcommon.dic;

import java.io.File;

public abstract class EtcDic {
	private EtcDic() {}
	//model
	public static final String CREDENTIAL = "CREDENTIAL";
	public static final String USER = "USER";
	public static final String GROUP = "GROUP";
	public static final String ACL = "ACL";
	public static final String ACE = "ACE";
	public static final String STORAGE = "STORAGE";
	public static final String CONTENT = "CONTENT";
	public static final String RULE = "RULE";
	public static final String WORKINGGROUP = "WORKINGGROUP";
	public static final String DOC = "DOC";
	public static final String PIPE = "PIPE";
	public static final String FOLDER = "FOLDER";
	public static final String REPOSITORY = "REPOSITORY";
	public static final String LCINFO = "LCINFO";
	public static final String LCSETTING = "LCSETTING";
	public static final String LCLIST= "LCLIST";
	public static final String RETID = "RETID";
	public static final String MIG = "MIG";
	public static final String META = "META";
	public static final String METASETTING = "METASETTING";
	public static final String CACHE = "CACHE";
	public static final String CLUSTER = "CLUSTER";
	public static final String VERSION = "VERSION";
	//Mount Default ClassName
	public static final String DISKCLASS = "yukecm.mount.DiskAdaptor";
	public static final String CENTERACLASS = "yukecm.mount.CenteraAdaptor";
	public static final String HCPCLASS = "yukecm.mount.HcpAdaptor";
	public static final String NETAPPCLASS = "yukecm.mount.NetappAdaptor";
	//LifeCycle Status
	public static final String NOTSTARTED = "Not Started";
	public static final String STARTED =  "Started";
	public static final String DBSELECT = "Select DB For Target";
	public static final String LCSTARTINGERROR = "Make Target Data Error";
	public static final String LIFECYCLERUN = "Running Cycler";
	public static final String LCDISTRUBUEERROR = "Distribute Has Error";
	public static final String LCSTOPING = "Stoping LifeCycle";
	//etc
	public static final String ACESEP = "^";
	public static final long LAGRELIMIT = (long)1024 * 1024 * 512;
	
	public static final String CHARSET = "UTF-8";
	public static final char PATHSEP = File.separatorChar;

}
