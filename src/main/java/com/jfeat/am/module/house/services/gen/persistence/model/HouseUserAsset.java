package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.crypto.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author Code generator
 * @since 2022-06-11
 */
@TableName("t_house_user_asset")
@ApiModel(value = "HouseUserAsset对象", description = "")
public class HouseUserAsset extends Model<HouseUserAsset> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @TableField(exist = false)
    private Long notUserId;

    @ApiModelProperty(value = "资产id")
    private Long assetId;

    @ApiModelProperty(value = "是否托管 0不托管 1托管")
    private Integer trust;

    @ApiModelProperty(value = "房东类型 1-房东 2-二房东")
    private Integer userType;


    private String note;

    private Date createTime;

    private Integer finalFlag;

    @ApiModelProperty("锁定换房 0-不锁定换房 1-锁定换房 默认为0")
    private Integer locked;

    @ApiModelProperty("不行喜欢换换试试 1-喜欢 0-不喜欢 默认为1")
    private Integer unlike;

    @ApiModelProperty("不行状态")
    @TableField(exist = false)
    private Boolean unlikeStatus;


    @ApiModelProperty(value = "详细地址")
    @TableField(exist = false)
    private String addressDetail;

    @ApiModelProperty("方向")
    @TableField(exist = false)
    private String direction;

    @TableField(exist = false)
    private String extra;


    @TableField(exist = false)
    private String tags;

    @ApiModelProperty(value = "社区Id")
    @TableField(exist = false)
    private Long communityId;

    @ApiModelProperty(value = "社区名")
    @TableField(exist = false)
    private String communityName;

    @ApiModelProperty(value = "楼栋id")
    @TableField(exist = false)
    private Long buildingId;

    @ApiModelProperty(value = "楼栋编号")
    @TableField(exist = false)
    private String buildingCode;

    @ApiModelProperty(value = "区域")
    @TableField(exist = false)
    private String buildingArea;

    @ApiModelProperty(value = "期数")
    @TableField(exist = false)
    private Integer issue;

    @ApiModelProperty(value = "单元Id")
    @TableField(exist = false)
    private Long unitId;

    @TableField(exist = false)
    private Long houseTypeId;

    @ApiModelProperty(value = "户型")
    @TableField(exist = false)
    private String houseType;

    @ApiModelProperty(value = "户型图")
    @TableField(exist = false)
    private String houseTypePicture;

    @ApiModelProperty(value = "vr缩略图")
    @TableField(exist = false)
    private String vrSnapshot;

    @ApiModelProperty(value = "vr连接")
    @TableField(exist = false)
    private String vrLink;

    @ApiModelProperty(value = "房屋面积")
    @TableField(exist = false)
    private BigDecimal unitArea;

    @TableField(exist = false)
    @ApiModelProperty(value = "真实面积")
    private BigDecimal realArea;

    @ApiModelProperty(value = "房屋编号")
    @TableField(exist = false)
    private String roomNumber;

    @ApiModelProperty(value = "楼层")
    @TableField(exist = false)
    private Integer floor;


    @ApiModelProperty("地址")
    @TableField(exist = false)
    private String address;


    @ApiModelProperty("用户名")
    @TableField(exist = false)
    private String username;

    @ApiModelProperty("用户电话")
    @TableField(exist = false)
    private String userPhone;

    @ApiModelProperty("用户头像")
    @TableField(exist = false)
    private String userAvatar;

    @ApiModelProperty("真实姓名")
    @TableField(exist = false)
    private String realName;

    @TableField(exist = false)
    private Long orgId;

    @TableField(exist = false)
    private Integer assetNumber;


    public Long getHouseTypeId() {
        return houseTypeId;
    }

    public void setHouseTypeId(Long houseTypeId) {
        this.houseTypeId = houseTypeId;
    }

    public Boolean getUnlikeStatus() {
        return unlikeStatus;
    }

    public void setUnlikeStatus(Boolean unlikeStatus) {
        this.unlikeStatus = unlikeStatus;
    }

    public Long getNotUserId() {
        return notUserId;
    }

    public void setNotUserId(Long notUserId) {
        this.notUserId = notUserId;
    }

    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Integer getUnlike() {
        return unlike;
    }

    public void setUnlike(Integer unlike) {
        this.unlike = unlike;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public BigDecimal getRealArea() {
        return realArea;
    }

    public void setRealArea(BigDecimal realArea) {
        this.realArea = realArea;
    }

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(Integer assetNumber) {
        this.assetNumber = assetNumber;
    }

    public Integer getTrust() {
        return trust;
    }

    public void setTrust(Integer trust) {
        this.trust = trust;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNote() {
        return note;
    }

    public HouseUserAsset setNote(String note) {
        this.note = note;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public HouseUserAsset setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public HouseUserAsset setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public HouseUserAsset setUserPhone(String userPhone) {
        this.userPhone = userPhone;
        return this;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(String buildingArea) {
        this.buildingArea = buildingArea;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public BigDecimal getUnitArea() {
        return unitArea;
    }

    public void setUnitArea(BigDecimal unitArea) {
        this.unitArea = unitArea;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Long getId() {
        return id;
    }

    public HouseUserAsset setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public HouseUserAsset setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getAssetId() {
        return assetId;
    }

    public HouseUserAsset setAssetId(Long assetId) {
        this.assetId = assetId;
        return this;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public HouseUserAsset setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
        return this;
    }


    public String getHouseTypePicture() {
        return houseTypePicture;
    }

    public void setHouseTypePicture(String houseTypePicture) {
        this.houseTypePicture = houseTypePicture;
    }

    public String getVrSnapshot() {
        return vrSnapshot;
    }

    public void setVrSnapshot(String vrSnapshot) {
        this.vrSnapshot = vrSnapshot;
    }

    public String getVrLink() {
        return vrLink;
    }

    public void setVrLink(String vrLink) {
        this.vrLink = vrLink;
    }


    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getFinalFlag() {
        return finalFlag;
    }

    public void setFinalFlag(Integer finalFlag) {
        this.finalFlag = finalFlag;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String ASSET_ID = "asset_id";

    public static final String LOCKED = "locked";

    public static final Integer LOCKED_STATUS_LOCKED = 1;

    public static final Integer LOCKED_STATUS_UNLOCKED = 0;

    public static final String UNLIKE = "unlike";

    public static final Integer UNLIKE_STATUS_LIKE = 1;

    public static final Integer UNLIKE_STATUS_UNLIKE = 0;


    /*
    后台最终确认 房子最终产权
     */
    public static final Integer FINAL_FLAG_CONFIRM = 1;

    /*
    后台没有确认产权 默认值
     */
    public static final Integer FINAL_FLAG_NOT_CONFIRM = 0;

    public static final Integer USER_TYPE_LANDLORD = 1;

    public static final Integer USER_TYPE_PRINCIPAL = 2;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseUserAsset{" +
                "id=" + id +
                ", userId=" + userId +
                ", assetId=" + assetId +
                "}";
    }
}
