package com.jfeat.am.module.house.services.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyBuildingDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyBuildingUnitDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetRecord;
import com.jfeat.am.module.house.services.domain.model.HousePropertyBuildingUnitRecord;
import com.jfeat.am.module.house.services.domain.service.HouseAssetService;
import com.jfeat.am.module.house.services.domain.service.HousePropertyBuildingOverModelService;
import com.jfeat.am.module.house.services.domain.service.HousePropertyBuildingUnitService;
import com.jfeat.am.module.house.services.gen.crud.model.HousePropertyBuildingModel;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDHousePropertyBuildingOverModelServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAsset;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuildingUnit;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import io.jsonwebtoken.lang.Assert;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("housePropertyBuildingService")
public class HousePropertyBuildingOverModelServiceImpl extends CRUDHousePropertyBuildingOverModelServiceImpl implements HousePropertyBuildingOverModelService {

    @Resource
    QueryHousePropertyBuildingUnitDao queryHousePropertyBuildingUnitDao;

    @Resource
    QueryHouseAssetDao queryHouseAssetDao;

    @Resource
    HousePropertyBuildingOverModelService housePropertyBuildingOverModelService;

    @Resource
    QueryHousePropertyBuildingDao queryHousePropertyBuildingDao;

    @Resource
    HousePropertyBuildingUnitService housePropertyBuildingUnitService;



    @Override
    protected String entityName() {
        return "HousePropertyBuilding";
    }


    @Override
    public int initHouseProperty(HousePropertyBuildingModel entity) {
        Assert.isTrue((entity.getUnits() != null || entity.getUnits() > 0), "单元数不能为空或者小于等于0");
        if ((entity.getUnits() == null || entity.getUnits() <= 0)) {
            throw new BusinessException(BusinessCode.BadRequest);
        }
        Assert.isTrue((entity.getUnits() != null || entity.getUnits() > 0), "楼层数不能为空或者小于等于0");
        if (entity.getFloors() == null || entity.getFloors() <= 0) {
            throw new BusinessException(BusinessCode.BadRequest);
        }
        Integer affected = 0;


        List<HousePropertyBuildingUnit> unitList = new ArrayList<>();

        /*
        添加单元数据
         */
        for (int i = 1; i <= entity.getUnits(); i++) {
            HousePropertyBuildingUnit housePropertyBuildingUnit = new HousePropertyBuildingUnit();
            housePropertyBuildingUnit.setBuildingId(entity.getId());

//            单元编号为B1-1
            housePropertyBuildingUnit.setUnitCode("".concat(entity.getCode()).concat("-").concat(String.valueOf(i)));
            housePropertyBuildingUnit.setUnitNumber("".concat(String.valueOf(i)));
            unitList.add(housePropertyBuildingUnit);

        }
        /*
        批量插入单元
         */
        if (unitList !=null && unitList.size()>0){
            affected+=queryHousePropertyBuildingUnitDao.insertUnits(unitList);
        }


        /*
        判断单元表数据是否 和 楼栋单元数一样
         */
        HousePropertyBuildingUnitRecord unitRecord = new HousePropertyBuildingUnitRecord();
        unitRecord.setBuildingId(entity.getId());
        List<HousePropertyBuildingUnitRecord> housePropertyBuildingUnitList = queryHousePropertyBuildingUnitDao.findHousePropertyBuildingUnitPage(null,unitRecord,null,null,null,null,null);
        Assert.isTrue(housePropertyBuildingUnitList.size() == entity.getUnits(), "单元表插入有误");
        if (housePropertyBuildingUnitList.size() != entity.getUnits()) {
            throw new BusinessException(BusinessCode.DatabaseInsertError);
        }

        List<HouseAsset> houseAssetList = new ArrayList<>();
        for (int i = 1; i <= entity.getFloors(); i++) {
            for (int j = 1; j <= entity.getUnits(); j++) {
                HouseAsset housePropertyRoom = new HouseAsset();
                housePropertyRoom.setBuildingId(entity.getId());

//                获取单元表id
                housePropertyRoom.setUnitId(housePropertyBuildingUnitList.get(j - 1).getId());

                housePropertyRoom.setFloor(i);

                housePropertyRoom.setNumber(String.format("%d%02d", i, j));
                housePropertyRoom.setHouseNumber(String.format("%d%02d", i, j));

                houseAssetList.add(housePropertyRoom);
            }
        }
        /*
        添加房子
         */
        if (houseAssetList!=null && houseAssetList.size()>0){
            affected+=queryHouseAssetDao.insertAssets(houseAssetList);
        }

        return affected;
    }

    /*
    修改楼栋数和单元数
     */
    @Override
    public int modifyHouseBuilding(HousePropertyBuildingModel oldBuilding) {

        HousePropertyBuildingModel newBuilding =  queryHousePropertyBuildingDao.queryMasterModel(oldBuilding.getId());
        /*
            修改楼栋
             */

        Integer affected = 0;
        HousePropertyBuildingUnitRecord unitRecord = new HousePropertyBuildingUnitRecord();
        unitRecord.setBuildingId(oldBuilding.getId());
        List<HousePropertyBuildingUnitRecord> housePropertyBuildingUnitList = queryHousePropertyBuildingUnitDao.findHousePropertyBuildingUnitPage(null,unitRecord,null,null,null,null,null);

        if (oldBuilding.getFloors()!=null && oldBuilding.getFloors()!=newBuilding.getFloors()){

            /*
            当楼层数大于 原来的时候 增加 房子 按照原来层数增加
             */
            if (newBuilding.getFloors()>oldBuilding.getFloors()){
                List<HouseAsset> houseAssetList = new ArrayList<>();
                for (int i=oldBuilding.getFloors()+1;i<=newBuilding.getFloors();i++){
                    for (int j = 1;j<=oldBuilding.getUnits();j++){
                        HouseAsset housePropertyRoom = new HouseAsset();
                        housePropertyRoom.setBuildingId(oldBuilding.getId());

                        int count = j;

                        /*
                        判断单元id是否对应
                         */
                        for (; count<=housePropertyBuildingUnitList.size();count++){
                            HousePropertyBuildingUnitRecord unit = housePropertyBuildingUnitList.get(count - 1);
                            String[] unitCodes = unit.getUnitCode().split("-");
                            if (unitCodes==null && unitCodes.length!=2){
                                throw new BusinessException(BusinessCode.CodeBase,"单元码有错");
                            }
                            Integer unitCode  = Integer.valueOf(unitCodes[1]);
                            if (unitCode.equals(j)){
                               break;
                            }
                        }


                        housePropertyRoom.setUnitId(housePropertyBuildingUnitList.get(count - 1).getId());

                        housePropertyRoom.setFloor(i);

                        housePropertyRoom.setNumber(String.format("%d%02d", i, j));
                        housePropertyRoom.setHouseNumber(String.format("%d%02d", i, j));

                        houseAssetList.add(housePropertyRoom);
                    }
                }
             /*
        添加房子
         */
                if (houseAssetList!=null && houseAssetList.size()>0){
                    affected+=queryHouseAssetDao.insertAssets(houseAssetList);
                }
            } else {
                /*
                当楼层数小于 原来的时候 删除 房子
                 */
                queryHouseAssetDao.deleteHouseAssetByBuildingIdAndFloors(newBuilding.getId(),newBuilding.getFloors());

            }


        }
            /*
            当单元号不一样时 修改单元表
             */
        if (oldBuilding.getUnits()!=null && oldBuilding.getUnits()!=newBuilding.getUnits()){
           /*
           单元数大于 原来时候 增加 单元 房子
            */
            if (newBuilding.getUnits()>oldBuilding.getUnits())
            {
                List<HousePropertyBuildingUnit> unitList = new ArrayList<>();
                for (int i=oldBuilding.getUnits()+1;i<=newBuilding.getUnits();i++){
                    HousePropertyBuildingUnit housePropertyBuildingUnit = new HousePropertyBuildingUnit();
                    housePropertyBuildingUnit.setBuildingId(newBuilding.getId());

//            单元编号为B1-1
                    housePropertyBuildingUnit.setUnitCode("".concat(newBuilding.getCode()).concat("-").concat(String.valueOf(i)));
                    housePropertyBuildingUnit.setUnitNumber("".concat(String.valueOf(i)));
                    unitList.add(housePropertyBuildingUnit);
                }
                /*
                批量插入单元
                 */
                if (unitList !=null && unitList.size()>0){
                    affected+=queryHousePropertyBuildingUnitDao.insertUnits(unitList);
                }


                HousePropertyBuildingUnitRecord newUnitRecord = new HousePropertyBuildingUnitRecord();
                newUnitRecord.setBuildingId(newBuilding.getId());
                List<HousePropertyBuildingUnitRecord> newUnitList = queryHousePropertyBuildingUnitDao.findHousePropertyBuildingUnitPage(null,unitRecord,null,null,null,null,null);

                /*
                新旧单元差集
                 */


                List<HousePropertyBuildingUnitRecord> differenceUnit = newUnitList.stream().filter(item->!housePropertyBuildingUnitList.contains(item)).collect(Collectors.toList());



                List<HouseAsset> houseAssetList = new ArrayList<>();
                for (int i = 1; i <= newBuilding.getFloors(); i++) {
                    int count =0;
                    for (int j = oldBuilding.getUnits()+1; j <= newUnitList.size(); j++) {
                        HouseAsset housePropertyRoom = new HouseAsset();
                        housePropertyRoom.setBuildingId(newBuilding.getId());

//                      获取单元表id
                        housePropertyRoom.setUnitId(differenceUnit.get(count).getId());

                        housePropertyRoom.setFloor(i);

                        housePropertyRoom.setNumber(String.format("%d%02d", i, j));
                        housePropertyRoom.setHouseNumber(String.format("%d%02d", i, j));

                        houseAssetList.add(housePropertyRoom);
                        count++;
                    }
                }
                /*
                添加房子
                 */
                if (houseAssetList!=null && houseAssetList.size()>0){
                    affected+=queryHouseAssetDao.insertAssets(houseAssetList);
                }


            }else {
                 /*
            单元数小于 原来时候 删除单元和房子 需要确认是几号单元
             */
                for (HousePropertyBuildingUnitRecord housePropertyBuildingUnitRecord:housePropertyBuildingUnitList){
                    String[] unitCodes = housePropertyBuildingUnitRecord.getUnitCode().split("-");
                    if (unitCodes==null && unitCodes.length!=2){
                        throw new BusinessException(BusinessCode.CodeBase,"单元码有错");
                    }
                    Integer unitCode  = Integer.valueOf(unitCodes[1]);
                    if (unitCode>newBuilding.getUnits()){
                        housePropertyBuildingUnitService.deleteMaster(housePropertyBuildingUnitRecord.getId());
                    }
                }

                HousePropertyBuildingUnitRecord newUnitRecord = new HousePropertyBuildingUnitRecord();
                newUnitRecord.setBuildingId(newBuilding.getId());
                List<HousePropertyBuildingUnitRecord> newUnitList = queryHousePropertyBuildingUnitDao.findHousePropertyBuildingUnitPage(null,unitRecord,null,null,null,null,null);

                /*
                新旧单元差集
                 */
                List<HousePropertyBuildingUnitRecord> differenceUnit = housePropertyBuildingUnitList.stream().filter(item->!newUnitList.contains(item)).collect(Collectors.toList());

                for (int i=0;i<differenceUnit.size();i++){

                    HouseAssetRecord houseAssetRecord = new HouseAssetRecord();
                    houseAssetRecord.setUnitId(differenceUnit.get(i).getId());
                    List<HouseAssetRecord> assetRecordList = queryHouseAssetDao.findHouseAssetPage(null,houseAssetRecord,null,null,null,null,null);

                    if (assetRecordList !=null && assetRecordList.size()>0){
                        List<Long> ids = new ArrayList<>();
                        for (HouseAssetRecord assetRecord:assetRecordList){
                            ids.add(assetRecord.getId());
                        }
                        if (ids!=null && ids.size()>0){
                            affected += queryHouseAssetDao.deleteHouseAssets(ids);
                        }

                    }

                }

            }



        }
        return affected;
    }
}
