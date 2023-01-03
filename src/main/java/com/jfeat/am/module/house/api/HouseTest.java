package com.jfeat.am.module.house.api;


import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.module.house.services.domain.service.HouseEmailService;
import com.jfeat.am.module.house.services.domain.service.HouseExcelService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;
import com.jfeat.am.module.house.services.utility.ExcelUtility;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
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
        List<HouseAssetMatchLog> houseAssetMatchLogList = houseExcelService.addAllSameFloorExchange();
        Set<HouseAssetMatchLog> houseAssetMatchLogSet = new HashSet<>();
        houseAssetMatchLogSet.addAll(houseAssetMatchLogList);
        houseAssetMatchLogList = new ArrayList<>(houseAssetMatchLogSet);
        return SuccessTip.create(houseAssetMatchLogList);
    }


}
