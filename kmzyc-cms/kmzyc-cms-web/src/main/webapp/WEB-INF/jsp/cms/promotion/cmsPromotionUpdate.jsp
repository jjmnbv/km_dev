<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加模板</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
<script src="/etc/js/dialog.js"></script>
 <Script src="/etc/js/97dater/WdatePicker.js"></Script> 
<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>

<link rel="stylesheet" href="/etc/js/codemirror/codemirror.css">
<link rel="stylesheet" href="/etc/js/codemirror/fullscreen.css">
<link rel="stylesheet" href="/etc/js/codemirror/erlang-dark.css">
<script src="/etc/js/codemirror/codemirror.js"></script>
<script src="/etc/js/codemirror/xml.js"></script>
<script src="/etc/js/codemirror/fullscreen.js"></script>
<script src="/etc/js/codemirror/userdefined.js"></script>

</head>
<body>
 
 <!-- 导航栏 -->
 <script>
 
$(document).ready(function(){

  
  $(".selBtn").click(function(){
	 		dialog("选择模板","iframe:/cms/cmsPageAction_gotoSelPage.action?cmsTemplate.type=3" ,"900px","530px","iframe"); 
	 	});
  
  
  $("#promotionUpdate").validate({
               rules: {
					"cmspt.name": {required:true,maxlength:42},
					"cmspt.output": {maxlength:128},
					"cmspt.content":{required:true},
					"cmspt.p2":{maxlength:42}
					
					
					
				
						        	},
	         success: function (label){
	            label.removeClass("checked").addClass("checked");
	            }
          });
   });
   jQuery.validator.addMethod("compareTo", function(value, element) {  
       var  id=$("#cmsptid").val();
       var ok="";
        $.ajax({
        url:'/cms/cmsPromotion_outPutValidate.action',
        data:{"outPut":value,"outPutType":3,"promotionId":id},
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
		
    },"该文件已存在 ");
    
      jQuery.validator.addMethod("compareTo3", function(value, element) {  
     if(value.length<16){
     return true;
     }else{
     return false;
     }
     
    },"请输入小于16个字数的名字");
       
    
   
   
   //关闭弹出活动层并赋值
 	function closeOpenDialog(content)
 	{
 		closeThis();
 		var json = eval('(' + content + ')'); 
 		editor.setValue(json.content);
 		
    }
    
    
    function Submit(){
  $("input").attr("disabled",false); 
		$("form").submit();

   }
    
    
    
  function gotoList(){
  		 var pageForm= window.document.getElementById("promotionUpdate");
 		pageForm.action="/cms/cmsPromotion_queryForPage.action";
 		pageForm.submit();
	}
	//切换PC端、移动端的图片选择
	//切换PC端、移动端的图片选择
	function changeOperation()
	{
		var operateType=$("#operateType").val();
		if(operateType==0)
		{
			$("#pic1").html("详情页导航图");
			$("#pic2").html("列表页活动展示图");
			$("#pic3").html("过往活动展示图");
			$("#pic4").html("广告图");
		}
		else
		{
			$("#pic1").html("iphone系统");
			$("#pic2").html("android系统");
			$("#pic3").html("wp7系统");
			$("#pic4").html("MTK系统");
		}
	}
 </script>
 

<style type="text/css">
.listTitle{height:40px;line-height:40px;background:#c7e3f1;border-bottom:1px solid #c7e3f1;vertical-align:middle;font-size:14;color:#333;margin:0}
.listTitle span{padding-left:20px;height:30px;line-height:30px;vertical-align:middle;margin-top:5px}
.listTitle span img{vertical-align:middle}
</style>
<!-- 导航栏 -->
<div width="100%" class="listTitle"  height="40">
    <span>
	<img src="/etc/images/icon_02.png"  />&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;基础功能&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;页面管理&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;修改活动页面
	 </span>
</div>
<div  style="height:95%;" >
<s:form  id="promotionUpdate"   action="/cms/cmsPromotion_promotionUpdate.action" method="POST" enctype="multipart/form-data">
<s:token></s:token>
<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">
<!-- keyWords -->
<input type="hidden" name="keyWords.outPath_keyword" value="<s:property value='keyWords.outPath_keyword'/>">
<input type="hidden" name="keyWords.name_keyword" value="<s:property value='keyWords.name_keyword'/>">
<input type="hidden" name="keyWords.status_keyword" value="<s:property value='keyWords.status_keyword'/>"/>
<input type="hidden" name="keyWords.beginTime_keyword" value="<s:date name="keyWords.beginTime_keyword" format="yyyy-MM-dd HH:mm:ss"/>"/>
<input type="hidden" name="keyWords.endTime_keyword" value="<s:date name="keyWords.endTime_keyword" format="yyyy-MM-dd HH:mm:ss"/>"/>
<input type="hidden" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
<input type="hidden" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
<!-- 数据编辑区域 -->
<table width="95%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">

	<tr > <s:property value=""/>
		<th colspan="2" align="left" class="edit_title">页面信息</th>
	</tr>
	<!-- error message -->
		<tr  style="display:none;"> 
		<td width="20%" align="right"><font color="red">*</font>ID：</td>
		<td width="80%">
			<input name="cmspt.id" id="cmsptid" type="text" value="<s:property value="cmspt.id"/>"/>
			<input name="cmspt.siteId" id="cmsptid" type="hidden" value="<s:property value="cmspt.siteId"/>"/>
		</td>
	</tr>
	<tr class="hid" > 
		<td width="20%" align="right"><font color="red">*</font>活动名称：</td>
		<td width="80%">
			<input name="cmspt.name" id="name" type="text" value="<s:property value="cmspt.name"/>"/>
		</td>
	</tr>
	<tr class="hid" > 
		<td width="20%" align="right"><font color="red">*</font>输出路径：</td>
		<td width="80%">
		  <input name="cmspt.output" <s:if test="adminType==0">disabled="disabled"</s:if>id="output" type="text" value="<s:property value="cmspt.output"/>"/>
		</td>
	</tr>
	<tr class="hid" >  
		<td width="20%" align="right">开始时间：</td>
		<td width="80%"> 
			<input name="cmspt.beginTime"  id="beginTime" type="text" value="<s:date name="cmspt.beginTime" format="yyyy-MM-dd HH:mm:ss"/>"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
		</td>
	</tr>
	<tr class="hid" > 
		<td width="20%" align="right">结束时间：</td>
		<td width="80%"> 
			<input name="cmspt.endTime" id="endTime"   type="text" value="<s:date name="cmspt.endTime" format="yyyy-MM-dd HH:mm:ss"/>"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right">运营端类型：</td>
		<td width="80%">
			<select name="cmspt.operateType" id="operateType" onchange="changeOperation()">
				<option value="0" <s:if test="cmspt.operateType==0">selected="selected"</s:if>>轮播图</option>
				<option value="1" <s:if test="cmspt.operateType==1">selected="selected"</s:if>>活动专题</option>
			</select>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">上传活动图片(<span id="pic1">iphone系统</span>)：</td>

		<td width="80%">
	
			<input type=file name="resumefile" id="resumefile" onchange="javascript:setImagePreview();" style=" border: 1px #6699CC solid;">
								
									
									
									<div id="localImag">
							
									<img id="preview" <s:if test="cmspt.imagesFile!=null"> width="300px" height="120px"  </s:if> src="<s:property value="cmspt.imagesFile"/>"/>
								
									</div>
		</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right">上传活动图片2(<span id="pic2">android系统</span>)：</td>

		<td width="80%">
			<input type=file name="resumefile2" id="resumefile2" onchange="javascript:setImagePreview2();" style=" border: 1px #6699CC solid;">
									<div id="localImag2"><img id="preview2" <s:if test="cmspt.imagesFile2!=null"> width="300px" height="120px"  </s:if> src="<s:property value="cmspt.imagesFile2"/>" /></div>
		</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right">上传活动图片3(<span id="pic3">wp7系统</span>)：</td>

		<td width="80%">
			<input type=file name="resumefile3" id="resumefile3" onchange="javascript:setImagePreview3();" style=" border: 1px #6699CC solid;">
									<div id="localImag3"><img id="preview3" <s:if test="cmspt.imagesFile3!=null"> width="300px" height="120px"  </s:if> src="<s:property value="cmspt.imagesFile3"/>" /></div>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">上传活动图片4(<span id="pic4">MTK系统
		</span>)：</td>

		<td width="80%">
			<input type=file name="resumefile4" id="resumefile4" onchange="javascript:setImagePreview4();" style=" border: 1px #6699CC solid;">
			<div id="localImag4"><img id="preview4" <s:if test="cmspt.imagesFile4!=null"> width="300px" height="120px"  </s:if> src="<s:property value="cmspt.imagesFile4"/>" /></div>
		</td>
	</tr>
	
	

	<input name="adminType"  type="hidden"  value="<s:property value='adminType'/>"/>

	<s:if test="adminType!=0">
	<tr> 
		<td width="20%" align="right">模板选择：</td>
		<td width="80%"> 
			<INPUT TYPE="button" value="选择" onclick="" class="selBtn" style="cursor: pointer;">
		</td>
	</tr>
	
	<tr class="hid" > 
		<td width="20%" align="right"><font color="red">*</font>活动内容：</td>
		
		<td width="80%"> 
			当光标在编辑器中，按F11键切换全屏幕编辑。ESC可退出全屏幕编辑。
		</td>
	</tr>
	<tr class="hid"> 
		<td colspan="2">
			<textarea   name="cmspt.content" id="content" cols="100" rows="8" style="height:300px;"><s:property value="cmspt.content"/></textarea>
		</td>
	</tr>
	
</s:if> 
<tr class="hid" > 
		<td width="20%" align="right">页面备注：</td>
		<td width="80%"> 
		
			<textarea name="cmspt.p2" style="height:100px;width:60%"><s:property value="cmspt.p2"/></textarea>
		</td>
	</tr>
</table>


<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<INPUT class="saveBtn" onClick="Submit()" TYPE="button" value="" />
            &nbsp;&nbsp;
			<input class="backBtn"  onclick="gotoList()" type="button" value="" />
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

<br><br>

</s:form >
</div>
</BODY>


<script type="text/javascript">

function setImagePreview() {
        var docObj=document.getElementById("resumefile");
 
        var imgObjPreview=document.getElementById("preview");
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
        function setImagePreview2() {
			var docObj = document.getElementById("resumefile2");
			var imgObjPreview = document.getElementById("preview2");

			if (docObj.files && docObj.files[0]) {
				//火狐下，直接设img属性
				imgObjPreview.style.display = 'block';
				imgObjPreview.style.width = '300px';
				imgObjPreview.style.height = '120px';
				//imgObjPreview.src = docObj.files[0].getAsDataURL();
				//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式  
				imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
			} else {
				//IE下，使用滤镜
				docObj.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("localImag2");
				//必须设置初始大小
				localImagId.style.width = "300px";
				localImagId.style.height = "120px";
				//图片异常的捕捉，防止用户修改后缀来伪造图片
				try {
					localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
					localImagId.filters
							.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				} catch (e) {
					alert("您上传的图片格式不正确，请重新选择!");
					return false;
				}
				imgObjPreview.style.display = 'none';
				document.selection.empty();
			}
			return true;
		}
		function setImagePreview3() {
			var docObj = document.getElementById("resumefile3");
			var imgObjPreview = document.getElementById("preview3");

			if (docObj.files && docObj.files[0]) {
				//火狐下，直接设img属性
				imgObjPreview.style.display = 'block';
				imgObjPreview.style.width = '300px';
				imgObjPreview.style.height = '120px';
				//imgObjPreview.src = docObj.files[0].getAsDataURL();
				//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式  
				imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
			} else {
				//IE下，使用滤镜
				docObj.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("localImag3");
				//必须设置初始大小
				localImagId.style.width = "300px";
				localImagId.style.height = "120px";
				//图片异常的捕捉，防止用户修改后缀来伪造图片
				try {
					localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
					localImagId.filters
							.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				} catch (e) {
					alert("您上传的图片格式不正确，请重新选择!");
					return false;
				}
				imgObjPreview.style.display = 'none';
				document.selection.empty();
			}
			return true;
		}
		function setImagePreview4() {
				var docObj = document.getElementById("resumefile4");
			var imgObjPreview = document.getElementById("preview4");
			if (docObj.files && docObj.files[0]) {
				//火狐下，直接设img属性
				imgObjPreview.style.display = 'block';
				imgObjPreview.style.width = '300px';
				imgObjPreview.style.height = '120px';
				//imgObjPreview.src = docObj.files[0].getAsDataURL();
				//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式  
				imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
			} else {
				//IE下，使用滤镜
				docObj.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("localImag4");
				//必须设置初始大小
				localImagId.style.width = "300px";
				localImagId.style.height = "120px";
				//图片异常的捕捉，防止用户修改后缀来伪造图片
				try {
					localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
					localImagId.filters
							.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				} catch (e) {
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