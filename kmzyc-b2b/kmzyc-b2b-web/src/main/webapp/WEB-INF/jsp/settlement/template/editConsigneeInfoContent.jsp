<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
	    <!--内容区域-->
	    
	   <!-- 编辑货人信息-->
			<div class="container">
				 <form id="addConsignee" action="/settlement/wapaddAddressInfo.action" method="post">
				    <input type="hidden" value="${type}" name="type"   />
				     <input type="hidden" value="1" name="defaultAddressId"   />
    				<input type="hidden" value="${address.addressId}" name="naddressId"   />
				<!-- 	<input type="hidden" id="isMoren" name="isMoren" value=""> -->
					<div class="form-group ">
						<input type="text" id="name" name="name" maxlength="15"
							class="form-control input-lg" placeholder="收货人姓名"  value="${address.name}" >
					</div>
					<div class="form-group">
						<input type="text" id="mobile" name="mobile" maxlength="15"
							class="form-control input-lg" placeholder="手机号码" value="${address.cellphone}">
					</div>
					<div class="form-group">
						   <select id="province" name="province" data-value="<s:property value="address.province"/>"  class="form-control control-block" ></select>
					</div>
					<div class="form-group">
						    <select id="city" name="city"  data-value="<s:property value="address.city"/>" class="form-control control-block"></select>
					</div>
					<div class="form-group">
							<select id="area" name="area" data-value="<s:property value="address.area"/>" class="form-control control-block"></select>
					</div>
					<div class="form-group">
						<textarea rows="5" id="detailedAddress" name="detailedAddress"
							maxlength="60" placeholder="详细地址" class="form-control">${address.detailedAddress}</textarea>
					</div>
					<div class="form-group">
						<input type="text" id="postalcode" name="postalcode" maxlength="6"
							class="form-control input-lg" placeholder="邮编"  value="${address.postalcode}">
					</div>
					<div class="form-group">
						<div class="checkbox s_moren">
 							<label>	<input type="checkbox" <s:if test="address.status==0"> checked='checked'</s:if>  name="isMoren" id="isMoren" value="0" > 默认为收货地址
							</label>
						</div>
					</div>
					<div class="form-group">
						<a href="javascript:void(0)" class="btn btn-success btn-block j_saveConsignee">保存地址</a>
					</div>
				</form>
			</div> 
    <!-- 编辑页面 -->
     
    	
    	
    	<%-- <!--内容区域-->
	    <section class="page-content">
	        <header>
	            <div class="page-hd"><a href="javascript:void(0)" class="icon-angle-left fn-left"></a><h2>编辑收货人信息</h2></div>
	        </header>
	        <div class="page-box">
	            <!--编辑收货人信息-->
	            <div class="pay-box fn-p10">
	                <div class="form-input">
	                    <form id="addConsignee" action="/settlement/wapaddAddressInfo.action" method="post">
	                    <input type="hidden" value="${type}" name="type"   />
    					<input type="hidden" value="${address.addressId}" name="naddressId"   />
	                    <input type="hidden"  id="isMoren"  name="isMoren" value="" >
	                        <div class="form-group tbl-type">
	                            <label class="tbl-cell">收货人姓名：</label>
		                            <span class="tbl-cell">
		                            	<input type="text" class="input-text textinput input-block" id="name" name="name" value="${address.name}" maxlength="15" >
		                            </span>
	                        </div>
	                        
	                        <div class="form-group tbl-type">
	                            <label class="tbl-cell">手机号码：</label>
		                            <span class="tbl-cell">
		                           		 <input type="text" class="input-text textinput input-block" id="mobile" name="mobile" value="${address.cellphone}" maxlength="15">
		                            </span>
	                        </div>
	                        
	                        <div class="form-group tbl-type">
	                            <label class="tbl-cell">省  份：</label>
	                            <span class="tbl-cell">
	                                 <select id="province" name="province" data-value="<s:property value="address.province"/>"  class="form-control control-block" ></select>
	                            </span>
	                        </div>
	                        <div class="form-group tbl-type">
	                            <label class="tbl-cell">城  市：</label>
	                            <span class="tbl-cell">
	                                <select id="city" name="city"  data-value="<s:property value="address.city"/>" class="form-control control-block"></select>
	                            </span>
	                        </div>
	                        <div class="form-group tbl-type">
	                            <label class="tbl-cell">县  区：</label>
	                            <span class="tbl-cell">
	                                <select id="area" name="area" data-value="<s:property value="address.area"/>" class="form-control control-block"></select>
	                            </span>
	                        </div>
	                        <div class="form-group tbl-type">
	                            <label class="tbl-cell">详细地址：</label>
	                           	 <span class="tbl-cell">
	                           	 	 <textarea id="detailedAddress" name="detailedAddress" maxlength="60" 
	                           		 	   cols="" rows="" class="textinput control-block">${address.detailedAddress}</textarea>
	                             </span>
	                        </div>
	                        <div class="form-group tbl-type">
	                            <label class="tbl-cell">邮  编：</label>
	                            <span class="tbl-cell">
	                            	<input id="postalcode" name="postalcode" maxlength="6" value="${address.postalcode}"  type="text" class="input-text textinput input-block">
	                            </span>
	                        </div>
	                        <div class="form-group tbl-type">
	                            <label class="tbl-cell">&nbsp;</label>
	                            <!-- 默认收货地址 -->
	                            <span class="tbl-cell">
	                            <span class="cart-checkbox checked s_moren">
	                            <i class="" id="moren"></i></span>默认为收货地址</span>
	                        </div>
	                        <div class="form-group tbl-type">
	                            <label class="tbl-cell">&nbsp;</label>
	                            <span class="tbl-cell"><a class="btn btn-success j_saveConsignee">保存地址</a></span>
	                        </div>
	                    </form>
	                </div>
	            </div>
	        </div>
	    </section> --%>

