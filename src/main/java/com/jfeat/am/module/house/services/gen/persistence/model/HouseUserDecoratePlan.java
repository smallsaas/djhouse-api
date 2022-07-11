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
 * @since 2022-07-01
 */
@TableName("t_house_user_decorate_plan")
@ApiModel(value = "HouseUserDecoratePlan对象", description = "")
public class HouseUserDecoratePlan extends Model<HouseUserDecoratePlan> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "装修计划id")
    private Long decoratePlanId;

    @ApiModelProperty(value = "资产id")
    private Long assetId;

    @ApiModelProperty(value = "类型 1是装修方案 2是团购")
    private Integer optionType;

    @ApiModelProperty(value = "是否可修改 0不可修改 1可修改")
    private Integer modifyOption;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


    public Long getId() {
        return id;
    }

    public HouseUserDecoratePlan setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public HouseUserDecoratePlan setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getDecoratePlanId() {
        return decoratePlanId;
    }

    public HouseUserDecoratePlan setDecoratePlanId(Long decoratePlanId) {
        this.decoratePlanId = decoratePlanId;
        return this;
    }

    public Long getAssetId() {
        return assetId;
    }

    public HouseUserDecoratePlan setAssetId(Long assetId) {
        this.assetId = assetId;
        return this;
    }

    public Integer getOptionType() {
        return optionType;
    }

    public void setOptionType(Integer optionType) {
        this.optionType = optionType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public HouseUserDecoratePlan setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getModifyOption() {
        return modifyOption;
    }

    public void setModifyOption(Integer modifyOption) {
        this.modifyOption = modifyOption;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String DECORATE_PLAN_ID = "decorate_plan_id";

    public static final String ASSET_ID = "asset_id";

    public static final String CREATE_TIME = "create_time";

    public static final Integer DECORATE_TYPE = 1;

    public static final Integer BULK_TYPE = 2;

    public static final Integer MODIFY_TYPE_REFUSE=0;

    public static final Integer MODIFY_TYPE_ALLOW=1;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseUserDecoratePlan{" +
                "id=" + id +
                ", userId=" + userId +
                ", decoratePlanId=" + decoratePlanId +
                ", assetId=" + assetId +
                ", createTime=" + createTime +
                "}";
    }
}
