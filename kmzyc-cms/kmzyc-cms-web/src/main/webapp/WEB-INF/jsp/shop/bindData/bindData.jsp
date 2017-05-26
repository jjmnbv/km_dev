<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.pltfm.sys.util.StaticParams"%>
<html>
	<head>
		<title>窗口数据管理</title>
		<link href="/etc/css/style_app.css" type="text/css" rel="stylesheet">
		<link href="/etc/css/opendiv-normal.css" rel="stylesheet" type="text/css" />
		<!-- <link href="/etc/css/tpl.css" rel="stylesheet" type="text/css" /> -->
		<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>

		 <script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
        <script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
		<script src="/etc/js/dialog.js"></script>
    	
		<link rel="stylesheet" href="http://jscss.kmb2b.com/resshop/style/default/common.css?3">


	
	</head>
	<body>
	
	<div style="width:650px;" class="ui-dialog-z">
    <div class="ui-dialog-z-header">
        <h4>自定义关键字</h4>
        <!-- 
        <a style="display: block;" class="ui-dialog-z-close" title="关闭本框" href="javascript:;" data-role="close">×</a> -->
    </div>
    <s:form  name="pageForm" id="pageForm" action="/shop/cmsWindowDataAction_saveShopData.do" method="POST" enctype="multipart/form-data" >
					<s:token></s:token>
    <div class="ui-dialog-z-content" data-role="content">
        <div class="ui-tipbox-z-content">
            <div class="ui-tipbox ui-tipbox-success ui-tipbox-white">
                
                
                	
					<input type="hidden" id="windowId" name="windowId" value="<s:property value='windowId' />">
					<input type="hidden" id="pageId" name="pageId" value='<s:property value="pageId"/>'>
					<input type="hidden" id="adminType" name="adminType" value='<s:property value="adminType"/>'>
					<%--<input type="hidden" id="channelType" name="channelType" value='<s:property value="channelType"/>'>--%>
					<input type="hidden" name="shopI" id="shopI" value="<s:property value='shopI'/>">
					<input type="hidden" name="checkeds" id="checkeds" value="<s:property value='checkeds'/>" >
					<input type="hidden" name="dataType" id="dataType"  value="13">
					
                     <div id="list_table" >
                        
                        <s:if test="cmsWindowDatas == null || cmsWindowDatas.size  < 1 ">
                           <div class="ui-form-item" id="tabdiv">
                            <label for="" class="ui-label-inline">一级关键字</label>
                             <input type='hidden'  name='cmsWindowDatas[0].windowDataId' value="<s:property value='cmsWindowData.windowDataId' />">
                            <input type='hidden'  name='cmsWindowDatas[0].windowId' value="<s:property value='windowId' />">
                            <input type='hidden'  name='cmsWindowDatas[0].user_defined_type' value='0'>
                            <input type='hidden'  name='cmsWindowDatas[0].sort' value="0">
                            <input type="text" class="textkeyword ui-input mr20"  name="cmsWindowDatas[0].user_defined_name" value="<s:property value='cmsWindowData.user_defined_name' />">
                            <label for="" class="ui-label-inline">链接</label>
                            <input type="text" class="ui-input ui-input280"  name="cmsWindowDatas[0].user_defined_url"  value="<s:property value='cmsWindowData.user_defined_url' />">
                          
                        </div>
                      
                        
                     
	                        <div class="ui-form-item" id="tabdiv">
	                            <label for="" class="ui-label-inline">二级关键字</label>
	                             <input type='hidden'  name='cmsWindowDatas[1].user_defined_type' value='0'>
	                             <input type='hidden'  name='cmsWindowDatas[1].windowId' value="<s:property value='windowId' />">
	                             <input type='hidden'  name='cmsWindowDatas[1].sort' value="1">
	                            <input type="text" class="textkeyword ui-input mr20"  name="cmsWindowDatas[1].user_defined_name">
	                            <label for="" class="ui-label-inline">链接</label>
	                            <input type="text" class="ui-input ui-input280"  name="cmsWindowDatas[1].user_defined_url">
	                            <!-- 
	                            <span class="ui-edit-btn right">
	                                        <a href="#">- 删除</a>
	                            </span>
	                            
	                          -->
	                        </div>
	                        
                       
                        </s:if>
                        <s:else>
                             <s:iterator value="cmsWindowDatas" status="index" id="cmsWindowData"> 
                                  <s:if test="#cmsWindowData.sort== 0">
                                     <div class="ui-form-item" id="tabdiv">
			                            <label for="" class="ui-label-inline">一级关键字</label>
			                             <input type='hidden'  name='cmsWindowDatas[<s:property value="#index.index" />].windowDataId' value="<s:property value='#cmsWindowData.windowDataId' />">
			                            <input type='hidden'  name='cmsWindowDatas[<s:property value="#index.index" />].windowId' value="<s:property value='windowId' />">
			                            <input type='hidden'  name='cmsWindowDatas[<s:property value="#index.index" />].user_defined_type' value='0'>
			                            <input type='hidden'  name='cmsWindowDatas[<s:property value="#index.index" />].sort' value="0">
			                            <input type="text"  class="textkeyword ui-input mr20"  name="cmsWindowDatas[<s:property value="#index.index" />].user_defined_name" value="<s:property value='#cmsWindowData.user_defined_name' />">
			                            <label for="" class="ui-label-inline">链接</label>
			                            <input type="text" class="ui-input ui-input280"  name="cmsWindowDatas[<s:property value="#index.index" />].user_defined_url"  value="<s:property value='#cmsWindowData.user_defined_url' />">
			                          
			                        </div>
                                  </s:if>
                                  <s:else>
                                       
					                        <div class="ui-form-item" id="tabdiv">
					                            <label for="" class="ui-label-inline">二级关键字</label>
					                             <input type='hidden'  name='cmsWindowDatas[<s:property value="#index.index" />].user_defined_type' value='0'>
					                              <input type='hidden'  name='cmsWindowDatas[<s:property value="#index.index" />].windowDataId' value="<s:property value='#cmsWindowData.windowDataId' />">
					                             <input type='hidden'  name='cmsWindowDatas[<s:property value="#index.index" />].windowId' value="<s:property value='windowId' />">
					                             <input type='hidden'  name='cmsWindowDatas[<s:property value="#index.index" />].sort' value="1">
					                             <input type="text"  class="textkeyword ui-input mr20"  name="cmsWindowDatas[<s:property value="#index.index" />].user_defined_name" value="<s:property value='#cmsWindowData.user_defined_name' />">
			                                <label for="" class="ui-label-inline">链接</label>
			                                   <input type="text" class="ui-input ui-input280"  name="cmsWindowDatas[<s:property value="#index.index" />].user_defined_url"  value="<s:property value='#cmsWindowData.user_defined_url' />">
					                             
					                            <span class="ui-edit-btn right">
					                     
					                            <s:if test="#cmsWindowData.windowDataId== null">
					                                 <a href='#' onclick='delTr(this)'>- 删除</a>
					                            </s:if>
					                            <s:else>
					                             <a href='#' onclick='deleteByKey(<s:property value='#cmsWindowData.windowDataId' />)'>- 删除</a>
					                            </s:else>
					                                       
					                            </span>
					                            
					                         
					                        </div>
	                        
                                       
                                  </s:else>
                             
                             </s:iterator>
                        
                        </s:else>
                        
                         </div>
                        
                        <div class="ui-form-explain">
                           <div class="ui-tips"><span class="ui-form-required">*</span>可添加多个二级关键字。</div><span class="ui-edit-btn right">
                                        <a href="#" value="" onclick="addRowsFun()"  id="addBtn">+ 添加</a>   
                                    </span>
                        </div>
                        <div class="ui-form-item">
                            <label class="ui-label" for="">关联店内分类</label>
                            <div class="ui-form-explain">
                                <a href="javascript:void(0)" class="ui-widget-btn v-a-m"><span onclick="popUpcategory();">选择分类</span></a>
                                                                                      已关联分类:<span class=""><s:property value='shopCategoryStr' /></span>
                            </div>
                        </div>
                        
                        
                      
              
            </div>
        </div>
    </div>
    <div class="ui-dialog-z-footer">
        <a class="btn ui-btn-save" onclick="onsave();">保存</a>
        <a class="btn ui-btn-cancel" onclick="closeWindow ();">取消</a>
    </div>
         </s:form>	
</div>
		
	     
           
	</body>
	<script type="text/javascript">
	
	  $(document).ready(function(){
	        var msg='<s:property value="msg"/>';
	        if(msg!=null&&msg!=""){
	          //  messageDialog("消息提示","text:"+msg ,"300px","102px","text");
	           alert(msg);
	           msg="";
	        }
 			
         });
	      /** 关闭类目弹出层  **/
        function closeCategory(checkeds,windowId){
        	closeThis();
        
        		var shopI = $("#shopI").val();
        	//把类目选择项直接加入数据库
        	$("#checkeds").val(checkeds);
        	$("#dataType").val("13");
        	
    
        	//window.location.href="/shop/cmsWindowDataAction_saveCategoryData.do?checkeds="+checkeds+"&dataType=13&windowId="+windowId+"&shopI="+shopI;
        	
        	
        }
	
	   //弹出类目窗口层
        function popUpcategory() {
        	var windowId=$("#windowId").val();
          var adminType = $("#adminType").val();
        	var shopI = $("#shopI").val();
           dialog("选择类目信息","iframe:/shop/shopCategoryAction_gotoShopCategory.do?windowId="+windowId+"&shopI="+shopI+"&adminType="+adminType ,"550px","530px","iframe");
            
          //   dialog("选择类目信息","iframe:/cms/categoryAction_findAllCategory.action?windowId="+windowId+"&adminType="+adminType ,"900px","530px","iframe");
        }
        
	
		function closeWindow(){
	try {
				document.domain = 'kmb2b.com';
            	window.frameElement.trigger('close');
	        } catch (error) {
	            window.console && console.log('error',error);
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
    
	
	function onsave(){
	//	 var defined_name=$("#defined_name0").val();
		
	
		
		var pageForm= window.document.getElementById("pageForm");
		var shopI=$('#shopI').val();
		var windowId=$('#windowId').val();
	  
	    var checkeds=$('#checkeds').val();
	    var dataType=$('#dataType').val();
	 
	 

	 	  var isFalse = true;
	  	  $("#list_table .ui-form-item").each(function(i){
	  	     var t = $(this);
	  	  	var isTrue = '.textkeyword'; 
	  	  	var turl = ".ui-input280";
	  	  	var urlstring = t.find(turl).val();
	  	  var pattern = /^(http):\/\/([a-zA-Z0-9]*)\.(kmb2b)\.(com)(\/)*.*$/;
	  	  	    //t.find("input[type='text']")
	  		  /*   if($.trim(t.find(isTrue).val()) == ""){ 
	  		   //  console.log("tttt:"+t.find("input[type='text']").length);
			     	alert('关健字不能为空！');
			     	isFalse = false;
			     	return isFalse;
			      }
			      */
			   //    console.log("textkeyword:"+t.find(isTrue).val());
			      if(t.find(isTrue).val().length>6){
			      	alert('关健字超过6个字！');
			     	isFalse = false;
			     	return isFalse;
			      }else if(urlstring != "" && !pattern.test(urlstring)){
			    	  alert('请填写完整的康美商城站内链接！');
			    	  isFalse = false;
			    	  return isFalse;
			      }
			      
	  		});
			
		if(isFalse){
		   pageForm.action="/shop/cmsWindowDataAction_saveShopData.do?shopI="+shopI+'&windowId='+windowId+'&checkeds='+checkeds+'&dataType='+dataType;
		
		  $('#pageForm').submit();	 
		}
		
   }

		var num=0;
function delTr(obj)
		{
			var tab=document.getElementById("list_table");
			var tabLength=$("#list_table div").length;
			var tr = obj.parentNode.parentNode;
			var rowNum=tr.childNodes[0].innerHTML;
	  		$(obj).parent().parent().remove();
			for(i=rowNum;i<tabLength;i++)
			{
				//修改编号
				tab.rows[Number(i)-1].cells[0].innerHTML=tab.rows[Number(i)-1].cells[0].innerHTML-1;
				
				
				//修改类型下标
				var datatype=document.getElementById("datatype"+(Number(i)+1));
				//datatype.setAttribute("id", "datatype"+i);
				datatype.id="datatype"+i;
				//datatype.setAttribute("name", "cmsWindowDatas["+i+"].user_defined_type");
				datatype.name="cmsWindowDatas["+i+"].user_defined_type";
				
				//修改名称下标
				var datatext=document.getElementById("datatext"+(Number(i)+1));
				datatext.id="datatext"+i;
				datatext.name="cmsWindowDatas["+i+"].user_defined_name";
				//修改URL下标
				var dataurl=document.getElementById("dataurl"+(Number(i)+1));
				dataurl.id="dataurl"+i;
				dataurl.name="cmsWindowDatas["+i+"].user_defined_url";
				
				//修改图片下标
				var picurl=document.getElementById("picurl"+(Number(i)+1));
				picurl.id="picurl"+i;
			}
		//	num=Number(tabLength)-1;
		//	$("#num").html(num);
		}
		
		
// 添加一行
var _len=0;
function addRowsFun() {
	 _len = $("#list_table div").length;
	 console.log("len:"+_len);
	 var windowId=document.getElementById("windowId").value;
	$("#list_table").append(
					"<div class='ui-form-item' id='tabdiv'>"
					+" <input type='hidden'  name='cmsWindowDatas["+(Number(_len)+1)+"].sort' value="+(Number(_len)+1)+">"
					+" <input type='hidden'  name='cmsWindowDatas["+(Number(_len)+1)+"].user_defined_type' value='0'>"
					+"<input type='hidden'  name='cmsWindowDatas["+(Number(_len)+1)+"].windowId' value="+windowId+">"   
					+" <label for='' class='ui-label-inline'>二级关键字</label>"
					+" <input type='text' id=datatext"+(Number(_len)+1)+" name='cmsWindowDatas["+(Number(_len)+1)+"].user_defined_name' class='textkeyword ui-input mr20' />"
					+" <label for='' class='ui-label-inline'>链接</label>"
					+"<input class='ui-input ui-input280'  type='text' id='datatext" +(Number(_len)+1)+"' name='cmsWindowDatas["+(Number(_len)+1)+"].user_defined_url'/>"
					+"<span class='ui-edit-btn right'><a href='#' onclick='delTr(this)'>- 删除</a></span>"
					+"</div>"
					);
			var addBtn=document.getElementById("addBtn");
		//	addBtn.focus();
			//$("#num").html(Number(_len)+1);
}
function disPic(obj)
{
	var tr = obj.parentNode.parentNode;
	var rowNum=tr.childNodes[0].innerHTML;
	if(obj.value==0)
	{
		document.getElementById("picurl"+rowNum).setAttribute("disabled","disabled");
	}
	else if(obj.value==1)
	{
		document.getElementById("picurl"+rowNum).removeAttribute("disabled");
	}
}
function check()
{
	var tab=document.getElementById("list_table");
	var tabLength=$("#list_table tr").length;
	for(i=1;i<=tabLength;i++)
	{
		var objname = document.getElementById("datatext"+i);
		if(objname.value==null||objname.value=='')
		{
			objname.focus();
			alert("数据名称不能为空.");
			return false;
		}
		var objtype = document.getElementById("datatype"+i);
		if(objtype.value==1)
		{
			var objurl = document.getElementById("picurl"+i);
			if(objurl.value==null||objurl.value=='')
			{
				objurl.focus();
				alert("请选择图片.");
				return false;
			}
		}
	}
	return true;
}
</script>
</html>

