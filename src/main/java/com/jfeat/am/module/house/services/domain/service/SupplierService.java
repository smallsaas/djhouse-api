package com.jfeat.am.module.house.services.domain.service;

import com.jfeat.am.module.house.services.gen.crud.model.SupplierModel;
import com.jfeat.am.module.house.services.gen.crud.service.CRUDSupplierService;
import com.jfeat.am.module.house.services.gen.persistence.model.Supplier;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by vincent on 2017/10/19.
 */
public interface SupplierService extends CRUDSupplierService{
    @Transactional
    Supplier createOne(SupplierModel entity);

    @Transactional
    Integer deleteOne(Long id);

    String genAccountName(String name);
}