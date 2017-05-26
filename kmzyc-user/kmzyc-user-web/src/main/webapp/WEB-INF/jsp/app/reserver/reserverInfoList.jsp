<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预备金管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.min.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<script src="/etc/js/dialog.js"></script>

</head>
<body>

<s:set name="parent_name" value="'采购商管理'" scope="request" />
<s:set name="name" value="'预备金用户'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>


<!-- 查询条件区域 -->
<s:form name="reserverInfoForm"  action="/userInfo/reserverInfo_PageList.action" method="post">
<table  width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td>用户名:</td>
		<td><input name="reserverInfo.accountLogin" type="text" value='<s:property value="reserverInfo.accountLogin"/>'></td>
		<td>公司名:</td>
		<td><input name="reserverInfo.corporateName" type="text" value='<s:property value="reserverInfo.corporateName"/>'></td>
		<td >开通时间:</td>
		<td><input type="text" name="reserverInfo.startDate" value ="<s:date name = 'reserverInfo.startDate' format='yyyy-MM-dd HH:mm:ss' />" id="d523" class="Wdate"      onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'d521\')}'})"  />
		至<input type="text" name="reserverInfo.endDate" value ="<s:date name = 'reserverInfo.endDate' format='yyyy-MM-dd HH:mm:ss' />" id="d521" class="Wdate"      onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'d523\')}'})" /></td>	
		
    </tr>
    <tr>
    <td>联系电话:</td>
		<td><input name="reserverInfo.phone" type="text" value='<s:property value="reserverInfo.phone"/>'/> </td>
		<td >状态:</td>
		<td ><select name="reserverInfo.isAvailable" style="width:75">
		<option value="" <s:if test='reserverInfo.isAvailable==""'>selected="selected"</s:if>>所有</option>
		<option value="1" <s:if test='reserverInfo.isAvailable=="1"'>selected="selected"</s:if>>有效</option>
		<option value="2" <s:if test='reserverInfo.isAvailable=="2"'>selected="selected"</s:if>>停用</option></select></td>
		<td>结算周期:</td>
		<td><select name="reserverInfo.payType" style="width:75">
		<option value="" <s:if test='reserverInfo.payType==""'>selected="selected"</s:if>>所有</option>
		<option value="1" <s:if test='reserverInfo.payType=="1"'>selected="selected"</s:if>>月度结算</option>
		<option value="2" <s:if test='reserverInfo.payType=="2"'>selected="selected"</s:if>>季度结算</option>
		<option value="3" <s:if test='reserverInfo.payType=="3"'>selected="selected"</s:if>>半年结算</option>
		<option value="4" <s:if test='reserverInfo.payType=="4"'>selected="selected"</s:if>>年度结算</option></select></td>
		<td align="right"><INPUT TYPE="submit" class="queryBtn" value="">&nbsp;&nbsp;&nbsp;&nbsp;</td>	
    </tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="list_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
		<th align="center" >用户名</th>
		<th align="center" >公司名</th>
		<th align="center" >联系电话</th>		
		<th align="center" >结算周期</th>
		<th align="center" >总额度</th>
		<th align="center" >可用额度</th>
		<th align="center" >状态</th>
		<th align="center" >开通时间</th>
		<th align="center" >操作</th>
	</tr>
  <s:iterator id="custiterator" value="page.dataList">
	<tr>
		<td align="center">
		  <s:property value="accountLogin" />
		</td>
		<td align="center">
		  <s:property value="corporateName" />
		</td>
		<td align="center">
		  <s:property value="phone" />
		</td>
		<td align="center">
		  <s:if test="%{payType==1}">
			     月度结算
			</s:if>
			<s:elseif test="%{payType==2}">
			    季度结算
			</s:elseif>
			<s:elseif test="%{payType==3}">
			    半年结算
			</s:elseif>
			<s:elseif test="%{payType==4}">
			    年度结算
			</s:elseif>
			<s:if test="%{reserveId==null}">
			    --
			</s:if>
		</td>
		<td align="center">
		  <s:if test="%{reserveId==null}">
			    --
		  </s:if><s:else>
		  		￥<s:property value="%{formatDouble(totalLimit)}" />
		  </s:else>
		</td>
		<td align="center">
		  <s:if test="%{reserveId==null}">
			    --
		  </s:if><s:else>
		  	 ￥<s:property value="%{formatDouble(remainLimit)}" />
		  </s:else>
		</td>
		<td align="center">
		  <s:if test="%{isAvailable==1}">
			    有效
		  </s:if> <s:elseif test="%{isAvailable==2}">
		                停用
		  </s:elseif>
		  <s:else>
		  	  未开通
		  </s:else>
		</td>
		<td align="center">
			<s:date name = "openDate" format="yyyy-MM-dd HH:mm:ss"/>
			<s:if test="%{reserveId==null}">
			    --
			</s:if>
		</td>
		<td width="120px;">
			<s:if test="%{reserveId==null}">
			    <img title="开通" style="cursor: pointer;" src="/etc/images/icon/kaitong.png"  onclick="gotoOpen(null,<s:property value="userLoginId"/>)"/>
			</s:if>
			 <s:if test="%{isAvailable==1}">
			     <img title="详情" style="cursor: pointer;" src="/etc/images/icon/search.png"  onclick="gotoDetail(<s:property value="reserveId"/>,1,null)"/>
			     <img title="修改" style="cursor: pointer;" src="/etc/images/icon/xiugai.png"  onclick="gotoDetail(<s:property value="reserveId"/>,2,<s:property value="userLoginId"/>)"/>
				<img title="调整" style="cursor: pointer;" src="/etc/images/icon/tizheng.png"  onclick="gotoChange(<s:property value="reserveId"/>,<s:property value="userLoginId"/>)"/>
				<img title="关闭" style="cursor: pointer;" src="/etc/images/icon/guanbi.png"  onclick="gotoClose(<s:property value="reserveId"/>,<s:property value="userLoginId"/>)"/>
			</s:if>
			<s:elseif test="%{isAvailable==2}">
			 <img title="详情" style="cursor: pointer;" src="/etc/images/icon/search.png"  onclick="gotoDetail(<s:property value="reserveId"/>,1,null)"/>
			 <img title="修改" style="cursor: pointer;" src="/etc/images/icon/xiugai.png"  onclick="gotoDetail(<s:property value="reserveId"/>,2,<s:property value="userLoginId"/>)"/>
			 <img title="开通" style="cursor: pointer;" src="/etc/images/icon/kaitong.png"  onclick="gotoOpen(<s:property value="reserveId"/>,<s:property value="userLoginId"/>)"/>
			</s:elseif>
		</td>
		</tr>
	</s:iterator>
</table>
<table width="98%" align="center" class="page_table">
	<tr>
		<td>
			<s:set name="form_name"  value="'reserverInfoForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>

<script type="text/javascript">
	//调整
	function gotoChange(reserveId,userLoginId){
		location.href="/userInfo/reserverInfo_edit.action?reserveId="+reserveId+"&&userLoginId="+userLoginId+"&&opeaderId=4"+"&&editId="+3;
	}
	//详情修改
	function gotoDetail(reserveId,editId,userLoginId){
		location.href="/userInfo/reserverInfo_edit.action?reserveId="+reserveId+"&&opeaderId=1"+"&&editId="+editId+"&&userLoginId="+userLoginId;
	}

	//开通
    function gotoOpen(reserveId,userLoginId){
    	if(reserveId==null){
    		location.href="/userInfo/reserverInfo_edit.action?userLoginId="+userLoginId+"&&opeaderId=2"+"&&editId=4";
    	}else {
    		location.href="/userInfo/reserverInfo_edit.action?reserveId="+reserveId+"&&userLoginId="+userLoginId+"&&opeaderId=3"+"&&editId=4";
    	}
    }
    //关闭
    function gotoClose(reserveId,userLoginId){
    	if (confirm("是否确认关闭? ") == true) {
    		location.href="/userInfo/reserverInfo_reserverClose.action?reserveId="+reserveId+"&&userLoginId="+userLoginId;
    	}
    }
</script>

<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</body>
</html>

