package com.hcs.fastsales.dao.sales;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.hcs.fastsales.dto.sales.DtoSaleStatus;
import com.hcs.fastsales.sqlite.DataSource;
import com.hcs.fastsales.sqlite.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fernando.ramirez on 25/06/2015.
 */
public class DaoSalesStatus extends DataSource {

    private static final String TAG = "DaoSalesStatus";

    private String[] allColumns = {
            MySQLiteHelper.TableSaleStatus.COLUMN_SALE_STATUS,
            MySQLiteHelper.TableSaleStatus.COLUMN_SALE_STATUS_NAME,
            MySQLiteHelper.TableSaleStatus.COLUMN_DESCRIPTION };

    protected DaoSalesStatus(Context context) {
        super(context);
    }

    /**
     * Method to add a new Sale Status
     * @param dtoSaleStatus
     */
    private void addSaleStatus(DtoSaleStatus dtoSaleStatus) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.TableSaleStatus.COLUMN_SALE_STATUS, dtoSaleStatus.getSaleStatus());
        values.put(MySQLiteHelper.TableSaleStatus.COLUMN_SALE_STATUS_NAME, dtoSaleStatus.getSaleStatusName());
        values.put(MySQLiteHelper.TableSaleStatus.COLUMN_DESCRIPTION, dtoSaleStatus.getDescription());
        super.getSqLiteDatabase().insert(MySQLiteHelper.TableSaleStatus.TABLE_NAME, null, values);
    }

    /**
     * Method to create a Sale Status
     */
    public DtoSaleStatus createSaleStatus(String saleStatus, String saleStatusName, String description) {
        DtoSaleStatus dtoSaleStatus = null;
        try {
            super.open();
            dtoSaleStatus.setSaleStatus(saleStatus);
            dtoSaleStatus.setSaleStatusName(saleStatusName);
            dtoSaleStatus.setDescription(description);
            this.addSaleStatus(dtoSaleStatus);
        } catch (Exception e) {
            dtoSaleStatus = null;
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            super.close();
        }
        return dtoSaleStatus;
    }

    /**
     * Method to update a Sale Status
     * @param dtoSaleStatus
     */
    private void updateSaleStatus(DtoSaleStatus dtoSaleStatus) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.TableSaleStatus.COLUMN_SALE_STATUS, dtoSaleStatus.getSaleStatus());
        values.put(MySQLiteHelper.TableSaleStatus.COLUMN_SALE_STATUS_NAME, dtoSaleStatus.getSaleStatusName());
        values.put(MySQLiteHelper.TableSaleStatus.COLUMN_DESCRIPTION, dtoSaleStatus.getDescription());

        super.getSqLiteDatabase().update(MySQLiteHelper.TableSaleStatus.TABLE_NAME, values,
                MySQLiteHelper.TableSaleStatus.COLUMN_SALE_STATUS + " = ?",
                new String[]{dtoSaleStatus.getSaleStatus()});
    }

    /**
     * Method to update a Sale Status
     * @param saleStatus
     * @param saleStatusName
     * @param description
     */
    public DtoSaleStatus updateSaleStatus(DtoSaleStatus dtoSaleStatus, String saleStatus, String saleStatusName, String description) {
        try {
            super.open();
            dtoSaleStatus.setSaleStatus(saleStatus);
            dtoSaleStatus.setSaleStatusName(saleStatusName);
            dtoSaleStatus.setDescription(description);
            this.updateSaleStatus(dtoSaleStatus);
        } catch (Exception e) {
            dtoSaleStatus = null;
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            super.close();
        }
        return dtoSaleStatus;
    }

    /**
     * Method to delete a Sale Status
     * @param dtoSaleStatus
     */
    private void deleteSaleStatus(DtoSaleStatus dtoSaleStatus) {
        String SaleStatus = dtoSaleStatus.getSaleStatus();
        super.getSqLiteDatabase().delete(MySQLiteHelper.TableSaleStatus.TABLE_NAME,
                MySQLiteHelper.TableSaleStatus.COLUMN_SALE_STATUS + " = " + SaleStatus, null);
    }

    /**
     * Method to get all Sale Status
     * @return List<DtoSaleStatus>
     */
    private List<DtoSaleStatus> getAllSaleStatus() {
        List<DtoSaleStatus> listDtoSaleStatus = new ArrayList<>();

        Cursor cursor = super.getSqLiteDatabase().query(MySQLiteHelper.TableSaleStatus.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DtoSaleStatus dtoSaleStatus = cursorToDtoSaleStatus(cursor);
            listDtoSaleStatus.add(dtoSaleStatus);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listDtoSaleStatus;
    }

    /**
     * Method to covert from Cursor to DtoSaleStatus
     * @param cursor
     * @return dtoSaleStatus
     */
    private DtoSaleStatus cursorToDtoSaleStatus(Cursor cursor) {
        return new DtoSaleStatus(cursor.getString(0), cursor.getString(1), cursor.getString(2));
    }

    /**
     * Method to create default Sale Status
     */
    public void createSaleStatus() {
        try {
            super.open();
            super.getSqLiteDatabase().beginTransaction();
            List<DtoSaleStatus> listdtoSaleStatus = this.getAllSaleStatus();
            if (listdtoSaleStatus.isEmpty()) {
                this.addSaleStatus(new DtoSaleStatus("CANCELADA", "Cancelada", "Ventas canceladas"));
                this.addSaleStatus(new DtoSaleStatus("LIBERADA", "Liberada", "Ventas capturadas ya liberadas"));
                super.getSqLiteDatabase().setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            super.getSqLiteDatabase().endTransaction();
            super.close();
        }
    }

    public List<DtoSaleStatus> getAllListSaleStatus() {
        List<DtoSaleStatus> listSaleStatus = null;
        try {
            super.open();
            listSaleStatus = this.getAllSaleStatus();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            super.close();
        }
        return listSaleStatus;
    }
}