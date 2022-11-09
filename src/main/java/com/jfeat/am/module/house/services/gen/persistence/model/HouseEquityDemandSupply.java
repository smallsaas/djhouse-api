package com.jfeat.am.module.house.services.gen.persistence.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

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

    @ApiModelProperty(value = "权益选择，1：需求 2：供给")
    private Integer equityOption;

    @ApiModelProperty(value = "面积")
    private BigDecimal area;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(exist = false)
    private String simpleTime;

    @ApiModelProperty(value = "电话")
    @TableField(exist = false)
    private String phoneNumber;


    @ApiModelProperty("用户名")
    @TableField(exist = false)
    private String username;

    @ApiModelProperty("真实姓名")
    @TableField(exist = false)
    private String realName;

    @ApiModelProperty(value = "用户头像")
    @TableField(exist = false)
    private String userAvatar;


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public HouseEquityDemandSupply setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public HouseEquityDemandSupply setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
        return this;
    }

    public String getSimpleTime() {
        return simpleTime;
    }

    public void setSimpleTime(String simpleTime) {
        this.simpleTime = simpleTime;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String EQUITY_OPTION = "equity_option";

    public static final String AREA = "area";

    public static final Integer EQUITY_OPTION_DEMAND=1;

    public static final Integer EQUITY_OPTION_SUPPLY=2;

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
