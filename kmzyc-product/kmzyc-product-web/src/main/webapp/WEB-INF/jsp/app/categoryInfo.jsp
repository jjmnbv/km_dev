<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.model.SysMenu"%>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>			
<s:if test="category.categoryId != null">
修改类目信息:
</s:if>
<s:else>
增加子类目信息:
</s:else>
</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="/etc/js/dialog-up.js" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/jquery-latest.pack.js" type="text/javascript"></script>

<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.form.js"></script>

<script type="text/javascript">
$(function(){
	$("#updateCateForm").validate({
		rules: {
			"category.categoryName":{required:true,maxlength:20,unusualChar:true,checkCategoryName:true},
			"category.categoryDesc":{maxlength:160},
            "category.categoryTitle":{maxlength:160},
            "category.categoryKeyword":{maxlength:200}
	    	},
		success: function (label){
			label.removeClass("checked").addClass("checked");
		}
		
	});
	
	jQuery.validator.addMethod("checkCategoryName", function(value, element) {
		var flag = true;
		$.ajax({
				async:false,
				url:"/app/checkRepeatNameCategory.action",
				type:"post",
				data:"category.categoryId="+$("#categoryId").val()+"&category.categoryName="+value+"&category.isPhy="+$("input[name='category.isPhy']").val()+"&category.parentId="+$("#parentId").val(),
				success:function(data){
					if(data!="no"){
						element.select();
						flag = false;
					}
				}
		});
		return flag;
	}, "该类目名已存在！");

    var options = {
        url: '/app/saveCategory.action',
        resetForm: false,
        success: showResponse,
        beforeSubmit: showRequest,
        dataType: 'json'
    };

    $('#updateCateForm').submit(function() {
        $(this).ajaxSubmit(options);
        return false;
    });
});

function changePath(arg) {
    document.getElementById("fileName").value = arg.value;
}

function showRequest(a,form,options) {
    return $(form).validate().form();
}

function showResponse(data) {
    if (data.status == "fail") {
        alert(data.msg);
    } else {
        alert(data.msg);
        var isPhy = $("input[name='category.isPhy']").val();
        if (isPhy == 1) {
            isPhy = "phy";
        } else {
            isPhy = "busi";
        }
        parent.window.location.href = '/app/queryCategoryList.action?type='+isPhy;
    }
}
</script>
</head>
<body>
<form name="updateForm" id="updateCateForm" action="saveCategory.action" method="POST"  namespace='/app' target="main"
      enctype="multipart/form-data">
	<input type="hidden" id="categoryId" name="category.categoryId"
           value="<s:property value='category.categoryId'/>" />
	<input type="hidden" name="category.categoryCode"
           value="<s:property value='category.categoryCode'/>" />
	<input name="category.parentId" type="hidden" id="parentId"
           name="parentId" value="<s:property value='category.parentId'/>" />
	
	<table width="95%" class="tableInput1" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
		<tr> 
			<th colspan="2" align="left" class="modeltitle">
			<s:if test="category.categoryId != null">
			修改类目信息:
			</s:if>
			<s:else>
			增加子类目信息:
			</s:else>
			</th>
		</tr>
		<tr> 
			<th align="right"><font color="red">*</font>类目名称：</th>
			<td> 
				<input name="category.categoryName" type="text" size="40"
                       maxlength="20" value="<s:property value='category.categoryName'/>"/>
			</td>
		</tr>
        <s:if test='category.isPhy == 2'>
            <tr>
                <th width="30%" align="right">标题：</th>
                <td width="70%" align="left">
                    <input name="category.categoryTitle" size="20" type="text"
                           value="<s:property value='category.categoryTitle'/>"/>
                </td>
            </tr>
            <tr>
                <th align="right">图片：</th>
                <td align="left">
                    <input type="file" name="categoryFile" onChange="changePath(this)"/> (请选择100*100,小于50KB的图片)
                    <s:if test="category.filePath != null">
                        <a href="<s:property value='imagePath'/><s:property value='category.filePath'/>"
                           target="_blank">
                            <img src="<s:property value='imagePath'/><s:property value='category.filePath'/>"
                                 style="width: 200px;height: 80px;">
                            </img>
                        </a></s:if>
                    <input type="hidden" id="fileName" name="category.fileName" value="" />
                    <%--<font color="red" id="messages"><s:property value='message' /></font>--%>
                </td>
            </tr>
        </s:if>
		<tr> 
			<th width="20%" align="right">类别：</th>
			<td width="80%"> 
				<s:if test='category.isPhy == 1'>
					<input type="hidden" name="category.isPhy" value="1" />
					物理类目
				</s:if>
				<s:if test='category.isPhy == 2'>
					<input type="hidden" name="category.isPhy" value="2" />
					运营类目
				</s:if>
			</td>
		</tr>
		<s:if test="category.parentId != null && category.parentId != 0">
		<tr> 
			<th width="20%" align="right">上级类目：</th>
			<td width="80%"> 
				<s:property value='category.parentName'/>
			</td>
		</tr>
		</s:if>
		<tr> 
			<th width="20%" align="right">类目序号：</th>
			<td width="80%"> 
				<input type="text" name="category.sortno" size="5" maxlength="3"
                       value="<s:property value='category.sortno'/>" class="{digits:true,maxlength:8}"  />
			</td>
		</tr>
        <s:if test='category.isPhy == 2'>
            <tr>
                <th width="30%" align="right">关键字：</th>
                <td width="70%" align="left">
                    <input name="category.categoryKeyword" size="20" type="text"
                           value="<s:property value='category.categoryKeyword'/>"/>
                </td>
            </tr>
        </s:if>
		<tr>
			<th align="right">类目备注：</th>
			<td> 
				<textarea name="category.categoryDesc" cols="40" rows="4"
                          wrap="categoryDesc"><s:property value='category.categoryDesc'/></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input id="btnSubmit" type="submit" class="btngreen"  style="height:30px" value="  保存  "/>&nbsp;&nbsp;&nbsp;&nbsp;
				<%--
				<input type="button" class="btngreen"  value=" 取消" onclick=""/>&nbsp;&nbsp;&nbsp;&nbsp;
				 --%>
			</td>
		</tr>
	</table>
</form>
</BODY>
</HTML>