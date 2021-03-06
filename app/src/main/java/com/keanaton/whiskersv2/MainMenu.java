package com.keanaton.whiskersv2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        MenuItem home = menu.findItem(R.id.nav_home);
        home.setTitle("Home");

        MenuItem findPet = menu.findItem(R.id.nav_findpet);
        findPet.setTitle("Find Pet");

        MenuItem petEntry = menu.findItem(R.id.nav_petentry);
        petEntry.setTitle("Pet Entry");

        MenuItem message = menu.findItem(R.id.nav_message);
        message.setTitle("Message");

        MenuItem account = menu.findItem(R.id.nav_account);
        account.setTitle("Account");

        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.contentFrame, new FragmentHome());
        tx.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        ProgressDialog progressDialog = new ProgressDialog(MainMenu.this);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            fragment = new FragmentHome();
            toolbar.setTitle("Home");
        } else if (id == R.id.nav_findpet) {
            fragment = new FragmentFindPet();
            toolbar.setTitle("Find Pet");
        } else if (id == R.id.nav_petentry) {
            fragment = new FragmentPetEntry();
            toolbar.setTitle("Pet Entry");
        } else if (id == R.id.nav_message) {
            fragment = new FragmentMessage();
            toolbar.setTitle("Message");
        } else if (id == R.id.nav_account) {
            fragment = new FragmentAccount();
            toolbar.setTitle("Account");
        } else if (id == R.id.nav_logout) {
            progressDialog.setMessage("Logging out...");
            progressDialog.show();
            if(SharedPrefManager.getInstance(MainMenu.this).logout()){
                progressDialog.dismiss();
                finish();
                startActivity(new Intent(MainMenu.this, MainActivity.class));
            }

        }

        if(fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.contentFrame, fragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
