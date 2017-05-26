//返回我的桌面界面
function gotoList(){
    location.href="/sys/gotoSysMain.action";
}

function gotoAdd(){
    location.href="/sys/gotoSysUserAdd.action";
}

function gotoUpdate(userId){
    location.href="/sys/gotoSysUserUpdate.action?userId="+userId;
}
function doDelete(name){
	document.forms['frm'].action="/sys/deleteSysuser.action";
	document.forms['frm'].submit();
}