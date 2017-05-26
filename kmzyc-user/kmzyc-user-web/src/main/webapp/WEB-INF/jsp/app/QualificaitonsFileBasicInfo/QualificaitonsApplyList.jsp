<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发送短信邮件记录</title>
	<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
        <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script type="text/javascript">
    /**  进入修改个人信息页面  **/
    function gotoUpdate(id,userId){ 

      location.href="/qualificaitonsApply/qualificaitonsApply_qualificaitonsApplyUpdate.action?qualificationsApplyDO.id="+id+"&qualificationsApplyDO.userId="+userId;
    }


	/** 单条删除活动  **/
	function gotoDelete(id) {
		if (confirm("是否确认删除? ") == true) {
			location.href = "/qualificaitonsApply/qualificaitonsApply_qualificaitonsApplyDelete.action?qualificationsApplyDO.id="+id;
		}
	}



	//详情
		function gotoDetail(id,userId)
		{
			location.href = "/qualificaitonsApply/qualificaitonsApply_qualificaitonsApplyDetail.action?qualificationsApplyDO.id="+id+"&qualificationsApplyDO.userId="+userId;


		} 

		/** 审核 **/
		function gotoVerify(id) {
		 
				location.href = "/qualificaitonsApply/qualificaitonsApply_qualificaitonsApplyVerify.action?qualificationsApplyDO.id="+id;
			 
		}
    </script>
    
</head>
<body>
<!-- 标题条 -->
		<s:set name="parent_name" value="'采购商管理'" scope="request" />
		<s:set name="name" value="'采购资格申请管理'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
       <div  style="height:90%;overflow-y:auto; " >
<s:form  name="qualificaitonsFileForm" id="qualificaitonsFileForm"  onsubmit=" return checkAllTextValid(this)"
action="/qualificaitonsApply/qualificaitonsApply_pageList.action" method="post">
<!-- 查询条件区域 -->
 <table width="98%" align="center" class="content_table topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr>
	<td align="right">账户号：</td>
		<td>
		     <input name="qualificationsApplyDO.userName" type="text" value="<s:property value='qualificationsApplyDO.userName'/>">
		     
		      <input name="qualificationsApplyDO.userId" type="hidden" value="<s:property value='qualificationsApplyDO.userId'/>">
		</td>
		
				<td align="right">电话：</td>
 	<td>
		     <input name="qualificationsApplyDO.tel" type="text" value="<s:property value='qualificationsApplyDO.tel'/>">
		</td>
		
									 	<td align="right">申请时间：</td>
									 	<TD> 
		<input type="text" name="qualificationsApplyDO.applyStartDate"  id="d523" class="Wdate"  value ="<s:date name = 'qualificationsApplyDO.applyStartDate' format='yyyy-MM-dd ' />"     onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'d521\')}'})"  />
	至 
	 <input type="text" name="qualificationsApplyDO.applyEndDate" id="d521" class="Wdate"  value ="<s:date name = 'qualificationsApplyDO.applyEndDate' format='yyyy-MM-dd ' />"    onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'d523\')}'})" />
	
	</td>
 
	 </tr>
	<tr>
			<td align="right">状态：</td>
		<td>
		      <select name="qualificationsApplyDO.status" style="width:150px">
										<option value="" <s:if test='qualificationsApplyDO.status==""'>selected="selected"</s:if>>
										请选择
									</option>
								<option value="1" <s:if test='qualificationsApplyDO.status=="1"'>selected="selected"</s:if>>
										待审核
									</option>
									<option value="2" <s:if test='qualificationsApplyDO.status=="2"'>selected="selected"</s:if>>
									通过
									</option>
									<option value="3" <s:if test='qualificationsApplyDO.status=="3"'>selected="selected"</s:if>>
								 不通过
								 </option>
							</select>
		</td>
		<td align="right">审核人：</td>
		<td>
		     <input name="qualificationsApplyDO.userReal" type="text" value="<s:property value='qualificationsApplyDO.userReal'/>">
							</td>
			<td align="right">申请类型：</td>
		<td> 
			<s:if test="applyType==1">
		    			 
		    				<INPUT type="checkbox" name="qualificationName"      checked="checked"    value="1" >OTC</input>
		    				<INPUT type="checkbox" name="qualificationName"  value="2" >医疗器械 </input>
                        <INPUT type="checkbox" name="qualificationName"  value="3" >处方药 </input>
		    			</s:if>
		    		 		<s:elseif test="applyType==2">
		    			 
		    				<INPUT type="checkbox" name="qualificationName"   value="1" >OTC</input>
		    				<INPUT type="checkbox" name="qualificationName"  checked="checked"   value="2" >医疗器械 </input>
                        <INPUT type="checkbox" name="qualificationName"  value="3" >处方药 </input>
		    			</s:elseif>
		    			
		    					<s:elseif test="applyType==3">
		    		 
		    				<INPUT type="checkbox" name="qualificationName"     value="1" >OTC</input>
		    				<INPUT type="checkbox" name="qualificationName" value="2" >医疗器械 </input>
                        <INPUT type="checkbox" name="qualificationName"   checked="checked" value="3" >处方药 </input>
		    			</s:elseif>
		    			
		    				<s:elseif test="applyType=='1|2'">
		    		 
		    				<INPUT type="checkbox" name="qualificationName"   checked="checked"   value="1" >OTC</input>
		    				<INPUT type="checkbox" name="qualificationName"  checked="checked" value="2" >医疗器械 </input>
                        <INPUT type="checkbox" name="qualificationName"     value="3" >处方药 </input>
		    			</s:elseif>
		    			<s:elseif test="applyType=='1|3'">
		    				<INPUT type="checkbox" name="qualificationName"   checked="checked"  value="1" >OTC</input>
		    				<INPUT type="checkbox" name="qualificationName"   value="2" >医疗器械 </input>
                        <INPUT type="checkbox" name="qualificationName"  checked="checked"  value="3" >处方药 </input>
		    			</s:elseif>
		    			
		    				<s:elseif test="applyType=='2|3'">
		    	
		    				<INPUT type="checkbox" name="qualificationName"     value="1" >OTC</input>
		    				<INPUT type="checkbox" name="qualificationName"   checked="checked"  value="2" >医疗器械 </input>
                        <INPUT type="checkbox" name="qualificationName"    checked="checked"  value="3" >处方药 </input>
		    			</s:elseif>
		    	     		<s:elseif test="applyType=='1|2|3'">
		    	
		    				<INPUT type="checkbox" name="qualificationName"   checked="checked"  value="1" >OTC</input>
		    				<INPUT type="checkbox" name="qualificationName"   checked="checked"  value="2" >医疗器械 </input>
                        <INPUT type="checkbox" name="qualificationName"    checked="checked"  value="3" >处方药 </input>
		    			</s:elseif>
		    	
		    		   <s:else>
		     
		    			 <INPUT type="checkbox" name="qualificationName"   value="1" >OTC</input> 
						<INPUT type="checkbox" name="qualificationName"    value="2" >医疗器械 </input>
                        <INPUT type="checkbox" name="qualificationName"    value="3" >处方药 </input>
                        </s:else>
		</td>
		<td align="right">
			<INPUT TYPE="submit" class="queryBtn" value="">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
		  <th align="center" width="5%">
            <input type='checkbox' name='allbox'  onclick="checkAll(this,'ids')">
		</th> 
	<th align="center" >账户号</th>
	<th align="center" >申请类型</th>
	<th align="center" >电话</th>
	<th align="center" >申请时间</th>
 
	<th align="center" >状态</th>
	<th align="center" >审核时间</th>
	<th align="center" >审核人</th>
 
<th align="center" >操作</th>
	</tr>
	<s:iterator id="emailInfoiterator"  value="page.dataList">
	<tr>
	    <td align="center" width="5%">
		    <input type="checkbox"  name="id"  value='<s:property value="id"/>' />
		</td>
	<td align="center">
		     <s:property value="userName"/>
		</td>
		
		<td align="center">
 
	  	<s:if test="applyType==1">
					  OTC
</s:if>
						<s:if test="applyType=='1|2'">
                         OTC|医疗器械
</s:if>
				 
                     	<s:if test="applyType=='1|2|3'">
         OTC|医疗器械|处方药
                     
                     	</s:if>
                     	                   	<s:if test="applyType=='1|3'">
        OTC|处方药
                     
                     	</s:if>
                     	               	                   	<s:if test="applyType==2">
        医疗器械
                     
                     	</s:if>
                     	
                     	            	               	                   	<s:if test="applyType=='2|3'">
                                  医疗器械|处方药
                     
                     	</s:if>
                     	                  	            	               	                   	<s:if test="applyType==3">
                                                                                                                       处方药
                     
                     	</s:if>
                     	
   
              
		</td>
			<td align="center">
							 
							<s:property value="tel"/> 
							</td>
							
							<td align="center">
		<s:date name="applyDate"  format="yyyy-MM-dd HH:mm:ss" />
		</td>
 
		<td align="center">
  			<s:if test="status==1">
					   待审核
</s:if>
						<s:if test="status==2">
                               通过
</s:if>
				 
                     	<s:if test="status==3">
                      不通过
                     	
                     	</s:if>
		</td>
		<td align="center">
 
					<s:date name="auditingDate"  format="yyyy-MM-dd HH:mm:ss" />	 
                   
		
		
		<td align="center">
		<s:if test="userReal!=null">
		<s:property value="userReal"/> 
		</s:if>
		<s:else>
		<s:property value="loginName"/> 
		</s:else>
		</td> 
		
		
	 
	
	<TD>
	
	  <img title="详情" style="cursor: pointer;" src="/etc/images/u175_normal.png"  onclick="gotoDetail(<s:property value="id"/>,<s:property value="userId"/>)"/>
	
	<s:if test="%{status==1}">
		  <img title="修改" style="cursor: pointer;" src="/etc/images/u171_normal.png"  onclick="gotoUpdate(<s:property value="id"/>,<s:property value="userId"/>)" />
 <img title="审核" style="cursor: pointer;" src="/etc/images/u177_normal.png"  onclick="gotoVerify(<s:property value="id"/>)"/>
 <img title="删除" style="cursor: pointer;" src="/etc/images/u173_normal.png"  onclick="gotoDelete(<s:property value="id"/>)" />  
</s:if>
  		<s:if test="%{status==2}">
  		 <img title="删除" style="cursor: pointer;" src="/etc/images/u173_normal.png"  onclick="gotoDelete(<s:property value="id"/>)" />  
  		</s:if>
  			<s:if test="%{status==3}">
  		 <img title="删除" style="cursor: pointer;" src="/etc/images/u173_normal.png"  onclick="gotoDelete(<s:property value="id"/>)" />  
  		</s:if>
  		
  		

	
	</TD> 
	</tr>
	
	
	</s:iterator>
</table>

<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'qualificaitonsFileForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
</div>
</body>
</html>

