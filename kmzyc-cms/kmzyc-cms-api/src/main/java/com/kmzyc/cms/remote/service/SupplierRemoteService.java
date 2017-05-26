package com.kmzyc.cms.remote.service;

import java.util.List;

import com.kmzyc.supplier.model.ShopMain;
import com.pltfm.cms.vobject.CmsAdv;
//import com.pltfm.cms.vobject.CmsAdv;

public interface SupplierRemoteService {
    //店铺关闭(ShopId)
    public String closeShop(ShopMain shopMain);
    //重开店铺
    public String reopenShop(ShopMain shopMain);
    //发布时调用的接口
     public String addSupplierRelation(List<ShopMain> shopMainList);
     
     //提供广告位列表(返回广告位对象列表)接口
     public List queryAdvColumnListBySupplierId(ShopMain ShopMain);
     //提供广告列表(返回广告对象列表)接口
     public List queryAdvListBySupplierId(CmsAdv cmsAdv);
      //广告上传或修改接口
     public String uploadAdv(ShopMain shopMain,CmsAdv cmsAdv);
     
     //广告发布接口
	 
	 public String  parseAdv(CmsAdv cmsAdv);
	 
	// 广告详情页
	 public CmsAdv queryAdvById(CmsAdv cmsAdv);
	//删除
	 public String  delAdv(CmsAdv cmsAdv);
	 
	 
	 	
	    public Integer countByAdv(CmsAdv cmsAdv) throws Exception;
	  //多选发布
		public String parseSelAdv(Integer []ids);
		//多选删除
		public String delSelAdv(Integer []ids);
		
     public boolean checkAdvIsUpload(ShopMain shopMain);
        /**
         * 供应商店铺装修登录退出
         */
        public void supplyLogOut();

}
