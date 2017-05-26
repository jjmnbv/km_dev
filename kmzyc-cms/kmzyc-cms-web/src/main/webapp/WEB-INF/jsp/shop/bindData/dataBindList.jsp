<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>窗口数据管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<link href="/etc/css/tpl.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
		<script src="/etc/js/dialog.js"></script>
		<script type="text/javascript"  src="/etc/js/pageCommon.js"></script>
	
	
		
		<script src="/etc/js/codemirror/codemirror.js"></script>
<script src="/etc/js/codemirror/xml.js"></script>
<script src="/etc/js/codemirror/fullscreen.js"></script>
<script src="/etc/js/codemirror/userdefined.js"></script>

	<link rel="stylesheet" href="http://jscss.kmb2b.com/resshop/style/default/common.css">
		<script >

		

        /** 关闭类目弹出层  **/
        function closeCategory(checkeds,windowId){
        	closeThis();
        
        		var shopI = $("#shopI").val();
        	//把类目选择项直接加入数据库
    
        	window.location.href="/shop/cmsWindowDataAction_saveCategoryData.do?checkeds="+checkeds+"&dataType=13&windowId="+windowId+"&shopI="+shopI;
        	
        	
        }
     
        
     
      
        /** 关闭排自定义数据弹出层  **/
        function closeOpenUserDefinedDataInfo(windowId){
            closeThis();
            var pageId = $("#pageId").val();
            var adminType = $("#adminType").val();
            window.location.href="/shop/cmsWindowDataAction_queryPageList.do?windowId="+windowId+"&pageId="+pageId+"&adminType="+adminType;
        }
      
        
       
        //修改自定义数据
        function updateByKey(windowDataId)
        {
        	var windowId=$("#windowId").val();
        	var pageId=$("#pageId").val();
        	var adminType = $("#adminType").val();
        	var shopI = $("#shopI").val();
           window.location.href="/shop/cmsWindowDataAction_gotoUpdateShopData.do?windowDataId="+windowDataId+"&shopI="+shopI;
        }
      
           //弹出自定义数据层
        function popUpUserdefine()
        {
        	var windowId=$("#windowId").val();
        //	var pageId = $("#pageId").val();
        	var adminType = $("#adminType").val();
        	var shopI = $("#shopI").val();
        	
        	// window.location.href="/shop/cmsWindowDataAction_bindData.do?windowId="+windowId+"&channelType=1&shopI="+shopI;
        	  window.location.href="/shop/cmsWindowDataAction_gotoBindData.do?windowId="+windowId+"&adminType="+adminType+"&shopI="+shopI;
        }
        
        //弹出类目窗口层
        function popUpcategory() {
        	var windowId=$("#windowId").val();
          var adminType = $("#adminType").val();
        	var shopI = $("#shopI").val();
           dialog("选择类目信息","iframe:/shop/shopCategoryAction_gotoShopCategory.do?windowId="+windowId+"&shopI="+shopI+"&adminType="+adminType ,"900px","530px","iframe");
            
          //   dialog("选择类目信息","iframe:/cms/categoryAction_findAllCategory.action?windowId="+windowId+"&adminType="+adminType ,"900px","530px","iframe");
        }
        
        
     
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
                 var shopI = $("#shopI").val();
                 var windowId=$("#windowId").val();
                  document.windowDataForm.action="/shop/cmsWindowDataAction_deleteShopData.do?shopI="+shopI+"windowId="+windowId;
                  document.windowDataForm.submit();
         }
    }
    
    
     /** 单条删除客户等级信息  **/
    function  deleteByKey(id){
         if(confirm("是否确认删除? ")==true){
           var shopI = $("#shopI").val();
           var windowId=$("#windowId").val();  
              
            window.location.href="/shop/cmsWindowDataAction_deleteShopData.do?dataId="+id+"&shopI="+shopI+"&windowId="+windowId;
             
         }
    }
    
      $(document).ready(function(){
	        var msg='<s:property value="msg"/>';
	        if(msg!=null&&msg!=""){
	          //  messageDialog("消息提示","text:"+msg ,"300px","102px","text");
	           alert(msg);
	           msg="";
	        }
 			
         });
		</script>
	</head>
	<body>
	<div style=" width: 950px;" class="ui-dialog-z">
    <div class="ui-dialog-z-header">
        <h4>数据绑定</h4>
         <!--
        <a style="display: block;" class="ui-dialog-z-close" title="关闭本框" href="javascript:;" data-role="close">×</a>
        -->
    </div>
    <div style="height: 100%;" class="ui-dialog-z-content" data-role="content">
            <div class="ui-tipbox ui-tipbox-success ui-tipbox-white">
            <s:form class="ui-form" name="windowDataForm"   onsubmit=" return checkAllTextValid(this)" 
			action="/shop/cmsWindowDataAction_gotoDataBindList.do" method="post">
			<s:token></s:token>
			<input type="hidden" name="adminType" id="adminType" value="<s:property value='adminType'/>">
			<input type="hidden" name="stylesId" id="stylesId" value="<s:property value='stylesId'/>">
			<%--<input type="hidden" name="channelType" id="channelType" value="<s:property value='channelType'/>">--%>
			<input type="hidden" name="shopI" id="shopI" value="<s:property value='shopI'/>">
               
                    <fieldset >
                        <div class="ui-tips mb20"><span class="ui-form-required"></span></div>
                        <div class="ui-tipbox-z-content">
                        <!--  
                            <div class="ui-form-item">
                                <label for="" class="ui-label-inline">数据类型</label>
                               <select name="queryType">
									<option value="">全部</option>
									<option value="13">店铺分类</option>
									<option value="6">自定义数据</option>
							
						   </select>
                                             
                            </div>
                            -->
                            <div class="ui-dialog-z-footer">
                                <a class="add-btn" href="javascript:void(0);" onclick="popUpUserdefine();" >添加自定义数据</a>
                                <a class="add-btn" href="javascript:void(0);" onclick="popUpcategory();">店铺分类</a>
                               
                                <input type="hidden" id="windowId" name="windowId" value="<s:property value='windowId' />">
					              <input type="hidden" id="pageId" name="pageId" value="<s:property value='pageId' />">
                           </div>
                            <table class="ui-table">
                                <thead>
                                    <tr>
                                        <th class="w15"><input type='checkbox' name='windowData'  onclick="checkAll(this,'windowDataIds');"></th>
                                        <th class="w180">数据名称</th>
                                        <th class="w180">自定义URL</th>
                                        <th class="w180">数据类型</th>
                                        <th class="w100">操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                   <s:iterator id="custiterator" value="page.dataList">
										<tr >
											<td width="5%">
												<input type="checkbox" name="windowDataIds" value="<s:property value='windowDataId'/>">
											</td>
											<td>
												<s:property value="dataName" />
											</td>
										  
											<td>
											
												   <s:property value="user_defined_url" />
											
											</td>
											<td>
												<s:if test="dataType==13">
													运营类目
												</s:if>
											
											
												<s:if test="dataType==6">
													自定义数据
												</s:if>
												
												<input type="hidden" id="dataType" name="dataTypes" value="<s:property value='dataType' />"/>
											</td>
											<!-- 
											<td>
												<input style="width: 40px;" type="text" data_windowDataId="<s:property value='windowDataId' />" onblur="updateSort(this);" value="<s:property value='sort' />" />
											</td>
											 -->
											<td>
											  <span class="ui-edit-btn">
												<s:if test="dataType==6">
												  <a href="javascript:void(0);" class="ui-page-item ui-page-item3 ui-page-item-next" onclick="updateByKey(<s:property value='windowDataId'/>)"></a>	
												</s:if>
												   <a href="javascript:void(0);" class="ui-page-item ui-page-item4 ui-page-item-next" onclick="deleteByKey(<s:property value='windowDataId'/>)" ></a>
												
											  </span>
											  </td>
										</tr>
									</s:iterator>
                                   
                                    
                                </tbody>
                            </table>
                            <!--  
                            <div class="clear p10">
                                <div class="ui-page ui-page-sm right">
                                    <a class="ui-page-item ui-page-disabled ui-page-item-prev" href="javascript:void(0)"></a>
                                    <a class="ui-page-item ui-page-item-current" href="javascript:void(0)">1</a>
                                    <a class="ui-page-item" href="javascript:void(0)">2</a>
                                    <a class="ui-page-item" href="javascript:void(0)">3</a>
                                    <a class="ui-page-item" href="javascript:void(0)">4</a>
                                    <a class="ui-page-item" href="javascript:void(0)">5</a>
                                    <a class="ui-page-item" href="javascript:void(0)">6</a>
                                    <a class="ui-page-item" href="javascript:void(0)">7</a>
                                    <span class="ui-page-text">...</span>
                                    <a class="ui-page-item" href="javascript:void(0)">40</a>
                                    <a class="ui-page-item ui-page-item-next" href="javascript:void(0)"></a>
                                    <span class="ui-page-item-info ui-page-text">跳转到</span>
                                    <span><input type="text" class="ui-page-number" title="" value="1" name="pageNo" id="inputPageNo"></span>
                                    <span><input type="button" value="GO" class="ui-page-item"></span>
                                </div>
                            </div>
-->
                        </div>
                    </fieldset>
               </s:form>
            </div>
    </div>
    <!-- 
    <div class="ui-dialog-z-footer">
        <a class="btn ui-btn-save">保存</a>
        <a class="btn ui-btn-cancel">取消</a>
    </div>
     -->
</div>
	</body>
</html>

