<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.kmzyc.zkconfig.ConfigurationUtil" %>

<!-- 顶部导航条 -->
<div class="i-topbar">
    <div class="l-w fn-clear">
        <p class="fn-left loginbar" id="loginbar">
            您好，欢迎来到康美中药城！
        </p>
        <ul class="fn-right topmenu">
            <li class="topmenu-item topmenu-item-phone">
                <i class="ico-phone">
                </i>
                客服热线：
                <strong>
                    400-6600-518
                </strong>
            </li>
            <li class="topmenu-item topmenu-item-collect">
                <b>
                </b>
                <i class="ico-collect">
                </i>
                <a href="javascript:void(0);" id="addCookie" title="康美中药城">
                    收藏本站
                </a>
            </li>
            <li class="topmenu-item">
                <b>
                </b>
                <a href="<%=ConfigurationUtil.getString("staticPath")%>/helps/sd/index.shtml">
                    帮助中心
                </a>
            </li>
              <li class="topmenu-item">
                <b>
                </b>
                <a href="<%=ConfigurationUtil.getString("staticPath")%>/app/download.html">
                    手机版
                </a>
            </li>
        </ul>
    </div>
</div>
<!-- 登录 -->

<!-- login END -->
  <%
application.setAttribute("cssAndJsPath", ConfigurationUtil.getString("cssAndJsPath"));
application.setAttribute("cmsPath", ConfigurationUtil.getString("cmsPath"));
application.setAttribute("picPath", ConfigurationUtil.getString("picPath"));
application.setAttribute("staticPath", ConfigurationUtil.getString("staticPath"));
application.setAttribute("portalPath", ConfigurationUtil.getString("portalPath"));
application.setAttribute("advPath", ConfigurationUtil.getString("advPath"));
application.setAttribute("detailPath", ConfigurationUtil.getString("detailPath"));
application.setAttribute("cmsRootPath", ConfigurationUtil.getString("CMS_ROOT_PATH"));
%>
<input type="hidden" id="cssAndJsPath" data_id="<%=ConfigurationUtil.getString("cssAndJsPath")%>"/>
<input type="hidden" id="cmsPath" data_id="<%=ConfigurationUtil.getString("cmsPath")%>"/>
<input type="hidden" id="picPath" data_id="<%=ConfigurationUtil.getString("picPath")%>"/>
<input type="hidden" id="detailPath" data_id="<%=ConfigurationUtil.getString("detailPath")%>"/>
<input type="hidden" id="advPath" data_id="<%=ConfigurationUtil.getString("advPath")%>"/>
<input type="hidden" id="portalPath" data_id="<%=ConfigurationUtil.getString("portalPath")%>"/> 
<input type="hidden" id="staticPath" data_id="<%=ConfigurationUtil.getString("staticPath")%>"/> 
  <input type="hidden" id="facade_path" data_id="<%=ConfigurationUtil.getString("searchPath")%>"/>
  <input type="hidden" id="gysPortalPath" data_id="http://gys.kmb2b.com">
  <input type="hidden" id="logSysPath" data_id="http://kma.kmb2b.com/weblog/b2b">
