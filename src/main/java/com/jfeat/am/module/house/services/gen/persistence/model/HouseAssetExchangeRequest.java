package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;

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
@TableName("t_house_asset_exchange_request")
@ApiModel(value="HouseAssetExchangeRequest对象", description="")
public class HouseAssetExchangeRequest extends Model<HouseAssetExchangeRequest> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "用户id")
      private Long userId;

      @ApiModelProperty(value = "资产id")
      private Long assetId;

      @ApiModelProperty(value = "目标资产范围")
      private String targetAssetRange;

      @ApiModelProperty(value = "目标资产范围限制")
      private String targetAssetRangeLimit;


    @ApiModelProperty(value = "社区Id")
    @TableField(exist = false)
    private Long communityId;

    @ApiModelProperty(value = "社区名")
    @TableField(exist = false)
    private String communityName;

    @ApiModelProperty(value = "楼栋编号")
    @TableField(exist = false)
    private String buildingCode;

    @ApiModelProperty(value = "第几楼陈")
    @TableField(exist = false)
    private Integer floor;

    @TableField(exist = false)
    private String number;

    @ApiModelProperty(value = "地址")
    @TableField(exist = false)
    private String address;

    @TableField(exist = false)
    private String cadPicture;

    @TableField(exist = false)
    private Long designModelId;

    @TableField(exist = false)
    private String houseType;

    @TableField(exist = false)
    private String houseTypePicture;

    @TableField(exist = false)
    private String vrPicture;

    @TableField(exist = false)
    private String vrLink;

    @TableField(exist = false)
    private String vrSnapshot;

    @TableField(exist = false)
    private BigDecimal area;

    private String targetRangeStr;

    @ApiModelProperty("用户名")
    @TableField(exist = false)
    private String username;

    @ApiModelProperty("用户电话")
    @TableField(exist = false)
    private String userPhone;

    @ApiModelProperty("用户头像")
    @TableField(exist = false)
    private String userAvatar;

    public Long getCommunityId() {
        return communityId;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
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

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCadPicture() {
        return cadPicture;
    }

    public void setCadPicture(String cadPicture) {
        this.cadPicture = cadPicture;
    }

    public Long getDesignModelId() {
        return designModelId;
    }

    public void setDesignModelId(Long designModelId) {
        this.designModelId = designModelId;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getHouseTypePicture() {
        return houseTypePicture;
    }

    public void setHouseTypePicture(String houseTypePicture) {
        this.houseTypePicture = houseTypePicture;
    }

    public String getVrPicture() {
        return vrPicture;
    }

    public void setVrPicture(String vrPicture) {
        this.vrPicture = vrPicture;
    }

    public String getVrLink() {
        return vrLink;
    }

    public void setVrLink(String vrLink) {
        this.vrLink = vrLink;
    }

    public String getVrSnapshot() {
        return vrSnapshot;
    }

    public void setVrSnapshot(String vrSnapshot) {
        this.vrSnapshot = vrSnapshot;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTargetRangeStr() {
        return targetRangeStr;
    }

    public void setTargetRangeStr(String targetRangeStr) {
        this.targetRangeStr = targetRangeStr;
    }

    public Long getId() {
        return id;
    }

      public HouseAssetExchangeRequest setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getUserId() {
        return userId;
    }

      public HouseAssetExchangeRequest setUserId(Long userId) {
          this.userId = userId;
          return this;
      }
    
    public Long getAssetId() {
        return assetId;
    }

      public HouseAssetExchangeRequest setAssetId(Long assetId) {
          this.assetId = assetId;
          return this;
      }
    
    public String getTargetAssetRange() {
        return targetAssetRange;
    }

      public HouseAssetExchangeRequest setTargetAssetRange(String targetAssetRange) {
          this.targetAssetRange = targetAssetRange;
          return this;
      }
    
    public String getTargetAssetRangeLimit() {
        return targetAssetRangeLimit;
    }

      public HouseAssetExchangeRequest setTargetAssetRangeLimit(String targetAssetRangeLimit) {
          this.targetAssetRangeLimit = targetAssetRangeLimit;
          return this;
      }

      public static final String ID = "id";

      public static final String USER_ID = "user_id";

      public static final String ASSET_ID = "asset_id";

      public static final String TARGET_ASSET_RANGE = "target_asset_range";

      public static final String TARGET_ASSET_RANGE_LIMIT = "target_asset_range_limit";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseAssetExchangeRequest{" +
              "id=" + id +
                  ", userId=" + userId +
                  ", assetId=" + assetId +
                  ", targetAssetRange=" + targetAssetRange +
                  ", targetAssetRangeLimit=" + targetAssetRangeLimit +
              "}";
    }
}
