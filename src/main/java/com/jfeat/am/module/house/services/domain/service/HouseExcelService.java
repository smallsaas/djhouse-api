package com.jfeat.am.module.house.services.domain.service;

import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.module.house.services.domain.model.HouseAssetExchangeRequestRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetMatchLog;
import com.jfeat.am.module.house.services.gen.persistence.model.UserHouseExcel;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface HouseExcelService {



    JSONObject parseExcelData(List<UserHouseExcel> excelList);


    JSONObject setAssetId(JSONObject json);

    Integer addAsset(JSONObject json);



    List<HouseAssetMatchLog> addAllSameFloorExchange();



}
