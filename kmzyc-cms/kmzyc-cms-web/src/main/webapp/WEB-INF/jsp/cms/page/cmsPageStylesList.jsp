<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@page import="com.pltfm.cms.parse.PathConstants"%>
<%@page import="java.io.File"%>
<html>
	<head>
		<title>风格列表</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<Script src="/etc/js/97dater/WdatePicker.js"></Script>
		<script type="text/javascript" src="/etc/js/pageCommon.js"></script>
		<script type="text/javascript" src="/etc/js/checkeds.js"></script>
		<script type="text/javascript" src="/etc/js/rowDisplay.js"></script>
		<script>
	$(document).ready(function(){
    		var checks = "";
    		checks = $("#checkeds").val();
    		if(checks!=""){
        		var checkboxs = document.getElementsByName("siteId");
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
	   });
      
	
	  /** 删除站点信息  */
   /* function deleteSelected(id){
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
                          document.cmsSiteForm.action="/cms/cmsSite_delCmsSite.action";
                          document.cmsSiteForm.submit();
                 }
    }*/
	function addgoto(){
	
       location.href="/cms/gotoStylesAdd.action";
    }
	
    /** 进入修改站点页面  **/
    function editcmsSite(id){
         location.href="/cms/cmsStylesAction_gotoStylesUpdate.action?stylesId="+id;
    }
      //详情
	function detail(siteId){
			location.href="/cms/cmsSite_gotoDetail.action?siteId="+siteId;
		}

     //授权
	function warrant(siteId){
	dialog("选择窗口","iframe:/sys/listSysUserPop.action?siteId="+siteId,"900px","530px","iframe");  
		}
	function closeOpenSiteDiv(){
      closeThis();
       
	}		
    function addgoto(){
        var pageNo=$("#pageNo").val();
         location.href="/cms/cmsStylesAction_gotoStylesAdd.action";
    }
         
    /** 单条删除站点信息  **/
    function  deleteByKey(id){
        if(confirm("是否确认删除? ")==true){
       
	 
	 	   location.href="/cms/cmsStylesAction_delStyles.action?stylesId="+id;   	
          
         }
    }
    function visualization(id)
		{
			window.location.href="/cms/cmsPageVisualizationAction_queryForPage.action?stylesId="+id;
		}

	function selStyles(id){
		$.ajax({
			url:"/cms/cmsPageAction_selStyles.action?ajax=yes",
			data:"stylesId="+id,
			type:"post",
			success:function(result){
				parent.stylesCloseOpenDialog(result);
			},
			error:function(){
				alert("出错了");
			}
		});
	}
	<%
	String imageOut = PathConstants.cmsPicPath();
	imageOut = imageOut+"/";
	%>
	var imageOut = "<%=imageOut%>";
	//查看图片
	function viewImage(imageName){	
		window.open(imageOut+imageName);
	}
	</script>
	</head>
	<body>
		<!-- 导航栏 -->
		<s:set name="parent_name" value="'风格管理'" scope="request" />
		<s:set name="name" value="'风格列表'" scope="request" />
		<jsp:include page="/WEB-INF/jsp/common/title.jsp"></jsp:include>
		<div style="height: 90%; overflow-y: scroll;">
			<s:form class="pageForm" name="cmsSiteForm"
				onsubmit="return checkAllTextValid(this)"
				action="/cms/cmsStylesAction_queryForPage.action" method="post">
				
				<table width="98%" align="center" height="90" border="0"
					class="content_table" cellpadding="0" cellspacing="0">
					<tr>
						<td width="60%" valign="middle" colspan="2">
							


							<input type="hidden" id="checkeds" name="checkeds"
								value="<s:property value='checkeds'/>" />
						</td>
					</tr>
					<td align="left" width="22%">
						风格编号：
						<input name="cmsStyles.stylesId" type="text"
							value="<s:property value="cmsStyles.stylesId"/>">
					</td>
					<td align="left" width="22%">
						风格名称：
						<input name="cmsStyles.stylesName" type="text"
							value="<s:property value="cmsStyles.stylesName"/>">
					</td>
					
					<td align="right">
						<INPUT TYPE="submit" class="queryBtn" value="">
					</td>

					</tr>
				</table>
				<table width="98%" class="list_table" cellpadding="3" align="center">
					<tr>
						<th width="5%">
							<input type='checkbox' name='siteIds'
								onclick="checkAll(this,'siteIds')">
						</th>
						<th>
							风格编号
						</th>
						<th>
							风格名称
						</th>
						<th>
							风格描述
						</th>
						<th>
							风格图片
						</th>
						<th>
							模板编号
						</th>
						
						<th >
					                       修改日期
						</th>
						<th >
						         修改人
						</th>
						<th>
							操作
						</th>
					</tr>
					<s:iterator value="page.dataList">
						<tr>
							<td width="5%">
								<input type="checkbox" name="siteIds"
									value='<s:property value="stylesId"/>'
									onclick="checkpromotionId(this);">
							</td>
							<TD>
								<s:property
										value="stylesId" />
								
							</TD>
							<TD>
								<s:property
										value="stylesName" />
								
							</TD>
							<TD>
								<s:property
										value="stylesDescribe" />
								
							</TD>
							<TD>
							<a href="<%=imageOut%><s:property value="remark"/>" target="_blank">
								<s:property value="remark"/>
							</a>
							</TD>
							<TD>
								<s:property
										value="templateId" />
								
							</TD>
							
							<td>
							<s:date name="modifyDate" format="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<s:property value="sysUserMap[modified]"/>
							</td>
							<td>	
								<img title="选择" style="cursor: pointer;" src="/etc/images/icon_select.png"   onclick='selStyles(<s:property value="stylesId"/>)' />
								<img title="查看图片" style="cursor: pointer;" src="/etc/images/icon_preview.png"   onclick='viewImage("<s:property value="remark"/>")' />
							</td>
						</tr>
					</s:iterator>
				</table>
				<table class="page_table" width="98%" align="center" cellpadding="0"
					cellspacing="0" border="0">
					<tr>
						<td>
							<s:set name="form_name" value="'cmsSiteForm'" scope="request"></s:set>
							<jsp:include page="/WEB-INF/jsp/common/page.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</s:form>
			<!-- 消息提示页面 -->
			<jsp:include page="/WEB-INF/jsp/common/message.jsp"></jsp:include>
		</div>
	</body>
</html>

