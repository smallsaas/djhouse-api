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
 * @since 2022-05-27
 */
@TableName("t_house_vr_picture")
@ApiModel(value="HouseVrPicture对象", description="")
public class HouseVrPicture extends Model<HouseVrPicture> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "装修计划id")
      private Long decoratePlanId;

      @ApiModelProperty(value = "vr图名")
      private String name;

      @ApiModelProperty(value = "vr图地址")
      private String vrAddress;

    
    public Long getId() {
        return id;
    }

      public HouseVrPicture setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getDecoratePlanId() {
        return decoratePlanId;
    }

      public HouseVrPicture setDecoratePlanId(Long decoratePlanId) {
          this.decoratePlanId = decoratePlanId;
          return this;
      }
    
    public String getName() {
        return name;
    }

      public HouseVrPicture setName(String name) {
          this.name = name;
          return this;
      }
    
    public String getVrAddress() {
        return vrAddress;
    }

      public HouseVrPicture setVrAddress(String vrAddress) {
          this.vrAddress = vrAddress;
          return this;
      }

      public static final String ID = "id";

      public static final String DECORATE_PLAN_ID = "decorate_plan_id";

      public static final String NAME = "name";

      public static final String VR_ADDRESS = "vr_address";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseVrPicture{" +
              "id=" + id +
                  ", decoratePlanId=" + decoratePlanId +
                  ", name=" + name +
                  ", vrAddress=" + vrAddress +
              "}";
    }
}
