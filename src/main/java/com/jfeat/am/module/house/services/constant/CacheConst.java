package com.jfeat.am.module.house.services.constant;

/**
 * @description: 缓存业务常量
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/5/11 12:17
 * @author: hhhhhtao
 */
public final class CacheConst {

    // 便民服务拨打数redis key前缀
    private static final String FACILITATE_PEOPLE_REDIS_KEY_PREFIX = "facilitatePeople";
    // 便民服务拨打次数的初始化数
    public static final String FACILITATE_PEOPLE_INITIALIZATION_NUMBER = "0";
    // 便民服务拨打次数每次添加数
    public static final long FACILITATE_PEOPLE_ONCE_ADD_NUMBER = 1;

    /**
     * 便民服务拨打数redis key拼接方法
     * 完整的便民服务拨打数在redis中的key为：FACILITATE_PEOPLE_REDIS_KEY_PREFIX + 便民服务id
     *
     * @param id 便民服务id
     * @return
     */
    public static String getFacilitatePeopleRedisKey(Integer id) {
        return FACILITATE_PEOPLE_REDIS_KEY_PREFIX + id;
    }

    private CacheConst() {}
}
