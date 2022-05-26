package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HousePropertyUnitService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHousePropertyUnitServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("housePropertyUnitService")
public class HousePropertyUnitServiceImpl extends CRUDHousePropertyUnitServiceImpl implements HousePropertyUnitService {

    @Override
    protected String entityName() {
        return "HousePropertyUnit";
    }


                            }
