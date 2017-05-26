

$(document).ready(function() {
	$('.ct:gt(0)').hide();
	var hdw = $('ul li');
	hdw.click(function() {
		$(this).addClass('visit').siblings().removeClass('visit');
		var hdw_index = hdw.index(this);
		$('.ct').eq(hdw_index).show().siblings().hide();
	});
	
	
	hdw.hover(
	  function () {
	    $(this).addClass("hover");
	  },
	  function () {
	    $(this).removeClass("hover");
	  }
	);
	
});
