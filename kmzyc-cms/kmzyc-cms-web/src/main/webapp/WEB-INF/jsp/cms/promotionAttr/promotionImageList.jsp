<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>广告位管理</title>
	    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		 <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
	</head>
	<body >
		<!-- 导航栏 -->
	<script>
	
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
                          document.userLevelForm.action="/cms/PromotionAttr_All_Delete.action";
                          document.userLevelForm.submit();
                 }
    }
	function addgoto(){
       location.href="/cms/GotoPromotion.action"
    }
	
    /** 进入修改广告位页面  **/
    function editUserLevel(id){
         location.href="/cms/PromotionAttr_Byid.action?proid="+id;
    }
    
    /** 单条删除客户等级信息  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
           location.href="/cms/PromotionAttr_Delete.action?proid="+id;
         }
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
<div width="100%" class="listTitle"  height="40">
    <span>
	<img src="/etc/images/icon_02.png"  />&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;基础功能&nbsp;&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;&nbsp;活动图片列表
		
		
	  </span>
</div>
		<div  style="height:90%;overflow-y:scroll; " >
		        
<form id="ADV_queryPageList" name="userLevelForm" onsubmit="return checkAllTextValid(this)" action="/cms/PromotionAttr_List.action" method="post">
			<!-- 查询条件 -->
		    <table width="98%" align="center" height="90" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>
				
					<td width="60%" valign="middle" colspan="2">
						<input class="addBtn" TYPE="button" value="" onclick="addgoto();">&nbsp;&nbsp;
						<input class="delBtn" type="button" value=""
							onclick="deleteSelected('levelId');">
							<input type="hidden" id="checkeds" name="checkeds" value="<s:property value='checkeds'/>"/>
					</td>
				</tr>
				<tr width="60%">
					<td >
						活动标签：
						<input name="pro.promotionTag" type="text"  
							value="<s:property value="pro.promotionTag"/>">
							
							
					</td >
					
					<td align="right" >
						<INPUT TYPE="submit" class="queryBtn" value="">
					</td>
				</tr>
			</table>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1">
				<tr>
					<th width="5%">
						<input type='checkbox' name='level'  onclick="checkAll(this,'levelId')">
					</th>
					<th>
						编号
					</th>
					
					<th>
						活动标签
						
					</th>
					
					
					<th >
			                                    图片
					</th>
				<th width="60">
					             操作
					</th>
				</tr>
				<s:iterator  value="page.dataList">
					<tr>
						<td width="5%">
							<input type="checkbox" name="levelId"
								value='<s:property value="promotionAttrId"/>' onclick="checkpromotionId(this);">
								
						</td>
						<td>
						<s:property value="promotionAttrId"/>
						
						</td>
						<td>
							
							<s:property value="promotionTag"/>
						</td>
						<td>
				
							<s:property value="promotionTinyIcon"/>
						</td>
						
						<!--
						<td>
						    <s:property value="unitName"/>
						</td>
						-->
					
						
						
					<td>

						
						

						    <img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="editUserLevel(<s:property value="promotionAttrId"/>)" />
						    <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value="promotionAttrId"/>)" />
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
		</form>




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
 
 });
 </script>
		
	</body>
</html>

