<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="com.kmzyc.zkconfig.ConfigurationUtil" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  
<div class="i-topbar">
    <div class="l-w fn-clear">
        <p class="fn-left loginbar" id="loginbar">
            您好，欢迎来到康美中药城商城！
        </p>
        <ul class="fn-right topmenu">
            <li class="topmenu-item topmenu-item-app">
              <i class="ico-app"></i><a href="http://www.kmb2b.com/app/download.html" target="_blank">手机康美</a>
                <div class="app-code-top"><img src="http://img.kmb2b.com/cms/picB2B/app-code-1460017670337.gif" alt="手机康美"></div>
            </li>
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
			<a href="<%=ConfigurationUtil.getString("portalPath")%>/member/toMailSubscription.action">
				邮件订阅
			</a>
			</li>
            <li class="topmenu-item">
                <b>
                </b>
                <a href="javascript:void(0);" class="j_settle">商家登录</a>
            </li>
            <li class="topmenu-item topmenu-item-collect">
                <b>
                </b>
                <i class="ico-collect">
                </i>
                <a href="javascript:void(0);" id="addCookie" >
                    收藏本站
                </a>
            </li>
            <li class="topmenu-item">
                <b>
                </b>
                <a href="<%=ConfigurationUtil.getString("staticPath")%>/helps/sd/index.shtml">
                    帮助中心
                </a>
            </li>
        </ul>
    </div>
</div>

<div class="i-head l-w">
		<!--首页标语广告位-->
    	
  
  <!--首页标语广告位 end-->
        <div class="i-head-logo fn-clear">
			<!--首页标志窗口-->
			<div class="logo fn-left j_adminDiv" data-ids="145068" data-type="1">   



  <a href="http://10.1.0.213/sdzqshouye.html" target="_blank">
	 <img src="<%=ConfigurationUtil.getString("cmsPath")%>/1466502063602.png" alt="康美中药城">
 </a>
	
         
 

 
 
    
</div>

        <div class="i-search fn-left">
	      <form id="searchForm" method="POST" onSubmit="if(!this.action) return false;">
            <div class="search-tab-hd">
                <ul>
                    <li  data-value="1" class="curr">商品<i></i></li>
                    |
                    <li  data-value="2">店铺<i></i></li>
                </ul>
              </div>
            <div class="search-cont">
                 <div class="form">
                   
                     
  

   <label for="keyword" class="label_search" id="label_key" style="display:block"  data_url="http://search.kmb2b.com/10/search?kw=%E8%86%B3%E9%A3%9F%E6%B1%89%E6%96%B9">康美膳食汉方</label>
  
   


   <input name="search" id="keyword"  type="text" class="text" value="" state="begin" maxlength="60" >
    




                    <i class="search-icon"></i>
                    <input id="searchBtn" type="button" value="搜索" class="button" />
                 </div>
               </div>
            </form>
				<!--热门搜索窗口-->
                <div class="search-hotwords j_adminDiv" data-ids="1661" data-type="1">
                    <strong>热门搜索：</strong>
<a target="_blank" style="color:red;" href="http://www.kmb2b.com/qingcang.html">清仓快抢</a>
                                                      <a target="_blank" href="http://www.kmb2b.com/health06.html#n2">满50送50</a>
                                                      <a target="_blank" href="http://www.kmb2b.com/tejia.html">1元抢</a>
                                                      <a target="_blank" href="http://www.kmb2b.com/topic/zqdw.html">粽子包邮</a>
                                                      <a target="_blank" href="http://www.kmb2b.com/health06.html#n4">韩束减20</a>
                                                      <a target="_blank" href="http://www.kmb2b.com/products/86899.html">清补凉</a>
                                                      <a target="_blank" href="http://www.kmb2b.com/tuan2.html">团购</a>
</div>
            </div>
          <!-- 
                  <ul class="security fn-right">
                <li class="s-icon1"><a href="http://www.kmb2b.com/helps/infor-1421399788562.shtml" target="_blank"><i></i>全网比价<br><span class="fn-f12">贵就赔</span></a></li>
                <li class="s-icon2"><a href="http://www.kmb2b.com/helps/infor-1421399864543.shtml" target="_blank"><i></i>服务至上 投诉有礼</a></li>
                <li class="s-icon3"><a href="http://www.kmb2b.com/helps/infor-1421399932746.shtml" target="_blank"><i></i>全场顺丰 满69包邮</a></li>
            </ul>	
  -->
        </div>
 
  

  
  
       <div class="i-nav">
            <div class="nav-cont fn-clear">
                <div  id="sortGoodsList"  class="sort">
                    <h2 class="sort-link">
                        <a href="http://www.kmb2b.com/">全部商品分类<b></b></a>
                    </h2>
                    <ul id="otherGoodsList" class="sort-list sider-list" style="display: none">
					<!--商品分类1楼-->
					  <!-- newOtherheader -->
                        <li class="sort-list-item  critical-gift" id="id1">
                            <div class="sort-list-title j_adminDiv" data-ids="11041" data-type="1">

							  <i class="ico-sort419 ico-co1" data-categoryId="2982"></i>
											<h3>
												中西药品
										    </h3>	
                                       <p>

										<em><a href="http://search.kmb2b.com/10/search.action?kw=%E5%8F%91%E7%83%AD%E5%A4%B4%E7%97%9B">发热头痛</a></em>
										<em><a href="http://www.kmb2b.com/dayaofang.html">大药房</a></em>
										<em><a href="http://www.kmb2b.com/topic/jiatingyisheng.html">家庭医药</a></em>
								</p>
                                <b></b>
                               <span class="arrow"  style="display: none"></span>
                            </div>
							
						<div class="sortsub fn-clear" id="div1" style="display: none">
							<div class="sortsub-l">
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3047" target="_blank">
												外用药
											</a>
										</dt>
										<dd>
												<em class="1_1">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3050" target="_blank">
														皮炎/湿疹
													</a>
												</em>
												<em class="1_2">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3049" target="_blank">
														烧烫伤
													</a>
												</em>
												<em class="1_3">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3052" target="_blank">
														跌打用药
													</a>
												</em>
												<em class="1_4">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3053" target="_blank">
														贴/药膏
													</a>
												</em>
												<em class="1_5">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3051" target="_blank">
														手/脚/体癣
													</a>
												</em>
												<em class="1_6">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3048" target="_blank">
														驱风油
													</a>
												</em>
												<em class="1_7">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3055" target="_blank">
														创可贴
													</a>
												</em>
												<em class="1_8">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3054" target="_blank">
														祛疤
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2984" target="_blank">
												感冒用药
											</a>
										</dt>
										<dd>
												<em class="2_1">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2987" target="_blank">
														鼻塞流涕
													</a>
												</em>
												<em class="2_2">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2988" target="_blank">
														中药感冒药
													</a>
												</em>
												<em class="2_3">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2985" target="_blank">
														咽喉肿痛
													</a>
												</em>
												<em class="2_4">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2986" target="_blank">
														发热头痛
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3028" target="_blank">
												维生素矿物质
											</a>
										</dt>
										<dd>
												<em class="3_1">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3043" target="_blank">
														维生素
													</a>
												</em>
												<em class="3_2">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3046" target="_blank">
														孕妇（备孕）专用
													</a>
												</em>
												<em class="3_3">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3042" target="_blank">
														复合维矿
													</a>
												</em>
												<em class="3_4">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3045" target="_blank">
														小儿专用
													</a>
												</em>
												<em class="3_5">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3044" target="_blank">
														矿物质（钙铁锌硒）
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3021" target="_blank">
												滋补药
											</a>
										</dt>
										<dd>
												<em class="4_1">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3027" target="_blank">
														健脑安神
													</a>
												</em>
												<em class="4_2">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3026" target="_blank">
														补益脾胃
													</a>
												</em>
												<em class="4_3">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3024" target="_blank">
														阴阳双补
													</a>
												</em>
												<em class="4_4">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3022" target="_blank">
														阴虚
													</a>
												</em>
												<em class="4_5">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3023" target="_blank">
														肾阳虚
													</a>
												</em>
												<em class="4_6">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3025" target="_blank">
														补气/补血
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3016" target="_blank">
												五官科用药
											</a>
										</dt>
										<dd>
												<em class="5_1">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3018" target="_blank">
														鼻科用药
													</a>
												</em>
												<em class="5_2">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3017" target="_blank">
														眼科用药
													</a>
												</em>
												<em class="5_3">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3020" target="_blank">
														咽喉用药
													</a>
												</em>
												<em class="5_4">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3019" target="_blank">
														口腔用药
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2995" target="_blank">
												消化道用药
											</a>
										</dt>
										<dd>
												<em class="6_1">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3001" target="_blank">
														驱虫
													</a>
												</em>
												<em class="6_2">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2998" target="_blank">
														消化不良/胃胀
													</a>
												</em>
												<em class="6_3">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2996" target="_blank">
														胃痛/胃炎/肠胃炎
													</a>
												</em>
												<em class="6_4">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3002" target="_blank">
														便秘排毒
													</a>
												</em>
												<em class="6_5">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2999" target="_blank">
														腹泻
													</a>
												</em>
												<em class="6_6">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3000" target="_blank">
														中药养胃
													</a>
												</em>
												<em class="6_7">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2997" target="_blank">
														反酸/胃酸过多
													</a>
												</em>
												<em class="6_8">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3003" target="_blank">
														痔疮
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2989" target="_blank">
												呼吸系统用药
											</a>
										</dt>
										<dd>
												<em class="7_1">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2993" target="_blank">
														止咳平喘
													</a>
												</em>
												<em class="7_2">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2994" target="_blank">
														感冒咳嗽
													</a>
												</em>
												<em class="7_3">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2991" target="_blank">
														祛痰
													</a>
												</em>
												<em class="7_4">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2992" target="_blank">
														止咳化痰
													</a>
												</em>
												<em class="7_5">
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2990" target="_blank">
														止咳（干咳）
													</a>
												</em>
										</dd>
									</dl>
							</div>

					     <div class="sortsub-r">

                            <div class="sortsub-hotbrand">
                              <!--#include file="/adv/sort_f1.shtml"-->
								<a href="http://www.kmb2b.com/zhongxi.html"  target="_blank"><img src="<%=ConfigurationUtil.getString("cmsPath")%>/1462772826156.jpg" alt=""></a>
                             
                            </div>
							<a href="http://search.kmb2b.com/10/search.action?kw=%E6%AD%A2%E5%92%B3%E5%8C%96%E7%97%B0" class="sortsub-bt"  target="_blank">止咳化痰</a>
                        
                        </div>

						</div>
                        </li>

					<!--商品分类2楼-->
					                        <li class="sort-list-item  critical-gift" id="id1">
                            <div class="sort-list-title j_adminDiv" data-ids="11042" data-type="1">

							  <i class="ico-sort420 ico-co2" data-categoryId="2625"></i>
											<h3>
												营养保健1
										    </h3>	
                                       <p>

										<em><a href="http://search.kmb2b.com/10/operCateSearch.action?oid=4056">减肥瘦身</a></em>
										<em><a href="http://search.kmb2b.com/10/operCateSearch.action?oid=4996">补充维生素</a></em>
										<em><a href="http://search.kmb2b.com/10/operCateSearch.action?oid=4990">调节三高</a></em>
								</p>
                                <b></b>
                               <span class="arrow"  style="display: none"></span>
                            </div>
							
						<div class="sortsub fn-clear" id="div1" style="display: none">
							<div class="sortsub-l">
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2629" target="_blank">
												动植物精华
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2744" target="_blank">
														猴头菇
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2742" target="_blank">
														玛咖
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3442" target="_blank">
														银杏提取物
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2743" target="_blank">
														左旋肉碱
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2741" target="_blank">
														牛初乳
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2739" target="_blank">
														亚麻籽油
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2735" target="_blank">
														大蒜精油
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2733" target="_blank">
														芦荟
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=5864" target="_blank">
														红景天
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2737" target="_blank">
														葡萄籽
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=5965" target="_blank">
														螺旋藻
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=5964" target="_blank">
														叶黄素
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=5962" target="_blank">
														大豆异黄酮/提取物
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=5882" target="_blank">
														番茄红素
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4383" target="_blank">
														海豹油
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=5384" target="_blank">
														其他植物提取物
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4042" target="_blank">
												按功效
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4991" target="_blank">
														辅助降糖
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4985" target="_blank">
														排毒养颜
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4987" target="_blank">
														改善睡眠
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4990" target="_blank">
														调节三高
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4995" target="_blank">
														补充钙铁锌
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4986" target="_blank">
														缓解疲劳
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=6703" target="_blank">
														提高免疫
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4992" target="_blank">
														心脏养护
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4994" target="_blank">
														预防近视
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4996" target="_blank">
														补充维生素
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=6702" target="_blank">
														补气补血
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4993" target="_blank">
														益智健脑
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=5242" target="_blank">
														促进生长
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4056" target="_blank">
														减肥瘦身
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2628" target="_blank">
												营养素
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3450" target="_blank">
														复合型营养
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4362" target="_blank">
														蜂胶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2662" target="_blank">
														蛋白粉
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2664" target="_blank">
														氨基酸
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2721" target="_blank">
														磷脂
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4382" target="_blank">
														辅酶Q10
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=5963" target="_blank">
														褪黑素
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2718" target="_blank">
														鱼肝油
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2722" target="_blank">
														胶原蛋白
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2725" target="_blank">
														鱼油
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2626" target="_blank">
												维生素1
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2650" target="_blank">
														复合维生素
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2640" target="_blank">
														维生素C1
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2651" target="_blank">
														胡萝卜素
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2652" target="_blank">
														叶酸
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2643" target="_blank">
														B族维生素
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2642" target="_blank">
														维生素D
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2641" target="_blank">
														维生素A
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=6862" target="_blank">
														维生素AD
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3422" target="_blank">
														维生素E
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2627" target="_blank">
												矿物质
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2658" target="_blank">
														复合钙
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2659" target="_blank">
														儿童钙
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2660" target="_blank">
														牛乳钙
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2656" target="_blank">
														补硒
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2655" target="_blank">
														补锌
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2653" target="_blank">
														补铁
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2654" target="_blank">
														补镁
													</a>
												</em>
										</dd>
									</dl>
							</div>

					     <div class="sortsub-r">

                            <div class="sortsub-hotbrand">
							    <!--#include file="/adv/sort_f2.shtml"-->
								<a href="http://www.kmb2b.com/baoweizhan.html"  target="_blank"><img src="<%=ConfigurationUtil.getString("cmsPath")%>/1462772994060.png" alt=""></a>
                             
                            </div>
							<a href="" class="sortsub-bt"  target="_blank">营养保健</a>
                        
                        </div>

						</div>
                        </li>

					<!--商品分类3楼-->
					                        <li class="sort-list-item  critical-gift" id="id1">
                            <div class="sort-list-title j_adminDiv" data-ids="11043" data-type="1">

							  <i class="ico-sort450 ico-co3" data-categoryId="2785"></i>
											<h3>
												滋补养生
										    </h3>	
                                       <p>

										<em><a href="http://search.kmb2b.com/10/operCateSearch.action?oid=2802">冬虫夏草</a></em>
										<em><a href="http://search.kmb2b.com/10/operCateSearch.action?oid=13655">膳食汉方</a></em>
										<em><a href="http://search.kmb2b.com/10/operCateSearch.action?oid=2788">人参</a></em>
										<em><a href="http://search.kmb2b.com/10/operCateSearch.action?oid=2800">石斛</a></em>
								</p>
                                <b></b>
                               <span class="arrow"  style="display: none"></span>
                            </div>
							
						<div class="sortsub fn-clear" id="div1" style="display: none">
							<div class="sortsub-l">
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=6902" target="_blank">
												蜂蜜类
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=6922" target="_blank">
														九龙藤蜜
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=6926" target="_blank">
														人参蜜膏
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=6924" target="_blank">
														玫瑰蜜
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=6928" target="_blank">
														蓝莓蜜
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=6923" target="_blank">
														龙眼花蜜
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=6925" target="_blank">
														百花蜜
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=6927" target="_blank">
														椴树蜜
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2795" target="_blank">
												贵细滋补
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2803" target="_blank">
														当归
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2802" target="_blank">
														冬虫夏草
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2800" target="_blank">
														石斛
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4950" target="_blank">
														阿胶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2804" target="_blank">
														三七
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4242" target="_blank">
														金线莲
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4322" target="_blank">
														玛咖果/片
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2798" target="_blank">
														鹿茸
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2797" target="_blank">
														灵芝
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2796" target="_blank">
														燕窝
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2787" target="_blank">
												参类
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2789" target="_blank">
														新开河参
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2794" target="_blank">
														党参
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2788" target="_blank">
														人参
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2791" target="_blank">
														西洋参
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2792" target="_blank">
														红参
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=5142" target="_blank">
														花旗参
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2805" target="_blank">
												枣类
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4864" target="_blank">
														酸枣
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2806" target="_blank">
														玉枣
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4862" target="_blank">
														骏枣
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4882" target="_blank">
														大枣
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4863" target="_blank">
														灰枣
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=2807" target="_blank">
														蜜枣
													</a>
												</em>
										</dd>
									</dl>
							</div>

					     <div class="sortsub-r">

                            <div class="sortsub-hotbrand">
							    <!--#include file="/adv/sort_f3.shtml"-->
								<a href="http://www.kmb2b.com/topic/Summerbegin.html"  target="_blank"><img src="<%=ConfigurationUtil.getString("cmsPath")%>/1462773152694.jpg" alt=""></a>
                             
                            </div>
							<a href="http://www.kmb2b.com/huimuseum.html" class="sortsub-bt"  target="_blank">保健馆</a>
                        
                        </div>

						</div>
                        </li>

					<!--商品分类4楼-->
					                        <li class="sort-list-item  critical-gift" id="id1">
                            <div class="sort-list-title j_adminDiv" data-ids="11044" data-type="1">

							  <i class="ico-sort392 ico-co4" data-categoryId="2631"></i>
											<h3>
												药食同源
										    </h3>	
                                       <p>

										<em><a href="http://search.kmb2b.com/10/operCateSearch.action?oid=3680">金银花</a></em>
										<em><a href="http://search.kmb2b.com/10/operCateSearch.action?oid=9544">葛根</a></em>
										<em><a href="http://search.kmb2b.com/10/operCateSearch.action?oid=2794">党参</a></em>
										<em><a href="http://search.kmb2b.com/10/operCateSearch.action?oid=9587">罗汉果</a></em>
								</p>
                                <b></b>
                               <span class="arrow"  style="display: none"></span>
                            </div>
							
						<div class="sortsub fn-clear" id="div1" style="display: none">
							<div class="sortsub-l">
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9364" target="_blank">
												消脂瘦身
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9442" target="_blank">
														决明子
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9440" target="_blank">
														茯苓
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9438" target="_blank">
														荷叶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9457" target="_blank">
														莱菔子
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9452" target="_blank">
														菊花
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9448" target="_blank">
														山楂
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9365" target="_blank">
												美肌护肤
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9468" target="_blank">
														栀子
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9481" target="_blank">
														山楂
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9484" target="_blank">
														杏仁
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9465" target="_blank">
														枸杞
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9467" target="_blank">
														金银花
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9469" target="_blank">
														桑叶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9482" target="_blank">
														菊花
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9483" target="_blank">
														薏苡仁
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9486" target="_blank">
														淡竹叶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9376" target="_blank">
														决明子
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9496" target="_blank">
														干姜
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9463" target="_blank">
														茯苓
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9461" target="_blank">
														白芷
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9497" target="_blank">
														乌梅
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9495" target="_blank">
														葛根
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9492" target="_blank">
														桃仁
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9498" target="_blank">
														山药
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9494" target="_blank">
														桑葚
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9366" target="_blank">
												健胃消食
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9550" target="_blank">
														木瓜
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9545" target="_blank">
														蒲公英
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9548" target="_blank">
														甘草
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9549" target="_blank">
														白扁豆花
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9562" target="_blank">
														枸杞
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9518" target="_blank">
														肉豆蔻
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9525" target="_blank">
														花椒
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9527" target="_blank">
														砂仁
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9533" target="_blank">
														紫苏
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9539" target="_blank">
														薏苡仁
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9535" target="_blank">
														八角茴香
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9544" target="_blank">
														葛根
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9529" target="_blank">
														肉桂
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9378" target="_blank">
														莱菔子
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9380" target="_blank">
														山药
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9510" target="_blank">
														扁豆
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9513" target="_blank">
														大枣
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9514" target="_blank">
														佛手
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9554" target="_blank">
														芡实
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9555" target="_blank">
														小茴香
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9512" target="_blank">
														茯苓
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9500" target="_blank">
														山楂
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9502" target="_blank">
														鸡内金
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9511" target="_blank">
														陈皮
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9516" target="_blank">
														丁香
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9501" target="_blank">
														麦芽
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9541" target="_blank">
														干姜
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9363" target="_blank">
												三高人群
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9399" target="_blank">
														黄精
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9404" target="_blank">
														苦瓜
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9398" target="_blank">
														葛根
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9405" target="_blank">
														桑叶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9408" target="_blank">
														荷叶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9412" target="_blank">
														槐花
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9413" target="_blank">
														菊花
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9401" target="_blank">
														决明子
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9402" target="_blank">
														山药
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9400" target="_blank">
														乌梅
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9403" target="_blank">
														甘草
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9407" target="_blank">
														山楂
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9406" target="_blank">
														百合
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9591" target="_blank">
												视力疲劳
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9604" target="_blank">
														菊花
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9605" target="_blank">
														覆盆子
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9606" target="_blank">
														决明子
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9602" target="_blank">
														枸杞子
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3667" target="_blank">
												改善睡眠
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9434" target="_blank">
														茯苓
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9431" target="_blank">
														莲子
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9433" target="_blank">
														牡蛎
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9428" target="_blank">
														桑葚
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3684" target="_blank">
														莲子心
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9430" target="_blank">
														枸杞子
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3668" target="_blank">
												润肠通便
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3687" target="_blank">
														郁李仁
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3686" target="_blank">
														桃仁
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3688" target="_blank">
														决明子
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9569" target="_blank">
														百合
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9576" target="_blank">
														橘皮
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9578" target="_blank">
														山楂
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9581" target="_blank">
														桑葚
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9572" target="_blank">
														玉竹
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9566" target="_blank">
														火麻仁
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9623" target="_blank">
												清热下火
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9635" target="_blank">
														芦根
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9634" target="_blank">
														马齿笕
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9632" target="_blank">
														鱼腥草
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9631" target="_blank">
														荷叶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9630" target="_blank">
														金银花
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9633" target="_blank">
														菊花
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9622" target="_blank">
												传统香料
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9627" target="_blank">
														花椒
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9624" target="_blank">
														八角
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9625" target="_blank">
														小茴香
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9628" target="_blank">
														胡椒
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9629" target="_blank">
														桂皮
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9626" target="_blank">
														丁香
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3666" target="_blank">
												清咽润喉
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3682" target="_blank">
														乌梅
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3680" target="_blank">
														金银花
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3679" target="_blank">
														薄荷
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9583" target="_blank">
														菊花
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9585" target="_blank">
														胖大海
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3924" target="_blank">
														蒲公英
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9584" target="_blank">
														桑叶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9587" target="_blank">
														罗汉果
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3681" target="_blank">
														桔梗
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9586" target="_blank">
														橘红
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9590" target="_blank">
														甘草
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9607" target="_blank">
												改善贫血
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9612" target="_blank">
														龙眼肉
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9613" target="_blank">
														陈皮
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9611" target="_blank">
														大枣
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9610" target="_blank">
														桑葚
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9609" target="_blank">
														茯苓
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9608" target="_blank">
														阿胶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9614" target="_blank">
														赤小豆
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9615" target="_blank">
														枸杞
													</a>
												</em>
										</dd>
									</dl>
							</div>

					     <div class="sortsub-r">

                            <div class="sortsub-hotbrand">
							   <!--#include file="/adv/sort_f4.shtml"-->
								<a href="http://www.kmb2b.com/topic/hanfang.html"  target="_blank"><img src="<%=ConfigurationUtil.getString("cmsPath")%>/1461117410818.jpg" alt=""></a>
                             
                            </div>
							<a href="http://www.kmb2b.com/huimuseum.html" class="sortsub-bt"  target="_blank">保健馆</a>
                        
                        </div>

						</div>
                        </li>

					<!--商品分类5楼-->
					                        <li class="sort-list-item  critical-gift" id="id1">
                            <div class="sort-list-title j_adminDiv" data-ids="11045" data-type="1">

							  <i class="ico-sort464 ico-co5" data-categoryId="7962"></i>
											<h3>
												绿色食品
										    </h3>	
                                       <p>

										<em><a href="http://search.kmb2b.com/10/operCateSearch.action?oid=8027">茶籽油</a></em>
										<em><a href="http://search.kmb2b.com/10/operCateSearch.action?oid=7965">有机大米</a></em>
										<em><a href="http://search.kmb2b.com/10/operCateSearch.action?oid=11034">白牦牛肉</a></em>
								</p>
                                <b></b>
                               <span class="arrow"  style="display: none"></span>
                            </div>
							
						<div class="sortsub fn-clear" id="div1" style="display: none">
							<div class="sortsub-l">
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8028" target="_blank">
												保健茶饮
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9102" target="_blank">
														胖大海
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9063" target="_blank">
														桂花茶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9090" target="_blank">
														大麦茶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9092" target="_blank">
														姜茶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9091" target="_blank">
														复方保健茶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9042" target="_blank">
														金银花茶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9062" target="_blank">
														茉莉花茶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9064" target="_blank">
														菊花茶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9065" target="_blank">
														玫瑰花茶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9066" target="_blank">
														洛神花茶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9067" target="_blank">
														薰衣草茶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9068" target="_blank">
														菊皇茶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9082" target="_blank">
														决明子茶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9083" target="_blank">
														葛根茶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9086" target="_blank">
														参芪茶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9089" target="_blank">
														苦芥茶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9093" target="_blank">
														黑枸杞
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9094" target="_blank">
														金线莲
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9095" target="_blank">
														荞麦茶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9088" target="_blank">
														水果茶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9097" target="_blank">
														绿茶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9098" target="_blank">
														红茶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9099" target="_blank">
														荷叶茶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9087" target="_blank">
														花草茶
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8066" target="_blank">
												粮油干货
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8070" target="_blank">
														薏米
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8125" target="_blank">
														莲子
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8127" target="_blank">
														香菇
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8124" target="_blank">
														桂圆
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8067" target="_blank">
														大米
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8103" target="_blank">
														亚麻籽油
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8126" target="_blank">
														木耳/银耳
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8128" target="_blank">
														猴头菇
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8069" target="_blank">
														黑米
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8102" target="_blank">
														挂面
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8123" target="_blank">
														红枣
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8105" target="_blank">
														橄榄油
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8122" target="_blank">
														枸杞
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8068" target="_blank">
														面粉
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8104" target="_blank">
														葵花籽油
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8047" target="_blank">
												休闲零食
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8065" target="_blank">
														话梅
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8049" target="_blank">
														糖果
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8062" target="_blank">
														巴旦木
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8061" target="_blank">
														松子
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8058" target="_blank">
														开心果
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8057" target="_blank">
														夏威夷果
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8056" target="_blank">
														碧根果
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8055" target="_blank">
														核桃
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8048" target="_blank">
														饼干/糕点
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8064" target="_blank">
														山楂
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8063" target="_blank">
														葡萄干
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8059" target="_blank">
														杏仁
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8130" target="_blank">
												地方特产
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8134" target="_blank">
														华东
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8136" target="_blank">
														华中
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8137" target="_blank">
														西南
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8135" target="_blank">
														华南
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8132" target="_blank">
														东北
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8133" target="_blank">
														西北
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8131" target="_blank">
														华北
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9162" target="_blank">
												调味品
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9163" target="_blank">
														牛肉酱
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=7963" target="_blank">
												有机食品
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8405" target="_blank">
														有机牛奶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=7964" target="_blank">
														有机面粉
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8027" target="_blank">
														有机茶籽油
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8026" target="_blank">
														有机葵花籽油
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8022" target="_blank">
														有机玉米
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8023" target="_blank">
														有机杂粮
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=7965" target="_blank">
														有机大米
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8403" target="_blank">
												饮料/水
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8423" target="_blank">
														酵素
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8422" target="_blank">
														果汁/果酱
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8404" target="_blank">
												酒类
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8425" target="_blank">
														玛咖酒
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8424" target="_blank">
														蓝莓酒
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8035" target="_blank">
												母婴食品
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8037" target="_blank">
														成人奶粉
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8038" target="_blank">
														婴幼儿米粉
													</a>
												</em>
										</dd>
									</dl>
							</div>

					     <div class="sortsub-r">

                            <div class="sortsub-hotbrand">
							    <!--#include file="/adv/sort_f5.shtml"-->
								<a href="http://www.kmb2b.com/topic/yingtao.html"  target="_blank"><img src="<%=ConfigurationUtil.getString("cmsPath")%>/1462774327109.jpg" alt=""></a>
                             
                            </div>
							<a href="http://www.kmb2b.com/supermarket.html" class="sortsub-bt"  target="_blank">健康超市</a>
                        
                        </div>

						</div>
                        </li>

					<!--商品分类6楼-->
					                        <li class="sort-list-item  critical-gift" id="id1">
                            <div class="sort-list-title j_adminDiv" data-ids="11046" data-type="1">

							  <i class="ico-sort488 ico-co6" data-categoryId="3056"></i>
											<h3>
												医疗器械
										    </h3>	
                                       <p>

										<em><a href="http://search.kmb2b.com/10/search?kw=%E8%A1%80%E5%8E%8B%E8%AE%A1">血压计</a></em>
										<em><a href="http://search.kmb2b.com/10/search?kw=%E8%BF%90%E5%8A%A8%E5%87%8F%E8%82%A5">运动减肥</a></em>
										<em><a href="http://search.kmb2b.com/10/search?kw=%E8%A1%80%E7%B3%96%E4%BB%AA">血糖仪</a></em>
								</p>
                                <b></b>
                               <span class="arrow"  style="display: none"></span>
                            </div>
							
						<div class="sortsub fn-clear" id="div1" style="display: none">
							<div class="sortsub-l">
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3068" target="_blank">
												保健理疗
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=5742" target="_blank">
														制氧机
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4802" target="_blank">
														电磁治疗仪
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=5543" target="_blank">
														拔罐器
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8522" target="_blank">
														按摩椅/床
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8523" target="_blank">
														刮痧板
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4804" target="_blank">
														雾化器/加湿器
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8502" target="_blank">
														针灸/艾灸
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3077" target="_blank">
														保健枕
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4803" target="_blank">
														排便清肠器
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3076" target="_blank">
														按摩器
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=5544" target="_blank">
														热敷仪
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3084" target="_blank">
														贴膏
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3058" target="_blank">
												检测器材
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3066" target="_blank">
														计步器
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=4782" target="_blank">
														血糖仪
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3061" target="_blank">
														体温计
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3060" target="_blank">
														血压计
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3102" target="_blank">
												护具系列
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8544" target="_blank">
														护腕
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8542" target="_blank">
														护踝
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8547" target="_blank">
														护腰
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=3103" target="_blank">
														口罩
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8546" target="_blank">
														护膝
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8545" target="_blank">
														护肩
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8543" target="_blank">
														护肘
													</a>
												</em>
										</dd>
									</dl>
							</div>

					     <div class="sortsub-r">

                            <div class="sortsub-hotbrand">
							    <!--#include file="/adv/sort_f6.shtml"-->
								<a href="http://www.kmb2b.com/topic/yuanqitang.html"  target="_blank"><img src="<%=ConfigurationUtil.getString("cmsPath")%>/1462774441211.jpg" alt=""></a>
                             
                            </div>
							<a href="http://www.kmb2b.com/huimuseum.html" class="sortsub-bt"  target="_blank">保健馆</a>
                        
                        </div>

						</div>
                        </li>

					<!--商品分类7楼-->
					                        <li class="sort-list-item  critical-gift" id="id1">
                            <div class="sort-list-title j_adminDiv" data-ids="11047" data-type="1">

							  <i class="ico-sort524 ico-co7" data-categoryId="8142"></i>
											<h3>
												健康护理
										    </h3>	
                                       <p>

										<em><a href="http://search.kmb2b.com/10/operCateSearch.action?oid=8150">洗面奶</a></em>
										<em><a href="http://search.kmb2b.com/10/operCateSearch.action?oid=8189">私处洗液</a></em>
										<em><a href="http://search.kmb2b.com/10/operCateSearch.action?oid=8187">卫生巾</a></em>
								</p>
                                <b></b>
                               <span class="arrow"  style="display: none"></span>
                            </div>
							
						<div class="sortsub fn-clear" id="div1" style="display: none">
							<div class="sortsub-l">
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8149" target="_blank">
												美妆护理
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8162" target="_blank">
														喷雾/凝露
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8150" target="_blank">
														洗面奶
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8151" target="_blank">
														化妆水/爽肤水
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8152" target="_blank">
														眼霜/眼精华
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8154" target="_blank">
														面部精华
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8155" target="_blank">
														乳液/面霜
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8153" target="_blank">
														眼膜
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8157" target="_blank">
														润唇膏
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8158" target="_blank">
														面部护理套装
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8159" target="_blank">
														防晒隔离
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8160" target="_blank">
														洁面卸妆
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8161" target="_blank">
														T区/去角质
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8163" target="_blank">
														精油护理
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8156" target="_blank">
														面膜/面贴
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8186" target="_blank">
												女性护理
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8190" target="_blank">
														卫生湿巾
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8188" target="_blank">
														护垫
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8187" target="_blank">
														卫生巾
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8189" target="_blank">
														私处洗液
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8164" target="_blank">
												身体护理
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8166" target="_blank">
														脱毛膏/贴
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8171" target="_blank">
														手部护理
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8168" target="_blank">
														纤体瘦身
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8170" target="_blank">
														胸部护理
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8169" target="_blank">
														颈部护理
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8167" target="_blank">
														止汗香体
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8165" target="_blank">
														身体乳
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8172" target="_blank">
														足部护理
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8173" target="_blank">
												男士护理
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8184" target="_blank">
														男士洁面乳
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8182" target="_blank">
														男士止汗露
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8197" target="_blank">
												母婴护理
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8200" target="_blank">
														驱蚊止痒
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8202" target="_blank">
														尿裤/湿巾
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8204" target="_blank">
														孕妈系列
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8201" target="_blank">
														爽身粉
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8362" target="_blank">
														婴幼家纺
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8199" target="_blank">
														宝宝护肤
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8198" target="_blank">
														宝宝洗浴
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8562" target="_blank">
														清洁用品
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8482" target="_blank">
												美容工具
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8484" target="_blank">
														鼻毛器
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8487" target="_blank">
														嫩肤仪
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8486" target="_blank">
														补水喷雾仪
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8485" target="_blank">
														睫毛器
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8483" target="_blank">
														修眉刀
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8143" target="_blank">
												药妆系列
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8144" target="_blank">
														敏感损伤
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8148" target="_blank">
														缺水干燥
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8147" target="_blank">
														老化皱纹
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8145" target="_blank">
														痘痘痔疮
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8146" target="_blank">
														美白淡斑
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8191" target="_blank">
												口腔护理
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8192" target="_blank">
														牙膏
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8194" target="_blank">
														漱口水
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8193" target="_blank">
														牙刷
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=9002" target="_blank">
														口腔喷剂
													</a>
												</em>
										</dd>
									</dl>
							</div>

					     <div class="sortsub-r">

                            <div class="sortsub-hotbrand">
							    <!--#include file="/adv/sort_f7.shtml"-->
								<a href="http://www.kmb2b.com/topic/mailegou.html"  target="_blank"><img src="<%=ConfigurationUtil.getString("cmsPath")%>/1464241634009.jpg" alt=""></a>
                             
                            </div>
							<a href="http://www.kmb2b.com/makeup.html" class="sortsub-bt"  target="_blank">美妆馆</a>
                        
                        </div>

						</div>
                        </li>

					<!--商品分类8楼-->
					                        <li class="sort-list-item  critical-gift" id="id1">
                            <div class="sort-list-title j_adminDiv" data-ids="11048" data-type="1">

							  <i class="ico-sort525 ico-co8" data-categoryId="8207"></i>
											<h3>
												生活百货
										    </h3>	
                                       <p>

										<em><a href="http://search.kmb2b.com/10/operCateSearch.action?oid=10366">净水器</a></em>
										<em><a href="http://search.kmb2b.com/10/search?kw=壶">养生壶</a></em>
										<em><a href="http://www.kmb2b.com/supply/2581/index.html">活络油</a></em>
										<em><a href="http://search.kmb2b.com/10/search?kw=%E7%BA%B8">纸巾</a></em>
								</p>
                                <b></b>
                               <span class="arrow"  style="display: none"></span>
                            </div>
							
						<div class="sortsub fn-clear" id="div1" style="display: none">
							<div class="sortsub-l">
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8220" target="_blank">
												健康家纺
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8224" target="_blank">
														枕芯
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8230" target="_blank">
														抱枕靠垫
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8226" target="_blank">
														床垫/床褥
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8214" target="_blank">
												洗护用品
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8215" target="_blank">
														沐浴露
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8216" target="_blank">
														洗发水
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8218" target="_blank">
														香皂
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8217" target="_blank">
														护发素
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8208" target="_blank">
												季节用品
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8210" target="_blank">
														花露水
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8209" target="_blank">
														驱蚊
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8212" target="_blank">
														痱子粉
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8211" target="_blank">
														爽身粉
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8213" target="_blank">
														清凉油
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8462" target="_blank">
														防冻霜
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8262" target="_blank">
												健康家电
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8264" target="_blank">
														足浴盆
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8266" target="_blank">
														BB煲
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8263" target="_blank">
														按摩器
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8265" target="_blank">
														暖奶器
													</a>
												</em>
										</dd>
									</dl>
									<dl class="sortsub-item">
										<dt>
											<a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8232" target="_blank">
												洗涤用品
											</a>
										</dt>
										<dd>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8240" target="_blank">
														洗衣液
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8233" target="_blank">
														消毒液
													</a>
												</em>
												<em>
												   <a href="<%=ConfigurationUtil.getString("searchPath")%>/operCateSearch.action?oid=8241" target="_blank">
														肥皂
													</a>
												</em>
										</dd>
									</dl>
							</div>

					     <div class="sortsub-r">

                            <div class="sortsub-hotbrand">
							   <!--#include file="/adv/sort_f8.shtml"-->
								<a href="http://www.kmb2b.com/tuan2.html"  target="_blank"><img src="<%=ConfigurationUtil.getString("cmsPath")%>/1461117747843.jpg" alt=""></a>
                             
                            </div>
							<a href="http://www.kmb2b.com/supermarket.html" class="sortsub-bt"  target="_blank">健康超市</a>
                        
                        </div>

						</div>
                        </li>

						
                    </ul>
                </div>
			   <!--导航标题窗口-->
                <ul id="navitems" class="navitems fn-clear j_adminDiv" data-ids="11040" data-type="1">
    <li title=""><a          href="http://www.kmb2b.com" target="_blank">首页</a>
</li>
    <li title=""><a          href="http://www.kmb2b.com/zibu.html" target="_blank">滋补馆</a>
</li>
    <li title="药品、医疗器械"><a          href="http://www.kmb2b.com/dayaofang.html" target="_blank">大药房</a>
</li>
    <li title="营养保健品"><a          href="http://www.kmb2b.com/huimuseum.html" target="_blank">保健馆</a>
</li>
 <li  class="nav-news" title="进口"><a href="http://www.kmb2b.com/global.html" target="_blank">全球购</a>
  <b></b>
</li>
  
 <li  class="nav-news" title=""><a href="http://www.kmb2b.com/makeup.html" target="_blank">药妆馆</a>
  <b></b>
</li> 
    <li title="食品、生活用品"><a          href="http://www.kmb2b.com/supermarket.html" target="_blank">健康超市</a>
</li>
    <li title=""><a          href="http://info.kmb2b.com" target="_blank">健康生活</a>
</li>
</ul>


                <div class="fn-right minicart">
                <a class="minicart-go fn-right"  href="javascript:void(0);"  id="gotoSettlement">去结算</a>
                <div id="shopcart" class="minicart-number fn-left">
                    <a href="<%=ConfigurationUtil.getString("portalPath")%>/cart/listShopCar.action">
                        <i class="minicart-icon"></i>
                        <span>购物车<span class="number">0</span>件</span>
                        <i class="minicart-down"></i>
                    </a>
                    <div class="minicart-list" style="display:none;" id="nogoods">
                        <div class="nogoods fn-hide">
                            <b></b>
                            <p class="fn-block">购物车中还没有商品，赶紧选购吧！<br>您还没有 <a href="javascript:void(0);">登录</a></p>
                        </div>
                    </div>
                </div>
    
                </div>
            </div>
    	</div>
        <!--i-nav end-->
    </div>




<input type="hidden" id="cssAndJsPath" data_id="<%=ConfigurationUtil.getString("cssAndJsPath")%>"/>

<input type="hidden" id="cmsPath" data_id="<%=ConfigurationUtil.getString("cmsPath")%>"/>

<input type="hidden" id="picPath" data_id="<%=ConfigurationUtil.getString("picPath")%>"/>

<input type="hidden" id="detailPath" data_id="<%=ConfigurationUtil.getString("detailPath")%>"/>

<input type="hidden" id="advPath" data_id="<%=ConfigurationUtil.getString("advPath")%>"/>

<input type="hidden" id="portalPath" data_id="<%=ConfigurationUtil.getString("portalPath")%>"/> 

<input type="hidden" id="staticPath" data_id="<%=ConfigurationUtil.getString("staticPath")%>"/> 

<input type="hidden" id="facade_path" data_id="<%=ConfigurationUtil.getString("searchPath")%>"/>
  <input type="hidden" id="gysPortalPath" data_id="http://gys.kmb2b.com">
  <input type="hidden" id="logSysPath" data_id="http://kma.kmb2b.com/weblog/b2b">
