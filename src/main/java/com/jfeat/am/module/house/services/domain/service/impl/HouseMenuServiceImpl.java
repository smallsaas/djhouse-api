package com.jfeat.am.module.house.services.domain.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseMenuDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseTenantMenuDao;
import com.jfeat.am.module.house.services.domain.service.HouseMenuService;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHouseMenuServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseTenantMenuMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseMenu;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseTenantMenu;
import com.jfeat.crud.base.tips.SuccessTip;
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

@Service("houseMenuService")
public class HouseMenuServiceImpl extends CRUDHouseMenuServiceImpl implements HouseMenuService {

    @Resource
    HouseTenantMenuMapper houseTenantMenuMapper;

    @Resource
    QueryHouseTenantMenuDao queryHouseTenantMenuDao;

    @Override
    protected String entityName() {
        return "HouseMenu";
    }


    @Override
    @Transactional
    public int updateMenuStatus(HouseMenu entity) {
        if (entity.getId()==null){
            return 0;
        }
        Integer affect = 0;
        HouseMenu houseMenu = houseMenuMapper.selectById(entity.getId());
        if (houseMenu != null) {
            houseMenu.setEnabled(entity.getEnabled());
            affect+=houseMenuMapper.updateById(houseMenu);
//            如果是关闭功能则全部二级功能关闭
            if (!entity.getEnabled().equals(1)){
                QueryWrapper<HouseTenantMenu> tenantMenuQueryWrapper = new QueryWrapper<>();
                tenantMenuQueryWrapper.eq(HouseTenantMenu.MENU_ID,entity.getId());
                List<HouseTenantMenu> houseTenantMenuList = houseTenantMenuMapper.selectList(tenantMenuQueryWrapper);
                for (HouseTenantMenu tenantMenu:houseTenantMenuList){
                    tenantMenu.setEnabled(0);
                }

                if (houseTenantMenuList!=null && houseTenantMenuList.size()>0){
                    affect+=queryHouseTenantMenuDao.updateTenantMenus(houseTenantMenuList);
                }

            }
        }
        return affect;
    }
}
