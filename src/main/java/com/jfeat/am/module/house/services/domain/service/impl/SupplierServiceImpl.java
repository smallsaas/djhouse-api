package com.jfeat.am.module.house.services.domain.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jfeat.am.core.jwt.JWTKit;
import com.jfeat.am.core.model.UserType;
import com.jfeat.am.module.house.services.domain.service.SupplierService;
import com.jfeat.am.module.house.services.gen.crud.model.SupplierModel;
import com.jfeat.am.module.house.services.gen.crud.service.impl.CRUDSupplierServiceImpl;
import com.jfeat.am.module.house.services.gen.persistence.dao.SupplierMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.Supplier;
import com.jfeat.am.uaas.perm.services.persistence.dao.SysRoleMapper;
import com.jfeat.am.uaas.perm.services.persistence.model.SysRole;
import com.jfeat.am.uaas.system.services.domain.service.SysUserService;
import com.jfeat.am.uaas.system.services.transfer.Register;
import com.jfeat.am.uaas.system.services.transfer.RegisterTenant;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import com.jfeat.org.services.domain.service.SysOrgService;
import com.jfeat.org.services.persistence.dao.SysOrgMapper;
import com.jfeat.org.services.persistence.model.SysOrg;
import com.jfeat.org.services.persistence.model.SysUser;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2017-10-16
 */

@Service("supplierService")
public class SupplierServiceImpl extends CRUDSupplierServiceImpl implements SupplierService {

    @Resource
    SysUserService sysUserService;

    @Resource
    SysOrgMapper sysOrgMapper;

    @Resource
    SupplierMapper supplierMapper;
    @Resource
    SysOrgService sysOrgService;

    @Resource
    SysRoleMapper sysRoleMapper;

    @Override
    @Transactional
    public Supplier createOne(SupplierModel entity){
        Long tenantId = sysUserService.getOrgIdByDomain();
        String domain = sysUserService.getDomain();
        String account = entity.getAccount();
        //如果有输入账号
        if(account!=null && !account.equals("")){
            SysUser sysUser = sysUserService.checkUserExist(entity.getAccount(), tenantId);
            if(sysUser!=null){
                throw new BusinessException(BusinessCode.BadRequest,"用户账号已存在："+entity.getAccount());
            }

        }else{
            account = genAccountName(entity.getName());
        }


        /***------------创建组织-----------****/
        SysOrg sysOrg = new SysOrg();
        sysOrg.setName(entity.getName());
        sysOrg.setPid(tenantId);
        sysOrgService.createNewNode(tenantId,sysOrg,false);
        //维护租户id字段
        sysOrg.setTenantId(tenantId);
        sysOrgMapper.updateById(sysOrg);
        Long orgId = sysOrg.getId();

        /***------------创建系统用户分配角色-----------****/
        //初始化使用的角色id
        //查询一个管理员角色赋予给新建的用户
        QueryWrapper<SysRole> roleQueryWrapper = new QueryWrapper<>();
        SysRole sysRole = sysRoleMapper.selectOne(roleQueryWrapper.like("name","供应商").last(" limit 1 "));
        List<Long> ids=new ArrayList<>();
        ids.add(sysRole.getId());
        //为租户 生成admin名字的用户
        SysUser register = new SysUser();
        register.setName(entity.getName());
        register.setAccount(account);
        register.setPassword("111111");
        register.setTenantOrgId(tenantId);
        register.setOrgId(orgId);
        sysUserService.saveUser(register, ids, null);


        /***------------创建供应商-----------****/
        entity.setUserId(register.getId());
        entity.setOrgId(orgId);
        Integer master = supplierMapper.insert(entity);
        return entity;

    }

    @Override
    @Transactional
    public Integer deleteOne(Long id){
        Supplier supplier = supplierMapper.selectById(id);
        //删除用户
        boolean b = sysUserService.deleteUser(supplier.getUserId());
        if(!b){
            throw new BusinessException(BusinessCode.BadRequest,"用户删除失败");
        }
        //删除组织
        sysOrgService.deleteNode(JWTKit.getOrgId(), supplier.getOrgId());
        //删除供应商
        int i = supplierMapper.deleteById(id);
        return i;

    }


    @Override
    public String genAccountName(String name){
        name = cleanString(name);
        String pinYinHeadChar = getPinYinHeadChar(name);
        return pinYinHeadChar;
    }



    public String cleanString(String str){
        String regEx="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*()——+|{}_——\\-【】‘；：”“’。， 、？-]";
        //可以在中括号内加上任何想要替换的字符，实际上是一个正则表达式
        String replaceTo = "";//这里是将特殊字符换为replaceTo字符串," "代表直接去掉
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);//这里把想要替换的字符串传进来
        String newString = m.replaceAll(replaceTo).trim();
        //将替换后的字符串存在变量newString中
        return newString;
    }

    /**
     * 得到中文首字母（中国 -> ZG）
     * @param str 需要转化的中文字符串
     * @return 大写首字母缩写的字符串
     */
    public static String getPinYinHeadChar(String str) {
        StringBuilder convert = new StringBuilder();
        if (StringUtils.isBlank(str)) {
            return "";
        }
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert.append(pinyinArray[0].charAt(0));
            } else {
                convert.append(word);
            }
        }
        return convert.toString().toUpperCase();
    }


}
