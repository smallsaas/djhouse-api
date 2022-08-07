
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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseSurroundFacilitiesDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseSurroundFacilitiesRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseSurroundFacilities;

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
 * @since 2022-08-05
 */
    @RestController
    @Api("HouseSurroundFacilities")
            @RequestMapping("/api/crud/house/houseSurroundFacilities/houseSurroundFacilitieses")
    public class HouseSurroundFacilitiesEndpoint {

    @Resource
    HouseSurroundFacilitiesService houseSurroundFacilitiesService;

    @Resource
    QueryHouseSurroundFacilitiesDao queryHouseSurroundFacilitiesDao;



    @BusinessLog(name = "HouseSurroundFacilities", value = "create HouseSurroundFacilities")
    @Permission(HouseSurroundFacilitiesPermission.HOUSESURROUNDFACILITIES_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseSurroundFacilities",response = HouseSurroundFacilities.class)
    public Tip createHouseSurroundFacilities(@RequestBody HouseSurroundFacilities entity){
        Integer affected=0;
        try{
                affected= houseSurroundFacilitiesService.createMaster(entity);
            }catch(DuplicateKeyException e){
             throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
}

    @Permission(HouseSurroundFacilitiesPermission.HOUSESURROUNDFACILITIES_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseSurroundFacilities",response = HouseSurroundFacilities.class)
    public Tip getHouseSurroundFacilities(@PathVariable Long id){
                        return SuccessTip.create(houseSurroundFacilitiesService.queryMasterModel(queryHouseSurroundFacilitiesDao, id));
            }

    @BusinessLog(name = "HouseSurroundFacilities", value = "update HouseSurroundFacilities")
    @Permission(HouseSurroundFacilitiesPermission.HOUSESURROUNDFACILITIES_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseSurroundFacilities",response = HouseSurroundFacilities.class)
    public Tip updateHouseSurroundFacilities(@PathVariable Long id,@RequestBody HouseSurroundFacilities entity){
        entity.setId(id);
                return SuccessTip.create(houseSurroundFacilitiesService.updateMaster(entity));
            }

    @BusinessLog(name = "HouseSurroundFacilities", value = "delete HouseSurroundFacilities")
    @Permission(HouseSurroundFacilitiesPermission.HOUSESURROUNDFACILITIES_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseSurroundFacilities")
    public Tip deleteHouseSurroundFacilities(@PathVariable Long id){
            return SuccessTip.create(houseSurroundFacilitiesService.deleteMaster(id));
        }

    @Permission(HouseSurroundFacilitiesPermission.HOUSESURROUNDFACILITIES_VIEW)
    @ApiOperation(value = "HouseSurroundFacilities 列表信息",response = HouseSurroundFacilitiesRecord.class)
    @GetMapping
    @ApiImplicitParams({
        @ApiImplicitParam(name= "pageNum", dataType = "Integer"),
        @ApiImplicitParam(name= "pageSize", dataType = "Integer"),
        @ApiImplicitParam(name= "search", dataType = "String"),
                                                                                        @ApiImplicitParam(name = "id", dataType = "Long"),
                                                                                                    @ApiImplicitParam(name = "typeId", dataType = "Long"),
                                                                                    @ApiImplicitParam(name = "title", dataType = "String"),
                                                                                    @ApiImplicitParam(name = "icon", dataType = "String"),
                                                                                            @ApiImplicitParam(name = "distance", dataType = "BigDecimal") ,
                @ApiImplicitParam(name = "orderBy", dataType = "String"),
                @ApiImplicitParam(name = "sort", dataType = "String")
            })
    public Tip queryHouseSurroundFacilitiesPage(Page<HouseSurroundFacilitiesRecord> page,
    @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
    @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
    // for tag feature query
    @RequestParam(name = "tag" , required = false)String tag,
    // end tag
    @RequestParam(name = "search", required = false) String search,
                                                                                                                                        
                                                                                                                                            @RequestParam(name = "typeId", required = false) Long typeId,
                    
                                                                                                                            @RequestParam(name = "title", required = false) String title,
                    
                                                                                                                            @RequestParam(name = "icon", required = false) String icon,
                    
                                                                                                                                    @RequestParam(name = "distance", required = false) BigDecimal distance,
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

    HouseSurroundFacilitiesRecord record = new HouseSurroundFacilitiesRecord();
                                                                                                                                                                                                record.setTypeId(typeId);
                                                                                                                record.setTitle(title);
                                                                                                                record.setIcon(icon);
                                                                                                                record.setDistance(distance);
                        
                                

    List<HouseSurroundFacilitiesRecord> houseSurroundFacilitiesPage = queryHouseSurroundFacilitiesDao.findHouseSurroundFacilitiesPage(page, record, tag, search, orderBy, null, null);

        
        page.setRecords(houseSurroundFacilitiesPage);

        return SuccessTip.create(page);
    }
}

