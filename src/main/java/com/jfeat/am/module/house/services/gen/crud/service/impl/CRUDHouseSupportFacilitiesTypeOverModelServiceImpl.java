package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilitiesType;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseSupportFacilitiesTypeMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseSupportFacilitiesMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSupportFacilities;
    
    import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseSupportFacilitiesTypeOverModelService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOverModelImpl;
import com.jfeat.am.module.house.services.gen.crud.model.HouseSupportFacilitiesTypeModel;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseSupportFacilitiesTypeOverModelService
 * @author Code generator
 * @since 2022-08-05
 */

@Service
public class CRUDHouseSupportFacilitiesTypeOverModelServiceImpl  extends CRUDServiceOverModelImpl<HouseSupportFacilitiesType,HouseSupportFacilitiesTypeModel> implements CRUDHouseSupportFacilitiesTypeOverModelService {









    @Resource
    protected HouseSupportFacilitiesTypeMapper houseSupportFacilitiesTypeMapper;

    
    @Override
    protected BaseMapper<HouseSupportFacilitiesType> getMasterMapper() {
        return houseSupportFacilitiesTypeMapper;
    }

    @Override
    protected Class<HouseSupportFacilitiesType> masterClassName() {
        return HouseSupportFacilitiesType.class;
    }

    @Override
    protected Class<HouseSupportFacilitiesTypeModel> modelClassName() {
        return HouseSupportFacilitiesTypeModel.class;
    }



    
    @Resource
    private HouseSupportFacilitiesMapper houseSupportFacilitiesMapper;

                        private final static String houseSupportFacilitiesFieldName = "type_id";
    
        private final static String houseSupportFacilitiesKeyName = "items";
    
                        
    

    
    @Override
    protected String[] slaveFieldNames() {
        return new String[]{
                                             houseSupportFacilitiesKeyName
                                             };
    }

    @Override
    protected FIELD onSlaveFieldItem(String field) {

        
                                                
            if (field.compareTo(houseSupportFacilitiesKeyName) == 0) {
                FIELD _field = new FIELD();
            _field.setItemKeyName(field);
            _field.setItemFieldName(houseSupportFacilitiesFieldName);
            _field.setItemClassName(HouseSupportFacilities.class);
            _field.setItemMapper(houseSupportFacilitiesMapper);
            
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


