package com.hcs.fastsales.dto.sales;

import java.io.Serializable;

/**
 * Created by fernando.ramirez on 25/06/2015.
 */
public class DtoSaleStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    private String saleStatus;
    private String saleStatusName;
    private String description;

    public DtoSaleStatus(String saleStatus, String saleStatusName, String description) {
        this.saleStatus = saleStatus;
        this.saleStatusName = saleStatusName;
        this.description = description;
    }

    public DtoSaleStatus() {
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getSaleStatusName() {
        return saleStatusName;
    }

    public void setSaleStatusName(String saleStatusName) {
        this.saleStatusName = saleStatusName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DtoSaleStatus{" +
                "saleStatus='" + saleStatus + '\'' +
                ", saleStatusName='" + saleStatusName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DtoSaleStatus that = (DtoSaleStatus) o;

        return !(saleStatus != null ? !saleStatus.equalsIgnoreCase(that.saleStatus) : that.saleStatus != null);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((saleStatus == null) ? 0 : saleStatus.hashCode());
        return result;
    }
}
