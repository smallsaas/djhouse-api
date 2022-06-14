package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
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
 * @since 2022-06-11
 */
@TableName("t_house_asset_match_log")
@ApiModel(value="HouseAssetMatchLog对象", description="")
public class HouseAssetMatchLog extends Model<HouseAssetMatchLog> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "物主用户id")
      private Long ownerUserId;

      @ApiModelProperty(value = "物主用户的资产id")
      private Long ownerAssetId;

      @ApiModelProperty(value = "匹配到的用户id")
      private Long matchedUserId;

      @ApiModelProperty(value = "匹配到的用户资产id")
      private Long mathchedAssetId;

      @ApiModelProperty(value = "创建时间")
      private Date createTime;

    
    public Long getId() {
        return id;
    }

      public HouseAssetMatchLog setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getOwnerUserId() {
        return ownerUserId;
    }

      public HouseAssetMatchLog setOwnerUserId(Long ownerUserId) {
          this.ownerUserId = ownerUserId;
          return this;
      }
    
    public Long getOwnerAssetId() {
        return ownerAssetId;
    }

      public HouseAssetMatchLog setOwnerAssetId(Long ownerAssetId) {
          this.ownerAssetId = ownerAssetId;
          return this;
      }
    
    public Long getMatchedUserId() {
        return matchedUserId;
    }

      public HouseAssetMatchLog setMatchedUserId(Long matchedUserId) {
          this.matchedUserId = matchedUserId;
          return this;
      }
    
    public Long getMathchedAssetId() {
        return mathchedAssetId;
    }

      public HouseAssetMatchLog setMathchedAssetId(Long mathchedAssetId) {
          this.mathchedAssetId = mathchedAssetId;
          return this;
      }
    
    public Date getCreateTime() {
        return createTime;
    }

      public HouseAssetMatchLog setCreateTime(Date createTime) {
          this.createTime = createTime;
          return this;
      }

      public static final String ID = "id";

      public static final String OWNER_USER_ID = "owner_user_id";

      public static final String OWNER_ASSET_ID = "owner_asset_id";

      public static final String MATCHED_USER_ID = "matched_user_id";

      public static final String MATHCHED_ASSET_ID = "mathched_asset_id";

      public static final String CREATE_TIME = "create_time";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseAssetMatchLog{" +
              "id=" + id +
                  ", ownerUserId=" + ownerUserId +
                  ", ownerAssetId=" + ownerAssetId +
                  ", matchedUserId=" + matchedUserId +
                  ", mathchedAssetId=" + mathchedAssetId +
                  ", createTime=" + createTime +
              "}";
    }
}
