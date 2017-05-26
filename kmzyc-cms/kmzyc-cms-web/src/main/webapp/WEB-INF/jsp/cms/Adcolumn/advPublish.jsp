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
	

      
	
	
    
    function advPublish(adcolumnId){
	     location.href="/cms/advPublish_parse.action?adcolumn.adcolumnId="+adcolumnId;
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
<s:set name="parent_name" value="'基础功能'" scope="request" />
<s:set name="name" value="'广告发布'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:scroll; " >
  <form id="ADV_queryPageList" name="userLevelForm" onSubmit="return checkAllTextValid(this)" action="/cms/Adcolumn_List.action" method="post">
    
    <!-- 数据列表区域 -->
    <table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1">
      <tr>
        <th> 广告位名称 </th>
        <th> 广告位 </th>
        <th> 输出文件 </th>
        <th width="60"> 操作 </th>
      </tr>
      <s:iterator  value="cmsAdcolumnList">
        <tr>
          <td><s:property value="remark" /></td>
          <td><s:property value="name"/></td>
          <td><s:property value="output"/></td>
          <td><img title="发布" style="cursor: pointer;vertical-align:middle;" src="/etc/images/icon_publish.png"  onclick="advPublish(<s:property value="adcolumnId"/>)" /> </td>
        </tr>
      </s:iterator >
    </table>
  </form>
  
  <!-- 消息提示页面 --> 
  
  <!-- 消息提示 -->
  <div width="100%"> 
    <!-- 消息提示 --> 
    
  </div>
</div>
</body>
</html>
