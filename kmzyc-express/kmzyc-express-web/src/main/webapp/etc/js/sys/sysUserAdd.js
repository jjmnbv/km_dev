function gotoList(){
    location.href="/sys/listSysUser.action";
}


//弹出层 选择部门
function popSelectDept() {
    dialog("选择部门","iframe:/sys/gotoPopSelectDept.action" ,"500px","340px","iframe");
}

function closeOpenDept(deptId,deptName){
    closeThis();
    document.forms[0].deptId.value = deptId;
	document.forms[0].deptName.value = deptName;
}


//弹出层 选择角色
function popSelectRole() {
    dialog("选择角色","iframe:/sys/gotoPopSelectRole.action" ,"500px","340px","iframe");
}

function closeOpenRoleDiv(roleId,roleName){
    closeThis();
    document.forms[0].roleListStr.value = roleId;
	document.forms[0].roleName.value = roleName;
}



//光标移动
function changeKey()
{
	var tr=event.srcElement.getAttribute("type");
	if("textarea"!=tr && "button" != tr)
	{
			if(13 == event.keyCode)
			{
				event.keyCode=9;
			}
  }
}