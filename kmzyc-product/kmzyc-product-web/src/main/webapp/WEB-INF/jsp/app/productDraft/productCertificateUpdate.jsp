<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<script type='text/javascript' charset='utf-8' >alert('资质文件修改成功！');</script>
</head>
<body>
	<s:form action="/app/productDraftUpdate.action" method="POST" id="certificateFrm" name='certificateFrm' target="theID" enctype="multipart/form-data" >
		<input type="hidden" name="dataType" value="8"/>
		<s:hidden name="product.productId" id="certificateProductId" />
		<s:hidden name="product.productType" ></s:hidden>
		<s:hidden name="product.opType"></s:hidden>
		<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse" id="AttrValueTR">
			<s:iterator value="product.cerfificateList" >
				<tr>
					<th align="right" class="eidt_rowTitle" width="30%"><s:property value="#request.certificateTypeMAP[fileType]"/>：</th>
					<td>
						<input type="file" name="certificateFiles" data-fileType="<s:property value="fileType"/>" data-pscId="<s:property value="pscId"/>" />
						<s:if test="filePath != null">
							&nbsp;&nbsp;
							已有资质文件：
							<a target="_blank" href="<s:property value='certificateViewPath' /><s:property value='filePath' />"><s:property value='fileName' /></a>
							<input type="hidden" class="exsitFileName" value="<s:property value='fileName' />" />
						</s:if>
					</td>
				</tr>
			</s:iterator>
		</table>
		<br />
		<table>
			<tbody>
				<tr align="center">
					<td align="center">
						<input id="certificateInfoButton"  class="btnStyle_09" type="button" onClick="certificateInfoSubmit();"
							   value="保存资质文件">&nbsp;&nbsp;
						<input class="btnStyle" type="button" value="返回" onClick="gotoListForView();" >
					</td>
				</tr>
			</tbody>
		</table>
	</s:form>

</body>
</html>