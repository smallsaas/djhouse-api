package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HousePropertyRoomService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHousePropertyRoomServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("housePropertyRoomService")
public class HousePropertyRoomServiceImpl extends CRUDHousePropertyRoomServiceImpl implements HousePropertyRoomService {

    @Override
    protected String entityName() {
        return "HousePropertyRoom";
    }


                            }
