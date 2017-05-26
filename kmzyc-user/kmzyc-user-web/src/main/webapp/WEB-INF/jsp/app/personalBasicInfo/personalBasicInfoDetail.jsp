<%@page contentType="text/html;charset=UTF-8" import="com.pltfm.sys.util.StaticParams"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人详情信息</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<Script language="JavaScript" src="/etc/js/jquery-1.8.3.js" type="text/javascript"></Script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript">
/** 通过登录账号id查询账户详细信息  **/
 function queryAccountInfo(id){
     openwindow("/accounts/accountInfo_showAccountInfo.action?showType=1&accountInfo.n_LoginId="+id,"_blank",800,650); 
 }
 /** 通过登录账号id查询收货地址信息 **/
 function queryAddressInfo(id){
     openwindow("/accounts/address_queryPageList.action?viewOnly=1&showType=1&login_Id="+id,"_blank",1000,650);
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
      openwindow("/accounts/scoreInfo_queryPageList.action?showType=1&loginId="+n_loginId,"_blank",1000,650);
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
</script>
</head>
<body>
<!-- 导航栏 -->
<s:set name="parent_name" value="'客户资料'" scope="request"/>
<s:set name="name" value="'个人客户'" scope="request"/>
<s:set name="son_name" value="'查看详情'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
  <div  style="height:90%;overflow-y:auto; " >
<s:form action="personalBasicInfo_update.action" id="personalBasicInfoUpdateForm" enctype="multipart/form-data" method="POST"  namespace='/userInfo'>

<!-- hidden properties -->
<INPUT TYPE="hidden" name="isEnable" value="1">


<table width="100%"  cellpadding="10px" height="30px" cellspacing="0" >
<tr>  
		<td colspan="6"  align="center" >
		    <!-- <input type="button" value="账户信息"   onclick="queryAccountInfo(<s:property value='loginInfo.n_LoginId'/>)"  />&nbsp; -->
			<input type="button" value="地址信息"  class="btn-custom" onclick="queryAddressInfo(<s:property value='loginInfo.n_LoginId'/>)"/>&nbsp;
			<input type="button" value="消息中心" class="btn-custom" onclick="queryMessageInfo('<s:property value='loginInfo.n_LoginId'/>')" />&nbsp;
			<input type="button" value="电子优惠券" class="btn-custom" onClick="queryVouchersInfo(<s:property value='loginInfo.n_LoginId'/>)" />&nbsp;
			<input type="button" value="余额交易记录"  class="btn-custom" onclick="queryAccTrationInfo(<s:property value='loginInfo.n_LoginId'/>)" />&nbsp;
			<s:if test="personalBasicInfo.isEra!=1">
			<input type="button" value="积分明细" class="btn-custom" onClick="queryScoreInfo('<s:property value='loginInfo.n_LoginId'/>')" />
			</s:if>
		</td>
	</tr>
</table>

<!-- 数据编辑区域 -->
<table width="90%" class="edit_table" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
	<!-- error message -->
	<s:if test="rtnMessage != null">
	<tr> 
		<td colspan="6" align="center"> 
			<font color="red"><s:property value='rtnMessage'/></font>
			<s:set name="rtnMessage" value=""/>
		</td>
	</tr>
	</s:if>
	
    <tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;个人信息</td>
	</tr>
	<tr> 
		<td width="10%" align="right">会员账号：</td>
		<td width="10%"> 
			<s:property value="personalBasicInfo.loginAccount"/>
		</td>
		<td width="10%" align="right">姓名：</td>
		<td width="10%"> 
			<s:property value="personalBasicInfo.name"/>
		</td>
		<td width="10%" align="right">性别：</td>
		<td width="10%">		
			<s:if test="personalBasicInfo.sex==0">男</s:if>
			<s:else>女</s:else>
		</td> 
		<!-- 
		<td width="20%" align="right">客户类别：</td>
		<td width="20%" colspan="4"> 
		<s:property value="personalBasicInfo.customerName"/>
		</td>
		 -->
		
	</tr>
	
	<tr> 
		<%-- <td align="right">总部会员编号：</td>
		<td> 
			<s:property value="personalBasicInfo.cardNum"/>
		</td> --%>
		<td align="right">手机号码：</td>
		<td> 
			<s:property value="personalBasicInfo.mobile"/>
		</td>
		<td  align="right">手机状态：</td>
		<td> 
			<s:if test="personalBasicInfo.isMobile==1">已验证</s:if>
			<s:else>未验证</s:else>
		</td>
		
		<!-- 
		
		
		
		<td width="20%" align="right">客户头衔：</td>
		<td width="20%" colspan="4">
			<s:property value="personalBasicInfo.rankName"/> 
		</td>
		 -->
		 <td align="right">账号状态：</td>
		<td > 
			<s:if test="personalBasicInfo.n_Status==0">禁用</s:if>
			<s:if test="personalBasicInfo.n_Status==1">可用</s:if>
		</td>
	</tr>
	<%-- <tr>
		
		<td  align="right">邮箱地址：</td>
		<td> 
			<s:property value="personalBasicInfo.email"/>
		</td>
		<td  align="right">邮箱状态：</td>
		<td> 
			<s:if test="personalBasicInfo.isEmail==1">已验证</s:if>
			<s:else>未验证</s:else>
		</td> 
	</tr>--%>
	<tr>
		<%-- <td align="right">客户级别：</td>
		<td> 
			<s:property value="personalBasicInfo.levelName"/>
		</td> --%>
		<td align="right">客户来源：</td>
		<td>
			<s:if test="#request.isThird==1">
				授权登录
			</s:if>
            <s:else>
            	<s:property value="#request.registerPlatformMap[personalBasicInfo.registerPlatform]" />
            </s:else>	
		</td>
		<td align="right">客户身份：</td>
		<td> 
			<s:if test="personalBasicInfo.isCommonUser==1">普通用户</s:if>
			<s:if test="personalBasicInfo.isSupplier==1">，供应商</s:if>
			<s:if test="personalBasicInfo.isPurchaser==1">，采购商</s:if>
			<s:if test="personalBasicInfo.isTourist==1">，游客</s:if>
			<s:if test="personalBasicInfo.isEra==1">，康美中药城</s:if>
			<s:if test="personalBasicInfo.isSpreader==1">，云商</s:if>
			<s:if test="personalBasicInfo.isCrowder==1">，机构</s:if>
		</td>
		<td align="right">终端来源：</td>
		<td> 
			<s:property value="#request.registerDeviceMap[personalBasicInfo.registerDevice]" />
		</td>
	</tr>
	<tr>
		
		<td align="right">昵称：</td>
		<td> 
			<s:property value="personalityInfo.nickname"/>
		</td>
		<td align="right">创建时间：</td>
		<td> 
			<s:date name="personalBasicInfo.d_CreateDate" format="yyyy-MM-dd HH:mm:ss" />
		</td>
	</tr>
	<tr>
		<td align="right">生日：</td>
		<td>
			<s:date name="personalBasicInfo.d_Birthday" format="yyyy-MM-dd"/>
		</td>
		<td align="right">证件类型：</td>
		<td>
			<s:if test="personalBasicInfo.n_CertificateType==0">身份证</s:if>
			<s:if test="personalBasicInfo.n_CertificateType==1">护照</s:if>
			<s:if test="personalBasicInfo.n_CertificateType==2">回乡证</s:if>
		</td>
		<td align="right">证件号码：</td>
		<td>
			<s:property value="personalBasicInfo.certificateNumber"/>
		</td>
	</tr>
	<!-- 
	<tr> 
		<td width="20%" align="right">年龄：</td>
		<td width="20%">
		<s:property value="personalBasicInfo.n_Age"/>
		</td> 
	</tr>
	<tr> 
		
		<td width="20%" align="right">所在地：</td>
		<td width="20%">
			<s:property value="personalBasicInfo.location"/>
		</td> 
	</tr>
	 -->
	 <!-- 
	<tr> 
		
		<td width="20%" align="right">故乡所在地：</td>
		<td width="20%">
			<s:property value="personalBasicInfo.hometownLocation"/>
		</td> 
		<td width="20%" align="right">教育状况：</td>
		<td width="20%">
			<s:property value="personalBasicInfo.educationalStatus"/>
		</td>
		<td width="20%" align="right">工作单位：</td>
		<td width="20%">
			<s:property value="personalBasicInfo.workUnit"/>
		</td>
	</tr>
	 
	<tr> 
		<td width="20%" align="right">职业类型：</td>
		<td width="20%">
			<s:property value="personalBasicInfo.professionType"/>
		</td> 
		<td width="20%" align="right">职业：</td>
		<td width="20%">
			<s:property value="personalBasicInfo.profession"/>
		</td>

		<td width="20%" align="right">年收入：</td>
		<td width="20%"   >
			<s:property value="personalBasicInfo.n_AnnualIncome"/>
		</td>
	</tr>
	-->
	<tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账户信息</td>
	</tr>
	<tr> 
		<td align="right">账户金额：</td>
		<td   >
			<s:property value="accountInfo.n_AccountAmount"/>
		</td>
		<td align="right">账户可用金额：</td>
		<td   >
			<s:property value="accountInfo.amountAvlibal"/>
		</td>
		<td align="right">账户冻结金额：</td>
		<td >
			<s:property value="accountInfo.amountFrozen"/>
		</td>
	</tr>
	<%-- <s:if test="personalBasicInfo.isEra!=1">  --%>
	<tr>
	
		<td align="right">总积分：</td>
		<td colspan = "4"> 
			<s:property value="personalityInfo.n_TotalIntegral"/>
		</td>
		<%-- <td align="right">可用积分：</td>
		<td> 
			<s:property value="personalityInfo.n_AvailableIntegral"/>
		</td> --%>
		<!-- 
		<td width="20%" align="right"  rowspan="4">头像照片：</td>
		<td width="20%"    rowspan="4">
		<img title="头像" width="100" height="100" style="cursor: pointer;" src="<s:property value='personalityInfo.headSculpture'/>" />
		</td>
		 -->
		 <td colspan="2"></td>
	</tr>
	<%-- </s:if> --%>
	
	<s:if test="personalBasicInfo.isEra==1">
	<tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时代会员信息</td>
	</tr>
	<tr> 
		<td align="right">康美中药城会员ID：</td>
		<td  >
			<s:property value="eraInfo.eraInfoId"/>
		</td>
		<td align="right">时代会员编号：</td>
		<td  >
			<s:property value="eraInfo.eraNo"/>
		</td>
		<td align="right">时代等级：</td>
		<td>
			<s:property value="eraInfo.eraGradeName"/>
		</td>
	</tr>
	<tr> 
		<td align="right">手机号码：</td>
		<td   >
			<s:property value="eraInfo.contactInformation"/>
		</td>
		<td align="right">邮箱地址：</td>
		<td>
			
		</td>
		<td align="right">联系电话：</td>
		<td >
			<s:property value="eraInfo.contactInformation"/>
		</td>
	</tr>
	<tr> 
		<%-- <td align="right">时代订单积分：</td>
		<td   >
			<s:property value="eraInfo.expIntegralValue"/>
		</td> --%>
		<td align="right">推荐人编号：</td>
		<td   >
			<s:property value="eraInfo.recommendedNo"/>
		</td>
		<td align="right">时代会员信息更新时间：</td>
		<td colspan = "3">
			<s:date name="eraInfo.modifyDate" format="yyyy-MM-dd HH:mm:ss" />
		</td>
	</tr>
	</s:if>
	
	<!-- 
	<tr> 
		<td width="20%" align="right">信用值：</td>
		<td width="20%"> 
		<s:property value="personalityInfo.n_IndividualCreditValue"/>
		</td> 
		<td width="20%" align="right">性格：</td>
		<td width="20%">
			<s:property value="personalityInfo.character"/>
		</td>
	</tr>
	<tr> 
	
		<td width="20%" align="right">兴趣爱好：</td>
		<td width="20%">
			<s:property value="personalityInfo.interest"/>
		</td>
		<td width="20%" align="right">QQ号：</td>
		<td width="20%"  >
			<s:property value="personalityInfo.qqNumber"/>
		</td>
	</tr>
	<tr> 
	<td width="20%" align="right">微博地址：</td>
		<td width="20%" colspan="4">
			<s:property value="personalityInfo.microblogAddress"/>
		</td>
		
	</tr>
	<tr> 
		<td colspan="6" align="left" class="edit_title" style="size: 8px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;健康类属信息</td>
	</tr>
	<tr> 
		<td width="20%" align="right">社保号：</td>
		<td width="20%">
			<s:property value="healthYgenericInfo.socialSecurityNumber"/>
		</td> 
			<td width="20%" align="right">婚姻状态：</td>
		<td width="20%" colspan="4">
		<s:if test="healthYgenericInfo.n_MaritalStatus==2">
		保密
		</s:if>
		
		<s:if test="healthYgenericInfo.n_MaritalStatus==1">
		已婚
		</s:if>
		<s:if test="healthYgenericInfo.n_MaritalStatus==0">
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
			<s:elseif test="healthYgenericInfo.n_ThereIsNoFertility==1">
			无
			</s:elseif>			
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
		</td>
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
	 -->
</table>

<!-- 底部 按钮条 -->
<table width="60%"  class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
		<td align="left">
			<input class="backBtn"  onclick="gotoList()" type="button" value="">
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

<br><br>

</s:form>
</div>
<SCRIPT LANGUAGE="JavaScript">
function gotoList(){
	//返回
	history.go(-1);
    //location.href="/userInfo/personalBasicInfo_pageList.action";
}
</SCRIPT>
</BODY>
</HTML>