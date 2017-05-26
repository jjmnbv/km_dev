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
            <div class="user-m fn-t10">
                <div class="mc">
                	<div class="ui-table">
                    	<table class="ui-table user-table ">
                            <thead>
                                <tr>
                                    <th class="td-s6">标题</th>
                                    <th class="td-s7">发送时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            <s:iterator value="pagintion.recordList">
                              <tr class="alink">
                                </tr>
                            	<s:if test="status==0">
	                             	<tr class="alink" >
	                                	<td class="fn-blue"><a href="
		                                        		<s:url action="readerSiteNotice" >
		                                        		 <s:param name="messageId" value="messageId" ></s:param>
		                                        		 </s:url>"><s:property value="title"/></a></td>
	                                    <td><s:date name="releaseDate" format="yyyy-MM-dd" /></td>
	                                    <td>
	                                        <p class="fn-blue"><span><a href="
		                                        		<s:url action="readerSiteNotice" >
		                                        		 <s:param name="messageId" value="messageId" ></s:param>
		                                        		 </s:url>">阅读</a></span>
	                                        	<span><a href="javascript:void(0);" data-id="<s:property value='messageId'/>" class="j_deleteSiteNotice">删除</a>
	                                        	  </span>
	                                        </p>
	                                    </td>
	                                </tr>
                                </s:if>
                                <s:else>
	                               	 <tr >
		                                	<td class="fn-mgray"><a href="
		                                        		<s:url action="readerSiteNotice" >
		                                        		 <s:param name="messageId" value="messageId" ></s:param>
		                                        		 </s:url>"><s:property value="title"/></a></td>
		                                    <td><s:date name="releaseDate" format="yyyy-MM-dd" /></td>
		                                    <td>
		                                        <p class="fn-mgray"><span><a href="
			                                        		<s:url action="readerSiteNotice" >
			                                        		 <s:param name="messageId" value="messageId" ></s:param>
			                                        		 </s:url>">阅读</a></span>
		                                        	<span><a href="javascript:void(0);"  data-id="<s:property value='messageId'/>" class="j_deleteSiteNotice">删除</a>
		                                        	  </span>
		                                        </p>
		                                    </td>
		                                </tr>
                                </s:else>
                            </s:iterator>
                            </tbody>
                        </table>
                        <!--站内通知分页-->
						<tiles:insertDefinition name="pagination"/>
						<!-- 站内通知分布END -->
                    </div>
                </div>
            </div>
          </div>
</s:form>
  </div>