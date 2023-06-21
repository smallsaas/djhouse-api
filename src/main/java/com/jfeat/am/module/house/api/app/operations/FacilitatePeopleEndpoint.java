package com.jfeat.am.module.house.api.app.operations;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.domain.model.FacilitatePeopleRecord;
import com.jfeat.am.module.house.services.domain.service.FacilitatePeopleService;
import com.jfeat.am.module.house.services.gen.persistence.model.FacilitatePeople;
import com.jfeat.am.module.house.services.utility.UserAccountUtility;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description: facilitatePeople controller: 便民服务控制器
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/2/24 11:55
 * @author: hhhhhtao
 */
@RestController
@RequestMapping("/api/u/house/operations/facilitate-people")
public class FacilitatePeopleEndpoint {

    @Resource
    FacilitatePeopleService facilitatePeopleService;

    @Resource
    UserAccountUtility userAccountUtility; // 用户权限判断工具类

    /**
     * 分页获取便民服务列表
     *
     * @param pageNum
     * @param pageSize
     * @param search 搜索关键词
     * @return
     */
    @GetMapping()
    public Tip findFacilitatePeople(@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                    @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                    @RequestParam(name = "search", required = false) String search) {

        Page<FacilitatePeopleRecord> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        return SuccessTip.create(facilitatePeopleService.findFacilitatePeople(page, search));
    }

    /**
     * 增加指定便民服务的拨打次数
     *
     * @param id 便民服务id
     * @return 当前的次数
     */
    @PatchMapping("/addFrequency/{id}")
    public Tip addFacilitatePeoPleDialFrequency(@PathVariable("id") Integer id) {

        return SuccessTip.create(facilitatePeopleService.facilitatePeoPleDialFrequencyAddOne(id));
    }

    /**
     * 以下属于管理员操作，均需社区管理员权限才可访问
     */

    /**
     * 获取指定便民服务详情
     *
     * @param id 便民服务id
     * @return
     */
    @GetMapping("/{id}")
    public Tip getFacilitatePeople(@PathVariable Integer id) {

        // 判断用户是否拥有社区管理员权限
        if (!(userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_OPERATION)))
            throw new BusinessException(BusinessCode.NoPermission, "没有社区管理权");

        return SuccessTip.create(facilitatePeopleService.getFacilitatePeople(id));
    }

    /**
     * 创建便民服务
     *
     * @param facilitatePeople 便民服务实体
     * @return
     */
    @PostMapping()
    public Tip saveFacilitatePeople(@RequestBody FacilitatePeople facilitatePeople) {

        // 判断用户是否拥有社区管理员权限
        if (!(userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_OPERATION)))
            throw new BusinessException(BusinessCode.NoPermission, "没有社区管理权");

        return SuccessTip.create(facilitatePeopleService.saveFacilitatePeople(facilitatePeople));
    }

    /**
     * 管理员-分页获取便民服务列表
     *
     * @param pageNum
     * @param pageSize
     * @param search 搜索关键词
     * @return
     */
    @GetMapping("/management")
    public Tip managementFindFacilitatePeople(@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                              @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                              @RequestParam(name = "search", required = false) String search) {

        // 判断用户是否拥有社区管理员权限
        if (!(userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_OPERATION)))
            throw new BusinessException(BusinessCode.NoPermission, "没有社区管理权");

        Page<FacilitatePeople> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        return SuccessTip.create(facilitatePeopleService.managementFindFacilitatePeople(page, search));
    }

    /**
     * 修改便民服务
     *
     * @param facilitatePeople
     * @return
     */
    @PutMapping()
    public Tip updateFacilitatePeople(@RequestBody FacilitatePeople facilitatePeople) {

        // 判断用户是否拥有社区管理员权限
        if (!(userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_OPERATION)))
            throw new BusinessException(BusinessCode.NoPermission, "没有社区管理权");

        return SuccessTip.create(facilitatePeopleService.updateFacilitatePeople(facilitatePeople));
    }

    /**
     * 关闭指定便民服务
     *
     * @param id 便民服务记录id
     * @return
     */
    @PutMapping("/close/{id}")
    public Tip closeFacilitatePeople(@PathVariable Integer id) {

        // 判断用户是否拥有社区管理员权限
        if (!(userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_OPERATION)))
            throw new BusinessException(BusinessCode.NoPermission, "没有社区管理权");

        return SuccessTip.create(facilitatePeopleService.updateFacilitatePeopleOfStatusClose(id));
    }

    /**
     * 开启指定便民服务
     *
     * @param id
     * @return
     */
    @PutMapping("/open/{id}")
    public Tip openFacilitatePeople(@PathVariable Integer id) {

        // 判断用户是否拥有社区管理员权限
        if (!(userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_OPERATION)))
            throw new BusinessException(BusinessCode.NoPermission, "没有社区管理权");

        return SuccessTip.create(facilitatePeopleService.updateFacilitatePeopleOfStatusOpen(id));
    }

    /**
     * 删除指定便民服务
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Tip removeFacilitatePeople(@PathVariable("id") Integer id) {

        // 社区管理员权限判断
        if (!(userAccountUtility.judgementJurisdiction(EndUserTypeSetting.USER_TYPE_OPERATION)))
            throw new BusinessException(BusinessCode.NoPermission, "没有社区管理权");

        return SuccessTip.create(facilitatePeopleService.removeFacilitatePeople(id));
    }

}
