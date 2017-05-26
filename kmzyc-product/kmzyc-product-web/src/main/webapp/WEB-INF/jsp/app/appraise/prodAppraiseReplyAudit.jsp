<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<Script language="JavaScript" src="/etc/js/Form.js" type="text/javascript"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>

<style type="text/css">
	.eidt_rowTitle{padding:0px;}
</style>
</head>
<body>


<!-- 导航栏 -->
<s:set name="parent_name" value="'产品评价'" scope="request"/>
<s:set name="name" value="'评价管理'" scope="request"/>
<s:set name="son_name" value="'评价回复审核'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>

<s:form action="/app/gotoAuditProdAppraise.action" method="POST" namespace='/app' onsubmit="return Validator.Validate(this,2)"  id="frm" name='frm'>



<!-- 数据编辑区域 -->
<table width="95%" class="edit_table" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C7D3E2" style="border-collapse: collapse;font-size:12px;">
	<tr> 
		<th colspan="4" align="left" class="edit_title">评价信息</th>
		<s:hidden name="prodApp.appraiseId"></s:hidden>
		<input type="hidden" value="${prodApp.appraiseId}" name="prodAppId"></input>
		<s:hidden name="prodApp.custName"></s:hidden>
		<s:hidden id="prodAppCheckStatus" name="prodApp.checkStatus" ></s:hidden>
		<s:if test="prodApp.elite==null">
			<input type="hidden" name="prodApp.elite" value="0"></input>
		</s:if>
		<s:else>
			<input type="hidden" name="prodApp.elite" value="${prodApp.elite}"></input>
		</s:else>
	</tr>
	<tr> 
		<th align="right" class="eidt_rowTitle">产品名称：</th>
		<td> 
			<s:property value='prodApp.productName'/>
		</td>
		<th align="right" class="eidt_rowTitle">产品购买日期：</th>
		<td> 
			<s:date name='prodApp.prodBuyDate' format="yyyy-MM-dd HH:mm:ss" />
		</td>
	</tr>
	<tr> 
		<th width="10%" align="right" class="eidt_rowTitle">分数：</th>
		<td width="40%"> 
			<s:property value='prodApp.point'/>分
		</td>
		<th width="10%" align="right" class="eidt_rowTitle">满意度：</th>
		<td width="40%"> 
			<s:property value='prodApp.satisficing'/>
		</td>
	</tr>
	<s:if test='prodApp.propName1!=null||prodApp.propName2!=null'>
		<tr> 
			<th width="10%" align="right" class="eidt_rowTitle">
				<s:property value='prodApp.propName1'/><s:if test='prodApp.propName1!=null'>：</s:if>
			</th>
			<td width="40%"> 
				<s:if test='prodApp.propVal1!=null'>
					<s:property value='prodApp.propVal1'/>
					&nbsp;&nbsp;
					<s:property value='prodApp.propPoint1'/>分
				</s:if>
			</td>
			<th width="10%" align="right" class="eidt_rowTitle">
				<s:property value='prodApp.propName2'/><s:if test='prodApp.propName2!=null'>：</s:if>
			</th>
			<td width="40%"> 
				<s:if test='prodApp.propVal1!=null'>
					<s:property value='prodApp.propVal2'/>
					&nbsp;&nbsp;
					<s:property value='prodApp.propPoint2'/>分
				</s:if>
			</td>
		</tr>
	</s:if>
	<tr> 
		<th align="right" class="eidt_rowTitle">评价人名称：</th>
		<td> 
			<s:property value='prodApp.custName'/>
		</td>
		<th align="right" class="eidt_rowTitle">评价时间：</th>
		<td> 
			<s:date name="prodApp.appraiseDate" format="yyyy-MM-dd HH:mm:ss" />
		</td>
	</tr>
	<tr> 
		<th align="right" class="eidt_rowTitle">评价内容：</th>
		<td colspan="3"> 
			<s:property value='prodApp.appraiseContent'/>
			<s:if test="prodApp.checkStatus == 0 "><span style="color:red;margin-left:20px;">【待审核】</span></s:if> 
					<s:if test="prodApp.checkStatus == 1 "><span style="color:red;margin-left:20px;">【审核已通过】</span></s:if>
					<s:if test="prodApp.checkStatus == 2 "><span style="color:red;margin-left:20px;">【审核不通过】</span></s:if>
					<input type="button"  value="评价审核通过" onClick="changeStatus1(1);" />
			<input type="button"  value="评价审核不通过" onClick="changeStatus1(2);" />
		</td>
	</tr>
	<s:if test='#request.addContentList != null && #request.addContentList.size != 0'>
	  	<s:iterator value="#request.addContentList">
	  	<input type="hidden" name="addContentIds" value="<s:property value='addContentId' />"></input>
			<tr> 
				<th align="right" class="eidt_rowTitle">追加评价：</th>
				<td colspan="3"> 
					<s:property value='addContent' /><span style="color:red;margin-left:20px;">【<s:property value="#request.auditStatusMap[checkStatus]"/>】</span>
					<s:if test="prodApp.addtoContent == 1">
			<input type="button"  value="追加审核通过" onClick="changeStatusAdd(1);" />
			<input type="button"  value="追加审核不通过" onClick="changeStatusAdd(2);" />
			</s:if>
				</td>
			</tr>
		</s:iterator>
	</s:if>
	<s:else>
		<tr> 
			<th align="right" class="eidt_rowTitle">追加评价：</th>
			<td colspan="3"> 
				暂无追加评价
			</td>
		</tr>
	</s:else>
	
	<s:if test='#request.replyList != null && #request.replyList.size != 0'>
	
		<s:iterator value="#request.replyList">
		<input type="hidden" value="<s:property value='apprReplyId' />" name="delId"></input>
			<tr> 
				<th align="right" class="eidt_rowTitle">客服回复：</th>
				<td colspan="3">
					<s:property value='replyContent' />
					<s:if test="replyStatus == 0 "><span style="color:red;margin-left:20px;">【待审核】</span></s:if> 
					<s:if test="replyStatus == 1 "><span style="color:red;margin-left:20px;">【审核已通过】</span></s:if>
					<s:if test="replyStatus == 2 "><span style="color:red;margin-left:20px;">【审核不通过】</span></s:if>
					<s:if test="prodApp.replyContent == 1">
			<input type="button"  value="回复审核通过" style="height: 30px;" onClick="changeStatus(1);" />
			<input type="button"  value="回复审核不通过" style="height: 30px;" onClick="changeStatus(2);" />
			</s:if>
				</td>
			</tr>
		</s:iterator>
	</s:if>
	<s:else>
		<tr> 
			<th align="right" class="eidt_rowTitle">客服回复：</th>
			<td colspan="3"> 
				暂无客服回复
			</td>
		</tr>
	</s:else>
	<!-- <s:if test='prodApp.checkStatus == 0 && type != "view" '>
		
	</s:if>-->
	<tr> 
			<th align="right" class="eidt_rowTitle">是否为精华帖：</th>
			<td colspan="3"> 
				<s:if test="prodApp.elite == 1">
					是
				</s:if>
				<s:else>
					否
				</s:else>
			</td>
		</tr>
	<tr> 
			<th align="right" class="eidt_rowTitle">是否设为精华帖：</th>
			<td colspan="3"> 
				<!-- <s:radio list="#{1:'是',0:'否'}" value="0" name="prodApp.elite" ></s:radio>-->
				
				<s:if test="prodApp.elite == 1">
				<input type="button"  value="取消精华帖" onClick="ckElite(${prodApp.appraiseId},0)" />
				</s:if>
				<s:else>
				<input type="button"  value="设置精华帖" onClick="ckElite(${prodApp.appraiseId},1)" />
				</s:else>
			</td>
		</tr>
	<!-- <s:else>
		
	</s:else>-->
	
	<tr> 
		<th align="right" class="eidt_rowTitle">备注：</th>
		<td colspan="3">
			<!-- <s:if test='prodApp.checkStatus == 0 && type != "view" '>
				<s:textarea name="prodApp.des" cols="100" rows="5" ></s:textarea>
			</s:if>-->
			<!--  <s:else>
				
			</s:else>
			-->
			
			<s:textarea name="prodApp.des" cols="100" rows="5" ></s:textarea>
		</td>
	</tr>
</table>

<!-- 底部 按钮条 -->
<table width="98%" align="center" class="edit_bottom" height="30" border="0" cellpadding="0" cellspacing="0" style="font-size:12px;">
	<tr> 
		<td align="center">
			<!--<s:if test='prodApp.checkStatus == 0 && type != "view" '>
			</s:if>
			<s:if test="prodApp.addtoContent == 1">
			<input type="button" class="btnStyle" value="追加审核通 过" onclick="changeStatusAdd(1);" />
			<input type="button" class="btnStyle" value="追加审核不通过" onclick="changeStatusAdd(2);" />
			</s:if>
			<s:if test="prodApp.replyContent == 1">
			<input type="button" class="btnStyle" value="回复审核通 过" onclick="changeStatus(1);" />
			<input type="button" class="btnStyle" value="回复审核不通过" onclick="changeStatus(2);" />
			</s:if>
			<input type="button" class="btnStyle" value="评价审核通 过" onclick="changeStatus1(1);" />
			<input type="button" class="btnStyle" value="评价审核不通 过" onclick="changeStatus1(2);" />-->
			<input type="button" class="backBtn" onClick="gotoList()" />
		</td>
		<td width="20%" align="center"></td>
	</tr>
</table>

</s:form>
 <form name="frm"  method="post">
	<!-- <input type="hidden"  name="pageNum"  value="<s:property value='pageNum' />"   /> -->
	<input type="hidden" value="<s:date name='selectProdApp.startBuyDate' format='yyyy-MM-dd' />" name="selectProdApp.startBuyDate"></input>
	<input type="hidden" value="<s:date name='selectProdApp.endBuyDate' format='yyyy-MM-dd' />" name="selectProdApp.endBuyDate"></input>
	<input type="hidden" value="<s:date name='selectProdApp.startAppDate' format='yyyy-MM-dd' />" name="selectProdApp.startAppDate"></input>
	<input type="hidden" value="<s:date name='selectProdApp.endAppDate' format='yyyy-MM-dd' />" name="selectProdApp.endAppDate"></input>
	<input type="hidden" value="<s:property value='selectProdApp.productSku.product.productTitle'/>" name="selectProdApp.productSku.product.productTitle"></input>
	<input type="hidden" value="<s:property value='selectProdApp.satisficing'/>" name="selectProdApp.satisficing"></input>
	<input type="hidden" value="<s:property value='selectProdApp.custName'/>" name="selectProdApp.custName"></input>
	<input type="hidden" value="<s:property value='selectProdApp.checkStatus'/>" name="selectProdApp.checkStatus"></input>
	<input type="hidden" value="<s:property value='selectProdApp.serchType'/>" name="selectProdApp.serchType"></input>
	<input type="hidden" value="<s:property value='selectProdApp.serchType1'/>" name="selectProdApp.serchType1"></input>
</form>
<SCRIPT LANGUAGE="JavaScript">

function gotoList(){
   // location.href="/app/prodAppraiseShow.action";
    document.forms[1].action= "/app/prodAppraiseShow.action";
	document.forms[1].submit();
	//document.forms['frm'].action="/app/prodAppraiseShow.action";
	//document.forms['frm'].submit();
}

function changeStatus(arg){
	if(confirm("确定执行回复审核操作吗？")){
		//document.forms['frm'].action="/app/appReplyToAuditApp.action?statusArg="+arg;
		document.getElementById("frm").action="/app/appReplyToAuditApp.action?statusArg="+arg;
		//document.forms['frm'].submit();
		//$("#frm").action="/app/appReplyToAuditApp.action?statusArg="+arg;
		$("#frm").submit();
	}
	//document.getElementById("prodAppCheckStatus").value = arg;
	//document.forms['frm'].submit();
}
 function changeStatusAdd(arg){
	 if(confirm("确定执行追加审核操作吗？")){
			//document.forms['frm'].action="/app/addToContentToAuditApp.action?statusArg="+arg;
			document.getElementById("frm").action="/app/addToContentToAuditApp.action?statusArg="+arg;
			//document.forms['frm'].submit();
			$("#frm").submit();
		}
	 }

 function changeStatus1(arg){
	 if(confirm("确定执行评价审核操作吗？")){
		document.getElementById("prodAppCheckStatus").value = arg;
		//document.forms['frm'].action="/app/gotoAuditProdAppraise.action";
		//document.forms['frm'].submit();
		$("#frm").submit();
	 }
	}

	function ckElite(id,ckType){
		 $.ajax({
             url: '/app/updateAppraiseElite.action',
             async:false,
             data: 'prodApp.appraiseId='+id+"&ckType="+ckType,
             success: function(info) {
                if('0' == info){
                	alert("系统异常，操作失败!");
        			return;
                }else{
                	alert("操作成功!");
                	location.href="/app/gotoProdAppraiseView.action?prodAppId="+id+"&type=replyView";
                	//document.forms[1].action= "supplierAuditList.action";
        		    //document.forms[1].submit();
                }
             }
         });
		}
</SCRIPT>


</BODY>
</HTML>


