<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.kmzyc.zkconfig.ConfigurationUtil" %>
		<title>康美中药城</title>
<!--#include file="./adv/testAdv.shtml"-->

                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

		<meta name="Keywords" content=""/>

		<meta name="Description" content=""/>

		
		<!-- 顶部导航条 -->
			<div class="i-topbar">
    <div class="l-w fn-clear">
        <p class="fn-left loginbar" id="loginbar">
            您好，欢迎来到康美中药城！
        </p>
        <ul class="fn-right topmenu">
            <li class="topmenu-item topmenu-item-phone">
                <i class="ico-phone">
                </i>
                客服热线：
                <strong>
                    400-6600-518
                </strong>
            </li>
            
            <li class="topmenu-item">
                <b>
                </b>
               <a href="javascript:void(0);" class="j_settle">商家入驻</a>
            </li>
            <li class="topmenu-item topmenu-item-collect">
                <b>
                </b>
                <i class="ico-collect">
                </i>
                <a href="javascript:void(0);" id="addCookie" title="康美中药城">
                    收藏本站
                </a>
            </li>
            <li class="topmenu-item">
                <b>
                </b>
                <a href="<%=ConfigurationUtil.getString("staticPath_B2B")%>/helps/index.shtml">
                    帮助中心
                </a>
            </li>
        </ul>
    </div>
</div>
		<!-- topbar END -->

		<!-- 头部 -->
			<div class="i-head">
	<div class="l-w fn-clear">
		<div class="logo fn-left">

				<a href="<%=ConfigurationUtil.getString("staticPath_B2B")%>/index.html" hidefocus="true"><img height="115" src="<%=ConfigurationUtil.getString("cssAndJsPath_B2B")%>res/images/common/logo.png" alt="康美中药城" /></a>

		</div>
		<div class="i-search fn-left">
                <div class="search-cont">
					<form id="searchForm" method="POST"  onSubmit="if(!this.action) return false;">
                           <div class="search-triggers">
                <ul class="switchable-nav">
                    <li data-value="1" class="curr"><a>商品</a></li>
                    <li data-value="2"><as>店铺</a></li>
                </ul>
                <s class="icon-btn-arrow-h"></s>
            </div>
						<div class="form">
							<input id="keyword" value="请输入搜索关键字" type="text" class="text" state="begin" maxlength="60" />
							<input id="searchBtn" type="button" value="搜索" class="button" />
						</div>
					</form>
                </div>
          
          
          
			<div class="search-hotwords">
				<strong>热门搜索：</strong>
				
                                <a target="_blank" href="http://10.1.0.213/">情人节</a>
<a target="_blank" href="http://10.1.0.210:8090/10/search.action?kw=%E8%8F%8A%E8%8A%B1%E8%8C%B6">菊花茶</a>
<a target="_blank" href="http://10.1.0.210:8090/10/search.action?kw=%E5%BA%B7%E7%BE%8E%E4%B9%8B%E6%81%8B">康美中药城</a>
<a target="_blank" href="http://10.1.0.210:8090/10/search.action?kw=%E6%8F%90%E5%8D%87%E5%85%8D%E7%96%AB%E5%8A%9B">提升免疫力</a>
<a target="_blank" href="http://10.1.0.210:8090/10/search.action?kw=%E9%87%91%E8%BE%BE%E5%85%8B%E5%AE%81">金达克宁</a>
<a target="_blank" href="http://10.1.0.210:8090/10/search.action?kw=%E4%B9%B3%E8%86%8F">乳膏</a>

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
		<a href="/index.html" id="showOtherGoodsList">所有商品分类<b></b> </a>
	</h2>
       <ul class="sort-list" id="otherGoodsList" style="display: none">
				<li class="sort-list-item" id="id1"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort488"></i>
				<!-- 一级-->
				<div>
					     <a href="">养生茶饮</a>
                     
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
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=522"
								target="_blank">清润养生</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=523" target="_blank">菊皇茶</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=728"
								target="_blank">调理气血</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=729" target="_blank">玫瑰花</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=730"
								target="_blank">清肝明目</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=732" target="_blank">贡菊花</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=731" target="_blank">杭白菊</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=733" target="_blank">胎菊</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=883"
								target="_blank">众圣植物茶</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=884" target="_blank">养生植物茶</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=736"
								target="_blank">爱这茶语</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=737" target="_blank">钻石茶包</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=734"
								target="_blank">清热解毒</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=735" target="_blank">金银花</a></em>
                                                </dd>
					</dl>
				</div>  
     

			
			


			
			</div>

		</li>
		<li class="sort-list-item" id="id2"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort464"></i>
				<!-- 一级-->
				<div>
					     <a href="">成人用品</a>
                     
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
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=468"
								target="_blank">验孕/排卵</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=469" target="_blank">排卵检测试纸/盒</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=470" target="_blank">妊娠诊断试纸/盒</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=471"
								target="_blank">润滑/延时</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=472" target="_blank">润滑液</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=473" target="_blank">延时液</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=474"
								target="_blank">男用器具</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=478" target="_blank">充气娃娃</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=477" target="_blank">后庭塞</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=475" target="_blank">延时环/震震环</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=476" target="_blank">后庭拉珠</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=479" target="_blank">飞机杯</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=480" target="_blank">名器倒模</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=481"
								target="_blank">女用器具</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=483" target="_blank">跳蛋</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=482" target="_blank">按摩棒</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=484" target="_blank">仿真阳具</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=485"
								target="_blank">情趣内衣</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=487" target="_blank">性感内衣</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=486" target="_blank">角色扮演服</a></em>
                                                </dd>
					</dl>
				</div>  
     

			
			


			
			</div>

		</li>
		<li class="sort-list-item" id="id3"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort419"></i>
				<!-- 一级-->
				<div>
					     <a href="">保健用品</a>
                     
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
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=601"
								target="_blank">修复产品</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=603" target="_blank">足部</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=600"
								target="_blank">理疗产品</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=602" target="_blank">筋骨类</a></em>
                                                </dd>
					</dl>
				</div>  
     

			
			


			
			</div>

		</li>
		<li class="sort-list-item" id="id4"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort420"></i>
				<!-- 一级-->
				<div>
					     <a href="">个人护理</a>
                     
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
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=421"
								target="_blank">脸部护理</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=423" target="_blank">洁面</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=422" target="_blank">祛痘</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=425" target="_blank">眼部护理</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=424" target="_blank">面膜</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=427" target="_blank">爽肤水</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=426" target="_blank">乳液/面霜</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=428" target="_blank">鼻贴</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=429"
								target="_blank">皮肤护理</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=430" target="_blank">补水保湿</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=431" target="_blank">防晒隔离</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=433" target="_blank">祛臭止汗</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=434" target="_blank">疤痕修复</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=432" target="_blank">祛痱止痒</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=435"
								target="_blank">口腔护理</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=436" target="_blank">口气清新剂</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=437" target="_blank">牙刷/牙线</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=438" target="_blank">漱口水</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=439" target="_blank">牙膏</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=440"
								target="_blank">身体护理</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=441" target="_blank">洗发护发</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=442" target="_blank">手足护理</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=444" target="_blank">脱毛</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=443" target="_blank">私处洗液</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=445"
								target="_blank">男士护理</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=447" target="_blank">爽肤</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=446" target="_blank">男士洁面</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=448" target="_blank">祛臭止汗</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=449" target="_blank">其它</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=961"
								target="_blank">生活用品</a>
						</dt>
						<!--三级  -->
						   <dd>
                                                </dd>
					</dl>
				</div>  
     

			
			


			
			</div>

		</li>
		<li class="sort-list-item" id="id5"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort392"></i>
				<!-- 一级-->
				<div>
					     <a href="">营养保健</a>
                     
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
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=584"
								target="_blank">矿物类</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=932" target="_blank">补锌</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=931" target="_blank">补铁</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=930" target="_blank">补钙</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=933" target="_blank">钙铁锌</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=585"
								target="_blank">维生素</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=598" target="_blank">复合维生素</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=596" target="_blank">维生素E</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=595" target="_blank">维生素C</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=597" target="_blank">维生素D</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=927" target="_blank">维生素A</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=926" target="_blank">B族维生素</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=928" target="_blank">叶酸</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=934"
								target="_blank">调节免疫</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=936" target="_blank">牛初乳</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=935" target="_blank">蜂胶</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=938" target="_blank">大蒜油</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=937" target="_blank">硒</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=939" target="_blank">肾茶</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=940"
								target="_blank">美容养颜</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=942" target="_blank">葡萄籽</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=941" target="_blank">胶原蛋白</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=943" target="_blank">芦荟</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=944"
								target="_blank">调节三高</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=948" target="_blank">丹参</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=947" target="_blank">磷脂</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=946" target="_blank">银杏红曲</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=945" target="_blank">鱼油</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=950"
								target="_blank">润肠通便</a>
						</dt>
						<!--三级  -->
						   <dd>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=949"
								target="_blank">护眼明目</a>
						</dt>
						<!--三级  -->
						   <dd>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=951"
								target="_blank">增加骨密度</a>
						</dt>
						<!--三级  -->
						   <dd>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=952"
								target="_blank">减肥美体</a>
						</dt>
						<!--三级  -->
						   <dd>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=954"
								target="_blank">改善记忆力</a>
						</dt>
						<!--三级  -->
						   <dd>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=953"
								target="_blank">改善睡眠</a>
						</dt>
						<!--三级  -->
						   <dd>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=955"
								target="_blank">按人群分类</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=956" target="_blank">男士保健</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=957" target="_blank">女士保健</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=958" target="_blank">老年保健</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=959" target="_blank">儿童保健</a></em>
                                                </dd>
					</dl>
				</div>  
     

			
			


			
			</div>

		</li>
		<li class="sort-list-item" id="id6"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort524"></i>
				<!-- 一级-->
				<div>
					     <a href="">参茸贵细</a>
                     
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
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=604"
								target="_blank">西洋参</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=607" target="_blank">西洋参</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=605"
								target="_blank">鹿茸</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=608" target="_blank">鹿茸</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=606"
								target="_blank">石斛</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=609" target="_blank">康美石斛</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=783"
								target="_blank">人参</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=784" target="_blank">人参</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=785"
								target="_blank">三七</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=786" target="_blank">三七</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=787"
								target="_blank">燕窝</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=789" target="_blank">燕窝</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=788"
								target="_blank">阿胶</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=790" target="_blank">阿胶</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=791"
								target="_blank">灵芝</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=792" target="_blank">灵芝</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=827"
								target="_blank">粉剂礼盒</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=828" target="_blank">粉剂礼盒</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=862"
								target="_blank">丹参</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=863" target="_blank">丹参</a></em>
                                                </dd>
					</dl>
				</div>  
     

			
			


			
			</div>

		</li>
		<li class="sort-list-item" id="id7"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort526"></i>
				<!-- 一级-->
				<div>
					     <a href="">精品汤料</a>
                     
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
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=632"
								target="_blank">理气和中</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=652" target="_blank">陈皮</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=570"
								target="_blank">滋阴补虚</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=571" target="_blank">枸杞子</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=636" target="_blank">麦冬</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=572" target="_blank">红枣</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=637" target="_blank">龙眼肉</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=638" target="_blank">玉竹</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=640" target="_blank">虫草花</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=639" target="_blank">灵芝片</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=641" target="_blank">白莲子</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=642" target="_blank">百合</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=610"
								target="_blank">补气补阳</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=645" target="_blank">北沙参</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=643" target="_blank">山药</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=644" target="_blank">党参</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=647" target="_blank">核桃仁</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=646" target="_blank">黄芪</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=631"
								target="_blank">调理气血</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=648" target="_blank">玫瑰花</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=649" target="_blank">核桃仁</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=650" target="_blank">当归头片</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=651" target="_blank">三七片</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=633"
								target="_blank">清肝明目</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=653" target="_blank">杭白菊</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=654" target="_blank">贡菊花</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=655" target="_blank">胎菊</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=634"
								target="_blank">温里驱寒</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=657" target="_blank">大茴香</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=658" target="_blank">胡椒</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=656" target="_blank">小茴香</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=659" target="_blank">花椒</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=660" target="_blank">肉桂</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=661" target="_blank">丁香</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=635"
								target="_blank">清热解毒</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=662" target="_blank">金银花</a></em>
                                                </dd>
					</dl>
				</div>  
     

			
			


			
			</div>

		</li>
		<li class="sort-list-item" id="id8"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort665"></i>
				<!-- 一级-->
				<div>
					     <a href="">健康食材</a>
                     
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
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=666"
								target="_blank">补虚类</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=668" target="_blank">补血类</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=667" target="_blank">补气类</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=669" target="_blank">补阳类</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=670" target="_blank">补阴类</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=671"
								target="_blank">解表类</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=672" target="_blank">发散风寒类</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=673" target="_blank">发散风热类</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=674"
								target="_blank">清热类</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=675" target="_blank">清热泻火</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=677" target="_blank">清热解毒</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=678" target="_blank">清热凉血</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=676" target="_blank">清热燥湿</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=679" target="_blank">清虚热</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=680"
								target="_blank">泻下类</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=682" target="_blank">攻下类</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=687" target="_blank">峻下逐水</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=683" target="_blank">润下类</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=681"
								target="_blank">祛风湿类</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=684" target="_blank">祛风寒湿</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=685" target="_blank">祛风湿热</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=686" target="_blank">去风湿强筋骨</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=688"
								target="_blank">祛湿类</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=689" target="_blank">利水消肿</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=690" target="_blank">利尿通淋</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=691" target="_blank">利湿退黄</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=692"
								target="_blank">化湿类</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=693" target="_blank">化湿类</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=694"
								target="_blank">温里类</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=709" target="_blank">温里类</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=695"
								target="_blank">理气类</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=710" target="_blank">理气类</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=696"
								target="_blank">消食类</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=711" target="_blank">消食类</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=697"
								target="_blank">驱虫类</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=712" target="_blank">驱虫类</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=698"
								target="_blank">止血类</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=713" target="_blank">止血类</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=700"
								target="_blank">化痰止咳平喘</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=715" target="_blank">化痰止咳平喘</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=699"
								target="_blank">活血化瘀类</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=714" target="_blank">活血化瘀</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=701"
								target="_blank">安神类</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=716" target="_blank">重镇安神</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=717" target="_blank">养心安神</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=702"
								target="_blank">平肝熄风</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=719" target="_blank">息风止痉</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=718" target="_blank">平抑肝阳</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=703"
								target="_blank">开窍类</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=720" target="_blank">开窍类</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=704"
								target="_blank">收敛类</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=721" target="_blank">固表止汗</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=722" target="_blank">敛肺涩肠</a></em>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=723" target="_blank">固精缩尿</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=705"
								target="_blank">攻毒杀虫止痒</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=724" target="_blank">攻毒 杀虫 止痒</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=706"
								target="_blank">拔毒化腐生肌</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=725" target="_blank">拔毒 化腐 生肌</a></em>
                                                </dd>
					</dl>
				</div>  
     

			
			


			
			</div>

		</li>
		<li class="sort-list-item" id="id9"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort525"></i>
				<!-- 一级-->
				<div>
					     <a href="">新开河参</a>
                     
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
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=573"
								target="_blank">新开河参</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=576" target="_blank">新开河参</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=575"
								target="_blank">新开河套装</a>
						</dt>
						<!--三级  -->
						   <dd>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=574"
								target="_blank">精品新开河参</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=577" target="_blank">精品新开河参</a></em>
                                                </dd>
					</dl>
				</div>  
     

			
			


			
			</div>

		</li>
		<li class="sort-list-item" id="id10"
			>

			<h3 class="sort-list-title">
				<i class="ico-sort726"></i>
				<!-- 一级-->
				<div>
					     <a href="">绿色食品</a>
                     
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
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=727"
								target="_blank">玛咖</a>
						</dt>
						<!--三级  -->
						   <dd>
						
							<em><a href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=802" target="_blank">玛咖</a></em>
                                                </dd>
					</dl>
					<dl class="sortsub-item">
						<!-- 二级 -->
						<dt>
							<a
								href="<%=ConfigurationUtil.getString("searchPath_B2B")%>/operCateSearch.action?oid=960"
								target="_blank">清谷新禾</a>
						</dt>
						<!--三级  -->
						   <dd>
                                                </dd>
					</dl>
				</div>  
     

			
			


			
			</div>

		</li>
	</ul>
</div>
<input type="hidden" id="nav" value="navIndex"/>
					<ul id="navitems" class="navitems fn-clear">
                                                                               
<li data_id="http://10.1.0.213/index.html"><a href="http://10.1.0.213/index.html">首页</a></li>

<li data_id="http://10.1.0.213/afzy.html"><a href="http://10.1.0.213/afzy.html">按方抓药</a></li>

<li data_id="http://10.1.0.213/qgys.html"><a href="http://10.1.0.213/qgys.html">千古养生</a></li>

<li data_id="http://10.1.0.213/salesrank.html"><a href="http://10.1.0.213/salesrank.html">热销排行</a></li>

					</ul>

					<div class="fn-right minicart">

						<a class="minicart-go fn-right" href="javascript:void(0);"  id="gotoSettlement"></a>

						<div id="shopcart" class="minicart-number fn-right">

							<a href="<%=ConfigurationUtil.getString("portalPath_B2B")%>/cart/listShopCar.action">

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

<!--#include file="/adv/testAdv.shtml"-->
		<!-- nav END -->

                <!-- 登录 -->
              <!--    login END -->
	
<input type="hidden" id="cssAndJsPath" data_id="<%=ConfigurationUtil.getString("cssAndJsPath_B2B")%>"/>
<input type="hidden" id="cmsPath" data_id="<%=ConfigurationUtil.getString("cmsPath_B2B")%>"/>
<input type="hidden" id="picPath" data_id="<%=ConfigurationUtil.getString("picPath_B2B")%>"/>
<input type="hidden" id="detailPath" data_id="<%=ConfigurationUtil.getString("detailPath_B2B")%>"/>
<input type="hidden" id="advPath" data_id="<%=ConfigurationUtil.getString("advPath_B2B")%>"/>
<input type="hidden" id="portalPath" data_id="<%=ConfigurationUtil.getString("portalPath_B2B")%>"/> 
<input type="hidden" id="staticPath" data_id="<%=ConfigurationUtil.getString("staticPath_B2B")%>"/> 
<input type="hidden" id="facade_path" data_id="<%=ConfigurationUtil.getString("searchPath_B2B")%>"/>
<input type="hidden" id="gysPortalPath" data_id="http://10.1.0.214:7088">

