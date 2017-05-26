<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
    <script type='text/javascript' charset='utf-8' >alert('SKU规格修改成功！');</script>
</head>
<body>
	<s:set name="skuAttrList" value="product.productAttrDraftList.{?#this.productAttrType==1 && #this.isSku==1}"></s:set> 
		<s:form action="/app/productDraftUpdate.action" method="POST" id="SkuAttrFrm" name='SkuAttrFrm' target="theID" >
			<input type="hidden" name="dataType" value="3"/>
			<s:hidden name="product.productId" />
			<s:hidden name="product.productNo" />
			<s:hidden name="product.opType" id="opType" ></s:hidden>
			<s:if test="#skuAttrList != null && #skuAttrList.size()>0">
			<table width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<s:iterator value="#skuAttrList" status="s">
				<tr class="isCheckBoxListTr"> 
					<th width="30%" align="right" class="eidt_rowTitle">
					<input type="hidden" class="input_style"  name="product.productAttrDraftList[<s:property value='#s.index'/>].productAttrId" value="<s:property value='productAttrId'/>"/>
					<input type="hidden" class="input_style"  name="product.productAttrDraftList[<s:property value='#s.index'/>].relateAttrId" value="<s:property value='relateAttrId'/>"/>
					<input type="hidden" class="input_style"  name="product.productAttrDraftList[<s:property value='#s.index'/>].productAttrName" value="<s:property value='productAttrName'/>"/>
					<input type="hidden" class="input_style"  name="product.productAttrDraftList[<s:property value='#s.index'/>].oldChecks" value="<s:property value='oldChecks'/>"/>
					<input type="hidden" class="attrNameHide" value="<s:property value='productAttrName'/>" >
					<s:if test="isReq==1"><font color="red">*</font></s:if><s:property value='productAttrName'/>：
					</th>
					<td width="70%" align="left"> 
						<s:checkboxlist list="categoryAttrValueList" listKey="categoryAttrValueId" listValue="categoryAttrValue" name="product.productAttrDraftList[%{#s.index}].productAttrValues" value="checkBoxIds"></s:checkboxlist>
						
						<s:if test="#request.skuNewAttrList.containsKey(relateAttrId)">
							<s:checkboxlist cssClass="newAttr" list="#request.skuNewAttrList.get(relateAttrId)" listKey="newAttr" listValue="newAttr" name="newAttr_%{#s.index}"></s:checkboxlist>
						</s:if>
						<%--&nbsp;&nbsp;
						<a href="javascript:void(0);" onclick="addSkuValue(this.parentNode);">添加</a>--%>
					</td>
				</tr>
				</s:iterator>
			</table>
			</s:if>
			<input type="hidden" id="toDeleteSkuIds" name="toDeleteSkuIds" value="" />
			<table id="skuDataTable" width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr id="firstSkuViewTr">
					<th align="center">
						SKU编码
					</th>
					<s:iterator value="product.productSkuDrafts[0].attributeValues" var="sku" status="st" >
						<th align="center">
							<s:property value='#sku.attribute' escape="false" />
						</th>
					</s:iterator>
					<th align="center">
						操作
					</th>
					<th align="center">
						状态
					</th>
				</tr>
				<s:iterator value="product.productSkuDrafts" var="sku" status="st" >
					<s:set name="oldSkus" value="''" ></s:set>
					<tr class='oldSkuDataTr'>
						<td align="center">
							<s:property value="#sku.productSkuCode" /><input type="hidden" class="hideProductSkuId" value="<s:property value="#sku.productSkuId" />" >
						</td>
						<s:iterator value="#sku.attributeValues" var="attr" status="st" >
							<s:if test="categoryAttrValueId == null">
								<s:set name="oldSku" value="categoryAttrId + ':' + attribute + ':' + '@' + value" ></s:set>
							</s:if>
							<s:else>
								<s:set name="oldSku" value="categoryAttrId + ':' + attribute + ':' + categoryAttrValueId" ></s:set>
							</s:else>
							<td align="center">
								<s:property value="value" />
							</td>
							<s:set name="oldSkus" value="#oldSkus + #oldSku" ></s:set>
							<s:if test="#st.count != #sku.attributeValues.size()">
								<s:set name="oldSkus" value="#oldSkus +','" ></s:set>
							</s:if>
						</s:iterator>
						<input type='hidden' name='oldskuCheckedId' value='<s:property value="#oldSkus" />' />
						<td align='center'>
							<s:if test="#sku.productSkuCode == null">
								<input type='button' value='删除' onclick='removeSkuTr(this.parentNode.parentNode,<s:property value="productSkuId" />);' class='btnStyle' />
							</s:if>
							<s:else>
								&nbsp;
							</s:else>
							<input type="button" class="btnStyle" value="编辑图片" onClick="openSkuListDialog(<s:property value="#sku.productSkuId" />);" />
						</td>
						<td align="center">
							<input type="hidden" name="product.productSkuDrafts[<s:property value='#st.index'/>].productSkuId" value="<s:property value="#sku.productSkuId" />" >
							<s:radio list="#{'0':'无效','1':'有效'}" listKey="key" listValue="value" name="product.productSkuDrafts[%{#st.index}].status" value="#sku.status" ></s:radio>						
						</td>
					</tr>
				</s:iterator>
			</table>
			<%--
			<table id="skuDataTable" width="100%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr id="firstSkuViewTr">
					<th align="center" width="70%">
						已生成SKU
					</th>
					<th align="center">
						操作
					</th>
					<th align="center">
						状态
					</th>
				</tr>
				<input type="hidden" id="toDeleteSkuIds" name="toDeleteSkuIds" value="" />
				<s:iterator value="product.productSkuDrafts" var="sku" status="st" >
					<tr class='oldSkuDataTr'>
						<td align='center'>
							<s:set name="oldSkus" value="''" ></s:set>
							SKU编码：<s:property value="#sku.productSkuCode" /><input type="hidden" class="hideProductSkuId" value="<s:property value="#sku.productSkuId" />" >
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<s:iterator value="#sku.attributeValues" var="attr" status="st" >
								<s:set name="oldSku" value="categoryAttrId + ':' + attribute + ':' + categoryAttrValueId" ></s:set>
								<s:property value="attribute" />：<s:property value="value" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<s:set name="oldSkus" value="#oldSkus + #oldSku" ></s:set>
									<s:if test="#st.count != #sku.attributeValues.size()">
										<s:set name="oldSkus" value="#oldSkus +','" ></s:set>
									</s:if>
							</s:iterator>
							<input type='hidden' name='oldskuCheckedId' value='<s:property value="#oldSkus" />' />
						</td>
						<td align='center'>
							<s:if test="#sku.productSkuCode == null">
								<input type='button' value='删除' onclick='removeSkuTr(this.parentNode.parentNode,<s:property value="productSkuId" />);' class='btnStyle' />
							</s:if>
							<s:else>
								&nbsp;
							</s:else>
							<input type="button" class="btnStyle" value="编辑图片" onClick="openSkuListDialog(<s:property value="#sku.productSkuId" />);" />
						</td>
						<td align="center">
							<input type="hidden" name="product.productSkuDrafts[<s:property value='#st.index'/>].productSkuId" value="<s:property value="#sku.productSkuId" />" >
							<s:radio list="#{'0':'无效','1':'有效'}" listKey="key" listValue="value" name="product.productSkuDrafts[%{#st.index}].status" value="#sku.status" ></s:radio>						
						</td>
					</tr>
				</s:iterator>
				
			</table>
			--%>
			<br />
			<table>
				<tbody>
					<tr align="center">
						<td align="center">
							<input id="skuAttrButton"  class="btn-custom btnStyle_09" type="button" value="保存SKU规格" onClick="skuAttrSubmit();" >
							&nbsp;&nbsp;<input class="btn-custom btnStyle" type="button" value="返回" onClick="gotoList();" >
						</td>
					</tr>
				</tbody>
			</table>
		</s:form>
	
	<script type="text/javascript">
	
		//动态生成SKU预览 Begin
		function creatProductSku(obj){
			var flag = false;
			if(obj.checked){
				if($(obj).parent().find("input[type='checkbox'][name$='productAttrValues']:checked").length ==1 ){
					$(".oldSkuDataTr").each(function(i){
						$("#toDeleteSkuIds").val($("#toDeleteSkuIds").val()+$(this).find(".hideProductSkuId").val()+",");
					});
					$(".oldSkuDataTr").remove();
					$("#firstSkuViewTr").remove();
					flag = true;
				}
			}else{
				if($(obj).parent().find("input[type='checkbox'][name$='productAttrValues']:checked").length == 0 ){
					$("#firstSkuViewTr").remove();
					flag = true;
				}
			}
			$(".skuDataTr").remove();
			
			var array = new Array();
			var totalLength = 1;
			var index = 0;
			var _skuTitle = "";
			
			//循环每个属性
			$("#ct_sku .isCheckBoxListTr").each(function(i){
				var attrName = $(this).find("input[type='hidden'][name$='productAttrName']")[0].value;
				
				var attrId = $(this).find("input[type='hidden'][name$='relateAttrId']")[0].value;
				
				if($(this).find("input[type='checkbox'][name$='productAttrValues']:checked").length>0){
					var childArray = new Array();
					//循环该属性下被选中的值
					$(this).find("input[type='checkbox'][name$='productAttrValues']:checked").each(function(j){
//						childArray[j] = attrName + "：" + $($(this).next("label")[0]).html()+"^"+attrId+":"+ attrName +":"+this.value;
						childArray[j] = $($(this).next("label")[0]).html()+"^"+attrId+":"+attrName+":"+this.value;
					});
					
					_skuTitle += "<th align='center'>" + attrName + "</th>";
					totalLength = totalLength * childArray.length;
					array[index] = childArray;
					++index;
				}
			});
			
			if(array.length > 0){
				if(flag){
					var _firstSkuViewTr = "<tr id='firstSkuViewTr'>";
					_firstSkuViewTr += "<th align='center'>SKU编码</th>";
					_firstSkuViewTr += _skuTitle;
					_firstSkuViewTr += "<th align='center'>操作</th><th align='center'>状态</th>";
					_firstSkuViewTr += "</tr>";
					
					$("#skuDataTable").append(_firstSkuViewTr);
				}
				
				
				var newArray = cross(array,totalLength);
				var content = "";
				var valueId = "";
				//生成预览图
				for(var i=0;i < newArray.length;i++){
					content = "<td>&nbsp;</td>";
					valueId = "";
					for(var j=0;j < newArray[i].length;j++){
						var str = newArray[i][j].split("^");
						valueId += "," + str[1];
						content += "<td align='center'>"+str[0]+"</td>";
					}
					valueId = valueId.substring(1);
					if($("#ct_sku #skuDataTable .oldSkuDataTr").find("input[type='hidden'][name='oldskuCheckedId'][value='"+valueId+"']").length==0){
						var rowLength = $(".skuDataTr").length;
						var _newRow = "<tr class='skuDataTr'>";
						_newRow += "<input type='hidden' name='skuCheckAttrs["+rowLength+"].skuCheckedId' value='"+valueId+"' />";
						_newRow += content;
						_newRow += "<td align='center'>";
						_newRow += "<input type='button' value='删除' onclick='removeSkuTr(this.parentNode.parentNode);' class='btnStyle' />";
						_newRow += "</td>";
						_newRow += "<td align='center'>";
						_newRow += "<input id='productSku"+rowLength+"_status0' type='radio' value='0' name='skuCheckAttrs["+rowLength+"].status'>";
						_newRow += "<label for='productSku"+rowLength+"_status0'>无效</label>";
						_newRow += "<input id='productSku"+rowLength+"_status1' type='radio' checked='checked' value='1' name='skuCheckAttrs["+rowLength+"].status'>";
						_newRow += "<label for='productSku"+rowLength+"_status1'>有效</label>";
						_newRow += "</td>";
						_newRow += "</tr>";
						
						$("#skuDataTable").append(_newRow);
					}
				}
			}
		}
		
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
	
		$(function(){
			if($("#opType").val()!=1){
				$(".isCheckBoxListTr").each(function(i){
					if($(this).find("input[type='checkbox'][name$='productAttrValues']:checked").length>0){
						$(this).find("input[type='checkbox'][name$='productAttrValues']").attr("disabled",false);
					}else{
						$(this).find("input[type='checkbox'][name$='productAttrValues']").attr("disabled",true);
					}
				});
			}else{
				$(".isCheckBoxListTr").each(function(i){
					if($(this).find("input[type='checkbox'][name$='productAttrValues']:checked").length == 0){
						return false;
					}
				});
			}
			$("#ct_sku input[type='checkbox'][name$='productAttrValues']:checked").each(function(i){
				this.disabled = true;
			});
			
			
			$(".newAttr").each(function(i){
				var _name=$(this).parent().find("input:eq(0)").attr("name");
				var _lab = "lab" + i;
				$(this).attr({name:_name,checked:"checked",disabled:"disabled",id:_lab}).val("@"+$(this).val()).next().attr("for",_lab);
			});
			
			$("#ct_sku input[type='checkbox'][name$='productAttrValues']").click(function(){
				creatProductSku(this);
			});
			
		});
	
	</script>
</body>
</html>