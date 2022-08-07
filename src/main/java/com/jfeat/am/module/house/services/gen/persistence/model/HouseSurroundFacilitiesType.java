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
 * @since 2022-08-05
 */
@TableName("t_house_surround_facilities_type")
@ApiModel(value="HouseSurroundFacilitiesType对象", description="")
public class HouseSurroundFacilitiesType extends Model<HouseSurroundFacilitiesType> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "小区")
      private Long communityId;

      @ApiModelProperty(value = "英文名")
      private String enName;

      @ApiModelProperty(value = "中文名")
      private String cnName;

      @ApiModelProperty(value = "图标")
      private String icon;

    
    public Long getId() {
        return id;
    }

      public HouseSurroundFacilitiesType setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getCommunityId() {
        return communityId;
    }

      public HouseSurroundFacilitiesType setCommunityId(Long communityId) {
          this.communityId = communityId;
          return this;
      }
    
    public String getEnName() {
        return enName;
    }

      public HouseSurroundFacilitiesType setEnName(String enName) {
          this.enName = enName;
          return this;
      }
    
    public String getCnName() {
        return cnName;
    }

      public HouseSurroundFacilitiesType setCnName(String cnName) {
          this.cnName = cnName;
          return this;
      }
    
    public String getIcon() {
        return icon;
    }

      public HouseSurroundFacilitiesType setIcon(String icon) {
          this.icon = icon;
          return this;
      }

      public static final String ID = "id";

      public static final String COMMUNITY_ID = "community_id";

      public static final String EN_NAME = "en_name";

      public static final String CN_NAME = "cn_name";

      public static final String ICON = "icon";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseSurroundFacilitiesType{" +
              "id=" + id +
                  ", communityId=" + communityId +
                  ", enName=" + enName +
                  ", cnName=" + cnName +
                  ", icon=" + icon +
              "}";
    }
}
