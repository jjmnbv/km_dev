<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>选择供应商</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script src="/etc/js/ztree/jquery-1.4.4.min.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript">

$(document).ready(function(){ 
	//初始化时多选默认选择
	var oldIdArr = $('#supplierId',parent.window.document).val().split(',');
	var supheres=$("#listTable input[name='supplierIds']"); 
	for ( var i = 0; i < oldIdArr.length; i++) {
		for ( var j = 0; j < supheres.length; j++) {
			if(oldIdArr[i]==supheres.eq(j).attr("id")){
				$("#listTable input[name='supplierIds']").eq(j).attr("checked",true);
			}
		}
		
	} 
	
	
$('#selectInfo').click(function() {
	var idschecks=$("#listTable input[name='supplierIds']:checked");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
	if(idschecks.size()==0){
		alert('请选择商户','提示');
		return false;
	}else{
		var cname="";
		var cid="";
		for ( var i = 0; i < idschecks.length; i++) {
			 var data = idschecks.eq(i).val();
			  var aa=data.split(",");
			  var supplierId=	aa[0];
			  var corporateName=aa[1];
			  if(cid==""){
				  cid = supplierId;
			  } else {
				  cid = cid+","+supplierId;  
			  }
			  if(cname == ""){
				  cname = corporateName;
			  } else {
				  cname=cname+","+corporateName;
			  }
		}
	    parent.closeOpenUserInfo(cid,cname);
	}
});
});

</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:hidden name="supplierIdLists" id="supplierIdLists" value="<s:property value='supplierIds'/>"></s:hidden>
		<div style="height: 90%; overflow-y: auto;">
			<s:form name="supplierForm"
				action="/coupon/couponRule_checkSupplier.action" method="post">
				
				<!-- 查询条件 -->
				<table width="90%" align="center" height="50" border="0"
					class="content_table" cellpadding="0" cellspacing="0">
				
					<tr>
					<td lign="right">
						公司名称：</td><td>
						<input name="selectSuppliersInfo.corporateName" type="text" value="<s:property value='selectSuppliersInfo.corporateName' />" />
						
					</td>
					<td lign="right">
					商家类型：</td>
					<td>
					入驻
					</td>
					
					<td align="center">
							<INPUT TYPE="submit" class="queryBtn" value="">
							<input type="button" class="btn-custom"  value="确定" id="selectInfo">
						</td>
					</tr>
				</table>
				<!-- 数据列表区域 -->
				<table id="listTable" width="90%" class="list_table" cellpadding="3" align="center"
					cellspacing="0" border="1">
					<tr>
						<th width="5%">
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
								<input type="checkbox" name="supplierIds" id="<s:property value='supplierId'/>"
									value="<s:property value='supplierId'/>,<s:property value='corporateName'/>">
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
				<table class="page_table" width="90%" align="center" cellpadding="0"
					cellspacing="0" border="0">
					<tr>
						<td>
							<s:set name="form_name" value="'supplierForm'" scope="request"></s:set>
							<jsp:include page="/WEB-INF/jsp/public/pager.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
	</body>
</html>
