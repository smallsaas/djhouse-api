package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserNote;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseUserNoteMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseUserNoteService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseUserNoteService
 * @author Code generator
 * @since 2022-08-29
 */

@Service
public class CRUDHouseUserNoteServiceImpl  extends CRUDServiceOnlyImpl<HouseUserNote> implements CRUDHouseUserNoteService {





        @Resource
        protected HouseUserNoteMapper houseUserNoteMapper;

        @Override
        protected BaseMapper<HouseUserNote> getMasterMapper() {
                return houseUserNoteMapper;
        }







}


