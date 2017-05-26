<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>余额提现管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="/etc/css/lottery.css" />
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript" src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript" src="/etc/js/app/bnesAcctEnchashment/bnesAcctEnchashmentList.js"></script>
		<script type="text/javascript">

		$(function(){
			if($('#fromConfirmMenu').val()=='yes'){
				$('select[name="bnesAcctEnchashment.enchashmentStatus"]').attr("disabled","disabled");
			}
			$('#dateRange').val('<s:property value="dateRange"/>');
		})
		
		function exportDate(){
			document.bnesAcctEnchashmentForm.action="/accounts/bnesAcctEnchashment_exportDate.action";
	    	document.bnesAcctEnchashmentForm.submit();
		/*	$.ajax({
	            async: false,
	            url: '/accounts/exportDate.action?bnesAcctEnchashment.loginAccount='+loginAccount,
	            cache:false,
	            type:'POST',
	            success: function (data) {
	
	            },
	            error:function(){
					alert('生成报表出错！');
		        }
	        });
		*/
		}
		
		 function querySubmit(){
	    	 document.bnesAcctEnchashmentForm.action="/accounts/bnesAcctEnchashment_PageList.action";
	    	 document.bnesAcctEnchashmentForm.submit();
	    }
	
		</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'账务管理'" scope="request" />
		<s:set name="name" value="'余额提现管理'" scope="request" />
		<s:set name="son_name" value="'列表'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div style="height: 90%; overflow-y: auto;">
			<s:form name="bnesAcctEnchashmentForm" action="/accounts/bnesAcctEnchashment_PageList.action" method="post">
			    <!-- 根据该字段判断是否是提现确认页面  -->
			    <input type="hidden" id="fromConfirmMenu" name="fromConfirmMenu" value='<s:property value="fromConfirmMenu"/>' />
				<!-- 查询条件 -->
				<table width="98%" align="center" border="0" class="content_table" cellpadding="0" cellspacing="0">
					<tr>
						<td align="right">申请账号：</td>
						<td><input name="bnesAcctEnchashment.loginAccount" type="text" value='<s:property value="bnesAcctEnchashment.loginAccount"/>' ></td>
						<td align="right">提现人说明：</td>
						<td><input name="bnesAcctEnchashment.enchashmentDepict" type="text" value='<s:property value="bnesAcctEnchashment.enchashmentDepict"/>' ></td>
						<td align="right">提现来源：</td>
						<td>
							<s:select list="#request.enchashmentResourceEnumMap" listKey="key" listValue="value" headerKey="" headerValue="--全部--" name="bnesAcctEnchashment.enchashmentResource">
							</s:select>
						</td>	
						
					</tr>
					<tr>
					<td align="right">审核状态：</td>
					<td>
					<c:if test="${isStatus==1}">
					<input type="hidden" name="bnesAcctEnchashment.enchashmentStatus"  value = "3">
					 	审核通过
					</c:if>
					<c:if test="${isStatus!=1}">
						<s:select list="#request.enchashmentStatusEnumMap" listKey="key" listValue="value" headerKey="" headerValue="--全部状态--" name="bnesAcctEnchashment.enchashmentStatus"></s:select>
					</c:if>
					</td>
					<td align="right">申请提现时间：</td>
						<td>
						<input type="text" name="bnesAcctEnchashment.createDateBegin"  id="d523" class="Wdate"  value ="<s:date name = 'bnesAcctEnchashment.createDateBegin' format='yyyy-MM-dd HH:mm:ss' />"     onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'d521\')}'})"  />
						至：
						<input type="text" name="bnesAcctEnchashment.createDateEnd" id="d521" class="Wdate"  value ="<s:date name = 'bnesAcctEnchashment.createDateEnd' format='yyyy-MM-dd HH:mm:ss' />"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'d523\')}'})" />
						</td>
						<td align="right"></td>
						<td></td>	
						
<!-- 						<td align="right">提现时间范围：</td> -->
<!-- 						<td> -->
<%-- 							<select class="condition" id="dateRange" name="dateRange" style="width:96px" onchange="changeDateRange()"> --%>
<!-- 						   		<option value="all">全部</option> -->
<!-- 						   		<option value="today">今天</option> -->
<!-- 						   		<option value="ysday">昨天</option> -->
<!-- 						   		<option value="tsQuearter">本季</option> -->
<!-- 						   		<option value="lsQuearter">上季</option> -->
<!-- 						   		<option value="tsYear">本年</option> -->
<!-- 						   		<option value="lsYear">上年</option> -->
<%-- 						   	</select> --%>
<!-- 						</td> -->

						
						
					</tr>
					<tr>
					<td align="right">提现完成时间：</td>
						<td>
						<input type="text" name="bnesAcctEnchashment.finishDateBegin"  id="finishDateBegin" class="Wdate"  value ="<s:date name = 'bnesAcctEnchashment.finishDateBegin' format='yyyy-MM-dd HH:mm:ss' />"     onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'finishDateEnd\')}'})"  />
						至：
						<input type="text" name="bnesAcctEnchashment.finishDateEnd" id="finishDateEnd" class="Wdate"  value ="<s:date name = 'bnesAcctEnchashment.finishDateEnd' format='yyyy-MM-dd HH:mm:ss' />"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'finishDateBegin\')}'})" />
						</td>
					</tr>
					<tr>
					<td colspan="2" align="left" style="font-weight:bold;">&nbsp;提现总金额：<fmt:formatNumber value="${totalAmount}" type="currency" pattern="#,##0.00元"/>（仅为审核通过+提现完成状态下的统计）</td>
					<td colspan="10">&nbsp;</td>
                    <td><INPUT TYPE="submit" class="queryBtn" value="" onclick = "querySubmit()">&nbsp;&nbsp;<input type = "button"  class="btn-custom" value="导出excel" onClick="exportDate()">
                        </td>	
					</tr>
				</table>
				<!-- 数据列表区域 -->
				<table width="98%" class="list_table" cellpadding="3" align="center"
					cellspacing="0" border="1">
					<tr>
						<th width="5%">
							<input type='checkbox' name='allbox'
								onclick="checkAll(this,'enchashmentIds')">
						</th>
						<th>
							申请账号
						</th>
						<th>
							提现来源
						</th>
						<th>
							提现人说明
						</th>
						<th>
							提现金额
						</th>
						<th>
							申请时间
						</th>
						<th>
							审核状态
						</th>
						<th>
							审核时间
						</th>
						<th>
							提现完成时间
						</th>
						<th>
							操作
						</th>
					</tr>
					<s:iterator id="custiterator" value="page.dataList">
						<tr>
							<td width="5%">
								<input type="checkbox" name="enchashmentIds"
									value='<s:property value="enchashmentId"/>'>
							</td>
							<td>
								<s:property value="loginAccount" />
							</td>
							<td>
								<s:property   value="#request.enchashmentResourceEnumMap[enchashmentResource]"  />
							</td>
							<td>
								<s:property value="enchashmentDepict" />
							</td>
							<td>   
								<fmt:formatNumber value="${enchashmentAmount}" type="currency" pattern="#,##0.00元"/>
							</td>
							<td>
								<s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<s:property value="#request.enchashmentStatusEnumMap[enchashmentStatus]" />
							</td>
							<td>
								<s:date name="modifyDate" format="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
							   <s:date name="finashDate" format="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<s:if test="enchashmentStatus==3 && fromConfirmMenu=='yes'">
									<img title="提现确认" style="cursor: pointer;" src="/etc/images/icon/search.png" onClick="gotoConfirmEditDetail(<s:property value='enchashmentId' />,<s:property value='enchashmentResource'/>)"/>
								</s:if>
								<s:else>
									<img title="查看" style="cursor: pointer;" src="/etc/images/icon/search.png" onClick="gotoDetail(<s:property value="enchashmentId" />,<s:property value="enchashmentResource"/>)"/>
								</s:else>
							</td>
						</tr>
					</s:iterator>
				</table>
				<table class="page_table" width="98%" align="center" cellpadding="0"
					cellspacing="0" border="0">
					<tr>
						<td>
							<s:set name="form_name" value="'bnesAcctEnchashmentForm'" scope="request"></s:set>
							<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
	</body>
</html>
