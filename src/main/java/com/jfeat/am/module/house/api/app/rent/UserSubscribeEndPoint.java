package com.jfeat.am.module.house.api.app.rent;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.crud.tag.services.persistence.dao.StockTagMapper;
import com.jfeat.am.crud.tag.services.persistence.dao.StockTagRelationMapper;
import com.jfeat.am.crud.tag.services.persistence.model.StockTag;
import com.jfeat.am.crud.tag.services.persistence.model.StockTagRelation;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseSubscribeDao;
import com.jfeat.am.module.house.services.domain.model.HouseRentAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HouseSubscribeRecord;
import com.jfeat.am.module.house.services.domain.service.HouseRentAssetService;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseSubscribeMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSubscribe;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/rent/subscribe")
public class UserSubscribeEndPoint {


    @Resource
    QueryHouseSubscribeDao queryHouseSubscribeDao;

    @Resource
    HouseSubscribeMapper houseSubscribeMapper;

    @Resource
    StockTagMapper stockTagMapper;

    @Resource
    HouseRentAssetService houseRentAssetService;

    @Resource
    StockTagRelationMapper stockTagRelationMapper;

    /**
     * 订阅开关 当用没有订阅过时订阅 已经订阅了进行取关
     *
     * @param entity
     * @return
     */
    @PostMapping("/subscribeSwitch")
    public Tip subscribeSwitch(@RequestBody HouseSubscribe entity,@RequestParam(value = "userId",required = false) Long userId) {

        userId = JWTKit.getUserId()!=null?JWTKit.getUserId():userId;

        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
        }
        if (entity.getSubscribeId() == null || entity.getSubscribeId().equals("")) {
            throw new BusinessException(BusinessCode.BadRequest, "subscribeId为必填项");
        }
        entity.setUserId(userId);
        QueryWrapper<HouseSubscribe> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseSubscribe.SUBSCRIBE_ID, entity.getSubscribeId()).eq(HouseSubscribe.USER_ID, userId);
        HouseSubscribe houseSubscribe = houseSubscribeMapper.selectOne(queryWrapper);
        if (houseSubscribe == null) {
            return SuccessTip.create(houseSubscribeMapper.insert(entity));
        } else {
            return SuccessTip.create(houseSubscribeMapper.delete(queryWrapper));
        }
    }


    @GetMapping
    public Tip queryHouseSubscribePage(Page<HouseRentAssetRecord> page,
                                       @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                       // for tag feature query
                                       @RequestParam(name = "tag", required = false) String tag,
                                       // end tag
                                       @RequestParam(name = "search", required = false) String search,

                                       @RequestParam(name = "subscribeId", required = false) Long subscribeId,

                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                       @RequestParam(name = "createTime", required = false) Date createTime,
                                       @RequestParam(name = "orderBy", required = false) String orderBy,
                                       @RequestParam(name = "sort", required = false) String sort) {

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "没有登录");
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

        HouseSubscribeRecord record = new HouseSubscribeRecord();
        record.setUserId(JWTKit.getUserId());
        record.setSubscribeId(subscribeId);
        record.setCreateTime(createTime);


        List<HouseRentAssetRecord> houseRentAssetPage = queryHouseSubscribeDao.userSubscribe(page, record);

//        添加tag标签 和房屋信息
        QueryWrapper<StockTag> stockTagQueryWrapper = new QueryWrapper<>();
        List<StockTag> stockTags = stockTagMapper.selectList(stockTagQueryWrapper);

        QueryWrapper<StockTagRelation> stockTagRelationQueryWrapper = new QueryWrapper<>();
        stockTagRelationQueryWrapper.eq(StockTagRelation.STOCK_TYPE,houseRentAssetService.getEntityName());
        List<StockTagRelation> stockTagRelations = stockTagRelationMapper.selectList(stockTagRelationQueryWrapper);

        for (HouseRentAssetRecord houseRentAssetRecord:houseRentAssetPage){
            JSONObject resultJson = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (StockTagRelation stockTagRelation:stockTagRelations){
                if (stockTagRelation.getStockId().equals(houseRentAssetRecord.getId())){

                    for (StockTag stockTag:stockTags){
                        if (stockTag.getId().equals(stockTagRelation.getTagId())){
                            JSONObject tagJson = new JSONObject();
                            tagJson.put("id",stockTag.getId());
                            tagJson.put("tagName",stockTag.getTagName());
                            jsonArray.add(tagJson);
                        }

                    }
                }
            }
            resultJson.put("tags",jsonArray);
            houseRentAssetRecord.setExtra(resultJson.toJSONString());

        }
        for (HouseRentAssetRecord houseRentAssetRecord:houseRentAssetPage){
            houseRentAssetRecord.setSubscribeStatus(true);
        }
        page.setRecords(houseRentAssetPage);

        return SuccessTip.create(page);
    }


}
