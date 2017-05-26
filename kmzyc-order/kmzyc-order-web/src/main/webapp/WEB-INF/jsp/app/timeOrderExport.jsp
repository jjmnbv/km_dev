<%@ page contentType="application/msexcel;charset=UTF-8" import="java.util.Map,java.util.List,java.math.BigDecimal" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String time=(String)request.getAttribute("time");
	if(time==null){
	  time="";
	}
   	response.setContentType("application/msexcel");
   	response.setHeader("Content-disposition","inline; filename="+time.replaceAll(" ","").replaceAll(":","")+".xls");
%>

<%@page import="java.util.HashMap"%><html>
	<head>
		<title>时代会员订单数据报表</title>
	</head>
	<body>
  		<table width="100%" align="center" border="1" cellpadding="0" cellspacing="0">
			<tr>
	   			<td colspan="17" style="font-weight: bold;font-size: 42px;text-align:center;">时代会员订单统计简表</td>
			</tr>
			<tr>
				<td colspan="7" align="right">统计时间</td>
	   			<td colspan="10" style="font-weight: bold;"><%=time%></td>
			</tr>
			<tr>
	   			<td colspan="17">&nbsp;</td>
			</tr>
			<tr>
	   			<td colspan="17">订单数量及订货金额统计</td>
			</tr>
			<tr>
	   			<td colspan="17">&nbsp;</td>
			</tr>
			<tr>
	   			<td>&nbsp;</td>
	   			<td colspan="5">订单统计（个）</td>
	   			<td>&nbsp;</td>
	   			<td colspan="5">订单金额统计（元）</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
			</tr>
			<%
				Map<String, Object> result=(Map<String, Object>)request.getAttribute("result");
				if(null!=result && !result.isEmpty()){
				  Map<String, BigDecimal> SaleInfo=(Map<String, BigDecimal>)result.get("SaleInfo");
				  List<Map<String, String>> ProvinceOrder=(List<Map<String, String>>)result.get("ProvinceOrder");
				  List<Map<String, String>> SaleVolume=(List<Map<String, String>>)result.get("SaleVolume");
				  List<Map<String, String>> SaleAmount=(List<Map<String, String>>)result.get("SaleAmount");
			%>
			<tr style="font-weight: bold;">
	   			<td>&nbsp;</td>
	   			<td>总订单</td>
	   			<td><%=SaleInfo.get("OC")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>总金额</td>
	   			<td><%=SaleInfo.get("AP")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>总PV</td>
	   			<td><%=SaleInfo.get("OP")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
			</tr>
			<tr>
	   			<td>&nbsp;</td>
	   			<td>已支付订单</td>
	   			<td><%=SaleInfo.get("POC")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>已支付订单金额</td>
	   			<td><%=SaleInfo.get("PAP")%></td>
	   			<td>实付金额</td>
	   			<td><%=SaleInfo.get("POM")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>已支付订单PV</td>
	   			<td><%=SaleInfo.get("POP")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
			</tr>
			<tr>
	   			<td>&nbsp;</td>
	   			<td>未支付订单</td>
	   			<td><%=SaleInfo.get("UOC")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>未支付订单金额</td>
	   			<td><%=SaleInfo.get("UAP")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>未支付订单PV</td>
	   			<td><%=SaleInfo.get("UOP")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
			</tr>
			<tr>
	   			<td>&nbsp;</td>
	   			<td>取消订单</td>
	   			<td><%=SaleInfo.get("COC")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>取消订单金额</td>
	   			<td><%=SaleInfo.get("CAP")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>取消订单PV</td>
	   			<td><%=SaleInfo.get("COP")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
			</tr>
			<tr>
	   			<td colspan="17">&nbsp;</td>
			</tr>
			<tr>
	   			<td colspan="17">&nbsp;</td>
			</tr>
			<tr style="font-weight: bold;">
	   			<td>&nbsp;</td>
	   			<td>自营订单</td>
	   			<td><%=SaleInfo.get("SOC")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>自营订单金额</td>
	   			<td><%=SaleInfo.get("SAP")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
			</tr>
			<tr>
	   			<td>&nbsp;</td>
	   			<td>自营已支付</td>
	   			<td><%=SaleInfo.get("SPOC")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>自营已支付金额</td>
	   			<td><%=SaleInfo.get("SPAP")%></td>
	   			<td>自营实付金额</td>
	   			<td><%=SaleInfo.get("SPOM")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
			</tr>
			<tr>
	   			<td>&nbsp;</td>
	   			<td>自营未支付</td>
	   			<td><%=SaleInfo.get("SUOC")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>自营未支付金额</td>
	   			<td><%=SaleInfo.get("SUAP")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
			</tr>
			<tr>
	   			<td>&nbsp;</td>
	   			<td>自营取消订单</td>
	   			<td><%=SaleInfo.get("SCOC")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>自营取消订单金额</td>
	   			<td><%=SaleInfo.get("SCAP")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
			</tr>
			<tr>
	   			<td colspan="17">&nbsp;</td>
			</tr>
			<tr>
	   			<td colspan="17">&nbsp;</td>
			</tr>
			<tr style="font-weight: bold;">
	   			<td>&nbsp;</td>
	   			<td>入驻订单</td>
	   			<td><%=SaleInfo.get("EOC")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>入驻订单金额</td>
	   			<td><%=SaleInfo.get("EAP")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
			</tr>
			<tr>
	   			<td>&nbsp;</td>
	   			<td>入驻已支付</td>
	   			<td><%=SaleInfo.get("EPOC")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>入驻已支付金额</td>
	   			<td><%=SaleInfo.get("EPAP")%></td>
	   			<td>入驻实付金额</td>
	   			<td><%=SaleInfo.get("EPOM")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
			</tr>
			<tr>
	   			<td>&nbsp;</td>
	   			<td>入驻未支付</td>
	   			<td><%=SaleInfo.get("EUOC")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>入驻未支付金额</td>
	   			<td><%=SaleInfo.get("EUAP")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
			</tr>
			<tr>
	   			<td>&nbsp;</td>
	   			<td>入驻取消订单</td>
	   			<td><%=SaleInfo.get("ECOC")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>入驻取消订单金额</td>
	   			<td><%=SaleInfo.get("ECAP")%></td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
	   			<td>&nbsp;</td>
			</tr>
			<tr>
	   			<td colspan="17">&nbsp;</td>
			</tr>
			<tr style="font-weight: bold;">
	   			<td colspan="5">省份订单统计</td>
	   			<td>&nbsp;</td>
	   			<td colspan="5">销售Top20 产品</td>
	   			<td>&nbsp;</td>
	   			<td colspan="5">销量Top20</td>
			</tr>
			<tr style="font-weight: bold;">
				<td>省份</td>
	   			<td>销售额</td>
	   			<td>实收</td>
	   			<td>PV总额</td>
	   			<td>订单总数</td>
	   			<td>&nbsp;</td>
	   			<td>销售额</td>
	   			<td>实收</td>
	   			<td>数量</td>
	   			<td>SKU</td>
	   			<td>产品名称</td>
	   			<td>&nbsp;</td>
	   			<td>销售额</td>
	   			<td>实收</td>
	   			<td>数量</td>
	   			<td>SKU</td>
	   			<td>产品名称</td>
			</tr>
			<%
				int size=ProvinceOrder.size();
				if(size < SaleVolume.size()){
				  size= SaleVolume.size();
				}
				int plen=ProvinceOrder.size(),vlen=SaleVolume.size(),alen=SaleAmount.size();
				for(int i=0;i<size;i++){
				  Map<String, String> pm=plen>i?ProvinceOrder.get(i):new HashMap<String, String>(); 
				  Map<String, String> sv=vlen>i?SaleVolume.get(i):new HashMap<String, String>(); 
				  Map<String, String> sa=alen>i?SaleAmount.get(i):new HashMap<String, String>();
			%>
			<tr>
				<td style="text-align:left;"><%=null==pm.get("PROVINCE")?"":pm.get("PROVINCE")%></td>
	   			<td style="text-align:left;"><%=null==pm.get("ORDERAMOUNT")?"":pm.get("ORDERAMOUNT")%></td>
	   			<td style="text-align:left;"><%=null==pm.get("ORDERMONEY")?"":pm.get("ORDERMONEY")%></td>
	   			<td style="text-align:left;"><%=null==pm.get("ORDERPV")?"":pm.get("ORDERPV")%></td>
	   			<td style="text-align:left;"><%=null==pm.get("ORDERCOUNT")?"":pm.get("ORDERCOUNT")%></td>
	   			<td>&nbsp;</td>
	   			<td style="text-align:left;"><%=null==sv.get("AMOUNT")?"":sv.get("AMOUNT")%></td>
	   			<td style="text-align:left;"><%=null==sv.get("MONEY")?"":sv.get("MONEY")%></td>
	   			<td style="text-align:left;"><%=null==sv.get("NUM")?"":sv.get("NUM")%></td>
	   			<td style="text-align:left;vnd.ms-excel.numberformat:@"><%=null==sv.get("SKU")?"":sv.get("SKU")%></td>
	   			<td style="text-align:left;"><%=null==sv.get("PRODUCTNAME")?"":sv.get("PRODUCTNAME")%></td>
	   			<td>&nbsp;</td>
	   			<td style="text-align:left;"><%=null==sa.get("AMOUNT")?"":sa.get("AMOUNT")%></td>
	   			<td style="text-align:left;"><%=null==sa.get("MONEY")?"":sa.get("MONEY")%></td>
	   			<td style="text-align:left;"><%=null==sa.get("NUM")?"":sa.get("NUM")%></td>
	   			<td style="text-align:left;vnd.ms-excel.numberformat:@"><%=null==sa.get("SKU")?"":sa.get("SKU")%></td>
	   			<td><%=null==sa.get("PRODUCTNAME")?"":sa.get("PRODUCTNAME")%></td>
			</tr>
			<%				  	
				}
			}
			%>
		</table>
	</body>
</html>