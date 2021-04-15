package com.example.gogreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import cn.lankton.flowlayout.FlowLayout;

public class SetupActivity extends AppCompatActivity {
FirebaseDatabase fire;
DatabaseReference db;
DatabaseReference myShop;
String num;
EditText name;
Button submit;
String category="";
ArrayList<TextView> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        list=new ArrayList<TextView>();
        name=findViewById(R.id.name);
        submit=findViewById(R.id.submit);
        num=getIntent().getExtras().getString("number");
        FlowLayout fl=findViewById(R.id.flowlayout);
        ArrayList<String> arr=new ArrayList<String>();
        fire=FirebaseDatabase.getInstance();
        myShop=fire.getReference("shop_details");
        db=fire.getReference("Suggestions");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(category.isEmpty())
                    Toast.makeText(SetupActivity.this, "Please select a category", Toast.LENGTH_LONG).show();
                else if (name.getText().toString().isEmpty())
                    Toast.makeText(SetupActivity.this, "Enter the name", Toast.LENGTH_SHORT).show();
                else
                {
                    myShop.child(num).child("name").setValue(name.getText().toString());
                    myShop.child(num).child("category").setValue(category);
                    Intent intent=new Intent(SetupActivity.this,DashboardActivity.class);
                    intent.putExtra("number",num);
                    startActivity(intent);
                    finish();
                }

            }
        });
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot i:snapshot.getChildren())
                {
                    arr.add((String)i.getValue());
                    Log.e("acha",(String)i.getValue());
                }
                for(String s:arr)
                {
                    int ranHeight = dip2px(SetupActivity.this, 30);
                    ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ranHeight);
                    lp.setMargins(dip2px(SetupActivity.this, 10), 0, dip2px(SetupActivity.this, 10), 0);
                    TextView tv = new TextView(SetupActivity.this);
                    tv.setPadding(dip2px(SetupActivity.this, 15), 0, dip2px(SetupActivity.this, 15), 0);
                    tv.setTextColor(Color.parseColor("#FF3030"));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    tv.setText(s);
                    tv.setGravity(Gravity.CENTER_VERTICAL);
                    tv.setLines(1);
                    tv.setBackgroundResource(R.drawable.bg_tag);
                    fl.addView(tv, lp);
                    list.add(tv);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for(TextView t:list)
                                t.setBackgroundResource(R.drawable.bg_tag);
                            tv.setBackgroundResource(R.drawable.bg1_tag);
                            category = tv.getText().toString();
                        }
                    });

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

    });
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
