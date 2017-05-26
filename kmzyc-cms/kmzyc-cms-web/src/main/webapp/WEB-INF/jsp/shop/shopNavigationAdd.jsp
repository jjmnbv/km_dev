<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    
    <title>添加导航</title>
    <meta name="keywords" content="店铺模版">
	<meta name="description" content="店铺模版">
	<link rel="stylesheet" href="http://jscss.kmb2b.com/resshop/style/default/common.css">
	<script src="/etc/js/dialog.js"></script>
	<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
	<script type="text/javascript"  src="/etc/js/jquery.form.js"></script>
	<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
	<style>
	label.error{
	  color: red;
	}
</style>
  </head>
  <script type="text/javascript">
  var arr;
  	$(function(){
  		
  		$("#ajaxSubmit").validate({
            rules: {
					"cmsWindowDatas[0].user_defined_url":{checks:true}
				}
     });
	   
  jQuery.validator.addMethod("checks", function(value, element) {  
 	     
 	      var definedUrl=$("#definedUrl").val();
 	     var pattern = /^(http):\/\/([a-zA-Z0-9]*)\.(kmb2b)\.(com)(\/)*.*$/;
		  if(definedUrl == "" || pattern.test(definedUrl)){
			  return true;
		  }else{
			  return false;
		  }		
			
	    },"请填写完整的康美商城站内链接！"); 
  		
  		
  	//console.log(window.location.search.substr(1))
  	arr = window.location.search.substr(1).split('&')[1].split(',');
  	 
  		//限定输入长度
//	  $('.lengs').keyup(function(){
//	  	var len = $(this).val().length;
//	  	if(len>6){
//	  		$(this).val($(this).val().substring(0,6));
//	  	}
//	  });
	  $(".ajaxBut").click(function (){
	  	  var dataNameIs=$(".lengs").val();
	  	  var dataUrl = $("#definedUrl").val().substring(0,23);
	  	  if($.trim(dataNameIs) == ""){ 
	     	alert('导航名称不能为空！');
	     	return;
	      }else if(dataNameIs.length>6){
	      	alert('导航名称超过6个字！');
	     	return;
	      }else if(dataUrl != "" && dataUrl != "http://www.kmb2b.com"){
	    	 return; 
	      }else{
	     	$("#ajaxSubmit").ajaxSubmit({  
		         type:"post",  //提交方式  
		         url:"/cms/shopsDecorateCustomAction_addNavigationCustomData.do", //请求url  
		         success:function(data){ //提交成功的回调函数  
		         	if(isNaN(data)){
		         		alert("保存数据成功！");
		         		var id = $("#windowId_id").val();
	        			var shopI = $("#shopI").val();
		         		window.location.href="/cms/shopsDecorateCustomAction_queryNavigationPageList.do?windowId="+id+"&shopI="+shopI
		         	}else{
		         		alert("输入的栏目标签不合法！");
		         	} 
		         },
		         error:function(){  
		     		alert('保存数据失败！');  
		     	}   
		     }); 
	     }
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
  	function blurs(v){
  		var $v = $(v);
        var dataName = $v.val();
        arr.forEach(function(i){
        	if(decodeURIComponent(i).trim()==dataName.trim()){
        		alert("导航名称已存在，不可重复！");
        		var timer_alert = setTimeout(function() {
     				messageCloseThis();
     			}, 2000);
      			v.focus();
        	}
        })
  	}
  </script>
  <body>
    <!--添加导航-->
	<div class="ui-dialog-z" style="width: 650px;">
		<s:form id="ajaxSubmit" namespace="/shop" enctype="multipart/form-data">
			<input type="hidden" id="windowId_id" name="windowId" value="<s:property value='windowId' />">
			<input type="hidden" id="shopI" name="shopI" value="<s:property value='shopI' />">
			<input type="hidden" id="windowId" name="cmsWindowDatas[0].windowId" value="<s:property value='windowId' />">
			<input type="hidden" name="cmsWindowDatas[0].user_defined_type" value="<s:property value='0' />">
			<div class="ui-dialog-z-header">
				<h4>添加导航</h4>
			</div>
			<div data-role="content" class="ui-dialog-z-content" >
				<div class="ui-tipbox-z-content">
					<div class="ui-tipbox ui-tipbox-success ui-tipbox-white">
						<fieldset>
							<div class="ui-form-item">
								<label class="ui-label" for="">导航名称</label>
								<input class="lengs" type="text" name="cmsWindowDatas[0].user_defined_name"  class="ui-input ui-input280" onblur="blurs(this);">
								最长可输6个汉字
							</div>
							<div class="ui-form-item">
								<label class="ui-label" for="">链接地址</label>
								<input type="text" id="definedUrl" name="cmsWindowDatas[0].user_defined_url" placeholder="请输入全路径(http://...)" class="ui-input ui-input280">
								
							</div>
						</fieldset>
					</div>
					<div class="ui-dialog-z-footer">
						<input class="btn ui-btn-save ajaxBut" type="button" value="保存" />
						<input id='close' class="btn ui-btn-cancel  close" type="button" value="取消" />
					</div>
				</div>
			</div>
		</s:form>
	</div>
  </body>
</html>
