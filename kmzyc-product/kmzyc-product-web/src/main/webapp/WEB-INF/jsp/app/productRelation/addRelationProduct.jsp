<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>列表选择SKU码</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<script src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/product/product.js"></script>
<script type="text/javascript" src="/etc/js/validate/easy_validator.pack.js"></script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css"/>
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css"/>
<link href="/etc/js/warehouse/distributionInfo.js"/>
<script type="text/javascript" src="/etc/js/common.js"></script>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<style type="text/css">
body {
	padding: 0px;
	margin: 0px;
}
table {
	margin-left: 10px;
}
</style>
</head>
<body>
	<s:form action="/productRelation/relationQueryProduct.action" method="post"
          namespace='/productRelation' id="frm" name='frm'>
		<input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>"/>
		<input type="hidden" name="productTied.productSkuId" value="<s:property value='productTied.productSkuId'/>"/>
        <input type="hidden"  name="relationType" value="<s:property value='productRelation.relationType'/>"/>
		<br/>
		<table width="90%" class="table_search" align="center" cellpadding="0"
               cellspacing="0" style="border-collapse: collapse; font-size: 12px">
			<tr>
				<td>状态：&nbsp;
                    <s:select list="#request.productRelationStatusMap" name="productTied.status" id="productStatus"
                              headerKey="" headerValue="--全部状态--"></s:select>
                </td>
			</tr>
			<tr>
				<td>类别：
                    <s:select list="#request.categoryList" id="categoryId1" listKey="categoryId" listValue="categoryName"
                              headerKey="0" headerValue="--一级类目--"
                              onchange="change1('categoryId1','categoryId2');"></s:select>
                    <select id="categoryId2" onchange="change1('categoryId2','categoryId3');">
						<option value="0">--二级类目--</option>
				    </select>
                    <select id="categoryId3" name="productTied.categoryId">
						<option value="0">--三级类目--</option>
				    </select>
                </td>
				<td>品牌：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <s:select list="#request.productBrandMap" id="brandId" headerKey="0" style="width:116px"
                              name="productTied.brandId" headerValue="--全部品牌--"></s:select>
                </td>
			</tr>
			<tr>
				<td width="432px" >产品编号：&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;
                    <s:textfield name="productTied.productNo" cssClass="input_style" id="productNo"/>
				</td>
				<td>名称：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <s:textfield type="text" name="productTied.procuctName" cssClass="input_style"/>
					<input type="button" onclick="doSearch()" class="btngray" value=" 查询 "/>
                </td>
			</tr>
			<tr/>
        </table>
    </s:form>
	<s:form action="/productRelation/saveProductRelation.action" method="post"
            namespace='/productRelation' id="relationfrm" name='relationfrm'>
        <table width="90%" class="table_search" align="center" cellpadding="0"
			cellspacing="0" style="border-collapse: collapse; font-size: 12px">	
			<tr>
				<input type="hidden" name="productRelation.mainSkuId" value="<s:property value='productTied.productSkuId'/>"/>
				<td width="432px"  >主产品价格：&nbsp;&nbsp;&nbsp;
                    <s:textfield name="productRelation.mainSkuPrice" id="mainSkuPrice" cssClass="input_style"
                                 tip="请输入1-1000000的整数" reg="^((?!0)[0-9]+\.?\d{0,2}|(0)\.{1}\d{1,2}|1000000)$"
                                 onblur="addScript();"  ></s:textfield>
		            产品原价格 <span id="oldMainPrice"><s:property value="mainSkuPrice"/></span>
		        </td>
				<td>关联名称：
                    <s:textfield name="productRelation.relationName" cssClass="input_style"
						 reg="^(\S{0,10})$" tip="备注不要超过10个汉字" id="productName"></s:textfield>
				</td>
			</tr>
			<tr>
				<td>关联类型 ：		
                    <s:iterator value="#request.productRelationType" >
                        <s:if test="key==0"  >
                         <input type="radio" name="productRelation.relationType"  checked="checked"
                                value="<s:property value="key"/>"/>
                        </s:if>
                        <s:else >
                            <input type="radio" name="productRelation.relationType" value="<s:property value="key"/>"/>
                        </s:else>
                         <s:property value="value"/>
                    </s:iterator>
                </td>
                <td>
                    <input type="button" class="btngreen" value="保存所选" onclick="selectList(this);"/>
                </td>
			</tr>
        </table>

		<!-- 数据列表区域 -->
		<table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
			<tr>
				<th align="center" width="5%"><input type='checkbox' id='allbox' name='allbox' onclick='checkAll(this)'/></th>
				<th align="center" width="15%">产品编号</th>
				<th align="center" width="15%">产品SKU编号</th>
				<th align="center" width="15%">产品名称</th>
				<th align="center" width="8%">品牌</th>
				<th align="center" width="8%">关键字</th>
				<th align="center" width="8%">状态</th>
				<th align="center" width="8%">销售单价</th>
				<th id="relationProductSkuPrice" width="10%">关联商品定价</th>
			</tr>
			<s:iterator id="productiterator" value="page.dataList" status="stuts">
				<tr>
					<td align="center">
                        <input type="checkbox" onclick="checkBoxClick(this)" name="checkSkuId"
                               value="<s:property value='productSkuId'/>"/>
                    </td>
					<input type="hidden" name="productRelationSku"/>
					<input type="hidden" name="productRelationPrice"/>
					<input type="hidden" name="productRelationType"/>
					<td align="center" ><s:property value="productNo"/></td>
					<td align="center"  style="word-break: break-all"><s:property value="productSkuCode"/></td>
					<td align="center"  style="word-break: break-all"><s:property value='procuctName'/></td>
					<td align="center"  style="word-break: break-all"><s:property value='brandName'/></td>
					<td align="center"  style="word-break: break-all"><s:property value="keyword"/></td>
					<td align="center"  style="word-break: break-all">
					<s:iterator value="#request.productRelationStatusMap"  >
					    <s:if test="status==key"  >
					        <s:property value="value"/>
					    </s:if>
					</s:iterator>
					<td align="center"  style="word-break: break-all">
						<s:property value="price"/>
						<input type="hidden"  name="productOldPriceRe" value="<s:property value='price'/>"/>
                    </td>
					<td align="center"  style="word-break: break-all" >
						<input type="text" name="relationProductPrice" size="5"/>
                    </td>
				</tr>
			</s:iterator>
		</table>
		<br/>
        <table width="95%" align="center" cellpadding="0" cellspacing="0">
			<tr>
	            <table width="100%" border="0"  cellspacing="0" cellpadding="5" style="font-size:12px;">
                    <tr valign="bottom" border="0">
                        <td align="left">
                            共<font color='#008000'> <s:property value='page.recordCount'/> </font>条记录，
                            第<font color='#008000'> <s:property value='page.pageNo'/> </font>页，
                            共<font color='#008000'> <s:property value='page.pageCount'/> </font>页
                        </td>
                        <td align="right">
                            跳转到
                            <select id="pageNo" name="page.pageNo" onchange="goPage()">
                            </select>
                            页
                            &nbsp;&nbsp;&nbsp;
                            <script language="JavaScript">
                                  var pageCount = parseInt(<s:property value='page.pageCount'/>);
                                  for ( var i=0;i<pageCount;i++){
                                      var   oOption   =   document.createElement("OPTION");
                                      oOption.value=i+1;
                                      oOption.text=i+1;
                                      document.getElementById('pageNo').options.add(oOption);
                                  }
                                  document.getElementById('pageNo').value=<s:property value='page.pageNo'/>;
                            </script>
                        </td>
                        <td align="right" style="width: 200px; padding-top:4px">
                            <s:if test="(page.pageCount==0)||(page.pageCount==1)">
                                <img src="/etc/images/ICON_FIRST.gif" alt="首页" border="0">
                                <img src="/etc/images/ICON_PRIOR.gif" alt="上页" border="0">
                                <img src="/etc/images/ICON_NEXT.gif" alt="下页" border="0">
                                <img src="/etc/images/ICON_LAST.gif" alt="末页" border="0">
                            </s:if>
                            <s:else>
                                <s:if test="page.pageNo==1">
                                    <img src="/etc/images/ICON_FIRST.gif" alt="首页" border="0">
                                    <img src="/etc/images/ICON_PRIOR.gif" alt="上页" border="0">
                                    <a href="javascript:;" onClick="javascript:nextPage();"><img src="/etc/images/ICON_NEXT.gif" alt="下页" border="0"></a>
                                    <a href="javascript:;" onClick="javascript:lastPage();"><img src="/etc/images/ICON_LAST.gif" alt="末页" border="0"></a>
                                </s:if>
                                <s:elseif test="page.pageNo==page.pageCount">
                                    <a href="javascript:;" onClick="javascript:firstPage();"><img src="/etc/images/ICON_FIRST.gif" alt="首页" border="0"></a>
                                    <a href="javascript:;" onClick="javascript:priorPage();"><img src="/etc/images/ICON_PRIOR.gif" alt="上页" border="0"></a>
                                    <img src="/etc/images/ICON_NEXT.gif" alt="下页" border="0">
                                    <img src="/etc/images/ICON_LAST.gif" alt="末页" border="0">
                                </s:elseif>
                                <s:else>
                                    <a href="javascript:;" onClick="javascript:firstPage();"><img src="/etc/images/ICON_FIRST.gif" alt="首页" border="0"></a>
                                    <a href="javascript:;" onClick="javascript:priorPage();"><img src="/etc/images/ICON_PRIOR.gif" alt="上页" border="0"></a>
                                    <a href="javascript:;" onClick="javascript:nextPage();"><img src="/etc/images/ICON_NEXT.gif" alt="下页" border="0"></a>
                                    <a href="javascript:;" onClick="javascript:lastPage();"><img src="/etc/images/ICON_LAST.gif" alt="末页" border="0"></a>
                                </s:else>
                            </s:else>
                        </td>
                    </tr>
                </table>
            </tr>
		</table>
	</s:form>
	<s:if test='!rtnMessage.isEmpty()'>
		<script language="JavaScript">
			var msg = document.getElementById("rtnMsg").value;
            if(msg=="success"){
                alert("保存成功");
            } else{
                alert("保存失败");
            }
			if (msg) {
				parent.closeOpenSku();
			}
		</script>
	</s:if>
	<script type="text/javascript">
		function doSearch() {
			document.frm.submit();
		}

		function selectList(theButton) {
             var  chekcLength=$("input[name='checkSkuId']:checked").length;
             if(chekcLength==0){
                 alert("请选择关联的产品");
                 return false;
             }
            var boxlist=$("input[name='checkSkuId']:checked");
			var count = 0;
			boxlist.each(function(i) {
				var productRelationSkuId = $(this).val();
				$(this).parent().parent().find("input[name='productRelationSku']")
                        .attr("name","list[" + i + "]." + "relationSkuId")
                        .attr("value",productRelationSkuId);
				var productRelationPrice = $(this).parent().parent().find("input[name='relationProductPrice']").val();
				if (productRelationPrice) {
					count = count + 1;
				}
				$(this).parent().parent().find("input[name='productRelationPrice']")
                        .attr("name","list[" + i + "]." + "relationSkuPrice")
                        .attr("value",productRelationPrice);
				$(this).parent().parent().find("input[name='productRelationType']")
                        .attr("name","list[" + i + "]." + "relationDetailType").attr("value", 0);
			});

			if (count == 0) {
				alert("请为关联的产品输入价格");
				return false;
			}
            var  productRelationRelationName=$("input[name='productRelation.relationName']").val();
            var mainPriceInput=$("input[name='productRelation.mainSkuPrice']").val();
            if(mainPriceInput.length==0){
                alert("请输入主产品价格");
                return false;
            }
            if(productRelationRelationName.length==0){
            	alert("请输入关联名称");
				return false;
            }
            $("#relationfrm").submit();
		}

        function addScript(){
            //重新加载js,验证出现错误提示用的
            var oHead = document.getElementsByTagName('HEAD').item(0);
            var oScript= document.createElement("script");
	        oScript.type = "text/javascript";
	        oScript.src="/etc/js/validate/easy_validator.pack.js";
	        oHead.appendChild(oScript);
        }
	
        function checkBoxClick(self){
            if ($(self).attr("checked") == true) {
                $(self).parent().parent().find("input[name='relationProductPrice']")
                        .replaceWith("<input type='text' name='relationProductPrice' size='5' reg='^((?!0)[0-9]+\\.?\\d{0,2}|(0)\\.{1}\\d{1,2}|1000000)$' tip='范围[1-1000000]'/>");
                  //重新加载js,验证出现错误提示用的
	        	var oHead = document.getElementsByTagName('HEAD').item(0);
	            var oScript= document.createElement("script");
	            oScript.type = "text/javascript";
	            oScript.src="/etc/js/validate/easy_validator.pack.js";
	            oHead.appendChild(oScript);
            } else {
                $(self).parent().parent().find("input[name='relationProductPrice']")
                        .replaceWith("<input type='text' name='relationProductPrice' size='5'/>");
				 //	重新加载js,验证出现错误提示用的
	            var oHead = document.getElementsByTagName('HEAD').item(0);
	            var oScript= document.createElement("script");
	            oScript.type = "text/javascript";
	            oScript.src="/etc/js/validate/easy_validator.pack.js";
                oHead.appendChild(oScript);
            }
        }

    $(document).ready(function(){
        $("input[type='radio']").click(function(){
            if($(this).val()!=0){
                var p=$("#oldMainPrice")[0].innerHTML;
                $("#mainSkuPrice").replaceWith("<input type='text' readonly='readonly' name='productRelation.mainSkuPrice' value="+p+"/>");
                $("input[name='checkSkuId']").each(function(i){
                    var checkBoxValue=$(this).val();
                    var productRelationPrice = $(this).parent().parent().find("input[name='productOldPriceRe']").val();
                    $(this).parent().parent().find("input[name='relationProductPrice']").val(productRelationPrice);
                    var priceI=$(this).parent().parent().find("input[name='relationProductPrice']");
                    priceI.hide();
                    priceI.parent().hide();
                    //注意上面这一行必须放在下面这一行 上面 ，不然取不到值
                    $(this).replaceWith(" <input type='checkbox' name='checkSkuId' value="+checkBoxValue+"/>");
                });
                $("#relationProductSkuPrice").hide();
            } else {
                $("input[name='productRelation.mainSkuPrice']")
                        .replaceWith("<input type='text' id='mainSkuPrice' tip='请输入1-1000000的整数' reg='^((?!0)[0-9]+\\.?\\d{0,2}|(0)\\.{1}\\d{1,2}|1000000)$' onblur='addScript();' name='productRelation.mainSkuPrice'/>");
                //重新加载js,验证出现错误提示用的
                var oHead = document.getElementsByTagName('HEAD').item(0);
                var oScript= document.createElement("script");
                oScript.type = "text/javascript";
                oScript.src="/etc/js/validate/easy_validator.pack.js";
                var oScript1= document.createElement("script");
                oHead.appendChild(oScript);
                $("input[name='checkSkuId']").each(function(i){
                    var priceI=$(this).parent().parent().find("input[name='relationProductPrice']");
                    priceI.parent().show();// 注意这一行与下面一行的次序不能调换
                    priceI.replaceWith("<input type='text' name='relationProductPrice' size='5'/>");
                    //注意上面这一行必须放在下面这一行 上面 ，不然取不到值
                    var cKValue=$(this).val();
                    $(this).replaceWith("<input type='checkbox' name='checkSkuId' onclick='checkBoxClick(this)'	value="+cKValue+"/>");
                });
                $("#relationProductSkuPrice").show();
            }
        });
    });
    function firstPage() {
        document.getElementById('pageNo').value=1;
        goPage();
    }
    function lastPage() {
        document.getElementById('pageNo').value=<s:property value='page.pageCount'/>;
        goPage();
    }
    function priorPage() {
        document.getElementById('pageNo').value=parseInt(document.getElementById('pageNo').value)-1;
        goPage();
    }
    function nextPage() {
        document.getElementById('pageNo').value=parseInt(document.getElementById('pageNo').value)+1;
        goPage();
    }
    function goPage() {
        var s = document.getElementById('pageNo').value;
        var typeValue=$("input[name='productRelation.relationType']:checked ").val();
        if(typeValue==0){
            document.forms[0].action="/productRelation/relationQueryProduct.action?page.pageNo="+s;
            document.forms[0].submit();
        } else{
            var typeValueNum=Number(typeValue);
            document.forms[0].action="/productRelation/relationQueryProduct.action?page.pageNo="+s+"&productRelation.relationType="+typeValueNum;
            document.forms[0].submit();
        }
    }

    /**
    *以下函数用于记录的选择
    */
    function checkCkSelected(oForm){
        for (var i=0;i<oForm.all.tags("input").length;i++){
            var ele = oForm.all.tags("input")[i];
            var ct = ele.getAttribute("type");
            if ((ele.type=="checkbox")&&(ele.checked==true))
                return true;
        }
        return false;
    }

    function checkAll(ck) {
        var inputs = ck.form.getElementsByTagName("input");
        for (var i=0;i<inputs.length;i++){
            var ele = inputs[i];
            /*var ct = ele.getAttribute("type");*/
            if ((ele.type=="checkbox")){
                if(ck.checked!=ele.checked)
                    ele.click();
            }
        }
    }

    function checkSelected(sName){
       //window.event.returnValue = false;
        var chs = document.getElementsByName(sName);
        for(var i=0;i<chs.length;i++){
           var ele = chs[i];
              if(ele.type=="checkbox" && ele.checked==true)
                return true;
       }
       return false;
    }

    function deleteSelected(sName) {
        if (!checkSelected(sName)) {
            alert("请选择要删除的数据！");
            return false;
        }
        if (confirm("你确定要删除选中的数据？")) {
            doDelete(sName);
        }
    }

    function outlineMyRow(ckr){
        var otr = ckr.parentElement.parentElement;
        if(otr.tagName.toUpperCase()=="TR"){
            if(ckr.checked==true){
                ckr.ocls = otr.className;
                otr.className="select";
            }else{
                otr.className=ckr.ocls;
            }
        }
    }

    $(document).ready(function(){
        var typeValue=$("input[name='relationType']").val();
        if(typeValue!=0){
            $("input[name='productRelation.relationType'][value="+typeValue+"]").click();
        }
    });
</script>
</body>
</html>