package com.jfeat.am.module.house.api.userself;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.api.permission.HouseUserDecorateAddressPermission;
import com.jfeat.am.module.house.api.permission.HouseUserDecoratePlanPermission;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.*;
import com.jfeat.am.module.house.services.domain.service.HouseUserDecorateAddressService;
import com.jfeat.am.module.house.services.domain.service.HouseUserDecorateFunitureService;
import com.jfeat.am.module.house.services.domain.service.HouseUserDecoratePlanService;
import com.jfeat.am.module.house.services.gen.persistence.model.*;
import com.jfeat.am.module.order.definition.OrderType;
import com.jfeat.am.module.order.services.domain.model.OrderItemRecord;
import com.jfeat.am.module.order.services.domain.model.RequestOrder;
import com.jfeat.am.module.order.services.domain.service.OrderService;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.plus.CRUDObject;
import com.jfeat.am.module.house.api.permission.HouseDecoratePlanPermission;
import com.jfeat.am.module.house.services.domain.service.HouseDecoratePlanOverModelService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseDecoratePlanModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserDecoratePlanModel;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/u/house/houseUserDecoratePlan/houseUserDecoratePlans")
public class UserHouseDecoratePlanEndpoint {


    @Resource
    HouseDecoratePlanOverModelService houseDecoratePlanOverModelService;

    @Resource
    QueryHouseUserDecorateFunitureDao queryHouseUserDecorateFunitureDao;

    @Resource
    QueryHouseDecoratePlanFunitureDao queryHouseDecoratePlanFunitureDao;

    @Resource
    HouseUserDecorateFunitureService houseUserDecorateFunitureService;

    @Resource
    HouseUserDecoratePlanService houseUserDecoratePlanService;

    @Resource
    QueryHouseDecoratePlanDao queryHouseDecoratePlanDao;

    @Resource
    QueryHouseUserDecoratePlanDao queryHouseUserDecoratePlanDao;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    QueryProductDao queryProductDao;

    @Resource
    OrderService orderService;


    @ApiOperation(value = "HouseDecoratePlan ????????????", response = HouseDecoratePlanRecord.class)
    @GetMapping("/allDecoratePlans")
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
                    throw new BusinessException(BusinessCode.BadRequest.getCode(), "sort must be ASC or DESC");//??????????????????????????????????????????
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
        for (int i = 0; i < houseDecoratePlanPage.size(); i++) {
            Integer queryStar = queryHouseDecoratePlanDao.queryDecoratePlanStar(houseDecoratePlanPage.get(i).getId());
            if (queryStar != null && queryStar > 0) {
                houseDecoratePlanPage.get(i).setStar(queryStar);
            } else {
                houseDecoratePlanPage.get(i).setStar(0);
            }

            Double totalBudegt = queryHouseDecoratePlanDao.queryDecoratePlanTotalPrice(houseDecoratePlanPage.get(i).getId());
            if (totalBudegt != null && totalBudegt >= 0) {
                houseDecoratePlanPage.get(i).setTotalBudget(BigDecimal.valueOf(totalBudegt));
            }
        }

        page.setRecords(houseDecoratePlanPage);

        return SuccessTip.create(page);
    }


    //    ??????????????????
    @BusinessLog(name = "HouseUserDecoratePlan", value = "create HouseUserDecoratePlan")
    @PostMapping("/userDecoratePlanAddress")
    @ApiOperation(value = "?????? HouseUserDecoratePlan", response = HouseUserDecoratePlan.class)
    public Tip createHouseUserDecoratePlan(@RequestBody HouseUserDecoratePlan entity) {
        Integer affected = 0;
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }
        entity.setUserId(JWTKit.getUserId());
        entity.setOptionType(1);
        entity.setCreateTime(new Date());
        try {
            affected = houseUserDecoratePlanService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }
//        ?????????????????????????????? ???????????????????????????????????????
        if (affected.equals(1)) {
            HouseDecoratePlanFunitureRecord houseDecoratePlanFunitureRecord = new HouseDecoratePlanFunitureRecord();
            houseDecoratePlanFunitureRecord.setDecoratePlanId(entity.getDecoratePlanId());
            List<HouseDecoratePlanFunitureRecord> funitureRecordList = queryHouseDecoratePlanFunitureDao.findHouseDecoratePlanFuniturePage(null, houseDecoratePlanFunitureRecord, null, null, null, null, null);
//            ???????????????????????????
            for (int i = 0; i < funitureRecordList.size(); i++) {
                HouseUserDecorateFuniture houseUserDecorateFuniture = new HouseUserDecorateFuniture();
                houseUserDecorateFuniture.setUserId(JWTKit.getUserId());
                houseUserDecorateFuniture.setFunitureId(funitureRecordList.get(i).getFurnitureId());
                houseUserDecorateFuniture.setDecoratePlanId(funitureRecordList.get(i).getDecoratePlanId());
                houseUserDecorateFuniture.setAssetId(entity.getAssetId());
                houseUserDecorateFuniture.setFunitureNumber(funitureRecordList.get(i).getNumber());
                try {
                    affected += houseUserDecorateFunitureService.createMaster(houseUserDecorateFuniture);
                } catch (DuplicateKeyException e) {
                    throw new BusinessException(BusinessCode.DuplicateKey);
                }
            }

        }
        return SuccessTip.create(affected);
    }

    //    ??????????????????????????????
    @PutMapping("/userDecoratePlanAddress")
    public Tip updateHouseUserDecoratePlan(@RequestBody HouseUserDecoratePlan entity) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }
        if (entity.getDecoratePlanId() == null || entity.getAssetId() == null) {
            throw new BusinessException(BusinessCode.BadRequest);
        }
        Long decoratePlanId = entity.getDecoratePlanId();
        Long assetId = entity.getAssetId();

        HouseUserDecoratePlanRecord record = new HouseUserDecoratePlanRecord();
        record.setUserId(JWTKit.getUserId());
        record.setAssetId(assetId);
        record.setOptionType(1);
        List<HouseUserDecoratePlanRecord> houseUserDecoratePlanPage = queryHouseUserDecoratePlanDao.findHouseUserDecoratePlanPage(null, record, null, null, null, null, null);
        if (houseUserDecoratePlanPage != null && houseUserDecoratePlanPage.size() == 1) {
            Long oldUserDecoratePlanId  = houseUserDecoratePlanPage.get(0).getDecoratePlanId();

            HouseUserDecoratePlan houseUserDecoratePlan = new HouseUserDecoratePlan();
            houseUserDecoratePlan.setUserId(JWTKit.getUserId());
            houseUserDecoratePlan.setDecoratePlanId(decoratePlanId);
            houseUserDecoratePlan.setId(houseUserDecoratePlanPage.get(0).getId());
            houseUserDecoratePlan.setAssetId(assetId);
            houseUserDecoratePlan.setOptionType(1);
            houseUserDecoratePlan.setCreateTime(new Date());
            Integer affected = houseUserDecoratePlanService.updateMaster(houseUserDecoratePlan);

            if (affected > 0) {

//                ???????????????????????????
                HouseUserDecorateFunitureRecord houseUserDecorateFunitureRecord = new HouseUserDecorateFunitureRecord();
                houseUserDecorateFunitureRecord.setAssetId(assetId);
                houseUserDecorateFunitureRecord.setDecoratePlanId(oldUserDecoratePlanId);
                houseUserDecorateFunitureRecord.setUserId(JWTKit.getUserId());
                List<HouseUserDecorateFunitureRecord> userDecorateFunitureRecordList =queryHouseUserDecorateFunitureDao.findHouseUserDecorateFuniturePage(null,houseUserDecorateFunitureRecord,null
                ,null,null,null,null);
                for (HouseUserDecorateFunitureRecord houseDecoratePlanFunitureRecord:userDecorateFunitureRecordList){
                    houseUserDecorateFunitureService.deleteMaster(houseDecoratePlanFunitureRecord.getId());
                }

                //            ???????????????????????????
                HouseDecoratePlanFunitureRecord houseDecoratePlanFunitureRecord = new HouseDecoratePlanFunitureRecord();
                houseDecoratePlanFunitureRecord.setDecoratePlanId(decoratePlanId);
                List<HouseDecoratePlanFunitureRecord> funitureRecordList = queryHouseDecoratePlanFunitureDao.findHouseDecoratePlanFuniturePage(null, houseDecoratePlanFunitureRecord, null, null, null, null, null);
                for (int i = 0; i < funitureRecordList.size(); i++) {
                    HouseUserDecorateFuniture houseUserDecorateFuniture = new HouseUserDecorateFuniture();
                    houseUserDecorateFuniture.setUserId(JWTKit.getUserId());
                    houseUserDecorateFuniture.setFunitureId(funitureRecordList.get(i).getFurnitureId());
                    houseUserDecorateFuniture.setDecoratePlanId(funitureRecordList.get(i).getDecoratePlanId());
                    houseUserDecorateFuniture.setFunitureNumber(funitureRecordList.get(i).getNumber());
                    houseUserDecorateFuniture.setAssetId(assetId);
                    try {
                        affected += houseUserDecorateFunitureService.createMaster(houseUserDecorateFuniture);
                    } catch (DuplicateKeyException e) {
                        throw new BusinessException(BusinessCode.DuplicateKey);
                    }
                }

            }
            return SuccessTip.create(affected);
        }
        return SuccessTip.create(0);
    }


    //    ???????????????????????????????????????
    @GetMapping("/allUserDecoratePlan")
    public Tip getAllUserDecoratePlan(Page<HouseUserDecoratePlanRecord> page,
                                      @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                      @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }
        HouseUserDecoratePlanRecord record = new HouseUserDecoratePlanRecord();
        record.setUserId(JWTKit.getUserId());
        List<HouseUserDecoratePlanRecord> houseUserDecoratePlanPage = queryHouseUserDecoratePlanDao.findHouseUserDecoratePlanPage(page, record, null, null, null, null, null);
        if (houseUserDecoratePlanPage != null && houseUserDecoratePlanPage.size() > 0) {
            for (int i = 0; i < houseUserDecoratePlanPage.size(); i++) {

//                ??????????????????
                HouseAsset houseAsset = queryHouseAssetDao.queryMasterModel(houseUserDecoratePlanPage.get(i).getAssetId());
                if (houseAsset != null) {
                    houseUserDecoratePlanPage.get(i).setHouseAsset(houseAsset);
                }

//                ????????????????????????
                HouseDecoratePlan houseDecoratePlan = queryHouseDecoratePlanDao.queryMasterModel(houseUserDecoratePlanPage.get(i).getDecoratePlanId());
                if (houseDecoratePlan != null) {
                    Integer queryStar = queryHouseDecoratePlanDao.queryDecoratePlanStar(houseDecoratePlan.getId());
                    if (queryStar != null && queryStar > 0) {
                        houseDecoratePlan.setStar(queryStar);
                    } else {
                        houseDecoratePlan.setStar(0);
                    }

//                    ???????????????
                    Double totalBudegt = queryHouseDecoratePlanDao.queryDecoratePlanTotalPrice(houseDecoratePlan.getId());
                    if (totalBudegt != null && totalBudegt >= 0) {
                        houseDecoratePlan.setTotalBudget(BigDecimal.valueOf(totalBudegt));
                    }

//                    ????????????????????????
                    houseUserDecoratePlanPage.get(i).setHouseDecoratePlan(houseDecoratePlan);
                }
            }
        }
        page.setRecords(houseUserDecoratePlanPage);
        return SuccessTip.create(page);
    }

//    public Tip submitOrder(){
//
//        RequestOrder requestOrder = orderService.createOrder()
//    }

    //    ????????????????????????
    @GetMapping("/userDecorateDetails/{id}")
    public Tip getUserDecoratePlanDetails(@PathVariable("id") Long id) {
        HouseUserDecoratePlanRecord record = new HouseUserDecoratePlanRecord();

        record.setUserId(JWTKit.getUserId());
        record.setOptionType(1);
        record.setId(id);
        List<HouseUserDecoratePlanRecord> houseUserDecoratePlanPage = queryHouseUserDecoratePlanDao.findHouseUserDecoratePlanPage(null, record, null, null, null, null, null);
        HouseUserDecoratePlanRecord houseUserDecoratePlanRecord = new HouseUserDecoratePlanRecord();
        if (houseUserDecoratePlanPage != null && houseUserDecoratePlanPage.size() == 1) {
            HouseAsset houseAsset = queryHouseAssetDao.queryMasterModel(houseUserDecoratePlanPage.get(0).getAssetId());
            if (houseAsset != null) {
                houseUserDecoratePlanPage.get(0).setHouseAsset(houseAsset);
            }
            //                    ????????????????????????
            HouseDecoratePlan houseDecoratePlan = queryHouseDecoratePlanDao.queryMasterModel(houseUserDecoratePlanPage.get(0).getDecoratePlanId());
            houseUserDecoratePlanPage.get(0).setHouseDecoratePlan(houseDecoratePlan);

//            ??????????????????
            HouseUserDecorateFunitureRecord houseUserDecorateFunitureRecord = new HouseUserDecorateFunitureRecord();
            houseUserDecorateFunitureRecord.setUserId(JWTKit.getUserId());
            houseUserDecorateFunitureRecord.setDecoratePlanId(houseUserDecoratePlanPage.get(0).getDecoratePlanId());

            List<HouseUserDecorateFunitureRecord> funitureRecordList = new ArrayList<>();
            List<HouseUserDecorateFunitureRecord> houseUserDecorateFuniturePage = queryHouseUserDecorateFunitureDao.findHouseUserDecorateFuniturePage(null, houseUserDecorateFunitureRecord, null, null, null, null, null);
            for (HouseUserDecorateFunitureRecord funitureRecord : houseUserDecorateFuniturePage) {
                Product product = queryProductDao.queryMasterModel(funitureRecord.getFunitureId());
                if (product != null) {
                    funitureRecord.setProduct(product);
                    funitureRecordList.add(funitureRecord);
                }
            }
            houseUserDecoratePlanPage.get(0).setHouseUserDecorateFunitureRecordList(funitureRecordList);

        }
        HouseUserDecoratePlanRecord result = null;
        if (houseUserDecoratePlanPage != null && houseUserDecoratePlanPage.size() == 1) {
            result = houseUserDecoratePlanPage.get(0);
        }
        return SuccessTip.create(result);
    }


//    ???????????????????????? ??????????????????????????????????????????

    @PostMapping("/confirmDecoratePlanFurniture/{id}")
    public Tip confirmDecoratePlanFurniture(
            @PathVariable("id") Long id,
            @RequestBody List<HouseUserDecorateFunitureRecord> funitureRecordList) throws ServerException {
//        ???????????????????????????????????????

        HouseUserDecoratePlan houseUserDecoratePlan = queryHouseUserDecoratePlanDao.queryMasterModel(id);
        houseUserDecoratePlan.setModifyOption(HouseUserDecoratePlan.MODIFY_TYPE_REFUSE);
        houseUserDecoratePlanService.updateMaster(houseUserDecoratePlan);

//        ?????????????????????????????????
        HouseUserDecorateFunitureRecord houseUserDecorateFunitureRecord = new HouseUserDecorateFunitureRecord();
        houseUserDecorateFunitureRecord.setDecoratePlanId(id);
        houseUserDecorateFunitureRecord.setUserId(JWTKit.getUserId());
        houseUserDecorateFunitureRecord.setAssetId(funitureRecordList.get(0).getAssetId());
        List<HouseUserDecorateFunitureRecord> userDecorateFunitureRecordList = queryHouseUserDecorateFunitureDao.findHouseUserDecorateFuniturePage(null,houseUserDecorateFunitureRecord,null,null,null,null,null);
//        ????????????
        Integer effect = 0;

        List<Long> furnitureIdList = new ArrayList<>();

        for (int i=0;i<funitureRecordList.size();i++){

            Product product = funitureRecordList.get(i).getProduct();
            if (product!=null){
                RequestOrder order = new RequestOrder();
                List<OrderItemRecord> items = new ArrayList<>();
                OrderItemRecord itemRecord = new OrderItemRecord();

                itemRecord.setProductId(product.getId());
                itemRecord.setQuantity(funitureRecordList.get(i).getFunitureNumber());
                itemRecord.setPrice(product.getPrice());
                itemRecord.setWeight(product.getWeight());
                itemRecord.setBulk(product.getBulk());
                itemRecord.setCover(product.getCover());
                itemRecord.setProductName(product.getName());
                items.add(itemRecord);

                order.setType(OrderType.ORDER.name());
                order.setItems(items);
                order.setOrgId(JWTKit.getOrgId());
                order.setUserId(JWTKit.getUserId());
                effect+=orderService.createOrder(order);
                funitureRecordList.get(i).setOrderId(order.getId().intValue());
                effect+=houseUserDecorateFunitureService.updateMaster(funitureRecordList.get(i));

                furnitureIdList.add(funitureRecordList.get(i).getId());
            }
        }

//        ??????????????????????????????
      for (int i=0;i<userDecorateFunitureRecordList.size();i++){
          if (!furnitureIdList.contains(userDecorateFunitureRecordList.get(i).getId())){
              houseUserDecorateFunitureService.deleteMaster(userDecorateFunitureRecordList.get(i).getId());
          }
      }

//        ?????????id ?????? ??????????????????


        return SuccessTip.create(effect);
    }


    //    ????????????????????????
    @PutMapping("/funiture/{funitureId}")
    public Tip updateUserDecorateFuniture(
            @PathVariable("funitureId") Long funitureId,
            @RequestBody HouseUserDecorateFuniture houseUserDecorateFuniture) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }
        if (houseUserDecorateFuniture.getDecoratePlanId() == null) {
            throw new BusinessException(BusinessCode.BadRequest);
        }
        Long decoratePlanId = houseUserDecorateFuniture.getDecoratePlanId();
        HouseUserDecorateFunitureRecord record = new HouseUserDecorateFunitureRecord();
        record.setUserId(JWTKit.getUserId());
        record.setDecoratePlanId(decoratePlanId);
        record.setFunitureId(funitureId);

        List<HouseUserDecorateFunitureRecord> houseUserDecorateFuniturePage = queryHouseUserDecorateFunitureDao.findHouseUserDecorateFuniturePage(null, record, null, null, null, null, null);
        if (houseUserDecorateFuniturePage != null && houseUserDecorateFuniturePage.size() == 1) {
            houseUserDecorateFuniture.setId(houseUserDecorateFuniturePage.get(0).getId());
            houseUserDecorateFuniture.setUserId(JWTKit.getUserId());
            houseUserDecorateFuniture.setDecoratePlanId(decoratePlanId);
            houseUserDecorateFuniture.setAssetId(houseUserDecorateFuniturePage.get(0).getAssetId());
            houseUserDecorateFuniture.setCreateTime(new Date());
            return SuccessTip.create(houseUserDecorateFunitureService.updateMaster(houseUserDecorateFuniture));
        }
        return SuccessTip.create(0);
    }


    //    ????????????????????????
    @DeleteMapping("/funiture")
    public Tip deleteUserDecorateFuniture(@RequestParam("decoratePlanId") Long decoratePlanId,
                                          @RequestParam("funitureId") Long funitureId) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "???????????????");
        }

        HouseUserDecorateFunitureRecord record = new HouseUserDecorateFunitureRecord();
        record.setUserId(JWTKit.getUserId());
        record.setDecoratePlanId(decoratePlanId);
        record.setFunitureId(funitureId);

        List<HouseUserDecorateFunitureRecord> houseUserDecorateFuniturePage = queryHouseUserDecorateFunitureDao.findHouseUserDecorateFuniturePage(null, record, null, null, null, null, null);
        if (houseUserDecorateFuniturePage != null && houseUserDecorateFuniturePage.size() == 1) {
            return SuccessTip.create(houseUserDecorateFunitureService.deleteMaster(houseUserDecorateFuniturePage.get(0).getId()));
        }
        return SuccessTip.create(0);
    }
}
