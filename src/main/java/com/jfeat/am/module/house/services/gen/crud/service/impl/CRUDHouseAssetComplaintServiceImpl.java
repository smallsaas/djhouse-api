package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetComplaint;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseAssetComplaintMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseAssetComplaintService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseAssetComplaintService
 * @author Code generator
 * @since 2022-07-22
 */

@Service
public class CRUDHouseAssetComplaintServiceImpl  extends CRUDServiceOnlyImpl<HouseAssetComplaint> implements CRUDHouseAssetComplaintService {





        @Resource
        protected HouseAssetComplaintMapper houseAssetComplaintMapper;

        @Override
        protected BaseMapper<HouseAssetComplaint> getMasterMapper() {
                return houseAssetComplaintMapper;
        }







}


