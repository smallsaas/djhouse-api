package com.jfeat.am.module.house.services.definition;

public class Develops {
    private Boolean tenantData;

    private Boolean userData;

    private Boolean configurationData;

    private String dataBaseVersion;

    public Boolean getTenantData() {
        return tenantData;
    }

    public void setTenantData(Boolean tenantData) {
        this.tenantData = tenantData;
    }

    public Boolean getUserData() {
        return userData;
    }

    public void setUserData(Boolean userData) {
        this.userData = userData;
    }

    public Boolean getConfigurationData() {
        return configurationData;
    }

    public void setConfigurationData(Boolean configurationData) {
        this.configurationData = configurationData;
    }

    public String getDataBaseVersion() {
        return dataBaseVersion;
    }

    public void setDataBaseVersion(String dataBaseVersion) {
        this.dataBaseVersion = dataBaseVersion;
    }

    public static final String REDIS_KEY_TENANT_DATA = "develop:tenantData";
    public static final String REDIS_KEY_USER_DATA = "develop:userData";
    public static final String REDIS_KEY_CONFIGURATION_DATA = "develop:configurationData";
    public static final String REDIS_KEY_DATABASE_VERSION = "develop:configurationData";
}
