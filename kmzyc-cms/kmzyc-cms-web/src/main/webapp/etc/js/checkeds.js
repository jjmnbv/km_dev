

/** 全选js  **/
  function checkAllpop(titleName,checkBoxName){
		 var checkBox = document.getElementsByName(checkBoxName);
		 for (var i = 0; i < checkBox.length; i++){
		  var temp = checkBox[i];
		  
			  if(titleName.checked){
				  if(temp.checked){
					  continue;
				  }
			      temp.checked = true;
			  }else{
				  if(!temp.checked){
					  continue;
				  }
			      temp.checked = false;
			  }
				  checkpromotionId(temp);
		 }
  } 
  
  
  
  
  
  /**保持选中的checkbox		obj为当前的checkbox**/
  function checkpromotionId(obj){
 	 var check = "";
 	 	//checkeds以逗号隔开的Id的字符串，要对应Action的属性
      check = $("#checkeds").val();
      if(obj.checked){
     	 if(check==""){
         	 check = obj.value;
          }else{
              check +=","+obj.value;
          }
     	 $("#checkeds").val(check);
      }else{
          if(check!=""){
              if(check.indexOf(","+obj.value)>-1){
             	 check = check.replace(","+obj.value, "");
              }

              if(check.indexOf(obj.value+",")>-1){
             	 check = check.replace(obj.value+",", "");
              }
              
              if(check.indexOf(obj.value)>-1){
             	 check = check.replace(obj.value, "");
              }
         	$("#checkeds").val(check);
          }
      }
  }
  function checkpromotionIdRadio(obj){
	  var check = "";
	  if(obj.checked){
		  check = obj.value;
		  $("#checkeds").val(check);
	  }
  }