<%@ page language="java" import="java.util.*,com.kmzyc.zkconfig.ConfigurationUtil" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<div class="orderinfo regfree l-w">
	<div class="orderinfo-hd fn-clear">
		<h1 class="fn-f18"><a href="javascript:void(0)">免注册购物转为会员</a></h1>
	</div>
	<div class="Re-password-pages  fn-t10">
	  	<div class="procedure fn-t10 w306">
	    	<ul>
	      		<li class="black">1、填写买家信息<b class="background-dd"></b></li>
	      		<li class="black">2、验证身份<b class="background-dd"></b></li>
	      		<li class="black">3、设置用户名和密码<b class="background-dg"></b></li>
                <li class="green">4、完成</li>
        	</ul>
      	</div>
	  	<div class="tips-green fn-t10">
	    	<div class="i-modify fn-t10 fn-l200 ">
	      		<ul>
	        		<li>
		        		<s:actionerror id="errorDiv" />
		        		<div id="resultDiv">
		        		<s:if test="#request.info == 'success'">
	          			<div class="tips"><i></i>用户名和密码设置成功！
                    	<p>到<a href="/member/goHome.action" class="fn-blue">我的时代</a>查看我的会员权益。</p>
                    	<p>看看今天有什么<a href="<%=ConfigurationUtil.getString("CMS_ROOT_PATH")%>activitylist.html" class="fn-blue">特价促销商品</a></p>
                    	</div>
                    	</s:if>
                    	<s:else><div class="tips">免注册转会员失败，请稍后再试！</div></s:else>
                    	</div>
            		</li>
          		</ul>
        	</div>
      	</div>
  	</div>
</div>
