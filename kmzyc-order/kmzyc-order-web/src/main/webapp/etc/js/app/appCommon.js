function openShadeDiv(){
	var wnd = jQuery(window);
	var doc = jQuery(document);
	var wHeight=0;
	var wWidth=0;
	//当高度少于一屏
    if(wnd.height()>doc.height()){
     	wHeight = wnd.height();
     	wWidth = wnd.width();
    }else{
    	//当高度大于一屏
     	wHeight = doc.height();
     	wWidth = doc.width();
    }
	//创建遮罩背景
	jQuery("body").append("<div id=shadeDiv></div>");
	jQuery("body").find("#shadeDiv")
	    .width(wWidth)
	    .height(wHeight)
	    .css(
	    	{position:"absolute",
	    	top:"0px",left:"0px",
	    	background:"#ccc",
	    	filter:"Alpha(opacity=50);",
	    	opacity:"0.3",zIndex:"10000"
	    	});
}

function openLoadingDiv(promptMsg){
	var promptMsg = promptMsg ? promptMsg : '数据处理中';
	var strDiv="<div id='loadImgDiv' style='background-color: #ffffe1;z-Index:10001;border:1px solid;border-color: silver;width:240px;position: absolute;left:"+(document.body.clientWidth-420)/2+";top:"+(document.body.clientHeight-240)/2+"'>"+
				"<table align='center'>"+
					"<tr>"+
						"<td>"+
							"<img style='height:22px;width:22px' src='/etc/images/red-waiting.gif'/>"+
						"</td>"+
						"<td style='font-size: 15px;'>"+
							 promptMsg+
						"</td>"+
					"</tr>"+
				"</table>"+
			"</div>";
	//jQuery("select:enabled").attr("disabled","disabled").attr("removeDisabled","true");
	openShadeDiv();
	jQuery("body").append(strDiv);
}


function closeLoadingDiv(){
	//jQuery("select[removeDisabled]").removeAttr("disabled").removeAttr("removeDisabled");
	jQuery("#shadeDiv").remove();
	jQuery("#loadImgDiv").remove();
}