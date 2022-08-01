package com.jfeat.am.module.house.api.userself.operations;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseMenuDao;
import com.jfeat.am.module.house.services.domain.model.HouseMenuRecord;
import com.jfeat.am.module.house.services.domain.service.HouseMenuService;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseMenuMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseMenu;
import com.jfeat.am.module.house.services.utility.Authentication;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/operations/UserFunctionManageEndpoint")
public class UserFunctionManageEndpoint {

    @Resource
    HouseMenuService houseMenuService;

    @Resource
    QueryHouseMenuDao queryHouseMenuDao;

    @Resource
    HouseMenuMapper houseMenuMapper;
    @Resource
    Authentication authentication;


    @GetMapping
    public Tip queryHouseMenuPage(Page<HouseMenuRecord> page,
                                  @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                  // for tag feature query
                                  @RequestParam(name = "tag", required = false) String tag,
                                  // end tag
                                  @RequestParam(name = "search", required = false) String search,

                                  @RequestParam(name = "url", required = false) String url,

                                  @RequestParam(name = "path", required = false) String path,

                                  @RequestParam(name = "component", required = false) String component,

                                  @RequestParam(name = "name", required = false) String name,

                                  @RequestParam(name = "iconCls", required = false) String iconCls,

                                  @RequestParam(name = "requireAuth", required = false) Integer requireAuth,

                                  @RequestParam(name = "parentId", required = false) Long parentId,

                                  @RequestParam(name = "enabled", required = false) Integer enabled,
                                  @RequestParam(name = "orderBy", required = false) String orderBy,
                                  @RequestParam(name = "sort", required = false) String sort) {

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }

        if (orderBy != null && orderBy.length() > 0) {
            if (sort != null && sort.length() > 0) {
                String sortPattern = "(ASC|DESC|asc|desc)";
                if (!sort.matches(sortPattern)) {
                    throw new BusinessException(BusinessCode.BadRequest.getCode(), "sort must be ASC or DESC");//此处异常类型根据实际情况而定
                }
            } else {
                sort = "ASC";
            }
            orderBy = "`" + orderBy + "`" + " " + sort;
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        HouseMenuRecord record = new HouseMenuRecord();
        record.setUrl(url);
        record.setPath(path);
        record.setComponent(component);
        record.setName(name);
        record.setIconCls(iconCls);
        record.setRequireAuth(requireAuth);
        record.setParentId(parentId);
        record.setEnabled(enabled);


        List<HouseMenuRecord> houseMenuPage = queryHouseMenuDao.findHouseMenuPage(page, record, tag, search, orderBy, null, null);
        page.setRecords(houseMenuPage);

        return SuccessTip.create(page);
    }

    /*
    批量修改功能管理状态
     */
    @PutMapping
    public Tip updateHouseMenus(@RequestBody List<HouseMenu> houseMenuList){
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }
        Integer effect =0;
        for (int i=0;i<houseMenuList.size();i++){

            effect+=houseMenuMapper.updateById(houseMenuList.get(i));
        }
        return SuccessTip.create(effect);
    }

    /*
   批量修改功能管理状态
    */
    @PutMapping("/updateHouseMenu")
    public Tip updateHouseMenu(@RequestBody HouseMenu entity){
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        if (!authentication.verifyOperation(JWTKit.getUserId())){
            throw new BusinessException(BusinessCode.NoPermission,"该用户没有权限");
        }
        QueryWrapper<HouseMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseMenu.ID,entity.getId());
        HouseMenu houseMenu = houseMenuMapper.selectOne(queryWrapper);
        if (houseMenu!=null){
            houseMenu.setEnabled(entity.getEnabled());
            return SuccessTip.create(houseMenuMapper.updateById(houseMenu));
        }
        return SuccessTip.create();
    }


}
