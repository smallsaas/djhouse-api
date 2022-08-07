package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSurroundFacilitiesType;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseSurroundFacilitiesTypeMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseSurroundFacilitiesMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSurroundFacilities;
    
    import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseSurroundFacilitiesTypeOverModelService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOverModelImpl;
import com.jfeat.am.module.house.services.gen.crud.model.HouseSurroundFacilitiesTypeModel;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseSurroundFacilitiesTypeOverModelService
 * @author Code generator
 * @since 2022-08-05
 */

@Service
public class CRUDHouseSurroundFacilitiesTypeOverModelServiceImpl  extends CRUDServiceOverModelImpl<HouseSurroundFacilitiesType,HouseSurroundFacilitiesTypeModel> implements CRUDHouseSurroundFacilitiesTypeOverModelService {









    @Resource
    protected HouseSurroundFacilitiesTypeMapper houseSurroundFacilitiesTypeMapper;

    
    @Override
    protected BaseMapper<HouseSurroundFacilitiesType> getMasterMapper() {
        return houseSurroundFacilitiesTypeMapper;
    }

    @Override
    protected Class<HouseSurroundFacilitiesType> masterClassName() {
        return HouseSurroundFacilitiesType.class;
    }

    @Override
    protected Class<HouseSurroundFacilitiesTypeModel> modelClassName() {
        return HouseSurroundFacilitiesTypeModel.class;
    }



    
    @Resource
    private HouseSurroundFacilitiesMapper houseSurroundFacilitiesMapper;

                        private final static String houseSurroundFacilitiesFieldName = "type_id";
    
        private final static String houseSurroundFacilitiesKeyName = "items";
    
                        
    

    
    @Override
    protected String[] slaveFieldNames() {
        return new String[]{
                                             houseSurroundFacilitiesKeyName
                                             };
    }

    @Override
    protected FIELD onSlaveFieldItem(String field) {

        
                                                
            if (field.compareTo(houseSurroundFacilitiesKeyName) == 0) {
                FIELD _field = new FIELD();
            _field.setItemKeyName(field);
            _field.setItemFieldName(houseSurroundFacilitiesFieldName);
            _field.setItemClassName(HouseSurroundFacilities.class);
            _field.setItemMapper(houseSurroundFacilitiesMapper);
            
            return _field;
        }


            
        throw new BusinessException(BusinessCode.BadRequest);
    }


    @Override
    protected String[] childFieldNames() {
        return new String[]{
                                };
    }

    @Override
    protected FIELD onChildFieldItem(String field) {
                
        throw new BusinessException(BusinessCode.BadRequest);
    }




}


