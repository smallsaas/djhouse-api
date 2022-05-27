package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDecoratePlan;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseDecoratePlanMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.ProductMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.Product;
    import com.jfeat.am.module.house.services.gen.persistence.dao.HouseDecoratePlanFunitureMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDecoratePlanFuniture;
    
    import com.jfeat.am.module.house.services.gen.crud.service.CRUDHouseDecoratePlanOverModelService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOverModelImpl;
import com.jfeat.am.module.house.services.gen.crud.model.HouseDecoratePlanModel;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDHouseDecoratePlanOverModelService
 * @author Code generator
 * @since 2022-05-27
 */

@Service
public class CRUDHouseDecoratePlanOverModelServiceImpl  extends CRUDServiceOverModelImpl<HouseDecoratePlan,HouseDecoratePlanModel> implements CRUDHouseDecoratePlanOverModelService {









    @Resource
    protected HouseDecoratePlanMapper houseDecoratePlanMapper;

    
    @Override
    protected BaseMapper<HouseDecoratePlan> getMasterMapper() {
        return houseDecoratePlanMapper;
    }

    @Override
    protected Class<HouseDecoratePlan> masterClassName() {
        return HouseDecoratePlan.class;
    }

    @Override
    protected Class<HouseDecoratePlanModel> modelClassName() {
        return HouseDecoratePlanModel.class;
    }



    
    @Resource
    private ProductMapper productMapper;

                        private final static String productFieldName = "t_house_decorate_plan_funiture::decorate_plan_id:furniture_id";
    
        private final static String productKeyName = "items";
    
                        
        // relation mapper (one many peer)
    @Resource
    private HouseDecoratePlanFunitureMapper houseDecoratePlanFunitureMapper;
    

    
    @Override
    protected String[] slaveFieldNames() {
        return new String[]{
                                             productKeyName
                                             };
    }

    @Override
    protected FIELD onSlaveFieldItem(String field) {

        
                                                
            if (field.compareTo(productKeyName) == 0) {
                FIELD _field = new FIELD();
            _field.setItemKeyName(field);
            _field.setItemFieldName(productFieldName);
            _field.setItemClassName(Product.class);
            _field.setItemMapper(productMapper);
                        // one many by peer
            _field.setItemRelationMapper(houseDecoratePlanFunitureMapper);
            _field.setItemRelationClassName(HouseDecoratePlanFuniture.class);
            
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


