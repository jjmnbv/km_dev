package com.kmzyc.cms.remote.service;

import com.kmzyc.supplier.model.ShopMain;

public interface RemoteSupplierParseService {

    /**
     * 提供给供应商的发布接口
     * 
     * @author gwl
     * 
     *         参数1：ShopMain 对象 参数 2int themeType 模板 2默认 3简版 返回 URL
     */
    public String remoteParse(ShopMain shopMain, int themeType) throws Exception;

    /**
     * 预览给供应商
     * 
     * @author gwl
     * 
     *         参数1：ShopMain 对象 参数 2int themeType 模板 2默认 3简版 key店铺ID 值为URL
     */
    public String remoteViweParse(ShopMain shopMain, int themeType) throws Exception;

    /**
     * 选择供应商店铺模板 参数1：ShopMain 对象 参数 2int themeType 模板 2默认 3简版 返回 URL
     */
    public String remoteAddTheme(ShopMain shopMain, int type) throws Exception;

    /**
     * 可视化供应商店铺模板 参数1：ShopMain 对象 参数 2int themeType 模板 2默认 3简版 返回 URL
     */
    public String remotePreShopMain(ShopMain shopMain, int themeType) throws Exception;
}
