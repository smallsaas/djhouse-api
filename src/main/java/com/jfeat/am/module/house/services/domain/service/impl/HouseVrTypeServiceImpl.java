package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseVrTypeService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseVrTypeServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseVrTypeService")
public class HouseVrTypeServiceImpl extends CRUDHouseVrTypeServiceImpl implements HouseVrTypeService {

    @Override
    protected String entityName() {
        return "HouseVrType";
    }


                            }
