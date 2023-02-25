package com.jfeat.am.module.house.api.app.operations;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.house.services.domain.model.FacilitatePeopleRecord;
import com.jfeat.am.module.house.services.domain.service.FacilitatePeopleService;
import com.jfeat.am.module.house.services.gen.persistence.model.FacilitatePeople;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
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
                                    @RequestParam(name = "serverName",required = false) String serverName) {

        Page<FacilitatePeople> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        return SuccessTip.create(facilitatePeopleService.findFacilitatePeople(page,serverName));

    }

    @GetMapping("/{id}")
    public Tip getFacilitatePeople(@PathVariable Integer id) {

        return SuccessTip.create(facilitatePeopleService.getFacilitatePeople(id));
    }

    @PostMapping()
    public Tip saveFacilitatePeople(@RequestBody FacilitatePeopleRecord facilitatePeopleRecord) {

        return SuccessTip.create(facilitatePeopleService.saveFacilitatePeople(facilitatePeopleRecord));
    }

}