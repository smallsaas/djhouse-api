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
 * @since 2022-08-05
 */
@TableName("t_house_surround_facilities")
@ApiModel(value="HouseSurroundFacilities对象", description="")
public class HouseSurroundFacilities extends Model<HouseSurroundFacilities> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "类型id")
      private Long typeId;

      @ApiModelProperty(value = "标题")
      private String title;

      @ApiModelProperty(value = "图标")
      private String icon;

      @ApiModelProperty(value = "直线距离 单位km")
      private BigDecimal distance;

    
    public Long getId() {
        return id;
    }

      public HouseSurroundFacilities setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getTypeId() {
        return typeId;
    }

      public HouseSurroundFacilities setTypeId(Long typeId) {
          this.typeId = typeId;
          return this;
      }
    
    public String getTitle() {
        return title;
    }

      public HouseSurroundFacilities setTitle(String title) {
          this.title = title;
          return this;
      }
    
    public String getIcon() {
        return icon;
    }

      public HouseSurroundFacilities setIcon(String icon) {
          this.icon = icon;
          return this;
      }
    
    public BigDecimal getDistance() {
        return distance;
    }

      public HouseSurroundFacilities setDistance(BigDecimal distance) {
          this.distance = distance;
          return this;
      }

      public static final String ID = "id";

      public static final String TYPE_ID = "type_id";

      public static final String TITLE = "title";

      public static final String ICON = "icon";

      public static final String DISTANCE = "distance";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseSurroundFacilities{" +
              "id=" + id +
                  ", typeId=" + typeId +
                  ", title=" + title +
                  ", icon=" + icon +
                  ", distance=" + distance +
              "}";
    }
}
