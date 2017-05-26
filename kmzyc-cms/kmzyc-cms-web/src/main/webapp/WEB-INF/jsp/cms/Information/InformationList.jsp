<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>文章内容列表</title>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
</head>
<body >
<!-- 导航栏 --> 
<script>
	var pageForm;
	  /** 删除文章信息  **/
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
                          document.userLevelForm.action="/cms/Information_All_Delete.action";
                          document.userLevelForm.submit();
                 }
    }
    //多条发布
     function publishSelected(id){
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
                        alert("请选择要发布的数据。");
                        return false;
                 }else if(confirm('是否确认发布?')==true){      
                          document.userLevelForm.action="/cms/Information_publishAll.action";
                          document.userLevelForm.submit();
                 }
    }
	function addgoto(id){
    	pageForm.action="/cms/Information_gotoAdd.action";
    	pageForm.submit();
    }
	
    /** 进入修改文章页面  **/
    function editUserLevel(id){
    var pageNo=$("#pageNo").val();
         location.href="/cms/Information_Byid.action?adminType=0&inforid="+id+"&pageNo="+pageNo;
    }
    
   function viewPage(path){
    	 window.open(path);
    }
    
       function adminEditUserLevel(id){
         //  var pageNo=$("#pageNo").val();
        // location.href="/cms/Information_Byid.action?inforid="+id+"&pageNo="+pageNo;
         pageForm.action="/cms/Information_Byid.action?inforid="+id;
    	 pageForm.submit();
    }
    
    /** 单条删除文章信息  **/ 
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
           location.href="/cms/Information_Delete.action?inforid="+id;
         }
    }
	function publishInformation(id){
	     location.href="/cms/Information_publish.action?inforid="+id;
	}
	 
	</script>
<style type="text/css">
.listTitle {
	height: 40px;
	line-height:40px;
	background: #D6F2D9;
	border-bottom: 1px solid #079346;
	vertical-align: middle;
	font-size: 14;
	color: #028043;
	margin:0px;
}
.listTitle span{
 padding-left:20px;
 height:30px;
 line-height:30px;
 vertical-align: middle;
 margin-top:5px;
}
.listTitle span img{
   vertical-align: middle;
}
</style>
<!-- 导航栏 -->
<s:set name="parent_name" value="'基础功能'" scope="request" />
<s:set name="name" value="'文章列表'" scope="request" />
<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
<div  style="height:90%;overflow-y:scroll; " >
  <s:form id="ADV_queryPageList" name="userLevelForm" onsubmit="return checkAllTextValid(this)" action="/cms/Information_List.action" method="post">
    <s:token></s:token>
    <input type="hidden" id="pageNo_keyWords" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
    <input type="hidden" id="pageSize_keyWords" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
    <!-- 查询条件 -->
    <table width="98%" align="center" height="90" border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
      <tr>
        <td width="60%" valign="middle" colspan="2"><s:if test="adminType==0">
            <input name="adminType" type="hidden"   value="0"/>
          </s:if>
          <input class="addBtn" TYPE="button" value="" onClick="addgoto(<s:property value="adminType"/>
          );">&nbsp;&nbsp;
          <input class="delBtn" type="button" value="" onClick="deleteSelected('levelId');">
          <input type="button" value="多条发布" onClick="publishSelected('levelId');">
          <input type="hidden" id="checkeds" name="checkeds" value="<s:property value='checkeds'/>"/></td>
      </tr>
      <tr width="60%">
        <td align="left" width="25%"> 文章标题：
          <input name="keyWords.name_keyword" type="text" value="<s:property value="keyWords.name_keyword"/>
          " /> </td >
        <td align="left" width="25%"> 文章类型：
          <input type="hidden" id="Hid" value="<s:property value="infor.typeId"/>
          "/>
          <select name="keyWords.type_keyword" id="infortype">
            <option value="1">***
            <option>
          </select></td>
        <td align="right" >
        <td align="left" width="25%"> 输出路径：
          <input name="keyWords.outPath_keyword"  value="<s:property value="keyWords.outPath_keyword"/>
          "/> </td>
        <td align="right" >
        <td align="left" width="40%"> 发布状态：
          <select name="keyWords.status_keyword" id="infor.status">
            <option value="" 
            <s:if test="infor.status==null">selected="selected"</s:if>
            >全部
            </option>
            <option value="0" 
            <s:if test="infor.status==0">selected="selected"</s:if>
            >已发布
            </option>
            <option value="1" 
            <s:if test="infor.status==1">selected="selected"</s:if>
            >未发布
            </option>
          </select></td>
        <td align="right" ><INPUT TYPE="submit" class="queryBtn" value=""></td>
      </tr>
    </table>
    <!-- 数据列表区域 -->
    <table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1">
      <tr>
        <th width="5%"> <input type='checkbox' name='level'  onclick="checkAllpop(this,'levelId')">
        </th>
        <th> 编号 </th>
        <th> 类型 </th>
        <th> 标题 </th>
        <th> 输出路径 </th>
        <th>状态<!--0.有效1.无效--> </th>
        
        <!--	<th>
						排序
					</th>  -->
        
        <th > 发布日期 </th>
        <th > 修改日期 </th>
        <th > 修改人 </th>
        <th width="80"> 操作 </th>
      </tr>
      <s:iterator  value="page.dataList">
        <tr>
          <td width="5%"><input type="checkbox" name="levelId"
								value='<s:property value="InforId"/>' onClick="checkpromotionId(this);"></td>
          <td><s:property value="InforId"/></td>
          <td><s:property value="inforname"/></td>
          <td><a href="/cms/Information_Byid.action?pageNo=1&inforid=<s:property value="InforId"/>" >
            <s:property value="name"/>
            </a></td>
          <td><s:property value="content"/></td>
          <td><s:if test="status==0">已发布</s:if>
            <s:if test="status==1">未发布</s:if></td>
          
          <!-- 	<td><s:property value="orders" />
					     	
						</td> -->
          
          <td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
          <td><s:date name="modifyDate" format="yyyy-MM-dd HH:mm:ss"/></td>
          <td><s:property value="sysUserMap[modified]"/></td>
          <td><s:if test="status==1"> <img title="发布" style="cursor: pointer;" src="/etc/images/icon_publish.png"  onclick="publishInformation(<s:property value="InforId"/>)" /> </s:if>
            <s:if test="status==0"> <img title="预览" style="cursor: pointer;" src="/etc/images/icon_detail.png"   onclick="viewPage('<s:property value="publishPath"/>
              <s:property value="content"/>
              ')" /> </s:if>
            <img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png" 
            <s:if test="adminType==0"> onclick="editUserLevel(
              <s:property value="InforId"/>
              )"</s:if>
            <s:else>onclick="adminEditUserLevel(
              <s:property value="InforId"/>
              )"</s:else>
            /> <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value="InforId"/>)" /> </td>
        </tr>
      </s:iterator >
    </table>
    <table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
      <tr>
        <td>&nbsp;
          <s:set name="form_name" value="'userLevelForm'" scope="request"></s:set>
          <jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include></td>
      </tr>
    </table>
  </s:form>
  
  <!-- 消息提示页面 -->
  <jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
  
  <!-- 消息提示 -->
  <div width="100%"> </div>
</div>
<script>
$(document).ready(function(){
				var timer_alert = setTimeout(function() {
				messageCloseThis();
			}, 2000);
	var hid=$("#Hid").val();
	    $.ajax({
        url:'Information_Add_ajax.action',
        type:'post',
        dataType:'json',
        success:function (d) {
         var li='';
            if(hid==''){
              li=li+"<option selected='selected' value=''>全部</option>";
            }else{
              li=li+"<option value=''>全部</option>";
            }
           $.each(d.inforlist, function (index, item) {

            if(item.id==hid){
           li+="<option selected='selected' value="+item.id+">"+item.name+"</option>";
           }else{
           li+="<option value="+item.id+">"+item.name+"</option>";
          }
          });
          $("#infortype").html(li);
        }});
    		var checks = "";
    		checks = $("#checkeds").val();
    		if(checks!=""){
        		var checkboxs = document.getElementsByName("levelId");
        		var myarr = new Array();
    			myarr = checks.split(',');
        		for(var i = 0; i<checkboxs.length; i++){
            		for(var j = 0; j<myarr.length; j++){
                		if(checkboxs[i].value==myarr[j]){
                			checkboxs[i].checked = true;
                			break;
                    	}
                	}
            	}
        	}
        	
    	pageForm= window.document.getElementById("ADV_queryPageList");
	   });
		
		</script>
</body>
</html>
