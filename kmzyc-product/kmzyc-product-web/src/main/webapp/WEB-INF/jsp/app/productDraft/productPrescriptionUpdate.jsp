<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>药方属性</title>
</head>
<body>
<s:if test="#request.isPrescription">
	<s:form action="/app/productDraftUpdate.action" method="POST" id="prescriptionFrm" name='prescriptionFrm' target="theID" enctype="multipart/form-data" >
				 <input type="hidden" name="dataType" value="9"/>
				<s:hidden name="product.productId" />
				<s:hidden name="product.opType"/>
				<s:hidden name="product.categoryId"/>
				
				<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
					<s:iterator value="#request.prescriptionAttrList" status="st">
					<tr> 
						<th width="30%" align="right" class="eidt_rowTitle">
						<font color="red">*</font>&nbsp;<s:property value="productAttrName"/>:						
						<s:if test="inputType!=5 && childrenAttrType!=43">								
								<input type="hidden" name="draftAttrListForPresciption[<s:property value='#st.index'/>].productAttrName" value="<s:property value="productAttrName"/>" />
								<input type="hidden" name="draftAttrListForPresciption[<s:property value='#st.index'/>].sortno" value="<s:property value="sortno"/>"/>
								<input type="hidden" name="draftAttrListForPresciption[<s:property value='#st.index'/>].inputType" value="<s:property value="inputType"/>"/>
								<input type="hidden" name="draftAttrListForPresciption[<s:property value='#st.index'/>].childrenAttrType" value="<s:property value="childrenAttrType"/>"/>
								<input type="hidden" name="draftAttrListForPresciption[<s:property value='#st.index'/>].productAttrType" value="<s:property value="productAttrType"/>"/>
						</s:if>			
						</th>
						<td width="70%" align="left"> 							
							<s:if test="inputType==0">
								<input size="60" class="input_style" id="text_<s:property value='#st.index'/>" name="draftAttrListForPresciption[<s:property value='#st.index'/>].productAttrValue"  value="<s:property value='productAttrValue'/>" />  
							</s:if>							
							<s:elseif test="inputType==5 && childrenAttrType==43">							
								<font color="red">*</font><input id="uploadImage" name="creatorImageFile" type="file"/> &nbsp;&nbsp;&nbsp;&nbsp;
								<s:if test="productAttrValue!=null">
								<input type="hidden" id="alredExist" value="true"/>
									<s:set name="fileName" value="productAttrValue.substring(productAttrValue.lastIndexOf('creatorImage')+13, productAttrValue.lastIndexOf('_'))+ productAttrValue.substring(productAttrValue.lastIndexOf('.'))"></s:set>
									已有创建人图片:&nbsp;&nbsp;<a target="_blank" href="<s:property value='#request.creatorImageViewPath' /><s:property value='productAttrValue' />"><s:property value='#fileName' /></a>
								</s:if>	
								<s:else>
								<input type="hidden" id="alredExist" value="false"/>
								</s:else>														
							</s:elseif>
							<s:elseif test="inputType==4">
								<textarea rows="12" cols="100" id="required_<s:property value='#st.index'/>"  name="draftAttrListForPresciption[<s:property value='#st.index'/>].productAttrValue"><s:property value='productAttrValue'/></textarea>
							</s:elseif>
						</td>
					</tr>
					</s:iterator>
				</table>
				<br />
				<table>
					<tbody>
						<tr align="center">
							<td align="center"><input id="prescriptionButton"  class="btnStyle_09" type="button" onClick="prescriptionAttrSubmit();"
								value="保存药方属性">
								&nbsp;&nbsp;<input class="btnStyle" type="button" value="返回" onClick="gotoListForView();" >
							</td>
						</tr>
					</tbody>
				</table>
			</s:form>
			</s:if><s:else>&nbsp;&nbsp;<input class="btnStyle" type="button" value="返回" onClick="gotoListForView();" ></s:else>			
</body>
</html>