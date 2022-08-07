package com.jfeat.am.module.house.services.gen.persistence.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-07-13
 */
@TableName("t_house_appointment")
@ApiModel(value = "HouseAppointment对象", description = "")
public class HouseAppointment extends Model<HouseAppointment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "预约码/预约成功后前天显示")
    private String code;

    @ApiModelProperty(value = "预约类型id")
    private Long typeId;

    @ApiModelProperty(value = " 预约类型")
    private String type;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "预约客户电话")
    private String userPhone;

    @ApiModelProperty(value = "预约客户名称")
    private String userName;

    @ApiModelProperty(value = "处理员ID")
    private Long serverId;

    @ApiModelProperty(value = "处理员")
    private String serverName;

    @ApiModelProperty(value = "处理员电话")
    private String serverPhone;

    @ApiModelProperty(value = "预约名称")
    private String name;

    @ApiModelProperty(value = "预约服务地址ID")
    private Long addressId;

    @ApiModelProperty(value = "预约服务地址")
    private String addressName;

    @ApiModelProperty(value = "预约服务描述")
    private String description;

    @ApiModelProperty(value = "预约服务图标")
    private String icon;

    @ApiModelProperty(value = " 状态")
    private Integer status;

    @TableField(exist = false)
    private String statusStr;

    @ApiModelProperty(value = "费用")
    private BigDecimal fee;

    @ApiModelProperty(value = " 创建时间")
    private Date createTime;

    @ApiModelProperty(value = " 预约时间")
    private Date appointmentTime;

    @ApiModelProperty(value = " 结束时间")
    private Date closeTime;

    @ApiModelProperty(value = "支付时间")
    private Date paymentTimestamp;

    @ApiModelProperty(value = "支付方试")
    private String paymentMethod;

    @ApiModelProperty(value = "预约最早时间")
    private Date earliestTime;

    @ApiModelProperty(value = "预约最迟时间")
    private Date latestTime;

    @ApiModelProperty(value = "保留字段---已使用，接收--DOB")
    private String fieldC;


    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public Long getId() {
        return id;
    }

    public HouseAppointment setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public HouseAppointment setCode(String code) {
        this.code = code;
        return this;
    }

    public Long getTypeId() {
        return typeId;
    }

    public HouseAppointment setTypeId(Long typeId) {
        this.typeId = typeId;
        return this;
    }

    public String getType() {
        return type;
    }

    public HouseAppointment setType(String type) {
        this.type = type;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public HouseAppointment setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public HouseAppointment setUserPhone(String userPhone) {
        this.userPhone = userPhone;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public HouseAppointment setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Long getServerId() {
        return serverId;
    }

    public HouseAppointment setServerId(Long serverId) {
        this.serverId = serverId;
        return this;
    }

    public String getServerName() {
        return serverName;
    }

    public HouseAppointment setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public String getServerPhone() {
        return serverPhone;
    }

    public HouseAppointment setServerPhone(String serverPhone) {
        this.serverPhone = serverPhone;
        return this;
    }

    public String getName() {
        return name;
    }

    public HouseAppointment setName(String name) {
        this.name = name;
        return this;
    }

    public Long getAddressId() {
        return addressId;
    }

    public HouseAppointment setAddressId(Long addressId) {
        this.addressId = addressId;
        return this;
    }

    public String getAddressName() {
        return addressName;
    }

    public HouseAppointment setAddressName(String addressName) {
        this.addressName = addressName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public HouseAppointment setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public HouseAppointment setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public HouseAppointment setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public HouseAppointment setFee(BigDecimal fee) {
        this.fee = fee;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public HouseAppointment setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public HouseAppointment setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
        return this;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public HouseAppointment setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
        return this;
    }

    public Date getPaymentTimestamp() {
        return paymentTimestamp;
    }

    public HouseAppointment setPaymentTimestamp(Date paymentTimestamp) {
        this.paymentTimestamp = paymentTimestamp;
        return this;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public HouseAppointment setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public Date getEarliestTime() {
        return earliestTime;
    }

    public HouseAppointment setEarliestTime(Date earliestTime) {
        this.earliestTime = earliestTime;
        return this;
    }

    public Date getLatestTime() {
        return latestTime;
    }

    public HouseAppointment setLatestTime(Date latestTime) {
        this.latestTime = latestTime;
        return this;
    }

    public String getFieldC() {
        return fieldC;
    }

    public HouseAppointment setFieldC(String fieldC) {
        this.fieldC = fieldC;
        return this;
    }

    public static final String ID = "id";

    public static final String CODE = "code";

    public static final String TYPE_ID = "type_id";

    public static final String TYPE = "type";

    public static final String USER_ID = "user_id";

    public static final String USER_PHONE = "user_phone";

    public static final String USER_NAME = "user_name";

    public static final String SERVER_ID = "server_id";

    public static final String SERVER_NAME = "server_name";

    public static final String SERVER_PHONE = "server_phone";

    public static final String NAME = "name";

    public static final String ADDRESS_ID = "address_id";

    public static final String ADDRESS_NAME = "address_name";

    public static final String DESCRIPTION = "description";

    public static final String ICON = "icon";

    public static final String STATUS = "status";

    public static final String FEE = "fee";

    public static final String CREATE_TIME = "create_time";

    public static final String APPOINTMENT_TIME = "appointment_time";

    public static final String CLOSE_TIME = "close_time";

    public static final String PAYMENT_TIMESTAMP = "payment_timestamp";

    public static final String PAYMENT_METHOD = "payment_method";

    public static final String EARLIEST_TIME = "earliest_time";

    public static final String LATEST_TIME = "latest_time";

    public static final String FIELD_C = "field_c";


    /*
    未设置
     */
    public static final Integer STATUS_NOT_SET=1;

//    已签约
    public static final Integer STATUS_Sign=2;

//    失约
    public static final Integer STATUS_MISS=3;

//    再联系
    public static final Integer STATUS_CONTACT=4;

//    已查看
    public static final Integer STATUS_LOOKED=5;

//    暂时搁置
    public static final Integer STATUS_PENDING=6;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseAppointment{" +
                "id=" + id +
                ", code=" + code +
                ", typeId=" + typeId +
                ", type=" + type +
                ", userId=" + userId +
                ", userPhone=" + userPhone +
                ", userName=" + userName +
                ", serverId=" + serverId +
                ", serverName=" + serverName +
                ", serverPhone=" + serverPhone +
                ", name=" + name +
                ", addressId=" + addressId +
                ", addressName=" + addressName +
                ", description=" + description +
                ", icon=" + icon +
                ", status=" + status +
                ", fee=" + fee +
                ", createTime=" + createTime +
                ", appointmentTime=" + appointmentTime +
                ", closeTime=" + closeTime +
                ", paymentTimestamp=" + paymentTimestamp +
                ", paymentMethod=" + paymentMethod +
                ", earliestTime=" + earliestTime +
                ", latestTime=" + latestTime +
                ", fieldC=" + fieldC +
                "}";
    }
}
