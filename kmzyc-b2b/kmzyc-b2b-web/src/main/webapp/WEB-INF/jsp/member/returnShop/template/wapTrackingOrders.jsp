<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="container">
	<div class="row">
        <div class="col-lg-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <ul class="text-list">
                        <li>退换货编号：<span class="text-success">${oalter.orderAlterCode}:${oalter.alterComment}</span></li>
                        <li>状态：<span>${status}</span></li>
                        <li>最新进度：<span>[${time}] ${alterComment}</span> </li>
                    </ul>
                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>处理时间</th>
                                <th>进度信息</th>
                            </tr>
                            </thead>
                            <tbody>
                         	<s:iterator value="#request.listVo" var="orderAlterTraceInfo" >
                           	<tr>
                                <td><s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd HH:mm:ss"/></td>
                                <td>${orderAlterTraceInfo.info}</td>
                            </tr>
                          	</s:iterator>
                            </tbody>
                        </table>
                    </div>
                    <a class="btn btn-success btn-block" href="queryWapReturnDetail.action?orderAlterCode=${oalter.orderAlterCode}" id="cancel">返回退换货详情页</a>
                </div>
            </div>
        </div>
    </div>
</div>