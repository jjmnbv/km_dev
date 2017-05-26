package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

public class WarehouseInfo implements Serializable {
    
	private static final long serialVersionUID = 7667161140401474770L;

	/**
	 * 仓库ID
	 */
    private Long warehouseId;

    /**
     * 仓库编码
     */
    private String warehouseNo;

    /**
     * 仓库名称
     */
    private String warehouseName;

    /**
     * 所属商家
     */
    private String merchantCode;

    /**
     * 所属地域（城市）
     */
    private Integer areaId;

    /**
     * 所属城市的上级省
     */
    private Integer pAreaId;
    
    /**
     * 仓库类型,保留字段
     */
    private Short type;

    /**
     * 仓库描述
     */
    private String depict;

    /**
     * "仓库覆盖区域 格式：区域ID1|区域ID2..."
     */
    private String overlayArea;

    /**
     *"状态 0：停用 1：启用"
     */
    private String status;

    /**
     * 创建人
     */
    private Integer createUser;
    
    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 备注
     */
    private String remark;
    
    private String overlayAreaId;

    private String areaName;
    
    private WarehouseRelation warehouseRelation;
   
    public String getWarehouseNo() {
        return warehouseNo;
    }

    public void setWarehouseNo(String warehouseNo) {
        this.warehouseNo = warehouseNo;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getDepict() {
        return depict;
    }

    public void setDepict(String depict) {
        this.depict = depict;
    }

    public String getOverlayArea() {
        return overlayArea;
    }

    public void setOverlayArea(String overlayArea) {
        this.overlayArea = overlayArea;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getOverlayAreaId() {
		return overlayAreaId;
	}

	public void setOverlayAreaId(String overlayAreaId) {
		this.overlayAreaId = overlayAreaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public Integer getPAreaId() {
		return pAreaId;
	}

	public void setPAreaId(Integer pAreaId) {
		this.pAreaId = pAreaId;
	}

	public WarehouseRelation getWarehouseRelation() {
		return warehouseRelation;
	}

	public void setWarehouseRelation(WarehouseRelation warehouseRelation) {
		this.warehouseRelation = warehouseRelation;
	}
}