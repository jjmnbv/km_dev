<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<s:form id="faqForm" name="sform" action="queryFAQList.action" method="post" theme="simple">
	<s:hidden name="page" id="page"/>
	<s:token></s:token>
 	<div class="l-right user-m">
        <div class="o-mt">
             <h2  class="user-title" style="border-bottom:none">FAQ常见问题</h2>
             <div class="help-faq-m">
                 <ul>
                 	<s:iterator value="#request.pagintion.recordList" var="infoVar" status="infoVarStatus">
                    <li>
                       <div><span class="icon-ask"></span><span class="ask">${infoVar.name}</span></div>
                       <div><span class="icon-ans"></span><span class="ans">${infoVar.description}</span></div>
                    </li>
                	</s:iterator>
                 </ul>
			  </div>
			  <div class="fn-tr fn-t10">
				<div class="ui-page">
           				<tiles:insertDefinition name="pagination"/>
				</div>
			  </div>
         </div>			
      <!--fn-right-->
     </div>
</s:form>


 
