
// JavaScript Document
	//这个函数主要的功能计算页面的大小，屏幕的大小，返回值为Array,不必要细读，返回值为一个Array
		var procbg; 

		function ___getPageSize() {  
             var xScroll, yScroll;
             if (window.innerHeight && window.scrollMaxY) {    
                 xScroll = window.innerWidth + window.scrollMaxX;  
                 yScroll = window.innerHeight + window.scrollMaxY;  
             } else if (document.body.scrollHeight > document.body.offsetHeight){ // all but Explorer Mac  
                 xScroll = document.body.scrollWidth;  
                 yScroll = document.body.scrollHeight;  
             } else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari  
                 xScroll = document.body.offsetWidth; 
                 yScroll = document.body.offsetHeight;  
             }  
             var windowWidth, windowHeight;  
             if (self.innerHeight) { // all except Explorer  
                 if(document.documentElement.clientWidth){  
                     windowWidth = document.documentElement.clientWidth;   
                 } else {  
                     windowWidth = self.innerWidth;  
                 }  
                 windowHeight = self.innerHeight;  
             } else if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode  
                 windowWidth = document.documentElement.clientWidth;  
                 windowHeight = document.documentElement.clientHeight;  
             } else if (document.body) { // other Explorers  
                 windowWidth = document.body.clientWidth;  
                 windowHeight = document.body.clientHeight;  
             }     
             // for small pages with total height less then height of the viewport  
             if(yScroll < windowHeight){  
                 pageHeight = windowHeight;  
             } else {   
                 pageHeight = yScroll;  
             }  
             // for small pages with total width less then width of the viewport  
             if(xScroll < windowWidth){     
                 pageWidth = xScroll;          
             } else {  
                 pageWidth = windowWidth;  
             }
             var scrollTop;
             if(document.documentElement && document.documentElement.scrollTop){
            	 scrollTop = $(document.documentElement).scrollTop();
             }else{
            	 scrollTop = $(document.body).scrollTop();
             }
             arrayPageSize = new Array(pageWidth,pageHeight,windowWidth,windowHeight,scrollTop);  
             return arrayPageSize;  
         };  
		
		var isScroll = function (el) {   
			// test targets   
			var elems = el ? [el] : [document.documentElement, document.body];   
			var scrollX = false, scrollY = false;   
			for (var i = 0; i < elems.length; i++) {   
				var o = elems[i];   
				// test horizontal   
				var sl = o.scrollLeft;   
				o.scrollLeft += (sl > 0) ? -1 : 1;   
				o.scrollLeft !== sl && (scrollX = scrollX || true);   
				o.scrollLeft = sl;   
				// test vertical   
				var st = o.scrollTop;   
				o.scrollTop += (st > 0) ? -1 : 1;   
				o.scrollTop !== st && (scrollY = scrollY || true);   
				o.scrollTop = st;   
			}   
			// ret   
			return {   
				scrollX: scrollX,   
				scrollY: scrollY   
			};
		};

		 var divflag=1;//1标记没有层弹出，2标记有一个层弹出，3标记有两个层弹出
		
		 function showProc(frame){
			    $('#'+frame).Drags({
					 handler: '#'+frame+'dragDiv',                
					 zIndex:200,
					 opacity:.9
				 });
				 $('.select1').css('visibility','hidden');
				  var message_box = document.getElementById(frame);//$("#"+frame);
				  //$(message_box).draggable();
				  message_box.style.display="block";
				 
				 // message_box.style.left =
				  var pageSize = ___getPageSize();//这里的方法 返回的是页面的宽度，页面的高度，屏幕的高度，屏幕的宽度!
				  var divWidth=message_box.style.width;	
				  var divHeight=message_box.style.height;
				//alert(divWidth+" " +divHeight);
				  var width = divWidth.substr(0,divWidth.length-2);
				  var height = divHeight.substr(0,divHeight.length-2);
					
				  var pageWidth =  pageSize[0];//页面的宽度
				  var pageHeight = pageSize[1];//页面的高度
				  var ScreenWidth = pageSize[2];//屏幕的宽度
				  var ScreenHeight = pageSize[3];//屏幕的高度
				  var scrollTop = pageSize[4];
				  var top1=(ScreenHeight-height)/2 + scrollTop;
				  var left1=(ScreenWidth-width)/2;
				  message_box.style.top=top1;
				  message_box.style.left=left1; 
				  //message_box.style.top = (ScreenHeight-pageHeight+divHeight)/2+"px";//弹出div层，所属上一层div居中的位置,本实例中就为contentDiv高度一半的位置
				  //alert(pageSize) 打印的数据可以看出ScreenHeight 和PageHeight的高度是一样的，可能__getPageSize计算屏幕或者页面的高度有问题!所以关于div垂直居中的问题，请大家手动解决
				  //创建灰色背景层
				  createBG();
		 }
			 //拖动
		/*$().ready(function() {
			 $('#setmessage').Drags({
				 handler: '#dragDiv',                
				 zIndex:200,
				 opacity:.9
			 });
		 });*/
		 
	function createBG(){
		  if(divflag==1){
			  divflag = divflag+1;
			  procbg = document.createElement("div"); 
			  procbg.setAttribute("id","mybg"); 
			  procbg.style.background = "#000000"; 
			  procbg.style.width = "100%"; 
			  procbg.style.display = "none";
			  if(isScroll().scrollY){
			  	procbg.style.height = document.body.scrollHeight;
			  }else{
			  	procbg.style.height = "100%"; 
			  }
			  procbg.style.position = "absolute"; 
			  procbg.style.top = "0"; 
			  procbg.style.left = "0"; 
			  procbg.style.zIndex = "500"; 
			  procbg.style.opacity = "0.3"; 
			  procbg.style.filter = "Alpha(opacity=30)"; 
			  $(procbg).fadeIn(100);
			  //背景层加入页面
			  document.body.appendChild(procbg);
			  //document.body.style.overflow = "hidden";
		  }else{
			  divflag = divflag+1;
			  //alert(divflag);
		 }
	}
	
		 
	 function closeProc(frame){
		if(divflag == 3)
		{
	 	var message_box = document.getElementById(frame);
	    message_box.style.display='none';
	    //document.body.style.overflow="visible";
		divflag = divflag-1;
		}else
		{
			divflag = divflag-1;
			var message_box = document.getElementById(frame);
			//document.body.style.overflow="visible";
	   		 message_box.style.display='none';
	   		 $('.select1').css('visibility','visible');
	    	procbg.style.display='none';
	    	
		}
	 }

