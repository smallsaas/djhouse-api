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
 * @since 2022-06-06
 */
@TableName("t_house_property_community")
@ApiModel(value = "HousePropertyCommunity对象", description = "")
public class HousePropertyCommunity extends Model<HousePropertyCommunity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "小区")
    private String community;

    @ApiModelProperty(value = "小区编号")
    private String communityCode;

    @ApiModelProperty(value = "社区id")
    private Long tenantId;

    @ApiModelProperty(value = "小区地址")
    private String address;

    @ApiModelProperty(value = "图片")
    private String picture;

    @ApiModelProperty(value = "邮码")
    private String postcode;

    @ApiModelProperty(value = "社区名")
    @TableField(exist = false)
    private String tenant;


    public String getTenant() {
        return tenant;
    }

    public HousePropertyCommunity setTenant(String tenant) {
        this.tenant = tenant;
        return this;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public HousePropertyCommunity setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public HousePropertyCommunity setAddress(String address) {
        this.address = address;
        return this;
    }

    public Long getId() {
        return id;
    }

    public HousePropertyCommunity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCommunity() {
        return community;
    }

    public HousePropertyCommunity setCommunity(String community) {
        this.community = community;
        return this;
    }

    public String getCommunityCode() {
        return communityCode;
    }

    public HousePropertyCommunity setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public HousePropertyCommunity setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public String getPostcode() {
        return postcode;
    }

    public HousePropertyCommunity setPostcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public static final String ID = "id";

    public static final String COMMUNITY = "community";

    public static final String COMMUNITY_CODE = "community_code";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HousePropertyCommunity{" +
                "id=" + id +
                ", community=" + community +
                ", communityCode=" + communityCode +
                "}";
    }
}
