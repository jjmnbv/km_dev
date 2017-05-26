
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

//直接将以上js加入公共js文件即可；
//前提是：必须在使用页面引入jquery库，以及使用的table的class必须是“list_table”