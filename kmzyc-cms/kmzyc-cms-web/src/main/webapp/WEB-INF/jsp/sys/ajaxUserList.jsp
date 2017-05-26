<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<select id="unchecked" multiple="multiple" style="height: 250px; width: 150px;" size="15" onclick="check(this)" ondblclick="select(this);"> 
<s:iterator id="dataObj" value="dataList">
<OPTION VALUE='<s:property value="userId"/>' class='<s:property value="userId"/>'><s:property value="userName"/>(<s:property value="userReal"/>)</OPTION>
</s:iterator>
</select>