package com.pltfm.sys.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pltfm.sys.bean.SysParamBean;
import com.pltfm.sys.model.SysParam;

public class CachedParams {

		/**
		 * @description: 第一次查询某个参数，将查询结果list放入cache，并将sysCd+","+paramGp作为key
		 * @param sysCd,paramGp
		 * @return Map
		 */
//		private static Map<String,String> loadParamList(String sysCd,String paramGp) {
//			String paraStr = sysCd + "#" + paramGp;
//			List<SysParam> paramList = null;
//			HashMap<String,String> hm = null;
//			try {
//				System.out.println("--------------> 第一次查询参数："+paraStr);
//				SysParamBean bean = new SysParamBean();
//				paramList = bean.getSysParamList(sysCd,paramGp);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			if (null != paramList) {
//				hm = new HashMap<String,String>();
//				for(int i=0; i<paramList.size(); i++){
//					hm.put( ((SysParam)paramList.get(i)).getParamValue(), ((SysParam)paramList.get(i)).getParamName());
//				}
//				//set in cache
//				MemCachedUtil.getInstance().add(paraStr, hm);
//				System.out.println("-------------->loaded into cache["+paraStr+"]");
//			}
//			return hm;
//		}
//
//
//
//
//
//
//		/**
//		 * @description: 根据sysCd,paramGp查询cache中的值
//		 * @param sysCd,paramGp
//		 * @return Map
//		 */
//		@SuppressWarnings("unchecked")
//		public static Map<String,String> getParamListByCd(String sysCd,String paramGp) {
//			String paraStr = sysCd + "#" + paramGp;
//			Map<String,String> hm = null;
//			if (MemCachedUtil.getInstance().isExists(paraStr)) {
//				System.out.println("============ in cache:["+paraStr+"]");
//				hm = (Map<String, String>) MemCachedUtil.getInstance().get(paraStr);
//			}else{
//				System.out.println("************ not in cache:["+paraStr+"]");
//				hm = loadParamList(sysCd,paramGp);
//			}
//			
//			return hm;
//		}




	}