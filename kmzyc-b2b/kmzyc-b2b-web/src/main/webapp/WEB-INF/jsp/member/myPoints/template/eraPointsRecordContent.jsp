<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<form action="queryPointsRecordList.action" id="pointRecordForm" method="post" >
<s:hidden name="page" id="page"/>
<div class="l-right user-m">
	<div class="o-mt">
		 <h2>我的积分</h2>
			<div class="user-block-tip sd-block" style="position: relative;">
					<div class="sd-left">
						<span class="gray">当前等级</span>
						<span class="green"><s:property value="eraInfo.eraGradeName"/></span>
						<!--<span class="red"><a href="javascript:void(0)" target="_blank">我要提升等级</a> </span> -->
					</div>
					<div class="sd-left middle">
						<span class="gray">体验积分：</span>
						<span class="green"><s:property value="eraInfo.expIntegralValue"/></span>
					</div>

					<div class="sd-left middle2">
						<span class="gray">商城积分：</span>
						<span class="green"><s:property value="scoreView.curScore" /></span>
					</div>
					<div class="sd-left sd-right">
						<span class="gray">兑换优惠券：</span>
						<div class="sd-icon">
							<s:if test='#request.couponTypes.indexOf("5") >= 0'>
								<span data-value="512" class="small">￥<strong>5</strong></span>
							</s:if>
							<s:if test='#request.couponTypes.indexOf("5") < 0'>
								<span>￥<strong>5</strong></span>
							</s:if>
							<s:if test='#request.couponTypes.indexOf("10") >= 0'>
								<span class="small" data-value="325">￥<strong>10</strong></span>
							</s:if>
							<s:if test='#request.couponTypes.indexOf("10") < 0'>
								<span>￥<strong>10</strong></span>
							</s:if>
							<s:if test='#request.couponTypes.indexOf("20") >= 0'>
								<span class="small" data-value="326">￥<strong>20</strong></span>
							</s:if>
							<s:if test='#request.couponTypes.indexOf("20") < 0'>
								<span>￥<strong>20</strong></span>
							</s:if>
							<s:if test='#request.couponTypes.indexOf("50") >= 0'>
								<span class="small" data-value="332">￥<strong>50</strong></span>
							</s:if>
							<s:if test='#request.couponTypes.indexOf("50") < 0'>
								<span>￥<strong>50</strong></span>
							</s:if>
							<s:if test='#request.couponTypes.indexOf("100") >= 0'>
								<span class="small" data-value="333">￥<strong>100</strong></span>
							</s:if>
							<s:if test='#request.couponTypes.indexOf("100") < 0'>
								<span class="big">￥<strong>100</strong></span>
							</s:if>
						</div>
						<div class="sd-left sd-bottom">
							<span class="gray">康美中药城订单不可使用</span>
						</div>
						
					</div>
				</div>
            </div>
            <div class="user-m fn-t10">
                <div class="mt">
					<ul class="tab">
						<li class="curr"><s></s><b></b><a>积分明细记录</a></li>
					</ul>
				</div>
                <div class="mc">
                	<div class="Inquiry">
						<div class="ui-form">
							<span>您的剩余总积分：<strong class="fn-red"><s:property value="scoreView.curScore" />分</strong></span>
							<label>超始日期：</label><input id="d4311" value="<s:property value="searchKeyword.dateBegin" />" class="u-text-date Wdate"  name="searchKeyword.dateBegin" type="text" />
							<label>截止日期：</label><input id="d4312" value="<s:property value="searchKeyword.dateEnd" />" class="u-text-date Wdate"  name="searchKeyword.dateEnd" type="text" />
							<input class="bti btn" type="button" id="btnQuery" value="查 询" name="" >
						</div>
					</div>
                	<div class="ui-table">
                    	<table class="ui-table user-table ">
                            <thead>
                                <tr>
                                    <th class="td-s7">积分项目</th>
                                    <th class="td-s5">获得积分</th>
                                    <th class="td-s5">消费积分</th>
                                    <th class="td-s7">日期</th>
                                    <th class="td-s7">备注</th>
                                </tr>
                            </thead>
                            <tbody>
                              <s:iterator value="#request.pagintion.recordList">
								<tr>
									<td><s:property value="discribe" /></td>
									<s:if test='scoreType == 1'>
										<td>+<s:property value="scoreNumber" /></td>
										<td>&nbsp;</td>
									</s:if>
									<s:else>
										<td>&nbsp;</td>
										<td>-<s:property value="scoreNumber" /></td>
									</s:else>
									<td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss" /></td>
									<td><s:property value="discribe" /></td>
								</tr>
							  </s:iterator>
                            </tbody>
                        </table>
                    </div>
                </div>
        <div class="fn-tr fn-t10">
			<div class="ui-page">
				<!-- 分页组件 -->
		        <tiles:insertDefinition name="pagination"/>
			</div>
		</div>
	</div>
</div>
</form>
</div>