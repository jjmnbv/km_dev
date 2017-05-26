<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css">
<script language="JavaScript" src="/etc/js/dialog.js"
	type="text/javascript"></script>
<title>促销活动审核</title>
<link href="/etc/autocomplete/autocompletestyles.css" type="text/css"
	rel="stylesheet">
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="/etc/autocomplete/jquery.mockjax.js"></script>
<script type="text/javascript"
	src="/etc/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/etc/autocomplete/demo.js"></script>
<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
</style>
<script type="text/javascript">
    /**查看活动**/
    function queryPrmotionInfo(id){
    	window.location.href = "/promotion/toPromotionInfo.action?operationType=1&promotion.promotionId="+id;
    }
   
	//撤销活动
	function updatePromotionEndTime(id){
		var yes = confirm('是否确认撤销该活动（将活动设为过期）?');
		if(yes==false)return;
		$.ajax({
			url:'/promotion/updatePromotionEndTime.action',
			type:'post',
			dataType:'json',
			data:{'promotion.promotionId':id,'promotion.status':2},
			success:function(data){
				var code = data.code;
				if(code==0){
					alert("操作成功,活动上下线操作缓存更新会有一定延迟！");
					window.location.reload();
				}else{
					alert(data.title);
					//pInput.focus();
					return;
				}
			},
			error:function(){
				alert('操作失败请联系管理员');
			}
		});
	}
	//创建缓存
	function creatCache(id){
		var yes = confirm('是否重新创建活动缓存?');
		if(yes==false)return;
		$.ajax({
			url:'/promotion/creatCache.action',
			type:'post',
			dataType:'json',
			data:{'promotion.promotionId':id},
			success:function(data){
				var code = data.code;
				if(code==0){
					alert("操作成功！");
					window.location.reload();
				}else{
					alert(data.title);
					//pInput.focus();
					return;
				}
			},
			error:function(){
				alert('操作失败请联系管理员');
			}
		});
	}
	/**设置审核状态*/
	function updateStatus(type,id){
		var title = "";
		if(type==2){
			title = "是否确定将活动设为未审核?";
		}else{
			title = "是否确定将活动设为已审核?";
		}
		var yes = confirm(title);
		if(yes==false)return;
		$.ajax({
			url:'/promotion/updateIssuePromotion.action',
			type:'post',
			dataType:'json',
			data:{'promotion.promotionId':id},
			success:function(data){
				var success = data.success;
				if(success==true){
					alert("操作成功,活动上下线操作缓存更新会有一定延迟！");
					window.location.reload();
				}else{
					alert(data.title);
					return;
				}
			},
			error:function(){
				alert('操作失败请联系管理员');
			}
		});
	}
	/**  进入修改活动信息页面  **/
    function gotoUpdate(id){
    	window.location.href = "/promotion/toUpdataPromotionNew.action?promotion.promotionId="+id;
    }
	
	/*同步促销活动关联的商品缓存*/
	function synPromotionCom(id){
		var yes = confirm("是否同步活动关联的商品缓存?");		
		if(yes==false)return;
		$.ajax({
			type:"post",
			dataType:"json",
			data:"promotion.promotionId="+id,
			url:"/promotion/synPromotionCom.action",
			success:function(data){
				var success = data.success;
				if(success==true){
					alert("操作成功！");
					window.location.reload();
				}else{
					alert(data.title);
					return;
				}
			},
			error:function(){
				alert("操作失败请联系管理员");
			}			
		});
	}
	function submitForm(){
		var startTime =$("#startTime").val();
		var endTime =$("#startTime").val();
		var onlineStatus =$("#onlineStatus").val();
		if((startTime!=""||startTime!="")&&onlineStatus!=0){
			alert("时间状态和时间范围同时你能有一个作为查询条件！");
			return;
		}
		$("#frm").submit();
	}
</script>
</head>
<s:set name="parent_name" value="'促销系统'" scope="request" />
<s:set name="name" value="'促销活动审核'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<s:form name="sectionsForm" method="post"
		action="/promotion/checkPromotionList.action" id="frm">
		<!-- 标题条 -->
		<!--<div class="pagetitle">促销活动管理:</div>
		<!-- 查询条件区域 -->
		<table width="98%" class="searcharea" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align="left" width="150px">活动名称：<input
					name="promotion.promotionName" id="promotionName" type="text"
					class="input_style"
					value="<s:property value='promotion.promotionName' />"></td>
				<td align="left" width="150px">审核状态： <s:select
						list="#{0:'全部',1:'未审核',2:'已审核'}" listKey="key" listValue="value"
						name="promotion.status"></s:select>
				</td>

				<td align="left" colspan="2" width="200px">活动时间：<input
					name="startTime" type="text" class="input_style"
					onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					value="<s:property value='startTime' />" id="startTime">&nbsp;到&nbsp;<input
					name="endTime" type="text" class="input_style"
					onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					value="<s:property value='endTime' />" id="endTime"></td>

<td align=left width="200px">商家名称：<input id="autocomplete_forSuppliers"
					name="promotion.shopNames"  type="text"
					class="input_style"
					value="<s:property value='promotion.shopNames' />"></td>
			</tr>
			<tr>
<td align=left width="150px">活动ID：<input
					name="promotion.promotionId" id="promotionId" type="text"
					class="input_style" onKeyUp="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" 
					value="<s:property value='promotion.promotionId' />"></td>
				<td align="left" width="150px">活动类型: <s:select
						list="%{#request.promotionTypeMap}" headerKey="" headerValue="全部"
						listKey="key" listValue="value" name="promotion.promotionType"></s:select>
				</td>

				<td align="left" width="150px">时间状态： <s:select
						list="#{0:'全部',1:'未上线',2:'正在进行',3:'已过期'}" listKey="key"
						listValue="value" name="onlineStatus" id="onlineStatus"></s:select>
				</td>
				<td align=left width="200px">skuCode：<input
					name="promotion.promotionIds" id="promotionIds" type="text"
					class="input_style"
					value="<s:property value='promotion.promotionIds' />"></td>
				<td align="right" colspan="1" width="200px"><INPUT
					TYPE="button" onClick="submitForm();" class="queryBtn" value=""></td>
			</tr>
		</table>


		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center" width="3%"><input type='checkbox'
					name='allbox' onclick='checkAll(this)'></th>
					<th align="center" width="6%">活动ID</th>
				<th align="center" width="12%">标题</th>
				<th align="center" width="12%">活动名称</th>
				<th align="center" width="5%">类型</th>

				<th align="center" width="6%">商家类别</th>
				<th align="center" width="24%">起止时间</th>

				<th align="center" width="8%">时间状态</th>
				<th align="center" width="6%">审核状态</th>
				<th align="center" width="8%">优先级</th>
				<th align="center" width="16%">操作</th>
			</tr>
			<s:iterator value="page.dataList">
				<tr onMouseOver="this.style.backgroundColor='#def2fa'"
					onMouseOut="this.style.backgroundColor='#FFFFFF'">
					<td align="center" width="5%"><input type="checkbox"
						name="promotionId" value='<s:property value="promotionId"/>' /> <input
						type="hidden" id="status<s:property value='promotionId'/>"
						value="<s:property value='status'/>" /></td>
						<td align="center">
							<s:property value="promotionId" />
						</td>
					<td align="center"><s:property value="promotionTitle" /></td>
					<td align="center"><s:property value="promotionName" /></td>
					<td align="center"
						title="<s:property value='%{#request.promotionTypeMap[promotionType]'/>"><s:property
							value="#request.promotionTypeMap[promotionType]" /></td>

					<td align="center"><s:if test="shopSort==3">康美自营代销</s:if>
						<s:elseif test="shopSort==1">所有</s:elseif>
						<s:else>
							<s:property value='shopNames' />
						</s:else></td>
					<td align="center">从<s:date name="startTime"
							format="yyyy-MM-dd HH:mm:ss" /><br>到 <s:date name="endTime"
							format="yyyy-MM-dd HH:mm:ss" /></td>
					<td align="center"><s:if test="onlineStatus==1">未上线</s:if> <s:elseif
							test="onlineStatus==2">正在进行</s:elseif> <s:else>已过期</s:else></td>
					<td align="center"><s:if test="status==1">未审核</s:if>
						<s:else>已审核</s:else></td>
					<td align="center"><span id="noEditpricrity"
						style="display: inline"><s:property
								value="promotionPriority" /></span>&nbsp;</td>
					<td align="center"><s:if test="status==1">
							<!-- 未审核 -->
							<img TYPE="button" style="cursor: pointer;" title="审核通过"
								src="/etc/images/button_new/submit.png"
								onClick="updateStatus(1,<s:property value='promotionId'/>)">&nbsp;&nbsp;
						</s:if> <img TYPE="button" style="cursor: pointer;" title="查看信息"
						src="/etc/images/button_new/select.png"
						onClick="queryPrmotionInfo(<s:property value='promotionId'/>)">&nbsp;&nbsp;

						<s:if test="status==2">
							<s:if test="onlineStatus==2">
								<!-- 已上线 -->
								<img TYPE="button" style="cursor: pointer;" title="撤销活动"
									src="/etc/images/button_new/stopuse.png"
									onClick="updatePromotionEndTime(<s:property value='promotionId'/>)">&nbsp;&nbsp;
							</s:if>
							<s:if test="onlineStatus==1">
								<!-- 未上线 -->
								<img TYPE="button" style="cursor: pointer;" title="撤销审核"
									src="/etc/images/button_new/notpass.png"
									onClick="updateStatus(2,<s:property value='promotionId'/>)">&nbsp;&nbsp;
							</s:if>
							<%--
							<s:if test="isSycnCache==0 || isSycnCache==2"><!-- 未同步或同步成功 -->
								<img type="button" style="cursor:pointer;" onClick="synPromotionCom(<s:property value='promotionId'/>)" 
								title="同步活动商品" src="/etc/images/button_new/pass.png">
							</s:if>
							<s:if test="isSycnCache==1"><!-- 正在同步 -->
								<img type="button" style="cursor:pointer;" title="正在同步活动商品" src="/etc/images/button_new/startuse.png">
							</s:if>
							 
							<s:if test="isSycnCache==3"><!-- 同步失败 -->
								<img type="button" style="cursor:pointer;" onClick="synPromotionCom(<s:property value='promotionId'/>)"
								 title="同步活动商品失败" src="/etc/images/button_new/downshelf.png">
							</s:if>
							--%>
						</s:if></td>
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
</html>

