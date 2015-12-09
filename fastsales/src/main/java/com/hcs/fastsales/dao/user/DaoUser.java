package com.hcs.fastsales.dao.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.hcs.common.utils.SecureUtils;
import com.hcs.fastsales.dto.user.DtoUser;
import com.hcs.fastsales.dto.user.DtoUserStatus;
import com.hcs.fastsales.dto.user.DtoUserType;
import com.hcs.fastsales.sqlite.DataSource;
import com.hcs.fastsales.sqlite.MySQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fernando.ramirez on 22/06/2015.
 */
public class DaoUser extends DataSource {

    private static final String TAG = "DaoUser";

    private String[] allColumns = { MySQLiteHelper.TableUser.COLUMN_USER_ID,
            MySQLiteHelper.TableUser.COLUMN_USERNAME,
            MySQLiteHelper.TableUser.COLUMN_USERPASS,
            MySQLiteHelper.TableUser.COLUMN_FIRSTNAME,
            MySQLiteHelper.TableUser.COLUMN_LASTNAME,
            MySQLiteHelper.TableUser.COLUMN_ADDRESS,
            MySQLiteHelper.TableUser.COLUMN_EMAIL_ADDRESS,
            MySQLiteHelper.TableUser.COLUMN_USER_TYPE,
            MySQLiteHelper.TableUser.COLUMN_USER_STATUS};

    public DaoUser(Context context) {
        super(context);
    }

    /**
     * Method to create a new user
     * @param dtoUser
     */
    private boolean addUser(DtoUser dtoUser) {
        boolean created = false;
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.TableUser.COLUMN_USERNAME, dtoUser.getUserName());
        values.put(MySQLiteHelper.TableUser.COLUMN_USERPASS, dtoUser.getUserPass());
        values.put(MySQLiteHelper.TableUser.COLUMN_FIRSTNAME, dtoUser.getFirstName());
        values.put(MySQLiteHelper.TableUser.COLUMN_LASTNAME, dtoUser.getLastName());
        values.put(MySQLiteHelper.TableUser.COLUMN_ADDRESS, dtoUser.getAddress());
        values.put(MySQLiteHelper.TableUser.COLUMN_EMAIL_ADDRESS, dtoUser.getEmailAddress());
        values.put(MySQLiteHelper.TableUser.COLUMN_USER_TYPE, dtoUser.getDtoUserType().getUserType());
        values.put(MySQLiteHelper.TableUser.COLUMN_USER_STATUS, dtoUser.getDtoUserStatus().getUserSatus());
        super.getSqLiteDatabase().insert(MySQLiteHelper.TableUser.TABLE_NAME, null, values);
        created = true;
        return created;
    }

    /**
     * Method to update an user
     * @param dtoUser
     */
    private boolean updateUser(DtoUser dtoUser) {
        boolean created = false;
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.TableUser.COLUMN_USERPASS, dtoUser.getUserPass());
        values.put(MySQLiteHelper.TableUser.COLUMN_FIRSTNAME, dtoUser.getFirstName());
        values.put(MySQLiteHelper.TableUser.COLUMN_LASTNAME, dtoUser.getLastName());
        values.put(MySQLiteHelper.TableUser.COLUMN_ADDRESS, dtoUser.getAddress());
        values.put(MySQLiteHelper.TableUser.COLUMN_EMAIL_ADDRESS, dtoUser.getEmailAddress());
        values.put(MySQLiteHelper.TableUser.COLUMN_USER_TYPE, dtoUser.getDtoUserType().getUserType());
        values.put(MySQLiteHelper.TableUser.COLUMN_USER_STATUS, dtoUser.getDtoUserStatus().getUserSatus());

        super.getSqLiteDatabase().update(MySQLiteHelper.TableUser.TABLE_NAME, values,
                MySQLiteHelper.TableUser.COLUMN_USER_ID + " = ?",
                new String[]{dtoUser.getUserId() + ""});
        created = true;
        return created;
    }

    /**
     * Method to delete an user
     * @param dtoUser
     */
    private void removeUser(DtoUser dtoUser) {
        int userId = dtoUser.getUserId();
        super.getSqLiteDatabase().delete(MySQLiteHelper.TableUser.TABLE_NAME,
                MySQLiteHelper.TableUser.COLUMN_USER_ID + " = " + userId, null);
    }

    /**
     * Method to get all users
     * @return List<DtoUser>
     */
    private List<DtoUser> getUsers() {
        List<DtoUser> listDtoUser = new ArrayList<>();

        Cursor cursor = super.getSqLiteDatabase().query(MySQLiteHelper.TableUser.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DtoUser dtoUser = cursorToDtoUser(cursor);
            listDtoUser.add(dtoUser);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listDtoUser;
    }

    /**
     * Method to get all users
     * @return List<DtoUser>
     */
    public List<DtoUser> getUsersByUserName(String userName) {
        List<DtoUser> listDtoUser = new ArrayList<DtoUser>();
        try {
            super.open();
            String selectQuery = "SELECT * FROM " + MySQLiteHelper.TableUser.TABLE_NAME + " WHERE upper(" + MySQLiteHelper.TableUser.COLUMN_USERNAME + ") like upper(?)";
            Cursor cursor = super.getSqLiteDatabase().rawQuery(selectQuery, new String[]{"%" + userName + "%"});
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                DtoUser dtoUser = cursorToDtoUser(cursor);
                listDtoUser.add(dtoUser);
                cursor.moveToNext();
            }
            cursor.close();
        }  catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            super.close();
        }
        return listDtoUser;
    }

    /**
     * Method to get an specific user
     * @return DtoUser
     */
    private DtoUser getUserById(int idUser) {
        DtoUser dtoUser = null;

        String selectQuery = "SELECT * FROM "+MySQLiteHelper.TableUser.TABLE_NAME+" WHERE "+MySQLiteHelper.TableUser.COLUMN_USER_ID+" = ?";
        Cursor cursor = super.getSqLiteDatabase().rawQuery(selectQuery, new String[]{idUser + ""});

        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            dtoUser = cursorToDtoUser(cursor);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return dtoUser;
    }

    /**
     * Method to get an specific user
     * @return DtoUser
     */
    private DtoUser getUserByUserName(String userName) {
        DtoUser dtoUser = null;

        String selectQuery = "SELECT * FROM "+MySQLiteHelper.TableUser.TABLE_NAME+" WHERE "+MySQLiteHelper.TableUser.COLUMN_USERNAME+" = ?";
        Cursor cursor = super.getSqLiteDatabase().rawQuery(selectQuery, new String[]{userName});

        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            dtoUser = cursorToDtoUser(cursor);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return dtoUser;
    }

    /**
     * Method to get an specific user
     * @return DtoUserType
     */
    private DtoUser getUser(String userName, String pass) {
        DtoUser dtoUser = null;
        String secureStr = SecureUtils.getSecureStr(pass);
        String selectQuery = "SELECT " +
                MySQLiteHelper.TableUser.COLUMN_USER_ID +", "+
                MySQLiteHelper.TableUser.COLUMN_USERNAME +", "+
                MySQLiteHelper.TableUser.COLUMN_USERPASS +", "+
                MySQLiteHelper.TableUser.COLUMN_FIRSTNAME +", "+
                MySQLiteHelper.TableUser.COLUMN_LASTNAME +", "+
                MySQLiteHelper.TableUser.COLUMN_ADDRESS +", "+
                MySQLiteHelper.TableUser.COLUMN_EMAIL_ADDRESS +", "+
                MySQLiteHelper.TableUser.COLUMN_USER_STATUS +", "+
                MySQLiteHelper.TableUser.COLUMN_USER_TYPE +
                " FROM "+MySQLiteHelper.TableUser.TABLE_NAME+" WHERE "+MySQLiteHelper.TableUser.COLUMN_USERNAME+" = ? and "+MySQLiteHelper.TableUser.COLUMN_USERPASS+" = ?";
        Cursor cursor = super.getSqLiteDatabase().rawQuery(selectQuery, new String[]{userName, secureStr});

        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            dtoUser = cursorToDtoUser(cursor);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return dtoUser;
    }

    /**
     * Method to covert from Cursor to DtoUserType
     * @param cursor
     * @return DtoUserType
     */
    private DtoUser cursorToDtoUser(Cursor cursor) {
        DtoUser dtoUser = new DtoUser();
        dtoUser.setUserId(cursor.getInt(0));
        dtoUser.setUserName(cursor.getString(1));
        dtoUser.setUserPass(cursor.getString(2));
        dtoUser.setFirstName(cursor.getString(3));
        dtoUser.setLastName(cursor.getString(4));
        dtoUser.setAddress(cursor.getString(5));
        dtoUser.setEmailAddress(cursor.getString(6));
        dtoUser.setDtoUserStatus(new DtoUserStatus(cursor.getString(7), cursor.getString(7), cursor.getString(7)));
        dtoUser.setDtoUserType(new DtoUserType(cursor.getString(8), cursor.getString(8), cursor.getString(8)));
        return dtoUser;
    }

    public boolean existUserName(String userName) {
        DtoUser dtoUser = null;
        try {
            super.open();
            dtoUser = this.getUserByUserName(userName);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            super.close();
        }
        return dtoUser != null;
    }

    public DtoUser checkUserCredentials(String user, String pass) {
        DtoUser dtoUser = null;
        try {
            super.open();
            dtoUser = this.getUser(user, pass);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            super.close();
        }
        return dtoUser;
    }

    public DtoUser createUser(String userName, String firstName, String lastName, String address, String emailAddress, String pin) {
        DtoUser dtoUser = null;
        try {
            super.open();
            dtoUser = new DtoUser();
            dtoUser.setUserName(userName);
            dtoUser.setFirstName(firstName);
            dtoUser.setLastName(lastName);
            dtoUser.setAddress(address);
            dtoUser.setEmailAddress(emailAddress);
            dtoUser.setUserPass(SecureUtils.getSecureStr(pin));
            dtoUser.setDtoUserType(new DtoUserType(DtoUserType.Type.BASIC.userType(), DtoUserType.Type.BASIC.typeName(), DtoUserType.Type.BASIC.description()));
            dtoUser.setDtoUserStatus(new DtoUserStatus(DtoUserStatus.Status.RECEIVED.status(), DtoUserStatus.Status.RECEIVED.statusName(), DtoUserStatus.Status.RECEIVED.description()));
            this.addUser(dtoUser);
        } catch (Exception e) {
            dtoUser = null;
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            super.close();
        }
        return dtoUser;
    }

    public boolean updateUser(DtoUser dtoUser, String firstName, String lastName, String address, String emailAddress, String pin, DtoUserType dtoUserType, DtoUserStatus dtoUserStatus) {
        boolean updated = false;
        try {
            super.open();
            dtoUser.setFirstName(firstName);
            dtoUser.setLastName(lastName);
            dtoUser.setAddress(address);
            dtoUser.setEmailAddress(emailAddress);
            dtoUser.setUserPass(pin.length() > DtoUser.PIN_LENGTH ? pin : SecureUtils.getSecureStr(pin));
            dtoUser.setDtoUserType(dtoUserType);
            dtoUser.setDtoUserStatus(dtoUserStatus);
            this.updateUser(dtoUser);
            updated = true;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            super.close();
        }
        return updated;
    }

    /**
     * Method to create default user
     */
    public void createDefaultUser() {
        try {
            super.open();
            super.getSqLiteDatabase().beginTransaction();
            List<DtoUser> listDtoUser = this.getUsers();
            if(listDtoUser.isEmpty()) {
                DtoUser dtoSuperUser = new DtoUser();
                dtoSuperUser.setUserName("ramirezf");
                dtoSuperUser.setUserPass(SecureUtils.getSecureStr("2605"));
                dtoSuperUser.setFirstName("Fernando de Jesus");
                dtoSuperUser.setLastName("Ramirez Mendoza");
                dtoSuperUser.setAddress("Pozo de Cobre 228, Fracc. Pozo Bravo Sur");
                dtoSuperUser.setEmailAddress("fn_nando19@hotmail.com");
                dtoSuperUser.setDtoUserType(new DtoUserType(DtoUserType.Type.SUPER.userType(), DtoUserType.Type.SUPER.typeName(), DtoUserType.Type.SUPER.description()));
                dtoSuperUser.setDtoUserStatus(new DtoUserStatus(DtoUserStatus.Status.ACTIVE.status(), DtoUserStatus.Status.ACTIVE.statusName(), DtoUserStatus.Status.ACTIVE.description()));

                DtoUser dtoAdminUser = new DtoUser();
                dtoAdminUser.setUserName("ramirezj");
                dtoAdminUser.setUserPass(SecureUtils.getSecureStr("2605"));
                dtoAdminUser.setFirstName("Josue Fernando");
                dtoAdminUser.setLastName("Ramirez Sanchez");
                dtoAdminUser.setAddress("Pozo Blanco 154, Pozo Bravo Norte");
                dtoAdminUser.setEmailAddress("fn.nando19@gmail.com");
                dtoAdminUser.setDtoUserType(new DtoUserType(DtoUserType.Type.ADMIN.userType(), DtoUserType.Type.ADMIN.typeName(), DtoUserType.Type.ADMIN.description()));
                dtoAdminUser.setDtoUserStatus(new DtoUserStatus(DtoUserStatus.Status.ACTIVE.status(), DtoUserStatus.Status.ACTIVE.statusName(), DtoUserStatus.Status.ACTIVE.description()));

                DtoUser dtoSalesUser = new DtoUser();
                dtoSalesUser.setUserName("sancheze");
                dtoSalesUser.setUserPass(SecureUtils.getSecureStr("2605"));
                dtoSalesUser.setFirstName("Erika Janette");
                dtoSalesUser.setLastName("Sanchez Gutierrez");
                dtoSalesUser.setAddress("Andador Zenzontle 9206, Pilar Blanco");
                dtoSalesUser.setEmailAddress("eri_janette@hotmail.com");
                dtoSalesUser.setDtoUserType(new DtoUserType(DtoUserType.Type.SALES.userType(), DtoUserType.Type.SALES.typeName(), DtoUserType.Type.SALES.description()));
                dtoSalesUser.setDtoUserStatus(new DtoUserStatus(DtoUserStatus.Status.ACTIVE.status(), DtoUserStatus.Status.ACTIVE.statusName(), DtoUserStatus.Status.ACTIVE.description()));

                DtoUser dtoBasicUser = new DtoUser();
                dtoBasicUser.setUserName("sanchezb");
                dtoBasicUser.setUserPass(SecureUtils.getSecureStr("2605"));
                dtoBasicUser.setFirstName("Brenda Yaneth");
                dtoBasicUser.setLastName("Sanchez Gutierrez");
                dtoBasicUser.setAddress("Andador Zenzontle 9206, Pilar Blanco");
                dtoBasicUser.setEmailAddress("bre_yaneth@hotmail.com");
                dtoBasicUser.setDtoUserType(new DtoUserType(DtoUserType.Type.BASIC.userType(), DtoUserType.Type.BASIC.typeName(), DtoUserType.Type.BASIC.description()));
                dtoBasicUser.setDtoUserStatus(new DtoUserStatus(DtoUserStatus.Status.ACTIVE.status(), DtoUserStatus.Status.ACTIVE.statusName(), DtoUserStatus.Status.ACTIVE.description()));

                this.addUser(dtoSuperUser);
                this.addUser(dtoAdminUser);
                this.addUser(dtoSalesUser);
                this.addUser(dtoBasicUser);
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
}