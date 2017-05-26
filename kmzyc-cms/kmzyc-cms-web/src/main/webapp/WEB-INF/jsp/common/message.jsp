<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!-- 消息提示 -->
<div width="100%">
<!-- 消息提示 -->
		 <s:if test="hasActionMessages()">
		      <s:iterator value="actionMessages" >         
		         <script language="JavaScript">       
		                var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/><s:property escape="false"/>";
		                messageDialog("消息提示","text:"+msg ,"300px","102px","text");  
		            </script>         
	       </s:iterator> 
	    </s:if>
	     <s:if test="hasActionErrors()">         
	         <s:iterator value="actionErrors">         
	                 <script language="JavaScript">       
		                var msg="<img src='../etc/images/tip.png' style='vertical-align:middle;'/><s:property escape="false"/>";
		                messageDialog("消息提示","text:<font color='red'>"+msg+"</font>" ,"300px","100px","text");  
		            </script>             
	            </s:iterator>         
         </s:if>    
         <script type="text/javascript">
         $(document).ready(function(){
 			var timer_alert = setTimeout(function() {
 				messageCloseThis();
 			}, 2000);
         });
         </script>     
</div>