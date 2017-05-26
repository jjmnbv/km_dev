<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
   <!--fn-left end-->
   <form action="queryPrizeInvite.action" id="prizeInviteFrom" method="post" >
   <s:hidden name="page" id="page" />
   <input type="hidden" id="jsAndcss" value='${jsAndcss}' />
    <input type="hidden" id="rulesMoney" value="<s:property  value="rulesMoney"  />" />
   <s:token></s:token>
    <div class="l-right user-m">
        <div class="o-mt">
            <h2>邀请好友</h2>
        </div>
        <div class="fri-text">
            <p class="fri-wz">成功邀请一个好友加入，最高可获得奖励<span><s:property  value="rulesMoney"  /></span>元！</p>
            <div>
                <span class="lifs-wz">立即发送到：</span>
                <div class="fri-fx">
           			     <div class="bdsharebuttonbox">
						 	<a href="javascript:void(0)" class="bds_more" data-cmd="more"></a>
						 	<a href="javascript:void(0)" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
						 <!-- 	<a href="javascript:void(0)" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a> -->
						 	<a href="javascript:void(0)" class="bds_sqq" data-cmd="sqq" title="分享到QQ好友"></a><a href="javascript:void(0)" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
						 	<a href="javascript:void(0)" class="bds_renren" data-cmd="renren" title="分享到人人网"></a>
						 </div>
                </div>
            </div>
            
            
            <br>
            
            <div class="fri-lianjie">
                <strong>专属邀请链接：</strong>
                <input id="inviteHref"  value="<s:property value="myInvitHref" />" class="fri-text-date" name="myInvitHref" type="text">
                <input type="hidden" id="inviteHrefHide"  value="我已注册康美中药城商城，健康分享，邀你同行！<s:property value="myInvitHref" />" class="fri-text-date" >
                <span class="fri-but"><a id="copyB" href="javascript:void(0)">复制链接</a></span><br/>
              	<!-- 二维码图片 -->    
	              <div  id="qr_code" style="z-index:2;display:none" class="er-img"  > 
	              
	              </div>
            </div>
        </div>
        
         <div id="qrcodeTable"></div>
        <%@ include file="/html/common/adv/inviteFriends_banner.shtml" %> 
         <%--  <jsp:include page="/protal/adv/inviteFriends_banner.shtml"> --%>
      <!--   <div class="fri-banner"></div> -->
        
        
        
        <div class="user-m fn-t10">
            <div class="mt">
                <ul class="tab">
                    <li class="curr"><s></s><b></b><a>已邀请会员明细</a></li>
                    <span class="jl-right">奖励金额共计：<strong class="fn-red"><s:property value="myPrizeMoney" /></strong>元</span>
                </ul>

            </div>

            <div class="mc">
                <div class="Inquiry" >
                   
                    <div class="ui-form">
                        <input name="searchKeyword.keyword" type="text"  value="<s:property value="searchKeyword.keyword" />"  class="u-text hy-name"  >
                        <label>超始日期：</label><input id="d4311"   class="u-text-date Wdate" name="searchKeyword.dateBegin" value="<s:property value="searchKeyword.dateBegin" />" type="text">
                        <label>截止日期：</label><input id="d4312"   class="u-text-date Wdate" name="searchKeyword.dateEnd" value="<s:property value="searchKeyword.dateEnd" />" type="text">
                        <input name="" type="button" value="查 询" class="btn-b btn-b-success" id="sub_but">
                    </div>
                </div>
                <div class="ui-table">
                    <table class="ui-table user-table ">
                        <thead>
                        <tr>
                            <th class="td-s7">已邀请会员</th>
                            <th class="td-s5 td-128">注册时间</th>
                            <th class="td-s5 td-128">注册奖励</th>
                            <th class="td-s5 td-128">手机验证奖励</th>
                            <th class="td-s5 td-128">首次购物奖励</th>
                            <th>奖励小计</th>
                        </tr>
                        </thead> 
                        
	                        <tbody>
	                        <s:iterator value="#request.pagintion.recordList" var="prizeInvite" status="prizeInviteS" >
		                        <tr>
		                            <td>  <s:property  value="#prizeInvite.accountLogin" /> </td>
		                            <td><s:date name="#prizeInvite.createDate" format="yyyy-MM-dd HH:mm:ss" /></td>
		                            <td><s:property  value="#prizeInvite.registMoney" /></td>
		                            <td><s:property  value="#prizeInvite.telRegistMoney" /></td>
		                            <td> <s:property  value="#prizeInvite.fristByMoney" /></td>
		                            <td> <s:property  value="#prizeInvite.amount" /></td>
		                        </tr>
							</s:iterator>
	                        </tbody>
                    </table>
                </div>
            </div>
          <div class="fn-tr fn-t10">
					<div class="ui-page">
						<!-- 分页组件 -->

						<tiles:insertDefinition name="pagination" />

					</div>
				</div>
        </div>
    </div>
    <!--fn-right-->
    </form>
    
     <script type="text/javascript">
    var sharehref = document.getElementById("inviteHref").value;
    var jsAndcss= document.getElementById("jsAndcss").value+"res/images/common/200-200.jpg";
    var rulesMoney = "邀请有奖，成功邀请一个好友加入康美中药城商城，可获得奖励"+document.getElementById("rulesMoney").value+"元";
    
   window._bd_share_config=
   {"common":
   {"bdUrl":sharehref,
    "bdSnsKey":{},
    "bdText":rulesMoney,
    "bdDesc":"我已注册康美中药城商城，健康分享，邀你同行！",
    "bdComment":"康美中药城商城，一站式健康服务平台!网上药店、健康管理、虚拟医院一应俱全!",
    "bdMini":"2",
    "bdMiniList":false,
    "bdPic": jsAndcss,
    "bdStyle":"0","bdSize":"16"},
    "share":{}
    };
   with(document)0[(
		   getElementsByTagName('head')[0]||body).
	  		 appendChild(createElement('script')).
  		 src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];
   </script>
</div>
