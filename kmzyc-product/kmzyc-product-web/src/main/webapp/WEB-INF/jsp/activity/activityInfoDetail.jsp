<%@page contentType="text/html;charset=UTF-8"  isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>活动详情</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" type="text/css" rel="stylesheet"/>
<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
;
</style>

<!--<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>-->
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>

</head>

<script type="text/javascript">
	$(document).ready(function(){
		//初始化活动类型
		if($('#activityType').val()==3){
			//显示渠道
			$('#activityChannels').css('display','block');
	    	var activityChannel=$('#activityChannel').val();
	    	var channels=activityChannel.split(',');
	    	for(var i=0;i<channels.length;i++){
	    		if(channels[i]=='KMYD'){
	    			$('#activityChannel1').attr('checked',true);
	    		}
	    		if(channels[i]=='KMB2B'){
	    			$('#activityChannel2').attr('checked',true);
	    		}
	    		if(channels[i]=='FLW'){
	    			$('#activityChannel3').attr('checked',true);
	    		}
	    		
	    	}
		}
	});
	
	function auditActivityById(activityId){
		if(confirm('确定审核通过?')){
			$.ajax({
				dataType:'json',
				url:'/supplierActivity/auditActivityById.action',
				data:{'activityInfo.activityId':activityId,
					'activityInfo.entryStartTime': $('#entryStartTime').val(),
					'activityInfo.entryEndTime':$('#entryEndTime').val(),
					'activityInfo.activityStartTime':$('#activityStartTime').val(),
					'activityInfo.activityEndTime':$('#activityEndTime').val() },
				success:function(data){
					alert(data.msg);
					if(data.result==false){
						return;
					}else{
						window.location.href="/supplierActivity/activityAuditList.action";
					}
				},
				error:function(){
					alert('请求失败，请稍后重试或与管理员联系！');
				}
			});
		}
	}

</script>

<body>

<s:set name="parent_name" value="'活动管理'" scope="request" />
<s:set name="name" value="'查看活动信息'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/supplierActivity/findActivityInfoById.action" method="POST" id="frm" name='frm'>
	
	<table width="95%" class="edit_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C7D3E2"
			style="border-collapse: collapse; font-size: 12px;">
		<tr>
			<th colspan="2" align="left" class="edit_title">活动详情</th>
		</tr>
		<tr>
			<th width="14%" align="right" class="eidt_rowTitle">活动名称：</th>
			<td>
				<s:property value="activityInfo.activityName"/>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">活动类型：</th>
			<td>
				<s:property value="#request.activityTypeMap[activityInfo.activityType]"/>
				<div id="activityChannels"  style="display:none">
						<br/>
						<span><input id="activityChannel1" type="checkbox"  value="1" disabled="disabled" />康美云店 </span>
						<span><input id="activityChannel2" type="checkbox"  value="2" disabled="disabled" />康美中药城 </span>
						<span><input id="activityChannel3" type="checkbox"  value="3" disabled="disabled" />返利网</span>
				</div>
			</td>
			<s:hidden id="activityType" name="activityInfo.activityType"></s:hidden>
			<s:hidden id="activityChannel" name="activityInfo.activityChannel"></s:hidden>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">活动费用：</th>
			<td>
				<s:if test="activityInfo.chargeType==1">
					<span>
						<input type="radio" name="activityInfo.activityChargeType" checked="checked" disabled="disabled" value="1"/>
						免费
					</span>
				</s:if>
				<s:if test="activityInfo.chargeType==2">
					<span >
						<input type="radio" name="activityInfo.activityChargeType" disabled="disabled" checked="checked" value="2"/>
						固定收费<s:property value="activityInfo.activityCharge.fixedCharge"/>元
					</span>
				</s:if>
				<s:if test="activityInfo.chargeType==3">
					<span>
						<input type="radio" name="activityInfo.activityChargeType" disabled="disabled" checked="checked" value="3"/>
						按推广商品数量收取，<s:property value="activityInfo.activityCharge.singleCharge" />元/个SKU
					</span>
				</s:if>
				<s:if test="activityInfo.chargeType==4">
					<span>
						<input type="radio" name="activityInfo.activityChargeType" disabled="disabled" checked="checked" value="4"/>
						按推广佣金比例收取，不低于单价<s:property value="activityInfo.activityCharge.commissionRate" />%
					</span>
				</s:if>
			</td>
		</tr>
		
		<tr>
			<th align="right" class="eidt_rowTitle">商家范围：</th>
			<td>
				<s:if test="activityInfo.supplierChoiceType==1">
					<span><input type="radio"  disabled="disabled" checked="checked" value="1"/>全部商家</span>
				</s:if>
				<s:if test="activityInfo.supplierChoiceType==2">
					<span><input type="radio"  disabled="disabled" checked="checked" value="1"/>商家店铺评分≥&nbsp;
					<s:property value="activityInfo.activitySupplierScore.greatEqualPoint" />&nbsp;分</span>
				</s:if>
				<s:if test="activityInfo.supplierChoiceType==3">
					<span><input type="radio" disabled="disabled" checked="checked" value="1"/>指定经营类目</span>
					<br/>
					<s:iterator id="activityCategory" value="activityInfo.activityCategorysList">
						<div data-name="<s:property value='categoryName'/>" class="j_div" style="position:relative;margin:3px 5px 2px 0;white-space:nowrap;height:15px;line-height: 15px;cursor:pointer;border-radius:17px;border-style:solid;border-width:1px;font-size:14px;padding:2px 19px;border-color:#edb8b8;background-color:#ffeaea;color:#c30!important;display:inline-block;vertical-align:middle;">
						<em style="margin-left:-8px;vertical-align:top;display:inline-block;font-style:normal;text-decoration:none;white-space:nowrap;line-height:15px;cursor:pointer;font-size:14px;" unselectable="on"><s:property value="categoryName"/></em>
						</div>
					</s:iterator>
				</s:if>
				<s:if test="activityInfo.supplierChoiceType==4">
					<span><input type="radio" disabled="disabled" checked="checked" value="1"/>指定入驻商家</span>
					<br/>
					<s:iterator id="activitySupplier" value="activityInfo.activitySupplierEntryList">
						<div data-name="<s:property value='companyShowName'/>" class="j_div" style="position:relative;margin:3px 5px 2px 0;white-space:nowrap;height:15px;line-height: 15px;cursor:pointer;border-radius:17px;border-style:solid;border-width:1px;font-size:14px;padding:2px 19px;border-color:#edb8b8;background-color:#ffeaea;color:#c30!important;display:inline-block;vertical-align:middle;">
						<em style="margin-left:-8px;vertical-align:top;display:inline-block;font-style:normal;text-decoration:none;white-space:nowrap;line-height:15px;cursor:pointer;font-size:14px;" unselectable="on"><s:property value="companyShowName"/></em>
						</div>
					</s:iterator>
				</s:if>
			</td>
		</tr>
		
		<tr>
			<th align="right" class="eidt_rowTitle">品牌范围：</th>
			<td>
				<s:if test="activityInfo.brandChoiceType==1">
					<span><input type="radio"  disabled="disabled" checked="checked" value="1"/>全部品牌</span>
				</s:if>
				<s:if test="activityInfo.brandChoiceType==2">
					<span><input type="radio"  disabled="disabled" checked="checked" value="1"/>指定品牌</span>
					<br/>
					<s:iterator id="activityBrand" value="activityInfo.activityBrandList">
						<div data-name="<s:property value='brandName'/>" class="j_div" style="position:relative;margin:3px 5px 2px 0;white-space:nowrap;height:15px;line-height: 15px;cursor:pointer;border-radius:17px;border-style:solid;border-width:1px;font-size:14px;padding:2px 19px;border-color:#edb8b8;background-color:#ffeaea;color:#c30!important;display:inline-block;vertical-align:middle;">
						<em style="margin-left:-8px;vertical-align:top;display:inline-block;font-style:normal;text-decoration:none;white-space:nowrap;line-height:15px;cursor:pointer;font-size:14px;" unselectable="on"><s:property value="brandName"/></em>
						</div>
					</s:iterator>
				</s:if>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">商家报名名额限制：</th>
			<td>
				<s:if test="activityInfo.supplierMaximum==0">
					<span><input type="radio"  disabled="disabled" checked="checked" value="1"/>不限制</span>
				</s:if>                        
				<s:if test="activityInfo.supplierMaximum>0">
					<span><input type="radio"  disabled="disabled" checked="checked" value="1"/>限制&nbsp;
					<s:property value="activityInfo.supplierMaximum" />&nbsp;个商家报名</span>
				</s:if>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">活动商品数量限制：</th>
			<td>
				<s:if test="activityInfo.skuMaximum==0">
					<span><input type="radio"  disabled="disabled" checked="checked" value="1"/>不限制</span>
				</s:if>                        
				<s:if test="activityInfo.skuMaximum>0">
					<span><input type="radio"  disabled="disabled" checked="checked" value="1"/>限制&nbsp;每个商家提交&nbsp;
					<s:property value="activityInfo.skuMaximum" />&nbsp;个商品</span>
				</s:if>
			</td>
		</tr>
		
		<tr>
			<th align="right" class="eidt_rowTitle">报名起止时间：</th>
			<td>
				<s:date name="activityInfo.entryStartTime" format="yyyy-MM-dd HH:mm:ss" />&nbsp;至&nbsp;
				<s:date name="activityInfo.entryEndTime" format="yyyy-MM-dd HH:mm:ss" />
				<s:hidden id="entryStartTime" name="activityInfo.entryStartTime"/>	
				<s:hidden id="entryEndTime" name="activityInfo.entryEndTime"/>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">活动起止时间：</th>
			<td>
				<s:date name="activityInfo.activityStartTime" format="yyyy-MM-dd HH:mm:ss" />&nbsp;至&nbsp;
				<s:date name="activityInfo.activityEndTime" format="yyyy-MM-dd HH:mm:ss" />
				<s:hidden id="activityStartTime" name="activityInfo.activityStartTime"/>	
				<s:hidden id="activityEndTime" name="activityInfo.activityEndTime"/>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">logo图片：</th>
			<td>
				<s:if test="activityInfo.logoPath!=null">
					<a href="<s:property value='imagePath'/><s:property value='activityInfo.logoPath'/>" target="_blank" title="查看图片">查看图片</a>
				</s:if>
			</td>
		</tr> 
		<tr>
			<th align="right" class="eidt_rowTitle">活动标签：</th>
			<td>
				<s:property value="activityInfo.activityLabel"/>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">活动级别：</th>
			<td>
				<s:if test="activityInfo.activityLevel==1">钻级&nbsp;&nbsp;</s:if>
				<s:if test="activityInfo.activityLevel==2">大型&nbsp;&nbsp;</s:if>
				<s:if test="activityInfo.activityLevel==3">中型&nbsp;&nbsp;</s:if>
				<s:if test="activityInfo.activityLevel==4">小型&nbsp;&nbsp;</s:if>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">所属行业：</th>
			<td>
				<s:property value="activityInfo.industry"/>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">关键字：</th>
			<td>
				<s:property value="activityInfo.activitySeo" />
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">活动介绍：</th>
			<td>
				<s:property value="activityInfo.activityDesc" />
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">活动说明：</th>
			<td>
				<s:property value="activityInfo.activityIntroduce" escape="false"/>
			</td>
		</tr>
		<tr>
			<th align="right" class="eidt_rowTitle">活动答疑：</th>
			<td>
				<s:property value="activityInfo.activityQuestions" escape="false"/>
			</td>
		</tr>
	</table>
	
	<!-- 底部 按钮条 -->
	<table width="18%" align="left" class="edit_bottom" height="30"
			border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;float: left;clear: left;margin-top:30px;">
		<tr>
			<s:if test="pageType=='activityAuditPage'">
				<td align="center">
					<input type="button" class="btn-custom btnStyle" value="审核通过" onClick="auditActivityById(<s:property value='activityInfo.activityId' />)"/>
				</td>
			</s:if>
			<td align="center">
				<input type="button" class="backBtn" onClick="javascript:window.history.go(-1);" />
			</td>
		</tr>
	</table>

</s:form>
</body>
</html>
