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
 * @since 2022-07-01
 */
@TableName("t_house_user_decorate_funiture")
@ApiModel(value="HouseUserDecorateFuniture对象", description="")
public class HouseUserDecorateFuniture extends Model<HouseUserDecorateFuniture> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "用户id")
      private Long userId;

      @ApiModelProperty(value = "装修计划id")
      private Long decoratePlanId;

      @ApiModelProperty(value = "家居id")
      private Long funitureId;

    @ApiModelProperty(value = "装修地址")
    private Long assetId;

      @ApiModelProperty(value = "家居数量")
      private Integer funitureNumber;



      @ApiModelProperty(value = "创建时间")
      private Date createTime;



    
    public Long getId() {
        return id;
    }

      public HouseUserDecorateFuniture setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getUserId() {
        return userId;
    }

      public HouseUserDecorateFuniture setUserId(Long userId) {
          this.userId = userId;
          return this;
      }
    
    public Long getDecoratePlanId() {
        return decoratePlanId;
    }

      public HouseUserDecorateFuniture setDecoratePlanId(Long decoratePlanId) {
          this.decoratePlanId = decoratePlanId;
          return this;
      }
    
    public Long getFunitureId() {
        return funitureId;
    }

      public HouseUserDecorateFuniture setFunitureId(Long funitureId) {
          this.funitureId = funitureId;
          return this;
      }

    public Integer getFunitureNumber() {
        return funitureNumber;
    }

    public void setFunitureNumber(Integer funitureNumber) {
        this.funitureNumber = funitureNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

      public HouseUserDecorateFuniture setCreateTime(Date createTime) {
          this.createTime = createTime;
          return this;
      }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public static final String ID = "id";

      public static final String USER_ID = "user_id";

      public static final String DECORATE_PLAN_ID = "decorate_plan_id";

      public static final String FUNITURE_ID = "funiture_id";

      public static final String FUNITURE_NUMBER = "funiture_number";

      public static final String CREATE_TIME = "create_time";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseUserDecorateFuniture{" +
              "id=" + id +
                  ", userId=" + userId +
                  ", decoratePlanId=" + decoratePlanId +
                  ", funitureId=" + funitureId +
                  ", funitureNumber=" + funitureNumber +
                  ", createTime=" + createTime +
              "}";
    }
}
