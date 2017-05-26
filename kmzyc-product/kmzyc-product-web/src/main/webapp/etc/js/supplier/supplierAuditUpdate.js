function deleteFiles(codId) {
	var answer = confirm("确认删除吗?");
	
	if(answer == true){
    	 $.ajax({
             url: 'deleteFile.action',
             async:false,
             data: 'scId='+codId,
             success: function(info) {
                if('0' == info){
                	$("#hrefId"+codId).remove();
                	//$("#del"+codId).remove();
                }else{
                	alert("删除失败！");
                }
             }
         });
	}
	}

/**
 * 验证文件类型
 * @returns {boolean}
 */
function checkFile() {
    var filepath = $("#upFile").val();
    if(null!=filepath && "" != filepath){
        var extStart = filepath.lastIndexOf(".");
        var ext = filepath.substring(extStart, filepath.length).toUpperCase();
        if (ext != ".BMP" && ext != ".PNG" && ext != ".GIF" && ext != ".JPG" && ext != ".JPEG") {
            $("#upFile").val("");
            alert("图片限于bmp,png,gif,jpeg,jpg格式");
            return false;
        }else{
            return true;
        }
    }else {
        alert("请选择图片");
        return false;
    }
}

/**
 * 提交上传资质文件表单
 *
 */
function submitForms() {
	 //先验证文件名称和类型
	console.log($('#frm').length,$('#frm1').length);
    var realName = $("#fileName").val();//用户输入的名称
    if (realName.length < 1) {
        alert("资质文件名称不能为空!");
        return;
    }
    var flag = checkFile();
    if (flag) {//验证通过
        var options = {
            url: "uploadCeriticate.action",
            type: "POST",//提交方式
            dataType: "json",//数据类型
            clearForm: true,// clear all form fields after successful submit
            success: function (json) {
                callback(json);
            },
            error: function(){
            	 $("#error_message").text("上传文件出错");
            }
        };
        //上传图像时，不可以点击保存按钮
//        disableSaveButton();
       // $("#loading_img").css("display", "");//显示上传中图标
        $("#frm1").ajaxSubmit(options);
    }
}

/**
 * ajaxForm上传供应商资质文件后,回调函数
 * @param json
 */
function callback(json){
	var imgPath=$("#imagePath").val();
	var fileObj=$("#addFile");
    if(json.flg==true){//进行已上传文件显示的操作,重新加载一次供应商资质文件
      var html="<li id='hrefId"+json.scId+"' style='float: left;list-style-type:none;width: 200px;height:220px;'>&nbsp;&nbsp; <a id='hrefId"+json.scId+"' href="+'"'+imgPath+json.savePath+'"'+" target='_blank' title='点击放大'>"+
      "<img width='190px' height='190px' src='"+imgPath+json.savePath+"'>"+json.fileName+"</a>"+
     " <a id='del"+json.scId+"' onclick='deleteFiles("+json.scId+")' href='javascript:void(0);'>删除</a></li>";
    	fileObj.append(html);
    }else{
    	$("#error_message").text(json.message);
    }
}

function organizationFileOnchange(){
	  $("#organizationFileErr").hide();
	 document.getElementById("organizationFileNameId").value = $("#organizationFile").val();
}
function businessFileOnchange(){
	   $("#businessFileErr").hide();
	document.getElementById("businessFileNameId").value = $("#businessFile").val();
}
function taxRegistratFileOnchange(){
	  $("#taxRegistratFileErr").hide();
      document.getElementById("taxRegistratFileNameId").value = $("#taxRegistratFileId").val();
}
