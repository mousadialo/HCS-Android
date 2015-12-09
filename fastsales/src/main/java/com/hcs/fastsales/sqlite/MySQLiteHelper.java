package com.hcs.fastsales.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fernando.ramirez on 22/06/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "FastSalesDB.db";
    private static final int DATABASE_VERSION = 2;

    public class TableUserType {
        public static final String TABLE_NAME = "tc_user_type";
        public static final String COLUMN_USER_TYPE = "user_type";
        public static final String COLUMN_TYPE_NAME = "type_name";
        public static final String COLUMN_DESCRIPTION = "description";

        // Database creation sql statement
        private static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
                + COLUMN_USER_TYPE + " text not null primary key, "
                + COLUMN_TYPE_NAME + " text not null, "
                + COLUMN_DESCRIPTION  + " text not null);";
    }

    public class TableUserStatus {
        public static final String TABLE_NAME = "tc_user_status";
        public static final String COLUMN_USER_STATUS = "user_status";
        public static final String COLUMN_STATUS_NAME = "status_name";
        public static final String COLUMN_DESCRIPTION = "description";

        // Database creation sql statement
        private static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
                + COLUMN_USER_STATUS + " text not null primary key, "
                + COLUMN_STATUS_NAME + " text not null, "
                + COLUMN_DESCRIPTION + " text not null);";
    }

    public class TableUser {
        public static final String TABLE_NAME = "tr_user";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_USERNAME = "user_name";
        public static final String COLUMN_USERPASS = "user_pass";
        public static final String COLUMN_FIRSTNAME = "first_name";
        public static final String COLUMN_LASTNAME = "last_name";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_EMAIL_ADDRESS = "email_address";
        public static final String COLUMN_USER_TYPE = "user_type";
        public static final String COLUMN_USER_STATUS = "user_status";

        // Database creation sql statement
        private static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
                + COLUMN_USER_ID	+ " INTEGER not null primary key AUTOINCREMENT, "
                + COLUMN_USERNAME	+ " text not null, "
                + COLUMN_USERPASS	+ " text not null, "
                + COLUMN_FIRSTNAME	+ " text not null, "
                + COLUMN_LASTNAME	+ " text not null, "
                + COLUMN_ADDRESS	+ " text not null, "
                + COLUMN_EMAIL_ADDRESS + " text not null, "
                + COLUMN_USER_STATUS	+ " text not null, "
                + COLUMN_USER_TYPE	+ " text not null, "
                + "FOREIGN KEY("+COLUMN_USER_STATUS+") REFERENCES "+TableUserStatus.TABLE_NAME+"("+COLUMN_USER_STATUS+"), "
                + "FOREIGN KEY("+COLUMN_USER_TYPE+") REFERENCES "+TableUserType.TABLE_NAME+"("+COLUMN_USER_TYPE+") "
                + ");";
    }

    public class TableProductType {
        public static final String TABLE_NAME = "tc_product_type";
        public static final String COLUMN_PRODUCT_TYPE = "product_type";
        public static final String COLUMN_PROD_TYPE_NAME = "prod_type_name";
        public static final String COLUMN_DESCRIPTION = "description";

        // Database creation sql statement
        private static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
                + COLUMN_PRODUCT_TYPE + " text not null primary key, "
                + COLUMN_PROD_TYPE_NAME + " text not null, "
                + COLUMN_DESCRIPTION + " text not null);";
    }

    public class TableProduct {
        public static final String TABLE_NAME = "tr_product";
        public static final String COLUMN_PROD_ID = "product_id";
        public static final String COLUMN_PROD_CODE = "product_code";
        public static final String COLUMN_PROD_NAME = "product_name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_ENABLED = "enabled";
        public static final String COLUMN_PROD_TYPE = "product_type";

        // Database creation sql statement
        private static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
                + COLUMN_PROD_ID + " INTEGER not null primary key AUTOINCREMENT, "
                + COLUMN_PROD_CODE + " text not null, "
                + COLUMN_PROD_NAME + " text not null, "
                + COLUMN_DESCRIPTION + " text not null,"
                + COLUMN_PRICE + " double not null,"
                + COLUMN_ENABLED + " int not null,"
                + COLUMN_PROD_TYPE + " int not null,"
                + "FOREIGN KEY("+COLUMN_PROD_TYPE+") REFERENCES "+TableProductType.TABLE_NAME+"("+COLUMN_PROD_TYPE+") "
                + ");";
    }

    public class TableSaleStatus {
        public static final String TABLE_NAME = "tc_sale_status";
        public static final String COLUMN_SALE_STATUS = "sale_status";
        public static final String COLUMN_SALE_STATUS_NAME = "sale_status_name";
        public static final String COLUMN_DESCRIPTION = "description";

        // Database creation sql statement
        private static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
                + COLUMN_SALE_STATUS + " text not null primary key, "
                + COLUMN_SALE_STATUS_NAME + " text not null, "
                + COLUMN_DESCRIPTION + " text not null);";
    }

    public class TableSales {
        public static final String TABLE_NAME = "tr_sales";
        public static final String COLUMN_SALE_ID = "sale_id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_USER_SALE_ID = "user_sale_id";
        public static final String COLUMN_TOTAL = "total";
        public static final String COLUMN_SALE_STATUS = "sale_status";
        public static final String COLUMN_REF_COMMENT = "ref_comment";

        // Database creation sql statement
        private static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
                + COLUMN_SALE_ID + " INTEGER not null primary key AUTOINCREMENT, "
                + COLUMN_USER_ID + " INTEGER not null, "
                + COLUMN_USER_SALE_ID + " INTEGER not null, "
                + COLUMN_TOTAL + " double not null, "
                + COLUMN_SALE_STATUS + " text not null, "
                + COLUMN_REF_COMMENT + " text not null, "
                + "FOREIGN KEY("+COLUMN_USER_ID+") REFERENCES "+TableUser.TABLE_NAME+"("+COLUMN_USER_ID+"), "
                + "FOREIGN KEY("+COLUMN_SALE_STATUS+") REFERENCES "+TableSaleStatus.TABLE_NAME+"("+COLUMN_SALE_STATUS+") "
                + ");";
    }

    public class TableRelSalesProduct {
        public static final String TABLE_NAME = "trel_sales_products";
        public static final String COLUMN_SALE_ID = "sale_id";
        public static final String COLUMN_PROD_ID = "product_id";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_UNIT_PRICE = "unit_price";
        public static final String COLUMN_TOTAL = "total";
        public static final String COLUMN_REF_COMMENT = "ref_comment";

        // Database creation sql statement
        private static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
                + COLUMN_SALE_ID + " INTEGER not null, "
                + COLUMN_PROD_ID + " INTEGER not null, "
                + COLUMN_QUANTITY + " INTEGER not null, "
                + COLUMN_UNIT_PRICE + " double not null, "
                + COLUMN_TOTAL + " double not null, "
                + COLUMN_REF_COMMENT + " text not null, "
                + "FOREIGN KEY("+COLUMN_SALE_ID+") REFERENCES "+TableSales.TABLE_NAME+"("+COLUMN_SALE_ID+"), "
                + "FOREIGN KEY("+COLUMN_PROD_ID+") REFERENCES "+TableProduct.TABLE_NAME+"("+COLUMN_PROD_ID+"), "
                + "PRIMARY KEY("+COLUMN_SALE_ID+","+COLUMN_PROD_ID+") "
                + ");";
    }

    /**
     * @param context
     */
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableUserType.CREATE_TABLE);
        db.execSQL(TableUserStatus.CREATE_TABLE);
        db.execSQL(TableUser.CREATE_TABLE);
        db.execSQL(TableProductType.CREATE_TABLE);
        db.execSQL(TableProduct.CREATE_TABLE);
        db.execSQL(TableSaleStatus.CREATE_TABLE);
        db.execSQL(TableSales.CREATE_TABLE);
        db.execSQL(TableRelSalesProduct.CREATE_TABLE);
    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableUser.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TableUserType.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TableUserStatus.TABLE_NAME);
        onCreate(db);
    }
}