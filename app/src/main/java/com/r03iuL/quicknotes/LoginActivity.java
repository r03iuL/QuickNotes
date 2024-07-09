package com.r03iuL.quicknotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button loginButton;
    ProgressBar progressbar;
    TextView createAccountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email_text);
        passwordEditText = findViewById(R.id.password_text);
        loginButton = findViewById(R.id.login_btn);
        createAccountTextView = findViewById(R.id.create_account_btn);
        progressbar = findViewById(R.id.progress_bar);

        loginButton.setOnClickListener((v)-> loginUser() );
        createAccountTextView.setOnClickListener((v)-> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));
    }

    void loginUser(){

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        boolean isValid = dataValidate(email, password);

        if (!isValid) {
            return;
        }

        loginFirbaseAccount(email, password);
    }

    void loginFirbaseAccount(String email, String password){
        progress(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progress(false);
                        if (task.isSuccessful()) {
                            // Successfully created account
                            if (firebaseAuth.getCurrentUser().isEmailVerified()){
                                //got to main activity
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Email not verified!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        else{
                            Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    void progress(boolean inProgress) {
        if (inProgress) {
            progressbar.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.GONE);
        } else {
            progressbar.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);
        }
    }

    boolean dataValidate(String email, String password) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Invalid email address!");
            return false;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters!");
            return false;
        }


        return true;
    }
}