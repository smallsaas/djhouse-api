package com.jfeat.am.module.house.api.userself;


import com.jfeat.am.module.house.services.domain.dao.QueryHouseDesignModelDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyBuildingUnitDao;
import com.jfeat.am.module.house.services.domain.model.HousePropertyBuildingUnitRecord;
import com.jfeat.am.module.house.services.domain.service.HouseDesignModelService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseDesignModelModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDesignModel;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/u/houseDesignModel/houseDesignModels")
public class UserHouseTypeEndpoint {

    @Resource
    HouseDesignModelService houseDesignModelService;

    @Resource
    QueryHouseDesignModelDao queryHouseDesignModelDao;

    @Resource
    QueryHousePropertyBuildingUnitDao queryHousePropertyBuildingUnitDao;

    @GetMapping("/houseTypeDetails/{id}")
    public Tip getHouseTypeDetails(@PathVariable Long id) {
        HouseDesignModelModel houseDesignModel = houseDesignModelService.queryMasterModel(queryHouseDesignModelDao, id);

        if (houseDesignModel==null){
            return SuccessTip.create();
        }

        Set<String> unitCode = new HashSet<>();
        Set<String> direction = new HashSet<>();
        HousePropertyBuildingUnitRecord buildingUnitRecord = new HousePropertyBuildingUnitRecord();
        buildingUnitRecord.setDesignModelId(houseDesignModel.getId());
        List<HousePropertyBuildingUnitRecord> unitRecordList = queryHousePropertyBuildingUnitDao.findHousePropertyBuildingUnitPage(null,buildingUnitRecord,null,null,null,null,null);
        for (HousePropertyBuildingUnitRecord unit:unitRecordList){
            unitCode.add(unit.getUnitCode());
            direction.add(unit.getDirection());
        }
        houseDesignModel.setDirection(direction);
        houseDesignModel.setUnitCode(unitCode);
        return SuccessTip.create(houseDesignModel);
    }
}
