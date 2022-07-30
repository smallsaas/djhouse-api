package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.math.BigDecimal;

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
@TableName("t_house_decorate_plan_funiture")
@ApiModel(value = "HouseDecoratePlanFuniture对象", description = "")
public class HouseDecoratePlanFuniture extends Model<HouseDecoratePlanFuniture> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "装修计划id")
    private Long decoratePlanId;

    @ApiModelProperty(value = "家居id")
    private Long furnitureId;

    private Integer number;

    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private BigDecimal price;

    @TableField(exist = false)
    private String index;


    public Long getId() {
        return id;
    }

    public HouseDecoratePlanFuniture setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getDecoratePlanId() {
        return decoratePlanId;
    }

    public HouseDecoratePlanFuniture setDecoratePlanId(Long decoratePlanId) {
        this.decoratePlanId = decoratePlanId;
        return this;
    }

    public Long getFurnitureId() {
        return furnitureId;
    }

    public HouseDecoratePlanFuniture setFurnitureId(Long furnitureId) {
        this.furnitureId = furnitureId;
        return this;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public static final String ID = "id";

    public static final String DECORATE_PLAN_ID = "decorate_plan_id";

    public static final String FURNITURE_ID = "furniture_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseDecoratePlanFuniture{" +
                "id=" + id +
                ", decoratePlanId=" + decoratePlanId +
                ", furnitureId=" + furnitureId +
                "}";
    }
}
