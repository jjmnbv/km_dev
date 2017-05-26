<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>页面列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		 <Script src="/etc/js/97dater/WdatePicker.js"></Script> 
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
		<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
		<script>

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
	           parent.closePomotion(checkeds,userId,siteId);
	    }
		
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
        	
        	pageForm= window.document.getElementById("promotionForm");
	   });
        /** 删除信息  */
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
                          document.promotionForm.action="/cms/cmsPromotion_All_promotionDelete.action";
                          document.promotionForm.submit();
                 }
    }
	
  

    /** 进入修改  **/
    function editByKey(id){
        //var pageNo=$("#pageNo").val();
        //location.href="/cms/cmsPromotion_promotionById.action?adminType=0&promotionId="+id+"&pageNo="+pageNo;
        pageForm.action="/cms/cmsPromotion_promotionById.action?promotionId="+id; 
 		pageForm.submit();
    }
     /** 进入修改  **/
    function adminEditByKey(id){
        //var pageNo=$("#pageNo").val();
        //location.href="/cms/cmsPromotion_promotionById.action?promotionId="+id+"&pageNo="+pageNo;
        pageForm.action="/cms/cmsPromotion_promotionById.action?promotionId="+id; 
 		pageForm.submit();
    }
    
    /** 单条删除*/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
           location.href="/cms/cmsPromotion_promotionDelete.action?promotionId="+id;
         }
    }
		//活动发布
	 function publish(id){
	     location.href="/cms/cmsPromotion_promotionPublic.action?adminType=0&promotionId="+id;
	}
	 function adminPublish(id){
	     location.href="/cms/cmsPromotion_promotionPublic.action?promotionId="+id;
	}
	 function promotionPreview(id){
	 	window.open("/cms/cmsPromotion_promotionPreview.action?adminType=0&promotionId="+id);
	 }
	 
	 	function addgoto(){
       //location.href="/cms/cmsPromotion_addgoto.action?adminType=0";
       pageForm.action="/cms/cmsPromotion_addgoto.action";
 		pageForm.submit();
    }

	function adminaddgoto(){
       //location.href="/cms/cmsPromotion_addgoto.action";
       pageForm.action="/cms/cmsPromotion_addgoto.action";
 		pageForm.submit();
    }
	</script>	
		
		
	</head>
	<body >
		
		<s:form id="promotionForm" class="promotionForm" name="promotionForm" onsubmit="return checkAllTextValid(this)" action="/cms/cmsPromotion_queryByPromotion.action" method="post">
		 <s:token></s:token>
		  <!-- hidden -->
			<input type="hidden" id="pageNo_keyWords" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
			<input type="hidden" id="pageSize_keyWords" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
		 	<input type="hidden" id="userId" name="userId" value="<s:property value='userId' />">
			<input type="hidden" id="siteId" name="siteId" value="<s:property value='siteId' />">
			<input type="hidden" id="dataType" name="dataType" value="<s:property value='dataType' />">
			<input type="hidden" id="checkeds" name="checkeds" value="<s:property value='checkeds'/>"/>
		  <table width="98%" align="center" border="0"	class="content_table" cellpadding="0" cellspacing="0">
		  
		  <tr>
				
					<td width="60%" valign="middle" colspan="2">
							
					</td>
				</tr>
				<tr width="60%">
					<td align="left" width="20%">
						活动名称:<input name="keyWords.name_keyword" type="text" value="<s:property value="keyWords.name_keyword"/>">
							
							
					</td >
					<td align="left" width="22%">
					   
					输出路径:<input name="keyWords.outPath_keyword" type="text"  value="<s:property value="keyWords.outPath_keyword"/>">

					</td>
					<td align="left" width="12%">
					   发布状态:<SELECT name="keyWords.status_keyword" id="status">
						            <option <s:if test="cmspt.status==null">selected='selected'</s:if> value="">全部</option>
							        <option <s:if test="cmspt.status==1">selected='selected'</s:if> value="1">已发布</option>
							        <option <s:if test="cmspt.status==0">selected='selected'</s:if> value="0">未发布</option>
							       </SELECT>
					</td>
					
				</tr>
				<tr>
					<td align="left" width="20%">
						开始时间:<input name="keyWords.beginTime_keyword" type="text" readonly="readonly" value="<s:date name="keyWords.beginTime_keyword" format="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
					</td>
					<td align="left" width="20%">
						结束时间:<input name="keyWords.endTime_keyword" type="text" readonly="readonly" value="<s:date name="keyWords.endTime_keyword" format="yyyy-MM-dd HH:mm:ss"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
					</td>
					<td align="right" >
						<INPUT TYPE="submit" class="queryBtn" value="">
					</td>
				</tr>
		  
				
			</table>
			
			<table width="98%" class="list_table" cellpadding="3" align="center">
				<tr>
					<th width="5%">
						<input type='checkbox' name='level'  onclick="checkAllpop(this,'levelId')">
					</th>
					<th>编号</th>
					<th>活动名称</th>
					<th>活动类型</th>
					<th>活动规则</th>
					<th>输出路径</th>
					<th>开始时间</th>
					<th>结束时间</th>
					<th>状态</th>
				</tr>
			<s:iterator id="custiterator" value="page.dataList">
				<tr>
					<td width="5%">
					<s:if test="flag!=1">
						<input type="checkbox" name="levelId" id="levelId" value='<s:property value="id"/>' onclick="checkpromotionId(this);">
					</s:if>
					</td>
					<td><s:property value="id"/></td>
					<td><s:property value="name"/></td>
					<td>
					<s:if test="productFilterType==1">全场活动</s:if>
					<s:if test="productFilterType==2">指定商品</s:if>
					<s:if test="productFilterType==3">商品类目</s:if>
					<s:if test="productFilterType==4">商品品牌</s:if>
					<s:if test="productFilterType==5">指定商家</s:if>
					</td>
					<td><s:property value="categoryMap[promotionTypeId]"/></td>
					<td><s:property value="output"/></td>
					<td><s:date name="beginTime" format="yyyy-MM-dd HH:mm:ss"/>	</td>
					<td><s:date name="endTime" format="yyyy-MM-dd HH:mm:ss"/></td>
					
					<td>
						<s:if test="status==0">未发布</s:if>
					    <s:if test="status==1">已发布</s:if>
					    <s:if test="status==2"><span style="color:red;">已修改</span></s:if>
					</td>
				</tr>
			</s:iterator>
			</table>
			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>
						<s:set name="form_name" value="'promotionForm'" scope="request"></s:set>
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
		</div>
	</body>
</html>

