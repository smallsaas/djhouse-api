package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.HouseVrPictureService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseVrPictureServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseVrPictureService")
public class HouseVrPictureServiceImpl extends CRUDHouseVrPictureServiceImpl implements HouseVrPictureService {

    @Override
    protected String entityName() {
        return "HouseVrPicture";
    }


                            }
