<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<s:set name="skuAttrList" value="product.productAttrList.{?#this.productAttrType==1 && #this.isSku==1}"></s:set> 
	<s:if test="#skuAttrList != null && #skuAttrList.size()>0">
		<s:form action="/app/productUpdate.action" method="POST" id="SkuAttrFrm" name='SkuAttrFrm' target="theID" >
			<input type="hidden" name="dataType" value="3"/>
			<s:hidden name="product.id" />
			<s:hidden name="product.productNo" />
			<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<s:iterator value="#skuAttrList" status="s">
				<tr class="isCheckBoxListTr"> 
					<th width="30%" align="right" class="eidt_rowTitle">
					<input type="hidden" class="input_style"  name="product.productAttrList[<s:property value='#s.index'/>].productAttrId" value="<s:property value='productAttrId'/>"/>
					<input type="hidden" class="input_style"  name="product.productAttrList[<s:property value='#s.index'/>].relateAttrId" value="<s:property value='relateAttrId'/>"/>
					<input type="hidden" class="input_style"  name="product.productAttrList[<s:property value='#s.index'/>].productAttrName" value="<s:property value='productAttrName'/>"/>
					<input type="hidden" class="input_style"  name="product.productAttrList[<s:property value='#s.index'/>].oldChecks" value="<s:property value='oldChecks'/>"/>
					<s:if test="isReq==1"><font color="red">*</font></s:if><s:property value='productAttrName'/>：
					</th>
					<td width="70%" align="left"> 
					<s:checkboxlist list="categoryAttrValueList" listKey="categoryAttrValueId" listValue="categoryAttrValue" name="product.productAttrList[%{#s.index}].productAttrValues" value="checkBoxIds"></s:checkboxlist>
					</td>
				</tr>
				</s:iterator>
			</table>
			
			<table id="skuDataTable" width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr id="firstSkuViewTr">
					<th align="center" width="70%">
						已生成SKU
					</th>
					<th align="center">
						操作
					</th>
				</tr>
				<input type="hidden" id="toDeleteSkuIds" name="toDeleteSkuIds" value="" />
				<s:iterator value="product.productSkus" var="sku" >
					<tr class='oldSkuDataTr'>
						<td align='center'>
							<s:set name="oldSkus" value="''" ></s:set>
							SKU编码：<s:property value="#sku.productSkuCode" />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<s:iterator value="#sku.productSkuAttrList" var="attr" >
								<s:set name="oldSku" value="categoryAttrId + ':' + categoryAttrName + ':' + categoryAttrValueId" ></s:set>
								<s:property value="categoryAttrName" />：<s:property value="categoryAttrValue" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<s:set name="oldSkus" value="#oldSkus +','+ #oldSku" ></s:set>
							</s:iterator>
						</td>
						<input type='hidden' name='oldskuCheckedId' value='<s:property value="#oldSkus" />' />
						<td align='center'>
							<input type='button' value='删除' onclick='removeSkuTr(this.parentNode.parentNode,<s:property value="productSkuId" />);' class='btnStyle' />
						</td>
					</tr>
				</s:iterator>
				
			</table>
			<br />
			<table>
				<tbody>
					<tr align="center">
						<td align="center">
							<input id="skuAttrButton"  class="btn-custom btnStyle_09" type="button" value="保存SKU规格" onClick="skuAttrSubmit();" >
							&nbsp;&nbsp;<input class="btnStyle" type="button" value="返回" onClick="gotoList();" >
						</td>
					</tr>
				</tbody>
			</table>
		</s:form>
	</s:if>
	
	<script type="text/javascript">
		$(function(){
			$("#ct_sku input[type='checkbox'][name$='productAttrValues']:checked").each(function(i){
				this.disabled = true;
			});
			
			//动态生成SKU预览 Begin
			$("#ct_sku input[type='checkbox'][name$='productAttrValues']").click(function(){
				
				$(".skuDataTr").remove();
				
				var array = new Array();
				var totalLength = 1;
				var index = 0;
				
				//循环每个属性
				$("#ct_sku .isCheckBoxListTr").each(function(i){
					var attrName = $(this).find("input[type='hidden'][name$='productAttrName']")[0].value;
					
					var attrId = $(this).find("input[type='hidden'][name$='relateAttrId']")[0].value;
					
					if($(this).find("input[type='checkbox'][name$='productAttrValues']:checked").length>0){
						var childArray = new Array();
						//循环该属性下被选中的值
						$(this).find("input[type='checkbox'][name$='productAttrValues']:checked").each(function(j){
							childArray[j] = attrName + "：" + $($(this).next("label")[0]).html()+"^"+attrId+":"+ attrName +":"+this.value;
						});
						totalLength = totalLength * childArray.length;
						array[index] = childArray;
						++index;
					}
				});
				
				if(array.length > 0){
					var newArray = cross(array,totalLength);
					//生成预览图
					for(var i=0;i < newArray.length;i++){
						var content = "";
						var valueId = "";
						for(var j=0;j < newArray[i].length;j++){
							var str = newArray[i][j].split("^");
							valueId += "," + str[1];
							content += str[0]+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						if($("#ct_sku #skuDataTable .oldSkuDataTr").find("input[type='hidden'][name='oldskuCheckedId'][value='"+valueId+"']").length==0){
							var _newRow = "<tr class='skuDataTr'>";
							_newRow += "<input type='hidden' name='skuCheckedId' value='"+valueId.substring(1)+"' />";
							_newRow += "<td align='center'>";
							_newRow += content;
							_newRow += "</td>";
							_newRow += "<td align='center'>";
							_newRow += "<input type='button' value='删除' onclick='removeSkuTr(this.parentNode.parentNode);' class='btnStyle' />";
							_newRow += "</td>";
							_newRow += "</tr>";
							
							$("#skuDataTable").append(_newRow);
						}
					}
				}
			});
			
			function cross(sourceArray,totalLength){
				// 笛卡尔积索引记录
				var record = new Array(sourceArray.length);
				for(var k=0;k<sourceArray.length;k++){
					record[k] = 0;
				}
				var results = new Array();
				// 产生笛卡尔积
				for (var i = 0; i < totalLength; i++) {
					var row = new Array();
					// 生成笛卡尔积的每组数据
					for (var index = 0; index < record.length; index++) {
						row.push(sourceArray[index][record[index]]);
					}
					results[i] = row;
					crossRecord(sourceArray, record, sourceArray.length - 1);
				}
				return results;
			}
			
			function crossRecord(sourceArray, record, level){
				record[level] = record[level] + 1;
				if (record[level] >= sourceArray[level].length && level > 0) {
					record[level] = 0;
					crossRecord(sourceArray, record, level - 1);
				}
			}
			//动态生成SKU预览 End
			
			
		});
	
	</script>
</body>
</html>