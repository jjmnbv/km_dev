function submitForm(){
	
	var sDate = new Date(document.getElementById("staTime").value.replace(/\-/g, "\/"));
    var eDate = new Date(document.getElementById("endTime").value.replace(/\-/g, "\/"));
    if(sDate > eDate){
    	$("#validTimeErr").show();
    	return;
    }else{
    	$("#validTimeErr").hide();
    }
    $("#companyAccountErr").hide();
	var ckTypes=false;
	var ckCorporateName=false;
	var corporateNameObj=$("#corporateNameId");
	if("" == corporateNameObj.val()){
		$("#corporateNameNull").show();
		corporateNameObj.focus();
		return;
	}else{
		$("#corporateNameNull").hide();
		$.ajax({
            url: 'ckCorporateName.action?_t='+new Date().getTime(),
            async:false,
            data: 'corporateName='+corporateNameObj.val(),
            success: function(info) {
               if('0' == info){//没有存在同样的公司名称
               	$("#corporateNameErr").hide();
               	ckCorporateName=true;
               }else{//公司名称有一样的
               	corporateNameObj.focus();
               	$("#corporateNameErr").show();
               	ckCorporateName=false;
                return;
               }
            }
        });
	}
	if($("#suppliersTypeId").val() == 2){//入驻才能判断佣金比例
	var categorys = document.getElementsByName("categorys");
	var ckCategorys=false;
	var str="";
	var strClass="";//保存class名
	for(var i=0;i<categorys.length;i++){
	  if(categorys[i].checked==true){
		  strClass = (categorys[i].className).split(' ');
		  if(strClass[1] == 'class0'){
// 	 			 console.log("SacId:"+categorys[i].value+",佣金:null");
 			str+=categorys[i].value+"_"+"null"+",";
 			isTrue = false;
		  }else{
// 				 console.log("SacId:"+categorys[i].value+",佣金:"+$("#inputs"+categorys[i].value).val());
			str+=categorys[i].value+"_"+$("#inputs"+categorys[i].value).val()+",";
			isTrue = "" == $("#inputs"+categorys[i].value).val();
		  }
		  if(categorys[i].checked==true && isTrue){
			alert("请输入佣金比例！");
	       	return;
		  }else{
       		if(strClass[1] != 'class0'){
       			var ckbili=$("#inputs"+categorys[i].value).val();
       			//var reg=/^0\.\d*[1-9]\d*$/;
       		    var reg=/^((\d|[123456789]\d)(\.\d+)?)$/;
       			if(categorys[i].checked==true && (reg.test(ckbili)== false || ckbili.substring(ckbili.indexOf(".")+1,ckbili.length).length > 2 || ckbili==0)){
	       			alert("请输入大于0小于100的佣金比例,小数最多为两位");
	       			return;
       			}
       		}
       	  }
		  ckCategorys=true;
	  }
	 }
   	 if(ckCategorys == false){
   		 $("#categorysNull").show();
  		  		return;
   	 }else{
   		 $("#supplierCategorys").val(str);
   		 $("#categorysNull").hide();
   	 }
	}
   	if($("#suppliersTypeId").val() == ""){
		 $("#supplierTypesNull").show();
		 return;
	}else{
		$("#supplierTypesNull").hide();
	}
   	if(ckCorporateName == true){
   		$('#frm').submit();
   	}
		
		}
//判断选择供应商类型，如果是入驻就需要填写佣金比例
function ckSuppType() {
	
	//var suppliersTypeVal=obj.value;
	var suppliersTypeVal=$("#suppliersTypeId").val();
//	var cBoxs=document.getElementsByName("supplierCategorysName");
	if("1" == suppliersTypeVal || "3" == suppliersTypeVal){
		$("input:checkbox").attr("disabled","disabled");
		}else{
			$("input:checkbox").removeAttr("disabled"); //启用
		}
}

/**
 * 验证文件类型
 * @returns {boolean}
 */
function checkFile(obj) {
    var filepath = obj.val();
    if(null!=filepath && "" != filepath){
        var extStart = filepath.lastIndexOf(".");
        var ext = filepath.substring(extStart, filepath.length).toUpperCase();
        if (ext != ".BMP" && ext != ".PNG" && ext != ".GIF" && ext != ".JPG" && ext != ".JPEG") {
        	obj.val("");
            return false;
        }else{
            return true;
        }
    }else {
        return false;
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

