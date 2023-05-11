package com.jfeat.am.module.house.api.app.operations;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.house.services.domain.model.FacilitatePeopleRecord;
import com.jfeat.am.module.house.services.domain.service.FacilitatePeopleService;
import com.jfeat.am.module.house.services.gen.persistence.model.FacilitatePeople;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description: facilitatePeople controller: 便民服务接口
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

    @GetMapping()
    public Tip findFacilitatePeople(@RequestParam(name = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                                    @RequestParam(name = "pageSize",required = false,defaultValue = "10") Integer pageSize,
                                    @RequestParam(name = "search",required = false) String search) {

        Page<FacilitatePeopleRecord> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        return SuccessTip.create(facilitatePeopleService.findFacilitatePeople(page,search));

    }

    /**
     * 以下属于管理员操作，均需社区管理员权限才可访问
     */
    @GetMapping("/{id}")
    public Tip getFacilitatePeople(@PathVariable Integer id) {

        return SuccessTip.create(facilitatePeopleService.getFacilitatePeople(id));
    }

    @PostMapping()
    public Tip saveFacilitatePeople(@RequestBody FacilitatePeople facilitatePeople) {

        return SuccessTip.create(facilitatePeopleService.saveFacilitatePeople(facilitatePeople));
    }

    @GetMapping("/management")
    public Tip managementFindFacilitatePeople(@RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                              @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                              @RequestParam(name = "search", required = false) String search) {

        Page<FacilitatePeople> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        return SuccessTip.create(facilitatePeopleService.managementFindFacilitatePeople(page,search));
    }

    @PutMapping()
    public Tip updateFacilitatePeople(@RequestBody FacilitatePeople facilitatePeople) {

        return SuccessTip.create(facilitatePeopleService.updateFacilitatePeople(facilitatePeople));
    }

    /**
     * 关闭指定便民服务
     * @param id 便民服务记录id
     * @return
     */
    @PutMapping("/close/{id}")
    public Tip closeFacilitatePeople(@PathVariable Integer id) {

        return SuccessTip.create(facilitatePeopleService.updateFacilitatePeopleOfStatusClose(id));
    }

    /**
     * 开启指定便民服务
     * @param id
     * @return
     */
    @PutMapping("/open/{id}")
    public Tip openFacilitatePeople(@PathVariable Integer id) {

        return SuccessTip.create(facilitatePeopleService.updateFacilitatePeopleOfStatusOpen(id));
    }

    @DeleteMapping("/{id}")
    public Tip removeFacilitatePeople(@PathVariable(name = "id") Integer id) {

        return SuccessTip.create(facilitatePeopleService.removeFacilitatePeople(id));
    }

}
