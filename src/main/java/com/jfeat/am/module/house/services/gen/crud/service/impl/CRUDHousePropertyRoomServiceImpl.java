package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyRoom;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyRoomMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHousePropertyRoomService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHousePropertyRoomService
 * @author Code generator
 * @since 2022-06-06
 */

@Service
public class CRUDHousePropertyRoomServiceImpl  extends CRUDServiceOnlyImpl<HousePropertyRoom> implements CRUDHousePropertyRoomService {





        @Resource
        protected HousePropertyRoomMapper housePropertyRoomMapper;

        @Override
        protected BaseMapper<HousePropertyRoom> getMasterMapper() {
                return housePropertyRoomMapper;
        }







}


