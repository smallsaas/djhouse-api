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
 * @since 2022-08-08
 */
@TableName("t_house_rent_support_facilities")
@ApiModel(value="HouseRentSupportFacilities对象", description="")
public class HouseRentSupportFacilities extends Model<HouseRentSupportFacilities> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "设施id")
      private Long facilitiesId;

      @ApiModelProperty(value = "房屋id")
      private Long assetId;

    
    public Long getId() {
        return id;
    }

      public HouseRentSupportFacilities setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getFacilitiesId() {
        return facilitiesId;
    }

      public HouseRentSupportFacilities setFacilitiesId(Long facilitiesId) {
          this.facilitiesId = facilitiesId;
          return this;
      }
    
    public Long getAssetId() {
        return assetId;
    }

      public HouseRentSupportFacilities setAssetId(Long assetId) {
          this.assetId = assetId;
          return this;
      }

      public static final String ID = "id";

      public static final String FACILITIES_ID = "facilities_id";

      public static final String ASSET_ID = "asset_id";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseRentSupportFacilities{" +
              "id=" + id +
                  ", facilitiesId=" + facilitiesId +
                  ", assetId=" + assetId +
              "}";
    }
}
