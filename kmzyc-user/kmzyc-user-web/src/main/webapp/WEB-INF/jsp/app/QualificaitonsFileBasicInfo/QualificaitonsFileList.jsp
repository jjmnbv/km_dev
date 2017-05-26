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
 
      location.href="/qualificaitonsFile/qualificaitonsFile_qualificaitonsFileUpdate.action?qualificaitonsFileDO.id="+id;
    }


	/** 单条删除活动  **/
	function gotoDelete(id) {
		if (confirm("是否确认删除? ") == true) {
			location.href = "/qualificaitonsFile/qualificaitonsFile_qualificaitonsDelete.action?qualificaitonsFileDO.id="+id;
		}
	}

//详情
	function gotoDetail(id)
	{
		location.href = "/qualificaitonsFile/qualificaitonsFile_qualificaitonsDetail.action?qualificaitonsFileDO.id="+id;


	}


	/** 审核***/
	
	function gotoVerify(id)
	{

		location.href = "/qualificaitonsFile/qualificaitonsFile_qualificaitonsVerify.action?qualificaitonsFileDO.id="+id;

   }
    </script>
    
</head>
<body>
<!-- 标题条 -->
		<s:set name="parent_name" value="'采购商管理'" scope="request" />
		<s:set name="name" value="'资质文件管理'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
       <div  style="height:90%;overflow-y:auto; " >
<s:form  name="qualificaitonsFileForm" id="qualificaitonsFileForm"  onsubmit=" return checkAllTextValid(this)"
action="/qualificaitonsFile/qualificaitonsFile_pageList.action" method="post">
<!-- 查询条件区域 -->
 <table width="98%" align="center" class="content_table topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr>
	<td align="right">账户号：</td>
		<td>
		     <input name="qualificaitonsFileDO.userName" type="text" value="<s:property value='qualificaitonsFileDO.userName'/>">
		</td>
		
			<td align="right">文件名：</td>
		<td>
		     <input name="qualificaitonsFileDO.fileName" type="text" value="<s:property value='qualificaitonsFileDO.fileName'/>">
		</td>
		
	<td align="right">有效结束时间：</td>
	 <TD>
		<input type="text" name="qualificaitonsFileDO.beginDate"  id="d523" class="Wdate"  value ="<s:date name = 'qualificaitonsFileDO.beginDate' format='yyyy-MM-dd ' />"     onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'d521\')}'})"  />
	
	至 <input type="text" name="qualificaitonsFileDO.endDate" id="d521" class="Wdate"  value ="<s:date name = 'qualificaitonsFileDO.endDate' format='yyyy-MM-dd ' />"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'d523\')}'})" />
	</TD>
	 </tr>
	<tr>
		 
			<td align="right">认证状态：</td>
		<td>
		      <select name="qualificaitonsFileDO.status"   style="width:150px">
										<option value="" <s:if test='qualificaitonsFileDO.status==""'>selected="selected"</s:if>>
										请选择
									</option>
								<option value="1" <s:if test='qualificaitonsFileDO.status=="1"'>selected="selected"</s:if>>
										 未认证
									</option>
									<option value="2" <s:if test='qualificaitonsFileDO.status=="2"'>selected="selected"</s:if>>
									通过
									</option>
									<option value="3" <s:if test='qualificaitonsFileDO.status=="3"'>selected="selected"</s:if>>
								 不通过
								 </option>
							</select>
		</td>
		<td align="right">删除状态：</td>
		<td>
		<select name="qualificaitonsFileDO.deleted"  style="width:150px">
										<option value="" <s:if test='qualificaitonsFileDO.deleted==""'>selected="selected"</s:if>>
										请选择
									</option>
								<option value="0" <s:if test='qualificaitonsFileDO.deleted=="0"'>selected="selected"</s:if>>
									 未删除
									</option>
									<option value="1" <s:if test='qualificaitonsFileDO.deleted=="1"'>selected="selected"</s:if>>
									 已删除
									</option>
								 
							</select>
							</td>
		

		 
		<td align="center">
			<INPUT TYPE="submit" class="queryBtn" value="">
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
	<th align="center" >文件名</th>
	<th align="center" >有效开始时间</th>
	<th align="center" >有效结束时间</th>
	<th align="center" >认证状态</th>
	<th align="center" >添加时间</th>
	<th align="center" >审核时间</th>
		<th align="center" >删除状态</th>
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
		
	  <s:property value="fileName"/>
		</td>
			<td align="center">
								<s:date name="beginDate"  format="yyyy-MM-dd" />
							 
							</td>
		<td align="center">
		     	<s:date name="endDate"  format="yyyy-MM-dd" />
		<td align="center">
			<s:if test="status==1">
						未认证
</s:if>
						<s:if test="status==2">
                                                 通过
</s:if>
				 
                     	<s:if test="status==3">
                     	 不通过
                     	
                     	</s:if>
		</td>

				 
                   
		<td align="center">
		<s:date name="createDate"  format="yyyy-MM-dd HH:mm:ss" />
		</td>
		
		<td align="center">
		<s:date name="auditingDate"   format="yyyy-MM-dd HH:mm:ss"/>
		</td>
		
				<td align="center">
		   <s:if test="deleted==0">
	      未删除
	 </s:if>
	<s:if test="deleted==1">
                       已删除
</s:if>
 </td>
	
	<TD>
	  <img title="详情" style="cursor: pointer;" src="/etc/images/u175_normal.png"  onclick="gotoDetail(<s:property value="id"/>)"/>
	    <img title="修改" style="cursor: pointer;" src="/etc/images/u171_normal.png"  onclick="gotoUpdate(<s:property value="id"/>)" />
   
	<s:if test="%{status==1}">

  	 <img title="审核" style="cursor: pointer;" src="/etc/images/u177_normal.png"  onclick="gotoVerify(<s:property value="id"/>)"/>
  </s:if>
  	<s:if test="%{deleted==1}">
 <img title="删除" style="cursor: pointer;" src="/etc/images/u173_normal.png"  onclick="gotoDelete(<s:property value="id"/>)" />
	</s:if>
	
	</TD> 
	</tr>
	
	
	</s:iterator>
</table>

<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'qualificaitonsFileForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
</div>
</body>
</html>

