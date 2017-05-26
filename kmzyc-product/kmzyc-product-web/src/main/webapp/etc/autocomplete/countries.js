var countries;
$(function(){
	$.post(
		'/basedata/findAllBrandForJson.action',	
		function(data){
			countries = data;
		},'text'
	);
});
