<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>退换货申请</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/etc/css/orderTab.css">
<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
<script type="text/javascript" src="/etc/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/etc/js/urchin.js"></script>
<script type="text/javascript" src="/etc/js/order_tab.js"></script>
<style type="text/css">
li {list-style-type:none;line-height:25px}
.Complaints-text{width:600px;height:100px}
.upload-photos{ padding:10px 0px;}
.upload-photos li{ height:50px; border:1px solid #e6e6e6; float:left; margin-right:10px;}
.upload-photos li img{ width:50px; height:50px;}
.upload-photos-Address{ background-color:#def2fa; border:1px solid #c7e3f1; padding:10px 0px; height:100px; width:700px;}
.new-Address{ background-color:#fffdee; border:1px solid #edd28b; padding:10px 0px; height:150px; width:700px;}
.upload-photos-Address label{ float:left;}
.upload-photos-form{}
.upload-photos-form span{ padding-left:45px;}
.upload-photos-Address .tisp{ padding-left:80px;}
.upload-photos-Address .tisp ul{ display:block; float:left;}
.upload-photos-Address .tisp ul li{ line-height:25px; color:#e1a156;}
font{color:blue}
</style>
<script type="text/javascript">
$(function(){
	//取消
    $('#cancel').click(function(){
    	history.go(-1); 
    });
	
    $('.js_setNum0').click(function(){
		var type = $(this).val();
		if(type==2){
			$('#exchangeAdressLabel').show();
			$('#address').show();
			$('#alterNum').rules('add',{required:true,min:1,max:$('#maxnum').val()});
			$('#name').rules('add',{required:true});
			$('#phone').rules('add',{required:true,cellphone:true});
			$('#zipcode').rules('add',{required:true,isZipCode:true});
			$('#address').rules('add',{required:true});
			$('#alterComment').rules('add',{realMaxlength:166});
		}else{
			$('#exchangeAdressLabel').hide();
			$('#address').hide();
			$('#alterNum').rules('remove','required min max');
			$('#name').rules('remove','required');
			$('#phone').rules('remove','required cellphone');
			$('#zipcode').rules('remove','required isZipCode');
			$('#address').rules('remove','required');
			$('#alterComment').rules('remove','realMaxlength');
		}
	});
	
    $('#applyForm').validate({     
		rules: {
			'alter.alterNum': {required:true,min:1,max:$('#maxnum').val()},
			'alter.name': {required:true},
			'alter.phone': {required:true,cellphone:true},
			'alter.zipcode': {required:true,isZipCode:true},
			'alter.address': {required:true},
			'alter.alterComment': {realMaxlength:166}
 	   	},
		submitHandler: function (form) {
			$.post('/app/orderBackdownbackDownAction.action',
				$('#applyForm').serialize(),
				function(result){
					alert(result);
	  				if(result.indexOf('失败')>0){
	  					history.go(0);
	  				}else{
	   					location='/app/orderBackdownlistByMapAction.action';
	  				}
	  			}
			);		
       	}
	});
});
</script>
</head>
<body>
<s:set name="parent_name" value="'业务操作'" scope="request"/>
<s:set name="name" value="'退换货管理'" scope="request"/>
<s:set name="son_name" value="'退换货申请'" scope="request"/>
<s:include value="/WEB-INF/jsp/public/title.jsp"/>
<form id="applyForm" name="sform" action="/app/orderBackdownbackDownAction.action" method="post" theme="simple" autocomplete="off">
          <div class="l-right user-m">
            <div class="m-w w-noborder fn-clear fn-t10">
                <div class="wc">
                    <ul class="form-info">
                    	<li>
                    	<label><em>*</em>服务类型：</label>
                    	<span><input name="alter.alterType" type="radio" value="2" checked="checked" class="js_setNum0"></span><span>换货</span>
                    	<span><input name="alter.alterType" type="radio" value="1"  class="js_setNum0"></span><span>退货</span>
                    	<span><input name="alter.alterType" type="radio" value="3"  class="js_setNum0"></span><span>不退货退款</span>
                    	</li>
                        <li><label><em>*</em>申请商品：</label><a href='<s:property value="cmsPagePath"/><s:property value="item.productSkuId"/>.shtml' class="fn-blue" target="_blank">[<s:property value="item.commoditySku"/>]&nbsp;&nbsp;<s:property value="item.commodityName"/></a></li>
                        <li class="upload-photos-Address" style="height:25px">
                        <label><em>*</em>数量：</label>
                        <div class="quantity-form sh-name fn-left">
                        <s:if test="order.orderType==7">
                             <input type="text" name="alter.alterNum" id="alterNum" value='<s:property value="item.commodityNumber"/>' readOnly="true" class="quantity-text js_setNumOnchange">
                        </s:if>                                                   
                        <s:else>
                             <input type="text" name="alter.alterNum" id="alterNum" value="1" class="quantity-text js_setNumOnchange">
                        </s:else>
                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="fn-red">注：商品数量必须&lt;=您的收货数量。</span>
                        </div>
                        <div class="tisp" id="tisp" style="display:none">
                        	<em class="icon-tip fn-left fn-t5"></em>
                            <ul>
                            	<li>如果您购买时享受促销活动的优惠，您的退货商品有可能不能按商品原价全额退款；</li>
                                <li>本次退货有可能导致您购买时的订单不满足免运费条件，订单运费将影响本次退款金额；</li>
                                <li>本次退货申请预计退款金额约为：￥<span id="total">0.00</span>元。</li>
                                <li>扣除运费：￥<span id="fareAdditional">0.00</span>元。</li>
                                <li>实际退款金额约为：￥<span id="returnSum">0.00</span>元。</li>
                                <%--
                              	  ，返还面值为￥<span id="preferentialAmount">0.00</span>元优惠券一张
                              	--%>
                                <li>上述退款金额仅供参考，最终实际退款金额将由审核人员根据退换货实际情况确定。</li>
                            </ul>
                        </div>
                        </li>
                        <li><label>申请凭据：</label><span><input name="alter.evidence" type="checkbox" value="1"></span><span>有发票</span></li>
                        <li><label>问题描述：</label>
                        <div class="sh-name item">
	                        <textarea id="alterComment" name="alter.alterComment" cols="" rows="" class="Complaints-text fn-left"></textarea>
	                        <p style="clear:left;line-height:22px;">请您如实填写申请原因及商品情况，限500字以内描述。</p>
                        </div></li>
                        <li>
                        </li>
                        <li class="fn-t20"><label><em>*</em>商品退回方式：</label><span><input name="alter.backType" type="radio" value="1" checked="checked"></span><span>快递至康美中药城</span><span>请先自付邮费，如果是由于质量原因的退换货，邮费将会在退换货完成后返还到您的账户。</span></li>
                        <li>
                        <label id="exchangeAdressLabel"><em>*</em>换货送达地址：</label>
                            <div id="address" class="new-Address">
                            	<ul>
	                                <li><label><em>*</em>收&nbsp;货&nbsp;&nbsp;人：</label><input id="name" name="alter.name" type="text" class="u-text fn-left" value='<s:property value="order.consigneeName"/>'></li>
                                	<li><label><em>*</em>联系电话：</label><input id="phone" name="alter.phone" type="text" class="u-text fn-left" value='<s:property value="order.consigneeMobile"/>'></li>
	                                <li><label><em>*</em>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编：</label><input id="zipcode" name="alter.zipcode" type="text" maxlength="6" class="u-text fn-left" value='<s:property value="order.zipcode"/>'></li>
	                                <li><label><em>*</em>收货地址：</label><input id="address" name="alter.address" type="text" class="u-text fn-left" style="width:450px" value='<s:property value="order.consigneeAddr"/>'></li>
                            	</ul>
                            </div>
                        </div>
                        </li>
                    </ul>
                </div>
                <div class="button">
                	<div>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" id="submit0" class="saveBtn" value=""/>
						&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="backBtn" id="cancel" />
						<input type="hidden" name="alter.proposer" value='<s:property value="order.customerAccount"/>'/>
						<input type="hidden" name="alter.orderCode" value='<s:property value="item.orderCode"/>'/>
						<input type="hidden" name="alter.orderItemId" value='<s:property value="item.orderItemId"/>'/>
						<input type="hidden" id="maxnum" value='<s:property value="maxnum"/>'/>
                	</div>
                </div>
            </div>
          </div>
       </div>
</form>
</body>
</html>