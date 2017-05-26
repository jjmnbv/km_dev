<%@page contentType="text/html;charset=UTF-8" %>
	<%@ taglib prefix="s" uri="/struts-tags" %>
		<%@ page import="com.pltfm.sys.util.StaticParams" %>
			<html>

			<head>
				<title>选择供应商</title>
				<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
				<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
				<script type="text/javascript" src="/etc/js/jquery-1.8.3.min.js">
				</script>
				<Script src="/etc/js/97dater/WdatePicker.js"></Script>
				<script src="/etc/js/dialog.js">
				</script>
				<script type="text/javascript" src="/etc/js/pageCommon.js">
				</script>
				<script type="text/javascript">
					/** 删除地址信息  **/
					
					function removeRepetitive(arr){
					    var len = arr.length;
					    var newArr = [];
					    if (len < 2) return arr;
					    for (var i = 0; i < len; i++) {
					        for (var j = i + 1; j < len; j++) {
					            if (arr[i] === arr[j]) {
					                j = ++i;
					               // alert(arr[i]);
					            }
					        }
					        if(arr[i]){
					        	 newArr.push(arr[i]);
					         }
					       
					    }
					    return newArr;
					}
					$(document).ready(function(){ 
					$('#selectInfo').click(function() {
								var idschecks=$("#listTable input[name='supplierIds']:checked"); 
								if(idschecks.size()==0){
									alert('请选择商户','提示');
									return false;
								}else{
								   var oldNameArr = $('#corporateName',parent.window.document).val().split(',');
								   var oldIdArr = $('#supplierId',parent.window.document).val().split(',');
									idschecks.each(function(){	
									  var data = this.value;
									  var aa=data.split(",");
									  var supplierId=	aa[0];
									  //alert(supplierId,'提示');
									  var corporateName=aa[1];
									  //alert(corporateName,'提示');
									  if(supplierId){
										  oldIdArr.push(supplierId);
										 }
									  if(corporateName){
										  oldNameArr.push(corporateName);
										 }
									 
									});
									var newNameArr = removeRepetitive(oldNameArr);
									var newIdArr = removeRepetitive(oldIdArr);
									   var method = "closeOpenUserInfo";
						         if(method!=null||method!=""){
						            parent.closeOpenUserInfo(newIdArr.join(','),newNameArr.join(','));
						         }else{
						           parent.closeOpenUserInfo(newIdArr.join(','),newNameArr.join(','));
						         }
							   }
							});
					});
				</script>
			</head>

			<body>
				<!-- 导航栏 -->

				<div style="height: 90%; overflow-y: auto;">
					<s:form name="supplierForm" onsubmit=" return checkAllTextValid(this)" action="/userInfo/supplier_queryPageList.action" method="post">

						<!-- 查询条件 -->
						<table width="90%" align="center" height="50" border="0" class="content_table" cellpadding="0" cellspacing="0">

							<tr>
								<td lign="right">
									公司名称：</td>
								<td>
									<input name="corporateName" type="text" value="<s:property value='corporateName' />" />

								</td>
								<td lign="right">
									商家类型：</td>
								<td>
									入驻
								</td>

								<td align="center">
									<INPUT TYPE="submit" class="queryBtn" value="">
									<input type="button"  class="btn-custom"  style="height:30px" value="确定" id="selectInfo">
								</td>
							</tr>
						</table>
						<!-- 数据列表区域 -->
						<table id="listTable" width="90%" class="list_table" cellpadding="3" align="center" cellspacing="0" border="1">
							<tr>
								<th width="5%">
									<input type='checkbox' name='allbox' onClick="checkAll(this,'supplierIds')">
								</th>
								<th>
									编号
								</th>
								<th>
									公司名称
								</th>
								<th>
									类型
								</th>

							</tr>
							<s:iterator id="custiterator" value="page.dataList">
								<tr>
									<td width="5%">
										<input type="checkbox" name="supplierIds" value="<s:property value='supplierId'/>,<s:property value='corporateName'/>">
									</td>

									<td>
										<s:property value="supplierId" />
									</td>
									<td>
										<s:property value="corporateName" />
									</td>
									<td>
										<s:if test="supplierType==2">
											入驻
										</s:if>
									</td>
								</tr>
							</s:iterator>
						</table>
						<table class="page_table" width="90%" align="center" cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td>
									<s:set name="form_name" value="'supplierForm'" scope="request"></s:set>
									<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
								</td>
							</tr>
						</table>
					</s:form>
				</div>
				<!-- 消息提示 -->
				<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
			</body>

			</html>