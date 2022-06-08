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
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {
    TextView email, pass;
    Button login;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        email = findViewById(R.id.editTextTextEmailAddress2);
        pass = findViewById(R.id.editTextNumberPassword2);
        login = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String password = pass.getText().toString().trim();
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
                else
                {
                    mAuth.signInWithEmailAndPassword(Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginPage.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                Toast.makeText(LoginPage.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    public void updateUI(FirebaseUser currentUser){
        Intent profileIntent = new Intent(LoginPage.this, ProfilePage.class);
        profileIntent.putExtra("Email", currentUser.getEmail());
        startActivity(profileIntent);
    }
    public void onClickRegister(View view)
    {
        Intent intent = new Intent(LoginPage.this, MainActivity.class);
        startActivity(intent);
    }
}