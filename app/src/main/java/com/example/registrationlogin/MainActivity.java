package com.example.registrationlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText email, pass;
    Button button;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.editTextTextEmailAddress);
        pass = findViewById(R.id.editTextNumberPassword);
        button = findViewById(R.id.button2);
        auth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String password = pass.getText().toString();
                if(TextUtils.isEmpty(Email)){
                    email.setError("Email is required.");
                    email.requestFocus();
                }
                else if(TextUtils.isEmpty(password)){
                    pass.setError("Password is required.");
                    pass.requestFocus();
                }
                else if(password.length()<6)
                {
                    pass.setError("Password must be 6 digits");
                    pass.requestFocus();
                }
                else {
                    auth.createUserWithEmailAndPassword(Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "User successfully added", Toast.LENGTH_SHORT).show();
                                email.setText("");
                                pass.setText("");
                            } else {
                                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    public void onClickLogin(View view){
        Intent intent = new Intent(MainActivity.this, LoginPage.class);
        startActivity(intent);
    }
}