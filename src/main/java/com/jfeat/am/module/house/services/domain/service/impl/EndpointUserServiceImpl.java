package com.jfeat.am.module.house.services.domain.service.impl;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.core.model.EndUserTypeSetting;
import com.jfeat.am.module.house.services.domain.dao.QueryEndpointUserDao;
import com.jfeat.am.module.house.services.domain.model.EndpointUserRecord;
import com.jfeat.am.module.house.services.domain.service.EndpointUserService;
import com.jfeat.am.module.house.services.gen.crud.model.EndpointUserModel;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDEndpointUserServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.model.EndpointUser;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.users.account.services.domain.service.UserAccountService;
import com.jfeat.users.account.services.gen.persistence.dao.UserAccountMapper;
import com.jfeat.users.account.services.gen.persistence.model.UserAccount;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("endpointUserService")
public class EndpointUserServiceImpl extends CRUDEndpointUserServiceImpl implements EndpointUserService {

    @Resource
    QueryEndpointUserDao endpointUserDao;

    @Resource
    private UserAccountMapper userAccountMapper;

    @Resource
    UserAccountService userAccountService;

    @Override
    protected String entityName() {
        return "EndpointUser";
    }


    @Override
    public EndpointUserModel listUser(Long id) {
        return endpointUserDao.queryMasterModel(id);
    }

    /**
     * 销售 修改 置业顾问的联系电话
     *
     * @param id      置业顾问的id
     * @param contact 修改后的联系电话
     * @return
     */
    @Override
    public int salesUpdateIntermediaryContact(Long id, String contact) {
        // 查询当前访问用户信息,判断用户权限
        UserAccount userAccount = userAccountMapper.selectById(JWTKit.getUserId());
        if (userAccount == null) throw new BusinessException(BusinessCode.UserNotExisted,"无效用户");
        // 获取用户类型列表
        if (userAccount.getType() != null) {
            List<Integer> userTypeList = userAccountService.getUserTypeList(userAccount.getType());
            // 判断请求进来的用户类型是否有销售，不是销售则没有权限进行操作
            if (userTypeList == null || !(userTypeList.contains(EndUserTypeSetting.USER_TYPE_SALES))) {
                throw new BusinessException(BusinessCode.NoPermission,"您没有该权限");
            }
        }

        // 判断该id用户，确认是否是置业顾问,不是置业顾问不允许替换该用户的联系电话号码
        if (id == null) throw new BusinessException(BusinessCode.EmptyNotAllowed,"id cannot null");
        UserAccount serverAccount = userAccountMapper.selectById(id);
        if (serverAccount == null) throw new BusinessException(BusinessCode.UserNotExisted,"id" + id + "用户不存在");
        // 获取该用户类型列表
        if (serverAccount.getType() != null) {
            List<Integer> serverTypeList = userAccountService.getUserTypeList(serverAccount.getType());
            // 判断是否有置业顾问权限，不是置业顾问则不允许替换
            if (serverTypeList == null || !(serverTypeList.contains(EndUserTypeSetting.USER_TYPE_INTERMEDIARY))) {
                throw new BusinessException(BusinessCode.NoPermission,"id" + id + "不是置业顾问，无法替换非置业顾问的联系手机");
            }
        }

        // 判断contact
        if (org.apache.commons.lang3.StringUtils.isBlank(contact)) throw new BusinessException(BusinessCode.EmptyNotAllowed,"contact cannot null");
        if (contact.length() > 11) throw new BusinessException(BusinessCode.OutOfRange,"输入的手机号大于11位数字，请输入正确的手机号");

        // 执行更新
        EndpointUser user = new EndpointUser();
        user.setId(id);
        user.setContact(contact);
        int affected = endpointUserDao.updateById(user);

        // 判断影响数
        if (affected < 1) throw new BusinessException(BusinessCode.DatabaseUpdateError,"更新失败");

        return affected;
    }

    /**
     * 获取置业顾问信息
     * 如果请求用户没有销售权限 或 置业顾问id实际参数为非置业顾问都会抛出异常
     *
     * @param id 置业顾问id
     * @return
     */
    @Override
    public EndpointUser salesGetIntermediaryInfo(Long id) {
        // 查询当前访问用户信息,判断用户权限
        UserAccount userAccount = userAccountMapper.selectById(JWTKit.getUserId());
        if (userAccount == null) throw new BusinessException(BusinessCode.UserNotExisted,"无效用户");
        // 获取用户类型列表
        if (userAccount.getType() != null) {
            List<Integer> userTypeList = userAccountService.getUserTypeList(userAccount.getType());
            // 判断请求进来的用户类型是否有销售，不是销售则没有权限进行操作
            if (userTypeList == null || !(userTypeList.contains(EndUserTypeSetting.USER_TYPE_SALES))) {
                throw new BusinessException(BusinessCode.NoPermission,"您没有该权限");
            }
        }

        // 判断该id用户，确认是否是置业顾问,不是置业顾问不允许获取
        if (id == null) throw new BusinessException(BusinessCode.EmptyNotAllowed,"id cannot null");
        UserAccount serverAccount = userAccountMapper.selectById(id);
        if (serverAccount == null) throw new BusinessException(BusinessCode.UserNotExisted,"id" + id + "用户不存在");
        // 获取该用户类型列表
        if (serverAccount.getType() != null) {
            List<Integer> serverTypeList = userAccountService.getUserTypeList(serverAccount.getType());
            // 判断是否有置业顾问权限，不是置业顾问则不允许获取
            if (serverTypeList == null || !(serverTypeList.contains(EndUserTypeSetting.USER_TYPE_INTERMEDIARY))) {
                throw new BusinessException(BusinessCode.NoPermission,"id" + id + "不是置业顾问，无法替换非置业顾问的联系手机");
            }
        }

        // 执行查询，并提取必要信息返回
        EndpointUser intermediary = endpointUserDao.selectById(id);
        EndpointUser result = new EndpointUser();
        result.setId(intermediary.getId());
        result.setName(intermediary.getName());
        result.setPhone(intermediary.getPhone());
        result.setContact(intermediary.getContact());

        return result;
    }
}
