package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author Code generator
 * @since 2022-08-01
 */
@TableName("t_house_menu")
@ApiModel(value = "HouseMenu对象", description = "")
public class HouseMenu extends Model<HouseMenu> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "请求路径规则")
    private String url;

    @ApiModelProperty(value = "路由请求路径")
    private String path;

    @ApiModelProperty(value = "组件名称")
    private String component;

    @ApiModelProperty(value = "组件名")
    private String name;

    @ApiModelProperty(value = "图标")
    @TableField("iconCls")
    private String iconCls;

    @ApiModelProperty(value = "是否登录可用")
    @TableField("requireAuth")
    private Integer requireAuth;

    @ApiModelProperty(value = "父组件id")
    @TableField("parentId")
    private Long parentId;

    @ApiModelProperty(value = "是否可用")
    private Integer enabled;

    private String type;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public HouseMenu setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public HouseMenu setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPath() {
        return path;
    }

    public HouseMenu setPath(String path) {
        this.path = path;
        return this;
    }

    public String getComponent() {
        return component;
    }

    public HouseMenu setComponent(String component) {
        this.component = component;
        return this;
    }

    public String getName() {
        return name;
    }

    public HouseMenu setName(String name) {
        this.name = name;
        return this;
    }

    public String getIconCls() {
        return iconCls;
    }

    public HouseMenu setIconCls(String iconCls) {
        this.iconCls = iconCls;
        return this;
    }

    public Integer getRequireAuth() {
        return requireAuth;
    }

    public HouseMenu setRequireAuth(Integer requireAuth) {
        this.requireAuth = requireAuth;
        return this;
    }

    public Long getParentId() {
        return parentId;
    }

    public HouseMenu setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public HouseMenu setEnabled(Integer enabled) {
        this.enabled = enabled;
        return this;
    }

    public static final String ID = "id";

    public static final String URL = "url";

    public static final String PATH = "path";

    public static final String COMPONENT = "component";

    public static final String NAME = "name";

    public static final String ICONCLS = "iconCls";

    public static final String REQUIREAUTH = "requireAuth";

    public static final String PARENTID = "parentId";

    public static final String ENABLED = "enabled";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseMenu{" +
                "id=" + id +
                ", url=" + url +
                ", path=" + path +
                ", component=" + component +
                ", name=" + name +
                ", iconCls=" + iconCls +
                ", requireAuth=" + requireAuth +
                ", parentId=" + parentId +
                ", enabled=" + enabled +
                "}";
    }
}
