package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Code generator
 * @since 2022-05-19
 */
@TableName("t_supplier")
public class Supplier extends Model<Supplier> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 渚涘簲鍟�
     */
      private String name;

      /**
     * 浠嬬粛
     */
      private String note;

      /**
     * 绫诲瀷 棰勭暀
     */
      private Integer type;

      /**
     * 渚涘簲鍟嗗浘鐗�
     */
      private String url;

      /**
     * 渚涘簲鍟嗗湴鍧�
     */
      private String address;

    private Long orgId;

    
    public Long getId() {
        return id;
    }

      public Supplier setId(Long id) {
          this.id = id;
          return this;
      }
    
    public String getName() {
        return name;
    }

      public Supplier setName(String name) {
          this.name = name;
          return this;
      }
    
    public String getNote() {
        return note;
    }

      public Supplier setNote(String note) {
          this.note = note;
          return this;
      }
    
    public Integer getType() {
        return type;
    }

      public Supplier setType(Integer type) {
          this.type = type;
          return this;
      }
    
    public String getUrl() {
        return url;
    }

      public Supplier setUrl(String url) {
          this.url = url;
          return this;
      }
    
    public String getAddress() {
        return address;
    }

      public Supplier setAddress(String address) {
          this.address = address;
          return this;
      }
    
    public Long getOrgId() {
        return orgId;
    }

      public Supplier setOrgId(Long orgId) {
          this.orgId = orgId;
          return this;
      }

      public static final String ID = "id";

      public static final String NAME = "name";

      public static final String NOTE = "note";

      public static final String TYPE = "type";

      public static final String URL = "url";

      public static final String ADDRESS = "address";

      public static final String ORG_ID = "org_id";

      @Override
    protected Serializable pkVal() {
          return this.id;
      }

    @Override
    public String toString() {
        return "Supplier{" +
              "id=" + id +
                  ", name=" + name +
                  ", note=" + note +
                  ", type=" + type +
                  ", url=" + url +
                  ", address=" + address +
                  ", orgId=" + orgId +
              "}";
    }
}
