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
 * @since 2022-06-11
 */
@TableName("t_house_equity_demand_supply")
@ApiModel(value = "HouseEquityDemandSupply对象", description = "")
public class HouseEquityDemandSupply extends Model<HouseEquityDemandSupply> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "权益选择，0：需求 1：供给")
    private Integer equityOption;

    @ApiModelProperty(value = "面积")
    private BigDecimal area;

    @ApiModelProperty(value = "电话")
    @TableField(exist = false)
    private String phoneNumber;


    public Long getId() {
        return id;
    }

    public HouseEquityDemandSupply setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public HouseEquityDemandSupply setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Integer getEquityOption() {
        return equityOption;
    }

    public HouseEquityDemandSupply setEquityOption(Integer equityOption) {
        this.equityOption = equityOption;
        return this;
    }

    public BigDecimal getArea() {
        return area;
    }

    public HouseEquityDemandSupply setArea(BigDecimal area) {
        this.area = area;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public HouseEquityDemandSupply setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String EQUITY_OPTION = "equity_option";

    public static final String AREA = "area";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseEquityDemandSupply{" +
                "id=" + id +
                ", userId=" + userId +
                ", equityOption=" + equityOption +
                ", area=" + area +
                "}";
    }
}
