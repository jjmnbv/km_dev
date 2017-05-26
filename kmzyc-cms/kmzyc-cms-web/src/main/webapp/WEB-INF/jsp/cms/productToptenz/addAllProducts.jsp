<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<%@page import="com.pltfm.cms.parse.PathConstants"%>
<%@page import="java.io.File"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/etc/js/jquery-latest.pack.js"></script>
<script src="/etc/js/dialog.js"></script>
<script type="text/javascript"  src="/etc/js/validate/jquery.validate.js"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/artDialog.js?skin=default" type="text/javascript"></script>
<script language="JavaScript" src="/etc/js/artDialog4.1.7/plugins/iframeTools.source.js" type="text/javascript"></script>
<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="http://jscss.kmb2b.com/resshop/style/default/common.css">

</head>
<body>
<!-- 导航栏 -->
<style type="text/css">
 
</style>
<!-- 导航栏 -->
<div class="ui-dialog-z" style=" width: 650px;">
      
   <div class="ui-dialog-z-header">
<h4>全部商品</h4>

</div>
    <div data-role="content" class="ui-dialog-z-content" style="height: 50%;">
        <div class="ui-tipbox-z-content">
            <div class="ui-tipbox ui-tipbox-success ui-tipbox-white">
                <form id="frm" action="addAllProducts.do" method="post" name="" class="ui-form">
                   <input type="hidden"  name="shopI" value="<s:property value='shopI' />">
                <input type="hidden"  name="cmsWindowData.windowId" value="<s:property value='windowId' />">
                 
                <input type="hidden"  name="cmsWindowData.windowDataId" value="<s:property value='cmsWindowData.windowDataId' />">
                <input type="hidden"  name="cmsShopData.shopDataId" value="<s:property value='cmsShopData.shopDataId' />">
                  <input type="hidden"  name="cmsShopData.windowId" value="<s:property value='windowId' />">
                    <fieldset>
           
                        <div class="ui-form-item">
                            <label class="ui-label" for="">排序方式</label>
                            <div class="ui-form-explain">
                                <select class="ui-select165">
                                    <option value="默认">默认</option>
                                </select>
                            
                            
                            </div>
                        </div>
                        <div class="ui-form-item">
                            <label class="ui-label" for="">展示商品数</label>
                            <div class="ui-form-explain">
                                <select class="ui-select165" name="cmsShopData.lineNumber" id="lineNumber">
                                    <option value="4" <s:if test="cmsShopData.lineNumber==4" >selected='selected'</s:if>>4</option>
                                    <option value="8" <s:if test="cmsShopData.lineNumber==8" >selected='selected'</s:if>>8</option>
                                    <option value="12" <s:if test="cmsShopData.lineNumber==12" >selected='selected'</s:if>>12</option>
                                    <option value="16" <s:if test="cmsShopData.lineNumber==16" >selected='selected'</s:if>>16</option>
                                    <option value="20" <s:if test="cmsShopData.lineNumber==20" >selected='selected'</s:if>>20</option>
                                    <option value="24" <s:if test="cmsShopData.lineNumber==24" >selected='selected'</s:if>>24</option>
                                    <option value="28" <s:if test="cmsShopData.lineNumber==28" >selected='selected'</s:if>>28</option>
                                    <option value="32" <s:if test="cmsShopData.lineNumber==32" >selected='selected'</s:if>>32</option>
                                    <option value="36" <s:if test="cmsShopData.lineNumber==36" >selected='selected'</s:if>>36</option>
                                    <option value="40" <s:if test="cmsShopData.lineNumber==40" >selected='selected'</s:if>>40</option>
                                </select>
                            </div>
                        </div>
                        
                        <div class="ui-form-item">
                        <label class="ui-label" for=""> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                            <div class="ui-form-explain">
                                
                            
                                <s:if test="cmsWindowData.user_defined_url == 1">
                                <span class="ui-tpis"><input type="checkbox" value="1" name="cmsWindowData.user_defined_url" checked ="true">显示栏目标题</span>
                                </s:if>
                                <s:else>
            		  <span class="ui-tpis"><input type="checkbox" value="1" name="cmsWindowData.user_defined_url" >显示栏目标题</span>
				</s:else>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
    <div class="ui-dialog-z-footer">
       <a class="btn ui-btn-save" TYPE="button" onclick="javascript:submit();" >保存</a>
        <a class="btn ui-btn-cancel" TYPE="button" onclick="javascript:closeWin();">取消</a>
    </div>
</div>

<script type="text/javascript">
var result='<s:property value='result' />';

   if(result=='success'){
	alert("保存成功！");
	closeWin();
   }
   function submit(){
	   $("#frm").submit();

	}
   function showErrorMessage(checkId,title,afterId){
		var t = $("#"+checkId).val();
		if(t){
			$("#error"+checkId).remove();
			return true;
		}
		showError(checkId,title,afterId);
		return false;
	}
	function showError(errorId,title,afterId){
		if($("#error"+errorId).length>0){
			return;
		}
		var html = '<label for="error" id="error'+errorId+'" generated="true" class="error">'+title+'</label>';
		var id = "#"+afterId;
		$(id).after(html);
	}
	
 	//关闭弹出窗口
 	function stylesCloseOpenDialog(content)
 	{
 		closeThis();
 		var json = eval('(' + content + ')'); 
 		editor.setValue(json.content);
 		$("#templateId").val(json.templateId);
 		$("#stylesId").val(json.stylesId);
 		$("#preview").attr("src",imageOut+json.remark);
 		$("#previewHref").attr("href",imageOut+json.remark);
 		$("#preDiv").show();
    }
    
    //返回
   function gotoList(id){
  	var pageForm= window.document.getElementById("addPageForm");
 	pageForm.action="/cms/cmsPageAction_queryForPage.action";
 	pageForm.submit();
  }

   function closeWin() {
		try {
		     		document.domain = 'kmb2b.com';
		            window.frameElement.trigger('close');
		        } catch (error) {
		            window.console && console.log('error',error);
		        }
	    window.opener=null;

	    window.open('','_self');

	    window.close();

	}
</script>
</BODY>
</HTML>