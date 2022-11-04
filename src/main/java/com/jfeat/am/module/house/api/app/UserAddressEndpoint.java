package com.jfeat.am.module.house.api.app;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAddressDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseUserAssetDao;
import com.jfeat.am.module.house.services.domain.model.HouseUserAddressRecord;
import com.jfeat.am.module.house.services.domain.model.HouseUserAssetRecord;
import com.jfeat.am.module.house.services.domain.service.HouseUserAddressService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseUserAddressModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseUserAddress;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.module.kafka.services.domain.service.KafkaEmailProductService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/houseUserAddress")
public class UserAddressEndpoint {


    @Resource
    HouseUserAddressService houseUserAddressService;

    @Resource
    QueryHouseUserAddressDao queryHouseUserAddressDao;

    @Resource
    QueryHouseUserAssetDao queryHouseUserAssetDao;


    @Resource
    KafkaEmailProductService kafkaEmailProductService;




//    新建用户地址
    @PostMapping
    public Tip createHouseUserAddress(@RequestBody HouseUserAddress entity) {
        Integer affected = 0;
        Long userId = JWTKit.getUserId();
        if (userId == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        if (entity.getAssetId()==null || entity.getAssetId().equals("")){
            throw new BusinessException(BusinessCode.BadRequest,"assetId不能为空");
        }
        HouseUserAssetRecord record = new HouseUserAssetRecord();
        record.setUserId(userId);
        record.setAssetId(entity.getAssetId());
        List<HouseUserAssetRecord> recordList = queryHouseUserAssetDao.findHouseUserAssetPage(null,record,null,null,null,null,null);

//        将查询到的用户房屋信息地址 添加到用户地址表中
        if (recordList!=null || recordList.size()==1){
            HouseUserAddress houseUserAddress = new HouseUserAddress();
            houseUserAddress.setUserId(userId);
            houseUserAddress.setAssetId(entity.getAssetId());
            houseUserAddress.setUserName(recordList.get(0).getUsername());
            houseUserAddress.setUserPhone(recordList.get(0).getUserPhone());
            houseUserAddress.setCommunity(recordList.get(0).getCommunityName());
            houseUserAddress.setBuildingCode(recordList.get(0).getBuildingCode());
            houseUserAddress.setRoomNumber(recordList.get(0).getRoomNumber());
            houseUserAddress.setAddress(recordList.get(0).getAddress());
            try {
                affected = houseUserAddressService.createMaster(houseUserAddress);
            } catch (DuplicateKeyException e) {
                throw new BusinessException(BusinessCode.DuplicateKey,"改地址已存在");
            }

            return SuccessTip.create(affected);
        }

       return SuccessTip.create(0);
    }


    @GetMapping("/{id}")
    public Tip getHouseUserAddress(@PathVariable Long id) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        return SuccessTip.create(houseUserAddressService.queryMasterModel(queryHouseUserAddressDao, id));
    }


    @PutMapping("/{id}")
    public Tip updateHouseUserAddress(@PathVariable Long id, @RequestBody HouseUserAddress entity) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

        HouseUserAddressModel houseUserAddressModel = queryHouseUserAddressDao.queryMasterModel(id);
        if (houseUserAddressModel!=null){
            if (entity.getUserName()!=null){
                houseUserAddressModel.setUserName(entity.getUserName());
            }
            if (entity.getUserPhone()!=null){
                houseUserAddressModel.setUserPhone(entity.getUserPhone());
            }
            return SuccessTip.create(houseUserAddressService.updateMaster(houseUserAddressModel));
        }
        return SuccessTip.create();
    }

    @DeleteMapping("/{id}")
    public Tip deleteHouseUserAddress(@PathVariable Long id) {
        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }
        return SuccessTip.create(houseUserAddressService.deleteMaster(id));
    }


    @GetMapping
    public Tip queryHouseUserAddressPage(Page<HouseUserAddressRecord> page,
                                         @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                         @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                         // for tag feature query
                                         @RequestParam(name = "tag", required = false) String tag,
                                         // end tag
                                         @RequestParam(name = "search", required = false) String search,

                                         @RequestParam(name = "assetId", required = false) Long assetId,

                                         @RequestParam(name = "community", required = false) String community,

                                         @RequestParam(name = "buildingCode", required = false) String buildingCode,

                                         @RequestParam(name = "roomNumber", required = false) String roomNumber,

                                         @RequestParam(name = "userName", required = false) String userName,

                                         @RequestParam(name = "userPhone", required = false) String userPhone,
                                         @RequestParam(name = "orderBy", required = false) String orderBy,
                                         @RequestParam(name = "sort", required = false) String sort) {

        if (JWTKit.getUserId() == null) {
            throw new BusinessException(BusinessCode.NoPermission, "用户未登录");
        }

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

        HouseUserAddressRecord record = new HouseUserAddressRecord();
        record.setUserId(JWTKit.getUserId());
        record.setAssetId(assetId);
        record.setCommunity(community);
        record.setBuildingCode(buildingCode);
        record.setRoomNumber(roomNumber);
        record.setUserName(userName);
        record.setUserPhone(userPhone);


        List<HouseUserAddressRecord> houseUserAddressPage = queryHouseUserAddressDao.findHouseUserAddressPage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseUserAddressPage);

        return SuccessTip.create(page);
    }

    @GetMapping("/test")
    public SuccessTip test(){

        return SuccessTip.create();
    }
}
