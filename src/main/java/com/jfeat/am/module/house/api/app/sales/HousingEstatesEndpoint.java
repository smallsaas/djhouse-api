package com.jfeat.am.module.house.api.app.sales;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAsset;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.plugins.tiff.TIFFDirectory;
import javax.swing.plaf.SeparatorUI;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/sales/housingEstates")
public class HousingEstatesEndpoint {

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;

    @GetMapping
    public Tip getHousingEstatesPage(Page<HouseUserAssetRecord> page,
                                     @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                     @RequestParam(name = "search", required = false) String search){

        page.setCurrent(pageNum);
        page.setSize(pageSize);

        HouseUserAssetRecord record = new HouseUserAssetRecord();
        record.setOrgId(JWTKit.getOrgId());

        List<HouseUserAssetRecord> userAssetRecords = queryHouseUserAssetDao.findHouseUserAssetPage(page,record,null,null,search,null,null,null);
        page.setRecords(userAssetRecords);
        return SuccessTip.create(page);
    }
}
