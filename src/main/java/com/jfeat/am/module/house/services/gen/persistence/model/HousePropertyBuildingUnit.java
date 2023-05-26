package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author Code generator
 * @since 2022-06-09
 */
@TableName("t_house_property_building_unit")
@ApiModel(value = "HousePropertyBuildingUnit对象", description = "")
public class HousePropertyBuildingUnit extends Model<HousePropertyBuildingUnit> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "楼栋id")
    private Long buildingId;

    @ApiModelProperty(value = "户型id")
    private Long designModelId;

    @ApiModelProperty(value = "单元编号")
    private String unitCode;

    @ApiModelProperty(value = "方向")
    private String direction;

    @ApiModelProperty(value = "数字单元")
    private String unitNumber;

    @ApiModelProperty(value = "x轴")
    private BigDecimal xAxis;

    @ApiModelProperty(value = "y轴")
    private BigDecimal yAxis;

    @ApiModelProperty(value = "面积")
    private BigDecimal area;

    @ApiModelProperty(value = "真实面积")
    private BigDecimal realArea;

    @ApiModelProperty(value = "复式权益面积")
    private BigDecimal multiArea;

    @ApiModelProperty(value = "复式真实面积")
    private BigDecimal multiRealArea;

    @TableField(exist = false)
    private Integer floorsCount;

    @TableField(exist = false)
    private Integer startFloors;

    @TableField(exist = false)
    private Integer endFloors;


    @TableField(exist = false)
    private List<HouseAsset> items;

    @TableField(exist = false)
    private String communityName;

    @TableField(exist = false)
    private Long communityId;

    @TableField(exist = false)
    private String buildingCode;

    @TableField(exist = false)
    private String houseType;

    public BigDecimal getRealArea() {
        return realArea;
    }

    public void setRealArea(BigDecimal realArea) {
        this.realArea = realArea;
    }

    public BigDecimal getMultiArea() {
        return multiArea;
    }

    public void setMultiArea(BigDecimal multiArea) {
        this.multiArea = multiArea;
    }

    public BigDecimal getMultiRealArea() {
        return multiRealArea;
    }

    public void setMultiRealArea(BigDecimal multiRealArea) {
        this.multiRealArea = multiRealArea;
    }

    public Integer getFloorsCount() {
        return floorsCount;
    }

    public void setFloorsCount(Integer floorsCount) {
        this.floorsCount = floorsCount;
    }

    public Integer getStartFloors() {
        return startFloors;
    }

    public void setStartFloors(Integer startFloors) {
        this.startFloors = startFloors;
    }

    public Integer getEndFloors() {
        return endFloors;
    }

    public void setEndFloors(Integer endFloors) {
        this.endFloors = endFloors;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public List<HouseAsset> getItems() {
        return items;
    }

    public void setItems(List<HouseAsset> items) {
        this.items = items;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public Long getId() {
        return id;
    }

    public HousePropertyBuildingUnit setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public HousePropertyBuildingUnit setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
        return this;
    }

    public Long getDesignModelId() {
        return designModelId;
    }

    public HousePropertyBuildingUnit setDesignModelId(Long designModelId) {
        this.designModelId = designModelId;
        return this;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public HousePropertyBuildingUnit setUnitCode(String unitCode) {
        this.unitCode = unitCode;
        return this;
    }

    public BigDecimal getxAxis() {
        return xAxis;
    }

    public void setxAxis(BigDecimal xAxis) {
        this.xAxis = xAxis;
    }

    public BigDecimal getyAxis() {
        return yAxis;
    }

    public void setyAxis(BigDecimal yAxis) {
        this.yAxis = yAxis;
    }

    //    1到20英文
    public static String[] UNIT_NUMBER = {
            "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twentY"
    };

    public static final String ID = "id";

    public static final String BUILDING_ID = "building_id";

    public static final String DESIGN_MODEL_ID = "design_model_id";

    public static final String UNIT_CODE = "unit_code";

    public BigDecimal getArea() {
        return area;
    }

    public HousePropertyBuildingUnit setArea(BigDecimal area) {
        this.area = area;
        return this;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HousePropertyBuildingUnit{" +
                "id=" + id +
                ", buildingId=" + buildingId +
                ", designModelId=" + designModelId +
                ", unitCode='" + unitCode + '\'' +
                ", area='" + area + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HousePropertyBuildingUnit that = (HousePropertyBuildingUnit) o;
        return Objects.equals(id, that.id) && Objects.equals(buildingId, that.buildingId) && Objects.equals(designModelId, that.designModelId) && Objects.equals(unitCode, that.unitCode) && Objects.equals(area, that.area) && Objects.equals(direction, that.direction) && Objects.equals(communityName, that.communityName) && Objects.equals(buildingCode, that.buildingCode) && Objects.equals(houseType, that.houseType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, buildingId, designModelId, unitCode, area, direction, communityName, buildingCode, houseType);
    }
}
