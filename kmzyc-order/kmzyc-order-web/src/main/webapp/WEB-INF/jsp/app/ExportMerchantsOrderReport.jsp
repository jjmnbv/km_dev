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
		<script type="text/javascript" src="/etc/js/jquery-ui.js"></script>
		<script src="/etc/js/97dater/WdatePicker.js"></script>
		<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
		<style>
		.autocomplete-suggestions{position: absolute;top: 176px;left: 140px;width: 300px!important;max-height: 300px!important;z-index: 9999;overflow-y: scroll;font-size: 12px;background-color: #fff;border: 1px solid #ccc;padding: 5px;line-height: 20px;}
		</style>
		<script>
		window.onload=function(){ 
			//设置年份的选择 
			var myDate= new Date(); 
			//var startYear=myDate.getFullYear();//起始年份 
			var startYear='2015';//起始年份 
			var endYear=myDate.getFullYear()+10;//结束年份 
			var obj=document.getElementById('myYear');
			for (var i=startYear;i<=endYear;i++) 
			{ 
			obj.options.add(new Option(i,i)); 
			} 
			
			//导出查询结果按钮置灰
			 $("#exportBtn").attr("disabled", true); 
			} ;
			
			var isSub=false;
			function exportFile(){
			
				 if(isSub){
					return;
				}
			   var selectTime="";
	           var check_array=document.getElementsByName("category");
	           for(var i=0;i<check_array.length;i++)
	           {
	               if(check_array[i].checked==true)
	               {         
	            	   selectTime+=check_array[i].value+",";
	               }
	           }
	            var selectTimes = selectTime;
				 if(selectTimes.length<1){
						alert("请选择账期！");
						return;
					}
			
				var supplier=$('#supplier').val();
				
				isSub=true; 
				if(confirm("报表正在生成,请稍后导出!")==true){
					$.ajax({
			            async: false,
			            url: '/app/AsynmerchantsOrderReport.action',
			            cache:false,
			            type:'POST',
			            data: {"map['selectTimes']": selectTimes, "map['supplier']":supplier,supplier,"map['exprotType']":"1" },
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
			
			function queryOrderInfo(){ //查询账期
				var obj=document.getElementById('myYear');
				var index=obj.selectedIndex ;
				year = obj.options[index].text;
				var str = "";
				var index = "";
				for(var i = 1; i <= 12; i++){
					  if(i<10){
						  index = "0"+i;
					  }else{
						  index = i;
					  }
					  str += "<input type='checkbox' name='category' value='"+year+index+'H1'+"'/>"+year+index+"H1";
					  str += "<input type='checkbox' name='category' value='"+year+index+'H2'+"'/>"+year+index+"H2";
				
					  str +="<br/>";
				}
				str +="          <span style='color:red'>选择本年度已生成数据的账期</span>";
				//导出按钮的激活和置灰
				if(year == "请选择"){
					$("#timeSelect").html(""); //清空
				    $("#exportBtn").attr("disabled", true);
				}else{
					$("#timeSelect").html(str);
					$("#exportBtn").removeAttr("disabled");
				}
			};
			
			window.onload=function(){ 
				var suppliersArray;
			 	$.ajax({
					async:false,
				    url:'/app/findAllSuppliersForJson.action',
				    data:{"map['supplierType']":"2"},
				    success:function(data){
				    	suppliersArray = $.map(data, function (value, key) { return { value: value, data: key }; });
				    	$('#supplier').autocomplete({
					    	lookup: suppliersArray,
					        minChars: 0,
					        max:2,
					        scrollHeight: 100
					       
					    });
				    },
				    dataType:'json'
				}); 
				};
		</script>
				<style>
		td{font-size: 13px;}
		</style>
	</head>
	<body style="overflow: hidden;">
		<s:form action="" method="post" name="mainForm">
			<table width="600px" border="0" cellspacing="0" cellpadding="10" style="margin-top: 30px;">
				
				
				<tr>
				    
					<td align="center">
						<span style="font-weight:bold" >供应商：</span><input id="supplier" name="map['supplier']" style="size: 12px;" type="text" value='<s:property value="map['supplier']"/>'>
					</td>
				</tr>
				<tr>
					<td align="center">
						<span style="font-weight:bold" >选择导出订单的账期：</span><select id="myYear" onChange="queryOrderInfo()"> 
					     <option value="">请选择 </option>
					   </select><span style="color:red">选择年份</span>
					</td>
				</tr>
				<tr>
					<td align="center">
						<div id="timeSelect"></div>
					</td>
				</tr>
				<tr> 
					<td  align="center">
					<input type="button" id="exportBtn" value="生成报表" class="btn-custom" onClick="exportFile()">&nbsp;&nbsp;
					<input type="button" value=" 取消 " class="btn-custom" onClick="closeWin()"/>&nbsp;&nbsp;
					</td>
				</tr>
				
			</table>
		</s:form>
	</body>
</html>