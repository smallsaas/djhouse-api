package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

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
 * @since 2022-12-08
 */
@TableName("t_house_user_asset_history")
@ApiModel(value = "HouseUserAssetHistory对象", description = "")
public class HouseUserAssetHistory extends Model<HouseUserAssetHistory> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "资产id")
    private Long assetId;

    @ApiModelProperty(value = "是否托管 0没有托管 1托管")
    private Integer trust;

    @ApiModelProperty(value = "是否出租 0没有出租 1出租")
    private Integer rentStatus;

    @ApiModelProperty(value = "添加房产时间")
    private Date createTime;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "是否为后台确认的最终标记 0-不是 1-是")
    private Integer finalFlag;

    @ApiModelProperty(value = "用户类型 1-房东 2-二房东")
    private Integer userType;

    @ApiModelProperty(value = "是否锁定不参与换房 0-不锁定 1-锁定")
    private Integer locked;

    @ApiModelProperty(value = "不行喜欢换换试试 1-喜欢 0-不喜欢")
    private Integer unlike;

    private Date deleteTime;

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Long getId() {
        return id;
    }

    public HouseUserAssetHistory setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public HouseUserAssetHistory setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getAssetId() {
        return assetId;
    }

    public HouseUserAssetHistory setAssetId(Long assetId) {
        this.assetId = assetId;
        return this;
    }

    public Integer getTrust() {
        return trust;
    }

    public HouseUserAssetHistory setTrust(Integer trust) {
        this.trust = trust;
        return this;
    }

    public Integer getRentStatus() {
        return rentStatus;
    }

    public HouseUserAssetHistory setRentStatus(Integer rentStatus) {
        this.rentStatus = rentStatus;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public HouseUserAssetHistory setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getNote() {
        return note;
    }

    public HouseUserAssetHistory setNote(String note) {
        this.note = note;
        return this;
    }

    public Integer getFinalFlag() {
        return finalFlag;
    }

    public HouseUserAssetHistory setFinalFlag(Integer finalFlag) {
        this.finalFlag = finalFlag;
        return this;
    }

    public Integer getUserType() {
        return userType;
    }

    public HouseUserAssetHistory setUserType(Integer userType) {
        this.userType = userType;
        return this;
    }

    public Integer getLocked() {
        return locked;
    }

    public HouseUserAssetHistory setLocked(Integer locked) {
        this.locked = locked;
        return this;
    }

    public Integer getUnlike() {
        return unlike;
    }

    public HouseUserAssetHistory setUnlike(Integer unlike) {
        this.unlike = unlike;
        return this;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String ASSET_ID = "asset_id";

    public static final String TRUST = "trust";

    public static final String RENT_STATUS = "rent_status";

    public static final String CREATE_TIME = "create_time";

    public static final String NOTE = "note";

    public static final String FINAL_FLAG = "final_flag";

    public static final String USER_TYPE = "user_type";

    public static final String LOCKED = "locked";

    public static final String UNLIKE = "unlike";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseUserAssetHistory{" +
                "id=" + id +
                ", userId=" + userId +
                ", assetId=" + assetId +
                ", trust=" + trust +
                ", rentStatus=" + rentStatus +
                ", createTime=" + createTime +
                ", note=" + note +
                ", finalFlag=" + finalFlag +
                ", userType=" + userType +
                ", locked=" + locked +
                ", unlike=" + unlike +
                "}";
    }
}
