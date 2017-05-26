
function submitForms(){
	var shopNameVal=$("#shopNameId").val();
	if("" == shopNameVal){
		alert("请输入店铺名称！");
		return;
	}
	
	var shopTitleVal=$("#shopTitleId").val();
	if("" == shopTitleVal){
		alert("请输入店铺标题！");
		return;
	}
	 var flag = checkFile();
	 if(flag == false){
		 return;
	 }
	 
	 var shopSeoKeyVal=$("#shopSeoKeyId").val();
	 if("" == shopSeoKeyVal){
		 alert("请输入店铺SEO！");
			return;
	 }
	 
	 var introduceVal=$("#introduceId").val();
	 if("" == introduceVal){
		 alert("请输入店铺简介！");
			return;
	 }
	 
	 var shopSiteVal=$("#shopSiteId").val();
	 if("" == shopSiteVal){
		 alert("请选择店铺站点！");
			return;
	 }
	 $("#frm").submit();
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
    }
    //else {
       // alert("请选择图片");
        //return false;
    //}
}