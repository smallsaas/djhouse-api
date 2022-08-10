package com.jfeat.am.module.house.services.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.domain.service.HouseApplicationIntermediaryService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseApplicationIntermediaryServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseApplicationIntermediary;
import com.jfeat.users.account.services.domain.service.UserAccountService;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("houseApplicationIntermediaryService")
public class HouseApplicationIntermediaryServiceImpl extends CRUDHouseApplicationIntermediaryServiceImpl implements HouseApplicationIntermediaryService {

    @Resource
    UserAccountMapper userAccountMapper;

    @Resource
    UserAccountService userAccountService;

    @Override
    protected String entityName() {
        return "HouseApplicationIntermediary";
    }


    @Override
    @Transactional
    public int passHouseApplicationIntermediary(Long id) {
        Integer affect = 0;
        HouseApplicationIntermediary houseApplicationIntermediary = houseApplicationIntermediaryMapper.selectById(id);
        if (houseApplicationIntermediary != null) {
            houseApplicationIntermediary.setStatus(HouseApplicationIntermediary.STATUS_PASS);
            affect += houseApplicationIntermediaryMapper.updateById(houseApplicationIntermediary);

            if (affect > 0) {
                UserAccount userAccount =  userAccountMapper.selectById(houseApplicationIntermediary.getUserId());
                if (userAccount!=null){
                    List<Integer> userTypeList = userAccountService.getUserTypeList(userAccount.getType());
//                    判读用户是否有中介身份，没有需要添加中介身份
                    if (!userTypeList.contains(EndUserTypeSetting.USER_TYPE_INTERMEDIARY)){
                        userTypeList.add(EndUserTypeSetting.USER_TYPE_INTERMEDIARY);
                        userAccount.setType(userAccountService.getUserTypeByList(userTypeList));
                        affect+=userAccountMapper.updateById(userAccount);
                    }
                }

            }
        }
        return affect;
    }
}
