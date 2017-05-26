<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品供应商</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default' type='text/javascript'></script>
<script language='JavaScript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js' type='text/javascript'></script>

<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
</style>

<script type="text/javascript">

</script>


</head>
<body>
	<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

	<s:set name="parent_name" value="'供应商管理'" scope="request" />
	<s:set name="name" value="'供应商店铺审核列表'" scope="request" />
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>



	<s:form action="/app/showShopMainDraftList.action" method="POST"
		id="frm" name='frm'>


<input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>" />



		<!-- 查询条件区域 -->
		<table width="98%" class="content_table" align="center"
			cellpadding="0" cellspacing="0">

			<tr>
				<td>店铺标题：&nbsp; &nbsp; <s:textfield
						name="searchShopMainDraft.shopTitle" cssClass="input_style" /></td>

				<td>店铺名称：&nbsp; &nbsp; <s:textfield
						name="searchShopMainDraft.shopName" cssClass="input_style" /></td>
						
				<td>商户名称：&nbsp; &nbsp; <s:textfield
						name="searchShopMainDraft.corporateName" cssClass="input_style" /></td>

			</tr>
			<tr>
				<td>店铺状态：&nbsp; &nbsp; 
					<s:select name="searchShopMainDraft.status" headerKey="" headerValue="--全部--" list="#{0:'关闭',1:'开启'}"></s:select>
				</td>


				<td colspan="2">
					<INPUT TYPE="submit" class="queryBtn" value="">&nbsp;
                    <input type="button" value="审核通过" class="btn-custom" onclick="batchAudit('2')" />&nbsp;
					<input type="button" value="不通过" class="btn-custom"  onclick="batchAudit('3')" />
				</td>
			</tr>
		</table>


		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center" width="5%"><input type='checkbox'
					id='allbox' name='allbox' onclick='checkAll(this)'>
				</th>
				<th align="center" width="10%">店铺标题</th>
				<th align="center" width="19%">店铺名称</th>
				<th align="center" width="10%">店铺状态</th>
				<th align="center" width="10%">审核状态</th>
				<th align="center" width="10%">申请时间</th>
				<th align="center" width="10%">商户名称</th>
				<th align="center" width="10%">操作</th>
			</tr>
			<s:iterator id="supplieriterator" value="page.dataList">
				<tr>
					<td align="center" width="5%"><input type="checkbox"
						id="check" name="shopIdList"
						value='<s:property value="shopId"/>'></td>
					<td align="center"><s:property value="shopTitle" />
					</td>
					<td align="center"><s:property value="shopName" /></td>
					<td align="center"><s:if test="status == 0">关闭</s:if><s:if test="status == 1">开启</s:if></td>
					<td align="center">
					<s:if test="auditStatus == 1">
					申请中，未审核
					</s:if>
					<s:elseif test="auditStatus == 2">
					审核通过
					</s:elseif>
					<s:elseif test="auditStatus == 3">
					审核未通过
					</s:elseif>
					<s:elseif test="auditStatus == 4">
					永久关闭
					</s:elseif>
					<s:elseif test="auditStatus == 0">
					编辑
					</s:elseif>
					</td>
					<td align="center">
					<s:date name="applyTime" format="yyyy-MM-dd" />
					</td>
					<td align="center"><s:property value="corporateName" /></td>
					<td align="center">
					<s:if test="auditStatus == 1">
					<img
						title="审核" style="cursor: pointer;"
						src="/etc/images/button_new/view.png"
						onclick="gotoAudit(<s:property value='shopId'/>)" />
						</s:if>
						<!--<s:if test="auditStatus == 2">
							<img type="button" style="cursor: pointer;" title="永久关闭" src="/etc/images/button_new/stopuse.png" onclick="closeSupplierShopStatus(<s:property value='shopId'/>)">
						</s:if>
						--><!--<s:if test="auditStatus == 3 || auditStatus == 4">
							<img type="button" style="cursor: pointer;" title="启用店铺" value="管理商品" src="/etc/images/button_new/upshelf.png" onclick="openSupplierShopStatus(<s:property value='shopId'/>)">
						</s:if>-->
						<img title="详情" style="cursor: pointer;"
						src="/etc/images/view.png"
						onclick="gotoView(<s:property value='shopId'/>)" />
					</td>
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
		<s:hidden name="describe" id="reasonText" ></s:hidden>
		<s:hidden name="auditStatus" id="auditStatus" ></s:hidden>
	</s:form>
	
	<div id="auditReason" style="display: none;">
		<textarea id="reasonArea" rows="10" cols="100" style="resize: none;" ></textarea>
	</div>
	
   
<s:if test='!rtnMessage.isEmpty()'>
	<SCRIPT LANGUAGE="JavaScript">
	
		alert(document.getElementById("rtnMsg").value);

	</SCRIPT>
</s:if>
	
	
	<SCRIPT LANGUAGE="JavaScript">

	
	$(document).ajaxStart(function() {
		$.blockUI({ 
			message: '<img src="/etc/images/wait.gif" style="vertical-align:middle;height:42px;" /><b style="font-size:30px;vertical-align:middle;">正在操作，请稍后...</b>', 
			css: {width:'40%'}
		});
    });
	$(document).ajaxComplete(function() {
		$.unblockUI();
    });
	
	
	function gotoAudit(id){
	  var  pageNum=$("#pageNo option:selected").val();
	  document.forms[0].action="/app/toViewShopMainDraft.action?shopId="+id+"&type=audit&pageNum="+pageNum;
	    document.forms[0].submit();
	}
	
	function gotoView(id){
		var  pageNum=$("#pageNo option:selected").val();
		document.forms[0].action="/app/toViewShopMainDraft.action?shopId="+id+"&type=view&pageNum="+pageNum;
		document.forms[0].submit();
	}
	
	function batchAudit(auditStatus){
		var chkObj = $('input[name="shopIdList"]:checked');
		if(chkObj.length==0){
	   		alert('请勾选要审核的店铺！')
	   		return;
	   	}
		
		if(confirm('确定审核已选店铺吗？')){
			$("#auditStatus").val(auditStatus);
			if("3"==auditStatus){
				var dia = art.dialog({   
					title:'请填写审核不通过的原因',
				    content: $("#auditReason").html(),   
				    drag:true,
				    lock:true,
				    ok: function () {
				    	$("#reasonText").val($("#reasonArea").val());
				    	$.post(
				    		"/app/updateShoMainDraftAudit.action",
				    		$("#frm").serializeArray(),
				    		function(data){
				    			if("1"==data){
				    				alert("操作成功！");
				    				dia.close();
				    				location.href = "/app/showShopMainDraftList.action";
				    			}else{
				    				alert("操作失败，请联系管理人员或稍后再试！");
				    			}
				    		},"json"
				    	);
				    	
				    },   
				    cancelVal: '关闭',   
				    cancel: true //为true等价于function(){}   
				}); 
			}else{
				$.post(
			    		"/app/updateShoMainDraftAudit.action",
			    		$("#frm").serializeArray(),
			    		function(data){
			    			if("1"==data){
			    				alert("操作成功！");
			    				location.href = "/app/showShopMainDraftList.action";
			    			}else{
			    				alert("操作失败，请联系管理人员或稍后再试！");
			    			}
			    		},"json"
			    	);
			}
	}
	}

  function closeSupplierShopStatus(id){
	  var answer = confirm("确认永久关闭吗?");
      if (!answer) {
				return false;
			}
      $.ajax({
          url: '/app/supplierShopStatus.action',
          async:false,
          data: 'shopId='+id,
          success: function(info) {
             if('0' == info){
             	alert("系统异常，停用失败!");
     			return;
             }else{
             	alert("关店成功!");
             	 document.forms[1].action= "/app/supplierShopList.action";
      		    document.forms[1].submit();
             }
          }
      });
	  }

  function openSupplierShopStatus(id){

	  var answer = confirm("确认启用店铺吗?");
      if (!answer) {
				return false;
			}
      $.ajax({
          url: '/app/supplierShopStatus.action',
          async:false,
          data: 'shopId='+id+"&ckType="+1,
          success: function(info) {
             if('0' == info){
             	alert("系统异常，启用失败!");
     			return;
             }else{
             	alert("启用成功!");
             	 document.forms[1].action= "/app/supplierShopList.action";
     		    document.forms[1].submit();
             }
          }
      });
	}
</SCRIPT>
</body>

</HTML>