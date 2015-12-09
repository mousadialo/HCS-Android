package com.hcs.fastsales.dao;

import android.content.Context;

import com.hcs.fastsales.dao.product.DaoProduct;
import com.hcs.fastsales.dao.product.DaoProductType;
import com.hcs.fastsales.dao.user.DaoUser;
import com.hcs.fastsales.dao.user.DaoUserStatus;
import com.hcs.fastsales.dao.user.DaoUserType;

/**
 * Created by fernando.ramirez on 23/06/2015.
 */
public class DaoDefault {
    public static void createDefaultData(Context context) {
        new DaoUserType(context).createUserTypes();
        new DaoUserStatus(context).createUserStatus();
        new DaoUser(context).createDefaultUser();
        new DaoProductType(context).createProductTypes();
        new DaoProduct(context).createProducts();
        new DaoUserStatus(context).createUserStatus();
    }
}
