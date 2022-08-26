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
 * @since 2022-08-25
 */
@TableName("t_house_browse_log")
@ApiModel(value="HouseBrowseLog对象", description="")
public class HouseBrowseLog extends Model<HouseBrowseLog> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "用户id")
      private Long userId;

      @ApiModelProperty(value = "浏览数量")
      private Integer browseNumber;

    
    public Long getId() {
        return id;
    }

      public HouseBrowseLog setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getUserId() {
        return userId;
    }

      public HouseBrowseLog setUserId(Long userId) {
          this.userId = userId;
          return this;
      }
    
    public Integer getBrowseNumber() {
        return browseNumber;
    }

      public HouseBrowseLog setBrowseNumber(Integer browseNumber) {
          this.browseNumber = browseNumber;
          return this;
      }

      public static final String ID = "id";

      public static final String USER_ID = "user_id";

      public static final String BROWSE_NUMBER = "browse_number";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseBrowseLog{" +
              "id=" + id +
                  ", userId=" + userId +
                  ", browseNumber=" + browseNumber +
              "}";
    }
}
