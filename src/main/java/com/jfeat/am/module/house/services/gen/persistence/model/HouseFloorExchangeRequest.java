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
 * @since 2022-11-19
 */
@TableName("t_house_floor_exchange_request")
@ApiModel(value="HouseFloorExchangeRequest对象", description="")
public class HouseFloorExchangeRequest extends Model<HouseFloorExchangeRequest> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "用户id")
      private Long userId;

      @ApiModelProperty(value = "房屋id")
      private Long assetId;

      @ApiModelProperty(value = "楼层")
      private Integer floor;

      @ApiModelProperty(value = "楼上1 楼下为-1")
      private Integer floorState;

      @ApiModelProperty(value = "是否执行")
      private Integer stute;

      @ApiModelProperty(value = "创建时间")
      private Date createTime;

      @ApiModelProperty(value = "更新时间")
      private Date updateTime;

    
    public Long getId() {
        return id;
    }

      public HouseFloorExchangeRequest setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getUserId() {
        return userId;
    }

      public HouseFloorExchangeRequest setUserId(Long userId) {
          this.userId = userId;
          return this;
      }
    
    public Long getAssetId() {
        return assetId;
    }

      public HouseFloorExchangeRequest setAssetId(Long assetId) {
          this.assetId = assetId;
          return this;
      }
    
    public Integer getFloor() {
        return floor;
    }

      public HouseFloorExchangeRequest setFloor(Integer floor) {
          this.floor = floor;
          return this;
      }
    
    public Integer getFloorState() {
        return floorState;
    }

      public HouseFloorExchangeRequest setFloorState(Integer floorState) {
          this.floorState = floorState;
          return this;
      }
    
    public Integer getStute() {
        return stute;
    }

      public HouseFloorExchangeRequest setStute(Integer stute) {
          this.stute = stute;
          return this;
      }
    
    public Date getCreateTime() {
        return createTime;
    }

      public HouseFloorExchangeRequest setCreateTime(Date createTime) {
          this.createTime = createTime;
          return this;
      }
    
    public Date getUpdateTime() {
        return updateTime;
    }

      public HouseFloorExchangeRequest setUpdateTime(Date updateTime) {
          this.updateTime = updateTime;
          return this;
      }

      public static final String ID = "id";

      public static final String USER_ID = "user_id";

      public static final String ASSET_ID = "asset_id";

      public static final String FLOOR = "floor";

      public static final String FLOOR_STATE = "floor_state";

      public static final String STUTE = "stute";

      public static final String CREATE_TIME = "create_time";

      public static final String UPDATE_TIME = "update_time";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseFloorExchangeRequest{" +
              "id=" + id +
                  ", userId=" + userId +
                  ", assetId=" + assetId +
                  ", floor=" + floor +
                  ", floorState=" + floorState +
                  ", stute=" + stute +
                  ", createTime=" + createTime +
                  ", updateTime=" + updateTime +
              "}";
    }
}
