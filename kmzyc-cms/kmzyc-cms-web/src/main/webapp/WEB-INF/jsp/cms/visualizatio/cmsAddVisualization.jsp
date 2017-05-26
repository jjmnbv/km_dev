<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>页面可视化窗口数据添加</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/windowData.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>

		<style type="text/css">
			.list_table tr input
			{
				width:120px;
			}
		</style>
	</head>
	<body>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form name="dateForm" id="dateForm" action="/cms/cmsPageVisualizationAction_saveDate.action" method="post" enctype="multipart/form-data">
		<s:token></s:token>
		<input type="hidden" id="stylesId" name="stylesId" value='<s:property value="stylesId"/>'>
			<!-- 数据列表区域 -->
			<table width="95%" class="list_table" cellpadding="3" align="center">	
				<tr><td style="font-size:20px;text-align:left;">共添加了<span id="num">0</span>行</td></tr>
			</table>
			<table width="95%" class="list_table" cellpadding="3" align="center" id="list_table">	
			</table>
			<!-- 底部 按钮条 -->
			<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
				<tr> 
					<td align="left">
						<INPUT id="addBtn" class="addBtn" TYPE="button" value="" onclick="addRowsFun()">
						<INPUT class="saveBtn" TYPE="submit" value="">
						<input class="backBtn"  onclick="javascript:window.history.back(-1)" type="button" value="">
					</td>
				</tr>
			</table>
		</s:form>
		
		
		<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	</body>
	<script type="text/javascript">
		var num=0;
		function delTr(obj)
		{
			var tab=document.getElementById("list_table");
			var tabLength=$("#list_table tr").length;
			var tr = obj.parentNode.parentNode;
			var rowNum=tr.childNodes[0].innerHTML;
	  		$(obj).parent().parent().remove();
			for(i=rowNum;i<tabLength;i++)
			{
				//修改编号
				tab.rows[Number(i)-1].cells[0].innerHTML=tab.rows[Number(i)-1].cells[0].innerHTML-1;
				
				
				//修改类型下标
				var datatype=document.getElementById("datatype"+(Number(i)+1));
				//datatype.setAttribute("id", "datatype"+i);
				datatype.id="datatype"+i;
				//datatype.setAttribute("name", "cmsWindowDatas["+i+"].user_defined_type");
				datatype.name="cmsPageVisualizations["+i+"].user_defined_type";
				
				//修改名称下标
				var datatext=document.getElementById("datatext"+(Number(i)+1));
				datatext.id="datatext"+i;
				datatext.name="cmsPageVisualizations["+i+"].user_defined_name";
				//修改URL下标
				var dataurl=document.getElementById("dataurl"+(Number(i)+1));
				dataurl.id="dataurl"+i;
				dataurl.name="cmsPageVisualizations["+i+"].user_defined_url";
				
				//修改图片下标
				var picurl=document.getElementById("picurl"+(Number(i)+1));
				picurl.id="picurl"+i;
			}
			num=Number(tabLength)-1;
			$("#num").html(num);
		}
		
		
// 添加一行
var _len=0;
function addRowsFun() {
	var pageId = $("#pageId").val();
	 _len = $("#list_table tr").length;
	$("#list_table").append(
					"<tr>"
					+"<td>"
					+(Number(_len)+1)
					+"</td>"
					+"<td>数据编号:<input  type='text' id=datawindow"
					+(Number(_len)+1)
					+" name='cmsPageVisualizations["
					+(Number(_len)+1)
					+"].dataid'/>"
					+"</td>"
					+"<td>高度:<input type='text' id=dataheight"
					+(Number(_len)+1)
					+" name='cmsPageVisualizations["
					+(Number(_len)+1)
					+"].height'/>"
					+"</td>"
					+"<td>宽度:<input type='text' id=datawidth"
					+(Number(_len)+1)
					+" name='cmsPageVisualizations["
					+(Number(_len)+1)
					+"].width'/></td>"
					+"<td>排序:<input type='text' id=datasort"
					+(Number(_len)+1)
					+" name='cmsPageVisualizations["
					+(Number(_len)+1)
					+"].sort'/></td>"
					+"<td>类名:<input type='text' id=datasort"
					+(Number(_len)+1)
					+" name='cmsPageVisualizations["
					+(Number(_len)+1)
					+"].divclass'/></td>"
					+"<td>数据类型:"
					+"	<select id=datatype"
					+(Number(_len)+1)
					+" name='cmsPageVisualizations["
					+(Number(_len)+1)
					+"].datatype'>"
					+"		<option value='1'>窗口</option>"
					+"		<option value='2'>广告</option>"
					+"	</select>"
					+"</td>"
					+"<td>是否隐藏:"
					+"	<select id=datatype"
					+(Number(_len)+1)
					+" name='cmsPageVisualizations["
					+(Number(_len)+1)
					+"].isdisplay'>"
					+"		<option value='1'>否</option>"
					+"		<option value='0'>是</option>"
					+"	</select>"
					+"</td>"
					+"<td>图片地址:<input type='file' id=picurl"
					+(Number(_len)+1)
					+" name='files'"
					+"/></td>"
					+"<td><img style='cursor:pointer;' src='/etc/images/icon_delete.png' onclick='delTr(this)'/></td>"
					+"</tr>"
					);
			var addBtn=document.getElementById("addBtn");
			addBtn.focus();
			$("#num").html(Number(_len)+1);
}

</script>

</script>
</html>

