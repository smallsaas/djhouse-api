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
@TableName("t_house_property_building_unit")
@ApiModel(value="HousePropertyBuildingUnit对象", description="")
public class HousePropertyBuildingUnit extends Model<HousePropertyBuildingUnit> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "楼栋id")
      private Long buildingId;

      @ApiModelProperty(value = "房产编号")
      private String number;

    
    public Long getId() {
        return id;
    }

      public HousePropertyBuildingUnit setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getBuildingId() {
        return buildingId;
    }

      public HousePropertyBuildingUnit setBuildingId(Long buildingId) {
          this.buildingId = buildingId;
          return this;
      }
    
    public String getNumber() {
        return number;
    }

      public HousePropertyBuildingUnit setNumber(String number) {
          this.number = number;
          return this;
      }

      public static final String ID = "id";

      public static final String BUILDING_ID = "building_id";

      public static final String NUMBER = "number";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HousePropertyBuildingUnit{" +
              "id=" + id +
                  ", buildingId=" + buildingId +
                  ", number=" + number +
              "}";
    }
}
