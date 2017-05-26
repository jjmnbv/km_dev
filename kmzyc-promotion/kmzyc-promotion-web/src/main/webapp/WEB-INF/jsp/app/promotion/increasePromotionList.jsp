<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<title></title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>

<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}

</style>
</head>
<s:set name="parent_name" value="'促销管理'" scope="request"/>
<s:set name="name" value="'加价购组合列表'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<s:form name="sectionsForm" method="post"
		action="/promotion/queryPromotionIncreaseList.action">
		<s:hidden name="promotion.nature" value="2" id="nature"></s:hidden>
		<!-- 标题条 -->
		<!--<div class="pagetitle">促销活动管理:</div>
		<!-- 按钮条 -->
		<table width="98%" align="center" class="topbuttonbar" height="30"
			border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="90%" valign="middle"><input class="addBtn"
					type="button" value="" onClick="gotoAdd();"> <input
					class="delBtn" type="button" value="" onClick="del_sect();">
				</td>
				<td width="10%" align="center">
					<!--a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a-->
				</td>
			</tr>
		</table>
		<!-- 查询条件区域 -->
		<table width="98%" class="searcharea" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="right">组合名称：<input name="promotion.promotionName" id="promotionName" type="text" 
					class="input_style" value="<s:property value='promotion.promotionName' />"></td>
				<td align="right" colspan="1"><INPUT TYPE="submit" class="queryBtn"
					value=""></td>
			</tr>
		</table>


		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center" width="3%"><input type='checkbox'
					name='allbox' onclick='checkAll(this)'></th>
				<th align="center" width="14%">组合名称</th>
				<th align="center" width="15%">操作</th>
			</tr>
			<s:iterator  value="page.dataList">
				<tr onMouseOver="this.style.backgroundColor='#def2fa'"
				onMouseOut="this.style.backgroundColor='#FFFFFF'">
					<td align="center" width="5%"><input type="checkbox"
						name="promotionId" value='<s:property value="promotionId"/>' />
						<input type="hidden" id="status<s:property value='promotionId'/>" value="<s:property value='status'/>"/></td>
					<td align="center"><s:property value="promotionName"/></td>
					<td align="center">
						<img TYPE="button"  style="cursor: pointer;" title="管理商品" value="管理商品" src="/etc/images/button_new/upshelf.png"
							onClick="queryPromotionProductList(<s:property value='promotionId'/>)">&nbsp;&nbsp;
						<img TYPE="button"  style="cursor: pointer;" title="删除"  src="/etc/images/button_new/delete.png"
							onClick="javascript:deleteSelectedPromotion(this);">&nbsp;&nbsp;
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
	</s:form>
</body>
<script type="text/javascript">
	function gotoAdd(){
		/*
		
		*/
		window.location.href = '/promotion/toIncreasePromotionAddOrUpdate.action';
	}
	/**  查看商品  **/
    function queryPromotionProductList(promotionId){
    	//alert(pre_sectionId);
    	location.href="/promotion/queryPromotionProductList.action?promotionProduct.promotionId="+promotionId+"&promotion.promotionId="+promotionId;
    }
	/** 删除  **/
	function deleteSelectedPromotion(o){
		$(o).parent().parent().children().children().attr("checked",true);
		del_sect();
	}

	function del_sect()
	{
		if($("input[type='checkbox'][name='promotionId']:checked").length==0){
			alert("请选择活动！");
			return false;
		}
		var ids = "";
		var flag = false;
		$("input[type='checkbox'][name='promotionId']:checked").each(function(i){
			if($("#status"+$(this).val()).val() != 1){
	    		alert("已使用的组合不能删除！");
	    		flag = true;
	    		return false;
			}
			ids += "," + $(this).val();
		});
			if (!flag && confirm("您确定要删除选中的数据吗？")){
				ids = ids.substr(1);
				$.ajax({
					//toIncreasePromotionAddOrUpdate
					url:'/promotion/deletePromotion.action',
					type:'post',
					dataType:'json',
					data:{'promotion.promotionIds':ids},
					success:function(data){
						var code = data.code;
						if(code==0){
							alert("删除成功！");
							window.location.href = '/promotion/queryPromotionIncreaseList.action';
						}else{
							alert(data.module);
							//pInput.focus();
							return;
						}
					},
					error:function(){
						alert('操作失败请联系管理员');
					}
				});
				//location.href="/promotion/deleteExtra;Promotion.action?promotion.promotionIds="+;
			}
	}
</script>
</html>

