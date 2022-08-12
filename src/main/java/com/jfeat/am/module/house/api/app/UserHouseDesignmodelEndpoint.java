package com.jfeat.am.module.house.api.app;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.house.services.domain.dao.*;
import com.jfeat.am.module.house.services.domain.model.HouseAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HouseDesignModelRecord;
import com.jfeat.am.module.house.services.domain.model.HousePropertyBuildingUnitRecord;
import com.jfeat.am.module.house.services.domain.model.HouseVrPictureRecord;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api("HouseDesignModel")
@RequestMapping("/api/u/house/houseDesignModel/houseDesignModels")
public class UserHouseDesignmodelEndpoint {

    @Resource
    QueryHouseDesignModelDao queryHouseDesignModelDao;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    QueryHousePropertyBuildingUnitDao queryHousePropertyBuildingUnitDao;

    @Resource
    QueryHouseVrPictureDao queryHouseVrPictureDao;

    @ApiOperation(value = "HouseDesignModel 列表信息", response = HouseDesignModelRecord.class)
    @GetMapping
    public Tip queryHouseDesignModelPage(Page<HouseDesignModelRecord> page,
                                         @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                         @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                         // for tag feature query
                                         @RequestParam(name = "tag", required = false) String tag,
                                         // end tag
                                         @RequestParam(name = "search", required = false) String search,

                                         @RequestParam(name = "houseType", required = false) String houseType,

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

        HouseDesignModelRecord record = new HouseDesignModelRecord();
        record.setHouseType(houseType);


        List<HouseDesignModelRecord> houseDesignModelPage = queryHouseDesignModelDao.findHouseDesignModelPage(page, record, tag, search, orderBy, null, null);
        for (HouseDesignModelRecord designModelRecord:houseDesignModelPage){
            HousePropertyBuildingUnitRecord unitRecord = new HousePropertyBuildingUnitRecord();
            HouseVrPictureRecord houseVrPictureRecord = new HouseVrPictureRecord();
            houseVrPictureRecord.setId(designModelRecord.getVrId());

//            查询vr连接
            List<HouseVrPictureRecord> vrPictureRecordList = queryHouseVrPictureDao.findHouseVrPicturePage(null,houseVrPictureRecord,null,null,null,null,null);
            if (vrPictureRecordList!=null && vrPictureRecordList.size()==1){
                designModelRecord.setVrLink(vrPictureRecordList.get(0).getLink());
            }
//            房屋数
            Integer assetCount = 0;

            unitRecord.setDesignModelId(designModelRecord.getId());
            List<HousePropertyBuildingUnitRecord> housePropertyBuildingUnitRecordList = queryHousePropertyBuildingUnitDao.findHousePropertyBuildingUnitPage(null,unitRecord,null,null,null,null,null);

            for (HousePropertyBuildingUnitRecord buildingUnitRecord:housePropertyBuildingUnitRecordList){
                HouseAssetRecord assetRecord = new HouseAssetRecord();
                assetRecord.setUnitId(buildingUnitRecord.getId());
                List<HouseAssetRecord> assetRecordList = queryHouseAssetDao.findHouseAssetPage(null,assetRecord,null,null,null,null,null);
                assetCount+=assetRecordList.size();
            }

            //            单元数
            Integer unitCount = housePropertyBuildingUnitRecordList.size();

//            查看vr连接

            designModelRecord.setHouseCount(assetCount);
            designModelRecord.setUnitCount(unitCount);
        }

        page.setRecords(houseDesignModelPage);

        return SuccessTip.create(page);
    }
}
