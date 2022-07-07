package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseVrPicture;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseVrPictureMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseVrPictureService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseVrPictureService
 * @author Code generator
 * @since 2022-07-04
 */

@Service
public class CRUDHouseVrPictureServiceImpl  extends CRUDServiceOnlyImpl<HouseVrPicture> implements CRUDHouseVrPictureService {





        @Resource
        protected HouseVrPictureMapper houseVrPictureMapper;

        @Override
        protected BaseMapper<HouseVrPicture> getMasterMapper() {
                return houseVrPictureMapper;
        }







}


