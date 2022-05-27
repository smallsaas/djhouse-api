package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.module.house.services.domain.service.ProductService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDProductServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("productService")
public class ProductServiceImpl extends CRUDProductServiceImpl implements ProductService {

    @Override
    protected String entityName() {
        return "Product";
    }


                            }
