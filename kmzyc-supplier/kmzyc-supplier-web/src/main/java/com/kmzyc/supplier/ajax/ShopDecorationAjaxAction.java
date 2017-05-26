package com.kmzyc.supplier.ajax;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.framework.constants.Constants;
import com.kmzyc.supplier.action.SupplierBaseAction;
import com.kmzyc.supplier.maps.ShopMainTypeMap;
import com.kmzyc.supplier.model.ShopMain;
import com.kmzyc.supplier.service.ShopDecorationService;
import com.kmzyc.supplier.service.ShopMainService;
import com.kmzyc.zkconfig.ConfigurationUtil;

@Controller("shopDecorationAjaxAction")
@Scope("prototype")
public class ShopDecorationAjaxAction extends SupplierBaseAction  {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private ShopDecorationService shopDecorationService;
	
	@Resource
	private ShopMainService shopMainService;
	
	private String dataType;
	
	private String shopId;
	
	public String editTemplet(){
		try{
            ShopMain shopMain = shopMainService.findShopMainById(Long.parseLong(shopId));
			String requestDomainUrl = ConfigurationUtil.getString("CMS_EDIT_TEMPLET_PATH");
			String editRemoteUrl = new StringBuffer(requestDomainUrl)
                    .append("cms/authenSupplyAction_supplyAuthen.action?shopId=").append(shopId)
                    .append("&dataType=").append(dataType)
                    .append("&shopSite=").append("B2B")
                    .append("&supplyid=").append(shopMain.getSupplierId())
                    .append("&username=").append(URLEncoder.encode(getLoginUserName(), "UTF-8"))
                    .append("&password=").append(getSession().getAttribute(Constants.SESSION_USER_PWD)).toString();
			logger.info("返回链接={}。", editRemoteUrl);
			getResponse().sendRedirect(editRemoteUrl);
		}catch(Exception e){
			logger.error("编辑模板出错: ", e);
		}
		return null;
	}
	
	public String viewTemplet(){
		Map jsonMap = new HashMap();
		try{
            ShopMain shopMain = shopMainService.findShopMainById(Long.parseLong(shopId));
			String editUrl = shopDecorationService.viewTemplet(shopMain,dataType);
			jsonMap.put("flag", true);
			jsonMap.put("editUrl", editUrl);
		}catch(Exception e){
			logger.error("预览模板出错: ", e);
			jsonMap.put("flag", false);
		}
		writeJson(jsonMap);
		return null;
	}
	
	public String publishTemplet(){
		Map jsonMap = new HashMap();
		try{
            ShopMain shopMain = shopMainService.findShopMainById(Long.parseLong(shopId));
			String url = shopDecorationService.publishTemplet(shopMain,dataType);
			if(StringUtils.isNotBlank(url)){
				jsonMap.put("flag", true);
				jsonMap.put("url", url);
				final HttpSession session = super.getSession();
				session.setAttribute(Constants.SESSION_SUPPLIER_SHOPURL,url);
				session.setAttribute(Constants.SESSION_SUPPLIER_SHOPNAME, shopMain.getShopName());
			    session.setAttribute(Constants.SESSION_SUPPLIER_SHOPTYPE, ShopMainTypeMap.getValue(shopMain.getShopType()));
			}else{
				jsonMap.put("flag", false);
			}

			if(StringUtils.isNotBlank(dataType)){
				shopMain.setTemplateType(Integer.parseInt(dataType));
				shopMainService.updateShopMain(shopMain);
			}
		}catch(Exception e){
            jsonMap.put("flag", false);
            logger.error("发布模板出错: ", e);
		}
		
		writeJson(jsonMap);
		return null;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

}
