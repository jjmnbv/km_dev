<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<s:form id="sform" name="sform" action="queryLotteryList.action" method="post" theme="simple">
	<s:hidden name="page" id="page" />
	<s:token></s:token>
  <div class="l-right user-m">
          	<div class="o-mt">
            	<h2>我的抽奖</h2>
            	
				<div class="user-block-tip">
                            	<s:select name="searchKeyword.createDate" id="searchKeyword.createDate" list="createDateOptionsMap" 
					cssClass="sele j_queryByTime" />	
				　
			    <input class="Inquiry-text j_dealKeyword" id="luckName"  value="<s:property value="luckName"/>" name="luckName"/>		  
			    <input class="Inquiry-text j_dealKeyword" id="prizeNumbers" value="<s:property value="prizeNumbers"/>" name="prizeNumbers"/>
			  
					<s:select name="searchKeyword.status" id="searchKeyword.status" list="#{'':'全部状态',0:'未中奖',1:'已中奖',2:'进行中',3:'已过期'}"
					cssClass="sele j_queryByStatus" /> 
                    <input type="button"  id="search" class="btn-b btn-b-success" value="查 询" name="">
                    </div>
            </div>
            <div class="ui-table">
                    	<table class="ui-table user-table ">
                            <thead>
                                <tr>
                                    <th class="td-s2">抽奖活动</th>
                                    <th class="td-s1">抽奖号</th>
                                    <th class="td-s7">参与时间</th>
                                    <th>奖品</th>
                                    <th class="td-s1">领奖状态</th>
                                </tr>
                            </thead>
                            <tbody>
                              <s:set var="AWARDS_ID"  value="0"/>
                            <s:iterator value="#request.pagintion.recordList" var="lotteryVar" status="entryStatus">
                                <tr>
                                	<td> ${lotteryVar.titel}</td>
                                    <td>${lotteryVar.prizeNumbers}</td>
                                    <td> 
                                      <s:date name="#lotteryVar.inTime" format="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                <td>   
                                 <s:if test="#lotteryVar.activeType==0">
                                    <s:if test="#lotteryVar.status==0">
                                                                          未中奖
                                   </s:if>
                                   <s:else>
                                 <strong >【  ${lotteryVar.awardsName} 】</strong><br/>
                                  <s:iterator value="#lotteryVar.prizeList" var="prize">
                                    <s:set name="statusNumber" value="0"/>  <!-- 奖品状态 -->
	                                     <s:iterator value="#lotteryVar.winPrizeList"  var="winPrize">
		                                     <s:if test="#winPrize.prizeIdWin==#prize.prizeId">
			                                     <s:if test="#winPrize.status==1">
			                                     <s:set name="statusNumber" value="1"/>
			                                     </s:if>
			                                     <s:if test="#winPrize.status==2">
			                                     <s:set name="statusNumber" value="2"/>
			                                     </s:if>
			                                     <s:if test="#winPrize.status==3">
			                                     <s:set name="statusNumber" value="3"/>
			                                     </s:if>
		                                     </s:if>
	                                     </s:iterator>
	                             <s:if test="#statusNumber==2&&#prize.type!=4">
	                                   <s:if test="#prize.type==1">
	                                   <a class="fn-blue" href="/member/queryCouponList.action?couponStatus=3">
	                                   </s:if>  
	                                  <s:if test="#prize.type==2">
	                                   <a class="fn-blue" href="/member/queryOrderList.action?searchKeyword.keyword=<s:property value="#prize.productId"/>">
	                                  </s:if>  
	                                   <s:if test="#prize.type==3">
	                                   <a class="fn-blue" href="/member/queryPointsRecordList.action">
	                                  </s:if>  
	                              </s:if>
	                              <s:if test="#prize.awardsId==#lotteryVar.awardsId"><!-- 对应奖项的奖品 -->
	                                  <s:property value="#prize.name"/>*<s:property value="#prize.prizeCount"/>
	                              </s:if>
	                              <s:if test="#statusNumber==2&&#prize.type!=4">
	                                  </a>
	                              </s:if>
	                                <s:if test="#prize.awardsId==#lotteryVar.awardsId"><!-- 对应奖项的奖品 -->
	                                  <s:if test="#statusNumber==0">
	                                    	<strong class="fn-red">未领取<!-- 未颁发 --></strong>
	                                  </s:if>  
	                                   <s:if test="#statusNumber==1">
	                                    	<strong class="fn-red">未领取</strong>
	                                  </s:if> 
	                                   <s:if test="#statusNumber==2">
	                                    	<strong class="fn-green">  已领取  </strong>
	                                  </s:if>  
	                                  <s:if test="#statusNumber==3">
	                                    	<strong class="fn-red">  已过期  </strong>
	                                  </s:if>  
	                                       <br> 
	                                   </s:if>
                                
                                     </s:iterator>
                                   </s:else>
                                  </s:if>
                                  <s:else>
                                    <s:if test="#lotteryVar.status!=1&&#lotteryVar.luckStatus==7">
                                                                          未中奖
                                    </s:if>
                                    <s:elseif test="#lotteryVar.status!=1&&#lotteryVar.luckStatus!=7">
                                                                         抽奖进行中
                                    </s:elseif>
                                   <s:elseif test="#lotteryVar.status==1">
                                       <strong >【  ${lotteryVar.awardsName} 】</strong><br/>
                                  <s:iterator value="#lotteryVar.prizeList" var="prize">
                                    <s:set name="statusNumber" value="0"/>  <!-- 奖品状态 -->
	                                     <s:iterator value="#lotteryVar.winPrizeList"  var="winPrize">
		                                     <s:if test="#winPrize.prizeIdWin==#prize.prizeId">
			                                     <s:if test="#winPrize.status==1">
			                                     <s:set name="statusNumber" value="1"/>
			                                     </s:if>
			                                     <s:if test="#winPrize.status==2">
			                                     <s:set name="statusNumber" value="2"/>
			                                     </s:if>
			                                      <s:if test="#winPrize.status==3">
			                                     <s:set name="statusNumber" value="3"/>
			                                     </s:if>
		                                     </s:if>
	                                     </s:iterator>
	                             <s:if test="#statusNumber==2&&#prize.type!=4">
	                                   <s:if test="#prize.type==1">
	                                   <a class="fn-blue" href="/member/queryCouponList.action?couponStatus=3">
	                                   </s:if>  
	                                  <s:if test="#prize.type==2">
	                                   <a class="fn-blue" href="/member/queryOrderList.action?searchKeyword.keyword=<s:property value="#prize.productId"/>">
	                                  </s:if>  
	                                   <s:if test="#prize.type==3">
	                                   <a class="fn-blue" href="/member/queryPointsRecordList.action">
	                                  </s:if>  
	                              </s:if>
	                               
	                              <s:if test="#prize.awardsId==#lotteryVar.awardsId"> <!-- 对应奖项的奖品 -->
	                                  <s:property value="#prize.name"/>*<s:property value="#prize.prizeCount"/>
	                              </s:if>
	                              <s:if test="#statusNumber==2&&#prize.type!=4">
	                                  </a>
	                              </s:if>
	                               <s:if test="#prize.awardsId==#lotteryVar.awardsId">
	                                  <s:if test="#statusNumber==0">
	                                    	<strong class="fn-red">未领取<!-- 未颁发 --></strong>
	                                  </s:if>  
	                                   <s:if test="#statusNumber==1">
	                                    	<strong class="fn-red">未领取</strong>
	                                  </s:if> 
	                                   <s:if test="#statusNumber==2">
	                                    	<strong class="fn-green">已领取  </strong>
	                                  </s:if> 
	                                   <s:if test="#statusNumber==3">
	                                    	<strong class="fn-red">已过期  </strong>
	                                  </s:if>
	                                     <br> 
	                                  </s:if>
                                   
                                     </s:iterator>
                                    
                                    </s:elseif>
                                  </s:else>
                                </td>
                                    <td>   
                                   <s:set  name="number" value="0"/><!-- 颁奖数 -->
                                   <s:set  name="numbers" value="0"/><!-- 领奖数 -->
                                   <s:set  name="numberss" value="0"/><!-- 未颁奖数 -->
                                   <s:set  name="numbersss" value="0"/><!-- 过期数 -->
                                 
                                   
                            <s:if test="#lotteryVar.activeType==1"><!--后开 -->
                     
                                    <s:if test="#lotteryVar.status!=1&&#lotteryVar.luckStatus==7">
                                                                          未中奖
                                    </s:if>
                                    <s:elseif test="#lotteryVar.status!=1&&#lotteryVar.luckStatus!=7">
                                                                       抽奖进行中
                                    </s:elseif>
                                   <s:elseif test="#lotteryVar.status==1">
                                    <s:iterator value="#lotteryVar.winPrizeList" >
	                                      <s:if test="status==1">
	                                       <s:set name="number" value="#number+1" />                       
	                                      </s:if>
	                                      <s:if test="status==2">
	                                      <s:set name="numbers" value="#numbers+1" />    
	                                      </s:if>
	                                       <s:if test="status==0">
	                                      <s:set name="numberss" value="#numberss+1" />    
	                                      </s:if>
	                                       <s:if test="status==3">
	                                      <s:set name="numbersss" value="#numbersss+1" />    
	                                      </s:if>
                                     </s:iterator>
                                     
                                       <s:if test="#numberss>0">  
                                      <input type="button"  class="btn-b"      value="领取">
                                       </s:if>                               
                                      <s:elseif test="#number>0">   
                                      <input type="button" class="btn-b btn-b-success"  data-userid="${lotteryVar.loginId}"   data-prizeNumbers="${lotteryVar.prizeNumbers}"  data-extractPrizeId="${lotteryVar.extractPrizeId}" data-awardsId="${lotteryVar.awardsId}"    value="领取">
                                      </s:elseif>
                                     <s:elseif  test="#numbers==#lotteryVar.winPrizeList.size">     
                                                                           全部已领取
                                      </s:elseif>
                                      <s:elseif  test="${numbersss}==#lotteryVar.winPrizeList.size">     
                                                                                已过期
                                     </s:elseif>
                                     <s:elseif  test="${numbers+numbersss}==#lotteryVar.winPrizeList.size">     
                                                                                已领取
                                     </s:elseif>
                                     
                                     </s:elseif>
                            </s:if>
                            <s:else>
                                    <s:iterator value="#lotteryVar.winPrizeList" >
	                                      <s:if test="status==1">
	                                       <s:set name="number" value="#number+1" />                       
	                                      </s:if>
	                                      <s:if test="status==2">
	                                      <s:set name="numbers" value="#numbers+1" />    
	                                      </s:if>
	                                       <s:if test="status==0">
	                                      <s:set name="numberss" value="#numberss+1" />    
	                                      </s:if>
	                                       <s:if test="status==3">
		                                    <s:set name="numbersss" value="#numbersss+1" />    
		                                  </s:if>
                                     </s:iterator>
                                       <s:if test="#lotteryVar.winPrizeList.size==0">
                                                                                  未中奖
                                        </s:if>
                                        <s:else>
                                      
                                          <s:if test="#numberss>0"> 
                                            <input type="button"  class="btn-b"      value="领取">
                                         </s:if>
                                         <s:elseif test="#number>0">   
                                           <input type="button" class="btn-b btn-b-success"  data-userid="${lotteryVar.loginId}" data-prizeNumbers="${lotteryVar.prizeNumbers}"  data-extractPrizeId="${lotteryVar.extractPrizeId}" data-awardsId="${lotteryVar.awardsId}"    value="领取">
                                        </s:elseif>
                                        <s:elseif  test="#numbers==#lotteryVar.winPrizeList.size">     
                                                                               全部已领取
                                        </s:elseif>
                                         <s:elseif  test="${numbersss}==#lotteryVar.winPrizeList.size">     
                                          	已过期
                                        </s:elseif>
                                         <s:elseif  test="${numbers+numbersss}==#lotteryVar.winPrizeList.size">     
                                          	已领取
                                        </s:elseif>
                                        
                                        </s:else>          
                                            
                            </s:else>                             
                                       </td>
                                   
                                </tr>
                                </s:iterator>
                            </tbody>
                        </table>
                    </div>
                    <div class="fn-tr fn-t10">
					<div class="ui-page">
						<!-- 分页组件 -->
            			<tiles:insertDefinition name="pagination"/>
					</div>
        </div>	
        </div>
        
</s:form>
</div>
