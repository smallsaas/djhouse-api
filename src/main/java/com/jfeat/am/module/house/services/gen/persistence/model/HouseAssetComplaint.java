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
 * @since 2022-07-22
 */
@TableName("t_house_asset_complaint")
@ApiModel(value = "HouseAssetComplaint对象", description = "")
public class HouseAssetComplaint extends Model<HouseAssetComplaint> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "原来用户id")
    private Long oldUserId;

    @ApiModelProperty(value = "房子id")
    private Long assetId;

    @ApiModelProperty(value = "冲突证明材料 图片列表")
    private String clashCertificate;

    @ApiModelProperty(value = "冲突描述")
    private String clashDescribe;

    @ApiModelProperty(value = "0-暂时未解决 1-解决完成 2-拒绝")
    private Integer solveStatus;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "解决时间")
    private Date solveTime;


    public Long getId() {
        return id;
    }

    public HouseAssetComplaint setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public HouseAssetComplaint setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getOldUserId() {
        return oldUserId;
    }

    public HouseAssetComplaint setOldUserId(Long oldUserId) {
        this.oldUserId = oldUserId;
        return this;
    }

    public Long getAssetId() {
        return assetId;
    }

    public HouseAssetComplaint setAssetId(Long assetId) {
        this.assetId = assetId;
        return this;
    }

    public String getClashCertificate() {
        return clashCertificate;
    }

    public HouseAssetComplaint setClashCertificate(String clashCertificate) {
        this.clashCertificate = clashCertificate;
        return this;
    }

    public String getClashDescribe() {
        return clashDescribe;
    }

    public HouseAssetComplaint setClashDescribe(String clashDescribe) {
        this.clashDescribe = clashDescribe;
        return this;
    }

    public Integer getSolveStatus() {
        return solveStatus;
    }

    public HouseAssetComplaint setSolveStatus(Integer solveStatus) {
        this.solveStatus = solveStatus;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public HouseAssetComplaint setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getSolveTime() {
        return solveTime;
    }

    public HouseAssetComplaint setSolveTime(Date solveTime) {
        this.solveTime = solveTime;
        return this;
    }

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String OLD_USER_ID = "old_user_id";

    public static final String ASSET_ID = "asset_id";

    public static final String CLASH_CERTIFICATE = "clash_certificate";

    public static final String CLASH_DESCRIBE = "clash_describe";

    public static final String SOLVE_STATUS = "solve_status";

    public static final String CREATE_TIME = "create_time";

    public static final String SOLVE_TIME = "solve_time";

    public static final Integer SOLVE_STATUS_UNFINISHED = 0;

    public static final Integer SOLVE_STATUS_COMPLETE = 1;

    public static final Integer SOLVE_STATUS_refuse = 2;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseAssetComplaint{" +
                "id=" + id +
                ", userId=" + userId +
                ", oldUserId=" + oldUserId +
                ", assetId=" + assetId +
                ", clashCertificate=" + clashCertificate +
                ", clashDescribe=" + clashDescribe +
                ", solveStatus=" + solveStatus +
                ", createTime=" + createTime +
                ", solveTime=" + solveTime +
                "}";
    }
}
