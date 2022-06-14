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
 * @since 2022-06-11
 */
@TableName("t_house_asset")
@ApiModel(value="HouseAsset对象", description="")
public class HouseAsset extends Model<HouseAsset> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "楼栋id")
      private Long buildingId;

      @ApiModelProperty(value = "单元id")
      private Long unitId;

      @ApiModelProperty(value = "第几楼陈")
      private Integer floor;

      @ApiModelProperty(value = "房产编号")
      private String number;

      @ApiModelProperty(value = "资产卡位 备用")
      private String assetSlot;

      @ApiModelProperty(value = "资产类型")
      private String assetType;

      @ApiModelProperty(value = "资产类型id")
      private Long assetTypeId;

    @ApiModelProperty(value = "社区Id")
    @TableField(exist = false)
    private Long communityId;

    @ApiModelProperty(value = "社区名")
    @TableField(exist = false)
    private String communityName;

    @ApiModelProperty(value = "楼栋编号")
    @TableField(exist = false)
    private String buildingCode;

    @ApiModelProperty(value = "区域")
    @TableField(exist = false)
    private String buildingArea;

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

    public Long getId() {
        return id;
    }

      public HouseAsset setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getBuildingId() {
        return buildingId;
    }

      public HouseAsset setBuildingId(Long buildingId) {
          this.buildingId = buildingId;
          return this;
      }
    
    public Long getUnitId() {
        return unitId;
    }

      public HouseAsset setUnitId(Long unitId) {
          this.unitId = unitId;
          return this;
      }
    
    public Integer getFloor() {
        return floor;
    }

      public HouseAsset setFloor(Integer floor) {
          this.floor = floor;
          return this;
      }
    
    public String getNumber() {
        return number;
    }

      public HouseAsset setNumber(String number) {
          this.number = number;
          return this;
      }
    
    public String getAssetSlot() {
        return assetSlot;
    }

      public HouseAsset setAssetSlot(String assetSlot) {
          this.assetSlot = assetSlot;
          return this;
      }
    
    public String getAssetType() {
        return assetType;
    }

      public HouseAsset setAssetType(String assetType) {
          this.assetType = assetType;
          return this;
      }
    
    public Long getAssetTypeId() {
        return assetTypeId;
    }

      public HouseAsset setAssetTypeId(Long assetTypeId) {
          this.assetTypeId = assetTypeId;
          return this;
      }

      public static final String ID = "id";

      public static final String BUILDING_ID = "building_id";

      public static final String UNIT_ID = "unit_id";

      public static final String FLOOR = "floor";

      public static final String NUMBER = "number";

      public static final String ASSET_SLOT = "asset_slot";

      public static final String ASSET_TYPE = "asset_type";

      public static final String ASSET_TYPE_ID = "asset_type_id";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseAsset{" +
              "id=" + id +
                  ", buildingId=" + buildingId +
                  ", unitId=" + unitId +
                  ", floor=" + floor +
                  ", number=" + number +
                  ", assetSlot=" + assetSlot +
                  ", assetType=" + assetType +
                  ", assetTypeId=" + assetTypeId +
              "}";
    }
}
