package com.hcs.fastsales.dao.product;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.hcs.fastsales.dto.product.DtoProduct;
import com.hcs.fastsales.dto.product.DtoProductType;
import com.hcs.fastsales.sqlite.DataSource;
import com.hcs.fastsales.sqlite.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fernando.ramirez on 26/06/2015.
 */
public class DaoProduct extends DataSource {

    private static final String TAG = "DaoProduct";

    private String[] allColumns = {
            MySQLiteHelper.TableProduct.COLUMN_PROD_ID,
            MySQLiteHelper.TableProduct.COLUMN_PROD_CODE,
            MySQLiteHelper.TableProduct.COLUMN_PROD_NAME,
            MySQLiteHelper.TableProduct.COLUMN_DESCRIPTION,
            MySQLiteHelper.TableProduct.COLUMN_PRICE,
            MySQLiteHelper.TableProduct.COLUMN_ENABLED,
            MySQLiteHelper.TableProduct.COLUMN_PROD_TYPE
    };

    public DaoProduct(Context context) {
        super(context);
    }

    /**
     * Method to add a new Product
     * @param dtoProduct
     */
    private void addProduct(DtoProduct dtoProduct) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.TableProduct.COLUMN_PROD_CODE, dtoProduct.getProductCode());
        values.put(MySQLiteHelper.TableProduct.COLUMN_PROD_NAME, dtoProduct.getProductName());
        values.put(MySQLiteHelper.TableProduct.COLUMN_DESCRIPTION, dtoProduct.getDescription());
        values.put(MySQLiteHelper.TableProduct.COLUMN_PRICE, dtoProduct.getPrice());
        values.put(MySQLiteHelper.TableProduct.COLUMN_ENABLED, dtoProduct.isEnabled());
        values.put(MySQLiteHelper.TableProduct.COLUMN_PROD_TYPE, dtoProduct.getDtoProductType().getProductType());
        super.getSqLiteDatabase().insert(MySQLiteHelper.TableProduct.TABLE_NAME, null, values);
    }

    /**
     * Method to create a product
     */
    public DtoProduct createProduct(String productCode, String productName, String description, double price, boolean enabled, DtoProductType dtoProductType) {
        DtoProduct dtoProduct = null;
        try {
            super.open();
            dtoProduct.setProductCode(productCode);
            dtoProduct.setProductName(productName);
            dtoProduct.setDescription(description);
            dtoProduct.setPrice(price);
            dtoProduct.setEnabled(enabled);
            dtoProduct.setDtoProductType(dtoProductType);
            this.addProduct(dtoProduct);
        } catch (Exception e) {
            dtoProduct = null;
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            super.close();
        }
        return dtoProduct;
    }

    /**
     * Method to update a Product
     * @param dtoProduct
     */
    private void updateProduct(DtoProduct dtoProduct) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.TableProduct.COLUMN_PROD_CODE, dtoProduct.getProductCode());
        values.put(MySQLiteHelper.TableProduct.COLUMN_PROD_NAME, dtoProduct.getProductName());
        values.put(MySQLiteHelper.TableProduct.COLUMN_DESCRIPTION, dtoProduct.getDescription());
        values.put(MySQLiteHelper.TableProduct.COLUMN_PRICE, dtoProduct.getPrice());
        values.put(MySQLiteHelper.TableProduct.COLUMN_ENABLED, dtoProduct.isEnabled());
        values.put(MySQLiteHelper.TableProduct.COLUMN_PROD_TYPE, dtoProduct.getDtoProductType().getProductType());

        super.getSqLiteDatabase().update(MySQLiteHelper.TableProduct.TABLE_NAME, values,
                MySQLiteHelper.TableProduct.COLUMN_PROD_ID + " = ?",
                new String[]{dtoProduct.getProductId() + ""});
    }

    /**
     * Method to update a Product
     * @param dtoProduct
     * @param productCode
     * @param productName
     * @param description
     * @param price
     * @param enabled
     * @param dtoProductType
     */
    public DtoProduct updateProduct(DtoProduct dtoProduct, String productCode, String productName, String description, double price, boolean enabled, DtoProductType dtoProductType) {
        try {
            super.open();
            dtoProduct.setProductCode(productCode);
            dtoProduct.setProductName(productName);
            dtoProduct.setDescription(description);
            dtoProduct.setPrice(price);
            dtoProduct.setEnabled(enabled);
            dtoProduct.setDtoProductType(dtoProductType);
            this.updateProduct(dtoProduct);
        } catch (Exception e) {
            dtoProduct = null;
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            super.close();
        }
        return dtoProduct;
    }

    /**
     * Method to delete a Product
     * @param dtoProduct
     */
    private void deleteProduct(DtoProduct dtoProduct) {
        int productId = dtoProduct.getProductId();
        super.getSqLiteDatabase().delete(MySQLiteHelper.TableProduct.TABLE_NAME,
                MySQLiteHelper.TableProduct.COLUMN_PROD_ID + " = " + productId, null);
    }

    /**
     * Method to get all products
     * @return List<DtoProduct>
     */
    private List<DtoProduct> getAllProducts() {
        List<DtoProduct> listdtoProduct = new ArrayList<>();

        Cursor cursor = super.getSqLiteDatabase().query(MySQLiteHelper.TableProduct.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DtoProduct dtoProduct = cursorToDtoProduct(cursor);
            listdtoProduct.add(dtoProduct);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listdtoProduct;
    }

    /**
     * Method to covert from Cursor to DtoProduct
     * @param cursor
     * @return dtoProduct
     */
    private DtoProduct cursorToDtoProduct(Cursor cursor) {
        return new DtoProduct(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getDouble(4),
                cursor.getInt(5) == 1 ? true : false,
                new DtoProductType(cursor.getString(2), cursor.getString(2), cursor.getString(2)));
    }

    /**
     * Method to create default product
     */
    public void createProducts() {
        try {
            super.open();
            super.getSqLiteDatabase().beginTransaction();
            List<DtoProduct> listDtoProduct = this.getAllProducts();
            if (listDtoProduct.isEmpty()) {
                this.addProduct(new DtoProduct("TACO", "Taco normal", "Taco normal con cualquier guisado", 6, true, new DtoProductType("TACO", "TACO", "TACO")));
                this.addProduct(new DtoProduct("TACO_HC", "Taco huevo cosido", "Taco de huevo cosido con cualquier guisado", 12, true, new DtoProductType("TACO", "TACO", "TACO")));

                this.addProduct(new DtoProduct("TORTA_CH_IB", "Torta ch. con bolillo", "Torta chica de guisado que incluye bolillo", 9.5, true, new DtoProductType("TORTA", "TORTA", "TORTA")));
                this.addProduct(new DtoProduct("TORTA_CH_NIB", "Torta ch. sin bolillo", "Torta chica de guisado que no incluye bolillo", 7, true, new DtoProductType("TORTA", "TORTA", "TORTA")));
                this.addProduct(new DtoProduct("TORTA_GDE_IB", "Torta gde. con bolillo", "Torta grande de guisado que incluye bolillo", 19, true, new DtoProductType("TORTA", "TORTA", "TORTA")));
                this.addProduct(new DtoProduct("TORTA_GDE_NIB", "Torta gde. sin bolillo", "Torta grande de guisado que no incluye bolillo", 14, true, new DtoProductType("TORTA", "TORTA", "TORTA")));

                this.addProduct(new DtoProduct("PLATO_CH_10", "Plato chico 10", "Plato chico de $10 de guisados basicos", 10, true, new DtoProductType("PLATO", "PLATO", "PLATO")));
                this.addProduct(new DtoProduct("PLATO_CH_20", "Plato chico 20", "Plato chico de $20 de cualquier guisado", 20, true, new DtoProductType("PLATO", "PLATO", "PLATO")));
                this.addProduct(new DtoProduct("PLATO_CH_25", "Plato chico 25", "Plato chico de $25 de cualquier guisado", 25, true, new DtoProductType("PLATO", "PLATO", "PLATO")));
                this.addProduct(new DtoProduct("PLATO_GDE_30", "Plato grande 30", "Plato grande de 30 de cualquier guisado", 30, true, new DtoProductType("PLATO", "PLATO", "PLATO")));
                this.addProduct(new DtoProduct("PLATO_GDE_40", "Plato grande 40", "Plato grande de 40 de cualquier guisado", 40, true, new DtoProductType("PLATO", "PLATO", "PLATO")));

                this.addProduct(new DtoProduct("TORTI_1CUARTO", "Tortillas 1/4 Kg", "Tortillas un cuarto, 1/4 Kg", 5, true, new DtoProductType("TORTILLA", "TORTILLA", "TORTILLA")));
                this.addProduct(new DtoProduct("TORTI_1MEDIO", "Tortillas 1/2 Kg", "Tortillas un medio, 1/2 Kg", 9, true, new DtoProductType("TORTILLA", "TORTILLA", "TORTILLA")));
                this.addProduct(new DtoProduct("TORTI_3CUARTO", "Tortillas 3/4 Kg", "Tortillas tres cuarto, 3/4 Kg", 13, true, new DtoProductType("TORTILLA", "TORTILLA", "TORTILLA")));
                this.addProduct(new DtoProduct("TORTI_1KILO", "Tortillas 1 Kg", "Tortillas un kilogramo, 1 Kg", 15, true, new DtoProductType("TORTILLA", "TORTILLA", "TORTILLA")));

                this.addProduct(new DtoProduct("REFRE_VIDRIO", "Refresco de vidrio", "Cualquier refresco de vidrio", 10, true, new DtoProductType("BEBIDA", "BEBIDA", "BEBIDA")));
                this.addProduct(new DtoProduct("REFRE_DES_400", "Refresco desechable de 400ml", "Cualquier refresco desechable de 400ml", 10, true, new DtoProductType("BEBIDA", "BEBIDA", "BEBIDA")));
                this.addProduct(new DtoProduct("REFRE_DES_500", "Refresco desechable de 500ml", "Cualquier refresco desechable de 500ml", 11, true, new DtoProductType("BEBIDA", "BEBIDA", "BEBIDA")));
                this.addProduct(new DtoProduct("REFRE_DES_600", "Refresco desechable de 600ml", "Cualquier refresco desechable de 600ml", 12, true, new DtoProductType("BEBIDA", "BEBIDA", "BEBIDA")));

                this.addProduct(new DtoProduct("HUEVO_COSIDO", "Huevo cosido", "Huevo cosido", 6, true, new DtoProductType("OTRO", "OTRO", "OTRO")));
                this.addProduct(new DtoProduct("BOLILLO_SOLO", "Bolillo solo", "Bolillo solo sin comida", 4, true, new DtoProductType("OTRO", "OTRO", "OTRO")));
                this.addProduct(new DtoProduct("OTRO", "Otro productos", "Cualquier otro producto no registrado", 0, true, new DtoProductType("OTRO", "OTRO", "OTRO")));
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

    public List<DtoProduct> getAllListProduct() {
        List<DtoProduct> listProduct = null;
        try {
            super.open();
            listProduct = this.getAllProducts();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            super.close();
        }
        return listProduct;
    }
}