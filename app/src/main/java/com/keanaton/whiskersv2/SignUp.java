package com.keanaton.whiskersv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

public class SignUp extends AppCompatActivity {
    private Button proceed;
    private EditText fname, lname, contact, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fname = (EditText)findViewById(R.id.editSignUpFname);
        lname = (EditText)findViewById(R.id.editSignUpLname);
        contact = (EditText)findViewById(R.id.editSignUpContact);
        email = (EditText) findViewById(R.id.editSignUpEmail);

        proceed = (Button)findViewById(R.id.signUpContinue);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, SignUpInfo.class);
                Bundle b = new Bundle();
                b.putString("fname", fname.getText().toString().trim());
                b.putString("lname", lname.getText().toString().trim());
                b.putString("contact", contact.getText().toString().trim());
                b.putString("email", email.getText().toString().trim());

                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
}
