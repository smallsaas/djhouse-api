package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HousePropertyUserUnitService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHousePropertyUserUnitServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("housePropertyUserUnitService")
public class HousePropertyUserUnitServiceImpl extends CRUDHousePropertyUserUnitServiceImpl implements HousePropertyUserUnitService {

    @Override
    protected String entityName() {
        return "HousePropertyUserUnit";
    }


                            }
