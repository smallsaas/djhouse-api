package com.jfeat.am.module.house.services.gen.persistence.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-05-27
 */
@TableName("t_house_decorate_plan")
@ApiModel(value="HouseDecoratePlan对象", description="")
public class HouseDecoratePlan extends Model<HouseDecoratePlan> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "装修风格名称")
      private String decorateStyleName;

      @ApiModelProperty(value = "装修风格是否可变，0是不可变 1是可变")
      private Integer allowChanged;

      @ApiModelProperty(value = "颜色格调")
      private String colorStyle;

      @ApiModelProperty(value = "总预算")
      private BigDecimal totalBudget;

      @ApiModelProperty(value = "人气值")
      private Integer star;

      @ApiModelProperty(value = "封面url")
      private String cover;

      @ApiModelProperty(value = "商家")
      private String merchant;

      @ApiModelProperty(value = "选项")
      private Integer optionType;

    @ApiModelProperty(value = "装修地址")
    @TableField(exist = false)
    private String decorateAddress;

    public String getDecorateAddress() {
        return decorateAddress;
    }

    public HouseDecoratePlan setDecorateAddress(String decorateAddress) {
        this.decorateAddress = decorateAddress;
        return this;
    }


    public String getCover() {
        return cover;
    }

    public HouseDecoratePlan setCover(String cover) {
        this.cover = cover;
        return this;
    }

    public String getMerchant() {
        return merchant;
    }

    public HouseDecoratePlan setMerchant(String merchant) {
        this.merchant = merchant;
        return this;
    }

    public Long getId() {
        return id;
    }

      public HouseDecoratePlan setId(Long id) {
          this.id = id;
          return this;
      }
    
    public String getDecorateStyleName() {
        return decorateStyleName;
    }

      public HouseDecoratePlan setDecorateStyleName(String decorateStyleName) {
          this.decorateStyleName = decorateStyleName;
          return this;
      }
    
    public Integer getAllowChanged() {
        return allowChanged;
    }

      public HouseDecoratePlan setAllowChanged(Integer allowChanged) {
          this.allowChanged = allowChanged;
          return this;
      }
    
    public String getColorStyle() {
        return colorStyle;
    }

      public HouseDecoratePlan setColorStyle(String colorStyle) {
          this.colorStyle = colorStyle;
          return this;
      }
    
    public BigDecimal getTotalBudget() {
        return totalBudget;
    }

      public HouseDecoratePlan setTotalBudget(BigDecimal totalBudget) {
          this.totalBudget = totalBudget;
          return this;
      }

    public Integer getOptionType() {
        return optionType;
    }

    public void setOptionType(Integer optionType) {
        this.optionType = optionType;
    }

    public Integer getStar() {
        return star;
    }

      public HouseDecoratePlan setStar(Integer star) {
          this.star = star;
          return this;
      }

      public static final String ID = "id";

      public static final String DECORATE_STYLE_NAME = "decorate_style_name";

      public static final String ALLOW_CHANGED = "allow_changed";

      public static final String COLOR_STYLE = "color_style";

      public static final String TOTAL_BUDGET = "total_budget";

      public static final String STAR = "star";

      public static final String COVER = "cover";

      private static final String MERCHANT = "merchant";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseDecoratePlan{" +
                "id=" + id +
                ", decorateStyleName='" + decorateStyleName + '\'' +
                ", allowChanged=" + allowChanged +
                ", colorStyle='" + colorStyle + '\'' +
                ", totalBudget=" + totalBudget +
                ", star=" + star +
                ", cover='" + cover + '\'' +
                ", merchant='" + merchant + '\'' +
                '}';
    }
}
