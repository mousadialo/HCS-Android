package com.hcs.fastsales.dao.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.hcs.fastsales.dto.user.DtoUserStatus;
import com.hcs.fastsales.sqlite.DataSource;
import com.hcs.fastsales.sqlite.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fernando.ramirez on 22/06/2015.
 */
public class DaoUserStatus extends DataSource {

    private static final String TAG = "DaoUserStatus";

    private String[] allColumns = {
            MySQLiteHelper.TableUserStatus.COLUMN_USER_STATUS,
            MySQLiteHelper.TableUserStatus.COLUMN_STATUS_NAME,
            MySQLiteHelper.TableUserType.COLUMN_DESCRIPTION };

    public DaoUserStatus(Context context) {
        super(context);
    }

    /**
     * Method to create a new status
     * @param  dtoUserStatus
     */
    private void addUserStatus(DtoUserStatus dtoUserStatus) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.TableUserStatus.COLUMN_USER_STATUS, dtoUserStatus.getUserSatus());
        values.put(MySQLiteHelper.TableUserStatus.COLUMN_STATUS_NAME, dtoUserStatus.getStatusName());
        values.put(MySQLiteHelper.TableUserStatus.COLUMN_DESCRIPTION, dtoUserStatus.getDescription());
        super.getSqLiteDatabase().insert(MySQLiteHelper.TableUserStatus.TABLE_NAME, null, values);
    }

    /**
     * Method to delete a status
     * @param  dtoUserStatus
     */
    private void removeUserStatus(DtoUserStatus dtoUserStatus) {
        String userStatus = dtoUserStatus.getUserSatus();
        super.getSqLiteDatabase().delete(MySQLiteHelper.TableUserStatus.TABLE_NAME,
                MySQLiteHelper.TableUserStatus.COLUMN_USER_STATUS + " = " + userStatus, null);
    }

    /**
     * Method to get all user status
     * @return List<DtoUserStatus>
     */
    private List<DtoUserStatus> getAllUserStatus() {
        List<DtoUserStatus> listDtoUserStatus = new ArrayList<>();

        Cursor cursor = super.getSqLiteDatabase().query(MySQLiteHelper.TableUserStatus.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DtoUserStatus dtoUserStatus = cursorToDtoUserStatus(cursor);
            listDtoUserStatus.add(dtoUserStatus);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listDtoUserStatus;
    }

    /**
     * Method to covert from Cursor to DtoUserStatus
     * @param cursor
     * @return DtoUserStatus
     */
    private DtoUserStatus cursorToDtoUserStatus(Cursor cursor) {
        return new DtoUserStatus(cursor.getString(0), cursor.getString(1), cursor.getString(2));
    }

    /**
     * Method to create all user types
     */
    public void createUserStatus() {
        try {
            super.open();
            super.getSqLiteDatabase().beginTransaction();
            List<DtoUserStatus> listDtoUserSatus = this.getAllUserStatus();
            if (listDtoUserSatus.isEmpty()) {
                DtoUserStatus.Status enumStatus = DtoUserStatus.Status.ACTIVE;
                DtoUserStatus activeUserStatus = new DtoUserStatus(enumStatus.status(), enumStatus.statusName(), enumStatus.description());
                enumStatus = DtoUserStatus.Status.RECEIVED;
                DtoUserStatus recivedUserStatus = new DtoUserStatus(enumStatus.status(), enumStatus.statusName(), enumStatus.description());
                enumStatus = DtoUserStatus.Status.INACTIVE;
                DtoUserStatus inactiveUserStatus = new DtoUserStatus(enumStatus.status(), enumStatus.statusName(), enumStatus.description());
                enumStatus = DtoUserStatus.Status.CANCELLED;
                DtoUserStatus cancelledUserStatus = new DtoUserStatus(enumStatus.status(), enumStatus.statusName(), enumStatus.description());

                this.addUserStatus(activeUserStatus);
                this.addUserStatus(recivedUserStatus);
                this.addUserStatus(inactiveUserStatus);
                this.addUserStatus(cancelledUserStatus);
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

    public List<DtoUserStatus> getAllListUserStatus() {
        List<DtoUserStatus> listDtoUserSatus = null;
        try {
            super.open();
            listDtoUserSatus = this.getAllUserStatus();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            super.close();
        }
        return listDtoUserSatus;
    }
}
