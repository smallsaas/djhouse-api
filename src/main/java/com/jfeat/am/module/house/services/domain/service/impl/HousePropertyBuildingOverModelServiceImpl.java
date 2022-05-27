package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HousePropertyBuildingOverModelService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHousePropertyBuildingOverModelServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("housePropertyBuildingService")
public class HousePropertyBuildingOverModelServiceImpl extends CRUDHousePropertyBuildingOverModelServiceImpl implements HousePropertyBuildingOverModelService {

    @Override
    protected String entityName() {
        return "HousePropertyBuilding";
    }


                            }
