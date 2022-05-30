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
 * @since 2022-05-27
 */
@TableName("t_house_user_decorate_plan")
@ApiModel(value="HouseUserDecoratePlan对象", description="")
public class HouseUserDecoratePlan extends Model<HouseUserDecoratePlan> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "装修计划id")
    private Long decoratePlanId;

    @ApiModelProperty(value = "家居数量，默认为1")
    private Integer funitureNumber;

    @ApiModelProperty(value = "替换：replace 移除remove")
    private String changeOption;

    @ApiModelProperty(value = "替换或者移除的家居id")
    private Long funitureId;

    @ApiModelProperty(value = "替换后的家居id 移除家居为空")
    private Long changedFunitureId;

    @ApiModelProperty(value = "替换或者移除说明")
    private String note;


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

    public Integer getFunitureNumber() {
        return funitureNumber;
    }

    public HouseUserDecoratePlan setFunitureNumber(Integer funitureNumber) {
        this.funitureNumber = funitureNumber;
        return this;
    }

    public String getChangeOption() {
        return changeOption;
    }

    public HouseUserDecoratePlan setChangeOption(String changeOption) {
        this.changeOption = changeOption;
        return this;
    }

    public Long getFunitureId() {
        return funitureId;
    }

    public HouseUserDecoratePlan setFunitureId(Long funitureId) {
        this.funitureId = funitureId;
        return this;
    }

    public Long getChangedFunitureId() {
        return changedFunitureId;
    }

    public HouseUserDecoratePlan setChangedFunitureId(Long changedFunitureId) {
        this.changedFunitureId = changedFunitureId;
        return this;
    }

    public String getNote() {
        return note;
    }

    public HouseUserDecoratePlan setNote(String note) {
        this.note = note;
        return this;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String DECORATE_PLAN_ID = "decorate_plan_id";

    public static final String FUNITURE_NUMBER = "funiture_number";

    public static final String CHANGE_OPTION = "change_option";

    public static final String FUNITURE_ID = "funiture_id";

    public static final String CHANGED_FUNITURE_ID = "changed_funiture_id";

    public static final String NOTE = "note";

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
                ", funitureNumber=" + funitureNumber +
                ", changeOption=" + changeOption +
                ", funitureId=" + funitureId +
                ", changedFunitureId=" + changedFunitureId +
                ", note=" + note +
                "}";
    }
}