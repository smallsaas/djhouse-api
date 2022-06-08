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
 * @since 2022-06-07
 */
@TableName("t_house_user_community_status")
@ApiModel(value="HouseUserCommunityStatus对象", description="")
public class HouseUserCommunityStatus extends Model<HouseUserCommunityStatus> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "社区id")
      private Long communityId;

      @ApiModelProperty(value = "用户id")
      private Long userId;

    
    public Long getId() {
        return id;
    }

      public HouseUserCommunityStatus setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getCommunityId() {
        return communityId;
    }

      public HouseUserCommunityStatus setCommunityId(Long communityId) {
          this.communityId = communityId;
          return this;
      }
    
    public Long getUserId() {
        return userId;
    }

      public HouseUserCommunityStatus setUserId(Long userId) {
          this.userId = userId;
          return this;
      }

      public static final String ID = "id";

      public static final String COMMUNITY_ID = "community_id";

      public static final String USER_ID = "user_id";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseUserCommunityStatus{" +
              "id=" + id +
                  ", communityId=" + communityId +
                  ", userId=" + userId +
              "}";
    }
}
