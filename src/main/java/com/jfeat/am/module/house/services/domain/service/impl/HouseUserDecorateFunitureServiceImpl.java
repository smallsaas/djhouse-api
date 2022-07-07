package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseUserDecorateFunitureService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseUserDecorateFunitureServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseUserDecorateFunitureService")
public class HouseUserDecorateFunitureServiceImpl extends CRUDHouseUserDecorateFunitureServiceImpl implements HouseUserDecorateFunitureService {

    @Override
    protected String entityName() {
        return "HouseUserDecorateFuniture";
    }


                            }
