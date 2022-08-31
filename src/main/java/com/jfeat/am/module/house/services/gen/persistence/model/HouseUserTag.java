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
 * @since 2022-08-29
 */
@TableName("t_house_user_tag")
@ApiModel(value="HouseUserTag对象", description="")
public class HouseUserTag extends Model<HouseUserTag> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "orgId")
      private Long orgId;

      @ApiModelProperty(value = "销售id")
      private Long salesId;

      @ApiModelProperty(value = "tag名称")
      private String tagName;

      @ApiModelProperty(value = "创建时间")
      private Date createTime;

      @ApiModelProperty(value = "更新时间")
      private Date updateTime;

    
    public Long getId() {
        return id;
    }

      public HouseUserTag setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getOrgId() {
        return orgId;
    }

      public HouseUserTag setOrgId(Long orgId) {
          this.orgId = orgId;
          return this;
      }
    
    public Long getSalesId() {
        return salesId;
    }

      public HouseUserTag setSalesId(Long salesId) {
          this.salesId = salesId;
          return this;
      }
    
    public String getTagName() {
        return tagName;
    }

      public HouseUserTag setTagName(String tagName) {
          this.tagName = tagName;
          return this;
      }
    
    public Date getCreateTime() {
        return createTime;
    }

      public HouseUserTag setCreateTime(Date createTime) {
          this.createTime = createTime;
          return this;
      }
    
    public Date getUpdateTime() {
        return updateTime;
    }

      public HouseUserTag setUpdateTime(Date updateTime) {
          this.updateTime = updateTime;
          return this;
      }

      public static final String ID = "id";

      public static final String ORG_ID = "org_id";

      public static final String SALES_ID = "sales_id";

      public static final String TAG_NAME = "tag_name";

      public static final String CREATE_TIME = "create_time";

      public static final String UPDATE_TIME = "update_time";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseUserTag{" +
              "id=" + id +
                  ", orgId=" + orgId +
                  ", salesId=" + salesId +
                  ", tagName=" + tagName +
                  ", createTime=" + createTime +
                  ", updateTime=" + updateTime +
              "}";
    }
}
