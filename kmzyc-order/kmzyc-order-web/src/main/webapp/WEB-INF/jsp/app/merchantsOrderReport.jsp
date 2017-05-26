<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>自营商城订单财务版报表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/etc/css/block.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jq.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/jquery-ui.css">
		<link rel="stylesheet" type="text/css" href="/etc/css/autocompletestyles.css">
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="/etc/js/chili-1.7.pack.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="/etc/js/urchin.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.validate.js"></script>
		<script type="text/javascript" src="/etc/js/jquery.metadata.js"></script>
		<script type="text/javascript" src="/etc/js/messages_cn.js"></script>
		<script src="/etc/js/97dater/WdatePicker.js"></script>
		<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
		<script>
			var isSub=false;
			function exportFile(){
			 var selectTime="";
	           var check_array=document.getElementsByName("category");
	           for(var i=0;i<check_array.length;i++)
	           {
	               if(check_array[i].checked==true)
	               {         
	            	   selectTime+=check_array[i].value+",";
	               }
	           }
	          
				if(isSub){
					return;
				}
				var selectTimes = selectTime; 
				var supplier=$('#supplier').val();
				if(selectTimes.length<1){
					alert("请选择账期！");
					return;
				}
				if(supplier == ""){
					document.getElementById("no_wirte").style.display="inline-block"; 
					return;
				}
				isSub=true;
				$.ajax({
		            async: false,
		            url: '/app/merchantsOrderReport.action',
		            cache:false,
		            type:'POST',
		            data: {"map['selectTimes']": selectTimes, "map['supplier']":supplier },
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
				 $("#exportBtn").attr("disabled", true);  // removeAttr("disabled");
				} ;
				
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
				
				$(function(){
					var countriesArray;
					   $.ajax({
							//async:'false',
							url:'selectSupplierListByName.action',
							success:function(data){
								  countriesArray = $.map(data, function (value, key) { 
									return { value: value, name: key }; 
									});
								  
								  $("#supplier").bind("change",function(){
									  $("#hid_supplier_id").val(null);
									})
								$('#supplier').autocomplete({
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
		</script>
	</head>
	<body>
		<a href="#" id="downLoadLink" style="display: none;" target="_blank"><span id="downLoadSp" ></span></a>
		<s:set name="parent_name" value="'业务操作'" scope="request" />
		<s:set name="name" value="'订单报表'" scope="request" />
		<s:set name="son_name" value="'商家版订单报表'" scope="request" />
		<s:include value="/WEB-INF/jsp/public/title.jsp" />
		<s:form action="financeOrderReport" method="post" name="mainForm">
		
			<table width="98%" align="center" border="0" class="table_search">
				<tr align="center">
				
					<td align="center" width="50%">
						<span style="font-weight:bold" >供应商:</span><input id="supplier" name="map['supplier']" style="size: 12px;" type="text" value='<s:property value="map['supplier']"/>'>
						<span  style="display:none;color: red;"  id="no_wirte"   style="" />请输入供应商名称</span>
					</td>
                    					<td align="center"  width="50%">
						<span style="font-weight:bold" >选择导出订单的账期:</span><select id="myYear" onChange="queryOrderInfo()"> 
					     <option value="">请选择 </option>
					   </select><span style="color:red">选择年份</span>
					</td>
				</tr>
				<tr align="center">
				<td align="center" colspan="4">
				 	<div id="timeSelect"></div>
				</td>
				</tr>
				<tr> 
					<td colspan="6" align="center">
					<input id="hid_supplier_id" type="hidden" name="_sellerId" value="${_sellerId }">
						<input type="reset" value=" 重置 " class="btn-custom"/>&nbsp;&nbsp;
						<input type="button" id="exportBtn" value="导出查询结果" class="btn-custom" onClick="exportFile()">&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</s:form>
	</body>
</html>