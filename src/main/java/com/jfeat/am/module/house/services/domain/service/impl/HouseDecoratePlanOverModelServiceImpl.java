package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseDecoratePlanOverModelService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseDecoratePlanOverModelServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseDecoratePlanService")
public class HouseDecoratePlanOverModelServiceImpl extends CRUDHouseDecoratePlanOverModelServiceImpl implements HouseDecoratePlanOverModelService {

    @Override
    protected String entityName() {
        return "HouseDecoratePlan";
    }


                            }
