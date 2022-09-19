
package com.jfeat.am.module.house.api.manager;


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
import com.jfeat.am.module.house.services.domain.dao.QueryHouseBrowseLogDao;
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
import com.jfeat.am.module.house.services.domain.model.HouseBrowseLogRecord;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseBrowseLog;

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
 * @since 2022-08-25
 */
    @RestController
    @Api("HouseBrowseLog")
            @RequestMapping("/api/crud/house/houseBrowseLog/houseBrowseLogs")
    public class HouseBrowseLogEndpoint {

    @Resource
    HouseBrowseLogService houseBrowseLogService;

    @Resource
    QueryHouseBrowseLogDao queryHouseBrowseLogDao;



    @BusinessLog(name = "HouseBrowseLog", value = "create HouseBrowseLog")
    @Permission(HouseBrowseLogPermission.HOUSEBROWSELOG_NEW)
    @PostMapping
    @ApiOperation(value = "新建 HouseBrowseLog",response = HouseBrowseLog.class)
    public Tip createHouseBrowseLog(@RequestBody HouseBrowseLog entity){
        Integer affected=0;
        try{
                affected= houseBrowseLogService.createMaster(entity);
            }catch(DuplicateKeyException e){
             throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
}

    @Permission(HouseBrowseLogPermission.HOUSEBROWSELOG_VIEW)
    @GetMapping("/{id}")
    @ApiOperation(value = "查看 HouseBrowseLog",response = HouseBrowseLog.class)
    public Tip getHouseBrowseLog(@PathVariable Long id){
                        return SuccessTip.create(houseBrowseLogService.queryMasterModel(queryHouseBrowseLogDao, id));
            }

    @BusinessLog(name = "HouseBrowseLog", value = "update HouseBrowseLog")
    @Permission(HouseBrowseLogPermission.HOUSEBROWSELOG_EDIT)
    @PutMapping("/{id}")
    @ApiOperation(value = "修改 HouseBrowseLog",response = HouseBrowseLog.class)
    public Tip updateHouseBrowseLog(@PathVariable Long id,@RequestBody HouseBrowseLog entity){
        entity.setId(id);
                return SuccessTip.create(houseBrowseLogService.updateMaster(entity));
            }

    @BusinessLog(name = "HouseBrowseLog", value = "delete HouseBrowseLog")
    @Permission(HouseBrowseLogPermission.HOUSEBROWSELOG_DELETE)
    @DeleteMapping("/{id}")
    @ApiOperation("删除 HouseBrowseLog")
    public Tip deleteHouseBrowseLog(@PathVariable Long id){
            return SuccessTip.create(houseBrowseLogService.deleteMaster(id));
        }

    @Permission(HouseBrowseLogPermission.HOUSEBROWSELOG_VIEW)
    @ApiOperation(value = "HouseBrowseLog 列表信息",response = HouseBrowseLogRecord.class)
    @GetMapping
    @ApiImplicitParams({
        @ApiImplicitParam(name= "pageNum", dataType = "Integer"),
        @ApiImplicitParam(name= "pageSize", dataType = "Integer"),
        @ApiImplicitParam(name= "search", dataType = "String"),
                                                                                        @ApiImplicitParam(name = "id", dataType = "Long"),
                                                                                                    @ApiImplicitParam(name = "userId", dataType = "Long"),
                                                                                                    @ApiImplicitParam(name = "browseNumber", dataType = "Integer") ,
                @ApiImplicitParam(name = "orderBy", dataType = "String"),
                @ApiImplicitParam(name = "sort", dataType = "String")
            })
    public Tip queryHouseBrowseLogPage(Page<HouseBrowseLogRecord> page,
    @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
    @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
    // for tag feature query
    @RequestParam(name = "tag" , required = false)String tag,
    // end tag
    @RequestParam(name = "search", required = false) String search,
                                                                                                                                        
                                                                                                                                            @RequestParam(name = "userId", required = false) Long userId,
                    
                                                                                                                                            @RequestParam(name = "browseNumber", required = false) Integer browseNumber,
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

    HouseBrowseLogRecord record = new HouseBrowseLogRecord();
                                                                                                                                                                                                record.setUserId(userId);
                                                                                                                        record.setBrowseNumber(browseNumber);
                        
                                

    List<HouseBrowseLogRecord> houseBrowseLogPage = queryHouseBrowseLogDao.findHouseBrowseLogPage(page, record, tag, search, orderBy, null, null);

        
        page.setRecords(houseBrowseLogPage);

        return SuccessTip.create(page);
    }
}

