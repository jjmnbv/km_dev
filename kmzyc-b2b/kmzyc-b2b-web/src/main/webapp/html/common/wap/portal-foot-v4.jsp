<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.kmzyc.zkconfig.ConfigurationUtil" %>
<!--底部-->
    <footer class="footer" data="test">
        <div class="foot-content">
            <h2 class="fn-t5"><a href="tel:4006600518">客服热线：400-6600-518<i class="crow-arrow icon-phone"></i></a></h2>
            <!--搜索-->
            <div class="search fn-p10">
                
                    <div class="search-con" id="main-search2" data-surl="<%=ConfigurationUtil.getString("searchPath_WAP")%>/app/suggest?c=b2b&q=">
                    <form action="<%=ConfigurationUtil.getString("searchPath_WAP")%>/wap/kwsearch" method="get" onSubmit="return check2()">
                        <div class="textinput input-block">
                            <span class="clear icon-cross"></span>
                            <input class="sub" type="submit" value="搜索">
                            <input autocomplete="off" class="text" name="kw" type="search" value="">
                            <div id="su_result2" class="search-list result">
                            </div>
                        </div>

                    </form>
                        <script>
                 function check2(){
                    document.getElementById('su_result2').style.display = "none";
                     return true;
                
                }
              
              </script>
                </div>
            </div>
            <div class="user-panel">
                <p class="member" id="loginbar">
                 
					
                </p>


                <a class="fb-top" href="javascript:scroll(0,0)"><i class="icon-arrow-up"></i>top</a>
            </div>
        </div>
        <div class="foot-copy">
            <p class="version">
                 <a href="http://www.kmb2b.com/index.html?device=mobile">电脑版</a>
                <a href="http://m.kmb2b.com" class="fn-green">触屏版</a>
                <a href="http://m.kmb2b.com/wap/thepapp.html">下载app</a>
            </p>
            <p class="copyright">
                 互联网B2B药品交易服务资格证 粤C20140003</br>
                  ©康美中药材数据信息服务有限公司 版权所有
             
            </p>
        </div>
    </footer>
    <!--底部 end-->
<script type="text/javascript">
function _load(fun){
    if (window.attachEvent) {
        window.attachEvent('onload', fun);
    } else {
        window.addEventListener('load', fun, false);
    }
}
function async_load(src) {
    var live800_script = document.createElement('script');
    live800_script.type = 'text/javascript';
    live800_script.async = true;
    live800_script.src = src;
    document.body.appendChild(live800_script);
}


_load(function () {
  	//CNZZ统计代码
    async_load("http://s22.cnzz.com/z_stat.php?id=1000398254&web_id=1000398254");
  });
</script>
  
  <input type="hidden" id="cssAndJsPath" data_id="<%=ConfigurationUtil.getString("cssAndJsPath_WAP")%>"/>
<input type="hidden" id="cmsPath" data_id="<%=ConfigurationUtil.getString("cmsPath_WAP")%>"/>
<input type="hidden" id="picPath" data_id="<%=ConfigurationUtil.getString("picPath_WAP")%>"/>
<input type="hidden" id="detailPath" data_id="<%=ConfigurationUtil.getString("detailPath_WAP")%>"/>
<input type="hidden" id="advPath" data_id="<%=ConfigurationUtil.getString("advPath_WAP")%>"/>
<input type="hidden" id="portalPath" data_id="<%=ConfigurationUtil.getString("portalPath_WAP")%>"/> 
<input type="hidden" id="staticPath" data_id="<%=ConfigurationUtil.getString("staticPath_WAP")%>"/> 
<input type="hidden" id="facade_path" data_id="<%=ConfigurationUtil.getString("searchPath_WAP")%>"/> 
  <input type="hidden" id="searchPath" data_id="<%=ConfigurationUtil.getString("searchPath_WAP")%>"/> 
<input type="hidden" id="gysPortalPath" data_id="http://10.1.0.214:7088">
  <input type="hidden" id="user_img_path" data-bb="1" data_id=""/> 
