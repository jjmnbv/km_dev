var dialogFirst=true;

function dialog(title,content,width,height,cssName){
	if(dialogFirst==true){
	  var temp_float=new String;
	  temp_float="<div id=\"floatBoxBg\" style=\"height:"+$(document).height()+"px;filter:alpha(opacity=0);opacity:0;\"></div>";
	  temp_float+="<div id=\"floatBox\" class=\"floatBox\">";
	  temp_float+="<div class=\"title\"><h4></h4><span><img src='../etc/images/del16.gif' title='关闭'/></span></div>";
	  temp_float+="<div class=\"content\"></div>";
	  temp_float+="</div>";
	  $("body").append(temp_float);
	  dialogFirst=false;
	}

	$("#floatBox .title span").click(function(){
	  //$("#floatBoxBg").animate({opacity:"0"},"normal",function(){$(this).hide();});
	  //$("#floatBox").animate({top:($(document).scrollTop()-(height=="auto"?300:parseInt(height)))+"px"},"normal",function(){$(this).hide();}); 
		$("#floatBoxBg").hide({opacity:"0"});//animate({opacity:"0"},"normal",function(){$("#floatBoxBg").hide();});
		$("#floatBox").hide({opacity:"0"});
	});

	$("#floatBox .title h4").html(title);
	contentType=content.substring(0,content.indexOf(":"));
	content=content.substring(content.indexOf(":")+1,content.length);
	switch(contentType){
	  case "url":
	  var content_array=content.split("?");
	  $("#floatBox .content").ajaxStart(function(){
	    $(this).html("loading...");
	  });
	  $.ajax({
	    type:content_array[0],
	    url:content_array[1],
	    data:content_array[2],
		error:function(){
		  $("#floatBox .content").html("error...");
		},
	    success:function(html){
	      $("#floatBox .content").html(html);
	    }
	  });
	  break;
	  case "text":
	  $("#floatBox .content").html(content);
	  break;
	  case "id":
	  $("#floatBox .content").html($("#"+content+"").html());
	  break;
	  case "iframe":
	  $("#floatBox .content").html("<iframe id=\"iframe_floatBox\" src=\""+content+"\" width=\"99%\" height=\""+(parseInt(height)-30)+"px"+"\" scrolling=\"auto\" frameborder=\"0\" marginheight=\"0\" marginwidth=\"0\"></iframe>");
	}

	$("#floatBoxBg").show();
	$("#floatBoxBg").animate({opacity:"0.3"},"normal");
	$("#floatBox").attr("class","floatBox "+cssName);
	$("#floatBox").css({display:"block",left:(($(document).width())/2-(parseInt(width)/2))+"px",top:($(document).scrollTop()-(height=="auto"?300:parseInt(height)))+"px",width:width,height:height});
	$("#floatBox").animate({top:"1px"},"normal"); 
	}

	function closeThis(){
		$("#floatBox").hide("slow");
		$("#floatBoxBg").hide("slow");
	}

var dialogFlag=true;
	
function messageDialog(title,content,width,height,cssName){
if(dialogFlag==true){
  var temp_float=new String;
  temp_float="<div id=\"messageBoxBg\" style=\"height:"+$(document).height()+"px;filter:alpha(opacity=0);opacity:0;\"></div>";
  temp_float+="<div id=\"messageBox\" class=\"messageBox\">";
  temp_float+="<div class=\"title\"><h4></h4><span><img src='../etc/images/del16.gif' title='关闭'/></span></div>";
  temp_float+="<div class=\"content\"></div>";
  temp_float+="</div>";
  $("body").append(temp_float);
  dialogFlag=false;
}

$("#messageBox .title span").click(function(){
  $("#messageBoxBg").animate({opacity:"0"},"normal",function(){$(this).hide();});
  $("#messageBox").animate({top:($(document).scrollTop()-(height=="auto"?300:parseInt(height)))+"px"},"normal",function(){$(this).hide();}); 
});

$("#messageBox .title h4").html(title);
contentType=content.substring(0,content.indexOf(":"));
content=content.substring(content.indexOf(":")+1,content.length);
switch(contentType){
  case "url":
  var content_array=content.split("?");
  $("#messageBox .content").ajaxStart(function(){
    $(this).html("loading...");
  });
  $.ajax({
    type:content_array[0],
    url:content_array[1],
    data:content_array[2],
	error:function(){
	  $("#messageBox .content").html("error...");
	},
    success:function(html){
      $("#messageBox .content").html(html);
    }
  });
  break;
  case "text":
  $("#messageBox .content").html(content);
  break;
  case "id":
  $("#messageBox .content").html($("#"+content+"").html());
  break;
  case "iframe":
  $("#messageBox .content").html("<iframe id=\"iframe_messageBox\" src=\""+content+"\" width=\"99%\" height=\""+(parseInt(height)-30)+"px"+"\" scrolling=\"auto\" frameborder=\"0\" marginheight=\"0\" marginwidth=\"0\"></iframe>");
}

$("#messageBoxBg").show();
$("#messageBoxBg").animate({opacity:"0.3"},"normal");
$("#messageBox").attr("class","messageBox "+cssName);
$("#messageBox").css({display:"block",left:(($(document).width())/2-(parseInt(width)/2))+"px",top:($(document).scrollTop()-(height=="auto"?300:parseInt(height)))+"px",width:width,height:height});
$("#messageBox").animate({top:"1px"},"normal"); 
}

function messageCloseThis(){
	$("#messageBox").hide("slow");
	$("#messageBoxBg").hide("slow");
}

