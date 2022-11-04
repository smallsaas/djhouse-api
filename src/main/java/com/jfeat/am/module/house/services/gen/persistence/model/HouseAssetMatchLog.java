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
 * @since 2022-06-11
 */
@TableName("t_house_asset_match_log")
@ApiModel(value = "HouseAssetMatchLog对象", description = "")
public class HouseAssetMatchLog extends Model<HouseAssetMatchLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "物主用户id")
    private Long ownerUserId;

    @ApiModelProperty(value = "物主用户的资产id")
    private Long ownerAssetId;


    @ApiModelProperty(value = "匹配到的用户id")
    private Long matchedUserId;


    @ApiModelProperty(value = "匹配到的用户资产id")
    private Long mathchedAssetId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "1已联系 2-未设置 3-完成")
    private Integer status;

    private Long orgId;

    @ApiModelProperty(value = "物主名字")
    @TableField(exist = false)
    private String ownerName;

    @ApiModelProperty(value = "物主电话")
    @TableField(exist = false)
    private String ownerPhone;

    @TableField(exist = false)
    private String ownerEmail;

    @TableField(exist = false)
    private String ownerAvatar;

    @TableField(exist = false)
    private String ownerCommunity;

    @TableField(exist = false)
    private String ownerBuilding;

    @TableField(exist = false)
    private String ownerNumber;

    @TableField(exist = false)
    private String ownerRealName;

    @ApiModelProperty(value = "物主名字")
    @TableField(exist = false)
    private String matchedName;

    @ApiModelProperty(value = "物主电话")
    @TableField(exist = false)
    private String matchedPhone;

    @TableField(exist = false)
    private String matchedEmail;

    @TableField(exist = false)
    private String matchedAvatar;

    @TableField(exist = false)
    private String matchedCommunity;

    @TableField(exist = false)
    private String matchedBuilding;

    @TableField(exist = false)
    private String matchedNumber;

    @TableField(exist = false)
    private String matchedRealName;

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getMatchedEmail() {
        return matchedEmail;
    }

    public void setMatchedEmail(String matchedEmail) {
        this.matchedEmail = matchedEmail;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOwnerRealName() {
        return ownerRealName;
    }

    public void setOwnerRealName(String ownerRealName) {
        this.ownerRealName = ownerRealName;
    }

    public String getMatchedRealName() {
        return matchedRealName;
    }

    public void setMatchedRealName(String matchedRealName) {
        this.matchedRealName = matchedRealName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getId() {
        return id;
    }

    public HouseAssetMatchLog setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public HouseAssetMatchLog setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
        return this;
    }

    public Long getOwnerAssetId() {
        return ownerAssetId;
    }

    public HouseAssetMatchLog setOwnerAssetId(Long ownerAssetId) {
        this.ownerAssetId = ownerAssetId;
        return this;
    }

    public Long getMatchedUserId() {
        return matchedUserId;
    }

    public HouseAssetMatchLog setMatchedUserId(Long matchedUserId) {
        this.matchedUserId = matchedUserId;
        return this;
    }

    public Long getMathchedAssetId() {
        return mathchedAssetId;
    }

    public HouseAssetMatchLog setMathchedAssetId(Long mathchedAssetId) {
        this.mathchedAssetId = mathchedAssetId;
        return this;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getOwnerCommunity() {
        return ownerCommunity;
    }

    public void setOwnerCommunity(String ownerCommunity) {
        this.ownerCommunity = ownerCommunity;
    }

    public String getOwnerBuilding() {
        return ownerBuilding;
    }

    public void setOwnerBuilding(String ownerBuilding) {
        this.ownerBuilding = ownerBuilding;
    }

    public String getOwnerNumber() {
        return ownerNumber;
    }

    public void setOwnerNumber(String ownerNumber) {
        this.ownerNumber = ownerNumber;
    }

    public String getMatchedName() {
        return matchedName;
    }

    public void setMatchedName(String matchedName) {
        this.matchedName = matchedName;
    }

    public String getMatchedPhone() {
        return matchedPhone;
    }

    public void setMatchedPhone(String matchedPhone) {
        this.matchedPhone = matchedPhone;
    }

    public String getMatchedCommunity() {
        return matchedCommunity;
    }

    public void setMatchedCommunity(String matchedCommunity) {
        this.matchedCommunity = matchedCommunity;
    }

    public String getMatchedBuilding() {
        return matchedBuilding;
    }

    public void setMatchedBuilding(String matchedBuilding) {
        this.matchedBuilding = matchedBuilding;
    }

    public String getMatchedNumber() {
        return matchedNumber;
    }

    public void setMatchedNumber(String matchedNumber) {
        this.matchedNumber = matchedNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public HouseAssetMatchLog setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getOwnerAvatar() {
        return ownerAvatar;
    }

    public void setOwnerAvatar(String ownerAvatar) {
        this.ownerAvatar = ownerAvatar;
    }

    public String getMatchedAvatar() {
        return matchedAvatar;
    }

    public void setMatchedAvatar(String matchedAvatar) {
        this.matchedAvatar = matchedAvatar;
    }

    public static final String ID = "id";

    public static final String OWNER_USER_ID = "owner_user_id";

    public static final String OWNER_ASSET_ID = "owner_asset_id";

    public static final String MATCHED_USER_ID = "matched_user_id";

    public static final String MATHCHED_ASSET_ID = "mathched_asset_id";

    public static final String CREATE_TIME = "create_time";

    public static final String STATUS = "status";

    public static final Integer STATUS_CONTACT =2;

    public static final Integer STATUS_COMPLETE =3;

    public static final Integer STATUS_NOT_SET =1;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseAssetMatchLog{" +
                "id=" + id +
                ", ownerUserId=" + ownerUserId +
                ", ownerAssetId=" + ownerAssetId +
                ", matchedUserId=" + matchedUserId +
                ", mathchedAssetId=" + mathchedAssetId +
                ", createTime=" + createTime +
                "}";
    }
}
