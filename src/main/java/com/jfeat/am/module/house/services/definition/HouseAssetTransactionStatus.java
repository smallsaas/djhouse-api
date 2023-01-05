package com.jfeat.am.module.house.services.definition;

import com.jfeat.am.module.house.services.gen.persistence.model.HouseAssetTransaction;

public enum HouseAssetTransactionStatus {


    BUY(HouseAssetTransaction.STATE_BUY,"购买"),
    SELL(HouseAssetTransaction.STATE_SELL,"出售"),

    ;

    HouseAssetTransactionStatus(Integer state, String status) {
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

        for (HouseAssetTransactionStatus houseAssetTransactionStatus : HouseAssetTransactionStatus.values()){
            if (houseAssetTransactionStatus.name().equals(name)){
                return houseAssetTransactionStatus.getState();
            }
        }
        return null;
    }


    public static String getNameByState(Integer state){

        for (HouseAssetTransactionStatus houseAssetTransactionStatus : HouseAssetTransactionStatus.values()){
            if (houseAssetTransactionStatus.getState().equals(state)){
                return houseAssetTransactionStatus.name();
            }
        }
        return null;
    }


    public static String getStatusByState(Integer state){
        for (HouseAssetTransactionStatus houseAssetTransactionStatus : HouseAssetTransactionStatus.values()){
            if (houseAssetTransactionStatus.getState().equals(state)){
                return houseAssetTransactionStatus.getStatus();
            }
        }
        return null;
    }

    public static String getStatusByName(String name){
        for (HouseAssetTransactionStatus houseAssetTransactionStatus : HouseAssetTransactionStatus.values()){
            if (houseAssetTransactionStatus.name().equals(name)){
                return houseAssetTransactionStatus.getStatus();
            }
        }
        return null;
    }


    public static Boolean containName(String name){

        for (HouseAssetTransactionStatus houseAssetTransactionStatus : HouseAssetTransactionStatus.values()){
            if (houseAssetTransactionStatus.name().equals(name)){
                return true;
            }
        }

        return false;
    }

    public static Boolean containerState(Integer state){
        for (HouseAssetTransactionStatus houseAssetTransactionStatus : HouseAssetTransactionStatus.values()){
            if (houseAssetTransactionStatus.getState().equals(state)){
                return true;
            }
        }
        return false;
    }

}
