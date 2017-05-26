package com.kmzyc.supplier.enums;

/**
 * 功能：供应商报名活动状态
 *
 * @author Zhoujiwei
 * @since 2016/3/22 16:41
 */
public enum SupplierEntryStatus {

    //报名表：报名状态为0（未报名），并且审核状态为0（未审核）
    //款项表：首次缴费有效性1（有效），首次缴费有效性1（有效）
    NOT_ENTRY("未报名",1),
    //报名表：报名状态为3（撤销报名），并且审核状态为0（未审核）
    //款项表：首次缴费有效性1（有效），首次缴费有效性1（有效）
    REVOKE("已撤销",3),
    //报名表：报名状态为2（已报名），并且审核状态为2（审核不通过）
    //款项表：首次缴费有效性1（有效），首次缴费有效性1（有效）
    NOT_PASS("审核不通过",6),
    //报名表：报名状态为2（已报名），并且审核状态为1（已审核）
    //款项表：款项类型为1（首次缴费），首次缴费有效性1（有效），款项状态为2（已缴费）
    PASS("报名成功",2),
    //报名表：报名状态为2（已报名），并且审核状态为0（未审核）
    //款项表：款项类型为1（首次缴费），首次缴费有效性1（有效），款项状态为1（待缴费）
    NOT_PAY("已提交待缴费",4),
    //报名表：报名状态为2（已报名），并且审核状态为0（未审核）
    //款项表：款项类型为1（首次缴费），首次缴费有效性1（有效），款项状态为2（已缴费）
    NOT_AUDIT("已提交待审核",5),
    //报名表：报名状态为2（已报名），并且审核状态不为1（非已审核）
    //款项表：首次缴费有效性1（有效），首次缴费有效性1（有效）
    //TODO
    ENTRY_FAIL("报名失败",7);

    private String title;

    private Integer status;

    @Override
    public String toString() {
        return "SupplierEntryStatus{" +
                "title='" + title + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    SupplierEntryStatus(String title, Integer status) {
        this.title = title;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    private void setStatus(Integer status) {
        this.status = status;
    }
}
