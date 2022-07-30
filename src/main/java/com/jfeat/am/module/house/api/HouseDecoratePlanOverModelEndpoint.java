
package com.jfeat.am.module.house.api;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseDecoratePlanFunitureDao;
import com.jfeat.am.module.house.services.gen.crud.model.HouseDecoratePlanFunitureModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseDecoratePlanFunitureMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.ProductMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDecoratePlanFuniture;
import com.jfeat.am.module.house.services.gen.persistence.model.Product;
import com.jfeat.crud.plus.META;
import com.jfeat.am.core.jwt.JWTKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.dao.DuplicateKeyException;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseDecoratePlanDao;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.request.Ids;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.plus.CRUDObject;
import com.jfeat.crud.plus.DefaultFilterResult;
import com.jfeat.am.module.house.api.permission.*;
import com.jfeat.am.common.annotation.Permission;

import java.math.BigDecimal;

import com.jfeat.am.module.house.services.domain.service.*;
import com.jfeat.am.module.house.services.domain.model.HouseDecoratePlanRecord;
import com.jfeat.am.module.house.services.gen.crud.model.HouseDecoratePlanModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDecoratePlan;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

/**
 * <p>
 * api
 * </p>
 *
 * @author Code generator
 * @since 2022-05-27
 */
@RestController
@Api("HouseDecoratePlan")
@RequestMapping("/api/crud/house/houseDecoratePlan/houseDecoratePlans")
public class HouseDecoratePlanOverModelEndpoint {

    @Resource
    HouseDecoratePlanOverModelService houseDecoratePlanOverModelService;

    @Resource
    QueryHouseDecoratePlanDao queryHouseDecoratePlanDao;

    @Resource
    QueryHouseDecoratePlanFunitureDao queryHouseDecoratePlanFunitureDao;

    @Resource
    HouseDecoratePlanFunitureMapper houseDecoratePlanFunitureMapper;

    @Resource
    ProductMapper productMapper;


    // 要查询[从表]关联数据，取消下行注释
    // @Resource
    // QueryProductDao queryProductDao;

    @BusinessLog(name = "HouseDecoratePlan", value = "create HouseDecoratePlan")
    @Permission(HouseDecoratePlanPermission.HOUSEDECORATEPLAN_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseDecoratePlan", response = HouseDecoratePlanModel.class)
    public Tip createHouseDecoratePlan(@RequestBody HouseDecoratePlanModel entity) {
        Integer affected = 0;
        try {
            DefaultFilterResult filterResult = new DefaultFilterResult();
            affected = houseDecoratePlanOverModelService.createMaster(entity, filterResult, null, null);
            System.out.println(entity);
            if (affected > 0) {
                return SuccessTip.create(filterResult.result());
            }
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @BusinessLog(name = "HouseDecoratePlan", value = "查看 HouseDecoratePlanModel")
    @Permission(HouseDecoratePlanPermission.HOUSEDECORATEPLAN_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseDecoratePlan", response = HouseDecoratePlanModel.class)
    public Tip getHouseDecoratePlan(@PathVariable Long id) {
        CRUDObject<HouseDecoratePlanModel> entity = houseDecoratePlanOverModelService
                .registerQueryMasterDao(queryHouseDecoratePlanDao)
                // 要查询[从表]关联数据，取消下行注释
                //.registerQuerySlaveModelListDao(Product.class, queryProductDao)
                .retrieveMaster(id, null, null, null);

        // sample query for registerQueryMasterDao
        // e.g. <select id="queryMasterModel" resultType="PlanModel">
        //       SELECT t_plan_model.*, t_org.name as orgName
        //       FROM t_plan_model
        //       LEFT JOIN t_org ON t_org.id==t_plan_model.org_id
        //       WHERE t_plan_model.id=#{id}
        //       GROUP BY t_plan_model.id
        //    </select>

        QueryWrapper<HouseDecoratePlanFuniture> houseDecoratePlanFunitureQueryWrapper = new QueryWrapper<>();
        houseDecoratePlanFunitureQueryWrapper.eq(HouseDecoratePlanFuniture.DECORATE_PLAN_ID,id);
        List<HouseDecoratePlanFuniture> houseDecoratePlanFunitureList = houseDecoratePlanFunitureMapper.selectList(houseDecoratePlanFunitureQueryWrapper);


        if (entity != null) {
            List<HouseDecoratePlanFuniture> funitureList = new ArrayList<>();
            JSONObject jsonObject = entity.toJSONObject();
            JSONArray jsonArray  = (JSONArray) jsonObject.get("items");
            for (int i=0;i<jsonArray.size();i++){
                for (int j=0;j<houseDecoratePlanFunitureList.size();j++){
                    JSONObject item = (JSONObject) jsonArray.get(i);
                    Long itemId = item.getLong("id");
                    if (houseDecoratePlanFunitureList.get(j).getFurnitureId().equals(itemId)){
                        HouseDecoratePlanFuniture funitureModel =  houseDecoratePlanFunitureList.get(j);
                        funitureModel.setName(item.getString("name"));
                        funitureModel.setPrice(item.getBigDecimal("price"));
                        funitureList.add(funitureModel);
                    }
                }
            }
            jsonObject.put("funitureList",funitureList);
            return SuccessTip.create(jsonObject);
        } else {
            return SuccessTip.create();
        }

    }

    @BusinessLog(name = "HouseDecoratePlan", value = "update HouseDecoratePlan")
    @Permission(HouseDecoratePlanPermission.HOUSEDECORATEPLAN_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseDecoratePlan", response = HouseDecoratePlanModel.class)
    public Tip updateHouseDecoratePlan(@PathVariable Long id, @RequestBody HouseDecoratePlanModel entity) {
        entity.setId(id);
        int newOptions = META.UPDATE_CASCADING_DELETION_FLAG;
        int effect = houseDecoratePlanOverModelService.updateMaster(entity, null, null, null, newOptions);

        QueryWrapper<HouseDecoratePlanFuniture> houseDecoratePlanFunitureQueryWrapper = new QueryWrapper<>();
        houseDecoratePlanFunitureQueryWrapper.eq(HouseDecoratePlanFuniture.DECORATE_PLAN_ID,id);

        List<HouseDecoratePlanFuniture> houseDecoratePlanFunitureList = new ArrayList<>();
        if (entity.getFurnitureItem()!=null){
            houseDecoratePlanFunitureMapper.delete(houseDecoratePlanFunitureQueryWrapper);
            houseDecoratePlanFunitureList = JSONObject.parseArray(entity.getFurnitureItem(),HouseDecoratePlanFuniture.class);
            System.out.println(houseDecoratePlanFunitureList);

        }

        for (int i=0;i<houseDecoratePlanFunitureList.size();i++){
            if (houseDecoratePlanFunitureList.get(i).getDecoratePlanId()==null || "".equals(houseDecoratePlanFunitureList.get(i).getDecoratePlanId())){
                houseDecoratePlanFunitureList.get(i).setDecoratePlanId(id);
            }
        }
        if (houseDecoratePlanFunitureList!=null && houseDecoratePlanFunitureList.size()>0){
            queryHouseDecoratePlanFunitureDao.insertDecoratePlanFunitures(houseDecoratePlanFunitureList);
        }


        return SuccessTip.create(effect);
    }

    @BusinessLog(name = "HouseDecoratePlan", value = "delete HouseDecoratePlan")
    @Permission(HouseDecoratePlanPermission.HOUSEDECORATEPLAN_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseDecoratePlan")
    public Tip deleteHouseDecoratePlan(@PathVariable Long id) {
        return SuccessTip.create(houseDecoratePlanOverModelService.deleteMaster(id));
    }

    @Permission(HouseDecoratePlanPermission.HOUSEDECORATEPLAN_VIEW)
    @ApiOperation(value = "HouseDecoratePlan 列表信息", response = HouseDecoratePlanRecord.class)
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", dataType = "Integer"),
            @ApiImplicitParam(name = "search", dataType = "String"),
            @ApiImplicitParam(name = "id", dataType = "Long"),
            @ApiImplicitParam(name = "decorateStyleName", dataType = "String"),
            @ApiImplicitParam(name = "allowChanged", dataType = "Integer"),
            @ApiImplicitParam(name = "colorStyle", dataType = "String"),
            @ApiImplicitParam(name = "totalBudget", dataType = "BigDecimal"),
            @ApiImplicitParam(name = "star", dataType = "Integer"),
            @ApiImplicitParam(name = "orderBy", dataType = "String"),
            @ApiImplicitParam(name = "sort", dataType = "String")
    })
    public Tip queryHouseDecoratePlanPage(Page<HouseDecoratePlanRecord> page,
                                          @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                          // for tag feature query
                                          @RequestParam(name = "tag", required = false) String tag,
                                          // end tag
                                          @RequestParam(name = "search", required = false) String search,

                                          @RequestParam(name = "decorateStyleName", required = false) String decorateStyleName,

                                          @RequestParam(name = "allowChanged", required = false) Integer allowChanged,

                                          @RequestParam(name = "colorStyle", required = false) String colorStyle,

                                          @RequestParam(name = "totalBudget", required = false) BigDecimal totalBudget,

                                          @RequestParam(name = "star", required = false) Integer star,
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

        HouseDecoratePlanRecord record = new HouseDecoratePlanRecord();
        record.setDecorateStyleName(decorateStyleName);
        record.setAllowChanged(allowChanged);
        record.setColorStyle(colorStyle);
        record.setTotalBudget(totalBudget);
        record.setStar(star);


        List<HouseDecoratePlanRecord> houseDecoratePlanPage = queryHouseDecoratePlanDao.findHouseDecoratePlanPage(page, record, tag, search, orderBy, null, null);
        for (int i=0;i<houseDecoratePlanPage.size();i++){
            Integer queryStar = queryHouseDecoratePlanDao.queryDecoratePlanStar(houseDecoratePlanPage.get(i).getId());
            if (queryStar!=null && queryStar>0){
                houseDecoratePlanPage.get(i).setStar(queryStar);
            }else {
                houseDecoratePlanPage.get(i).setStar(0);
            }

            Double totalBudegt = queryHouseDecoratePlanDao.queryDecoratePlanTotalPrice(houseDecoratePlanPage.get(i).getId());
            if (totalBudegt!=null && totalBudegt>=0){
                houseDecoratePlanPage.get(i).setTotalBudget(BigDecimal.valueOf(totalBudegt));
            }

        }

        page.setRecords(houseDecoratePlanPage);

        return SuccessTip.create(page);
    }
}

