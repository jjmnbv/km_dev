<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/validate.css" type="text/css" rel="stylesheet">
<style type="text/css">
#imageDIV img { max-width:600px; myimg:expression(onload=function(){ this.style.width=(this.offsetWidth > 600)?"600px":"auto"}); }
</style>
</head>

<script type="text/javascript">
$(document).ready(function() {
	var code =$("#code").val();
	if(code=='0'){
		alert('上传成功');
		parent.location.reload();
	}
	$("#frm").validate({
        rules: {
    		"image":{required:true}
        },
        messages:{
        	"image":"请选择图片"
    	},
    	success: function (label){
	            label.removeClass("checked").addClass("checked");
       	}
    });
	var imageUrl = $('#imageUrl').val();
	if(imageUrl){}else{
		$("#imageTd").hide();
	}
})

</script>
<body>
<s:form action="/promotion/uploadImage.action" method="post" id="frm" enctype="multipart/form-data" >
	<s:hidden name="imageCode" value="%{#request.imageCode}" id="imageCode"></s:hidden>
	<s:hidden name="promotion.promotionId"></s:hidden>
	<s:hidden name="code" id="code" value="%{#request.code}"></s:hidden>
	<s:hidden name="imageUrl" id="imageUrl" value="%{#request.imageUrl}"></s:hidden>
	<%//<s:hidden name="originalImageUrl" value="#request.originalImageUrl"></s:hidden> %>
   <table width="98%" align="center" class="edit_bottom" height="30"
		border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
		
		<tr>
			<td align="left">
			<s:file label="上传" theme="simple" name="image"/>
   			<s:submit value="上传"/>
   			</td>
		</tr>
		<tr>
		
   		<td id="imageTd" align="center"><img id="imageDIV" alt="" src="<s:property value="#request.imageUrl"/>"/>
   		</td>
		</tr>
	</table>
</s:form>
</body>
</html>