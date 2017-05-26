<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
 
 
<html>
	<head>
		<title>订单评价回复信息</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<!-- <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" /> -->
		<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
        <link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
		<script type="text/javascript"  src="/etc/js/jquery-1.4.2.min.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"> 
	  
    /** 删除客户评价回复信息  **/
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
                          document.orderAssessReplyForm.action="/app/removeAllAssessReplyAction.action ";
                          document.orderAssessReplyForm.submit();
                 }
    }
    /**  进入新增客户评价信息页面  **/
    function gotoAdd(id){
         document.orderAssessReplyForm.action="/app/testOrderAssessReplyAddAction.action?assessInfoId="+id;
         document.orderAssessReplyForm.submit();
    }
    
    /** 进入新增客户评价信息页面  **/
    function editOrderAssessInfoReply(id){
         location.href="/app/orderAssessReplySelect.action?replyId="+id;
    }
    
    /** 进入新增客户评价信息删除页面  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
           location.href="/app/removeOrderAssessReplyAction.action?replyId="+id;
         }
    }
    
    function gotoList(){
    	 location.href="/app/orderAssessInfoDetailAction.action";
    	
    }
    
    
    
</script>
	</head>
	<body>

<s:set name="parent_name" value="'业务操作'" scope="request"/>
<s:set name="name" value="'订单管理'" scope="request"/>
<s:set name="son_name" value="'订单回复列表查询'" scope="request"/>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>

		<form id="orderAssessReplyqueryPageList" name="orderAssessReplyForm" action="/app/assessInfo.action" method="post">
	
		      <table width="98%" align="center" height="100" border="0"	class="content_table" cellpadding="0" cellspacing="0">
				<tr>
					<td width="60%" valign="middle" colspan="2">
						<!-- <input class="addBtn" TYPE="button" value="" onclick="gotoAdd(<s:property value='#session.assessInfoId'/>);">&nbsp;&nbsp; -->
						<input class="delBtn" type="button" value=""
							onclick="deleteSelected('orderId');">
							
						<input class="backBtn" type="button" value=""
							onclick="gotoList();">	
					</td>
				</tr>
				
			</table>
			<!-- 数据列表区域 -->
			<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
				<tr>
					<th width="5%">
						<input type='checkbox' name='replyId'  onclick="checkAll(this,'replyId')">
					</th>
					
					<th width="10%">
						客户名称
					</th>
					

					<th width="40%">
						回复内容
					</th>
					<th>
						回复日期
					</th>
					
					<th width="20%">
						操作
					</th>
				</tr>
				<s:iterator id="replyInfo" value="assessReplyList">
					<tr>
						<td width="5%">
							<input type="checkbox" name="replyId"
								value='<s:property value="#replyInfo.replyId"/>'>
						</td>
			
						<td>
							<s:property value="#replyInfo.replyGuestNum"/>
						</td>
						<!--   <td>
							<s:property value="#replyInfo.orderId"/>
						</td> -->
						
						<td>
							<s:property value="#replyInfo.replyContent"/>
						</td>
						
						<td>
							<s:date name="#replyInfo.replyeDate" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						
						
						<td>
						    <!--  <img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="editOrderAssessInfoReply(<s:property value="#replyInfo.replyId"/>)" /> -->
						     <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"  onclick="deleteByKey(<s:property value="#replyInfo.replyId"/>)" />
						</td>
					</tr>
				</s:iterator>
			</table>

<br/>
<table class="page_table" width="98%" align="center">
   <tr>
     <td align="right"><s:include  value="/WEB-INF/jsp/public/pager.jsp"/></td>
   </tr>
</table>
</form>

	<!-- 
	<div id="question" style="position:absolute;width:430px;height:600px;z-index:1000;display:none"></div>
	 -->
	<div id="question" style="display:none"></div>
	
	</body>
</html>
 
