package com.jfeat.am.module.house.services.gen.persistence.model;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author Code generator
 * @since 2022-05-27
 */
@TableName("t_product")
@ApiModel(value = "Product对象", description = "")
public class Product extends Model<Product> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer categoryId;

    private Integer brandId;

    private String name;

    private String shortName;

    private String cover;

    private Integer stockBalance;

    private Integer sales;

    private String status;

    private Date createdDate;

    private Date lastModifiedDate;

    private String unit;

    private BigDecimal price;

    private BigDecimal costPrice;

    private BigDecimal suggestedPrice;

    private Integer promoted;

    private BigDecimal freight;

    private Integer freeShipping;

    private Integer sortOrder;

    private Integer partnerLevelZone;

    private Long viewCount;

    private Integer fareId;

    private String barcode;

    private String storeLocation;

    private Integer weight;

    private Integer bulk;

    private String skuId;

    private String skuName;

    private String skuCode;

    private String barCode;

    @ApiModelProperty(value = "商家id")
    private Integer mid;

    @ApiModelProperty(value = "优惠活动-优惠券")
    private Integer allowCoupon;

    @ApiModelProperty(value = "优惠活动-可用积分")
    private Integer credit;

    @ApiModelProperty(value = "是否虚拟产品")
    private Integer isVirtual;

    @ApiModelProperty(value = "是否需要做了检测才可以购买")
    private Integer requiredParticipateExam;

    @ApiModelProperty(value = "分销价")
    private BigDecimal distributionPrice;

    @ApiModelProperty(value = "0")
    private Integer presale;

    @ApiModelProperty(value = "org_id")
    private Long orgId;

    @ApiModelProperty(value = "产品banner图")
    private String banner;

    @ApiModelProperty(value = "产品销售地区, 省-市-区,省-市,省 格式")
    private String region;

    @ApiModelProperty(value = "图片链接")
    private String description;


    public Integer getId() {
        return id;
    }

    public Product setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Product setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public Product setBrandId(Integer brandId) {
        this.brandId = brandId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public Product setShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public String getCover() {
        return cover;
    }

    public Product setCover(String cover) {
        this.cover = cover;
        return this;
    }

    public Integer getStockBalance() {
        return stockBalance;
    }

    public Product setStockBalance(Integer stockBalance) {
        this.stockBalance = stockBalance;
        return this;
    }

    public Integer getSales() {
        return sales;
    }

    public Product setSales(Integer sales) {
        this.sales = sales;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Product setStatus(String status) {
        this.status = status;
        return this;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Product setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Product setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public Product setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public Product setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
        return this;
    }

    public BigDecimal getSuggestedPrice() {
        return suggestedPrice;
    }

    public Product setSuggestedPrice(BigDecimal suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
        return this;
    }

    public Integer getPromoted() {
        return promoted;
    }

    public Product setPromoted(Integer promoted) {
        this.promoted = promoted;
        return this;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public Product setFreight(BigDecimal freight) {
        this.freight = freight;
        return this;
    }

    public Integer getFreeShipping() {
        return freeShipping;
    }

    public Product setFreeShipping(Integer freeShipping) {
        this.freeShipping = freeShipping;
        return this;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public Product setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public Integer getPartnerLevelZone() {
        return partnerLevelZone;
    }

    public Product setPartnerLevelZone(Integer partnerLevelZone) {
        this.partnerLevelZone = partnerLevelZone;
        return this;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public Product setViewCount(Long viewCount) {
        this.viewCount = viewCount;
        return this;
    }

    public Integer getFareId() {
        return fareId;
    }

    public Product setFareId(Integer fareId) {
        this.fareId = fareId;
        return this;
    }

    public String getBarcode() {
        return barcode;
    }

    public Product setBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public Product setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
        return this;
    }

    public Integer getWeight() {
        return weight;
    }

    public Product setWeight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public Integer getBulk() {
        return bulk;
    }

    public Product setBulk(Integer bulk) {
        this.bulk = bulk;
        return this;
    }

    public String getSkuId() {
        return skuId;
    }

    public Product setSkuId(String skuId) {
        this.skuId = skuId;
        return this;
    }

    public String getSkuName() {
        return skuName;
    }

    public Product setSkuName(String skuName) {
        this.skuName = skuName;
        return this;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public Product setSkuCode(String skuCode) {
        this.skuCode = skuCode;
        return this;
    }

    public String getBarCode() {
        return barCode;
    }

    public Product setBarCode(String barCode) {
        this.barCode = barCode;
        return this;
    }

    public Integer getMid() {
        return mid;
    }

    public Product setMid(Integer mid) {
        this.mid = mid;
        return this;
    }

    public Integer getAllowCoupon() {
        return allowCoupon;
    }

    public Product setAllowCoupon(Integer allowCoupon) {
        this.allowCoupon = allowCoupon;
        return this;
    }

    public Integer getCredit() {
        return credit;
    }

    public Product setCredit(Integer credit) {
        this.credit = credit;
        return this;
    }

    public Integer getIsVirtual() {
        return isVirtual;
    }

    public Product setIsVirtual(Integer isVirtual) {
        this.isVirtual = isVirtual;
        return this;
    }

    public Integer getRequiredParticipateExam() {
        return requiredParticipateExam;
    }

    public Product setRequiredParticipateExam(Integer requiredParticipateExam) {
        this.requiredParticipateExam = requiredParticipateExam;
        return this;
    }

    public BigDecimal getDistributionPrice() {
        return distributionPrice;
    }

    public Product setDistributionPrice(BigDecimal distributionPrice) {
        this.distributionPrice = distributionPrice;
        return this;
    }

    public Integer getPresale() {
        return presale;
    }

    public Product setPresale(Integer presale) {
        this.presale = presale;
        return this;
    }

    public Long getOrgId() {
        return orgId;
    }

    public Product setOrgId(Long orgId) {
        this.orgId = orgId;
        return this;
    }

    public String getBanner() {
        return banner;
    }

    public Product setBanner(String banner) {
        this.banner = banner;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public Product setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public static final String ID = "id";

    public static final String CATEGORY_ID = "category_id";

    public static final String BRAND_ID = "brand_id";

    public static final String NAME = "name";

    public static final String SHORT_NAME = "short_name";

    public static final String COVER = "cover";

    public static final String STOCK_BALANCE = "stock_balance";

    public static final String SALES = "sales";

    public static final String STATUS = "status";

    public static final String CREATED_DATE = "created_date";

    public static final String LAST_MODIFIED_DATE = "last_modified_date";

    public static final String UNIT = "unit";

    public static final String PRICE = "price";

    public static final String COST_PRICE = "cost_price";

    public static final String SUGGESTED_PRICE = "suggested_price";

    public static final String PROMOTED = "promoted";

    public static final String FREIGHT = "freight";

    public static final String FREE_SHIPPING = "free_shipping";

    public static final String SORT_ORDER = "sort_order";

    public static final String PARTNER_LEVEL_ZONE = "partner_level_zone";

    public static final String VIEW_COUNT = "view_count";

    public static final String FARE_ID = "fare_id";

    public static final String BARCODE = "barcode";

    public static final String STORE_LOCATION = "store_location";

    public static final String WEIGHT = "weight";

    public static final String BULK = "bulk";

    public static final String SKU_ID = "sku_id";

    public static final String SKU_NAME = "sku_name";

    public static final String SKU_CODE = "sku_code";

    public static final String BAR_CODE = "bar_code";

    public static final String MID = "mid";

    public static final String ALLOW_COUPON = "allow_coupon";

    public static final String CREDIT = "credit";

    public static final String IS_VIRTUAL = "is_virtual";

    public static final String REQUIRED_PARTICIPATE_EXAM = "required_participate_exam";

    public static final String DISTRIBUTION_PRICE = "distribution_price";

    public static final String PRESALE = "presale";

    public static final String ORG_ID = "org_id";

    public static final String BANNER = "banner";

    public static final String REGION = "region";

    public static final String DESCRIPTION = "description";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", brandId=" + brandId +
                ", name=" + name +
                ", shortName=" + shortName +
                ", cover=" + cover +
                ", stockBalance=" + stockBalance +
                ", sales=" + sales +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", unit=" + unit +
                ", price=" + price +
                ", costPrice=" + costPrice +
                ", suggestedPrice=" + suggestedPrice +
                ", promoted=" + promoted +
                ", freight=" + freight +
                ", freeShipping=" + freeShipping +
                ", sortOrder=" + sortOrder +
                ", partnerLevelZone=" + partnerLevelZone +
                ", viewCount=" + viewCount +
                ", fareId=" + fareId +
                ", barcode=" + barcode +
                ", storeLocation=" + storeLocation +
                ", weight=" + weight +
                ", bulk=" + bulk +
                ", skuId=" + skuId +
                ", skuName=" + skuName +
                ", skuCode=" + skuCode +
                ", barCode=" + barCode +
                ", mid=" + mid +
                ", allowCoupon=" + allowCoupon +
                ", credit=" + credit +
                ", isVirtual=" + isVirtual +
                ", requiredParticipateExam=" + requiredParticipateExam +
                ", distributionPrice=" + distributionPrice +
                ", presale=" + presale +
                ", orgId=" + orgId +
                ", banner=" + banner +
                ", region=" + region +
                ", description=" + description +
                "}";
    }
}
