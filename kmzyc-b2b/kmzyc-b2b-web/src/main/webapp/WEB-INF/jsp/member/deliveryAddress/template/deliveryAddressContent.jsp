<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<input name="addressId" type="hidden" value="" id="addressId">
<div class="l-right user-m">
	<div class="o-mt">
		<h2>收货地址</h2>
		<div class="Inquiry">
			<div class="ui-form">
				<a class="btn-add j_btnAdd" data-accountId="<s:property value='#request.addressList[0].accountId'/>" ><span>添加收货地址</span></a> <span>最多保存10个有效地址</span>
			</div>
		</div>
	</div>
	<div class="user-m fn-t10">
		<div class="mt">
					<ul class="tab">
						<li class="curr"><s></s><b></b><a>全部地址</a>
					</ul>
		</div>
		<div class="mc">
			<div class="ui-table">
				<table class="ui-table user-table" id="addressTB">
					<thead>
						<tr>
						        	<th class="td-s3">收货人</th>
                                    <th class="td-s7">手机</th>                    
                                    <th class="td-s6">地址</th>           
                                    <th class="td-s3">默认</th>
                                    <th>操作</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="#request.addressList" id="Address" status="status">
							<tr>
								<td>${Address.name}</td>
								<td>${Address.cellphone}</td>	
								<td>${Address.province}${Address.city}${Address.area}${Address.detailedAddress}</td>
								<td>
								<s:if test="#Address.status==0">
								 是
								</s:if>
								<s:elseif test="#Address.status==1">
								否
								</s:elseif> 	
								</td>
								<td>
									<p class="fn-blue">
										<span><a href="javascript:void(0)" data-addressId="${Address.addressId}" class="j_updateAddress">修改</a></span>
										<s:if test="#Address.status==1">
										<span><a href="javascript:void(0)" data-addressId="${Address.addressId}" class="j_deleteAddress">删除</a></span>
										</s:if>
									</p>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<div class="fn-tr fn-t10">
					<div class="ui-page"></div>
				</div>
			</div>
		</div>
	</div>
</div>

</div>
