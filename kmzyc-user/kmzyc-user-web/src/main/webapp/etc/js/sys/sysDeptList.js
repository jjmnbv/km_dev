//初始化部门列表
$().ready(function(e) {
	
});

//新增一级部门
function gotoAdd(){
   dialog("新增一级部门","iframe:gotoSysDeptAdd.action","500px","310px","iframe");
}

function closePopDiv(){
	closeThis();
}

function closePopDiv2(){
	closeThis();
	window.location.reload();//刷新当前页面  
}
