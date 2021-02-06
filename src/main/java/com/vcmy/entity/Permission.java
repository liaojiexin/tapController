package com.vcmy.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel("权限")
public class Permission implements Serializable {
    @ApiModelProperty("权限id")
    private Integer permissionId;
    @ApiModelProperty("权限名称")
    private String permissionName;
    @ApiModelProperty("父级id")
    private Integer parentId;
    @ApiModelProperty("是否是子级")
    private Boolean isChild = false;
    @ApiModelProperty("子级集合")
    private List<Permission> children;
    @ApiModelProperty("url")
    private String url;

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Boolean getIsChild() {
        return isChild;
    }

    public void setIsChild(Boolean isChild) {
        this.isChild = isChild;
    }

    public List<Permission> getChildren() {
        return children;
    }

    public void setChildren(List<Permission> children) {
        this.children = children;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addChildren(Permission permission) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(permission);
    }
}