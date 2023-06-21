package com.jfeat.am.module.house.services.domain.model;

/**
 * @description: FacilitatePeople DTO
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/2/24 10:28
 * @author: hhhhhtao
 */
public class FacilitatePeopleRecord {

    private static final long serialVersionUID = 1L;

    // 主键
    private Integer id;

    // 服务名
    private String serverName;

    // 联系人
    private String linkmanName;

    // 联系电话
    private String contactNumber;

    // 备注
    private String notes;

    // 微信号
    private String wechat;

    // 电话拨打次数
    private Integer frequency;

    // 是否置顶
    private Boolean top;

    public String getWechat() {
        return wechat;
    }
    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

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

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getFrequency() {
        return frequency;
    }
    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }
}
