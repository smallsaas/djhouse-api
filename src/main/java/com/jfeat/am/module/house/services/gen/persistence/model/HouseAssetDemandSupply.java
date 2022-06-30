package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author Code generator
 * @since 2022-06-29
 */
@TableName("t_house_asset_demand_supply")
@ApiModel(value="HouseAssetDemandSupply对象", description="")
public class HouseAssetDemandSupply extends Model<HouseAssetDemandSupply> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "用户id")
      private Long userId;

      @ApiModelProperty(value = "资产id")
      private Long assetId;

      @ApiModelProperty(value = "买卖，1：买 2：卖")
      private Integer assetOption;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "户型id")
    private Long designModelId;


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

      public HouseAssetDemandSupply setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getUserId() {
        return userId;
    }

      public HouseAssetDemandSupply setUserId(Long userId) {
          this.userId = userId;
          return this;
      }
    
    public Long getAssetId() {
        return assetId;
    }

      public HouseAssetDemandSupply setAssetId(Long assetId) {
          this.assetId = assetId;
          return this;
      }
    
    public Integer getAssetOption() {
        return assetOption;
    }

      public HouseAssetDemandSupply setAssetOption(Integer assetOption) {
          this.assetOption = assetOption;
          return this;
      }

    public Long getDesignModelId() {
        return designModelId;
    }

    public void setDesignModelId(Long designModelId) {
        this.designModelId = designModelId;
    }

    public static final String ID = "id";

      public static final String USER_ID = "user_id";

      public static final String ASSET_ID = "asset_id";

      public static final String ASSET_OPTION = "asset_option";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseAssetDemandSupply{" +
              "id=" + id +
                  ", userId=" + userId +
                  ", assetId=" + assetId +
                  ", assetOption=" + assetOption +
              "}";
    }
}
