<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加优惠券</title>
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
	//表单校验		
	  $(document).ready(function(){
		  if($("#viewTypes").val() == 'view' )
			 {
			     $("#couponForm").validate({
		               rules: {
							"coupon.couponName":{required:true,maxlength:15,unusualChar:true},
							"coupon.starttime":{required:true},
							"coupon.endtime":{required:true},
							"coupon.couponDescribe":{maxlength:260},
							"coupon.couponValidDay":{required:true,number:true}
			        	},
			           success: function (label){
			            label.removeClass("checked").addClass("checked");
			            }
		          });
			 }
		 
	          $("#couponForm").validate({
	               rules: {
						"coupon.couponName":{required:true,maxlength:15,unusualChar:true,checkCouponNameRepeat:true},
						"coupon.starttime":{required:true},
						"coupon.endtime":{required:true},
						"coupon.couponDescribe":{maxlength:260},
						"coupon.couponValidDay":{required:true,number:true},
						"are":{checkP:true}
		        	},
		           success: function (label){
		            label.removeClass("checked").addClass("checked");
		            }
	          });
	           });	
	  
		
	jQuery.validator.addMethod("checkCouponNameRepeat", function(value, element) {
		 	var rows = 0;
		 		var couponName = $("#couponName").val();
				$.ajax({
					async:false,
					url:"checkCouponNameRepeat.action?haveChoosedPro="+couponName,
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
	
	jQuery.validator.addMethod("checkP", function(value, element) {
		if($("#couponGivetypeId").val()==1){
		var a = document.getElementsByName("coupon.customLeveid").length;
		var b = document.getElementsByName("coupon.customId").length;	 
			if(a+b != 0){
				return true;
			}else{
	 			return false;
	 		}
		}
	}, "请选择发放范围");	
	 
</script>
</head>
<body>
 
	 <s:set name="parent_name" value="'优惠劵管理'" scope="request" />
	 <s:if test="viewType=='add'" >
	<s:set name="name" value="'优惠券新增'" scope="request" />
	</s:if>
	 <s:if  test="viewType=='show' " >
	 <s:set name="name" value="'优惠券查看'" scope="request" />
	 </s:if>
	  <s:if  test="viewType=='view'" >
	  	 <s:set name="name" value="'优惠券编辑'" scope="request" />
	  </s:if>
	<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
   
	<s:form action="/app/saveCoupon.action" method="POST" namespace='/app' id="couponForm">
	<s:token></s:token>
		<!--   隐藏数据域!-->
		<input type="hidden" name="categoryIds" id="categoryId" />
		<input type="hidden" name="viewType" id="viewTypes" 	value="<s:property value='viewType' />" />
		<input type="hidden" name="coupon.couponId" id="couponsId" 	value="<s:property value='coupon.couponId' />">
     	<input type="hidden"    id="giveType" value="<s:property value='coupon.couponGivetypeId' />"    />
    
	 		 <s:iterator id="havingCate" value="haveCate">
				<input type="hidden" name="havedCate"
					value="<s:property value='relatedId' />" />
			 </s:iterator>
		<input type="hidden"   id="validDay" value="<s:property value="coupon.couponValidDay"   />"    />
	 

		<!-- 数据编辑区域 -->
		<table width="97%" class="edit_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C7D3E2"
			style="border-collapse: collapse; font-size: 12px;">
			<tr>
				<th height="25" colspan="4" align="left" class="edit_title">优惠券规则添加</th>
			</tr>
			<tr>
				<th width="11%" height="27" align="right"><font color="red">*</font>优惠券类别：</th>
				<td width="39%"><s:if test="viewType=='add'">
						<label for="select"></label>
						<s:select name="coupon.couponGivetypeId" id="couponGivetypeId"
                        	list="#request.CouponGrantTypeMap"  >
						</s:select>
					</s:if>
                     <s:if test="viewType=='show' || viewType=='view'">
						<span id="giveType"><s:property
								value="#request.CouponGrantTypeMap[coupon.couponGivetypeId]" />
						</span>
					</s:if></td>
				<th width="11%" align="left"><span class="eidt_rowTitle"
					id="couponText"><font color="red">*</font>是否可赠送： </span></th>
				<td width="45%"><s:if test="viewType=='add' ">
						<input name="coupon.isGrant" type="radio" id="radio" value="2" checked> <label for="radio">是 
                        <input type="radio" name="coupon.isGrant" id="radio2" value="1"><label for="radio"> 否
						</label>
					</s:if> 
                    <s:if test="viewType=='view'" >
                    
                     <s:if test="coupon.isGrant==1">
                     <input name="coupon.isGrant" type="radio" id="radio" value="2" > <label for="radio">是 
                        <input type="radio" name="coupon.isGrant" id="radio2" value="1" checked><label for="radio"> 否
                     </s:if>
                     
                     <s:if test="coupon.isGrant==2">
                     <input name="coupon.isGrant" type="radio" id="radio" value="2" checked > <label for="radio">是 
                        <input type="radio" name="coupon.isGrant" id="radio2" value="1" ><label for="radio"> 否
                     </s:if>
                     
                    </s:if>
                    <s:if test="viewType=='show'">
					<s:if test="coupon.isGrant==1">
           					 否
          			</s:if>
					<s:if test="coupon.isGrant==2">
            			是
          			 </s:if>
					</s:if>
				</td>
			</tr>

			<tr>
				<th align="right"><font color="red">*</font>优惠券名称： <label
					for="textfield"></label></th>
				<td>
                <s:if test="viewType=='add' || viewType=='view'">
				<input type="text" name="coupon.couponName" id="couponName" value="<s:property value='coupon.couponName' />" id="textfield">
				</s:if> 
                  <s:if test="viewType=='show'">
					 <s:property value='coupon.couponName' />
					</s:if></td>
                  
		<th><span class="eidt_rowTitle"><font color="red">*</font>最低消费金额
						</span></th>
				<td>
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
				<th align="right"><font color="red">*</font>有效时间：</th>  
				<td>
               <s:if test="viewType=='add' ||viewType=='view'">
               
               <div id="timeInput">
			 <input id="d4311" class="Wdate"
				type="text" name="coupon.starttime" value="<s:date name='coupon.starttime' format='yyyy-MM-dd HH:mm:ss' />"
                  required="true"	
		onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'%y-%M-%d\'}',minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/ >
				至  
			<input id="d4312" class="Wdate" type="text"
							name="coupon.endtime"  value="<s:date name='coupon.endtime' format='yyyy-MM-dd HH:mm:ss' />"
					 required="true"		 onFocus="WdatePicker({minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
				</div>	
				<div id="dayInput" >
				</div> 
				
					</s:if>
                    
                    <s:if test="viewType=='show'">
                    	 <s:if test="coupon.couponGivetypeId==4">
               	 	 <s:property value="coupon.couponValidDay"   />天
               	    </s:if>	
               	 		<s:else>
                     <s:date name='coupon.starttime' format='yyyy-MM-dd HH:mm:ss' /> 至
                     <s:date name='coupon.endtime' format='yyyy-MM-dd HH:mm:ss' />
                      </s:else>
                    </s:if>

                   		
                    </td>
				<th align="center"><font color="red">*</font>优惠券抵扣金额：</th>
				<td> <s:if test="viewType=='add' ||viewType=='view' ">
						<input type="input" name="coupon.couponMoney" value="<s:property  value='coupon.couponMoney' />" 
                        id="textfield8">
					</s:if>
                     <s:if test="viewType=='show'">
						 <s:property     value='coupon.couponMoney' />
					</s:if></td>
			</tr>

			<tr>
				<th align="left" class="eidt_rowTitle">优惠券描述：</th>
				<td> <s:if test="viewType=='add' ||viewType=='view'">
						<textarea  name="coupon.couponDescribe" id="textarea" cols="45"  
							rows="5"><s:property value='coupon.couponDescribe' /></textarea>
                           
					</s:if>
                    
                    
                     <s:if test="viewType=='show'">
						 <s:property value='coupon.couponDescribe' />
					 

					</s:if></td>
				<td colspan="2">&nbsp;</td>
			</tr>

			<tr>
				<th colspan="4" align="left" class="eidt_rowTitle">&nbsp;</th>
			</tr>
  
			<tr id="fafang_title">
				<th colspan="4" align="left" class="edit_title"><span id="given_type">发放范围</span><input type="hidden" name="are" id="are1" /></th>
			</tr>	 
			<tr id="leveRow">
				<th height="100" colspan="2" align="right" class="eidt_rowTitle"  width="39%">
					<div id="leve_div" style="float: left;">

						<table width="426" height="39" border="1" align="left"
							cellspacing="0" id="custom_table1">
                            <tbody>
							<tr>
								<td width="100"><input type="checkbox" name="checkbox"
									id="select_leveall"> <label for="checkbox"></label></td>
								<th width="187">操作</th>
							</tr>
                            </tbody>
                            <s:if test="viewType=='add'">
                            <tbody id="customLeveContent">
                            
                            </tbody>
                            </s:if>
						</table>
                        
						<table width="426" height="39" border="1" align="left"
							cellspacing="0" id="leve_table2">
							
						</table>

					</div>
				</th>
				<th height="100" colspan="4" align="right" class="eidt_rowTitle"  width="61%">
                <div id="leve_div" style="float: left;">
						<table width="426" height="39" border="1" align="left"
							cellspacing="0" id="custom_table">
                            <tbody>
							<tr>
								<td width="100">
                                <input type="checkbox" name="checkbox"
									id="select_leveall"> <label for="checkbox"></label></td>
								<th width="169">会员账号</th>
								<th width="156">会员姓名</th>
								<th width="187">操作</th>
							</tr>
                            </tbody>
                            <s:if test="viewType=='add'">
                            <tbody id="customContent">
                            </tbody>
                            </s:if>
                            <s:if test="viewType=='view'">
								<s:iterator id="customList" value="userList" status='st'>
									<tr id="trLeve<s:property value='#st.getIndex()'/>">
										<td width="98"><input type="checkbox" name="checkbox"
											id="select_all"> <label for="checkbox"></label></td>
										<th width="169"><s:property value='loginId' /></th>
										<th width="219"><s:property value='loginAccount' /></th>
										<th width="100"><input  class='delBtn'
											onclick="delcustom('<s:property value='#st.getIndex()'/>')"></input></th>
									</tr>
								</s:iterator>
							</s:if>
                            
                           	<s:if test="viewType=='show'">
                            <s:iterator id="customList"  value="userList" status='st'>
							<tr id="trLeve<s:property value='#st.getIndex()'/>">
							<td width="98">
                            <input type="checkbox" name="checkbox"
										id="select_all"> <label for="checkbox"></label></td>
										<th width="169"><s:property value='loginAccount' /></th>
										<th width="219"><s:property value='name' /></th>
										<th width="100"></th>
										 
									</tr>
								</s:iterator>
                            </s:if>
                            
						</table>
						<table width="426" height="39" border="1" align="left"
							cellspacing="0" id="costomTable">
							
						</table>

					</div>
                </th>
				 
			</tr>
			<tr>
				<th colspan="4" align="left" class="edit_title">使用范围</th>
			</tr>
			<tr>
				<th height="32" align="right" class="eidt_rowTitle">产品类别：</th>
				<td><label> </label></td>
				<td align="center"><span class="eidt_rowTitle">产品列表：
					     
                  <s:if test="viewType=='add' ||viewType=='view'"></s:if>
                   


				</span></td>
				<td>
				<s:if test="viewType=='add' ||viewType=='view'">
				<input	type="button" name="button" id="addProd" value="选择" class="button-2s"
						onClick="addProds()" />
				</s:if>		
						</td>
			</tr>
			<tr>
				<th colspan="2" align="right" class="eidt_rowTitle">
					<div class="ctree" id="dtree_show" style="margin-left: 86px;"
						disabled="disabled">
						<p>
							<a href="#" onClick="expandAll(true)"> </a><a href="#"
								onClick="expandAll(false)"></a>
						</p>
						<ul id="treeDemo" class="ztree"></ul>
					</div>
				</th>
				<td colspan="2">&nbsp;
					<div id="auto_div">
						<table width="500" height="39" border="1" cellspacing="0"
							id="table2">
                             <tbody>
							<tr>
								<td width="96"><input type="checkbox" name="checkbox"
									id="select_all"> <label for="checkbox"></label></td>
								<th width="163">产品编号</th>
								<th width="94">产品名称</th>
                                <th width="129">操作</th>
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
											name="couponList[<s:property value='#st.getIndex()' />].relatedId"
											id="select_all"> <label for="checkbox"></label></td>
										<th width="169"><s:property value='relatedId' /></th>
										<th width="219"><s:property value='product_name' /></th>
										<th width="100"><input   class='delBtn'
											onclick="del('<s:property value='#st.getIndex()'/>')"></input></th>
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
										<th width="169"><s:property value='relatedId' /></th>
										<th width="219"><s:property value='product_name' /></th>
										<th width="100"></th>
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
			<tr>
				<th height="32" align="right" class="eidt_rowTitle"></th>
				<td><label> </label></td>
				<td align="center"><span class="eidt_rowTitle">供应商列表：
					     
                  <s:if test="viewType=='add' ||viewType=='view'"></s:if>
                   


				</span></td>
				<td>
				<s:if test="viewType=='add' ||viewType=='view'">
				<input	type="button" name="button" id="addProd" value="选择" class="button-2s"
						onClick="addSupplys()" />
				</s:if>		
						</td>
			</tr>
			<tr>
				<th colspan="2" align="right" class="eidt_rowTitle">
					
				</th>
				<td colspan="2">&nbsp;
					<div id="auto_div">
						<table width="500" height="39" border="1" cellspacing="0"
							id="table3">
                             <tbody>
							<tr>
								<td width="96"><input type="checkbox" name="checkbox"
									id="select_all"> <label for="checkbox"></label></td>
								<th width="163">供应商编号</th>
								<th width="94">供应商名称</th>
                                <th width="129">操作</th>
							</tr>
                            </tbody>
                          
                            <tbody id="supplyContent">
                            </tbody>
                          
                            <s:if test="viewType=='view'">
                                <tbody id="editBody1">
								<s:iterator id="custiterator" value="supplierList" status='st'>
                            
									<tr id="<s:property value='#st.getIndex()' />">
										<td width="98"><input type="checkbox"
											value="<s:property value='supplierId' />"
											name="haveChoosedSuplly"
											id="select_all"> <label for="checkbox"></label></td>
										<th width="169"><s:property value='supplierId' /></th>
										<th width="219"><s:property value='corporateName' /></th>
										<th width="100"><input   class='delBtn'
											onclick="del('<s:property value='#st.getIndex()'/>')"></input></th>
									</tr>
								</s:iterator> </tbody>
							</s:if>
                            	<s:if test="viewType=='show'">
                                <s:iterator id="custiterator" value="supplierList" status='st'>
                                <tbody >
									<tr id="<s:property value='#st.getIndex()' />">
										<td width="98"><input type="checkbox"
											value="<s:property value='supplierId' />"
											name="haveChoosedSuplly"
											id="select_all"> <label for="checkbox"></label></td>
										<th width="169"><s:property value='supplierId' /></th>
										<th width="219"><s:property value='corporateName' /></th>
										<th width="100"></th>
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
                    <INPUT class="saveBtn" TYPE="submit"   value=""  onClick="selectInput()">
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
<script type="text/javascript">
//初始化
	//初始化
	$().ready(function(e) {
		//手工发放
		if ($("#giveType").val()==1) {
			 $("#leveRow").show();
			$("#custom_leve").show();
			$("#custom_id").show();
			$("#order_money").hide();
			$("#payLeastMoney").show()
			$("#payLeastMoney_value").show();
			$("#textfield10").detach();
			$("#fafang_title").show();
			$("#timeInput").show();
			$("#Coupondays").remove();
			$("#days").remove();
		}
		//注册发放
		if ($("#giveType").val() == 2) {
			$("#custom_leve").hide();
			$("#leveRow").hide();
			$("#order_money").hide();
			$("#custom_id").hide();
			$("#fafang_title").hide();
			$("#payLeastMoney").remove();
			$("#payLeastMoney_value").remove();
			$("#timeInput").show();
			$("#days").remove();
		}
		//订单
		if ($("#giveType").val() == 3) {
			$("#custom_leve").hide();
			$("#leveRow").hide();
			$("#order_money").hide();
			$("#custom_id").hide();
		 	$("#fafang_title").hide();
			$("#payLeastMoney").remove();
			$("#payLeastMoney_value").remove();
			$("#timeInput").show();
			$("#days").remove();
		}
		//积分兑换
		if ($("#giveType").val()== 4) {
			$("#custom_leve").hide();
			$("#leveRow").hide();
			$("#order_money").hide();
			$("#custom_id").hide();
			$("#fafang_title").hide();
			$("#payLeastMoney").remove();
			$("#payLeastMoney_value").remove();
			$("#timeInput").hide();
		//	alert($("#Conpondays").length);
		 	 if ($("#Conpondays").length==0){
			$("#dayInput").html("");
			var days =$("#validDay").val();
			//alert(days)
			 $("#dayInput").append(" <div id='days'> <input id='Coupondays'  type='input' name='coupon.couponValidDay' value="+days+"    /> 天</div> ");	
		 	 }
			$("#dayInput").removeAttr("style");
	}
		 	});
</script>

 
</HTML>


