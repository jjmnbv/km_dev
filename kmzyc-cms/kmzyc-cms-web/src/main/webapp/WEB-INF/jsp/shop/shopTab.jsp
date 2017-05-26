<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
  <head>
    
    <title>TAB切换页</title>
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
	  		$("#checkedone").val("1");
	  	}else{
	  		$("#checkedone").val("0");
	  	}
	  });
	  
	  
	   $(".ajaxBut").click(function (){
	   	  var isFalse = true;
	  	  $(".ui-form-item").each(function(i){
	  	  var t = $(this);
	  	  	var isTrue = '.ui-input'; 
	  		     if($.trim(t.find(isTrue).val()) == ""){ 
			     	alert('栏目标题不能为空！');
			     	isFalse = false;
			     	return isFalse;
			      }
			      if(t.find(isTrue).val().length>20){
			      	alert('栏目标题超过20个字！');
			     	isFalse = false;
			     	return isFalse;
			      }
	  		});
			if(isFalse){
				$("#ajaxSubmit").ajaxSubmit({  
			         type:"post",  //提交方式  
			         url:"/cms/shopsDecorateCustomAction_saveTabCustomData.do", //请求url  
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
			         		//window.location.href="/cms/shopsDecorateCustomAction_queryTabPageList.do?windowId="+id+"&shopI="+shopI
			         	}else{
			         		alert("输入的栏目标签不合法！");
			         	} 
			         },
			         error:function(){  
			     		alert('保存数据失败！');  
			     	}   
			     }); 
			}else{
				return;
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
	     
	     // 添加一行
	     var _len=0;
		$(".ui-manage-title").click(function (){
			 _len = $(".ui-form-item").length+1;
			 $("#list_div").append("<div class='ui-form-item'>"
			 						+"<label class='ui-label'>栏目标题</label>"
			 						+"<div class='ui-form-explain'>"
			 						+"<input type='text' placeholder='' class='sortone ui-input ' name='cmsWindowDatas["+(_len-1)+"].user_defined_name' value='<s:property value='dataName' />'>"
			 						+"<input type='hidden' class='sortfive' name='cmsWindowDatas["+(_len-1)+"].user_defined_type' value='<s:property value='0' />'>"
			 						+"<input type='hidden' id='checkedone' class='sorttwo' name='cmsWindowDatas["+(_len-1)+"].user_defined_url' value='0'>"
			 						+"<input type='hidden' class='sortfour' name='cmsWindowDatas["+(_len-1)+"].windowDataId' value='<s:property value='windowDataId' />'>"
			 						+"<span class='ui-table-edit'><a data-id='str' class='deletes' href='javascript:void(0);'>删除</a></span>"
			 						+"</div>"
			 						+"</div>");
			 						sorts(); 
		});
		
		//点击删除div
		  $(document).on("click", ".deletes", function(){
		  //console.log('进删除方法块');
		    var t = $(this);
		            if(t.attr('data-id') == 'str'){
		            //console.log('删除div块');
		            	t.parent().parent().parent().eq(0).remove();
		            	sorts();
		            }else{
		            //console.log('进删除数据块');
	        			var shopI = $("#shopI").val();
			 			$.ajax({     
				            //要用post方式      
				            type: "Post",     
				            //方法所在页面和方法名      
				            url: "/cms/shopsDecorateCustomAction_deleteWindowDataIdCustomData.do?shopI="+shopI,     
				            data: "windowDataId="+t.attr('data-id'),  
				            success: function(data) {  
				            //console.log('删除数据成功');        
				                alert('删除存在数据成功');   
				                t.parent().parent().parent().eq(0).remove();
				                sorts(); 
				            },     
				            error: function(err) {     
				                alert('删除存在数据失败');     
			            	}     
			        	});
		            }
				 });
		//删除数据后整理排序		 
		function sorts(){
	  		$(".ui-form-item div").each(function(i){
	  		     $(this).find('.sortone').attr('name','cmsWindowDatas['+(i)+'].user_defined_name');
	  		     $(this).find('.sorttwo').attr('name','cmsWindowDatas['+(i)+'].user_defined_url');
	  		     $(this).find('.sortfour').attr('name','cmsWindowDatas['+(i)+'].windowDataId');
	  		     $(this).find('.sortfive').attr('name','cmsWindowDatas['+(i)+'].user_defined_type');
	  		});
  		}
				 
	 });
	 
      
	 
  </script>
  <body>
	<!--公告-->
	<div class="ui-dialog-z" style=" width: 650px;">
		<s:form id="ajaxSubmit" method="post" namespace="/shop" enctype="multipart/form-data">
			<input type="hidden" id="windowId_id" name="windowId"  value="<s:property value='windowId' />">
			<input type="hidden" id="shopI" name="shopI" value="<s:property value='shopI' />">
			<div class="ui-dialog-z-header">
				<h4>TAB标题</h4>
			</div>
			<div data-role="content" class="ui-dialog-z-content" >
			<span>栏目标题自定义，数量可增减，最多可添加的标题数量与模板相关，建议添加标题数量在3个左右。</span>
				<div class="ui-tipbox-z-content">
					<div class="ui-tipbox ui-tipbox-success ui-tipbox-white">
						<fieldset id="list_div">
							<s:if test="page.recordCount < 1 || page.dataList == null">
								<div class="ui-form-item">
									<label class="ui-label">栏目标题</label>
									<div class="ui-form-explain">
										<input type="text" placeholder="" class="sortone ui-input " name="cmsWindowDatas[0].user_defined_name" value="">
										<input type="hidden" class="sortfive" name="cmsWindowDatas[0].user_defined_type" value="0">
										<input type="hidden" id="checkedone" class="sorttwo" name="cmsWindowDatas[0].user_defined_url" value="0">
										<input type="hidden" class="sortfour" name="cmsWindowDatas[0].windowDataId" value="">
										<span class="ui-table-edit">
											<a data-id="str" class="deletes" href="javascript:void(0);">删除</a>
										</span>
									</div>
								</div>
							</s:if>
							<s:else>
								<s:iterator value="page.dataList" status="index">
									<div class="ui-form-item">
										<label class="ui-label" for="">栏目标题</label>
										<div class="ui-form-explain">
											<input type="text" placeholder="" class="sortone ui-input " name="cmsWindowDatas[<s:property value="#index.index" />].user_defined_name" value='<s:property value="page.dataList.get(page.recordCount-#index.index-1).dataName" />'>
											<input type="hidden" id="checkedone" class="sorttwo" name="cmsWindowDatas[<s:property value="#index.index" />].user_defined_url" value='<s:property value="user_defined_url" />'>
											<input type="hidden" class="sortfour"  name="cmsWindowDatas[<s:property value="#index.index" />].windowDataId" value="<s:property value='windowDataId' />">
											<input type="hidden" class="sortfive"  name="cmsWindowDatas[<s:property value="#index.index" />].user_defined_type" value='<s:property value="0" />'>
											<span class="ui-table-edit">
												<a class="deletes" data-id="<s:property value='windowDataId' />" href="javascript:void(0);">删除</a>
											</span>
										</div>
									</div>
								</s:iterator>
							</s:else>
						</fieldset>
					</div>
					<!-- 新增标题栏目 -->
					<div class="ui-manage-title">
						<a href="javascript:void(0);">+ 新增标题栏目</a>
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
