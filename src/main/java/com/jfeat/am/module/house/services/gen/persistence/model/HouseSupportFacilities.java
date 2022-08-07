package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author Code generator
 * @since 2022-08-05
 */
@TableName("t_house_support_facilities")
@ApiModel(value = "HouseSupportFacilities对象", description = "")
public class HouseSupportFacilities extends Model<HouseSupportFacilities> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "类型id")
    private Long typeId;

    @ApiModelProperty(value = "值")
    private String title;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "是否绑定")
    @TableField(exist = false)
    private Integer status;



    public Long getId() {
        return id;
    }

    public HouseSupportFacilities setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getTypeId() {
        return typeId;
    }

    public HouseSupportFacilities setTypeId(Long typeId) {
        this.typeId = typeId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public HouseSupportFacilities setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public HouseSupportFacilities setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static final String ID = "id";

    public static final String TYPE_ID = "type_id";

    public static final String TITLE = "title";

    public static final String ICON = "icon";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseSupportFacilities{" +
                "id=" + id +
                ", typeId=" + typeId +
                ", title=" + title +
                ", icon=" + icon +
                "}";
    }
}
