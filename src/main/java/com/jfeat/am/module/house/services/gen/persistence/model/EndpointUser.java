package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author Code generator
 * @since 2022-06-14
 */
@TableName("t_end_user")
@ApiModel(value = "EndUser对象", description = "")
public class EndpointUser extends Model<EndpointUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "账号(登录账号)")
    private String account;

    @ApiModelProperty(value = "密码MD5值")
    private String password;

    @ApiModelProperty(value = "盐值")
    private String salt;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "生日")
    private Date dob;

    @ApiModelProperty(value = "性别:0-男,1-女,2-保密")
    private Integer gender;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "专属邀请码")
    private String inviteCode;

    @ApiModelProperty(value = "邀请人id")
    private Long invitorId;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "团队人数")
    private Integer teamCount;

    @ApiModelProperty(value = "创建时间，可理解为注册时间")
    private Date createTime;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "逻辑删除字段")
    @TableLogic
    private Integer deleteFlag;

    @ApiModelProperty(value = "注册的github用户名")
    private String registeredGithubUsername;

    @ApiModelProperty(value = "注册手机")
    private String registeredPhone;

    @ApiModelProperty(value = "注册EMAIL")
    private String registeredEmail;

    @ApiModelProperty(value = "邮箱是否验证，默认 0-未验证,1-验证")
    private Integer emailValidated;

    @ApiModelProperty(value = "电话是否验证，默认 0-未验证,1-验证")
    private Integer phoneValidated;

    @ApiModelProperty(value = "部门 id ")
    private Long orgId;

    @ApiModelProperty(value = "分类字段")
    private String tag;

    @ApiModelProperty(value = "分类字段2")
    private String categoryTag;

    @ApiModelProperty(value = "是否是主持人，0否1是")
    private Integer isCompere;

    @ApiModelProperty(value = "是否是注册用户，0否1是")
    private Integer isRegister;

    @ApiModelProperty(value = "类型 1 普通用户 2供应商")
    private Integer type;

    @ApiModelProperty(value = "关联系统用户id")
    private Long sysUserId;

    @ApiModelProperty(value = "用户所属渠道")
    private String vendor;

    private Date orgUpdateTime;

    private String produceTeam;

    private String appid;

    private Date endLoginTime;

    private String contact;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getEndLoginTime() {
        return endLoginTime;
    }

    public void setEndLoginTime(Date endLoginTime) {
        this.endLoginTime = endLoginTime;
    }

    @ApiModelProperty(value = "用户拥有房产列表")
    @TableField(exist = false)
    private List<HouseUserAssetRecord> houseUserAssetRecords;

    @ApiModelProperty(value = "交换记录")
    @TableField(exist = false)
    private List<HouseAssetExchangeRequestRecord> exchangeRequestRecords;

//    @ApiModelProperty(value = "团购列表")
//    @TableField(exist = false)
//    private List<Product> products;

    @ApiModelProperty(value = "用户类型列表")
    @TableField(exist = false)
    private List<Integer> typeList;

    @TableField(exist = false)
    private List<Long> appids;


    public Long getId() {
        return id;
    }

    public EndpointUser setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public EndpointUser setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public EndpointUser setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public EndpointUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public EndpointUser setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public String getName() {
        return name;
    }

    public EndpointUser setName(String name) {
        this.name = name;
        return this;
    }

    public Date getDob() {
        return dob;
    }

    public EndpointUser setDob(Date dob) {
        this.dob = dob;
        return this;
    }

    public Integer getGender() {
        return gender;
    }

    public EndpointUser setGender(Integer gender) {
        this.gender = gender;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public EndpointUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public EndpointUser setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public EndpointUser setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
        return this;
    }

    public Long getInvitorId() {
        return invitorId;
    }

    public EndpointUser setInvitorId(Long invitorId) {
        this.invitorId = invitorId;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public EndpointUser setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getTeamCount() {
        return teamCount;
    }

    public EndpointUser setTeamCount(Integer teamCount) {
        this.teamCount = teamCount;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public EndpointUser setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public EndpointUser setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public EndpointUser setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
        return this;
    }

    public String getRegisteredGithubUsername() {
        return registeredGithubUsername;
    }

    public EndpointUser setRegisteredGithubUsername(String registeredGithubUsername) {
        this.registeredGithubUsername = registeredGithubUsername;
        return this;
    }

    public String getRegisteredPhone() {
        return registeredPhone;
    }

    public EndpointUser setRegisteredPhone(String registeredPhone) {
        this.registeredPhone = registeredPhone;
        return this;
    }

    public String getRegisteredEmail() {
        return registeredEmail;
    }

    public EndpointUser setRegisteredEmail(String registeredEmail) {
        this.registeredEmail = registeredEmail;
        return this;
    }

    public Integer getEmailValidated() {
        return emailValidated;
    }

    public EndpointUser setEmailValidated(Integer emailValidated) {
        this.emailValidated = emailValidated;
        return this;
    }

    public Integer getPhoneValidated() {
        return phoneValidated;
    }

    public EndpointUser setPhoneValidated(Integer phoneValidated) {
        this.phoneValidated = phoneValidated;
        return this;
    }

    public Long getOrgId() {
        return orgId;
    }

    public EndpointUser setOrgId(Long orgId) {
        this.orgId = orgId;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public EndpointUser setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getCategoryTag() {
        return categoryTag;
    }

    public EndpointUser setCategoryTag(String categoryTag) {
        this.categoryTag = categoryTag;
        return this;
    }

    public Integer getIsCompere() {
        return isCompere;
    }

    public EndpointUser setIsCompere(Integer isCompere) {
        this.isCompere = isCompere;
        return this;
    }

    public Integer getIsRegister() {
        return isRegister;
    }

    public EndpointUser setIsRegister(Integer isRegister) {
        this.isRegister = isRegister;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public EndpointUser setType(Integer type) {
        this.type = type;
        return this;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public EndpointUser setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
        return this;
    }

    public String getVendor() {
        return vendor;
    }

    public EndpointUser setVendor(String vendor) {
        this.vendor = vendor;
        return this;
    }

    public List<Long> getAppids() {
        return appids;
    }

    public void setAppids(List<Long> appids) {
        this.appids = appids;
    }

    public List<HouseUserAssetRecord> getHouseUserAssetRecords() {
        return houseUserAssetRecords;
    }

    public void setHouseUserAssetRecords(List<HouseUserAssetRecord> houseUserAssetRecords) {
        this.houseUserAssetRecords = houseUserAssetRecords;
    }

    public List<HouseAssetExchangeRequestRecord> getExchangeRequestRecords() {
        return exchangeRequestRecords;
    }

    public void setExchangeRequestRecords(List<HouseAssetExchangeRequestRecord> exchangeRequestRecords) {
        this.exchangeRequestRecords = exchangeRequestRecords;
    }

//    public List<Product> getProducts() {
//        return products;
//    }
//
//    public void setProducts(List<Product> products) {
//        this.products = products;
//    }

    public List<Integer> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Integer> typeList) {
        this.typeList = typeList;
    }

    public static final String ID = "id";

    public static final String AVATAR = "avatar";

    public static final String ACCOUNT = "account";

    public static final String PASSWORD = "password";

    public static final String SALT = "salt";

    public static final String NAME = "name";

    public static final String DOB = "dob";

    public static final String GENDER = "gender";

    public static final String EMAIL = "email";

    public static final String PHONE = "phone";

    public static final String INVITE_CODE = "invite_code";

    public static final String INVITOR_ID = "invitor_id";

    public static final String STATUS = "status";

    public static final String TEAM_COUNT = "team_count";

    public static final String CREATE_TIME = "create_time";

    public static final String VERSION = "version";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String REGISTERED_GITHUB_USERNAME = "registered_github_username";

    public static final String REGISTERED_PHONE = "registered_phone";

    public static final String REGISTERED_EMAIL = "registered_email";

    public static final String EMAIL_VALIDATED = "email_validated";

    public static final String PHONE_VALIDATED = "phone_validated";

    public static final String ORG_ID = "org_id";

    public static final String TAG = "tag";

    public static final String CATEGORY_TAG = "category_tag";

    public static final String IS_COMPERE = "is_compere";

    public static final String IS_REGISTER = "is_register";

    public static final String TYPE = "type";

    public static final String SYS_USER_ID = "sys_user_id";

    public static final String VENDOR = "vendor";

    public Date getOrgUpdateTime() {
        return orgUpdateTime;
    }

    public void setOrgUpdateTime(Date orgUpdateTime) {
        this.orgUpdateTime = orgUpdateTime;
    }

    public String getProduceTeam() {
        return produceTeam;
    }

    public void setProduceTeam(String produceTeam) {
        this.produceTeam = produceTeam;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "EndpointUser{" +
                "id=" + id +
                ", avatar=" + avatar +
                ", account=" + account +
                ", password=" + password +
                ", salt=" + salt +
                ", name=" + name +
                ", dob=" + dob +
                ", gender=" + gender +
                ", email=" + email +
                ", phone=" + phone +
                ", inviteCode=" + inviteCode +
                ", invitorId=" + invitorId +
                ", status=" + status +
                ", teamCount=" + teamCount +
                ", createTime=" + createTime +
                ", version=" + version +
                ", deleteFlag=" + deleteFlag +
                ", registeredGithubUsername=" + registeredGithubUsername +
                ", registeredPhone=" + registeredPhone +
                ", registeredEmail=" + registeredEmail +
                ", emailValidated=" + emailValidated +
                ", phoneValidated=" + phoneValidated +
                ", orgId=" + orgId +
                ", tag=" + tag +
                ", categoryTag=" + categoryTag +
                ", isCompere=" + isCompere +
                ", isRegister=" + isRegister +
                ", type=" + type +
                ", sysUserId=" + sysUserId +
                ", vendor=" + vendor +
                "}";
    }
}
