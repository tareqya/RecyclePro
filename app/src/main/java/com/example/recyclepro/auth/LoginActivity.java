package com.example.recyclepro.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.recyclepro.R;
import com.example.recyclepro.callback.AuthCallBack;
import com.example.recyclepro.home.HomeActivity;
import com.example.recyclepro.utils.Database;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout login_TF_email;
    private TextInputLayout login_TF_password;
    private Button login_BTN_login;
    private ProgressBar login_PB_loading;
    private Button login_BTN_signup;

    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        initVars();
    }

    private void findViews() {
        login_TF_email = findViewById(R.id.login_TF_email);
        login_TF_password = findViewById(R.id.login_TF_password);
        login_BTN_login = findViewById(R.id.login_BTN_login);
        login_PB_loading = findViewById(R.id.login_PB_loading);
        login_BTN_signup = findViewById(R.id.login_BTN_signup);

    }

    private void initVars() {
        database = new Database();
        database.setAuthCallBack(new AuthCallBack() {
            @Override
            public void onLoginComplete(Task<AuthResult> task) {
                login_PB_loading.setVisibility(View.INVISIBLE);
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(LoginActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCreateAccountComplete(boolean status, String err) {

            }
        });

        login_BTN_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        login_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_TF_email.getEditText().getText().toString();
                String password = login_TF_password.getEditText().getText().toString();
                login_PB_loading.setVisibility(View.VISIBLE);
                database.loginUser(email, password);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Database db = new Database();
        if(db.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }
    }
}