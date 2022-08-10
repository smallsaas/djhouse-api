package com.jfeat.am.module.house.services.domain.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.house.services.domain.model.HousePropertyBuildingRecord;
import com.jfeat.am.module.house.services.domain.service.HousePropertyCommunityOverModelService;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyCommunityModel;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHousePropertyCommunityOverModelServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyBuildingMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HousePropertyCommunityMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuilding;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyCommunity;
import com.jfeat.am.uaas.tenant.services.gen.persistence.dao.TenantMapper;
import com.jfeat.am.uaas.tenant.services.gen.persistence.model.Tenant;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.plus.DefaultFilterResult;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("housePropertyCommunityService")
public class HousePropertyCommunityOverModelServiceImpl extends CRUDHousePropertyCommunityOverModelServiceImpl implements HousePropertyCommunityOverModelService {

    @Resource
    HousePropertyBuildingMapper housePropertyBuildingMapper;

    @Resource
    HousePropertyCommunityMapper housePropertyCommunityMapper;

    @Resource
    TenantMapper tenantMapper;

    @Resource
    HousePropertyCommunityOverModelService housePropertyCommunityOverModelService;


    @Override
    protected String entityName() {
        return "HousePropertyCommunity";
    }


    /**
     * 删除小区 当全部删除完后修改社区表的has_children字段
     * @param id
     * @return
     */
    @Override
    @Transactional
    public int deleteCommunity(Long id) {

        Integer affect = 0;

//        判断小区下是否有楼栋
        QueryWrapper<HousePropertyBuilding> buildingQueryWrapper = new QueryWrapper<>();
        buildingQueryWrapper.eq(HousePropertyBuilding.COMMUNITY_ID,id);
        List<HousePropertyBuilding> buildingList = housePropertyBuildingMapper.selectList(buildingQueryWrapper);
        if (buildingList!=null && buildingList.size()>0){
            throw new BusinessException(BusinessCode.CodeBase,"已经配置房屋，不可以删除");
        }

        HousePropertyCommunity housePropertyCommunity =  housePropertyCommunityMapper.selectById(id);

//        判断删除后是否还有小区，如果没有小区将社区的has_children字段设置为0
        if (housePropertyCommunity!=null){
            QueryWrapper<HousePropertyCommunity> communityQueryWrapper = new QueryWrapper<>();
            communityQueryWrapper.eq(HousePropertyCommunity.TENANT_ID,housePropertyCommunity.getTenant());
            List<HousePropertyCommunity> communityList = housePropertyCommunityMapper.selectList(communityQueryWrapper);

            affect = housePropertyCommunityMapper.deleteById(id);
            if (affect>0 && communityList!=null && communityList.size()<=1){
                Tenant tenant = tenantMapper.selectById(housePropertyCommunity.getTenantId());
                if (tenant!=null){
                    tenant.setHasChildren(false);
                    affect +=  tenantMapper.updateById(tenant);
                }
            }
            return affect;
        }



        return 0;
    }

    /**
     * 创建小区 更新社区表t_teant 的has_children
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public int createCommunity(HousePropertyCommunityModel entity) {

        Integer affected = 0;
        try {
            DefaultFilterResult filterResult = new DefaultFilterResult();
            affected = housePropertyCommunityOverModelService.createMaster(entity, filterResult, null, null);
            if (affected > 0) {
                Tenant tenant = tenantMapper.selectById(entity.getTenantId());
                if (tenant!=null){
                    tenant.setHasChildren(true);
                    affected +=  tenantMapper.updateById(tenant);
                }
            }
            return affected;
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }
    }
}
