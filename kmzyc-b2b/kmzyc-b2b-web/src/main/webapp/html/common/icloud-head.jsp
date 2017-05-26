<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.kmzyc.zkconfig.ConfigurationUtil" %>

	
 

		<!-- 顶部导航条 -->
			<div class="i-topbar">
			<div class="l-w fn-clear">
				<span class="fn-right" id="loginbar">您好，欢迎来到康美中药城</span>
				<p class="fn-left loginbar">
					互联网药品交易资格证书编号：粤xxxxxxxxx
				</p>
			</div>
		</div>
		<!-- topbar END -->

	
		
<input type="hidden" id="cssAndJsPath" data_id="<%=ConfigurationUtil.getString("cssAndJsPath")%>"/>
<input type="hidden" id="cmsPath" data_id="<%=ConfigurationUtil.getString("cmsPath")%>"/>
<input type="hidden" id="picPath" data_id="<%=ConfigurationUtil.getString("picPath")%>"/>
<input type="hidden" id="detailPath" data_id="<%=ConfigurationUtil.getString("detailPath")%>"/>
<input type="hidden" id="advPath" data_id="<%=ConfigurationUtil.getString("advPath")%>"/>
<input type="hidden" id="portalPath" data_id="<%=ConfigurationUtil.getString("portalPath")%>"/> 
<input type="hidden" id="staticPath" data_id="<%=ConfigurationUtil.getString("staticPath")%>"/> 
<input type="hidden" id="facade_path" data_id="<%=ConfigurationUtil.getString("searchPath")%>"/> 
