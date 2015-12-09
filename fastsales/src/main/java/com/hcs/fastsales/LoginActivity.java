package com.hcs.fastsales;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hcs.common.activity.CommonFullActivity;
import com.hcs.common.dialog.ViewDialog;
import com.hcs.common.session.SessionManager;
import com.hcs.common.task.MapErrorProgressTask;
import com.hcs.common.task.MsgProgressTask;
import com.hcs.common.utils.ViewUtils;
import com.hcs.fastsales.dao.DaoDefault;
import com.hcs.fastsales.dao.user.DaoUser;
import com.hcs.fastsales.dto.user.DtoUser;
import com.hcs.fastsales.dto.user.DtoUserStatus;
import com.hcs.fastsales.user.CreateUserActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fernando.ramirez on 22/06/2015.
 */
public class LoginActivity extends CommonFullActivity {

    private AutoCompleteTextView actvUserName;
    private EditText etPin;
    private Button btnLogin;
    private TextView tvJoinUs;
    private DtoUser dtoUser;
    private ViewDialog viewDialog;
    private SessionManager sessionManager;

    public LoginActivity() {
        super(R.layout.activity_login);
    }

    @Override
    protected void initViews() {
        sessionManager = new SessionManager(this);
        actvUserName = (AutoCompleteTextView) findViewById(R.id.actvUserName);
        etPin = (EditText) findViewById(R.id.etPin);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewUtils.setBlankError(actvUserName, etPin);
                MapErrorProgressTask task = new MapErrorProgressTask(LoginActivity.this);
                task.execute(new String[]{"doInBackgroundCheckUser", "onPostExecuteValidateUser"});
            }
        });

        tvJoinUs = (TextView) findViewById(R.id.tvJoinUs);
        tvJoinUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivity(LoginActivity.this, CreateUserActivity.class);
            }
        });
        this.enabledActions(false);
        MsgProgressTask msgProgressTask = new MsgProgressTask(this);
        msgProgressTask.execute(new String[]{"doInBackgroundCreateDefaultsDB", "onPostExecuteEnabledActions"});
    }

    public String doInBackgroundCreateDefaultsDB() {
        DaoDefault.createDefaultData(this);
        return ViewUtils.completedTask;
    }

    public void onPostExecuteEnabledActions() {
        this.enabledActions(true);
    }

    private void enabledActions(boolean enabled) {
        btnLogin.setEnabled(enabled);
        tvJoinUs.setEnabled(enabled);
    }

    public Map<View, String> doInBackgroundCheckUser() {
        Map<View, String> mapError = new HashMap<>();
        if(ViewUtils.isBlank(actvUserName)) {
            mapError.put(actvUserName, ViewUtils.getStringResource(this, R.string.error_required, R.string.user_name));
        }

        if (ViewUtils.isBlank(etPin)) {
            mapError.put(etPin, ViewUtils.getStringResource(this, R.string.error_required, R.string.pin));
        }

        if(mapError.isEmpty()) {
            dtoUser = new DaoUser(this).checkUserCredentials(ViewUtils.getText(actvUserName), ViewUtils.getText(etPin));
        }
        return mapError;
    }

    public void onPostExecuteValidateUser() {
        viewDialog = new ViewDialog(this);
        if (dtoUser != null) {
            String userStatus = dtoUser.getDtoUserStatus().getUserSatus();
            if (userStatus.equals(DtoUserStatus.Status.ACTIVE.status())) {
                sessionManager.createLoginSession(dtoUser);
                goToActivityAndFinishThis(this, MainMenuActivity.class);
            } else if (userStatus.equals(DtoUserStatus.Status.RECEIVED.status())) {
                viewDialog.showInfoDialog(R.string.txtReceivedUser);
            } else if (userStatus.equals(DtoUserStatus.Status.INACTIVE.status())) {
                viewDialog.showInfoDialog(R.string.txtInactiveUser);
            } else {
                viewDialog.showInfoDialog(R.string.txtcancelledUser);
            }
        } else {
            viewDialog.showInfoDialog(R.string.txtUserPassX);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.sessionManager.checkLogin(MainMenuActivity.class, DtoUser.class);
    }
}