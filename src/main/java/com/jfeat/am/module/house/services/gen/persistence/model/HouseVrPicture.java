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
 * @since 2022-07-04
 */
@TableName("t_house_vr_picture")
@ApiModel(value = "HouseVrPicture对象", description = "")
public class HouseVrPicture extends Model<HouseVrPicture> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "vr图名")
    private String name;

    @ApiModelProperty(value = "vr link")
    private String link;

    @ApiModelProperty(value = "vr图")
    private String vrPicture;

    @ApiModelProperty(value = "vr 缩略图")
    private String snapshot;

    @ApiModelProperty(value = "人气值")
    private Integer star;

    @ApiModelProperty(value = "几居室")
    private String bedrooms;

    @ApiModelProperty(value = "风格")
    private String style;

    @ApiModelProperty(value = "类型")
    private String typeOption;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "上架状态 0-下架 1上架")
    private Integer status;

    public Long getId() {
        return id;
    }

    public HouseVrPicture setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public HouseVrPicture setName(String name) {
        this.name = name;
        return this;
    }

    public String getLink() {
        return link;
    }

    public HouseVrPicture setLink(String link) {
        this.link = link;
        return this;
    }

    public String getVrPicture() {
        return vrPicture;
    }

    public HouseVrPicture setVrPicture(String vrPicture) {
        this.vrPicture = vrPicture;
        return this;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public HouseVrPicture setSnapshot(String snapshot) {
        this.snapshot = snapshot;
        return this;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getTypeOption() {
        return typeOption;
    }

    public void setTypeOption(String typeOption) {
        this.typeOption = typeOption;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String LINK = "link";

    public static final String VR_PICTURE = "vr_picture";

    public static final String SNAPSHOT = "snapshot";

    /*
    上架vr图
     */
    public static final Integer STATUS_SHELVES=1;

    //下架vr图
    public static final Integer STATUS_UNSHELVES=0;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HouseVrPicture{" +
                "id=" + id +
                ", name=" + name +
                ", link=" + link +
                ", vrPicture=" + vrPicture +
                ", snapshot=" + snapshot +
                "}";
    }
}
