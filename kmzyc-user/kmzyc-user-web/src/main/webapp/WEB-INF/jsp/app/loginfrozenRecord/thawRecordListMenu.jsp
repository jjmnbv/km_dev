<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>会员账号冻结/解冻</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
        <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
 		 <link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
 		 <script src="/etc/js/97dater/WdatePicker.js"></script>
		<script type="text/javascript">
    /** 删除账号冻结信息  **/
    function deleteSelected(id){
        var obj = document.getElementsByName(id);
                var count = 0;
                var obj_cehcked = null;
                // 遍历所有用户，找出被选中的用户
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
                          document.bnesFrozenRecordForm.action="/loginfrozenRecord/defrostingRecord_detele.action";
                          document.bnesFrozenRecordForm.submit();
                 }
    }
   /** 全选js  **/
      function checkAll(ck){
		  for (var i=0;i<ck.form.all.tags("input").length;i++){
		    var ele = ck.form.all.tags("input")[i];
		    /*var ct = ele.getAttribute("type");*/
		    if ((ele.type=="checkbox")){
		      if(ck.checked!=ele.checked)
		        ele.click();
		    }
		  }
    }
      /**  进入新账号冻结信息页面  **/
      function gotoAdd(){
           document.bnesFrozenRecordForm.action="/loginfrozenRecord/frozenRecord_preAdd.action";
           document.bnesFrozenRecordForm.submit();
      }
      /**  进入冻结账号信息页面  **/
      function gotoUpdateStatus(id){
      location.href="/loginfrozenRecord/frozenRecord_preUpdate.action?updateType=1&FRId="+id;    	
      }
    
         function queryUserInfo(id,type){
	  //  alert("aaaa"+type);
	   	if(type=='1'){
	   	    
	   	     dialog("选择会员账号","iframe:/userInfo/personalBasicInfo_prePersonDetail.action?loginId="+id,"900px","760px","iframe");
	   	}
	   	if(type=='2'){
	   	 
	   	    dialog("选择会员账号","iframe:/personalbasic/personalbasic_preExpertDetail.action?loginId="+id,"900px","760px","iframe");
	   	}
	   	if(type=='4'){
	   	  
	   	    dialog("选择会员账号","iframe:/userInfo/commercialTenantBasicInfo_preBusinessDetail.action?loginId="+id,"900px","760px","iframe");
	   	}
	   }
</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户资料'" scope="request" />
		<s:set name="name" value="'会员账号冻结/解冻'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form name="bnesFrozenRecordForm" method="post" onsubmit=" return checkAllTextValid(this)"
			action="/loginfrozenRecord/defrostingRecord_pageThawList.action">
		<s:token/>
			<!-- 查询条件区域 -->
			<table width="98%" height="60" class="content_table" align="center"
				cellpadding="0" cellspacing="0">
				<%---<tr>
					<td colspan="8">
					    	<input class="delBtn" type="button" value=""  onclick="deleteSelected('frozenRecordIds');">		
					</td>
				</tr> --%>
				<tr>
				<td colspan="8">
					   
				</tr>
				<tr>
						<td align="right">
							会员账号：
						</td>
						<td>
						   <s:if test="%{showType!=null}">
						   <input name="username" type="text"  readonly="readonly"
								value="<s:property value='bnesFrozenRecord.username'/>">
						   </s:if>
							<s:else>
							<input name="username" type="text"
								value="<s:property value='bnesFrozenRecord.username'/>">
							</s:else>
						</td>
						
						<!-- 2016-08-18隐藏 -->
							<input type="hidden" name="realName" type="text" value="<s:property value='bnesFrozenRecord.realName'/>">
					
						<td align="right">
							冻结操作账号：
						</td>
						<td>
							<input name="sueName" type="text"
								value="<s:property value='bnesFrozenRecord.sueName'/>">
						</td>
						
						<td align="right">
							解冻操作账号：
						</td>
						<td>
							<input name="sysUserName" type="text"
								value="<s:property value='bnesFrozenRecord.sysUserName'/>">
						</td>
						
						<!-- 2016-08-18隐藏 -->
							<input type="hidden" name="sueRealName" type="text" value="<s:property value='bnesFrozenRecord.sueRealName'/>">
						
						
						</td>
						
						
						
					</tr>
					<tr>
					<td align="right">
							冻结时间：
						</td>
						<td>
							
						<input type="text" id="d523" class="Wdate" readonly value="<s:date name = 'bnesFrozenRecord.sueDateStart' format='yyyy-MM-dd HH:mm:ss' />" name="bnesFrozenRecord.sueDateStart" onClick="WdatePicker({el:'d523',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
						  		至
						<input type="text" id="d524" class="Wdate" readonly value="<s:date name = 'bnesFrozenRecord.sueDateEnd' format='yyyy-MM-dd HH:mm:ss' />" name="bnesFrozenRecord.sueDateEnd" onClick="WdatePicker({el:'d524',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					
						
						</td>
						
						<td align="right">
							解冻时间：
						</td>
						<td>
						<input type="text" id="operatorDateStart" class="Wdate" readonly value="<s:date name = 'bnesFrozenRecord.operatorDateStart' format='yyyy-MM-dd HH:mm:ss' />" name="bnesFrozenRecord.operatorDateStart" onClick="WdatePicker({el:'operatorDateStart',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
						  		至
						<input type="text" id="operatorDateEnd" class="Wdate" readonly value="<s:date name = 'bnesFrozenRecord.operatorDateEnd' format='yyyy-MM-dd HH:mm:ss' />" name="bnesFrozenRecord.operatorDateEnd" onClick="WdatePicker({el:'operatorDateEnd',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
											
						</td>
						
						<td align="right">
							状态：
						</td>
						<td>
						  <select name="status">
							<option value=""
								<s:if test='bnesFrozenRecord.status==""'>selected="selected"</s:if>>
							请选择</option>
							<option value="0"
								<s:if test='bnesFrozenRecord.status=="0"'>selected="selected"</s:if>>
							已冻结</option>
							<option value="1"
								<s:if test='bnesFrozenRecord.status=="1"'>selected="selected"</s:if>>
							已解冻</option>
						</select>
						</td>
                        <td align="right">
							<INPUT TYPE="submit" class="queryBtn" value="">
                              <s:if test="%{showType!=null}">
					    </s:if>
					    <s:else>
					       	<input TYPE="button" value="添加冻结账号" class="btn-custom" onClick="gotoAdd();">
					    </s:else>
						</td>
						
					</tr>
			</table>

			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1">
				<tr>
					<%--- <th align="center" width="5%">
						<input type='checkbox' name='allbox' onclick='checkAll(this)'>
					</th>--%>
					<th>
						会员账号
					</th>
					<th>
						冻结操作账号
					</th>
					<th>
						冻结原因
					</th>
					<th>
						冻结日期
					</th>
					<th>
						解冻操作账号
					</th>
					<th>
						解冻原因
					</th>
					<th>
						解冻日期
					</th>
					<th>
						冻结状态
					</th>
					<th>
						操作
					</th>
				</tr>
			</table>
			<div style=" text-align: center;">请输入具体条件进行查询,如想查询全部数据请直接点击查询按钮</div>
			
		</s:form>
		</div>
	</body>
	<!-- 消息提示 -->
	<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</html>

