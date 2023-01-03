package com.jfeat.am.module.house.api.app.common;

import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseAssetMatchLogDao;
import com.jfeat.am.module.house.services.domain.model.HouseAssetMatchLogRecord;
import com.jfeat.am.module.house.services.domain.service.HouseExcelService;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;
import com.jfeat.am.module.house.services.utility.ExcelUtility;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
@RequestMapping("/api/u/house/tool")
public class HouseAssetScriptEndpoint {



    private static final Logger log = LoggerFactory.getLogger(HouseAssetScriptEndpoint.class);


    @Resource
    HouseExcelService houseExcelService;

    @Resource
    QueryHouseAssetMatchLogDao queryHouseAssetMatchLogDao;

    /**
     * 自动导入 同层匹配成功Excel
     * @param file 导入房屋产权
     */
    @PostMapping("/import/excel")
    public void importEmp( MultipartFile file) {
        JSONObject json = houseExcelService.setAssetId(houseExcelService.parseExcelData(ExcelUtility.readExcel(file)));
        houseExcelService.addAsset(json);
    }


    @GetMapping("/export/excel")
    public void exportExcel(HttpServletResponse response, HttpServletRequest request) {


        String sheetName = "同层匹配成功记录";
        String fileName = "同层匹配成功记录";
//        List<HouseAssetMatchLog> houseAssetMatchLogList = houseExcelService.addAllSameFloorExchange();
//        Set<HouseAssetMatchLog> houseAssetMatchLogSet = new HashSet<>();
//        houseAssetMatchLogSet.addAll(houseAssetMatchLogList);
//        houseAssetMatchLogList = new ArrayList<>(houseAssetMatchLogSet);
        HouseAssetMatchLogRecord record = new HouseAssetMatchLogRecord();
        record.setFlag(1);
        List<HouseAssetMatchLog> houseAssetMatchLogList = queryHouseAssetMatchLogDao.findHouseAssetMatchLog(null,record,null,null,null,null,null);

        try {
            ExcelUtility.exportExcel(response, houseAssetMatchLogList, sheetName, fileName, 15);
        } catch (IOException e) {
            log.error("同层匹配导出失败");
            log.error(e.getMessage());
        }

    }

    @PostMapping("/sync")
    public Tip syncSameFloorExchange(){
        List<HouseAssetMatchLog> houseAssetMatchLogList = houseExcelService.addAllSameFloorExchange();
        Set<HouseAssetMatchLog> houseAssetMatchLogSet = new HashSet<>();
        houseAssetMatchLogSet.addAll(houseAssetMatchLogList);
        houseAssetMatchLogList = new ArrayList<>(houseAssetMatchLogSet);
        return SuccessTip.create(houseAssetMatchLogList);
    }
}
