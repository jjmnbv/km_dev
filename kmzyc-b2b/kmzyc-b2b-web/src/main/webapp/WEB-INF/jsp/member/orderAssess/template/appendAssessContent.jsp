<!-- 此页已废弃，整合入orderAssess -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<style type="text/css"> 
*{margin:0;padding:0;list-style-type:none;}
body{color:#666;font:12px/1.5 Arial;}
</style>
 
<script type="text/javascript">
    window.onload = function () {
        var aMsg = [
				"很不满意|差得太离谱，与卖家描述的严重不符，非常不满",
				"不满意|部分有破损，与卖家描述的不符，不满意",
				"一般|质量一般，没有卖家描述的那么好",
				"满意|质量不错，与卖家描述的基本一致，还是挺满意的",
				"非常满意|质量非常好，与卖家描述的完全一致，非常满意"
				];
        var i, iScore,  point;
        var oStar = $(".star"); // document.getElementById("star");
        $(oStar).each(function () {
            var aLi = $(this).find("li"); // oStar.getElementsByTagName("li");
            //此处传值
            fnPoint(aLi, $(aLi).parents(".star").find("input[name='point']").val());
            var oUl = $(this).find("ul"); // oStar.getElementsByTagName("ul")[0];
            var oSpan = $(this).find("span"); // oStar.getElementsByTagName("span")[1];
            var oP = $(this).find("p"); // oStar.getElementsByTagName("p")[0];
            i = iScore = point = 0;
            $(aLi).each(function () {
                i++;
                this.index = i;

                //鼠标移过显示分数
            //    $(this).mouseover(function () {
              //      fnPoint(aLi, this.index);
                    //浮动层显示
               //     $(oP).css("display", "block"); // oP.style.display = "block";
                    //计算浮动层位置
                //    $(oP).css("left", oUl.offsetLeft + this.index * this.offsetWidth - 104)// oP.style.left = oUl.offsetLeft + this.index * this.offsetWidth - 104 + "px";
                    //匹配浮动层文字内容
                //    $(oP).html("<em><b>" + this.index + "</b> 分 " + aMsg[this.index - 1].match(/(.+)\|/)[1] + "</em>" + aMsg[this.index - 1].match(/\|(.+)/)[1]);
                //});

                //鼠标离开后恢复上次评分
             //   $(this).mouseout(function () {
              //      fnPoint(aLi);
                    //关闭浮动层
               //     $(oP).css("display", "none"); // oP.style.display = "none"
               // });

                //点击后进行评分处理
              //  $(this).click(function () {
             ///       $(this).parents(".star").find("input[name='point']").val(this.index);
                //    $(oP).hide(); // oP.style.display = "none";
              //      $(oSpan).html("<strong>" + (this.index) + " 分</strong> (" + aMsg[this.index - 1].match(/\|(.+)/)[1] + ")");
               //     $(this).parents(".star").find("input[name='score']").val(this.index);// document.getElementById("score").value = this.index;
               //     //alert($(this).parents(".star").find("input[name='score']").val());
            //    });

            });
        });

        //评分处理
        function fnPoint(aLi1, iArg) {
            //分数赋值
            iScore = iArg || $(aLi1).parents(".star").find("input[name='point']").val();
			
            if (aLi1) {
                for (i = 0; i < aLi1.length; i++)
                    aLi1[i].className = i < iScore ? "on" : "";
            }
        }

    };
    //校验追加的内容
    function 	checkAppendAssess()
    {
    	var flag=true;
    	var appendAssess = $(".appendContent");
    	for (var a=0;a<appendAssess.length;a++)
    		{
    	// 	alert($.trim(appendAssess[a].value).length);
    		if ($.trim(appendAssess[a].value).length==0)
    			{	
    			$("#appendContent"+a).show();
    			flag=false;
    			}
    		else
    			{
    			$("#appendContent"+a).hide();
    			}
    		}
    	return flag;
    }
	
	//提交评分
	function submitAssess(){
		if(checkAppendAssess())
			{
			
			
		//处理产品的评分
	//这是评价的ID
	var x = $(".appendPraiseId"); 
	var content = $(".appendContent");

	var prodContent="";
	var prodAppraiseId="";
	 for (var a =0 ;a<x.length;a++)
	 {
		//得到评价的ID
		 prodAppraiseId =prodAppraiseId+x[a].value+","; 
	 }
	  for (var b =0 ;b<content.length;b++)
	 {
		//得到评价的追平
		 prodContent =prodContent+content[b].value+","; 
	 }
	 
	$("#prodAppriseId").val($.trim(prodAppraiseId));
	 $("#prodContent").val($.trim(prodContent));
	  document.getElementById("AppendAssessContentFrom").submit();
	 alert("提交追加评价成功！");
		}
	}
	
	
</script>


<s:form id="AppendAssessContentFrom" name="sform" action="saveAppendAssessContent.action" method="post" theme="simple">
<!--隐藏数据域-->
<input name="prodAppriseId" type="hidden" id="prodAppriseId" value="" />
<input name="prodappraiseContent" type="hidden" id="prodContent" value="" />
<input name="orderCodes" type="hidden" id="orderCod" value="<s:property  value="orderMain.orderCode" />" />


	<s:hidden name="page" id="page" />
	<s:token></s:token>
<div class="l-right user-m">
          	<div class="o-mt">
            	<h2>商品评分</h2>
                <div class="OrderInfo" >
                	<ul class="Order-Number">
                    	<li>订单编号：  <s:property  value="orderMain.orderCode" /> </li>
                        <li>状态: 
                        <s:if test="orderMain.orderStatus == -1">已取消</s:if>
							<s:if test="orderMain.orderStatus == 1">未付款</s:if>
							<s:if test="orderMain.orderStatus == 2 || 
										orderMain.orderStatus == 3 ||
										orderMain.orderStatus == 4|| 
										orderMain.orderStatus == 20|| 
										orderMain.orderStatus == 21|| 
										orderMain.orderStatus == 22">已付款</s:if>
							<s:if test="orderMain.orderStatus == 5">已发货</s:if>
							<s:if test="orderMain.orderStatus == 6">已完成</s:if>
							<s:if test="orderMain.orderStatus == 7">已评价</s:if>
                        </li>
                 <p id="statusInfo">
					 <s:if test="expressPath.data!=null">
					     <strong>最新进度:</strong> <span>[<s:date name="expressPath.data[0].ftime" format="yyyy-MM-dd HH:mm:ss"/>]
					     </span>
						 <s:property value="expressPath.data[0].context" /> 
					  </s:if>
					  <s:elseif test="listorder!=null">
				          <strong>最新进度:</strong> <span>[<s:date name="listorder[0].nowOperateDate" format="yyyy-MM-dd HH:mm:ss"/>]
						</span>
						<s:property value="listorder[0].operateInfo" />	  
					  </s:elseif>
					  <s:else>
					                                暂无进度
					  </s:else>
					<a href="initOrderLogistics.action?orderTrailInfo.expressComName=${orderMain.logisticsName }&orderTrailInfo.expressNo=${orderMain.logisticsOrderNo }&orderMainId=${orderMainId}&orderTrailInfo.orderNo=${orderMain.orderCode}&orderTrailInfo.orderStatus=${orderMain.orderStatus}">查看所有进度跟踪</a>
				</p>
                    </ul>
                </div>
            </div>
            <div class="user-m fn-t10">
                <div class="mc">
                	<div class="m-w w-noborder fn-clear">
                        <div class="wc" style=" height:131px;">
               <div id="pingjia">
	
	<div class="star" title="包装">
	 <font style="float: left; " >物流发货速度</font>
		<ul>
			<li><a href="javascript:;">1</a></li>
			<li><a href="javascript:;">2</a></li>
			<li><a href="javascript:;">3</a></li>
			<li><a href="javascript:;">4</a></li>
			<li><a href="javascript:;">5</a></li>
		</ul>
		<span></span>
		<p></p>
    <input type="hidden" name="point"  value="<s:property value='orderAssessPoint.Assess_Type_one' />"  class="orderPoint" />
     
    <input type="hidden" name="score"  value="<s:property value='orderAssessPoint.Assess_Type_one' />"   />
	</div><!--star end-->

	<div class="star" title="包装">
	 <font style=" float: left; " >包装是否完好</font>
		<ul>
			<li><a href="javascript:;">1</a></li>
			<li><a href="javascript:;">2</a></li>
			<li><a href="javascript:;">3</a></li>
			<li><a href="javascript:;">4</a></li>
			<li><a href="javascript:;">5</a></li>
		</ul>
		<span></span>
		<p></p>
    <input type="hidden" name="point"  value="<s:property value='orderAssessPoint.Assess_Type_two' />"   class="orderPoint" />
    <input type="hidden" name="score" value="<s:property value='orderAssessPoint.Assess_Type_two' />"   />
	</div><!--star end-->
	
	<div class="star" title="包装">
	 <font style=" float: left; " >卖家发货速度</font>
		<ul>
			<li><a href="javascript:;">1</a></li>
			<li><a href="javascript:;">2</a></li>
			<li><a href="javascript:;">3</a></li>
			<li><a href="javascript:;">4</a></li>
			<li><a href="javascript:;">5</a></li>
		</ul>
		<span></span>
		<p></p>
    <input type="hidden" name="point"   class="orderPoint"  value="<s:property value='orderAssessPoint.Assess_Type_three' />"  />
    <input type="hidden" name="score"   value="<s:property value='orderAssessPoint.Assess_Type_three' />"  />
	</div><!--star end-->
	
	<div class="star" title="包装">
	 <font style=" float: left; " >售前售后服务</font>
		<ul>
			<li><a href="javascript:;">1</a></li>
			<li><a href="javascript:;">2</a></li>
			<li><a href="javascript:;">3</a></li>
			<li><a href="javascript:;">4</a></li>
			<li><a href="javascript:;">5</a></li>
		</ul>
		<span></span>
		<p></p>
    <input type="hidden" name="point"    class="orderPoint" value="<s:property value='orderAssessPoint.Assess_Type_four' />"   />
    <input type="hidden" name="score"  value="<s:property value='orderAssessPoint.Assess_Type_four' />"  />
	</div><!--star end-->
</div>
                        
        
	</div>
    
                        </div>
                        
                    </div>
                
                    <!--明细循环-->
                  <s:iterator value="orderItemList" var="orderItem" status='st' >  
          			
                    <div class="Evaluate">
                    	<div class="item fn-clear fn-t10">
                	<div class="user">
                    	<div class="u-icon">
                        <a href="javascript:void(0)">  <img src="${productImgServerUrl}${orderItem.defaultProductImage.imgPath}" title="${orderItem.commodityName}" onerror="this.onerror='';this.src='<%=ConfigurationUtil.getString("CSS_JS_PATH")%>images/default__logo_err60_60.jpg'"> </a>
                      
                        </div>
						<div class="p-name"><a href="javascript:void(0)">${orderItem.commodityName}</a></div>
                        <input name="appraiseId" value="${orderItem.prodappraiseId}"  class="appendPraiseId"  
                        type="hidden" ></input>
                    </div>
                    <div class="i-item">
                    	<div class="arrowLeft"></div>

                        <div class="i-item-c">
                          <div class="evaluate-m">
                                	<p><label>评分：</label><span class="star">
                                                              	<div class="o-topic" style=" margin-top: -52px; margin-left: 90px; ">
                        	<span >
                <div class="star" title="包装">
		<ul style=" margin-left: -55px; ">
			<li><a href="javascript:;">1</a></li>
			<li><a href="javascript:;">2</a></li>
			<li><a href="javascript:;">3</a></li>
			<li><a href="javascript:;">4</a></li>
			<li><a href="javascript:;">5</a></li>
		</ul>
		<span></span>
		<p></p>
		<input type="hidden" name="point" id="productPoint"   class="product_point"  value="${orderItem.credits}" />
   	 <input type="hidden" name="score" value="${orderItem.credits}" />
    
	</div>
                                </span>
                            </span>
                        </div>
                                            </span>
                                        </span></p>
                                    <p><label>评价：</label>${orderItem.commodityDescription}</p>
                                    <p><label>回复：</label><span class="bgyellow fn-w640">亲！您好！康美药业对于产品市场价格是有严格控制的，康美官方旗舰店是康美药业股份有限公司直接与天猫合作开店，保证品质，亲可以放心！我们也会经常做一些老会员的优惠活动，请老朋友们多多关注哦! 期待您的再次光临！祝你生活愉快、万事如意。 (*^__^*)</span></p>
                                    <p><label>追加：</label>
                                    <span class="i-item-inner i-item-inner660">
                                        <textarea name="" class="appendContent" cols="" rows="" ></textarea>
                                        <span class=" fn-right">最多300字</span>
                                         <div  id="appendContent<s:property value='#st.index' />"  style="padding-left:623px;display:none"  class="red-text fn-red">
                                				请输入追加内容
                         				  </div>
                                    </span>
									</p>
                                </div>
                        </div>
                    </div>
                </div>
                </div>
                
              
                </s:iterator>
                <!--明细订单循环-->
                    </div>
                    <div class="button"><div><a class="btn-submit"  onclick="submitAssess()" href="javascript:void(0)"><span>发表</span></a><a class="btn-cancel" href="javascript:void(0)"><span>取消</span></a></div></div>
                </div>
            </div>
          </div>
</s:form>

</div>

<div>
 	<input type="hidden" id="orderMainId" value="<s:property value='orderMainId' />" />
 	<input type="hidden" id="orderStatusId" value="<s:property value='orderTrailInfo.orderStatus' />" />
</div>

<script type="text/javascript">
	//定义未发货的状态
	var orderStatus = [-1,1,2,3]; 
	var orderStatusLength = orderStatus.length;
$(document).ready(function() {
	
	var orderStatusId = $("#orderStatusId").val();//订单状态
	var orderMainId = $("#orderMainId").val();//订单ID
	
	//获得订单状态,如果状态为已发货，则发送请求查询快递信息
	for(var i = 0;i<orderStatusLength;i++){
		if(parseInt(orderStatusId)<orderStatus[i]){
			return;
		}
	}
	
	$.getJSON("queryExpressOrderInfo.action?orderMainId="+ orderMainId,
			function(json){
        if(json.code==200){//其他状态,快递信息不用处理,直接还是显示原来的系统流水日志
        	expressPath = json.returnObject.data;
        	expressPathLength = expressPath.length;//data的长度
        	//对数据进行遍历
        	for(var i = 0;i<expressPathLength;i++){
        		if(i==0){
        			//向p标签后面添加文字
                	var pInnerHtml = document.getElementById("statusInfo");
                	pInnerHtml.innerHTML = "<strong>最新进度:</strong> <span>["+expressPath[i]["time"]+"]</span>"
                	+expressPath[i]["context"]
                	+"<a href='initOrderLogistics.action?orderTrailInfo.expressComName=${orderMain.logisticsName }&orderTrailInfo.expressNo=${orderMain.logisticsOrderNo }&orderMainId=${orderMainId}&orderTrailInfo.orderNo=${orderMain.orderCode}&orderTrailInfo.orderStatus=${orderMain.orderStatus}'>查看所有进度跟踪</a>";
        		}
        	};
        }
    });
});
</script>
