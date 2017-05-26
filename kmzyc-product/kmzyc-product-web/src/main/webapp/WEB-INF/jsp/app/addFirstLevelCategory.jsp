<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加顶级类目</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery-1.4.4.min.js"></script>

<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.form.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<s:form name="popForm" id="popForm" action="/app/saveFirstLevelCategory.action"
        method="POST" enctype="multipart/form-data">
<!-- hidden properties -->
<INPUT TYPE="hidden" name="category.parentId" id="parentId" value="0">
<INPUT TYPE="hidden" name="para" value="isFirst">
	<table width="95%" class="tableInput1" align="center" cellpadding="3"
           cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
		<tr>
			<th colspan="2" align="left" class="modeltitle">增加顶级类目</th>
		</tr>
		<tr>
			<th width="30%" align="right"><font color="red">*</font>类目名称：</th>
			<td width="70%" align="left">
				<input name="category.categoryName" size="20"  maxlength="20"  type="text" />
			</td>
		</tr>
        <s:if test='type == "busi"'>
            <tr>
                <th width="30%" align="right">标题：</th>
                <td width="70%" align="left">
                    <input name="category.categoryTitle" size="20"  maxlength="160"  type="text" />
                </td>
            </tr>
            <tr>
                <th align="right">图片：</th>
                <td align="left">
                    <input type="file" name="categoryFile" onChange="changePath(this)"/> (请选择100*100,小于50KB的图片)
                    <input type="hidden" id="fileName" name="category.fileName" value="" />
                </td>
            </tr>
        </s:if>
		<tr>
			<th align="right">类目类别：</th>
			<td align="left">
				<s:if test='type == "phy"'>
					<input type="hidden" name="category.isPhy" value="1" />
					<input type="hidden" id="type" value="phy" />
					物理类目
				</s:if>
				<s:if test='type == "busi"'>
					<input type="hidden" name="category.isPhy" value="2" />
					<input type="hidden" id="type" value="busi" />
					运营类目
				</s:if>
			</td>
		</tr>
		<tr>
			<th align="right">类目序号：</th>
			<td align="left">
				<input type="text" name="category.sortno" size="5" maxlength="3" class="{digits:true,maxlength:8}"
                       value="<s:property value='model.sortno'/>" />
			</td>
		</tr>
        <s:if test='type == "busi"'>
            <tr>
                <th width="30%" align="right">关键字：</th>
                <td width="70%" align="left">
                    <input name="category.categoryKeyword" size="20"  maxlength="200"  type="text" />
                </td>
            </tr>
        </s:if>
		<tr>
			<th align="right">类目备注</th>
			<td align="left">
				<textarea rows="4" cols="40" name="category.categoryDesc" ></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="submit" class="btngreen" style="height:30px" value="保存"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btngreen" value="关闭" style="height:30px" onClick="closeDiv();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
	</table>

</s:form>
<SCRIPT LANGUAGE="JavaScript">
$(function(){
    $("#popForm").validate({
        rules: {
            "category.categoryName":{required:true,maxlength:20,unusualChar:true,checkCategoryName:true},
            "category.categoryDesc":{maxlength:160}
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
				data:"category.categoryName="+value+"&category.isPhy="+$("input[name='category.isPhy']").val()+"&category.parentId="+$("#parentId").val(),
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
        url: '/app/saveFirstLevelCategory.action',
        resetForm: false,
        beforeSubmit: showRequest,beforeSubmit: showRequest,
        success: showResponse,
        dataType: 'json'
    };

    $('#popForm').submit(function() {
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
        parent.closePopDiv();
    }
}

function closeDiv(){
    parent.closeThis();
}
</SCRIPT>
</BODY>
</HTML>