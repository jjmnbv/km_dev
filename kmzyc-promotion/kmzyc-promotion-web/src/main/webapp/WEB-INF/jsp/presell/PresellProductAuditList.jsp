<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<Script src="/etc/js/97dater/WdatePicker.js"></Script>
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css">
<title>预售审核列表</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<style type="text/css">
.tableStyle1 { font-size: 12px; }
</style>
</head>
<s:set name="parent_name" value="'预售管理'" scope="request"/>
<s:set name="name" value="'预售审核'" scope="request"/>
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<body>
<s:form id="auditSpreaderListForm" method="post" action="queryAuditPresellList" namespace="/presell"> 
<input type="hidden" id="exportStatus" value="${exportStatus}" />
		<!-- 查询条件区域 -->
  <table width="98%" class="searcharea"  cellpadding="0" cellspacing="0" align="center">
    <tr height="30px">
      <td align="left">预售标题：
        <input name="promotionPresellCriteria.presellTitle" type="text" 
					class="input_style" value="<s:property value='promotionPresellCriteria.presellTitle' />"></td>
      <td align="left">预售ID：
        <input name="promotionPresellCriteria.presellId"  type="text" 
					class="input_style" onKeyUp="this.value=this.value.replace(/\D/g,'')" 
					onafterpaste="this.value=this.value.replace(/\D/g,'')"  value="<s:property value='promotionPresellCriteria.presellId' />"></td>
      <td align="left">商品标题：
        <input name="promotionPresellCriteria.productTitle"  type="text" 
					class="input_style" value="<s:property value='promotionPresellCriteria.productTitle' />"></td>
      <td align="left">商品编码：
        <input name="promotionPresellCriteria.productNO"  type="text" 
					class="input_style" value="<s:property value='promotionPresellCriteria.productNO' />"></td>
      <!-- 					预售状态，0：草稿，1：提交审核（待审核），3：终止，--动态状态：4：支付定金，5：未到尾款支付时间，6：支付尾款，7：已结束-- -->
      <td align="left">预售状态：
        <select name="promotionPresellCriteria.presellStatus">
          <option value="9" >所有</option>
          <%-- 					<option <c:if test="${promotionPresellCriteria.presellStatus ==0 }">selected="selected"</c:if> value="0">草稿</option> --%>
          <option <c:if test="${promotionPresellCriteria.presellStatus ==1 }">selected="selected"</c:if> value="1">待审核</option>
          <option <c:if test="${promotionPresellCriteria.presellStatus ==3 }">selected="selected"</c:if> value="3">终止</option>
          <option <c:if test="${promotionPresellCriteria.presellStatus ==4 }">selected="selected"</c:if> value="4">支付定金</option>
          <option <c:if test="${promotionPresellCriteria.presellStatus ==5 }">selected="selected"</c:if> value="5">未到尾款支付时间</option>
          <option <c:if test="${promotionPresellCriteria.presellStatus ==6 }">selected="selected"</c:if> value="6">支付尾款</option>
          <option <c:if test="${promotionPresellCriteria.presellStatus ==7 }">selected="selected"</c:if> value="7">已结束</option>
        </select></td>
      <td align="right" rowspan="2" ><INPUT TYPE="submit" class="queryBtn"
					value=""></td>
    </tr>
    <tr>
      <td align="left" >审核状态：&nbsp;&nbsp;&nbsp;
        <select name="promotionPresellCriteria.auditStatus">
          <option value="9" >所有</option>
          <option <c:if test="${promotionPresellCriteria.auditStatus ==0 }">selected="selected"</c:if>  value="0">未审核</option>
          <option <c:if test="${promotionPresellCriteria.auditStatus ==1}">selected="selected"</c:if> value="1">审核通过</option>
          <option <c:if test="${promotionPresellCriteria.auditStatus ==2 }">selected="selected"</c:if> value="2">审核不通过</option>
        </select></td>
      <td align="left">预售时间：
        <input id="inputPayTime" name="promotionPresellCriteria.payTime"  type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					class="input_style" value="<s:date name="promotionPresellCriteria.payTime" format="yyyy-MM-dd HH:mm:ss"/>" /></td>
      <td align="left">至：
        <input id="inputPayTime2" name="promotionPresellCriteria.payTime2"  type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					class="input_style"  value="<s:date name="promotionPresellCriteria.payTime2" format="yyyy-MM-dd HH:mm:ss"/>" /></td>
    <tr>
  </table>
  
  <!-- 数据列表区域 -->
  <table width="98%" class="list_table" align="center" cellpadding="3"
			cellspacing="0" border="1" bordercolor="#C1C8D2">
    <tr>
      <th align="center" width="5%">预售ID</th>
      <th align="center" width="12%">预售标题</th>
      <th align="center">商品标题</th>
      <th align="center" width="10%">商品编码</th>
      <th align="center" width="10%">SKU编码</th>
      <th align="center" width="5%">预售价</th>
      <th align="center" width="5%">定金</th>
      <th align="center" width="15%">预售时间</th>
      <th align="center" width="10%">预售状态</th>
      <th align="center" width="5%">审核状态</th>
      <th align="center" width="10%">操作</th>
    </tr>
    <c:if test="${not empty dataList }">
      <c:forEach var="item" items="${dataList }">
        <tr onMouseOver="this.style.backgroundColor='#def2fa'"
				onMouseOut="this.style.backgroundColor='#FFFFFF'">
          <td align="center">${item.presellId }</td>
          <td align="center">${item.presellTitle }</td>
          <!--SKU信息 -->
          <c:set var="exitId" value="1">
            </c:set>
          <c:forEach var="detailLitem" items="${detailList}" >
            <c:if test="${exitId == '1' }">
              <c:if test="${detailLitem.presellId == item.presellId }">
                <td align="center">${detailLitem.productTitle }</td>
                <td align="center">${detailLitem.productNo }</td>
                <c:set var="exitId" value="0">
                  </c:set>
              </c:if>
            </c:if>
          </c:forEach>
          <td align="center" colspan='3'><table width="98%" class="list_table" align="center" cellpadding="0"cellspacing="0" border="1" bordercolor="#C1C8D2">
              <c:forEach var="detailLitem" items="${detailList}"  >
                <c:if test="${detailLitem.presellId == item.presellId }">
                  <tr >
                    <td width="25%" align="center">${detailLitem.productSkuCode }</td>
                    <td width="13%" align="center"><fmt:formatNumber value="${detailLitem.presellPrice }" pattern="0.00"/></td>
                    <td width="13%" align="center"><fmt:formatNumber value="${detailLitem.depositPrice }" pattern="0.00"/></td>
                  </tr>
                </c:if>
              </c:forEach>
            </table></td>
          
          <!--SKU信息  end-->
          <td align="center">从
            <fmt:formatDate value="${ item.depositStartTime   }" pattern="yyyy-MM-dd HH:mm:ss" />
            <br/>
            到
            <fmt:formatDate value="${ item.finalpayEndTime  }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
          <td align="center"><c:if test="${item.presellStatus == 0 }">草稿</c:if>
            <c:if test="${item.presellStatus == 1 }">待审核</c:if>
            <c:if test="${item.presellStatus == 3 }">终止</c:if>
            <c:if test="${item.presellStatus == 4 }">支付定金</c:if>
            <c:if test="${item.presellStatus == 5}">未到尾款支付时间</c:if>
            <c:if test="${item.presellStatus == 6 }">支付尾款</c:if>
            <c:if test="${item.presellStatus == 7 }">已结束</c:if></td>
          <td align="center"><c:if test="${item.auditStatus == 0 }">未审核</c:if>
            <c:if test="${item.auditStatus == 1 }">审核通过</c:if>
            <c:if test="${item.auditStatus == 2 }">审核不通过</c:if></td>
          <td align="center"><a onClick="getSpreaderDetail(${item.presellId},0)" href="javascript:void(0)" style='text-decoration:none;' ><img alt="查看" title="查看" src="/etc/images/button_new/select.png" onClick="turnDetail(1720)" style="cursor: pointer;">&nbsp;</a>
            <c:if test="${item.auditStatus == 0 and item.presellStatus != 3 and item.presellStatus != 7}"><a href="javascript:void(0)" onClick="getSpreaderDetail(${item.presellId},1)"  style='text-decoration:none;'><img alt="审核" title="审核" src="/etc/images/button_new/submit.png"  style="cursor: pointer;">&nbsp;</a></c:if></td>
        </tr>
      </c:forEach>
    </c:if>
  </table>
  <!-- 分页按钮区 -->
  <c:if test="${not empty  dataList }">
    <table width="98%" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td><%@ include file="/WEB-INF/jsp/public/pager.jsp"%></td>
      </tr>
    </table>
  </c:if>
  <c:if test="${empty  dataList }">
    <table width="98%" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="98%"  align="center">没有查询到数据</td>
      </tr>
    </table>
  </c:if>
</s:form>
</body>
<script src="/etc/js/qtip/jquery.min.1.8.3.js"></script>
<script type="text/javascript">
	  

    
    /**  查看商品  **/
    function getSpreaderDetail(inviteId,status){
    	location.href="getAuditPresellDetail.action?presellId="+inviteId+"&status="+status;
    }
    

    
    
   /** 全选js  **/
      function checkAll(ck){
		
		  var  $cbx = $("body").find(".checkbox");
		 
		  for (var i=0;i< $cbx.length;i++){
		    var ele =  $cbx [i];
		    if ((ele.type=="checkbox")){
		      if(ck.checked!=ele.checked)
		        ele.click();
		    }
		  }
    }

</script>
</html>
