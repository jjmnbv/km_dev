function selectList() {
	var roleIds = document.forms[0].delId;
	var idStr = "";
	var nameStr = "";
	for(i=0;i<roleIds.length;i++)
	{   
		if(roleIds[i].checked == true)   
		{   
				 idStr = idStr + roleIds[i].id + ",";
				 nameStr = nameStr + roleIds[i].value + ",";
		}
	}
	if(idStr == ""){
        alert("没有选中任何岗位角色！");
		return false;
	}
	idStr = idStr.substring(0,idStr.lastIndexOf(","));
	nameStr = nameStr.substring(0,nameStr.lastIndexOf(","));
    parent.closeOpenRoleDiv(idStr,nameStr);
}