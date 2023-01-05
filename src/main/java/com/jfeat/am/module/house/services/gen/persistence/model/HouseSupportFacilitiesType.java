package com.jfeat.am.module.house.services.gen.persistence.model;

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
@TableName("t_house_support_facilities_type")
@ApiModel(value = "HouseSupportFacilitiesType对象", description = "")
public class HouseSupportFacilitiesType extends Model<HouseSupportFacilitiesType> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "英文名")
    private String enName;

    @ApiModelProperty(value = "中文名")
    private String cnName;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty
    private Integer sortNum;

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Long getId() {
        return id;
    }

    public HouseSupportFacilitiesType setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEnName() {
        return enName;
    }

    public HouseSupportFacilitiesType setEnName(String enName) {
        this.enName = enName;
        return this;
    }

    public String getCnName() {
        return cnName;
    }

    public HouseSupportFacilitiesType setCnName(String cnName) {
        this.cnName = cnName;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public HouseSupportFacilitiesType setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public static final String ID = "id";

    public static final String EN_NAME = "en_name";

    public static final String CN_NAME = "cn_name";

    public static final String ICON = "icon";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseSupportFacilitiesType{" +
                "id=" + id +
                ", enName=" + enName +
                ", cnName=" + cnName +
                ", icon=" + icon +
                "}";
    }
}
