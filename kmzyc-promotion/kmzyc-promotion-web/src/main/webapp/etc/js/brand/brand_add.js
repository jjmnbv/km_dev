$(document).ready(function() {
	$('.ct:gt(0)').hide();
	var hdw = $('ul li');
	hdw.click(function() {
		$(this).addClass('visit').siblings().removeClass();
		var hdw_index = hdw.index(this);
		$('.ct').eq(hdw_index).show().siblings().hide();
	});
	
	hdw.hover(
	  function () {
	    $(this).addClass("hover");
	  },
	  function () {
	    $(this).removeClass("hover");
	  }
	);
});

function gotoList() {
	location.href = "/basedata/prodBrandShow.action";
}

$(function(){
	$("#brandForm").validate({
		rules: {
			"brandName":{required:true,maxlength:50,unusualChar:true,checkBrandName:true},
			"chnSpell":{required:true,maxlength:16,unusualChar:true,checkEnglish:true},
			"homepage":{unusualChar:true,checkHttp:true},
			"engName":{unusualChar:true,checkEnglish:true},
			//"logoFile":{required:true,maxlength:50,checkFile:true},
			"des":{maxlength:500,unusualChar:true},
			"remark":{maxlength:200,unusualChar:true}
	    	},
		success: function (label){
			label.removeClass("checked").addClass("checked");
		}
   });
	
	
	jQuery.validator.addMethod("checkBrandName", function(value, element) {
		var flag = true;
		$.ajax({
				async:false,
				url:"/basedata/findRepeatName.action",
				type:"post",
				data:{name:value},
				success:function(data){
					if(data>0){
						element.select();
						flag = false;
					}
				}
		});
		return flag;
	}, "该品牌名已存在，请重新命名！");
});

function changeLogoPath(arg) {
	document.getElementById("logoPath").value = arg.value;
	$("#messages").text("");
}

function changePavilionPath(arg){
	document.getElementById("pavilionPath").value = arg.value;
}

function changeIntroducePath(arg){
	document.getElementById("introducePath").value = arg.value;
}

function changeCertificateFilesPath(arg){
	$(arg).siblings("input[type='hidden'][name='certificateFilesPath']").val(arg.value);
}

function addFileRow(){
	var _row = '<p><input type="file" name="certificateFiles" onchange="changeCertificateFilesPath(this)" /><INPUT class="btnStyle" TYPE="button" value="删除" onclick="deleteFileRow(this.parentNode);"><input type="hidden" name="certificateFilesPath" value="" /></p>';
	$("#certificateFilesRow").append(_row);
}
function addTextRow(){
	var _row = '<p><input type="text"" name="contactTypes" value="" size="5" />：<input type="text"" name="contactValues" value="" size="50" />&nbsp;<INPUT class="btnStyle" TYPE="button" value="删除" onclick="deleteFileRow(this.parentNode);"></p>';
	$("#textRow").append(_row);
}

function deleteFileRow(arg){
	$(arg).remove();
}

function submitBrandAddForm(){

	 var intro = $("#editor_id").val(),
     editor_change = $('.editor_change');
	 
	 var rows;
		$.ajax({
			async : false,
			url : "/app/checkIntroduce.action?checkedIntro=" + intro,
			type : "POST",
			dataType : "json",
			success : function(json) {
				rows = json;
			}
		});
		
		if (rows == 'wrong')
		{
			alert("请输入文明语言");
			return false;
		}
	 
	 editor_change.html(intro);
	
    $.each(editor_change.find('img'),function(i){
        var t = $(this),
            src = t.attr('src');
        t.attr('data-original',src);
        t.attr('src','http://jscss.kmb2b.com/res/images/default__logo.png');
        t.addClass('lazy');

    });
    
    $("#editor_lazy").val(editor_change.html());
    
    $("#brandForm").submit();
}