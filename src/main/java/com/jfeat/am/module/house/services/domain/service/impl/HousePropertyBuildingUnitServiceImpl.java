package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HousePropertyBuildingUnitService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHousePropertyBuildingUnitServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    protected String entityName() {
        return "HousePropertyBuildingUnit";
    }


                            }
