package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

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
    private Integer id;

    // 服务名
    private String serverName;

    // 联系人
    private String linkman;

    // 联系电话
    private String contactNumber;

    // 备注
    private String notes;

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

    public String getLinkman() {
        return linkman;
    }
    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
