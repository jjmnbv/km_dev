<meta http-equiv="X-UA-Compatible" content="IE=9" />
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>添加推广短信邮件</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link type="text/css" href="/etc/js/ueditor/themes/default/css/ueditor.css"/>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script src="/etc/js/97dater/WdatePicker.js"></script>
	 	 <script type="text/javascript" src="/etc/js/ueditor/ueditor.config.js">  </script>
	 	 <script type="text/javascript" src="/etc/js/ueditor/ueditor.all.js">  </script> 
		<script type="text/javascript">
		 $(document).ready(function(){
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

            //添加验证短信
			jQuery.validator.addMethod("smsTextlength", function(value, element) {
		 		var smsText = value; 
		 	   if(smsText.length>0){
			        return true;
				 }
		 	   else{
		 	 	   return false;
		 	 	   }
		 	   
			}, "请输入短信内容");

			jQuery.validator.addMethod("emailContextlength", function(value, element) {
		 		var emailContext = value;
		 
		 	   if(emailContext.length>0){
			        return true;
				 }
		 	   else{
		 	 	   return false;
		 	 	   }
		 	   
			}, "请输入邮件内容");

			jQuery.validator.addMethod("timingDatelength", function(value, element) {
		 		var isTime = value;
		 	   if(isTime==0){
			        return true;
				 }
		 	   else{
		 	 	   return true;
		 	 	   }
		 	   
			}, "请选择发送日期");
			
          $("#emailsmspromotionMessage").validate({
               rules: {
					
					"publicType":{required:true},	 
					"status":{required:true},
					"promotionType":{required:true},
					"channelId":{required:true},
					"smsType":{required:true},
					"smsText":{required:true,smsTextlength:true ,maxlength:250},
					"isTime":{required:true,timingDatelength:true},
					"emailContext":{required:true,emailContextlength:true },
					"timingDate":{required:true,myvalidator:true}
	        	},
	         success: function (label){
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
        function gotoList(){
        	 location.href= "/emailsmspromotion/emailsmspromotion_pageList.action";
        }  
   
        //弹出 多选账号页面
function popUpUserInfo() {
    dialog("选择账号","iframe:/logininfo/logininfo_checkboxInfoList.action?callBack=closeOpenUserInfo" ,"900px","500px","iframe");
    $("#floatBox").css('z-index',1000);
}
//关闭弹出窗口 
function closeOpenUserInfo(loginIds,loginNames){
    closeThis();
    $("#loginAccount").val(loginNames);
    $("#n_LoginId").val(loginIds);
    var j_personInfo = $('.j_personInfo');
    var $div = ' class="j_div" style="float: left;position:relative;margin:3px 5px 2px 0;white-space:nowrap;height:15px;line-height: 15px;cursor:pointer;border-radius:17px;border-style:solid;border-width:1px;font-size:14px;padding:2px 19px;border-color:#edb8b8;background-color:#ffeaea;color:#c30!important;display:inline-block;vertical-align:middle;">';
    var $em = '<em style="margin-left:-8px;vertical-align:top;display:inline-block;font-style:normal;text-decoration:none;white-space:nowrap;line-height:15px;cursor:pointer;font-size:14px;" unselectable="on">';
    var $a = '<a style="position: absolute;right: -2px;top: -1px;display: inline;text-decoration: none;font-family: verdana;border-radius: 0 17px 17px 0;font-weight: bold;padding: 2px 5px 2px 3px;border-width: 1px;border-style: solid;border-color:#edb8b8!important;color:#c30!important;" href="javascript:void(0)" hidefocus="hidefocus">x</a>';
    var aNamelist = loginNames.split(',');
    var aIdlist = loginIds.split(',');
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
//消息发布对象控制
function showType(val){
    
    if(val==1){
       
        $("#personInfo").hide();
   }
   if(val==2){
       $("#personInfo").show();
     
   }
  
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
function cole() {
     var timingDate =document.getElementById("timingDate");
    // releaseDate.value="";
   timingDate.focus();
	} 
//布类型的日期校验
function getselectvalue() {
    var rtl=document.getElementById("status");
  var i = rtl.value;
  if(i==1){
  WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})
  }else{
  WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})
  }
	} 
	
	
	//提交表单
	function gotoSubmit(){
		//校验表单内容
		var publicType = document.getElementById("publicType").value;
		var loginAccount = document.getElementById("loginAccount").value;
		if(publicType==2 && loginAccount==""){
			document.getElementById("spanCheckloginAccount").style.display="";
		}else {document.getElementById("emailsmspromotionMessage").submit();}
	}
</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户信息'" scope="request" />
		<s:set name="name" value="'推广短信邮件'" scope="request" />
		<s:set name="son_name" value="'添加'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
	   <div  style="height:90%;overflow-y:scroll; " >
		<s:form action="/emailsmspromotion/emailsmspromotion_addPromotion.action" method="POST" 
			id="emailsmspromotionMessage" namespace='emailsmspromotionMessage' name="emailsmspromotionMessage">
<s:token/>
			<!-- hidden properties -->
			<INPUT TYPE="hidden" name="isEnable" value="1">
			<!-- 数据编辑区域 -->
			<table width="80%" class="edit_table" cellpadding="3" cellspacing="0"
				border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
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
						<font color="red">*</font>推广类型：
					</td>
					<td width="80%">
						 <s:select  list="#{1:'短信'}" value="1"  onchange="changeShowType(this.value)" name="promotionType"  id="promotionType"></s:select>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						推广标题：
					</td>
					<td width="80%">
						 <input type="text" name="title" id="title" size="65"/>
					</td>
				</tr>	
				<tr  id="smsTypeId" style="display:none">
					<td width="20%" align="right">
						<font color="red">*</font>短信接口类型：
					</td>
					<td width="80%">
						<select name="smsType" id="smsType"  >		
							<option value="3"  selected="selected"> 
							 广告短信
							</option>
						</select>
					</td>
				</tr>
				<tr id="smsTextId" >
					<td width="20%" align="right"> 
						<font color="red">*</font>推广内容：
					</td>
					<td width="80%">
						<textarea rows="5" cols="20" name="smsText" id="smsText"> </textarea>
					</td>
				</tr>
				<tr id="emailContextId"  style="display:none;" >
					<td width="20%" align="right"> 
						<font color="red">*</font>推广内容：
					</td> 
					<Td width="80%">  
				<script type="text/plain" id="emailContext" name="emailContext"  style="width:800px;height:400px"> </script>
                <script type="text/javascript">  
                 var editor = new baidu.editor.ui.Editor({ textarea:'emailContext'});  
                    editor.render('emailContext');      
                      </script> 
	             </td>
				</tr>	
				<tr>
					<td width="20%" align="right">
						消息发布对象：
					</td>
					<td width="80%">
					      <s:select  list="#{1:'全部客户',2:'按具体客户发布'}" value="1"  onchange="showType(this.value)" name="publicType"  id="publicType"></s:select>
					</td>
				</tr>			
		  
		  

					<tr id="personInfo"   style="display:none;">
					<td width="20%" align="right">
						指定个人：
					</td>
					<td class='j_personInfo'>
					      <input id="n_LoginId" type="hidden" value="" name="loginId">
                          <input id="loginAccount" type="hidden" value="" name="loginAccount">
                          <input type="button" style="float:left;"  value="选择" onclick="popUpUserInfo()"/>
                          <span id ="spanCheckloginAccount" style="display:none"><font color="red">请选择指定个人！</font></span>
					</td>
				</tr>
					<tr   style="display:none;">
					<td width="20%" align="right">
						<font color="red">*</font>发布状态：
					</td>
					<td width="80%">
						<select name="status" id="status" onchange="cole()">		
							<option value="0"  selected="selected">
							 草稿
							</option>
						</select>
					</td>
				</tr>
							<tr>
					<td width="20%" align="right"  >
						<font color="red">*</font>是否定时发布：
					</td>
					<td width="80%">
						<select name="isTime" id="isTime" onchange="changeIsTime(this.value)">
							<option value="0"  selected="selected">
								否
							</option>
							<option value="1">
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
						<input type="text" name="timingDate"  id="timingDate" value="" onClick="getselectvalue()" readonly="readonly">
					 
					</td>
				</tr>
	
			</table>
			<!-- 底部 按钮条 -->
			<table width="60%" class="edit_bottom" height="30" border="0"
				cellpadding="0" cellspacing="0">
				<tr>
					<td align="left">
						<input class="saveBtn" onclick="gotoSubmit();" value=" ">
						&nbsp;&nbsp;
						<input class="backBtn" onclick="gotoList();" type="button"
							value=" ">
					</td>
					<td width="20%" align="center"></td>
				</tr>
			</table>

			<br>
			

		</s:form>
	  </div>
	</BODY>
</HTML>