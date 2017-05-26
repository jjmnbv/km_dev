<%@page contentType="text/html;charset=UTF-8" import="com.pltfm.sys.util.StaticParams" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>消息中心管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
        <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript">	 
    	$(function(){
			$('#allbox').on('click', function () {
				var ckd=this.checked;
				$('.ck_msgId').each(function(){
					this.checked=ckd;
				});
    		});
			$('#btn_delmsg').on('click', function () {
				var ids=[];
				$('.ck_msgId:checked').each(function(){
					ids.push(this.value);
				});
				if (ids.length==0) {
                    alert("请选择要删除的数据。");
	    		}else if(confirm('是否确认删除?')==true){ 
                      $('#bnesMessageCenterForm').attr('action','/messageCenter/messageCenter_detele.action').submit();
             	}
    		});
        });
		function queryUserInfo(id,type){
			if(type=='1'){
				dialog("消息中心","iframe:/messageCenter/messageCenter_pageList.action?viewOnly=1&showType=1&flag=1&bnesMessageCenter.accountId="+id,"900px","760px","iframe");
// 				dialog("选择登录账号","iframe:/userInfo/personalBasicInfo_prePersonDetail.action?loginId="+id,"900px","760px","iframe");
		   	}else if(type=='2'){
		   	    dialog("选择会员账号","iframe:/personalbasic/personalbasic_preExpertDetail.action?loginId="+id,"900px","760px","iframe");
		   	}else if(type=='4'){
		   	    dialog("选择会员账号","iframe:/userInfo/commercialTenantBasicInfo_preBusinessDetail.action?loginId="+id,"900px","760px","iframe");
		   	}
		}
		</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:if test="%{showType==null}">
			<s:set name="parent_name" value="'客户业务'" scope="request" />
			<s:set name="name" value="'消息中心'" scope="request" />
			<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		</s:if>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form  name="bnesMessageCenterForm" id="bnesMessageCenterForm" onsubmit=" return checkAllTextValid(this)"  method="post" action="/messageCenter/messageCenter_pageList.action">
			<s:token/>
			<table width="98%" align="center" border="0" class="content_table" cellpadding="0" cellspacing="0">
 
				<tr width="60%">
					<td align="right">会员账号：</td>
					<td>
					     <s:if test="%{showType!=null}">
					     	<s:property value='bnesMessageCenter.loginAccount'/>
					        <input name="loginAccount" type="hidden" readonly value="<s:property value='bnesMessageCenter.loginAccount'/>">
					        <input name="accountId"  type="hidden" value="<s:property value='bnesMessageCenter.accountId'/>">
					        <input name="showType" type="hidden" value="<s:property value='showType'/>">
					     </s:if>
					     <s:else>
					     <input name="loginAccount" type="text" value="<s:property value='bnesMessageCenter.loginAccount'/>">
					     </s:else>
					</td>
					<td align="right">消息标题：</td>
					<td>
					    <input name="title" type="text" value="<s:property value='bnesMessageCenter.title'/>">
					</td>
					<td align="right">消息类型：</td>
					<td>
						<select name="type">
				                  <option value="">全部</option>
								<option <s:if test="bnesMessageCenter.type==1">selected="selected"</s:if> value="1">安全消息</option>
									<option <s:if test="bnesMessageCenter.type==2">selected="selected"</s:if> value="2">产品信息</option>
									<option <s:if test="bnesMessageCenter.type==3">selected="selected"</s:if> value="3">订单信息</option>
									<option <s:if test="bnesMessageCenter.type==4">selected="selected"</s:if> value="4">审核信息</option>
							</select>
					</td>
					<td align="right">发送平台：</td>
					<td>
					    <select name="messagePlatform">
						 <option value="">全部</option>
						 <option <s:if test="bnesMessageCenter.messagePlatform==1">selected="selected"</s:if> value="1">b2b平台</option>
						 <option <s:if test="bnesMessageCenter.messagePlatform==2">selected="selected"</s:if> value="2">供应商平台</option>
						 <!-- <option <s:if test="bnesMessageCenter.messagePlatform==3">selected="selected"</s:if> value="3">微商平台</option> -->
						</select>
					</td>
					
					<td align="right">是否查阅：</td>
					<td>
					    <select name="status">
									<option value="">全部</option>
									<option <s:if test="bnesMessageCenter.status==0">selected="selected"</s:if> value="0">未阅</option>
									<option <s:if test="bnesMessageCenter.status==1">selected="selected"</s:if> value="1">已阅</option>
									</select>
					</td>	
					<td align="right" >
						<input type="submit" class="queryBtn" value="">
					</td>
				</tr>
			</table>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center" cellspacing="0" border="1">
				<tr>
				    <s:if test="%{showType==null}">
						<th width="10%">会员账号</th>
					</s:if>
					<th width="10%">消息类型</th>
					<th width="15%">消息标题</th>
					<th width="20%">消息内容</th>
					<th width="10%">是否查阅</th>
					<th width="10%">查阅日期</th>
					<th width="10%">是否定时发布</th>	
					<th width="10%">发布日期</th>
					<th width="10%">发送平台</th>
				</tr>
				 <c:if test="${flag !='0'}">
				<s:iterator id="custiterator"  value="page.dataList">
				<tr>
					<s:if test="%{showType==null}">
					<td align="center">
			           <s:property value="loginAccount" />
					</td>
					</s:if>
					<td align="center">
					   <s:if test="type==1">安全消息</s:if>
					   <s:elseif test="type==2">产品信息</s:elseif>
					   <s:elseif test="type==3">订单信息</s:elseif>
					    <s:elseif test="type==4">审核信息</s:elseif>
					</td>
					<td align="center">
						<s:if test="title.length()>15">
				   		  <s:property value='title.substring(0,15)'/>...
				 		</s:if>   
						<s:else>      
				     		<s:property value='title'/>
				 		</s:else>  
					</td>
					<td align="center">
					<s:if test="content.length()>15">
					  <a title="${content }">
				   		  <s:property value='content.substring(0,15)'/>...
				 		</s:if>   
						<s:else>      
				     		<s:property value='content'/>
				 		</s:else> 
				 		</a>
					</td>
					<td align="center">
					     <s:if test="#custiterator.status==0">未阅</s:if>
					   <s:elseif test="#custiterator.status==1">已阅</s:elseif>
					</td>
					
					<td align="center">
					     <s:date name="checkDate" format="yyyy-MM-dd"/>
					</td>
					<td align="center">
					  <s:if test="#custiterator.isTime==1">是</s:if>
					  <s:elseif test="#custiterator.isTime==0">否</s:elseif>     
					</td>
					<td align="center">
					<s:date name="releaseDate" format="yyyy-MM-dd"/>
					</td>
					<td align="center">
					<c:if test="${messagePlatform==1}">b2b平台</c:if>
					<c:if test="${messagePlatform==2}">供应商平台</c:if>
					<c:if test="${messagePlatform==3}">微商平台 </c:if>
					</td>
				</tr>
				</s:iterator>
				</c:if>
			</table>
			<c:if test="${flag==0}">
			<div style=" text-align: center;">请输入具体条件进行查询,如想查询全部数据请直接点击查询按钮</div>
			</c:if>
			<c:if test="${flag!='0'}">
			
			<table width="98%" align="center" class="page_table">
				<tr>
					<td><s:set name="form_name" value="'bnesMessageCenterForm'"
						scope="request"></s:set> <jsp:include
						page="/WEB-INF/jsp/common/page.jsp"></jsp:include></td>
				</tr>
			</table>
			</c:if>
		</s:form>
		</div>
        <jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</body>
</html>