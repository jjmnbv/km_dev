package com.pltfm.app.action;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.fobject.ShopBrowseInfo;
import com.pltfm.app.service.ShopBrowserService;
import com.pltfm.app.service.SupplierShopService;

/**
 * 店铺浏览量
 * @author Administrator
 *
 */
@Controller("shopBrowseAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class ShopBrowseAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Logger log = Logger.getLogger(ShopBrowseAction.class);
	
	@Resource(name="shopBrowserService")
	private ShopBrowserService shopBrowserService;
	
	@Resource(name="supplierShopService")
	private SupplierShopService supplierShopService;
	
	
	private ShopBrowseInfo shopBrowseInfo;
	
	/**
	 * 分页对象
	 */
	private Page page;

	/**
	 * 调用接口查询店铺浏览量
	 * @return
	 */
	public String queryShopBrowseList(){
		try {
			if(page==null){
				page=new Page();		
			}
			
			
			if(shopBrowseInfo==null){
				shopBrowseInfo=new ShopBrowseInfo();
				shopBrowseInfo.setTimeTypeQuery("0");
			}


            setSupplierShop();
            setTimeTypeMap();
			if(shopBrowseInfo.getShopId()==null || shopBrowseInfo.getShopId().isEmpty()){
				super.getRequest().setAttribute("noShopId", true);
				return "success";
			}	

			shopBrowseInfo.setPn(page.getPageNo());
			shopBrowseInfo.setPs(page.getPageSize());
			log.info("调用接口查询店铺浏览量传递参数如下: shopid="+shopBrowseInfo.getShopId()+",title="+shopBrowseInfo.getTitleForQuery()+",url="+shopBrowseInfo.getTitleForQuery()+",startDate="+shopBrowseInfo.getStartDate()+",endDate="+shopBrowseInfo.getEndDate()+",ps="+shopBrowseInfo.getPs()+",pn="+shopBrowseInfo.getPs()+",timeType="+shopBrowseInfo.getTimeTypeQuery());			
			ShopBrowseInfo returnData=shopBrowserService.queryBrowseInfoList(shopBrowseInfo);
			
			if(returnData.getNgroups()!=null){
				page.setDataList(returnData.getEntity());
				page.setRecordCount(returnData.getNgroups());
				super.getRequest().setAttribute("totalCount", returnData.getMatches());
			}
			
			//分析折线图数据
			Map<String, String> groupData=shopBrowserService.queryGroupData(shopBrowseInfo);
			if(groupData.containsKey("errorCode")){
				log.info("调用接口查询店铺浏览量折线图数据接口发生错误.详情="+groupData.get("errorCode"));	
				super.getRequest().setAttribute("groupDataError", groupData.get("errorCode"));
				return "success";
			}
			String dataStr=groupData.get("dataList");
			if(groupData.get("dataList")==null || "null".equals(groupData.get("dataList"))){
				dataStr=null;
			}
			super.getRequest().setAttribute("groupData", dataStr);
			super.getRequest().setAttribute("groupDataSize", groupData.get("dataSize"));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return "success";
	}
	
	/**
	 * 获取到所有的供应商列表 
	 * @return
	 * @throws Exception 
	 */
	public String findAllSupplierShop() throws Exception{
		try {
			Map<String,String> supplierMap = supplierShopService.querySupplierShop();
			Map<String,String> mp = new LinkedHashMap();
			for(Map.Entry<String, String> entry : supplierMap.entrySet()){
				mp.put(entry.getKey(), entry.getValue());
			}
			super.writeJson(mp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 查询时间类型
	 * 0 所有：起止时间为空（不限时间范围）；
	 * 1 当天：起止时间都自动选择当前日期；
	 * 2 最近七天：开始时间为当前日期减去6天，截止时间为当天；
	 * 3 当月：开始时间为当月1日，截止时间为当天；
	 * 4 上月：开始时间为上月1日，截止时间为上月最后一天； 
	 */
	private void setTimeTypeMap(){
		Map map=new HashMap();
		map.put("0", "所有");
		map.put("1", "当天");
		map.put("2", "最近七天");
		map.put("3", "当月");
		map.put("4", "上月");
		super.getRequest().setAttribute("timeTypeMap", map);
	}
	
	private void setSupplierShop(){
		try {
            super.getRequest().setAttribute("supplierB2bShop",
                    supplierShopService.querySupplierShop());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ShopBrowseInfo getShopBrowseInfo() {
		return shopBrowseInfo;
	}


	public void setShopBrowseInfo(ShopBrowseInfo shopBrowseInfo) {
		this.shopBrowseInfo = shopBrowseInfo;
	}



	@Override
    public Page getPage() {
		return page;
	}



	@Override
    public void setPage(Page page) {
		this.page = page;
	}
	

}
