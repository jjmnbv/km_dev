<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

    <!--内容区域-->
  <div class="container">
            <!--收货人信息-->
            	<s:iterator value="#request.addressList" var="addr">
	               <section class="saleinfo">
        			<div class="order-list del">
            			<ul class="text-list">
	                    	<%-- <s:if test="${addr.status} == 0">
	                      	  <span class="pitchon icon-check"></span>
	                      	 </s:if> --%>
	                          <li>姓名：${addr.name}</li>
	                          <li>电话：${addr.cellphone}</li>
	                          <li>地址：${addr.province} ${addr.city} ${addr.area}</li>
	                        </ul>
	                        <!-- 修改 -->
	                       		<div class="btnabs-tr0">
	                            <a href="javascript:void(0)" class="icon icon-uniE608 j_edit" data-addressId="${addr.addressId}"> </a>
	                            <a href="javascript:void(0)" class="icon icon-uniE602 j_deleteConsignee" data-addressId="${addr.addressId}"></a>
	                            </div>
	                    </div>
	                </section>
	             </s:iterator>
                <p class="fn-t-c fn-t5"><a class="btn btn-success j_addConsignee">添加新地址</a></p>
    </div>
    

