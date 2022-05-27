package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseUserDecoratePlanService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseUserDecoratePlanServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseUserDecoratePlanService")
public class HouseUserDecoratePlanServiceImpl extends CRUDHouseUserDecoratePlanServiceImpl implements HouseUserDecoratePlanService {

    @Override
    protected String entityName() {
        return "HouseUserDecoratePlan";
    }


                            }
