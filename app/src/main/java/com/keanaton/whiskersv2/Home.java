package com.keanaton.whiskersv2;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class Home extends AppCompatActivity {
    private Button logout;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        progressDialog = new ProgressDialog(this);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        logout = (Button)findViewById(R.id.buttonLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == logout){
                    progressDialog.setMessage("Logging out...");
                    progressDialog.show();
                    if(SharedPrefManager.getInstance(Home.this).logout()){
                        progressDialog.dismiss();
                        finish();
                        startActivity(new Intent(Home.this, MainActivity.class));
                    }
                }
            }
        });


    }
}
