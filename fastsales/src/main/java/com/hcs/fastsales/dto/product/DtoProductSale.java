package com.hcs.fastsales.dto.product;

import java.io.Serializable;

/**
 * Created by fernando.ramirez on 25/06/2015.
 */
public class DtoProductSale extends DtoProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    private int quantity;
    private double unitPrice;
    private double total;
    private String refComment;

    public DtoProductSale(int productId, String productCode, String productName, String description, double price, boolean enabled, DtoProductType dtoProductType, int quantity, double unitPrice, double total, String refComment) {
        super(productId, productCode, productName, description, price, enabled, dtoProductType);
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = total;
        this.refComment = refComment;
    }

    public DtoProductSale() {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getRefComment() {
        return refComment;
    }

    public void setRefComment(String refComment) {
        this.refComment = refComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DtoProductSale that = (DtoProductSale) o;

        if(super.getProductId() != that.getProductId()) return false;
        if (quantity != that.quantity) return false;
        if (Double.compare(that.unitPrice, unitPrice) != 0) return false;
        if (Double.compare(that.total, total) != 0) return false;
        return !(refComment != null ? !refComment.equals(that.refComment) : that.refComment != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + quantity;
        temp = Double.doubleToLongBits(unitPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(total);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (refComment != null ? refComment.hashCode() : 0);
        return result;
    }
}
