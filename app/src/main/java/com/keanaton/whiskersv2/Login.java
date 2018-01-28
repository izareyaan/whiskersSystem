package com.keanaton.whiskersv2;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private EditText logUsername, logPassword;
    private Button loginUser;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainMenu.class));
            return;
        }

        logUsername = (EditText)findViewById(R.id.editUsername);
        logPassword = (EditText)findViewById(R.id.editPassword);
        loginUser = (Button)findViewById(R.id.logLogin);

        progressDialog = new ProgressDialog(this);

        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == loginUser){
                    loginUser();
                }
            }
        });
    }

    private void loginUser(){
        final String username = logUsername.getText().toString().trim();
        final String password = logPassword.getText().toString().trim();

        progressDialog.setMessage("Login User...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.url_login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(response);
                            //Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();

                            if(json.getString("error").equals("true")){
                                Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
                            }else{
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(
                                        json.getString("id"),
                                        json.getString("username"),
                                        json.getString("fname"),
                                        json.getString("lname"),
                                        json.getString("contact"),
                                        json.getString("email"),
                                        json.getString("address")
                                );

                                Intent intent = new Intent(Login.this, MainMenu.class);
                                Bundle b = new Bundle();
                                b.putString("jsonObject", response);
                                intent.putExtras(b);
                                startActivity(intent);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error on displaying json object!"+response, Toast.LENGTH_LONG).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(), "Error in the System!", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
