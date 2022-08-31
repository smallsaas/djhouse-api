package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseUserTagService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseUserTagServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseUserTagService")
public class HouseUserTagServiceImpl extends CRUDHouseUserTagServiceImpl implements HouseUserTagService {

    @Override
    protected String entityName() {
        return "HouseUserTag";
    }


                            }
