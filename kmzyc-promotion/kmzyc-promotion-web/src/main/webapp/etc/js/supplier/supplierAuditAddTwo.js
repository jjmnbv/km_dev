function submitForm(){
	
	var sDate = new Date(document.getElementById("staTime").value.replace(/\-/g, "\/"));
    var eDate = new Date(document.getElementById("endTime").value.replace(/\-/g, "\/"));
    if(sDate > eDate){
    	$("#validTimeErr").show();
    	return;
    }else{
    	$("#validTimeErr").hide();
    }
	
	 var organizationFilePath=$("#organizationFile");
	  var businessFilePath=$("#businessFile");
	  var taxRegistratFileIdPath=$("#taxRegistratFileId");
	  if(organizationFilePath.val() != ""){
 			 if(checkFile(organizationFilePath)==false){
 	    		  $("#organizationFileErr").show();
 	    		  return;
 	    	  }
   	  }else{
   		$("#organizationFileErr").show();
		  return;
   	  }
 		if(businessFilePath.val() != ""){
 			 if(checkFile(businessFilePath)==false){
 	        	  $("#businessFileErr").show();
 	    		  return;
 	    	  }
 		}else{
 			$("#businessFileErr").show();
   		  return;
 		}
 		if(taxRegistratFileIdPath.val() != ""){
 			 if(checkFile(taxRegistratFileIdPath)==false){
 	         	  $("#taxRegistratFileErr").show();
 	     		  return;
 	     	  }
 		}else{
 			$("#taxRegistratFileErr").show();
    		  return;
 		}
	
 		var registerBankrollVal=$("#registerBankroll").val();//检查是否选择了注册资金
		if("" == registerBankrollVal){
			 $("#registerBankrollNull").show();
			 return;
		}else{
			$("#registerBankrollNull").hide();
		}
 		
 		var taxRegistrationCnoVal=$("#taxRegistrationCno").val();//检查税务登记证号不能为空
    	if(taxRegistrationCnoVal == ""){
    		$("#taxRegistrationCnoNull").show();
    		$("#taxRegistrationCnoErr").hide();
    		return;
    	}else{
    		$("#taxRegistrationCnoNull").hide();
    		var regBus=/^[0-9]{15}$/;
    		if(regBus.test(taxRegistrationCnoVal) == false){
    			$("#taxRegistrationCnoErr").show();
    			return;
    		}else{
    			$("#taxRegistrationCnoErr").hide();
    		}
    	}
    	
    	var bankOfDepositVal=$("#bankOfDeposit").val();//开户银行
		if("" == bankOfDepositVal){
			$("#bankOfDepositNull").show();
			return;
		}else{
			$("#bankOfDepositNull").hide();
		}
		
		var bankAccountNameVal=$("#bankAccountName").val();//账户名
		if("" == bankAccountNameVal){
			$("#bankAccountNameNull").show();
			return;
		}else{
			$("#bankAccountNameNull").hide();
		}
		
		var companyAccountVal=$("#companyAccount").val();//开户银行
    	if(companyAccountVal == ""){
    		$("#companyAccountNull").show();
    		$("#companyAccountErr").hide();
    		return;
    	}else{
    		$("#companyAccountNull").hide();
    		var regBus=/^\d+$/;
    		if(regBus.test(companyAccountVal) == false){
    			$("#companyAccountErr").show();
    			return;
    		}else{
    			$("#companyAccountErr").hide();
    		}
    	}
	
	var ckTypes=false;
	var corporateNameObj=$("#corporateNameId");
	if("" == corporateNameObj.val()){
		$("#corporateNameNull").show();
		corporateNameObj.focus();
		return;
	}else{
		$("#corporateNameNull").hide();
	}
	
	var organizationCodeObj=$("#organizationCodeId");
	if("" == organizationCodeObj.val()){
		$("#organizationCodeNull").show();
		organizationCodeObj.focus();
		return;
	}else{
		$("#organizationCodeNull").hide();
		var reg=/^[a-zA-Z0-9]{8}-[a-zA-Z0-9]{1}$/;
		if(reg.test(organizationCodeObj.val()) == false){
			$("#organizationCodeErr").show();
			organizationCodeObj.focus();
			return;
			}else{
				$("#organizationCodeErr").hide();
			}
	}
	
	var businessLicenceRegisterObj=$("#businessLicenceRegisterId");
	if("" == businessLicenceRegisterObj.val()){
		$("#businessLicenceRegisterNull").show();
		businessLicenceRegisterObj.focus();
		return;
	}else{
		$("#businessLicenceRegisterNull").hide();
		var regBus=/^[0-9]{15}$/;
		if(regBus.test(businessLicenceRegisterObj.val())== false){
			$("#businessLicenceRegisterErr").show();
			businessLicenceRegisterObj.focus();
			return;
			}else{
				$("#businessLicenceRegisterErr").hide();
			}
	}
	
	var corporateLocationObj=$("#corporateLocationId");
	if("" == corporateLocationObj.val()){
		$("#corporateLocationNull").show();
		corporateLocationObj.focus();
		return;
	}else{
		$("#corporateLocationNull").hide();
	}
	
	//var contactsNameObj=$("#contactsNameId");
	//if("" == contactsNameObj.val()){
		//$("#contactsNameNull").show();
		///contactsNameObj.focus();
		//return;
	//}else{
	//	$("#contactsNameNull").hide();
	//}
	
	//var mobileObj=$("#mobileId");
	//if("" == mobileObj.val()){
		//$("#mobileNull").show();
		//mobileObj.focus();
		//return;
	//}else{
	//	$("#mobileNull").hide();
	//	var regMobil=/^1\d{10}$/;
		//if(regMobil.test(mobileObj.val()) == false){
		//	$("#mobileErr").show();
		//	mobileObj.focus();
		//	return;
		//}else{
		///	$("#mobileErr").hide();
		//}
	//}
	
	
		//验证固定电话
    	var fixedPhoneVal=$("#fixedPhoneId").val();
    	if("" == fixedPhoneVal){
    		$("#fixedPhoneNull").show();
    		$("#fixedPhoneId").focus();
    		return;
    	}else{
    		$("#fixedPhoneNull").hide();
    		var regFix=/^0\d{2,3}-\d{5,9}|0\d{2,3}-\d{5,9}$/;
    		if(regFix.test(fixedPhoneVal) == false){
    			$("#fixedPhoneErr").show();
    			$("#fixedPhoneId").focus();
    			return;
    		}else{
    			$("#fixedPhoneErr").hide();
    		}
    	}
    

    		//联系邮箱
	    	//var contactsEmailVal=$("#contactsEmailId").val();
	    	//if("" == contactsEmailVal){
	    		//$("#contactsEmailId").focus();
	    		//$("#contactsEmailNull").show();
	    		//return;
	    	//}else{
	    		//$("#contactsEmailNull").hide();
	    	//	var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	    	//	if(myreg.test(contactsEmailVal)== false){
	    		//	$("#contactsEmailErr").show();
	    		//	$("#contactsEmailId").focus();
	        	//	return;
	    	//	}else{
	    		//	$("#contactsEmailErr").hide();
	    		//}
	    	//}
	   
	
	
	//var productDescObj=$("#productDesc");
	//if("" == productDescObj.val()){
		//$("#saleProductDescribeNull").show();
		//productDescObj.focus();
		//return;
	//}else{
	//	$("#saleProductDescribeNull").hide();
	//}
	
	var categorys = document.getElementsByName("supplierCategorysName");
	var ckCategorys=false;
	var str="";//保存要新增的渠道类型名称
		 for(var i=0;i<categorys.length;i++){
   		  if(categorys[i].checked==true){
   				str+=categorys[i].value+",";
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
	
   	if($("#suppliersTypeId").val() == ""){
		 $("#supplierTypesNull").show();
		 return;
	}else{
		$("#supplierTypesNull").hide();
	}
	
	//var types = document.getElementsByName("supplierType");
	//var str="";//保存要新增的渠道类型名称
		// for(var i=0;i<types.length;i++){
   		//  if(types[i].checked==true){
   			//$("#channelNull").hide();
   				//str+=types[i].value+",";
   			   // ckTypes=true;
   		 // }
   		// }
   	// if(ckTypes == false){
   		//$("#channelNull").show();
  		  	//	return;
   //	 }else{
   		// $("#channeTypes").val(str);
   	// }
		$('#frm').submit();
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

