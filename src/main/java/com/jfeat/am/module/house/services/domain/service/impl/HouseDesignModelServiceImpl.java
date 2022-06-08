package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseDesignModelService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseDesignModelServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseDesignModelService")
public class HouseDesignModelServiceImpl extends CRUDHouseDesignModelServiceImpl implements HouseDesignModelService {

    @Override
    protected String entityName() {
        return "HouseDesignModel";
    }


                            }
