package com.jfeat.am.module.house.services.gen.crud.model;
// this is serviceModel.java.vm




import com.jfeat.am.module.house.services.gen.persistence.model.HouseVrPicture;

import java.math.BigDecimal;

/**
 * Created by Code generator on 2022-07-04
 *  * slaves.size() : 0
 *  * modelpack : $modelpack
 */
public class HouseVrPictureModel extends HouseVrPicture{
    private String note;
    private BigDecimal area;
    private String otherName;
    private Long otherID;
    private String typeName;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public Long getOtherID() {
        return otherID;
    }

    public void setOtherID(Long otherID) {
        this.otherID = otherID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
