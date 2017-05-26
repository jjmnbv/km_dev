<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="/etc/css/autocompletestyles.css">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css">
<title>机构编码管理</title>
</head>
<body>
	<s:set name="parent_name" value="'机构管理'" scope="request" />
	<s:set name="name" value="'机构码管理'" scope="request" />
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
	<form action="" id="f_getCode">
		<table width="98%" class="content_table" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td align="right">编码张数：</td>
				<td><input type="text" name="_codeCount" id="_codeCount"
					style="width: 150px" required> <span style="color: red"
					id="tip_count"></span></td>
                    				<td align="right">业务员：</td>
				<td><input type="text" id="autocomplete" style="width: 150px" name="_bagmanName"
				placeholder="请选择业务员" required> <input type="hidden" name="_bagmanId"
					id="bagmanId"> <span style="color: red" id="tip_name"></span>
				</td>
                <td width="60%" align="left"><input type="button" class="btn-custom" value="领取机构编码" id="getCodes"></td>
			</tr>
		</table>

	</form>
	<form action="/crowd/instCodesManage.action" id="f_instCodesManage">
		<table width="98%" class="content_table" align="center"
			cellpadding="0" cellspacing="0">
			<tr>
				<td width="20%">业务员： <span style="margin-right: 25px;"></span><input
					type="text" maxlength="10" name="qrcodeApplyRecord.bagmanName"
					size="15" value="${qrcodeApplyRecord.bagmanName }"></td>
				<td width="40%">领取时间： <span style="margin-right: 10px;"></span><input
					type="text" id="d523" readonly class="Wdate" size="15" style="width: 158px"
					value="<s:date name = 'qrcodeApplyRecord.createStart' format='yyyy-MM-dd HH:mm:ss' />"
					name="qrcodeApplyRecord.createStart"
					onclick="WdatePicker({el:'d523',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />

					<span style=""></span>至 <span
					style=""></span><input type="text" id="d524"  style="width: 158px"
					readonly="readonly" class="Wdate" size="15"
					value="<s:date name = 'qrcodeApplyRecord.createEnd' format='yyyy-MM-dd HH:mm:ss' />"
					name="qrcodeApplyRecord.createEnd"
					onclick="WdatePicker({el:'d524',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
				<td width="40%"><input type="button" class="queryBtn"
					id="queryList"></td>

			</tr>
		</table>

		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center" width="10%">业务员</th>
				<th align="center" width="15%">编码序列号起</th>
				<th align="center" width="15%">编码序列号止</th>
				<th align="center" width="10%">领取张数</th>
				<th align="center" width="20%">领取时间</th>
				<th align="center" width="15%">操作人</th>
				<th align="center" width="15%">操作</th>

			</tr>
			<c:forEach items="${page.dataList }" var="record">
				<tr>
					<td align="center">${record.bagmanName }</td>
					<td align="center">${record.minCode }</td>
					<td align="center">${record.maxCode }</td>
					<td align="center">${record.institutionCodeCount }</td>
					<td align="center"><fmt:formatDate
							value="${record.createDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td align="center">${record.operator }</td>
					<td align="center"><input type="button" value="预览/下载"
						onclick="openCodeView(${record.id})" /><!-- <input type="button"
						value="下载" onclick="" /> --></td>
				</tr>
			</c:forEach>
		</table>

		<table width="98%" align="center" class="page_table">
				<tr>
					<td><s:set name="form_name" value="'f_instCodesManage'"
							scope="request"></s:set> <jsp:include
							page="/WEB-INF/jsp/common/page.jsp"></jsp:include></td>
				</tr>
		</table>

		<div id="qrcodeImage"></div>
	</form>


</body>
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script language="JavaScript" src="/etc/js/dialog.js"
	type="text/javascript"></script>
<script src="/etc/js/97dater/WdatePicker.js"></script>
<script type="text/javascript"
	src="/etc/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/etc/js/pageCommon.js"></script>
<script type="text/javascript">
	
	//表单验证
	function count_valid(){
		if($("#_codeCount").val() == null || $("#_codeCount").val() == ''){
			$("#tip_count").html("请输入大于0的整数！");
			return false;
		}
		if($("#_codeCount").val().trim() > 20 || $("#_codeCount").val().trim() < 1){
			$("#tip_count").html("编码张数最少1张,最多20张！");
			return false;
		}
/* 		if(!Number($("#_codeCount").val())){
			$("#tip_count").html("请输入整数！");
			return false;
		}
 */		if(parseInt($("#_codeCount").val().trim())!=$("#_codeCount").val().trim()){
			$("#tip_count").html("请输入大于0的整数！");
			return false;
		}
		
		$("#tip_count").html("");
		return true;
	}
	function bagman_valid(){
		if($("#autocomplete").val() == null || $("#autocomplete").val() == ''){
			$("#tip_name").html("请选择业务员！");
			return false;
		}
		$("#tip_name").html("");
		return true;
	}
	
	
	$(function() {

		$("#_codeCount").blur(function(){
			count_valid();
		});
		
		$("#getCodes")
				.click(
						function() {
							if (count_valid() && bagman_valid()) {
								$
										.post(
												'/crowd/getCodes.action',
												$("#f_getCode").serialize(),
												function(data) {
													var jsonObj = eval("("
															+ data + ")");
													var code = jsonObj.code;
													var message = jsonObj.message;
													if (code == 1)
														window.location.href = "/crowd/instCodesManage.action";
													else if (code == -3){
														$("#tip_name").html("请选择业务员！");
													}
													else
														alert(message);
												});
							}

						});


		//combox异步加载业务员信息至下拉框
		var countriesArray;
		   $.ajax({
				//async:'false',
				url:'/crowd/ajaxBagManInfos.action',
				success:function(data){
					  countriesArray = $.map(data, function (value, key) { 
						return { value: value, name: key }; 
						});
					  
					  $("#autocomplete").bind("change",function(){
						  $("#bagmanId").val(null);
						})
					$('#autocomplete').autocomplete({
				    	lookup: countriesArray,
				      	minChars: 0 ,
				      //  minLength: 1,
				        onSelect: function (value) {
				        	 $("#bagmanId").val(value.name);
				        }
				    })
				},
				dataType:'json'
				
			}); 

		//条件查询按钮事件
		$("#queryList").click(function() {
			$("#f_instCodesManage").submit();
		});
	});
	
	//弹出框二维码内容begin......................
	/* 弹出框，显示二维码页 */
	function openCodeView(id){
		dialog("请点击下载按钮进行下载,保存时请自定义后缀名，如：png、jpg等","iframe:/crowd/showQrCodesPage.action?recordId="+id,"1000px","800px","iframe",50);
	}
	//弹出框二维码内容end........................
</script>
</html>