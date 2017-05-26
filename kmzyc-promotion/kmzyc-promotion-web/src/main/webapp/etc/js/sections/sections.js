	var options = { dataType: 'json', beforeSubmit: validateForm, success: createsuccess};

	function validateForm(){
		
	}
	
	function createsuccess(data){
		alert(data.msg);
		$('#sectionsDetailForm').submit();
	}
	
	//批量删除
	function batchDeleteSections(){
		var chkObj = $('input[name="sectionsDetailIds"]:checked');
		if(chkObj.length==0){
	   		alert('请勾选要删除的产品!')
	   		return;
	   	}
		
		if(confirm('确定删除已选产品吗？')){
			var chk_value =[]; 
			var html = "";
			$('input[name="sectionsDetailIds"]:checked').each(function(){ 
			   chk_value.push($(this).val());
			   $(this).parent().parent().remove();
			}); 

			for(var i=0;i<chk_value.length;i++){
				if(chk_value[i]!=0)
					html += '<input type="hidden" name="delSec_ids" value="'+chk_value[i]+'"/>';
			}
			//alert(html);
			
			$('#delForm').html(html);
			$('#delForm').ajaxSubmit(options);
		}
	}
