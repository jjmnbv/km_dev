<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>修改推荐短信邮件</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet"type="text/css" />
			<link type="text/css" href="/etc/js/ueditor/themes/default/css/ueditor.css"/>
		
</script>
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js">
</script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js">
</script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js">
</script>
		<script type="text/javascript" src="/etc/js/messages_cn.js">
</script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script src="/etc/js/dialog.js">
</script>
	 <script type="text/javascript" src="/etc/js/ueditor/ueditor.config.js">  </script>
	 	 <script type="text/javascript" src="/etc/js/ueditor/ueditor.all.js">  </script>
 

		<script type="text/javascript">
 
		
$(document).ready(function() {
	var oldNameArr = $('#loginAccount').val();
	var oldIdArr = $('#n_LoginId').val();	 
	 var j_personInfo = $('.j_personInfo');
	    var $div = ' class="j_div" style="float: left;position:relative;margin:3px 5px 2px 0;white-space:nowrap;height:15px;line-height: 15px;cursor:pointer;border-radius:17px;border-style:solid;border-width:1px;font-size:14px;padding:2px 19px;border-color:#edb8b8;background-color:#ffeaea;color:#c30!important;display:inline-block;vertical-align:middle;">';
	    var $em = '<em style="margin-left:-8px;vertical-align:top;display:inline-block;font-style:normal;text-decoration:none;white-space:nowrap;line-height:15px;cursor:pointer;font-size:14px;" unselectable="on">';
	    var $a = '<a style="position: absolute;right: -2px;top: -1px;display: inline;text-decoration: none;font-family: verdana;border-radius: 0 17px 17px 0;font-weight: bold;padding: 2px 5px 2px 3px;border-width: 1px;border-style: solid;border-color:#edb8b8!important;color:#c30!important;" href="javascript:void(0)" hidefocus="hidefocus">x</a>';
	    var aNamelist = oldNameArr.split(',');
	    var aIdlist = oldIdArr.split(',');
	    var str = '';
	    for(var i=0; i<aNamelist.length; i++){
	    	str += '<div data-name='+aNamelist[i]+' data-id='+aIdlist[i] + $div + $em + aNamelist[i] + '</em>' + $a + '</div>';
	         
	    }
	    var reDiv = j_personInfo.find('div.j_div');
	    if(reDiv.length){
	    	j_personInfo.find('div.j_div').remove();
	     }
	     if(oldNameArr)
	     {
	    j_personInfo.prepend(str);
	     }

	var  publicType=$("#publicType").val();
	 if(publicType==1){
	       
	        $("#personInfo").hide();
	   }
	var  promotionType= $("#promotionType").val(); 
	   if(promotionType==1){
	         $("#smsTextId").show();
	         $("#emailContextId").hide();
	    }
	    if(promotionType==2){
	        $("#emailContextId").show();
	        $("#smsTextId").hide();
	        $("#smsTypeId").hide();
	    }

   var isTime=$("#isTime").val(); 
    if(isTime==1)
    {
    	 $("#timingDateId").show();
     }
    else{
    	 $("#timingDateId").hide();
      }
      var customError = "";
			$.validator.addMethod("myvalidator", function(value, element) {
			   var returnVal = true;
			    var rtl=document.getElementById("status");
  				var i = rtl.value;
  				var d2=new Date();
				var d3=d2.getFullYear()+"-"+(d2.getMonth()+1)+"-"+d2.getDate();
				var d4=new Date(d3.replace(/-/g, "/"));
				var d1=new Date(value.replace(/-/g, "/"));
  				if(i==0){
  				   if(Date.parse(d4) - Date.parse(d1)>0){
  				customError = "待发布信息，发布日期不能小于当前系统日期";
					returnVal = false;
					}
  				}
  				if(i==1){
  				   if(Date.parse(d4) - Date.parse(d1)<0){
  				    customError = "已发布信息，发布日期不能大于当前系统日期";
					returnVal = false;
					}
  				}
				$.validator.messages.myvalidator = customError;
				return returnVal;
			}, "error " + customError);
	$("#emailsmspromotionMessageForm").validate({
		rules : {
		"emailSmsPromotion.title":{required:true,maxlength:50},
		"publicType":{required:true},	 
		"emailSmsPromotion.status":{required:true},
		"emailSmsPromotion.promotionType":{required:true},
		"emailSmsPromotion.smsText":{required:true ,maxlength:250},
		"emailSmsPromotion.isTime":{required:true},
		"emailSmsPromotion.emailContext":{required:true},
		"emailSmsPromotion.channelId":{required:true},
		"emailSmsPromotion.timingDate":{required:true,myvalidator:true}
		},
		success : function(label) {
			label.removeClass("checked").addClass("checked");
		}
	});

	   $('.j_personInfo').on('click','div.j_div',function(){ 
			var t = $(this);
			var name = t.attr('data-name');
			var id = t.attr('data-id');
			var oldNameArr = $('#loginAccount').val().split(',');
			var oldIdArr = $('#n_LoginId').val().split(',');	 
			for(var i=0;i<oldNameArr.length;i++){
                if(name == oldNameArr[i] ){
               	 oldNameArr.splice(i,1);
                }
		     }
			for(var j=0;j<oldIdArr.length;j++){
               if(id == oldIdArr[j] ){
               	oldIdArr.splice(j,1);
               }
		     }
		     t.remove();
		     $("#loginAccount").val(oldNameArr.join(','));
		     $("#n_LoginId").val(oldIdArr.join(','));
		     
     });

});


//消息发布对象控制
function showType(val){
    
    if(val==1){
       
        $("#personInfo").hide();
   }
   if(val==2){
       $("#personInfo").show();
     
   }
  
}


/**通过customer类别id 查询子客户类别 **/
function querySonCustomerType(id, value) {
	$.ajax( {
		async : false,
		url : "/growing/userLevel_ajaxOperateCustomerType.action",
		type : "POST",
		data : "customerId=" + id,
		dataType : "json",
		success : function(data) {
			if ((data != null || data != "") && data.length > 0) {
				$("#customer option").remove();
				$("#customer").show();
				for ( var i = 0; i < data.length; i++) {
					if (value != "" && value == data[i].customerTypeId) {
						$("#customer").append(
								"<option value='" + data[i].customerTypeId
										+ "' selected>" + data[i].name
										+ "</option>");
					} else {
						$("#customer").append(
								"<option value='" + data[i].customerTypeId
										+ "'>" + data[i].name + "</option>");
					}
				}
			} else {
				$("#customer option").remove();
				$("#customer").hide();
			}
		}
	});
 
}

function  changeIsTime(val)
{
	if(val==1)
	{
	    $("#timingDateId").show();
	}
	else{
		 $("#timingDateId").hide();
		}


}

//弹出 多选账号页面
function popUpUserInfo() {
	dialog(
			"选择账号",
			"iframe:/logininfo/logininfo_checkboxInfoList.action?callBack=closeOpenUserInfo",
			"900px", "500px", "iframe");
	 $("#floatBox").css('z-index',1000);
}
//关闭弹出窗口 
function closeOpenUserInfo(loginIds, loginNames) {
	 closeThis(); 
	$("#loginAccount").val(loginNames);
	$("#n_LoginId").val(loginIds);
	var oldNameArr = $('#loginAccount').val();
	var oldIdArr = $('#n_LoginId').val();	 
	 var j_personInfo = $('.j_personInfo');
	    var $div = ' class="j_div" style="float: left;position:relative;margin:3px 5px 2px 0;white-space:nowrap;height:15px;line-height: 15px;cursor:pointer;border-radius:17px;border-style:solid;border-width:1px;font-size:14px;padding:2px 19px;border-color:#edb8b8;background-color:#ffeaea;color:#c30!important;display:inline-block;vertical-align:middle;">';
	    var $em = '<em style="margin-left:-8px;vertical-align:top;display:inline-block;font-style:normal;text-decoration:none;white-space:nowrap;line-height:15px;cursor:pointer;font-size:14px;" unselectable="on">';
	    var $a = '<a style="position: absolute;right: -2px;top: -1px;display: inline;text-decoration: none;font-family: verdana;border-radius: 0 17px 17px 0;font-weight: bold;padding: 2px 5px 2px 3px;border-width: 1px;border-style: solid;border-color:#edb8b8!important;color:#c30!important;" href="javascript:void(0)" hidefocus="hidefocus">x</a>';
	    var aNamelist = oldNameArr.split(',');
	    var aIdlist = oldIdArr.split(',');
	    var str = '';
	    for(var i=0; i<aNamelist.length; i++){
	    	str += '<div data-name='+aNamelist[i]+' data-id='+aIdlist[i] + $div + $em + aNamelist[i] + '</em>' + $a + '</div>';
	         
	    }
	    var reDiv = j_personInfo.find('div.j_div');
	    if(reDiv.length){
	    	j_personInfo.find('div.j_div').remove();
	     }
	    j_personInfo.prepend(str);

}
function cole() {
     var timingDate =document.getElementById("timingDate");
     timingDate.focus();
	} 
//布类型的日期校验
function getselectvalue() {
    var rtl=document.getElementById("status");
  var i = rtl.value;
  if(i==1){
  WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:(new Date())})
  }else{
  WdatePicker({dateFmt:'yyyy-MM-dd',minDate:(new Date())})
  }
	}

function changeShowType(val){
    if(val==1){
         $("#smsTextId").show();
         $("#emailContextId").hide();
    }
    if(val==2){
        $("#emailContextId").show();
        $("#smsTextId").hide();
        $("#smsTypeId").hide();
    }
   
}


</script>
	</head>

	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户信息'" scope="request" />
		<s:set name="name" value="'推广短信邮件'" scope="request" />
		<s:set name="son_name" value="'修改'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div style="height: 90%; overflow-y: scroll;">
			<INPUT TYPE="hidden" name="isEnable" value="1">
			<s:form action="/emailsmspromotion/emailsmspromotion_edit.action" id="emailsmspromotionMessageForm"
				method="POST" namespace='emailsmspromotionMessageForm'  name="emailsmspromotionMessageForm">
				<s:token/>
				<!-- 数据编辑区域 -->
				<table width="80%" class="edit_table" cellpadding="3"
					cellspacing="0" border="1" bordercolor="#C7D3E2"
					style="border-collapse: collapse">
					<!-- error message -->
					<s:if test="rtnMessage != null">
						<tr>
							<td colspan="2" align="center">
								<font color="red"><s:property value='rtnMessage' /> </font>

							</td>
						</tr>
					</s:if>
					<tr>
						<td width="20%" align="right">
							<font color="red">*</font>推广标题：
						</td>
						<td width="80%">
							<input type="hidden" name="emailSmsPromotion.promotionId"
								value="<s:property value='emailSmsPromotion.promotionId'/>">
							<input type="text" name="emailSmsPromotion.title" id="title"
								value="<s:property value='emailSmsPromotion.title' />" />

						</td>
					</tr>
							<tr>
						<td width="20%" align="right">
							<font color="red">*</font>推广类型：
						</td>
						<td width="80%">
							<select name="emailSmsPromotion.promotionType" onchange="changeShowType(this.value)" id="promotionType">
								<option value="1"
								<s:if test="emailSmsPromotion.promotionType==1">selected</s:if>>
								  短信
								</option>
								<!-- <option value="2"
									<s:if test="emailSmsPromotion.promotionType==2">selected</s:if>>
								 邮件
								</option>  -->
							</select>
						</td>
					</tr>
					<tr id="smsTypeId" style="display:none">
					<td width="20%" align="right">
						<font color="red">*</font>短信接口类型：
					</td>
					<td width="80%">
						<select name="emailSmsPromotion.smsType" id="smsType">		
					 
							<option value="3"  <s:if test="emailSmsPromotion.smsType==3"   >selected</s:if>>
							 广告短信
							</option>
						</select>
					</td>
				</tr>
					<tr  id="smsTextId">
						<td width="20%" align="right">
							<font color="red">*</font>短信内容：
						</td>
						<td width="80%">
							<textarea rows="5" cols="20" id="smsText" name="emailSmsPromotion.smsText"><s:property value='emailSmsPromotion.smsText' /></textarea>
						</td>
					</tr>
				<%--删除邮件 <tr id="emailContextId"  style="display:none;" >
					<td width="20%" align="right"> 
						<font color="red">*</font>邮件内容：
					</td>
					<td width="80%">
						<textarea rows="5" cols="20"   style="width:800px;height:400px" name="emailSmsPromotion.emailContext" id="emailContext"><s:property value='emailSmsPromotion.emailContext' /></textarea>
					</td>
				 <script type="text/javascript">  
                 var editor = new baidu.editor.ui.Editor();  
                    editor.render('emailContext');  
           
				 </script>                                                            
					</td>
				</tr>    --%>   
			
					<td width="20%" align="right">
						消息发布对象：
					</td>
			         			<td width="80%">
		     
		     <select name="emailSmsPromotion.publicType"  onchange="showType(this.value)"  id="publicType">
								<option value="1" <s:if test="emailSmsPromotion.publicType==1">selected</s:if>>
								 全部客户
								</option>
								<option value="2" <s:if test="emailSmsPromotion.publicType==2">selected</s:if>>
									按具体客户发布
								</option>
							</select>
							</td>
						<tr  id="personInfo"  >
							<td width="20%" align="right">
								指定个人：
							</td>
							<td width="80%" class='j_personInfo'>
								<input id="n_LoginId"  type="hidden" value="<s:property value='loginIds'/>" name="loginIds">
								<input id="loginAccount" type="hidden" value="<s:property value='loginNames'/>" name="loginNames"	readonly="readonly" style="width:400">
								<input type="button"  style="float:left;" value="选择" onclick="popUpUserInfo();" />
							</td>
						</tr>
			

					<tr   style="display:none;">
						<td width="20%" align="right">
							<font color="red">*</font>发布状态：
						</td>
						<td width="80%">
						
						
						
						
						<select name="emailSmsPromotion.status" id="status" onchange="cole()">		
							<option value="0"<s:if test="emailSmsPromotion.status==0">selected</s:if>>
							 草稿
							</option>
					 
							</select>
						
						 
						</td>
					</tr>
					<tr>
						<td width="20%" align="right">
							<font color="red">*</font>是否定时发布：
						</td>
						<td width="80%">
							<select name="emailSmsPromotion.isTime"  onchange="changeIsTime(this.value)"  id="isTime">
								<option value="0"
									<s:if test="emailSmsPromotion.isTime==0">selected</s:if>>
									否
								</option>
								<option value="1"
									<s:if test="emailSmsPromotion.isTime==1">selected</s:if>>
									是
								</option>
							</select>
						</td>
					</tr>
					<tr id="timingDateId"   style="display:none;">
						<td width="20%" align="right">
							<font color="red">*</font>发布日期：
						</td>
						<td width="80%">
							<input type="text" name="emailSmsPromotion.timingDate" 
								value="<s:date name="emailSmsPromotion.timingDate" format="yyyy-MM-dd HH:mm:ss" />"
								onClick="getselectvalue()" id="timingDate" readonly="readonly">
							 
						</td>
					</tr>
					<tr>
						<td width="20%" align="right">
							状态：
						</td>
						<td width="80%">
							<s:if test="emailSmsPromotion.status==4">已发布</s:if>
                                <s:if test="emailSmsPromotion.status==3">审核通过</s:if>
                                <s:if test="emailSmsPromotion.status==2">审核拒绝</s:if>
								<s:if test="emailSmsPromotion.status==1">待审核</s:if>
								<s:if test="emailSmsPromotion.status==0">草稿</s:if>
						</td>
					</tr>
					
				</table>


				<!-- 底部 按钮条 -->
				<table width="60%" class="edit_bottom" height="30" border="0"
					cellpadding="0" cellspacing="0">
					<tr>
						<td align="left">
							<input class="saveBtn" type="submit" value=" ">
							&nbsp;&nbsp;
							<input class="backBtn" onclick="gotoList()" type="button"
								value=" ">
						</td>
						<td width="20%" align="center"></td>
					</tr>
				</table>

				<br>
				<br>

			</s:form>
		</div>
		<SCRIPT LANGUAGE="JavaScript">
<!--
function gotoList(){
    location.href="/emailsmspromotion/emailsmspromotion_pageList.action";
}

</SCRIPT>
	</BODY>
</HTML>