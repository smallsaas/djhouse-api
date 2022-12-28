package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author Code generator
 * @since 2022-06-11
 */
@TableName("t_house_property_building")
@ApiModel(value = "HousePropertyBuilding对象", description = "")
public class HousePropertyBuilding  extends Model<HousePropertyBuilding> implements Comparable<HousePropertyBuilding> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

//      @ApiModelProperty(value = "租户数据隔离")
//      private Long orgId;

    @ApiModelProperty(value = "社区id")
    private Long communityId;

    @ApiModelProperty(value = "区域")
    private String area;

    @ApiModelProperty(value = "楼栋编号")
    private String code;

    @ApiModelProperty(value = "楼栋层数")
    private Integer floors;

    @ApiModelProperty(value = "每层单元数")
    private Integer units;

    @ApiModelProperty(value = "cad图纸")
    private String cadPicture;

    private String flatTitleOne;
    private String flatPictureOne;


    private String flatTitleTwo;
    private String flatPictureTwo;

    private String flatTitleThree;
    private String flatPictureThree;


    @ApiModelProperty()
    private Integer multipleNumber;

    private String unitPicture;

    private Integer issue;

    @TableField(exist = false)
    private Integer houseTypeNumber;

    @TableField(exist = false)
    private Integer assertTotal;

    public Integer getHouseTypeNumber() {
        return houseTypeNumber;
    }

    public HousePropertyBuilding setHouseTypeNumber(Integer houseTypeNumber) {
        this.houseTypeNumber = houseTypeNumber;
        return this;
    }

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    public Integer getAssertTotal() {
        return assertTotal;
    }

    public HousePropertyBuilding setAssertTotal(Integer assertTotal) {
        this.assertTotal = assertTotal;
        return this;
    }

    public String getCadPicture() {
        return cadPicture;
    }

    public HousePropertyBuilding setCadPicture(String cadPicture) {
        this.cadPicture = cadPicture;
        return this;
    }

    public Long getId() {
        return id;
    }

    public HousePropertyBuilding setId(Long id) {
        this.id = id;
        return this;
    }

//    public Long getOrgId() {
//        return orgId;
//    }
//
//      public HousePropertyBuilding setOrgId(Long orgId) {
//          this.orgId = orgId;
//          return this;
//      }


    public String getFlatTitleOne() {
        return flatTitleOne;
    }

    public void setFlatTitleOne(String flatTitleOne) {
        this.flatTitleOne = flatTitleOne;
    }

    public String getFlatPictureOne() {
        return flatPictureOne;
    }

    public void setFlatPictureOne(String flatPictureOne) {
        this.flatPictureOne = flatPictureOne;
    }

    public String getFlatTitleTwo() {
        return flatTitleTwo;
    }

    public void setFlatTitleTwo(String flatTitleTwo) {
        this.flatTitleTwo = flatTitleTwo;
    }

    public String getFlatPictureTwo() {
        return flatPictureTwo;
    }

    public void setFlatPictureTwo(String flatPictureTwo) {
        this.flatPictureTwo = flatPictureTwo;
    }

    public String getFlatTitleThree() {
        return flatTitleThree;
    }

    public void setFlatTitleThree(String flatTitleThree) {
        this.flatTitleThree = flatTitleThree;
    }

    public String getFlatPictureThree() {
        return flatPictureThree;
    }

    public void setFlatPictureThree(String flatPictureThree) {
        this.flatPictureThree = flatPictureThree;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public HousePropertyBuilding setCommunityId(Long communityId) {
        this.communityId = communityId;
        return this;
    }

    public String getArea() {
        return area;
    }

    public HousePropertyBuilding setArea(String area) {
        this.area = area;
        return this;
    }

    public String getCode() {
        return code;
    }

    public HousePropertyBuilding setCode(String code) {
        this.code = code;
        return this;
    }

    public Integer getFloors() {
        return floors;
    }

    public HousePropertyBuilding setFloors(Integer floors) {
        this.floors = floors;
        return this;
    }

    public Integer getUnits() {
        return units;
    }

    public HousePropertyBuilding setUnits(Integer units) {
        this.units = units;
        return this;
    }

    public String getUnitPicture() {
        return unitPicture;
    }

    public void setUnitPicture(String unitPicture) {
        this.unitPicture = unitPicture;
    }

    public Integer getMultipleNumber() {
        return multipleNumber;
    }

    public void setMultipleNumber(Integer multipleNumber) {
        this.multipleNumber = multipleNumber;
    }

    public static final String ID = "id";

    public static final String ORG_ID = "org_id";

    public static final String COMMUNITY_ID = "community_id";

    public static final String AREA = "area";

    public static final String CODE = "code";

    public static final String FLOORS = "floors";

    public static final String UNITS = "units";

    public static final String ISSUE = "issue";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HousePropertyBuilding{" +
                "id=" + id +
                ", communityId=" + communityId +
                ", area='" + area + '\'' +
                ", code='" + code + '\'' +
                ", floors=" + floors +
                ", units=" + units +
                ", cadPicture='" + cadPicture + '\'' +
                '}';
    }

    @Override
    public int compareTo(HousePropertyBuilding target) {


        int isTargetNum = 0;
        int isThisNum = 0;

        if (target.getCode()!=null&&!target.getCode().equals("")){
            char c = target.getCode().charAt(0);
            if (!Character.isLetter(c)){
                isTargetNum = 1;
            }
        }

        if (this.getCode()!=null&&!this.getCode().equals("")){
            char c = this.getCode().charAt(0);
            if (!Character.isLetter(c)){
                isThisNum = 1;
            }
        }

        if (isTargetNum==1&&isThisNum==1){

            Integer thisNumber=0;
            Integer targetNumber=0;
            Pattern p = Pattern.compile("(^[0-9]*)");
            Matcher thisMatcher = p.matcher(this.getCode());
            if (thisMatcher.find()) {
                thisNumber = Integer.valueOf(thisMatcher.group(1));
            }

            Matcher targetMatcher = p.matcher(target.getCode());
            if (targetMatcher.find()) {
                targetNumber = Integer.valueOf(targetMatcher.group(1));
            }
            return thisNumber-targetNumber;

        }else {
           return isThisNum-isTargetNum;
        }

    }
}
