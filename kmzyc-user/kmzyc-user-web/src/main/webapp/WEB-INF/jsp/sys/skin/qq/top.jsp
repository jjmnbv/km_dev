<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<head>
<link href="/etc/css/style_sys_skin.css" rel="stylesheet" type="text/css" />
<link href="/etc/css/iconfont.css" rel="stylesheet" type="text/css" />
</head>
<body >
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>
<div id="header_qq"  height="78">
	<table width="100%" height="58" border="0" cellpadding="0" cellspacing="0">
	  <tr>
	    <td width="200" align="center" valign="middle" rowspan="2" > 
		    <div style="padding-left:10px;padding-top: 10px;"><img src="/etc/images/logo.png"></div>
		</td>
	  </tr>
	  <tr height="45">
	    <td >
	        <div id="tabsI">
	            <ul>
				<s:iterator id="menuiterator" value="dataList" status="idx">
					<li id="head_<s:property value='#idx.index+1'/>">
						<a href="<s:property value='menuLink'/>" target="_parent" onClick="changeHeadMenu(<s:property value='#idx.index+1'/>);">
							<s:if test="menuName=='客户系统'"><i class="icon-customer"></i></s:if>
							<s:if test="menuName=='产品系统'"><i class="icon-product"></i></s:if>
							<s:if test="menuName=='订单系统'"><i class="icon-order"></i></s:if>
							<s:if test="menuName=='CMS系统'"><i class="icon-cms"></i></s:if>
							<s:if test="menuName=='搜索系统'"><i class="icon-sousuo"></i></s:if>
							<s:if test="menuName=='促销系统'"><i class="icon-sale"></i></s:if>
							<s:if test="menuName=='系统配置'"><i class="icon-setup"></i></s:if>
							<s:if test="menuName=='分析系统'"><i class="icon-Analysis"></i></s:if>
							<span><s:property value="menuName"/></span>
						</a>
					</li>
				</s:iterator>
	            </ul>
	        </div>
	<div class="tabsii">
	<img src="/etc/images/sys/icon_03.png" >
	<a href="/sys/gotoSysMain.action" target="main" style="color:#fff;text-decoration: none;vertical-align: inherit;" >桌&nbsp;&nbsp;面</a>
	 　|　
	<img src="/etc/images/sys/icon_04.png" >
	<a href="/sys/gotoSysUserPwdModify.action?userId=<s:property value="#session['sysUser'].userId"/>" target="main" style="color:#fff;text-decoration: none;vertical-align: inherit;">修改密码</a>
	 　|　
	<img src="/etc/images/sys/icon_05.png" >
	<a href="http://kmcas.kmb2b.com/logout?service=http://kmcas.kmb2b.com/casServer/login" target="_top" style="color:#fff;text-decoration: none; vertical-align: inherit;">退&nbsp;&nbsp;出</a>
	</div>
		</td>
	  </tr> 
	</table>
</div>
<script type="text/javascript">
	function changeHeadMenu(indexId){
		document.getElementById("head_"+indexId).className="current";
		for(var i=1;i<=<s:property value='dataList.size()'/>;i++){
			if(i!=indexId){
				document.getElementById("head_"+i).className="";
			}
		}
	}

</script>	
</div>
</body>
</html>
