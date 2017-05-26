<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>会员账号冻结</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
        <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript">
    /**  进入新账号冻结信息页面  **/
    function gotoAdd(){
         document.bnesFrozenRecordForm.action="/loginfrozenRecord/frozenRecord_preAdd.action";
         document.bnesFrozenRecordForm.submit();
    }

     /**  进入冻结账号信息页面  **/
    function gotoUpdateStatus(id){
    location.href="/loginfrozenRecord/frozenRecord_preUpdate.action?updateType=1&FRId="+id;    	
    }
    
     /** 根据参数showType进入冻结账号信息页面  **/
    function byShowTypeStatus(id,showType){
      location.href="/loginfrozenRecord/frozenRecord_preUpdate.action?updateType=1&bnesFrozenRecord.loginId="+id+"&showType="+showType;    	
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
    
     function queryUserInfo(id,type){
	  //  alert("aaaa"+type);
	   	if(type=='1'){
	   	   //  alert(type+"个人");
	   	     dialog("选择会员账号","iframe:/userInfo/personalBasicInfo_prePersonDetail.action?loginId="+id,"900px","760px","iframe");
	   	}
	   	if(type=='2'){
	   	//   alert(type+"bbb");
	   	    dialog("选择会员账号","iframe:/personalbasic/personalbasic_preExpertDetail.action?loginId="+id,"900px","760px","iframe");
	   	}
	   	if(type=='4'){
	   //	   alert(type+"cc");
	   	    dialog("选择会员账号","iframe:/userInfo/commercialTenantBasicInfo_preBusinessDetail.action?loginId="+id,"900px","760px","iframe");
	   	}
	   }
</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'客户业务'" scope="request" />
		<s:set name="name" value="'会员账号冻结'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div  style="height:90%;overflow-y:scroll; " >
		<s:form name="bnesFrozenRecordForm" method="post" onsubmit=" return checkAllTextValid(this)"
			action="/loginfrozenRecord/frozenRecord_pageList.action">
			  <s:hidden name="showType"></s:hidden>
			<!-- 查询条件区域 -->
			<table width="98%"  class="content_table" align="center"
				cellpadding="0" cellspacing="0">
				
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
						<td align="right">
							真实姓名：
						</td>
						<td>
							<input name="realName" type="text"
								value="<s:property value='bnesFrozenRecord.realName'/>">
						</td>
						<td align="right">
							投诉人会员账号：
						</td>
						<td>
							<input name="sueName" type="text"
								value="<s:property value='bnesFrozenRecord.sueName'/>">
						</td>
						<td align="right">
							投诉人姓名：
						</td>
						<td>
							<input name="sueRealName" type="text"
								value="<s:property value='bnesFrozenRecord.sueRealName'/>">
						</td>

						<td align="center">
							<INPUT TYPE="submit" class="queryBtn" value="">
                            <s:if test="%{showType!=null}">
					    </s:if>
					    <s:else>
					       	<input class="addBtn" TYPE="button" value="" onClick="gotoAdd();">
					    </s:else>
						</td>
					</tr>
			</table>

			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1">
				<tr>
					<th>
						会员账号
					</th>
					<th>
						冻结状态
					</th>
					<th>
						冻结操作账号
					</th>
					<th>
						冻结日期
					</th>
					<th>
						冻结原因
					</th>
					<th>
						操作
					</th>
				</tr>
				<s:iterator id="custiterator" value="page.dataList">
					<tr>
						<td align="center">
							
							 <a href="#" onClick="queryUserInfo(<s:property value="loginId"/>,'<s:property value="customerType"/>');">
							 <s:property value="username" />
							 </a>
						</td>
						<td align="center">
						
							<s:if test="status==1"> 
		已解冻
		</s:if>
							<s:elseif test="status==0">
		冻结
		</s:elseif>
						</td>
						<td align="center">
							<s:property value="sueName" />
						</td>

						<td align="center">
							<s:date name="sueDate" format="yyyy-MM-dd" />
						</td>
						<td align="center">
						<s:if test="frozenReason.length()>14">
			   		 	 <s:property value='frozenReason.substring(0,14)'/>...
				 		</s:if>   
						<s:else>      
				     		<s:property value='frozenReason'/>
				 		</s:else>
						</td>
						<td>
					    <s:if test="showType!=null">
					         <img title="解冻" style="cursor: pointer;"
								src="/etc/images/icon_modify.png"
								onclick="byShowTypeStatus('<s:property value='loginId'/>',<s:property value='showType'/>)" />	
					    </s:if>
					    <s:else>
					    <img title="解冻" style="cursor: pointer;"
								src="/etc/images/icon_modify.png"
								onclick="gotoUpdateStatus(<s:property value='frozenRecordId'/>)" />	
					    </s:else>
										
						</td>
						
					</tr>
				</s:iterator>
			</table>

			<table width="500" align="right">
				<tr>
					<td>
						<s:set name="form_name" value="'bnesFrozenRecordForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
		</s:form>
		
	</body><!-- 消息提示 -->
	<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
	</div>
</html>

