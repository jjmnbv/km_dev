<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查看供应商</title>
    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
    <script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
    <Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
    <script src="/etc/js/jquery-latest.pack.js"></script>
    <script type="text/javascript" src="/etc/js/jquery.form.js"></script>
    <Script src="/etc/js/97dater/WdatePicker.js"></Script>
    <script type="text/javascript" src="/etc/js/sections/sections.js"></script>
    <script type="text/javascript" src="/etc/js/validate/easy_validator.pack.js"></script>
    <script type='text/javascript' src='/etc/js/dialog-common.js'></script>
    <script type='text/javascript' src='/etc/js/artDialog4.1.7/artDialog.js?skin=default'></script>
    <script type='text/javascript' src='/etc/js/artDialog4.1.7/plugins/iframeTools.source.js'></script>
    <script type='text/javascript' src='/etc/js/jquery.blockUI.js'></script>
    <script type="text/javascript" src="/etc/js/supplier/supplierAuditView.js"></script>
</head>
<body onkeydown="changeKey();">

<!-- 导航栏 -->
<s:set name="parent_name" value="'供应商审核管理 '" scope="request" />
<s:set name="name" value="'产品供应商'" scope="request" />
<s:set name="son_name" value="'供应商详情'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<!-- 数据编辑区域 -->
<table width="95%" class="edit_table" align="center" cellpadding="3"
       cellspacing="0" border="1" bordercolor="#C7D3E2"
       style="border-collapse: collapse; font-size: 12px;">
    <!-- error message -->
    <input type="hidden" value="${suppliersInfo.supplierId}" id="supplierId"></input>
    <input type="hidden" value="${merchantOrSupplier.loginId}" id="UserLoginId"></input>
    <s:if test="rtnMessage != null">
        <tr>
            <td colspan="2" align="center">&quot;<font color="red"><s:property
                    value='rtnMessage' /> </font>
            </td>
        </tr>
    </s:if>
    <tr>
        <th colspan="2" align="left" class="edit_title">基本信息</th>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle" style="width:200px"><font color="red">*</font>供应商类型</th>
        <td>
            <s:select list="#{2:'入驻',3:'代销'}" name="supplierTypes" headerKey=""  id="suppliersTypeId" headerValue="--请选择--"></s:select>
            <%--   <s:select list="#request.SuppliersTypeMap" id="suppliersTypeId" name="supplierTypes" headerKey="" headerValue="--请选择类型--"></s:select>--%>
        </td>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle">公司名称：</th>
        <td><s:property value="merchantOrSupplier.corporateName" />
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle">地址：</th>
        <td>
            <s:property value="merchantOrSupplier.province" />
            <s:property value="merchantOrSupplier.city" />
            <s:property value="merchantOrSupplier.area" />
            <s:property value="merchantOrSupplier.corporateLocation" />
        </td>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle">组织机构代码：</th>
        <td><s:property value="merchantOrSupplier.organizationCode"  /></td>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle">组织机构代码电子版：</th>
        <td><s:if test="merchantOrSupplier.organizationUrl != null"><a href="<s:property value='imagePath'/><s:property value='merchantOrSupplier.organizationUrl'/>" target="_blank"><img src="<s:property value='imagePath'/><s:property value='merchantOrSupplier.organizationUrl'/>" style="width: 200px;height: 80px;"></img></a> </s:if>
        </td>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle">营业执照注册号：</th>
        <td><s:property value="merchantOrSupplier.businessLicenceRegister"  /></td>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle">营业执照电子版：</th>
        <td><s:if test="merchantOrSupplier.uploadBusinessLicencePictur != null"><a href="<s:property value='imagePath'/><s:property value='merchantOrSupplier.uploadBusinessLicencePictur'/>" target="_blank"><img src="<s:property value='imagePath'/><s:property value='merchantOrSupplier.uploadBusinessLicencePictur'/>" style="width: 200px;height: 80px;"></img></a> </s:if>
        </td>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle">营业执照有效期：</th>
        <td><s:date name='%{merchantOrSupplier.blinceStartdate}' format='yyyy-MM-dd'/>到<s:date name='%{merchantOrSupplier.blinceEnddate}' format='yyyy-MM-dd'/>
        </td>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle">法定经营范围：</th>
        <td><s:property value="merchantOrSupplier.businessScope" />
        </td>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle">税务登记证号：</th>
        <td><s:property value="merchantOrSupplier.taxRegistrationCno" />
        </td>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle">税务登记证电子版：</th>
        <td><s:if test="merchantOrSupplier.taxRegCertificateCopy != null"><a href="<s:property value='imagePath'/><s:property value='merchantOrSupplier.taxRegCertificateCopy'/>" target="_blank"><img src="<s:property value='imagePath'/><s:property value='merchantOrSupplier.taxRegCertificateCopy'/>" style="width: 200px;height: 80px;"></img> </a></s:if>
        </td>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle">纳税人识别号：</th>
        <td><s:property value="merchantOrSupplier.taxpayerIdnumber" />
        </td>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle">注册资金：</th>
        <td>
            <s:property value="merchantOrSupplier.registerBankroll" />万
        </td>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle">开户银行：</th>
        <td><s:property value="merchantOrSupplier.bankOfDeposit" />
        </td>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle">账户名：</th>
        <td><s:property value="merchantOrSupplier.bankAccountName" />
        </td>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle">公司联系人：</th>
        <td><s:property value="merchantOrSupplier.contactsName" />
        </td>
    </tr>

    <tr>
        <th align="right" class="eidt_rowTitle">公司联系人手机：</th>
        <td><s:property value="merchantOrSupplier.mobile" />
        </td>
    </tr>
    <%-- <tr>
				<th align="right" class="eidt_rowTitle">公司联系人电话：</th>
				<td><s:property value="merchantOrSupplier.phone" />
				</td>
			</tr> --%>
    <tr>
        <th align="right" class="eidt_rowTitle">公司固定电话：</th>
        <td><s:property value="merchantOrSupplier.fixedPhone" />
        </td>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle">联系人电子邮箱：</th>
        <td><s:property value="merchantOrSupplier.contactsEmail" />
        </td>
    </tr>
    <tr>
        <%-- <th align="right" class="eidt_rowTitle"><font color="red">*</font>
					分配佣金比例：</th>
				<td style="width:900px;height:80px">
			    <s:iterator value="#request.supplierCategorysMap" status="status" id="infoList">
                    ${value} <input type="text"  style="width: 50px;" id="inputs${key}" name="inputs" title="请输入佣金比例"></input></li>
                     <input type="hidden" id="grId${key}" value="${key}" name="supplierCategorysIds" class="ui-form-checkbox"/>
                    	</s:iterator>
				</td> --%>
        <th align="right" class="eidt_rowTitle" ><font color="red">*</font>
            分配经营类目(比例为百分比)：</th>
        <td class="Supplierslist">
            <input type="hidden" name="categoryValueList" id="categoryValue" value="">
            <input type="hidden" value="" name="CommissionRatios" id="CommissionRatios">
            <input type="hidden" value="" name="sacIds" id="sacIds">

            <s:iterator value="categorysList" id="hisList1">
                <s:if test="#hisList1.categoryParentId == 0">
                    <input type="hidden" style="width: 50px;" value="${sacId}" id="hid${categoryId}"></input>
                    <input type="hidden" value="${categoryId}"  class="hidSacId"></input>
                    <s:if test="#hisList1.sacId == null">
                        <input type="checkbox"  id="box${categoryId}" value="${categoryId}" onClick="checkedInput('${categoryId}')" name="categorys" class="ui-form-checkbox class${categoryParentId}"/>
                    </s:if>
                    <s:else>
                        <input type="checkbox"  id="box${categoryId}" value="${categoryId}" checked="checked" onClick="checkedInput('${categoryId}')" name="categorys" class="ui-form-checkbox class${categoryParentId}"/>
                    </s:else>
                    ${categoryName}
                    <li style="list-style-type:none;">
                        <s:iterator value="categorysList" id="hisList2">
                            <s:if test="#hisList2.categoryParentId != 0">
                                <s:if test="#hisList1.categoryId == #hisList2.categoryParentId">
                                    <input type="hidden" style="width: 50px;" value="${sacId}" id="hid${categoryId}"></input>
                                    <input type="hidden" value="${categoryId}"  class="hidSacId"></input>
                                    <s:if test="#hisList2.sacId == null">
                                        <input type="checkbox"  id="box${categoryId}" value="${categoryId}" onClick="checkedInput('${categoryId}')" name="categorys" class="ui-form-checkbox class${categoryParentId}"/>
                                        ${categoryName}
                                        <input type="text" name="inputRat${categoryParentId}" class="inputRat" style="width: 50px;" id="inputs${categoryId}" disabled="true" maxlength="5" value='${commissionRatio}' title="请输入佣金比例"></input>%
                                    </s:if>
                                    <s:else>
                                        <input type="checkbox" id="box${categoryId}" value="${categoryId}" checked="checked" onClick="checkedInput('${categoryId}')" name="categorys" class="ui-form-checkbox class${categoryParentId}"/>
                                        ${categoryName}
                                        <input type="text" name="inputRat${categoryParentId}" class="inputRat" style="width: 50px;" id="inputs${categoryId}" maxlength="5" value='${commissionRatio}' title="请输入佣金比例"></input>%
                                    </s:else>
                                </s:if>
                            </s:if>
                        </s:iterator>
                    </li>
                    </br>
                </s:if>
            </s:iterator>

            <%--
			   <s:iterator value="#request.supplierCategorysMapAll" status="status" id="infoList1">
                    	<s:set name="flag" value="0"  >  </s:set> 
                    			<s:iterator value="categorysList" id="hisList1">
                    				<s:if test="#infoList1.key == #hisList1.categoryId">
                    				 <s:set name="flag"  value="1" > </s:set>
                    				 <input type="hidden" style="width: 50px;" value="${sacId}" id="hid${key}"></input>
                    				 <input type="hidden" value="${categoryId}"  class="hidSacId"></input>
                    			  </s:if>
                    			 </s:iterator>
                    			 <s:if test="#flag==0">
                    			 	<li style="float: left;list-style-type:none;width: 200px;">
                    			 	<input type="hidden" id="box${key}" value="${key}" name="categorys" onclick="checkedInput('${key}')" class="ui-form-checkbox"/>
                    			 	 <input type="hidden" name="inputRat" disabled="true" class="inputRat" style="width: 50px;" id="inputs${key}" maxlength="5" title="请输入佣金比例"></input></li>
                    			 	 <input type="hidden" value=""  id="hid${key}"></input>
                    			  </s:if>
                    			 <s:elseif test="#flag==1"  >
                    			 <s:iterator value="categorysList" id="hisList1">
                    				<s:if test="#infoList1.key == #hisList1.categoryId">
                    				 <s:set name="flag"  value="1" > </s:set>
                    				<li style="float: left;list-style-type:none;width: 200px;">
	                    				<s:if test="categoryParentId == 0">
                    						${categoryName}
	                    				</s:if>
	                    				<s:else>
	                    					<input type="checkbox" id="box${key}" value="${key}" checked="checked" onclick="checkedInput('${key}')" name="categorys" class="ui-form-checkbox"/>
	                    					${categoryName}
                    				 		<input type="text" name="inputRat" class="inputRat" style="width: 50px;" id="inputs${key}" maxlength="5" value='${commissionRatio}' title="请输入佣金比例"></input>%
	                    				</s:else>
                    				
                    				<input type="hidden" value=""/>
                    			 </li>
                    			  </s:if>	
                    			 </s:iterator>
                    			  </s:elseif>
                    	</s:iterator> 
                    	 --%>
        </td>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle"><font color="red">*</font>分配仓库：</th>
        <td>
            <s:select list="#request.warehouseInfoStatusMap" id="warehouseId" name="warehouseId" headerKey="" headerValue="--请选择仓库--"></s:select>
        </td>
    </tr>
    <tr>
        <th align="right" class="eidt_rowTitle">资质文件：</th>
        <td>
            <s:iterator value="suppliersCertificateList">
                &nbsp;&nbsp;<a href="${imagePath}${filePath}" title="单击放大" target="_blank" id="hrefId${scId}"><img width="190px" height="190px" src="${imagePath}${filePath}">
                ${fileName }</a>&nbsp;&nbsp;
            </s:iterator>
        </td>
    </tr>
</table>

<!-- 底部 按钮条 -->
<table width="98%" align="center" class="edit_bottom" height="30"
       border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
    <tr>
        <td align="left">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" class="backBtn" onClick="gotoList()" />&nbsp;&nbsp;&nbsp;<input class="btn-custom btnStyle" type="button" onClick="batchAuditSupplier()" value="审核通过">
            &nbsp;&nbsp;&nbsp;<input class="btn-custom btnStyle" type="button" onClick="noPassSupplier()" value="不通过">
        <td width="20%" align="center">&nbsp; <br>
        </td>
    </tr>
</table>

<br>
<br>

<form name="frm"  method="post" >
    <input type="hidden"  name="pageNum"  value="<s:property value='pageNum' />"   />
    <input type="hidden"  name="selectSuppliersInfo.corporateName"  value="<s:property value='selectSuppliersInfo.corporateName' />"   />
    <input type="hidden"  name="selectSuppliersInfo.corporateLocation"  value="<s:property value='selectSuppliersInfo.corporateLocation' />"   />
    <input type="hidden"  name="selectSuppliersInfo.contactsName"  value="<s:property value='selectSuppliersInfo.contactsName' />"   />
    <input type="hidden"  name="selectSuppliersInfo.status"  value="<s:property value='selectSuppliersInfo.status' />"   />
    <input type="hidden"  name="selectSuppliersInfo.enterpriseStatus"  value="<s:property value='selectSuppliersInfo.enterpriseStatus' />"   />
    <input type="hidden"  name="selectSuppliersInfo.supplierType"  value="<s:property value='selectSuppliersInfo.supplierType' />"   />
</form>

<SCRIPT LANGUAGE="JavaScript">
    function gotoList() {
        document.forms[0].action= "supplierAuditList.action";
        document.forms[0].submit();

    }

    function batchAuditSupplier(){
        var supplierId=$("#supplierId").val();//获得供应商id
        var userLoginIdVal=$("#UserLoginId").val();
        var ckTypes=false;
        var types = document.getElementsByName("inputs");
        var categorysId = document.getElementsByName("supplierCategorysIds");
        var cate = document.getElementsByName("categorys");

        var str="";//保存类目id
        var bili="";//佣金比例
        var sacIdStr="";//供应商类目主键id
        var suppliersTypeIdVal=$("#suppliersTypeId").val();
        var str1="";//保存没选择中自营类目id
        var strClass="";//保存class名
        var isTrue = true;
        if("2" == suppliersTypeIdVal){
            for(var i=0;i<cate.length;i++){
                strClass = (cate[i].className).split(' ');
                if(strClass[1] == 'class0'){
                    isTrue = false;
                }else{
                    isTrue = "" == $("#inputs"+cate[i].value).val();
                }
                if(cate[i].checked == true && isTrue ){
                    alert("请输入佣金比例!");
                    return;
                }else{
                    if(strClass[1] != 'class0'){
                        var ckbili=$("#inputs"+cate[i].value).val();
                        // var reg=/^[1-9]|[1-9]\d|100/;
                        var reg=/^((\d|[123456789]\d)(\.\d+)?)$/;
                        if(cate[i].checked == true && (reg.test(ckbili)== false || ckbili.substring(ckbili.indexOf(".")+1,ckbili.length).length > 2 || ckbili==0)){
                            alert("请输入大于0小于100的佣金比例,小数最多为两位");
                            return;
                        }
                        if(cate[i].checked == true){
                            str+=cate[i].value+",";//类目id
                            bili+=$("#inputs"+cate[i].value).val()+",";//佣金比例
                            sacIdStr+=$("#hid"+cate[i].value).val()+",";//供应商分配类目id
                            ckTypes=true;
                        }
                    }else{
                        if(cate[i].checked == true){
                            str+=cate[i].value+",";//类目id
                            bili+=",";//佣金比例
                            sacIdStr+=$("#hid"+cate[i].value).val()+",";//供应商分配类目id
                            ckTypes=true;
                        }
                    }
                }

            }

            if(ckTypes == false){
                alert("请选择类目!");
                return;
            }
        }else{
            for(var i=0;i<cate.length;i++){
                strClass = (cate[i].className).split(' ');
                if(strClass[1] == 'class0'){
                    isTrue = false;
                }else{
                    isTrue = "" == $("#inputs"+cate[i].value).val();
                }
                if(cate[i].checked == true && isTrue){
                    alert("请输入佣金比例!");
                    return;
                }else{
                    if(strClass[1] != 'class0'){
                        var ckbili=$("#inputs"+cate[i].value).val();
                        // var reg=/^[1-9]|[1-9]\d|100/;
                        var reg=/^((\d|[123456789]\d)(\.\d+)?)$/;
                        if(cate[i].checked == true && (reg.test(ckbili)== false || ckbili.substring(ckbili.indexOf(".")+1,ckbili.length).length > 2 || ckbili==0)){
                            alert("请输入大于0小于100的佣金比例,小数最多为两位");
                            return;
                        }
                        if(cate[i].checked == true){
                            str+=cate[i].value+",";//类目id
                            bili+=$("#inputs"+cate[i].value).val()+",";//佣金比例
                            sacIdStr+=$("#hid"+cate[i].value).val()+",";//供应商分配类目id
                            ckTypes=true;
                        }
                    }else{
                        if(cate[i].checked == true){
                            str+=cate[i].value+",";//类目id
                            bili+=",";//佣金比例
                            sacIdStr+=$("#hid"+cate[i].value).val()+",";//供应商分配类目id
                            ckTypes=true;
                        }
                    }
                }

            }

            if(ckTypes == false){
                var hidSacIdObjs=$(".hidSacId");
                for(var i=0;i<hidSacIdObjs.length;i++){
                    str1+=hidSacIdObjs[i].value+",";
                }
            }
        }
        var warehouseIdval=$("#warehouseId").val();
        var supplierTypeVal=$("#suppliersTypeId").val();
        if("" == supplierTypeVal){
            alert("请选择供应商类型!");
            return;
        }
        if("" == warehouseIdval){
            alert("请选择仓库!");
            return;
        }
        $.ajax({
            url: 'updateSupplierStatus.action',
            async:false,
            data: 'suppliersInfo.supplierId='+supplierId+'&goryIdsValues='+str+'&warehouseId='+warehouseIdval+'&CommissionRatios='+bili+'&userLoginId='+userLoginIdVal+'&supplierTypes='+supplierTypeVal+'&sacIds='+sacIdStr+'&goryIdsValues1='+str1,
            success: function(info) {
                if('0' == info){
                    alert("审核失败!");
                    document.forms[0].action= "supplierAuditList.action";
                    document.forms[0].submit();
                    return;
                }else{
                    alert("审核成功!");
                    //location.href = "supplierAuditList.action";
                    document.forms[0].action= "supplierAuditList.action";
                    document.forms[0].submit();
                }
            }
        });
    }

    function checkedInput(val){
        var inpuObj = document.getElementById("box"+val);
        var strClass = (inpuObj.className).split(' ');
        var nodeId = '';//父节点id
        if(strClass[1]=='class0'){//父类 点击   同级下所有联动
            if(inpuObj.checked){
                $(".class"+val).attr("checked", true);
                $(":input[name='inputRat"+val+"']").attr("disabled",false);
            }else{
                $(".class"+val).attr("checked", false);
                $(":input[name='inputRat"+val+"']").attr("disabled",true);
            }
        }else{//子类点击  判断自身将要勾选 检查父类是否选择，未选中则选择  将要取消选中  检查所有子类确定是否取消
            if(inpuObj.checked){//子节未勾选
                nodeId = 'box'+strClass[1].substring(5);
                if(nodeId.checked){//父节点已勾选
                }else{//未勾选就勾选
                    $("#"+nodeId).attr("checked", true);
                }
                $("#inputs"+val).attr("disabled",false);
            }else{//子节点已勾选
                nodeId = 'box'+strClass[1].substring(5);
                if ($("."+strClass[1]+":checked").length>0) {//存在勾选不操作
                }else {//不存在勾选，去掉父节点勾选
                    $("#"+nodeId).attr("checked", false);
                }
                $("#inputs"+val).attr("disabled",true);
            }
        }
    }

    function noPassSupplier(){
        var suppId=$("#supplierId").val();
        popDialog('supplierForCause.action?suppliersInfo.supplierId='+suppId,'填写不通过的理由',650,330);
    }
    function closes(){
        window.location.href="supplierAuditList.action";
        closeThis();
    }
    function showImg(path){
        popDialog(path,'查看纸质文件',650,430);
    }
</SCRIPT>
</BODY>
</HTML>