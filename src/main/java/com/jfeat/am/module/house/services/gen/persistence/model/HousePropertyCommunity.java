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
 * @since 2022-06-01
 */
@TableName("t_house_property_community")
@ApiModel(value="HousePropertyCommunity对象", description="")
public class HousePropertyCommunity extends Model<HousePropertyCommunity> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "社区")
      private String community;

      @ApiModelProperty(value = "社区编号")
      private String communityCode;

    
    public Long getId() {
        return id;
    }

      public HousePropertyCommunity setId(Long id) {
          this.id = id;
          return this;
      }
    
    public String getCommunity() {
        return community;
    }

      public HousePropertyCommunity setCommunity(String community) {
          this.community = community;
          return this;
      }
    
    public String getCommunityCode() {
        return communityCode;
    }

      public HousePropertyCommunity setCommunityCode(String communityCode) {
          this.communityCode = communityCode;
          return this;
      }

      public static final String ID = "id";

      public static final String COMMUNITY = "community";

      public static final String COMMUNITY_CODE = "community_code";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HousePropertyCommunity{" +
              "id=" + id +
                  ", community=" + community +
                  ", communityCode=" + communityCode +
              "}";
    }
}
