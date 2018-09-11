
create table YukAcl (
AclId varchar2(36) PRIMARY KEY,
AclName varchar2(20) not null unique
);

create table YukUserGroup (
UserId varchar2(36) PRIMARY KEY,
UserParentId varchar2(36),
UserName varchar2(30) not null,
UserPassWord VARCHAR2(100),
UserType varchar2(6) not null,
ManageType varchar2(11)
);

create table YukAce(
AclId varchar2(36) not null,
AceId varchar2(36) not null,
HasPermission VARCHAR2(300) not null
);

CREATE unique INDEX IDX_NOTINSAMEACL ON YukAce(AclId,AceId);

create table YukRepository(
RepoId varchar2(36) PRIMARY KEY,
RepoName varchar2(20) not null unique
);

create table YukStorage(
StorageId varchar2(36) PRIMARY KEY,
StorageName varchar2(20) not null unique,
StorageType VARCHAR2(10) not null,
ThreadCount int not null,
StorageClass VARCHAR2(100) not null,
BaseDir varchar2(36) not null, --if centera "" use
Used varchar2(6) default 'true',
ReadOnly varchar2(6) default 'false',
WormAddress varchar2(500),
WormId Varchar2(30),
WormPass Varchar2(30),
CenteraTag Varchar2(30),
CenteraClip Varchar2(30)
);

create table YukRepoStorage(
RepoId varchar2(36) not null
CONSTRAINT FK_RSRepoId REFERENCES YukRepository(RepoId),
StorageId varchar2(36) not null unique
CONSTRAINT FK_RSStrId REFERENCES YukStorage(StorageId),
PutOrder int not null,
GetOrder int not null
);

CREATE INDEX IDX_FK_RSRepoId ON YukRepoStorage(RepoId);
CREATE unique INDEX IDX_NOTINSAMEREPO ON YukRepoStorage(RepoId,StorageId);

create table YukRule(
RuleId varchar2(36) PRIMARY KEY,
RuleName varchar2(20) not null unique,
RuleType int not null,
RuleCondition varchar2(1000) not null --json
);

create table YukPipe(
Pipeid varchar2(36) primary key,
PipeName varchar2(20) not null unique,
ClassName varchar2(255) not null,
PassOnError varchar2(6)
);

create table YukRepoPipe(
RepoId varchar2(36) not null
CONSTRAINT FK_parentRepoId REFERENCES YukRepository(RepoId),
Pipeid varchar2(36) not null
CONSTRAINT FK_parentPipeId REFERENCES YukPipe(Pipeid),
pipeOrder int not null
);

CREATE INDEX IDX_FK_parentRepoId ON YukRepoPipe(RepoId);
CREATE INDEX IDX_FK_parentPipeId ON YukRepoPipe(Pipeid);
CREATE unique INDEX IDX_NOTINSAMEORDER ON YukRepoPipe(RepoId,pipeOrder);

create table YukWorkingGroup(
WorkId varchar2(36) PRIMARY KEY,
WorkParentId varchar2(36)
CONSTRAINT FK_workParentId REFERENCES YukWorkingGroup(WorkId),
WorkName varchar2(20) not null unique,
WorkAudit varchar2(6) not null,
AclId varchar2(36)
);

--not reqired
CREATE INDEX IDX_FK_parentWorkId ON YukWorkingGroup(WorkParentId);

create table YukWorkingRule(
WorkId varchar2(36) not null 
CONSTRAINT FK_workRuleParentId REFERENCES YukWorkingGroup(WorkId),
RuleId varchar2(36) not null
CONSTRAINT FK_WorkRuleId REFERENCES YukRule(RuleId),
RuleType int not null
);

CREATE INDEX IDX_FK_workParentId ON YukWorkingRule(WorkId);
CREATE INDEX IDX_FK_WorkRuleId ON YukWorkingRule(RuleId);
CREATE unique INDEX IDX_NOTINSAMERULE ON YukWorkingRule(WorkId,RuleId,RuleType);

create table YukMeta(
    MetaName varchar2(36) primary key,
    MetaType varchar2(10),
    MetaQuery varchar2(300),
    QueryOrder varchar2(500) --json
);

create table YukFolder(
    FolderId varchar2(36) primary key,
    ParentId varchar2(36)
    CONSTRAINT FK_FolderId REFERENCES YukFolder(FolderId),
    FolderName varchar2(200),
    FolderDescr varchar2(100),
    AclId varchar2(36),
    IsDerived varchar2(6) not null
);

CREATE INDEX IDX_FK_FolderId ON YukFolder(ParentId);
CREATE unique INDEX IDX_NOTINSAMEFOLDER ON YukFolder(FolderId,ParentId);

create table YukDoc(
    Docid varchar2(36) primary key,
    DocName varchar2(200) not null,
    WorkId varchar2(36) not null
    CONSTRAINT FK_DocWorkId REFERENCES YukWorkingGroup(WorkId),
    createDate varchar2(26) not null,
    DocLastVersion int not null,
    isCheckOut varchar2(6),
    MigId varchar2(36) default '0' not null,
    MetaName varchar2(36) 
    CONSTRAINT FK_DocMetaId REFERENCES YukMeta(MetaName),
    parentFolderId varchar(36)
    CONSTRAINT FK_DocFolderId REFERENCES YukFolder(FolderId),
    aclId varchar(36),
    IsDerived varchar2(6) not null
);

CREATE INDEX IDX_FK_DocWorkId ON YukDoc(WorkId);
CREATE INDEX IDX_FK_DocMetaId ON YukDoc(MetaName);
CREATE INDEX IDX_FK_DocFolderId ON YukDoc(parentFolderId);
CREATE INDEX IDX_DATEFORMIG ON YukDoc(WorkId,createDate);

create table YukContent(
Apnum int not null,
Docid varchar2(36) not null
CONSTRAINT FK_ContentDocId REFERENCES YukDoc(Docid),
DocVersion int not null,
ContentLoc varchar2(200) not null,
ContentName varchar2(30) not null,
ContentSize int not null,
ContentCreator varchar(36) not null
CONSTRAINT FK_ContentUserGroupId REFERENCES YukUserGroup(UserId),
FileType varchar2(36),
StorageId varchar2(36) not null
CONSTRAINT FK_ConStorageId REFERENCES YukStorage(StorageId)
);

CREATE INDEX IDX_FK_ContentDocId ON YukContent(Docid);
CREATE INDEX IDX_FK_ContentCreatorId ON YukContent(ContentCreator);
CREATE INDEX IDX_FK_ContentStrId ON YukContent(StorageId);
CREATE unique INDEX IDX_NOTINSAMESTR ON YukContent(Docid,DocVersion,StorageId);

create table YukLifeCycle(
    LCId varchar2(36) primary key,
    LCName varchar2(36) not null,
    LCType int not null,
    WorkId varchar2(36) not null
    CONSTRAINT FK_WorkId REFERENCES YukWorkingGroup(WorkId),
    StartCron varchar2(36) not null,
    EndCron varchar2(36) not null,
    StartRange varchar2(26),
    EndRange varchar2(26),
    Tcount int not null,
    LoopBack varchar2(6) not null
);

CREATE unique INDEX IDX_FK_WorkId ON YukLifeCycle(WorkId);

create table YukCluster(
  ApNum int primary key,
  address varchar2(50),
  HealthState varchar2(6)
);

create table YukLifeCycleInfo(
    LCId varchar2(36) 
    CONSTRAINT FK_LCInfoId REFERENCES YukLifeCycle(LCId),
    LCState varchar2(36) not null,
    NextRownum int not null,
    LCStart varchar2(36),
    LCEnd varchar2(36),
    TotalCount int,
    errorCount int,
    execCount int
);

CREATE unique INDEX IDX_FK_LCInfoId ON YukLifeCycleInfo(LCId);




