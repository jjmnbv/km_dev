
  /**
   * 校验所有输入域是否含有特殊符号
   * 所要过滤的符号写入正则表达式中，注意，一些符号要用'\'转义.
   * 试例：
   * if(checkAllTextValid(document.forms[0]))
   * alert("表单中所有文本框通过校验！");
   */
  function checkAllTextValid(form) {
      //记录不含引号的文本框数量
      var resultTag = 0;
      //记录所有text文本框数量
      var flag = 0;
      for (var i = 0; i < form.elements.length; i++) {
          if (form.elements[i].type == "text") {
              flag = flag + 1;
              //此处填写所要过滤的特殊符号
              //注意：修改####处的字符，其它部分不许修改.
              //if(/^[^####]*$/.test(form.elements[i].value))
              
              if (/^[^\|"'<>~&$%{}?《》｛｝【】]*$/.test(form.elements[i].value))
                  resultTag = resultTag + 1;
              else
                  form.elements[i].select();
          }
      }
      
      /**
       * 如果含引号的文本框等于全部文本框的值，则校验通过
       */
      if (resultTag == flag)
          return true;
      else {
    	  var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>文本输入框中不能输入|\"\'<>~&$%{}?\《》｛｝【】 ";
          dialog("消息提示","text:"+msg ,"300px","127px","text");  
          return false;
      }
      
      
  }
  
  function isCheck(checks){
	  var checkBoxs = document.getElementsByName(checks);
	 
	  for (var i = 0; i < checkBoxs.length; i++){
		  if(checkBoxs[i].checked){
			  return true;
		  }
	  }
	  var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>请选择!";
      messageDialog("消息提示","text:"+msg ,"300px","102px","text");
      var timer_alert = setTimeout(function() {
			messageCloseThis();
		}, 2000);
	  return false;
  }

  /** 全选js  **/
    function checkAll(titleName,checkBoxName){
  		 var checkBox = document.getElementsByName(checkBoxName);
  		 for (var i = 0; i < checkBox.length; i++){
  		  var temp = checkBox[i];
  		  
  			  if(titleName.checked){
  			      temp.checked = true;
  			  }else{
  			      temp.checked = false;
  			  }
  		 }
    } 
    
   