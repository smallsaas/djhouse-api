package com.jfeat.am.module.house.services.domain.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseMenuDao;
import com.jfeat.am.module.house.services.domain.model.HouseMenuRecord;
import com.jfeat.am.module.house.services.domain.service.HouseTenantMenuService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseTenantMenuServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseMenuMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseTenantMenuMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseMenu;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseTenantMenu;
import com.jfeat.am.module.house.services.utility.TenantUtility;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.springframework.stereotype.Service;

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

@Service("houseTenantMenuService")
public class HouseTenantMenuServiceImpl extends CRUDHouseTenantMenuServiceImpl implements HouseTenantMenuService {

    @Resource
    HouseTenantMenuMapper houseTenantMenuMapper;

    @Resource
    HouseMenuMapper houseMenuMapper;

    @Resource
    QueryHouseMenuDao queryHouseMenuDao;

    @Resource
    TenantUtility tenantUtility;

    @Override
    protected String entityName() {
        return "HouseTenantMenu";
    }


    @Override
    public int updateMenuStatus(HouseMenu entity) {
        if (entity.getId()==null || JWTKit.getOrgId()==null){
            return 0;
        }

//        查询一级菜单是否开启功能
        HouseMenu houseMenu =  houseMenuMapper.selectById(entity.getId());
        if (!houseMenu.getEnabled().equals(1)){
            throw new BusinessException(BusinessCode.NoPermission,"该功能暂时没有开放");
        }

        Integer affect = 0;

        QueryWrapper<HouseTenantMenu> tenantMenuQueryWrapper = new QueryWrapper<>();
        tenantMenuQueryWrapper.eq(HouseTenantMenu.ORG_ID, JWTKit.getOrgId()).eq(HouseTenantMenu.MENU_ID,entity.getId());

//        查询二级菜单表中是否有该功能记录 没有就添加
        HouseTenantMenu houseTenantMenu =  houseTenantMenuMapper.selectOne(tenantMenuQueryWrapper);
        if (houseTenantMenu==null){
            HouseTenantMenu menu = new HouseTenantMenu();
            menu.setMenuId(entity.getId());
            menu.setOrgId(JWTKit.getOrgId());
            menu.setEnabled(entity.getEnabled());
            affect+= houseTenantMenuMapper.insert(menu);
        }else {
            houseTenantMenu.setEnabled(entity.getEnabled());
            affect+=houseTenantMenuMapper.updateById(houseTenantMenu);
        }
        return affect;
    }

    @Override
    public List<HouseMenuRecord> getSecondaryMenu(Long userId,HouseMenuRecord record) {

        Long orgId = tenantUtility.getCurrentOrgId(userId);

        List<HouseMenuRecord> houseMenuPage = queryHouseMenuDao.findHouseMenuPage(null, record, null, null, null, null, null);

//        查询当前社区功能状态开启情况
        QueryWrapper<HouseTenantMenu> tenantMenuQueryWrapper = new QueryWrapper<>();
        tenantMenuQueryWrapper.eq(HouseTenantMenu.ORG_ID, orgId);
        List<HouseTenantMenu> tenantMenus = houseTenantMenuMapper.selectList(tenantMenuQueryWrapper);


        for (HouseMenuRecord houseMenuRecord : houseMenuPage) {
            for (HouseTenantMenu houseTenantMenu : tenantMenus) {
//                当二级菜单有一级菜单时，将菜单状态设置为二级菜单状态
                if (houseMenuRecord.getId().equals(houseTenantMenu.getMenuId())) {
                    houseMenuRecord.setEnabled(houseTenantMenu.getEnabled());
                    break;
                } else {
                    houseMenuRecord.setEnabled(0);
                }
            }
        }
        return houseMenuPage;
    }
}
