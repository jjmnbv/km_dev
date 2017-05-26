<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>按方抓药审核信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script src="/etc/js/jquery-1.8.3.js"></script>
<script language='JavaScript' src='/etc/js/dialog-common.js' type='text/javascript'></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
 <script src="/etc/js/dialog.js"></script>
 <script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/jquery.blockUI.js' type='text/javascript'></script>
<script type="text/javascript">
    /**  返回列表页 **/
    function gotoList(){
  
      location.href="/userInfo/prescripinfo_selectPageList.action";
    } 
    
    function updateProCount(pdid,price){
    	var purchaseId=$("#purchaseId").val();
    	var productCount=$("#"+pdid).val();
    	
    	//alert("--"+productCount+"---"+price+"----"+pdid);
    	var reg1 = /^\d+$/;
    	if(productCount.trim().match(reg1) == null){
    		alert("输入数量必须为整数！");
    		return false;
    	}
    	else{
    		
    	
    document.prescripinfoForm.action="/userInfo/prescripinfo_updatePurchaseDes.action?purchaseId="+purchaseId+"&purchaseDetailId="+pdid+"&dProductPrice="+price+"&dProductCount="+productCount;
   	 document.prescripinfoForm.submit();
    	}
    	}
    function gotoDelete(id){
    	var purchaseId=$("#purchaseId").val();
    	location.href="/userInfo/prescripinfo_deleteBypurchaseDetailId.action?purchaseDetailId="+id+"&purchaseId="+purchaseId;
    	
    }
    /**  返回列表页 **/
    function purchaseInfo()
    {
    	var purchaseId=$("#purchaseId").val();
    	alert("审核通过！");
    	
    	 document.prescripinfoForm.action="/userInfo/prescripinfo_purchaseInfoPass.action?purchaseId="+purchaseId;
    	 document.prescripinfoForm.submit();
    }

    function noPurchaseInfo()
    {
    	var purchaseId=$("#purchaseId").val();
    	
    	dialog("药方审核不通过情况：","iframe:/userInfo/prescripinfo_toNoPassPres.action?purchaseId="+purchaseId,"550","330","iframe");
    	   
    }
    function gotoUpdate(id)
    {
    	 location.href="/userInfo/commercialTenantBasicCopy_CommercialTenantBasicUpdate.action?commercialCopyId="+id;
        }
    function closes(){
    	window.location.href="/userInfo/prescripinfo_selectPageList.action";
    	closeThis();
    	}
    /**  添加药品 **/
     function addProduct(){

    	var purchaseId=$("#purchaseId").val();
    	var url ="/userInfo/productinfo_addProducts.action?purchaseId="+purchaseId;
    	art.dialog.open(url, {
    		       id:'dg_test34243',
    			   title: '添加商品',
    			    width:900,
    				height:500,
    				drag:false,
    				close:function(){
    					window.location.href="/userInfo/prescripinfo_toUpdatePurchaseInfo.action?purchaseId="+purchaseId;
    				}
    		   });
 
    } 
</script>
</head>
<body>
<!-- 标题条 -->
<s:set name="parent_name" value="'药方管理'" scope="request" />
<s:set name="name" value="'药方审核'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:auto; " >
<s:form name="prescripinfoForm" action="/userInfo/prescripinfo_updatePurchaseInfo.action" 
    onsubmit=" return checkAllTextValid(this)"  method="post">
    <s:token/>
<!-- 查询条件区域 -->
	<table width="60%" class="edit_table" cellpadding="3" cellspacing="0"
				border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr>
					<td width="20%" class="eidt_rowTitle">
						<font color="red">*</font>药方名称：
					</td>
					<td>
					<input name="purchaseListDO.presName" type="text" size="50"
								value="<s:property value='purchaseListDO.presName'/>" />
								<input id="purchaseId" name="purchaseListDO.purchaseId" type="hidden" size="50"
								value="<s:property value='purchaseListDO.purchaseId'/>" />
								
					</td>
						 
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">
						处方图片：
						</td>
						<td width="80%">
							<a href="<s:property value='purchaseListDO.presUlr'/>" title="单击放大" target="_blank" id="hrefId101">	<img title="处方图片" width="160" height="155" style="cursor: pointer;" src="<s:property value='purchaseListDO.presUlr'/>" />
		
		查看源文件</a>
						</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">
						客户信息：
						</td>
						<td width="80%">
							 <s:property value="purchaseListDO.loginAccount"/>（已验证手机号码：<s:property value="purchaseListDO.mobile"/>）
						</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">
						审核状态：
						</td>
						<td width="80%">
							<s:if test="purchaseListDO.presStatus==0">草稿</s:if>
		 					<s:if test="purchaseListDO.presStatus==1">待审核</s:if>
	        				<s:if test="purchaseListDO.presStatus==2">审核通过</s:if>
	        				<s:if test="purchaseListDO.presStatus==3">审核不通过</s:if>
						</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">
						处方医院：
						</td>
						<td width="80%">
							<input name="purchaseListDO.hospital" type="text" value="<s:property value='purchaseListDO.hospital'/>" />
						</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">
						处方医生：
						</td>
						<td width="80%">
							<input name="purchaseListDO.doctor" type="text"
								value="<s:property value='purchaseListDO.doctor'/>" />
						</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">
						患者名称：
						</td>
						<td width="80%">
							<input name="purchaseListDO.patientName" type="text"
								value="<s:property value='purchaseListDO.patientName'/>" />
						</td>
				</tr>
				<tr>
					<td width="20%" class="eidt_rowTitle">
						临床诊断结论：
						</td>
						<td width="80%">
						<textarea   name="purchaseListDO.clinicalDiagnosis" style="width:557px;height:35px"><s:property value='purchaseListDO.clinicalDiagnosis'/></textarea>	
						</td>
				</tr>
				
			</table>
	<table width="98%"  align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	<th align="left" >药方内容</th>
	</tr>
	<tr>
	<td><input   onclick="addProduct()" type="button" value="添加药品"></td>
	</tr>
	</table>
<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
		<th align="center" >SKU编号</th>
		<th align="center" >产品标题</th>
		<!--删除渠道 <th align="center" >渠道</th> -->
		<th align="center" >品牌</th>
		<th align="center" >状态</th>
		<th align="center" >销售单价</th>
		<th align="center" >数量</th>
		<th align="center" >SKU描述</th>
		<th align="center" >金额小计</th>
		<th align="center" >删除</th>
	</tr>
	<s:iterator id="accountiterator"  value="listPurchaseListDetailDO">
	<tr> 
		<td align="center">
		     <s:property value="skuCode"/>
		</td>
		<td align="center">
		<s:property value="productTitle"/>
		</td>
	 
		<%-- <td align="center">
		<s:property value="channel"/>
		</td> --%>
		<td align="center">
		  <s:property value="frandName"/>
		</td>
		<td align="center">	
		 	<s:if test="status==0">草稿</s:if>
		 	<s:if test="status==1">待审核</s:if>
	        <s:if test="status==2">已审核,待上架</s:if>
	        <s:if test="status==3">已上架</s:if>
	        <s:if test="status==4">已下架</s:if>
	        <s:if test="status==5">系统下架</s:if>
	        <s:if test="status==6">审核未通过</s:if>
		</td>
		<td align="center">	
		 	<s:property value="productPrice"/>
		</td>
		<td align="center">	
		<input id="<s:property value="purchaseDetailId"/>" name="productCount" type="text"
								value="<s:property value='productCount'/>" onblur="updateProCount(<s:property value="purchaseDetailId"/>,<s:property value="productPrice"/>)"/>
		</td>
		<td align="center">	
		 	<s:property value="col"/>
		</td>
		<td align="center">	
		 	<s:property value="amount"/>
		</td>
		<td>
        <img title="删除" style="cursor: pointer;" src="/etc/images/u173_normal.png"  onclick="gotoDelete(<s:property value="purchaseDetailId"/>)" />
		</td>
	</tr>
	</s:iterator>
</table>

<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<input   onclick="purchaseInfo()" type="button" value="审核通过" class="btn-custom" > <input   onclick="noPurchaseInfo()"  class="btn-custom" type="button" value="审核拒绝">
            <INPUT TYPE="submit"  value="保存修改">  &nbsp;&nbsp;
			<input class="backBtn"  onclick="gotoList()" type="button" value=" ">
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

</s:form>
</div>
<!-- 消息提示页面 -->
	
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>	
</body>
</html>

