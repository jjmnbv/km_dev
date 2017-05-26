<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.kmzyc.zkconfig.ConfigurationUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

	    
	   <!--添加收货人信息-->
			<div class="container">
				<form id="addConsignee" action="/settlement/wapaddAddressInfo.action" method="post">
					<input type="hidden" value="${type}" name="type" /> 
				<!-- 	<input type="hidden" id="isMoren" name="isMoren" value=""> -->
					<div class="form-group ">
						<input type="text" id="name" name="name" maxlength="15"
							class="form-control input-lg" placeholder="收货人姓名">
					</div>
					<div class="form-group">
						<input type="text" id="mobile" name="mobile" maxlength="15"
							class="form-control input-lg" placeholder="手机号码">
					</div>
					<div class="form-group">
						<select id="province" name="province"
							class="form-control input-lg">
						</select>
					</div>
					<div class="form-group">
						<select id="city" name="city" class="form-control input-lg">
						</select>
					</div>
					<div class="form-group">
						<select id="area" name="area" class="form-control input-lg">
						</select>
					</div>
					<div class="form-group">
						<textarea rows="5" id="detailedAddress" name="detailedAddress"
							maxlength="60" placeholder="详细地址" class="form-control"></textarea>
					</div>
					<div class="form-group">
						<input type="text" id="postalcode" name="postalcode" maxlength="6"
							class="form-control input-lg" placeholder="邮编">
					</div>
					<div class="form-group">
						<div class="checkbox s_moren">
							<label> 
							<input type="checkbox" name="isMoren" id="isMoren" value="0" >
							 默认为收货地址
							</label>
						</div>
					</div>
					<div class="form-group">
						<a href="javascript:void(0);" class="btn btn-success btn-block j_saveConsignee">保存地址</a>
					</div>
				</form>
			</div>
  
