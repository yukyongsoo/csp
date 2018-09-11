package yukcommon.dic;

public abstract class UriDic {
	private UriDic() {}
	//user
	public static final String ADDUSER = "/user/adduser";
	public static final String DELUSER = "/user/deluser";
	public static final String UPDUSER = "/user/upduser";
	public static final String UPDUSERPARENT = "/user/upduserparent";
	public static final String GETUSER = "/user/getuser";
	public static final String GETGROUPUSER = "/user/getgroupuser";
	public static final String ADDGROUP = "/user/addgroup";
	public static final String DELGROUP = "/user/delgroup";
	public static final String UPDGROUP = "/user/updgroup";
	public static final String GETGROUP = "/user/getgroup";
	public static final String GETGROUPLIST = "/user/getgrouplist";
	public static final String GETGROUPCHILDLIST ="/user/getgroupchildlist";
	public static final String CHECKUSER = "/user/checkuser";
	//acl
	public static final String ADDACL = "/acl/addacl";
	public static final String DELACL = "/acl/delacl";
	public static final String UPDACL = "/acl/updacl";
	public static final String GETACL = "/acl/getacl";
	public static final String ADDACE = "/acl/addace";
	public static final String DELACE = "/acl/delace";
	public static final String UPDACE = "/acl/updace";
	public static final String GETACE = "/acl/getace";
	//storage
	public static final String ADDSTORAGE = "/storage/addstorage";
	public static final String DELSTORAGE = "/storage/delstorage";
	public static final String UPDSTORAGE = "/storage/updstorage";
	public static final String GETSTORAGE = "/storage/getstorage";
	//rule
	public static final String ADDRULE = "/rule/addrule";
	public static final String DELRULE = "/rule/delrule";
	public static final String UPDRULE = "/rule/updrule";
	public static final String GETRULE = "/rule/getrule";
	//working group
	public static final String ADDWORK = "/working/addgroup";
	public static final String DELWORK = "/working/delgroup";
	public static final String UPDWORK = "/working/updgroup";
	public static final String GETWORK = "/working/getgroup";
	//working rule
	public static final String ADDWORKRULE = "/working/addgrouprule";
	public static final String DELWORKRULE = "/working/delgrouprule";
	public static final String GETWORKRULE = "/working/getgrouprule";
	//pipe
	public static final String ADDPIPE = "/pipe/addpipe";
	public static final String DELPIPE = "/pipe/delpipe";
	public static final String UPDPIPE = "/pipe/updpipe";
	public static final String GETPIPE = "/pipe/getpipe";
	//doc
	public static final String ADDDOC = "/document/adddoc";
	public static final String ADDDOCTOFOLDER = "/document/adddoctofolder";
	public static final String DELDOC = "/document/deldoc";
	public static final String GETDOC = "/document/getdoc";
	public static final String GETFOLDERDOCLIST = "/document/getfolderdoclist";
	public static final String UPDDOC = "/document/upddoc";
	//version
	public static final String CHECKOUT = "/version/checkout";
	public static final String CHECKIN = "/version/checkin";
	public static final String REVERSEVERSION = "/version/reverseversion";
	public static final String CANCLECHECKOUT = "/version/canclecheckout";
	//Folder
	public static final String ADDFOLDER = "/folder/addfolder";
	public static final String DELFOLDER = "/folder/delfolder";
	public static final String GETFOLDER = "/folder/getfolder";
	public static final String GETFOLDERLIST = "/folder/getfolderlist";
	public static final String GETFOLDERCHILDLIST = "/folder/getfolderchildlist";
	public static final String UPDFOLDER = "/folder/updfolder";
	//repository
	public static final String ADDREPOSITORY = "/repository/addrepository";
	public static final String DELREPOSITORY = "/repository/delrepository";
	public static final String GETREPOSITORY = "/repository/getrepository";
	public static final String UPDREPOSITORY = "/repository/updrepository";
	//add storage to repository
	public static final String ADDREPOSTR = "/repository/addrepostr";
	public static final String DELREPOSTR = "/repository/delrepostr";
	public static final String GETREPOSTR = "/repository/getrepostr";
	//add pipe to rule
	public static final String ADDREPOPIPE = "/repository/addrepopipe";
	public static final String DELREPOPIPE = "/repository/delrepopipe";
	public static final String UPDREPOPIPE = "/repository/updrepopipe";
	public static final String GETREPOPIPE = "/repository/getrepopipe";
	//mig
	public static final String ADDLCSCH = "/lifecycle/addlcsch";
	public static final String DELLCSCH = "/lifecycle/dellcsch";
	public static final String GETLCSCH = "/lifecycle/getlcsch";
	public static final String GETLCINFO = "/lifecycle/getlcinfo";
	public static final String STARTLC = "/lifecycle/startlc";
	public static final String STOPLC = "/lifecycle/stoplc";
	//inner Use
	public static final String STARTLCNOTREPL = "/innerlifecycle/startlcnotrepl";
	public static final String STOPLCNOTREPL = "/innerlifecycle/stoplcnotrepl";
	public static final String MIGFILE = "/innerlifecycle/migfile";
	//meta
	public static final String ADDMETASETTING = "/meta/addmetasetting";
	public static final String DELMETASETTING = "/meta/delmetasetting";
	public static final String GETMETASETTING = "/meta/getmetasetting";
	public static final String GETMETASETTINGLIST = "/meta/getmetasettinglist";
	public static final String ADDMETASUBSETTING = "/meta/addmetasubsetting";
	public static final String DELMETASUBSETTING = "/meta/delmetasubsetting";
	public static final String ADDDOCMETA = "/meta/adddocmeta";
	public static final String DELDOCMETA = "/meta/deldocmeta";
	public static final String GETDOCMETA = "/meta/getdocmeta";
	//cluster
	public static final String ADDCLUSTER = "/cluster/addcluster";
	public static final String DELCLUSTER = "/cluster/delcluster";
	public static final String UPDCLUSTER = "/cluster/updcluster";
	public static final String GETCLUSTER = "/cluster/getcluster";
	//inner control
	public static final String SYNCCACHE = "/cache/synccache";
	public static final String REINITCACHE = "/cache/reinit";
	
	

	

}
