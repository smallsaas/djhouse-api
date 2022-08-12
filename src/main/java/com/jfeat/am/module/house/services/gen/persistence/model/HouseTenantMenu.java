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
 * @since 2022-08-12
 */
@TableName("t_house_tenant_menu")
@ApiModel(value="HouseTenantMenu对象", description="")
public class HouseTenantMenu extends Model<HouseTenantMenu> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      @ApiModelProperty(value = "社区id")
      private Long orgId;

      @ApiModelProperty(value = "上级菜单id")
      private Long menuId;

      @ApiModelProperty(value = "是否可用")
      private Integer enabled;

    
    public Long getId() {
        return id;
    }

      public HouseTenantMenu setId(Long id) {
          this.id = id;
          return this;
      }
    
    public Long getOrgId() {
        return orgId;
    }

      public HouseTenantMenu setOrgId(Long orgId) {
          this.orgId = orgId;
          return this;
      }
    
    public Long getMenuId() {
        return menuId;
    }

      public HouseTenantMenu setMenuId(Long menuId) {
          this.menuId = menuId;
          return this;
      }
    
    public Integer getEnabled() {
        return enabled;
    }

      public HouseTenantMenu setEnabled(Integer enabled) {
          this.enabled = enabled;
          return this;
      }

      public static final String ID = "id";

      public static final String ORG_ID = "org_id";

      public static final String MENU_ID = "menu_id";

      public static final String ENABLED = "enabled";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "HouseTenantMenu{" +
              "id=" + id +
                  ", orgId=" + orgId +
                  ", menuId=" + menuId +
                  ", enabled=" + enabled +
              "}";
    }
}
