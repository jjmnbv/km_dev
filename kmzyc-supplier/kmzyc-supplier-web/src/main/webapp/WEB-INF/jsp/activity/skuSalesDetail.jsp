<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <jsp:include page="/WEB-INF/jsp/common/template.jsp">
        <jsp:param name="titlePrefix" value="活动管理"></jsp:param>
    </jsp:include>
    <title>活动管理</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
<div class="container-fluid">
    <div class="row-fluid">
        <jsp:include page="/WEB-INF/jsp/common/menubars/leftMenu_activity.jsp"></jsp:include>
        <div class="content">
            <div class="row-fluid">
                <%-- block --%>
                <div class="block_01">
                    <div class="navbar-inner">
                        <ul class="breadcrumb">
                            <i class="icon-home hide-sidebar"><a href='#' title="Hide Sidebar" rel='tooltip'>&nbsp;</a></i>
                            <li>活动 <span class="divider">/</span></li>
                            <li>活动推广效果<span class="divider">/</span> </li>
                            <li>销售明细</li>
                        </ul>
                    </div>

                    <div class="block-content collapse in">
                        <%--开始--%>
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#home" data-toggle="tab">销售明细</a></li>
                        </ul>

                        <%--搜索开始--%>
                        <s:form action="/supplierActivityResultAction/findMyActivitySkuSalesDetail.action" method="post" id="frm" name="frm">
                            <s:hidden name="page" id="page"/>
                            <s:hidden name="supplierEntryId" id="supplierEntryId"/>
                            <s:hidden name="skuId" id="skuId"/>
                            <div class="com_topform">
                                <ul>
                                    <li>
                                        <label>订单号：</label>
                                        <s:textfield name="orderCodeForSearch" placeholder="" cssStyle="width:170px;"/>
                                    </li>
                                    <li>
                                        <label>订单状态：</label>
               							<s:select name="orderStatusForMenuQuery" list="#request.orderStatusMapForOrderQuery" listKey="key"
											listValue="value" headerKey="" headerValue="全部"
											style="width:110px;" id="orderStatusForMenuQuery"></s:select>	
                                    </li>
                                    <li>
                                        <label>下单时间：</label>
										<input type="text" placeholder="" maxlength="10" readonly="readonly" class="ui-input ui-form-date" name="orderBeginDate" style="width:180px;" value='<s:property value="orderBeginDate" />' id="orderBeginDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'orderEndDate\'),\'%y-%M-%d %H:%m:%s\'}'})" />
												&nbsp;至&nbsp;
										<input type="text" placeholder="" maxlength="10" readonly="readonly" class="ui-input ui-form-date" name="orderEndDate" style="width:180px;" value='<s:property value="orderEndDate" />' id="orderEndDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'orderBeginDate\')}'})" />
                                    </li>
                                    <li>
                                        <button class="btn btn-primary">
                                            <i class="icon-search icon-white activity_search"></i> 搜索
                                        </button>
                                    </li>
                                </ul>
                            </div>
                            <%--搜索结束--%>
                           <table cellpadding="0" cellspacing="0" border="0" class="table com_tablest">
								<thead>
                                </thead>
                                <tbody>
									<tr>
										<th >商品</th>
										<th class="width90">SKU</th>
										<th class="width90">单价</th>
										<th class="width90">数量</th>
										<th class="width90">订单状态</th>
										<th class="width90">操作</th>
									</tr>
								</tbody>
							</table>
							
							<s:iterator id="activitySkuOrderEntry" value="pagintion.recordList" var="activitySkuOrderEntryVo" status="st">
								<table cellpadding="0" cellspacing="0" border="0" class="table  table-bordered" >
									<thead>
										<tr class="tablesbg">
											<th colspan="5" class="textL"> 订单号：
												<s:if test="orderStatus==16">[主订单]</s:if>
												<s:if test="parentOrderCode!=null">[子订单]</s:if>
													<s:property value="orderCode" />&nbsp;&nbsp;&nbsp;
												下单时间：&nbsp;
												<s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" />
												<s:if test="finishDate!=null">&nbsp;&nbsp;&nbsp; 完成时间：&nbsp;
												<s:date name="finishDate" format="yyyy-MM-dd HH:mm:ss" /></s:if>
											</th>
										</tr>
									</thead>
									<tbody>
										<tr>			
											<td>
											<s:if test="orderItemList!=null && orderItemList.size>3">
												<div class="orders3p">
											</s:if>
											<s:iterator id="activitySkuOrderItem" value="pagintion.recordList.get(#st.index).orderItemList" var="activitySkuOrderItemVo">
												 <table width="100%" border="0" class="newform">
								                    <tbody>
								                      <tr>
								                        <td width="120px">
								                        	<a target="_blank" href="<s:property value='cmsPagePath'/><s:property value='productSkuId'/>.shtml" class="pull-left">
								                        		<img class="thumbnail" src="<s:property value="imagePath"/><s:property value="imageUrl" />">
								                        	</a>
								                        </td>
								                        <td class="textc">
								                        	<a target="_blank" href="<s:property value='cmsPagePath'/><s:property value='productSkuId'/>.shtml" 
								                        		title="<s:property value="commodityTitle" />"><s:property value="commodityTitle" />
								                        	</a>
								                        </td>
								                        <td width="80px">
								                        	<s:if test="commoditySku!=null"><s:property value="commoditySku" /></s:if>
								                        </td>
								                        <td width="80px">
								                        	<s:property value="commodityUnitPrice" />
								                        </td>
								                        <td width="80px">
								                        	<s:property value="commodityNumber" />
								                        </td>
								                      </tr>
								                    </tbody>
								                  </table>	
											</s:iterator>
											<s:if test="orderItemList!=null && orderItemList.size>3">
											</s:if>
											</td>
											<td class="width80">
												<s:if test='orderStatusStr=="待风控评估"'>
													订单风控中
												</s:if>
												<s:elseif test='orderStatusStr=="风控通过"'>
													已锁库存
												</s:elseif>
												<s:else>
													<s:property value="orderStatusStr" />
												</s:else>
											</td>
											<td class="width90">
												<button data-orderCode="<s:property value="orderCode" />" 
													    supplierEntry-id="<s:property value="supplierEntryId"/>" 
                                        	 		    product-skuId="<s:property value="skuId"/>"
													class="btn btn-mini btn-success width66 j_view_order">查看订单</button>
											</td>		
										</tr>
									</tbody>
								</table>
							</s:iterator>
                            <div class="fn-clear fn-mt10">
                                <%-- 分页组件 --%>
                                <tiles:insertDefinition name="paginationBottom"/>
                            </div>
                        </s:form>
                        <%--结束--%>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>
</html>