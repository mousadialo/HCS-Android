package com.hcs.medicinealarm;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hcs.common.dialog.ViewDialog;
import com.hcs.common.session.SessionManager;
import com.hcs.common.task.MapErrorProgressTask;
import com.hcs.common.utils.ViewUtils;
import com.hcs.medicinealarm.dto.DtoUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "1","fer@hcs.com", "2228", "Fernando", "Ramirez"
    };

    private DtoUser dtoUser;
    private ViewDialog viewDialog;
    private SessionManager sessionManager;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewUtils.setBlankError(mEmailView, mPasswordView);
                MapErrorProgressTask task = new MapErrorProgressTask(LoginActivity.this);
                task.execute(new String[]{"doInBackgroundAttemptLogin", "onPostExecuteValidateUser"});
            }
        });
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public Map<View, String> doInBackgroundAttemptLogin() {

        Map<View, String> mapError = new HashMap<>();

        if(ViewUtils.isBlank(mEmailView)) {
            mapError.put(mEmailView, ViewUtils.getStringResource(this, R.string.error_field_required));
        } else if (!isEmailValid(ViewUtils.getText(mEmailView))) {
            mapError.put(mEmailView, ViewUtils.getStringResource(this, R.string.error_invalid_email));
        }

        // Check for a valid password, if the user entered one.
        if (ViewUtils.isBlank(mPasswordView) || !isPasswordValid(ViewUtils.getText(mPasswordView))) {
            mapError.put(mPasswordView, ViewUtils.getStringResource(this, R.string.error_invalid_password));
        }

        if(mapError.isEmpty()) {
            dtoUser = checkUserCredentials(ViewUtils.getText(mEmailView), ViewUtils.getText(mPasswordView));
        }
        return mapError;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 4;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    public DtoUser checkUserCredentials(String mEmail, String mPassword){
        // TODO: attempt authentication against a network service.
        DtoUser dtoUserLoged = null;
        if (DUMMY_CREDENTIALS[1].equals(mEmail) && DUMMY_CREDENTIALS[2].equals(mPassword)) {
                dtoUserLoged = new DtoUser();
                dtoUserLoged.setUserId(Integer.parseInt(DUMMY_CREDENTIALS[0]));
                dtoUserLoged.setEmailAddress(mEmail);
                dtoUserLoged.setUserPass(mPassword);
                dtoUserLoged.setEmailAddress(mEmail);
                dtoUserLoged.setFirstName(DUMMY_CREDENTIALS[3]);
                dtoUserLoged.setLastName(DUMMY_CREDENTIALS[4]);
                return dtoUserLoged;
        }
        // TODO: register the new account here.
        return dtoUserLoged;
    }

    public void onPostExecuteValidateUser() {
        viewDialog = new ViewDialog(this);
        if (dtoUser== null) {
            viewDialog.showInfoDialog("User not Loged");
        } else {
            viewDialog.showInfoDialog("User Loged "+ dtoUser.getFirstName());
        }
    }
}