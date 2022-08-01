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
 * @since 2022-08-01
 */
@TableName("t_house_user_address")
@ApiModel(value="HouseUserAddress对象", description="")
public class HouseUserAddress extends Model<HouseUserAddress> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "用户id")
      private Long userId;

      @ApiModelProperty(value = "资产id")
      private Long assetId;

      @ApiModelProperty(value = "小区")
      private String community;

      @ApiModelProperty(value = "楼栋号")
      private String buildingCode;

      @ApiModelProperty(value = "地址")
      private String address;

      @ApiModelProperty(value = "房号")
      private String roomNumber;

      @ApiModelProperty(value = "用户名")
      private String userName;

      @ApiModelProperty(value = "用户电话")
      private String userPhone;

    
    public Long getId() {
        return id;
    }

      public HouseUserAddress setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getUserId() {
        return userId;
    }

      public HouseUserAddress setUserId(Long userId) {
          this.userId = userId;
          return this;
      }
    
    public Long getAssetId() {
        return assetId;
    }

      public HouseUserAddress setAssetId(Long assetId) {
          this.assetId = assetId;
          return this;
      }
    
    public String getCommunity() {
        return community;
    }

      public HouseUserAddress setCommunity(String community) {
          this.community = community;
          return this;
      }
    
    public String getBuildingCode() {
        return buildingCode;
    }

      public HouseUserAddress setBuildingCode(String buildingCode) {
          this.buildingCode = buildingCode;
          return this;
      }
    
    public String getAddress() {
        return address;
    }

      public HouseUserAddress setAddress(String address) {
          this.address = address;
          return this;
      }
    
    public String getRoomNumber() {
        return roomNumber;
    }

      public HouseUserAddress setRoomNumber(String roomNumber) {
          this.roomNumber = roomNumber;
          return this;
      }
    
    public String getUserName() {
        return userName;
    }

      public HouseUserAddress setUserName(String userName) {
          this.userName = userName;
          return this;
      }
    
    public String getUserPhone() {
        return userPhone;
    }

      public HouseUserAddress setUserPhone(String userPhone) {
          this.userPhone = userPhone;
          return this;
      }

      public static final String ID = "id";

      public static final String USER_ID = "user_id";

      public static final String ASSET_ID = "asset_id";

      public static final String COMMUNITY = "community";

      public static final String BUILDING_CODE = "building_code";

      public static final String ADDRESS = "address";

      public static final String ROOM_NUMBER = "room_number";

      public static final String USER_NAME = "user_name";

      public static final String USER_PHONE = "user_phone";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseUserAddress{" +
              "id=" + id +
                  ", userId=" + userId +
                  ", assetId=" + assetId +
                  ", community=" + community +
                  ", buildingCode=" + buildingCode +
                  ", address=" + address +
                  ", roomNumber=" + roomNumber +
                  ", userName=" + userName +
                  ", userPhone=" + userPhone +
              "}";
    }
}
