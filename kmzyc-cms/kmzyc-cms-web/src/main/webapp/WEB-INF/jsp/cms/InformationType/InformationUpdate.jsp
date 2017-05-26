<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加广告</title>

<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
 <Script src="/etc/js/97dater/WdatePicker.js"></Script> 
 <script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
<script src="/etc/js/dialog.js"></script>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
 
</head>
<body>
 
 <!-- 导航栏 -->
 

<style type="text/css">
.listTitle {
	height: 40px;
    line-height: 40px;
    background: #c7e3f1;
    vertical-align: middle;
    font-size: 13;
    color: #222222;
    margin-bottom: 20px;
}
.listTitle span{
padding-left: 20px;
    height: 40px;
    line-height: 40px;
    vertical-align: middle;
    margin-top: 5px;
}.listTitle span img{
   vertical-align: middle;
}
.content_table {
    background-color: #f5f5f5;
    border: 1px solid #e3e3e3;
    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.05);
    -moz-box-shadow: inset 0 1px 1px rgba(0,0,0,0.05);
    box-shadow: inset 0 1px 1px rgba(0,0,0,0.05);
    overflow: hidden;
    padding: 6px;
}
</style>
<!-- 导航栏 -->
<s:set name="parent_name" value="'基础功能'" scope="request" />
		<s:set name="name" value="'资讯类别管理'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
<s:form  id="AdcolumnAdd" name="AdcolumnAdd" action="/cms/InformationType_Update.action" method="POST" enctype="multipart/form-data">
<s:token></s:token>
<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<!-- keyWords -->
<input type="hidden" name="keyWords.name_keyword" value="<s:property value='keyWords.name_keyword'/>">
<input type="hidden" name="keyWords.status_keyword" value="<s:property value='keyWords.status_keyword'/>"/>
<input type="hidden" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
<input type="hidden" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
<!-- 数据编辑区域 -->
<table width="60%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">

	<tr> 
		<th colspan="2" align="left" class="edit_title">资讯类别信息</th>
	</tr>
	<!-- error message -->
	<tr> <input type="hidden" id="infortypeid" name="infortype.id" value="<s:property value="infortype.id" />" />
		<td width="20%" align="right"><font color="red">*</font>资讯类名：</td>
		<td width="80%" align="left">
			<input name="infortype.name" id="corporateName" type="text" value="<s:property value="infortype.name" />"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right">资讯编码：</td>
		<td width="80%" align="left">
			<input name="infortype.typeCode" id="typeCode" type="text" value="<s:property value="infortype.typeCode" />"/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>状态</td>
		<td width="80%"> 
			<input type="radio"  <s:if test="infortype.status==0">checked='checked'</s:if> name="infortype.status" value="0">开
			<input type="radio" <s:if test="infortype.status==1">checked='checked'</s:if> name="infortype.status" value="1">关
			
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>排序：</td>
		<td width="80%">
			<input name="infortype.orders" id="url" type="text" value="<s:property value="infortype.orders" />" />
		</td>
	</tr>

	
	
</table>


<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<INPUT class="saveBtn" type="submit"  value="">
            &nbsp;&nbsp;
			<input class="backBtn"  onclick="gotoList()" type="button" value="">
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

<br><br>

</s:form >




</div>

</BODY>
<!-- 消息提示页面 -->
		

<!-- 消息提示 -->
<div width="100%">
<!-- 消息提示 -->
		 
	              
</div>

<script>
  $("#AdcolumnAdd").validate({
               rules: {
					"infortype.name": {required:true,compareTo:true,compareTo3:true},
					"infortype.orders": {required:true,number:true,compareTo2:true}
						        	},
	         success: function (label){
	            label.removeClass("checked").addClass("checked");
	            }
          });
 
 function gotoList(){
  var pageForm= window.document.getElementById("AdcolumnAdd");
 	pageForm.action="/cms/InformationType_queryInformationType.action";
 	pageForm.submit();
 }
         
 jQuery.validator.addMethod("compareTo", function(value, element) {  
      var id=$("#infortypeid").val();
      
       var ok="";
        $.ajax({
        url:'/cms/InformationType_name_ajax.action',
        data:{"infortype.name":value,"infortype.id":id},
        async:false,
        type:'post',
        success:function (d) {
         if(d=="0"){
         	ok="0";
         }else {
         	ok="1";
         }
        }
       
	    });
	    if(ok=="0"){
	    return true;
	    }else{
	    return false;
	    }
		
    },"该名已存在 ");
 
   jQuery.validator.addMethod("compareTo2", function(value, element) {  
     if(value.length<10){
     return true;
     }else{
     return false;
     }
     
    },"请输入0-999999999之间的数字");
    
    
   jQuery.validator.addMethod("compareTo3", function(value, element) {  
     if(value.length<16){
     return true;
     }else{
     return false;
     }
     
    },"请输入小于16个字数的名字");
          
             
          
          
 </script>
 
</HTML>