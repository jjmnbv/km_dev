<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账户金额修改</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />

<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery-1.8.3.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery.validate.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/jquery.metadata.js" type="text/javascript"></Script>
<Script language="JavaScript" src="/etc/js/messages_cn.js" type="text/javascript"></Script>
<script charset="utf-8" src="/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>

<script type="text/javascript" >


function afterCreateHandler(){
    var doc = this.edit.doc; 
    var cmd = this.edit.cmd; 
    //console.log(navigator.userAgent.toLowerCase());
    //Paste in chrome
    if(KindEditor.WEBKIT){
        $(doc.body).bind('paste', function(ev){
        	var original =  ev.originalEvent;
            var file =  original.clipboardData.items[0].getAsFile();
            var reader = new FileReader();
            reader.onload = function (evt) {
                var result = evt.target.result; 
                var arr = result.split(",");
                var base64Data = arr[1];//图片base64数据
                var imgSuffix = arr[0].split(";")[0].split(":")[1].split('/')[1];//图片类型
                $.ajax({
					async:false,
					url:"/accounts/bnesAcctEnchashment_uploadPasteImg.action",
					data:{"map['imgBase64']":base64Data,"map['imgSuffix']":imgSuffix},
					type:'POST',
					dataType:'text',
					success:function(data){
						html = '<img src="' + data + '" alt="" />';
                        cmd.inserthtml(html);
                    }
                 }); 
            };
            reader.readAsDataURL(file);
        });
    }
    // Paste in firfox. IE11
    if(KindEditor.GECKO){
    	KindEditor(doc.body).bind('paste', function(ev){
            setTimeout(function(){
                var html = KindEditor(doc.body).html();
                var imgDataReg = /\"(data:image[^\"]*)\"/mg;
                if(html.search(imgDataReg) > -1){
                	var imgData = html.match(imgDataReg)[0];
                	console.log('img:'+imgData);
                	var arr = imgData.split(",");
                     var base64Data = arr[1];
                     var imgSuffix = arr[0].split(";")[0].split(":")[1].split('/')[1];//图片类型
                     $.ajax({
							async:false,
							url:"/accounts/bnesAcctEnchashment_uploadPasteImg.action",
							data:{"map['imgBase64']":base64Data,"map['imgSuffix']":imgSuffix},
							type:'POST',
							dataType:'text',
							success:function(data){
								var newHtml = html.replace(imgDataReg,"'" + data + "'");
								confirmEditor.html(newHtml);
	                        }
                      }); 
                }
            }, 100);
        });
    } 
}

//给出字符数提示.
//var confirmValBefore='';
function afterChangeHandler(){
      var limitNum = 500;  //设定限制字数,自动截取
      var tipInfo ='你还可以输入 <span style="color:red;font-weight:bold">'+(limitNum-this.count())+'</span>个字符'
      if(limitNum-this.count()>=0){
          $('#confirmMarksTip').html(tipInfo);
      }else{
    	  tipInfo ='输入的信息已超出 <span style="color:red;font-weight:bold">' + Math.abs(limitNum-this.count()) + '</span>个字符'
    	  $('#confirmMarksTip').html(tipInfo);
      }
      
      //$('#confirmMarksCount').text(limitNum-this.count());
	  /*  限制字符输入，待完善
		  if(this.count()>limitNum && confirmValBefore!= confirmEditor.text()){
			 confirmValBefore = confirmEditor.text();
			 $('#confirmMarksWarn').text('提现备注信息超过允许输入的最大字符数，\n\n请删除部分备注信息,否则无法保存！')
   	  }*/
}



function formateAmout(){
	var value = $("#amount").val();
	var amount = value.substring(0,value.indexOf(".") + 3);
	$("#amount").val(amount);
}

$().ready(function() {
	jQuery.validator.addMethod("checkAmoutLength", function(value, element) {
 		var amount = value;
 		
 	   if(amount.length>9){
	        return false;
		 }
 	   else{
 	 	   return true;
 	 	   }
 	   
	}, "输入的金额必须小于9位");
	
	jQuery.validator.addMethod("checkAmoutNum", function(value, element) {
 		var amount = value;
 		var amountnum =/^\-?([1-9][0-9]*|0)(\.[0-9]+)?$/;
 		if(amountnum.test(amount)){
 			return true;
 		}
 		else{
 			return false;
 		}
 	   
	}, "输入的金额必须为数字");
	
	jQuery.validator.addMethod("checkAmout", function(value, element) {
 		var amount = value;
 		var amountAvlibal =$("#amountAvlibal").val();
 		if((parseFloat(amount)+parseFloat(amountAvlibal))<0){
 			return false;
 		}
 		else{
 			return true;
 		}
 	   
	}, "输入扣减金额不能大于账户可用金额");
	jQuery.validator.addMethod("checkAmoutZero", function(value, element) {
 		var amount = value;

 		if(parseFloat(amount)==0){
 			return false;
 		}
 		else{
 			return true;
 		}
 	   
	}, "输入账户金额不能为0");
	
	
	
	<%--jQuery.validator.addMethod("checkAmout", function(value, element) {
 		var amount = value;
 		
 	   if(amount>0){
	        return true;
		 }
 	   else{
 	 	   return false;
 	 	   }
 	   
	}, "输入的金额必须大于0");--%>
	
$("#modifyAcotInfo").validate({
	rules: { 
		
		"amount":{required: true,checkAmoutNum: true,checkAmout: true,checkAmoutZero: true,checkAmoutLength: true},
		"content":{maxlength:120}
		},
		success: function (label){
            label.removeClass("checked").addClass("checked");
        }
	});
	
	var confirmOptions={
		width:750,
		height:165,
		pasteType : 2,
		items:['undo','redo','|','cut','copy', 'paste',
		       '|','justifyleft','justifycenter','justifyright','justifyfull','indent','outdent','|','fontname','fontsize',
		       '|','forecolor','hilitecolor','bold','italic','underline','lineheight','removeformat','|','image'],
	            afterCreate : afterCreateHandler,
	            afterChange : afterChangeHandler
		 };
	
	KindEditor.ready(function(K) {
		window.confirmEditor = K.create("textarea[name='content']",confirmOptions);
	});
});

</script>
</head>
<body >

<!-- 导航栏 -->
<s:set name="parent_name" value="'账务管理'" scope="request"/>
<s:set name="name" value="'账户后台充值'" scope="request"/>
<s:set name="son_name" value="'编辑'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form id="modifyAcotInfo"  action="/accounts/modifyAcut_saveAcoutAmout.action" method="post">
<s:token/>
<input type="hidden" id="accountTransactionId" name="accountTransactionId"   value="<s:property value='bnesAcctTransactionQuery.accountTransactionId'/>" />
<input type="hidden" id="status" name="status"   value="<s:property value='bnesAcctTransactionQuery.status'/>" />
<input type="hidden" id="type" name="type"   value="<s:property value='bnesAcctTransactionQuery.type'/>" />
<INPUT TYPE="hidden" name="modifieId" id="modifieId" value="<s:property value="#session['sysUser'].userId"/>">
<INPUT TYPE="hidden" name="accountId" id="accountId" value="<s:property value="bnesAcctTransactionQuery.accountId"/>">
<input type="hidden" id="accountNumber" name="accountNumber"   value="<s:property value='bnesAcctTransactionQuery.accountNumber'/>" />
<input type="hidden" id="amountAvlibal" name="amountAvlibal"   value="<s:property value='bnesAcctTransactionQuery.amountAvlibal'/>" />
<table width="90%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="2" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
		</td>
	</tr>
	</s:if>
	<tr> 
		<th colspan="100" align="left" class="edit_title">账户金额信息修改</th>
	</tr>
	  <tr> 
		<td width="25%"  class="eidt_rowTitle"><font color="red">*</font>会员账号：：</td>
		<td width="75%"> 
		<s:property value="bnesAcctTransactionQuery.accountLogin"/>
		</td>
	</tr>
	<tr>
		<td width="25%"  class="eidt_rowTitle"><font color="red">*</font>手机号码：</td>
		<td width="75%"> 
		<s:property value="bnesAcctTransactionQuery.mobile"/>
		</td>
	</tr>
	<tr>
		<td width="25%"  class="eidt_rowTitle">
		账户金额：
		</td>
		<td width="75%">
			<s:property value="%{formatDouble(bnesAcctTransactionQuery.AccountAmount)}"/>
		</td>
	</tr>
	<tr>
		<td width="25%"  class="eidt_rowTitle">
		账户可用金额：
		</td>
		<td width="75%">
			<s:property value="%{formatDouble(bnesAcctTransactionQuery.amountAvlibal)}"/>
		</td>
	</tr>
	  <tr> 
		<td width="25%"  class="eidt_rowTitle">账户冻结金额：</td>
		<td width="75%"> 
			<s:property value="%{formatDouble(bnesAcctTransactionQuery.amountFrozen)}"/>
		</td>
	</tr>
	
	 <tr> 
		<td width="25%"  class="eidt_rowTitle"><font color="red">*</font>账户金额修改流水号：</td>
		<td width="75%"> 
			<s:property value="bnesAcctTransactionQuery.accountNumber"/> 
		</td>
		
	</tr>
	<tr>
		<td width="25%"  class="eidt_rowTitle"><font color="red">*</font>修改金额：</td>
		<td width="75%"> 
			<input id="amount" name="amount" id="input_style"  type="text" onkeyup="this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3')" value="<s:property value='%{formatDouble(bnesAcctTransactionQuery.amount)}'/>" />
		</td>
	</tr>

	<tr> 
		<td width="25%"  class="eidt_rowTitle">账户金额修改备注：	<br/><span id="confirmMarksTip"></span></td>
		<td width="75%"> 		
			<textarea id="content" name="content"  cols="1" rows="5" ></textarea>
		</td>
	</tr>
	</table>


<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<input class="saveBtn" type="submit"  value=" ">
            &nbsp;&nbsp;
			<input class="backBtn"  onclick="gotoList()" type="button" value=" ">
		</td>
		<td width="20%" align="center"></td>
	</tr>
	
	
</table>
<br><br>

</s:form>
<SCRIPT LANGUAGE="JavaScript">
function gotoList(){
    location.href="/accounts/modifyAcut_showAcotAmout.action";
}
</SCRIPT>
</body>
</html>