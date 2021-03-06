package com.jfeat.am.module.house.api.userself;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.service.HouseUserAssetService;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseEquityDemandSupplyDao;
import com.jfeat.am.module.house.services.domain.model.HouseEquityDemandSupplyRecord;
import com.jfeat.am.module.house.services.domain.service.HouseEquityDemandSupplyService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseEquityDemandSupply;
import com.jfeat.crud.base.util.DateTimeKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


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
        entity.setCreateTime(new Date());

        Integer affected = 0;

        HouseEquityDemandSupplyRecord record = new HouseEquityDemandSupplyRecord();
        record.setUserId(JWTKit.getUserId());
        List<HouseEquityDemandSupplyRecord> houseEquityDemandSupplyPage = queryHouseEquityDemandSupplyDao.findHouseEquityDemandSupplyPage(null, record, null, null, null, null, null, null, null);
        if (houseEquityDemandSupplyPage==null|| houseEquityDemandSupplyPage.size()==0){
            try {
                affected = houseEquityDemandSupplyService.createMaster(entity);
            } catch (DuplicateKeyException e) {
                throw new BusinessException(BusinessCode.DuplicateKey);
            }
        }
        if (houseEquityDemandSupplyPage!=null && houseEquityDemandSupplyPage.size()==1){
            entity.setId(houseEquityDemandSupplyPage.get(0).getId());
            return SuccessTip.create(houseEquityDemandSupplyService.updateMaster(entity));
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
                                                @RequestParam(name = "leftRange", required = false) Double leftRange,
                                                @RequestParam(name = "rightRange", required = false) Double rightRange,
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


        List<HouseEquityDemandSupplyRecord> houseEquityDemandSupplyPage = queryHouseEquityDemandSupplyDao.findHouseEquityDemandSupplyPage(page, record, tag, search, orderBy, null, null, leftRange, rightRange);

        for (HouseEquityDemandSupply houseEquityDemandSupply : houseEquityDemandSupplyPage) {
            houseEquityDemandSupply.setUserId(null);
            houseEquityDemandSupply.setUsername(null);
            String number = houseEquityDemandSupply.getPhoneNumber();

            houseEquityDemandSupply.setPhoneNumber(number.substring(0, 3).concat("********").concat(number.substring(number.length() - 1, number.length())));
            houseEquityDemandSupply.setUserAvatar(null);
        }
        page.setRecords(houseEquityDemandSupplyPage);

        return SuccessTip.create(page);
    }

    @GetMapping("/userDemandSupply")
    public Tip getDemandSupply(
            Page<HouseEquityDemandSupplyRecord> page, @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "area", required = false) BigDecimal area) {

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        HouseEquityDemandSupplyRecord record = new HouseEquityDemandSupplyRecord();
        record.setArea(area);
        record.setUserId(JWTKit.getUserId());

        Double leftRange = null;
        Double rightRange = null;
        Integer optionStatus = null;



        List<HouseEquityDemandSupplyRecord> houseEquityDemandSupplyPage = queryHouseEquityDemandSupplyDao.findHouseEquityDemandSupplyPage(page, record, null, search, null, null, null, leftRange, rightRange);
        HouseEquityDemandSupplyRecord resultRecord = new HouseEquityDemandSupplyRecord();

        List<HouseEquityDemandSupplyRecord> resultHouseEquityDemandSupplyPage =null;


        if (houseEquityDemandSupplyPage!=null && houseEquityDemandSupplyPage.size()==1){
            optionStatus = houseEquityDemandSupplyPage.get(0).getEquityOption();
            if (optionStatus!=null && optionStatus.equals(1)){
                leftRange = houseEquityDemandSupplyPage.get(0).getArea().doubleValue();
                resultRecord.setEquityOption(2);
            }
            if (optionStatus!=null && optionStatus.equals(2)){
                rightRange= houseEquityDemandSupplyPage.get(0).getArea().doubleValue();
                resultRecord.setEquityOption(1);
            }

            resultHouseEquityDemandSupplyPage = queryHouseEquityDemandSupplyDao.findHouseEquityDemandSupplyPage(page, resultRecord, null, search, null, null, null, leftRange, rightRange);
            for (HouseEquityDemandSupply houseEquityDemandSupply : resultHouseEquityDemandSupplyPage) {
                houseEquityDemandSupply.setUserId(null);
                if (houseEquityDemandSupply.getCreateTime()!=null){
                    houseEquityDemandSupply.setSimpleTime(DateTimeKit.toTimeline(houseEquityDemandSupply.getCreateTime()));
                }

                String name  = houseEquityDemandSupply.getUsername();
                if (name!=null&&!("".equals(name))){
                   houseEquityDemandSupply.setUsername(name.substring(0,1).concat("*"));
                }
                houseEquityDemandSupply.setUserAvatar(null);
                String number = houseEquityDemandSupply.getPhoneNumber();
                if (number!=null && number.length()>=3){
                    houseEquityDemandSupply.setPhoneNumber(number.substring(0, 1).concat("*********").concat(number.substring(number.length() - 1, number.length())));

                }

            }
        }
        page.setRecords(resultHouseEquityDemandSupplyPage);

        return SuccessTip.create(page);
    }

//    返回自己的权益状态数据
    @GetMapping("/userStatus")
    public Tip getUserStatus(){
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        HouseEquityDemandSupplyRecord record = new HouseEquityDemandSupplyRecord();
        record.setUserId(JWTKit.getUserId());

        Integer optionStatus = null;
        List<HouseEquityDemandSupplyRecord> houseEquityDemandSupplyPage = queryHouseEquityDemandSupplyDao.findHouseEquityDemandSupplyPage(null, record, null, null, null, null, null, null, null);
        HouseEquityDemandSupplyRecord houseEquityDemandSupplyRecord = null;
        if (houseEquityDemandSupplyPage!=null && houseEquityDemandSupplyPage.size()==1){
            houseEquityDemandSupplyRecord = houseEquityDemandSupplyPage.get(0);
        }

        return SuccessTip.create(houseEquityDemandSupplyRecord);
    }


}
