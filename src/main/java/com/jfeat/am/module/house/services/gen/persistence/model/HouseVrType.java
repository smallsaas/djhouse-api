package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author Code generator
 * @since 2022-08-16
 */
@TableName("t_house_vr_type")
@ApiModel(value = "HouseVrType对象", description = "")
public class HouseVrType extends Model<HouseVrType> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "社区id")
    private Long orgId;

    @ApiModelProperty(value = "小区id")
    private Long communityId;

    @TableField(exist = false)
    private List<HouseVrPicture> items;


    public Long getId() {
        return id;
    }

    public HouseVrType setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public HouseVrType setName(String name) {
        this.name = name;
        return this;
    }

    public Long getOrgId() {
        return orgId;
    }

    public HouseVrType setOrgId(Long orgId) {
        this.orgId = orgId;
        return this;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public List<HouseVrPicture> getItems() {
        return items;
    }

    public void setItems(List<HouseVrPicture> items) {
        this.items = items;
    }

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String ORG_ID = "org_id";

    public static final String COMMUNITY_id = "community_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseVrType{" +
                "id=" + id +
                ", name=" + name +
                ", orgId=" + orgId +
                "}";
    }
}
