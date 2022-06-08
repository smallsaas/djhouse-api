package com.jfeat.am.module.house.services.domain.service.impl;

import com.jfeat.am.module.house.services.domain.service.HousePropertyUserRoomService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHousePropertyUserRoomServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("housePropertyUserRoomService")
public class HousePropertyUserRoomServiceImpl extends CRUDHousePropertyUserRoomServiceImpl implements HousePropertyUserRoomService {

    @Override
    protected String entityName() {
        return "HousePropertyUserRoom";
    }


}
