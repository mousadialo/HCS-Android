package com.hcs.fastsales.dao.sales;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.hcs.fastsales.dto.product.DtoProductSale;
import com.hcs.fastsales.dto.sales.DtoSales;
import com.hcs.fastsales.dto.user.DtoUser;
import com.hcs.fastsales.sqlite.DataSource;
import com.hcs.fastsales.sqlite.MySQLiteHelper;

/**
 * Created by fernando.ramirez on 26/06/2015.
 */
public class DaoSales extends DataSource {

    private static final String TAG = "DaoProduct";

    private String[] allColumnsSale = {
            MySQLiteHelper.TableSales.COLUMN_SALE_ID,
            MySQLiteHelper.TableSales.COLUMN_USER_ID,
            MySQLiteHelper.TableSales.COLUMN_USER_SALE_ID,
            MySQLiteHelper.TableSales.COLUMN_TOTAL,
            MySQLiteHelper.TableSales.COLUMN_SALE_STATUS,
            MySQLiteHelper.TableSales.COLUMN_REF_COMMENT
    };

    private String[] allColumnsSaleProducts = {
            MySQLiteHelper.TableRelSalesProduct.COLUMN_SALE_ID,
            MySQLiteHelper.TableRelSalesProduct.COLUMN_PROD_ID,
            MySQLiteHelper.TableRelSalesProduct.COLUMN_QUANTITY,
            MySQLiteHelper.TableRelSalesProduct.COLUMN_UNIT_PRICE,
            MySQLiteHelper.TableRelSalesProduct.COLUMN_TOTAL,
            MySQLiteHelper.TableRelSalesProduct.COLUMN_REF_COMMENT
    };

    protected DaoSales(Context context) {
        super(context);
    }

    /**
     * Method to add a new Sale
     * @param dtoSales
     */
    private void addSale(DtoSales dtoSales) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.TableSales.COLUMN_USER_ID, dtoSales.getDtoUser().getUserId());
        values.put(MySQLiteHelper.TableSales.COLUMN_USER_SALE_ID, dtoSales.getUserSaleId());
        values.put(MySQLiteHelper.TableSales.COLUMN_TOTAL, dtoSales.getTotal());
        values.put(MySQLiteHelper.TableSales.COLUMN_SALE_STATUS, dtoSales.getDtoSaleStatus().getSaleStatus());
        values.put(MySQLiteHelper.TableSales.COLUMN_REF_COMMENT, dtoSales.getRefComment());
        super.getSqLiteDatabase().insert(MySQLiteHelper.TableSales.TABLE_NAME, null, values);
    }

    /**
     * Method to add Products in a Sale
     * @param dtoSales
     */
    private void addSaleProduct(DtoSales dtoSales) {
        for (DtoProductSale dtoProductSale : dtoSales.getListDtoProductSale()) {
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.TableRelSalesProduct.COLUMN_SALE_ID, dtoSales.getSaleId());
            values.put(MySQLiteHelper.TableRelSalesProduct.COLUMN_PROD_ID, dtoProductSale.getProductId());
            values.put(MySQLiteHelper.TableRelSalesProduct.COLUMN_QUANTITY, dtoProductSale.getQuantity());
            values.put(MySQLiteHelper.TableRelSalesProduct.COLUMN_UNIT_PRICE, dtoProductSale.getPrice());
            values.put(MySQLiteHelper.TableRelSalesProduct.COLUMN_TOTAL, (dtoProductSale.getQuantity() * dtoProductSale.getPrice()));
            values.put(MySQLiteHelper.TableRelSalesProduct.COLUMN_REF_COMMENT, dtoProductSale.getRefComment());
            super.getSqLiteDatabase().insert(MySQLiteHelper.TableRelSalesProduct.TABLE_NAME, null, values);
        }
    }


    private int getConsecutiveUserSaleId(DtoUser dtoUser) {
        int userSaleId = 0;
        try {
            String selectQuery = "SELECT IF(user_sale_id is null, 1, max(user_sale_id) + 1) FROM " + MySQLiteHelper.TableSales.TABLE_NAME + " WHERE " + MySQLiteHelper.TableSales.COLUMN_USER_ID + " = ?";
            Cursor cursor = super.getSqLiteDatabase().rawQuery(selectQuery, new String[]{dtoUser.getUserId()+""});
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                userSaleId = cursor.getInt(0);
            }
            cursor.close();
        }  catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return userSaleId;
    }

    /**
     * Method to register a sale
    */
    public DtoSales registerSale(DtoSales dtoSales) {
        try {
            super.open();
            super.getSqLiteDatabase().beginTransaction();
            dtoSales.setUserSaleId(this.getConsecutiveUserSaleId(dtoSales.getDtoUser()));
            this.addSale(dtoSales);
            this.addSaleProduct(dtoSales);
            super.getSqLiteDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            dtoSales.setUserSaleId(0);
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            super.getSqLiteDatabase().endTransaction();
            super.close();
        }
        return dtoSales;
    }
}