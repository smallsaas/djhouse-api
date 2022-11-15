package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseAssetLogService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseAssetLogServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseAssetLogService")
public class HouseAssetLogServiceImpl extends CRUDHouseAssetLogServiceImpl implements HouseAssetLogService {

    @Override
    protected String entityName() {
        return "HouseAssetLog";
    }


                            }
