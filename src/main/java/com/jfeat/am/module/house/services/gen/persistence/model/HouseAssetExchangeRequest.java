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
