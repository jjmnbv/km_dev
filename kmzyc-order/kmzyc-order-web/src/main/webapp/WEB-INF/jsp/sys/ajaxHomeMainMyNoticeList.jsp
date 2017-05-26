<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<marquee behavior="scroll" direction="up" height="170" width="178" align="left" scrollamount="1" scrolldelay="50" onmouseover="this.stop()" onmouseout="this.start()">
	<s:iterator id="dataObj" value="dataList">

	    <li><a href="/sys/gotoNoticeDetail.action?noticeId=<s:property value='noticeId'/>">
		    <s:if test="%{null!=noticeTitle && noticeTitle.length()>12}"><s:property value="%{noticeTitle.substring(0, 12)}"/>...</s:if>
			<s:else><s:property value="%{noticeTitle}" default="[无标题]" /></s:else>
			</a>
	</s:iterator>
</marquee>