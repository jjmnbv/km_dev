	 /** 删除数据信息  **/
	    function deleteSelected(id){
	        var obj = document.getElementsByName(id);
	                var count = 0;
	                // 遍历所有用户，找出被选中的用户
	                for (var i = 0; i < obj.length; i++) {
	                    if (obj[i].checked) {
	                        count++;
	                    }
	                }
	                 if (count == 0) {
	                        alert("请选择要删除的数据。");
	                        return false;
	                 }else if(confirm('是否确认删除?')==true){ 
	                	
	                     for(var k = obj.length - 1; k >= 0; k--){
	                    	 if (obj[k].checked) {
	                        	 var $object = $(obj[k]);
	                        	 $object.parent().parent('.dataTr').remove();
	                         }
	                     }
	                     var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle;'/>删除成功!";
	                     messageDialog("消息提示","text:"+msg,"300px","100px","text");
	                     var timer_alert = setTimeout(function() {
	         				messageCloseThis();
	         			}, 2000);
	                 }
	    }
	   
	    
	    /** 单条删除数据信息  **/
	    function  deleteByKey(obj){
	         if(confirm("是否确认删除?")==true){
	        	 var $object = $(obj);
	        	 $object.parent().parent('.dataTr').remove();
	        	 var msg="<img src='../etc/images/tipMsg.png' style='vertical-align:middle;'/>删除成功!";
	        	 messageDialog("消息提示","text:"+msg,"300px","100px","text");
	        	 var timer_alert = setTimeout(function() {
	 				messageCloseThis();
	 			}, 2000);
	         }
	    }
	    
	    //全选反选
		function checkboxChange(){
			
				 var flag = true;
				 var dataIds = document.getElementsByName("dataIds");
				 for(var k = 0;k<dataIds.length;k++){
	                	if(dataIds[k].checked==false){
	                		 flag = false;
	       					 break;
	                    }
	               	}
				 $("input[type='checkbox'][name='data']")[0].checked = flag;
		}