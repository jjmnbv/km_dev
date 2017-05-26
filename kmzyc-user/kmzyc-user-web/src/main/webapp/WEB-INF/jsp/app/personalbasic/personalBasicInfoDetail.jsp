<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>专家详情信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<Script language="JavaScript" src="/etc/js/jquery-1.8.3.js" type="text/javascript"></Script>
<script src="/etc/js/dialog.js"></script>
<SCRIPT type="text/javascript">
 /** 通过登录账号id查询账户详细信息  **/
 function queryAccountInfo(id){
     openwindow("/accounts/accountInfo_showAccountInfo.action?showType=2&accountInfo.n_LoginId="+id,"_blank",800,650); 
 }
 /** 通过登录账号id查询收货地址信息 **/
 function queryAddressInfo(id){
     openwindow("/accounts/address_queryPageList.action?viewOnly=1&showType=2&address.loginId="+id,"_blank",1000,650);
 }
  /** 通过登录账号id查询消息中心信息 **/
 function queryMessageInfo(accountId){
      openwindow("/messageCenter/messageCenter_pageList.action?viewOnly=1&showType=1&bnesMessageCenter.accountId="+accountId,"_blank",1000,650);
 }
 /**通过登录账号查询消费电子卷 **/
 function queryVouchersInfo(id){
      openwindow("/accounts/vouchers_showVouchersList.action?showType=1&coupons.custmoId="+id,"_blank",1000,650);
 }
 function queryAccTrationInfo(id){
     openwindow("/accounts/tration_showTrationList.action?showType=1&loginId="+id,"_blank",1000,650); 
 }
 /** 通过登录账号查询积分明细信息 **/
 function queryScoreInfo(n_loginId){
      openwindow("/accounts/scoreInfo_queryPageList.action?showType=1&scoreInfo.n_loginId="+n_loginId,"_blank",1000,650);
 }
 /** 通过登录账号id查询经验值明细信息  **/
 function queryRankInfo(loginId){
       openwindow("/bloodIntegralInfo/integralInfo_pageList.action?showType=1&bloodIntegralInfo.loginId="+loginId,"_blank",1000,650);
 }
 /** 通过登录账号id冻结登录账号 **/
 function queryLoginAccountForzen(id,loginAccount){
      openwindow("/loginfrozenRecord/frozenRecord_preAdd.action?showType=1&bnesFrozenRecord.username="+loginAccount+"&bnesFrozenRecord.loginId="+id,"_blank",1000,650);
 }
 
 /**通过登录账号id解冻登录账户**/
 function queryLoginAccountThraw(id){
     openwindow("/loginfrozenRecord/frozenRecord_preUpdate.action?showType=1&bnesFrozenRecord.loginId="+id,"_blank",1000,650);
 }
 
 function openwindow(url,name,iWidth,iHeight){
	var url; //转向网页的地址;
	var name; //网页名称，可为空;
	var iWidth; //弹出窗口的宽度;
	var iHeight; //弹出窗口的高度;
	var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
	window.open(url,name,'height='+iHeight+',innerHeight='+iHeight+',width='+iWidth+',innerWidth='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
 }

</SCRIPT>
</head>
<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'客户资料'" scope="request"/>
<s:set name="name" value="'专家客户'" scope="request"/>
<s:set name="son_name" value="'详情'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:scroll; " >
<s:form action="personalbasic_update.action" method="POST" id="personalBasicInfoAddForm" enctype="multipart/form-data"  namespace='/personalbasic' >
<!-- 数据编辑区域 -->
<table width="90%" class="edit_table" cellpadding="4" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<!-- error message -->
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="6" align="center"> 
	    <s:property value='rtnMessage'/>
		 <s:set name="rtnMessage" value=""/>
		</td>
	</tr>
	</s:if>
	<tr> 
		<td colspan="6" align="left" class="edit_title">专家信息</td>
	</tr>
		<tr>  
		<td colspan="6"  align="center" >
		   <input type="button" value="账户信息"   onclick="queryAccountInfo(<s:property value='loginInfo.n_LoginId'/>)"  />&nbsp;
			<input type="button" value="地址管理"   onclick="queryAddressInfo(<s:property value='loginInfo.n_LoginId'/>)"/>&nbsp;
			<input type="button" value="消息中心"  onclick="queryMessageInfo('<s:property value='loginInfo.n_LoginId'/>')" />&nbsp;
			<input type="button" value="消费电子券" onclick="queryVouchersInfo(<s:property value='loginInfo.n_LoginId'/>)" />&nbsp;
			<input type="button" value="交易记录"  onclick="queryAccTrationInfo(<s:property value='loginInfo.n_LoginId'/>)" />&nbsp;		
			<input type="button" value="积分明细" onclick="queryScoreInfo('<s:property value='loginInfo.n_LoginId'/>')" />&nbsp;
		    <input type="button" value="经验值明细" onclick="queryRankInfo('<s:property value='loginInfo.n_LoginId'/>')" />
		</td>
	</tr>
     <tr> 
		<td colspan="6"  align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;登录信息</td>
	</tr>
	<tr> 
		<td width="20%" align="right">会员账号：</td>
		<td width="20%"> 
			<s:property value="loginInfo.loginAccount"/>
		</td>	
		<td width="20%" align="right">客户类别：</td>
		<td width="20%" colspan="4"> 
		<s:property value="personalBasicInfo.customerName"/>
		</td>
			
	</tr>
	<tr> 
	<td width="20%" align="right">专家级别：</td>
		<td width="20%"> 
			
			<s:property value="personalBasicInfo.levelName"/>
		</td>		
		<td width="20%" align="right">专家头衔：</td>
		<td width="20%" colspan="4">
			<s:property value="personalBasicInfo.rankName"/> 
		</td>
	</tr>
	<tr> 
		<td colspan="6"  align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;个人基本资料</td>
	</tr>
	
   <tr> 
		<td width="20%" align="right">姓名：</td>
		<td width="20%"> 
			<s:property value="personalBasicInfo.name"/>
			
		</td>
		<td width="20%" align="right">手机号码：</td>
		<td width="20%"> 
			<s:property value="loginInfo.mobile"/>

		</td>
		<td width="20%" align="right">邮箱地址：</td>
		<td width="20%"> 
			<s:property value="loginInfo.email"/>
		
		</td>
	</tr>
  	<tr> 
		<td width="20%" align="right">性别：</td>
		<td width="20%"> 	
		<s:if test="personalBasicInfo.sex==0">
		男
		</s:if>
		<s:else>
		女
		</s:else>
	
		</td>
		<td width="20%" align="right">年龄：</td>
		<td width="20%"> 
		<s:property value="personalBasicInfo.n_Age"/>
		</td> 
		<td width="20%" align="right">生日：</td>
		<td width="20%"> 
		<s:date name="personalBasicInfo.d_Birthday" format="yyyy-MM-dd" />
		</td>
	</tr>
     <tr> 
		<td width="20%" align="right">证件类型：</td>
		<td width="20%">
		<s:if test="personalBasicInfo.n_CertificateType==0">
			身份证
			</s:if>
		
			<s:if test="personalBasicInfo.n_CertificateType==1">
			护照
			</s:if>
			
			
			<s:if test="personalBasicInfo.n_CertificateType==2">
			回乡证
			</s:if>
		</td> 
		<td width="20%" align="right">证件号码：</td>
		<td width="20%"> 
			<s:property value="personalBasicInfo.certificateNumber"/>
		</td>
		<td width="20%" align="right">所在地：</td>
		<td width="20%"> 
		<s:property value="personalBasicInfo.location"/>
		</td> 
	</tr>
	<tr> 	
		<td width="20%" align="right">故乡所在地：</td>
		<td width="20%"> 
			<s:property value="personalBasicInfo.hometownLocation"/>
		</td> 
		<td width="20%" align="right">教育状况：</td>
		<td width="20%"> 
			<s:property value="personalBasicInfo.educationalStatus"/>
		</td>
		<td width="20%" align="right">职业类型：</td>
		<td width="20%"> 
			<s:property value="personalBasicInfo.professionType"/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">职业：</td>
		<td width="20%"> 
			<s:property value="personalBasicInfo.profession"/>
		</td>
		<td width="20%" align="right">年收入：</td>
		<td width="20%" colspan="4"> 
			<s:property value="personalBasicInfo.n_AnnualIncome"/>
		</td>
	</tr>	
	<tr> 
		<td colspan="6"  align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;个性信息</td>
	</tr>
	<tr> 
		<td width="20%" align="right">昵称：</td>
		<td width="20%" >
			<s:property value="personalityInfo.nickname"/>
		</td> 
		<td width="20%" align="right">个性签名：</td>
		<td width="20%" colspan="4">
      <s:property value="personalityInfo.personalityAutograph"/>
		</td>
	</tr>
	<tr > 
	<td width="20%" align="right">信用值：</td>
		<td width="20%"> 
		<s:property value="personalityInfo.n_IndividualCreditValue"/>
		</td> 
		<td width="20%" align="right">经验值：</td>
		<td width="20%"> 
		<s:property value="personalityInfo.n_EmpiricalValue"/>
		</td>
		<td width="20%" rowspan="4"align="right">头像照片：</td>
		<td width="20%" rowspan="4">		
	     <img title="头像" width="310" height="150" style="cursor: pointer;" src="http://10.1.0.213:82/user/<s:property value='personalityInfo.headSculpture'/>"/>
		</td>
	</tr>
	<tr> 
	
		<td width="20%" align="right">总积分：</td>
		<td width="20%"> 
			<s:property value="personalityInfo.n_TotalIntegral"/>
		</td> 
		<td width="20%" align="right">可用积分：</td>
		<td width="20%"> 
		<s:property value="personalityInfo.n_AvailableIntegral"/>
		</td>
	</tr>
	<tr>
	<td width="20%" align="right">性格：</td>
		<td width="20%">
			<s:property value="personalityInfo.character"/>
		</td>
		<td width="20%" align="right">兴趣爱好：</td>
		<td width="20%">
			<s:property value="personalityInfo.interest"/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">微博地址：</td>
		<td width="20%">
			<s:property value="personalityInfo.microblogAddress"/>
		</td>
		<td width="20%" align="right">QQ号：</td>
		<td width="20%">
		<s:property value="personalityInfo.qqNumber"/>
		</td>
	</tr>
	<tr> 
		<td colspan="6"  align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;医务专属信息</td>
	</tr>
	
	<tr> 
		<td width="20%" align="right">所属医院：</td>
		<td width="20%"> 
			<s:property value="personalBasicInfo.workUnit"/>
		</td> 
		<td width="20%" align="right">医院等级：</td>
		<td width="20%"> 
			<s:property value="mdicalExcusieInfo.hospitalLevel"/>
		</td>
		<td width="20%" align="right">所属科室：</td>
		<td width="20%"> 
			<s:property value="mdicalExcusieInfo.theDepartment"/>
		</td>
	</tr>
	
    <tr> 
		
		<td width="20%" align="right">专业专长：</td>
		<td width="20%"> 
		<s:property value="mdicalExcusieInfo.professionalExprtise"/>
		</td> 
		<td width="20%" align="right">证书类型：</td>
		<td width="20%"> 
		<s:if test="mdicalExcusieInfo.n_certificateType==0">
		医生资格证
		</s:if>
		<s:else>
		医生执业证
		</s:else>
	     </td> 
		<td width="20%" align="right">证书编号：</td>
		<td width="20%"> 
			<s:property value="mdicalExcusieInfo.certificateNumber"/>
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">审核电话：</td>
		<td width="20%" colspan="6"> 
			<s:property value="mdicalExcusieInfo.auditingPhone"/>
		</td>
	</tr>
	<tr> 
		<td colspan="6"  align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;健康类属信息</td>
	</tr>
	<tr>
	<td width="20%" align="right">社保号：</td>
		<td width="20%">
			<s:property value="healthYgenericInfo.socialSecurityNumber"/>
		</td> 
		<td width="20%" align="right">婚姻状态：</td>
		<td width="20%" colspan="4">
		<s:if test="healthYgenericInfo.n_MaritalStatus==0">
		保密	
		</s:if>
		<s:if test="healthYgenericInfo.n_MaritalStatus==1">
		已婚
		</s:if>
		<s:if test="healthYgenericInfo.n_MaritalStatus==2">
		未婚			
		</s:if>	
		</td>  
	</tr>
		<tr> 
		<td width="20%" align="right">有无生育：</td>
		<td width="20%">
			<s:if test="healthYgenericInfo.n_ThereIsNoFertility==0">
	                       有		
			</s:if>
			<s:else>
			无
			</s:else>
			
		</td>
		<td width="20%" align="right">血型：</td>
		<td width="20%"  colspan="4">
		<s:if test="healthYgenericInfo.bloodType==0">
			A型
			
		</s:if>
		<s:if test="healthYgenericInfo.bloodType==1">
			B型
		</s:if>
		<s:if test="healthYgenericInfo.bloodType==2">
			O型			
		</s:if>
		<s:if test="healthYgenericInfo.bloodType==3">
			AB型
		</s:if>
	</tr>
	 <tr>
		<td width="20%" align="right">健康状况：</td>
		<td width="20%" colspan="6">
	    <s:property value="healthYgenericInfo.healthyState"/>
		</td>
	</tr>
	<tr>
		<td width="20%" align="right">健康咨询兴趣：</td>
		<td width="20%" colspan="6">
	    <s:property value="healthYgenericInfo.healthySeekAdviceFrom"/>
		</td>
	</tr>
	<tr>
	<td width="20%" align="right">饮食习惯：</td>
		<td width="20%" colspan="6">
		<s:property value="healthYgenericInfo.eatingHabits"/>	
	
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">烟酒史：</td>
		<td width="20%" colspan="6">
		<s:property value="healthYgenericInfo.smokingAndAlcoholHistory"/>	
		
		</td>
	</tr>
	<tr> 
		<td width="20%" align="right">过往病史：</td>
		<td width="20%" colspan="6">
		<s:property value="healthYgenericInfo.pastMedicalHistory"/>
		
		</td>
	</tr>
</table>
<!-- 底部 按钮条 -->
<table width="80%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<input class="backBtn"  onclick="gotoList()" type="button" value="">
		</td>
		<td width="15%" align="center"></td>
	</tr>
</table>

<br><br>

</s:form>
</div>
<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
<SCRIPT LANGUAGE="JavaScript">

function gotoList(){
    location.href="/personalbasic/personalbasic_pageList.action";
}

</SCRIPT>
</BODY>
</HTML>