package com.pltfm.sys.model;

import java.io.Serializable;
import java.util.Objects;


public class SysRole implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_ROLE.ROLE_ID
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    private Integer roleId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_ROLE.ROLE_NAME
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    private String roleName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_ROLE.ROLE_EXPLAIN
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    private String roleExplain;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column SYS_ROLE.ROLE_REMARK
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    private String roleRemark;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table SYS_ROLE
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_ROLE.ROLE_ID
     *
     * @return the value of SYS_ROLE.ROLE_ID
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_ROLE.ROLE_ID
     *
     * @param roleId the value for SYS_ROLE.ROLE_ID
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_ROLE.ROLE_NAME
     *
     * @return the value of SYS_ROLE.ROLE_NAME
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_ROLE.ROLE_NAME
     *
     * @param roleName the value for SYS_ROLE.ROLE_NAME
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_ROLE.ROLE_EXPLAIN
     *
     * @return the value of SYS_ROLE.ROLE_EXPLAIN
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public String getRoleExplain() {
        return roleExplain;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_ROLE.ROLE_EXPLAIN
     *
     * @param roleExplain the value for SYS_ROLE.ROLE_EXPLAIN
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public void setRoleExplain(String roleExplain) {
        this.roleExplain = roleExplain == null ? null : roleExplain.trim();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column SYS_ROLE.ROLE_REMARK
     *
     * @return the value of SYS_ROLE.ROLE_REMARK
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public String getRoleRemark() {
        return roleRemark;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column SYS_ROLE.ROLE_REMARK
     *
     * @param roleRemark the value for SYS_ROLE.ROLE_REMARK
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    public void setRoleRemark(String roleRemark) {
        this.roleRemark = roleRemark == null ? null : roleRemark.trim();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ROLE
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysRole sysRole = (SysRole) o;
        return Objects.equals(roleId, sysRole.roleId) &&
                Objects.equals(roleName, sysRole.roleName) &&
                Objects.equals(roleExplain, sysRole.roleExplain) &&
                Objects.equals(roleRemark, sysRole.roleRemark);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_ROLE
     *
     * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
     */
    @Override
    public int hashCode() {
        int hash = 23;
        if (getRoleId() != null) {
            hash *= getRoleId().hashCode();
        }
        if (getRoleName() != null) {
            hash *= getRoleName().hashCode();
        }
        if (getRoleExplain() != null) {
            hash *= getRoleExplain().hashCode();
        }
        if (getRoleRemark() != null) {
            hash *= getRoleRemark().hashCode();
        }
        return hash;
    }
}