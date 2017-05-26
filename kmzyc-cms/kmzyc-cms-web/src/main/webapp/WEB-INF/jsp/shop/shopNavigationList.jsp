<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
  <head>
    
    <title>编辑导航</title>
    <meta name="keywords" content="店铺模版">
	<meta name="description" content="店铺模版">
	<link rel="stylesheet" href="http://jscss.kmb2b.com/resshop/style/default/common.css">
	<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
	<script type="text/javascript"  src="/etc/js/jquery.form.js"></script>
	<script src="/etc/js/dialog.js"></script>

  </head>
  <script type="text/javascript">
  		$(function(){
  			//保存
	        $(".ajaxBut").click(function (){
				  $("#ajaxSubmit").ajaxSubmit({  
			         type:"post",  //提交方式  
			         url:"/cms/shopsDecorateCustomAction_saveNavigationCustomData.do", //请求url  
			         success:function(data){ //提交成功的回调函数  
			             alert('保存数据成功！');  
			             try {
					     		document.domain = 'kmb2b.com';
					            window.frameElement.trigger('close');
					        } catch (error) {
					            window.console && console.log('error',error);
					        }
			         },
			         error:function(){  
			     		alert('保存数据失败！');  
			     	}   
			     });
		     	});
		     //关闭弹窗
		     $(".close").click(function (){
		     	try {
		     		document.domain = 'kmb2b.com';
		            window.frameElement.trigger('close');
		        } catch (error) {
		            window.console && console.log('error',error);
		        }
		     });
        });
        function gotoNavigationCustomData(windowId){
	  		if($(".ui-table tr").length>7){
	  			alert('最多可添加7个导航');
	  		}else{
		  		var arr = [];
				$('.ui-table tr').each(function(){
				   var t = $(this);
				   var str = t.find('td').eq(0).html();
				   if(str){
				   	arr.push(str);
				   }
				});
		        var shopI = $("#shopI").val();
				//console.log(arr.join(','));
		  		window.location.href="/cms/shopsDecorateCustomAction_gotoNavigationCustomData.do?windowId="+windowId+"&"+arr+"&shopI="+shopI;
	  		}
	  	}
	  	
	  	function deleteNavigationCustomData(windowDataId,windowId){
	        var shopI = $("#shopI").val();
	  		window.location.href="/cms/shopsDecorateCustomAction_deleteNavigationCustomData.do?windowDataId="+windowDataId+"&windowId="+windowId+"&shopI="+shopI;
	  	}
	  	
	  	function selectNavigationCustomData(windowDataId){
	  		var arr = [];
			$('.ui-table tr').each(function(){
			   var t = $(this);
			   var str = t.find('td').eq(0).html();
			   if(str){
			   	arr.push(str);
			   }
			});
	        var shopI = $("#shopI").val();
	  		window.location.href="/cms/shopsDecorateCustomAction_selectNavigationCustomData.do?windowDataId="+windowDataId+"&"+arr+"&shopI="+shopI;
	  	}
	  	
		function updateSort(v){
	            var $v = $(v);
	            var sort = $v.val();
	            var windowDataId = $v.attr("data_windowDataId");
	            if((parseInt(sort)==sort)&&(parseInt(sort)>=0)){
	            }else{
	                 alert("请输入正整数数据，用于排序(含0)!")
	                 var timer_alert = setTimeout(function() {
	     				messageCloseThis();
	     			}, 2000);
	      			v.focus();
	            }
	        }
	  	
  </script>
  <body>
    <!--编辑导航-->
	<div class="ui-dialog-z" style=" width: 650px;">
		<s:form id="ajaxSubmit" namespace="/shop" enctype="multipart/form-data">
		<input type="hidden" id="windowId" name="windowId" value="<s:property value='windowId' />">
		<input type="hidden" id="shopI" name="shopI" value="<s:property value='shopI' />">
		
		<div class="ui-dialog-z-header">
			<h4>编辑导航</h4>
		</div>
		<div data-role="content" class="ui-dialog-z-content" >
			<div class="ui-tipbox-z-content">
				<div class="ui-tipbox ui-tipbox-success ui-tipbox-white">
					<div class="ui-manage-title">
						<a id="btnPopup" href='javascript:gotoNavigationCustomData(<s:property value="windowId"/>)'>+ 添加导航</a>
					</div>
					<!-- 数据列表 -->
					<table class="ui-table">
						<thead>
						<tr>
							<th>导航名称</th>
							<th>排序</th>
							<th>操作</th>
						</tr>
						</thead>
						<tbody>
						<s:iterator value="page.dataList" status="index">
							<tr>
								<td ><s:property value="dataName" /></td>
								<td><input type="text" name="cmsWindowDatas[<s:property value="page.recordCount-#index.index-1" />].sort" data_windowDataId="<s:property value='windowDataId' />" value="<s:property value='sort' />" onblur="updateSort(this);" class="ui-table-input"></td>
								<input type="hidden" name="cmsWindowDatas[<s:property value="page.recordCount-#index.index-1" />].windowDataId" value='<s:property value="windowDataId"/>'>
								<input type="hidden" name="cmsWindowDatas[<s:property value="page.recordCount-#index.index-1" />].user_defined_name" value='<s:property value="dataName"/>'>
								<input type="hidden" name="cmsWindowDatas[<s:property value="page.recordCount-#index.index-1" />].user_defined_url" value='<s:property value="user_defined_url"/>'>
								<td><span class="ui-table-edit">
									<a href='javascript:selectNavigationCustomData(<s:property value="windowDataId"/>)'>编辑</a>
									<a href='javascript:deleteNavigationCustomData(<s:property value="windowDataId"/>,<s:property value="windowId"/>)'>删除</a></span></td>
							</tr>
						</s:iterator>
						</tbody>
					</table>
					<p class="ui-tips"><span class="ui-form-required">*</span>最多可添加7个导航</p>
				</div>
				<div class="ui-dialog-z-footer">
					<input id='close' class="btn ui-btn-save ajaxBut" type="button" value="保存" />
					<input id='close' class="btn ui-btn-cancel close" type="button" value="取消" />
				</div> 
			</div>
		</div>
		</s:form>
	</div>
  </body>
</html>
