package com.example.secure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.secure.Modules.PasswordModel;
import com.example.secure.RecyclerViews.PasswordRecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PasswordScreen extends AppCompatActivity {

    DatabaseReference database;
    RecyclerView passwordView;
    FloatingActionButton addNewEntity;
    AlertDialog.Builder alert;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_screen);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("password").child(mAuth.getUid());
        addNewEntity = (FloatingActionButton) findViewById(R.id.newPassword);
        passwordView = (RecyclerView) findViewById(R.id.passwords);
        passwordView.setLayoutManager(new LinearLayoutManager(PasswordScreen.this));

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<PasswordModel> list = new ArrayList<>();
                for(DataSnapshot pwd : dataSnapshot.getChildren()) {
                    PasswordModel p = pwd.getValue(PasswordModel.class);
                    list.add(p);
                }
                passwordView.setAdapter(new PasswordRecyclerView(list));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        passwordView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1))
                    addNewEntity.hide();
                else
                    addNewEntity.show();
            }
        });


        addNewEntity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert = new AlertDialog.Builder(PasswordScreen.this);
                final View view = getLayoutInflater().inflate(R.layout.add_new_password_view, null);
                alert.setView(view);

                final AlertDialog diaglog = alert.create();

                Button add = (Button) view.findViewById(R.id.add);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText website = (EditText) view.findViewById(R.id.website),
                                username = (EditText) view.findViewById(R.id.username),
                                password = (EditText) view.findViewById(R.id.password);

                        String websitename = website.getText().toString(),
                                usrname = username.getText().toString(),
                                pswd = password.getText().toString();

                        if (TextUtils.isEmpty(usrname)) {
                            Toast.makeText(PasswordScreen.this, "Username/Email field can not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(pswd)) {
                            Toast.makeText(PasswordScreen.this, "Password field can not be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String key = database.push().getKey();
                        PasswordModel ps;
                        if(TextUtils.isEmpty(websitename))
                            ps = new PasswordModel(key, usrname, pswd);
                        else
                            ps = new PasswordModel(key, websitename, usrname, pswd);

                        database.child(key).setValue(ps);
                        diaglog.dismiss();

                    }
                });
                diaglog.show();
            }
        });

    }
}
