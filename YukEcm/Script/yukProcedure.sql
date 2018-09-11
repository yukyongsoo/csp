CREATE OR REPLACE PACKAGE YukCusor AS
    TYPE REFCUR IS REF CURSOR;
END YukCusor;
/

CREATE OR REPLACE PROCEDURE YukAddUser(ids IN VARCHAR2,names in varchar2,pass in VARCHAR2,iparent in varchar2,iType in varchar2) AS 
    BEGIN 
       Insert Into YukUserGroup(UserId,UserName,UserPassWord,UserParentId,UserType,ManageType) VALUES(ids,names,pass,iparent,'USER',iType);
END YukAddUser;
/

CREATE OR REPLACE PROCEDURE YukDelUser(userids IN VARCHAR2) AS 
    BEGIN 
      delete from YukUserGroup where UserId = userids and UserType='USER';
END YukDelUser;
/

CREATE OR REPLACE PROCEDURE YukUpdUser(ids IN VARCHAR2,names in varchar2,pass in VARCHAR2,iparent in varchar2,iType in varchar2) AS 
    BEGIN 
       update YukUserGroup set UserName=names,UserPassWord=pass,UserParentId=iparent,ManageType=iType where UserId = ids and UserType='USER';
END YukUpdUser;
/

CREATE OR REPLACE PROCEDURE YukGetUser(ids IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select UserId,UserName,UserPassWord,UserParentId,ManageType from YukUserGroup where UserId = ids and UserType='USER';
END YukGetUser;
/

CREATE OR REPLACE PROCEDURE YukGetUserList(RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select UserId,UserName,UserPassWord,UserParentId,ManageType from YukUserGroup where UserType='USER';
END YukGetUserList;
/

CREATE OR REPLACE PROCEDURE YukGetGroupUserList(iParentId in varchar2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select UserId,UserName,UserPassWord,UserParentId,ManageType from YukUserGroup where UserParentId = iParentId and UserType='USER';
END YukGetGroupUserList;
/

CREATE OR REPLACE PROCEDURE YukGetGroupList(RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select UserId,UserName,UserParentId from YukUserGroup where UserType='GROUP' and UserParentId is null;
END YukGetGroupList;
/

CREATE OR REPLACE PROCEDURE YukGetAllGroupList(RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select UserId,UserName,UserParentId from YukUserGroup where UserType='GROUP';
END YukGetAllGroupList;
/

CREATE OR REPLACE PROCEDURE YukGetGroupChildList(iParentId in varchar2, RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select UserId,UserName,UserParentId from YukUserGroup where UserType='GROUP' and UserParentId = iParentId;
END YukGetGroupChildList;
/

CREATE OR REPLACE PROCEDURE YukGetGroup(ids IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select UserId,UserName,UserParentId from YukUserGroup where UserId = ids and UserType='GROUP';
END YukGetGroup;
/

CREATE OR REPLACE PROCEDURE YukAddGroup(ids IN VARCHAR2,names in varchar2,iparent in varchar2) AS 
    BEGIN 
       Insert Into YukUserGroup(UserId,UserName,UserParentId,UserType) VALUES(ids,names,iparent,'GROUP');
END YukAddGroup;
/

CREATE OR REPLACE PROCEDURE YukDelGroup(userids IN VARCHAR2) AS 
    BEGIN 
      delete from YukUserGroup where UserId = userids and UserType='GROUP';
END YukDelGroup;
/

CREATE OR REPLACE PROCEDURE YukUpdGroup(ids IN VARCHAR2,names in varchar2,iparent in varchar2) AS 
    BEGIN 
       update YukUserGroup set UserName=names,UserParentId=iparent where UserId = ids and UserType='GROUP';
END YukUpdGroup;
/

CREATE OR REPLACE PROCEDURE YukAddAcl(acli in varchar2,acln IN VARCHAR2) AS 
    BEGIN 
       Insert Into YukAcl(AclId,AclName) VALUES(acli,acln);
END YukAddAcl;
/

CREATE OR REPLACE PROCEDURE YukGetAcl(aclids IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select AclId,AclName from YukAcl where AclId = aclids;
END YukGetAcl;
/

CREATE OR REPLACE PROCEDURE YukGetAclByName(aclnames IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select AclId,AclName from YukAcl where AclName = aclnames;
END YukGetAclByName;
/

CREATE OR REPLACE PROCEDURE YukGetAclList(RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select AclId,AclName from YukAcl;
END YukGetAclList;
/

CREATE OR REPLACE PROCEDURE YukDelAcl(aclids IN VARCHAR2) AS 
    BEGIN 
      delete from YukAcl where AclId = aclids;
END YukDelAcl;
/

CREATE OR REPLACE PROCEDURE YukUpdAcl(aclids IN VARCHAR2,aclnames IN VARCHAR2) AS 
    BEGIN 
       update YukAcl set AclName=aclnames where AclId = aclids;
END YukUpdAcl;
/

CREATE OR REPLACE PROCEDURE YukGetAce(aclids IN VARCHAR2,aceIds IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
     open RC for
      select AclId,AceId,HasPermission from YukAce where AclId = aclids and AceId = aceIds;
END YukGetAce;
/

CREATE OR REPLACE PROCEDURE YukGetAceList(aclIds IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
     open RC for
      select AclId,AceId,HasPermission from YukAce where AclId = aclIds;
END YukGetAceList;
/

CREATE OR REPLACE PROCEDURE YukGetAllAceList(RC OUT YukCusor.REFCUR) AS 
    BEGIN 
     open RC for
      select AclId,AceId,HasPermission from YukAce;
END YukGetAllAceList;
/

CREATE OR REPLACE PROCEDURE YukAddAce(aclIds IN VARCHAR2,aceIds IN VARCHAR2,iPermission in varchar2) AS 
    BEGIN 
     Insert Into YukAce(AclId,AceId,HasPermission) VALUES(aclIds,aceIds,iPermission);
END YukAddAce;
/

CREATE OR REPLACE PROCEDURE YukDelAce(aclIds IN VARCHAR2,aceIds IN VARCHAR2) AS 
    BEGIN 
      delete from YukAce where AclId=aclIds and AceId = aceIds;
END YukDelAce;
/

CREATE OR REPLACE PROCEDURE YukUpdAce(aclIds IN VARCHAR2,aceIds IN VARCHAR2,iPermission in varchar2) AS 
    BEGIN 
       update YukAce set AclId=aclIds,HasPermission=iPermission where AceId = aceIds and AclId= aclIds;
END YukUpdAce;
/

CREATE OR REPLACE PROCEDURE YukAddStorage(strid IN VARCHAR2,strname in varchar2,strtype in VARCHAR2,strclass in varchar2, strdir in VARCHAR2,strUse in varchar2,strRead in varchar2,
    straddr in varchar2, strwid in varchar2, strwpass in varchar2, strctag in varchar2, strclip in varchar2,iThread in int) AS
    BEGIN 
       Insert Into YukStorage(StorageId,StorageName,StorageType,StorageClass,BaseDir,Used,ReadOnly,WormAddress,WormId,WormPass,CenteraTag,CenteraClip,ThreadCount) 
        VALUES(strid,strname,strtype,strclass,strdir,strUse,strRead,straddr,strwid,strwpass,strctag,strclip,iThread);
END YukAddStorage;
/

CREATE OR REPLACE PROCEDURE YukDelStorage(strid IN VARCHAR2) AS 
    BEGIN 
      delete from YukStorage where StorageId = strid;
END YukDelStorage;
/


CREATE OR REPLACE PROCEDURE YukUpdStorage(strid IN VARCHAR2,strname in varchar2,strtype in VARCHAR2,strclass in varchar2,strdir in VARCHAR2,strUse in varchar2,strRead in varchar2,
    straddr in varchar2, strwid in varchar2, strwpass in varchar2, strctag in varchar2, strclip in varchar2,iThread in int) AS 
    BEGIN 
       update YukStorage set StorageName=strname,StorageType=strtype,StorageClass=strclass,
       BaseDir=strdir,Used=strUse,ReadOnly=strRead,WormAddress=straddr,WormId=strwid,WormPass=strwpass,CenteraTag=strctag,
       CenteraClip=strclip,ThreadCount=iThread  where StorageId = strid;
END YukUpdStorage;
/

CREATE OR REPLACE PROCEDURE YukGetStorage(ids IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select StorageId,StorageName,StorageType,StorageClass,BaseDir,Used,ReadOnly,WormAddress,WormId,WormPass,CenteraTag,CenteraClip,ThreadCount from YukStorage where StorageId = ids;
END YukGetStorage;
/

CREATE OR REPLACE PROCEDURE YukGetStorageByName(names IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select StorageId,StorageName,StorageType,StorageClass,BaseDir,Used,ReadOnly,WormAddress,WormId,WormPass,CenteraTag,CenteraClip,ThreadCount from YukStorage where StorageName = names;
END YukGetStorageByName;
/



CREATE OR REPLACE PROCEDURE YukGetStorageList(RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select StorageId,StorageName,StorageType,StorageClass,BaseDir,Used,ReadOnly,WormAddress,WormId,WormPass,CenteraTag,CenteraClip,ThreadCount from YukStorage;
END YukGetStorageList;
/

CREATE OR REPLACE PROCEDURE YukAddRepo(rId IN VARCHAR2,rName in varchar2) AS 
    BEGIN 
       Insert Into YukRepository(RepoId,RepoName) VALUES(rId,rName);
END YukAddRepo;
/

CREATE OR REPLACE PROCEDURE YukUpdRepo(rId IN VARCHAR2,rName in varchar2) AS 
    BEGIN 
      update YukRepository set RepoName=rName where RepoId = rId;
END YukUpdRepo;
/

CREATE OR REPLACE PROCEDURE YukDelRepo(rId IN VARCHAR2) AS 
    BEGIN 
        delete from YukRepository where RepoId = rId;
END YukDelRepo;
/

CREATE OR REPLACE PROCEDURE YukgetRepoList(RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select RepoId,RepoName from YukRepository;
END YukgetRepoList;
/

CREATE OR REPLACE PROCEDURE YukGetRepo(ids IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select RepoId,RepoName from YukRepository where RepoId = ids;
END YukGetRepo;
/

CREATE OR REPLACE PROCEDURE YukGetRepoByName(names IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
       select RepoId,RepoName from YukRepository where RepoName = names;
END YukGetRepoByName;
/

CREATE OR REPLACE PROCEDURE YukGetRepoStorage(rId IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select RepoId,StorageId,PutOrder,GetOrder from YukRepoStorage where RepoId = rId;
END YukGetRepoStorage;
/

CREATE OR REPLACE PROCEDURE YukAddRepoStorage(rId IN VARCHAR2,strId in varchar2, pOrder in int, gOrder in int) AS 
    BEGIN       
        Insert Into YukRepoStorage(RepoId,StorageId,PutOrder,GetOrder) VALUES(rId,strId,pOrder,gOrder);
END YukAddRepoStorage;
/

CREATE OR REPLACE PROCEDURE YukDelRepoStorage(rId IN VARCHAR2,strId in varchar2) AS 
    BEGIN 
      delete from YukRepoStorage where RepoId = rId and StorageId = strId;
END YukDelRepoStorage;
/

CREATE OR REPLACE PROCEDURE YukFindRepoStorage(strId IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select RepoId,StorageId,PutOrder,GetOrder from YukRepoStorage where StorageId = strId;
END YukFindRepoStorage;
/

CREATE OR REPLACE PROCEDURE YukAddRule(rulid IN VARCHAR2,rulname in varchar2,rultype in int ,rulcon in varchar2) AS 
    BEGIN 
       Insert Into YukRule(RuleId,RuleName,RuleType,RuleCondition) 
        VALUES(rulid,rulname,rultype,rulcon);
END YukAddRule;
/

CREATE OR REPLACE PROCEDURE YukUpdRule(rulid IN VARCHAR2,rulname in varchar2,rultype in int,rulcon in varchar2) AS 
    BEGIN 
      update YukRule set RuleName=rulname,RuleType=rultype,RuleCondition=rulcon where RuleId = rulid;
END YukUpdRule;
/

CREATE OR REPLACE PROCEDURE YukDelRule(ids IN VARCHAR2) AS 
    BEGIN 
        delete from YukRule where RuleId = ids;
END YukDelRule;
/

CREATE OR REPLACE PROCEDURE YukGetRule(ids IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select RuleId,RuleName,RuleType,RuleCondition from YukRule where RuleId = ids;
END YukGetRule;
/

CREATE OR REPLACE PROCEDURE YukGetRuleByName(names IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select RuleId,RuleName,RuleType,RuleCondition from YukRule where RuleName = names;
END YukGetRuleByName;
/

CREATE OR REPLACE PROCEDURE YukGetRuleList(RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select RuleId,RuleName,RuleType,RuleCondition from YukRule;
END YukGetRuleList;
/

CREATE OR REPLACE PROCEDURE YukGetRepoPipe(ids IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select RepoId,Pipeid,pipeOrder from YukRepoPipe where RepoId = ids order by pipeOrder asc;
END YukGetRepoPipe;
/

CREATE OR REPLACE PROCEDURE YukAddRepoPipe(ids IN VARCHAR2,pipids in varchar2, pOrder in int) AS 
    BEGIN       
        Insert Into YukRepoPipe(RepoId,Pipeid,pipeOrder) VALUES(ids,pipids,pOrder);
END YukAddRepoPipe;
/

CREATE OR REPLACE PROCEDURE YukDelRepoPipe(ids IN VARCHAR2,pipids in varchar2) AS 
    BEGIN 
      delete from YukRepoPipe where RepoId = ids and Pipeid = pipids;
END YukDelRepoPipe;
/

CREATE OR REPLACE PROCEDURE YukGetWorking(ids IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select WorkId,WorkName,WorkAudit from YukWorkingGroup where WorkId = ids;
END YukGetWorking;
/

CREATE OR REPLACE PROCEDURE YukGetWorkList(RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
      select WorkId,WorkName,WorkAudit from YukWorkingGroup;
END YukGetWorkList;
/

CREATE OR REPLACE PROCEDURE YukGetWorkingByName(names IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
      open RC for
       select WorkId,WorkName,WorkAudit from YukWorkingGroup where WorkName = names;
END YukGetWorkingByName;
/


CREATE OR REPLACE PROCEDURE YukAddWorking(wrkid IN VARCHAR2,wrkname in varchar2,wrkaudit in varchar2) AS 
    BEGIN 
       Insert Into YukWorkingGroup(WorkId,WorkName,WorkAudit) VALUES(wrkid,wrkname,wrkaudit);
END YukAddWorking;
/

CREATE OR REPLACE PROCEDURE YukUpdWorking(wrkid IN VARCHAR2,wrkname in varchar2,wrkaudit in varchar2) AS 
    BEGIN 
      update YukWorkingGroup set WorkName=wrkname,WorkAudit=wrkaudit where WorkId = wrkid;
END YukUpdWorking;
/

CREATE OR REPLACE PROCEDURE YukDelWorking(ids IN VARCHAR2) AS 
    BEGIN 
        delete from YukWorkingGroup where WorkId = ids;
END YukDelWorking;
/

CREATE OR REPLACE PROCEDURE YukDelWorkingRule(wrkid IN VARCHAR2,rulid in varchar2) AS 
    BEGIN 
        delete from YukWorkingRule where WORKID = wrkid and RULEID = rulid;
END YukDelWorkingRule;
/

CREATE OR REPLACE PROCEDURE YukAddWorkingRule(wrkid IN VARCHAR2,rulid in varchar2,rultype in int) AS 
    BEGIN 
       Insert Into YukWorkingRule(WORKID,RULEID,RULETYPE) VALUES(wrkid,rulid,rultype);
END YukAddWorkingRule;
/

CREATE OR REPLACE PROCEDURE YukGetWorkingRule(wId IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
         select WORKID,RULEID,RULETYPE from YukWorkingRule where WORKID = wId;
END YukGetWorkingRule;
/

CREATE OR REPLACE PROCEDURE YukGetPipe(pipId IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
         select Pipeid,PipeName,ClassName,PassOnError from YukPipe where Pipeid = pipId;
END YukGetPipe;
/

CREATE OR REPLACE PROCEDURE YukGetPipeList(RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
         select Pipeid,PipeName,ClassName,PassOnError from YukPipe;
END YukGetPipeList;
/

CREATE OR REPLACE PROCEDURE YukDelPipe(pipId IN VARCHAR2) AS 
    BEGIN 
        delete from YukPipe where Pipeid = pipId;
END YukDelPipe;
/

CREATE OR REPLACE PROCEDURE YukAddPipe(pipId IN VARCHAR2,pipName in varchar2,pipClass in varchar2,pipPass in VARCHAR2) AS 
    BEGIN 
       Insert Into YukPipe(Pipeid,PipeName,ClassName,PassOnError) VALUES(pipId,pipName,pipClass,pipPass);
END YukAddPipe;
/

CREATE OR REPLACE PROCEDURE YukUpdPipe(pipId IN VARCHAR2,pipName in varchar2,pipClass in varchar2,pipPass in VARCHAR2) AS 
    BEGIN 
      update YukPipe set PipeName=pipName,ClassName=pipClass,PassOnError=pipPass where Pipeid = pipId;
END YukUpdPipe;
/

CREATE OR REPLACE PROCEDURE YukAddFolder(fId IN varchar2,fName in varchar2, pId in varchar2,
    pdescr in varchar2,iAclId in varchar2,iderived in varchar2) AS 
    BEGIN 
       Insert Into YukFolder(FolderId,FolderName,ParentId,Folderdescr, aclId,IsDerived) VALUES(fId,fName,pId,pdescr,iAclId,iderived);
END YukAddFolder;
/

CREATE OR REPLACE PROCEDURE YukGetFolder(fId IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
         select FolderId,ParentId,FolderName,Folderdescr,aclId,IsDerived from YukFolder where FolderId = fId;
END YukGetFolder;
/

CREATE OR REPLACE PROCEDURE YukGetFolderList(RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
         select FolderId,ParentId,FolderName,Folderdescr,aclId,IsDerived from YukFolder where ParentId is null;
END YukGetFolderList;
/

CREATE OR REPLACE PROCEDURE YukGetFolderChildList(fId IN varchar2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
         select FolderId,ParentId,FolderName,Folderdescr,aclId,IsDerived from YukFolder where ParentId = fId;
END YukGetFolderChildList;
/

CREATE OR REPLACE PROCEDURE YukUpdFolder(fId IN varchar2,pId in varchar2,fName in varchar2,
    fdescr in varchar2,iAclId in varchar2,iderived in varchar2) AS 
    BEGIN 
      update YukFolder set FolderName=fName,Folderdescr=fdescr,ParentId=pId,aclId=iAclId,IsDerived=iderived where FolderId = fId;
END YukUpdFolder;
/

CREATE OR REPLACE PROCEDURE YukDelFolder(fId IN VARCHAR2) AS 
    BEGIN 
        delete from YukFolder where FolderId = fId;
END YukDelFolder;
/

CREATE OR REPLACE PROCEDURE YukAddDoc(iDocId in varchar2,iDocName in varchar2,iWorkId in varchar2,iCreateDate in VARCHAR2,iDocLastVersion in int,iIsCheckOut in varchar2,iMigId in varchar2
    ,iMeta in varchar2,iparent in varchar2, iAclId in varchar2,iDerived in varchar2) AS 
    BEGIN 
       Insert Into YukDoc(Docid,DocName,WorkId,createDate,DocLastVersion,IsCheckOut,MigId,MetaName,ParentFolderId,AclId,IsDerived) 
        VALUES(iDocId,iDocName,iWorkId,iCreateDate,iDocLastVersion,iIsCheckOut,iMigId,iMeta,iparent,iAclId,iDerived);
END YukAddDoc;
/

CREATE OR REPLACE PROCEDURE YukGetDoc(iDocId IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
         select Docid,DocName,WorkId,createDate,DocLastVersion,IsCheckOut,MigId,MetaName,ParentFolderId,AclId,IsDerived from YukDoc where Docid = iDocId;
END YukGetDoc;
/

CREATE OR REPLACE PROCEDURE YukGetFolderDoc(iFolderId IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
         select Docid,DocName,WorkId,createDate,DocLastVersion,IsCheckOut,MigId,MetaName,ParentFolderId,AclId,IsDerived from YukDoc where ParentFolderId = iFolderId;
END YukGetFolderDoc;
/

CREATE OR REPLACE PROCEDURE YukDelDoc(iDocId IN VARCHAR2) AS 
    BEGIN 
        delete from YukDoc where Docid = iDocId;
END YukDelDoc;
/

CREATE OR REPLACE PROCEDURE YukUpdDoc(iDocId IN VARCHAR2,iDocName in varchar2,iDocLastVersion in int,iIsCheckOut in varchar2,iMigId in varchar2,iMeta in varchar2,iparent in varchar2, iAclId in varchar2,iDerived in varchar2) AS 
    BEGIN 
        update YukDoc set DocLastVersion=iDocLastVersion,DocName=iDocName,IsCheckOut=iIsCheckOut,MigId=iMigId,MetaName=iMeta,ParentFolderId=iparent,AclId=iAclId,IsDerived=iDerived where Docid = iDocId;
END YukUpdDoc;
/

CREATE OR REPLACE PROCEDURE YukAddContent(iApnum in int,iDocId IN varchar2,iDocVersion in int,iLoc in varchar2,iName in VARCHAR2,
    iSize in int, iCreator in varchar2,iType in varchar2,strId in varchar2) AS 
    BEGIN 
       Insert Into YukContent(Apnum,Docid,DocVersion,ContentLoc,ContentName,ContentSize,ContentCreator,FileType,StorageId) 
        VALUES(iApnum,iDocId,iDocVersion,iLoc,iName,iSize,iCreator,iType,strId);
END YukAddContent;
/

CREATE OR REPLACE PROCEDURE YukGetContent(iDocId IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
         select Apnum,Docid,DocVersion,ContentLoc,ContentName,ContentSize,ContentCreator,FileType,StorageId from YukContent where Docid = iDocId;
END YukGetContent;
/

CREATE OR REPLACE PROCEDURE YukGetVersionContent(iDocId IN VARCHAR2,iVersion in int, RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
         select Apnum,Docid,DocVersion,ContentLoc,ContentName,ContentSize,ContentCreator,FileType,StorageId from YukContent where Docid = iDocId and DocVersion=iVersion;
END YukGetVersionContent;
/

CREATE OR REPLACE PROCEDURE YukGetStorageContent(iDocId IN VARCHAR2,iStrId in varchar2, RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
         select Apnum,Docid,DocVersion,ContentLoc,ContentName,ContentSize,ContentCreator,FileType,StorageId from YukContent where Docid = iDocId and StorageId=iStrId;
END YukGetStorageContent;
/

CREATE OR REPLACE PROCEDURE YukGetSingleContent(iDocId IN VARCHAR2,iStrId in varchar2,iver in int, RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
         select Apnum,Docid,DocVersion,ContentLoc,ContentName,ContentSize,ContentCreator,FileType,StorageId from YukContent where Docid = iDocId and StorageId=iStrId and DocVersion=iver;
END YukGetSingleContent;
/

CREATE OR REPLACE PROCEDURE YukUpdContent(iDocId IN VARCHAR2,iApnum in int, iConLoc in varchar2,iCreator in varchar2, iConSize in varchar2,iStrId in varchar2,iOldStrId in varchar2, iver in int) AS 
    BEGIN 
        update YukContent set Apnum=iApnum,ContentLoc=iConLoc,ContentSize=iConSize,ContentCreator=iCreator,StorageId=iStrId where Docid = iDocId and StorageId=iOldStrId and DocVersion=iver;
END YukUpdContent;
/

CREATE OR REPLACE PROCEDURE YukDelContent(iDocId IN VARCHAR2,strId in varchar2,iver in int) AS 
    BEGIN 
        delete from YukContent where Docid = iDocId and StorageId=strId and DocVersion=iver;
END YukDelContent;
/

CREATE OR REPLACE PROCEDURE YukAddLifeCycle(iLcId IN varchar2,iLcName in varchar2,iLCType in int,iWorkId in varchar2,iStartCron in VARCHAR2,iEndCron in VARCHAR2,
    iStartRange in varchar2,iEndRange in varchar2, iTcount in int,iLoopBack in varchar2) AS 
    BEGIN 
       Insert Into YukLifeCycle(LCId,LCName,LCType,WorkId,StartCron,EndCron,StartRange,EndRange,Tcount,LoopBack) 
        VALUES(iLcId,iLcName,iLCType,iWorkId,iStartCron,iEndCron,iStartRange,iEndRange,iTcount,iLoopBack);
END YukAddLifeCycle;
/

CREATE OR REPLACE PROCEDURE YukDelLifeCycle(iLcId IN VARCHAR2) AS 
    BEGIN 
        delete from YukLifeCycle where LCId  = iLcId;
END YukDelLifeCycle;
/

CREATE OR REPLACE PROCEDURE YukGetLifeCycle(iLcId IN VARCHAR2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
         select LCId,LCName,LCType,WorkId,StartCron,EndCron,StartRange,EndRange,Tcount,LoopBack from YukLifeCycle where LCId = iLcId;
END YukGetLifeCycle;
/

CREATE OR REPLACE PROCEDURE YukGetLifeCycleList(RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
         select LCId,LCName,LCType,WorkId,StartCron,EndCron,StartRange,EndRange,Tcount,LoopBack from YukLifeCycle;
END YukGetLifeCycleList;
/

CREATE OR REPLACE PROCEDURE YukAddCluster(iApnum IN int,iAdress in varchar2,iState in VARCHAR2) AS 
    BEGIN 
       Insert Into YukCluster(ApNum,address,HealthState) 
        VALUES(iApnum,iAdress,iState);
END YukAddCluster;
/

CREATE OR REPLACE PROCEDURE YukUpdCluster(iAdress in varchar2,iState in VARCHAR2) AS 
    BEGIN 
        update YukCluster set HealthState=iState where address = iAdress;
END YukUpdCluster;
/

CREATE OR REPLACE PROCEDURE YukDelCluster(iApnum IN int) AS 
    BEGIN 
        delete from YukCluster where ApNum  = iApnum;
END YukDelCluster;
/

CREATE OR REPLACE PROCEDURE YukGetClusterList(RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
         select ApNum,address,HealthState from YukCluster order by Apnum asc;
END YukGetClusterList;
/

CREATE OR REPLACE PROCEDURE YukGetCluster(iAdress in varchar2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
         select ApNum,address,HealthState from YukCluster where address = iAdress;
END YukGetCluster;
/

CREATE OR REPLACE PROCEDURE YukGetClusterByApnum(iApnum in int,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
         select ApNum,address,HealthState from YukCluster where apNum = iApnum;
END YukGetClusterByApnum;
/

CREATE OR REPLACE PROCEDURE YukAddLifeCycleInfo(iLcId in varchar2,iLcState in varchar2,iNextRownum in int,
    iLcStart in varchar2,iLcEnd in varchar2,iTotalCount in int,ierrorCount in int,iexecCount in int) AS 
    BEGIN 
       Insert Into YukLifeCycleInfo(LCId,LCState,NextRownum,LCStart,LCEnd,TotalCount,errorCount,execCount) 
        VALUES(iLcId,iLcState,iNextRownum,iLcStart,iLcEnd,iTotalCount,ierrorCount,iexecCount);
END YukAddLifeCycleInfo;
/

CREATE OR REPLACE PROCEDURE YukUpdLifeCycleInfo(iLcId in varchar2,iLcState in varchar2,iNextRownum in int,
                                    iLcStart in varchar2,iLcEnd in varchar2,iTotalCount in int ,iexecCount in int,ierrorCount in int) AS 
    BEGIN 
        update YukLifeCycleInfo set LCState=iLcState,NextRownum=iNextRownum,LCStart=iLcStart,LCEnd=iLcEnd,
            TotalCount=iTotalCount,execCount=iexecCount,errorCount=ierrorCount where LCId = iLcId;
END YukUpdLifeCycleInfo;
/

CREATE OR REPLACE PROCEDURE YukUpdLcInfoAddData(iLcId in varchar2,ierrorCount in int,iexecCount in int) AS 
 iexectemp int;
 ierrortemp int;
 BEGIN 
    select errorCount,execCount into ierrortemp,iexectemp from YukLifeCycleInfo where LCId = iLcId for update nowait;
    update YukLifeCycleInfo set errorCount=(ierrortemp+ierrorCount),execCount=(iexectemp+iexecCount) where LCId = iLcId;
    commit;
END YukUpdLcInfoAddData;
/

CREATE OR REPLACE PROCEDURE YukUpdLcInfoRowNum(iLcId in varchar2,nextnum out int) AS 
  BEGIN  
    select NextRownum into nextnum from YukLifeCycleInfo where LCId = iLcId for update nowait;
    update YukLifeCycleInfo set NextRownum=(nextnum+1000) where LCId = iLcId;
    commit;
END YukUpdLcInfoRowNum;
/

CREATE OR REPLACE PROCEDURE YukDelLifeCycleInfo(iLcId in varchar2) AS 
    BEGIN 
        delete from YukLifeCycleInfo where LCId  = iLcId;
END YukDelLifeCycleInfo;
/

CREATE OR REPLACE PROCEDURE YukGetLifeCycleInfo(iLcId in varchar2, RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
         select LCId,LCState,NextRownum,LCStart,LCEnd,TotalCount,errorCount,execCount from YukLifeCycleInfo where LCId=iLcId;
END YukGetLifeCycleInfo;
/

CREATE OR REPLACE PROCEDURE YukDesTargetCount(iWorkId in varchar2,iLimitTime in varchar2,target OUT int) AS 
    BEGIN 
          select count(1) into target from YukDoc where WorkId = iWorkId and createdate < iLimitTime;
END YukDesTargetCount;
/

CREATE OR REPLACE PROCEDURE YukRangeTargetCount(iWorkId in varchar2,iMigId in varchar2,iStart in varchar2,iEnd in varchar2, target OUT int) AS 
    BEGIN 
        select count(1) into target from YukDoc where WorkId = iWorkId and createDate between iStart and iEnd and MigId <> iMigid;
END YukRangeTargetCount;
/

---0,1000,2000,3000
CREATE OR REPLACE PROCEDURE YukDesTargetPaging(iWorkId in varchar2,iLimitTime in varchar2,iPaging in int, RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
          select Docid,DocName,WorkId,createDate,DocLastVersion,IsCheckOut,MigId,MetaName,ParentFolderId,AclId,IsDerived from 
            (select rownum as rnum,Docid,DocName,WorkId,createDate,DocLastVersion,IsCheckOut,MigId,MetaName,ParentFolderId,AclId,IsDerived from 
                (select Docid,DocName,WorkId,createDate,DocLastVersion,IsCheckOut,MigId,MetaName,ParentFolderId,AclId,IsDerived
                  from YukDoc where WorkId = iWorkId and createdate < iLimitTime)
            where rownum < (iPaging+1001))
          where rnum >= (iPaging+1);
END YukDesTargetPaging;
/

       
CREATE OR REPLACE PROCEDURE YukGetRangeTargetPaging(iWorkId in varchar2,iMigId in varchar2,iStart in varchar2,iEnd in varchar2,iPaging in int, RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
          select Docid,DocName,WorkId,createDate,DocLastVersion,IsCheckOut,MigId,MetaName,ParentFolderId,AclId,IsDerived from 
            (select rownum as rnum,Docid,DocName,WorkId,createDate,DocLastVersion,IsCheckOut,MigId,MetaName,ParentFolderId,AclId,IsDerived from 
                ( select Docid,DocName,WorkId,createDate,DocLastVersion,IsCheckOut,MigId,MetaName,ParentFolderId,AclId,IsDerived
                    from YukDoc where WorkId = iWorkId and createDate between iStart and iEnd and MigId <> iMigid)
            where rownum < (iPaging+1001))
          where rnum >= (iPaging+1);    
END YukGetRangeTargetPaging;
/

CREATE OR REPLACE PROCEDURE YukDesTarget(iWorkId in varchar2,iLimitTime in varchar2, RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
          select Docid,DocName,WorkId,createDate,DocLastVersion,IsCheckOut,MigId,MetaName,ParentFolderId,AclId,IsDerived from YukDoc where WorkId = iWorkId and createdate < iLimitTime;
END YukDesTarget;
/

CREATE OR REPLACE PROCEDURE YukGetRangeTarget(iWorkId in varchar2,iMigId in varchar2,iStart in varchar2,iEnd in varchar2, RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
          select Docid,DocName,WorkId,createDate,DocLastVersion,IsCheckOut,MigId,MetaName,ParentFolderId,AclId,IsDerived from YukDoc where WorkId = iWorkId and createDate between iStart and iEnd and MigId <> iMigid;
END YukGetRangeTarget;
/

CREATE OR REPLACE PROCEDURE YukAddMetaSetting(iMetaName in varchar2,iMetaType in varchar2,iMetaQuery in varchar2,iQueryOrder in varchar2) AS 
    BEGIN 
       Insert Into YukMeta(MetaName,MetaType,MetaQuery,QueryOrder) 
        VALUES(iMetaName,iMetaType,iMetaQuery,iQueryOrder);
END YukAddMetaSetting;
/

CREATE OR REPLACE PROCEDURE YukUpdMetaSetting(iMetaName in varchar2,iMetaType in varchar2,iMetaQuery in varchar2,iQueryOrder in varchar2) AS 
    BEGIN 
        update YukMeta set MetaType=iMetaType,MetaQuery=iMetaQuery,QueryOrder=iQueryOrder where MetaName = iMetaName;
END YukUpdMetaSetting;
/

CREATE OR REPLACE PROCEDURE YukDelMetaSetting(iMetaName in varchar2) AS 
    BEGIN 
        delete from YukMeta where MetaName  = iMetaName;
END YukDelMetaSetting;
/

CREATE OR REPLACE PROCEDURE YukGetMetaSetting(iMetaName in varchar2,RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
          select MetaName,MetaType,MetaQuery,QueryOrder from YukMeta where MetaName = iMetaName;
END YukGetMetaSetting;
/

CREATE OR REPLACE PROCEDURE YukGetMetaSettingList(RC OUT YukCusor.REFCUR) AS 
    BEGIN 
        open RC for
          select MetaName,MetaType,MetaQuery,QueryOrder from YukMeta;
END YukGetMetaSettingList;
/









