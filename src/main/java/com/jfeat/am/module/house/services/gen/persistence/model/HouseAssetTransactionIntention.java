package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @description: 房屋买卖-用户"有意向"记录表，`t_house_asset_transaction_intention`实体类
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/1/30 11:37
 * @author: hhhhhtao
 */
@TableName("t_house_asset_transaction_intention")
@ApiModel(value = "HouseAssetTransactionIntention对象", description = "")
public class HouseAssetTransactionIntention extends Model<HouseAssetTransactionIntention> {

    // 序列化id
    private static final long serialVersionUID = 1L;

    // 自增id
    private Long id;

    // 房屋买卖记录id
    @ApiModelProperty(value = "房屋买卖记录id")
    private Long TransactionId;

    // 意向用户id
    @ApiModelProperty(value = "意向用户id")
    private Long userId;

    // 备注
    @ApiModelProperty(value = "备注")
    private String note;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getTransactionId() {
        return TransactionId;
    }
    public void setTransactionId(Long transactionId) {
        TransactionId = transactionId;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseAssetTransactionIntention{" +
                "id=" + id +
                ", TransactionId=" + TransactionId +
                ", userId=" + userId +
                ", note='" + note + '\'' +
                '}';
    }
}
