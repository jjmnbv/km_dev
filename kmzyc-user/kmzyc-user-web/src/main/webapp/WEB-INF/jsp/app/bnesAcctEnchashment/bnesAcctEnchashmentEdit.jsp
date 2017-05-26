<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@ page import="com.pltfm.app.util.Constants"%>
<%@ page import="com.pltfm.app.util.EnchashmentStatusEnumType"%>

<html>
	<head>
		<title>余额提现信息</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
		<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
		<script charset="utf-8" src="/kindeditor/kindeditor.js"></script>
		<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
		<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>
		<script type="text/javascript" src="/etc/js/app/bnesAcctEnchashment/bnesAcctEnchashmentEdit.js"></script>
		<script type="text/javascript">
			
		var editor;
		KindEditor.ready(function(K) {
			editor = K.create('textarea[name="bnesAcctEnchashment.remarks"]', {
				cssPath : '/kindeditor/plugins/code/prettify.css',
				items:['source','|','undo','redo','|','cut','copy', 'paste',
				       '|','justifyleft','justifycenter','justifyright','justifyfull','indent','outdent','|','fontname','fontsize',
				       '|','forecolor','hilitecolor','bold','italic','underline','lineheight','removeformat','|','image'],
				uploadJson : '/kindeditor/jsp/upload_json.jsp',
				fileManagerJson : '/kindeditor/jsp/file_manager_json.jsp',
				allowFileManager : true
			});
		});
		
		var i=0;	
		$(function(){
			if($('#fromConfirmMenu').val()=='yes'){
				if(i==1){
					return;
				}
				var confirmOptions={
						width:750,
						height:165,
						pasteType : 2,
						items:['undo','redo','|','cut','copy', 'paste',
						       '|','justifyleft','justifycenter','justifyright','justifyfull','indent','outdent','|','fontname','fontsize',
						       '|','forecolor','hilitecolor','bold','italic','underline','lineheight','removeformat','|','image'],
					            afterCreate : afterCreateHandler,
					            afterChange : afterChangeHandler
						 };
					
				KindEditor.ready(function(K) {
					window.confirmEditor = K.create("textarea[name='bnesAcctEnchashment.confirmRemarks']",confirmOptions);
				});

				
				$('#buttonFinash').bind('click',function(){
					
					var limitNum = 500;  //设定限制字数
					if(confirmEditor.count()>limitNum){
				   		alert("提现备注信息超过允许输入的最大字符数，请修改提现备注信息！");
				   		return;
					}
					if(confirm("确认申请已提现完成了吗？\n\n请务必确认已经完成转账动作再进行此操作！\n\n确认提现完成将按申请金额从申请人账户冻结余额中进行扣除。")){
						openLoadingDiv("数据信息处理中，请稍候....");
						confirmEditor.sync();
						$("#enchashmentStatus").val('<%=EnchashmentStatusEnumType.Stuats_Complete.getType()%>');
						document.bnesAcctEnchashmentForm.action='/accounts/bnesAcctEnchashment_finashEnchashment.action';
						document.bnesAcctEnchashmentForm.submit();
					}	
				});

				$('#buttonReject').bind('click',function(){
					
					var limitNum = 500;  //设定限制字数
					if(confirmEditor.count()>limitNum){
				   		alert("提现备注信息超过允许输入的最大字符数，请修改提现备注信息！");
				   		return;
					}
					confirmEditor.sync();
					if($('textarea[id="bnesAcctEnchashment.confirmRemarks"]').val()==''){
						alert("提现失败必须填写提现备注信息！");
						return;
					}else{
						if(confirm("确认申请已提现失败吗？\n\n请务必确认转账动作失败后 再进行此操作！\n\n提现失败后，用户需要重新申请提现。")){
							openLoadingDiv("数据信息处理中，请稍候....");
							$("#enchashmentStatus").val('<%=EnchashmentStatusEnumType.Status_Fail.getType()%>');
							document.bnesAcctEnchashmentForm.action='/accounts/bnesAcctEnchashment_finashEnchashment.action';
							document.bnesAcctEnchashmentForm.submit();
						}
					 }
				});
			}
		}); 
		
	
	
		function newCheckEnchashment(status){
		if(i==1){
			return;
		}
			editor.sync();
			if(status != null || status != ""){
				 $("#enchashmentStatus").val(status);
				 var confir="";
				 if(status==3){
					 confir="确认通过当前申请吗？\n审核通过后请注意须按本申请金额转账到申请人收款账户中。";
				 }
				 if(status==2){
					 confir="确认拒绝当前申请吗？\n拒绝操作不可逆，拒绝后如仍需提现，需由用户重新提起提现申请。";
				 }
				 if(confirm(confir)){
					 var remark=$.trim($("#remarks").val());
					 if(status==2 &&editor.count()==0){
							alert("请填写取现审核备注");
							return;
					 }if(editor.count()>500){
					   		alert("提现备注信息超过允许输入的最大字符数(500)，请修改提现备注信息！");
					   		return;
						} else {
						 $("#buttonTrue").attr('disabled',"true");
						 $("#buttonFalse").attr('disabled',"true");
						 i++;
						 document.bnesAcctEnchashmentForm.submit();
					 }
				 }
			}
		}
		
		
  </script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'账务管理'" scope="request" />
		<s:if test="bnesAcctEnchashment.enchashmentStatus==3 && fromConfirmMenu=='yes'">
		<s:set name="name" value="'余额提现确认'" scope="request" />
		</s:if>
		<s:else>
		<s:set name="name" value="'余额提现管理'" scope="request" />
		</s:else>
		
		<s:set name="son_name" value="'余额提现信息'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<s:form id="bnesForm" name="bnesAcctEnchashmentForm" action="/accounts/bnesAcctEnchashment_checkEnchashment.action" method="post">
			<input type="hidden" name="bnesAcctEnchashment.enchashmentId" value="<s:property value='bnesAcctEnchashment.enchashmentId'/>"/>
			<input type="hidden" id="enchashmentStatus" name="enchashmentStatus" value="<s:property value='bnesAcctEnchashment.enchashmentStatus'/>"/>
			<input type="hidden" id="fromConfirmMenu" name="fromConfirmMenu" value='<s:property value="fromConfirmMenu"/>' />
			<s:token />
			<!-- 数据编辑区域 -->
			<table width="70%" class="edit_table" cellpadding="2" cellspacing="0"
				border="1" bordercolor="#C7D3E2" style="border-collapse: collapse">
				<tr>
					<th colspan="2" align="left" class="edit_title">
						余额提现信息
					</th>
				</tr>
				<tr>
					<td width="25%" class="eidt_rowTitle">申请账号：</td>
					<td width="80%"><s:property value="bnesAcctEnchashment.loginAccount"/></td>
				</tr>
				<tr>
					<td width="25%" class="eidt_rowTitle">提现来源：</td>
					<td width="80%"><s:property  value="#request.enchashmentResourceEnumMap[bnesAcctEnchashment.enchashmentResource]"/></td>
				</tr>
				<tr>
					<td width="25%" class="eidt_rowTitle">提现人说明：</td>
					<td width="80%"><s:property value="bnesAcctEnchashment.loginAccount"/>
					(	<!-- 供应商  0-->
						<s:if test="bnesAcctEnchashment.enchashmentResource ==0">
							<s:property value="bnesAcctEnchashment.bankAccountName"/>
						</s:if>
						<!-- 微商  1-->
						<s:if test="bnesAcctEnchashment.enchashmentResource ==1">
							<s:property value="bnesAcctEnchashment.vsNumber"/>
						</s:if>
						<!-- 机构  2-->
						<s:if test="bnesAcctEnchashment.enchashmentResource ==2">
							<s:property value="bnesAcctEnchashment.bankAccountName"/>
						</s:if>
					) 
					</td>
				</tr>
				<tr>
					<td width="25%" class="eidt_rowTitle">申请时间：</td>
					<td width="80%"><s:date name="bnesAcctEnchashment.createDate" format="yyyy-MM-dd HH:mm:ss"/> </td>
				</tr>
				<tr>
					<td width="25%" class="eidt_rowTitle">提现金额：</td>
					<td width="80%">
					<span style="color:rgb(153,0,85);font-weight:bold">
					<input value="${bnesAcctEnchashment.enchashmentAmount}" name="bnesAcctEnchashment.enchashmentAmount"  type="hidden"/>
						<fmt:formatNumber value="${bnesAcctEnchashment.enchashmentAmount}" type="currency" pattern="#,##0.00元"/>
					</span>
					</td>
				</tr>
				<tr>
					<td width="25%" class="eidt_rowTitle">提现方式：</td>
					<td width="80%">
						<s:if test="bnesAcctEnchashment.enchashmentType==0">
							银行卡
						</s:if><s:elseif test="bnesAcctEnchashment.enchashmentType==1">
						          支付宝
						</s:elseif>
					</td>
				</tr>
				
					<tr>
						<td width="25%" class="eidt_rowTitle">收款户名：</td>
						<td width="80%"><s:property value="bnesAcctEnchashment.bankAccountName"/> </td>
					</tr>
					<tr>
						<td width="25%" class="eidt_rowTitle">收款机构/开户行：</td>
						<td width="80%"><s:property value="bnesAcctEnchashment.bankOfDeposit"/> <s:property value="bnesAcctEnchashment.bankBranchName"/></td>
					</tr>
					<tr>
						<td width="25%" class="eidt_rowTitle">收款账号：</td>
						<td width="80%"><s:property value="bnesAcctEnchashment.bankCardNumber"/> </td>
					</tr>
				
				
				<s:elseif test="bnesAcctEnchashment.enchashmentResource==1">
						<tr>
						<td width="25%" class="eidt_rowTitle">微商号：</td>
						<td width="80%"><s:property value="bnesAcctEnchashment.vsNumber"/> </td>
					</tr>
					<tr>
						<td width="25%" class="eidt_rowTitle">微信账号：</td>
						<td width="80%"><s:property value="bnesAcctEnchashment.vxAccount"/> </td>
					</tr>
				</s:elseif>
				<tr>
					<td width="25%" class="eidt_rowTitle">对应交易流水：</td>
					<td width="80%">
						<s:property value="bnesAcctEnchashment.accountTransactionId"/> 
						<a href="/accounts/tration_showTrationList.action?bnesAcctTransListQuery.accountLogin=${bnesAcctEnchashment.loginAccount}">查看申请者余额流水记录&gt;&gt;</a>
					</td>
				</tr>
				
				<s:if test="bnesAcctEnchashment.enchashmentStatus != 0">
					<tr>
						<td width="25%" class="eidt_rowTitle">状态：</td>
						<td width="80%">
					    <span style="font-weight:bold"><s:property value="#request.enchashmentStatusEnumMap[bnesAcctEnchashment.enchashmentStatus]" /></span>
					    </td>
					</tr>
					<tr>
						<td width="25%" class="eidt_rowTitle">审核人：</td>
						<td width="80%"><s:property value="bnesAcctEnchashment.auditName"/> </td>
					</tr>					
					<tr>
						<td width="25%" class="eidt_rowTitle">审核时间：</td>
						<td width="80%"><s:date name="bnesAcctEnchashment.modifyDate" format="yyyy-MM-dd HH:mm:ss"/> </td>
					</tr>
				</s:if>
				<tr>
					<td width="25%" class="eidt_rowTitle">审核备注：
					</td>
					<td width="80%">
					    <s:if test="bnesAcctEnchashment.enchashmentStatus==0">
					    	<s:textarea name="bnesAcctEnchashment.remarks" id="remarks" cssStyle="width:90%;height:300px;visibility:hidden;"></s:textarea>
					    </s:if>
					    <s:else>
					    ${bnesAcctEnchashment.remarks}
					    </s:else>
					</td>
				</tr>
				
				<!-- 提现完成状态需要显示的信息-->
				<s:if test="bnesAcctEnchashment.enchashmentStatus==1||bnesAcctEnchashment.enchashmentStatus==4">
					<tr>
						<td width="25%" class="eidt_rowTitle">提现操作人：</td>
						<td width="80%"><s:property value="bnesAcctEnchashment.enchashmentOperName"/> </td>
					</tr>					
					<tr>
						<td width="25%" class="eidt_rowTitle">提现完成时间：</td>
						<td width="80%"><s:date name="bnesAcctEnchashment.finashDate" format="yyyy-MM-dd HH:mm:ss"/> </td>
					</tr>
					<tr>
						<td width="25%" class="eidt_rowTitle">提现备注：</td>
						<td width="80%"><s:property value='bnesAcctEnchashment.confirmRemarks' escape="false"/></td>
					</tr>
				</s:if>
				<!-- 提现完成备注 -->
				<s:if test="bnesAcctEnchashment.enchashmentStatus==3 && fromConfirmMenu=='yes'">
					<td width="25%" class="eidt_rowTitle">提现备注：
						<br/><span id="confirmMarksTip"></span>
					</td>
					<td width="80%"><textarea id="bnesAcctEnchashment.confirmRemarks" name="bnesAcctEnchashment.confirmRemarks"></textarea></td>
				</s:if>
			</table>
			<!-- 底部 按钮条 -->
			<table width="80%" class="edit_bottom" height="30" border="0"
				cellpadding="0" cellspacing="0">
				<tr>
					<td align="left">
						<s:if test="bnesAcctEnchashment.enchashmentStatus==0">
							<input id="buttonTrue" class="btn-custom" type="button" value="审核通过" onClick="newCheckEnchashment(3)"/>
							&nbsp;&nbsp;
							<input id="buttonFalse" class="btn-custom" type="button" value="审核拒绝" onClick="newCheckEnchashment(2)"/>
						</s:if>
						<!--提现完成操作按钮 -->
						<s:if test="bnesAcctEnchashment.enchashmentStatus==3 && fromConfirmMenu=='yes'">
							<input  id="buttonFinash" type="button" value="提现确认" class="btn-custom" />&nbsp;&nbsp;
							<input  id="buttonReject" type="button" value="提现失败" class="btn-custom"/>&nbsp;&nbsp;
						</s:if>
						&nbsp;&nbsp;
						<input class="backBtn" onClick="window.history.back()" type="button"  value="">
					</td>
					<td width="25%" align="center"></td>
				</tr>
			</table>
		</s:form>
		<!-- 消息提示 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		
	</BODY>
</HTML>