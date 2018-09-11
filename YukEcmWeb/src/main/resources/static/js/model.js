const ACL = "ACL";
const ACLMODEL = {"id":"","name":""};

const ACE = "ACE";
const ACEMODEL = {"id":"","childId":"","permissionMap":{"FOLDER":false,"ADDFOLDER":false,"DELFOLDER":false,"UPDFOLDER":false,"GETFOLDER":false,"FILE":false,"ADDFILE":false,"DELFILE":false,"UPDFILE":false,"GETFILE":false,"VERSIONFILE":false}};

const CLUSTER = "CLUSTER";
const CLUSTERMODEL = {"apNum":0,"address":"","state":true,"me":false};

const PIPE = "PIPE";
const PIPEMODEL = {"id":"","name":"","className":"","passOnError":false};

const REPOSITORY = "REPOSITORY";
const REPOMODEL= {"id":"","name":"","putOrderList":{},"getOrderList":{},"pipeMap":{}};
const SUBREPOSTRMODEL={"strId":"","putOrder":0,"getOrder":0};
const SUBREPOPIPEMODEL={"pipeId":"","order":0};

const RULE = "RULE";
const RULEMODEL= {"id":"","name":"","ruleType":"","subRule":"","initRule":"","migRule":"","desRule":""};
const INITRULEMODEL = {"id":"","repoId":"","required":true};
const DELRULEMODEL = {"id":"","strId":"","limitTime":0};
const MIGRULEMODEL = {"id":"","strId":"","fileType":"","copyed":false,"limitTime":-1,"dbUpdate":false,"targetList":[]};
const MIGSUBRULEMODEL = {"serverAddress":"","targetWorkId":"","targetRepoId":""};

const STORAGE = "STORAGE";
const STRMODEL = {"id":"","threadCount":"","name":"","type":"","className":"","address":"","baseDir":"","used":true,"readOnly":false,"tag":"","clip":"","wormId":"","wormPass":""};

const USER = "USER";
const USERMODEL = {"id":"","name":"","parentid":"","password":"","UserManageType":"PUBLIC"};

const GROUP = "GROUP";
const GROUPMODEL = {"id":"","parentId":"","name":""};

const WORKINGGROUP = "WORKINGGROUP";
const WORKINGMODEL = {"id":"","name":"","audit":false,"initList":[],"migId":"","desId":"","tempId":"","tempType":""}

const METASETTING = "METASETTING";
const METASETTINGMODEL = {"name":"","query":"","type":"","map":{}}
const METASETTINGTYPEMODEL = {"keyName":"","order":-1,"type":""};

const LCINFO = "LCINFO";
const LCINFOMODEL = {"id":"","state":"","nextApNum":0,"start":"","end":"","totalTarget":0,"error":0,"excuted":0,"runTime":0,"currentMainAddress":""}

const LCSETTING = "LCSETTING";
const LCSETTINGMODEL = {"id":"","name":"","type":"","workId":"","startingCron":"0 0 12 1 1 ? *","endingCron":"0 0 12 1 1 ? 9999","startingRange":"","endRange":"","thread":1,"loopBack":false}

const DOC = "DOC";
const DOCMODEL = {"id":"","lastVersion":10,"createDate":"","workId":"","checkOut":false,"migId":"0","content":{},"aclId":"","FolderId":"","isDerived":false};

const FOLDER = "FOLDER";
const FOLDERMODEL = {"parentId":"","id":"","name":"","descr":"","aclId":"","isDerived":false};

//header name
//model
const RETID = "RETID";