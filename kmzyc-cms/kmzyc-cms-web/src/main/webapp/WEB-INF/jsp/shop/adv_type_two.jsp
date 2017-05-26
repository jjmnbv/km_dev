<%@page contentType="text/html;charset=UTF-8"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>店铺装修模版页-图片广告</title>
<meta name="keywords" content="店铺模版"/>
<meta name="description" content="店铺模版"/>
<script type="text/javascript"  src="/etc/js/jquery-1.8.3.js"></script>
 <Script src="/etc/js/97dater/WdatePicker.js"></Script> 
 <script type="text/javascript"  src="/etc/js/jquery.validate.js"></script>
<script type="text/javascript"  src="/etc/js/jquery.metadata.js"></script>
<script type="text/javascript"  src="/etc/js/messages_cn.js"></script>
<script type="text/javascript"  src="/etc/js/checkeds.js"></script>
<script src="/etc/js/dialog.js"></script>

<link rel="stylesheet" href="http://jscss.kmb2b.com/resshop/style/default/common.css">
<style>
	label.error{
	  color: red;
	}
</style>
</head>

<body>

<div class="ui-dialog-z" style=" width: 650px;">
    <div class="ui-dialog-z-header">
        <h4>图片广告</h4>
          <!-- 
        <a data-role="close" href="javascript:;" title="关闭本框" class="ui-dialog-z-close" style="display: block;">×</a>
         -->

    </div>
    	<s:form name="dateForm" id="dateForm" action="/shop/cmsWindowDataAction_savaWindowPic.do"  method="POST" enctype="multipart/form-data" >
		<s:token></s:token>
    <div data-role="content" class="ui-dialog-z-content" style="height: 100%;">
        <div class="ui-tipbox-z-content">
            <div class="ui-tipbox ui-tipbox-success ui-tipbox-white">
                <form id="" action="#" method="post" name="" class="ui-form">
                    <fieldset>
                        <div class="ui-form-item">
                            <label class="ui-label" for="">栏目标题</label>
                            <div class="ui-form-explain">
                                <input type="text" placeholder="" class="ui-input ui-input280" id="defined_name0" name="cmsWindowDatas[0].user_defined_name" value="<s:property value="cmsWindowDatas[0].user_defined_name"/>">
                                
                                  	<input type="hidden" name="cmsWindowDatas[0].windowDataId" value="<s:property value="cmsWindowDatas[0].windowDataId"/>"/>	
                            <input type="hidden" name="cmsWindowDatas[0].user_defined_type" value="1"/>
                            
                             <input type="hidden" name="cmsWindowDatas[0].dataType" value="6"/>
                             <input type="hidden" name="cmsWindowDatas[0].windowId" value="<s:property value="cmsWindow.windowId"/>"/>
                             <input type="hidden" name="cmsWindowDatas[0].siteId" value="<s:property value="cmsWindow.siteId"/>"/>
                             <input type="hidden" name="cmsWindowDatas[0].sort" value="1"/>
                             
                             <input type="hidden" name="cmsWindowDatas[1].user_defined_name" id="defined_name1">
                             <input type="hidden" name="cmsWindowDatas[1].windowDataId" value="<s:property value="cmsWindowDatas[1].windowDataId"/>"/>	
                            <input type="hidden" name="cmsWindowDatas[1].user_defined_type" value="1"/>
                             <input type="hidden" name="cmsWindowDatas[1].dataType" value="6"/>
                             <input type="hidden" name="cmsWindowDatas[1].windowId" value="<s:property value="cmsWindow.windowId"/>"/>
                             <input type="hidden" name="cmsWindowDatas[1].siteId" value="<s:property value="cmsWindow.siteId"/>"/>
                             <input type="hidden" name="cmsWindowDatas[1].sort" value="2"/>
                             
                                 <input type="hidden" name="picType" value="<s:property value="picType"/>">
                               <input type="hidden" name="windowId"  id="windowId"  value="<s:property value="windowId"/>">
                                <input type="hidden"  id="shopI"  name="shopI" value="<s:property value="shopI"/>">
                                
                                <span class="ui-tpis"><input type="checkbox" <s:if test="cmsWindowDatas[0].status==0">checked</s:if> id="flag" value="0" name="dataIds">显示栏目标题</span>
                            </div>
                        </div>
                        <div class="ui-form-item">
                           <!--   <label class="ui-label" for="">图片设置</label>
                              <div class="ui-tips">支持jpg、png格式，尺寸：宽度485 高度250</div>
                           <div class="ui-form-explain">
                                <label class="ui-label">宽度485 高度250</label>
                                <label><span>高度600</span> <input type="text" value="600" class="ui-input ui-input280" style=" width: 66px;">
                                    px</label>

                            </div>-->
                        </div>
                        <div class="ui-tab">
                            <div class="ui-tab-trigger">
                                <ul class="ui-tab-user" id="pictab">
                                    <li class="ui-tab-user-item ui-tab-user-item-current" id="tab1">
                                        <a href="javascript:void(0);">图片1</a>
                                    </li>
                                    <li class="ui-tab-user-item" id="tab2">
                                        <a href="javascript:void(0);">图片2</a>
                                    </li>
                                    <!-- 
                                    <span class="ui-edit-btn right">
                                        <a href="#">+ 添加</a>
                                        <a href="#">- 删除</a>
                                    </span>
                                     -->
                                </ul>
                            </div>
                            <div class="ui-tab-cnt">
                                <div class="ui-tab-cnt-item"  id="pictab1">
                                    <div class="ui-form-item">
                                        <label class="ui-label" for="">上传图片</label>
                                        <span class="ui-form-explain">
                                      <!--    <a class="ui-widget-btn" href="javascript:void(0)"><span>浏览</span></a> 未选择文件-->
                                         <input type="file" name="resumefile0" id="resumefile0" onchange="javascript:setImagePreview('resumefile0','preview0','localImag0');" >  
                                        </span>
                                    </div>
                                       <div class="ui-form-item">
                                        <label class="ui-label" for="">预览图</label>
                                      
                                         <div class="ui-form-explain" id="localImag0"><img id="preview0" <s:if test="cmsWindowDatas[0].user_defined_picpath!=null"> width="300px" height="120px"</s:if> style="diplay:none" src="<s:if test="cmsWindowDatas[0].user_defined_picpath!=null"><s:property value="cmsWindowDatas[0].user_defined_picpath"/></s:if><s:else>/etc/resshop/images/template/u508.png</s:else>" > </div>
                                    </div>
                                    <div class="ui-form-item">
                                        <label class="ui-label" for="">链接地址</label>
                                        <input type="text" placeholder="" class="ui-input ui-input280" name="cmsWindowDatas[0].user_defined_url"  value="<s:property value="cmsWindowDatas[0].user_defined_url"/>">
                                    </div>
                                      <s:if test="cmsWindowDatas[0].windowDataId!=null">
                          
                                    <div class="clear"><span class="shop-collect t-c right"> <a  onclick="delPic(<s:property value='cmsWindowDatas[0].windowDataId'/>)">删除数据</a></span></div>
                              </s:if>
                                 
                                </div>
                                <div class="ui-tab-cnt-item" style="display: none;"  id="pictab2">
                                    <div class="ui-form-item">
                                        <label class="ui-label" for="">上传图片</label>
                                        <span class="ui-form-explain">
                                      <!--    <a class="ui-widget-btn" href="javascript:void(0)"><span>选择宝贝</span></a> 未选择文件 -->
                                           <input type="file" name="resumefile1" id="resumefile1" onchange="javascript:setImagePreview('resumefile1','preview1','localImag1');" >
                                         </span>
                                    </div>
                                     <div class="ui-form-item">
                                        <label class="ui-label" for="">预览图</label>
                                        <div class="ui-form-explain" id="localImag1"><img id="preview1" <s:if test="cmsWindowDatas[1].user_defined_picpath!=null"> width="300px" height="120px"</s:if> style="diplay:none" src="<s:if test="cmsWindowDatas[1].user_defined_picpath!=null"><s:property value="cmsWindowDatas[1].user_defined_picpath"/></s:if><s:else>/etc/resshop/images/template/u508.png</s:else>" > </div>
                                    </div>
                                    <div class="ui-form-item">
                                        <label class="ui-label" for="">链接地址</label>
                                        <input type="text" placeholder="" class="ui-input ui-input280"  name="cmsWindowDatas[1].user_defined_url"  value="<s:property value="cmsWindowDatas[1].user_defined_url"/>">
                                    </div>
                                      <s:if test="cmsWindowDatas[1].windowDataId!=null">
                          
                                    <div class="clear"><span class="shop-collect t-c right"> <a  onclick="delPic(<s:property value='cmsWindowDatas[1].windowDataId'/>)">删除数据</a></span></div>
                              </s:if>
                                
                                </div>
                            </div>
                        </div>
                    </fieldset>
                </form>
           
              <div class="ui-dialog-z-footer">
        <a class="btn ui-btn-save" onclick="onsave();">保存</a>
        <a class="btn ui-btn-cancel" onclick="closeWindow();">取消</a>
    </div>
            </div>
        </div>
    </div>
  
       </s:form>
             <!-- 消息提示页面 -->
			
	        <script type="text/javascript">
	        $(document).ready(function(){
	        var msg='<s:property value="msg"/>';
	        if(msg!=null&&msg!=""){
	          //  messageDialog("消息提示","text:"+msg ,"300px","102px","text");
	           alert(msg);
	           msg="";
	        }
 			
         });
	            </script>  
</div>
<script>

function delPic(dataId){
	 var shopI=$("#shopI").val();
	$.ajax({
		url:'/shop/cmsWindowDataAction_deletePic.do?dataId='+dataId+'&shopI='+shopI,
		type:'get',
		dataType:'jsonp',
		jsonp:'jsoncallback',
		success:function(json){
			if(json.code==200){
				alert("操作成功!");
				
			}else{
				alert("操作失败!");
			}	
		//	$('#dateForm').attr('action','');
		//	window.location.reload();
		closeWindow();
		}
		
	});
}

function onsave(){
	
	 var pageForm= window.document.getElementById("dateForm");
	$("#defined_name1").val($("#defined_name0").val());
	var shopI=$('#shopI').val();
		var windowId=$('#windowId').val();
		
		
			
			var falg=$("#flag").is(":checked");
	var definedame=$("#defined_name0").val();
	
	var resumefile0=$("#resumefile0").val();
	var resumefile1=$("#resumefile1").val();
		var preview0=$("#preview0").attr("src");
	var preview1=$("#preview1").attr("src");
	
	
		if(resumefile0==""&&resumefile1==""&&
	preview0=="/etc/resshop/images/template/u508.png"&&preview1=="/etc/resshop/images/template/u508.png"){
	       alert("请上传图片！");
			return ;
	}
	 if(resumefile0!=""){
       var type0=resumefile0.substring(resumefile0.lastIndexOf("\.")+1,resumefile0.length);
        if (type0.toUpperCase()!="GIF" && type0.toUpperCase()!="JPG" && type0.toUpperCase()!="PNG"&& type0.toUpperCase()!="JPEG"){
            alert("第一张图片格式不对！");
            return ;
        }
         
    }
    if(resumefile1!=""){
       var type1=resumefile1.substring(resumefile1.lastIndexOf("\.")+1,resumefile1.length);
        if (type1.toUpperCase()!="GIF" && type1.toUpperCase()!="JPG" && type1.toUpperCase()!="PNG"&& type1.toUpperCase()!="JPEG"){
            alert("第二张图片格式不对！");
            return ;
        }   
    }
	
	if(falg){
		if(definedame==''){
			alert("已选中显示栏目标题，请填写栏目标题！");
			return ;
		}
	}
	
	
	
	pageForm.action="/shop/cmsWindowDataAction_savaWindowPic.do?shopI="+shopI+'&windowId='+windowId;
	$('#dateForm').submit()

	/*
	
	$("#defined_name1").val($("#defined_name0").val());
	   var formParam =$("#dateForm").serialize();
	$.ajax({
		url:'/shop/cmsWindowDataAction_savaWindowPic.do',
		type:'post',
		 dataType: 'jsonp',
         jsonp: 'jsoncallback',
		data:formParam,
		success:function(json){
			
			if(json.code==200){
				alert("操作成功!");
				
			}else{
				alert("操作失败!");
			}	
			closeWindow();
		}
		
	});
	*/
}

function closeWindow(){
	try {
				document.domain = 'kmb2b.com';
            	window.frameElement.trigger('close');
	        } catch (error) {
	            window.console && console.log('error',error);
	        }
}

$(function(){
	 $("#pictab li").click(function(){
	 
	    $(this).addClass("ui-tab-user-item-current").siblings().removeClass("ui-tab-user-item-current");
		  if(this.id=="tab2"){
			 $("#pictab2").show();
			 $("#pictab1").hide();
		  }else{
			   $("#pictab1").show();
			 $("#pictab2").hide();
		  }
	  
   });
	 
	 
	
});


$(function(){
	   $("#dateForm").validate({
               rules: {
					"cmsWindowDatas[0].user_defined_name": {compareTo:true,maxlength:10},
					"cmsWindowDatas[0].user_defined_url":{checks:true},
					"cmsWindowDatas[1].user_defined_url":{checks:true}
					
				}
        });
	   
	   jQuery.validator.addMethod("checks", function(value, element) { 
	 	      
		   var pattern = /^(http):\/\/([a-zA-Z0-9]*)\.(kmb2b)\.(com)(\/)*.*$/;
			  if(value == "" || pattern.test(value)){
				  return true;
			  }else{
				  return false;
			  }		
				
		    },"请填写完整的康美商城站内链接！"); 
   
    
     jQuery.validator.addMethod("compareTo", function(value, element) {  
    	      var msg=true;
			   var shopI=$("#shopI").val();
			   $.ajax({                   
				url:'/shop/cmsWindowDataAction_checkKeywords.do?shopI='+shopI,
				type:'Post',
				 dataType:'jsonp',
		         jsonp:'jsoncallback',
				data:'keywords='+value,
				async:false,
				success:function(json){
					if(json.code==200){
					    msg=json.returnObject;				
					}	
				}
				
				
			});
			   if(msg){	
					return false;
							
				}else{
							
					return true;
				}	
	     
	    },"栏目标题不能敏感词"); 
     
      jQuery.validator.addMethod("keyWords", function(value, element) {  
    	      var msg=true;
			   $.ajax({                   
				url:'/shop/cmsWindowDataAction_checkKeywords.do',
				type:'Post',
				 dataType:'jsonp',
		         jsonp:'jsoncallback',
				data:'keywords='+value,
				async:false,
				success:function(json){
					if(json.code==200){
					    msg=json.returnObject;				
					}	
				}
				
				
			});
			   if(msg){	
					return false;
							
				}else{
							
					return true;
				}	
	     
	    },"链接地址不能敏感词"); 
});

function setImagePreview(resumefile,preview,localImag) {
        var docObj=document.getElementById(resumefile);
 
        var imgObjPreview=document.getElementById(preview);
                if(docObj.files &&    docObj.files[0]){
                        //火狐下，直接设img属性
                        imgObjPreview.style.display = 'block';
                        imgObjPreview.style.width = '300px';
                        imgObjPreview.style.height = '120px';                    
                        //imgObjPreview.src = docObj.files[0].getAsDataURL();
      //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式  
      imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);

                }else{
                        //IE下，使用滤镜
                        docObj.select();
                        var imgSrc = document.selection.createRange().text;
                        var localImagId = document.getElementById(localImag);
                        //必须设置初始大小
                        localImagId.style.width = "300px";
                        localImagId.style.height = "120px";
                        //图片异常的捕捉，防止用户修改后缀来伪造图片
                try{
                                localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                                localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
                        }catch(e){
                                alert("您上传的图片格式不正确，请重新选择!");
                                return false;
                        }
                        imgObjPreview.style.display = 'none';
                        document.selection.empty();
                }
                return true;
        }
</script>


</script>

</body>
</html>
