package com.example.movierec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    EditText inputEmail,inputPassword;
    TextView forgotPassword,signUp;
    FirebaseAuth mAuth;
    ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth= FirebaseAuth.getInstance();
        mLoadingBar=new ProgressDialog(this);

        loginBtn = findViewById(R.id.loginBtn);
        inputPassword=findViewById(R.id.inputPassword);
        inputEmail=findViewById(R.id.inputEmail);
        forgotPassword=findViewById(R.id.forgotPassword);
        signUp=findViewById(R.id.signUp);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });





        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttemptLogin();
            }
        });

    }

    private void AttemptLogin() {

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();


        if (email.isEmpty() || !email.contains("@gmail.com"))
        {
            showError(inputEmail,"Email is not Valid");
        }else if (password.isEmpty()||password.length()<5){
            showError(inputPassword,"password should be longer than 5 characters");
        }
        else
        {
            mLoadingBar.setTitle("Login in");
            mLoadingBar.setMessage("Please wait ...");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){
                        mLoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Login is successful", Toast.LENGTH_SHORT).show();
                        Intent mainActivity = new Intent(LoginActivity.this,MainActivity.class);
                        mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(mainActivity);
                        finish();
                    }else {
                        mLoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }
    private void showError(EditText field, String text) {
        field.setError(text);
        field.requestFocus();
    }
}