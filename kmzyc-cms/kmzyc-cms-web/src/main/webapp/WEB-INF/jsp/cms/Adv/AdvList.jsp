<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>广告位管理</title>
	    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="/etc/js/autocomplete2/jquery.autocomplete.css" />
		
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		 <script src="/etc/js/dialog.js"></script>
		  <Script src="/etc/js/97dater/WdatePicker.js"></Script> 
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
		
		<script type="text/javascript"  src="/etc/js/autocomplete/jquery.mockjax.js"></script>
		
		<script type='text/javascript' src='/etc/js/autocomplete2/jquery.autocomplete.js'></script>

	</head>
	<body >
		<!-- 导航栏 -->
	<script>
	var pageForm;
	$(document).ready(function(){
    		var checks = "";
    		checks = $("#checkeds").val();
    		if(checks!=""){
        		var checkboxs = document.getElementsByName("levelId");
        		var myarr = new Array();
    			myarr = checks.split(','); 
        		for(var i = 0; i<checkboxs.length; i++){
            		for(var j = 0; j<myarr.length; j++){
                		if(checkboxs[i].value==myarr[j]){
                			checkboxs[i].checked = true;
                			break;
                    	}
                	}
            	}
        	}
        	pageForm= window.document.getElementById("ADV_queryPageList");
        	
        	
        	
        	
        	
        	var adcolumnArray;
        	$.ajax({
        		async:'false',
        		url:'Adv_Add_ajax.action',
        		success:function(data){
        		//   data=62:xxx;
        			adcolumnArray = $.map(data, function (value,key) { 
        				return { value:value, data:key };
        				
        			});
        		//	product_add_suppliersArray = suppliersArray;
        			$("#adcolumn").autocomplete(adcolumnArray, {
						minChars: 0,
						max:50,
						formatItem: function(row, i, max, term) {
							return row.value;
						}
					});
        			
        			var hid=$("#adcolumnId").val();
        			if(hid != null){
        				var thecolumnName = "";
        				 for(var j=0;j<adcolumnArray.length ; j++){
        					if( hid == adcolumnArray[j].data ){
        						thecolumnName = adcolumnArray[j].value;
        					}
        				 }
        				 $("#adcolumn").val(thecolumnName);
        			}
        		},
        		error: function(XMLHttpRequest, textStatus, errorThrown){
        			//alert("aaa:"+XMLHttpRequest+"...."+textStatus+"...."+errorThrown);
        			},
        		dataType:'json'
        	});
        	
        	function bindId(){
        		var adcolumnId = 0;
        		var name = $("#adcolumn").val();
        		for(var i=0;i<adcolumnArray.length ; i++){
        			if( name == adcolumnArray[i].value ){
        				adcolumnId = adcolumnArray[i].data;
        			}
        		}
        		$("#adcolumnId").val(adcolumnId);
        	}

        	$(".queryBtn").bind("click",function(){
        		bindId();
        	});
        	
        	
	   });
      
	
	  /** 删除广告位信息  */
    function deleteSelected(id){
        var obj = document.getElementsByName(id);
                var count = 0;
                var obj_cehcked = null;
                // 遍历所有用户，找出被选中的用户
                for (var i = 0; i < obj.length; i++) {
                    if (obj[i].checked) {
                        count++;
                        obj_cehcked = obj[i];
                    }
                }
                 if (count == 0) {
                        alert("请选择要删除的数据。");
                        return false;
                 }else if(confirm('是否确认删除?')==true){ 
                          document.userLevelForm.action="/cms/Adv_All_Delete.action";
                          document.userLevelForm.submit();
                 }
    }
    
    /***/
	//页面批量发布
	function allParse(){
		var checks=$("#checkeds").val();
		if(checks=='')
		{
			var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>请选择!";
        messageDialog("消息提示","text:"+msg ,"300px","102px","text");  
			return ;
		}
		var ok=confirm("确定发布所选吗？！");
		if(ok==false)
		{
			return ;
		}
		else
		{
			window.location.href="/cms/Adv_parseAll.action?checkeds="+checks;
		}
	}
	 	
    
      /** 多选发布广告位信息  
    function allParse(id){
        var obj = document.getElementsByName(id);
                var count = 0;
                var obj_cehcked = null;
                // 遍历所有用户，找出被选中的用户
                for (var i = 0; i < obj.length; i++) {
                    if (obj[i].checked) {
                        count++;
                        obj_cehcked = obj[i];
                    }
                }
                 if (count == 0) {
                        alert("请选择要发布的数据。");
                        return false;
                 }else if(confirm('是否确认发布?')==true){ 
                          document.userLevelForm.action="/cms/Adv_allParse.action";
                          document.userLevelForm.submit();
                 }
    }
	 */ 
    
	function addgoto(){
	  //  if(id==0){
	  //   location.href="/cms/Adv_addgoto.action?adminType=0";
	  //  }else{
       //location.href="/cms/Adv_addgoto.action";
       pageForm.action="/cms/Adv_addgoto.action"; 
 	   pageForm.submit();
    //}
    }
	
    /** 进入修改广告位页面  **/
    function editUserLevel(id){
    pageForm.action="/cms/Adv_Byid.action?Advid="+id; 
 	    pageForm.submit();
    }
    function adminEditUserLevel(id){
    	//var pageNo=$("#pageNo").val();
        //location.href="/cms/Adv_Byid.action?Advid="+id+"&pageNo="+pageNo;
        pageForm.action="/cms/Adv_Byid.action?Advid="+id; 
 	    pageForm.submit();
    }
    
    /** 单条删除客户等级信息  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除?")==true){
           location.href="/cms/Adv_Delete.action?Advid="+id;
         }
    }
	
	 function advPublish(advId){
	     location.href="/cms/Adv_parse.action?adminType=0&cmsAdv.advId="+advId;
	}
	 function adminAdvPublish(advId){
	     location.href="/cms/Adv_parse.action?cmsAdv.advId="+advId;
	}
	 function advPreview(advId){
	 	window.open("/cms/Adv_avdPreview.action?cmsAdv.advId="+advId);
	 }
	 
	</script>	
		
		

<style type="text/css">
.listTitle {
	height: 40px;
	line-height:40px;
	background: #c7e3f1;
	
	vertical-align: middle;
	font-size:13;
	color: #222222;
	margin-bottom: 20px;
}
.listTitle span {
    padding-left: 20px;
    height: 40px;
    vertical-align: middle;
    margin: 0px;
}
.listTitle span img{
   vertical-align: middle;
}
.content_table{
	background-color: #f5f5f5;
    border: 1px solid #e3e3e3;
    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.05);
    -moz-box-shadow: inset 0 1px 1px rgba(0,0,0,0.05);
    box-shadow: inset 0 1px 1px rgba(0,0,0,0.05);
    overflow: hidden;
    
    padding: 6px;

	
	}
</style>
<!-- 导航栏 -->
<div width="100%" class="listTitle"  height="40">
    <span>
	<img src="/etc/images/icon_02.png"  />&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;基础功能&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;广告位管理
		
		
	  </span>
</div>
		<div  style="height:90%;overflow-y:scroll; " >
		        
<s:form id="ADV_queryPageList" name="userLevelForm" onsubmit="return checkAllTextValid(this)" action="/cms/Adv_queryAdv.action" method="post">
			<s:token></s:token>
			<!-- hidden -->
			<input type="hidden" id="pageNo_keyWords" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
			<input type="hidden" id="pageSize_keyWords" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
			<!-- 查询条件 -->
		    <table width="98%" align="center" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>
				
					<td  valign="middle" colspan="2">
						<input name="adminType" type="hidden"   value="<s:property value='adminType'/>"/>     
						
					</td>
				</tr>
				<tr width="60%">
					<td >
						广告名称:<input name="keyWords.name_keyword" type="text"  
							value="<s:property value="keyWords.name_keyword"/>">
							
							
					</td >
					<td >
						广告位置:<input type="text" id="adcolumn" name="theAdcolumnname" value="<s:property value="cmsAdcolumnname"/>">
	        				  <input type="hidden" name="keyWords.adcolumnId_keyword" id="adcolumnId" value="<s:property value="keyWords.adcolumnId_keyword"/>"/>
						
						<!--  <SELECT name="keyWords.adcolumnId_keyword" id="adcolumnId">
							        <option value="1">**</option>
							       </SELECT>-->
					</td>
					<td >
					   <input  type="hidden" id="HadcolumnId" value="<s:property value="keyWords.adcolumnId_keyword"/>"/>
						发布状态:<SELECT name="keyWords.status_keyword" id="status">
						            <option <s:if test="keyWords.status_keyword==null">selected='selected'</s:if> value="">全部</option>
							        <option <s:if test="keyWords.status_keyword==0">selected='selected'</s:if> value="0">未发布</option>
							        <option <s:if test="keyWords.status_keyword==1">selected='selected'</s:if> value="1">已发布</option>
							         <option <s:if test="keyWords.status_keyword==2">selected='selected'</s:if> value="2">已修改</option>
							       </SELECT>
					</td>
					<td align="left" >
						开始时间:<input name="keyWords.beginTime_keyword" type="text" readonly value="<s:date name="keyWords.beginTime_keyword" format="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
					</td>
					<td>
						结束时间:<input name="keyWords.endTime_keyword" type="text" readonly value="<s:date name="keyWords.endTime_keyword" format="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
					</td>
					<td align="right" >
						<INPUT TYPE="submit" class="queryBtn" value="">
                        <input class="addBtn" TYPE="button" value=""   onclick="addgoto();">
						
						  <s:if test="adminType!=0">
						  	<input class="delBtn" type="button" value="" onClick="deleteSelected('levelId');">
						  </s:if>
							<input  type="button" value="多选发布" class="btn-custom" id="allPublishAdv" onClick="allParse();">
							
							<input type="hidden" id="checkeds" name="checkeds" value="<s:property value='checkeds'/>"/>
					</td>
				</tr>
			</table>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1">
				<tr>
					<th width="5%">
						<input type='checkbox' name='level'  onclick="checkAllpop(this,'levelId')">
					</th>
					<th>
						编号
					</th>
					<th>
						广告名称
					</th>
					<th>
						广告位置
						
					</th>
					<!--
					<th>
						单位名称
					</th>
					-->
						<th>
						开始时间 
					</th>
					<th width="80">
						结束时间
					</th>
					<th>发布状态<!--0.有效1.无效-->
					</th>
					<th>点击数
					</th>
					<th>
						联系人
					</th>
					<th >
					         修改日期
					</th>
					<th >
					         修改人
					</th>
					<th width="80">
					      操作
					</th>
				</tr>
				<s:iterator  value="page.dataList">
					<tr>
						<td width="5%">
							<input type="checkbox" name="levelId"
								value='<s:property value="advId"/>' onClick="checkpromotionId(this);">
								
						</td>
						<td width="5%">
						<s:property value="advId"/>
						
						</td>
						<td width="15%">
							
							<s:property value="name"/>
						</td>
						<td width="15%">
				
							<s:property value="adname"/>
						</td>
						
						<!--
						<td>
						    <s:property value="unitName"/>
						</td>
						-->
					
						
						<td width="10%"><s:date name="beginTime" format="yyyy-MM-dd HH:mm:ss"/>
					     	
						</td>
						<td width="10%">
								<s:date name="endTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
					
						<td width="5%">
						   
						    <s:if test="status==1">已发布</s:if>
						    <s:if test="status==0">未发布</s:if>
						    <s:if test="status==2"><font color="red">已修改</font></s:if>
						</td>
							<td width="5%">
						    <s:property value="clickCount"/>
						  
						</td>
						<td width="5%">
						    <s:property value="linkman"/>
						</td><td width="12%"><s:date name="modifyDate" format="yyyy-MM-dd HH:mm:ss"/>
							
						</td>
						<td>
							<s:property value="sysUserMap[modified]"/>
						</td>
						<td>

						
							<img title="预览" style="cursor: pointer;" src="/etc/images/icon_preview.png"  onclick="advPreview(<s:property value="advId"/>)" />
							
						      <img title="发布" style="cursor: pointer;" src="/etc/images/icon_publish.png" 
						      <s:if test="adminType==0"> onclick="advPublish(<s:property value="advId"/>)"</s:if> 
						      <s:else>onclick="adminAdvPublish(<s:property value="advId"/>)"</s:else> />
							<!--  
							<s:if test="isPublish==0">
								<s:if test="status!=1"> 
							    </s:if>
                            </s:if>
                            -->
						    <img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"   <s:if test="adminType==0"> onclick="editUserLevel(<s:property value="advId"/>)"</s:if><s:else> onclick="adminEditUserLevel(<s:property value="advId"/>)"</s:else> />
						   <s:if test="adminType!=0">
						    <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value="advId"/>)" />
						   </s:if>
						</td>
					</tr>
				
				</s:iterator > 
				
					
				
			</table>
			
			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>

						<s:set name="form_name" value="'userLevelForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
		</s:form>




	<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		

<!-- 消息提示 -->
<div width="100%">

		 
	              
</div>

	
		</div>
		
		<script>
    $().ready(function(){
	    var timer_alert = setTimeout(function() {
			messageCloseThis();
		}, 2000);
 

	
	
	/*
	 var hid=$("#HadcolumnId").val();
	  $.ajax({
	        url:'Adv_Add_ajax.action',
	        type:'post',
	        dataType:'json',
	        success:function (d) {
	       var li='<option value="0" selected="selected">全部</option>';
	         $.each(d.advlist, function (index, item) {
	         
	           if(hid==item.adcolumnId){
	         li+="<option value="+item.adcolumnId+" selected='selected'  >"+item.name+"</option>";
	         }else{
	         li+="<option value="+item.adcolumnId+"   >"+item.name+"</option>";
	        }
	        });
	        $("#adcolumnId").html(li);
	        }
	 });*/
 });
 </script>
		
	</body>
</html>

