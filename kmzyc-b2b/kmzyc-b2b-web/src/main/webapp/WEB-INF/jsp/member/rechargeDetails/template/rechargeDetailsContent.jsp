<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>

<div class="l-right user-m">
	<div class="o-mt">
		<h2>账号余额明细记录</h2>
	</div>
			<div class="Inquiry">
				<div class="ui-form">
					<ul class="Order-Number fn-clear">
                        
						<li>账号可用余额：<strong class="fn-red fn-f18">￥<fmt:formatNumber
									value="${rechargeDetails.amountAvlibal}" type="currency"
									pattern="#,##0.00" />
						</strong></li>
						<li>账号状态：<strong class="fn-red fn-f18"> <s:if
									test="rechargeDetails.accountStatus==0">
                                                                                                        禁用
                                </s:if> <s:else>
                                                                                                       有效
                                </s:else>
						</strong></li>
					</ul>

					<p>
					<div class="recharge-date">
						<s:form action="queryRechargeDetail.action" method="post">
							<s:hidden name="page" id="page" />
							<label>超始日期：</label>
							<input type="text" name="startDate"
								value='<s:date name="startDate" format="yyyy-MM-dd"/>'
								id="startDate" class="u-text-date "/>
							<label>截止日期：</label>
							<input type="text" name="endDate"
								value='<s:date name="endDate" format="yyyy-MM-dd"/>'
								id="endDate" class="u-text-date"/>
							&nbsp;&nbsp;<s:submit value="查询" cssClass="bti btn"></s:submit>
						</s:form>
                      </div>
					</p>
				</div>
			</div>

					<div class="ui-table">
						<table class="ui-table user-table">
							<thead>
								<tr>
									<th class="td-s2">时间</th>
									<th class="td-s7">账户流水号</th>
									<th class="td-s7">金额（元）</th>
									<th class="td-s5">状态</th>
									<th class="td-s1">来源</th>
									<th>备注</th>
								</tr>
							</thead>
							<tbody>
								<s:iterator value="pagintion.recordList" id="recharge"
									status="status">
									<tr>
										<td><s:date name="createDate"
												format="yyyy-MM-dd HH:mm:ss" /></td>
										<td>${recharge.accountNumber}</td>
											<!-- 负数+ 成功-->
											<s:if test="#recharge.status ==1 && #recharge.amount<0">
												<td class="fn-red">
											</s:if>
											<!-- 正数 + 成功-->
											<s:elseif test="#recharge.status ==1 && #recharge.amount>0">
												<td class="fn-green">
											</s:elseif>
											<!-- 正数和负数 + 失败-->
											<s:else>
												<td>
											</s:else>
										
										<s:if test="#recharge.amount >0">
											+<fmt:formatNumber value="${recharge.amount }" type="currency"
												pattern="#,##0.00" />
										</s:if>	
										<s:else>
											<fmt:formatNumber value="${recharge.amount }" type="currency"
												pattern="#,##0.00" />
										</s:else>									
										</td>
										<!-- 状态:失败==2 -->
		                                <s:if test="#recharge.status==2">
		                             	 <td class="fn-red">
		                                </s:if>
		                                <s:else>
		                                	<td >
		                                </s:else>
			                                <s:iterator value="#request.rechargeDetailStatusMap" id="it">
												<s:if test="#it.key==#recharge.status">
														<s:property value="value"/>
												</s:if>
											</s:iterator>
		                               </td>
		                                 <td>
			                                <s:iterator value="#request.rechargeDetailTypeMap" id="it">
												<s:if test="#it.key==#recharge.type">
												<s:property value="value"/>
												</s:if>
											</s:iterator>
		                               </td>
		                                <td>${recharge.content}</td>
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
<!--fn-right-->
</div>
<!--内容区 END-->
