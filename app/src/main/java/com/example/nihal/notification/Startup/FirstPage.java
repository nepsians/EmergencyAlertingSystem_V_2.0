package com.example.nihal.notification.Startup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.nihal.notification.MainActivity;
import com.example.nihal.notification.R;

public class FirstPage extends AppCompatActivity {
    public LinearLayout User,Personal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        findViewById(R.id.onUserClicked).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FirstPage.this,userLogin_Activity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.onPersonalClicked).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FirstPage.this,ambulanceLogin_Activity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.skipToMainActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Adsf","Adfasdf");
                Intent intent=new Intent(FirstPage.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
