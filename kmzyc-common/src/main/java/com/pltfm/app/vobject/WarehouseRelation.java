package com.pltfm.app.vobject;

import java.io.Serializable;


public class WarehouseRelation implements Serializable {
    
	private static final long serialVersionUID = -8711298176939848468L;

    private Long warehouseRelationId;

    /**
     * 产品系统仓库ID
     */
    private Long warehouseId;

    /**
     * 外部系统仓库编码
     */
    private String externalWarehouseCode;

    /**
     * 外部系统代码
     */
    private String systemCode;

    /**
     * 0：未对应 1：已对应
     */
    private Integer status;

	public Long getWarehouseRelationId() {
		return warehouseRelationId;
	}

	public void setWarehouseRelationId(Long warehouseRelationId) {
		this.warehouseRelationId = warehouseRelationId;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getExternalWarehouseCode() {
		return externalWarehouseCode;
	}

	public void setExternalWarehouseCode(String externalWarehouseCode) {
		this.externalWarehouseCode = externalWarehouseCode;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}