<%@ page language="java" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html class="no-js">

<head>
<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
<meta name="renderer" content="webkit|ie-comp|ie-stand"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>发布促销活动－新供应商平台</title>
<jsp:include page="/WEB-INF/jsp/common/template.jsp">
	<jsp:param name="titlePrefix" value="活动信息"></jsp:param>
</jsp:include>
<!-- Bootstrap -->


<script src="/resgys/script/jquery-1.9.1.min.js"></script>
<!-- 弹出层并引用网页 -->
<script type="text/javascript" src="/resgys/script/thickbox_plus.js"></script>

<!-- 弹出层并引用网页 -->
</head>

<body>
	<!--顶部导航开始-->
	<jsp:include page="/WEB-INF/jsp/common/menubars/topMenu_index.jsp"></jsp:include>
	<!--顶部导航结束-->


	<div class="container-fluid">
		<div class="row-fluid">

			<!--左侧菜单开始-->
			<jsp:include
				page="/WEB-INF/jsp/common/menubars/leftMenu_promotion.jsp"></jsp:include>

			<!--左侧菜单结束-->

			<div class="content">
				
					
					
				<div class="row-fluid">
					<!-- block -->
					<div class="block_01">
						<div class="navbar-inner">
							<ul class="breadcrumb">
								<i class="icon-home"></i>
								<li>促销 <span class="divider">/</span></li>
								<li>发布促销活动</li>
							</ul>
						</div>
						<div class="block-content collapse in">
							<!--内容菜单开始-->
							<ul class="nav nav-tabs">
								<li class="active"><a href="#home" data-toggle="tab">发布促销活动</a></li>
							</ul>
							<!--内容菜单结束-->
							<!--提示开始-->
							<div class="alert">
								<button class="close" data-dismiss="alert">&times;</button>
								<strong>提示：</strong>
								促销活动发布成功后，需要您进行审核确认，才能正式生效。活动未生效时，仍可进行编辑修改工作。
							</div>
							<!--提示结束-->
							<form class="form-horizontal" action="addPromotionNew.action"
								method="post" id="frm" name="frm">

								<input type="hidden" value="" id="sellUpType" name="sellUpType"></input>
								<input type="hidden" value="" id="Data" name="prizeData"></input>
								<input type="hidden" value="" id="meetDataType"
									name="meetDataType"></input>
								<fieldset>
									<div class="control-group">

										<label class="control-label "><span class="required">*</span>活动类型：</label>
										<div class="controls">
											<s:select list="%{#request.promotionTypeMap}" listKey="key"
												listValue="value" id="promotionType" name="promotionType"
												headerKey="" headerValue="请选择"></s:select>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="typeahead"><span
											class="required">*</span>活动标题： </label>
										<div class="controls">
											<input type="text" class="span5" maxlength="70"
												name="promotion.promotionTitle" id="promotionTitle">
											<p>此标题仅在后台显示，方便商家查看管理</p>
										</div>
									</div>

									<!--满赠开始-->
									<div class="control-group promotionType" id="promotionType3"
										style="display: none;">
										<label class="control-label" for="textarea2"><span
											class="required">*</span>优惠内容：</label>
										<div class="controls stle stle1"  style="margin-left:180px!important;"
											id="promotionTypepromotionType3">
											<ul>
												<li class="stlebg"><p>
														买满：
														<s:select style="width: 110px;"
															list="#request.meetDataMap"
															listKey="key" listValue="value" id="meetDataType3"></s:select>
													</p>
													<p class="sw400">赠送商品</p>
													<p><span class="stlebga">
														<a href="#" class="addDataSpan"><i class="icon-plus"></i>新增梯级</a>
													    
														<a href="#" class="deleteSpan"><i class="icon-minus"></i>减少梯级</a>
														</span>
													</p></li>
											</ul>
											<div class="controls stle1" id="divIndex31" value="sf">
												<ul class="div_index1">
													<li><p>
															<input name="meetData1" id="meetData1" type="text"
																class="span6">
														</p>
														<p>
															<input name="entity1" id="entity1" type="text"
																class="span11" readonly="readonly"> <input type="hidden"
																name="entity1entity1" id="entity1entity1" />
														</p>
														<p>X</p>
														<p>
															<input type="text" class="span7" name="num1" id="num1">
															件
														</p>
														<p>
															<a id="del01" class="removeDataAv" data_id="div_index1" href="#"
															>清除</a>
															<a id="del01" class="manzeng" href="#"
															>添加</a>
														</p></li>
												</ul>
											</div>


										</div>
									</div>
									<!--满增结束-->

									<!--满减开始-->
									<div class="control-group promotionType" id="promotionType6"
										style="display: none;">
										<label class="control-label" for="textarea2"><span
											class="required">*</span>优惠内容：</label>
										<div class="controls stle stle2"  style="margin-left:180px!important;"
											id="promotionTypepromotionType6">
											<ul >
												<li class="stlebg"><p>
														买满：
														<s:select style="width: 110px;"
															list="#request.meetDataMap" 
															listKey="key" listValue="value" id="meetDataType6"></s:select>
													</p>
													<p style="width: 200px;">减免金额（元）</p>
													<span class="stlebga">
														<a href="#" class="addDataSpan"><i class="icon-plus"></i>新增梯级</a>
													
														<a href="#" class="deleteSpan"><i class="icon-minus"></i>减少梯级</a>
													</span></li>
											</ul>
											<div class="controls stle2">
												<ul>
													<li><p>
															<input name="meetData1" id="meetData1" type="text"
																class="span6">
														</p>
														<p>
															<input name="prizeData1" id="prizeData1" type="text"
																class="span11"> <input type="hidden"
																name="prizeData1prizeData1" id="prizeData1prizeData1"
																marking="double" class="productCount"/>
														</p></li>
												</ul>
											</div>

										</div>
									</div>
									<!--满减结束-->

									<!--加价购开始-->
									<div class="control-group promotionType" style="display: none;"
										id="promotionType5">
										<label class="control-label" for="textarea2"><span
											class="required">*</span>优惠内容：</label>
										<div class="controls stle stle3"  style="margin-left:180px!important;" id="promotionTypepromotionType5">
											<ul >
												<li class="stlebg"><p>
														买满：<s:select style="width: 110px;"
															list="#request.meetDataMap" 
															listKey="key" listValue="value" id="meetDataType5"></s:select>
													</p>
													<p>可换购数量(件)</p>
													<p>可换购商品</p>
													<p>换购价（元）</p>
													<p>
													<span class="stlebga">
														<a  class="addDataSpan"><i class="icon-plus"></i>新增梯级</a>
													
														<a  class="deleteSpan"><i class="icon-minus"></i>减少梯级</a>
														</span>
													</p>	
													</li>
											</ul>

											<div class="controls stle3" id="divIndex51">
												<ul class="div_index2">
													<li><p>
															<input name="meetData1" id="meetData1" type="text"
																class="span8">
														</p>
														<p>
															<input name="num5" id="num5" type="text" class="span7">
														</p>
														<p>
															<input name="entity1" id="entity1" type="text"
																class="span9" readonly="readonly"> <input type="hidden"
																name="entity1entity1" id="entity1entity1" />
														</p>
														<p>
															<input name="prizeData1" id="prizeData1" type="text"
																class="span7">
														</p>
														<p>
															<a href="#"  class="removeDataAv" data_id="div_index1" href="#"
															>清除</a>
															<a id="del01" class="manzeng" href="#"
															>添加</a>
														</p></li>
												</ul>
											</div>

										</div>
									</div>
									<!--加价购结束-->

									<!--打折开始-->
									<div class="control-group promotionType" id="promotionType8"
										style="display: none;">
										<label class="control-label" for="textarea2"><span
											class="required">*</span>折扣幅度：</label>
										<div class="controls stle stle3"  style="margin-left:180px!important;"
											id="promotionTypepromotionType8">
											<ul>
												<li class="stlebg fontc">折扣百分比须填入0~99之间的正整数。</li>
											</ul>
											<div class="controls stle3">
												<ul>
													<li>活动商品价格为原价的：<input type="text" class="span1"
														name="discount" id="discount" maxlength="6"> %
													</li>
												</ul>
											</div>
											<div class="controls stle3">
												<ul>
													<li>活动库存(如有)卖光后：<s:select cssStyle="width:200px"
															list="#request.sellUpTypeMap" listKey="key"
															listValue="value"  
															id="setSellUpTypeDiscount"></s:select></li>
												</ul>
											</div>
										</div>
									</div>
									<!--打折结束-->

									<!--特价开始-->
									<div class="control-group promotionType" id="promotionType10"
										style="display: none;">
										<label class="control-label" for="textarea2"><span
											class="required">*</span>优惠内容：</label>
										<div class="controls stle stle3"  style="margin-left:180px!important;"
											id="promotionTypepromotionType10">
											<ul>
												<li class="stlebg fontc">如需为每个活动商品指定特价价格，活动商品统一价格输入请留空。</li>
											</ul>
											<div class="controls stle3">
												<ul>
													<li>活动商品价格统一为：<input name="salePrice" id="salePrice"
														type="text" class="span1" maxlength="7"> 元
													</li>
												</ul>
											</div>
											<div class="controls stle3">
												<ul>
													<li>活动库存(如有)卖光后：<s:select cssStyle="width:200px"
															list="#request.sellUpTypeMap" listKey="key"
															listValue="value" 
															id="setSellUpTypeSale"></s:select></li>
												</ul>
											</div>
										</div>
									</div>
									<!--特价结束-->



									<div class="form-actions">
										<a class="btn btn-success btn-large" id="addPromotion">
											下一步，编辑活动信息 <i class="icon-chevron-right icon-white"></i>
										</a>
									</div>
								</fieldset>
							</form>

							<!--结束-->
							<form action="toUpdataPromotionNew.action" method="POST"
								namespace='/promotion' id="toUpdataPromotionNew"
								name='toUpdataPromotionNew'></form>
						</div>
					</div>
				</div>
				<hr>
			</div>
			<jsp:include page="/WEB-INF/jsp/common/menubars/bottomMenu.jsp"></jsp:include>
</body>

</html>