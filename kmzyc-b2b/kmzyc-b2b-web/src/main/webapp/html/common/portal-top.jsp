<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.kmzyc.zkconfig.ConfigurationUtil" %>

<input type="hidden" id="cssAndJsPath" data_id="<%=ConfigurationUtil.getString("cssAndJsPath")%>"/>
<input type="hidden" id="cmsPath" data_id="<%=ConfigurationUtil.getString("cmsPath")%>"/>
<input type="hidden" id="picPath" data_id="<%=ConfigurationUtil.getString("picPath")%>"/>
<input type="hidden" id="detailPath" data_id="<%=ConfigurationUtil.getString("detailPath")%>"/>
<input type="hidden" id="advPath" data_id="<%=ConfigurationUtil.getString("advPath")%>"/>
<input type="hidden" id="portalPath" data_id="<%=ConfigurationUtil.getString("portalPath")%>"/>
<input type="hidden" id="staticPath" data_id="<%=ConfigurationUtil.getString("staticPath")%>"/>
<input type="hidden" id="facade_path" data_id="<%=ConfigurationUtil.getString("searchPath")%>"/>
<!-- 顶部导航条 -->
<div class="i-topbar">
    <div class="l-w fn-clear">
        <p class="fn-left loginbar" id="loginbar">
            欢迎来到康美中药城&emsp;&emsp;<a id="displayLogin2" href="javascript:void(0);">请登录</a>&emsp;&emsp;<a href="javascript:void(0);">免费注册</a>
        </p>
        <ul class="fn-right topmenu">
          <li class="topmenu-item topmenu-item-app"><a href="http://www.kmb2b.com">官方首页</a></li>
          <li class="topmenu-item topmenu-item-app"><a href="http://www.kmb2b.com">会员中心</a></li>
          <li class="topmenu-item topmenu-item-app"><a href="http://www.kmb2b.com">采购单</a></li>
          <li class="topmenu-item topmenu-item-app"><a href="http://www.kmb2b.com">我的收藏</a></li>
          <li class="topmenu-item topmenu-item-app"><a href="http://www.kmb2b.com">我是卖家</a></li>
          <li class="topmenu-item topmenu-item-app"><a href="http://www.kmb2b.com">客服中心</a></li>
          <li class="topmenu-item topmenu-item-app"><a href="http://www.kmb2b.com">网站导航</a></li>
        </ul>
    </div>
</div>
  