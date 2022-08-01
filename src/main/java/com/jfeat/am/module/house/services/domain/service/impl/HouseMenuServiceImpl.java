package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseMenuService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseMenuServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseMenuService")
public class HouseMenuServiceImpl extends CRUDHouseMenuServiceImpl implements HouseMenuService {

    @Override
    protected String entityName() {
        return "HouseMenu";
    }


                            }
