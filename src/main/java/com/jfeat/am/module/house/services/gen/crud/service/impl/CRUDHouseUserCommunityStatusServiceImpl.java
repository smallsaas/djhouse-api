package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserCommunityStatus;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserCommunityStatusMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseUserCommunityStatusService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseUserCommunityStatusService
 * @author Code generator
 * @since 2022-06-07
 */

@Service
public class CRUDHouseUserCommunityStatusServiceImpl  extends CRUDServiceOnlyImpl<HouseUserCommunityStatus> implements CRUDHouseUserCommunityStatusService {





        @Resource
        protected HouseUserCommunityStatusMapper houseUserCommunityStatusMapper;

        @Override
        protected BaseMapper<HouseUserCommunityStatus> getMasterMapper() {
                return houseUserCommunityStatusMapper;
        }







}


