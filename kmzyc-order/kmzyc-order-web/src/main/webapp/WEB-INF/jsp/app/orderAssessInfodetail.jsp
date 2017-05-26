<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ page import="java.util.*" %>
<%@ page import="com.pltfm.app.vobject.OrderAssessDetailVO" %>
<%@ page import="com.pltfm.app.entities.OrderAssessInfo" %>

 
<html>
	<head>
		<title>订单评价明细信息</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<!-- <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" /> -->
		<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
        <link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
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
    
    /** 进评价信息回复页面  **/
    function OrderAssessInfoReply(id){
      
        location.href="/app/orderAssessInfoReply.action?assessInfoId="+id;
       //  location.href="/app/orderAssessInfoReply.action?assessInfoId='+id+'";
       
    }
    
    /** 进入新增客户评价信息删除页面  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
           location.href="/app/deleteAssessInfoByAssessId.action?assessInfoId="+id;
         }
    }
    
    function gotoList(){
   	   location.href="/app/getOrderAssessInfo.action";
   	
   }
    
</script>
	</head>
	<body>

<s:set name="parent_name" value="'业务操作'" scope="request"/>
<s:set name="name" value="'订单评价'" scope="request"/>
<s:set name="son_name" value="'订单评价查询'" scope="request"/>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<form id="orderAssessqueryPageList" name="orderAssessForm" action="/app/assessInfo.action" method="post">
			<!-- 查询条件 -->
		    <table width="98%" align="center" height="100" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>
					<td width="60%" valign="middle" colspan="2">
						<!--  <input class="addBtn" TYPE="button" value="" onclick="gotoAdd(<s:property value="#session.assessInfoId"/>);">&nbsp;&nbsp; -->
						<input class="delBtn" type="button" value=""
							onclick="deleteSelected('orderId');">
						  <!-- <input class="backBtn" type="button" value=""
							onclick="gotoList();">	 -->
					</td>
				</tr>
				
			</table>
			<!-- 数据列表区域 -->
			<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
				<tr>
					<th width="5%">
						<input type='checkbox' name='orderId'  onclick="checkAll(this,'orderId')">
					</th>
					<th>
					      评论人
					</th>
					<th>
					   维度类别
					</th>  
					<th>
					  评价内容
					</th>     
					<th>
					       时间
					</th>
					
					<th>
					     操作   
					</th>
					
					
				</tr>
				
				<%
				   List list=(List)request.getAttribute("list");
				   for(int i=0;i<list.size();i++){
				   OrderAssessInfo oAssessInfo=(OrderAssessInfo)list.get(i);
				   request.setAttribute("oAssessInfo", oAssessInfo);
				%>
				 <tr>
				  <td width="5%">
							 <input type="checkbox" name="assessInfoId" value="<s:property value='#request.oAssessInfo.assessInfoId'/>">
							
				  </td>
				
				  <td width="10%">
						   <a href="/app/orderAssessInfoRelpyListAction.action?assessInfoId=<s:property value='#request.oAssessInfo.assessInfoId'/>"><s:property value="#request.oAssessInfo.guestNum"/></a> 
							
				 </td>
				
				
				
				<td width="40%" >
				          <%
			            	List<List<OrderAssessDetailVO>>allList=(List<List<OrderAssessDetailVO>>)request.getAttribute("allList");
				            List<OrderAssessDetailVO> detailList=(List<OrderAssessDetailVO>)allList.get(i);
				            request.setAttribute("detailList", detailList);
				         %>
				
			              <!-- <table border="0" align="center"  style="font-size:12px" cellpadding="0" border="0" cellspacing="0" >
			                <s:iterator value="#request.detailList" id="detail">
			                <tr>
			                   <td><s:property value="#detail.assessdetailContext"/></td>
			                   <td><s:property value="#detail.assessScore"/></td>
			                </tr>
			               </s:iterator>
			              </table> -->
			              
			              
			             
			                <s:iterator value="#request.detailList" id="detail">
			                   
			                   <span><s:property value="#detail.assessdetailContext"/>:<s:property value="#detail.assessScore"/><span>
			                
			               </s:iterator>
			            
			              
			                
			   </td>
			   
			   <td>
			      <s:property value="#request.oAssessInfo.assessContext"/>
			   
			   </td>
			            
				
				
				<td width="15%">
                         
                           <s:date name="#request.oAssessInfo.createDate" format="yyyy-MM-dd HH:mm:ss"/>
			    </td>
				
				
				<td width="7%">
						     <img title="回复" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="OrderAssessInfoReply(<s:property value="#request.oAssessInfo.assessInfoId"/>)"/> 
						  
						    <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"  onclick="deleteByKey(<s:property value="#request.oAssessInfo.assessInfoId"/>)" /> 
						     
				</td>
				
				
				</tr>
				<%	   
					   
					   
				   }
				
				%>
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
 
