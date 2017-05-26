function deleteThisClass(){
    var isHaveSon = "<s:property value='isHaveSon'/>";
    if(isHaveSon!=null&&isHaveSon=="1"){
		alert("该部门下面有子部门，请不要删除！！！");
		return;
    }
    if(confirm("您确定要删除该部门吗？")){
    	window.document.updateForm.action = "deleteSysDept.action";
    	window.document.updateForm.target = "main";
		window.document.updateForm.submit();
	}
}
function addSonClass(){
	var deptId = window.document.getElementById("deptId").value;
	//var deptLv = window.document.getElementById("deptLv").value;
	//if(deptLv>=0){
	//	deptLv = Number(deptLv)+1;
	//}
	dialog("新增子部门","iframe:gotoSysDeptAdd.action?upDeptid="+deptId,"500px","310px","iframe");
}

function closePopDiv(){
	closeThis();
}

function closePopDiv2(){
	closeThis();
	alert("保存完毕！");
	window.document.updateForm.action = "gotoSysDeptList.action";
	window.document.updateForm.target = "main";
	window.document.updateForm.submit();
}

//弹出层 选择上级部门
function popSelectDept() {
    dialog("选择上级部门","iframe:/sys/gotoPopSelectUpDept.action?deptId=<s:property value='model.deptId'/>" ,"500px","310px","iframe");
}

function closeOpenDept(deptId,deptName,deptLv){
    closeThis();
    window.document.getElementById("upDeptid").value = deptId;
    window.document.getElementById("upDept").value = deptName;
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
