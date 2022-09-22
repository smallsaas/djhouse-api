package com.jfeat.am.module.house.api.app.operations;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseMenuDao;
import com.jfeat.am.module.house.services.domain.model.HouseMenuRecord;
import com.jfeat.am.module.house.services.domain.model.HouseTenantMenuRecord;
import com.jfeat.am.module.house.services.domain.service.HouseTenantMenuService;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseTenantMenuMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseMenu;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseTenantMenu;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/operations/userFunctionSecondaryManage")
public class UserFunctionSecondaryManageEndpoint {

    @Resource
    QueryHouseMenuDao queryHouseMenuDao;

    @Resource
    HouseTenantMenuMapper houseTenantMenuMapper;

    @Resource
    HouseTenantMenuService houseTenantMenuService;

    @RequestMapping("/getMenuList")
    public Tip getSecondaryMenuStatusPage(Page<HouseMenuRecord> page,
                                          @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                          @RequestParam(name = "search", required = false) String search,
                                          @RequestParam(name = "type",required = false) String type,
                                          @RequestParam(name = "enabled", required = false) Integer enabled) {


        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (JWTKit.getOrgId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "社区不能为空");
        }
        Long orgId = JWTKit.getOrgId();

        page.setCurrent(pageNum);
        page.setSize(pageSize);

        HouseMenuRecord record = new HouseMenuRecord();
        record.setEnabled(enabled);
        record.setType(type);
        List<HouseMenuRecord> houseMenuPage = queryHouseMenuDao.findHouseMenuPage(page, record, null, search, null, null, null);

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
        page.setRecords(houseMenuPage);
        return SuccessTip.create(page);
    }

    @PutMapping("/updateHouseMenu")
    public Tip updateSecondaryMenuStatus(@RequestBody HouseMenu entity) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        return SuccessTip.create(houseTenantMenuService.updateMenuStatus(entity));
    }


}
