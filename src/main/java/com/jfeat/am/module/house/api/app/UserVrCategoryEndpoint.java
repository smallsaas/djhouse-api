package com.jfeat.am.module.house.api.app;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseDesignModelDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseVrPictureDao;
import com.jfeat.am.module.house.services.domain.model.HouseDesignModelRecord;
import com.jfeat.am.module.house.services.domain.model.HouseVrPictureRecord;
import com.jfeat.am.module.house.services.domain.service.HouseVrPictureService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseVrPictureModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseVrTypeMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseVrPicture;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseVrType;
import com.jfeat.am.module.house.services.utility.UserCommunityAsset;
import com.jfeat.am.module.supplier.services.domain.dao.QuerySupplierDao;
import com.jfeat.am.module.supplier.services.domain.model.SupplierRecord;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.eav.services.domain.service.DataServiceService;
import com.jfeat.eav.services.domain.service.EavEntityService;
import com.jfeat.eav.services.gen.persistence.model.EavEntity;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@Api("UserVrCategory")
@RequestMapping("/api/u/vr")
public class UserVrCategoryEndpoint {


    @Resource
    QueryHouseVrPictureDao queryHouseVrPictureDao;

    @Resource
    HouseVrPictureService houseVrPictureService;

    @Resource
    UserCommunityAsset userCommunityAsset;

    @Resource
    HouseVrTypeMapper houseVrTypeMapper;




    @GetMapping("/vrType")
    public Tip getVrType(){
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        Long communityId = userCommunityAsset.getUserCommunityStatus(JWTKit.getUserId());
        if (communityId==null){
            throw new BusinessException(BusinessCode.CodeBase,"未找到小区信息");
        }

        QueryWrapper<HouseVrType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseVrType.COMMUNITY_id,communityId);
        List<HouseVrType> vrTypes = houseVrTypeMapper.selectList(queryWrapper);
        return SuccessTip.create(vrTypes);

    }


    @GetMapping
    public Tip queryCategory(
            Page<HouseVrPictureRecord> page,
            @RequestParam("typeId") Long typeId,
            @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        page.setCurrent(pageNum);
        page.setSize(pageSize);

        HouseVrPictureRecord record = new HouseVrPictureRecord();
        record.setTypeId(typeId);
        record.setStatus(HouseVrPicture.STATUS_SHELVES);
        List<HouseVrPictureRecord> houseVrPictureRecordList = queryHouseVrPictureDao.findHouseVrPicturePage(page, record, null, null, null, null, null);
        page.setRecords(houseVrPictureRecordList);

        return SuccessTip.create(page);
    }


    /*
    增加vr图看过人数
     */
    @PutMapping("/addVrCount/{id}")
    public Tip vrClientCount(@PathVariable("id") Long id) {
        HouseVrPictureModel houseVrPictureModel = houseVrPictureService.queryMasterModel(queryHouseVrPictureDao, id);
        if (houseVrPictureModel != null) {
            houseVrPictureModel.setStar(houseVrPictureModel.getStar() + 1);

        }
        return SuccessTip.create(houseVrPictureService.updateMaster(houseVrPictureModel));
    }





}
