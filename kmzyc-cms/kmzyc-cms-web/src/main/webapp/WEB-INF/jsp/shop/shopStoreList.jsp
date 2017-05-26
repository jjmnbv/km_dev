<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    
    <title>店铺搜索</title>
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
	  
	  $(".checkedstwo").click(function (){
	  	var t = $(this);
	  	if(t.attr("checked")){
	  		$("#checkedtwo").val("0");
	  	}else{
	  		$("#checkedtwo").val("1");
	  	}
	  });
	  
	  //限定输入长度
//	  $('.lengs').keyup(function(){
//	  	var len = $(this).val().length;
//	  	if(len>5){
//	  		$(this).val($(this).val().substring(0,5));
//	  	}
//	  });
	  
	  
	  $(".ajaxBut").click(function (){
	  	  var dataNameIs=$(".lengsis").val();
	  	  var dataNameIs1=$(".lengsis1").val();
	  	  var dataNameIs2=$(".lengsis2").val();
	  	  var dataNameIs3=$(".lengsis3").val();
	  	  var dataNameIs4=$(".lengsis4").val();
	  	 if ($('#checkedone').val()==0) {
    		if($.trim(dataNameIs) == ""){ 
		     	alert('栏目标题不能为空！');
		     	return;
	      	}
		  }
		  if(dataNameIs.length>5){
	      	alert('栏目标题超过5个字！');
	     	return;
	      }else if(dataNameIs1.length>5){
	      	alert('关键字超过5个字！');
	     	return;
	      }else if(dataNameIs2.length>5){
	      	alert('关键字超过5个字！');
	     	return;
	      }else if(dataNameIs3.length>5){
	      	alert('关键字超过5个字！');
	     	return;
	      }else if(dataNameIs4.length>5){
	      	alert('关键字超过5个字！');
	     	return;
	      }else{
	     	$("#ajaxSubmit").ajaxSubmit({  
		         type:"post",  //提交方式  
		         url:"/cms/shopsDecorateCustomAction_saveShopStoreCustomData.do", //请求url  
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
		         		//window.location.href="/cms/shopsDecorateCustomAction_queryShopPageList.do?windowId="+id+"&shopI="+shopI
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
	<!--店铺搜索-->
	<div class="ui-dialog-z" style=" width: 650px; height:600px">
		<s:form id="ajaxSubmit" namespace="/shop" enctype="multipart/form-data">
		<input type="hidden" id="windowId_id" value="<s:property value='windowId' />">
		<input type="hidden" id="shopI" name="shopI" value="<s:property value='shopI' />">
			<div class="ui-dialog-z-header">
				<h4>店铺搜索</h4>
			</div>
			<div data-role="content" class="ui-dialog-z-content">
				<div class="ui-tipbox-z-content">
					<div class="ui-tipbox ui-tipbox-success ui-tipbox-white">
						<fieldset>
							<div class="ui-form-item">
								<label class="ui-label" for="">栏目标题</label>
								<div class="ui-form-explain">
									<s:if test="page.recordCount < 1">
										<input type="text" class="lengs lengsis ui-input" name="cmsWindowDatas[0].user_defined_name"  value='<s:property value="dataName" />'>
										<input type="hidden" name="windowId" value="<s:property value='windowId' />">
										<input type="hidden" id="checkedone" name="cmsWindowDatas[0].user_defined_url" value='<s:property value="0" />'>
										<input type="hidden" name="cmsWindowDatas[0].user_defined_type" value='<s:property value="0" />'>
										<input type="hidden" name="cmsWindowDatas[0].windowId" value='<s:property value="windowId" />'>
										<span class="ui-tpis">
											<input type="checkbox" checked="checked" class="checkedsone" value="0">
											显示栏目标题
										</span>
									</s:if>
									<s:else>
										<s:iterator value="page.dataList">
											<s:if test="sort==1">
												<input type="text" class="lengs lengsis ui-input" name="cmsWindowDatas[0].user_defined_name"  value='<s:property value="dataName" />'>
												<input type="hidden" name="cmsWindowDatas[0].windowDataId" value="<s:property value='windowDataId' />">
												<input type="hidden" name="windowId" value="<s:property value='windowId' />">
												<input type="hidden" id="checkedone" name="cmsWindowDatas[0].user_defined_url" value='<s:property value="user_defined_url" />'>
												<span class="ui-tpis">
													<s:if test="user_defined_url == 0 || user_defined_url == null">
														<input type="checkbox" class="checkedsone" checked="checked" value="0">
													</s:if>
													<s:if test="user_defined_url==1">
														<input type="checkbox" class="checkedsone" value="1">
													</s:if>
													显示栏目标题
												</span>
											</s:if>
										</s:iterator>
									</s:else>
								</div>
							</div>
							<div class="ui-form-item">
								<label class="ui-label" for="">预置关键字</label>
								<div class="ui-form-explain">
									<s:if test="page.recordCount < 2">
										<input class="lengs lengsis1 ui-input" type="text" name="cmsWindowDatas[1].user_defined_name"  value='<s:property value="dataName" />'>
										<input type="hidden" name="cmsWindowDatas[1].user_defined_type" value='<s:property value="0" />'>
										<input type="hidden" name="cmsWindowDatas[1].windowId" value='<s:property value="windowId" />'>
										<input type="hidden" name="cmsWindowDatas[1].user_defined_url" value="0">
											<span class="ui-tpis">
												预置在搜索框中，最长5个汉字
											</span>
									</s:if>
									<s:else>
										<s:iterator value="page.dataList">
											<s:if test="sort==2">
												<input class="lengs lengsis1 ui-input" type="text" name="cmsWindowDatas[1].user_defined_name"  value='<s:property value="dataName" />'>
												<input type="hidden" name="cmsWindowDatas[1].windowDataId" value="<s:property value='windowDataId' />">
												<input type="hidden" name="cmsWindowDatas[1].user_defined_url" value="0">
													<span class="ui-tpis">
														预置在搜索框中，最长5个汉字
													</span>
											</s:if>
										</s:iterator>
									</s:else>
								</div>
							</div>
							<div class="ui-form-item">
								<label class="ui-label" for="">推荐关键字</label>
								<div class="ui-form-explain">
									<s:if test="page.recordCount < 3">
										<input class="lengs lengsis2 ui-input" type="text" name="cmsWindowDatas[2].user_defined_name"  value='<s:property value="dataName" />'>
										<input type="hidden" name="cmsWindowDatas[2].user_defined_type" value='<s:property value="0" />'>
										<input type="hidden" name="cmsWindowDatas[2].windowId" value='<s:property value="windowId" />'>
										<input type="hidden" name="cmsWindowDatas[2].user_defined_url" value="0">
									</s:if>
									<s:else>
										<s:iterator value="page.dataList">
											<s:if test="sort==3">
												<input class="lengs lengsis2 ui-input" type="text" name="cmsWindowDatas[2].user_defined_name"  value='<s:property value="dataName" />'>
												<input type="hidden" name="cmsWindowDatas[2].windowDataId" value="<s:property value='windowDataId' />">
												<input type="hidden" name="cmsWindowDatas[2].user_defined_url" value="0">
											</s:if>
										</s:iterator>
									</s:else>
									<s:if test="page.recordCount < 4">
										<input class="lengs lengsis3 ui-input" type="text" name="cmsWindowDatas[3].user_defined_name"  value='<s:property value="dataName" />'>
										<input type="hidden" name="cmsWindowDatas[3].user_defined_type" value='<s:property value="0" />'>
										<input type="hidden" name="cmsWindowDatas[3].windowId" value='<s:property value="windowId" />'>
										<input type="hidden" name="cmsWindowDatas[3].user_defined_url" value="0">
									</s:if>
									<s:else>
										<s:iterator value="page.dataList">
											<s:if test="sort==4">
												<input class="lengs lengsis3 ui-input" type="text" name="cmsWindowDatas[3].user_defined_name"  value='<s:property value="dataName" />'>
												<input type="hidden" name="cmsWindowDatas[3].windowDataId" value="<s:property value='windowDataId' />">
												<input type="hidden" name="cmsWindowDatas[3].user_defined_url" value="0">
											</s:if>
										</s:iterator>
									</s:else>
									<s:if test="page.recordCount < 5">
										<input class="lengs lengsis4 ui-input" type="text" name="cmsWindowDatas[4].user_defined_name"  value='<s:property value="dataName" />'>
										<input type="hidden" name="cmsWindowDatas[4].user_defined_type" value='<s:property value="0" />'>
										<input type="hidden" name="cmsWindowDatas[4].windowId" value='<s:property value="windowId" />'>
										<input type="hidden" name="cmsWindowDatas[4].user_defined_url" value="0">
									</s:if>
									<s:else>
										<s:iterator value="page.dataList">
											<s:if test="sort==5">
												<input class="lengs lengsis4 ui-input" type="text" name="cmsWindowDatas[4].user_defined_name"  value='<s:property value="dataName" />'>
												<input type="hidden" name="cmsWindowDatas[4].windowDataId" value="<s:property value='windowDataId' />">
												<input type="hidden" name="cmsWindowDatas[4].user_defined_url" value="0">
											</s:if>
										</s:iterator>
									</s:else>
									<p class="ui-tips">推荐在搜索框中,最长5个汉字</p>
								</div>
							</div>
							<div class="ui-form-item">
								<label class="ui-label" for="">是否显示价格</label>
								<div class="ui-form-explain">
									<label>
										<s:if test="page.recordCount < 6">
											<input type="hidden" name="cmsWindowDatas[5].user_defined_name" value="">
											<input type="hidden" id="checkedtwo" name="cmsWindowDatas[5].user_defined_url" value='<s:property value="0" />'>
											<input type="hidden" name="cmsWindowDatas[5].user_defined_type" value='<s:property value="0" />'>
											<input type="hidden" name="cmsWindowDatas[5].windowId" value='<s:property value="windowId" />'>
											<input type="checkbox" checked="checked" value="0" class="ui-checkbox checkedstwo" >
											<span>价格刷选</span>
										</s:if>
										<s:else>
											<s:iterator value="page.dataList">
												<s:if test="sort==6">
													<input type="hidden" name="cmsWindowDatas[5].user_defined_name" value="">
													<input type="hidden" name="cmsWindowDatas[5].windowDataId" value="<s:property value='windowDataId' />">
													<input type="hidden" id="checkedtwo" name="cmsWindowDatas[5].user_defined_url" value='<s:property value="user_defined_url" />'>
													<s:if test="user_defined_url == 0 || user_defined_url == null">
														<input type="checkbox"  checked="checked" value="0" class="ui-checkbox checkedstwo" >
													</s:if>
													<s:if test="user_defined_url == 1">
														<input type="checkbox" value="1" class="ui-checkbox checkedstwo" >
													</s:if>
													<span>价格刷选</span>
												</s:if>
											</s:iterator>
										</s:else>
									</label>
								</div>
							</div>
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
