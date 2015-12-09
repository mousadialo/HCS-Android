package com.hcs.fastsales.dto.sales;

import com.hcs.fastsales.dto.product.DtoProductSale;
import com.hcs.fastsales.dto.user.DtoUser;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by fernando.ramirez on 25/06/2015.
 */
public class DtoSales implements Serializable {

    private static final long serialVersionUID = 1L;
    private int saleId;
    private DtoUser dtoUser;
    private int userSaleId;
    private double total;
    private DtoSaleStatus dtoSaleStatus;
    private String refComment;
    private List<DtoProductSale> listDtoProductSale;

    public DtoSales(int saleId, DtoUser dtoUser, int userSaleId, DtoSaleStatus dtoSaleStatus, double total, String refComment, List<DtoProductSale> listDtoProductSale) {
        this.saleId = saleId;
        this.dtoUser = dtoUser;
        this.userSaleId = userSaleId;
        this.dtoSaleStatus = dtoSaleStatus;
        this.total = total;
        this.refComment = refComment;
        this.listDtoProductSale = listDtoProductSale;
    }

    public DtoSales(DtoUser dtoUser, int userSaleId, DtoSaleStatus dtoSaleStatus, double total, String refComment, List<DtoProductSale> listDtoProductSale) {
        this.dtoUser = dtoUser;
        this.userSaleId = userSaleId;
        this.dtoSaleStatus = dtoSaleStatus;
        this.total = total;
        this.refComment = refComment;
        this.listDtoProductSale = listDtoProductSale;
    }

    public DtoSales() {
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public DtoUser getDtoUser() {
        return dtoUser;
    }

    public void setDtoUser(DtoUser dtoUser) {
        this.dtoUser = dtoUser;
    }

    public int getUserSaleId() {
        return userSaleId;
    }

    public void setUserSaleId(int userSaleId) {
        this.userSaleId = userSaleId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public DtoSaleStatus getDtoSaleStatus() {
        return dtoSaleStatus;
    }

    public void setDtoSaleStatus(DtoSaleStatus dtoSaleStatus) {
        this.dtoSaleStatus = dtoSaleStatus;
    }

    public String getRefComment() {
        return refComment;
    }

    public void setRefComment(String refComment) {
        this.refComment = refComment;
    }

    public List<DtoProductSale> getListDtoProductSale() {
        return listDtoProductSale;
    }

    public void setListDtoProductSale(List<DtoProductSale> listDtoProductSale) {
        this.listDtoProductSale = listDtoProductSale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DtoSales dtoSales = (DtoSales) o;

        return saleId == dtoSales.saleId;

    }

    @Override
    public int hashCode() {
        return saleId;
    }
}
