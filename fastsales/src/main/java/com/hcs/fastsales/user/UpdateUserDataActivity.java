package com.hcs.fastsales.user;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.hcs.common.activity.CommonFullActivity;
import com.hcs.common.dialog.ViewDialog;
import com.hcs.common.session.SessionManager;
import com.hcs.common.task.MapErrorProgressTask;
import com.hcs.common.utils.ViewUtils;
import com.hcs.fastsales.R;
import com.hcs.fastsales.dao.user.DaoUser;
import com.hcs.fastsales.dao.user.DaoUserStatus;
import com.hcs.fastsales.dao.user.DaoUserType;
import com.hcs.fastsales.dto.user.DtoUser;
import com.hcs.fastsales.dto.user.DtoUserStatus;
import com.hcs.fastsales.dto.user.DtoUserType;
import com.hcs.fastsales.user.popup.SearchUserPopup;
import com.hcs.fastsales.user.popup.UserSpinnerAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fernando.ramirez on 23/06/2015.
 */
public class UpdateUserDataActivity extends CommonFullActivity {

    private RelativeLayout rlChangePin;
    private CheckBox cbChangePin;
    private EditText etUserName;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etAddress;
    private EditText etEmailAddress;
    private EditText etPin;
    private EditText etVerifyPin;
    private TextView tvSearchUser;
    private DtoUser dtoUpdateUser = null;
    private boolean isUserUpdated = false;
    private SessionManager sessionManager;
    private ViewDialog viewDialog = null;
    private final static int REQ_CODE_CHILD = 1;
    private RelativeLayout rlStatusAndType;
    private Spinner spUserStatusList;
    private Spinner spUserTypeList;
    private List<DtoUserStatus> dtoUserStatusList;
    private List<DtoUserType> dtoUserTypeList;
    private DtoUserType dtoUserType;
    private DtoUserStatus dtoUserStatus;

    public UpdateUserDataActivity() {
        super(R.layout.activity_update_user_data);
    }

    @Override
    protected void initViews() {
        sessionManager = new SessionManager(this);
        dtoUpdateUser = (DtoUser)this.sessionManager.getUserDetails(DtoUser.class);

        rlChangePin = (RelativeLayout) findViewById(R.id.rlChangePin);
        rlChangePin.setVisibility(View.INVISIBLE);
        rlChangePin.setEnabled(false);
        rlStatusAndType = (RelativeLayout) findViewById(R.id.rlStatusAndType);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etUserName.setEnabled(false);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etEmailAddress = (EditText) findViewById(R.id.etEmailAddress);
        etPin = (EditText) findViewById(R.id.etPin);
        etVerifyPin = (EditText) findViewById(R.id.etVerifyPin);

        tvSearchUser = (TextView) findViewById(R.id.tvSearchUser);
        tvSearchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent child = new Intent(UpdateUserDataActivity.this, SearchUserPopup.class);
                startActivityForResult(child, REQ_CODE_CHILD);
            }
        });

        String userType = dtoUpdateUser.getDtoUserType().getUserType();
        boolean isNotSuper = !userType.equalsIgnoreCase(DtoUserType.Type.SUPER.userType());
        boolean isNotAdmin = !userType.equalsIgnoreCase(DtoUserType.Type.ADMIN.userType());
        if(isNotSuper && isNotAdmin) {
            rlStatusAndType.setVisibility(View.INVISIBLE);
            rlStatusAndType.setEnabled(false);
            tvSearchUser.setVisibility(View.INVISIBLE);
            tvSearchUser.setEnabled(false);
        }

        cbChangePin = (CheckBox) findViewById(R.id.cbChangePin);
        cbChangePin.setChecked(false);
        cbChangePin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rlChangePin.setVisibility(View.VISIBLE);
                    rlChangePin.setEnabled(true);
                } else {
                    rlChangePin.setVisibility(View.INVISIBLE);
                    rlChangePin.setEnabled(false);
                }
            }
        });

        spUserStatusList = (Spinner) findViewById(R.id.spUserStatusList);
        spUserStatusList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dtoUserStatus = dtoUserStatusList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        dtoUserStatusList = new DaoUserStatus(this).getAllListUserStatus();
        UserStatusSpinnerAdapter userStatusSpinnerAdapter = new UserStatusSpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, dtoUserStatusList);
        spUserStatusList.setAdapter(userStatusSpinnerAdapter);

        spUserTypeList = (Spinner) findViewById(R.id.spUserTypeList);
        spUserTypeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dtoUserType = dtoUserTypeList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        dtoUserTypeList = new DaoUserType(this).getAllListUserType();
        UserTypeSpinnerAdapter userTypeSpinnerAdapter = new UserTypeSpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, dtoUserTypeList);
        spUserTypeList.setAdapter(userTypeSpinnerAdapter);

        final Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewUtils.setBlankError(etUserName, etFirstName, etLastName, etAddress, etEmailAddress, etPin, etVerifyPin);
                MapErrorProgressTask task = new MapErrorProgressTask(UpdateUserDataActivity.this);
                task.execute(new String[]{"doInBackgroundUpdateUser", "onPostExecuteUserUpdated"});
            }
        });

        final Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.fillUserData();
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if(requestCode == resultCode) {
            dtoUpdateUser = (DtoUser)data.getExtras().getSerializable("dtoUserSelected");
            this.fillUserData();
        }
    }

    public Map<View, String> doInBackgroundUpdateUser() {
        Map<View, String> mapError = new HashMap<>();
        if(ViewUtils.isBlank(etUserName)) {
            mapError.put(etUserName, ViewUtils.getStringResource(this, R.string.error_required, R.string.user_name));
        }

        if(ViewUtils.isBlank(etFirstName)) {
            mapError.put(etFirstName, ViewUtils.getStringResource(this, R.string.error_required, R.string.txtFirstName));
        }

        if(ViewUtils.isBlank(etLastName)) {
            mapError.put(etLastName, ViewUtils.getStringResource(this, R.string.error_required, R.string.txtLastName));
        }

        if(ViewUtils.isBlank(etAddress)) {
            mapError.put(etAddress, ViewUtils.getStringResource(this, R.string.error_required, R.string.txtAddress));
        }

        if(ViewUtils.isBlank(etEmailAddress)) {
            mapError.put(etEmailAddress, ViewUtils.getStringResource(this, R.string.error_required, R.string.txtEmail));
        }

        String pin;
        if(cbChangePin.isChecked()) {

            if (ViewUtils.isBlank(etPin)) {
                mapError.put(etPin, ViewUtils.getStringResource(this, R.string.error_required, R.string.pin));
            }

            if (ViewUtils.isBlank(etVerifyPin)) {
                mapError.put(etVerifyPin, ViewUtils.getStringResource(this, R.string.error_required, R.string.txtConfirmPin));
            }

            pin = ViewUtils.getText(etPin);
            String confirmPin = ViewUtils.getText(etVerifyPin);
            if (!pin.equals(confirmPin)) {
                mapError.put(etPin, ViewUtils.getStringResource(this, R.string.txtMgsPinEquals, R.string.pin));
                mapError.put(etVerifyPin, ViewUtils.getStringResource(this, R.string.txtMgsPinEquals, R.string.txtConfirmPin));
            }

            pin = ViewUtils.getText(etPin);
            if(pin.length() < DtoUser.PIN_LENGTH) {
                mapError.put(etPin, ViewUtils.getStringResource(this, R.string.txtCompletePIN));
                mapError.put(etVerifyPin, ViewUtils.getStringResource(this, R.string.txtCompletePIN));
            }
        } else {
            pin = dtoUpdateUser.getUserPass();
        }

        if(mapError.isEmpty()) {
            isUserUpdated = new DaoUser(this).updateUser(dtoUpdateUser,
                    ViewUtils.getText(etFirstName),	ViewUtils.getText(etLastName),
                    ViewUtils.getText(etAddress), ViewUtils.getText(etEmailAddress),
                    pin, dtoUserType, dtoUserStatus);
        }
        return mapError;
    }

    public void onPostExecuteUserUpdated() {
        viewDialog = new ViewDialog(this);
        if(!isUserUpdated) {
            viewDialog.showInfoDialog(R.string.txtUserNoUpdated);
            return;
        }

        viewDialog.showInfoDialog(R.string.txtUserUpdated);
        DtoUser dtoSessionUser = (DtoUser)this.sessionManager.getUserDetails(DtoUser.class);
        if(dtoSessionUser.getUserId() == dtoUpdateUser.getUserId()) {
            this.sessionManager.updateUserSession(dtoUpdateUser);
        }
        this.fillUserData();
        ViewUtils.setBlankError(etUserName, etFirstName, etLastName, etAddress, etEmailAddress, etPin, etVerifyPin);
    }

    private void fillUserData() {
        etUserName.setText(dtoUpdateUser.getUserName());
        etFirstName.setText(dtoUpdateUser.getFirstName());
        etLastName.setText(dtoUpdateUser.getLastName());
        etAddress.setText(dtoUpdateUser.getAddress());
        etEmailAddress.setText(dtoUpdateUser.getEmailAddress());
        etPin.setText(null);
        etVerifyPin.setText(null);

        for (int i = 0; i < spUserStatusList.getCount(); i++) {
            String userSatus = ((DtoUserStatus) spUserStatusList.getItemAtPosition(i)).getUserSatus();
            if (userSatus.equals(dtoUpdateUser.getDtoUserStatus().getUserSatus())){
                spUserStatusList.setSelection(i);
            }
        }

        for (int i = 0; i < spUserTypeList.getCount(); i++) {
            String userType = ((DtoUserType) spUserTypeList.getItemAtPosition(i)).getUserType();
            if (userType.equals(dtoUpdateUser.getDtoUserType().getUserType())){
                spUserTypeList.setSelection(i);
            }
        }
    }
}