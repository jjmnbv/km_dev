<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ page import="java.util.*" %>
<%@ page import="com.pltfm.app.vobject.OrderAssessDetailVO" %>
<%@ page import="com.pltfm.app.entities.OrderAssessInfo" %>

 
<html>
	<head>
		<title>订单评价信息</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="/etc/css/notifier-base.css">
        <link rel="stylesheet" type="text/css" href="/etc/css/block.css">
        <link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
        <link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
        <link rel="stylesheet" type="text/css" href="/etc/css/notifier-theme-plastic.css">
		<script type="text/javascript"  src="/etc/js/jquery-1.4.2.min.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"> 
	   
    /** 删除客户等级信息  **/
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
                          document.orderAssessForm.action="/app/deleteAllAssessInfo.action ";
                          document.orderAssessForm.submit();
                 }
    }
    /**  进入新增客户评价信息页面  **/
    function gotoAdd(id){
         document.orderAssessForm.action="/app/testOrderAssessAddAction.action?orderId="+id;
         document.orderAssessForm.submit();
    }
    
    /** 进入新增客户评价信息页面  **/
    function editOrderAssessInfo(id){
    	
        location.href="/app/orderAssessInfoSelect.action?assessInfoId="+id;
    }
    
    /** 进入新增客户评价信息删除页面  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
        	 alert(id);
           location.href="/app/deleteAssessByorder.action?orderId="+id;
         }
    }
    
    function gotoList(){
   	   location.href="/app/ordershowAction.action";
   	
   }
    
    // 选择本页
	  $("#checkPage").click(function(){
		  alert($(this).attr("checked")==true);
		  if($(this).attr("checked")==true){
			  $(":checkbox").not($("#checkAll")).removeAttr("checked");
			  //$(":checkbox").not($("#checkAll")).not($("#checkPage")).removeAttr("checked");
		  }else{
			  $(":checkbox").not($("#checkAll")).attr("checked","checked");
			  //$(":checkbox").not($("#checkAll"))..not($("#checkPage"))attr("checked","checked");
		  }
	  });
	  
	  // 选择所有
	  $("#checkAll").click(function(){
		  alert($(this).attr("checked")=="checked");
		  if($(this).attr("checked")=="checked"){
			  //$(":checkbox").not($("#checkAll")).removeAttr("checked");
			  $(":checkbox").not($(this)).removeAttr("checked");
		  }else{
			  $(":checkbox").not($(this)).attr("checked","checked");
			  //$(":checkbox").not($("#checkAll")).attr("checked","checked");
		  }
	  });

    
</script>
</head>
	<body>

<s:set name="parent_name" value="'业务操作'" scope="request"/>
<s:set name="name" value="'订单评价'" scope="request"/>
<s:set name="son_name" value="'订单评价查询'" scope="request"/>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>
 <form id="getOrderAssessInfo" name="orderAssessForm" action="/app/querOrderMain.action" method="post">
 <table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<th align="right">订单号：</th>
        <td>
		    <input class="condition" name="map['orderCode']" type="text" value=''>
		</td>
		
		<th align="right">客户账户：</th>
        <td>
		    <input class="condition" name="map['customerAccount']" type="text" value=''>
		</td>
		<td> <input type="submit" class="queryBtn" value=""></td>
	</tr>

</table>
<!--<br>  -->
			<!-- 数据列表区域 -->
			<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
				<tr>
					<!--  <th width="5%">
						<input type='checkbox' name='orderId'  onclick="checkAll(this,'orderId')">
					</th>-->
					  <th>
					     订单编号   
					</th>
					<th>
					      订单号
					</th>
					<th>
					    客户账号
					</th>
					
					<th>
					  客户名称
					</th>
					
					<th>
					 评价条数
					</th> 
				
					<th>
					   订单时间        
					</th>

					<!-- <th>
						操作
					</th> -->
				</tr>
					
				    <s:iterator id="oAssessInfo" value="page.dataList" status="st">
			
					  <tr>
						<!--  <td width="5%">
							 <input type="checkbox" name="assessInfoId" value="<s:property value='#oAssessInfo.assessInfoId'/>">
							
						</td>-->
						 <td>
							<!-- <a href='/app/orderAssessReplyListAction.action?assessInfoId=<s:property value="#oAssessInfo.assessInfoId"/>'><s:property value="#oAssessInfo.assessInfoId"/></a> -->
							<!--  <a href="/app/orderAssessInfoDetailAction.action?orderId=<s:property value='#oAssessInfo.orderId'/>"><s:property value="#oAssessInfo.orderId"/></a>-->
							<s:property value="#st.count"/>
						</td>
						<td>
						
						    <a href="/app/orderAssessInfoDetailAction.action?orderId=<s:property value='#oAssessInfo.orderId'/>"><s:property value="#oAssessInfo.orderCode"/></a>
						    <!--<s:property value="#oAssessInfo.orderCode"/>-->
							
						</td>
			            <td>
			               <s:property value="#oAssessInfo.customerAccount"/>
			               <!--   <a href='/app/getCustomerScoreAction.action?guestNum=<s:property value="#oAssessInfo.guestNum"/>'><s:property value="#oAssessInfo.guestNum"/></a>-->
			            </td>
			            
			           <td>
			           
			               <s:property value="#oAssessInfo.customerName"/>
                          
					  </td>
				        
				        <td>
						    <s:property value="#oAssessInfo.assessCount"/>
						</td>
				       
					    <td>
						   <s:date name="#oAssessInfo.createDate" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
					
					  
						<!-- <td>
						    <img title="服务满意度" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="editOrderAssessInfo(<s:property value="#oAssessInfo.assessInfoId"/>)" /> 
						    <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"  onclick="deleteByKey(<s:property value="#oAssessInfo.assessInfoId"/>)" /> 
						     
						</td> -->
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
 
