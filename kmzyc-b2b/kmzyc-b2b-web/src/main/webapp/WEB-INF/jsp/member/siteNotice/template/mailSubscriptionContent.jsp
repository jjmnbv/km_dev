<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<s:form id="sform" name="sform" action="addComplaint.action" method="post" theme="simple">
	<s:hidden name="page" id="page" />
	<s:token></s:token>
	<div class="l-right user-m">
          	<div class="o-mt">
            	<h2>短信订阅</h2>
            </div>
            <div class="m-w w-noborder fn-clear fn-t10">
            	<div class="wh"><h3>短信订阅</h3></div>
                <div class="wc">
                	<div class="tb-void">
                		<strong class="remindlist-tit fn-t10">购物短信提醒：</strong>
                    	<ul class="remindlist">
	                    	<s:iterator value="emailRrsInfoList" status="status" id="infoList">
	                    		<s:set name="flag" value="0"  >  </s:set>  
	                    		<s:if test="emailRrsParentId==21">
	                            	<s:iterator value="emailRrsHisList" id="hisList">
	                    			  <s:if test="#infoList.emailRrsId == #hisList.emailRrsId">
	                    				 <s:set name="flag"  value="1" > </s:set>
	                    			  </s:if>	
                    			 	</s:iterator>
                    			 
	                    			 <s:if test="#flag==0"  >
	                    			 	<li><input id="<s:property value='emailRrsId'/>" type="checkbox" name="emailRrsId" value="<s:property value='emailRrsId'/>"/><label for="<s:property value='emailRrsId'/>"><s:property value="emailRrsName"/></label> </li> 
	                    			  </s:if>
	                    			 <s:elseif test="#flag==1"  >
	                    				<li><input id="<s:property value='emailRrsId'/>" type="checkbox" name="emailRrsId" checked="checked" value="<s:property value='emailRrsId'/>"/><label for="<s:property value='emailRrsId'/>"><s:property value="emailRrsName"/></label> </li> 
	                    			  </s:elseif>
	                            </s:if>
	                    	</s:iterator>
                        </ul>
                        <strong class="remindlist-tit fn-t10">账户短信提醒：</strong>
                    	<ul class="remindlist">
	                    	<!-- 如果父类型为购物提醒邮件(emailRrsId=2) -->
	                    	<s:iterator value="emailRrsInfoList" status="status" id="infoList">
	                    		<s:set name="flag" value="0"  >  </s:set>  
	                    		<s:if test="emailRrsParentId==17">
	                            	<s:iterator value="emailRrsHisList" id="hisList">
	                    			  <s:if test="#infoList.emailRrsId == #hisList.emailRrsId">
	                    				 <s:set name="flag"  value="1" > </s:set>
	                    			  </s:if>	
                    			 	</s:iterator>
                    			 
	                    			 <s:if test="#flag==0"  >
	                    			 	<li><input id="<s:property value='emailRrsId'/>" type="checkbox" name="emailRrsId" value="<s:property value='emailRrsId'/>"/><label for="<s:property value='emailRrsId'/>"><s:property value="emailRrsName"/></label> </li> 
	                    			  </s:if>
	                    			 <s:elseif test="#flag==1"  >
	                    				<li><input id="<s:property value='emailRrsId'/>" type="checkbox" name="emailRrsId" checked="checked" value="<s:property value='emailRrsId'/>"/><label for="<s:property value='emailRrsId'/>"><s:property value="emailRrsName"/></label> </li> 
	                    			  </s:elseif>
	                            </s:if>
	                    	</s:iterator>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="m-w w-noborder fn-clear fn-t10">
           <!--    <div class="wh"><h3>短信订阅</h3></div> -->
                <div class="wc">
                	<div class="tb-void">
                    	<div class="tb-void">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tbody>
                                   <tr class="">
                                <td class="td-s3"><span class="checkFather"><input id="cb_subscribeAll" type="checkbox" name="cb_subscribeAll"  class="j_checkAllFather"/><label for="cb_subscribeAll">全选</label></span></td>
                                <td colspan="3" style="border-left:0;">
                                    <div class="o-mb">
                                        <div class="btns">
                                            <a class="btn-add j_confirmEmailSubscribe" href="javascript:void(0);" ><span >确认</span></a>
                                            <span class="fn-red">如要修改/退订，请在相应的复选框内勾选或取消勾选并点“确认”按钮即可。</span></div>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                            </table>    
                        </div>
                    </div>
                </div>
            </div>
         
            <!--fn-right-->
        </div>
</s:form>
  </div>
  