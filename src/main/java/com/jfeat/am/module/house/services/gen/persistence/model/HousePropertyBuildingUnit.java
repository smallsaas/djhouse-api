package com.jfeat.am.module.house.services.gen.persistence.model;

import java.math.BigDecimal;
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
 * @since 2022-06-06
 */
@TableName("t_house_property_building_unit")
@ApiModel(value="HousePropertyBuildingUnit对象", description="")
public class HousePropertyBuildingUnit extends Model<HousePropertyBuildingUnit> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "楼栋id")
      private Long buildingId;

      @ApiModelProperty(value = "单元编号")
      private String unitCode;

      @ApiModelProperty(value = "户型")
      private String houseType;

      @ApiModelProperty(value = "户型图")
      private String houseTypePicture;

      @ApiModelProperty(value = "面积")
      private BigDecimal area;

    
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
    
    public String getUnitCode() {
        return unitCode;
    }

      public HousePropertyBuildingUnit setUnitCode(String unitCode) {
          this.unitCode = unitCode;
          return this;
      }
    
    public String getHouseType() {
        return houseType;
    }

      public HousePropertyBuildingUnit setHouseType(String houseType) {
          this.houseType = houseType;
          return this;
      }
    
    public String getHouseTypePicture() {
        return houseTypePicture;
    }

      public HousePropertyBuildingUnit setHouseTypePicture(String houseTypePicture) {
          this.houseTypePicture = houseTypePicture;
          return this;
      }
    
    public BigDecimal getArea() {
        return area;
    }

      public HousePropertyBuildingUnit setArea(BigDecimal area) {
          this.area = area;
          return this;
      }

      public static final String ID = "id";

      public static final String BUILDING_ID = "building_id";

      public static final String UNIT_CODE = "unit_code";

      public static final String HOUSE_TYPE = "house_type";

      public static final String HOUSE_TYPE_PICTURE = "house_type_picture";

      public static final String AREA = "area";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HousePropertyBuildingUnit{" +
              "id=" + id +
                  ", buildingId=" + buildingId +
                  ", unitCode=" + unitCode +
                  ", houseType=" + houseType +
                  ", houseTypePicture=" + houseTypePicture +
                  ", area=" + area +
              "}";
    }
}
