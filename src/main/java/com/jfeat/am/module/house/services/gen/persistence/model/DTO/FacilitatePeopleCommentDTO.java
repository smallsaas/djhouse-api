package com.jfeat.am.module.house.services.gen.persistence.model.DTO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

/**
 * @description: 便民服务评论功能DTO(数据传输对象)
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/6/21 18:10
 * @author: hhhhhtao
 */
public class FacilitatePeopleCommentDTO {

    // id
    private Long id;

    // uid 唯一标识
    private String uid;

    // 创建时间
    private LocalDateTime createTime;

    // 所属便民服务
    private Integer facilitatePeopleId;

    // 所属用户
    private Long userId;

    // 评论内容
    private String value;

    // 用户名
    private String username;

    // 用户头像
    private String avatar;

    // 是否匿名
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }
}
