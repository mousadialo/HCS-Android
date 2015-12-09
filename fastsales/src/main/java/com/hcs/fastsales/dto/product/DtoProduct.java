package com.hcs.fastsales.dto.product;

import java.io.Serializable;

/**
 * Created by fernando.ramirez on 25/06/2015.
 */
public class DtoProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    private int productId;
    private String productCode;
    private String productName;
    private String description;
    private double price;
    private boolean enabled;
    private DtoProductType dtoProductType;

    public DtoProduct(int productId, String productCode, String productName, String description, double price, boolean enabled, DtoProductType dtoProductType) {
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.enabled = enabled;
        this.dtoProductType = dtoProductType;
    }

    public DtoProduct(String productCode, String productName, String description, double price, boolean enabled, DtoProductType dtoProductType) {
        this.productCode = productCode;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.enabled = enabled;
        this.dtoProductType = dtoProductType;
    }

    public DtoProduct() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public DtoProductType getDtoProductType() {
        return dtoProductType;
    }

    public void setDtoProductType(DtoProductType dtoProductType) {
        this.dtoProductType = dtoProductType;
    }

    @Override
    public String toString() {
        return "DtoProduct{" +
                "productId=" + productId +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", enabled=" + enabled +
                ", dtoProductType=" + dtoProductType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DtoProduct dtoProduct = (DtoProduct) o;

        return productId == dtoProduct.productId;
    }

    @Override
    public int hashCode() {
        return productId;
    }
}
