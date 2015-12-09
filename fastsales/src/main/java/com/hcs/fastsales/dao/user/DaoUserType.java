package com.hcs.fastsales.dao.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.hcs.fastsales.dto.user.DtoUserType;
import com.hcs.fastsales.sqlite.DataSource;
import com.hcs.fastsales.sqlite.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fernando.ramirez on 22/06/2015.
 */
public class DaoUserType extends DataSource {

    private static final String TAG = "DaoUserType";

    private String[] allColumns = {
            MySQLiteHelper.TableUserType.COLUMN_USER_TYPE,
            MySQLiteHelper.TableUserType.COLUMN_TYPE_NAME,
            MySQLiteHelper.TableUserType.COLUMN_DESCRIPTION };

    public DaoUserType(Context context) {
        super(context);
    }

    /**
     * Method to create a new User Type
     * @param dtoUserType
     */
    private void addUserType(DtoUserType dtoUserType) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.TableUserType.COLUMN_USER_TYPE, dtoUserType.getUserType());
        values.put(MySQLiteHelper.TableUserType.COLUMN_TYPE_NAME, dtoUserType.getTypeName());
        values.put(MySQLiteHelper.TableUserType.COLUMN_DESCRIPTION, dtoUserType.getDescription());
        super.getSqLiteDatabase().insert(MySQLiteHelper.TableUserType.TABLE_NAME, null, values);
    }

    /**
     * Method to delete a User Type
     * @param dtoUserType
     */
    void delete(DtoUserType dtoUserType) {
        String userType = dtoUserType.getUserType();
        super.getSqLiteDatabase().delete(MySQLiteHelper.TableUserType.TABLE_NAME,
                MySQLiteHelper.TableUserType.COLUMN_USER_TYPE + " = " + userType, null);
    }

    /**
     * Method to get all user types
     * @return List<DtoUserType>
     */
    private List<DtoUserType> getAllUserTypes() {
        List<DtoUserType> listDtoUserType = new ArrayList<>();

        Cursor cursor = super.getSqLiteDatabase().query(MySQLiteHelper.TableUserType.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DtoUserType dtoUserType = cursorToDtoUserType(cursor);
            listDtoUserType.add(dtoUserType);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listDtoUserType;
    }

    /**
     * Method to covert from Cursor to DtoUserType
     * @param cursor
     * @return DtoUserType
     */
    private DtoUserType cursorToDtoUserType(Cursor cursor) {
        return new DtoUserType(cursor.getString(0), cursor.getString(1), cursor.getString(2));
    }

    /**
     * Method to create all user types
     */
    public void createUserTypes() {
        try {
            super.open();
            super.getSqLiteDatabase().beginTransaction();
            List<DtoUserType> listDtoUserType = this.getAllUserTypes();
            if (listDtoUserType.isEmpty()) {
                DtoUserType superUserType = new DtoUserType(DtoUserType.Type.SUPER.userType(), DtoUserType.Type.SUPER.typeName(), DtoUserType.Type.SUPER.description());
                DtoUserType adminUserType = new DtoUserType(DtoUserType.Type.ADMIN.userType(), DtoUserType.Type.ADMIN.typeName(), DtoUserType.Type.ADMIN.description());
                DtoUserType salesUserType = new DtoUserType(DtoUserType.Type.SALES.userType(), DtoUserType.Type.SALES.typeName(), DtoUserType.Type.SALES.description());
                DtoUserType basicUserType = new DtoUserType(DtoUserType.Type.BASIC.userType(), DtoUserType.Type.BASIC.typeName(), DtoUserType.Type.BASIC.description());

                this.addUserType(superUserType);
                this.addUserType(adminUserType);
                this.addUserType(salesUserType);
                this.addUserType(basicUserType);
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

    public List<DtoUserType> getAllListUserType() {
        List<DtoUserType> listDtoUserType = null;
        try {
            super.open();
            listDtoUserType = this.getAllUserTypes();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            super.close();
        }
        return listDtoUserType;
    }
}
