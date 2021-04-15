package com.example.gogreen;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomDialogClass extends Dialog {
EditText name;
EditText price;
EditText qty;
Button add;
//Button choose;
Context c;
String num;
DatabaseReference db;
    public CustomDialogClass(Activity a,String n) {
        super(a);
        this.num=n;
        this.c=a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customdialog);
        price=findViewById(R.id.price);
        qty=findViewById(R.id.quantity);
        name=findViewById(R.id.item);
        add=findViewById(R.id.add);
        //choose=findViewById(R.id.img);
        item it=new item();
        FirebaseDatabase fire;
        fire =FirebaseDatabase.getInstance();
        db=fire.getReference();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p,n,q;
                n=name.getText().toString();
                p=price.getText().toString();
                q=qty.getText().toString();
                if(n.isEmpty())
                    Toast.makeText(c, "Please enter item name", Toast.LENGTH_SHORT).show();
                else if(q.isEmpty()||q.equals("0"))
                    Toast.makeText(c, "Please specify the quantity", Toast.LENGTH_SHORT).show();
                else if(p.isEmpty())
                    Toast.makeText(c, "Please enter the price", Toast.LENGTH_SHORT).show();
                else
                {
                    it.name=n;
                    it.price=Float.parseFloat(p);
                    it.qty=Integer.parseInt(q);
                    db.child("shop_details").child(num).child("items").child(n).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.exists()){
                                db.child("shop_details").child(num).child("items").child(n).setValue(it);
                                Toast.makeText(c, n+" is added in your list.", Toast.LENGTH_SHORT).show();dismiss();}
                            else
                                Toast.makeText(c, "Entry already exist", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

        });

    }
}
