<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<s:form id="sform" name="sform" action="queryFavoriteShopList.action" method="post" theme="simple">
	<s:hidden name="page" id="page" />
	<s:token></s:token>
	<div class="l-right user-m">
		<div class="o-mt">
			<h2>我的收藏</h2>
			
		</div>
		<div class="user-m fn-t20">
		<div class="mt">
            <ul class="tab">
                <li  ><s></s><b></b><a href="queryFavoriteList.action">商品收藏</a></li>
                <li class="curr"><s></s><b></b><a href="queryFavoriteShopList.action">店铺收藏</a></li>
            </ul>
        </div>
      <!--   <div class="Inquiry">
				
			</div> -->
			<div class="mc">
				<div class="ui-table">
				<table class="ui-table user-table">
                <tbody>
                    <tr>
                        <th>您收藏的店铺</th>
                        <th class="td-s7">所在地</th>
                        <th class="td-s5">综合评分</th>
                        <th class="td-s7">操作</th>
                    </tr>
                    <s:iterator value="#request.pagintion.recordList" var="entryVar" >
                    <tr>
                        <td><a target="_blank" href="<s:property  value='#entryVar.defaultDomainUrl'/>" class="fn-blue">
                            <s:property  value="#entryVar.shopName"/></a></td>
                        <td><s:property  value="#entryVar.province"/><s:property  value="#entryVar.city"/><s:property  value="#entryVar.area"/>
                     
                        </td>
                        <td>
                            <span class="star">
                                <span class="star-default">
                                
                                <s:if test="1>#entryVar.point">
                                    <span class="star-present s0"></span>
                                 </s:if>
                                 <s:elseif test="2>#entryVar.point">
                                   <span class="star-present s1"></span>
                                  </s:elseif>
                                  <s:elseif test="3>#entryVar.point">
                                   <span class="star-present s2"></span>
                                  </s:elseif>
                                  <s:elseif test="4>#entryVar.point">
                                   <span class="star-present s3"></span>
                                  </s:elseif>
                                  <s:elseif test="5>#entryVar.point">
                                   <span class="star-present s4"></span>
                                  </s:elseif>
                                  <s:elseif test="6>#entryVar.point">
                                   <span class="star-present s5"></span>
                                  </s:elseif>
                                    
                                </span>
                            </span>
                        </td>
                        <td>
                        <a class="green-btn fn-r5" target="_blank"  href="<s:property  value='#entryVar.defaultDomainUrl'/>">进入店铺</a>
                        <a class="deleteShop" data-id="<s:property  value='#entryVar.favoriteId'/>"  href="javascript:void(0);">删除</a></td>
                    </tr>
                    </s:iterator>
                    
            </tbody></table>
				</div>
				<div class="fn-tr fn-t10">
					<div class="ui-page">
						<!-- 分页组件 -->
            			<tiles:insertDefinition name="pagination"/>
					</div>
				</div>
			</div>
		</div>
	</div>
</s:form>
</div>
