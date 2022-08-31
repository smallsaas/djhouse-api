package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-08-29
 */
@TableName("t_house_user_tag_relate")
@ApiModel(value="HouseUserTagRelate对象", description="")
public class HouseUserTagRelate extends Model<HouseUserTagRelate> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "tagId")
      private Long tagId;

      @ApiModelProperty(value = "用户id")
      private Long userId;

      @ApiModelProperty(value = "销售id")
      private Long salesId;

      @ApiModelProperty(value = "创建时间")
      private Date createTime;

      @ApiModelProperty(value = "更新时间")
      private Date updateTime;

      @TableField(exist = false)
      private String tagName;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Long getId() {
        return id;
    }

      public HouseUserTagRelate setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getTagId() {
        return tagId;
    }

      public HouseUserTagRelate setTagId(Long tagId) {
          this.tagId = tagId;
          return this;
      }
    
    public Long getUserId() {
        return userId;
    }

      public HouseUserTagRelate setUserId(Long userId) {
          this.userId = userId;
          return this;
      }
    
    public Long getSalesId() {
        return salesId;
    }

      public HouseUserTagRelate setSalesId(Long salesId) {
          this.salesId = salesId;
          return this;
      }
    
    public Date getCreateTime() {
        return createTime;
    }

      public HouseUserTagRelate setCreateTime(Date createTime) {
          this.createTime = createTime;
          return this;
      }
    
    public Date getUpdateTime() {
        return updateTime;
    }

      public HouseUserTagRelate setUpdateTime(Date updateTime) {
          this.updateTime = updateTime;
          return this;
      }

      public static final String ID = "id";

      public static final String TAG_ID = "tag_id";

      public static final String USER_ID = "user_id";

      public static final String SALES_ID = "sales_id";

      public static final String CREATE_TIME = "create_time";

      public static final String UPDATE_TIME = "update_time";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseUserTagRelate{" +
              "id=" + id +
                  ", tagId=" + tagId +
                  ", userId=" + userId +
                  ", salesId=" + salesId +
                  ", createTime=" + createTime +
                  ", updateTime=" + updateTime +
              "}";
    }
}
