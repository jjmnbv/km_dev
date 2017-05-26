<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8" />
		<title>康美中药城-邮件内容发送</title>
		<meta name="Keywords" content=""/>
		<meta name="Description" content=""/>
		
	</head>

<style type="text/css">

.ui-list{}
.ui-list-item{}
.ui-list-item-hover{}


.ui-nav{}
.ui-nav-item{}
.ui-nav-item-current{}
.ui-nav-item-child{}
.ui-nav-item-active-child{}


.ui-title{}
.ui-title-cnt{border-bottom:1px solid #ddd;padding-bottom:8px;}
.ui-title-subcnt{padding:10px;background:#f5f5f5;}

.ui-step{height:23px;background:url(images/steps.png) no-repeat;overflow:hidden;}
.ui-step-item{display:inline-block;*display:inline;*zoom:1;width:220px;text-align:center;font-weight:700;font-size:14px;color:#555}
.ui-step-item-current{color:#fff;}
.ui-step2{background-position:0 -23px}
.ui-step3{background-position:0 -46px;}
.ui-step-item-first{}
.ui-step-item-last{}


.ui-repeater{}
.ui-repeater-item{border-top:1px solid #ddd;border-right:1px solid #ddd;border-left:1px solid #ddd;padding:10px;width:60px;background:#f5f5f5;}
.ui-repeater-item-last{border-bottom:1px solid #ddd;}
.ui-repeater-item-current{background:#fff;border-right:none;}

.ui-page{display:inline-block;_display:inline;font-family:"Tahoma";height:28px;line-height:26px;white-space:nowrap;}
.ui-page-item{
	float:left;
	display:inline-block;
	border:1px solid #ccc;
	color:#7e7e7e;
	font-family:Arial,"\5b8b\4f53";
	font-size:13px;
	height:26px;
	line-height:26px;
	margin-right:5px;
	min-width:15px;
	padding:0 8px;
	border-radius: 2px;
	text-align:center;
	vertical-align:middle;
	white-space:nowrap;
}
.ui-page .ui-page-item-current{background:#379945;border:1px solid #379945;color:#fff;cursor:default;font-weight:700;text-decoration:none;}
.ui-page a:hover{border:1px solid #379945;color:#379945;text-decoration:none;}
.ui-page .ui-page-item-current:hover{border:1px solid #4690cd;color:#fff;}
.ui-page .ui-page-disabled,.ui-page .ui-page-disabled:hover{background: #fff;color:#ccc;border:1px solid #ccc;cursor: default;}
.ui-page-text{border:none;}
.ui-page-number{border:1px solid #ccc;font-family: "verdana";text-align:center;height:18px;line-height: 18px;padding:3px 1px;width:30px;margin:0 5px;}
.ui-page-btnconfirm{border:1px solid #ccc;border-radius:2px;color:#333;cursor: pointer;height: 28px;margin-top:0;text-align: center;text-decoration: none;width:51px;margin-left:5px;}

.ui-page-ellipsis{float:left;font-weight:700;margin-right:5px;line-height:13px;}
.ui-page-info{display:inline-block;*display:inline;float:left;padding:1px 0;}
.ui-page-info span{float:left;line-height:22px;margin:0 5px;}
.ui-page-info .fn-text{float:left;width:32px;}
.ui-page-info .page-submit{border:1px solid #7e7e7e;padding:1px 8px;text-align:center;background:#f4f4f4;text-shadow:1px 1px #fff;cursor:pointer;height:24px;line-height:24px;overflow:hidden;zoom;1;vertical-align:middle;}
.ui-page .ui-page-info .page-submit:hover{border:1px solid #feb391;color:#feb391;background:#f7e5ddu}
.ui-page-item-info{color:#999;}
.ui-page-item-prev,.ui-page .ui-page-item-next{left:0;position:relative;top:0;width:47px;}
.ui-page .ui-angle-left{left:20px;}
.ui-page .ui-angele-right{left:26px;}
.ui-page .ui-angle{top:6px;}
.ui-page-item-first{}
.ui-page-item-last{}
.ui-page-item-prev{}
.ui-page-item-next{}

.ui-angle{position:absolute;}
.ui-angle span{border:4px dashed transparent;font-size:0;height:0;overflow:hidden;zoom:1;position:absolute;width:0;}
.ui-angle .ui-angle-outter{left:0;top:0;}
.ui-angle-left span{border-right:5px solid #7e7e7e;}
.ui-angle-left .ui-angle-inner{border-right:5px solid #fff;left:2px;}
.ui-page-item:hover .ui-angle-left span{border-right:5px solid #ff8400;}
.ui-page-item:hover .ui-angle-left .ui-angle-inner{border-right:5px solid #fff;}

.ui-angle-right span{border-left:5px solid #7e7e7e;}
.ui-angle-right .ui-angle-inner{border-left:5px solid #fff;left:-2px;}
.ui-page-item:hover .ui-angle-right span{border-left:5px solid #ff8400;}
.ui-page-item:hover .ui-angle-right .ui-angle-inner{border-left:5px solid #fff;}

.ui-table{margin:0 auto;border:1px solid #f1f0f0;font-size:12px;}
.ui-table caption{font-weight:500;background:#f1f0f0;line-height:33px;height:33px;text-indent:20px;}
.ui-table th,.ui-table td{padding:5px 10px;border:1px solid #f1f0f0;}



.ui-table-common{margin:0 auto;}
.ui-table-common caption{font-weight:500;background:#f1f0f0;line-height:33px;height:33px;text-indent:20px;}
.ui-table-common,.ui-table-common td{border:1px solid #f1f0f0;}
.ui-table-common,.ui-table-common td{padding:5px 0;}
.ui-table-common th{text-align:center;font-weight:500;height:25px;line-height:25px;}
.ui-table-common tbody tr:hover,.ui-table-common tr.hover,.ui-table-common tbody tr.highlight{background:#f5ebcb;}
.ui-table-common tbody tr.even{background:#f5f5f5;}


.ui-table-dialog{min-width:540px;_width:expression((documentElement.clientWidth<540)?'540':'auto');}
.ui-table-dialog caption{font-weight:500;background:#f1f0f0;line-height:33px;height:33px;text-indent:20px;}
.ui-table-dialog{border:1px solid #f1f0f0;}
.ui-table-dialog,.ui-table-dialog th,.ui-table-dialog td{padding:5px 20px;}
.ui-table-dialog td.highlight{background:#f1f0f0;font-weight:700;}


.nav-item-table{background:none repeat scroll 0 0 #fff;white-space:nowrap}
.nav-item-table td,.nav-item-table th{border-right:1px solid #e7e7e7;padding:3px 16px;white-space:nowrap;font-size:12px;}
.nav-item-table th{font-weight:700;padding:0 14px 3px 17px;border-collapse:separate;white-space:nowrap}
.nav-item-table th.last,.nav-item-table td.last{border-right:0}
.nav-item-table a{color:#a0a0a0;display:block;padding-left:3px;position:relative;vertical-align:middle}
.nav-item-table a:hover{background-color:#aaa;border-radius:2px 2px 2px 2px;color:#fff;text-decoration:none;vertical-align:middle}

.ui-button{display:inline-block;vertical-align:middle;}
.ui-button s,.ui-button b,.ui-button u{background:url("images/button_icon.png") no-repeat;cursor:pointer;zoom:1;overflow:hidden;display:inline-block}

.ui-button-1b s,.ui-button-1b b,.ui-button-1b u{width:12px;height:27px;display:inline-block;vertical-align:top;}
.ui-button-1b s{background-position:0 -219px}
.ui-button-1b u{background-position:right -219px}
.ui-button-1b b{width:auto; background-position:-5px -789px;background-repeat:repeat-x;white-space: nowrap;color:#333;font-size:12px; font-weight:700;padding:0 8px;
line-height:26px;overflow:hidden;}
a:hover.ui-button-1b s,.ui-button-1b:focus s{background-position:0 -246px}
a:hover.ui-button-1b u,.ui-button-1b:focus u{background-position:right -246px}
a:hover.ui-button-1b b,.ui-button-1b:focus b{background-position:-5px -816px;}

.ui-button.readonly{cursor:default}
.ui-button.readonly s,a:hover.ui-button-1b.readonly s{background-position:0 -735px;cursor:default}
.ui-button.readonly b,a:hover.ui-button-1b.readonly b{background-position:-5px -735px;color:#787878;cursor:default}
.ui-button.readonly u,a:hover.ui-button-1b.readonly u{background-position:right -735px;cursor:default}

.ui-button-dsb{cursor:text;zoom:1;overflow:hidden;display:inline-block}
.ui-button-dsb s,.ui-button-dsb b,.ui-button-dsb u{width:12px;height:27px;display:inline-block;vertical-align:top;}
.ui-button-dsb  s{background-position:0 -735px}
.ui-button-dsb  u{background-position:right -735px}
.ui-button-dsb  b{width:auto; background-position:-5px -762px;background-repeat:repeat-x;white-space: nowrap;color:#afafaf;font-size:12px; font-weight:700;padding:0 8px;
line-height:25px;overflow:hidden;}
a:hover.ui-button-dsb  s,.ui-button-dsb :focus s{background-position:0 -735px}
a:hover.ui-button-dsb  u,.ui-button-dsb :focus u{background-position:right -735px}
a:hover.ui-button-dsb  b,.ui-button-dsb :focus b{background-position:-5px -762px;}

.ui-button-icon-left {}
.ui-button-icon-left .ui-icon-rarr {}
.ui-button-icon-left .ui-button-text {}

.ui-button-icon-right {}
.ui-button-icon-right .ui-icon-rarr {}
.ui-button-icon-right .ui-button-text {}

.ui-button-hover {}
.ui-button-disabled {}
.ui-state-selected {}



.ui-input {}
.ui-input-hover {}
.ui-input-focus {}
.ui-input-disabled {}

.ui-label {}
.ui-label-hover {}
.ui-label-focus {}

.ui-textarea {}
.ui-textarea-hover {}
.ui-textarea-disabled {}


.ui-form {}
.ui-form legend {}

.ui-form-item{}
.ui-form-required {}
.ui-form-explain {}

.ui-form-hover {}
.ui-form-focus {}
.ui-form-error {}
.ui-form-success {}
.ui-form-warn {}
.ui-form-loading {}
.ui-message {}
.ui-message-cnt {}

.ui-message-error {}
.ui-message-warn {}
.ui-message-success {}

.ui-iptmesg-icon,.ui-iptmesg-cnt .larr,.ui-iptmesg-cnt .rarr,.ui-iptmesg-cnt .uarr,.ui-iptmesg-cnt .darr{position:absolute;display:inline-block;background:url(images/common_icon.png) no-repeat -999em -999em;}
.ui-iptmesg{display:inline-block;*display:inline;}
.ui-iptmesg .ui-iptmesg-icon{margin-top:8px;}
.ui-iptmesg .ui-iptmesg-cnt{display:inline-block;border-radius:3px;padding:3px 6px 4px;white-space:nowrap;position:absolute;margin-left:25px;}

.ui-iptmesg-error .ui-iptmesg-icon{background-position:-14px -12px;width:14px;height:14px;}
.ui-iptmesg-error .larr{display:inline-block;background-position:-7px -43px;width:7px;height:11px;position:absolute;left:-7px;top:7px;_font-size:0}
.ui-iptmesg-error .ui-iptmesg-cnt{color:#c33;background:#fee5e0;border:1px solid #ffa997;z-index:5;}

.ui-iptmesg-ok .ui-iptmesg-icon{background-position:0 -12px;width:14px;height:14px;}
.ui-iptmesg-ok .larr{display:inline-block;background-position:0 -43px;width:7px;height:11px;position:absolute;left:-7px;top:7px;_font-size:0}
.ui-iptmesg-ok .ui-iptmesg-cnt{color:#390;background:#e7ffe5;border:1px solid #99da68;z-index:5;}

.ui-iptmesg-disabled .ui-iptmesg-icon{visibility:hidden;width:14px;height:14px;}
.ui-iptmesg-disabled .larr{display:inline-block;background-position:-14px -43px;width:7px;height:11px;position:absolute;left:-7px;top:7px;_font-size:0}
.ui-iptmesg-disabled .ui-iptmesg-cnt{color:#999 !important;background:#fff !important;;border:1px solid #ccc !important;z-index:5;}

.ui-iptmesg-warning .ui-iptmesg-icon{background-position:-28px -12px;width:14px;height:14px;}
.ui-iptmesg-warning .larr{display:inline-block;background-position:-21px -43px;width:7px;height:11px;position:absolute;left:-7px;top:7px;_font-size:0}
.ui-iptmesg-warning .ui-iptmesg-cnt{color:#c60 !important;background:#ffe5a8 !important;;border:1px solid #ecc57a !important;z-index:5;}


.ui-toptips{position: fixed;_position: absolute;top:0;_top:expression(eval(documentElement.scrollTop));z-index:20;width:100%;text-align:center;}
.ui-toptips-cnt{display:inline-block;_display:inline;padding:3px 30px 7px 10px;border-radius:5px;color:#333;font-size:14px;font-weight:700;position:relative;*zoom:1}
.ui-toptips-close{background:url("images/common_icon.png") no-repeat 0 0;display:block;height:17px;width:17px;overflow:hidden;*zoom:1;position:absolute;right:8px;top:7px;}
.ui-toptips-true{background:#cfc;border:1px solid #3c0;}
.ui-toptips-true .ui-toptips-close{background-position:0 -54px;}
.ui-toptips-true .ui-toptips-close:hover{background-position:-17px -54px;}
.ui-toptips-error{background:#fcc;border:1px solid #f99;}
.ui-toptips-error .ui-toptips-close{background-position:-34 -54px;}
.ui-toptips-error .ui-toptips-close:hover{background-position:-51px -54px;}
.ui-toptips-warning{background:#ffe5a8;border:1px solid #ecc57a}
.ui-toptips-warning .ui-toptips-close{background-position:-68 -54px;}
.ui-toptips-warning .ui-toptips-close:hover{background-position:-85px -54px;}

.ui-tipwrap{position:absolute;z-index:10;}
.ui-tipwrap-cnt{display:inline-block;*display:inline;*zoom:1;border:1px solid #cead8c;border-radius:5px;zoom:1;z-index:5;background:#ffc;padding:11px 37px 12px 63px;min-height:44px;
	-webkit-box-shadow:0 0 6px rgba(51,51,51,0.3);
	-moz-box-shadow:0 0 6px rgba(51,51,51,0.3);
	box-shadow:0 0 6px rgba(51,51,51,0.3);}
.ui-tipwrap-cnt .ui-tips-logo{display:block;width:39px;height:41px;position:absolute;top:14px;left:18px;background:url("images/common_icon.png") no-repeat 0 -270px;}
.ui-tipwrap-cnt .ui-tips-ok{display:block;text-align:right;color:#963;margin-top:5px;}
.ui-tips-close{display:block;width:15px;height:15px;position:absolute;top:6px;right:8px;cursor:pointer;background:url("images/common_icon.png") no-repeat 0 -255px;}
.ui-tips-close:hover{background-position:-15px -255px}
.ui-tips-icon{display:inline-block;height:11px;width:7px;position:absolute;overflow:hidden;zoom;1;background:url("images/arrow.png") no-repeat 0 0;}
.ui-tips-icon-uarr{width:11px;height:7px;background-position:-28px 0;left:15px;top:-6px;}
.ui-tips-icon-darr{width:11px;height:7px;background-position:-16px 0;right:15px;bottom:-6px;}
.ui-tips-icon-rarr{background-position:-8px 0;right:-6px;top:15px;}
.ui-tips-icon-larr{background-position:0 0;left:-6px;top:15px;}

.ui-tab {}
.ui-tab-trigger {height:32px;line-height: 32px;}
.ui-tab-trigger-item {float:left;text-align:center;border-right:1px solid #dbdbdb;margin-right:-1px;}
.ui-tab-trigger-item a{display:inline-block;width:100%;}
.ui-tab-trigger-item-current{}

.ui-tab-cnt {padding:10px;}
.ui-tab-cnt-item {}
.ui-tab-cnt-item-current {}

.ui-tab-user{padding:3px 0 0 15px;background:#f4f4f4;font-size:14px;font-weight:700;letter-spacing:-0.31em;*letter-spacing:normal;word-spacing:-0.43em;border-bottom:1px solid #ddd;}
.ui-tab-user-item{display:inline-block;*display:inline;zoom:1;letter-spacing:normal;word-spacing:normal;}
.ui-tab-user-item a{display:inline-block;height:30px;line-height:30px;margin-bottom:-1px;border-bottom:1px solid #ddd;padding:0 20px;margin-right:10px;text-align:center;vertical-align:bottom;color:#2A7CA2;}
.ui-tab-user-item a:hover{background:#ddd;text-decoration:none;border-radius:5px 5px 0 0;}
.ui-tab-user-item-current a{border:1px solid #ddd;border-radius:5px 5px 0 0;background:#fff;border-bottom:1px solid #fff;cursor:text}
.ui-tab-user-item-current a:hover{background:#fff;}

.ui-dropdown {}
.ui-dropdown-header {}
.ui-dropdown-container {}
.ui-dropdown-active .ui-dropdown-header  {}
.ui-dropdown-active .ui-dropdown-container {}

.ui-tip {}
.ui-tip-cnt {}

.ui-link,.auth-link,.partner{
		letter-spacing:-0.31em;
		*letter-spacing:normal;
		word-spacing:-0.41em;
}
.ui-link-item,.auth-link-item,.partner-item{    
	display: inline-block;
   *display: inline;
   *zoom:1;
   vertical-align:top;
   letter-spacing:normal;
   word-spacing:normal;
}

.ui-link-item,.ui-link-item a{color:#a0a0a0;_position:relative;}
.ui-link-item a:hover{color:#a0a0a0;text-decoration:underline;}

.i-topbar{border-bottom: 1px solid #eee;background: #f7f7f7;height:30px;line-height: 30px;}
.i-topbar .loginbar{width:400px;}
.topmenu{height: 30px;line-height: 30px;}
.topmenu-item{float:left;padding:0 10px;position: relative;zoom:1;}
.topmenu-item b{position: absolute;top:9px;left:0;width:0;height:12px;border-left:1px solid #ddd;overflow: hidden;zoom:1;}
.topmenu-item-phone,.topmenu-item-collect{padding-left:28px;}
.topmenu-item-phone strong{color:#379945;font-family:"tahoma";}
.ico-phone,.ico-collect,.ico-app{display:inline-block;position:absolute;left:10px;top:9px;width:13px;height:13px;overflow:hidden;zoom:1;background: url("/images/common/common_icon.png") 0 0 no-repeat;}
.ico-phone{background-position:-14px -1px;}
.ico-collect{background-position:-28px -1px;}

.i-head{height:65px;padding:15px 0;}
.i-head .logo{width:290px;}
.i-search{width:510px;padding-right:126px;}
.i-search .search-cont{border:3px solid #379945;position: relative;zoom:1;height:32px;z-index:50;}
.i-search .keyslist{position:absolute;top:34px;left:-3px;background:#fff;border:1px solid #379945;width:426px;}
.i-search .keyslist .keyslist-item a{display:block;padding:6px 10px;overflow:hidden;zoom:1;}
.i-search .keyslist .keyslist-item a:hover{background:#ededed;}

.i-search .search-cont .form{height:32px;overflow: hidden;}
.i-search .text{float:left;width:416px;height:22px;padding:5px;font-size:14px;line-height: 22px;border:0;}
.i-search .button{float:left;width:78px;height:32px;background: #379945;font-size:14px;font-weight: 700;color:#fff;border:0;cursor: pointer;}
/*.i-search .search-hotwords{height:18px;color:#999;margin-top:5px;overflow: hidden;zoom:1;}
.i-search .search-hotwords strong{font-weight: 400;}
.i-search .search-hotwords a{margin-right:10px;color:#999;}
.i-search .search-hotwords a:hover{color:#e4393c;}*/

.suggest-div {
	background: #fff;
	border: 2px solid #379945;
	position: absolute;
	display: none;
	margin: 0px;
	left:-3px;
	z-index:100;
}

.suggest-div ul {
	margin: 0px;
	padding: 0px;
	list-style: none;
}

.suggest-div li {
	margin: 0px;
	line-height: 25px;
	height: 25px;
	color: #666;
	padding: 2px 10px;
	cursor: pointer;
}


.suggest-div li span.left {
	margin: 0px;
	padding: 0px;
	float: left;
	display: block;
}

.suggest-div li span.right {
	margin: 0px;
	padding: 0px;
	float: right;
	display: block;
	text-align: left;
}

.suggest-div-hover{
	color:#E4393C;
	background: #ededed;
}
.i-head .security{width:314px;height:48px;overflow: hidden;background: url("/images/common/common_icon.png") 0 -220px no-repeat;}

 
.i-shorthead{height:65px;padding:15px 0;border-bottom:2px solid #379945;}
.i-shorthead .logo{width:260px;}

.i-nav{background: #379945;height:40px;}
.i-nav .nav-cont{position: relative;zoom:1;z-index:30;padding-left:210px;}
.i-nav .nav-cont .sort{position: absolute;top:-5px;left:-5px;overflow:visible;width:220px;height:45px;background:url("/images/common/common_icon.png") -329px -220px no-repeat;;}
.i-nav .nav-cont .sort-hover .sort-list{display:block;}
.i-nav .sort-link{line-height: 40px;font-size:16px;padding:0 15px;margin-top:4px;}
.i-nav .sort-link a{display:block;color:#fff;text-shadow:1px 1px #196c26;}
.i-nav .sort-link a:hover{text-decoration: none;}
.i-nav .sort-link a b{position:absolute;right:20px;top:20px;display:block;width:11px;height:11px;background:url("/images/common/common_icon.png") 0 -50px no-repeat; }
.i-nav .sort-list{position:relative;zoom:1;display:none;border:2px solid #379945;background:#fff;margin:0 5px;box-shadow:1px 1px 3px rgba(51, 51, 51, 0.3);}
.i-nav .sort-list-item{position:relative;height:34px;line-height: 34px;border-bottom:1px solid #e2dedf;}
.i-nav .sort-list-title{position:relative;zoom:1;padding:0 20px 0 40px;}
.i-nav .sort-list-title a{font-size: 14px;font-weight: 400;color:#333;}
.i-nav .sort-list-title b{position:absolute;right:16px;top:14px;background:url("/images/common/common_icon.png") -53px -50px no-repeat;width:5px;height:7px;display: block;overflow: hidden;zoom:1;}
.ico-sort1,.ico-sort2,.ico-sort3,.ico-sort4,.ico-sort5,.ico-sort6,.ico-sort7,.ico-sort8,.ico-sort9,.ico-sort10,.ico-sort11{position:absolute;left:16px;top:7px;display:block;width:20px;height:20px;overflow:hidden;zoom:1;background:url("/images/common/common_icon.png") 0 0 no-repeat;}
.ico-sort1{background-position: 0 -130px;}
.ico-sort2{background-position: -30px -130px;}
.ico-sort3{background-position: -60px -130px;}
.ico-sort4{background-position: -90px -130px;}
.ico-sort5{background-position: -120px -130px;}
.ico-sort6{background-position: -150px -130px;}
.ico-sort7{background-position: -180px -130px;}
.ico-sort8{background-position: -210px -130px;}
.ico-sort9{background-position: -240px -130px;}
.ico-sort10{background-position: -270px -130px;}
.ico-sort11{background-position: -300px -130px;}
.i-nav .sort-list-item.sort-list-hover{border-bottom:1px solid #379945;height:35px;}
.i-nav .sort-list-hover .sort-list-title{z-index: 12;margin:-1px -2px 0 0; background:#fff;height:33px;border-top:1px solid #379945;}
.i-nav .sort-list-hover .sort-list-title a{text-decoration: none;color:#379945;font-weight: 700;}
.i-nav .sort-list-hover .sort-list-title b{background-position: -66px -50px;right:18px;}
.i-nav .sortsub{position:absolute;top:0;left:206px;border:1px solid #379945;width:800px;background:#fff;}
.i-nav .sortsub .sortsub-l{float:left;width:570px;padding:10px;}
.i-nav .sortsub .sortsub-item{border-bottom:1px dotted #dbdbdb;overflow: hidden;zoom:1;padding:5px 0 10px 90px;}
.i-nav .sortsub .sortsub-item dt{width:80px;height:20px;float:left;margin-left:-80px;line-height: 20px;font-weight: 700;_display:inline;zoom:1;}
.i-nav .sortsub .sortsub-item dd{float:left;height:22px;line-height: 22px;overflow: hidden;width:75px;}
.i-nav .sortsub .sortsub-r{float:right;width:200px;}
.i-nav .sortsub .sortsub-hotbrand{padding:15px 15px 15px 0;overflow: hidden;zoom:1;}

.i-nav .navitems{overflow:hidden;zoom:1;width:650px;float:left;}
.i-nav .navitems li{float:left; position: relative; width:85px;height:40px;line-height: 40px;text-align: center;}
.i-nav .navitems li a{display:inline-block;height:40px;width:85px;color:#fff;font: 700 15px/40px "microsoft yahei";text-decoration: none;}
.i-nav .curr a,.i-nav .hover a{background: #237f31;}
.i-nav .minicart{padding:2px 0;height:36px;line-height: 36px;width:260px;}
.i-nav .minicart .minicart-go{display:block;width:71px;height:36px;overflow: hidden;background: url("/images/common/common_icon.png") 0 -290px no-repeat;}
.i-nav .minicart .minicart-go:hover{background: url("/images/common/common_icon.png") -81px -290px no-repeat;}
.i-nav .minicart-number{position:relative;zoom:1;padding:0 20px 0 35px;width:85px;text-align:left;background: url("/images/common/common_icon.png") -162px -290px no-repeat;cursor: pointer;}
.i-nav .minicart-number .number{color:#e4393c;font-weight: 700;font-family:"tahoma";padding:0 2px;}
.i-nav .minicart-list{position: absolute;top:35px;right:0;border:1px solid #ddd;width:320px;background: #fff;box-shadow:0 0 3px rgba(51, 51, 51, 0.3);padding:10px 15px;}
.minicart-list .nogoods{color:#999;overflow: hidden;zoom:1;}
.minicart-list .nogoods b{float:left;display:block;width:42px;height:42px;margin-right:10px;background: url("/images/common/common_icon.png") 0 -70px no-repeat;}
.minicart-list .nogoods p{line-height: 20px;}
.minicart-list .nogoods p a{color:#379945;}
.settleup .settleup-list{height: auto !important;max-height: 140px;_height:140px;overflow-y:auto;margin-right:-10px;}
.settleup .settleup-list li{border-bottom:1px dotted #dbdbdb;line-height: 17px;overflow: hidden;padding:8px 10px 8px 0;vertical-align: bottom;}
.settleup .settleup-list .settleup-img{border:1px solid #dbdbdb;width:50px;height:50px;margin-right:10px;padding:0;font-size:0;float:left;}
.settleup .settleup-list .settleup-name{float:left;height:52px;width: 150px;overflow: hidden;zoom:1;}
.settleup .settleup-list .settleup-detail{float:right;text-align: right;}
.settleup .settleup-detail .price strong{font-family: "verdana";color:#e4393c;margin-right:5px;}
.settleup .settleup-detail .delete{color:#005EA7;}
.settleup .checkout{text-align: right;background: #f5f5f5;}
.settleup .checkout b{color:#e4393c;margin:0 5px;}
.settleup .checkout strong{color:#e4393c;font-family: "verdana"}
.l-w1000 .i-nav .navitems{ width:500px;} 


.i-footer{border-top: 1px solid #ddd;background: #fafafa;text-align: center;margin-top:20px;}
.i-footer .service{text-align: left;padding:20px 18px;}
.i-footer .service-item{
	width:190px;
	display: inline-block;
   *display: inline;
   *zoom:1;
   vertical-align:top;
   letter-spacing:normal;
   word-spacing:normal;
}
.i-footer .service-item-title{position:relative;zoom:1;padding:6px 0 6px 45px;font-family:"microsoft yahei";font-size:18px;line-height: 18px; }
.i-footer .ico-fore{position: absolute;top:0;left:0;width:40px;height:40px; background: url("/images/common/common_icon.png") 0 -160px no-repeat;}
.i-footer .ico-fore.fore2{background-position: -45px -160px;}
.i-footer .ico-fore.fore3{background-position: -90px -160px;}
.i-footer .ico-fore.fore4{background-position: -135px -160px;}
.i-footer .ico-fore.fore5{background-position: -180px -160px;}
.i-footer .service-item dd{padding-left:50px;line-height: 24px;}
.i-footer .service-item-first dd{padding-left:0;}
.i-footer .links{border-top:1px dotted #ddd;margin-top:10px;padding:15px 0;}
.i-footer .links a{margin:0 10px;}
.i-footer .copyright{line-height: 22px;}


.i-feedback{position: fixed;_position: absolute;right:0;bottom: 250px;z-index:30;background:#379945;padding:10px 0;border-top-left-radius:6px; border-bottom-left-radius: 6px;box-shadow:-1px -1px 3px rgba(51, 51, 51, 0.3);}
.i-feedback .toppanel{width:39px;}
.i-feedback .toppanel .backtop{display:block;text-indent: -999em;width:39px;height:39px;overflow:hidden;background: url("/images/common/common_icon.png") -555px -160px no-repeat;}
.i-feedback .toppanel .backtop:hover{background-position: -611px -160px;}
.i-feedback .toppanel .services{display:block;text-indent: -999em;width:39px;height:72px;overflow:hidden;background:url("/images/common/common_icon.png") -611px -269px no-repeat; vertical-align: top;}
.i-feedback .toppanel .services:hover{background-position: -568px -269px;}
.i-feedback .servipanel{width:115px;}
.i-feedback .servipanel-title{position: relative;zoom:1;margin-top:-5px;color:#fff;font-family: "microsoft yahei";font-size: 14px;padding-left:10px;}
.i-feedback .servipanel-title .close{position:absolute;right:5px;top:2px;text-align:center;display:block;width:16px;height:16px;line-height:14px;font-size:20px;color:#fff;}
.i-feedback .servipanel-title .close:hover{text-decoration: none;background: #007800;}
.i-feedback .servipanel-cont{margin:5px 0 0 5px;padding:5px 8px;background: #fff;border-top-left-radius: 5px;border-bottom-left-radius: 5px;box-shadow:-1px -1px 3px rgba(51, 51, 51, 0.3);}
.i-feedback .servipanel-cont li{border-bottom:1px dotted #dbdbdb;padding:5px 0;line-height: 24px;}
.i-feedback .servipanel-cont li a{display:block;padding-left:25px;background:url("/images/common/common_icon.png") -572px -216px no-repeat;}
.i-feedback .servipanel-ft{margin:5px 0 0 8px;color:#fff;font-family: "verdana";text-shadow:1px 1px #007800;}


.modal-mask{background:#000;height:100%;width:100%;position:fixed;_position: absolute;left:0;top:0;z-index:100;opacity:0.2;}/*IE6下需要固定高度*/
.modal-dialog{background:#379945;position:fixed;_position:absolute;left:50%;top:50%;
_left:expression(eval(documentElement.scrollLeft + documentElement.clientWidth/2));_top:expression(eval(documentElement.scrollTop + documentElement.clientHeight/2));
z-index:101;overflow:hidden;zoom:1;border-radius:5px;box-shadow:0 0 3px rgba(51,51,51,0.3);}
.modal-dialog-hd{height:36px;line-height:36px;color:#fff;}
.modal-dialog-hd .dialog-hd-tit{font-size:14px;font-weight:700;padding-left:8px;}
.modal-dialog-hd .close{display:inline-block;height:15px;width:15px;cursor:pointer;background:url("/images/common/common_icon.png") -390px -50px no-repeat;position: absolute;right:6px;top:12px;}
.modal-dialog-hd .close:hover{background-position: -373px -50px;}
.modal-dialog-bd{margin:0 3px 3px 3px;background:#fff;border-radius:5px;}


.i-breadcrumb{padding:10px 0;}
.i-breadcrumb strong,.i-breadcrumb h1{display:inline;font-family: "microsoft yahei";font-size:18px;font-weight: 700px;line-height: 20px;}
.i-breadcrumb span{padding-left:10px;color:#999;font-family: "宋体";}
.i-breadcrumb span b{font-weight: 700;color:#999;}
.i-breadcrumb span a{margin:0 5px;color:#666;}
.i-breadcrumb span a:hover{color:#E4393C;}


.p-img a:hover{opacity: 0.9;}
.p-img .rebate{font-size: 12px;line-height: 14px;}
.p-img .rebate span{font-size: 20px;}
.p-img{margin:0 5px 5px 5px;text-align: center;width:170px;height:175px;position: relative;background:url("/images/common/common_loading.gif") 50% 50% no-repeat;}
.ico-sale,.ico-saleout{display:block;position:absolute;top:0;right:0;z-index:6;text-align:center;width:48px;height:42px;line-height:28px;font-size:16px;color:#fff;font-family:"microsoft yahei";padding-top:12px;background:url("/images/common/common_sale.png") 0 0 no-repeat;}
.ico-sale{background-position: -2px -2px;_background-position:-2px -60px;}
.ico-saleout{background-position: -559px -160px;}
.p-name,.g-name{height:3em;overflow: hidden;}
.p-price strong,.g-price strong{font-size:14px;color:#e4393c;font-family: "verdana";}
.p-price del,.g-price del{font-size:12px;color:#c3c3c3;font-family: "verdana";margin-left:5px;}
.p-tag{margin-top:3px;height:18px;}
.i-dayhot{margin-bottom:10px;}
.i-dayhot .dayhot-list{margin-right:-20px;}
.i-dayhot .dayhot-list .dayhot-item{float:left;padding:5px;width:314px;margin:0 10px 10px 0;border:1px solid #fff;overflow:hidden;zoom:1;}
.i-dayhot .dayhot-list .dayhot-item:hover{border:1px solid #dbdbdb;}
.i-dayhot .dayhot-list .dayhot-item .p-img{width:100px;height:100px;margin:0 10px 0 0;}
.i-dayhot .dayhot-list .dayhot-item-l{ width:287px;}
.i-dayhot .dayhot-item-cont{padding-top:5px;}
 


.ui-slide{overflow:hidden;zoom:1;position: relative;width:780px;}
.ui-slide-cont{height:270px;width:780px;overflow: hidden;zoom:1;}
.slide-cont-list-item{float:left;width:780px;}
.ui-slide-number{position: absolute;right: 8px;bottom: 8px;}
.ui-slide-number a{display:inline-block;background: #999;color:#fff;width:18px;height:18px;line-height:18px;text-align: center;border-radius: 9px;}
.ui-slide-number a:hover,.ui-slide-number .slide-number-active{text-decoration: none;background: #379945;}
.ui-slide-prev,.ui-slide-next{position: absolute;top:120px;width:11px;height:21px;background:url("/images/common/common_icon.png") 0 0 no-repeat;}
.ui-slide-prev{display:block;left:10px;background-position: -623px -31px;}
.ui-slide-next{display:block;right:10px;background-position: -639px -31px;}
.ui-slide-prev:hover{background-position: -623px 0;}
.ui-slide-next:hover{background-position: -639px 0;}


.m-prosales{margin-top:20px;}
.m-prosales-tit h2{border-top:2px solid #999;font-family: "microsoft yahei";font-size:18px;font-weight:normal;height:34px;line-height: 34px;}
.m-prosales-tit.prosales-style1 h2{color:#379945;border-top:2px solid #379945;}
.m-prosales-tit.prosales-style2 h2{color:#e5383c;border-top:2px solid #e5383c;}
.m-prosales-tit.prosales-style3 h2{color:#25b2c3;border-top:2px solid #25b2c3;}
.m-prosales-list{margin-right:-18px;}
.m-prosales-item{float:left;_display:inline;position:relative;border:1px solid #fff;padding-bottom:10px;margin:10px 18px 0 0;width:242px;}
.m-prosales-item:hover{border:1px solid #dbdbdb;}
.m-prosales-item .p-img{width:240px;height:245px;margin:0;}
.m-prosales-item .p-price,.m-prosales-item .p-name,.m-prosales-item .p-tag,.m-prosales-item .p-extra,.m-prosales-item .p-btns{padding:0 10px;}
.p-extra{font-family: "verdana";height:16px;line-height: 16px;margin-top:5px;overflow: hidden;zoom:1;padding:2px 0;}
.p-extra a{color:#005AA0;margin-left:10px;}
.p-btns{margin-top:6px;line-height:1;}


.star span,.star a{float:left;display:inline-block;height:15px;overflow:hidden;zoom:1;}
.star .star-default{background:url("/images/common/common_icon.png") -220px -50px no-repeat;width:75px;}
.star .star-present{background:url("/images/common/common_icon.png") -145px -50px no-repeat;position: relative;z-index:1;}
.star .s1{width:15px;}
.star .s2{width:30px;}
.star .s3{width:45px;}
.star .s4{width:60px;}
.star .s5{width:75px;}



.ico-tag{background: #f9eff0;border:1px solid #ffc7b0;color:#e23401;display:inline-block;margin-right:5px;padding:2px 3px;line-height:12px;border-radius: 2px;}
.ico-tag.green{background: #fff0d9;border:1px solid #7bbe55;color:#7bbe55;}
.ico-checkbox{display:inline-block;vertical-align: middle;height:13px;width:13px;background:url("/images/common/common_icon.png") -637px -63px no-repeat;overflow:hidden;margin-right:3px;}
.ico-return-now{border-radius: 3px;display:inline-block;color:#fff;line-height:12px;overflow: hidden;zoom:1;text-align:center;background:#f77b17;padding:2px 5px;}
.ico-return-now em{font-family:"verdana";}
.ico-true,.ico-flase,.ico-warn{display:inline-block;overflow:hidden;width:48px;height:48px;background: url("/images/common/common_shopcart.png") 0 0 no-repeat;}
.ico-true{background-position:0 -596px;}
.ico-flase{background-position:-46px -596px;}
.ico-warn{width:52px;background-position:-90px -596px;}
.ico-err,.icon-tip{display:inline-block;width:14px;height:14px;vertical-align:-3px;margin-right:3px;overflow: hidden;zoom:1;background:url("/images/common/common_shopcart.png") no-repeat;}
.ico-err{ background-position:-14px -442px;}
.icon-tip{background-position:-28px -442px;}




.m-w{border:1px solid #dbdbdb;}
.m-w .wh{background: #f7f7f7;border-bottom:1px solid #dbdbdb;height:37px;line-height: 37px;padding-left:10px;}
.m-w.m-w-noborder .wh{border-bottom:1px solid #fff;background: #fff;}
.m-w .wh h3{font-family: "microsoft yahei";font-weight: normal;font-size: 16px;}
.m-w .wh h3 strong{font-weight: normal;color:#379945;display:inline-block;margin-right:5px;}
.m-w .wb{padding:0 10px;}


.i-select .select-cont dl{border-top:1px dotted #ccc;overflow:hidden;zoom:1;padding:4px 0 2px;}
.i-select .select-cont .select-first{border-top:none;}
.i-select .select-cont dt{float:left;font-weight: 700;line-height: 25px;text-align:right;width:100px;}
.i-select .select-cont dd{overflow:hidden;zoom:1;line-height: 12px;padding-top:4px;}
.i-select .select-cont dd a{color:#005AA0;display:inline-block;_display:inline;padding:2px 3px;margin:0 20px 10px 0;float:left;}
.i-select .select-cont dd a:hover{color:#E4393C;}
.i-select .select-cont dd .current{color:#fff;background:#379945;_padding:1px 3px;}
.i-select .select-cont dd .current:hover{color:#fff;text-decoration: none;}
.i-select .select-cont dd .current b{display:inline-block;width:7px;height:7px;overflow:hidden;zoom:1;background:url("/images/common/common_icon.png") -127px -50px no-repeat;_vertical-align:middle;}
.i-select .select-cont .select-price dt{padding-top:4px;}
.i-select .select-cont .select-price dd a{padding-top:6px;}
.i-select .select-cont .select-price i{border-bottom:1px solid #333;font-size:0;height:1px;width:5px;overflow: hidden;display:inline-block;margin:0 5px;}
.i-select .select-cont dd .price-range{border:1px solid #ccc;font-family: "verdana";height:17px;line-height: 17px;padding:3px 1px;width:46px;}
.i-select .select-cont dd .btn-confirm{border:1px solid #ccc;border-radius:2px;color:#333;cursor: pointer;height: 25px;margin-top:0;text-align: center;text-decoration: none;width:51px;margin-left:5px;}
.i-select .select-attr dd{padding-right:100px;position: relative;}
.i-select .select-attr .recall{position: absolute;right:0;}
.i-select .select-attr .attr-list li{border:1px solid #e6e6e6;height:20px;line-height:20px;margin:-bottom:2px;padding:0 20px 0 5px;position:relative;float:left;margin:0 12px 4px 0;}
.i-select .select-attr .attr-list li a{float:none;color:#666;height:20px;line-height:20px;margin-top:0;white-space: nowrap;padding:0;text-decoration: none;margin:0;}
.i-select .select-attr .attr-list li a:hover{text-decoration: none;}
.i-select .select-attr .attr-list li:hover{border:1px solid #d6d6d6;}
.i-select .select-attr .attr-list li strong{color:#E4393C;}
.i-select .select-attr .attr-list li b{display:block;cursor:pointer;width:7px;height:7px;position:absolute;right:6px;top:7px;background: url("/images/common/common_icon.png") -484px -55px no-repeat;}

.i-filter{border:1px solid #dbdbdb;border-top:1px solid #379945;background:#f5f5f5;margin-top:10px;padding:8px 12px;}
.i-filter .filter-order{width:310px;}
.i-filter .filter-order li{float:left;border:1px solid #CECBCE;background:#fff;height:24px;line-height: 24px;margin-right:5px;overflow:hidden;zoom:1;}
.i-filter .filter-order li a{display:block;padding:0 18px 0 10px;background:url("/images/common/common_icon.png") -72px -11px no-repeat;}
.i-filter .filter-order li a:hover{color:#379945;}
.i-filter .filter-order .current{border:1px solid #379945;background: #379945;}
.i-filter .filter-order .current a{background-position:-118px -11px;color:#fff;font-weight: 700;}
.i-filter .filter-order .current a:hover{color:#fff;text-decoration: none;}
.i-filter .filter-order .contrl-default a{background-image:none;padding-right:10px;}
.i-filter .filter-order .contrl-upward a{background-position: -167px -11px;}
.i-filter .filter-chose{margin-top:4px;}
.i-filter .filter-chose a:hover b{background-position:-637px -76px;}
.i-filter .filter-chose .selected b{background-position:-637px -76px;}
.i-filter .filter-total{line-height: 24px;}
.i-filter .filter-total .prev,.i-filter .filter-total .next,.i-filter .filter-total .prev-disabled{border:1px solid #CECBCE;font-family: "宋体";display:inline-block;height:22px;line-height:22px;padding:0 5px;border-radius:2px;}
.i-filter .filter-total .prev-disabled{border:1px solid #ccc;color:#ccc;}
.i-filter .filter-total .number{color:#379945;margin-right:6px;}
.i-filter .filter-total .text{margin-right: 8px;}
.i-filter .filter-total .text i{color:#379945;font-weight: 700;}


.btn{background: url("/images/common/common_btn.png") 0 0 no-repeat;height:36px;width:125px;overflow:hidden;display:inline-block;}
.btn-returncart{width:120px;background-position:0 -597px;}/*返回购物车*/
.btn-returncart:hover{width:120px;background-position:-145px -597px;}
.btn-regfree{background-position: 0 -642px;}/*免注册购物*/
.btn-regfree:hover{background-position: -145px -642px;}
.btn-checkout{background-position: 0 -367px;}/*去结算*/
.btn-checkout:hover{background-position: -145px -367px;}
.btn-joincart{height:21px;width:104px;background-position: 0 -92px;}/*加入购物车 绿 小*/
.btn-joincart:hover{background-position:-145px -92px;}
.btn-confirmsubmit{width:189px;background-position:0 -505px;}/*先保存订单，暂不支付*/
.btn-confirmsubmit:hover{background-position:-200px -505px;}
.btn-saveorder{width:206px;background-position:0 -688px;}/*先保存订单，暂不支付*/
.btn-saveorder:hover{width:206px;background-position:-216px -688px;}
.btn-soonpay{width:125px;background-position:0 -413px;}/*马上支付*/
.btn-soonpay:hover{width:125px;background-position:-145px -413px;}
.btn-continue{width:138px;background-position:-290px -551px;}/*返回继续购物*/
.btn-continue:hover{width:138px;background-position:-290px -597px;}
.btn-payproblem{width:125px;background-position:0 -551px;}/*支付遇到问题*/
.btn-payproblem:hover{width:125px;background-position:-145px -551px;}
.btn-addcart{width:79px;height:30px;background-position:0 -123px;}/*加入购物车 产品p-btn*/
.btn-addcart:hover{background-position:-145px -123px;}
.btn-collect{width:55px;height:30px;background-position: 0 -771px;}/*收藏*/
.btn-collect:hover{background-position:-145px -771px;}
.btn-minicheckout{width:110px;height:25px;background-position:-290px -734px;}/*导航购物车去购物车结算*/
.btn-minicheckout:hover{background-position:-290px -771px;}
.btn-finishpay{width:125px;background-position:0 -459px;}/*已完成支付*/
.btn-finishpay:hover{background-position:-145px -459px;}
.btn-charge{width:79px;height:27px;background-position: -290px -811px;vertical-align: middle;margin-left:5px;}
.btn-charge:hover{background-position:-374px -811px;}
.btn-gocheckout{width:189px;background-position: 0 -321px}/*去购物车结算*/
.btn-gocheckout:hover{background-position: -199px -321px}


.btn-submit,.btn-submit span{display:inline-block;background:url("/images/common/common_shopcart.png") 0 -466px no-repeat;color:#fff;}
.btn-submit{font-size:14px;font-weight: 700;height:30px;line-height: 30px;margin-right:10px;padding-left:20px;color:#fff;text-decoration: none;}
.btn-submit span{background-position:100% -496px;cursor: pointer;height:30px;padding-right:20px;}
.btn-submit:hover{background-position:0 -526px;}
.btn-submit:hover span{background-position:100% -556px;}


.btn-add,.btn-add span{display:inline-block;background:url("/images/common/common_shopcart.png") 0 -978px no-repeat;color:#fff; border-radius:4px;}
.btn-add{height:21px;line-height:21px;margin-right:10px;padding-left:20px;color:#fff;text-decoration: none;}
.btn-add span{background-position:100% -999px;cursor: pointer;height:21px;padding-right:20px;}
.btn-add:hover{background-position:0 -1020px;}
.btn-add:hover span{background-position:100% -1041px;}


.btn-cancel,.btn-cancel span{display:inline-block;background:url("/images/common/common_shopcart.png") 0 -845px no-repeat;color:#666;}
.btn-cancel{font-size:14px;font-weight: 700;height:30px;line-height: 30px;margin-right:10px;padding-left:20px;color:#fff;text-decoration: none;}
.btn-cancel span{background-position:100% -875px;cursor: pointer;height:30px;padding-right:20px;}
.btn-cancel:hover{background-position:0 -905px;}
.btn-cancel:hover span{background-position:100% -935px;}



.Evaluate{ margin:10px;}
.Evaluate .item{ }
.Evaluate .item .user{ float:left; text-align:center; width:60px;}
.Evaluate .item .user .u-icon{ width:58px; height:58px; border:1px solid #ebebeb;}
.Evaluate .item .user .u-icon img{ width:48px; height:48px; border:1px solid #cdcdcd; margin:4px;}
.Evaluate .item .user .p-name{ color:#005aa0; line-height:30px;}
.Evaluate .i-item{ float:right; border:1px solid #d3d3d3; width:88%; padding:10px; position:relative;}
.Evaluate .i-item .i-item-c p{ line-height:22px; padding:10px 0px;}
.Evaluate .i-item .o-topic{ height:20px; border-bottom:1px solid #d3d3d3;}
.Evaluate .i-item .o-topic time{ float:right; color:#999999;}
.Evaluate .arrowLeft{ background:url(/images/common/Product-detailed.png) no-repeat -116px -14px; width:14px; height:25px; position:absolute; top:12px; left:-14px;}
.Evaluate .arrowRight{ background:url(/images/common/Product-detailed.png) no-repeat -153px -14px; width:14px; height:25px; position:absolute; top:12px; right:-14px;}
.i-item-inner{ width:90%; float:right; }
.i-item-inner textarea{ width:99%; height:100px; margin-top:10px;}
.i-item-inner .i-item-inner-text{ width:85%; float:left; border:1px solid #d3d3d3; padding:0px 10px; position:relative;}

.login-interface .title{height:28px;width:100%;line-height:28px;;background:#379945;}
.login-interface .title .text{float:left;color:#fff;font-weight:bold;font-size:14px;text-indent:10px}
.login-interface .title span {float:right;display:inline;cursor:pointer;}
.login-interface .title .ca a{width:20px;height:20px;float:right;background:url(/images/common/bg-box_03.png) no-repeat;display:block;}
.login-interface .title .ca a:hover{width:20px;height:20px;float:right;background:url(/images/common/bg-box_05.png) no-repeat;display:block;}

.interface-con{padding:10px;}
.con-title{border-bottom:1px solid #ccc;}
.con-title ul{margin-bottom:-1px;padding-left:100px;}
.con-title ul li{height:32px;line-height:32px;cursor:pointer;text-align:center;padding:0 25px;display:inline-block;float:left;margin-right:4px;font-size:14px;}
.con-title ul li.current{height:32px;border-radius:2px;border:1px solid #ccc;border-bottom:1px solid #fff;display:inline-block;font-weight:bold;color:#379945;}


.interface-con .con{width:330px;margin-top:20px;margin-left:60px;}
.con .wb-k{position:relative;}
.con .wb-k .kuang{width:239px;height:30px;line-height:30px;border:1px solid #ccc;margin-bottom:20px;}
.con .wb-k .cw{width: 239px;color:#F00;background: #fde5e5;position: absolute;border: 1px solid #fd9494;left:0;top: 30px;z-index:5;}/*登陆界面*/
.con .verify{margin-top:10px;}
.con .verify a{vertical-align: 8px;color:#005EA7;}
.con .w{width:397px;height:20px;line-height:20px;float:left;}
.checkbox{float:left;width:13px;height:13px;background:url(/images/bg-box_04.png)  no-repeat;margin-top:4px;margin-right:3px;}
.con .w span{line-height:20px;}
.w a{margin-left:5px;color:#005EA7;}

.con .anniu{width:397px;height:41px;float:left;margin-top:20px;}
.con .anniu .login-btn{width:241px;height:41px;background:url(/images/common/common_btn.png) no-repeat 0 -820px;display:block;}
.con .anniu .signin-btn{width:241px;height:41px;background:url(/images/common/common_btn.png) no-repeat 0 -925px;display:block;}




.m .mt{cursor:default;}
.m .mt h3{font-family: "microsoft yahei";font-size:14px;font-weight: normal;color:#333;}
.m .mc{overflow:hidden;zoom:1;}


.m3{border-top:1px dotted #dbdbdb;}
.m3 .mt h3{height:30px;line-height: 30px;}
.m3 .mc dl{padding:10px 0;float:left;}
.m3 .mc dt{padding:10px;}
.m3 .mc dd{width:110px;padding:5px;}
.fn-clear:after {
	visibility:hidden;
	display:block;
	font-size:0;
	content:" ";
	clear:both;
	height:0;
}
.fn-clear {
	zoom:1; /* for IE6 IE7 */
}
body .fn-hide {
	display:none;
}

.fn-left {
	float:left;
}
.fn-right {
	float:right;
}
.fn-left,.fn-right {
	_display:inline;
}

.fn-block{
  overflow:hidden;
  zoom:1;
}

.fn-inline-block li{
    display: inline-block;
   *display: inline;
   *zoom:1;
   vertical-align:top;
   letter-spacing:normal;
   word-spacing:normal;
}

.fn-letter-spacing{
  letter-spacing:-0.31em;
  *letter-spacing:normal;
  word-spacing:-0.41em;
}

.fn-ib{font-size:0;line-height:1%}
.fn-ib *{font-size:12px}

.fn-ie6lh{_font-family:\5b8b\4f53;_line-height:1.231}

.fn-word-wrap{
	word-wrap:break-word;
  word-break:break-all;
}

.fn-text,textarea{ border:1px solid #ccc;border-radius:3px;vertical-align:top;color:#666}
.fn-text{height:25px;padding-left:6px;line-height:25px\9}
textarea{padding:4px 0 7px 6px;overflow:auto}
.fn-text:hover,textarea:hover{border-color:#999}
.fn-text:focus, textarea:focus {
	border-color:#b5b5b5;
	   -moz-transition:border linear .2s,    -moz-box-shadow linear .5s;
	-webkit-transition:border linear .2s, -webkit-box-shadow linear .5s;
	        transition:border linear .2s,         box-shadow linear .5s;
	   -moz-box-shadow:0 0 3px rgba(209,187,138,.3);
	-webkit-box-shadow:0 0 3px rgba(209,187,138,.3);
	        box-shadow:0 0 3px rgba(209,187,138,.3);


}
.fn-text.fn-grey,textarea.fn-grey{color:#ccc !important}

.fn-text.fn-readonly, textarea.fn-readonly{border-color:#ccc;background:#F1F1F1;color:#ccc;cursor:default;resize:none;-moz-box-shadow:0 0 0;-webkit-box-shadow:0 0 0;box-shadow:0 0 0;}

.fn-fixedtop{
    position: fixed;
   _position: absolute;
    top:0;
   _top:expression(eval(documentElement.scrollTop));
}

.fn-fixedbottom{
    position: fixed;
   _position:absolute;
    bottom:0;
   _top:expression(eval(document.documentElement.scrollTop+document.documentElement.clientHeight-this.offsetHeight-(parseInt(this.currentStyle.marginTop,10)||0)-(parseInt(this.currentStyle.marginBottom,10)||0)) - 0)};


.fn-text-overflow{
  overflow: hidden;
     text-overflow: ellipsis;
  -o-text-overflow: ellipsis;
  white-space: nowrap;
}

.fn-debug{
	border:1px solid #f00;
}

.fn-shadow{-moz-box-shadow:0 0 4px rgba(51, 51, 51, 0.3);-o-box-shadow:0 0 4px rgba(51, 51, 51, 0.3);-webkit-box-shadow:0 0 4px rgba(51, 51, 51, 0.3);box-shadow:0 0 4px rgba(51, 51, 51, 0.3);-ms-filter:"progid:DXImageTransform.Microsoft.Glow(color=#a0a0a0,strength=0) progid:DXImageTransform.Microsoft.Shadow(color=#a0a0a0,direction=0,strength=2) progid:DXImageTransform.Microsoft.Shadow(color=#a0a0a0,direction=90,strength=2) progid:DXImageTransform.Microsoft.Shadow(color=#a0a0a0,direction=180,strength=2) progid:DXImageTransform.Microsoft.Shadow(color=#a0a0a0,direction=270,strength=2)";*filter:progid:DXImageTransform.Microsoft.Shadow(color=#a0a0a0,direction=0,strength=2) progid:DXImageTransform.Microsoft.Shadow(color=#a0a0a0,direction=90,strength=2) progid:DXImageTransform.Microsoft.Shadow(color=#a0a0a0,direction=180,strength=2) progid:DXImageTransform.Microsoft.Shadow(color=#a0a0a0,direction=270,strength=2)}
:root .fn-shadow{filter:none\9}

.fn-tc{text-align:center;}
.fn-tl{text-align:left;}
.fn-tr{text-align:right;}
.fn-nw{white-space: nowrap;}
.fn-t5{margin-top:5px;}
.fn-r5{margin-right:5px;}
.fn-b5{margin-bottom:5px;}
.fn-l5{margin-left:5px;}
.fn-t10{margin-top:10px;}
.fn-r10{margin-right:10px;}
.fn-b10{margin-bottom:10px;}
.fn-l10{margin-left:10px;}
.fn-m10{margin:10px;}
.fn-p10{padding:10px;}
.fn-t20{margin-top:20px;}

.fn-red{ color:#e4394b;}
.fn-green{ color:#379945;}
.fn-yellow{ color:#f77b17;}
.fn-blue,.fn-blue:hover,.fn-blue a{ color:#005EA7;}
.fn-redbg{background-color:#e4393c; color:#fff; padding:0px 5px;}

.fn-text-right{ text-align:right;}
.fn-text-left{ text-align:left;}
.fn-text-center{ text-align:center;}

.fn-bottom-dashed{ border-bottom:1px dashed #b3b3b2;}
.l-w{width:1250px;margin:0 auto;}

.l-w1000 .l-w{width:1000px;}
.l-w960 .l-w{width:960px;}


.l-left{float:left;width:210px;}
.l-right{float:right;width:1030px;}
.l-left,.l-right{_display:inline;}

.l-w1000 .l-left{ float:left; width:160px;}
.l-w1000 .l-right{ float:right; width:830px;}


.l-w1000 .i-nav .navitems{ width:500px;}

html{color:#333;background:#fff;font-size: 100%;-webkit-text-size-adjust: 100%;-ms-text-size-adjust:100%;}

body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,textarea,p,blockquote,th,td,hr,button,article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section {
	margin:0;padding:0;
}
article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section {display:block;}
audio,canvas,video {display: inline-block;*display: inline;*zoom:1;}
body,button,input,select,textarea{color:#666;font:12px/1.5 Arial,Helvetica,\5b8b\4f53,sans-serif;_font-family:"SimSun";}
input,select,textarea{font-size:100%;background:#fff;}
table{border-collapse:collapse;border-spacing:0;table-layout:fixed;word-wrap:break-word;word-break:break-all;width:100%;vertical-align:top;}
th,td{vertical-align: top;}

th{text-align:inherit;}
fieldset,img{border:none;}
iframe{display:block;}
abbr,acronym{border:none;font-variant:normal;}
del {text-decoration:line-through;}
address,caption,cite,code,dfn,em,var {font-style:normal;font-weight:500;}
ol,ul {list-style:none;}
caption,th {text-align:left;font-weight:700;}
h1,h2,h3,h4,h5,h6 {font-size:100%;font-weight:700;}
q:before,q:after {content:'';}
sub, sup { font-size: 75%; line-height: 0; position: relative; vertical-align: baseline;}
sup {top: -0.5em;}
sub {bottom: -0.25em;}

ins,a {	text-decoration:none;}
a{color:#666;}
a:focus {outline: thin dotted #666;outline: 5px auto -webkit-focus-ring-color;outline-offset: -2px;}
a:hover,a:active {outline: 0;text-decoration:underline;}
a:hover{color:#e4393c;text-decoration:underline;}
select{border:1px solid #ccc;}
code{white-space:pre;}
s,u,q,i,b{text-decoration:none;font-style:normal;font-weight:normal;}
.Order_Schedule{ background:url(/images/pages/Order_Schedule.png) no-repeat;  display:block;}
 
.mail-logo{ width:180px;}
.mail-safeguard{ width:230px;}
.mail-safeguard li{ width:65px; _width:63px; float:left; text-align:center; margin-left:10px;}
.mail-safeguard li i{ width:32px; height:35px; margin:0px auto;}
.mail-safeguard p{ line-height:25px;}
.overhundred{ background-position:-186px -533px;}
.genuine{ background-position:-227px -533px;}
.pharmacist{ background-position:-265px -533px;}
.header-banner{ width:528px; height:54px;}

.mail-nav{ background-color:#019944;}
.mail-menu{ width:720px;}
.mail-menu ul{}
.mail-menu ul li{ background:url(/images/pages/nav_line.png) no-repeat right; display:block; float:left;}
.mail-menu ul li a{color:#fff; font-size:14px; font-weight:bold; padding:0px 30px; line-height:40px; display:block;}
.mail-menu ul li a:hover{ background-color:#017e30;}
.mail-nav-hotline{ width:160px; color:#fff; background:#03a249 url(/images/pages/nav_right_shadow.png) no-repeat left; padding-left:25px;}
.mail-nav-hotline .phone{ line-height:15px;}
.mail-nav-hotline .phone i{ background-position:-107px -554px; width:13px; height:12px; float:left; margin:3px 3px 0 0;}
.mail-nav-hotline .number{ font-size:16px; font-family: "microsoft yahei"; letter-spacing:1px;}
.i-topbar-gray{ background-color:#efefef; border-bottom:1px solid #e9e4e4;}
.i-topbar-list{}
.i-topbar-list li{ display:inline;}
.i-topbar-list li a{ border-right:1px solid #d3d1d1; color:#999999; padding:0px 5px;}

.mail-con{ width:742px; margin:0px auto; padding-top:20px;}
.mail-con p{ line-height:22px; padding-bottom:10px;}
.mail-con h5{ line-height:35px;}
.mail-con h2{ font-size:14px; line-height:40px;}
.mail-con .hellow{ padding-bottom:20px;}
.mail-con .hellow p{ font-size:14px; line-height:25px; }
.mail-con .user{ padding:30px 0px;}
.mail-con .user li{ float:left; margin-right:20px;}
.mail-con .text-con{ background-color:#edfdf0; border:1px solid #b9e9c0;color:#379946; padding:10px;}
.mail-con .text-con p{ line-height:22px; }
.mail-con-list .code{ width:168px; height:120px; position:absolute; bottom:20px; right:0px;}
.mail-con-list{ position:relative; width:100%;}
.mail-con-list ul{ padding:10px 0px;}
.mail-con-list ul li{ line-height:25px; color:#999999;}

.ico-spoh {background: url(/images/common/common_shopcart.png) no-repeat; }　　　/******　common_shopcart.png 文件　******/
.bin {background:url(/images/common/common_btn.png) no-repeat;} /******　common_btn.png 文件　******/
 
 
 
.ico-user,.mykm .mt h2 a,.user-img,.mykm .mc b,.item i{background: url("/images/pages/Order_Schedule.png") no-repeat;}
.Cancel{ background:url(/images/common/common_btn.png) no-repeat 0px -1106px; color:#333333;}

.ullist-x{ overflow:hidden; border-bottom:1px solid #f1f0f0;}
.ullist-x li{ float:left; margin-right:10px; line-height:35px;}

.bti,.green-btn52{ border:0px; width:52px; height:29px; line-height:29px;  cursor:pointer;  text-align:center;}
.bti{background-position:-290px -162px; color:#333;}
.bti:hover,.green-btn52:hover{background-position:-358px -162px; color:#ccc;}
.green-btn52{background-position:-290px -366px;  color:#fff;}
.fn-f16 { font-size:16px;}
.fn-f16 { font-weight:bold;}s
.fn-f16 { font-family:Verdana;}

.oper-shop {height:50px; width:100%; float:left;}
.oper-shop ul {margin:10px auto;; padding:0px;}
.oper-shop ul li {float: left;list-style-type: none;}
.oper-shop1 span,.oper-shop2 span,.oper-shop3 span {width: 36px;height: 36px;background-image: url(/images/common/common_flow.png);margin: 7px 0px;float: left;color: #FFFFFF;font-family: Arial, Helvetica, sans-serif;font-size: 26px;font-weight: bold;line-height:36px;text-align: center;}
.oper-shop1 span {background-position:0px -104px;}
.oper-shop2 span {background-position:0px -178px;}
.oper-shop3 span {background-position:0px -141px;}

.oper-shopbi-40 ul { width:89%;} 
.oper-shopbi-40 ul li b,.oper-shopbi-40 ul li i { width:40px;}
.oper-shopbi-61 ul { width:85%;} 
.oper-shopbi-61 ul li b,.oper-shopbi-61 ul li i { width:56px;}
.oper-shopbi-76 ul { width:84%;} 
.oper-shopbi-76 ul li b,.oper-shopbi-76 ul li i { width:72px;}

.oper-shop1 b,.oper-shop2 b,.oper-shop3 b,.oper-shop1 i,.oper-shop2 i,.oper-shop3 i{ height:6px; line-height:6px; margin:22px 0px;float:left; }
.oper-shop1 b {background-color:#88c264;}
.oper-shop1 i {background-color:#88c264;}
.oper-shop2 b {background-color:#cecece;}
.oper-shop2 i {background-color:#cecece;}
.oper-shop3 b {background-color:#d3e8c5;}
.oper-shop3 i {background-color:#d3e8c5;}

.operatxt {height:50px;float:left;margin:10px auto;}
.operatxt ul {margin:0px auto;; padding:0px;}
.operatxt ul li {float: left;list-style-type: none; text-align:center; font-size:12px;}
.operatxt-117 ul li {width:117px; }
.operatxt-180 ul li {width:180px; }
.operatxt-148 ul li {width:148px; }
.operatxt ul li p { margin:5px 0px;padding:0px;}
.operatxt-shop1{ color:#6fa54d;}
.operatxt-shop2{ color:#cecece;}
.operatxt-shop3{ color:#bcd5ab;}


.userinfo{border:1px solid #edd28b;background:#fffdee;padding:15px;}
.userinfo-pic{float:left;width:100px;margin-right:20px;border:1px solid #ffeed6;border-radius:2px;background:#fff;padding:4px;text-align: center;}
.userinfo-cot{overflow:hidden;zoom:1;}
.userinfo-name strong{font-size:16px;color:#379945;margin-right:8px;}
.userinfo-verify{border-top:1px dotted #dbdbdb;padding:8px 0;margin:8px 0;}
.userinfo-verify a{color:#005EA7;margin-left:10px;}
.userinfo-verify .ico-spoh{float:none;display:inline-block;}
.userinfo-detail{border:1px solid #ffeed6;border-radius:2px;background:#fff;padding:12px 8px 0 8px;}
.userinfo-detail dl{border-bottom:1px dotted #dbdbdb;overflow:hidden;zoom:1;margin-bottom:10px;padding-bottom:10px;}
.userinfo-detail dt,.userinfo-detail dd{float:left;padding:0 4px;color:#999;width:138px;}
.userinfo-detail dt{color:#666;padding:0;width:80px;text-align: right;}
.userinfo-detail dd a{color:#005EA7;}
.userinfo-detail .item-last{border-bottom:none;margin-bottom:0;}
.ico-user-phone,.ico-user-mail,.ico-user-lock{display:inline-block;width:16px;height:16px;font-size:0;margin-right:3px;overflow:hidden;zoom:1;vertical-align: middle;}
.ico-user-phone{background-position: -20px -531px;}
.ico-user-mail{background-position: -37px -531px;}
.ico-user-lock{background-position: -54px -531px;}
.ico-user-phone.default{background-position: -20px -548px;}
.ico-user-mail.default{background-position: -37px -548px;}
.ico-user-lock.default{background-position: -54px -548px;}

.hot-sale{width:805px;}
.hot-sale .ui-slide-cont{width:750px;height:auto;margin:10px 30px;}
.hot-sale .slide-cont-list-item{border:1px solid #fff;width:176px;margin-right:10px;}
.hot-sale .slide-cont-list-item:hover{border:1px solid #dbdbdb;}
.hot-sale .p-name,.hot-sale .p-price{padding:0 5px;}


.recent-look{width:805px;}
.recent-look .p-img{width:105px;height:105px;}
.recent-look .ui-slide-cont{width:750px;height:auto;margin:10px 30px;}
.recent-look .slide-cont-list-item{border:1px solid #fff;width:120px;margin-right:20px;padding:6px;}
.recent-look .slide-cont-list-item:hover{border:1px solid #dbdbdb;}
.recent-look .p-name,.hot-sale .p-price{padding:0 5px;}

.upfile{padding:30px 30px 15px 30px;}
.upfile .upfile-scan{border-bottom:1px dotted #dbdbdb;padding-bottom:15px;}
.upfile .upfile-scan p{color:#999;margin-top:6px;}
.upfile .upfile-cont{padding-top:20px;overflow: hidden;zoom:1;}
.upfile .upfile-cont .upfile-pic{border:1px solid #dbdbdb;float:left;text-align:center;width:200px;height:200px;}
.upfile .upfile-cont .upfile-pic img{vertical-align:middle;display:inline-block;}
.upfile .upfile-create{margin-left:230px;border-left: 1px solid #eaeaea;padding-left: 30px;}
.upfile .create-pic{margin-top:10px;}
.upfile .create-pic img{border:1px solid #dbdbdb;}
.upfile .create-pic span{display:inline-block;margin-left:5px;vertical-align: top;}
.upfile .upfile-btns{margin-top:20px;text-align: right;border-top:1px dotted #dbdbdb;padding-top:15px;}


.mykm .mt{ height:34px;width:160px;overflow:hidden;zoom:1;}
.mykm .mt h2 a{display:block;background-position: 0 -568px;line-height:34px;font-size:14px;color:#fff;padding-left:10px;}
.mykm .mt h2 a:hover{background-position:-170px -568px;text-decoration: none;}
.mykm .mc{overflow:hidden;zoom:1;border:1px solid #e6e6e6;border-top:none;}
.mykm .mc dl{display: block;}
.mykm .mc dl dt{position: relative;margin-bottom: -1px;height: 30px;padding: 0 12px;font-weight: bold;line-height: 30px;cursor: pointer;color: #333333;background-color: #ececec;}
.mykm .mc dl dd{display: block; margin-left:40px; padding:10px 0px;}
.mykm .mc dl dd div{ line-height:26px; position:relative;}
.mykm .mc dl dd div.curr{ font-weight:bold;}
.mykm .mc b{ width:9px; height:5px; background-position:0 -547px; display: block; position: absolute;top: 12px;right:10px;width: 9px;height: 5px;font-size: 0;line-height: 0;overflow: hidden;}
.mykm .mc .curr b{ background-position:0px -553px;}

.user-m{}
.user-m .o-mt{ border:1px solid #e6e6e6;}
.user-m .o-mt h2{ border-bottom:2px solid #339933; color:#333333; font-size:14px; line-height:36px; text-indent:12px; }
.user-m .mt{display:block; height:31px;}
.user-m .mt span{   width:91px; height:31px;  line-height:31px; text-align:center; float:left;background-position:-383px -201px; margin-right:2px;}
.user-m .mt span.curr{ font-weight:bold; background-position:-290px -201px;}
.user-m .button{margin:10px 0px;padding:10px 0 0 310px;}
.user-m h3{ font-size:14px; line-height:35px;}

.user-m .tab li {float: left;background: url(/images/pages/tab.gif) 0 -28px;line-height: 29px;margin-right: 3px;text-align: center;height: 27px;padding: 0 10px;position: relative;
overflow: hidden;}
.user-m .tab li.curr, .tab li.curr a:link, .tab li.curr a:visited {color: #ff6600;font-weight: bold;}
.user-m .tab li s, .tab li b {width: 3px;height: 27px;background: url(/images/pages/tab.gif);position: absolute;top: 0;}
.user-m .tab li.curr {background-position: 0 -85px;}
.user-m .tab {padding-left: 5px;border-bottom: 2px solid #d4d4d4;z-index: 2; overflow:hidden; height:27px;}
.user-m .tab li.curr b {background-position: -7px -57px;}
.user-m .tab li.curr s {background-position: 0 -57px;}
.user-m .tab li.curr b {background-position: -7px -57px;}
.user-m .tab li b {right: 0;background-position: -7px 0;}
.user-m .tab li s {left: 0;}

.ui-form{ overflow:hidden;}
.ui-form li{ float:left;}
.form-info{ padding:20px;}
.form-info li{ line-height:35px; overflow:hidden; height:100%;}
.form-info label{ float:left; width:105px; text-align:right; }
.form-info .sh-name{ margin-left:3px;  float:left;}
.form-info span{ padding:0px 5px;}
.form-info em{ color:#e4393c; padding:0px 5px;}
.quantity-form{height: 22px;margin: 0 auto;overflow: hidden;text-align: center;width: 76px; padding-top:8px;}
.quantity-form a{background: #FFFFFF;border: 1px solid #dbdbdb;display: block;float: left;height: 13px;line-height: 13px;margin-top: 3px;overflow: hidden;zoom:1;text-align: center;width: 13px;}
.quantity-text{background: #fff;height:18px;overflow: hidden;zoom:1;padding-top:2px;text-align: center;width:34px;}
.quantity-form input{float:left;border:1px solid #dbdbdb;}
.quantity-form a.increment{float:right;}
.quantity-form a.decrement{margin-right:5px;}
.quantity-form a:hover{border:1px solid #999;color:#333;text-decoration: none;}


.Inquiry{ padding:12px 10px;}
.Inquiry .sele{ border:1px solid #e6e6e6; margin-right:10px; line-height:20px; height:30px; width:130px;}
.Inquiry label{ padding-left:5px;}
.Inquiry span{  margin-right:10px;}
.Inquiry a{ background-color:#000;}


.user-table{ table-layout:auto;}
.user-table th{ background:url(/images/pages/tb-th.2011.gif) repeat-x; text-align:center;}
.user-table td{ vertical-align:middle;  padding:10px 10px; text-align:center;}
.user-table td a{ line-height:25px;}
.user-table td span{ margin:0px 3px;}
.user-table td img{ border:1px solid #e6e6e6; width:50px; height:50px;  margin:1px; float:left;}
.user-table td .img{ display:block; width:55px; overflow:hidden; text-align:center; float:left; margin-right:10px;}
.user-table td .user-cp-img{ height:100px; width:100px; overflow-x:scroll;}
.user-table td .td-name{ display:block; float:right; width:285px;}
.user-table .fn-text-left{ text-align:left;}
.red-btn,.yellow-btn,.green-btn{ padding:2px 8px; color:#fff; border-radius:2px;}
.red-btn:hover,.yellow-btn:hover,.green-btn:hover{ color:#fff;}
.red-btn{ background-color:#e4393c;}
.yellow-btn{ background-color:#f77b17;}
.green-btn{ background-color:#379945;}

.i-m { width:70%; float:left;}
.i-m-w{ width:100%;}
.i-m .item {padding-bottom: 20px; line-height:25px;}
.i-m .item em{ color:#e4394b; padding:0px 5px;}
.i-m .item i{ width:15px; height:15px; display:inline-block; vertical-align:middle;}
.i-m .item i.yes{ background-position:-83px -533px;}
.i-m .item i.wrong{ background-position:-103px -533px;}
.i-m .item .prompt{ padding-left:167px; }
.item .label {width:160px; height:30px; line-height:35px; text-align: right;color: #666666;  display:block; float:left;}
.item textarea{ height:80px;}
.item select,.Inquiry select{ height:27px;}
.u-text,.Inquiry-text,.Complaints-text {padding:4px 3px; border:1px solid #e6e6e6; line-height:20px; height:20px;}
.Inquiry-text{ width:210px;}
.Complaints-text{ width:400px; _width:394px;}
.item input[type="radio"],.item input[type="checkbox"]{ margin:0px 3px; vertical-align:middle;}
.form .label, .right .form label {line-height: 25px;margin-right: 8px;}
.right-extra{ float:right; width:30%;}
.right-extra .headpic{ text-align:center;}

.OrderInfo{ width:90%; margin:10px auto;}
.OrderInfo .Order-Number span{ color:#cccccc;}
.OrderInfo .Order-Number em{ padding:0px 5px;}
.OrderInfo .Order-Number p{ line-height:35px;}
.OrderInfo a,.user-column a{ margin:0px 10px; color:#005EA7;}
.Order-Number li{ width:50%; float:left; line-height:35px; }
.OrderInfo .Order-Schedule{ padding:5px 0px; height:95px;}
.OrderInfo .Order-Schedule .Order-Schedule-c{ background:url(/images/pages/Order_Schedule.png); width:719px; height:76px; margin:0px auto;}
.OrderInfo .Order-Schedule .No1{ background-position:0 0;}
.OrderInfo .Order-Schedule .No2{ background-position:0 -80px;}
.OrderInfo .Order-Schedule .No3{ background-position:0 -167px;}
.OrderInfo .Order-Schedule .No4{ background-position:0 -252px;}
.OrderInfo .Order-Schedule .No5{ background-position:0 -338px;}
.user-m .user-column{ padding:10px 0px; background-color:#eeeeee;}
.user-m .user-column h2{ line-height:35px;}
.user-m .user-column ul{ width:28%; float:left; border-right:1px solid #e6e6e6; padding:0px 20px;}
.user-m .user-column ul:nth-child(n+3){ border-right:0px; }
.user-m .user-column ul li{ line-height:25px;}
ol.decimal {list-style-type: decimal}
ol.decimal li{ line-height:35px;}

.percent{ width:170px; padding:10px;}
.percent dl{  width:250px; margin:0px auto; height:25px; }
.percent dl dt{ float:left; padding-right:10px;}
.l-1000 .Evaluate .i-item{ width:500px;}
.i-item-inner100{ width:100%;}

.percent-left{ width:450px; float:left;}
.percent-left dl{ padding:10px;}
.percent-left dl dt,.percent h2{ font-size:16px; font-family: "microsoft yahei"; line-height:35px; font-weight:normal;}
.percent-left dl dd{ border:1px solid #e9e9e9;}
.percent-left textarea{ width:420px; border:none;  resize:none; height:80px;}

.integration-text{ padding:10px;}
.integration-text h2{ font-size:16px; line-height:35px; border-bottom:2px solid #88c264; color:#88c264; text-align:center;}
.integration-text li{ line-height:25px;}

.tb-void .remindlist{overflow:hidden; padding:10px;}
.tb-void .remindlist li{ float:left; width:16%;}
.tb-void input[type="checkbox"]{ vertical-align:middle; margin-right:5px;}
.tb-void table {border-collapse: collapse;border: solid #E6E6E6 ;border-width: 0px 0px 0px 0px;}
.tb-void td {border: solid #E6E6E6;border-width: 1px 0 0px 1px;padding: 5px 4px;}


.safe-level{padding-top:10px;padding-bottom:10px;margin-top:10px;font-size:14px;font-weight:bold;color:#666666;text-indent:12px;}
.safe-level span{font-weight: normal;font-size:12px; color:#CC0000;}
.safe-level strong { margin:0px 10px;}
.tips-green{ background-color:#f1fff2; border:1px solid #AFD0A5;}
.tips-red { background-color:#fee5e0; border:1px solid #ebb7b7;}
.tips-yellow {background-color:#fff8ea; border:1px solid #ffdc9f;}
.tips-green strong { color:#429130;}
.tips-red strong { color:#c54040;}
.tips-yellow strong { color:#e79300;}

.progb-safe-l,.progb-safe-c,.progb-safe-h，.progb-safe-imp {display:inline-block;width:121px;height:14px;overflow:hidden;zoom:1;float:left; margin-right:5px;}
.progb-safe-l { background-position:0px -697px;}
.progb-safe-c { background-position:0px -712px;}
.progb-safe-h { background-position:0px -726px;}
.progb-safe-no { background-position:0px -683px;}

.td-s1{ width:75px;}
.td-s2{ width:225px; }
.td-s3{ width:65px;}
.td-s4{ width:350px;}
.td-s5{ width:70px;}
.td-s6{ width:358px;}
.td-s7{ width:120px;}
.td-s8{ width:290px;}
.td-s9{ width:570px;}

.upload-photos{ padding:10px 0px;}
.upload-photos li{ height:50px; border:1px solid #e6e6e6; float:left; margin-right:10px;}
.upload-photos li img{ width:50px; height:50px;}
.upload-photos-Address{ background-color:#fffdee; border:1px solid #edd28b; padding:10px 0px; height:100%;}
.upload-photos-form{}
.upload-photos-form span{ padding-left:45px;}
.upload-photos-Address .tisp{ padding-left:80px;}
.upload-photos-Address .tisp ul{ display:block; float:left;}
.upload-photos-Address .tisp ul li{ line-height:25px; color:#e1a156;}


.inner-inform{padding:25px 20px;font-size:14px;}
.inner-inform a{color:#005EA7;}
.inner-inform h1{font-size:16px;text-align: center;}
.inner-inform .inform-cont{margin:15px 0;}
.inner-inform .time{text-align: right;color:#999;}
.inner-inform .inform-tip{border:1px dotted #dbdbdb;background:#f5f5f5;padding:8px;color:#999;line-height:24px;margin-top:10px;font-size:12px;}


.ordertracking{ padding:30px 0px;}
.ordertracking .order-left,.order-right{ width:47%; float:left;}
.ordertracking .title{ padding-bottom:10px; border-bottom:1px solid #e8e8e8; width:90%;}
.ordertracking .title p{ color:#999;}
.ordertracking h2{ font-size:16px; color:#379945; line-height:35px;}
.ordertracking .order-left{ border-right:1px solid #edd28b; margin-right:50px;}
.ordertracking .ordertracking-form{}
.ordertracking .ordertracking-form li{ padding:5px 0px; display:block; overflow:hidden; height:100%;}
.ordertracking .ordertracking-form li p{ padding:5px 0px;}
.ordertracking .reg-btn a,.ordertracking .reg-btn a:hover{ margin-left:0px;}


.help-left{ width:678px;}
.help-right{ width:312px;}
.help-right h3{ font-size:14px; border-bottom:1px solid #e8e8e8; padding:10px 8px;}
.help-right h3 span{ float:left; width:3px; background-color:#c7c9c7;  height:18px; margin-right:10px;}
.help-right h3 span a{ background-color:#000; width:3px; height:50%;display:block;}
.border-gray{ border:1px solid #dbdbdb; font-family: "microsoft yahei"}
.help-title{border-bottom:1px solid #dedede; line-height:35px; margin:0px 10px;}
.help-title span{font-size:16px; color:#666666; font-family: "microsoft yahei"}
.help-title i{ font-size:14px; color:#929292; margin-left:10px;}
.help-title .lefticon{ background-position:-129px -531px; width:13px; height:17px; padding-left:18px;}
.help-title .righticon{ background-position:-84px -553px; width:14px; height:14px; display:block; float:left; margin:10px 10px 0px 0px; line-height:30px;}
.help-title a{ float:right; }
.help-bottom-green{ border-bottom:2px solid #68bb62; padding-bottom:8px;}
.help-right-border{ border-right:1px dashed #cfcfcf;}
.help-list{ padding:0px 10px; margin-top:10px;}
.help-list li{ border-bottom:1px dashed #d0d0d0; line-height:30px; background:url(/images/pages/dian.png) no-repeat left; text-indent:15px;}

.help-left .top-search{ background-position:0 -822px; width:100%; border-radius:4px; border:1px solid #d7d7d7}
.help-left .top-search .search-cont{ width:502px; margin:24px auto;}
.help-left .top-search .search-cont span{ font-size:14px; float:left;font-family: "microsoft yahei"; line-height:16px; padding:0px 10px;}
.help-left .top-search .text{ float:left; width:380px; border:1px solid #d7d7d7; height:28px;}
.help-left .top-search .button{ width:69px; height:30px; background-color:#949494; font-size:14px; color:#fff; border:0px; float:left;}

.help-service{ width:158px; margin:20px 5px; float:left;}
.help-service dt{ float:left; width:62px; height:72px; margin-right:10px; text-align:center; vertical-align:middle;}
.help-service dd{ width:76px; float:left;}
.help-service dd li{background:url(/images/pages/dian.png) no-repeat left; text-indent:10px; color:#999999; line-height:18px;}
.help-service dd li a{ color:#999999;}
.ser1,.ser2,.ser3,.ser4,.lefticon,.righticon,.into,.top-search{ width:62px; height:72px; background:url(/images/pages/Order_Schedule.png) no-repeat;}
.ser1{ background-position:0px -623px;}
.ser2{ background-position:-63px -623px;}
.ser3{ background-position:-126px -623px;}
.ser4{ background-position:-189px -623px;}

.novice{ padding-top:20px; overflow:hidden;}
.novice .into{background-position:0px -718px; width:100%; height:72px;}
.novice .into li{ float:left; text-align:center; font-size:14px; font-family: "microsoft yahei"; width:135px; padding-top:12px;}
.novice .into li a:hover{ color:#238030; text-decoration:none;}
.novice .into-list{ padding-top:10px;}
.novice .into-list li{ float:left; width:135px; text-align:center;}
.novice .into-list li a{ line-height:18px; display:block; color:#666666;}

.help-link{ padding:10px;}
.help-link dl{ border-bottom:1px dashed #cfcfcf;}
.help-link dl dt{ font-size:14px; font-weight:bold; margin-top:10px;}
.help-link dl dd{ line-height:35px;}
.help-link dl dd a{ padding-right:30px;}

.about-text{ padding:20px;}
.about-text h3{ font-size:14px;}
.about-text p{ font-size:14px; line-height:25px; padding-bottom:20px;}
</style>

<body class="l-w960">
<!-- 顶部导航条 -->
	<#include "mail-head.ftl">  
<!-- Header end -->

        <!--内容区-->
        <div class="l-w fn-clear">
        	<div class="mail-con">
            	<div class="hellow fn-bottom-dashed">
                	<h2>尊敬的${loginName}您好：</h2>
                	<p class="fn-green">您在访问康美中药城网站时点击了“忘记密码”操作，这是一封密码重置确认邮件。如果您并未尝试修改登录密码，请忽略本邮件。
                	<br/> 您可以通过点击以下链接重设账户密码。
                	<a href="${resetPasswordUrl}">${resetPasswordUrl}						
						<br/>
						请在24小时内点击该链接，您也可以将链接复制到浏览器地址栏访问。
					</a>
                	</p>
                </div>
               
                <div class="fn-bottom-dashed">
                	<h2 class="fn-bottom-dashed fn-green fn-text-center">康美中药城欢迎您再次光临</h2>
                    <h5>重要声明：</h5>
                    <p>本邮件仅表明销售方已收到了您提交的订单；销售方收到您的订单信息后，只有在销售方将您在订单中订购的商品从仓库实际直接向您发出时（以商品出库为标志），方视为您与销售方之间就实际直接向您发出的商品建立了合同关系；</p>
                    <p>如果您在一份订单里订购了多种商品并且销售方只给您发出了部分商品时，您与销售方之间仅就实际直接向您发出的商品建立了合同关系；只有在销售方实际直接向您发出了订单中订购的其他商品时，您和销售方之间就订单中该其他已实际直接向您发出的商品才成立合同关系。</p>
                    <p>您可以随时登陆您在京东注册的账户，查询您的订单状态。更多内容请见最新的康美中药城网站用户注册协议及康美中药城网站各类购物规则，我们建议您不时地予以浏览阅读。</p>
                    <h5>帐户安全提醒：</h5>
                    <p>互联网账号存在被盗风险，为了保障您的帐户及资金安全，康美中药城提醒您访问 我的康美 —> 账户安全，尽快启用所有安全服务。</p>
                </div>
               
        <!--内容区 END-->	
      <!-- 底部特别推荐商品 -->
      <#include "mail-body.ftl">  
        
      <!-- 底部导航链接 -->
      <#include "mail-foot.ftl">  
        <!-- footer END -->
</body>
</html>
