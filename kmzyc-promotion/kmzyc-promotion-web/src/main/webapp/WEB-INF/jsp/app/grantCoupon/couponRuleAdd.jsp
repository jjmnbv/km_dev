<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑优惠券规则</title>
<script src="/etc/js/dialog.js"></script>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<link href="/etc/js/dtree/dtree.css" type="text/css" rel="stylesheet">
<link href="/etc/js/ztree/ztree.css" type="text/css" rel="stylesheet">

<link href="/etc/css/validate.css"  rel="stylesheet" type="text/css" />
<script src="/etc/js/jquery-latest.pack.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
 <Script src="/etc/js/coupon.js"></Script>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery-1.4.4.min.js"></script>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.core-3.5.min.js"></script>
<script language="javascript" type="text/javascript" src="/etc/js/ztree/jquery.ztree.exhide-3.5.min.js"></script>
<script language="javascript" type="text/javascript"  src="/etc/js/ztree/jquery.ztree.excheck-3.5.min.js"></script>
 
<script type="text/javascript" src="/etc/js/validate/easy_validator.pack.js"></script>

<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/validate/messages_cn.js"></script>
<script type="text/javascript">
	//类目树
	var zNodes = <s:property value="nodes" escape="false"/>;
	var bb;
	var setting = {
		check : {
			enable : true
		},
		data : {
			key : {
				name : "categoryName"
			},
			simpleData : {
				enable : true,
				idKey : "categoryId",
				pIdKey : "parentId",
				rootPId : 0
			}
		}
	};
	function expandAll(flag) {
		$.fn.zTree.getZTreeObj('treeDemo').expandAll(flag);
	}
	//处理初始化页面最后的动作
	$(document).ready(
			function() {
				$.fn.zTree.init($("#treeDemo"), setting, zNodes);
				//展开所有节点
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
				if ($("#viewTypes").val() == 'view' ||$("#viewTypes").val() == 'show') {
					var cateId = document.getElementsByName("havedCate");
					for ( var a = 0; a < cateId.length; a++) {
						var x = treeObj.getNodeByParam("categoryId",
								cateId[a].value, null);
						treeObj.checkNode(x, true, true);
					}}});
	//节点全选
	function checkAll(){
		if(document.getElementById("checkAllBox").checked){
			$.fn.zTree.getZTreeObj("treeDemo").checkAllNodes(true);
		} else{
			$.fn.zTree.getZTreeObj("treeDemo").checkAllNodes(false);
		}
	}
	
	//时间限制选择事件
	 function clickChange(value){
		if(value==1){
			$("input[name='coupon.couponValidDay']").rules("remove");
			$("input[name='coupon.couponValidDay']").next('label').remove();
			$("input[name='coupon.starttime']").rules("add",{required:true});
			$("input[name='coupon.endtime']").rules("add",{required:true});
		}
		if(value == 2){
			$("input[name='coupon.couponValidDay']").rules("add",{required:true,positiveinteger:true});
			$("input[name='coupon.starttime']").rules("remove");
			$("input[name='coupon.starttime']").next('label').remove();
			$("input[name='coupon.endtime']").rules("remove");
			$("input[name='coupon.endtime']").next('label').remove();
		}
	} 
	
	//表单校验		
	  $(document).ready(function(){
	          $("#couponForm").validate({
	               rules: {
						"coupon.couponName":{required:true,maxlength:15,unusualChar:true,checkCouponNameRepeat:true},
						"coupon.supplierType":{required:true},
						"coupon.couponMoney":{required:true,checkMoney:true},
						"coupon.payLeastMoney":{required:true,checkMoney:true,checkPayLeastMoney:true},
						"coupon.couponDescribe":{required:true,maxlength:260}
		        	},
		           success: function (label){
		            label.removeClass("checked").addClass("checked");
		            },
		           errorPlacement: function(error, element) {
		            	if(element.is("input[name=coupon.supplierType]")){
		            		error.appendTo(element.parent());
		            	} else {
		            		error.insertAfter(element);   
		            	}
		            } 
		           
	          });
	          
	          bb =  $('.j_personInfo');
	          //修改规则移除重复规则名验证  修改操作初始化验证
			   if($("#viewTypes").val() == 'view' ){
					  if($('#timeType').val()== 1){
						  	$("input[name='coupon.starttime']").rules("add",{required:true});
					  		$("input[name='coupon.endtime']").rules("add",{required:true});
					  	  }
				  	  if($('#timeType').val()== 2){
				  			$("input[name='coupon.couponValidDay']").rules("add",{required:true,positiveinteger:true});
				  	  }
		   	   	  } ;
		   	   	//添加初始化验证操作
		   	   if($("#viewTypes").val() == 'add' ){
		   		$("input[name='coupon.starttime']").rules("add",{required:true});
		  		$("input[name='coupon.endtime']").rules("add",{required:true});
		   	   }  
		   	   	
		   	   	
		   	//初始化已选择的入驻供应商
		    var corporateName = $("#corporateName").val();
		    var supplierId = $("#supplierId").val();
		    var supplierType = $("#supplierType").val();
		    var j_personInfo = $('.j_personInfo');
		    var $div = ' class="j_div" style="position:relative;margin:3px 5px 2px 0;white-space:nowrap;height:15px;line-height: 15px;cursor:pointer;border-radius:17px;border-style:solid;border-width:1px;font-size:14px;padding:2px 19px;border-color:#edb8b8;background-color:#ffeaea;color:#c30!important;display:inline-block;vertical-align:middle;">';
		    var $em = '<em style="margin-left:-8px;vertical-align:top;display:inline-block;font-style:normal;text-decoration:none;white-space:nowrap;line-height:15px;cursor:pointer;font-size:14px;" unselectable="on">';
		    var $a = '<a style="position: absolute;right: -2px;top: -1px;display: inline;text-decoration: none;font-family: verdana;border-radius: 0 17px 17px 0;font-weight: bold;padding: 2px 5px 2px 3px;border-width: 1px;border-style: solid;border-color:#edb8b8!important;color:#c30!important;" href="javascript:void(0)" hidefocus="hidefocus">x</a>';
		    var str = '';
		    if(corporateName!=""){
		    	var cname=corporateName.split(',');
		    	var sId=supplierId.split(',');
		    	 for(var i=0; i<cname.length; i++){
		 	    	str += '<div data-name='+cname[i]+' data-id='+sId[i] + $div + $em + cname[i] + '</em>' + $a + '</div>';
		 	         
		 	    }
			    var reDiv = j_personInfo.find('div.j_div');
			    if(reDiv.length){
			    	j_personInfo.find('div.j_div').remove();
			     }
		    }
		    j_personInfo.prepend(str);
		    //如果不是入驻就隐藏掉选择按钮
			 if(supplierType!=3){
				$('#supplierClick').hide();
				$('#xuanze').hide();
		   	}  
	        
	 });
	
	//验证商家类型的指定入驻商家是否已选择
	jQuery.validator.addMethod("checkSupplierType",function(value,element){
		//获取商家类别
		var supplierValue = $('input[name="coupon.supplierType"]:checked').val();
		//指定入驻
		if(supplierValue==3){
			//判断是否已选择
			var div=$(".j_div");
			if(div.length==0){
				return false;
			}
		}
		return true;
	},"请选择");
	
	//验证正整数天数
 	 jQuery.validator.addMethod("positiveinteger", function(value, element) {
	   var aint=parseInt(value);	
	    return aint>0&& (aint+"")==value;   
	  }, "请输入正确天数");   
	 
	//金钱验证>0
	jQuery.validator.addMethod("checkMoney", function(value, element) {
		value=parseFloat(value);      
        return this.optional(element) || value>0;  
	},"请输入正确的金额");
	//验证最低消费金额大于等于抵扣金额
	jQuery.validator.addMethod("checkPayLeastMoney",function(value,element){
		//获取抵扣金额
		var couponMoney=$("input[name='coupon.couponMoney']").val();
		value=parseFloat(value); 
		if(couponMoney==""){
			return false;
		} else if(value<couponMoney){
			return false;
		} else {
			return true;
		}
	},"金额不能小于抵扣金额");
	//规则名称重复验证	
	jQuery.validator.addMethod("checkCouponNameRepeat", function(value, element) {
		 	var rows = 0;
		 		var couponNameOld = $("#couponName").val();
		 		var couponName=couponNameOld.replace(/(^\s+)|(\s+$)/g,"");//去掉空格
		 		var couponId = $("#couponsId").val();
		 		var viewType=$("#viewTypes").val();
				$.ajax({
					async:false,
					url:"/app/couponRule_checkCouponName.action?newCouponName="+couponName+"&&couponId="+couponId+"&&viewType="+viewType,
					type:"POST",
					dataType:"json",
					success:function(json){
							rows = json;
					}
				});
				if(rows==1){
					return false;
				}else{
		 			return true;
		 		}		
	}, "优惠券名称重复，请重新添写！");
	
	//入驻商家选择
	function checkSupplier(){
		 dialog("选择账号","iframe:/app/couponRule_checkSupplier.action" ,"900px","450px","iframe");
		 $("#floatBox").css('z-index',1000);
	}
	//关闭入驻商家页面
	function closeOpenUserInfo(supplierIds,corporateNames){
		closeThis();
		$("#corporateName").val(corporateNames);
		$("#supplierId").val(supplierIds);
	    var j_personInfo = bb;
	    var $div = ' class="j_div" style="position:relative;margin:3px 5px 2px 0;white-space:nowrap;height:15px;line-height: 15px;cursor:pointer;border-radius:17px;border-style:solid;border-width:1px;font-size:14px;padding:2px 19px;border-color:#edb8b8;background-color:#ffeaea;color:#c30!important;display:inline-block;vertical-align:middle;">';
	    var $em = '<em style="margin-left:-8px;vertical-align:top;display:inline-block;font-style:normal;text-decoration:none;white-space:nowrap;line-height:15px;cursor:pointer;font-size:14px;" unselectable="on">';
	    var $a = '<a style="position: absolute;right: -2px;top: -1px;display: inline;text-decoration: none;font-family: verdana;border-radius: 0 17px 17px 0;font-weight: bold;padding: 2px 5px 2px 3px;border-width: 1px;border-style: solid;border-color:#edb8b8!important;color:#c30!important;" href="javascript:void(0)" hidefocus="hidefocus">x</a>';
	    var aNamelist = corporateNames.split(',');
	    var aIdlist = supplierIds.split(',');
	    var str = '';
	    for ( var i= 0; i < aNamelist.length; i++) {
	    	str += '<div data-name='+aNamelist[i]+' data-id='+aIdlist[i] + $div + $em + aNamelist[i] + '</em>' + $a + '</div>';
		}
	   	var reDiv = j_personInfo.find('div.j_div');
	    if(reDiv.length){
	    	j_personInfo.find('div.j_div').remove();
	     }
	    j_personInfo.prepend(str);
	    
	}
	//选择的入驻商家取消
	 $('.j_div').live('click','div.j_div',function(){
			var t = $(this);
			var name = t.attr('data-name');
			var id = t.attr('data-id');	
			//获取id集合
			var oldId = $('#supplierId').val().split(',');	
			//获取name集合
			var oldName = $('#corporateName').val().split(',');
			for ( var i = 0; i < oldId.length; i++) {
				if(id==oldId[i]){
					oldId.splice(i,1);
				}
			}
			for ( var j = 0; j < oldName.length; j++) {
				if(name==oldName[j]){
					oldName.splice(j,1);
				}
			}
		     t.remove();
		     $('#supplierId').val(oldId.join(","));
		     $('#corporateName').val(oldName.join(","));
	});
	
	//商家类别点击事件
	 function supplierTypeClick() {
		//获取前一次点击的商家类别
		var beforSupplierType= $("#beforSupplierType").val();
		//获取当前选中商家类别
		var supplierValue = $('input[name="coupon.supplierType"]:checked').val();
		if(beforSupplierType != supplierValue){
			//如果选择不同类别     移除之前选中的商家类别对应的商品
			$("#productContent").empty();
			$("#editBody").empty();
		}
		//移除商品后当前类别 设置为前类别  下次可用
		$("#beforSupplierType").val(supplierValue);
		
		if(supplierValue!=3){
			$('#supplierClick').hide();
			$('#xuanze').hide();
		}
		if(supplierValue == 3){
			$('#supplierClick').show();
			$('#xuanze').show();
			//验证是否添加指定入驻商
			$("input[name='coupon.supplierType']").rules("add",{checkSupplierType:true});
		}
	} 
	
</script>
</head>
<body>
 
	 <s:set name="parent_name" value="'优惠劵规则管理'" scope="request" />
	 <s:if test="viewType=='add'" >
	<s:set name="name" value="'优惠券规则新增'" scope="request" />
	</s:if>
	 <s:if  test="viewType=='show' " >
	 <s:set name="name" value="'优惠券规则查看'" scope="request" />
	 </s:if>
	  <s:if  test="viewType=='view'" >
	  	 <s:set name="name" value="'优惠券规则编辑'" scope="request" />
	  </s:if>
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
   
	<s:form action="/app/couponRule_saveCoupon.action" method="POST" namespace='/app' id="couponForm">
	<s:token></s:token>
		<!--   隐藏数据域!-->
		<input type="hidden" name="categoryIds" id="categoryId" />
		<input type="hidden" name="viewType" id="viewTypes" 	value="<s:property value='viewType' />" />
		<input type="hidden" name="coupon.couponId" id="couponsId" 	value="<s:property value='coupon.couponId' />">
	 		 <s:iterator id="havingCate" value="haveCate">
				<input type="hidden" name="havedCate"
					value="<s:property value='relatedId' />" />
			 </s:iterator>
	    <input type="hidden" name="timeType" id="timeType" 	value="<s:property value='coupon.timeType' />" />
	    <input id="supplierType" type="hidden" value='<s:property value="coupon.supplierType"/>'> 
	    <!-- 设前一次类别  判断可用 -->
	    <input id="beforSupplierType" type="hidden" value='<s:property value="coupon.supplierType"/>'> 
		<input id="supplierId" type="hidden" value='<s:property value="coupon.shopCode"/>' name="coupon.shopCode">
 		<input id="corporateName" type="hidden" value='<s:property value="supplierObj.corporateName"/>' name="corporateName">
		<!-- 数据编辑区域 -->
		<table width="97%" class="edit_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C7D3E2"
			style="border-collapse: collapse; font-size: 12px;">
			<tr>
				<s:if test="viewType=='add'" >
				<th height="25" colspan="4" align="left" class="edit_title">优惠券规则添加</th>
				</s:if>
		 		<s:if  test="viewType=='show' " >
		 		<th height="25" colspan="4" align="left" class="edit_title">优惠券规则查看</th>
		 		</s:if>
		  		<s:if  test="viewType=='view'" >
		  		<th height="25" colspan="4" align="left" class="edit_title">优惠券规则编辑</th>
		 		</s:if>
			</tr>
			<tr >
			 <td colspan="4" align="right" class="eidt_rowTitle"  >
				<table width="97%" align="center" >
					<tr>
						<td width="14%"><span class="eidt_rowTitle"><font color="red">*</font>优惠券名称：</span></td>
						<td >
			                <s:if test="viewType=='add' || viewType=='view'">
								<input type="text" name="coupon.couponName" id="couponName" value="<s:property value='coupon.couponName' />" id="textfield">
							</s:if> 
			                <s:if test="viewType=='show'">
								<s:property value='coupon.couponName' />
							</s:if>
						</td>
		            </tr>
		            <tr>
						<td ><span class="eidt_rowTitle"><font color="red">*</font>商家类别：</span></td>
						<td >
			                <s:if test="viewType=='add' || viewType=='view'">
			                	<s:radio onclick="supplierTypeClick()"  name="coupon.supplierType" list="%{#{'1':'所有商家','2':'康美自营代销','3':'指定入驻商家'}}" theme="simple" ></s:radio>
								<span class='j_personInfo' id="supplierClick">
								
								</span>
								<input type="button" id="xuanze" value="选择" onclick="checkSupplier()">
							</s:if> 
			                <s:if test="viewType=='show'">
								<s:property  value="#request.CouponSupplierType[coupon.supplierType]" /> 
								<s:if test="coupon.supplierType==3">
								：<s:property value="supplierObj.corporateName"/>
								</s:if>
							</s:if>
						</td>
		            </tr>
		            <tr>    
		            	<td ><span class="eidt_rowTitle"><font color="red">*</font>可抵扣金额：</span></td>
						<td >
			                <s:if test="viewType=='add' ||viewType=='view'">
									<input name="coupon.couponMoney" value="<s:property value='coupon.couponMoney' />" 
									id="couponMoney" >
								</s:if>
			                     <s:if test="viewType=='show'">
									<s:property value="coupon.couponMoney" />
								</s:if>
		                </td>      
					</tr>
		            <tr>    
		            	<td ><span class="eidt_rowTitle"><font color="red">*</font>最低消费金额：</span></td>
						<td >
			                <s:if test="viewType=='add' ||viewType=='view'">
									<input name="coupon.payLeastMoney" value="<s:property value='coupon.payLeastMoney' />" 
									id="leastMoney" >
								</s:if>
			                     <s:if test="viewType=='show'">
									<s:property value="coupon.payLeastMoney" />
								</s:if>
		                </td>      
					</tr>
					<tr>    
		            	<td ><span class="eidt_rowTitle"><font color="red">*</font>优惠券描述：</span></td>
						<td >
			                <s:if test="viewType=='add' ||viewType=='view'">
									<input  name="coupon.couponDescribe" value="<s:property value='coupon.couponDescribe' />" 
									id="couponDescribe" >
								</s:if>
			                     <s:if test="viewType=='show'">
									<s:property value="coupon.couponDescribe" />
								</s:if>
		                </td>      
					</tr>
					<tr>
						<td ><span class="eidt_rowTitle"><font color="red">*</font>时间限制：</span></td>  
						<td >
			              	  <s:if test="viewType=='add' ||viewType=='view'">
				               	<input type="radio" onclick="clickChange(1)" checked  name="coupon.timeType" value="1" <s:if test='coupon.timeType=="1"'>checked</s:if>/> 固定时间范围，有限时间：
								<input id="d4311" class="Wdate" type="text" id="coupon.starttime" name="coupon.starttime" value="<s:date name='coupon.starttime' format='yyyy-MM-dd HH:mm:ss' />"
					                  	onclick="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')}',minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/ >
									至  
								 <input id="d4312"  class="Wdate" type="text" id="coupon.endtime" name="coupon.endtime"  value="<s:date name='coupon.endtime' format='yyyy-MM-dd HH:mm:ss' />"
									   onclick="WdatePicker({minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /> 
								<br/>
								<input type="radio"  onclick="clickChange(2)"name="coupon.timeType" value="2" <s:if test='coupon.timeType=="2"'>checked</s:if>/>固定天数范围，从获得日(含)起计，
								<input id="coupon.couponValidDay" name="coupon.couponValidDay" value='<s:property value="coupon.couponValidDay"/>'/>天内有效;
							</s:if> 
			                <s:if test="viewType=='show'">
			                    <s:if test="coupon.timeType==2">
			               	 	 	<s:property value="coupon.couponValidDay"   />天
			               	    </s:if>	
			               	 	<s:else>
			                     <s:date name='coupon.starttime' format='yyyy-MM-dd HH:mm:ss' /> 至
			                     <s:date name='coupon.endtime' format='yyyy-MM-dd HH:mm:ss' />
			                    </s:else>
			                </s:if>
		                </td>
		            </tr>
				</table>	
				</td>
			</tr>



			<tr>
				<td colspan="4" align="left" class="eidt_rowTitle">&nbsp;</td>
			</tr>
  
			<tr id="fafang_title">
				<td colspan="4" align="left" class="edit_title"><span id="given_type">优惠券适用范围限制</span><input type="hidden" name="are" id="are1" /></td>
			</tr>	 
			<tr >
			    <td colspan="4" align="right" class="eidt_rowTitle"  >
					<table width="97%" align="center" >
					   <tr>
					   		<td><span class="eidt_rowTitle"><font color="red">*</font>范围限制方式：</span></td>
					   		<td>	<input type="radio" checked name="coupon.rangType" value="1" <s:if test='coupon.rangType=="1"'>checked</s:if>/>白名单方式：只有下方指定范围内商品适用此优惠券<br/>
					   				<input type="radio" name="coupon.rangType" value="2" <s:if test='coupon.rangType=="2"'>checked</s:if>/>黑名单方式：除下方指定范围内外,其他商品都适用此优惠券<br/>
					   		</td>
					   </tr>
					</table>
				</td>
			</tr>
			
			
			
			
			
			
			<tr>
				<td colspan="4" align="left" class="edit_title">使用范围</td>
			</tr>
			<tr>
				<td height="32" align="right" class="eidt_rowTitle" colspan="2">物理类目属于：<input type="checkbox" id="checkAllBox" onclick="checkAll()"/>全选</td>
				<td align="center" colspan="2"><span class="eidt_rowTitle">并且属于下方指定商品：
                  <s:if test="viewType=='add' ||viewType=='view'"></s:if></span>
				<s:if test="viewType=='add' ||viewType=='view'">
				<input	type="button" name="button" id="addProd" value="选择" class="button-2s"
						onClick="addProdsNew()" />
				</s:if>	
				</td>	
			</tr>
			
			<tr>
				<td colspan="2" align="right" class="eidt_rowTitle">
					<div class="ctree" id="dtree_show" style="margin-left: 86px;"
						disabled="disabled">
						<p>
							<a href="#" onClick="expandAll(true)"> </a><a href="#"
								onClick="expandAll(false)"></a>
						</p>
						<ul id="treeDemo" class="ztree"></ul>
					</div>
				</td>
				<td colspan="2">&nbsp;
					<div id="auto_div">
						<table width="500" height="39" border="1" cellspacing="0"
							id="table2">
                             <tbody>
							<tr>
								<td width="96"><input type="checkbox" name="checkbox"
									id="select_all"> <label for="checkbox"></label></td>
								<td width="163">产品编号</td>
								<td width="94">产品名称</td>
                                <td width="129">操作</td>
							</tr>
                            </tbody>
                          
                            <tbody id="productContent">
                            </tbody>
                          
                            <s:if test="viewType=='view'">
                                <tbody id="editBody">
								<s:iterator id="custiterator" value="couponList" status='st'>
                            
									<tr id="<s:property value='#st.getIndex()' />">
										<td width="98"><input type="checkbox"
											value="<s:property value='relatedId' />"
											name="couponProduct.relatedId"
											id="select_all"> <label for="checkbox"></label></td>
										<td width="169"><span name="haveChoosedPro"><s:property value='relatedId' /></span></td>
										<td width="219"><s:property value='product_name' /></td>
										<td width="100"><input   class='delBtn'
											onclick="del('<s:property value='#st.getIndex()'/>')"></input></td>
									</tr>
								</s:iterator> </tbody>
							</s:if>
                            	<s:if test="viewType=='show'">
                                <s:iterator id="custiterator" value="couponList" status='st'>
                                <tbody >
									<tr id="<s:property value='#st.getIndex()' />">
										<td width="98"><input type="checkbox"
											value="<s:property value='relatedId' />"
											name="couponList[<s:property value='#st.getIndex()' />].relatedId"
											id="select_all"> <label for="checkbox"></label></td>
										<td width="169"><s:property value='relatedId' /></td>
										<td width="219"><s:property value='product_name' /></td>
										<td width="100"></td>
									</tr>
                                    </tbody>
								</s:iterator>
                                </s:if>
						</table>

						<table width="500" height="39" border="1" cellspacing="0"
							id="table2_">
							
						</table>


					</div>
				</td>
			</tr>
		</table>

		
			<!-- 底部 按钮条 -->
			<table width="98%" align="center" class="edit_bottom" height="30"
				border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;">
				<tr>
					<td align="center">
                    <s:if test="viewType!='show'">
                    <!-- 点击事件进行校验-->
                    <INPUT class="saveBtn" TYPE="submit"   value="" onclick="selectInput()">
                    </s:if>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                    <input  type="button" class="backBtn" onClick="javascript:history.go(-1);" />
					<td width="20%" align="center"></td>
				</tr>
		  </table>
		<br>
		<br>
	</s:form>
</body>
<style type="text/css">
#auto_div {
	width: 515px;
	height: 230px;
	/*word-break:break-all;*/
	word-wrap: break-word;
	/*自动出现滚动条*/
	overflow: auto; /*超出div部份则自动隐藏*/
	*overflow-x: hidden;
	border: 1px solid #666;
}

#leve_div {
	width: 444px;
	height: 118px;
	word-wrap: break-word;
	overflow: auto;
	*overflow-x: hidden;
	border: 1px solid #666;
}
</style>


 
</HTML>


