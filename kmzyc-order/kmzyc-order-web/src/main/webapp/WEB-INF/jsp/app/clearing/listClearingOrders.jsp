<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>结算单管理</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
<script type="text/javascript" src="/etc/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
<script type="text/javascript" src="/etc/js/urchin.js"></script>

<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="/etc/css/autocompletestyles.css">

<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript">
	function turnDetail(obj){
		var sno = $(obj).attr("class");
		window.location.href="/app/toDetailOrder.action?sno=" + sno + "&toType=yun";
	};
	
	function amount(th){
	    var regStrs = [
	        ['^0(\\d+)$', '$1'], //禁止录入整数部分两位以上，但首位为0
	        ['[^\\d\\.\\-]+$', ''], //禁止录入任何非数字,点和负数符号
	        ['\\.(\\d?)\\.+', '.$1'], //禁止录入两个以上的点
	        ['^(\\d+\\.\\d{2}).+', '$1'] //禁止录入小数点后两位以上
	    ];
	    for(i=0; i<regStrs.length; i++){
	        var reg = new RegExp(regStrs[i][0]);
	        th.value = th.value.replace(reg, regStrs[i][1]);
	    }
	};
	
	function overFormat(th){
	    var v = th.value;
	   if(/^0\.\d$/.test(v)){
        v = v + '0';
        }else if(/^\-\d+$/.test(v)){
            v = v + '.00';
        }else if(/^\-\d+\.$/.test(v)){
            v = v + '00';
        }else if(/^\-\d+\.\d$/.test(v)){
            v = v + '0';
        }else if(/^\d+$/.test(v)){
            v = v + '.00';
        }else if(/^\d+\.$/.test(v)){
            v = v + '00';
        }else if(/^\d+\.\d$/.test(v)){
            v = v + '0';
        }else if(/^\.+$/.test(v)){
        	v = '';
        }
	  
	    th.value = v; 
	};
	
	$(function(){
		var countriesArray;
		   $.ajax({
				//async:'false',
				url:'selectSupplierListByName.action',
				success:function(data){
					  countriesArray = $.map(data, function (value, key) { 
						return { value: value, name: key }; 
						});
					  
					  $("#autocomplete").bind("change",function(){
						  $("#hid_supplier_id").val(null);
						})
					$('#autocomplete').autocomplete({
				    	lookup: countriesArray,
				      	minChars: 0 ,
				      //  minLength: 1,
				        onSelect: function (value) {
				        	 $("#hid_supplier_id").val(value.name);
				        	
				        
				        }
				    })
				    
				
				    
				},
				dataType:'json'
				
			}); 
	});
	
	var isSub=false;
	function exportFile(){ 
		if(isSub){
			return;
		}
		var settlementNo=$('#settlementNo').val();
		var startDate=$('#startDate').val();
		var endDate=$('#endDate').val();
		var settlementStatus=$('#settlementStatus').val();
		var sellerName=$('#autocomplete').val();
		var minMoney=$('#minMoney').val();
		var maxMoney=$('#maxMoney').val();
		
		
	
		if(settlementNo == ""&&startDate == ""&&endDate == ""&&settlementStatus == ""
			&&sellerName == ""&&minMoney == ""&&maxMoney == ""){
			alert("请输入至少一个查询条件");
			return;
		}
	
		if((startDate.length>1&&endDate.length<1)||(endDate.length<1&&endDate.length>1)){
			alert("开始账期时间和结束账期时间必须同时选择或不选！");
			return;
		}
		
		if((minMoney=="">1&&maxMoney!="")||(minMoney != ""&&maxMoney=="")){
			alert("起始应结金额必须同时选择或不选！");
			return;
		}
	
		
		isSub=true;
	 	 $.ajax({
            async: false,
            url: '/app/exportFinacialAuditInfo.action',
            cache:false,
            type:'POST',
            data: {"_settlementNo": settlementNo,"_startDate": startDate, "_endDate":endDate,"_settlementStatus": settlementStatus,"_sellerName": sellerName,"flag": 3,"_minMoney": minMoney,"_maxMoney": maxMoney},
            success: function (data) {
	            try{
            	var start=data.indexOf('\'path\':\'');
            	var end=data.indexOf('.xls');
            	var url=data.substring(start+8,end+4);
            	$('#downLoadLink').attr('href',url).find('span').click();
	            }catch(e){
	            	alert('生成报表出错！');
		        }
            	isSub=false;
            },
            error:function(){
				alert('生成报表出错！');
	        }
        });  
	};
</script>
</head>
<body>
    <a href="#" id="downLoadLink" style="display: none;" target="_blank"><span id="downLoadSp" ></span></a>
	<s:set name="parent_name" value="'商家结算管理'" scope="request" />
	<s:set name="name" value="'结算单管理'" scope="request" />
	<s:set name="son_name" value="'列表'" scope="request" />
	<s:include value="/WEB-INF/jsp/public/title.jsp" />
	
	<div style="margin:10px">
			
	</div>
	<form action="listClearingOrders.action?flag=0" method="POST">
        <!-- <input id="pagel" type="hidden" name="act" value="1"/> -->
		<!-- 查询条件区域 -->
		<table width="98%" class="table_search" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<th align="right">结算单号：</th>
				<td><input value="<s:property value="sellerSettlementCriteria.settlementNo" />" name="_settlementNo" type="text"></td>
				<th align="right">账期：</th>
				<td><input name="_startDate" id="startDate"
					value='<fmt:formatDate value="${sellerSettlementCriteria.startDate}" pattern="yyyy-MM-dd HH:mm:ss" />' type="text"
					class="Wdate"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\')}'})" /><strong>&nbsp;
					至&nbsp;</strong><input name="_endDate" id="endDate"
					value='<fmt:formatDate value="${sellerSettlementCriteria.endDate}" pattern="yyyy-MM-dd HH:mm:ss" />' type="text"
					class="Wdate"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startDate\')}'})" /></td>
				
				<th align="right">状态：</th>
				<td><select name="_settlementStatus" >
						<option value="" <c:if test="${sellerSettlementCriteria.settlementStatus ==''  }">selected="selected"</c:if>>所有</option>
						<option value="1" <c:if test="${sellerSettlementCriteria.settlementStatus ==1  }">selected="selected"</c:if>>未确认</option>
						<option value="2" <c:if test="${sellerSettlementCriteria.settlementStatus ==2  }">selected="selected"</c:if>>商家已确认</option>
						<option value="3" <c:if test="${sellerSettlementCriteria.settlementStatus ==3  }">selected="selected"</c:if>>运营已确认</option>
						<option value="4" <c:if test="${sellerSettlementCriteria.settlementStatus ==4  }">selected="selected"</c:if>>财务审核通过</option>
						<option value="5" <c:if test="${sellerSettlementCriteria.settlementStatus ==5  }">selected="selected"</c:if>>财务审核拒绝</option>
						<option value="6" <c:if test="${sellerSettlementCriteria.settlementStatus ==6  }">selected="selected"</c:if>>已结出</option>
				</select></td>
				</tr>
			    <tr>
			    <th align="right">商家名称：</th>
				<%-- <td><input value="<s:property value="sellerSettlementCriteria.sellerName" />" name="sellerSettlementCriteria.sellerName" type="text"></td> --%>
				<td>
					<div id="commerceNameDiv" >	
			           <input id="autocomplete" name="_sellerName" value='<s:property value="sellerSettlementCriteria.sellerName" />' type="text" >
				    </div>
				</td>		
				<th align= "right">应结金额：</th>
				<td>
					<input id="minMoney" name="_minMoney" type="text" value='<s:property value="sellerSettlementCriteria.minMoney" />'  onkeyup="amount(this)" onafterpaste="amount(this)" onBlur="if(this.value != ''){overFormat(this)}"/>
					<strong>&nbsp;至&nbsp;</strong>
					<input id="maxMoney" name="_maxMoney"  type="text" value='<s:property value="sellerSettlementCriteria.maxMoney" />' onKeyUp="amount(this)" onafterpaste="amount(this)" onBlur="if(this.value != ''){overFormat(this)}"/>
				</td>
				
				<input id="hid_supplier_id" type="hidden" name="_sellerId" value="${_sellerId }">
				<td align="left" ><input type="button" class="btn-custom" value="手动生成结算单${roleName }" onClick="javascript:location.href='createSettlementPage.action?flag=1'">
                <INPUT TYPE="submit" class="btn-custom button-blue-1"
					value="查询 ">
				<input type="button" value="导出 "  class="btn-custom" onclick="exportFile()"/>	
					
					</td>
				
			</tr>
		</table>
		<table class="list_table" width="98%" align="center" cellpadding="3"
			cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc"
			style="border-collapse: collapse; font-size: 12px">
			<tr>
				<th><s:checkbox name='allbox' onclick='checkAll(this)' /></th>
				<th>结算单号</th>
				<th>商家</th>
				<th>账期</th>
				<th>结算时间</th>
				<th>期内货款</th>
				<th>运费</th>
				<th>平台佣金</th>
				<th>推广服务费</th>
				<th>差异调整</th>
				<th>应结金额</th>
				<th>结算状态</th>
				<th>操作</th>
			</tr>
			<c:if test="${not empty dataList}">
			
				<c:forEach var="item" items="${dataList }"> 
				
					<tr onMouseOver="this.style.backgroundColor='#7BB5E2'"
				onMouseOut="this.style.backgroundColor='#FFFFFF'">
					<td align="center"><input type="checkbox" class="checkbox" value='' /></td>
					<td>${item.settlementNo }</td>
					<td>${item.sellerName }</td>
						<td>${item.settlementPeriod }</td>
						<td><fmt:formatDate value="${item.settlementCreateTime }"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td><fmt:formatNumber type="NUMBER"
								value="${item.receiveSum-item.refundSum }" pattern="#0.00#"></fmt:formatNumber></td>
						<td><fmt:formatNumber type="NUMBER" value="${item.fareSum-item.returnFareSum }"
								pattern="#0.00#"></fmt:formatNumber></td>

						<td>
						<c:if test="${item.commissionSum gt item.refundComSum}">-
							<fmt:formatNumber type="NUMBER"
									value="${(item.commissionSum - item.refundComSum)}" pattern="#0.00#"></fmt:formatNumber></c:if>
						<c:if test="${item.commissionSum lt item.refundComSum or item.commissionSum eq item.refundComSum}">
							<fmt:formatNumber type="NUMBER"
									value="${(item.refundComSum - item.commissionSum )}" pattern="#0.00#"></fmt:formatNumber></c:if>
						</td>
						<td><fmt:formatNumber type="NUMBER" value="${(item.returnpvsum - item.addpvsum)}" pattern="#0.00"></fmt:formatNumber></td>
						<td><c:if test="${not empty item.diffAdjSum}">
								<fmt:formatNumber type="NUMBER" value="${item.diffAdjSum }"
									pattern="#0.00#" />

							</c:if> <c:if test="${empty item.diffAdjSum}">
								<fmt:formatNumber type="NUMBER" value="0.00" pattern="#0.00#" />
							</c:if></td>
							
						<td><b>
						
						<fmt:formatNumber type="NUMBER" value="${item.currSettleAccounts + item.diffAdjSum}" pattern="#0.00#" />
						
						</b></td>
						<td><c:if test="${item.settlementStatus ==1  }"> 未确认</c:if> 
						    <c:if test="${item.settlementStatus ==2  }"> 商家已确认</c:if> 
						    <c:if test="${item.settlementStatus ==3  }"> 运营已确认</c:if> 
						    <c:if test="${item.settlementStatus ==4  }"> 财务审核通过</c:if> 
						    <c:if test="${item.settlementStatus ==5  }"> 财务审核拒绝</c:if> 
						    <c:if test="${item.settlementStatus ==6  }"> 已结出</c:if></td>

						<td align="center"><img style="cursor: pointer;" class="${item.settlementNo}"
						src="/etc/images/button/view.png" alt="查看" onClick="turnDetail(this)"></td>
				</tr>
					
				</c:forEach>
			</c:if>
			
		</table>
		<br />
		<!-- 分页按钮区 -->
		<table class="page_table" width="98%" align="center">
			<tr>
				<td align="right"><s:include
						value="/WEB-INF/jsp/public/pager.jsp" /></td>
			</tr>
		</table>
		
		
	</form>
     
</body>
</html>

