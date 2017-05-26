<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>生成报表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="/etc/js/urchin.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script src="/etc/js/97dater/WdatePicker.js"></script>
		<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
		<style>
		.autocomplete-suggestions{position: absolute;top: 176px;left: 140px;width: 300px!important;max-height: 100px!important;z-index: 9999;overflow-y: scroll;font-size: 12px;background-color: #fff;border: 1px solid #ccc;padding: 5px;line-height: 20px;}
		</style>
		<script>
		window.onload=function(){ 
			var suppliersArray;
			var supplierType = $("#supplierType").val();
		 	$.ajax({
				async:false,
			    url:'/app/findAllSuppliersForJson.action',
			    data:{"map['supplierType']":supplierType},
			    success:function(data){
			    	suppliersArray = $.map(data, function (value, key) { return { value: value, data: key }; });
			    	$('#supplier').autocomplete({
				    	lookup: suppliersArray,
				        minChars: 0,
				        scrollHeight: 100,
				        max: 5
				    });
			    },
			    dataType:'json'
			}); 
			};

			var isSub=false;
			function exportFile(){
				 if(isSub){
					return;
				}
				var startDate=$('#startDate').val();
				var endDate=$('#endDate').val();
				var supplier=$('#supplier').val();
				if(startDate.length<1||endDate.length<1){
					 alert("请先选择订单付款时间范围！"); 
					return;
				}
				if(startDate.length>1&&endDate.length>1){
					
	                if(DateDiff(startDate,endDate)>90){
						alert("交易时间段不能大于90天!");
						return ;
					}
				}
				isSub=true; 
				
	            if(confirm("报表正在生成,请稍后导出!")==true){
		            	$.ajax({
			            async: false,
			            url: '/app/AsynfinanceOrderReport.action',
			            cache:false,
			            type:'POST',
			            data: {"map['startDate']": startDate,"map['endDate']": endDate, "map['supplier']":supplier,"map['exprotType']":"0" },
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
		            closeWin();
	            }else{
	            	closeWin();
	            }
				
			
			};
			function closeWin(){
			self.opener=null;
			self.close();
			};
			function querySupplierInfo(){
				var suppliersArray;
				var supplierType = $("#supplierType").val();
			 	$.ajax({
					async:false,
				    url:'/app/findAllSuppliersForJson.action',
				    data:{"map['supplierType']":supplierType},
				    success:function(data){
				    	suppliersArray = $.map(data, function (value, key) { return { value: value, data: key }; });
				    	$('#supplier').autocomplete({
					    	lookup: suppliersArray,
					        minChars: 0,
					        scrollHeight: 10,
					        max: 5
					    });
				    },
				    dataType:'json'
				});
			};
			function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式  
			       var  aDate,  oDate1,  oDate2,  iDays  
			       aDate  =  sDate1.split("-")  
			       oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //转换为12-18-2006格式  
			       aDate  =  sDate2.split("-")  
			       oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])  
			       iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数  
			       return  iDays+1;  
			   }  
		</script>
		<style>
		td{font-size: 13px;}
		</style>
	</head>
	<body style="overflow: hidden;">
		<s:form action="financeOrderReport" method="post" name="mainForm">
			<table width="600px" border="0" cellspacing="0" cellpadding="10"  style="margin-top: 30px;">
				<tr>
					<th align="right" width="110px" style="word-wrap:break-word;">订单付款时间从：</th>
					<td align="left">
						<input class="Wdate condition" id="startDate" name="map['startDate']"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\'),\'%y-%M-%d %H:%m:%s\'}'})"
						type="text" value='<s:property value="map['startDate']"/>'> 至
						<input class="Wdate condition" id="endDate" name="map['endDate']"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d %H:%m:%s',minDate:'#F{$dp.$D(\'startDate\')}'})"
						type="text" value='<s:property value="map['endDate']"/>'>
					</td>
				</tr>
				<tr>
					<th align="right" width="110px" style="word-wrap:break-word;">供应商类型&nbsp;&nbsp;&nbsp;：</th>
					<td align="left">
						<s:select  list="#{1:'自营',2:'入驻',3:'代销'}" name="map['supplierType']" headerKey=""  id="supplierType" style="width:185px" headerValue="全部" value="map['supplierType']" onchange="querySupplierInfo()"></s:select>
					</td>
				</tr>
				<tr>
					<th align="right" width="110px" style="word-wrap:break-word;">供应商&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;：</th>
					<td align="left">
						<input id="supplier" name="map['supplier']" style="size: 12px;" type="text" value='<s:property value="map['supplier']"/>'>
					</td>
				</tr>
				<tr> 
					<td align="center" colspan="2">
					<input type="button" value="导出查询结果" class="btn-custom" onClick="exportFile()">&nbsp;&nbsp;
					<input type="button" value=" 取消 " class="btn-custom" onClick="closeWin()"/>&nbsp;&nbsp;
					</td>
				</tr>
			
			</table>
		</s:form>
	</body>
</html>