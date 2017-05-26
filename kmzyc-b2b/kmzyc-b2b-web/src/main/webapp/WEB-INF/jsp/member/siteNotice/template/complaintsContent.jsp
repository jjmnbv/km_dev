<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<s:form id="sform" name="sform" action="addComplaint" method="post"
	theme="simple">
	<s:hidden name="page" id="page" />
	<s:token></s:token>
	<input type="hidden" name="user.loginId" value="<s:property value='#session.b2b_seesionUserId'/>"/>
	<div class="l-right user-m">
		<div class="o-mt">
			<h2>建议投诉</h2>
		</div>
		<div class="user-m fn-t10">
			<div class="mc fn-clear">
				<div class="i-m i-m-w">
					<div class="i-mc">
						<div class="form">
							<div class="item">
								<span class="label">您登录的账号：</span>
                                <strong class="bnesAcctAppealInfo.username">${b2b_sessionUserName}</strong>
							</div>
							<div class="item">
								<span class="label">您反馈的主题：</span> <input type="text"
									style="width: 370px;" name="bnesAcctAppealInfo.appealTitle"
									 class="Complaints-text j_appealTitle" maxlength="20">
								<span id="nospecial0" class="ico-tag" name="specialWarning" style="display:none;" >请勿填写敏感词!</span>
							</div>
							<div class="item">
								<span class="label">投诉类型：</span> 
								<select class="sele" name="bnesAcctAppealInfo.type">
									<option  selected="selected" value="0">请选择</option>
									<option value="1">产品相关</option>
									<option value="2">价格相关</option>
									<option value="3">物流相关</option>
									<option value="4">服务相关</option>
								</select>
							</div>
							<div class="item">
								<span class="label">相关订单：</span> <input type="text"
									style="width: 370px;" name="bnesAcctAppealInfo.order_Desc" class="Complaints-text" maxlength="20">
							<span id="nospecial1" class="ico-tag" name="specialWarning" style="display:none;">请勿填写敏感词!</span>
							</div>
							<div class="item">
								<span class="label">您的反馈内容：</span>
								<textarea name="bnesAcctAppealInfo.appealContent" cols=""
									style="width: 370px;" rows="" class="Complaints-text j_appealContent" maxlength="150"></textarea>
								<span id="nospecial2" class="ico-tag" name="specialWarning" style="display:none;">请勿填写敏感词!</span>
								<p class="prompt">最多可以输入150字</p>
							</div>

						</div>
					</div>
				</div>
			</div>
			<div class="button" style="padding:0 0 0 170px;">
				<div>
					<a class="btn-submit" href="javascript:void(0);"><span>提交</span></a>&nbsp;&nbsp;&nbsp;
					<a class="btn-cancel" href="javascript:void(0);"><span>重置</span></a>
				</div>
			</div>
		</div>
	</div>
	<!--fn-right-->
	</div>
</s:form>
</div>
<div>
	<input type="hidden" id="saveOks" value="<s:property value='saveOks' />" />
</div>