//提现审核
function checkEnchashment(status){
	if(status != null || status != ""){
		 $("#enchashmentStatus").val(status);
		 var confir="";
		 if(status==1){
			 confir="确认通过当前申请吗？";
		 }
		 if(status==2){
			 confir="确认拒绝当前申请吗？";
		 }
		 if(confirm(confir)){
			 var remark=$.trim($("#remarks").val());
			 if(remark==""){
					alert("请填写取现审核备注");
			 } else {
				 $("#buttonTrue").attr('disabled',"true");
				 $("#buttonFalse").attr('disabled',"true");
				 document.bnesAcctEnchashmentForm.submit();
			 }
		 }
	}
}

//返回列表页面
function gotoBack(){
	if($('#fromConfirmMenu') && $('#fromConfirmMenu').val()=='yes'){
		location.href="/accounts/bnesAcctEnchashment_PageList.action?bnesAcctEnchashment.enchashmentStatus=3&fromConfirmMenu=yes";
	}else{
		location.href="/accounts/bnesAcctEnchashment_PageList.action";
	}
}


function afterCreateHandler(){
    var doc = this.edit.doc; 
    var cmd = this.edit.cmd; 
    //console.log(navigator.userAgent.toLowerCase());
    //Paste in chrome
    if(KindEditor.WEBKIT){
        $(doc.body).bind('paste', function(ev){
        	var original =  ev.originalEvent;
            var file =  original.clipboardData.items[0].getAsFile();
            var reader = new FileReader();
            reader.onload = function (evt) {
                var result = evt.target.result; 
                var arr = result.split(",");
                var base64Data = arr[1];//图片base64数据
                var imgSuffix = arr[0].split(";")[0].split(":")[1].split('/')[1];//图片类型
                $.ajax({
					async:false,
					url:"/accounts/bnesAcctEnchashment_uploadPasteImg.action",
					data:{"map['imgBase64']":base64Data,"map['imgSuffix']":imgSuffix},
					type:'POST',
					dataType:'text',
					success:function(data){
						html = '<img src="' + data + '" alt="" />';
                        cmd.inserthtml(html);
                    }
                 }); 
            };
            reader.readAsDataURL(file);
        });
    }
    // Paste in firfox. IE11
    if(KindEditor.GECKO){
    	KindEditor(doc.body).bind('paste', function(ev){
            setTimeout(function(){
                var html = KindEditor(doc.body).html();
                var imgDataReg = /\"(data:image[^\"]*)\"/mg;
                if(html.search(imgDataReg) > -1){
                	var imgData = html.match(imgDataReg)[0];
                	console.log('img:'+imgData);
                	var arr = imgData.split(",");
                     var base64Data = arr[1];
                     var imgSuffix = arr[0].split(";")[0].split(":")[1].split('/')[1];//图片类型
                     $.ajax({
							async:false,
							url:"/accounts/bnesAcctEnchashment_uploadPasteImg.action",
							data:{"map['imgBase64']":base64Data,"map['imgSuffix']":imgSuffix},
							type:'POST',
							dataType:'text',
							success:function(data){
								var newHtml = html.replace(imgDataReg,"'" + data + "'");
								confirmEditor.html(newHtml);
	                        }
                      }); 
                }
            }, 100);
        });
    } 
}

//给出字符数提示.
//var confirmValBefore='';
function afterChangeHandler(){
      var limitNum = 500;  //设定限制字数,自动截取
      var tipInfo ='你还可以输入 <span style="color:red;font-weight:bold">'+(limitNum-this.count())+'</span>个字符'
      if(limitNum-this.count()>=0){
          $('#confirmMarksTip').html(tipInfo);
      }else{
    	  tipInfo ='输入的信息已超出 <span style="color:red;font-weight:bold">' + Math.abs(limitNum-this.count()) + '</span>个字符'
    	  $('#confirmMarksTip').html(tipInfo);
      }
      
      //$('#confirmMarksCount').text(limitNum-this.count());
	  /*  限制字符输入，待完善
		  if(this.count()>limitNum && confirmValBefore!= confirmEditor.text()){
			 confirmValBefore = confirmEditor.text();
			 $('#confirmMarksWarn').text('提现备注信息超过允许输入的最大字符数，\n\n请删除部分备注信息,否则无法保存！')
   	  }*/
}


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


