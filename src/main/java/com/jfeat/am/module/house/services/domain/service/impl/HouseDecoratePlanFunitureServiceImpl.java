package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseDecoratePlanFunitureService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseDecoratePlanFunitureServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseDecoratePlanFunitureService")
public class HouseDecoratePlanFunitureServiceImpl extends CRUDHouseDecoratePlanFunitureServiceImpl implements HouseDecoratePlanFunitureService {

    @Override
    protected String entityName() {
        return "HouseDecoratePlanFuniture";
    }


                            }
