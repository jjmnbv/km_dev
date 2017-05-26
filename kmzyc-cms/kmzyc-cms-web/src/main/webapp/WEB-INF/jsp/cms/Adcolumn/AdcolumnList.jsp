<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>广告位管理</title>
	    <link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/etc/js/autocomplete2/jquery.autocomplete.css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		 <script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
		<script type='text/javascript' src='/etc/js/autocomplete2/jquery.autocomplete.js'></script>
	</head>
	<body >
		<!-- 导航栏 -->
	<script>
	var pageForm;
	$(document).ready(function(){
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
        	
        	
        	
        	var adcolumnArray;
        	$.ajax({
        		async:'false',
        		url:'Adv_Add_ajax.action',
        		success:function(data){
        		//   data=62:xxx;
        			adcolumnArray = $.map(data, function (value,key) { 
        				return { value:value, data:key };
        				
        			});
        		//	product_add_suppliersArray = suppliersArray;
        			$("#adcolumnName").autocomplete(adcolumnArray, {
						minChars: 0,
						max:50,
						formatItem: function(row, i, max, term) {
							return row.value;
						}
					});
        			
        		},
        		error: function(XMLHttpRequest, textStatus, errorThrown){
        			//alert("aaa:"+XMLHttpRequest+"...."+textStatus+"...."+errorThrown);
        			},
        		dataType:'json'
        	});
        	
        	
        	
        	
        	
        	
        	
        	
        	
	   });
      
	
	  /** 删除广告位信息  */
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
                          document.userLevelForm.action="/cms/Adcolumn_All_Delete.action";
                          document.userLevelForm.submit();
                 }
    }
	function addgoto(){
	
       //location.href="/cms/GotoAdcolumnAdd.action"
       pageForm.action="/cms/Adcolumn_gotoAdd.action";
 	   pageForm.submit();
    }
	
    /** 进入修改广告位页面  **/
    function editUserLevel(id){
        var pageNo=$("#pageNo").val();
         location.href="/cms/Adcolumn_Byid.action?adminType=0&Advid="+id+"&pageNo="+pageNo;
    }
    function adminEditUserLevel(id){
        //var pageNo=$("#pageNo").val();
         //location.href="/cms/Adcolumn_Byid.action?Advid="+id+"&pageNo="+pageNo;
         pageForm.action="/cms/Adcolumn_Byid.action?Advid="+id;
 	    	pageForm.submit();
         }
    /** 单条删除客户等级信息  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
           $.ajax({
        url:'Adcolumn_ByAdId.action',
        type:'post',
        data:{"Advid":id},
        success:function (d) {
        if(d=="0"){
          location.href="/cms/Adcolumn_Delete.action?Advid="+id;   
        }else{
        	   var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>此广告位下有广告，如需删除请先清除此广告位下广告。 ";
               messageDialog("消息提示","text:"+msg ,"300px","102px","text");
        }
        }
     });
         }
    }
	
	 
	</script>	
		
		

<style type="text/css">
.listTitle {
	height: 40px;
	line-height:40px;
	background: #c7e3f1;
	
	vertical-align: middle;
	font-size:13;
	color: #222222;
	margin-bottom: 20px;
}
.listTitle span {
    padding-left: 20px;
    height: 40px;
    vertical-align: middle;
    margin: 0px;
}
.listTitle span img{
   vertical-align: middle;
}
.content_table{
	background-color: #f5f5f5;
    border: 1px solid #e3e3e3;
    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.05);
    -moz-box-shadow: inset 0 1px 1px rgba(0,0,0,0.05);
    box-shadow: inset 0 1px 1px rgba(0,0,0,0.05);
    overflow: hidden;
    
    padding: 6px;

	
	}
</style>
<!-- 导航栏 -->
<div width="100%" class="listTitle"  height="40">
    <span>
	<img src="/etc/images/icon_02.png"  />&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;基础功能&nbsp;
		<img src="/etc/images/icon_08.png" />&nbsp;广告位管理
		
		
	  </span>
</div>
		<div  style="height:90%;overflow-y:scroll; " >
		        
<s:form  id="ADV_queryPageList" name="userLevelForm" onsubmit="return checkAllTextValid(this)" action="/cms/Adcolumn_queryAdcolumn.action" method="post">
			<s:token></s:token>
			<input type="hidden" id="pageNo_keyWords" name="keyWords.pageNo" value="<s:property value='keyWords.pageNo'/>">
			<input type="hidden" id="pageSize_keyWords" name="keyWords.pageSize" value="<s:property value='keyWords.pageSize'/>"/>
			<!-- 查询条件 -->
		    <table width="98%" align="center"  border="0"	class="content_table"
			cellpadding="0" cellspacing="0">
				<tr>
				
					<td valign="middle" colspan="2">
					
					</td>
				</tr>
				<tr >
					<td >
			广告位名称：<input name="keyWords.name_keyword" type="text" id="adcolumnName" value="<s:property value="keyWords.name_keyword"/>" />
			</td >
			<td >
			 输出路径：<input name="keyWords.outPath_keyword" type="text" 	value="<s:property value="keyWords.outPath_keyword"/>"/>
					</td >
					<td >
						有效性:
						<select name="keyWords.status_keyword" id="adcolumnId">
						<option value="" <s:if test="cmsAdcolumn.status==null">selected="selected"</s:if>>全部</option> 
						<option value="0"<s:if test="cmsAdcolumn.status==0">selected="selected"</s:if>>有效</option>
						<option value="1" <s:if test="cmsAdcolumn.status==1">selected="selected"</s:if>>无效</option>
						</select>
                     
					</td>
					<td align="right" >
						<INPUT TYPE="submit" class="queryBtn" value="">
                        	<s:if test="adminType==0">
	<input name="adminType" type="hidden"   value="0"/>
	</s:if>
	                 <s:if test="adminType!=0">
						<input class="addBtn" TYPE="button" value="" onClick="addgoto();">&nbsp;&nbsp;
						
						<input class="delBtn" type="button" value=""
							onclick="deleteSelected('levelId');">
							</s:if>
							<input type="hidden" id="checkeds" name="checkeds" value="<s:property value='checkeds'/>"/>
					</td>
				</tr>
			</table>
			<!-- 数据列表区域 -->
			<table width="98%" class="list_table" cellpadding="3" align="center"
				cellspacing="0" border="1">
				<tr>
					<th width="5%">
						<input type='checkbox' name='level'  onclick="checkAllpop(this,'levelId')">
					</th>
					<th>
						编号
					</th>
					<th>
						广告位名称
						
					</th>
					<th>
						输出路径
					</th>
					<!--
					<th>
						广告类型 0.图片1.Flash 
					</th>
					<th>
						高度
					</th>
					<th>
						宽度
					</th>
					--><th>状态<!--0.有效1.无效-->
					</th><!--
					<th>
						创建日期
					</th>
					<th width="80">
						创建人
					</th>
					--><th >
					         修改日期
					</th>
					<th >
					         修改人
					</th>
					<th width="60">
					      操作
					</th>
				</tr>
				<s:iterator  value="page.dataList">
					<tr>
						<td width="5%">
							<input type="checkbox" name="levelId"
								value='<s:property value="adcolumnId"/>' onClick="checkpromotionId(this);">
								
						</td>
						<td>
						<s:property value="adcolumnId"/>
						</td>
						<td>
							<s:property value="name"/>
						</td>
						<td>
							<s:property value="output"/>
						</td>
						<!--<td>
							
							
						    <s:if test="type==0">图片</s:if>
						    <s:if test="type==1">flash</s:if>
						</td>
						<td>
						    <s:property value="height"/>px
						</td>
						<td>
						    <s:property value="width"/>px
						</td>
						--><td>
						   
						    <s:if test="status==0">有效</s:if>
						    <s:if test="status==1">无效</s:if>
						</td><!--
						<td>
					     	<s:property value="createDate"/>
						</td>
						<td>
								<s:property value="created"/>
						</td>-->
						<td>
							<s:date name="modifyDate" format="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<s:property value="sysUserMap[modified]"/>
						</td>
						<td>  
						     <img title="修改" style="cursor: pointer;" src="/etc/images/icon_modify.png"  
						     <s:if test="adminType==0">onclick="editUserLevel(<s:property value="adcolumnId"/>)"</s:if>
						     <s:else>onclick="adminEditUserLevel(<s:property value="adcolumnId"/>)"</s:else>  />
						    <s:if test="adminType!=0"> <img title="删除" style="cursor: pointer;" src="/etc/images/icon_delete.png"   onclick="deleteByKey(<s:property value="adcolumnId"/>)" />
						     </s:if>
						</td>
					</tr>
				
				</s:iterator > 
				
					
				
			</table>
			<table class="page_table" width="98%" align="center" cellpadding="0"
				cellspacing="0" border="0">
				<tr>
					<td>

						<s:set name="form_name" value="'userLevelForm'" scope="request"></s:set>
						<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
					</td>
				</tr>
			</table>
		</s:form >



	<!-- 消息提示页面 -->
		<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>


<div width="100%">

		 
	              
</div>
		</div>
	</body>
</html>

