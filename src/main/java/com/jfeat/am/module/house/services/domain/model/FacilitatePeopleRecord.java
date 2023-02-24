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
}
