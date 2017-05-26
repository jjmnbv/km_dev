<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<s:form id="sform" name="sform" action="toSiteNotice.action" method="post" theme="simple">
	<s:hidden name="page" id="page" />
	<s:token></s:token>
	 <div class="l-right user-m">
          	<div class="o-mt">
            	<h2>站内通知</h2>
            </div>
            <div class="inner-inform">
		     	<h1><s:property value="siteNoticeDetail.title"/></h1>
				<div class="inform-cont">${siteNoticeDetail.content }</div>
				<p class="time"><s:date name="siteNoticeDetail.releaseDate" format="yyyy-MM-dd"/></p>
				<p class="inform-tip">
					您之所以收到的站内通知，是因为您曾经注册成为康美中药城的用户。<br/>
					本通知由康美中药城系统自动发出，请勿直接回复！<br/>
					在购物中遇到任何问题，请点击<a href="javascript:void(0);" id="helpCenter_index">帮助中心。</a><br/>
					如果您有任何疑问或建议，请<a href="javascript:void(0);)" id="helpCenter_help_contact">联系我们</a>。<br/>
					康美中药城是专业的网上购药商城。
				</p>
			</div>
			
				<div class="button">
					<div>
						<a class="btn-submit j_returnNotice" href="javascript:void(0);"><span>返回</span></a>
					</div>
				</div>
          </div>
            <!--fn-right-->
        </div>
</s:form>
  </div>