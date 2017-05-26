<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>收退款列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/autocompletestyles.css">
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/jquery-migrate-1.2.1.min.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript" src="/etc/js/jquery-ui.min.js"></script>
		<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="/etc/js/urchin.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
		<script  src="/etc/js/97dater/WdatePicker.js"></script>
		<script type="text/javascript">
		
			  var isSub=false;
				function exportFile(){ 
					if(isSub){
						return;
					}
					var platformCode=$('#platformCode').val();
					var payType=$('#payType').val();
					var orderCode=$('#orderCode').val();
					var outsidePayStatementNo=$('#outsidePayStatementNo').val();
					var customerAccount=$('#customerAccount').val();
					var flag=$('#flag').val();
					var dateStart=$('#dateStart').val();
					var dateEnd=$('#dateEnd').val();
					if(platformCode == ""&&payType == ""&&orderCode == ""&&outsidePayStatementNo == ""
							&&customerAccount == ""&&flag == ""&&dateStart == ""&&dateEnd == ""){
						alert("请输入至少一个查询条件");
						return;
					}
					if((orderCode == ""&&outsidePayStatementNo == ""&&customerAccount == "")&&(dateStart == ""||dateEnd == "")){
						alert("请选择交易时间范围!");
						return;
					}
					if((dateStart.length>1&&dateEnd.length<1)||(dateStart.length<1&&dateEnd.length>1)){
						alert("开始时间和结束时间必须同时选择或不选！");
						return;
					}
					
					if(dateStart.length>1&&dateEnd.length>1){
						/* var date1,date2,sdate1,sdate2;
						date1 = dateStart.split(" ");
						date2 = dateEnd.split(" ");
						sdate1  = date1[0];
						sdate2  = date2[0]; */
		                if(DateDiff(dateStart,dateEnd)>90){
							alert("交易时间段不能大于90天!");
							return;
						}
					}
					
					isSub=true;
					 $.ajax({
			            async: false,
			            url: '/app/orderPayReport.action',
			            cache:false,
			            type:'POST',
			            data: {"map['platformCode']": platformCode,"map['payType']": payType, "map['orderCode']":orderCode,"map['outsidePayStatementNo']": outsidePayStatementNo,"map['customerAccount']": customerAccount,"map['flag']": flag,"map['dateStart']": dateStart,"map['dateEnd']": dateEnd },
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
				}
				
			
			  function check(){
				var platformCode=$('#platformCode').val();
				var payType=$('#payType').val();
				var orderCode=$('#orderCode').val();
				var outsidePayStatementNo=$('#outsidePayStatementNo').val();
				var customerAccount=$('#customerAccount').val();
				var flag=$('#flag').val();
				var dateStart=$('#dateStart').val();
				var dateEnd=$('#dateEnd').val();
				
				  var checkok = true;
				  if(platformCode == ""&&payType == ""&&orderCode == ""&&outsidePayStatementNo == ""
						&&customerAccount == ""&&flag == ""&&dateStart == ""&&dateEnd == ""){
					alert("请输入至少一个查询条件");
					checkok = false;
					return false;
				}
				if((orderCode == ""&&outsidePayStatementNo == ""&&customerAccount == "")&&(dateStart == ""||dateEnd == "")){
					alert("请选择交易时间范围!");
					checkok = false;
					return false;
				}
				if((dateStart.length>1&&dateEnd.length<1)||(dateStart.length<1&&dateEnd.length>1)){
					alert("开始时间和结束时间必须同时选择或不选！");
					checkok = false;
					return false;
				}
				
				if(dateStart.length>1&&dateEnd.length>1){
					/*var date1,date2,sdate1,sdate2;
					date1 = dateStart.split(" ");
					date2 = dateEnd.split(" ");
					sdate1  = date1[0];
					sdate2  = date2[0]; */
	                if(DateDiff(dateStart,dateEnd)>90){
						alert("交易时间段不能大于90天!");
						checkok = false;
						return false;
					}
				}
				
				return checkok;
			  }
			  
			  function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式  
			       var  aDate,  oDate1,  oDate2,  iDays  
			       aDate  =  sDate1.split("-")  
			       oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //转换为12-18-2006格式  
			       aDate  =  sDate2.split("-")  
			       oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])  
			       iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数  
			       return  iDays+1;  
			   }  
		    
		     
		      function resetValue(){
		    	  $('#platformCode').val('');
		    	  $('#payType').val('');
		    	  $('#orderCode').val('');
		    	  $('#outsidePayStatementNo').val('');
		    	  $('#customerAccount').val('');
		    	  $('#flag').val('');
		    	  $('#dateStart').val('');
		    	  $('#dateEnd').val('');
		      }
		     
		     
		     
		   
		</script>
	</head>
	<body>
		<a href="#" id="downLoadLink" style="display: none;" target="_blank"><span id="downLoadSp" ></span></a>
		<s:set name="parent_name" value="'业务操作'" scope="request" />
		<s:set name="name" value="'订单报表'" scope="request" />
		<s:set name="son_name" value="'收退款报表'" scope="request" />
		<s:include value="/WEB-INF/jsp/public/title.jsp"/>
		<form id="orderPayList" onsubmit="return check()" action="/app/orderPayInfoList.action" method="POST">
		<table class="table_search" width="98%" align="center" cellpadding="0" cellspacing="0" >
			<tr>
			    <th align="right">支付渠道:</th>
			     <td>
				    <select class="condition" id="platformCode" name="map['platformCode']" style="width:156px">
				   		<option value="" <s:if test='map["platformCode"]==""'>selected="selected"</s:if> >全部</option>
				   		<option value="3" <s:if test='map["platformCode"]=="3"'>selected="selected"</s:if> >支付宝</option>
				   		<option value="5" <s:if test='map["platformCode"]=="5"'>selected="selected"</s:if> >微信</option>
				   		<option value="7" <s:if test='map["platformCode"]=="7"'>selected="selected"</s:if> >康美通</option>
				   </select>
				</td>
				 <th align="right">支付类型:</th>
			     <td>
				    <select class="condition" id="payType" name="map['payType']" style="width:156px">
				   		<option value="" <s:if test='map["payType"]==""'>selected="selected"</s:if> >全部</option>
				   		<option value="12" <s:if test='map["payType"]=="12"'>selected="selected"</s:if> >订单交易</option>
				   		<option value="3" <s:if test='map["payType"]=="3"'>selected="selected"</s:if> >余额充值</option>
				   </select>
				</td>
				<th align="right">订单号:</th>
		        <td>
				    <input class="condition" id="orderCode" name="map['orderCode']" type="text" value='<s:property value="map['orderCode']"/>'>
				</td>
				<th align="right">第三方支付流水号:</th>
		        <td>
				    <input class="condition" id="outsidePayStatementNo" name="map['outsidePayStatementNo']" type="text" value='<s:property value="map['outsidePayStatementNo']"/>'>
				</td>
				</tr>
				
				<tr>
				<th align="right">下单账号:</th>
		        <td>
				    <input class="condition" id="customerAccount" name="map['customerAccount']" type="text" value='<s:property value="map['customerAccount']"/>'>
				</td>
				<th align="right">收/退款类型:</th>
				 <td>
				    <select class="condition" id="flag" name="map['flag']" style="width:156px">
				    <option value="" <s:if test='map["flag"]==""'>selected="selected"</s:if> >全部</option>
				   		<option value="1" <s:if test='map["flag"] =="1"'>selected="selected"</s:if> >收款</option>
				   		<option value="2" <s:if test='map["flag"]=="2"'>selected="selected"</s:if> >退款</option>
				   	</select>
				</td>
				<th align="right">交易时间从:</th>
		        	<td>
						<input class="Wdate condition" id="dateStart" name="map['dateStart']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'dateStart\'),\'%y-%M-%d %H:%m:%s\'}'})" type="text" value='<s:property value="map['dateStart']"/>' />
                                                                                        至
                        <input class="Wdate condition" id="dateEnd" name="map['dateEnd']" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'dateEnd\')}'})" type="text" value='<s:property value="map['dateEnd']"/>' />

					</td>						
		        </tr>
				
				
				
		       
			
				
			<tr >
				<td colspan="6" align="right" >
                <input type="submit" class="queryBtn" value="" class="btn-custom"/>
				<input type="button" value="重置 " class="btn-custom" onclick="resetValue()"/>
				<input type="button" value="导出 "  class="btn-custom" onclick="exportFile()"/>
		        </td>
		    </tr>
		 </table>
		<br/>
		<br/>
		<b>&nbsp;&nbsp;&nbsp;&nbsp;收款金额：<font color="red"><s:property value="reMap['pay']"/></font>&nbsp;&nbsp;&nbsp;&nbsp;退款金额：<font color="red"><s:property value="reMap['return']"/></font></b>
		<table class="list_table" width="98%" align="center" cellpadding="3" cellspacing="0" bgcolor="#f2f8ff" border="1" bordercolor="#0099cc" style="border-collapse: collapse;font-size:12px">
			<tr>
		   		<th width="10%">支付渠道</th>
		   		<th width="5%">支付类型</th>
		   		<th width="10%">订单号</th>
		   		<th width="4%">第三方支付流水号</th>
		   		<th width="8%">下单账号</th>
		   		<th width="8%">收/退款类型</th>
		   		<th width="10%">金额</th>
		   		<th width="10%">交易时间</th>
			</tr>
			
			<s:iterator id="orderPay" value="page.dataList">
			<tr>
		   		 <td>
		   		 	<s:if test="#orderPay.platformCode==7">&nbsp;&nbsp;&nbsp;&nbsp;康美通</s:if>
		   		 	<s:if test="#orderPay.platformCode==5">&nbsp;&nbsp;&nbsp;&nbsp;微信</s:if>
		   		 	<s:if test="#orderPay.platformCode==3">&nbsp;&nbsp;&nbsp;&nbsp;支付宝</s:if>
		   		 </td>
		          <td>
		   		 	<s:if test="#orderPay.flag==1">&nbsp;&nbsp;&nbsp;&nbsp;订单交易</s:if>
		   		 	<s:if test="#orderPay.flag==2">&nbsp;&nbsp;&nbsp;&nbsp;订单交易</s:if>
		   		 	<s:if test="#orderPay.flag==3">&nbsp;&nbsp;&nbsp;&nbsp;余额充值</s:if>
		   		 </td>
		   		 <td><s:property value="#orderPay.orderCode"/></td>
		   		 <td><s:property value="#orderPay.outsidePayStatementNo"/></td>
		   		 <td><s:property value="#orderPay.customerAccount"/></td>
		   		  <td>
		   		 	<s:if test="#orderPay.flag==1">&nbsp;&nbsp;&nbsp;&nbsp;收款</s:if>
		   		 	<s:if test="#orderPay.flag==3">&nbsp;&nbsp;&nbsp;&nbsp;收款</s:if>
		   		 	<s:if test="#orderPay.flag==2">&nbsp;&nbsp;&nbsp;&nbsp;退款</s:if>
		   		 </td>
		   		 <td><s:property value="#orderPay.orderMoney"/></td>
		   		  <td><s:property value="#orderPay.operateDate"/></td>
		   		<%--  <td><s:date name="#orderPay.operateDate" format="yyyy-MM-dd" /></td> --%>
		   		
			</tr>
			</s:iterator>
			
		</table>
		
		<br/>
		<table class="page_table" width="98%" align="center">
		   <tr>
		     <td align="right"><s:include  value="/WEB-INF/jsp/public/pager.jsp"/></td>
		   </tr>
		</table>
		</form>
		<div id="question" style="display:none"></div>
	</body>
</html>