<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>医务专属信息管理</title>
<link href="/etc/css/style_sys.css" type="text/css" rel="stylesheet">
<script type="text/javascript">
    /** 删除医务专属信息  **/
    function deleteSelected(id){
        var obj = document.getElementsByName(id);
                var count = 0;
                var obj_cehcked = null;
                // 遍历所有用户，找出被选中的用户
                for (var i = 0; i < obj.length; i++) {
                    if (obj[i].checked) {
                        count++;
                        obj_cehcked = obj[i];
                    }
                }
                 if (count == 0) {
                        alert("请选择要删除的数据。");
                        return false;
                 }else if(confirm('是否确认删除?')==true){ 
                          document.mdicalExcusieForm.action="/mdicalExcusie/mdicalExcusie_detele.action";
                          document.mdicalExcusieForm.submit();
                 }
    }
    /**  进入新增医务专属信息页面  **/
    function gotoAdd(){
         document.mdicalExcusieForm.action="/mdicalExcusie/mdicalExcusie_preAdd.action";
         document.mdicalExcusieForm.submit();
    }

    /**  进入修改医务专属信息页面  **/
    function gotoUpdate(id){
    	var obj = document.getElementsByName(id);
        var count = 0;
        var obj_cehcked = null;
        // 遍历所有用户，找出被选中的用户
        for (var i = 0; i < obj.length; i++) {
            if (obj[i].checked) {
                count++;
                	obj_cehcked = obj[i];
                
            }
        }
         if (count == 1) {
        	 document.mdicalExcusieForm.action="/mdicalExcusie/mdicalExcusie_preUpdate.action?nmeid="+obj_cehcked.value;
             document.mdicalExcusieForm.submit();
         }else{
        	 alert("请选择一个条账户信息。");
         }
         
    }
    
   /** 全选js  **/
      function checkAll(ck){
		  for (var i=0;i<ck.form.all.tags("input").length;i++){
		    var ele = ck.form.all.tags("input")[i];
		    /*var ct = ele.getAttribute("type");*/
		    if ((ele.type=="checkbox")){
		      if(ck.checked!=ele.checked)
		        ele.click();
		    }
		  }
    }
</script>
</head>
<body>
<s:form  name="mdicalExcusieForm"  method="post" action="/mdicalExcusie/mdicalExcusie_pageList.action">
<!-- 标题条 -->
<div class="pagetitle">医务专属信息管理:</div>
<!-- 按钮条 -->
<table width="98%" align="center" class="topbuttonbar" height="30" border="0" cellpadding="0" cellspacing="0">
	<tr> 
	    <td width="90%" valign="middle">
            <input class="btngreen" type="button" value="+ 新增 " onclick="gotoAdd();">
            <input class="btngreen" type="button" value="* 修改 " onclick="gotoUpdate('nmeids');">
			<input class="btngreen" type="button" value="- 删除 "  onclick="deleteSelected('nmeids');">
		</td>
	    <td width="10%" align="center"><!--a href="#" onclick="gotoList();">>&nbsp;返回&nbsp;</a--></td>
	</tr>
</table>

<!-- 查询条件区域 -->
<table  width="98%" class="searcharea" align="center" cellpadding="0" cellspacing="0" >
	<tr>
		<td align="right">姓名：</td>
		<td>
		     <input name="mdicalExcusieInfo.name" type="text" value="<s:property value='mdicalExcusieInfo.name'/>">
		</td>
			<td align="right">所在城市：</td>
         <td>
		     <input name="mdicalExcusieInfo.theCity" type="text" value="<s:property value='mdicalExcusieInfo.theCity'/>">
		</td>
		<td align="right">所属医院：</td>
		<td>
		     <input name="mdicalExcusieInfo.theHospital" type="text" value="<s:property value='mdicalExcusieInfo.theHospital'/>">
		</td>
		
		<td align="center">
			<INPUT TYPE="submit" class="button-blue-1" value=" 查询 ">
		</td>
	</tr>
</table>


<!-- 数据列表区域 -->
<table width="98%" class="tableStyle1" align="center" cellpadding="3" cellspacing="0" border="1" bordercolor="#C1C8D2">
	<tr>
	    <th align="center" width="5%">
            <input type='checkbox' name='allbox'  onclick='checkAll(this)'>
		</th>
		<th align="center" >姓名</th>
		<th align="center" >所在城市</th>
		<th align="center" >所属医院</th>
		<th align="center" >医院等级</th>
		<th align="center" >所属科室</th>
		<th align="center" >职业称号</th>
		<th align="center" >专业专长</th>
		<th align="center" >证书类型</th>
		<th align="center" >证书编号</th>
		<th align="center" >审核电话</th>		
	</tr>
	<s:iterator id="custiterator"  value="page.dataList">
	<tr>
	    <td align="center" width="5%">
		    <input type="checkbox"  name="nmeids"  value='<s:property value="n_medicalMattersExclusive_id"/>' />
		</td>
		<td align="center">
		     <s:property value="name"/>
		</td>
		<td align="center">
		 <s:property value="theCity"/>
		</td>
		<td align="center">
		     <s:property value="theHospital"/>
		</td>
		<td align="center">
		     <s:property value="hospitalLevel"/>
		</td>
		<td align="center">
		     <s:property value="theDepartment"/>
		</td>
		<td align="center">
		     <s:property value="professionName"/>
		</td>
		<td align="center">
		 <s:property value="professionalExpertise"/>
		</td>
		<td align="center">
		     <s:if test="#custiterator.n_certificateType==0">医生资格证</s:if>
		     <s:if test="#custiterator.n_certificateType==1">医生执业证</s:if>
		</td>
		<td align="center">
		     <s:property value="certificateNumber"/>
		</td>
		<td align="center">
		     <s:property value="auditingPhone"/>
		</td>
	</tr>
	</s:iterator>
</table>

<table width="500"  align="right">
	<tr>
		<td>
			<s:set name="form_name"  value="'mdicalExcusieForm'"  scope="request"></s:set>
			<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
		</td>
	</tr>
</table>
</s:form>
</body>
</html>

