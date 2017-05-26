package com.kmzyc.supplier.model;

import com.pltfm.app.vobject.ProdAppraise;

public class ProdAppraiseAddContent extends ProdAppraise {

    private static final long serialVersionUID = 2556074436854284848L;

    private String addContent;

    private Long addCheckStatus;

    public String getAddContent() {
        return addContent;
    }

    public void setAddContent(String addContent) {
        this.addContent = addContent;
    }

    public Long getAddCheckStatus() {
        return addCheckStatus;
    }

    public void setAddCheckStatus(Long addCheckStatus) {
        this.addCheckStatus = addCheckStatus;
    }

}