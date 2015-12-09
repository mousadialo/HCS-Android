package com.hcs.fastsales.user;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.hcs.common.activity.CommonFullActivity;
import com.hcs.common.dialog.ViewDialog;
import com.hcs.common.task.MapErrorProgressTask;
import com.hcs.common.utils.ViewUtils;
import com.hcs.fastsales.R;
import com.hcs.fastsales.dao.user.DaoUser;
import com.hcs.fastsales.dto.user.DtoUser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fernando.ramirez on 23/06/2015.
 */
public class CreateUserActivity extends CommonFullActivity {

    private AutoCompleteTextView actvFirstName;
    private AutoCompleteTextView actvLastName;
    private AutoCompleteTextView actvAddress;
    private EditText etEmailAddress;
    private EditText etUserName;
    private EditText etPin;
    private EditText etConfirmPin;
    private Button btnJoin;
    private Button btnBack;
    private DaoUser daoUser;
    private DtoUser dtoUser;
    private ViewDialog viewDialog;

    public CreateUserActivity() {
        super(R.layout.activity_create_user);
    }

    @Override
    protected void initViews() {
        daoUser = new DaoUser(this);
        actvFirstName = (AutoCompleteTextView) findViewById(R.id.actvFirstName);
        actvLastName = (AutoCompleteTextView) findViewById(R.id.actvLastName);
        actvAddress = (AutoCompleteTextView) findViewById(R.id.actvAddress);
        etEmailAddress = (EditText) findViewById(R.id.etEmailAddress);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPin = (EditText) findViewById(R.id.etPin);
        etConfirmPin = (EditText) findViewById(R.id.etConfirmPin);

        btnJoin = (Button) findViewById(R.id.btnJoin);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewUtils.setBlankError(actvFirstName, actvLastName, actvAddress, etUserName, etPin, etConfirmPin);
                MapErrorProgressTask task = new MapErrorProgressTask(CreateUserActivity.this);
                task.execute(new String[]{"doInBackgroundCreateUser", "onPostExecuteValidRegistered"});
            }
        });

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public Map<View, String> doInBackgroundCreateUser() {
        Map<View, String> mapError = new HashMap<>();
        if(ViewUtils.isBlank(actvFirstName)) {
            mapError.put(actvFirstName, ViewUtils.getStringResource(this, R.string.error_required, R.string.txtFirstName));
        }

        if (ViewUtils.isBlank(actvLastName)) {
            mapError.put(actvLastName, ViewUtils.getStringResource(this, R.string.error_required, R.string.txtLastName));
        }

        if (ViewUtils.isBlank(actvAddress)) {
            mapError.put(actvAddress, ViewUtils.getStringResource(this, R.string.error_required, R.string.txtAddress));
        }

        if (ViewUtils.isBlank(etEmailAddress)) {
            mapError.put(etEmailAddress, ViewUtils.getStringResource(this, R.string.error_required, R.string.txtEmail));
        }

        if (ViewUtils.isBlank(etUserName)) {
            mapError.put(etUserName, ViewUtils.getStringResource(this, R.string.error_required, R.string.user_name));
        } else if (daoUser.existUserName(ViewUtils.getText(etUserName))) {
            mapError.put(etUserName, ViewUtils.getStringResource(this, R.string.txtChooseUniqueUserName));
        }

        if (ViewUtils.isBlank(etPin)) {
            mapError.put(etPin, ViewUtils.getStringResource(this, R.string.error_required, R.string.pin));
        }

        if (ViewUtils.isBlank(etConfirmPin)) {
            mapError.put(etConfirmPin, ViewUtils.getStringResource(this, R.string.error_required, R.string.txtConfirmPin));
        }

        String pin = ViewUtils.getText(etPin);
        String confirmPin = ViewUtils.getText(etConfirmPin);
        if (!pin.equals(confirmPin)) {
            mapError.put(etPin, ViewUtils.getStringResource(this, R.string.txtMgsPinEquals, R.string.pin));
            mapError.put(etConfirmPin, ViewUtils.getStringResource(this, R.string.txtMgsPinEquals, R.string.txtConfirmPin));
        }

        if(mapError.isEmpty()) {
            dtoUser = daoUser.createUser(
                    ViewUtils.getText(etUserName),
                    ViewUtils.getText(actvFirstName),
                    ViewUtils.getText(actvLastName),
                    ViewUtils.getText(actvAddress),
                    ViewUtils.getText(etEmailAddress),
                    pin);
        }
        return mapError;
    }

    public void onPostExecuteValidRegistered() {
        viewDialog = new ViewDialog(this);
        if(dtoUser == null) {
            viewDialog.showInfoDialog(R.string.txtUserCannotBeRegistered);
            return;
        }

        viewDialog.showInfoDialog(R.string.txtUserPendingAcept);
        ViewUtils.setBlank(actvFirstName, actvLastName, actvAddress, etUserName, etPin, etConfirmPin);
    }
}
