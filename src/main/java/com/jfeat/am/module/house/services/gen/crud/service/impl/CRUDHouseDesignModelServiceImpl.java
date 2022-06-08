package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDesignModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseDesignModelMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseDesignModelService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseDesignModelService
 * @author Code generator
 * @since 2022-06-08
 */

@Service
public class CRUDHouseDesignModelServiceImpl  extends CRUDServiceOnlyImpl<HouseDesignModel> implements CRUDHouseDesignModelService {





        @Resource
        protected HouseDesignModelMapper houseDesignModelMapper;

        @Override
        protected BaseMapper<HouseDesignModel> getMasterMapper() {
                return houseDesignModelMapper;
        }







}


