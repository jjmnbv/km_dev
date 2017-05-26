<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.kmzyc.zkconfig.ConfigurationUtil" %>
		<title>康美中药城</title>


                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />



		<link rel="shortcut icon" href="<%=ConfigurationUtil.getString("cssAndJsPath")%>/affixesimages/logos/favicon.png" type="image/x-icon"/>

		<link rel="stylesheet" href="<%=ConfigurationUtil.getString("cssAndJsPath")%>/css/core/reset.css" />

		<link rel="stylesheet" href="<%=ConfigurationUtil.getString("cssAndJsPath")%>/css/core/layout.css" />

		<link rel="stylesheet" href="<%=ConfigurationUtil.getString("cssAndJsPath")%>/css/core/function.css" />

		<link rel="stylesheet" href="<%=ConfigurationUtil.getString("cssAndJsPath")%>/css/lib/module.css" />	

		<link rel="stylesheet" href="<%=ConfigurationUtil.getString("cssAndJsPath")%>/css/lib/tpl.css" />	


<link rel="stylesheet" href="<%=ConfigurationUtil.getString("cssAndJsPath")%>/css/dev/Search-page.css"/>
<link rel="stylesheet" href="<%=ConfigurationUtil.getString("cssAndJsPath")%>/css/pages/registration.css" />

<script type="text/javaScript" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/js/jquery-core.js"></script>

<script type="text/javaScript" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/js/suggest.js"></script>

<script type="text/javascript" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/js/test/jquery.validate.js"></script>
<script type="text/javascript" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/js/messages_cn.js"></script>
<script type="text/javascript" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/js/md5.js"></script>
<script type="text/javascript" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/js/login.js"></script>
<script type="text/javascript" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/js/adv.js"></script>
<script type="text/javascript" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>/js/index.js" charset="gb2312"></script>
		<!-- 顶部导航条 -->
			<div class="i-topbar">
<div class="l-w fn-clear">
				<p class="fn-left loginbar" id="loginbar">您好，欢迎来到康美中药城商城！


				</p>
				<ul class="fn-right topmenu">
					<li class="topmenu-item topmenu-item-phone"><i class="ico-phone"></i>客服热线：<strong>400-6600-518</strong></li>
					<li class="topmenu-item"><b></b><a href="">供应商入口</a></li>
<li class="topmenu-item"><b></b><a href="<%=ConfigurationUtil.getString("searchPath")%>/showOrderTrail.action">订单跟踪</a></li>
					<li class="topmenu-item topmenu-item-collect"><b></b><i class="ico-collect"></i><a href="javascript:void(0);" id="addCookie" title="康美中药城">收藏本站</a></li>
					<li class="topmenu-item"><b></b><a href="http://10.1.0.213/index.html">帮助中心</a></li>
				</ul>
			</div>
</div>
		<!-- topbar END -->

		<!-- 头部 -->
			<div class="i-head">
	<div class="l-w fn-clear">
		<div class="logo fn-left">

				<a href="<%=ConfigurationUtil.getString("staticPath")%>/index.html" hidefocus="true"><img height="115" src="<%=ConfigurationUtil.getString("cssAndJsPath")%>images/common/logo.png" onerror='this.src="<%=ConfigurationUtil.getString("cssAndJsPath")%>images/default__logo_err170_170.jpg"' alt="康美中药城" /></a>

		</div>
		<div class="i-search fn-left">
			<div class="search-cont">
				<form id="searchForm" method="POST">
				<div class="form">
					<input id="keyword" type="text" class="text" />
					<input id="searchBtn" type="button" value="搜索" class="button" />
				</div>
				</form>
			</div>
			<div class="search-hotwords">
				<strong>热门搜索：</strong>
				
                                <a target="_blank" href="http://10.1.0.214:8088/search.action?kw=康美中药城">康美中药城</a>
<a target="_blank" href="http://10.1.0.214:8088/search.action?kw=菊花茶">菊花茶</a>
<a target="_blank" href="http://10.1.0.214:8088/search.action?kw=金达克宁">金达克宁</a>
<a target="_blank" href="http://10.1.0.214:8088/search.action?kw=提升免疫力">提升免疫力</a>
<a target="_blank" href="http://10.1.0.214:8088/search.action?kw=乳膏">乳膏</a>

			</div>
		</div>
			
	</div>
</div>
		<!-- head END -->

		<!-- 导航栏 -->
			<div class="i-nav">

			<div class="l-w">

				<div class="nav-cont fn-clear">

					<div id="sortGoodsList" class="sort" >
	<h2 class="sort-link">
		<a href="" id="showOtherGoodsList">所有商品分类<b></b> </a>
	</h2>
	<ul class="sort-list" id="otherGoodsList" style="display: none">
				<li class="sort-list-item" id="id1"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort1"></i>
				<!-- 一级-->
				<div>
					 <a href="http://10.1.0.213/oneGift.shtml">中西成药</a>
                   
                     
				</div>


				<b></b>
			</h3>

			<div class="sortsub fn-clear" id="div1"
				style="display: none">
				<div class="sortsub-l">
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=684"
								target="_blank">感冒/呼吸道</a>
						</dt>
						<!--三级  -->
					</dl>
				</div>  
                           <script src="<%=ConfigurationUtil.getString("advPath")%>/categroy_1_floor.js"></script>
      
			</div>

		</li>
		<li class="sort-list-item" id="id2"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort2"></i>
				<!-- 一级-->
				<div>
					 <a href="http://10.1.0.213/onePyxides.shtml">传统中药</a>
                   
                     
				</div>


				<b></b>
			</h3>

			<div class="sortsub fn-clear" id="div2"
				style="display: none">
				<div class="sortsub-l">
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=681"
								target="_blank">中药饮片</a>
						</dt>
						<!--三级  -->
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=682"
								target="_blank">清热解表</a>
						</dd>
					</dl>
				</div>  
                           <script src="<%=ConfigurationUtil.getString("advPath")%>/categroy_2_floor.js"></script>
      
			</div>

		</li>
		<li class="sort-list-item" id="id3"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort3"></i>
				<!-- 一级-->
				<div>
					 <a href="http://10.1.0.213/oneNutrition.shtml">家庭药箱</a>
                   
                     
				</div>


				<b></b>
			</h3>

			<div class="sortsub fn-clear" id="div3"
				style="display: none">
				<div class="sortsub-l">
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=678"
								target="_blank">当季常备</a>
						</dt>
						<!--三级  -->
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=679"
								target="_blank">春季常备</a>
						</dd>
					</dl>
				</div>  
                           <script src="<%=ConfigurationUtil.getString("advPath")%>/categroy_3_floor.js"></script>
      
			</div>

		</li>
		<li class="sort-list-item" id="id4"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort4"></i>
				<!-- 一级-->
				<div>
					 <a href="http://10.1.0.213/oneMedical.shtml">药食同源</a>
                   
                     
				</div>


				<b></b>
			</h3>

			<div class="sortsub fn-clear" id="div4"
				style="display: none">
				<div class="sortsub-l">
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=674"
								target="_blank">药膳汤料</a>
						</dt>
						<!--三级  -->
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=675"
								target="_blank">生熟地</a>
						</dd>
					</dl>
				</div>  
                           <script src="<%=ConfigurationUtil.getString("advPath")%>/categroy_4_floor.js"></script>
      
			</div>

		</li>
		<li class="sort-list-item" id="id5"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort5"></i>
				<!-- 一级-->
				<div>
					 <a href="http://10.1.0.213/oneMother.shtml">参茸贵细</a>
                   
                     
				</div>


				<b></b>
			</h3>

			<div class="sortsub fn-clear" id="div5"
				style="display: none">
				<div class="sortsub-l">
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=693"
								target="_blank">参类制品</a>
						</dt>
						<!--三级  -->
					</dl>
				</div>  
                           <script src="<%=ConfigurationUtil.getString("advPath")%>/categroy_5_floor.js"></script>
      
			</div>

		</li>
		<li class="sort-list-item" id="id6"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort6"></i>
				<!-- 一级-->
				<div>
					 <a href="http://10.1.0.213/oneNursing.shtml">营养保健</a>
                   
                     
				</div>


				<b></b>
			</h3>

			<div class="sortsub fn-clear" id="div6"
				style="display: none">
				<div class="sortsub-l">
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=624"
								target="_blank">大众保健</a>
						</dt>
						<!--三级  -->
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=626"
								target="_blank">增强免疫</a>
						</dd>
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=627"
								target="_blank">改善睡眠</a>
						</dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=625"
								target="_blank">中老年保健</a>
						</dt>
						<!--三级  -->
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=628"
								target="_blank">调节三高</a>
						</dd>
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=629"
								target="_blank">骨骼保健</a>
						</dd>
					</dl>
				</div>  
                           <script src="<%=ConfigurationUtil.getString("advPath")%>/categroy_6_floor.js"></script>
      
			</div>

		</li>
		<li class="sort-list-item" id="id7"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort7"></i>
				<!-- 一级-->
				<div>
					 <a href="http://10.1.0.213/oneChineseMedicine.shtml">医疗器具</a>
                   
                     
				</div>


				<b></b>
			</h3>

			<div class="sortsub fn-clear" id="div7"
				style="display: none">
				<div class="sortsub-l">
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=630"
								target="_blank">测量器材</a>
						</dt>
						<!--三级  -->
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=632"
								target="_blank">和其正[正]</a>
						</dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=631"
								target="_blank">家庭护理</a>
						</dt>
						<!--三级  -->
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=633"
								target="_blank">康美[正]</a>
						</dd>
					</dl>
				</div>  
                           <script src="<%=ConfigurationUtil.getString("advPath")%>/categroy_7_floor.js"></script>
      
			</div>

		</li>
		<li class="sort-list-item" id="id8"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort8"></i>
				<!-- 一级-->
				<div>
					 <a href="http://10.1.0.213/oneMedicineFood.shtml">美容护理</a>
                   
                     
				</div>


				<b></b>
			</h3>

			<div class="sortsub fn-clear" id="div8"
				style="display: none">
				<div class="sortsub-l">
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=612"
								target="_blank">脸部护理</a>
						</dt>
						<!--三级  -->
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=613"
								target="_blank">复合维生素[正]</a>
						</dd>
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=614"
								target="_blank">维生素E[正]</a>
						</dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=615"
								target="_blank">皮肤护理</a>
						</dt>
						<!--三级  -->
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=616"
								target="_blank">钙片[正]</a>
						</dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=617"
								target="_blank">口腔护理</a>
						</dt>
						<!--三级  -->
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=618"
								target="_blank">镁[正]</a>
						</dd>
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=619"
								target="_blank">铁[正]</a>
						</dd>
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=620"
								target="_blank">锌[正]</a>
						</dd>
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=621"
								target="_blank">复合矿物质[正]</a>
						</dd>
					</dl>
				</div>  
                           <script src="<%=ConfigurationUtil.getString("advPath")%>/categroy_8_floor.js"></script>
      
			</div>

		</li>
		<li class="sort-list-item" id="id9"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort9"></i>
				<!-- 一级-->
				<div>
					 <a href="http://10.1.0.213/oneAdultProducts.shtml">成人用品</a>
                   
                     
				</div>


				<b></b>
			</h3>

			<div class="sortsub fn-clear" id="div9"
				style="display: none">
				<div class="sortsub-l">
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=622"
								target="_blank">避孕套</a>
						</dt>
						<!--三级  -->
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=623"
								target="_blank">杜蕾斯[正]</a>
						</dd>
					</dl>
				</div>  
                           <script src="<%=ConfigurationUtil.getString("advPath")%>/categroy_9_floor.js"></script>
      
			</div>

		</li>
		<li class="sort-list-item" id="id10"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort10"></i>
				<!-- 一级-->
				<div>
					 <a href="http://10.1.0.213/oneWesternMedicine.html">母婴专区</a>
                   
                     
				</div>


				<b></b>
			</h3>

			<div class="sortsub fn-clear" id="div10"
				style="display: none">
				<div class="sortsub-l">
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=590"
								target="_blank">婴幼儿奶粉</a>
						</dt>
						<!--三级  -->
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=591"
								target="_blank">感冒发烧[正]</a>
						</dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=593"
								target="_blank">辅食营养</a>
						</dt>
						<!--三级  -->
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=594"
								target="_blank">眼干眼涩[正]</a>
						</dd>
					</dl>
				</div>  
                           <script src="<%=ConfigurationUtil.getString("advPath")%>/categroy_10_floor.js"></script>
      
			</div>

		</li>
		<li class="sort-list-item" id="id11"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort11"></i>
				<!-- 一级-->
				<div>
					 <a href="http://10.1.0.213/oneGinseng.shtml">礼品专区</a>
                   
                     
				</div>


				<b></b>
			</h3>

			<div class="sortsub fn-clear" id="div11"
				style="display: none">
				<div class="sortsub-l">
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=601"
								target="_blank">按使用人群</a>
						</dt>
						<!--三级  -->
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=603"
								target="_blank">当归[正]</a>
						</dd>
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=602"
								target="_blank">鹿茸[正]</a>
						</dd>
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=604"
								target="_blank">阿胶[正]</a>
						</dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=596"
								target="_blank">通用保健礼品</a>
						</dt>
						<!--三级  -->
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=597"
								target="_blank">西洋参[正]</a>
						</dd>
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=598"
								target="_blank">高丽参[正]</a>
						</dd>
						<dd>
							<a
								href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=599"
								target="_blank">党参[正]</a>
						</dd>
					</dl>
				</div>  
                           <script src="<%=ConfigurationUtil.getString("advPath")%>/categroy_11_floor.js"></script>
      
			</div>

		</li>
	</ul>
</div>
<input type="hidden" id="nav" value="navIndex"/>
					<ul id="navitems" class="navitems fn-clear">
                                                                               
<li data_id="http://10.1.0.213/index.html"><a href="http://10.1.0.213/index.html">首页</a></li>

<li data_id="http://10.1.0.213/salesrank.html"><a href="http://10.1.0.213/salesrank.html">热销排行</a></li>

<li data_id="http://10.1.0.213/activitylist.html"><a href="http://10.1.0.213/activitylist.html">今日特价</a></li>

<li data_id="http://10.1.0.213/qgys.html"><a href="http://10.1.0.213/qgys.html">千古养生</a></li>

<li data_id="http://10.1.0.213/afzy.html"><a href="http://10.1.0.213/afzy.html">按方抓药</a></li>

					</ul>

					<div class="fn-right minicart">

						<a class="minicart-go fn-right" href="javascript:void(0);"  id="gotoSettlement"></a>

						<div id="shopcart" class="minicart-number fn-right">

							<a href="<%=ConfigurationUtil.getString("searchPath")%>/cart/listShopCar.action">

						购物车(<span class="number">0</span>)件

							</a>

							<div class="minicart-list fn-hide" id="nogoods">
								<div class="nogoods">
									<b></b>
									<p class="fn-block">购物车中还没有商品，赶紧选购吧！<br>您还没有 <a href="">登录</a></p>
								</div>
								
							</div>

						</div>

					</div>

				</div>

			</div>

		</div><!-- nav END -->
		<!-- nav END -->

                <!-- 登录 -->
                <!-- 新版登录注册 -->
	<div id="viewLogin" style="display: none;">
	<div class="modal-dialog"
		style="width: 420px; height: 420px; left: 350px; top: 100px;" id="loginDialog">
		<div class="modal-dialog-hd">
			<span class="dialog-hd-tit">您尚未登录</span><a hideFocus="true"
				href="javascript:void(0);" id="hideLogin" class="close"></a>
		</div>
		<input type="hidden" id="hideMark" value="0"/>
		<div class="modal-dialog-bd login-interface" style="height: 381px" id="loginIn">
			<div class="interface-con"  id="divLogin" >
				<div class="con-title">
					<ul class="fn-clear">
						<li class="current">登录</li>
						<li ><a  href="javascript:void(0);"  id="registLi" >注册</a></li>
					</ul>
				</div>
				<div class="con" >
					<form action="login" method="post" id="login">
						<input type="hidden" name="vecodeHide" id="vecodeHide" value="1" />
						<div class="wb">邮箱/用户名</div>
						<div class="wb-k">
							<input name="loginName" id="loginName" type="text" class="kuang" />
						</div>
						<div class="wb">密码</div>
						<div class="wb-k">
							<div>
								<input name="password" id="password" type="password"
									class="kuang" />
							</div>
							<div class="cw" style="display: none;" id="failDiv"></div>
						</div>
						<div id="vaCodeDiv" style="display: none;">
							<div class="wb-k verify">
								<input name="veCode" id="veCode" class="fn-text" type="text" />
								<a href="javascript:void(0);" id="changeLogins"> 
									<img id="imageCodeLogin" width="80" height="30" src="http://10.1.0.214:8088/imageAuthCode.action" alt="" />
								</a> 
								<a href="javascript:void(0);" id="changeLogin">看不清？换一张</a>
							</div>
						</div>
						<div class="w">
							<input type="hidden" id="checkboxVa"  name="checkboxVa" value="0"></input>
							<input class="checkbox" type="checkbox" name="chkRememberMe"  id="loginCheckbox"/> <span
								class="mar">自动登录</span> <span><a href="http://10.1.0.214:8088/resetPwd.action" target="_blank">忘记密码?</a></span>
						</div>
						<div class="anniu">
							<a href="javascript:void(0);" id="formSubmit"> <span class="login-btn"></span>
							</a>
						</div>
					</form>
				</div>
			</div>
			<div class="interface-con"  id="divRegist"  style="display: none;">
				<div class="con-title">
					<ul class="fn-clear">
						<li><a href="javascript:void(0);"  id="login_dialog" >登录</a></li>
						<li class="current">注册</li>
					</ul>
				</div>
				<div class="con">
					<form name="regist" id="regist" action="regist.action" method="post">
						<div class="wb">邮箱</div>
						<div class="wb-k">
							<div>
							<input name="registEmail" id="registEmail" type="text"class="kuang"  />
							</div>
							<div class="cw" style="display: none;" id="veEmailFailDiv"></div>
						</div>
						<div class="wb">用户名</div>
						<div class="wb-k">
							<div>
							<input name="loginAccount" id="loginAccount" type="text"class="kuang"  />
							</div>
							<div class="cw" style="display: none;" id="veNameFailDiv"></div>
						</div>
					<div class="wb">请设置密码</div>
					<div class="wb-k">
							<div>
							<input name="registPassword" id="registPassword" type="password"	class="kuang" />
							</div>
							<div class="cw" style="display: none;" id="vePasswordFailDiv"></div>
							<div class="cw letter fn-clear" style="width: 239px;position: absolute;left:0;top: 32px;z-index:4;border:0;background:#fff;color:#666;">
								<span id="slevel1" class="current">弱</span>
								<span id="slevel2">中</span>
								<span id="slevel3">强</span>
							</div>
					</div>
					<div class="wb">请确认密码</div>
					<div class="wb-k">
						<div>
							<input name="vePassword" id="vePassword" type="password" class="kuang" />
						</div>
						<div class="cw" style="display: none;" id="veFailDiv"></div>
					</div>
					<div class="wb-k verify">
						<div>
							<input name="veCode" id="veCodeRegist" class="fn-text" type="text" /> 
							<a href="javascript:void(0);">
							<img id="imageCodeRegist" width="80"	height="30" src="http://10.1.0.214:8088/imageAuthCode.action" alt="" /> </a> 
							<a href="javascript:void(0);" id="changeRegist">看不清？换一张</a>
						</div>
						<div class="cw" style="display: none;" id="veCodeFailDiv"></div>
					</div>
					<div class="w">
						<input id="ckdivreg" class="checkbox" type="checkbox"  checked="checked"  name="chkRememberMe"/>
						<span class="mar">我已阅读并接受</span> <span><a href="javascript:void(0)">《用户协议》</a></span>
					</div>
					<div class="anniu">
						<a href="javascript:void(0);"  id="regist_link"><span class="signin-btn"></span></a>
					</div>
					</form>
				</div>
			</div>

		</div>
	</div>
	<div class="modal-mask"></div>
</div>
                <!-- login END -->
		
<input type="hidden" id="cssAndJsPath" data_id="<%=ConfigurationUtil.getString("cssAndJsPath")%>"/>
<input type="hidden" id="cmsPath" data_id="<%=ConfigurationUtil.getString("cmsPath")%>"/>
<input type="hidden" id="picPath" data_id="<%=ConfigurationUtil.getString("picPath")%>"/>
<input type="hidden" id="detailPath" data_id="<%=ConfigurationUtil.getString("detailPath")%>"/>
<input type="hidden" id="advPath" data_id="<%=ConfigurationUtil.getString("advPath")%>"/>
<input type="hidden" id="portalPath" data_id="<%=ConfigurationUtil.getString("searchPath")%>"/> 
<input type="hidden" id="staticPath" data_id=""/> 
