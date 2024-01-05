package com.example.recyclepro.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.recyclepro.R;
import com.example.recyclepro.callback.AuthCallBack;
import com.example.recyclepro.utils.Database;
import com.example.recyclepro.utils.User;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

public class SignupActivity extends AppCompatActivity {
    private TextInputLayout signup_TF_firstName;
    private TextInputLayout signup_TF_lastName;
    private TextInputLayout signup_TF_email;
    private TextInputLayout signup_TF_password;
    private TextInputLayout signup_TF_confirmPassword;
    private Button signup_BTN_signup;
    private ProgressBar signup_PB_loading;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViews();
        initVars();
    }

    private void initVars() {
        database = new Database();
        database.setAuthCallBack(new AuthCallBack() {
            @Override
            public void onLoginComplete(Task<AuthResult> task) {

            }

            @Override
            public void onCreateAccountComplete(boolean status, String err) {
                signup_PB_loading.setVisibility(View.INVISIBLE);
                if(status){
                    Toast.makeText(SignupActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    database.logout();
                    finish();
                }else{
                    Toast.makeText(SignupActivity.this, err, Toast.LENGTH_SHORT).show();
                }

            }
        });
        signup_BTN_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkInput()) return;
                signup_PB_loading.setVisibility(View.VISIBLE);
                User user = new User()
                        .setEmail(signup_TF_email.getEditText().getText().toString())
                        .setFirstname(signup_TF_firstName.getEditText().getText().toString())
                        .setLastname(signup_TF_lastName.getEditText().getText().toString());
                String password = signup_TF_password.getEditText().getText().toString();
                database.createAccount(user.getEmail(), password, user);
            }
        });
    }

    private boolean checkInput() {
        User user = new User()
                .setEmail(signup_TF_email.getEditText().getText().toString())
                .setFirstname(signup_TF_firstName.getEditText().getText().toString())
                .setLastname(signup_TF_lastName.getEditText().getText().toString());

        String password = signup_TF_password.getEditText().getText().toString();
        String confirmPassword = signup_TF_confirmPassword.getEditText().getText().toString();

        if(!user.isValid()) {
            Toast.makeText(SignupActivity.this, "Please fill all user info!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(password.length() < 6){
            Toast.makeText(SignupActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!password.equals(confirmPassword)){
            Toast.makeText(SignupActivity.this, "mismatch between password and confirm password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void findViews() {
        signup_TF_firstName = findViewById(R.id.signup_TF_firstName);
        signup_TF_lastName = findViewById(R.id.signup_TF_lastName);
        signup_TF_email = findViewById(R.id.signup_TF_email);
        signup_TF_password = findViewById(R.id.signup_TF_password);
        signup_TF_confirmPassword = findViewById(R.id.signup_TF_confirmPassword);
        signup_BTN_signup = findViewById(R.id.signup_BTN_signup);
        signup_PB_loading = findViewById(R.id.signup_PB_loading);
    }
}