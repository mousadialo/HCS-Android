package com.hcs.fastsales.user.popup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.hcs.common.activity.BlankAvtivity;
import com.hcs.common.dialog.ViewDialog;
import com.hcs.common.popup.PopupOnNewActivity;
import com.hcs.common.task.MsgProgressTask;
import com.hcs.common.utils.ViewUtils;
import com.hcs.fastsales.R;
import com.hcs.fastsales.dao.user.DaoUser;
import com.hcs.fastsales.dto.user.DtoUser;

import java.util.List;

/**
 * Created by fernando.ramirez on 23/06/2015.
 */
public class SearchUserPopup extends BlankAvtivity {

    private static final ViewGroup NULL = null;
    public final static int RESULT_USER = 1;
    private Spinner spUserList;
    private EditText etSearchUserName;
    private DaoUser daoUser;
    private List<DtoUser> userList = null;
    private DtoUser dtoUserSelected = null;
    private PopupOnNewActivity popupOnNewActivity = null;
    private ViewDialog viewDialog = null;
    private final static int RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.daoUser = new DaoUser(this);
        this.currentActivity = this;
        this.viewDialog = new ViewDialog(this);
    }

    @Override
    protected void initViews() {
        final View layout = currentActivity.getLayoutInflater().inflate(R.layout.search_user, NULL);
        etSearchUserName = (EditText) layout.findViewById(R.id.etSearchUserName);

        spUserList = (Spinner) layout.findViewById(R.id.spUserList);
        spUserList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dtoUserSelected = userList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final ImageButton ibtnSearchUser = (ImageButton) layout.findViewById(R.id.ibtnSearchUser);
        ibtnSearchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgProgressTask task = new MsgProgressTask(currentActivity, SearchUserPopup.this);
                task.execute(new String[] { "doInBackgroundSearchUser", "onPostExecuteCompletedSearch" });
            }
        });

        final Button btnSelect = (Button) layout.findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dtoUserSelected == null) {
                    viewDialog.showInfoDialog(R.string.txtSelectAnUser);
                    return;
                }
                Intent resultIntent = new Intent();
                resultIntent.putExtra("dtoUserSelected", dtoUserSelected);
                setResult(RESULT_CODE, resultIntent);
                popupOnNewActivity.dismiss();
                currentActivity.finish();
            }
        });

        popupOnNewActivity = new PopupOnNewActivity(currentActivity, layout);
    }

    public String doInBackgroundSearchUser() {
        if(ViewUtils.isBlank(etSearchUserName)) {
            return ViewUtils.getStringResource(currentActivity, R.string.error_required, R.string.user_name);
        }

        userList = daoUser.getUsersByUserName(ViewUtils.getText(etSearchUserName));

        return ViewUtils.completedTask;
    }

    public void onPostExecuteCompletedSearch() {
        if (userList.isEmpty()) {
            viewDialog.showInfoDialog(R.string.txtNoResultSearch);
            return;
        }
        UserSpinnerAdapter adapter = new UserSpinnerAdapter(currentActivity, android.R.layout.simple_spinner_dropdown_item, userList);
        spUserList.setAdapter(adapter);
    }
}