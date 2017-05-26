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

<title>促销活动列表</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/autocomplete/autocompletestyles.css" type="text/css"
	rel="stylesheet">
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
    /** 删除  **/
    function deleteSelectedPromotion(o){
    	$("input[type='checkbox'][name='promotionId']").attr("checked",false);
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
        		alert("已审核的活动不能删除！");
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
						window.location.href ="/promotion/queryPromotionList.action";
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
   		}
	}
    
    
    /**  进入新增栏目信息页面  **/
    function gotoAdd(){
    	var nature = $("#nature").val();
    	if(nature=='2'){
    		window.location.href = "/promotion/toIncreasePromotionAddOrUpdate.action";
    	}else{
    		window.location.href = "/promotion/toAddPromotionNew.action";
    	}
    	
    }
    
    /**  查看商品  **/
    function queryPromotionProductList(promotionId){
    	//alert(pre_sectionId);
    	location.href="/promotion/queryPromotionProductList.action?promotionProduct.promotionId="+promotionId+"&promotion.promotionId="+promotionId;
    }
    
    /**  进入修改活动信息页面  **/
    function gotoUpdate(id){
    	window.location.href = "/promotion/toUpdataPromotionNew.action?promotion.promotionId="+id;
    }
    function queryPrmotionInfo(id){
    	window.location.href = "/promotion/toPromotionInfo.action?operationType=0&promotion.promotionId="+id;
    }
    
   /** 全选js  **/
      function checkAll(ck){
		  //ALERT(ck);
		  for (var i=0;i<ck.form.all.tags("input").length;i++){
		    var ele = ck.form.all.tags("input")[i];
		    if ((ele.type=="checkbox")){
		      if(ck.checked!=ele.checked)
		        ele.click();
		    }
		  }
    }
	
	
	function gotoAttrInfoUpdate(id){
		//alert(id);
		window.location.href="/promotion/toUpdatePromotionAttrInfo.action?promotion.promotionId="+id;
	}
	function update(obj,id){
		var pSpan=$(obj).prev();
    	var pInput=$(obj).prev().prev();
    	var isShow =pInput.css('display');
    	if(isShow=='none'){
    		pInput.val(pSpan.html());
    		pInput.css({'display':'inline'});
    		pSpan.css({'display':'none'});
    		pInput.focus();
    		return;
    	}else{
    		saveNum(pSpan,pInput,id);
    	}
	}
	function saveNum(pSpan,pInput,id){
		var num = pInput.val();
		if(num==''){
			pInput.focus();
			return;
		}
		if(num==pSpan.html()){
			pInput.css({'display':'none'});
    		pSpan.html(num);
    		pSpan.css({'display':'inline'});
		}else{
			var type = $("#promotionTypeId"+id).val();
			$.ajax({
				url:'/promotion/updatePromotionPriority.action',
				type:'post',
				dataType:'json',
				data:{'promotion.promotionId':id,'promotion.promotionTypeId':type,'promotion.promotionPriority':num},
				success:function(data){
					var code = data.code;
					if(code==0){
						pInput.css({'display':'none'});
			    		pSpan.html(num);
			    		pSpan.css({'display':'inline'});
					}else{
						alert(data.title);
						pInput.focus();
						return;
					}
				},
				error:function(){
					alert('操作失败请联系管理员');
				}
			});
		}
	}
document.onkeydown=function(event){
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if(e && e.keyCode==27){ // 按 Esc要做的事情
        	var pInput = document.activeElement;
        	if(pInput.name=="promotionPriority"){
        		leaveUpdate(pInput);
        	 }
        }
        if(e && e.keyCode==113){ // 按 F2要做的事情
           }            
         if(e && e.keyCode==13){
        	 // enter 键要做的事情
        }
    }; 
    function leaveUpdate(obj){
    	var pInput = $(obj);
      	 pInput.css({'display':'none'});
      	 pInput.next().css({'display':'inline'});
    }
</script>
</head>
<s:set name="parent_name" value="'促销管理'" scope="request" />
<s:set name="name" value="'活动列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<s:form name="sectionsForm" method="post" action="queryPromotionList"
		namespace="/promotion" id="frm">
		<s:hidden name="promotion.nature" value="1" id="nature"></s:hidden>
		<!-- 标题条 -->
		<!--<div class="pagetitle">促销活动管理:</div>
		<!-- 按钮条 -->
		<!--<table width="98%" align="center" class="topbuttonbar" height="30"
			border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="90%" valign="middle">
				</td>
				<td width="10%" align="center">
					<!--a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a-->
				<!--</td>
			</tr>
		</table>-->
		<!-- 查询条件区域 -->
		<table width="98%" class="searcharea" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td align=left width="150px">活动名称：<input
					name="promotion.promotionName" id="promotionName" type="text"
					class="input_style"
					value="<s:property value='promotion.promotionName' />"></td>
				<td align="left" width="150px">审核状态： <s:select
						list="#{0:'全部',1:'未审核',2:'已审核'}" listKey="key" listValue="value"
						name="promotion.status"></s:select>
				</td>

				<td align="left" colspan="2" width="200px">活动时间： <input
					name="startTime" type="text" class="input_style"
					onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					value="<s:property value='startTime'  />" id="startTime">
					&nbsp;到&nbsp;<input name="endTime" type="text" class="input_style"
					onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					value="<s:property value='endTime'  />" id="startTime"></td>

				<td align=left width="150px">商家名称：<input
					id="autocomplete_forSuppliers" name="promotion.shopNames"
					id="shopNames" type="text" class="input_style"
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
					type="submit"  onClick="submitForm();" class="queryBtn button-blue-1" value="">
                    <input class="addBtn"
					type="button" value="" onClick="gotoAdd();"> <input
					class="delBtn" type="button" value="" onClick="del_sect();">
                    </td>
			</tr>
		</table>


		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center" width="3%"><input type='checkbox'
					name='allbox' onclick='checkAll(this)'></th>
				<th align="center" width="6%">活动ID</th>
				<th align="center" width="8%">标题</th>
				<th align="center" width="14%">活动名称</th>
				<th align="center" width="4%">类型</th>

				<th align="center" width="6%">商家类别</th>
				<th align="center" width="20%">起止时间</th>

				<th align="center" width="8%">时间状态</th>
				<th align="center" width="6%">审核状态</th>
				<th align="center" width="6%">优先级</th>
				<th align="center" width="15%">操作</th>
			</tr>
			<s:iterator value="page.dataList">
				<tr onMouseOver="this.style.backgroundColor='#def2fa'"
					onMouseOut="this.style.backgroundColor='#FFFFFF'">
					<td align="center" width="5%"><input type="checkbox"
						name="promotionId" value='<s:property value="promotionId"/>' /> <input
						type="hidden" id="status<s:property value='promotionId'/>"
						value="<s:property value='status'/>" /></td>
					<td align="center"><s:property value="promotionId" /></td>
					<td align="center"><s:property value="promotionTitle" /></td>
					<td align="center"><s:property value="promotionName" /></td>
					<td align="center"
						title="<s:property value='%{#request.promotionTypeMap[promotionTypeId]'/>"><s:property
							value="#request.promotionTypeMap[promotionType]" /></td>
					<input type="hidden"
						id="promotionTypeId<s:property value='promotionId'/>"
						value="<s:property value='promotionType'/>" />



					<td align="center"><s:if test="shopSort==3">康美自营代销</s:if> <s:elseif
							test="shopSort==1">所有</s:elseif> <s:else>
							<s:property value='shopNames' />
						</s:else></td>
					<td align="center"><s:if test="startTime!=null&&endTime!=null">从<s:date
								name="startTime" format="yyyy-MM-dd HH:mm:ss" />
							<br>到
				<s:date name="endTime" format="yyyy-MM-dd HH:mm:ss" />
						</s:if></td>
					<td align="center"><s:if test="onlineStatus==1">未上线</s:if> <s:elseif
							test="onlineStatus==2">正在进行</s:elseif> <s:else>已过期</s:else></td>
					<td align="center"><s:if test="status==1">未审核</s:if> <s:else>已审核</s:else></td>
					<td align="center"><input title='数字越大，优先级越高，按Esc退出修改'
						style="display: none" size="2"
						onkeyup="value=value.replace(/[^\d]/g,'') "
						value="<s:property value='promotionPriority' />"
						id="promotionPriority" name="promotionPriority" /> <span
						id="noEditpricrity" style="display: inline"><s:property
								value="promotionPriority" /></span>&nbsp; <img TYPE="button"
						style="cursor: pointer;" title="点击修改或者保存优先级"
						src="/etc/images/icon_modify.png"
						onclick="javascript:update(this,<s:property value='promotionId'/>);" />

					</td>
					<td align="center"><s:if test="onlineStatus==3&&status==2">
						</s:if> <s:else>
							<img TYPE="button" style="cursor: pointer;" title="修改信息"
								src="/etc/images/button_new/modify.png"
								onClick="gotoUpdate(<s:property value='promotionId'/>)">&nbsp;&nbsp;
					</s:else> <s:if test="status==1&&onlineStatus!=3">
							<!-- 未审核 -->
							<s:if test="productFilterType==2">
								<img TYPE="button" style="cursor: pointer;" title="管理商品"
									value="管理活动商品" src="/etc/images/button_new/upshelf.png"
									onClick="queryPromotionProductList(<s:property value='promotionId'/>)">&nbsp;&nbsp;
							</s:if>

						</s:if> <s:if test="status==1">
							<img TYPE="button" style="cursor: pointer;" title="删除"
								src="/etc/images/button_new/delete.png"
								onClick="javascript:deleteSelectedPromotion(this);">&nbsp;&nbsp;
						</s:if> <s:if test="status==2&&onlineStatus!=3">
							<!-- 已审核未过期 -->
							<s:if test="productFilterType==2">
								<img TYPE="button" style="cursor: pointer;" title="管理商品"
									value="管理活动商品" src="/etc/images/button_new/upshelf.png"
									onClick="queryPromotionProductList(<s:property value='promotionId'/>)">&nbsp;&nbsp;
						 </s:if>
						</s:if> <img TYPE="button" style="cursor: pointer;" title="查看信息"
						src="/etc/images/button_new/select.png"
						onClick="queryPrmotionInfo(<s:property value='promotionId'/>)">&nbsp;&nbsp;
						<s:if test="sellUpType!=0">
							<img TYPE="button" style="cursor: pointer;" title="复制"
								src="/etc/images/button_new/save.png"
								onClick="copyPrmotionInfo(<s:property value='promotionId'/>)">&nbsp;&nbsp;
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
<script type="text/javascript">
	function copyPrmotionInfo(id){
		$.ajax({
			url:'/promotion/copyPromotion.action',
			type:'post',
			dataType:'json',
			data:{'promotion.promotionId':id},
			async: false,
			success:function(data){
				var isSuccess = data.isSuccess;
				if(isSuccess==true){
					alert("复制成功！");
					window.location.reload();
				}else{
					alert("复制失败，请联系管理员！");
					//pInput.focus();
					return;
				}
			},
			error:function(){
				alert('操作失败请联系管理员');
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
</html>

