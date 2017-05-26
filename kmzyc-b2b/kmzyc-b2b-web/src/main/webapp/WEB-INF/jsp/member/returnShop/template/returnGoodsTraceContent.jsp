<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="l-right user-m">
          	<div class="o-mt">
            	<h2>退换货记录进度跟踪</h2>
                <div class="OrderInfo" >
                	<ul class="Order-Number">
                    	<li>订单编号：${oalter.orderCode}:${oalter.alterComment} </li>
                        <li>状态: ${status}</li>
                        <p><strong>最新进度:</strong> <span>[${time}]</span>${alterComment}</p>
                    </ul>
                </div>
            </div>
            <div class="user-m fn-t10">
                <div class="mc">
                    <div class="ui-table">
                    	<table class="ui-table user-table">
                            <thead>
                                <tr>
                                    <th class="td-s7">处理时间</th>
                                    <th class="td-s9">进度信息</th>
                                    <th>操作人</th>
                                </tr>
                            </thead>
                            <tbody>
                            <s:iterator value="#request.listVo" var="orderAlterTraceInfo" >
                            	<tr>
                                	<td>
                                		<s:date name="#orderAlterTraceInfo.date" format="yyyy-MM-dd"/>&nbsp;
										<s:date name="#orderAlterTraceInfo.date" format="HH:mm:ss"/>
									</td>
                                    <td class="fn-text-left">
                                    ${orderAlterTraceInfo.info}
									</td>
                                    <td>
                                    ${orderAlterTraceInfo.operator }
                                    </td>
                                </tr>
                            </s:iterator>
                               
                            </tbody>
                        </table>
                        <div class="button"><div><a class="btn-submit" id="cancel"><span>返回</span></a></div></div>
                    </div>
                </div>
            </div>
          </div>
            <!--fn-right-->
            
        </div>