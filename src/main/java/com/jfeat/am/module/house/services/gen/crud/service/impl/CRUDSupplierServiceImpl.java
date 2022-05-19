package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.Supplier;
import com.jfeat.am.module.house.services.gen.persistence.dao.SupplierMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDSupplierService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDSupplierService
 * @author Code generator
 * @since 2022-05-19
 */

@Service
public class CRUDSupplierServiceImpl  extends CRUDServiceOnlyImpl<Supplier> implements CRUDSupplierService {





        @Resource
        protected SupplierMapper supplierMapper;

        @Override
        protected BaseMapper<Supplier> getMasterMapper() {
                return supplierMapper;
        }







}


