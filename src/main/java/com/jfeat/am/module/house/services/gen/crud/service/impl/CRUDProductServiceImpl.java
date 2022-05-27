package com.jfeat.am.module.house.services.gen.crud.service.impl;
// ServiceImpl start

            
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.crud.plus.FIELD;
import com.jfeat.am.module.house.services.gen.persistence.model.Product;
import com.jfeat.am.module.house.services.gen.persistence.dao.ProductMapper;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDProductService;
import org.springframework.stereotype.Service;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import javax.annotation.Resource;
import com.jfeat.crud.plus.impl.CRUDServiceOnlyImpl;

/**
 * <p>
 *  implementation
 * </p>
 *CRUDProductService
 * @author Code generator
 * @since 2022-05-27
 */

@Service
public class CRUDProductServiceImpl  extends CRUDServiceOnlyImpl<Product> implements CRUDProductService {





        @Resource
        protected ProductMapper productMapper;

        @Override
        protected BaseMapper<Product> getMasterMapper() {
                return productMapper;
        }







}


