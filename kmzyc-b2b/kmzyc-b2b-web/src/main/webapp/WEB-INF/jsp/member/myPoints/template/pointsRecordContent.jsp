<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<form action="queryPointsRecordList.action" id="pointRecordForm" method="post" >
<s:hidden name="page" id="page"/>
<div class="l-right user-m">
			<div class="o-mt">
            	<h2>我的等级</h2>
                <div class="grade-box">
                <div class="gr-left">
                <div class="gr-tit">当前等级</div>
                <div class="gr-info select1" <s:if test="scoreView.curLevelCode !='LV0001'">style="display:none;"</s:if>><s:property value="scoreView.curLevel"/>会员</div>
                <div class="gr-info select2" <s:if test="scoreView.curLevelCode !='LV0002'">style="display:none;"</s:if>><s:property value="scoreView.curLevel"/>会员</div>
                <div class="gr-info select3" <s:if test="scoreView.curLevelCode !='LV0003'">style="display:none;"</s:if>><s:property value="scoreView.curLevel"/>会员</div>
                <div class="gr-info select4" <s:if test="scoreView.curLevelCode !='LV0004'">style="display:none;"</s:if>><s:property value="scoreView.curLevel"/>会员</div>
                <div class="gr-info select5" <s:if test="scoreView.curLevelCode !='LV0005'">style="display:none;"</s:if>><s:property value="scoreView.curLevel"/>会员</div>
                <div class="gr-info" <s:if test="scoreView.curLevelCode !='LV0006'">style="display:none;"</s:if>><s:property value="scoreView.curLevel"/>会员</div>
                <div class="gr-number"><s:property value="scoreView.cardNum"/></div>
                </div>
                <div class="gr-con">
                <div class="gr-tit">商城积分</div>
                <div class="gr-integral"><s:property value="scoreView.curScore"/></div>
                </div>
                <div class="gr-right"><!-- 广告位 <img src="/res/images/common/grade-img.jpg"/> --></div>
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
										<s:if test='scoreNumber == 0'>
											<td>0</td>
										</s:if>
										<s:else>
											<td><s:property value="scoreNumber" /></td>
										</s:else>
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