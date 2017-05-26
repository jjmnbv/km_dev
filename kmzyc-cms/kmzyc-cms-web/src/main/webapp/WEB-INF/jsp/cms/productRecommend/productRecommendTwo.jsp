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
	</script>	
	

		<div class="recommendAft-box">
<form id="test" action="/cms/showListRecommend.do" method="post" name="userLevelForm1">			
			<div class="ui-form-item">
                                <label class="ui-label">已选商品数量</label>
                                <span class="tips"><s:property value="page.recordCount"/></span>
                                <a class="ui-widget-btn" href="javascript:delRecommend();"><span>批量删除</span></a>
                            </div>
			<!-- 数据列表区域 -->
			<input type="hidden" id="userDefinedNameId" name="userDefinedName" value='<s:property value="userDefinedName"/>'></input>
			<input type="hidden" value='<s:property value="windowId"/>' name="windowId" id="windowId">
			<input type="hidden" value='<s:property value="show"/>' name="show" id="show">
			<input type="hidden" id="rtnMsg" value="<s:property value="rtnMessage"/>" />
			<input type="hidden" value='<s:property value="shopI"/>' name="shopI" id="shopId">
			<input type="hidden" value='<s:property value="status"/>' name="status" id="status">
			<table class="ui-table" width="98%" class="ui-table" cellpadding="3" align="center"
				cellspacing="0">
				<thead>
				<tr>
					<th width="5%">
						<input type='checkbox' name='level'  onclick="checkAll(this,'levelId')">
					</th>
					<th>
						产品名称
					</th>
					
					<th class="w80">
						商品编码
						
					</th>
					
					
					<th class="w100">
			                                物理类目
					</th>
					<th class="w100">
			              SKU编码
					</th>
				<th class="w60">
					           价格
					</th>
					<th class="w30">
					           排序
					</th>
				</tr>
				</thead>
				<tbody>
				<s:iterator  value="page.dataList">
					<tr>
						<td width="5%">
							<input type="checkbox" name="levelId"
								value='<s:property value="windowDataId"/>'>
								
						</td>
						<td>
						<s:property value="procuctName"/>
						
						</td>
						<td>
							
							<s:property value="productNo"/>
						</td>
						<td>
				
							<s:property value="categoryName"/>
						</td>
						
						<td>
						    <s:property value="productSkuCode"/>
						</td>
					<td>
					<s:property value="price"/>
					<!--   <img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="editUserLevel(<s:property value="promotionAttrId"/>)" />
						    <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value="promotionAttrId"/>)" />-->
						</td>
						<td><input type="text" name="sortsArr" value=' <s:property value="sort"/>' onkeyup="value=value.replace(/[^0-9]/g,'')" reg="^((?!0)\d{1,2})$"  tip="请输入0-99的整数" maxlength="2" style="width: 30px"></input>
						<input type="hidden" name="dwindowDataIdArr" value='<s:property value="windowDataId"/>'></input>
						</td>
						
					</tr>
				
				</s:iterator > 
				</tbody>
					
				
			</table>
			
			<div class="clear p10">
				 <div class="ui-page ui-page-sm right">
				 <table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>
					 <s:set name="form_name" value="'userLevelForm1'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
						
					</td>
				</tr>
			</table>
				
				 </div>
			
			</div>
		</form>
		</div>
		  <div class="ui-dialog-z-footer">
        <a class="btn ui-btn-save" TYPE="button" onclick="javascript:updateSorts();" >保存</a>
       <!--   <a class="btn ui-btn-cancel" TYPE="button" onclick="javascript:closeWin();">取消</a>-->
    </div>
		<s:if test='!rtnMessage.isEmpty()'>
	<SCRIPT LANGUAGE="JavaScript">
	
		alert(document.getElementById("rtnMsg").value);

	</SCRIPT>
</s:if>
		<script>
		function delRecommend(){
			var checkboxObj=document.getElementsByName("levelId");
			var ck=false;
			var windowIdVals="";
			for(var i=0;i<checkboxObj.length;i++){
				if(checkboxObj[i].checked){
					ck=true;
					windowIdVals+=checkboxObj[i].value+",";
					}
				}
			if(ck == false){
				alert("请选择商品！");
				return;
				}
			if(confirm("确定删除所选信息？")){
				}else{
				return;
					}
			$.ajax({
				url: '/cms/delProductRecommendAction.do',
		          async:false,
		          data: 'dwindowDataIds='+windowIdVals+'&shopI='+$("#shopId").val(),
		          success: function(info) {
		             if('0' == info){
		             	alert("系统异常!");
		     			return;
		             }else{
		           	alert("删除成功!");
		             	//parent.document.getElementById("iframepage1").src='productRecommendAction2.action';
		             	//document.getElementById("test").action="/cms/showListRecommend.do";
		             	$("#test").submit();
		            	 //document.getElementById("ADV_queryPageList1").submit();
		            	 //document.getElementById("ADV_queryPageList").submit();
		          // 	parent.document.getElementById("iframepage1").src='/cms/showListRecommend.do';
		             }
		            
		          }
				});
			   	
          	
			}

		function updateSorts(){
			   var lableVal=window.parent.document.getElementById('labelName').value;
			   $("#userDefinedNameId").val(lableVal);
			   var shows="";
			   if(lableVal == "0"){
				   shows="0";
				   $("#show").val(0);
				   }else{
					   $("#show").val(1);
					   var sta=window.parent.document.getElementById('status');
					   if(sta.checked){
						   $("#status").val(0);
						   if(lableVal == ""){
							   alert("请输入栏目标题！");
							   return;
							   }
						   }else{
							   $("#status").val(1);
							   }
					  }
				//var strs="";
			//	var sts1="";
			 //  var windIdsObj=document.getElementsByName("dwindowDataId");
			 //  var sortsObj=document.getElementsByName("sorts");
			//   for(var i=0;i<windIdsObj.length;i++){
				 //  strs+=windIdsObj[i].value+",";
				  // sts1+=sortsObj[i].value+",";
				  // }
			  // $.ajax({
				//	url: 'updateSorts1.do',
			      //    async:false,
			     //     data: 'dwindowDataIds='+strs+"&sortsArr="+sts1+"&userDefinedName="+encodeURI(encodeURI(lableVal))+"&show="+shows+"&windowId="+$("#windowId").val(),
			     //     success: function(info) {
					//	var indes = info.lastIndexOf(",");
                   //     var counts = info.substring(indes + 1, info.length);//获得返回来的 标示
                     //   var indesTwo = info.indexOf(",");
                     //   var countTwo = info.substring(indesTwo + 1, info.length);
                     //   var name = info.substring(0, indesTwo);
                      //  alert(counts);
			           //  if('0' == info){
			            // 	alert("系统异常!");
			     		//	return;
			           //  }
			            // if('1'==counts){
			             //	alert("保存成功!");
			             //	window.parent.document.getElementById('labelName').value=$("#userDefinedNameId").val();
			             	//parent.document.getElementById("iframepage1").src='/cms/showListRecommend.do';
			             	//$("#ADV_queryPageList1").submit();
			             	//$("#test").submit();
			             //}
			        //  }
				//	});
				document.getElementById("test").action="/cms/updateSorts1.do";
			   $("#test").submit();
			   window.parent.document.getElementById('labelName').value=$("#userDefinedNameId").val();
			   if($("#status").val() == 0){
				   window.parent.document.getElementById('status').checked=true;
				   }else{
					   window.parent.document.getElementById('status').checked=false;
					   }
			   
			}
		//$(document).ready(function() {
			//window.parent.document.getElementById('labelName').value=$("#userDefinedNameId").val();
			//}); 
 </script>
		
	</body>
</html>

