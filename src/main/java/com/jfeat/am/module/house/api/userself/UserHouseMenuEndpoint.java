package com.jfeat.am.module.house.api.userself;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.api.permission.HouseMenuPermission;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseMenuDao;
import com.jfeat.am.module.house.services.domain.model.HouseMenuRecord;
import com.jfeat.am.module.house.services.domain.service.HouseMenuService;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/houseMenu/houseMenus")
public class UserHouseMenuEndpoint {

    @Resource
    HouseMenuService houseMenuService;

    @Resource
    QueryHouseMenuDao queryHouseMenuDao;

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
}
