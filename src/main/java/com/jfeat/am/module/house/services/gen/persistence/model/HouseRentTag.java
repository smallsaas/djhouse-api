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
 * @since 2022-06-27
 */
@TableName("t_house_rent_tag")
@ApiModel(value="HouseRentTag对象", description="")
public class HouseRentTag extends Model<HouseRentTag> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "标签中文名")
      private String cnName;

      @ApiModelProperty(value = "标签英文名")
      private String enName;

    
    public Long getId() {
        return id;
    }

      public HouseRentTag setId(Long id) {
          this.id = id;
          return this;
      }
    
    public String getCnName() {
        return cnName;
    }

      public HouseRentTag setCnName(String cnName) {
          this.cnName = cnName;
          return this;
      }
    
    public String getEnName() {
        return enName;
    }

      public HouseRentTag setEnName(String enName) {
          this.enName = enName;
          return this;
      }

      public static final String ID = "id";

      public static final String CN_NAME = "cn_name";

      public static final String EN_NAME = "en_name";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseRentTag{" +
              "id=" + id +
                  ", cnName=" + cnName +
                  ", enName=" + enName +
              "}";
    }
}
