<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    
    <title>公告</title>
    <meta name="keywords" content="店铺模版">
	<meta name="description" content="店铺模版">
	<link rel="stylesheet" href="http://jscss.kmb2b.com/resshop/style/default/common.css">
	<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
	<script type="text/javascript"  src="/etc/js/jquery.form.js"></script>
	<script src="/etc/js/dialog.js"></script>

  </head>
  <script type="text/javascript">
  
  	$(function(){
	  $(".checkedsone").click(function (){
	  	var t = $(this);
	  	if(t.attr("checked")){
	  		$("#checkedone").val("0");
	  	}else{
	  		$("#checkedone").val("1");
	  	}
	  });
	  
	  //限定输入长度
	  $('.lengs').keyup(function(){
	  	var len = $(this).val().length;
	  	if(len>200){
	  		$(this).val($(this).val().substring(0,200));
	  	}
	  });
	  //限定输入长度
	  $('.ui-input').keyup(function(){
	  	var len = $(this).val().length;
	  	if(len>10){
	  		$(this).val($(this).val().substring(0,10));
	  	}
	  });
	  
	  $(".ajaxBut").click(function (){
	  	  var dataNameIs=$(".ui-input").val();
	  	  if ($('#checkedone').val()==0) {
    		if($.trim(dataNameIs) == ""){ 
		     	alert('栏目标题不能为空！');
		     	return;
	      	}
		  }
		  if(dataNameIs.length>10){
	      	alert('栏目标题超过10个字！');
	     	return;
	      }else{
	     	$("#ajaxSubmit").ajaxSubmit({  
		         type:"post",  //提交方式  
		         url:"/cms/shopsDecorateCustomAction_saveNoticesStoreCustomData.do", //请求url  
		         success:function(data){ //提交成功的回调函数  
		         	if(isNaN(data)){
		         		alert("保存数据成功！");
		         		try {
				     		document.domain = 'kmb2b.com';
				            window.frameElement.trigger('close');
				        } catch (error) {
				            window.console && console.log('error',error);
				        }
				        //保存数据成功刷新页面
		         		//var id = $("#windowId_id").val();
	        			//var shopI = $("#shopI").val();
		         		//window.location.href="/cms/shopsDecorateCustomAction_queryNoticesPageList.do?windowId="+id+"&shopI="+shopI;
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
  </script>
  <body>
	<!--公告-->
	<div class="ui-dialog-z" style=" width: 650px;">
		<s:form id="ajaxSubmit" namespace="/shop" enctype="multipart/form-data" >
			<input type="hidden" id="windowId_id" value="<s:property value='windowId' />">
			<input type="hidden" id="shopI" name="shopI" value="<s:property value='shopI' />">
			<div class="ui-dialog-z-header">
				<h4>公告</h4>
			</div>
			<div data-role="content" class="ui-dialog-z-content" >
				<div class="ui-tipbox-z-content">
					<div class="ui-tipbox ui-tipbox-success ui-tipbox-white">
						<fieldset>
							<s:if test="page.recordCount < 1">
								<div class="ui-form-item">
									<label class="ui-label" for="">栏目标题</label>
									<div class="ui-form-explain">
										<input type="text" placeholder="" class="ui-input" name="cmsWindowDatas[0].user_defined_name" value='<s:property value="dataName" />'>
										<input type="hidden" name="cmsWindowDatas[0].user_defined_type" value='<s:property value="0" />'>
										<input type="hidden" id="checkedone" name="cmsWindowDatas[0].user_defined_url" value='0'>
										<input type="hidden" name="cmsWindowDatas[0].windowDataId" value="<s:property value='windowDataId' />">
										<input type="hidden" name="windowId" value="<s:property value='windowId' />">
										<span class="ui-tpis">
											<input type="checkbox" class="checkedsone"  checked="checked"  value="0">
											显示栏目标题
										</span>
									</div>
								</div>
								<div class="ui-form-item">
									<label class="ui-label" for="">内容</label>
									<div class="ui-form-explain">
										<textarea class="ui-textarea ui-textarea500 lengs" name="cmsWindowDatas[0].remark"><s:property value="remark" /></textarea>
										<p class="ui-tpis"><span class="ui-form-required">*</span>最多可填写200个汉字</p>
									</div>
								</div>
							</s:if>
							<s:else>
								<s:iterator value="page.dataList">
									<s:if test="sort==1">
										<div class="ui-form-item">
											<label class="ui-label" for="">栏目标题</label>
											<div class="ui-form-explain">
												<input type="text" placeholder="" class="ui-input" name="cmsWindowDatas[0].user_defined_name" value='<s:property value="dataName" />'>
												<input type="hidden" id="checkedone" name="cmsWindowDatas[0].user_defined_url" value='<s:property value="user_defined_url" />'>
												<input type="hidden" name="cmsWindowDatas[0].sort" value='<s:property value="sort" />'>
												<input type="hidden" name="cmsWindowDatas[0].windowDataId" value="<s:property value='windowDataId' />">
												<input type="hidden" name="cmsWindowDatas[0].user_defined_type" value='<s:property value="0" />'>
												<input type="hidden" name="windowId" value="<s:property value='windowId' />">
												<span class="ui-tpis">
													<s:if test="user_defined_url == 0 || user_defined_url == null">
														<input type="checkbox" class="checkedsone"  checked="checked"  value="0">
													</s:if>
													<s:if test="user_defined_url==1">
														<input type="checkbox" class="checkedsone" value="1">
													</s:if>
													显示栏目标题
												</span>
											</div>
										</div>
										<div class="ui-form-item">
											<label class="ui-label" for="">内容</label>
											<div class="ui-form-explain">
												<textarea class="ui-textarea ui-textarea500 lengs" name="cmsWindowDatas[0].remark"><s:property value="remark" /></textarea>
												<p class="ui-tpis"><span class="ui-form-required">*</span>最多可填写200个汉字</p>
											</div>
										</div>
									</s:if>
								</s:iterator>
							</s:else>
						</fieldset>
					</div>
					<div class="ui-dialog-z-footer">
						<input class="btn ui-btn-save ajaxBut" type="button" value="保存" />
						<input id='close' class="btn ui-btn-cancel close" type="button" value="取消" />
					</div>
				</div>
			</div>
		</s:form>
	</div>
  </body>
</html>
