package com.pltfm.app.action;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.pltfm.app.service.AreaDictService;
import com.pltfm.app.vobject.AreaDict;

@Controller("areaDictAction")
@Scope(value="prototype")
public class AreaDictAction extends BaseAction {

	private String overlayAreaId;
	
	@Resource
	private AreaDictService areaDictService;
	
	private Map<AreaDict,List<AreaDict>> dataList;
	
	private String areaIds;
		
	public String findAll(){
		try {
			List<AreaDict> li = areaDictService.findAllProvince();
			dataList = new LinkedHashMap();
			for(AreaDict ad : li){
				dataList.put(ad, areaDictService.findCityByProvince(ad.getAreaId()));
			}
		} catch (Exception e) {
			logger.error("findAll error,", e);
		}
		
		return Action.SUCCESS;
	}

	public String findAllProvince(){
		try {
			List<AreaDict> provinceList = areaDictService.findAllProvince();
			getRequest().setAttribute("provinceList", provinceList);
		} catch (Exception e) {
            logger.error("findAllProvince error,", e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String findAllProvinceByWarehouse(){
		try {
			Map<Integer,Integer> areaMap = new HashMap();
			StringTokenizer st = new StringTokenizer(overlayAreaId,"|");
			while(st.hasMoreTokens()){
				String areaId = st.nextToken();
				if(!"".equals(areaId) && !"|".equals(areaId)){
					areaMap.put(Integer.valueOf(areaId), Integer.valueOf(areaId));
				}
			}
			
			super.getRequest().setAttribute("areaMap", areaMap);
			super.getRequest().setAttribute("provinceList",  areaDictService.findAllProvince());
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public Map<AreaDict, List<AreaDict>> getDataList() {
		return dataList;
	}

	public void setDataList(Map<AreaDict, List<AreaDict>> dataList) {
		this.dataList = dataList;
	}

	public String getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}

	public String getOverlayAreaId() {
		return overlayAreaId;
	}

	public void setOverlayAreaId(String overlayAreaId) {
		this.overlayAreaId = overlayAreaId;
	}

}