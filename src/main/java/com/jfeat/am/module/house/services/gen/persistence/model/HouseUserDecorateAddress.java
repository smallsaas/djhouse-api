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
 * @since 2022-05-30
 */
@TableName("t_house_user_decorate_address")
@ApiModel(value="HouseUserDecorateAddress对象", description="")
public class HouseUserDecorateAddress extends Model<HouseUserDecorateAddress> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "用户id")
      private Long userId;

      @ApiModelProperty(value = "装修计划id")
      private Long decoratePlanId;

      @ApiModelProperty(value = "unitId")
      private Long unitId;

    
    public Long getId() {
        return id;
    }

      public HouseUserDecorateAddress setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getUserId() {
        return userId;
    }

      public HouseUserDecorateAddress setUserId(Long userId) {
          this.userId = userId;
          return this;
      }
    
    public Long getDecoratePlanId() {
        return decoratePlanId;
    }

      public HouseUserDecorateAddress setDecoratePlanId(Long decoratePlanId) {
          this.decoratePlanId = decoratePlanId;
          return this;
      }
    
    public Long getUnitId() {
        return unitId;
    }

      public HouseUserDecorateAddress setUnitId(Long unitId) {
          this.unitId = unitId;
          return this;
      }

      public static final String ID = "id";

      public static final String USER_ID = "user_id";

      public static final String DECORATE_PLAN_ID = "decorate_plan_id";

      public static final String UNIT_ID = "unit_id";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseUserDecorateAddress{" +
              "id=" + id +
                  ", userId=" + userId +
                  ", decoratePlanId=" + decoratePlanId +
                  ", unitId=" + unitId +
              "}";
    }
}
