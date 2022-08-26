package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseSubscribeService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseSubscribeServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseSubscribeService")
public class HouseSubscribeServiceImpl extends CRUDHouseSubscribeServiceImpl implements HouseSubscribeService {

    @Override
    protected String entityName() {
        return "HouseSubscribe";
    }


                            }
