<%@page contentType="text/html;charset=UTF-8"
	import="com.pltfm.sys.util.StaticParams" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人信息管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/sumoselect.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<script type="text/javascript" src="/etc/js/pageCommon.js"></script>
<script type="text/javascript" src="/etc/js/jquery.sumoselect.min.js"></script>

<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
	type="text/css" />
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript" src="/etc/js/app/userAppCommon.js"></script>
<script type="text/javascript">
    /**  进入新增个人信息页面  **/
    function gotoAdd(){
    	$('#personalBasicInfoForm').attr('action','/userInfo/personalBasicInfo_preAdd.action').submit();   
    }

    /**  进入修改个人信息页面  **/
    function gotoUpdate(id,isThird){
      location.href="/userInfo/personalBasicInfo_preUpdate.action?loginInfo.n_LoginId="+id+"&isThird="+isThird;
    }
    /**  进入详情个人信息页面  **/
    function gotoDetail(id,isThird){
      location.href="/userInfo/personalBasicInfo_preDetail.action?loginInfo.n_LoginId="+id+"&isThird="+isThird;
    }

	//异步将用户信息同步到总部会员系统
/*删除推送总部会员系统 	function syncUser2Base(paramAccountLogin){
		openLoadingDiv("用户信息正在同步中....");
		$.ajax({
			async:false,
			url:'/userInfo/personalBasicInfo_syncUser2Base.action',
			type:'POST',
			dataType:'json',
			data:{
				'personalBasicInfo.loginAccount':paramAccountLogin,
				t_i_m_e:Math.random()
			},
			timeout:'6000',
			success:function(data){
				if(data.syncResult="SUCESS"){
					alert("已成功将个人客户资料同步到总部会员系统!");
				}else{
					alert("个人客户资料同步到总部会员系统时失败，请稍后重试或联系系统管理员!");
				}
			},
			error:function(){
				alert("个人客户资料同步到总部会员系统时失败，请稍后重试或联系系统管理员!");
			}
		});	
		closeLoadingDiv();
	} */
    
    
    //导处筛选出来的个人客户
    function exportPersonalInfoList(){
        var startDate=$('#d523').val();
        var endDate=$('#d524').val();
        if(''==startDate || startDate.length<1 || ''==endDate || endDate<1){
			alert('请选择导出时间！');
			return;
		}
        var sDate = new Date(startDate);
        var eDate = new Date(endDate);
        if((eDate.getTime() - sDate.getTime())/(1000*3600*24) > 31){
        	alert('请选择导出时间跨度应在小于1个月！');
        }else{
	  		$('#personalBasicInfoForm').attr('action','/userInfo/personalBasicInfo_exportPersonalInfoList.action').submit();
	  		setTimeout(function(){
	  			$('#personalBasicInfoForm').attr('action','/userInfo/personalBasicInfo_pageList.action');
		  	},200);
        }
    }
    
    //查询提交
    function querySubmit(){
    	$('#personalBasicInfoForm').attr('action','/userInfo/personalBasicInfo_pageList.action').submit();        	
    }
    
    $(document).ready( function() {
    	window.asd = $('.SlectBox').SumoSelect({ csvDispCount: 5,captionFormatAllSelected: "全部" });
    	<s:iterator value= "personalBasicInfo.identity"  id= 'number' >       
    		window.asd.sumo.selectItem("<s:property value= 'number' />");
    	</s:iterator>
    });
    
    
</script>
<style type="text/css">
.SumoSelect{width: 185px;}
.SumoSelect > .CaptionCont { position: relative; border: 1px solid rgb(204, 204, 204); min-height: 14px; background-color: #fff;border-radius:2px;margin:0;}
.SumoSelect > .CaptionCont > span.placeholder { color: #606060;font-style: normal; font-size:12px}
.opt{font-size:12px}
.SelectBox {padding: 3px 8px;}
.SumoSelect > .CaptionCont >span {font-size:12px;color:#606060}
</style>
</head>
<body>
<s:set name="parent_name" value="'客户资料'" scope="request" />
<s:set name="name" value="'个人客户'" scope="request" />
<s:if test="'updateOK'.equals(rtnMessage)">
	<script>
		alert("修改个人信息成功!");
	</script>
</s:if>
<s:if test="'addOK'.equals(rtnMessage)">
	<script>
		alert("新增个人成功!");
	</script>
</s:if>
<s:if test="'deleteOK'.equals(rtnMessage)">
	<script>
		alert("删除个人成功!");
	</script>
</s:if>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div style="height: 90%; overflow-y: auto;">
	<s:form name="personalBasicInfoForm" id="personalBasicInfoForm" action="/userInfo/personalBasicInfo_pageList.action" onsubmit=" return checkAllTextValid(this)" method="post">
	<s:token />
	<input type="hidden" name="export" id="export" value="${export }" />
	<!-- 查询条件区域 -->
	<table width="98%" height="100" class="content_table" align="center"
		cellpadding="0" cellspacing="2">
		<tr>
			<td colspan="100"></td>
		</tr>
		<tr>
			<td>
				会员账号：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="personalBasicInfo.loginAccount" type="text" value="<s:property value='personalBasicInfo.loginAccount'/>">
			</td>
			<td>
				手机号码：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="personalBasicInfo.mobile" type="text"
				value="<s:property value='personalBasicInfo.mobile'/>">
			</td>
			<%-- <td >
				邮箱地址：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="personalBasicInfo.email" type="text"
				value="<s:property value='personalBasicInfo.email'/>">
			</td>
			<td>总部会员编号：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input name="personalBasicInfo.cardNum" type="text" value="<s:property value='personalBasicInfo.cardNum'/>">
            </td> --%>
            <td>
				客户来源：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<s:select name="personalBasicInfo.registerPlatform"
                          list="#request.registerPlatformMap"
                          listKey="key"
                          listValue="value"
                          headerKey=""
                          headerValue="全部"
                          cssClass="width90"/>
            </td>
		</tr>
		<tr>
			<!--  
			<td align="right">姓名：</td>
			<td><input name="personalBasicInfo.name" type="text"
				value="<s:property value='personalBasicInfo.name'/>"></td>-->
			<%-- <td>
				客户来源：&nbsp;<s:select name="personalBasicInfo.registerPlatform"
                          list="#request.registerPlatformMap"
                          listKey="key"
                          listValue="value"
                          headerKey=""
                          headerValue="全部"
                          cssClass="width90"/>
            </td> --%>
             <td>
             	账号状态：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               <select name="personalBasicInfo.n_Status" style="width:120px">
					<option value=""  <s:if test='personalBasicInfo.n_Status==""'>selected="selected"</s:if>>全部</option>
					<option value="1" <s:if test='personalBasicInfo.n_Status=="1"'>selected="selected"</s:if>>可用</option>
					<option value="0" <s:if test='personalBasicInfo.n_Status=="0"'>selected="selected"</s:if>>禁用</option>
				</select>
            </td>
            <td style="vertical-align:middle">客户身份：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 <select name="personalBasicInfo.identity" id="identitySelect" multiple="multiple" placeholder="全部" class="SlectBox">
					<option value="2">供应商</option>
					<option value="4">康美中药城</option>
					<!--删除 <option value="3">采购商</option>
					<option value="5">云商</option>
					<option value="6">机构</option> -->
            </select>
			</td>
		<%--删除 	<td>返利网用户名：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input name="personalBasicInfo.fanliUserName" type="text" value="<s:property value='personalBasicInfo.fanliUserName'/>">
            </td> --%>
            <td>
            	终端来源：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <s:select name="personalBasicInfo.registerDevice"
                          list="#request.registerDeviceMap"
                          listKey="key"
                          listValue="value"
                          headerKey=""
                          headerValue="全部"
                          cssStyle="width:120px"/>
            </td>
		</tr>
		<tr>
            
            
            <td >
				手机状态：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select name="personalBasicInfo.isMobile" style="width:120px">
					<option value=""  <s:if test='personalBasicInfo.isMobile==""'>selected="selected"</s:if>>全部</option>
					<option value="0" <s:if test='personalBasicInfo.isMobile=="0"'>selected="selected"</s:if>>未验证</option>
					<option value="1" <s:if test='personalBasicInfo.isMobile=="1"'>selected="selected"</s:if>>已验证</option>
				</select>
			</td>
            
             
            <td>中药城会员编号：
               <input name="personalBasicInfo.eraNo" type="text" value="<s:property value='personalBasicInfo.eraNo'/>">
            </td>
            <td>中药城会员推荐人编号：
               <input name="personalBasicInfo.recommendedNo" type="text" value="<s:property value='personalBasicInfo.recommendedNo'/>">
            </td>
		</tr>
		<tr>
			<%--删除 <td>
				客户级别：
				<select name="personalBasicInfo.n_LevelId" style="width:120px" />
					<option value="">全部</option>
					<s:iterator value="levelList" id="levelList">
						<s:if test="n_level_id == personalBasicInfo.n_LevelId">
							<option selected="selected"
								value="<s:property value='n_level_id'/>"><s:property
								value="level_name" /></option>
						</s:if>
						<s:else>
							<option value="<s:property value='n_level_id'/>"><s:property
								value="level_name" /></option>
						</s:else>
					</s:iterator>
				</select>
			</td>
			<td>
				邮箱状态：
				<select name="personalBasicInfo.isEmail" style="width:120px" >
					<option value=""  <s:if test='personalBasicInfo.isEmail==""'>selected="selected"</s:if>>全部</option>
					<option value="0" <s:if test='personalBasicInfo.isEmail=="0"'>selected="selected"</s:if>>未验证</option>
					<option value="1" <s:if test='personalBasicInfo.isEmail=="1"'>selected="selected"</s:if>>已验证</option>
				</select>
			</td> --%>
			
            <td>
            	创建时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="d523" class="Wdate" readonly value="<s:date name = 'personalBasicInfo.d_CreateDate' format='yyyy-MM-dd HH:mm:ss' />" name="personalBasicInfo.d_CreateDate" onClick="WdatePicker({el:'d523',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'d524\')}'})" />
							至
						<input type="text" id="d524" class="Wdate" readonly value="<s:date name = 'personalBasicInfo.endDate' format='yyyy-MM-dd HH:mm:ss' />" name="personalBasicInfo.endDate" onClick="WdatePicker({el:'d524',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'d523\')}',maxDate:'%y-%M-%d %H:%m:%s'})" />
			</td>
			<td align="right">
				
				<INPUT TYPE="button" class="queryBtn" value="" onClick="querySubmit()">
                <!-- <input class="addBtn" type="button" value=""
				onclick="gotoAdd();"> -->
                <s:if test="1==export">
					<input type="button" value="导出excel" onClick="exportPersonalInfoList()" />&nbsp;
				</s:if>
			</td>
		</tr>
	</table>
	<!-- 数据列表区域 -->
	<table width="98%" class="list_table" align="center" cellpadding="3"
		cellspacing="0" border="1" bordercolor="#C1C8D2">
		<tr>
			<th align="center">会员账号</th>
			<!-- 
			<th align="center">姓名</th>
			<th align="center">性别</th>
			 -->
			<th align="center">手机号</th>
			<!--删除 <th align="center">邮箱地址</th> -->
			<!-- <th align="center">总部会员编号</th> -->
			<!-- 
			<th align="center">证件号码</th>
			 -->
			<th align="center">手机状态</th>
			<!--删除 <th align="center">邮箱状态</th>
			<th align="center">客户级别</th> -->
			<th align="center">客户身份</th>
			<th align="center">客户来源</th>
			<th align="center">中药城会员编号</th>
			<th align="center">中药城会员推荐人编号</th>
			<!--删除 <th align="center">返利网用户名</th> -->
			<th align="center">终端来源</th>
			<th align="center">账号状态</th>
			<th align="center">创建日期</th>
			<th align="center">操作</th>
			<!-- 
			<th align="center">可用积分</th>
			<th align="center">总积分</th>
			 -->
		</tr>
		<s:if test="#request.isMenu=='true'">
					<tr>
						<td colspan="100">
							请输入具体条件进行查询，如果想查询全部数据请直接点击查询按钮
						</td>
					</tr>
		</s:if>
		<s:else>
		<s:iterator id="periterator" value="page.dataList">
			<tr>
				<td align="center"><s:property value="loginAccount" /></td>
				<!-- 
				<td align="center"><s:property value="name" /></td>
				<td align="center"><s:if test="#periterator.sex==0">男</s:if> <s:if
					test="#periterator.sex==1">女</s:if></td>
				 -->
				<td align="center"><s:property value="mobile" /></td>
			<%-- 	<td align="center"><s:property value="email" /></td> --%>
				<%-- <td align="center"><s:property value="cardNum" /></td> --%>
				<!-- 
				<td align="center"><s:property value="certificateNumber" /></td>
				
				 -->
				<td align="center">
					<s:if test="#periterator.isMobile==0">未验证</s:if>
					<s:if test="#periterator.isMobile==1">已验证</s:if>
				</td>
				<%-- <td align="center">
					<s:if test="#periterator.isEmail==0">未验证</s:if>
					<s:if test="#periterator.isEmail==1">已验证</s:if>
				</td>
				<td align="center"><s:property value="#request.levelMap[n_LevelId]" /></td> --%>
				<td align="center">
					<s:if test="#periterator.isCommonUser==1">普通用户</s:if>
					<s:if test="#periterator.isSupplier==1">，供应商</s:if>
					<s:if test="#periterator.isPurchaser==1">，采购商</s:if>
					<s:if test="#periterator.isTourist==1">，游客</s:if>
					<s:if test="#periterator.isEra==1">，康美中药城</s:if>
					<s:if test="#periterator.isSpreader==1">，云商</s:if>
					<s:if test="#periterator.isCrowder==1">，机构</s:if>
				</td>
				<td align="center">
					<s:if test="#periterator.isThird==1">
						授权登录
					</s:if>
                    <s:if test="#periterator.isThird!=1">
                    	<s:property value="#request.registerPlatformMap[registerPlatform]" />
                    </s:if>
                </td>
                <!-- #todo 时代会员编号 -->
                <td align="center"><s:property value="eraNo" /></td>
                <!-- #todo 时代会员推荐人编号-->
                <td align="center"><s:property value="recommendedNo" /></td>
                <!-- #todo 返利网用户名-->
                <%--删除 <td align="center"><s:property value="fanliUserName" /></td> --%>
                <td align="center">
                	<s:property value="#request.registerDeviceMap[registerDevice]" />
                </td>
                <td align="center">
                	<s:if test="#periterator.n_Status==0">禁用</s:if>
					<s:if test="#periterator.n_Status==1">可用</s:if>
				</td>
                <td align="center">
                	<s:date name="d_CreateDate" format="yyyy-MM-dd HH:mm:ss" />
                </td>
                <!-- 
				<td align="center"><s:property value="n_AvailableIntegral" /></td>
				<td align="center"><s:property value="n_TotalIntegral" /></td>
				 -->
				<td><img title="详情" style="cursor: pointer;"
					src="/etc/images/icon_msn.gif"
					onclick="gotoDetail(<s:property value='n_LoginId'/>,<s:property value='#periterator.isThird'/>)" /> 
					<s:if test="#periterator.n_Status==1">
						<img title="修改" style="cursor: pointer;"
							src="/etc/images/icon_modify.png"
							onclick="gotoUpdate(<s:property value='n_LoginId'/>,<s:property value='#periterator.isThird'/>)" />
						<%--删除同步总部会员系统 <img title="同步" style="cursor: pointer;"
							src="/etc/images/icon/houyi.png"
							onclick="syncUser2Base('<s:property value="loginAccount"/>')" /> --%>
					</s:if>
				</td>
			</tr>
		</s:iterator>
		</s:else>
	</table>
	<s:if test="#request.isMenu!='true'">
	<table width="98%" align="center" class="page_table">
		<tr>
			<td><s:set name="form_name" value="'personalBasicInfoForm'"
				scope="request"></s:set> <jsp:include
				page="/WEB-INF/jsp/common/page.jsp"></jsp:include></td>
		</tr>
	</table>
	</s:if>
</s:form></div>
<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
</body>
<!-- 消息提示页面 -->
</html>