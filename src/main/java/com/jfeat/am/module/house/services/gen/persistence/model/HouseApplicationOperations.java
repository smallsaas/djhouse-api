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
 * @since 2022-08-11
 */
@TableName("t_house_application_operations")
@ApiModel(value="HouseApplicationOperations对象", description="")
public class HouseApplicationOperations extends Model<HouseApplicationOperations> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "用户id")
      private Long userId;

      @ApiModelProperty(value = "社区名")
      private String tenantName;

      @ApiModelProperty(value = "姓名")
      private String name;

      @ApiModelProperty(value = "电话")
      private String phone;

      @ApiModelProperty(value = "身份证")
      private String idcard;

      @ApiModelProperty(value = "证明材料")
      private String evidence;

      @ApiModelProperty(value = "说明理由")
      private String reason;

      @ApiModelProperty(value = "是否同意申请 0-是未定 1-拒绝 2-同意")
      private Integer status;

      @ApiModelProperty(value = "创建时间")
      private Date createTime;

      @ApiModelProperty(value = "更新时间")
      private Date updateTime;

    
    public Long getId() {
        return id;
    }

      public HouseApplicationOperations setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getUserId() {
        return userId;
    }

      public HouseApplicationOperations setUserId(Long userId) {
          this.userId = userId;
          return this;
      }
    
    public String getTenantName() {
        return tenantName;
    }

      public HouseApplicationOperations setTenantName(String tenantName) {
          this.tenantName = tenantName;
          return this;
      }
    
    public String getName() {
        return name;
    }

      public HouseApplicationOperations setName(String name) {
          this.name = name;
          return this;
      }
    
    public String getPhone() {
        return phone;
    }

      public HouseApplicationOperations setPhone(String phone) {
          this.phone = phone;
          return this;
      }
    
    public String getIdcard() {
        return idcard;
    }

      public HouseApplicationOperations setIdcard(String idcard) {
          this.idcard = idcard;
          return this;
      }
    
    public String getEvidence() {
        return evidence;
    }

      public HouseApplicationOperations setEvidence(String evidence) {
          this.evidence = evidence;
          return this;
      }
    
    public String getReason() {
        return reason;
    }

      public HouseApplicationOperations setReason(String reason) {
          this.reason = reason;
          return this;
      }
    
    public Integer getStatus() {
        return status;
    }

      public HouseApplicationOperations setStatus(Integer status) {
          this.status = status;
          return this;
      }
    
    public Date getCreateTime() {
        return createTime;
    }

      public HouseApplicationOperations setCreateTime(Date createTime) {
          this.createTime = createTime;
          return this;
      }
    
    public Date getUpdateTime() {
        return updateTime;
    }

      public HouseApplicationOperations setUpdateTime(Date updateTime) {
          this.updateTime = updateTime;
          return this;
      }

      public static final String ID = "id";

      public static final String USER_ID = "user_id";

      public static final String TENANT_NAME = "tenant_name";

      public static final String NAME = "name";

      public static final String PHONE = "phone";

      public static final String IDCARD = "idcard";

      public static final String EVIDENCE = "evidence";

      public static final String REASON = "reason";

      public static final String STATUS = "status";

      public static final String CREATE_TIME = "create_time";

      public static final String UPDATE_TIME = "update_time";

    public static final Integer STATUS_NOT_SET = 0;

    public static final Integer STATUS_PASS = 2;

    public static final Integer STATUS_REFUSE = 1;

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseApplicationOperations{" +
              "id=" + id +
                  ", userId=" + userId +
                  ", tenantName=" + tenantName +
                  ", name=" + name +
                  ", phone=" + phone +
                  ", idcard=" + idcard +
                  ", evidence=" + evidence +
                  ", reason=" + reason +
                  ", status=" + status +
                  ", createTime=" + createTime +
                  ", updateTime=" + updateTime +
              "}";
    }
}
