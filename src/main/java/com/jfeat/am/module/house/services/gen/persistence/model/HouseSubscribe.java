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
 * @since 2022-08-24
 */
@TableName("t_house_subscribe")
@ApiModel(value="HouseSubscribe对象", description="")
public class HouseSubscribe extends Model<HouseSubscribe> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "用户id")
      private Long userId;

      @ApiModelProperty(value = "订阅id")
      private Long subscribeId;

      @ApiModelProperty(value = "创建时间")
      private Date createTime;

    
    public Long getId() {
        return id;
    }

      public HouseSubscribe setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getUserId() {
        return userId;
    }

      public HouseSubscribe setUserId(Long userId) {
          this.userId = userId;
          return this;
      }
    
    public Long getSubscribeId() {
        return subscribeId;
    }

      public HouseSubscribe setSubscribeId(Long subscribeId) {
          this.subscribeId = subscribeId;
          return this;
      }
    
    public Date getCreateTime() {
        return createTime;
    }

      public HouseSubscribe setCreateTime(Date createTime) {
          this.createTime = createTime;
          return this;
      }

      public static final String ID = "id";

      public static final String USER_ID = "user_id";

      public static final String SUBSCRIBE_ID = "subscribe_id";

      public static final String CREATE_TIME = "create_time";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseSubscribe{" +
              "id=" + id +
                  ", userId=" + userId +
                  ", subscribeId=" + subscribeId +
                  ", createTime=" + createTime +
              "}";
    }
}
