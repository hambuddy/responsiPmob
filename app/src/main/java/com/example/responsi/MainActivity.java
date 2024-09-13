package com.example.responsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button BtnUser;
    Button BtnAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BtnUser = (Button) findViewById(R.id.btnUser);
        BtnAdmin = (Button) findViewById(R.id.btnAdmin);

        BtnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent user = new Intent(MainActivity.this, LoginActivity.class);

                startActivity(user);
            }
        });

        BtnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent admin = new Intent(MainActivity.this, LoginAdminActivity.class);

                startActivity(admin);
            }
        });
    }
}