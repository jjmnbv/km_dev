<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<script type="text/javascript">
$(function(){
	
	//取消
    $('#cancel').bind('click', $.unblockUI);
	
	$("#fareForm").validate({     
	       rules: {
	    	   <s:if test="action=='/app/fareTypesaveAction.action'">
				"fareType.type_id": {required: true,digits:true
					 ,remote:{//校验序号是否已经存在  
		                 url:"/app/fareTypecheckTypeIdAction.action",  
		                 type:"post",  
		                 //dataType:"json",  
		                 data:{  
		                	 type_id:function(){return $("#typeId").val();}  
		                 } 
	                 }
	   			},
	   			</s:if>
				"fareType.name": {required:true
					 ,remote:{//校验名称是否已经存在  
		                 url:"/app/fareTypecheckNameAction.action",  
		                 type:"post",  
		                 //dataType:"json",  
		                 data:{  
		                	 type_id:function(){return $("#oldId").val();},  
		                	 name:function(){return $("#name").val();}
		                 }  
					 }
	   			},
				"fareType.site": {required:true
					 ,remote:{//校验适用站点是否已经存在  
		                 url:"/app/fareTypecheckSiteAction.action",  
		                 type:"post",  
		                 //dataType:"json",  
		                 data:{  
		                	 type_id:function(){return $("#oldId").val();},  
		                	 site:function(){return $("#site").val();}
		                 }  
					 }
	   			},	  	   			
				"fareType.firstHeavyFreight":{required:true
					//,num1:true
					,decimal1:true,max:1000.00
				},
				"fareType.continuedHeavyFreight": {required:true
					//,num1:true
					,decimal1:true,max:1000.00
				},
				"fareType.freePrice":{required:true
					//,num1:true
					,decimal1:true,max:10000000.00
				}
     	   },     
           messages:{   
               "fareType.type_id":{  
                   remote:"序号已经存在！"  
               }
	       	   ,"fareType.name":{  
	               remote:"名称已经存在！"  
	           }
	       	   ,"fareType.site":{  
	               remote:"适用站点已经存在！"  
	           }
           } 
     	   //,success: function(label) {    //当验证成功时在对应的后面添加--验证成功
     	   //    label.addClass("valid");  
     	   //}
	   	   ,submitHandler: function (form) {
	   			$.post(
	   		        	$("#fareForm").attr("action"),
	   		       		$("#fareForm").serialize(),
	   		           		function(result){
	   				            alert(result);
	   	  			            if(result.indexOf("失败")>0){
	   	  			            	history.go(0);
	   	  			            }else{
	   	  			            	location.reload();
	   	  			            }
	   		     			}
	   		    );
           }
	});
	
 });
</script>
<form id="fareForm" action="<s:property value='action'/>">
	<table>
		<caption id="caption"><s:property value="caption"/></caption>
		<tr>
			<th>&nbsp;</th>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<th>序号</th>
			<td>
				<input type="text" id="typeId" name="fareType.type_id" value="<s:property value='fareType.type_id'/>" <s:if test="action=='/app/fareTypeupdateAction.action'">readonly="readonly"</s:if>/>
				<input type="hidden" id="oldId" value="<s:property value='fareType.type_id'/>"/>
			</td>
		</tr>
		<tr>
			<th>适用站点</th>
			<td><input type="text" id="site" name="fareType.site" value="<s:property value='fareType.site'/>"/></td>
		</tr>		
		<tr>
			<th>名称</th>
			<td><input type="text" id="name" name="fareType.name" value="<s:property value='fareType.name'/>"/></td>
		</tr>
		<tr>
			<th>首重重量</th>
			<td>
				<select id="firstHeavy" name="fareType.firstHeavy" value="<s:property value='fareType.firstHeavy'/>" style="width:156px">
					<option value="0.5" label="0.5公斤" <s:if test="fareType.firstHeavy==0.5">selected="selected"</s:if> >0.5公斤</option>
					<option value="1" label="1公斤" <s:if test="fareType.firstHeavy==1">selected="selected"</s:if> >1公斤</option>
					<option value="1.2" label="1.2公斤" <s:if test="fareType.firstHeavy==1.2">selected="selected"</s:if> >1.2公斤</option>
					<option value="2" label="2公斤" <s:if test="fareType.firstHeavy==2">selected="selected"</s:if> >2公斤</option>
					<option value="5" label="5公斤" <s:if test="fareType.firstHeavy==5">selected="selected"</s:if> >5公斤</option>
					<option value="10" label="10公斤" <s:if test="fareType.firstHeavy==10">selected="selected"</s:if> >10公斤</option>
					<option value="20" label="20公斤" <s:if test="fareType.firstHeavy==20">selected="selected"</s:if> >20公斤</option>
					<option value="50" label="50公斤" <s:if test="fareType.firstHeavy==50">selected="selected"</s:if> >50公斤</option>
				</select>
			</td>
		</tr>
		<tr>			
			<th>续重单位</th>
			<td>
				<select id="continuedHeavy" name="fareType.continuedHeavy" value="<s:property value='fareType.continuedHeavy'/>" style="width:156px">
					<option value="0.5" label="0.5公斤" <s:if test="fareType.continuedHeavy==0.5">selected="selected"</s:if> >0.5公斤</option>
					<option value="1" label="1公斤" <s:if test="fareType.continuedHeavy==1">selected="selected"</s:if> >1公斤</option>
					<option value="1.2" label="1.2公斤" <s:if test="fareType.continuedHeavy==1.2">selected="selected"</s:if> >1.2公斤</option>
					<option value="2" label="2公斤" <s:if test="fareType.continuedHeavy==2">selected="selected"</s:if> >2公斤</option>
					<option value="5" label="5公斤" <s:if test="fareType.continuedHeavy==5">selected="selected"</s:if> >5公斤</option>
					<option value="10" label="10公斤" <s:if test="fareType.continuedHeavy==10">selected="selected"</s:if> >10公斤</option>
					<option value="20" label="20公斤" <s:if test="fareType.continuedHeavy==20">selected="selected"</s:if> >20公斤</option>
					<option value="50" label="50公斤" <s:if test="fareType.continuedHeavy==50">selected="selected"</s:if> >50公斤</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>首重费用</th>
			<td><input type="text"  name="fareType.firstHeavyFreight" value="<s:property value='fareType.firstHeavyFreight'/>"/></td>
		</tr>
		<tr>			
			<th>续重费用</th>
			<td><input type="text"  name="fareType.continuedHeavyFreight" value="<s:property value='fareType.continuedHeavyFreight'/>"/></td>
		</tr>
		<tr>
			<th>免运费起价</th>
            <td><input type="text"  id="freePrice" name="fareType.freePrice" value="<s:property value='fareType.freePrice'/>"/></td>
		</tr>
		<tr>
			<th>状态</th>
            <td><input type="radio" value="0" checked="" name="fareType.disabled"/>启用<input type="radio" value="1" name="fareType.disabled"/>关闭</td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<td>
				<input type="submit" id="submit0" class="saveBtn" value=""/>
				&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="backBtn" id="cancel" />
			</td>
		</tr>
	</table>
</form>