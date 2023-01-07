package com.jfeat.am.module.house.api;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.module.house.services.definition.HouseRentLogStatus;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseRentAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseRentAssetRecord;
import com.jfeat.am.module.house.services.domain.service.HouseEmailService;
import com.jfeat.am.module.house.services.domain.service.HouseExcelService;
import com.jfeat.am.module.house.services.domain.service.HouseRentAssetService;
import com.jfeat.am.module.house.services.domain.service.HouseRentLogService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseRentAssetModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseRentAssetMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentAsset;
import com.jfeat.am.module.house.services.utility.ExcelUtility;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.plus.CRUD;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/u/house/test")
public class HouseTest {

    @Resource
    HouseEmailService houseEmailService;


    @Resource
    HouseExcelService houseExcelService;

    @Resource
    HouseRentAssetMapper houseRentAssetMapper;

    @Resource
    QueryHouseRentAssetDao queryHouseRentAssetDao;

    @Resource
    HouseRentLogService houseRentLogService;


    @PostMapping()
    public void importEmp(HttpServletResponse response, HttpServletRequest request, MultipartFile file) {
        JSONObject json = houseExcelService.setAssetId(houseExcelService.parseExcelData(ExcelUtility.readExcel(file)));

        houseExcelService.addAsset(json);

//        return SuccessTip.create(houseExcelService.addSameFloorExchange(json));

        String sheetName = "同层匹配成功记录";
        String fileName = "同层匹配成功记录";
//        List<HouseAssetMatchLog> houseAssetMatchLogList = houseExcelService.addSameFloorExchange(json);
//
//        Set<HouseAssetMatchLog> houseAssetMatchLogSet = new HashSet<>();
//        houseAssetMatchLogSet.addAll(houseAssetMatchLogList);
//        houseAssetMatchLogList = new ArrayList<>(houseAssetMatchLogSet);
//
////        return SuccessTip.create(houseAssetMatchLogList);
//
//        try {
//            ExcelUtility.exportExcel(response, houseAssetMatchLogList, sheetName, fileName, 15);
//        } catch (IOException e) {
//        }
    }

    @GetMapping
    public Tip getTest() {
        return SuccessTip.create();
    }


}
