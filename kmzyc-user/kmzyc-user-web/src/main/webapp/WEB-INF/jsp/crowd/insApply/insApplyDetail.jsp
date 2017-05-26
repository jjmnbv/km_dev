<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=2,user-scalable=no"/>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<title>机构申请详情</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/img_append.css" type="text/css" rel="stylesheet">
<style type="text/css">
.tableStyle1 {
	font-size: 15px;
}

.two ul{width:155px;margin:0 6px 0 0;border:0px;padding:0px;text-align:left;list-style-type:none;}
.two ul li{list-style-type:none;width:150px;}
.two ul li img{width:150px;}


.emDiv,.sbDiv{
float: left;position:relative;margin:3px 5px 2px 0;
white-space:nowrap;height:15px;line-height: 15px;
cursor:pointer;border-radius:17px;border-style:
solid;border-width:1px;font-size:14px;
padding:2px 19px;border-color:#edb8b8;
background-color:#ffeaea;color:#c30!important;
display:inline-block;vertical-align:middle;
}

em{
margin-left:-8px;vertical-align:top;display:inline-block;font-style:normal;
text-decoration:none;white-space:nowrap;line-height:15px;cursor:pointer;font-size:14px;
}
.aclose,.deleteP{position: absolute;right: -2px;top: -1px;text-decoration: none;font-family: verdana;border-radius: 0 17px 17px 0;
font-weight: bold;padding: 2px 5px 2px 3px;border-width: 1px;border-style: solid;border-color:#edb8b8!important;color:#c30!important;}

</style>
</head>      
<s:set name="parent_name" value="'机构管理'" scope="request"/>
<s:set name="name" value="'机构审核'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
	<div style="margin:10px">
			<button class="backBtn" id="return" onClick="history.go(-1)"></button>
			
 	</div>
<div id="maind" style="width:90%;margin-left:5%;text-algin:center;">
<div id="content">		
<form action="updateSpreader.action" method="post" id="myForm">
	<!-- 数据编辑区域 -->
	<table width="60%" class="edit_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C7D3E2"
		style="border-collapse: collapse; font-size: 12px;">
		<!-- error message -->
		<s:if test="rtnMessage != null">
			<tr>
				<td colspan="2" align="center"><font color="red"><s:property
					value='rtnMessage' /></font></td>
			</tr>
		</s:if>
		
		
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">机构名称</th>
		  	<td width="80%">
		  		<s:property escape="false" value='institutionApplyRecord.institutionName' />
		  		<input id ="insCode" type="hidden" value="<s:property value='institutionApplyRecord.institutionName' />"></input>
		  	</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">机构编号</th>
		  	<td width="80%">
				<s:property value='institutionApplyRecord.institutionCode' />
				</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">机构地址</th>
		  	<td width="80%">
				<s:property escape="false" value='institutionApplyRecord.institutionAddress' />
			</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">机构联系人</th>
		  	<td width="80%">
				<s:property escape="false" value='institutionApplyRecord.institutionContactor' />
			</td>
		</tr><tr>
			<th width="20%" align="right" class="eidt_rowTitle">机构联系电话</th>
		  	<td width="80%">
				<s:property value='institutionApplyRecord.institutionPhonenumber' />
			</td>
		</tr><tr>
			<th width="20%" align="right" class="eidt_rowTitle">机构注册分利现金</th>
		  	<td width="80%">
				<s:property value='institutionApplyRecord.registRebate' />
			</td>
		</tr><tr>
			<th width="20%" align="right" class="eidt_rowTitle">机构推广有效期</th>
		  	<td width="80%">
		  	<s:date name="institutionApplyRecord.spreadStartDate" format="yyyy-MM-dd HH:mm:ss"/>至
		  	<s:date name="institutionApplyRecord.spreadEndDate" format="yyyy-MM-dd HH:mm:ss"/>
			</td>
		</tr><tr>
			<th width="20%" align="right" class="eidt_rowTitle">创建日期</th>
			<td width="80%">
			<s:date name="institutionApplyRecord.createDate" format="yyyy-MM-dd HH:mm:ss"/>
			</td>
		</tr><tr>
			<th width="20%" align="right" class="eidt_rowTitle">业务员</th>
		  	<td width="80%">
		  		<s:property value='institutionApplyRecord.bagmanName' />
		  		<input id ="bagmanId" type="hidden" value="<s:property value='institutionApplyRecord.bagmanId' />"></input>
			</td>
		</tr>
		
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">认证图片</th>
		  	<td width="80%">
				<div id="divImg">
				</div>
			</td>
		</tr>
		<tr>
			<th width="20%" align="right" class="eidt_rowTitle">备注</th>
		  	<td width="80%">
			<textarea id="auditeMemo" name="institutionApplyRecord.auditeMemo" cols="45" rows="5" maxlength="80"><s:property value='institutionApplyRecord.auditeMemo' /></textarea>

			</td>
		</tr>
		<%-- <tr>
			<th width="20%" align="right" class="eidt_rowTitle">创建时间</th>
		  	<td width="80%"><fmt:formatDate value="${info.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		</tr> --%>
		

		<tr>
		  	<td colspan="2"  align="center">
		  	<s:if test="institutionApplyRecord.auditeState == 0">
			<input type="button"  class="btn-custom" value="审核通过" id="auditAgree" />
		  	<input type="button" class="btn-custom"  value="审核拒绝" id="auditFire" />
			</s:if>
		  	
		  	 </td>
		</tr>
		
		<div id="append_parent"></div>
	</table>
	<br><br><br><br><br><br>
	<input type="hidden" id="aid" name="institutionApplyRecord.id"   value="<s:property value='institutionApplyRecord.id' />">
</form>	
</div></div>
</body>
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
$(function(){
	//审核通过
	 $("#auditAgree").click(function(){
		 $.getJSON("/crowd/auditInsApp.action", {
			 	'aid' : $("#aid").val(),
				'auditeState' : 1,
				'bagmanId' : $("#bagmanId").val(),
				'insCode' : $("#insCode").val(),
				'auditeMemo' : encodeURI($("#auditeMemo").val())
			}, function(data) {
				var code = data.code;
				var message = data.message;
				alert(message);
				self.location='/crowd/listInsApp.action'; 
			});

	 });
	 
	
	//审核拒绝
	 $("#auditFire").click(function(){
		 if($("#auditeMemo").val()==null || $("#auditeMemo").val().length==0){
			 alert("拒绝操作请填写备注信息！");
			 return;
		 }
		 $.getJSON("/crowd/auditInsApp.action", {
			 'aid' : $("#aid").val(),
				'auditeState' : 2,
				'bagmanId' : $("#bagmanId").val(),
				'insCode' : $("#insCode").val(),
				'auditeMemo' : encodeURI($("#auditeMemo").val())
			}, function(data) {
				var code = data.code;
				var message = data.message;
				alert(message);
				self.location='/crowd/listInsApp.action'; 
			});

	 });
	
	//ajax根据aid，获取图片
	 window.onload=function(){
	 	$.getJSON("/crowd/getInsAppInfo.action", {
	 		 	'aid' : $("#aid").val()
	 		}, function(data) {
	 			var code = data.code;
	 			var message = data.message;
	 			if(code==1){
	 				var list = data.data.imageUrls;
	 				var showCrowdSourcingImage = data.data.showCrowdSourcingImage;
	 				var imgHtmlString = "";
	 				for(var i in list){
	 					imgHtmlString = imgHtmlString+"<img  src='"+showCrowdSourcingImage+list[i].imageUrl
	 					+"' alt  style = 'height:200px; width:200px;' onclick='javascript:window.open(this.src);' style='cursor:pointer;' >"	 					
	 				}
	 				document.getElementById('divImg').innerHTML = imgHtmlString;
	 				
	 				
	 			}
	 			
	 		});
	 }
	
	
	//内容页图片点击放大效果
	    var imgBoxMod=$(".ctnlist .text img");
	    imgPop(imgBoxMod);
	    //内容页图片点击放大效果函数主体开始
	    function imgPop(imgBoxMod){
	        imgBoxMod.each(function(){
	        //超过最大尺寸时自动缩放内容页图片尺寸
	        var ctnImgWidth=$(this).width();
	        if(ctnImgWidth>618){
	                $(this).width(618);
	            }
	        //点击图片弹出层放大图片效果
	        $(this).click( function(){
	            $("#append_parent").html("<div id='imgzoom'><div id='imgzoom_zoomlayer' class='zoominner'><p><span class='y'><a title='在新窗口打开' target='_blank' class='imglink' id='imgzoom_imglink' href='''>在新窗口打开</a><a title='关闭' class='imgclose'>关闭</a></span></p><div id='imgzoom_img' class='hm'><img src='' id='imgzoom_zoom' style='cursor:pointer'></div></div></div><div id='imgzoom_cover'></div>"); //生成HTML代码
	            var domHeight =$(document).height(); //文档区域高度
	            $("#imgzoom_cover").css({"display":"block","height":domHeight});
	            var imgLink=$(this).attr("src");
	            $("#imgzoom_img #imgzoom_zoom").attr("src",imgLink);
	            $("#imgzoom").css("display","block");
	            imgboxPlace();
	            })
	    })
	            //关闭按钮
	    $("#append_parent .imgclose").live('click',function(){
	        $("#imgzoom").css("display","none");
	        $("#imgzoom_cover").css("display","none");
	    })
	        //新窗口打开图片
	    $("#imgzoom_imglink").live('click',function(){
	        var imgLink=$("#imgzoom_zoom").attr("src");
	        $("#imgzoom_imglink").attr("href",imgLink);
	    })
	    //弹出窗口位置
	    function imgboxPlace(){
	        var cwinwidth=$("#imgzoom").width();
	        var cwinheight=$("#imgzoom").height();
	        var browserwidth =$(window).width();//窗口可视区域宽度
	        var browserheight =$(window).height(); //窗口可视区域高度
	        var scrollLeft=$(window).scrollLeft(); //滚动条的当前左边界值
	        var scrollTop=$(window).scrollTop(); //滚动条的当前上边界值
	        var imgload_left=scrollLeft+(browserwidth-cwinwidth)/2;
	        var imgload_top=scrollTop+(browserheight-cwinheight)/2;
	        $("#imgzoom").css({"left":imgload_left,"top":imgload_top});
	        }
	    }
	    //内容页图片点击放大效果函数主体结束   
	
});
</script>
</html>

