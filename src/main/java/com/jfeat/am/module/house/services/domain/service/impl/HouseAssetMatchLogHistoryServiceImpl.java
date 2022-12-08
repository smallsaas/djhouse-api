package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseAssetMatchLogHistoryService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseAssetMatchLogHistoryServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseAssetMatchLogHistoryService")
public class HouseAssetMatchLogHistoryServiceImpl extends CRUDHouseAssetMatchLogHistoryServiceImpl implements HouseAssetMatchLogHistoryService {

    @Override
    protected String entityName() {
        return "HouseAssetMatchLogHistory";
    }


                            }
