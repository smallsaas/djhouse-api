package com.jfeat.am.module.house.api.app.rent;

import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseApplicationIntermediaryDao;
import com.jfeat.am.module.house.services.domain.service.HouseApplicationIntermediaryService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseApplicationIntermediary;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/u/house/rent/userApplicationIntermediary")
public class UserApplicationIntermediaryEndpoint {

    @Resource
    HouseApplicationIntermediaryService houseApplicationIntermediaryService;

    @Resource
    QueryHouseApplicationIntermediaryDao queryHouseApplicationIntermediaryDao;



//    申请中介
    @PostMapping
    public Tip createHouseApplicationIntermediary(@RequestBody HouseApplicationIntermediary entity) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        if (entity.getSignFlag()==null || !entity.getSignFlag().equals(1)){
            throw new BusinessException(BusinessCode.BadRequest,"请签订线下协议");
        }
        Integer affected = 0;
        entity.setUserId(JWTKit.getUserId());
        try {
            affected = houseApplicationIntermediaryService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }
}
