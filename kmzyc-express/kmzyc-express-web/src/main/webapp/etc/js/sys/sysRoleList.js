//返回我的桌面界面
function gotoList(){
    location.href="/sys/gotoSysMain.action";
}

function gotoAdd(){
    location.href="/sys/gotoSysRoleAdd.action";
}

function gotoUpdate(roleId){
    location.href="/sys/gotoSysRoleUpdate.action?roleId="+roleId;
}


function gotoRoleMenuEdit(roleId, roleName){
	location.href = "/sys/listMenuByRole.action?roleId="+roleId;
}

function gotoRoleCatgroupEdit(roleId){
	location.href = "/sys/gotoRoleCatgroupAction.action?sysRole.roleId="+roleId;
}

function checkAll(ck)
{
  for (var i=0;i<ck.form.all.tags("input").length;i++){
    var ele = ck.form.all.tags("input")[i];
    /*var ct = ele.getAttribute("type");*/
    if ((ele.type=="checkbox")){
      if(ck.checked!=ele.checked)
        ele.click();
    }
  }
}

function checkSelected(sName){
   window.event.returnValue = false;
   var chs = document.getElementsByName(sName);
   for(var i=0;i<chs.length;i++){
   	  var ele = chs[i];
   	  if(ele.type=="checkbox" && ele.checked==true)
	   	  return true;
   }
   return false;
}

function deleteSelected(sName)
{

   if (!checkSelected(sName))
   {
      alert("请选择要删除的数据！");
      return false;
   }
   if (confirm("你确定要删除选中的数据？"))
   {
      doDelete(sName);
   }
}

function doDelete(){
	document.forms[0].action="deleteSysRole.action";
	document.forms[0].submit();
}