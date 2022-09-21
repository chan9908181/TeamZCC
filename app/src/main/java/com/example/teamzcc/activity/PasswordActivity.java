package com.example.teamzcc.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.teamzcc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Button submitButton=(Button)findViewById(R.id.Submit);
        EditText emailAddress=(EditText)findViewById(R.id.emailSubmission);
        mAuth=FirebaseAuth.getInstance();


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailAddress.getText().toString().trim();
                if(email.isEmpty()){
                    emailAddress.setError("Email is required!");
                    emailAddress.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailAddress.setError("Please enter a valid email");
                    emailAddress.requestFocus();
                    return;
                }
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(PasswordActivity.this,"Email sent successfully!",Toast.LENGTH_LONG).show();
                                    finish();
                                }else{
                                    Toast.makeText(PasswordActivity.this,"Failed to send an email! Please try again!",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
}