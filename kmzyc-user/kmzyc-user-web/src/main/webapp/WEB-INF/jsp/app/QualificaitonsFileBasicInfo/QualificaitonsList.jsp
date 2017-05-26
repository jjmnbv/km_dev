<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发送短信邮件记录</title>
	<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
        <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script type="text/javascript">
    /**  进入修改个人信息页面  **/
    function gotoUpdate(id){ 

      location.href="/qualificaitons/qualificaitons_qualificaitonsUpdate.action?qualificaitonsDO.id="+id;
    }
	//详情
		function gotoDetail(id)
		{
			location.href = "/qualificaitons/qualificaitons_qualificaitonsDetail.action?qualificaitonsDO.id="+id;

		} 


		function gotoAdd() {
			document.qualificaitonsForm.action = "/qualificaitons/qualificaitons_qualificaitonsAdd.action ";
			document.qualificaitonsForm.submit();
		}
 
    </script>
    
</head>
<body>
<!-- 标题条 -->
		<s:set name="parent_name" value="'采购商管理'" scope="request" />
		<s:set name="name" value="'采购资格管理'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
       <div  style="height:90%;overflow-y:auto; " >
<s:form  name="qualificaitonsForm" id="qualificaitonsForm"  onsubmit=" return checkAllTextValid(this)"
action="/qualificaitons/qualificaitons_pageList.action" method="post">
<!-- 查询条件区域 -->
 <table width="98%" align="center" class="content_table topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
 
 	<tr>
							<td width="80%" valign="middle" colspan="6">
								
							 
								 
							</td>
						</tr>
	<tr>
	<td align="right">账户号：</td>
		<td>
		     <input name="qualificaitonsDO.userName" type="text" value="<s:property value='qualificaitonsDO.userName'/>">
		     
		      <input name="qualificaitonsDO.userId" type="hidden" value="<s:property value='qualificaitonsDO.userId'/>">
		</td>
		
				<td align="right">状态：</td>
		<td>
		      <select name="qualificaitonsDO.status" style="width:150px">
										<option value="" <s:if test='qualificaitonsDO.status==""'>selected="selected"</s:if>>
										请选择
									</option>
								<option value="0" <s:if test='qualificaitonsDO.status=="0"'>selected="selected"</s:if>>
										 无效
									</option>
									<option value="1" <s:if test='qualificaitonsDO.status=="1"'>selected="selected"</s:if>>
									   有效
									</option>
								 
							</select>
		</td> 
		
						<td align="right">资格类型：</td>
		<td> 
		
		   <select name="qualificaitonsDO.type" style="width:150px">
										<option value="" <s:if test='qualificaitonsDO.type==""'>selected="selected"</s:if>>
										请选择
									</option>
								<option value="0" <s:if test='qualificaitonsDO.type=="0"'>selected="selected"</s:if>>
										 普通
									</option>
									<option value="1" <s:if test='qualificaitonsDO.type=="1"'>selected="selected"</s:if>>
									  OTC
									</option>
								 	<option value="2" <s:if test='qualificaitonsDO.type=="2"'>selected="selected"</s:if>>
									  医疗器械
									</option>
										<option value="3" <s:if test='qualificaitonsDO.type=="3"'>selected="selected"</s:if>>
									 处方药
									</option>
									
							</select>
                        <td align="right">
			<INPUT TYPE="submit" class="queryBtn" value="">
            <input class="addBtn" TYPE="button" value=""
									onclick="gotoAdd();">
		</td> 
 
	 </tr>
 
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
		  <th align="center" width="5%">
            <input type='checkbox' name='allbox'  onclick="checkAll(this,'ids')">
		</th> 
	<th align="center" >账户号</th>
	<th align="center" >资格类型</th>
	<th align="center" >创建日期</th>
	<th align="center" >修改日期</th>
		<th align="center" >有效日期</th>
	<th align="center" >状态</th>
<th align="center" >操作</th>
	</tr>
	<s:iterator id="emailInfoiterator"  value="page.dataList">
	<tr>
	    <td align="center" width="5%">
		    <input type="checkbox"  name="id"  value='<s:property value="id"/>' />
		</td>
	<td align="center">
		     <s:property value="userName"/>
		</td>
		
		<td align="center">
			  	<s:if test="type==0">
					 普通
</s:if>
 
	  	<s:if test="type==1">
					OTC
</s:if>
						<s:if test="type==2">
                         医疗器械
</s:if>
				 
                     	<s:if test="type==3">
       处方药
                     
                     	</s:if>
                     	                
		</td>
	 
							
				<td align="center">
 
					<s:date name="createDate"  format="yyyy-MM-dd HH:mm:ss" />	 
                   
   </td>
 
 			<td align="center">
 
					<s:date name="modifyDate"  format="yyyy-MM-dd HH:mm:ss" />	 
                   
   </td>
   		<td align="center">
 
					<s:date name="validDate"  format="yyyy-MM-dd" />	 
                   
   </td>
		<td align="center">
  			<s:if test="status==0">
				 无效
</s:if>
						<s:if test="status==1">
                    有效
</s:if>
 
		</td>
 
	<TD> 
	  <img title="详情" style="cursor: pointer;" src="/etc/images/u175_normal.png"  onclick="gotoDetail(<s:property value="id"/>)"/>
	<img title="修改" style="cursor: pointer;" src="/etc/images/u171_normal.png"  onclick="gotoUpdate(<s:property value="id"/>)" />
 
	</TD> 
	</tr>
	
	
	</s:iterator>
</table>

<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'qualificaitonsForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
</div>
</body>
</html>

