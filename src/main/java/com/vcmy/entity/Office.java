package com.vcmy.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author liaojiexin
 * @Description 组织部门
 * @Date 2020/12/11 11:24
 * @Param
 * @return
 **/
public class Office extends PageDomain{
    @ApiModelProperty("部门id")
    private Integer officeId;  //部门id
    @ApiModelProperty("部门名称")
    private String officeName;  //部门名称
    @ApiModelProperty("创建人")
    private String createBy;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("修改时间")
    private Date updateTime;
    @ApiModelProperty("父级id")
    private Integer parentId;
    @ApiModelProperty("子级部门集合")
    private List<Office> children;
    @ApiModelProperty("判断是否是子级")
    private Boolean isChild = false;

    public Integer getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName == null ? null : officeName.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<Office> getChildren() {
        return children;
    }

    public void setChildren(List<Office> children) {
        this.children = children;
    }

    public Boolean getIsChild() {
        return isChild;
    }

    public void setIsChild(Boolean child) {
        isChild = child;
    }

    public void addChildren(Office child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
    }
}