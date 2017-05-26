package com.pltfm.app.vobject;

import java.util.ArrayList;
import java.util.List;

public class ConfigAreaDict implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4679198780621431966L;

	private Integer areaId;

    private String areaCode;

    private String areaName;

    private Integer supperareaId;

    private Short areatype;
    
    private ConfigAreaDict parentConfigAreaDict;
    
    private List<ConfigAreaDict> childConfigAreaDictList = new ArrayList<ConfigAreaDict>();


    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaCode() {
        return areaCode;
    }
    
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getSupperareaId() {
        return supperareaId;
    }

    public void setSupperareaId(Integer supperareaId) {
        this.supperareaId = supperareaId;
    }

    public Short getAreatype() {
        return areatype;
    }

    public void setAreatype(Short areatype) {
        this.areatype = areatype;
    }

	public List<ConfigAreaDict> getChildConfigAreaDictList() {
		return childConfigAreaDictList;
	}

	public void setChildConfigAreaDictList(
			List<ConfigAreaDict> childConfigAreaDictList) {
		this.childConfigAreaDictList = childConfigAreaDictList;
	}

	public ConfigAreaDict getParentConfigAreaDict() {
		return parentConfigAreaDict;
	}

	public void setParentConfigAreaDict(ConfigAreaDict parentConfigAreaDict) {
		this.parentConfigAreaDict = parentConfigAreaDict;
	}
}