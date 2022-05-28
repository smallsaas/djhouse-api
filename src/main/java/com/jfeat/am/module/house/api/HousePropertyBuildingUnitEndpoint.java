
package com.jfeat.am.module.house.api;


import com.jfeat.crud.plus.META;
import com.jfeat.am.core.jwt.JWTKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.dao.DuplicateKeyException;
import com.jfeat.am.module.house.services.domain.dao.QueryHousePropertyBuildingUnitDao;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.request.Ids;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.base.annotation.BusinessLog;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.plus.CRUDObject;
import com.jfeat.crud.plus.DefaultFilterResult;
import com.jfeat.am.module.house.api.permission.*;
import com.jfeat.am.common.annotation.Permission;
import java.math.BigDecimal;

import com.jfeat.am.module.house.services.domain.service.*;
import com.jfeat.am.module.house.services.domain.model.HousePropertyBuildingUnitRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HousePropertyBuildingUnit;

    import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import com.alibaba.fastjson.JSONArray;
/**
 * <p>
 *  api
 * </p>
 *
 * @author Code generator
 * @since 2022-05-27
 */
    @RestController
    @Api("HousePropertyBuildingUnit")
            @RequestMapping("/api/crud/house/housePropertyBuildingUnit/housePropertyBuildingUnits")
    public class HousePropertyBuildingUnitEndpoint {

    @Resource
    HousePropertyBuildingUnitService housePropertyBuildingUnitService;

    @Resource
    QueryHousePropertyBuildingUnitDao queryHousePropertyBuildingUnitDao;



    @BusinessLog(name = "HousePropertyBuildingUnit", value = "create HousePropertyBuildingUnit")
    @Permission(HousePropertyBuildingUnitPermission.HOUSEPROPERTYBUILDINGUNIT_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HousePropertyBuildingUnit",response = HousePropertyBuildingUnit.class)
    public Tip createHousePropertyBuildingUnit(@RequestBody HousePropertyBuildingUnit entity){
        Integer affected=0;
        try{
                affected= housePropertyBuildingUnitService.createMaster(entity);
            }catch(DuplicateKeyException e){
             throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
}

    @Permission(HousePropertyBuildingUnitPermission.HOUSEPROPERTYBUILDINGUNIT_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HousePropertyBuildingUnit",response = HousePropertyBuildingUnit.class)
    public Tip getHousePropertyBuildingUnit(@PathVariable Long id){
                        return SuccessTip.create(housePropertyBuildingUnitService.queryMasterModel(queryHousePropertyBuildingUnitDao, id));
            }

    @BusinessLog(name = "HousePropertyBuildingUnit", value = "update HousePropertyBuildingUnit")
    @Permission(HousePropertyBuildingUnitPermission.HOUSEPROPERTYBUILDINGUNIT_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HousePropertyBuildingUnit",response = HousePropertyBuildingUnit.class)
    public Tip updateHousePropertyBuildingUnit(@PathVariable Long id,@RequestBody HousePropertyBuildingUnit entity){
        entity.setId(id);
                return SuccessTip.create(housePropertyBuildingUnitService.updateMaster(entity));
            }

    @BusinessLog(name = "HousePropertyBuildingUnit", value = "delete HousePropertyBuildingUnit")
    @Permission(HousePropertyBuildingUnitPermission.HOUSEPROPERTYBUILDINGUNIT_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HousePropertyBuildingUnit")
    public Tip deleteHousePropertyBuildingUnit(@PathVariable Long id){
            return SuccessTip.create(housePropertyBuildingUnitService.deleteMaster(id));
        }

    @Permission(HousePropertyBuildingUnitPermission.HOUSEPROPERTYBUILDINGUNIT_VIEW)
    @ApiOperation(value = "HousePropertyBuildingUnit 列表信息",response = HousePropertyBuildingUnitRecord.class)
    @GetMapping
    @ApiImplicitParams({
        @ApiImplicitParam(name= "pageNum", dataType = "Integer"),
        @ApiImplicitParam(name= "pageSize", dataType = "Integer"),
        @ApiImplicitParam(name= "search", dataType = "String"),
                                                                                        @ApiImplicitParam(name = "id", dataType = "Long"),
                                                                                                    @ApiImplicitParam(name = "buildingId", dataType = "Long"),
                                                                                    @ApiImplicitParam(name = "number", dataType = "String") ,
                @ApiImplicitParam(name = "orderBy", dataType = "String"),
                @ApiImplicitParam(name = "sort", dataType = "String")
            })
    public Tip queryHousePropertyBuildingUnitPage(Page<HousePropertyBuildingUnitRecord> page,
    @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
    @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
    // for tag feature query
    @RequestParam(name = "tag" , required = false)String tag,
    // end tag
    @RequestParam(name = "search", required = false) String search,
                                                                                                                                        
                                                                                                                                            @RequestParam(name = "buildingId", required = false) Long buildingId,
                    
                                                                                                                            @RequestParam(name = "number", required = false) String number,
        @RequestParam(name = "orderBy", required = false) String orderBy,
        @RequestParam(name = "sort", required = false)  String sort) {
                    
            if(orderBy!=null&&orderBy.length()>0){
        if(sort!=null&&sort.length()>0){
        String sortPattern = "(ASC|DESC|asc|desc)";
        if(!sort.matches(sortPattern)){
        throw new BusinessException(BusinessCode.BadRequest.getCode(), "sort must be ASC or DESC");//此处异常类型根据实际情况而定
        }
        }else{
        sort = "ASC";
        }
        orderBy = "`"+orderBy+"`" +" "+sort;
        }
        page.setCurrent(pageNum);
        page.setSize(pageSize);

    HousePropertyBuildingUnitRecord record = new HousePropertyBuildingUnitRecord();
                                                                                                                                                                                                record.setBuildingId(buildingId);
                                                                                                                record.setNumber(number);
                        
                                

    List<HousePropertyBuildingUnitRecord> housePropertyBuildingUnitPage = queryHousePropertyBuildingUnitDao.findHousePropertyBuildingUnitPage(page, record, tag, search, orderBy, null, null);

        
        page.setRecords(housePropertyBuildingUnitPage);

        return SuccessTip.create(page);
    }
}

