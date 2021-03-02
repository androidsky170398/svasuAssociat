package com.example.svasuassociate;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    

    TextInputEditText mobile,edt_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mobile = findViewById(R.id.edt_mobile);
        edt_pass = findViewById(R.id.edt_pass);
        findViewById(R.id.btn_sendotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobile.getText().toString().trim().isEmpty() || mobile.getText().toString().equalsIgnoreCase("")) {
//                    com.example.svasuassociate.Constants.showCustomToastInCenter(MainActivity.this, "Please enter Password");
                       Toast.makeText(MainActivity.this, "Please enter mobile no.", Toast.LENGTH_SHORT).show();
                } else if (mobile.getText().toString().trim().length() != 10) {
                    Toast.makeText(MainActivity.this, "Please Enter 10 digit mobile no.", Toast.LENGTH_SHORT).show();
                } else if (edt_pass.getText().toString().trim().isEmpty() || edt_pass.getText().toString().equalsIgnoreCase("")) {
//                    com.example.svasuassociate.Constants.showCustomToastInCenter(MainActivity.this, "Please enter Password");
                       Toast.makeText(MainActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(MainActivity.this, dashboard.class);
                    startActivity(i);
                    //Toast.makeText(MainActivity.this, "You succesfully login", Toast.LENGTH_SHORT).show();
//                    isValid();
                }
                //  startActivity(new Intent(LoginActivity.this, OtpVerify.class));
            }
        });
    }

//    public void isValid() {
//        if (connectionDetector.isConnected()) {
//            sendOtp();
//        } else {
//            Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void sendOtp() {
//        Toast.makeText(MainActivity.this, "You succesfully login", Toast.LENGTH_SHORT).show();
//    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        finishAffinity();
        //
    }

}