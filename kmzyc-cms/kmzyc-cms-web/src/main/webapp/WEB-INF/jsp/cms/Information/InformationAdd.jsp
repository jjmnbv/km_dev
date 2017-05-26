<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加文章</title>
<%   
          java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
          java.util.Date currentTime = new java.util.Date();//得到当前系统时间 
          String str_date = formatter.format(currentTime); //将日期时间格式化 
 %> 
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
 <Script src="/etc/js/97dater/WdatePicker.js"></Script> 
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">

<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
 
  
   <link rel="stylesheet" href="/resource/kindeditor/themes/default/default.css" />
	<link rel="stylesheet" href="/resource/kindeditor/plugins/code/prettify.css" />
	<script charset="utf-8" src="/resource/kindeditor/kindeditor.js"></script>
	<script charset="utf-8" src="/resource/kindeditor/lang/zh_CN.js"></script>
	<script charset="utf-8" src="/resource/kindeditor/plugins/code/prettify.js"></script>
	
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>

<link rel="stylesheet" href="/etc/js/codemirror/codemirror.css">
<link rel="stylesheet" href="/etc/js/codemirror/fullscreen.css">
<link rel="stylesheet" href="/etc/js/codemirror/erlang-dark.css">
<script src="/etc/js/codemirror/codemirror.js"></script>
<script src="/etc/js/codemirror/xml.js"></script>
<script src="/etc/js/codemirror/fullscreen.js"></script>
<script src="/etc/js/codemirror/userdefined.js"></script>
  	<script>
		var editor;
		KindEditor.ready(function(K) {
			    editor = K.create('textarea[name="infor.Content_content"]', {
					cssPath : '/resource/kindeditor/plugins/code/prettify.css',
					uploadJson : '/resource/kindeditor/jsp/upload_json.jsp',
					fileManagerJson : '/resource/kindeditor/jsp/file_manager_json.jsp',
					allowFileManager : true
				});
		});
		$(function(){
		     var templateType = $("#templateType").val();
		    if(templateType!=3){
		      $("#content1").show();
		      $("#content2").hide();
		    }else{
		      $("#content1").hide();
		      $("#content2").show();
		    }
		});
		function choiceArea(){
		    var templateType = $("#templateType").val();
		    if(templateType!=3){
		      $("#content1").show();
		      $("#content2").hide();
		    }else{
		      $("#content1").hide();
		      $("#content2").show();
		    }
		}
		function back() {
			 window.location.href="/cms/Information_List.action";
		}
		function checkSubmit() {
			editor.sync();
			$("form").submit();
		}
	</script>
</head>
<body>
 
 <!-- 导航栏 -->
 

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
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;文章管理
		 &nbsp;&nbsp;<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;添加文章
		
		
	  </span>
</div>
<div  style="height:90%;overflow-y:auto; " >
<s:form  id="InformationAdd" name="InformationAdd" action="/cms/Information_Add.action" method="POST" enctype="multipart/form-data">
<s:token></s:token>
<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<!-- keyWords -->
<input type="hidden" name="keyWords.name_keyword" value="<s:property value='keyWords.name_keyword'/>">
<input type="hidden" name="keyWords.outPath_keyword" value="<s:property value='keyWords.outPath_keyword'/>">
<input type="hidden" name="keyWords.status_keyword" value="<s:property value='keyWords.status_keyword'/>"/>
<input type="hidden" name="keyWords.type_keyword" value="<s:property value='keyWords.type_keyword'/>"/>
<input type="hidden" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
<input type="hidden" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
<!-- 数据编辑区域 -->
<table width="60%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">

	<tr> 
		<th colspan="2" align="left" class="edit_title">资讯信息</th>
	</tr>
	<!-- error message -->
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>标题：</td>
		<td width="80%" align="left">
		<s:if test="adminType==0">
		<input type="hidden" name="adminType" value="<s:property  value="adminType"/>" />
		</s:if>
			<input name="infor.name" id="corporateName" type="text" value=""/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>类别：</td>
		<td width="80%"> 
		  <s:select list="inforTypeList" listKey="id" listValue="name" headerKey="1" headerValue="**" name="infor.typeId" id="infortype"> 
		  
		  </s:select>
		  <!--  
			<select name="infor.typeId" id="infortype">
	        <option value="1"  selected="selected">**</option>
	        </select>-->
		</td>
	</tr>
    <tr style="display:none;"> 
		<td width="20%" align="right"><font color="red">*</font>发布</td>
		<td width="80%"  > 
			<input type="radio" name="infor.status"  style="display:none;" value="0">已发布
			<input type="radio"  checked='checked' style="display:none;"  name="infor.status" value="1">未发布
			
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>排序：</td>
		<td width="80%"> 
			<input name="infor.orders" id="infor.orders" type="text"onkeydown="if (event.keyCode==13) {}" onblur="if(this.value=='')value='0';" onfocus="if(this.value=='0')value='';" value="0">
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right"><font color="red">*</font>发布时间：</td>
		<td width="80%"> 
			<input name="infor.createDate" id="beginTime" type="text" readonly="readonly" value="<%=str_date%>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
		</td>
	</tr>
	<tr>
		<td width="20%" align="right">资讯图片：</td>
		<td width="80%">
			<input type=file name="resumefile" id="resumefile" onchange="javascript:setImagePreview();" style=" border: 1px #6699CC solid;">
			<div id="localImag"><img id="preview" width=-1 height=-1 style="diplay:none" /></div>
		</td>
	</tr>
	
	<tr>
	  <td width="20%" align="right">模板类型</td>
		  <td width="80%"> 
			<select name="infor.templateType" id="templateType" onchange="choiceArea()">
		        <option value="5" selected="selected">帮助模板</option>
		        <option value="4">公告资讯模板</option>
		        <option value="6">移动端模板</option>
		        <option value="11">招商帮助模板</option>
		        <option value="23">活动中心帮助模板</option>
		        <option value="24">中药城帮助中心模板</option>
		     <!--     <option value="7">窄版静态专题页模板</option>
		        <option value="8">宽版静态专题页模板</option>-->
		        <option value="3">自定义</option>
	        </select>
		</td>
	   
	</tr>
	<tr> 
		<td width="20%" align="right">内容</td>
		<td width="80%" id="content1"> 
			<textarea  name="infor.Content_content" cols="100" rows="8" style="width:100%;height:300px;visibility:hidden;"></textarea>
		</td>
		<td id="content2" style="display:none;">
		   当光标在编辑器中，按F11键切换全屏幕编辑。ESC可退出全屏幕编辑。
		  <textarea id="content" name="infor.InformContent"  style="width:100%;height:400px;"></textarea>
		</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right">页面标题：</td>
		<td width="80%"> 
			<input name="infor.title" id="beginTime" type="text"  value="" >
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">页面关键字：</td>
		<td width="80%"> 
		<input name="infor.key"  id="endTime" type="text"  value="" >
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">页面描述：</td>
		<td width="80%">
			<textarea name="infor.description" cols="100" rows="8" style="width:100%;height:100px;"></textarea>
		</td>
	</tr>
	
	
</table>


<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<INPUT class="saveBtn"   type="submit"  value="">
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
 $().ready(function(){
 $("#InformationAdd").validate({
               rules: {
                  
					"infor.name": {required:true,compareTo2:true,compareTo3:true},
			    	"infor.orders": {number:true,},
			    	"infor.description": {maxlength:500}
			    	
						        	},
	         success: function (label){
	            label.removeClass("checked").addClass("checked");
	            }
          });
 /*
  $.ajax({
        url:'Information_Add_ajax.action',
        type:'post',
        dataType:'json',
        success:function (d) {
       var li='';
         $.each(d.inforlist, function (index, item) {
         li+="<option value="+item.id+">"+item.name+"</option>";
        });
        $("#infortype").html(li);
        }
 });
 */

 });
 jQuery.validator.addMethod("compareTo3", function(value, element) {  

       var ok="";
        $.ajax({
        url:'/cms/Information_name_ajax.action',
        data:{"infor.name":value},
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
    
    
 jQuery.validator.addMethod("compareTo", function(value, element) {  

       var ok="";
        $.ajax({
        url:'/cms/cmsPromotion_outPutValidate.action',
        data:{"outPut":value,"outPutType":4},
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
		
    },"该文件已存在 ");
 jQuery.validator.addMethod("compareTo2", function(value, element) {  
    if(value.length>200){
    return false;
    }else{
    return true;
    }
    },"名称不能长于200个汉字 ");
 
 
 
 function gotoList(){
  var pageForm= window.document.getElementById("InformationAdd");
 	pageForm.action="/cms/Information_List.action";
 	pageForm.submit();
 }
 
 function setImagePreview() {
        var docObj=document.getElementById("resumefile");
 
        var imgObjPreview=document.getElementById("preview");
        
        //图片大小限制 
        var fileSize = 0;         
        if (!docObj.files) {     
          var filePath = docObj.value;     
          var fileSystem = new ActiveXObject("Scripting.FileSystemObject");        
          var file = fileSystem.GetFile (filePath);     
          fileSize = file.Size;    
        } else {    
         fileSize = docObj.files[0].size;     
         }   
         var size = fileSize / 1024;  
         
         if(size>200){  
       	  var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/><span style='color:red'>图片上传大小不能超过200K！</span>";
   	      dialog("消息提示","text:"+msg ,"300px","text");
   	   	  docObj.value="";
   	   	  imgObjPreview.src = "";
   	 	  imgObjPreview.style.display = 'none';
          return false;
         }
         
                if(docObj.files &&    docObj.files[0]){
                        //火狐下，直接设img属性
                        imgObjPreview.style.display = 'block';
                        imgObjPreview.style.width = '300px';
                        imgObjPreview.style.height = '120px';                    
                        //imgObjPreview.src = docObj.files[0].getAsDataURL();
      //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式  
      imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);

                }else{
                        //IE下，使用滤镜
                        docObj.select();
                        var imgSrc = document.selection.createRange().text;
                        var localImagId = document.getElementById("localImag");
                        //必须设置初始大小
                        localImagId.style.width = "300px";
                        localImagId.style.height = "120px";
                        //图片异常的捕捉，防止用户修改后缀来伪造图片
                try{
                                localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                                localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
                        }catch(e){
                                alert("您上传的图片格式不正确，请重新选择!");
                                return false;
                        }
                        imgObjPreview.style.display = 'none';
                        document.selection.empty();
                }
                return true;
        }
 </script>
 
</HTML>