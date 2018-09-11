--drop all table
BEGIN
  FOR i IN (SELECT ut.table_name FROM USER_TABLES ut) LOOP
    EXECUTE IMMEDIATE 'drop table '|| i.table_name ||' CASCADE CONSTRAINTS ';
  END LOOP;
END;
--drop all procedure
BEGIN
  FOR i IN (SELECT ut.object_name FROM USER_OBJECTS ut  WHERE OBJECT_TYPE = 'PROCEDURE') LOOP
    EXECUTE IMMEDIATE 'drop procedure '|| i.object_name;
  END LOOP;
END;

--ADMIN admin
insert into yukusergroup(UserId,UserName,UserPassWord,UserType,ManageType) values('ADMIN','admin','jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=','USER','ADMIN');
--GUEST user                                                        
insert into yukusergroup(UserId,UserName,UserPassWord,UserType,ManageType) values('GUEST','guest','BPiZbadjt6lpsQKO4wB1aerzpjVIbdqyEdUSyFud+Ps=','USER','PUBLIC');
--default Group                                                        
insert into yukusergroup(UserId,UserName,UserType) values('4f40e47e-1126-4c62-8403-62950e69f273','ROOT','GROUP');
--default acl data
insert into yukacl(AclId,AclName) VALUES('4f40e47e-1126-4c62-8403-62950e69f273','ADMIN');
--default ace data
insert into yukace(AclId,AceId,hasPermission) VALUES('4f40e47e-1126-4c62-8403-62950e69f273','ADMIN','{"FOLDER":false,"ADDFOLDER":false,"DELFOLDER":false,"UPDFOLDER":false,"GETFOLDER":false,"FILE":false,"ADDFILE":false,"DELFILE":false,"UPDFILE":false,"GETFILE":false,"VERSIONFILE":false}}');
--add root folder
insert into yukFolder(FolderId,ParentId,FolderName,FolderDescr,ISDERIVED) values('ROOT','','ROOT','ROOT','false');

commit;
