<%@ page contentType="text/html; charset=utf-8" language="java"import="java.sql.*" errorPage=""%><%@ taglib prefix="s" uri="/struts-tags" %>
<SCRIPT LANGUAGE="JavaScript">
	var fileArray = new Array();
	// 0 id , 1 相对地址  ,2 类型 , 3 大小 , 4 名称 ， 转变后名称
	<s:iterator id="gditerator1" value="dataList">
	var fileDetail = new Array(<s:property value="fileId"/>,"<s:property value='fileUrl'/>","<s:property value='fileType'/>","<s:property value='fileSize'/>","<s:property value='fileName'/>","<s:property value='fileRemark'/>");
	fileArray.push(fileDetail);
	</s:iterator>
	parent.callbackUploadFile(fileArray,'<s:property value="rootPath" />','<s:property value="relativePath" />','<s:property value="isDate" />','<s:property value="fileFlag" />','<s:property value="isMultiple" />','<s:property value="specPath" />');
</script>

