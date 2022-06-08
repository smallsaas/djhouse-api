package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseUserCommunityStatusService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseUserCommunityStatusServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseUserCommunityStatusService")
public class HouseUserCommunityStatusServiceImpl extends CRUDHouseUserCommunityStatusServiceImpl implements HouseUserCommunityStatusService {

    @Override
    protected String entityName() {
        return "HouseUserCommunityStatus";
    }


                            }
