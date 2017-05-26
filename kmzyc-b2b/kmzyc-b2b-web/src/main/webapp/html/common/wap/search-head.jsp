<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.kmzyc.zkconfig.ConfigurationUtil" %>
 <!--头部-->
    <header class="header">
        <div class="topbar">
            <figure class="logo">
                <figcaption>
                    <a href="<%=ConfigurationUtil.getString("staticPath_WAP")%>/index.html">
                        <div class="logo-l">
                            <span class="icon-icon"></span>
                            <span class="icon-logo-text"></span>
                        </div>
                        <div class="logo-r">
                            <span class="icon-name-c"></span>
                            <span class="icon-name-e"></span>
                        </div>
                    </a>
                </figcaption>
            </figure>
        </div>
    </header>

<input type="hidden" id="cssAndJsPath" data_id="<%=ConfigurationUtil.getString("cssAndJsPath_WAP")%>"/>

<input type="hidden" id="cmsPath" data_id="<%=ConfigurationUtil.getString("cmsPath_WAP")%>"/>

<input type="hidden" id="picPath" data_id="<%=ConfigurationUtil.getString("picPath_WAP")%>"/>

<input type="hidden" id="detailPath" data_id="<%=ConfigurationUtil.getString("detailPath_WAP")%>"/>

<input type="hidden" id="advPath" data_id="<%=ConfigurationUtil.getString("advPath_WAP")%>"/>

<input type="hidden" id="portalPath" data_id="<%=ConfigurationUtil.getString("portalPath_WAP")%>"/> 

<input type="hidden" id="staticPath" data_id="<%=ConfigurationUtil.getString("staticPath_WAP")%>"/>

<input type="hidden" id="facade_path" data_id="<%=ConfigurationUtil.getString("searchPath_WAP")%>"/> 

<input type="hidden" id="searchPath" data_id="<%=ConfigurationUtil.getString("facadePath_WAP")%>"/>