package com.jfeat.am.module.house.services.constant;

import com.jfeat.am.module.house.services.gen.persistence.model.FacilitatePeopleComment;

/**
 * @description: TODO
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/6/26 16:41
 * @author: hhhhhtao
 */
public final class FacilitatePeopleCommentConst {

    private FacilitatePeopleCommentConst() {}

    // 便民服务id key
    public static final String FACILITATE_PEOPLE_ID_KEY = "facilitatePeopleId";
    // 是否已发布的 key
    public static final String PUBLISHED_KEY = "published";
    // 已发布
    public static final int PUBLISHED = 1;
    // 未发布
    public static final int UNRELEASED = 0;
}
