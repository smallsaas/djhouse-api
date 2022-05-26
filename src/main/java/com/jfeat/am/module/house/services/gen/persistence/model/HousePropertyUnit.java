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
 * @since 2022-05-23
 */
@TableName("t_house_property_unit")
@ApiModel(value="HousePropertyUnit对象", description="")
public class HousePropertyUnit extends Model<HousePropertyUnit> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "用户id")
      private Long userId;

      @ApiModelProperty(value = "楼栋id")
      private Long buildingId;

      @ApiModelProperty(value = "房产编号")
      private String number;

    
    public Long getId() {
        return id;
    }

      public HousePropertyUnit setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getUserId() {
        return userId;
    }

      public HousePropertyUnit setUserId(Long userId) {
          this.userId = userId;
          return this;
      }
    
    public Long getBuildingId() {
        return buildingId;
    }

      public HousePropertyUnit setBuildingId(Long buildingId) {
          this.buildingId = buildingId;
          return this;
      }
    
    public String getNumber() {
        return number;
    }

      public HousePropertyUnit setNumber(String number) {
          this.number = number;
          return this;
      }

      public static final String ID = "id";

      public static final String USER_ID = "user_id";

      public static final String BUILDING_ID = "building_id";

      public static final String NUMBER = "number";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HousePropertyUnit{" +
              "id=" + id +
                  ", userId=" + userId +
                  ", buildingId=" + buildingId +
                  ", number=" + number +
              "}";
    }
}
