<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
 

 <input type="hidden" id="defaultProductImg_id"  value="<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err170_170.jpg"/>
 <!-- 用户头像路径 -->
 <input type="hidden" id="userImag" value="<%=ConfigurationUtil.getString("USER_IMG_PATH")%>" />
 <!-- 产品链接 -->
 <input type="hidden" id="productPath" value="<%=ConfigurationUtil.getString("productPicPath_WAP")%>"  />
   
<div class="container text-center">
   <input id="show" type="hidden" value='<s:property value="show"/>' />  
    <div class="btn-group tabli" role="group" aria-label="Basic example">
          <button type="button" class="btn btn-default btn-success "   id="s_myFav" j_type="1"  data-page="1" total-page="100" >我的收藏</button>
          <button type="button" class="btn btn-default" id="s_myView" j_type="2" data-page="1" total-page="100">浏览记录</button>
    </div>
</div>

<div class="container" id="F_list">
  
</div>

<div class="container" id="V_list">
</div>
 <div class="body-load text-center text-success" id="loadingIn" >
                     	<i class="icon icon-spinner icon-xs"></i>加载中，请稍后...
                     </div>
                     <div class="body-load text-center text-error" id="loadingFail" style="display: none;">
                     	<i class="icon icon-frown-o icon-xs"></i>加载失败，请重试...
                     	</div>
                     	 <div class="body-load text-center text-success" id="nextPage" style="display: none" data-status="" >
                     				 加载更多
                        </div>
