package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2023-01-05
 */
@TableName("t_house_asset_transaction")
@ApiModel(value = "HouseAssetTransaction对象", description = "")
public class HouseAssetTransaction extends Model<HouseAssetTransaction> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "房屋id")
    private Long assetId;

    @ApiModelProperty(value = "户型id")
    private Long houseTypeId;

    @ApiModelProperty(value = "购买-0 出售-1")
    private Integer state;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


    @TableField(exist = false)
    @ApiModelProperty(value = "用户名")
    private String userName;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户电话")
    private String userPhone;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户真实姓名")
    private String userRealName;

    @TableField(exist = false)
    @ApiModelProperty(value = "中文状态")
    private String cnStatus;

    @TableField(exist = false)
    @ApiModelProperty(value = "英文状态")
    private String enStatus;

    @TableField(exist = false)
    @ApiModelProperty(value = "资产")
    private HouseAsset houseAsset;

    @TableField(exist = false)
    @ApiModelProperty(value = "户型")
    private HouseDesignModel houseDesignModel;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getCnStatus() {
        return cnStatus;
    }

    public void setCnStatus(String cnStatus) {
        this.cnStatus = cnStatus;
    }

    public String getEnStatus() {
        return enStatus;
    }

    public void setEnStatus(String enStatus) {
        this.enStatus = enStatus;
    }

    public HouseAsset getHouseAsset() {
        return houseAsset;
    }

    public void setHouseAsset(HouseAsset houseAsset) {
        this.houseAsset = houseAsset;
    }

    public HouseDesignModel getHouseDesignModel() {
        return houseDesignModel;
    }

    public void setHouseDesignModel(HouseDesignModel houseDesignModel) {
        this.houseDesignModel = houseDesignModel;
    }

    public Long getId() {
        return id;
    }

    public HouseAssetTransaction setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public HouseAssetTransaction setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getAssetId() {
        return assetId;
    }

    public HouseAssetTransaction setAssetId(Long assetId) {
        this.assetId = assetId;
        return this;
    }

    public Long getHouseTypeId() {
        return houseTypeId;
    }

    public HouseAssetTransaction setHouseTypeId(Long houseTypeId) {
        this.houseTypeId = houseTypeId;
        return this;
    }

    public Integer getState() {
        return state;
    }

    public HouseAssetTransaction setState(Integer state) {
        this.state = state;
        return this;
    }

    public String getNote() {
        return note;
    }

    public HouseAssetTransaction setNote(String note) {
        this.note = note;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public HouseAssetTransaction setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public HouseAssetTransaction setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String ASSET_ID = "asset_id";

    public static final String HOUSE_TYPE_ID = "house_type_id";

    public static final String STATE = "state";

    public static final String NOTE = "note";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    public static final Integer STATE_BUY=0;

    public static final Integer STATE_SELL=1;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseAssetTransaction{" +
                "id=" + id +
                ", userId=" + userId +
                ", assetId=" + assetId +
                ", houseTypeId=" + houseTypeId +
                ", state=" + state +
                ", note=" + note +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
