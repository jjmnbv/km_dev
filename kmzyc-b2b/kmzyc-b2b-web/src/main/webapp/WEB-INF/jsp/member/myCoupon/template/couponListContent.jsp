<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<s:form id="couponFrom" name="sform" action="queryCouponList.action"
	method="post" theme="simple">
	<input value="${couponStatus}" id="searStatus" type="hidden"  name="couponStatus"  />
	 
	<s:hidden name="page" id="page" />
	<s:token></s:token>
	<div class="l-right user-m">
		<div class="o-mt">
			<h2>我的优惠券</h2>
			<div class="Inquiry">
				<div class="ui-form">
					<ul class="fn-clear">
						<li><strong>激活优惠券：</strong></li>
						<li><s:textfield name="searchKeyword.keyword"
								id="searchKeyword.keyword" maxlength="12"
								cssClass="Inquiry-text" /> <input name="search" id="search"
							type="button" value="点击激活" class="btn-b btn-b-success"></li>
					</ul>
				</div>
			</div>
		</div>
		 
		<div class="user-m fn-t10">
			
			<div class="mt">
				<ul class="tab">
					<li id="record" ><s></s><b></b><a class="queryData" data-status="3">未使用优惠券</a></li>
					<li id="exchange"><s></s><b></b><a class="queryData" data-status="4">已使用优惠券</a></li>
					<li id="loseTime"><s></s><b></b><a class="queryData" data-status="0">失效优惠券</a></li>
				</ul>
			 
			</div>
			 
			<div class="mc">
				<div class="ui-table">
					<table class="ui-table user-table">
						<thead>
							<tr>
							   <th class="td-s5">编号</th>
								<th class="td-s5">名称</th>
								<th class="td-s1">面值</th>
								<th class="td-s7">时间限制</th>
								<th class="td-s5">状态</th>
								<th>说明</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="#request.pagintion.recordList"
								var="couponGrantVar" status="couponGrantVarStatus" >
								 
								<tr>
									<td>
										<span href="javascript:void(0)" target="_blank">
												<div>
													<s:iterator value="#couponGrantVar.couponList"
														var="couponVar" >			
						                                 <s:property  value="#couponGrantVar.couponGrantId"/> 
						                             </s:iterator>
												</div>
										</span>			
									</td>
									
									<td>
		                                    ${couponVar.couponName}
		                            </td>
		                            
									<td class="p-price">
										<s:iterator value="#couponGrantVar.couponList" var="couponVar">
											<div>满${couponVar.payLeastMoney}可用</div>
											<strong> <fmt:formatNumber
													value="${couponVar.couponMoney}" type="currency"
													pattern="#,##0.00元" />
											</strong>
										</s:iterator>
									</td>
									
									<td class="p-price">
									 	  <div class="col-9 kai_status" >从 <s:date name="#couponGrantVar.starttime_coupon"
												format="yyyy-MM-dd HH:mm:ss" /></div>
		                                  <div >至
		                                 	 <span class="wei_status"  id="cur_<s:property value="#couponGrantVarStatus.index" />">
		                                 	  	<s:date name="#couponGrantVar.endtime_coupon" format="yyyy-MM-dd HH:mm:ss" />
		                                 	   </span>
										  </div>
									</td>
									
							<td class="p-price nowStatus" id="str_<s:property value="#couponGrantVarStatus.index" />"  ><s:if
									test="#couponGrantVar.couponStatus == 1">
                                  未发放
                                  </s:if> <s:if
									test="#couponGrantVar.couponStatus == 2">
                                 已发放
                                  </s:if> <s:if
									test="#couponGrantVar.couponStatus == 3">
									 
                                    未使用
                                  </s:if> <s:if
									test="#couponGrantVar.couponStatus == 4">
                                   已使用
                                   </s:if> <s:if
									test="#couponGrantVar.couponStatus == 5">
                                   已过期
                                    </s:if> <s:if
									test="#couponGrantVar.couponStatus == 6">
                                    已作废
                                     </s:if> <s:if
									test="#couponGrantVar.couponStatus == 7">
                                   已冻结
                                     </s:if></td>
							<td class="p-price">${couponVar.couponDescribe}</td>
							</tr>
							</s:iterator>
						</tbody>
					</table>
				</div>
				<div class="fn-tr fn-t10">
					<div class="ui-page">
						<!-- 分页组件 -->

						<tiles:insertDefinition name="pagination" />

					</div>
				</div>
			</div>
		</div>
	</div>
</s:form>
</div>


