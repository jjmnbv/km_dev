<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资讯类别信息管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
<script type="text/javascript"  src="/etc/js/rowDisplay.js"></script>
 <Script src="/etc/js/97dater/WdatePicker.js"></Script> 
<script type="text/javascript">
       $(document).ready(function(){
    		var checks = "";
    		checks = $("#checkeds").val();
    		if(checks!=""){
        		var checkboxs = document.getElementsByName("promotionIds");
        		var myarr = new Array();
    			myarr = checks.split(',');
        		for(var i = 0; i<checkboxs.length; i++){
            		for(var j = 0; j<myarr.length; j++){
                		if(checkboxs[i].value==myarr[j]){
                			checkboxs[i].checked = true;
                			break;
                    	}
                	}
            	}
        	}
	   });
      
        /** 选择团出窗口数据  **/
         function selectOneAccount(){
           	var checkeds = "";
           	checkeds = $("#checkeds").val();
           	var windowId = $("#windowId").val();
           	if(checkeds == ""){
           		var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>请选择!";
                messageDialog("消息提示","text:"+msg ,"300px","102px","text");
                var timer_alert = setTimeout(function() {
        				messageCloseThis();
        			}, 2000);
                return;
            }
	           parent.closeInformationType(checkeds,windowId);
         }

         
</script>
</head>
<body>
<s:form  name="viewProductInfoForm" action="/cms/InformationType_queryPopInformationType.action" 
 method="post">
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0"  style="margin:10 0 10 0px;">
	<tr>
		<td>
						类别名称：
						<input name="keyWords.name_keyword" type="text"  
							value="<s:property value="keyWords.name_keyword"/>">
					</td>
					<td align="left" width="30%">
						
	                               有效性： <select name="keyWords.status_keyword" id="infortype.status">
					         <option value="" <s:if test="infortype.status==null">selected="selected"</s:if> >全部</option>
					          <option value="0" <s:if test="infortype.status==0">selected="selected"</s:if> >有效</option>
					           <option value="1" <s:if test="infortype.status==1">selected="selected"</s:if> >无效</option>
					         </select>	

					</td>
		<td align="center">
			<input type="submit" class="queryBtn" value="">
		</td>
		
	</tr>
	<tr>
		
		
		<input type="hidden" name="checkeds" id="checkeds" value="<s:property value='checkeds'/>"/>
		
		<input type="hidden" id="windowId" name="windowId" value='<s:property value="windowId"/>'>
		<input type="hidden" id="pageId" name="pageId" value='<s:property value="pageId"/>'>
		<input type="hidden" id="adminType" name="adminType" value='<s:property value="adminType"/>'>
		
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th><input type='checkbox' name='promotion' id="promotion"  onclick="checkAllpop(this,'promotionIds')"></th>
					<th>
						 编号
					</th>
					<th>
						资讯类别名称
						
					</th>
					<th>
						排序
					</th>
					<th>
						状态<!--0.有效1.无效-->
					</th>
					
					<th>
						创建日期
					</th>
					<th width="80">
						创建人
					</th>
					<th >
					         修改日期
					</th>
					<th >
					         修改人
					</th>
	</tr>
	<s:iterator id="viewPromotioniterator"  value="page.dataList">
	<tr>
	     				<td width="5%">
							<s:if test="flag!=1"><input type="checkbox" id="promotionIds" name="promotionIds" value='<s:property value="id"/>' onclick="checkpromotionId(this);">
							</s:if>
						</td>
						<td>
						<s:property value="id"/>
						</td>
						<td>
							<s:property value="name"/>
						</td>
						<td>
							
							<s:property value="orders"/>
						</td>
						
						<td>
						   
						    <s:if test="status==0">有效</s:if>
						    <s:if test="status==1">无效</s:if>
						</td>
							
						
						<td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/>
					     	
						</td>
						<td>
								<s:property value="sysUserMap[created]"/>
						</td>
						<td><s:date name="modifyDate" format="yyyy-MM-dd HH:mm:ss"/>
							
						</td>
						<td>
							<s:property value="sysUserMap[modified]"/>
						</td>
						
						
	</tr>
	</s:iterator>
</table>

<table width="98%"  align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'viewProductInfoForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>

<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<input class="saveBtn" type="button" value=" " onclick="selectOneAccount();">
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

</s:form>
</body>
</html>

