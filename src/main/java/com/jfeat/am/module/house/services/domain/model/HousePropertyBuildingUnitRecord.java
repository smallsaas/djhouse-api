package com.jfeat.am.module.house.services.domain.model;

import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuildingUnit;

/**
 * Created by Code generator on 2022-06-09
 */
public class HousePropertyBuildingUnitRecord extends HousePropertyBuildingUnit {
    private String vrLink;

    private String vrPicture;

    public String getVrLink() {
        return vrLink;
    }

    public void setVrLink(String vrLink) {
        this.vrLink = vrLink;
    }

    public String getVrPicture() {
        return vrPicture;
    }

    public void setVrPicture(String vrPicture) {
        this.vrPicture = vrPicture;
    }
}
