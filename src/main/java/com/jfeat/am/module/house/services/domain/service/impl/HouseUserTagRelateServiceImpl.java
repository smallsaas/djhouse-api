package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseUserTagRelateService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseUserTagRelateServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseUserTagRelateService")
public class HouseUserTagRelateServiceImpl extends CRUDHouseUserTagRelateServiceImpl implements HouseUserTagRelateService {

    @Override
    protected String entityName() {
        return "HouseUserTagRelate";
    }


                            }
