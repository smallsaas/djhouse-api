package com.jfeat.am.module.house.api.app.operations;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.common.annotation.Permission;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.module.house.api.permission.HouseVrPicturePermission;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseVrPictureDao;
import com.jfeat.am.module.house.services.domain.dao.QueryHouseVrTypeDao;
import com.jfeat.am.module.house.services.domain.model.HouseVrPictureRecord;
import com.jfeat.am.module.house.services.domain.model.HouseVrTypeRecord;
import com.jfeat.am.module.house.services.domain.service.HouseVrPictureService;
import com.jfeat.am.module.house.services.domain.service.HouseVrTypeService;
import com.jfeat.am.module.house.services.gen.crud.model.HouseVrPictureModel;
import com.jfeat.am.module.house.services.gen.crud.model.HouseVrTypeModel;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseDesignModelMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseVrPictureMapper;
import com.jfeat.am.module.house.services.gen.persistence.dao.HouseVrTypeMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseDesignModel;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseVrPicture;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseVrType;
import com.jfeat.am.module.house.services.utility.UserCommunityAsset;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import com.jfeat.crud.plus.META;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/u/house/operations/userVrManageEndpoint")
public class UserVrManageEndpoint {

    @Resource
    HouseVrTypeService houseVrTypeService;

    @Resource
    QueryHouseVrTypeDao queryHouseVrTypeDao;

    @Resource
    HouseVrPictureService houseVrPictureService;

    @Resource
    QueryHouseVrPictureDao queryHouseVrPictureDao;

    @Resource
    HouseVrPictureMapper houseVrPictureMapper;

    @Resource
    UserCommunityAsset userCommunityAsset;

    @Resource
    HouseVrTypeMapper houseVrTypeMapper;

    @Resource
    HouseDesignModelMapper houseDesignModelMapper;



    @PostMapping("/vrCategory")
    public Tip createHouseVrType(@RequestBody HouseVrType entity) {
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        Integer affected = 0;
        try {
            entity.setOrgId(JWTKit.getOrgId());
            affected = houseVrTypeService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }

    @GetMapping("/vrCategory/{id}")
    public Tip getHouseVrType(@PathVariable Long id) {
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        return SuccessTip.create(houseVrTypeService.queryMasterModel(queryHouseVrTypeDao, id));
    }


    @PutMapping("/vrCategory/{id}")
    public Tip updateHouseVrType(@PathVariable Long id, @RequestBody HouseVrType entity) {
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        entity.setId(id);
        entity.setOrgId(JWTKit.getOrgId());
        return SuccessTip.create(houseVrTypeService.updateMaster(entity));
    }


    @DeleteMapping("/vrCategory/{id}")
    public Tip deleteHouseVrType(@PathVariable Long id) {
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        QueryWrapper<HouseVrPicture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseVrPicture.TYPE_ID,id);
        List<HouseVrPicture> houseVrPictureList = houseVrPictureMapper.selectList(queryWrapper);
        if (houseVrPictureList!=null && houseVrPictureList.size()>0){
            throw new BusinessException(BusinessCode.CodeBase,"该类型已有VR图不可删除");
        }
        return SuccessTip.create(houseVrTypeService.deleteMaster(id));
    }

    @GetMapping("/vrCategory")
    public Tip queryHouseVrTypePage(Page<HouseVrTypeRecord> page,
                                    @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                    @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                    // for tag feature query
                                    @RequestParam(name = "tag", required = false) String tag,
                                    // end tag
                                    @RequestParam(name = "search", required = false) String search,

                                    @RequestParam(name = "name", required = false) String name,

                                    @RequestParam(name = "communityId",required = false) Long communityId,
                                    @RequestParam(name = "status",required = false) Integer status,

                                    @RequestParam(name = "orgId", required = false) Long orgId,
                                    @RequestParam(name = "orderBy", required = false) String orderBy,
                                    @RequestParam(name = "sort", required = false) String sort) {

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

        HouseVrTypeRecord record = new HouseVrTypeRecord();
        record.setName(name);
        record.setCommunityId(communityId);
        record.setStatus(status);

        if (META.enabledSaas()) {
            record.setOrgId(JWTKit.getOrgId());
        }

        List<HouseVrTypeRecord> houseVrTypePage = queryHouseVrTypeDao.findHouseVrTypePage(page, record, tag, search, orderBy, null, null);

        page.setRecords(houseVrTypePage);

        return SuccessTip.create(page);
    }





    @GetMapping("/getHouseVrTypeListItem")
    public Tip getHouseVrTypeListItem(@RequestParam(name = "communityId",required = true) Long communityId){

        HouseVrType record = new HouseVrType();
        record.setCommunityId(communityId);
        List<HouseVrType> houseVrTypePage = queryHouseVrTypeDao.queryHouseVrTypeListWithItem(null, record, null, null, null, null, null);



        return SuccessTip.create(houseVrTypePage);

    }

    @GetMapping("/vrCategory/getCurrentCommunity")
    public Tip getCurrentCommunityVrCategory(){
        if (JWTKit.getUserId()==null){
            throw new BusinessException(BusinessCode.NoPermission,"没有登录");
        }
        Long communityId =  userCommunityAsset.getUserCommunityStatus(JWTKit.getUserId());
        if (communityId==null){
            throw new BusinessException(BusinessCode.CodeBase,"没有找到当前小区信息");
        }
        QueryWrapper<HouseVrType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseVrType.COMMUNITY_id,communityId);
        return SuccessTip.create(houseVrTypeMapper.selectList(queryWrapper));


    }


    /*
上架vr类型
*/
    @PutMapping("/vrCategory/shelvesVrPicture/{id}")
    public Tip shelvesVrCategory(@PathVariable("id") Long id) {
        HouseVrTypeModel houseVrTypeModel = queryHouseVrTypeDao.queryMasterModel(id);
        if (houseVrTypeModel != null) {
            houseVrTypeModel.setStatus(HouseVrType.STATUS_SHELVE);
            return SuccessTip.create(houseVrTypeService.updateMaster(houseVrTypeModel));
        }
        return SuccessTip.create();
    }

    /*
    下架vr类型
     */
    @PutMapping("/vrCategory/unshelvesVrPicture/{id}")
    public Tip unshelvesVrCategory(@PathVariable("id") Long id) {
        HouseVrTypeModel houseVrTypeModel = queryHouseVrTypeDao.queryMasterModel(id);
        if (houseVrTypeModel != null) {
            houseVrTypeModel.setStatus(HouseVrType.STATUS_UNSHELVE);
            return SuccessTip.create(houseVrTypeService.updateMaster(houseVrTypeModel));
        }
        return SuccessTip.create();
    }



    @PostMapping("/vrPicture")
    public Tip createHouseVrPicture(@RequestBody HouseVrPicture entity) {
        Integer affected = 0;
        try {
            affected = houseVrPictureService.createMaster(entity);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(BusinessCode.DuplicateKey);
        }

        return SuccessTip.create(affected);
    }


    @GetMapping("/vrPicture")
    public Tip getHouseVrPictureList(@RequestParam(value = "typeId",required = true) Long typeId){
        QueryWrapper<HouseVrPicture> houseVrPictureQueryWrapper = new QueryWrapper<>();
        houseVrPictureQueryWrapper.eq(HouseVrPicture.TYPE_ID,typeId);
        List<HouseVrPicture> houseVrPictureList = houseVrPictureMapper.selectList(houseVrPictureQueryWrapper);
        return SuccessTip.create(houseVrPictureList);
    }


    @GetMapping
    public Tip queryHouseVrPicturePage(Page<HouseVrPictureRecord> page,
                                       @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                       // for tag feature query
                                       @RequestParam(name = "tag", required = false) String tag,
                                       // end tag
                                       @RequestParam(name = "search", required = false) String search,

                                       @RequestParam(name = "name", required = false) String name,

                                       @RequestParam(name = "typeId",required = false) Long typeId,

                                       @RequestParam(name = "link", required = false) String link,

                                       @RequestParam(name = "vrPicture", required = false) String vrPicture,
                                       @RequestParam(name = "snapshot", required = false) String snapshot,
                                       @RequestParam(name = "star", required = false) Integer star,
                                       @RequestParam(name = "bedrooms", required = false) String bedrooms,
                                       @RequestParam(name = "style", required = false) String style,

                                       @RequestParam(name = "typeOption", required = false) String typeOption,
                                       @RequestParam(name = "note", required = false) String note,
                                       @RequestParam(name = "orderBy", required = false) String orderBy,
                                       @RequestParam(name = "sort", required = false) String sort) {

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

        HouseVrPictureRecord record = new HouseVrPictureRecord();
        record.setTypeId(typeId);
        record.setName(name);
        record.setLink(link);
        record.setVrPicture(vrPicture);
        record.setSnapshot(snapshot);
        record.setStar(star);
        record.setBedrooms(bedrooms);
        record.setStyle(style);
        record.setTypeOption(typeOption);
        record.setNote(note);


        List<HouseVrPictureRecord> houseVrPicturePage = queryHouseVrPictureDao.findHouseVrPicturePage(page, record, tag, search, orderBy, null, null);


        page.setRecords(houseVrPicturePage);

        return SuccessTip.create(page);
    }





    @GetMapping("/vrPicture/{id}")
    public Tip getHouseVrPicture(@PathVariable Long id) {
        return SuccessTip.create(houseVrPictureService.queryMasterModel(queryHouseVrPictureDao, id));
    }


    @PutMapping("/vrPicture/{id}")
    public Tip updateHouseVrPicture(@PathVariable Long id, @RequestBody HouseVrPicture entity) {
        entity.setId(id);
        return SuccessTip.create(houseVrPictureService.updateMaster(entity));
    }


    @DeleteMapping("/vrPicture/{id}")
    public Tip deleteHouseVrPicture(@PathVariable Long id) {

        QueryWrapper<HouseDesignModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(HouseDesignModel.VR_ID,id);
        List<HouseDesignModel> houseDesignModelList = houseDesignModelMapper.selectList(queryWrapper);
        if (houseDesignModelList!=null && houseDesignModelList.size()>0){
            throw new BusinessException(BusinessCode.CodeBase,"已有户型绑定改VR,不可删除");
        }

        return SuccessTip.create(houseVrPictureService.deleteMaster(id));
    }


    /*
上架vr图
 */
    @PutMapping("/vrPicture/shelvesVrPicture/{id}")
    public Tip shelvesVrPicture(@PathVariable("id") Long id) {
        HouseVrPictureModel houseVrPictureModel = queryHouseVrPictureDao.queryMasterModel(id);
        if (houseVrPictureModel != null) {
            houseVrPictureModel.setStatus(HouseVrPicture.STATUS_SHELVES);
            return SuccessTip.create(houseVrPictureService.updateMaster(houseVrPictureModel));
        }
        return SuccessTip.create();
    }

    /*
    下架vr图
     */
    @PutMapping("/vrPicture/unshelvesVrPicture/{id}")
    public Tip unshelvesVrPicture(@PathVariable("id") Long id) {
        HouseVrPictureModel houseVrPictureModel = queryHouseVrPictureDao.queryMasterModel(id);
        if (houseVrPictureModel != null) {
            houseVrPictureModel.setStatus(HouseVrPicture.STATUS_UNSHELVES);
            return SuccessTip.create(houseVrPictureService.updateMaster(houseVrPictureModel));
        }
        return SuccessTip.create();
    }




}
