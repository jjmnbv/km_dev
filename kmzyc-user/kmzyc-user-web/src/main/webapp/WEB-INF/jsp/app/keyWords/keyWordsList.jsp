<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>敏感词信息</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
			type="text/css" />
		<script src="/etc/js/dialog.js">
</script>
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js">
</script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js">
</script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js">
</script>
		<script type="text/javascript" src="/etc/js/messages_cn.js">
</script>
		<script type="text/javascript" src="/etc/js/pageCommon.js">
</script>
		<script type="text/javascript">

		function deleteSelected(id) {
			var obj = document.getElementsByName(id);
			var count = 0;
			var obj_cehcked = null;
		<%-- 遍历所有用户，找出被选中的用户--%>
		            for (var i = 0; i < obj.length; i++) {
		                if (obj[i].checked) {
		                    count++;
		                    obj_cehcked = obj[i];
		                }
		            }
		             if (count == 0) {
		                    alert("请选择要删除的数据。");
		                    return false;
		             }else if(confirm('是否确认删除?')==true){ 
		                
		            	  document.keyWordsForm.action="/userInfo/keyWords_deleteAllKeyWords.action ";
		         	     document.keyWordsForm.submit();
		             }
		}


<%--/**  进入新增加信息页面  **/--%>

function gotoAdd(){
     document.keyWordsForm.action="/userInfo/keyWords_addKeyWords.action ";
     document.keyWordsForm.submit();
}
<%--/** 进入修改信息页面  **/--%>
function editPayment(id){
     location.href="/userInfo/keyWords_selectByKeyWords.action?keyWordsId="+id;
}

<%--单条删除信息 --%>
function  deleteByKey(id){
     if(confirm("是否确认删除? ")==true){
    	 location.href="/userInfo/keyWords_deleteKeyWords.action?keyWordsId="+id;
     }
}
 

     
     <%--//弹出层 选择账号 --%>
     
 	function popUpAccount() {
 	 
 	    dialog("选择账号","iframe:/accounts/accountInfo_popUpAccount.action" ,"900px","500px","iframe");
 	}
 
 	<%-- //关闭弹出窗口 --%>
 	
	function closeOpenAccount(accountId,account){
	    closeThis();
	    <%--document.forms[0].accountId.value = accountId;--%>
		document.forms[0].accountLogin.value = account;
	}
	
	    //账号层
function queryAccountInfo(id) {
    dialog("选择会员账号","iframe:/accounts/accountInfo_preDetail.action?showType=4&n_AccountId="+id ,"900px","760px","iframe");
    
    
}
</script>

	</head>
	<body>

		<s:set name="parent_name" value="'客户成长'" scope="request" />
		<s:set name="name" value="'敏感词过滤'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div style="height: 90%; overflow-y: auto;">
		<s:form  name="keyWordsForm" action="/userInfo/keyWords_pageList.action" 
onsubmit=" return checkAllTextValid(this)" method="post">
		<table width="98%" align="center" class="content_table" border="0" cellpadding="0" cellspacing="0">
	
	<tr width="80%">
	    <td >敏感词：
		     <input name="keyWords" type="text" value="<s:property value='keyWordsQuery.keyWords'/>">
		</td>
		
		<td align="right"  >
						<INPUT TYPE="submit" class="queryBtn" value="">&nbsp;&nbsp;
                        <input class="addBtn" TYPE="button" value="" onClick="gotoAdd();">&nbsp;&nbsp;
						<input class="delBtn" type="button" value="" onClick="deleteSelected('keyWordsIds');">
					</td>
	</tr>
</table>
				<%-- 数据列表区域 --%>
				<table width="98%" class="list_table" cellpadding="3" align="center"
					cellspacing="0" border="1">
					<tr>
						<th width="5%">
						<input type='checkbox' name='allbox' onclick="checkAll(this,'keyWordsIds')">
					</th>
						<th width="20%">
						敏感词
						</th>
						<th width="15%">
							敏感词替换为
						</th>
						<th width="10%">
							状态
						</th>
						<th width="15%">
							敏感词类型
						</th>
						<th width="15%">
							创建日期
						</th>
						<th width="15%">
							修改日期
						</th>
						<th width="10%">
							操作
						</th>

					</tr>


					<s:iterator id="custiterator" value="page.dataList">
						<tr>
							 <td width="5%">
							<input type="checkbox" name="keyWordsIds"
								value='<s:property value="keyWordsId"/>' />
						</td>
							<td>

								<s:property
										value="keyWords" />
								
							</td>
							<td>
								<s:property value="repWords" />
							</td>
							<td>
								<s:if test="status==0">
无效
</s:if>
					<s:if test="status==1">
有效
</s:if>
							</td>

							<td>
								<s:if test="wordsType==1">
有效（前端输入内容）
</s:if>
								<s:if test="wordsType==2">
用户名
</s:if>
		
							
							</td>
							<td>
						
								<s:date name="createDate" format="yy-MM-dd HH:mm:ss" />
							

							</td>
							<td>
								<s:date name="modifyDate" format="yy-MM-dd HH:mm:ss" />
							</td>
							<td>
<img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"  onclick="editPayment(<s:property value="keyWordsId"/>)" />
<img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value="keyWordsId"/>)" />
							
						</td>
	
						</tr>
					</s:iterator>
				</table>
				<table width="98%" align="right">
					<tr>
						<td>
							<s:set name="form_name" value="'keyWordsForm'" scope="request"></s:set>
							<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</s:form>
		</div>
	</body>
</html>
