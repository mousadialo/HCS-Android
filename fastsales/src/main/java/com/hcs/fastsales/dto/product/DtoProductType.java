package com.hcs.fastsales.dto.product;

import java.io.Serializable;

/**
 * Created by fernando.ramirez on 25/06/2015.
 */
public class DtoProductType implements Serializable {

    private static final long serialVersionUID = 1L;
    private String productType;
    private String prodTypeName;
    private String description;

    public DtoProductType(String productType, String prodTypeName, String description) {
        this.productType = productType;
        this.prodTypeName = prodTypeName;
        this.description = description;
    }

    public DtoProductType() {
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProdTypeName() {
        return prodTypeName;
    }

    public void setProdTypeName(String prodTypeName) {
        this.prodTypeName = prodTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DtoProductType{" +
                "productType='" + productType + '\'' +
                ", prodTypeName='" + prodTypeName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DtoProductType that = (DtoProductType) o;

        return !(productType != null ? !productType.equalsIgnoreCase(that.productType) : that.productType != null);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((productType == null) ? 0 : productType.hashCode());
        return result;
    }
}
