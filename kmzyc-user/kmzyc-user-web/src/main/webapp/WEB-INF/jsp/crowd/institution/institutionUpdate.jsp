<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改账户信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<script src="/etc/js/dialog.js"></script>
<Script language="JavaScript" src="/etc/js/jquery-1.8.3.js"
	type="text/javascript"></Script>

<script type="text/javascript" src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript" src="/etc/js/validate/messages_cn.js"></script>

<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
</head>
<body>
	<div id=m
		style="position: absolute; top: 0; left: 0; visibility: hidden; padding: 3px; border: 1px solid #528AC6; background-color: #ffdfd0;">
		<img
			src="http://img.km1818.com/product/upload/product/2156/3294/20150114161202661751_3294_1.jpg"
			width="400" height="300">
	</div>
	<script type="text/javascript">
		
	</script>

	<s:form id="frm" action="/crowd/updateInstitutionInfo.action"
		method="POST" enctype="multipart/form-data">

		<!-- hidden properties -->
		<INPUT TYPE="hidden" name="institutionInfo.id"
			value="<s:property value='institutionInfo.id'/>">
		<INPUT TYPE="hidden" name="institutionInfo.loginId"
			value="<s:property value='institutionInfo.loginId'/>">
		<input type="hidden" id="edit" name="edit"
			value='<s:property value="edit"/>'>


		<!-- 导航栏 -->
		<s:set name="parent_name" value="'机构管理'" scope="request" />

		<s:set name="name" value="'机构信息编辑'" scope="request" />



		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>


		<!-- 数据编辑区域 -->
		<table width="60%" class="edit_table" cellpadding="3" cellspacing="0"
			border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">

			<tr>
				<td colspan="2" align="left" class="edit_title">机构信息</td>
			</tr>
			<tr>
				<td width="20%" align="right">机构编码：</td>
				<td width="80%"><s:property
						value='institutionInfo.institutionCode' /><input
					name="institutionInfo.institutionCode" id="institutionCode"
					value="<s:property value='institutionInfo.institutionCode'/>"
					type="hidden"  maxlength="80"/></td>
			</tr>
			<tr>
				<td width="20%" align="right">机构名称：</td>
				<td width="80%"><input name="institutionInfo.institutionName" maxlength="16"
					id="institutionName" type="text"
					value="<s:property escape="false" value='institutionInfo.institutionName'/>" /></td>
			</tr>

			<tr>
				<td width="20%" align="right">机构地址：</td>
				<td width="80%"><input
					name="institutionInfo.institutionAddress" id="institutionAddress"
					type="text" maxlength="80"
					value="<s:property escape="false" value='institutionInfo.institutionAddress'/>" />
				</td>
			</tr>
			<tr>
				<td width="20%" align="right">机构联系人：</td>
				<td width="80%"><input
					name="institutionInfo.institutionContactor" maxlength="16"
					id="institutionContactor" type="text"
					value="<s:property escape="false" value='institutionInfo.institutionContactor'/>" />
				</td>
			</tr>
			<tr>
				<td width="20%" align="right">机构联系电话：</td>
				<td width="80%"><input
					name="institutionInfo.institutionPhonenumber"
					id="institutionPhonenumber" type="text"
					value="<s:property value='institutionInfo.institutionPhonenumber'/>" />
				</td>
			</tr>
			<tr>
				<td width="20%" align="right">验证手机：</td>
				<td width="80%"><s:property value='institutionInfo.mobile'/></td>
			</tr>
			<tr>
				<td width="20%" align="right">银行账户号：</td>
				<td width="80%"><s:property value='institutionInfo.bankAccount' /></td>
			</tr>
			<tr>
				<td width="20%" align="right">开户行：</td>
				<td width="80%"><s:property value='institutionInfo.bankName' /></td>
			</tr>
			<tr>
				<td width="20%" align="right">银行账户名：</td>
				<td width="80%"><s:property value='institutionInfo.bankUname' /></td>
			</tr>
			<tr>
				<td width="20%" align="right">机构注册分利现金：</td>
				<td width="80%"><input name="institutionInfo.registRebate"
					id="registRebate" type="text"
					value="<s:property value='institutionInfo.registRebate'/>" />元/人</td>
			</tr>
			<tr>
				<td width="20%" align="right">推广有效期：</td>
				<td><input type="text" id="d523" readonly="readonly"
					class="Wdate"
					value="<s:date name = 'institutionInfo.spreadStartDate' format='yyyy-MM-dd HH:mm:ss' />"
					name="institutionInfo.spreadStartDate"
					onclick="WdatePicker({el:'d523',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />

					至<input type="text" id="d524" readonly="readonly" class="Wdate"
					value="<s:date name = 'institutionInfo.spreadEndDate' format='yyyy-MM-dd HH:mm:ss' />"
					name="institutionInfo.spreadEndDate" id="endTime"
					onclick="WdatePicker({el:'d524',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
				</td>
			</tr>
			<tr>
				<td width="20%" align="right">引荐注册总人数：</td>
				<td width="80%"><s:property value="institutionInfo.referrerNum" /></td>
			</tr>
			<tr>
				<td width="20%" align="right">机构注册分利总现金：</td>
				<td width="80%"><s:property value="institutionInfo.referrerSum" /></td>
			</tr>
			<tr>
				<td width="20%" align="right">业务员：</td>
				<td width="80%">
				<s:property value='institutionInfo.bagmanName' />
				<input id="bagmanId" value="<s:property value='institutionInfo.bagmanId' />" style="display:none"></input>
				</td>
				
			</tr>
			<tr>
				<td width="20%" align="right">创建日期：</td>
				<td width="80%"><s:date name="institutionInfo.createDate"
						format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td width="20%" align="right">认证图片：</td>
				<td width="80%"><s:if test="image1!=null"><img width="100" height="100" id="src1"
					src="<s:property value='imgUrl' /><s:property value='image1' />"
					style="cursor: pointer;" title=""></s:if> <s:file name="upfile1"
						id="myFile1" class="rg-input rg-file" placeholder="" /> <input
					value="清空" type="button" onclick="disImage('1')"><br>
					<s:if test="image2!=null"><img width="100" height="100" id="src2"
					src="<s:property value='imgUrl' /><s:property value='image2' />"
					style="cursor: pointer;" title=""></s:if> <s:file name="upfile2"
						id="myFile2" class="rg-input rg-file" placeholder="" /><input
					value="清空" type="button" onclick="disImage('2')"><br>
					<s:if test="image3!=null"><img width="100" height="100" id="src3"
					src="<s:property value='imgUrl' /><s:property value='image3' />"
					style="cursor: pointer;" title=""> </s:if><s:file name="upfile3"
						id="myFile3" class="rg-input rg-file" placeholder="" /><input
					value="清空" type="button" onclick="disImage('3')"> <INPUT
					TYPE="hidden" name="image1" value="<s:property value='image1'/>"
					id="image1"> <INPUT TYPE="hidden" name="image2" id="image2"
					value="<s:property value='image2'/>"> <INPUT TYPE="hidden"
					id="image3" name="image3" value="<s:property value='image3'/>">
					<INPUT TYPE="hidden" name="id1" value="<s:property value='id1'/>">
					<INPUT TYPE="hidden" name="id2" value="<s:property value='id2'/>">
					<INPUT TYPE="hidden" name="id3" value="<s:property value='id3'/>">
				</td>
			</tr>
			<tr>
				<td width="20%" align="right">审核人：</td>
				<td width="80%"><s:property value='institutionInfo.auditorName' /></td>
			</tr>
			<tr>
				<td width="20%" align="right">审核日期：</td>
				<td width="80%"><s:date name="institutionInfo.auditeDate"
						format="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<td width="20%" align="right">审核备注：</td>
				<td width="80%"><input name="institutionInfo.auditeMemo"
					id="auditeMemo" type="text" maxlength="80"
					value="<s:property
						value='institutionInfo.auditeMemo' />" maxlength="80" /></td>
			</tr>
			<tr>
				<td width="20%" align="right">状态：</td>
				<td width="80%"><s:radio name="institutionInfo.status"
						id="status" list="#{1:'有效',0:'无效'}" /></td>
			</tr>
		</table>


		<!-- 底部 按钮条 -->
		<table width="60%" class="edit_bottom" height="30" border="0"
			cellpadding="0" cellspacing="0">
			<tr>
				<td align="left"><input class="saveBtn" type="button"
					onclick="submitForm()" value=" "> &nbsp;&nbsp; <input
					class="backBtn" onclick="gotoList()" type="button" value=" ">
				</td>
				<td width="20%" align="center"></td>
			</tr>
		</table>

		<br>
		<br>

	</s:form>

	<SCRIPT LANGUAGE="JavaScript">
		$("#src1").mouseover(function() {
			bigger("src1");
		});
		$("#src1").mouseleave(function() {
			smaller("src1");
		});
		$("#src2").mouseover(function() {
			bigger("src2");

		});
		$("#src2").mouseleave(function() {
			smaller("src2");
		});
		$("#src3").mouseover(function() {
			bigger("src3");

		});
		$("#src3").mouseleave(function() {
			smaller("src3");
		});
		function bigger(id) {

			document.getElementById(id).style.width = '400px';
			document.getElementById(id).style.height = '400px';
		}
		function smaller(id) {

			document.getElementById(id).style.width = '100px';
			document.getElementById(id).style.height = '100px';
		}
		jQuery.validator.addMethod("checkPhone", function(value, element) {
			if (($("#mobile").val()).length > 0) {
				if (!isphone($("#mobile").val())) {

					return false;
				}
			}
			return true;
		}, '输入手机号格式有误！');
		jQuery.validator.addMethod("checkPhone1", function(value, element) {
			if (($("#institutionPhonenumber").val()).length > 0) {
				if (!IsTelephone($("#institutionPhonenumber").val())) {

					return false;
				}
			}
			return true;
		}, '电话号码正确格式为‘区号-号码’ ！');
		jQuery.validator.addMethod("checkPrice", function(value, element) {
			if (($("#registRebate").val()).length > 0) {
				if (!testPrice($("#registRebate").val())) {

					return false;
				}
			}
			return true;
		}, '请输入正确的金额！');
		jQuery.validator.addMethod("checkTime",
				function(value, element) {
					var startTime = $("#d523").val();
					var now = new Date();
					var start = new Date(startTime.replace("-", "/").replace(
							"-", "/"));
					var endTime = value;
					var end = new Date(endTime.replace("-", "/").replace("-",
							"/"));
					if (end < now) {
						return false;
					}
					if (end < start) {
						return false;
					}
					return true;

				}, "结束时间不能早于开始时间,且不能早于当前时间！");
		function submitForm() {
			$("#frm").validate({
				rules : {
					"institutionInfo.institutionCode" : {
						required : true,
						maxlength : 80
					},
					"institutionInfo.mobile" : {

						checkPhone : true

					},
					"institutionInfo.registRebate" : {
						required : true,
						checkPrice : true
					},
					"institutionInfo.spreadEndDate" : {
						required : true,
						checkTime : true
					},
					"institutionInfo.institutionName" : {
						required : true,
						maxlength : 16
					},
					"institutionInfo.institutionAddress" : {
						required : true,
						maxlength : 80
					},
					"institutionInfo.institutionContactor" : {
						required : true,
						maxlength : 16
					},
					"institutionInfo.institutionPhonenumber" : {
						required : true,
						checkPhone1 : true
					}

				},
				messages : {
					"institutionInfo.institutionCode" : '请输入合法的机构编码'
				},
				success : function(label) {
					label.removeClass("checked").addClass("checked");
				}
			});

			var image1 = getName("myFile1");
			var image2 = getName("myFile2");
			var image3 = getName("myFile3");

			if (image1 != "" && image1 != undefined) {
				if (isPic(image1)) {
					$("#image1").val(image1);
				} else {

					return;
				}
			}

			if (image2 != "" && image2 != undefined) {
				if (isPic(image2)) {
					$("#image2").val(image2);
				} else {

					return;
				}
			}

			if (image3 != "" && image3 != undefined) {
				if (isPic(image3)) {
					$("#image3").val(image3);
				} else {

					return;
				}
			}
			var Url = "/crowd/checkInsName.action";                
            $.ajax({
                url: Url,
                type : "GET",  
                data: {
                    'institutionName': $('#institutionName').val(),
                    'institutionCode': $('#institutionCode').val(),
                    'bagmanId':  $('#bagmanId').val()
                },
                success:function(data, status){        //服务器响应成功时的处理函数
                	var code = data.substring(data.length-2,data.length-1);
                	var message = data.substring(9,data.length-10);
                    if(code == "1"||code == "2"){
                    	alert(message);
                        $('#institutionName').focus();
                    }else{
                    	$("#frm").submit();
                    }
                },
                error:function(data, status, e){ //服务器响应失败时的处理函数
                	alert("提交出错，请重试！");
                }
            });
			
		}
		function gotoList() {
			location.href = "/crowd/queryInstitutionList.action";

		}

		function getName(id) {
			var myFile = document.getElementById(id).value;

			var length = myFile.length;
			var x = myFile.lastIndexOf("\\");
			x++;
			var fileName = myFile.substring(x, length);
			return fileName;
		}
		//图片格式校验
		function isPic(pic) {
			if (pic == "")
				return true;
			var pos = pic.lastIndexOf(".");
			var lastname = pic.substring(pos, pic.length);
			var resultName = lastname.toLowerCase();
			if (resultName != ".jpg" && resultName != ".png"
					&& resultName != ".bmp" && resultName != ".ico") {
				alert("请上传jpg/png/ico/bmp格式的图片！");
				return false;
			}
			return true;
		}
		/*判断输入是否为合法的手机号码*/
		function isphone(inputString) {
			var partten = /^1[3,5,8]\d{9}$/;
			var fl = false;
			if (partten.test(inputString)) {
				//alert('是手机号码');
				return true;
			} else {
				return false;
				//alert('不是手机号码');
			}
		}
		//金额校验
		function testPrice(str) {
			var re = /^[0-9]\d{0,6}([.][0-9]\d{0,1})?$/;
			return re.test(str);
		}
		function disImage(index) {

			var id = "myFile" + index;

			$("#" + id).after($("#" + id).clone().val("")).remove();
		}
		//校验电话号
		function IsTelephone(mobileString)
		{
			   var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
	            var isMob=/^((\+?86)|(\(\+86\)))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
	            if(isMob.test(mobileString)||isPhone.test(mobileString)){
	                return true;
	            }else{
	                return false;
	            }
		}
		
		 //机构名称失焦、 检查机构名称
        $(document).on('blur', '#institutionName',
            function() {
                if(this.value==""){
                    alert('请输入机构名称！');
                    return;
                }
                var Url = "/crowd/checkInsName.action";                
                $.ajax({
                    url: Url,
                    type : "GET",  
                    data: {
                        'institutionName': $('#institutionName').val(),
                        'institutionCode': $('#institutionCode').val(),
                        'bagmanId':  $('#bagmanId').val()
                    },
                    success:function(data, status){        //服务器响应成功时的处理函数
                    	var code = data.substring(data.length-2,data.length-1);
                    	var message = data.substring(9,data.length-10);
                        if(code == "1"||code == "2"){
                        	alert(message);
                            $('#institutionName').focus();
                        }
                    },
                    error:function(data, status, e){ //服务器响应失败时的处理函数
                    	alert("提交出错，请重试！");
                    }
                });
        });
		
	</SCRIPT>
</BODY>
<!-- 消息提示页面 -->
<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</HTML>