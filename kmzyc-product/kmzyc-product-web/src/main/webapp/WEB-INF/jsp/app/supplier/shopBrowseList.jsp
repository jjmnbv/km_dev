<%@page contentType="text/html;charset=UTF-8"  isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>店铺浏览量</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/autocomplete/autocompletestyles.css" type="text/css" rel="stylesheet">
<style type="text/css">
.tableStyle1 {
	font-size: 12px;
}
;
</style>
<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="/etc/js/common.js"></script>
<script type="text/javascript" src="/etc/js/jquery.form.js"></script>
<script type="text/javascript" src="/etc/js/supplier/shopBrowseList.js"></script>
<script type="text/javascript" src="/etc/autocomplete/jquery.mockjax.js"></script>
<script type="text/javascript" src="/etc/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript" src="/etc/js/highcharts/highcharts.src.js"></script>
<script src="/etc/js/97dater/WdatePicker.js"></Script>
</head>
<body>
<%@ include file="/WEB-INF/jsp/sys/sessionJudge.jsp"%>

<s:set name="parent_name" value="'供应商管理'" scope="request" />
<s:set name="name" value="'店铺浏览量'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<s:form action="/app/queryShopBrowseList.action" method="POST" namespace='/app' id="frm" name='frm'>

	<input type="hidden" id="dataList" value='<s:property value="#request.groupData"/>' />
	<input type="hidden" id="dataSize" value='<s:property value="#request.groupDataSize"/>'/>

	<!-- 查询条件区域 -->
	<table width="98%" class="content_table" align="center" cellpadding="0" cellspacing="0">
		<tr>
		<td align="right">日期：&nbsp;&nbsp;</td>
		<td align="left">
		<input type="text" name="shopBrowseInfo.startDate" placeholder="请输入统计开始时间"  maxlength="10" readonly  id="startDate" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\'),\'%y-%M-%d\'}'})" 
					value="<s:property value='shopBrowseInfo.startDate' />">&nbsp;
					到
					&nbsp;
		<input type="text" name="shopBrowseInfo.endDate"
					 placeholder="请输入统计结束时间" maxlength="10" readonly 
					value="<s:property value='shopBrowseInfo.endDate' />" id="endDate" value="<s:property value='endDate'/>" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'startDate\')}'})" >	</td>
				<td align="right">页面标题：</td>
			<td align="left"><s:textfield name="shopBrowseInfo.titleForQuery"
				cssClass="input_style" id="productNo" size="68"/></td>
    	</tr>
		<tr>
        			<td align="right">页面url：</td>
			<td align="left"><s:textfield name="shopBrowseInfo.urlForQuery" cssClass="input_style"  size="68"/></td>
			<td align="right">商家名称：</td>
			<td align="left">
					<s:hidden name="shopBrowseInfo.shopId" id="shopId" />
					<s:hidden name="shopBrowseInfo.supplierNameForQuery" id="supplierName" />
					<input type="text" id="autocomplete_forShop" value="<s:property value='shopBrowseInfo.supplierNameForQuery'/>" size="32" />
			</td>
			<td align="right">筛选时间：</td>
			<td align="left">
			<!-- 
			 * 查询时间类型
			 * 0 所有：起止时间为空（不限时间范围）；
			 * 1 当天：起止时间都自动选择当前日期；
			 * 2 最近七天：开始时间为当前日期减去6天，截止时间为当天；
			 * 3 当月：开始时间为当月1日，截止时间为当天；
			 * 4 上月：开始时间为上月1日，截止时间为上月最后一天； 
			 * 日期格式: yyyy-MM-dd HH:mm
			 -->
			<select id="timeType" name="shopBrowseInfo.timeTypeQuery" onChange="autoChangeTime(this)">
				<option value="0" <s:if test="shopBrowseInfo.timeTypeQuery==0">selected="selected"</s:if> >所有</option>
				<option value="1" <s:if test="shopBrowseInfo.timeTypeQuery==1">selected='selected'</s:if> >当天</option>
				<option value="2" <s:if test="shopBrowseInfo.timeTypeQuery==2">selected='selected'</s:if> >最近七天</option>
				<option value="3" <s:if test="shopBrowseInfo.timeTypeQuery==3">selected='selected'</s:if> >当月</option>
				<option value="4" <s:if test="shopBrowseInfo.timeTypeQuery==4">selected='selected'</s:if> >上月</option>
			</select>
			</td>
            			<td>
				<input type="button" id="queryBtn" class="queryBtn" value="">
			</td>
		</tr>
	</table>
	<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;店铺总浏览量:<strong>
			<s:if test="#request.totalCount!=null">
			<s:property value="#request.totalCount"/>
			</s:if>
			<s:else>
			0
			</s:else>
			</strong></p>

		<div id="chartsContainer">
		</div>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" align="center" cellpadding="3"
				cellspacing="0" border="1" bordercolor="#C1C8D2">
				<tr>
					<th align="center" width="15%">页面地址</th>
					<th align="center" width="20%">页面标题</th>
					<th align="center" width="10%">点击量</th>			
				</tr>
				<s:if test="#request.noShopId">
					<tr>
						<td colspan="3">请您先选择需要查看的商家!</td>		
					</tr>
				</s:if>
				<s:else>
					<s:iterator id="shopBrowseiterator" value="page.dataList">
						<tr>
							<td align="center"><s:property value="url" /></td>
							<td align="center"><s:property value="title" /></td>
							<td align="center"><s:property value="count" /></td>
						</tr>
					</s:iterator>
				</s:else>
				
			</table>
		
			<!-- 分页按钮区 -->
			<table width="98%" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td><%@ include file="/WEB-INF/jsp/public/pager.jsp"%>
					</td>
				</tr>
			</table>

	
</s:form>
</BODY>

<script type="text/javascript">

	var dataList=$("#dataList").val();
	if(dataList=="" || dataList=="null"){
	    console.info("不满足分组条件或者没有数组,dataList="+dataList);
	}else{
		
	/*折线图所需要的x 和 y 轴 数组*/
	var xArray=[];
	var yArray=[];

	dataList=eval(dataList);
	console.info(dataList);
	
	if(dataList.length>1){
		
		for(var i=0; i<dataList.length; i++)
		{
			xArray[i]=dataList[i].groupDate;
			yArray[i]=dataList[i].groupTotalCount;
		    console.info(dataList[i].groupDate+"," + dataList[i].groupTotalCount);
		}

		console.info(yArray);
		console.info(xArray);
		var dataMap=[{
	        name: '浏览量',
	        data: yArray
	    }];
		
		var startDate=$("#startDate").val();
	     var endDate=$("#endDate").val();
	     var timeType=$("#timeType").val();
	     var subTitle="自统计到现在的店铺浏览量";

	     if(startDate!="" && endDate!=""){
	         subTitle=startDate+" 至 "+endDate+" 的店铺浏览量";
	     }


	     if(startDate=="" && endDate!=""){
	         subTitle="截止到"+endDate+"的店铺浏览量";
	     }

	     if(startDate!="" && endDate=="" ){
	         subTitle="从"+startDate+"截止到当前的店铺浏览量";
	     }

	     if(startDate=="" && endDate==""){
	    	 console.info("timeType="+timeType);
	         if(timeType=="1"){
	             subTitle="当天的店铺浏览量";
	         }else if(timeType=="2"){
	             subTitle="最近7天的店铺浏览量";
	         }else if(timeType=="3"){
	             subTitle="当月的店铺浏览量";
	         }else if(timeType=="4"){
	             subTitle="上月的店铺浏览量";
	         }else{
	             subTitle="自统计到现在的店铺浏览量";
	         }
	     }
	 	$(function(){
			/*加载折线图begin*/
			 $('#chartsContainer').highcharts({
			        title: {
			            text: '供应商店铺浏览统计折线图',
			            x: -20 //center
			        },
			        subtitle: {
			            text: subTitle,
			            x: -20
			        },
			        xAxis: {
			            categories: xArray
			        },
			        yAxis: {
			        	min: 0, 
			        	tickInterval: 1,
			            title: {
			                text: '供应商店铺各页面浏览总量'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			       		 formatter: function() {  
			                return '<b>浏览日期:'+ this.x +'</b><br/>'+  '<b>浏览量: '+ this.y+'<br/>';  
			           } 
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        series: dataMap,
			        plotOptions: {  
			            column: {  
			                stacking: 'normal',  
			                dataLabels: {  
			                    enabled: true,  
			                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'  
			                }  
			            }  
			        }
			       // plotOptions: {  //显示点值的方式 ,上面那种是显示悬浮效果
			         //   line: {
			         //       dataLabels: {
			         //           enabled: true
			         //       },
			          //      enableMouseTracking: false
			          //  }
			       // }
			    });
			
			/*加载折线图end*/
		});
	}	

	}
	
	
	function autoChangeTime(obj){
		 //* 0 所有：起止时间为空（不限时间范围）；
		 //* 1 当天：起止时间都自动选择当前日期；
		// * 2 最近七天：开始时间为当前日期减去6天，截止时间为当天；
		// * 3 当月：开始时间为当月1日，截止时间为当天；
		// * 4 上月：开始时间为上月1日，截止时间为上月最后一天； 
	//	alert("选中的值="+obj.value);
		
		var startDate="";
		var endDate="";
		
		var nowDate= new Date();
		var year=nowDate.getFullYear();
		var month=nowDate.getMonth()+1;  //当月=month+1
		var date=nowDate.getDate();
		//alert("year="+year+",month="+month+",date="+date);
		if(obj.value=="1"){			
			startDate=year+"-"+month+"-"+date;
			endDate=year+"-"+month+"-"+date;			
		}else if(obj.value=="2"){
			
			//设置日期，当前日期的前七天 
			var myDate = new Date();
			//获取今天日期
			myDate.setDate(myDate.getDate() - 7);
			
			//alert("year="+myDate.getFullYear()+",month="+myDate.getMonth());
			
			startDate=myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate();		
			endDate=year+"-"+month+"-"+date;
			
		}else if(obj.value=="3"){			
			startDate=year+"-"+month+"-1";
			endDate=year+"-"+month+"-"+date;		
		}else if(obj.value=="4"){			
			
			var new_year = year;    //取当前的年份    
			
			if(month==1){
				month=12;
				new_year=new_year-1;
			}else{
				month=month-1;
			}
			
			var tempDate=new Date(new_year,month,1); 
			var lastDate=new Date(tempDate.getTime()-1000*60*60*24);
			
			startDate=new_year+"-"+month+"-1";
			endDate=new_year+"-"+month+"-"+lastDate.getDate();	
		}
		console.info("时间范围值="+obj.value+",startDate="+startDate+",endDate="+endDate);
		$("#startDate").val(startDate);
		$("#endDate").val(endDate);
	}

</script>
</HTML>
