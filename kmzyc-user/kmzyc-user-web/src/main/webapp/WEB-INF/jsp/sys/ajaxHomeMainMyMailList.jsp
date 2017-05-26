<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<marquee behavior="scroll" direction="up" height="100" width="178" align="left" scrollamount="1" scrolldelay="50" onmouseover="this.stop()" onmouseout="this.start()">
	<s:iterator id="dataObj" value="list">
	    <li><a href="/sys/gotoMailView.action?mailId=<s:property value='mailId'/>&fromView=myMail&isRead=<s:property value='isRead'/>">
		    <s:if test="%{null!=mailTitle && mailTitle.length()>12}"><s:property value="%{mailTitle.substring(0, 12)}"/>...</s:if>
			<s:else><s:property value="%{mailTitle}" default="[无标题]" /></s:else>
			</a>
	</s:iterator>
</marquee>