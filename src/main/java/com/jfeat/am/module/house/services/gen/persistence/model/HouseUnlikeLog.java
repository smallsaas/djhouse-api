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
 * @since 2022-09-19
 */
@TableName("t_house_unlike_log")
@ApiModel(value="HouseUnlikeLog对象", description="")
public class HouseUnlikeLog extends Model<HouseUnlikeLog> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "用户id")
      private Long userId;

      @ApiModelProperty(value = "点击了不喜欢的房子id")
      private Long assetId;

      @ApiModelProperty(value = "创建时间")
      private Date createTime;

      @ApiModelProperty(value = "更新时间")
      private Date updateTime;

    
    public Long getId() {
        return id;
    }

      public HouseUnlikeLog setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getUserId() {
        return userId;
    }

      public HouseUnlikeLog setUserId(Long userId) {
          this.userId = userId;
          return this;
      }
    
    public Long getAssetId() {
        return assetId;
    }

      public HouseUnlikeLog setAssetId(Long assetId) {
          this.assetId = assetId;
          return this;
      }
    
    public Date getCreateTime() {
        return createTime;
    }

      public HouseUnlikeLog setCreateTime(Date createTime) {
          this.createTime = createTime;
          return this;
      }
    
    public Date getUpdateTime() {
        return updateTime;
    }

      public HouseUnlikeLog setUpdateTime(Date updateTime) {
          this.updateTime = updateTime;
          return this;
      }

      public static final String ID = "id";

      public static final String USER_ID = "user_id";

      public static final String ASSET_ID = "asset_id";

      public static final String CREATE_TIME = "create_time";

      public static final String UPDATE_TIME = "update_time";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseUnlikeLog{" +
              "id=" + id +
                  ", userId=" + userId +
                  ", assetId=" + assetId +
                  ", createTime=" + createTime +
                  ", updateTime=" + updateTime +
              "}";
    }
}
