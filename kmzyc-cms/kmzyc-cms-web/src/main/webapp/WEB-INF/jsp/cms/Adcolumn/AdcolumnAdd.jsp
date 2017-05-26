<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加广告位</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">

<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
 <script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
</head>
<body>
 
 <!-- 导航栏 -->
 <script>
 
 
$(document).ready(function(){

 	$("#AdcolumnAdd").validate({
               rules: {
					"cmsAdcolumn.output": {required:true,compareTo:true,maxlength:42},
					"cmsAdcolumn.name":{required:true,compareTo2:true,maxlength:42},
					"cmsAdcolumn.remark":{maxlength:84}
						        	},
	         success: function (label){
	            label.removeClass("checked").addClass("checked");
	            }
          });
    
	 	$(".selBtn").click(function(){
	 		dialog("选择模板","iframe:/cms/cmsPageAction_gotoSelPage.action" ,"900px","530px","iframe"); 
	 	});
	 	$(".pro_hid").click(function(){
	 		dialog("选择活动信息","iframe:/cms/cmsPromotion_promotion.action","900px","530px","iframe"); 
	 	});
	 	
          
 });
 
 jQuery.validator.addMethod("compareTo", function(value, element) {  

       var ok="";
        $.ajax({
        url:'/cms/cmsPromotion_outPutValidate.action',
        data:{"outPut":value,"outPutType":2},
        async:false,
        type:'post',
        success:function (d) {
         if(d=="1"){
         	ok="1";
         }else if(d=="0"){
         	ok="0";
         }
        }
       
	    });
	    if(ok=="0"){
	    return true;
	    }else{
	    return false;
	    }
		
    },"该文件已存在 ");
    
 jQuery.validator.addMethod("compareTo2", function(value, element) {  

       var ok="";
        $.ajax({
        url:'/cms/Adcolumn_name_ajax.action',
        data:{"cmsAdcolumn.name":value},
        async:false,
        type:'post',
        success:function (d) {
         if(d=="0"){
         	ok="0";
         }else{
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
 
 function gotoList(){
    //window.location.href="/cms/Adcolumn_queryAdcolumn.action";
    var pageForm= window.document.getElementById("AdcolumnAdd");
 	pageForm.action="/cms/Adcolumn_queryAdcolumn.action";
 	pageForm.submit();
 }

 function adcoumnChange(obj){
	 if(obj.value==2){
		 $("#output").attr("readonly",true);
		 $.ajax({
			   type: "POST",
			   url: "prodBrandAction_queryProdBrandJson.action",
			   success: function(data){
			   	var objs = eval(data);
			    //根据select的显示值来为select设值
			
			   	 	var podBrandId = $("#podBrandId");
			   	 	podBrandId.empty();
				   	var option = $("<option selected='selected'>").text("请选择品牌").val(-1);
			   		podBrandId.append(option);
			   		for(var i=0;i<objs.length;i++){
			   		  var option = $("<option>").text(objs[i].brandName).val(objs[i].brandId);
			   		  podBrandId.append(option);
				 }
			}
		 });
		 $("#podBrand").show();
		 $("#output").val("");
     }else{
    	 $("#podBrand").hide();
    	 $("#output").attr("readonly",false);
    	 $("#output").val("");
     }
 }

 function idChange(obj){
	 if(obj.value!=-1){
		 $("#output").val("/adv/prodBrand_"+obj.value+".js");
	 }else{
		 $("#output").val("");
     }
	 
 }
 
 </script>
 

<style type="text/css">
.listTitle {
	height: 40px;
	line-height:40px;
	background: #D6F2D9;
	border-bottom: 1px solid #079346;
	vertical-align: middle;
	font-size: 14;
	color: #028043;
	margin:0px;
}
.listTitle span{
 padding-left:20px;
 height:30px;
 line-height:30px;
 vertical-align: middle;
 margin-top:5px;
}
.listTitle span img{
   vertical-align: middle;
}
</style>
<!-- 导航栏 -->
<div width="100%" class="listTitle"  height="40">
    <span>
	<img src="/etc/images/icon_02.png"  />&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;基础功能&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;广告位管理
		
		 &nbsp;&nbsp;<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;添加广告位
		
		
	  </span>
</div>
<div  style="height:90%;overflow-y:auto; " >
<s:form  id="AdcolumnAdd"  name="AdcolumnAdd" action="/cms/Adcolumn_Add.action" method="POST" enctype="multipart/form-data">
<s:token></s:token>
<input type="hidden" name="struts.token.name" value="struts.token" />
<input type="hidden" name="struts.token" value="UFJ5Q17IYGPSOWDXRK4AI6BDFYY0QLF" />
<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<!-- keyWords -->
<input type="hidden" name="keyWords.name_keyword" value="<s:property value='keyWords.name_keyword'/>">
<input type="hidden" name="keyWords.status_keyword" value="<s:property value='keyWords.status_keyword'/>"/>
<input type="hidden" name="keyWords.outPath_keyword" value="<s:property value='keyWords.outPath_keyword'/>">
<input type="hidden" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
<input type="hidden" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
<!-- 数据编辑区域 -->
<table width="95%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<tr> 
		<th colspan="2" align="left" class="edit_title">广告位信息</th>
	</tr>
	<!-- error message -->
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>广告位名称：</td>
		<td width="80%">
			<input name="cmsAdcolumn.name" id="corporateName" type="text" value="<s:property value="cmsAdcolumn.name"/>"/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>广告类型：</td>
		<td width="80%">
			<select name="adcolumnType" id="adcolumnType" onchange="adcoumnChange(this);">
				<option value="1" selected="selected">常规广告</option>
				<option value="2">品牌广告</option>
			</select>
		</td>
	</tr>
	<tr id="podBrand" style="display: none;"> 
		<td width="20%" align="right"><font color="red">*</font>品牌：</td>
		<td width="80%">
			<select name="podBrandId" id="podBrandId" onchange="idChange(this);">
			</select>
		</td>
	</tr>
   
	 <tr> 
		<td width="20%" align="right"><font color="red">*</font>输出路径：</td>
		<td width="80%"> 
			<input name="cmsAdcolumn.output" id="output" type="text" value="<s:property value="cmsAdcolumn.output"/>" />
		</td>
	</tr>
    
	<!--<tr> 
		<td width="20%" align="right"><font color="red">*</font>宽度：</td>
		<td width="80%"> 
			<input name="cmsAdcolumn.width" id="mobile" type="text" value="<s:property value="cmsAdcolumn.width"/>" />
		</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>高度：</td>
		<td width="80%"> 
			<input name="cmsAdcolumn.height" id="email" type="text" value="<s:property value="cmsAdcolumn.height"/>" />
		</td>
	</tr>
	
<tr> 
		<td width="20%" align="right">类型：</td>
		<td width="80%">
			<select name="cmsAdcolumn.type">

	        <option value="0"  <s:if test="cmsAdcolumn.type==null">selected="selected"</s:if><s:elseif test="cmsAdcolumn.type==0">selected="selected"</s:elseif>>图片</option>
	        <option value="1"  <s:if test="cmsAdcolumn.type==1">selected="selected"</s:if>>Flash</option>
	        </select>
		</td>
	</tr>	-->
	<tr> 
		<td width="20%" align="right">有效性：</td>

		<td width="80%">
			<select name="cmsAdcolumn.status">
	        <option value="0" <s:if test="cmsAdcolumn.status==null">selected="selected"</s:if><s:elseif test="cmsAdcolumn.status==0">selected="selected"</s:elseif>>有效</option>
	        <option value="1" <s:if test="cmsAdcolumn.status==1">selected="selected"</s:if>>无效</option>
	        </select>
		</td>
	</tr>
	
	 <tr> 
		<td width="20%" align="right">备注：</td>
		<td width="80%"> 
			<input name="cmsAdcolumn.remark" id="contactsName" type="text" value="<s:property value="cmsAdcolumn.remark"/>" />
		</td>
	</tr>
</table>


<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<INPUT class="saveBtn" TYPE="submit" value="">
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
</HTML>