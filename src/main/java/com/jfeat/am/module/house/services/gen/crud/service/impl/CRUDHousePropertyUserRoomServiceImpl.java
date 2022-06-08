package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyUserRoom;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyUserRoomMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHousePropertyUserRoomService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHousePropertyUserRoomService
 * @author Code generator
 * @since 2022-06-06
 */

@Service
public class CRUDHousePropertyUserRoomServiceImpl  extends CRUDServiceOnlyImpl<HousePropertyUserRoom> implements CRUDHousePropertyUserRoomService {





        @Resource
        protected HousePropertyUserRoomMapper housePropertyUserRoomMapper;

        @Override
        protected BaseMapper<HousePropertyUserRoom> getMasterMapper() {
                return housePropertyUserRoomMapper;
        }







}


