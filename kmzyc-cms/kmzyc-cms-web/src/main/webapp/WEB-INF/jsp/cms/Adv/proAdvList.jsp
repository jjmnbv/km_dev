<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>广告位管理</title>
	    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		 <script src="/etc/js/dialog.js"></script>
		  <Script src="/etc/js/97dater/WdatePicker.js"></Script> 
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
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

    /** 选择团出窗口数据  **/
    function selectOneAccount(){
      	var checkeds = "";
      	checkeds = $("#checkeds").val();
      	var userId = $("#userId").val();
      	var siteId = $("#siteId").val();
      	if(checkeds == ""){
      		var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>请选择!";
           messageDialog("消息提示","text:"+msg ,"300px","102px","text");
           var timer_alert = setTimeout(function() {
   				messageCloseThis();
   			}, 2000);
           return;
       }
           parent.closeAdv(checkeds,userId,siteId);
    }
    
    /** 单条删除客户等级信息  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
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
	background: #D6F2D9;
	border-bottom: 1px solid #079346;
	vertical-align: middle;
	font-size: 14;
	color: #028043;
	margin:0px;
}
.listTitle span{
 padding-left:20px;
 height:30px;
 line-height:30px;
 vertical-align: middle;
 margin-top:5px;
}
.listTitle span img{
   vertical-align: middle;
}
</style>
<!-- 导航栏 -->

		        
<s:form id="ADV_queryPageList" name="userLevelForm" onsubmit="return checkAllTextValid(this)" action="/cms/Adv_queryByAdv.action" method="post">
				<s:token></s:token>
			<!-- hidden -->
			<input type="hidden" id="pageNo_keyWords" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
			<input type="hidden" id="pageSize_keyWords" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
			<input type="hidden" id="userId" name="userId" value="<s:property value='userId' />">
			<input type="hidden" id="siteId" name="siteId" value="<s:property value='siteId' />">
			<input type="hidden" id="dataType" name="dataType" value="<s:property value='dataType' />">
			<!-- 查询条件 -->
		    <table width="98%" align="center" border="0"	class="content_table" cellpadding="0" cellspacing="0">
				<tr>
				
					<td width="60%" valign="middle" colspan="2">
							
					</td>
				</tr>
				<tr width="60%">
					<td align="left" width="20%">
						广告名称:<input name="keyWords.name_keyword" type="text"  
							value="<s:property value="keyWords.name_keyword"/>">
							
							
					</td >
					<td align="left" width="22%">
					   
						广告位置:<SELECT name="keyWords.adcolumnId_keyword" id="adcolumnId">
							        <option value="1">**</option>
							       </SELECT>

					</td>
					<td align="left" width="12%">
					   <input  type="hidden" id="HadcolumnId" value="<s:property value="keyWords.adcolumnId_keyword"/>"/>
						发布状态:<SELECT name="keyWords.status_keyword" id="status">
						            <option <s:if test="keyWords.status_keyword==null">selected='selected'</s:if> value="">全部</option>
							        <option <s:if test="keyWords.status_keyword==0">selected='selected'</s:if> value="0">未发布</option>
							        <option <s:if test="keyWords.status_keyword==1">selected='selected'</s:if> value="1">已发布</option>
							         <option <s:if test="keyWords.status_keyword==2">selected='selected'</s:if> value="2">已修改</option>
							       </SELECT>
					</td>
					
				</tr>
				<tr>
					<td align="left" width="20%">
						开始时间:<input name="keyWords.beginTime_keyword" type="text" readonly="readonly" value="<s:date name="keyWords.beginTime_keyword" format="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
					</td>
					<td align="left" width="20%">
						结束时间:<input name="keyWords.endTime_keyword" type="text" style="width: 184px;" readonly="readonly" value="<s:date name="keyWords.endTime_keyword" format="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
					</td>
					<td align="right" >
						<input type="hidden" id="checkeds" name="checkeds" value="<s:property value='checkeds'/>"/>
						<INPUT TYPE="submit" class="queryBtn" value="">
						
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
					<!--<th >
					         修改日期
					</th>
					<th >
					         修改人
					</th>
					-->
				</tr>
				<s:iterator  value="page.dataList">
					<tr>
						<td width="5%">
						 <s:if test="flag!=1">
							<input type="checkbox" name="levelId" value='<s:property value="advId"/>' onclick="checkpromotionId(this);">
						 </s:if>		
						</td>
						<td>
						<s:property value="advId"/>
						
						</td>
						<td>
							
							<s:property value="name"/>
						</td>
						<td>
				
							<s:property value="adname"/>
						</td>
						
						<!--
						<td>
						    <s:property value="unitName"/>
						</td>
						-->
					
						
						<td width="130px;"><s:date name="beginTime" format="yyyy-MM-dd HH:mm:ss"/>
					     	
						</td>
						<td width="130px;">
								<s:date name="endTime" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
					
						<td>
						   
						    <s:if test="status==1">已发布</s:if>
						    <s:if test="status==0">未发布</s:if>
						     <s:if test="status==2"><font color="red">已修改</font></s:if>
						</td>
							<td>
						    <s:property value="clickCount"/>
						  
						</td>
						<td>
						    <s:property value="linkman"/>
						</td>
						<!--<td><s:date name="modifyDate" format="yyyy-MM-dd HH:mm:ss"/>
							
						</td>
						<td>
							<s:property value="modified"/>
						</td>
						-->
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
			
			<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="10" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<input class="saveBtn" type="button" value=" " onclick="selectOneAccount();">
		</td>
		<td width="20%" align="center"></td>
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
 });
 });
 </script>
		
	</body>
</html>

