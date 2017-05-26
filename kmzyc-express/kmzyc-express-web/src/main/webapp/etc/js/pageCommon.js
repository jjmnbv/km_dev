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
  
  function forbid(id){
 	 if(confirm("是否禁止次号中奖? ")==true){
 		 $.post("/userInfo/operationManeger_forbidOrRelieve.action?extractPrizeVO.extractPrizeId="+id+"&remark=0",function(data){
 			
 				 alert("操作成功！");
 				 //获取活动ID的值
 				 var luckDrawId= $("#luckDrawId").val();
 				 var titel = $("#titel").val();
 				document.extractPrizeInfoForm.action="/userInfo/operationManeger_extractPrizeList.action?extractPrizeVO.luckDrawId="+luckDrawId+"&TITEL="+titel;
 		        document.extractPrizeInfoForm.submit();
 			
 		 });
  	}
 	 }

	function relieve(id){
		 if(confirm("是否解禁次号? ")==true){
			 $.post("/userInfo/operationManeger_forbidOrRelieve.action?extractPrizeVO.extractPrizeId="+id+"&remark=1",function(info){
	 			 
	 				 alert("操作成功！");
	 				 //获取活动ID的值
	 				 var luckDrawId= $("#luckDrawId").val();
	 				 var titel = $("#titel").val();
	 				document.extractPrizeInfoForm.action="/userInfo/operationManeger_extractPrizeList.action?extractPrizeVO.luckDrawId="+luckDrawId+"&TITEL="+titel;
	 		        document.extractPrizeInfoForm.submit();
	 			
	 		 });
  	}
	}
	
	//全部颁奖
	function allPrize(luckDrawId,awardsId){
		var againExtract=$("#againMark");
		var activityStatus=$("#activityStatus");
		if(activityStatus.val()==7){
			alert("活动已经结束，不能颁奖！");
			return;
		}
		if(againExtract.val()==0){
			againExtract.val(1);
			document.extractPrizeInfoForm.action="/userInfo/operationManeger_allPrize.action?luckDraw.luckDrawId="+luckDrawId+"&awards.awardsId="+awardsId;
	        
			 document.extractPrizeInfoForm.submit();
		}else{
			alert("全部颁奖中,请稍等...");
		}
	}
	
	//导出报表
	function reportFile(luckDrawId,awardsId){
			document.extractPrizeInfoForm.action="/userInfo/reportAction_winPrizeReport.action?luckDraw.luckDrawId="+luckDrawId+"&awards.awardsId="+awardsId;
	        
			 document.extractPrizeInfoForm.submit();
		
	}
	//提前抽奖
	function extractPrize(luckDrawId,awardsId){
		 if(confirm('是否确认提前抽奖?')==true){
			 var againExtract=$("#againMark");
				if(againExtract.val()==0){
					againExtract.val(1);
					document.extractPrizeInfoForm.action="/userInfo/operationManeger_beforePrize.action?luckDraw.luckDrawId="+luckDrawId+"&awards.awardsId="+awardsId;
			        
					 document.extractPrizeInfoForm.submit();
				}else{
					alert("页面过期，请刷新页面后，重试");
				}
		 }
		
	}
	//重新抽奖
	function againExtractPrize(luckDrawId,awardsId){
		if(confirm('是否确认重新抽奖?')==true){
			var againExtract=$("#againMark");
			if(againExtract.val()==0){
				againExtract.val(1);
				document.extractPrizeInfoForm.action="/userInfo/operationManeger_rePrize.action?luckDraw.luckDrawId="+luckDrawId+"&awards.awardsId="+awardsId;
		        
				 document.extractPrizeInfoForm.submit();
			}else{
				alert("重新抽奖中,请稍等...");
			}
		}
		
		
	}
	//确认中奖名单
	function verdictAffirmList(luckDrawId,awardsId){
		if(confirm('是否确认中奖名单?')==true){
			var awardsCount=parseInt($("#awardsCount").val());
			var listCount=parseInt($("#awardsCount").val());
			if(awardsCount>=listCount){
				$.post("/userInfo/operationManeger_verdictAffirmList.action?luckDraw.luckDrawId="+luckDrawId+"&awards.awardsId="+awardsId,function(info){
					 var temp;
					 if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){ 
						 temp=arguments[2].responseText;
					   } else{
						   temp=info;
					   }
					 if(temp==1){
						 document.extractPrizeInfoForm.action="/userInfo/operationManeger_affirmList.action?luckDraw.luckDrawId="+luckDrawId+"&awards.awardsId="+awardsId;
			            
						 document.extractPrizeInfoForm.submit();//"/userInfo/operationManeger_affirmList.action?luckDraw.luckDrawId="luckDrawId+"&awards.awardsId="+awardsId;
					 }else{
							alert("数据不符合抽奖规则，请重新抽奖！");
						}
					
				 });
			}else{
				alert("数据不符合抽奖规则，请重新抽奖！");
			}
			
		}
		 
//		 $.post("/userInfo/operationManeger_verdictAffirmList.action?luckDraw.luckDrawId="+luckDrawId+"&awards.awardsId="+awardsId,function(info){
//			 alert("3:"+luckDrawId);
//				if(info=="1"){//中奖数据合法，可确认中奖名单
//					document.winPrizeForm.action="/userInfo/operationManeger_affirmList.action?luckDraw.luckDrawId="luckDrawId+"&awards.awardsId="+awardsId;
//                 document.winPrizeForm.submit();
//				}else{
//					alert("中奖名单数据异常，请重新抽奖！");
//				}
//		 });
		
	}
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
              
              if (/^[^\|"'<>~&$%{}?()（）《》｛｝【】]*$/.test(form.elements[i].value))
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
    	  var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle'/>文本输入框中不能输入|\"\'<>~&$%{}?\()（）《》｛｝【】 ";
          dialog("消息提示","text:"+msg ,"300px","127px","text");  
          return false;
      }
  }
  function mytable(a,b,c,d){
	  var t=$(".list_table")[0].getElementsByTagName("tr");
	  for(var i=0;i<t.length;i++){

	   t[i].style.backgroundColor=(t[i].sectionRowIndex%2==0)?a:b;
	   t[i].onclick=function(){
	 	  for(var i=0;i<t.length;i++){
	 		 if(t[i]!=this) t[i].x = "0";
	 		 t[i].style.backgroundColor=(t[i].sectionRowIndex%2==0)?a:b;
	 	  }
	    if(this.x!="1"){
	     this.x="1";//本来打算直接用背景色判断，FF获取到的背景是RGB值，不好判断
	     this.style.backgroundColor=d;
	    }else{
	     this.x="0";
	     this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
	    }
	   }
	   t[i].onmouseover=function(){
	 	  
	    if(this.x!="1")this.style.backgroundColor=c;
	   }
	   t[i].onmouseout=function(){
	    if(this.x!="1")this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
	   }
	  }
	 }

	 $(function(){
	 	//senfe("表格名称","偶数行背景","奇数行背景","鼠标经过背景","点击后背景");
	 	mytable("#fff","#EEF9F3","#cfc","#cfc");
	 });
