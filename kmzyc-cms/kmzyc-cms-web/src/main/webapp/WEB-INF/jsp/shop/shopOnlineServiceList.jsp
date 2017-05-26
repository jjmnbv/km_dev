<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>

		<title>在线客服</title>
		<meta name="keywords" content="店铺模版">
		<meta name="description" content="店铺模版">
		<link rel="stylesheet" href="http://jscss.kmb2b.com/resshop/style/default/common.css">
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
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
		  })
		  //时间赋值
		  $("#list_table").on("change",".optioneone1",function(){
  				var t=$(this);
  				//删除tr  t.parent().parent().remove();
  				//获取父元素的值t.parent().attr("value")
  				//t.parent().attr("value",t.val());//给父元素赋值
  				t.parent().attr("value",t.val()+'至'+t.parent().find('.optioneone2').val());
  				t.parent().find('.zhi').attr("value",t.val()+'至'+t.parent().find('.optioneone2').val());
  			});
  			
		  $("#list_table").on("change",".optioneone2",function(){
  				var t=$(this);
  				t.parent().attr("value",t.parent().find('.optioneone1').val()+'至'+t.val());
  				t.parent().find('.zhi').attr("value",t.parent().find('.optioneone1').val()+'至'+t.val());
  				
  			});
  		
  		//点击删除tr
		  $("table").on("click", ".deletes", function(){ 
		    var t = $(this);
		            if(t.attr('data-id') == 'str'){
		            	t.parents("tr").eq(0).remove();
		            	sorts();
		            }else{
	        			var shopI = $("#shopI").val();
			 			$.ajax({     
				            //要用post方式      
				            type: "Post",     
				            //方法所在页面和方法名      
				            url: "/cms/shopsDecorateCustomAction_deleteOnlineServiceCustomData.do?shopI="+shopI,     
				            data: "windowDataId="+t.attr('data-id'),  
				            success: function(data) {          
				                alert('删除存在数据成功');   
				                t.parents("tr").eq(0).remove(); 
				                sorts(); 
				            },     
				            error: function(err) {     
				                alert('删除存在数据失败');     
			            	}     
			        	});
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
				         url:"/cms/shopsDecorateCustomAction_saveOnlineServiceStoreCustomData.do", //请求url  
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
				         		//window.location.href="/cms/shopsDecorateCustomAction_queryOnlineServicePageList.do?windowId="+id+"&shopI="+shopI
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
   
  		function sorts(){
	  		var	a=$("#list_table tr").length;
	  		$("#list_table tr").each(function(i){
	  		     $(this).find('.sortone').attr('name','cmsWindowDatas['+(a-i)+'].user_defined_name');
	  		     $(this).find('.sorttwo').attr('name','cmsWindowDatas['+(a-i)+'].user_defined_url');
	  		});
  		}
  		
		// 添加一行
		var _len=0;
		function addRowsFun() {
			 _len = $("#list_table tr").length+1;
			 var str = 'del';
			$("#list_table").append(
							"<tr>"
							+"<td>"
							+"<input class='zhi sortone' type='hidden' name='cmsWindowDatas["+_len+"].user_defined_name' value='周一至周一'>"
							+"<select class='optioneone1'>"
							+"<s:iterator value="{'周一','周二','周三','周四','周五','周六','周日'}" id='number'> "
							+"<option value='<s:property value='#number'/>'><s:property value='#number'/></option>"
							+"</s:iterator> "
							+"</select>"
							+"<span> 至 </span>"
							+"<select class='optioneone2'>"
							+"<s:iterator value="{'周一','周二','周三','周四','周五','周六','周日'}" id='number'> "
							+"<option value='<s:property value='#number'/>'><s:property value='#number'/></option>"
							+"</s:iterator> "
							+"</select>"
							+"</td>"
							+"<td>"
							+"<input class='zhi sorttwo' type='hidden' name='cmsWindowDatas["+_len+"].user_defined_url' value='00:00至00:00'>"
							+"<select class='optioneone1'>"
							+"<s:iterator value="{'00:00','00:30','01:00','01:30','02:00','02:30','03:00','03:30','04:00','04:30','05:00','05:30',
												  '06:00','06:30','07:00','07:30','08:00','08:30','09:00','09:30','10:00','10:30','11:00','11:30',
												  '12:00','12:30','13:00','13:30','14:00','14:30','15:00','15:30','16:00','16:30','17:00','17:30',
												  '18:00','18:30','19:00','19:30','20:00','20:30','21:00','21:30','22:00','22:30','23:00','23:30'}" id='number'> "
							+"<option value='<s:property value='#number'/>'><s:property value='#number'/></option>"
							+"</s:iterator> "
							+"</select>"
							+"<span> 至 </span>"
							+"<select class='optioneone2'>"
							+"<s:iterator value="{'00:00','00:30','01:00','01:30','02:00','02:30','03:00','03:30','04:00','04:30','05:00','05:30',
												  '06:00','06:30','07:00','07:30','08:00','08:30','09:00','09:30','10:00','10:30','11:00','11:30',
												  '12:00','12:30','13:00','13:30','14:00','14:30','15:00','15:30','16:00','16:30','17:00','17:30',
												  '18:00','18:30','19:00','19:30','20:00','20:30','21:00','21:30','22:00','22:30','23:00','23:30'}" id='number'> "
							+"<option value='<s:property value='#number'/>'><s:property value='#number'/></option>"
							+"</s:iterator> "
							+"</select>"
							+"</td>"
							+"<td><span class='ui-table-edit'><a data-id='str' class='deletes' href='javascript:void(0);'>删除</a></span></td>"
							+"</tr>"
			);
			sorts();
		}
	
		
	
	
	
		
  </script>
	<body>
		<!--在线客服-->
		<div class="ui-dialog-z" style="width: 680px;">
			<s:form id="ajaxSubmit" namespace="/shop" enctype="multipart/form-data">
			<input type="hidden" id="windowId_id" value="<s:property value='windowId' />">
			<input type="hidden" id="shopI" name="shopI" value="<s:property value='shopI' />">
				<div class="ui-dialog-z-header">
					<h4>
						在线客服
					</h4>
				</div>
				<div data-role="content" class="ui-dialog-z-content">
					<div class="ui-tipbox-z-content">
						<div class="ui-tipbox ui-tipbox-success ui-tipbox-white">
							<fieldset>
								<div class="ui-form-item">
									<label class="ui-label" for="">
										栏目标题
									</label>
									<div class="ui-form-explain">
										<s:if test="page.recordCount == 0">
											<input type="hidden" name="windowId"
												value="<s:property value='windowId' />">
											<input type="text" name="cmsWindowDatas[0].user_defined_name"
												placeholder="" class="ui-input"
												value='<s:property value="dataName" />'>
											<input type="hidden" id='checkedone'
												name="cmsWindowDatas[0].user_defined_type"
												value='<s:property value="0" />'>
											<span class="ui-tpis"> <input class='checkedsone'
													type="checkbox" class="checkedsone" checked="checked" value="0">
												显示栏目标题 </span>
										</s:if>
										<s:else>
											<s:iterator value="page.dataList" status="index">
												<s:if test="sort<2">
													<input type="hidden" name="windowId"
														value="<s:property value='windowId' />">
													<input type="text"
														name="cmsWindowDatas[<s:property value="page.recordCount-#index.index-1" />].user_defined_name"
														placeholder="" class="ui-input"
														value='<s:property value="dataName" />'>
													<input type="hidden" id='checkedone'
														name="cmsWindowDatas[<s:property value="page.recordCount-#index.index-1" />].user_defined_type"
														value='<s:property value="user_defined_type" />'>
													<span class="ui-tpis"> <s:if
															test="user_defined_type == 0 || user_defined_type == null">
															<input class='checkedsone' type="checkbox"
																class="checkedsone" checked="checked" value="0">
														</s:if> <s:if test="user_defined_type==1">
															<input class='checkedsone' type="checkbox"
																 class="checkedsone" value="1">
														</s:if> 显示栏目标题 </span>
												</s:if>
											</s:iterator>
										</s:else>
									</div>
								</div>
								<div class="ui-form-item">
									<div class="ui-manage-title">
										<a href="javascript:addRowsFun()">+ 增加工作时间</a>
									</div>
									<table class="ui-table">
										<thead>
											<tr>
												<th>
													日期
												</th>
												<th>
													时间
												</th>
												<th>
													操作
												</th>
											</tr>
										</thead>
										<tbody id="list_table">
											<s:iterator value="page.dataList" status="index">
												<s:if test="sort>1">
													<tr>
														<td>
															<input class='zhi sortone' type='hidden'
																name='cmsWindowDatas[<s:property value="page.recordCount-#index.index-1" />].user_defined_name'
																value='<s:property value="user_defined_name.substring(0,5)" />'>
															<select class='optioneone1'>
																<s:iterator value="{'周一','周二','周三','周四','周五','周六','周日'}"
																	id='number'>
																	<s:if test="dataName.substring(0,2)==#number">
																		<option selected="selected"
																			value="<s:property value='#number'/>">
																			<s:property value='#number' />
																		</option>
																	</s:if>
																	<s:else>
																		<option value="<s:property value='#number'/>">
																			<s:property value='#number' />
																		</option>
																	</s:else>
																</s:iterator>
															</select>
															<span><s:property value='dataName.substring(2,3)' />
															</span>
															<select class='optioneone2'>
																<s:iterator value="{'周一','周二','周三','周四','周五','周六','周日'}"
																	id='number'>
																	<s:if test="dataName.substring(3,5)==#number">
																		<option selected="selected"
																			value="<s:property value='#number'/>">
																			<s:property value='#number' />
																		</option>
																	</s:if>
																	<s:else>
																		<option value="<s:property value='#number'/>">
																			<s:property value='#number' />
																		</option>
																	</s:else>
																</s:iterator>
															</select>
														</td>
														<td>
															<input class='zhi sorttwo' type='hidden'
																name='cmsWindowDatas[<s:property value="page.recordCount-#index.index-1" />].user_defined_url'
																value='<s:property value="user_defined_url.substring(0,11)" />'>
															<select class='optioneone1'>
																<s:iterator
																	value="{'00:00','00:30','01:00','01:30','02:00','02:30','03:00','03:30','04:00','04:30','05:00','05:30',
																				'06:00','06:30','07:00','07:30','08:00','08:30','09:00','09:30','10:00','10:30','11:00','11:30',
																				'12:00','12:30','13:00','13:30','14:00','14:30','15:00','15:30','16:00','16:30','17:00','17:30',
																				'18:00','18:30','19:00','19:30','20:00','20:30','21:00','21:30','22:00','22:30','23:00','23:30'}"
																	id='number'>
																	<s:if test="user_defined_url.substring(0,5)==#number">
																		<option selected="selected"
																			value="<s:property value='#number'/>">
																			<s:property value='#number' />
																		</option>
																	</s:if>
																	<s:else>
																		<option value="<s:property value='#number'/>">
																			<s:property value='#number' />
																		</option>
																	</s:else>
																</s:iterator>
															</select>
															<span><s:property
																	value='user_defined_url.substring(5,6)' />
															</span>
															<select class='optioneone2'>
																<s:iterator
																	value="{'00:00','00:30','01:00','01:30','02:00','02:30','03:00','03:30','04:00','04:30','05:00','05:30',
																				'06:00','06:30','07:00','07:30','08:00','08:30','09:00','09:30','10:00','10:30','11:00','11:30',
																				'12:00','12:30','13:00','13:30','14:00','14:30','15:00','15:30','16:00','16:30','17:00','17:30',
																				'18:00','18:30','19:00','19:30','20:00','20:30','21:00','21:30','22:00','22:30','23:00','23:30'}"
																	id='number'>
																	<s:if test="user_defined_url.substring(6,11)==#number">
																		<option selected="selected"
																			value="<s:property value='#number'/>">
																			<s:property value='#number' />
																		</option>
																	</s:if>
																	<s:else>
																		<option value="<s:property value='#number'/>">
																			<s:property value='#number' />
																		</option>
																	</s:else>
																</s:iterator>
															</select>
														</td>
														<td>
															<span class="ui-table-edit"><a class='deletes'
																data-id="<s:property value='windowDataId' />" href='javascript:void(0);'>删除</a>
															</span>
														</td>
													</tr>
												</s:if>
											</s:iterator>
										</tbody>
									</table>
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
