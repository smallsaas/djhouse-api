package com.jfeat.am.module.house.services.definition;


import com.jfeat.am.module.house.services.gen.persistence.model.HouseRentLog;

public enum HouseRentLogStatus {
    putOnShelves(HouseRentLog.PUT_ON_SHELVES,"上架"),
    pullOffShelves(HouseRentLog.PULL_OFF_SHELVES,"下架"),
    updateRentInfo(HouseRentLog.UPDATE_RENT_INFO,"更新信息"),
    createRentInfo(HouseRentLog.CREATE_RENT_INFO,"发布"),
    deleteRentInfo(HouseRentLog.DELETE_RENT_INFO,"删除")
    ;

    HouseRentLogStatus(Integer state, String status) {
        this.state = state;
        this.status = status;
    }

    private Integer state;

    private String status;

    public Integer getState() {
        return state;
    }

    public String getStatus() {
        return status;
    }

    public static Integer getStateByName(String name){

        for (HouseRentLogStatus HouseRentLogStatus : HouseRentLogStatus.values()){
            if (HouseRentLogStatus.name().equals(name)){
                return HouseRentLogStatus.getState();
            }
        }
        return null;
    }


    public static String getNameByState(Integer state){

        for (HouseRentLogStatus HouseRentLogStatus : HouseRentLogStatus.values()){
            if (HouseRentLogStatus.getState().equals(state)){
                return HouseRentLogStatus.name();
            }
        }
        return null;
    }


    public static String getStatusByState(Integer state){
        for (HouseRentLogStatus HouseRentLogStatus : HouseRentLogStatus.values()){
            if (HouseRentLogStatus.getState().equals(state)){
                return HouseRentLogStatus.getStatus();
            }
        }
        return null;
    }

    public static String getStatusByName(String name){
        for (HouseRentLogStatus HouseRentLogStatus : HouseRentLogStatus.values()){
            if (HouseRentLogStatus.name().equals(name)){
                return HouseRentLogStatus.getStatus();
            }
        }
        return null;
    }


    public static Boolean containName(String name){

        for (HouseRentLogStatus HouseRentLogStatus : HouseRentLogStatus.values()){
            if (HouseRentLogStatus.name().equals(name)){
                return true;
            }
        }

        return false;
    }

    public static Boolean containerState(Integer state){
        for (HouseRentLogStatus HouseRentLogStatus : HouseRentLogStatus.values()){
            if (HouseRentLogStatus.getState().equals(state)){
                return true;
            }
        }
        return false;
    }
}
