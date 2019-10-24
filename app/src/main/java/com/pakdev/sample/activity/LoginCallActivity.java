package com.pakdev.sample.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pakdev.sample.R;


public class LoginCallActivity extends Activity {

    private Button mLoginButton;
    private EditText mLoginName;
    private ProgressDialog mSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);



        mLoginName = (EditText) findViewById(R.id.loginName);

        mLoginButton = (Button) findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginClicked();
            }
        });
    }



    @Override
    protected void onPause() {
        if (mSpinner != null) {
            mSpinner.dismiss();
        }
        super.onPause();
    }



    private void loginClicked() {
        final String userName = mLoginName.getText().toString();

        if (userName.isEmpty()) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            return;
        }
        showSpinner();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSpinner.dismiss();
                    openPlaceCallActivity();



            }
        },500)
;


    }

    private void openPlaceCallActivity() {


//        Intent mainActivity = new Intent(this, MessagingActivity.class);
//       mainActivity.putExtra("user",mLoginName.getText().toString()) ;
//        startActivity(mainActivity);
    }

    private void showSpinner() {
        mSpinner = new ProgressDialog(this);
        mSpinner.setTitle("Logging in");
        mSpinner.setMessage("Please wait...");
        mSpinner.show();
    }
}
