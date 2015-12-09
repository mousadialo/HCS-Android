package com.hcs.fastsales;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.hcs.common.session.SessionManager;
import com.hcs.common.utils.ViewUtils;
import com.hcs.fastsales.dto.user.DtoUser;
import com.hcs.fastsales.sale.PayOrderActivity;
import com.hcs.fastsales.user.UpdateUserDataActivity;

/**
 * Created by fernando.ramirez on 22/06/2015.
 */
public class MainMenuActivity extends ActionBarActivity {

    private DtoUser currentUser;
    private SessionManager sessionManager;
    private Toolbar toolbar;                                      // Declaring the Toolbar Object
    private RecyclerView mRecyclerView;                           // Declaring RecyclerView
    private RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    private RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    private DrawerLayout Drawer;                                  // Declaring DrawerLayout
    private ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle
    private Button btnPayOrder;
    private Button btnPlaceOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        this.initViews();
    }

    protected void initViews() {
        sessionManager = new SessionManager(this);
        currentUser = (DtoUser) this.sessionManager.getUserDetails(DtoUser.class);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

        mAdapter = new MyAdapter(
                new String[]{
                        ViewUtils.getStringResource(this, R.string.action_help),
                        ViewUtils.getStringResource(this, R.string.action_updateUser),
                        ViewUtils.getStringResource(this, R.string.action_settings),
                        ViewUtils.getStringResource(this, R.string.action_logout)},
                new int[]{
                        R.mipmap.help,
                        R.mipmap.pencil_edit,
                        R.mipmap.settings_i,
                        R.mipmap.logout_icon},
                currentUser.getFirstName(),
                currentUser.getDtoUserType().getTypeName(),
                R.drawable.logo_hcs_3d);

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager


        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        //mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.openDrawer,R.string.closeDrawer){
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

        btnPayOrder = (Button) findViewById(R.id.btnPayOrder);
        btnPayOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainMenuActivity.this, PayOrderActivity.class);
                startActivity(i);
            }
        });

        btnPlaceOrder = (Button) findViewById(R.id.btnPlaceOrder);
        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.smiLogout:
                sessionManager.logoutUser(LoginActivity.class);
                break;
            case R.id.smiUpdateUser:
                Intent i = new Intent(this, UpdateUserDataActivity.class);
                startActivity(i);
                break;
            case R.id.miHelp:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}