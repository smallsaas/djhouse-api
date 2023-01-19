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
    private String houseType;

    @ApiModelProperty(value = "购买-0 出售-1")
    private Integer state;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    // 期数
    private String issue;

    // 楼栋
    private String building;

    // 单元
    private String unit;

    // 楼栋区间 - 开始楼栋
    private Integer startFloor;

    // 楼栋区间 - 结束楼栋
    private Integer endFloor;

    // 朝向
    private String direction;

    // 是否隐藏楼栋
    private Integer hide;

    // 出售 - 自定义楼层
    private String customFloor;

    public String getCustomFloor() {
        return customFloor;
    }

    public void setCustomFloor(String customFloor) {
        this.customFloor = customFloor;
    }

    public Integer getHide() {
        return hide;
    }

    public void setHide(Integer hide) {
        this.hide = hide;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getStartFloor() {
        return startFloor;
    }

    public void setStartFloor(Integer startFloor) {
        this.startFloor = startFloor;
    }

    public Integer getEndFloor() {
        return endFloor;
    }

    public void setEndFloor(Integer endFloor) {
        this.endFloor = endFloor;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

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

    @TableField(exist = false)
    @ApiModelProperty(value = "最后登录时间")
    private Date endLoginTime;

    public Date getEndLoginTime() {
        return endLoginTime;
    }

    public void setEndLoginTime(Date endLoginTime) {
        this.endLoginTime = endLoginTime;
    }

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

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
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

    public static final String HIDE = "hide";

    public static final String CUSTOM_FLOOR = "custom_floor";

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
                ", houseTypeId=" + houseType +
                ", state=" + state +
                ", note=" + note +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
