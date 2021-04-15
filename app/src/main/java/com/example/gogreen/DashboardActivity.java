package com.example.gogreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DashboardActivity extends AppCompatActivity {
FloatingActionButton fab;
String no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        fab=findViewById(R.id.fab);
        no=getIntent().getExtras().getString("number");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogClass cdd=new CustomDialogClass(DashboardActivity.this,no);
                cdd.show();

            }
        });
    }
}