<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<s:form id="sform" name="sform" action="applyPrepare.action" method="post" theme="simple">
<s:hidden name="page" id="page"/>
<s:token></s:token>
<div class="l-right user-m">
	<div class="o-mt">
  	<h2>退换货申请</h2>
      <div class="Inquiry">
      	<div class="ui-form">
          		<%--
				<s:select name="searchKeyword.createDate" id="searchKeyword.createDate" list="createDateOptionsMap" 
					onchange="javascript:query()" cssClass="sele" />
				<s:select name="searchKeyword.status" id="searchKeyword.status" list="#{0:'全部状态',1:'已提交待审核',2:'已通过待退货',3:'已退货待取件',4:'已取件待退款/发货',5:'已退款待确认',6:'已发货待签收',7:'已完成',8:'已驳回'}"
					onchange="javascript:query()" cssClass="sele" />
				 --%>
				<input type="hidden" name="searchKeyword.orderStatus" value="5"/>
				<s:textfield name="searchKeyword.keyword" id="searchKeyword.keyword" 
					maxlength="20" cssClass="Inquiry-text j_dealKeyword"/>　
				<input name="search" id="search" type="button" value="搜 索" class="bti btn j_querySearch">
          </div>
      </div>
  </div>
  <div class="user-m fn-t10">
      <div class="mc">
      	<div class="ui-table">
          	<table class="ui-table user-table ">
                  <thead>
                      <tr>
                          <th  class="td-s7"  >订单编号</th>
                          <th  class="td-s8" >商品名称</th>
                          <th  class="td-s7"  >下单时间</th>
                          <th>确认收货时间</th>
                      </tr>
                  </thead>
                  <tbody>
                  	  <s:iterator value="pagintion.recordList" var="var" status="st">
                      <tr>
                    
						  <td><a class="fn-blue" href="queryOrderDetail.action?orderMainCode=${var.orderCode}">${var.orderCode}</a></td>
                          <td>
							  <s:iterator value="#var.orderItemList" var="orderItemVar" status="orderItemStatus">
								
								<span class="img">	
									<a name="productlink" href="${cmsPagePath}${orderItemVar.defaultProductImage.skuId}.shtml" target="_blank">
                        			<img  src="${productImgServerUrl}${orderItemVar.defaultProductImage.imgPath}" title="${orderItemVar.commodityName}" onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'"/>
                        			</a>
                        			<s:if test="#orderItemVar.isReturning == 0" >
                        			<!-- 
                        			<s:if test="#orderItemVar.isOverdue == 0" >
                         			</s:if>
                         			 -->
                         	        <s:if test="#var.orderType != 7 " >   
                        			<a href="script:void(0)" data-id="${orderItemVar.orderItemId}" data-pv="${orderItemVar.commodityPv}"  data-type="<s:property value='#session.b2b_login_remark'/>"class="green-btn" id="apply">申请</a>
                        			</s:if>
                         			
                         			</s:if>
                         			<s:else>
                         			<a href="javascript:void(0)" class="green-btn">已申请</a>
                         			</s:else>
                         		</span>
                         		<s:if test="#orderItemStatus.count%4 == 0"><br/></s:if>
                         	  </s:iterator>
                          </td>
                          <td>
                          <s:date name="#var.createDate" format="yyyy-MM-dd"/><br/>
                          <s:date name="#var.createDate" format="HH:mm:ss"/>
                          </td>
                          <td>	
                          		<s:date name="#var.finishDate" format="yyyy-MM-dd"/><br/>
								<s:date name="#var.finishDate" format="HH:mm:ss"/>
						  </td>
                      </tr>
                      </s:iterator>
                  </tbody>
              </table>
          	<div class="fn-tr fn-t10">
					<div class="ui-page">
						<!-- 分页组件 -->
            			<tiles:insertDefinition name="pagination"/>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
</s:form>
