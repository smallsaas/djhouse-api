package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HousePropertyCommunityOverModelService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHousePropertyCommunityOverModelServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("housePropertyCommunityService")
public class HousePropertyCommunityOverModelServiceImpl extends CRUDHousePropertyCommunityOverModelServiceImpl implements HousePropertyCommunityOverModelService {

    @Override
    protected String entityName() {
        return "HousePropertyCommunity";
    }


                            }
