package com.jfeat.am.module.house.api.userself;

import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserCommunityStatusDao;
import com.jfeat.am.module.house.services.domain.service.HouseUserCommunityStatusService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserCommunityStatus;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/u/profile")
public class UserLoginEndpoint {

    @Resource
    QueryHouseUserCommunityStatusDao queryHouseUserCommunityStatusDao;


    @Resource
    HouseUserCommunityStatusService houseUserCommunityStatusService;

//    @GetMapping
//    public Tip queryUserCommunityStatusByUserId() {
//        if (JWTKit.getUserId() == null) {
//            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
//        }
//        Long userId = JWTKit.getUserId();
//        return SuccessTip.create(queryHouseUserCommunityStatusDao.queryUserCommunityStatusByUserId(userId));
//    }
}
