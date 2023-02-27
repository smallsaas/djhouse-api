package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @description: t_house_facilitate_people表的实体类
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/2/23 16:22
 * @author: hhhhhtao
 */
@TableName("t_house_facilitate_people")
public class FacilitatePeople extends Model<FacilitatePeople> {

    private static final long serialVersionUID = 1L;

    // 主键
    @TableId(type = IdType.AUTO)
    private Integer id;

    // 服务名
    private String serverName;

    // 联系人
    private String linkmanName;

    // 联系电话
    private String contactNumber;

    // 微信号
    private String wechat;

    // 备注
    private String notes;

    // 标签
    private String tags;

    // 状态
    private Boolean status;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getLinkmanName() {
        return linkmanName;
    }
    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName;
    }

    public String getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getWechat() {
        return wechat;
    }
    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }

    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }

    // 每个属性的长度
    public static final int SERVER_NAME_LENGTH = 10;
    public static final int LINKMAN_NAME_LENGTH = 5;
    public static final int CONTACT_NUMBER_LENGTH = 11;
    public static final int WECHAT_LENGTH = 20;
    public static final int NOTES_LENGTH = 50;
    public static final int TAGS_LENGTH = 30;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
