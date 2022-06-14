package com.jfeat.am.module.house.services.domain.service.impl;

import com.jfeat.am.module.house.services.domain.service.HouseAssetMatchLogService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseAssetMatchLogServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseAssetMatchLogService")
public class HouseAssetMatchLogServiceImpl extends CRUDHouseAssetMatchLogServiceImpl implements HouseAssetMatchLogService {

    @Override
    protected String entityName() {
        return "HouseAssetMatchLog";
    }


}
