package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseUserAssetHistoryService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseUserAssetHistoryServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseUserAssetHistoryService")
public class HouseUserAssetHistoryServiceImpl extends CRUDHouseUserAssetHistoryServiceImpl implements HouseUserAssetHistoryService {

    @Override
    protected String entityName() {
        return "HouseUserAssetHistory";
    }


                            }
