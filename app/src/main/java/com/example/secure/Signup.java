package com.example.secure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {

    EditText editTextUsername, editTextPassword, editTextConfirmPassword;
    Button ButtonSignup;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextUsername = (EditText) findViewById(R.id.signupEmail);
        editTextPassword = (EditText) findViewById(R.id.signupPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.signupConfirmPassword);
        ButtonSignup = (Button) findViewById(R.id.signup);

        mAuth = FirebaseAuth.getInstance();

        ButtonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateANewUser();
            }
        });
    }

    private void CreateANewUser() {
        String email = editTextUsername.getText().toString().trim(),
                password = editTextPassword.getText().toString().trim(),
                confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if(email.isEmpty()) {
            editTextUsername.setError("Email field can't be empty");
            editTextUsername.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            editTextPassword.setError("Password field can't be empty");
            editTextPassword.requestFocus();
            return;
        }

        if(confirmPassword.isEmpty()) {
            editTextConfirmPassword.setError("Confirm your password for safty");
            editTextConfirmPassword.requestFocus();
            return;
        }

        if(password.compareTo(confirmPassword)!= 0) {
            Toast.makeText(Signup.this, "Password did not match", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {

                            Toast.makeText(Signup.this, "Failed to Sign up!.. Try again later",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Intent intent = new Intent(Signup.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
    }


}
