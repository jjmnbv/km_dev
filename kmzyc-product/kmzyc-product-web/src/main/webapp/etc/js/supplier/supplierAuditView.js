function deleteFile(codId) {
	var answer = confirm("确认删除吗?");
	if(answer == true){
    	 $.ajax({
             url: 'deleteFile.action',
             async:false,
             data: 'scId='+codId,
             success: function(info) {
                if('0' == info){
                	$("#hrefId"+codId).remove();
                	$("#del"+codId).remove();
                }else{
                	alert("删除失败！");
                }
             }
         });
	}
	}