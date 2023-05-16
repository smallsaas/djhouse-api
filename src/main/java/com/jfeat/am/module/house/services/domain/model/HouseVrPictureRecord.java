package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.HouseVrPicture;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Code generator on 2022-07-04
 */
public class HouseVrPictureRecord extends HouseVrPicture{
    @ApiModelProperty(value = "typeName")
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
