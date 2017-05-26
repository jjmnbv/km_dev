//显示进球窗口
var startani_C,startani_A,startani_B,pop_TC;
var oPopup;


//实时设置浮动层的位置
function windowPop_Move()
{
	var windowPop_MarginLeft = 30;   //浮动层离浏览器右侧的距离
	var windowPop_MarginTop = 40;     //浮动层离浏览器顶部的距离
	var windowPop_Width = 400;        //浮动层宽度
	window.document.getElementById("windowPop").style.top = (window.document.documentElement.scrollTop + windowPop_MarginTop)+"px";
    window.document.getElementById("windowPop").style.left = (window.document.documentElement.clientWidth - windowPop_Width - windowPop_MarginLeft)+"px";

	setTimeout("windowPop_Move()",100);
}




//组装比赛时间弹出层内容
function ShowCHWindow(str,matchnum){
	var st='';
	st+='<table width="400" border="0" cellpadding="0" cellspacing="1" bgcolor="#666666">';
	st+='<tr> <td height="18" bgcolor="yellow">';
	st+='<table width="400" border="0" cellspacing="0" cellpadding="0">';
	st+='<tr><td width="150" height="18" align="left" class="weight blue"><em>COAL!!!</em></td><td width="150" align="right" class="font10 yellow">&nbsp;&nbsp;</td></tr>';
	st+='</table>';
	st+='</td></tr>';
	st+='<tr><td align="center" bgcolor="#FFFFFF">';
	st+='<table><tr><td height="3" align="center" bgcolor="#FFFFFF"></td></tr></table>';
	st+='<table width="390" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">';
	st+='<tr><td height="20" colspan="5" align="left" bgcolor="#F0F9FD">&nbsp;&nbsp;&nbsp;<img src="/etc/images/taocai/zoudi.gif" width="10" height="11" /> <span class="zongse">进球提示：</span> </td></tr>';
	st+=str;
	st+='</table>';
	st+='<table><tr><td height="3" align="center" bgcolor="#FFFFFF"></td></tr></table>';
	st+='</td></tr>';
	st+='<tr><td height="18" align="center" bgcolor="yellow" style="color:#35B0DC">欢迎观看比分&nbsp;&nbsp;[<a href="javascript:resetPop()"><span  style="color:#35B0DC">点击关闭窗口</span></a>]</td></tr>';
	st+='</table>';
		
	document.getElementById("windowPop").innerHTML=st;
	document.getElementById("windowPop").style.position="absolute";
	document.getElementById("windowPop").style.top="2px";
	document.getElementById("windowPop").style.right="2px"; 
	document.getElementById("windowPop").style.display="";

	windowPop_Move();
	setTimeout("resetPop()",30000);//30秒后隐藏弹出窗口
}

//关闭比赛时间弹出层内容
function resetPop(){
	document.getElementById("windowPop").style.display="none";
	document.getElementById("windowPop").innerHTML="";
}


var m_language;//语言
var loaded=0, LoadTime=0;//加载

var loadDetailFileTime=new Date();//加载事件时间
var flash_sound=Array(5);//声音
flash_sound[0] = "<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0' width='0' height='0'><param name='movie' value='/etc/images/taocai/sound1.swf'><param name='quality' value='high'><param name=LOOP value='false'><param name='wmode' value='transparent'> <embed src='/etc/images/taocai/sound1.swf' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width='1' height='1'></embed></object>";
flash_sound[1] = "<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0' width='0' height='0'><param name='movie' value='/etc/images/taocai/sound2.swf'><param name='quality' value='high'><param name=LOOP value='false'><param name='wmode' value='transparent'> <embed src='/etc/images/taocai/sound2.swf' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width='1' height='1'></embed></object>";
flash_sound[2] = "<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0' width='0' height='0'><param name='movie' value='/etc/images/taocai/sound3.swf'><param name='quality' value='high'><param name=LOOP value='false'><param name='wmode' value='transparent'> <embed src='/etc/images/taocai/sound3.swf' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width='1' height='1'></embed></object>";
flash_sound[3] = "<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0' width='0' height='0'><param name='movie' value='/etc/images/taocai/sound4.swf'><param name='quality' value='high'><param name=LOOP value='false'><param name='wmode' value='transparent'> <embed src='/etc/images/taocai/sound4.swf' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width='1' height='1'></embed></object>";
flash_sound[4] = "<object classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0' width='0' height='0'><param name='movie' value='/etc/images/taocai/sound5.swf'><param name='quality' value='high'><param name=LOOP value='false'><param name='wmode' value='transparent'> <embed src='/etc/images/taocai/sound5.swf' pluginspage='http://www.macromedia.com/go/getflashplayer' type='application/x-shockwave-flash' width='1' height='1'></embed></object>";
var lastUpdateTime, oldUpdateTime="";
var lastUpdateFileTime=0;
var soundid=0;//默认声音id
var hiddenID="_";//隐藏id
var windowCheck=true;//事件弹出框选中
var soundCheck=true;//声音框选中

var state_ch=Array(17);//转化比赛状态语言
state_ch[0]="推迟,推遲,Defer";
state_ch[1]="中断,中斷,Halt";
state_ch[2]="腰斩,腰斬,Halt";
state_ch[3]="<font color=green>待定</font>,<font color=green>待定</font>,<font color=green>Wait</font>";
state_ch[13]="<b style=color:red>完</b>,<b style=color:red>完</b>,<b style=color:red>Ft</b>";
state_ch[14]="&nbsp;,&nbsp;,&nbsp;";
state_ch[15]="上,上,Part1";
state_ch[16]="<font color=blue>中</font>,<font color=blue>中</font>,<font color=blue>Half</font>";
state_ch[17]="下,下,Part2";

var GoalCn="平手,平/半,半球,半/一,一球,一/球半,球半,球半/两,两球,两/两球半,两球半,两球半/三,三球,三/三球半,三球半,三球半/四球,四球,四球/四球半,四球半,四球半/五球,五球,五球/五球半,五球半,五球半/六球,六球,六球/六球半,六球半,六球半/七球,七球,七球/七球半,七球半,七球半/八球,八球,八球/八球半,八球半,八球半/九球,九球,九球/九球半,九球半,九球半/十球,十球".split(",");

//数字盘口转汉汉字
function Goal2GoalCn(goal){ 	
    if (goal==""){
        return "";
    }else{
	    if(goal>=0){
	    	return GoalCn[parseInt(goal*4)];
	    }else{
	         return "受让"+ GoalCn[Math.abs(parseInt(goal*4))];
	    }
	}
}

//组装赛事
function MakeSclass(){
	var st;
	var leaguehtml=new Array();
	for(var i=1;i<=sclasscount;i++){	
		leaguehtml.push("<div class='matchlist'><div class='mcbox1'><label><input type='checkbox' name='checkbox2' onclick='CheckLeague(" + i + ")' checked id='checkboxleague_" + i + "' /></label></div><div class='mcbox2'>" + B[i][m_language] + "</div></div>");
	}
    leaguehtml.push("<div class='matchlist-1'><div class='mcbox3'><a style='cursor:pointer;'  onClick='ShowAllMatch()'><img src='/etc/images/taocai/mclist-bnt1.gif' width='52' height='22' border='0' /></a></div><div class='mcbox4'><a style='cursor:pointer;'  onclick='SelectOtherLeague()'><img src='/etc/images/taocai/mclist-bnt2.gif' width='52' height='22' border='0' /></a></div></div>");
	document.getElementById("myleague").innerHTML=leaguehtml.join("");
}


//更新比赛进行的时间(针对于正在打的比赛)
function setMatchTime(){	
	for(var i=1;i<=matchcount;i++){//循环所有对阵
	  try{
	    if(A[i][13]=="1"){  //上半场			
	        var t = A[i][12].split(",");
			var t2 = new Date(t[0],t[1],t[2],t[3],t[4],t[5]);
			goTime = Math.floor((new Date()-t2-difftime)/60000);
			if(goTime>45) goTime = "45+";
			if(goTime<1)  goTime = "1";
			document.getElementById("time_" +  A[i][0]).innerHTML = goTime +  "<img src='/etc/images/taocai/in.gif' border=0>";
		}
		if(A[i][13]=="3"){  //下半场		
	        var t = A[i][12].split(",");
			var t2 = new Date(t[0],t[1],t[2],t[3],t[4],t[5]);
			goTime = Math.floor((new Date()-t2-difftime)/60000)+46;
			if(goTime>90) goTime = "90+";
			if(goTime<46) goTime = "46";
			document.getElementById("time_" +  A[i][0]).innerHTML = goTime +  "<img src='/etc/images/taocai/in.gif' border=0>";
		}
      }catch(e){}
	}
	window.setTimeout("setMatchTime()" , 60000);//一分钟刷新一次
}

//隐藏比赛
function hideSelMatch(){
    if(hiddenID=="_") return;
    var hh=0;
    var id=hiddenID.split("_");
    for(var i=1;i<id.length-1;i++){
        if(document.getElementById("tr1_" +  id[i])!=null){
            document.getElementById("tr1_" +  id[i]).style.display="none";
            document.getElementById("tr2_" +  id[i]).style.display="none";
            hh++;
        }
    }
	document.getElementById("hiddencount").innerHTML=hh;
}



//创建XmlHttp对象用来读取xml，也可以用来操作ajax
var zXml = {
    useActiveX: (typeof ActiveXObject != "undefined"),
    useXmlHttp: (typeof XMLHttpRequest != "undefined")
};

zXml.ARR_XMLHTTP_VERS = ["MSXML2.XmlHttp.6.0","MSXML2.XmlHttp.3.0"];

function zXmlHttp() {}

zXmlHttp.createRequest = function (){
    if (zXml.useXmlHttp){
    	return new XMLHttpRequest(); 
    }
    if(zXml.useActiveX){  
        if (!zXml.XMLHTTP_VER) {
            for (var i=0; i < zXml.ARR_XMLHTTP_VERS.length; i++) {
                try {
                    new ActiveXObject(zXml.ARR_XMLHTTP_VERS[i]);
                    zXml.XMLHTTP_VER = zXml.ARR_XMLHTTP_VERS[i];
                    break;
                } catch (oError) {}
            }
        }        
        if (zXml.XMLHTTP_VER){
        	 return new ActiveXObject(zXml.XMLHTTP_VER);
        }
    } 
    alert("对不起，您的电脑不支持 XML 插件，请安装好或升级浏览器。");
};


var oXmlHttp = zXmlHttp.createRequest();

function getOs(){ //得到是何种浏览器  
	var OsObject = "";   
	if(navigator.userAgent.indexOf("MSIE")>0) {   
		return "MSIE";       //IE浏览器
	}
	if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){   
		return "Firefox";     //Firefox浏览器
	}
	if(isSafari=navigator.userAgent.indexOf("Safari")>0) {   
		return "Safari";      //Safan浏览器
	}
	if(isCamino=navigator.userAgent.indexOf("Camino")>0){   
		return "Camino";   //Camino浏览器
	}
	if(isMozilla=navigator.userAgent.indexOf("Gecko/")>0){   
		return "Gecko";    //Gecko浏览器
	}   
}

//判断是否有比赛变化
function gettime(){
	try
	{
		//alert("1LoadTime====="+LoadTime);
	    LoadTime=(LoadTime+1)  % 28;
	    //alert("2LoadTime====="+LoadTime);
	    if (LoadTime == 0) {
			//alert("222222");
			getxml("2");
		}else {
			var _url = "http://live.bet007.com/vbsxml/time.txt";
			var _code = "gb2312";
			//var btype = getOs();
			//alert(oXmlHttp);
			oXmlHttp.open("get", "/football/getRemoteFileContent.action?url=" + _url + "&code=" + _code + "&thisTime=" + Date.parse(new Date()), true);
			//alert("open");
		    oXmlHttp.onreadystatechange = xmlHandleCallBack;
			oXmlHttp.send(null);	    
		}	
	}
	catch(e){}
	window.setTimeout("gettime()",5000);
}

function xmlHandleCallBack(){
	if (oXmlHttp.readyState == 4) {
		if( oXmlHttp.status == 200){
			lastUpdateTime=new Date(); 
			//alert(oXmlHttp.responseText);
			//alert(lastUpdateFileTime);
			if(oXmlHttp.responseText!="" && oXmlHttp.responseText!=lastUpdateFileTime){
				lastUpdateFileTime=oXmlHttp.responseText;
				getxml("");                        
			} 
		}
   }
}

function getxml(ii){
	//alert("ii=="+ii);
	var _url = "http://live.bet007.com/vbsxml/change"+ii+".xml";
	var _code = "gb2312";
    oXmlHttp.open("get","/football/getRemoteFileContent.action?url="+_url+"&code="+_code+"&thisTime=" + Date.parse(new Date()),true);
    oXmlHttp.onreadystatechange = refresh;	        
    oXmlHttp.send(null);
}
function refresh(){
	if(oXmlHttp.readyState!=4 || (oXmlHttp.status!=200 && oXmlHttp.status!=0)) return;
   // var root=oXmlHttp.responseXML.documentElement;
   
    var xmlDoc=oXmlHttp.responseText.replace(new RegExp("[\\r\\n]","gm"),"");
	var xmlobject;
		try{// 用于 IE 的代码：
			if (window.ActiveXObject){
					//alert("window");
	 				xmlobject =new ActiveXObject("Microsoft.XMLDOM");
	 				xmlobject.async="false";
					xmlobject.loadXML(xmlDoc);
	 			}else{// 用于 Mozilla, Firefox, Opera, 等浏览器的代码：
	 				//alert("Firefox");
	 				var parser=new DOMParser();
	 				xmlobject =parser.parseFromString(xmlDoc,"text/xml");
	 			}
	}catch (e){
		return;
	}
   
    var root=xmlobject.documentElement;
    //if(root==null){
    //	alert("empty");
    //}else{
    //	alert('xmlDoc='+xmlDoc);
    //    alert('xmlobject='+xmlobject);
    //    alert('root='+root);
    //    alert('root.attributes[0].value='+root.attributes[0].value);
    //}
    
	if(root.attributes[0]==null||root.attributes[0]=="undefined"){
	   		window.setTimeout("LoadLiveFile()",Math.floor(20000 * Math.random()));
			return;
	}else{
		 if(root.attributes[0].value!="0"){
			window.setTimeout("LoadLiveFile()",Math.floor(20000 * Math.random()));
			return;
	    }
	}
	var D=new Array();
    var matchindex,score1change, score2change, scorechange;
    var goTime,hometeam,guestteam,sclassname,score1,score2,tr;    
    var matchNum=0;
    var winStr="";
    
	for(var i = 0;i<root.childNodes.length;i++){   
	    if(document.all)
    	    D=root.childNodes[i].text.split("^"); //0:ID,1:state,2:score1,3:score2,4:half1,5:half2,6:card1,7:card2,8:time1,9:time2,10:explain,11:lineup	    
        else
            D=root.childNodes[i].textContent.split("^");
       
       //alert("D="+D);
       
        tr=document.getElementById("tr1_" + D[0]);
        if(tr==null){
        	//alert("not find , continue");
        	continue;
        }
    	
		matchindex=tr.attributes["index"].value;
		//alert("matchindex==="+matchindex);		
		score1change=false;//标志位为 "主队比分未改变"
		//alert("主队比分=="+A[matchindex][14]+"========="+D[2]);
		if(A[matchindex][14]!=D[2]){//如果主队比分不同
			A[matchindex][14]=D[2];//改变状态
			score1change=true;
			tr.cells[4].style.backgroundColor="#FFCC00";
		}
		score2change=false;//标志位为 "客队比分未改变""
		//alert("客队比分=="+A[matchindex][15]+"========="+D[3]);
		if(A[matchindex][15]!=D[3]){//如果客队比分不同
			A[matchindex][15]=D[3];
			score2change=true;
			tr.cells[6].style.backgroundColor="#FFCC00";
		}
		scorechange=score1change || score2change;//主队比分或客队比分任意一个改变，总比分改变
		 
		//附加说明改时变了'
		//alert("附加说明=="+A[matchindex][30]+"========="+D[10]);
		if(A[matchindex][30]!= D[10]){
			A[matchindex][30]= D[10];
			var _xxnr = ""
			if(D[10]!=""){
				_xxnr = D[10].replace(/<a.*?>(.*)<\/a>/ig,"");
				_xxnr = _xxnr.replace("<br>","");
			}
			if(_xxnr==""){
				document.getElementById("tr2_" + D[0]).style.display="none";
			}else{
				document.getElementById("other_" + D[0]).innerHTML=_xxnr;
				document.getElementById("tr2_" + D[0]).style.display="";
			}
		}

		//红牌变化了
		//alert("主队红牌变化=="+A[matchindex][18]+"========="+D[6]);
		if(D[6]!=A[matchindex][18]){//主队红牌
			A[matchindex][18]=D[6];
			if(D[6]=="0")
				document.getElementById("redcard1_" + D[0]).innerHTML="";
			else
				document.getElementById("redcard1_" + D[0]).innerHTML="<table width='5' border='0' cellspacing='0' cellpadding='0' class='redcard'><tr><td>" + D[6] + "</td></tr></table>"; 			
			tr.cells[4].style.backgroundColor="#ff8888";	
			window.setTimeout("timecolors(" + D[0] +","+ matchindex + ")",12000);
		}
		//alert("客队红牌变化=="+A[matchindex][19]+"========="+D[7]);
		if(D[7]!=A[matchindex][19]){//客队红牌
			A[matchindex][19]=D[7];
			if(D[7]=="0")
				document.getElementById("redcard2_" + D[0]).innerHTML="";
			else
				document.getElementById("redcard2_" + D[0]).innerHTML= "<table width='5' border='0' cellspacing='0' cellpadding='0' class='redcard'><tr><td>" + D[7] + "</td></tr></table>"; 			
			tr.cells[6].style.backgroundColor="#ff8888";	
			window.setTimeout("timecolors(" + D[0] +","+ matchindex + ")",12000);
		}		
		

		//开赛
		if(A[matchindex][11]!=D[8]) tr.cells[2].innerHTML=D[8];
		A[matchindex][11]=D[8];
		A[matchindex][12]=D[9];

		//半场比分
		A[matchindex][16]=D[4];
		A[matchindex][17]=D[5];

		//状态
		if(A[matchindex][13]!= D[1]){
			A[matchindex][13]=D[1];
			switch(A[matchindex][13]){
			case "0":
					tr.cells[3].innerHTML="";
					break;
			case "1":
				    var t = A[matchindex][12].split(",");
			        var t2 = new Date(t[0],t[1],t[2],t[3],t[4],t[5]);
			        goTime = Math.floor((new Date()-t2-difftime)/60000);
					if(goTime>45) goTime = "45+"
					if(goTime<1) goTime = "1";
					tr.cells[3].innerHTML = goTime + "<img src='/etc/images/taocai/in.gif'>";
					tr.cells[5].style.color = "blue";
					break;
			case "2":
					tr.cells[3].innerHTML=state_ch[parseInt(D[1])+14].split(",")[m_language];
					tr.cells[5].style.color = "blue";
					break;
			case "3":
				    var t = A[matchindex][12].split(",");
        			var t2 = new Date(t[0],t[1],t[2],t[3],t[4],t[5]);
		        	goTime = Math.floor((new Date()-t2-difftime)/60000)+46;
					if(goTime>90) goTime = "90+";
					if(goTime<46) goTime = "46";
					tr.cells[3].innerHTML = goTime + "<img src='/etc/images/taocai/in.gif'>";
					tr.cells[5].style.color = "blue";
					break;
			case "-1":
					tr.cells[3].innerHTML=state_ch[parseInt(D[1])+14].split(",")[m_language];
					tr.cells[5].style.color = "red";
					window.setTimeout("MoveToBottom(" + D[0] + ")",25000);
					break;
			default:
					tr.cells[3].innerHTML=state_ch[parseInt(D[1])+14].split(",")[m_language];
					MoveToBottom(D[0]);
					break;			
   			}
		}

		//比分变化
		switch(A[matchindex][13]){
			case "0":
				tr.cells[5].innerHTML="-";
				break;
			case "1":			
				tr.cells[5].innerHTML=A[matchindex][14] + "-" + A[matchindex][15];
				break;
			case "-11":
			case "-14":
				tr.cells[5].innerHTML="-";
				tr.cells[7].innerHTML="-";
				break;
			default:  //2 3 -1 -12 -13			
				tr.cells[5].innerHTML=A[matchindex][14] + "-" + A[matchindex][15];
				tr.cells[7].innerHTML=A[matchindex][16] + "-" + A[matchindex][17];
				tr.cells[7].style.color="red";
				break;
		}
		
		if(scorechange){ //如果比分变化
			ShowFlash(D[0],matchindex);//声音提示
			if(tr.style.display!="none"){//如果该行不为隐藏
				hometeam=A[matchindex][5+m_language].replace("<font color=#880000>(中)</font>"," 中").substring(0,7);
				guestteam=A[matchindex][8+m_language].substring(0,7);
				sclassname=A[matchindex][2+m_language];
				if(score1change){				
				    hometeam="<font color=red>" + hometeam +"</font>";
				    score1="<font color=red>" + D[2] +"</font>";
				    score2="<font color=blue>" + D[3] +"</font>";		    
				}
				if(score2change){
				    guestteam="<font color=red>" + guestteam + "<font>";
				    score1="<font color=blue>" + D[2]+"</font>";
				    score2="<font color=red>" + D[3] +"</font>";
				}	
			
				if(windowCheck && parseInt(D[1])>=-1){
				   winStr+= '<tr><td width="110" height="20" align="center" bgcolor="#F0F9FD">' + sclassname +'</td><td bgcolor="#F0F9FD"> ' + tr.cells[3].innerHTML + '</td><td bgcolor="#F0F9FD"><b>'+ hometeam +'</b></td><td width="80" align="center" bgcolor="#F0F9FD" class="weight">' + score1 + '-' + score2 + '</td><td bgcolor="#F0F9FD"><b>' + guestteam +'</b></td></tr>';
				   matchNum=matchNum+1
				}
			}
		}//scorechange
    }
    if(matchNum>0){
			ShowCHWindow(winStr,matchNum);
			matchNum = 0;
	}
}

function ShowFlash(id,n){
    try{
    	//alert("soundCheck="+soundCheck);
    	//alert("parseInt(A[n][13]="+parseInt(A[n][13]));
	    if(soundCheck && parseInt(A[n][13])>=-1){
	    	//alert(document.getElementById("tr1_" + id).style.display);
		    if(document.getElementById("tr1_" + id).style.display!="none"){
		       document.getElementById("sound").innerHTML=flash_sound[soundid] ;
		    }
	    }
	}
	catch(e){};
    window.setTimeout("timecolors(" + id +")",60000);
}
	
function timecolors(matchid){
	try{
	    var tr=document.getElementById("tr1_" + matchid);
        tr.cells[4].style.backgroundColor="";
        tr.cells[6].style.backgroundColor="";
    }
    catch(e){}
}

//'按比赛状态显示(单栏)
function ShowMatchByMatchState(n){
    var i,j;
	var hh=0;
	var trs=document.getElementById("table_live").getElementsByTagName("tr");
	for(var i=1; i<trs.length;i++){	
	    if(trs[i].getAttribute("index")!=null){
	        trs[i].style.display="none";
	        trs[i+1].style.display="none";
	    }
	} 

	for(var i=1;i<=matchcount;i++){
		if(n==1 && parseInt(A[i][13])>0 || n==2 && A[i][13]=="-1" || n==3 && A[i][13]=="0" || n==4 && A[i][28]=="True")
		{
			document.getElementById("tr1_" +  A[i][0]).style.display="";
			var _xiangxineirong = ""
			if(A[i][30]!=""){
				_xiangxineirong = A[i][30].replace(/<a.*?>(.*)<\/a>/ig,"");//所有超链接内的内容都替换成空格
				_xiangxineirong = _xiangxineirong.replace("<br>","");
			}
			if(_xiangxineirong!=""){
				document.getElementById("tr2_" +  A[i][0]).style.display="";
			}						
			hh=hh+1;
		}		
	}
	document.getElementById("hiddencount").innerHTML=matchcount -hh;
    
	if(n==1){
		$("#playType_all").removeClass(); 
		$("#playType_not").removeClass();
		$("#playType_ing").removeClass();
		$("#playType_end").removeClass(); 
		$("#playType_ing").addClass("ftScorePlayType");  
	}else if(n==2){
		$("#playType_all").removeClass(); 
		$("#playType_not").removeClass();
		$("#playType_ing").removeClass();
		$("#playType_end").removeClass(); 
		$("#playType_end").addClass("ftScorePlayType");  
	}else if(n==3){
		$("#playType_all").removeClass(); 
		$("#playType_not").removeClass();
		$("#playType_ing").removeClass();
		$("#playType_end").removeClass(); 
		$("#playType_not").addClass("ftScorePlayType");  
	}
}


//'按比赛状态显示(双栏)
function ShowMatchByMatchState2(n){
    var i,j;
	var hh=0;
	var trs1=document.getElementById("table_live1").getElementsByTagName("tr");
	for(var i=1; i<trs1.length;i++){	
	    if(trs1[i].getAttribute("index")!=null){
	        trs1[i].style.display="none";
	        trs1[i+1].style.display="none";
	    }
	} 
	
	var trs2=document.getElementById("table_live2").getElementsByTagName("tr");
	for(var i=1; i<trs2.length;i++){	
	    if(trs2[i].getAttribute("index")!=null){
	        trs2[i].style.display="none";
	        trs2[i+1].style.display="none";
	    }
	} 

	for(var i=1;i<=matchcount;i++){
		if(n==1 && parseInt(A[i][13])>0 || n==2 && A[i][13]=="-1" || n==3 && A[i][13]=="0" || n==4 && A[i][28]=="True")
		{
			document.getElementById("tr1_" +  A[i][0]).style.display="";
			var _xiangxineirong = ""
			if(A[i][30]!=""){
				_xiangxineirong = A[i][30].replace(/<a.*?>(.*)<\/a>/ig,"");//所有超链接内的内容都替换成空格
				_xiangxineirong = _xiangxineirong.replace("<br>","");
			}
			if(_xiangxineirong!=""){
				document.getElementById("tr2_" +  A[i][0]).style.display="";
			}					
			hh=hh+1;
		}		
	}
	document.getElementById("hiddencount").innerHTML=matchcount -hh;
    
	if(n==1){
		$("#playType_all").removeClass(); 
		$("#playType_not").removeClass();
		$("#playType_ing").removeClass();
		$("#playType_end").removeClass(); 
		$("#playType_ing").addClass("ftScorePlayType");  
	}else if(n==2){
		$("#playType_all").removeClass(); 
		$("#playType_not").removeClass();
		$("#playType_ing").removeClass();
		$("#playType_end").removeClass(); 
		$("#playType_end").addClass("ftScorePlayType");  
	}else if(n==3){
		$("#playType_all").removeClass(); 
		$("#playType_not").removeClass();
		$("#playType_ing").removeClass();
		$("#playType_end").removeClass(); 
		$("#playType_not").addClass("ftScorePlayType");  
	}
}


function ShowWord(){//是否展示文字
	if(document.getElementById("WordCheck").checked==true)
	{
		for(var i=1;i<=matchcount;i++)
			if(A[i][30]!="") document.getElementById("tr2_" +  A[i][0]).style.display="";
    }
	else
	{
		for(var i=1;i<=matchcount;i++)
			document.getElementById("tr2_" +  A[i][0]).style.display="none";
	}
}


function ShowTeamOrder(){//是否展示球队排名
	if(document.getElementById("TeamOrderCheck").checked){
		for(var i=1;i<=matchcount;i++){
			if(A[i][22]!="") document.getElementById("horder_" + A[i][0]).innerHTML="<font color=#444444><sup>["+ A[i][22] +"]</sup></font>";
			if(A[i][23]!="") document.getElementById("gorder_" + A[i][0]).innerHTML="<font color=#444444><sup>["+ A[i][23] +"]</sup></font>";
		}
	}
	else{
		for(var i=1;i<=matchcount;i++){
			document.getElementById("horder_" +  A[i][0]).innerHTML="";
			document.getElementById("gorder_" +  A[i][0]).innerHTML="";
		}
	}
}





function SelectOtherLeague(){//反选
    var inputs=document.getElementById("myleague").getElementsByTagName("input");
    var hh=0;
	for(var i=0;i<inputs.length;i++){
        if(inputs[i].checked){
           inputs[i].checked=false;
           for(var j=1;j<=matchcount;j++){
              if(A[j][2]==B[i+1][0]){
                 document.getElementById("tr1_" +  A[j][0]).style.display="none";
                 var _xiangxineirong = ""
				 if(A[j][30]!=""){
					_xiangxineirong = A[j][30].replace(/<a.*?>(.*)<\/a>/ig,"");
					_xiangxineirong = _xiangxineirong.replace("<br>","");
				 }
                 if(_xiangxineirong!="") document.getElementById("tr2_" +  A[j][0]).style.display="none";
			 	 hh=hh+1;
			 	 if(hiddenID.indexOf("_"+A[j][0] + "_")==-1) hiddenID+=A[j][0] + "_";
              }
           }
        } 
        else{
           inputs[i].checked=true;
           for(var j=1;j<=matchcount;j++){
              if(A[j][2]==B[i+1][0]){
              	 document.getElementById("tr1_" +  A[j][0]).style.display="";  
              	 var _xiangxineirong = ""
				 if(A[j][30]!=""){
					_xiangxineirong = A[j][30].replace(/<a.*?>(.*)<\/a>/ig,"");
					_xiangxineirong = _xiangxineirong.replace("<br>","");
				 }               
                 if(_xiangxineirong!="") document.getElementById("tr2_" +  A[j][0]).style.display="";
                 hiddenID=hiddenID.replace("_"+A[j][0] + "_","_")
              }
           }  
        }
    }
    document.getElementById("hiddencount").innerHTML=hh;
	writeCookie("ecp888FtScore_hiddenID", hiddenID);    
}

function CheckLeague(i){//选择赛事（隐藏或显示比赛）
	var hh=parseInt(document.getElementById("hiddencount").innerHTML);
    if(document.getElementById("checkboxleague_" +  i).checked){
       for(var j=1;j<=matchcount;j++){
              if(A[j][2]==B[i][0]){
                 document.getElementById("tr1_" +  A[j][0]).style.display=""; 
                 var _xiangxineirong = ""
				 if(A[j][30]!=""){
					_xiangxineirong = A[j][30].replace(/<a.*?>(.*)<\/a>/ig,"");
					_xiangxineirong = _xiangxineirong.replace("<br>","");
				 }                    
                 if(_xiangxineirong!="") document.getElementById("tr2_" +  A[j][0]).style.display="";
                 hh--;
                 hiddenID=hiddenID.replace("_"+A[j][0] + "_","_")
              }
       }
    }else{
       for(var j=1;j<=matchcount;j++){
              if(A[j][2]==B[i][0]){
                 document.getElementById("tr1_" +  A[j][0]).style.display="none";
                  var _xiangxineirong = ""
				 if(A[j][30]!=""){
					_xiangxineirong = A[j][30].replace(/<a.*?>(.*)<\/a>/ig,"");
					_xiangxineirong = _xiangxineirong.replace("<br>","");
				 }  
                 if(_xiangxineirong!="") document.getElementById("tr2_" +  A[j][0]).style.display="none";
			 	 hh++;
			 	 if(hiddenID.indexOf("_"+A[j][0] + "_")==-1) hiddenID+=A[j][0] + "_";
              }
       }
   }
   document.getElementById("hiddencount").innerHTML=hh;
   writeCookie("ecp888FtScore_hiddenID", hiddenID);    
}

function hidematch(i){//选择比赛（隐藏比赛）
    document.getElementById("tr1_" +  A[i][0]).style.display="none";
    document.getElementById("tr2_" +  A[i][0]).style.display="none";
	document.getElementById("hiddencount").innerHTML=parseInt(document.getElementById("hiddencount").innerHTML)+1;
	if(hiddenID.indexOf("_"+A[i][0] + "_")==-1) hiddenID+=A[i][0] + "_";
	writeCookie("ecp888FtScore_hiddenID", hiddenID); 
	var flagLeague=0;
	 for(var j=1;j<=matchcount;j++){
           if(A[j][2]==A[i][2]){
        		if(hiddenID.indexOf("_"+A[j][0] + "_")==-1) flagLeague++;
           }
	}
	if(flagLeague==0){
		for(var k=1;k<=sclasscount;k++){
			if(A[i][2]==B[k][0]){
				document.getElementById("checkboxleague_" +  k).checked=false;
			}
		}
	}   
}

function MoveToBottom(m){
    try{
        document.getElementById("tr1_" +  m).parentElement.insertAdjacentElement("BeforeEnd",document.getElementById("tr1_" +  m));
        document.getElementById("tr2_" +  m).parentElement.insertAdjacentElement("BeforeEnd",document.getElementById("tr2_" +  m));
    }catch(e){}
}


function check(){
	if (oldUpdateTime == lastUpdateTime && oldUpdateTime !=""){
		window.location.reload();
    }
	oldUpdateTime = lastUpdateTime;
	window.setTimeout("check()" , 300000);
}

function LoadLiveFile()
{
	//alert("--------------------------------------begin");
    var allDate=document.getElementById("allDate");
    var  s=document.createElement("script");   
    s.type="text/javascript";
    s.charset="gb2312";   
    s.src="http://live.bet007.com/vbsxml/bfdata.js?" +Date.parse(new Date());
    allDate.removeChild(allDate.firstChild);
    allDate.appendChild(s,"script"); 
    window.setTimeout("LoadLiveFile()",3600*1000);//1小时加载一次
}
function LoadDetailFile()
{
    var detail=document.getElementById("span_detail");
    var  s=document.createElement("script");   
    s.type="text/javascript";
    s.charset="gb2312";    
    s.src="http://live.bet007.com/vbsxml/detail.js?" +Date.parse(new Date());
    detail.removeChild(detail.firstChild);
    detail.appendChild(s,"script");      
    loadDetailFileTime=new Date();
}
LoadLiveFile();//加载主js文件
window.setTimeout("gettime()",5000);//半分钟刷新一次，判断是否有改变
window.setTimeout("check()" , 300000);//5分钟刷新一次判断是否连接








//赛前分析
function gotoShowAnalysis(gliveId,pTime){
	window.open("/football/gotoPlayAnalysis.action?gliveId="+gliveId+"&pTime="+pTime);
}
//即时比分中的亚指对比
function gotoShowAsiaOdds(gliveId){
	$.ajax({
	  type:"GET",
	  url: "/dataFile/oddsData/Asia/currentMain/"+gliveId+".txt",
	  cache: false,
	  async:false,
	  success: function(msg){
	    	window.open("/football/gotoAsiaOddsCompare.action?gliveId="+gliveId);
	  },
	  error:function(msg){
	  		window.open("/football/gotoAsiaOddsCompareBack.action?gliveId="+gliveId);  
	  }
	});
	
}
//即时比分中的欧指对比
function gotoShowEuropeOdds(gliveId){
	$.ajax({
	  type:"GET",
	  url: "/dataFile/oddsData/Europe/currentMain/"+gliveId+".txt",
	  cache: false,
	  async:false,
	  success: function(msg){
	    	window.open("/football/gotoEuropeOddsCompare.action?gliveId="+gliveId);  
	  },
	  error:function(msg){
	  		window.open("/football/gotoEuropeOddsCompareBack.action?gliveId="+gliveId);
	  }
	});
}

//赛程表中亚指对比
function gotoShowFutureAsiaOdds(gliveId){
	window.open("/football/gotoAsiaOddsCompare.action?gliveId="+gliveId);
}
//赛程表中的欧指对比
function gotoShowFutureEuropeOdds(gliveId){
	window.open("/football/gotoEuropeOddsCompare.action?gliveId="+gliveId);
}
//完场比分中的亚指对比
function gotoShowEndAsiaOdds(gliveId){
	window.open("/football/gotoAsiaOddsCompareBack.action?gliveId="+gliveId);
}
//完场比分中的欧指对比
function gotoShowEndEuropeOdds(gliveId){
	window.open("/football/gotoEuropeOddsCompareBack.action?gliveId="+gliveId);
}
