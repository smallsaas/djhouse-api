package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;

/**
 * @description: t_house_facilitate_people_comment 便民服务评论实体类
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/6/21 17:23
 * @author: hhhhhtao
 */
@TableName("t_house_facilitate_people_comment")
public class FacilitatePeopleComment {

    private static final long serialVersionUID = 1L;

    // id
    @TableId(type = IdType.AUTO)
    private Long id;

    // uid 唯一标识
    private String uid;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;

    // 所属便民服务
    private Integer facilitatePeopleId;

    // 所属用户
    private Long userId;

    // 评论内容
    private String value;

    // 逻辑删除
    @TableField(value = "is_deleted")
    private Boolean deleted;

    // 是否匿名
    @TableField(value = "is_anonymous")
    private Boolean anonymous;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getFacilitatePeopleId() {
        return facilitatePeopleId;
    }
    public void setFacilitatePeopleId(Integer facilitatePeopleId) {
        this.facilitatePeopleId = facilitatePeopleId;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }
    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }
}
