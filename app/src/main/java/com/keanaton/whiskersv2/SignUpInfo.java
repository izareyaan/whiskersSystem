package com.keanaton.whiskersv2;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpInfo extends AppCompatActivity {
    private EditText username, password;
    private ProgressDialog progressDialog;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_info);

        username = (EditText)findViewById(R.id.editSignUpUsername);
        password = (EditText)findViewById(R.id.editSignUpPword);
        register = (Button)findViewById(R.id.signUpRegister);

        progressDialog = new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == register){
                    registerUser();
                }
            }
        });
    }

    private void registerUser(){
        final String fname, lname, contact, email, uname, pword;
        Bundle b = getIntent().getExtras();

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        if(b != null){
            fname = b.getString("fname");
            lname = b.getString("lname");
            contact = b.getString("contact");
            email = b.getString("email");

            uname = username.getText().toString().trim();
            pword = password.getText().toString().trim();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.url_register,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject json = new JSONObject(response);
                                Toast.makeText(getApplicationContext(), json.getString("message"), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Error in JSON", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error in the System!", Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", uname);
                    params.put("password", pword);
                    params.put("fname", fname);
                    params.put("lname", lname);
                    params.put("contact", contact);
                    params.put("email", email);

                    return params;
                }
            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }

    }
}
