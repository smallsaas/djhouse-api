package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyCommunity;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyCommunityMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;
    
    import com.jfeat.am.module.house.services.gen.crud.service.CRUDHousePropertyCommunityOverModelService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOverModelImpl;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyCommunityModel;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHousePropertyCommunityOverModelService
 * @author Code generator
 * @since 2022-06-01
 */

@Service
public class CRUDHousePropertyCommunityOverModelServiceImpl  extends CRUDServiceOverModelImpl<HousePropertyCommunity,HousePropertyCommunityModel> implements CRUDHousePropertyCommunityOverModelService {









    @Resource
    protected HousePropertyCommunityMapper housePropertyCommunityMapper;

    
    @Override
    protected BaseMapper<HousePropertyCommunity> getMasterMapper() {
        return housePropertyCommunityMapper;
    }

    @Override
    protected Class<HousePropertyCommunity> masterClassName() {
        return HousePropertyCommunity.class;
    }

    @Override
    protected Class<HousePropertyCommunityModel> modelClassName() {
        return HousePropertyCommunityModel.class;
    }



    
    @Resource
    private HousePropertyBuildingMapper housePropertyBuildingMapper;

                        private final static String housePropertyBuildingFieldName = "community_id";
    
        private final static String housePropertyBuildingKeyName = "items";
    
                        
    

    
    @Override
    protected String[] slaveFieldNames() {
        return new String[]{
                                             housePropertyBuildingKeyName
                                             };
    }

    @Override
    protected FIELD onSlaveFieldItem(String field) {

        
                                                
            if (field.compareTo(housePropertyBuildingKeyName) == 0) {
                FIELD _field = new FIELD();
            _field.setItemKeyName(field);
            _field.setItemFieldName(housePropertyBuildingFieldName);
            _field.setItemClassName(HousePropertyBuilding.class);
            _field.setItemMapper(housePropertyBuildingMapper);
            
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


