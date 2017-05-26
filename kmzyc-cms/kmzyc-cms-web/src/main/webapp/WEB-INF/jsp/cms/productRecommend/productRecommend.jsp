<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>广告位管理</title>
	    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		 <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
				<link rel="stylesheet" href="http://jscss.kmb2b.com/resshop/style/default/common.css">
		
	</head>
	<body >
		<!-- 导航栏 -->
	<script>
	 function iFrameHeight() {

	        var ifm= document.getElementById("iframepage");

	        var subWeb = document.frames ? document.frames["iframepage"].document :

	ifm.contentDocument;

	            if(ifm != null && subWeb != null) {

	            ifm.height = subWeb.body.scrollHeight;

	            }

	    }
	 function iFrameHeight1() {

	        var ifm= document.getElementById("iframepage1");

	        var subWeb = document.frames ? document.frames["iframepage1"].document :

	ifm.contentDocument;

	            if(ifm != null && subWeb != null) {

	            ifm.height = subWeb.body.scrollHeight;

	            }

	    }
	</script>	
		
		


<!-- 导航栏 -->

<input type="hidden" value='<s:property value="windowId"/>' id="windowId">
<input type="hidden" value='<s:property value="show"/>' id="show">
<input type="hidden" value='<s:property value="shopI"/>' id="shopId">
<input type="hidden" value='<s:property value="supplierId"/>' id="supplierId">
<div class="ui-dialog-z-header">
<h4>商品推荐</h4>
</div>
<div style="height:90%;overflow-y:scroll; ">


		<div class="ui-tipbox-z-content">
			<!-- 数据列表区域 -->
			
<s:if test="show==1">
<div class="ui-form-item">
                            <label class="ui-label" for="">栏目标题</label>
                            <div class="ui-form-explain">
                                <input type="text" maxlength="10" class="ui-input ui-input280" name="pro.promotionTag" id="labelName" value='<s:property value="userDefinedName"/>'>
                                <s:if test="status == 0">
						<span class="ui-tpis"><input type="checkbox" name="status" checked="checked" id="status">显示栏目标题</span>
						</s:if>
						<s:else>
						<span class="ui-tpis"><input type="checkbox" name="status"  id="status">显示栏目标题</span>
						</s:else>
                            </div>
                        </div>
                        </s:if>
                        <s:else>
					<input type="hidden" id="labelName" value="0"></input>
					</s:else>
                        


<iframe src="/cms/productRecommendActionOne.do?windowId=<s:property value='windowId'/>&supplierId=<s:property value='supplierId'/>&shopI=<s:property value='shopI'/>" marginheight="0" marginwidth="0" frameborder="0" scrolling="yes" width="900"  id="iframepage" name="iframepage" onLoad="iFrameHeight();"></iframe>


				<iframe src="/cms/showListRecommend.do?windowId=<s:property value='windowId'/>&show=<s:property value='show'/>&shopI=<s:property value='shopI'/>" marginheight="0" marginwidth="0" frameborder="0" scrolling="yes" width="900"  id="iframepage1" name="iframepage1" onLoad="iFrameHeight1();"></iframe>


		</div>
		</div>
		<script>
		function updateSorts1(){
			document.getElementById('iframepage1').contentWindow.updateSorts();
			}
		//$(document).ready(function() {
			//window.parent.document.getElementById('labelName').value=$("#userDefinedNameId").val();
		//	document.getElementById('iframepage1').contentWindow.userDefinedName();
			//}); 
 </script>
		
	</body>
</html>

