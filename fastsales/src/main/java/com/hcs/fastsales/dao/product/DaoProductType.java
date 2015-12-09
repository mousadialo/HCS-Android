package com.hcs.fastsales.dao.product;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.hcs.fastsales.dto.product.DtoProductType;
import com.hcs.fastsales.sqlite.DataSource;
import com.hcs.fastsales.sqlite.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fernando.ramirez on 25/06/2015.
 */
public class DaoProductType extends DataSource {

    private static final String TAG = "DaoProductType";

    private String[] allColumns = {
            MySQLiteHelper.TableProductType.COLUMN_PRODUCT_TYPE,
            MySQLiteHelper.TableProductType.COLUMN_PROD_TYPE_NAME,
            MySQLiteHelper.TableProductType.COLUMN_DESCRIPTION };

    public DaoProductType(Context context) {
        super(context);
    }

    /**
     * Method to add a new Product Type
     * @param dtoProductType
     */
    private void addProductType(DtoProductType dtoProductType) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.TableProductType.COLUMN_PRODUCT_TYPE, dtoProductType.getProductType());
        values.put(MySQLiteHelper.TableProductType.COLUMN_PROD_TYPE_NAME, dtoProductType.getProdTypeName());
        values.put(MySQLiteHelper.TableProductType.COLUMN_DESCRIPTION, dtoProductType.getDescription());
        super.getSqLiteDatabase().insert(MySQLiteHelper.TableProductType.TABLE_NAME, null, values);
    }

    /**
     * Method to create a product type
     */
    public DtoProductType createProductType(String productType, String prodTypeName, String description) {
        DtoProductType dtoProductType = null;
        try {
            super.open();
            dtoProductType.setProductType(productType);
            dtoProductType.setProdTypeName(prodTypeName);
            dtoProductType.setDescription(description);
            this.addProductType(dtoProductType);
        } catch (Exception e) {
            dtoProductType = null;
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            super.close();
        }
        return dtoProductType;
    }

    /**
     * Method to update a Product Type
     * @param dtoProductType
     */
    private void updateProductType(DtoProductType dtoProductType) {
        boolean created = false;
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.TableProductType.COLUMN_PRODUCT_TYPE, dtoProductType.getProductType());
        values.put(MySQLiteHelper.TableProductType.COLUMN_PROD_TYPE_NAME, dtoProductType.getProdTypeName());
        values.put(MySQLiteHelper.TableProductType.COLUMN_DESCRIPTION, dtoProductType.getDescription());

        super.getSqLiteDatabase().update(MySQLiteHelper.TableProductType.TABLE_NAME, values,
                MySQLiteHelper.TableProductType.COLUMN_PRODUCT_TYPE + " = ?",
                new String[]{dtoProductType.getProductType()});
    }

    /**
     * Method to update a Product Type
     * @param productType
     * @param prodTypeName
     * @param description
     */
    public DtoProductType updateProductType(DtoProductType dtoProductType, String productType, String prodTypeName, String description) {
        try {
            super.open();
            dtoProductType.setProductType(productType);
            dtoProductType.setProdTypeName(prodTypeName);
            dtoProductType.setDescription(description);
            this.updateProductType(dtoProductType);
        } catch (Exception e) {
            dtoProductType = null;
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            super.close();
        }
        return dtoProductType;
    }

    /**
     * Method to delete a Product Type
     * @param dtoProductType
     */
    private void deleteProductType(DtoProductType dtoProductType) {
        String productType = dtoProductType.getProductType();
        super.getSqLiteDatabase().delete(MySQLiteHelper.TableProductType.TABLE_NAME,
                MySQLiteHelper.TableProductType.COLUMN_PRODUCT_TYPE + " = " + productType, null);
    }

    /**
     * Method to get all product types
     * @return List<DtoProductType>
     */
    private List<DtoProductType> getAllProductTypes() {
        List<DtoProductType> listDtoProductType = new ArrayList<>();

        Cursor cursor = super.getSqLiteDatabase().query(MySQLiteHelper.TableProductType.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DtoProductType dtoProductType = cursorToDtoProductType(cursor);
            listDtoProductType.add(dtoProductType);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listDtoProductType;
    }

    /**
     * Method to covert from Cursor to DtoProductType
     * @param cursor
     * @return DtoProductType
     */
    private DtoProductType cursorToDtoProductType(Cursor cursor) {
        return new DtoProductType(cursor.getString(0), cursor.getString(1), cursor.getString(2));
    }

    /**
     * Method to create default product types
     */
    public void createProductTypes() {
        try {
            super.open();
            super.getSqLiteDatabase().beginTransaction();
            List<DtoProductType> listDtoProductType = this.getAllProductTypes();
            if (listDtoProductType.isEmpty()) {
                this.addProductType(new DtoProductType("BEBIDA", "Bebidas", "Todo tipo de bebidas"));
                this.addProductType(new DtoProductType("TACO", "Tacos", "Todo tipo de tacos"));
                this.addProductType(new DtoProductType("TORTA", "Tortas", "Todo tipo de tortas"));
                this.addProductType(new DtoProductType("PLATO", "Platos", "Todo tipo de platos"));
                this.addProductType(new DtoProductType("TORTILLA", "Tortillas", "Tortillas por separado"));
                this.addProductType(new DtoProductType("OTRO", "Otros Productos", "Otros productos no registrados"));
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

    public List<DtoProductType> getAllListProductType() {
        List<DtoProductType> listProductType = null;
        try {
            super.open();
            listProductType = this.getAllProductTypes();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            super.close();
        }
        return listProductType;
    }
}