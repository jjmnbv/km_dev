<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
</style>
<script type="text/javascript" src="/etc/js/jquery-latest.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script type="text/javascript" src="/etc/js/product/product.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/jquery.blockUI.js" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/dialog-common.js" type="text/javascript"></script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
	<s:set name="parent_name" value="'产品管理'" scope="request" />
	<s:set name="name" value="'产品库存操作日志查询'" scope="request" />

	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
	<s:form action="/app/queryProductStockLog.action" method="POST"
		namespace="app" id="frm" name='frm'>

		<!-- 查询条件区域 -->
		<table width="98%" class="content_table" align="center" 
			cellpadding="0" cellspacing="0">
			<tr>
  				<td>产品名称： <s:textfield name="stockLog.productName"
						cssClass="input_style" id="productNo" /></td>
				<td>产品编号：&nbsp;<s:textfield type="text"
						name="stockLog.productNo" cssClass="input_style" id="productName" /></td>
				<td>产品货号：<s:textfield type="text" name="stockLog.productSkuCode"
						cssClass="input_style"></s:textfield></td>
			</tr>
			<tr>
				<td>单据编号：&nbsp;<s:textfield type="text" cssClass="input_style"
						name="stockLog.detailNo" id="keyword" /></td>
				<td>用户姓名：&nbsp;<s:textfield name="stockLog.userName" type="text" 
                                            cssClass="input_style"></s:textfield></td>
                <td>
                    <input type="button" onClick="query()" class="queryBtn" value="" />
				</td>
			</tr>
		</table>

		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center" width="15%">产品名称</th>
				<th align="center" width="8%">产品编号</th>
				<th align="center" width="8%">产品货号</th>
				<th align="center" width="6%">仓库</th>
				<th align="center" width="5%">库存ID</th>
				<th align="center" width="4%">更新前数量</th>
				<th align="center" width="4%">更新后数量</th>
				<th align="center" width="4%">变更数量</th>
				<th align="center" width="8%">操作类型</th>
				<th align="center" width="8%">单据编号</th>
				<th align="center" width="6%">单据类型</th>
				<th align="center" width="">用户</th>
				<th align="center" width="8%">创建时间</th>
				<th align="center" width="10%">备注</th>
			</tr>
			<s:iterator id="productiterator" value="page.dataList" status="index">
				<tr>
					<td align="center"><s:property value="productName" escape="false"/> </td>
					<td align="center">
                        <a href="#" style="text-decoration:none;" onclick="popDialog('/app/queryProductInfoByProductNo.action?productNo=<s:property value='productNo'/>','查看产品信息',780,780)"  value="<s:property value='productNo'/>"> <s:property value="productNo"/></a>
					</td>
					<td align="center"><a href="#" style="text-decoration:none;" onclick="popDialog('/app/findSkuInfoBySkuCode.action?skuCode=<s:property value='productSkuCode'/>','查看SKU信息',650,430)" value="<s:property value='productSkuCode'/>"><s:property value="productSkuCode"/></a>
                    </td>
					<td align="center">
						<s:property value="#request.warehouseInfoMap[warehouseId]" />
					</td>
					<td align="center"><a href="#" style="text-decoration:none;" onclick="popDialog('/app/queryProductStockByStockId.action?stockId=<s:property value='stockId'/>','查看仓库信息',780,130)" value="<s:property value='stockId'/>"> <s:property value="stockId"/></a>
					</td>
					<td align="center"><s:property value="beforeQuantity" /></td>
					<td align="center"><s:property value="endQuantity" /></td>
					<td align="center"><s:property value="changeQuantity" /></td>
					<td align="center"><s:property value="#request.stockLogOpTypeMap[opType]" /></td>
					<td align="center">
				        <a href="#" style="text-decoration:none;" onclick="popDialog('/app/queryStockOutDetailByStockOutId.action?billNo=<s:property value='detailNo'/>','查看单据信息',900,490)" value="<s:property value='detailNo'/>"> <s:property value="detailNo" /> </a>
					</td>
					<td align="center"><s:property value="#request.stockLogBillTypeMap[billType]" /></td>
					<td align="center"><s:property value="userName" /></td>
					<td align="center"><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td align="center"><s:property value="remark" /></td>
				</tr>
			</s:iterator>
		</table>

		<!-- 分页按钮区 -->
		<table width="98%" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td><%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
				</td>
			</tr>
		</table>
		<br>
		<br>
	</s:form>
</body>
<script type="text/javascript">
	function query() {
		document.frm.submit();
	}

	function add(){
    	document.frm.action="/app/addProductStockLog.action"  ;
        document.frm.submit();
	}
</script>
</html>