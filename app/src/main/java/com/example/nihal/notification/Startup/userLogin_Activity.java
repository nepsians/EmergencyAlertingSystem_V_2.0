package com.example.nihal.notification.Startup;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nihal.notification.R;

public class userLogin_Activity extends AppCompatActivity {

    private ProgressBar progressBar;
    private FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_);

        progressBar=findViewById(R.id.progress_bar);
        floatingActionButton=findViewById(R.id.fab);

        findViewById(R.id.sign_up_for_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(userLogin_Activity.this, "Login data register", Toast.LENGTH_LONG).show();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchAction();
            }
        });
    }

    private void searchAction(){
        progressBar.setVisibility(View.VISIBLE);
        floatingActionButton.setAlpha(0f);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                floatingActionButton.setAlpha(1f);
                Toast.makeText(userLogin_Activity.this, "Login data submitted", Toast.LENGTH_LONG).show();
            }
        },1000);
    }
}
