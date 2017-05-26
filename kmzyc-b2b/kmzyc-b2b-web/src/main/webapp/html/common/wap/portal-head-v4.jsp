<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="com.kmzyc.zkconfig.ConfigurationUtil" %>
 <!--头部--><!--
    <header class="header">
        <div class="topbar">
            <figure class="logo">
            <a href="http://m.kmb2b.com"><span class="icon-wap-logo"><span class="path1"></span><span class="path2"></span><span class="path3"></span><span class="path4"></span><span class="path5"></span><span class="path6"></span><span class="path7"></span><span class="path8"></span><span class="path9"></span><span class="path10"></span><span class="path11"></span><span class="path12"></span><span class="path13"></span><span class="path14"></span><span class="path15"></span><span class="path16"></span><span class="path17"></span><span class="path18"></span><span class="path19"></span><span class="path20"></span><span class="path21"></span><span class="path22"></span><span class="path23"></span><span class="path24"></span><span class="path25"></span><span class="path26"></span><span class="path27"></span><span class="path28"></span><span class="path29"></span><span class="path30"></span><span class="path31"></span><span class="path32"></span><span class="path33"></span><span class="path34"></span><span class="path35"></span><span class="path36"></span><span class="path37"></span><span class="path38"></span><span class="path39"></span><span class="path40"></span><span class="path41"></span><span class="path42"></span><span class="path43"></span><span class="path44"></span><span class="path45"></span><span class="path46"></span><span class="path47"></span><span class="path48"></span><span class="path49"></span><span class="path50"></span><span class="path51"></span><span class="path52"></span><span class="path53"></span><span class="path54"></span><span class="path55"></span><span class="path56"></span><span class="path57"></span></span></a>
             
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
          <div class="mainnav">
                <span class="icon-list-unordered"></span>
                <nav style="display: none;">
                    <ul>
                        <li><a href="<%=ConfigurationUtil.getString("portalPath_WAP")%>/member/goWapMyHome.action">我的时代</a></li>
                        <li><a href="<%=ConfigurationUtil.getString("staticPath_WAP")%>/wap/category.html">搜索</a></li>
                        <li><a href="javascript:;" class="close close-absolute">X</a> </li>
                    </ul>
                </nav>
        </div>
       </div>
       <div class="head-login" id="loginbar2">
           
              <span class="shop-cart"><a href="<%=ConfigurationUtil.getString("portalPath_WAP")%>/cart/listWapShopCar.action" class="icon-cart2"><i class="num j_shopcartnum">0</i></a></span>
        </div>
    </header>

-->
<script src="http://10.1.0.213:81/reswap/js/base/jquery/2.1.1/jquery.js"></script>
<script src="http://10.1.0.213:81/reswap/js/common/bootstrap.js"></script>

<%String titlePrefix = request.getParameter("titlePrefix"); %>
<header>
    <div class="header-inner">
        <div class="pages-hd">
            <a class="back icon-uniE61E" href="javascript:history.back(0);"></a>
            <h2><%=titlePrefix%></h2>
            <div class="sortlist">
                <span class="icon-uniE63A"></span>
            </div>
        </div>
        <div class="navigation" style="display: none;">
            <ul>
                <li>
                    <a href="<%=ConfigurationUtil.getString("staticPath_WAP")%>/index.html" class="on">
                        <span class="menu-icon icon-uniE612"></span>
                        <span class="menu-txt">首页</span>
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0)">
                        <span class="menu-icon icon-uniE639"></span>
                        <span class="menu-txt">分类</span>
                    </a>
                </li>
                <li>
                    <a href="<%=ConfigurationUtil.getString("portalPath_WAP")%>/cart/listWapShopCar.action">
                        <span class="menu-icon icon-uniE62C"></span>
                        <span class="menu-txt">购物车</span>
                    </a>
                </li>
                <li>
                    <a href="<%=ConfigurationUtil.getString("portalPath_WAP")%>/member/goWapMyHome.action">
                        <span class="menu-icon icon-uniE602"></span>
                        <span class="menu-txt">我的</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</header>
