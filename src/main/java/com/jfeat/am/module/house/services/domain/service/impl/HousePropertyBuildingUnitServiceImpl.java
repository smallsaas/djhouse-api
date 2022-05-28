package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyBuildingDao;
import com.jfeat.am.module.house.services.domain.service.HousePropertyBuildingUnitService;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyBuildingModel;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHousePropertyBuildingUnitServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuildingUnit;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("housePropertyBuildingUnitService")
public class HousePropertyBuildingUnitServiceImpl extends CRUDHousePropertyBuildingUnitServiceImpl implements HousePropertyBuildingUnitService {

    @Resource
    QueryHousePropertyBuildingDao queryHousePropertyBuildingDao;

    @Resource
    HousePropertyBuildingUnitService housePropertyBuildingUnitService;

    @Override
    protected String entityName() {
        return "HousePropertyBuildingUnit";
    }


    @Override
    public int initBuildingUnit() {
        Integer affected = 0;
        List<HousePropertyBuildingModel> housePropertyBuildingModelList = queryHousePropertyBuildingDao.queryAllHousePropertyBuilding();
        for (HousePropertyBuildingModel housePropertyBuildingModel:housePropertyBuildingModelList){
            int floors = housePropertyBuildingModel.getFloors() !=null ? housePropertyBuildingModel.getFloors().intValue() :0;
            int units = housePropertyBuildingModel.getUnits() !=null ? housePropertyBuildingModel.getUnits().intValue() :0;

            if (floors!=0 && units!=0){
                for (int floor=1;floor<floors+1;floor++){
                    for (int unit=1;unit<units+1;unit++){
                        ;
                        HousePropertyBuildingUnit entity = new HousePropertyBuildingUnit();
                        entity.setBuildingId(housePropertyBuildingModel.getId());
                        entity.setNumber(String.format("%d%02d",floor,unit));

                        try {
                            affected = housePropertyBuildingUnitService.createMaster(entity);
                        } catch (DuplicateKeyException e) {
                            throw new BusinessException(BusinessCode.DuplicateKey);
                        }
                    }

                }
            }

//            System.out.println(housePropertyBuildingModel.getFloors());
        }
        return affected;
    }
}
