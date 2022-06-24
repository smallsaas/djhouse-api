package com.jfeat.am.module.house.api.userself;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseEquityDemandSupplyDao;
import com.jfeat.am.module.house.services.domain.model.HouseEquityDemandSupplyRecord;
import com.jfeat.am.module.house.services.domain.service.HouseEquityDemandSupplyService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseEquityDemandSupply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;


@RestController
@Api("HouseEquityDemandSupply")
@RequestMapping("/api/u/house/houseEquityDemandSupply/houseEquityDemandSupplyies")
public class UserEquityDemandSupplyEndpoint {
    @Resource
    HouseEquityDemandSupplyService houseEquityDemandSupplyService;

    @Resource
    QueryHouseEquityDemandSupplyDao queryHouseEquityDemandSupplyDao;

    @BusinessLog(name = "HouseEquityDemandSupply", value = "create HouseEquityDemandSupply")
    @PostMapping
    @ApiOperation(value = "新建 HouseEquityDemandSupply", response = HouseEquityDemandSupply.class)
    public Tip createHouseEquityDemandSupply(@RequestBody HouseEquityDemandSupply entity) {
        System.out.println(JWTKit.getUserId());
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        Long userId = JWTKit.getUserId();
        entity.setUserId(userId);
        Integer affected = 0;
        try {
            affected = houseEquityDemandSupplyService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }



    @ApiOperation(value = "HouseEquityDemandSupply 列表信息", response = HouseEquityDemandSupplyRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "userId", dataType = "Long"),
            @ApiImplicitParam(name = "equityOption", dataType = "Integer"),
            @ApiImplicitParam(name = "area", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseEquityDemandSupplyPage(Page<HouseEquityDemandSupplyRecord> page,
                                                @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                // for tag feature query
                                                @RequestParam(name = "tag", required = false) String tag,
                                                // end tag
                                                @RequestParam(name = "search", required = false) String search,

                                                @RequestParam(name = "userId", required = false) Long userId,

                                                @RequestParam(name = "equityOption", required = false) Integer equityOption,

                                                @RequestParam(name = "area", required = false) BigDecimal area,
                                                @RequestParam(name = "orderBy", required = false) String orderBy,
                                                @RequestParam(name = "sort", required = false) String sort) {

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

        HouseEquityDemandSupplyRecord record = new HouseEquityDemandSupplyRecord();
        record.setUserId(userId);
        record.setEquityOption(equityOption);
        record.setArea(area);


        List<HouseEquityDemandSupplyRecord> houseEquityDemandSupplyPage = queryHouseEquityDemandSupplyDao.findHouseEquityDemandSupplyPage(page, record, tag, search, orderBy, null, null,null);

        for (HouseEquityDemandSupply houseEquityDemandSupply:houseEquityDemandSupplyPage){
            houseEquityDemandSupply.setUserId(null);
            houseEquityDemandSupply.setPhoneNumber(null);
        }

        page.setRecords(houseEquityDemandSupplyPage);

        return SuccessTip.create(page);
    }
}
