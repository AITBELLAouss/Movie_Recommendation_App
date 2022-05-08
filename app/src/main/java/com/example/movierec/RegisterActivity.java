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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {

    EditText inputUsername,inputEmail,inputPassword,inputConfirmepassword;
    Button btnRegister;
    TextView alreadyHaveAccount;
    FirebaseAuth mAuth;

    ProgressDialog mLoadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();
        mLoadingBar=new ProgressDialog(this);
        inputPassword=findViewById(R.id.inputPassword);
        inputEmail=findViewById(R.id.inputEmail);
        inputUsername=findViewById(R.id.inputUsername);
        inputConfirmepassword=findViewById(R.id.inputConfirmepassword);



        alreadyHaveAccount= findViewById(R.id.alreadyHaveAccount);
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginActivity);

            }
        });

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AtemptRegistration();
            }
        });

    }

    private void AtemptRegistration() {
        String username = inputUsername.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmepassword.getText().toString();

        if(username.isEmpty()){
            showError(inputUsername,"username required");
        }
        else if (email.isEmpty() || !email.contains("@gmail.com"))
        {
            showError(inputEmail,"Email is not Valid");
        }else if (password.isEmpty()||password.length()<5){
            showError(inputPassword,"password should be longer than 5 characters");
        }else if (!confirmPassword.equals(password)){
            showError(inputConfirmepassword,"password is not the same");
        }

        else
        {
            mLoadingBar.setTitle("REGISTRATION");
            mLoadingBar.setMessage("Please wait ...");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        User user = new User(username, email, password);

                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance()
                                .getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    mLoadingBar.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Registration is successful ", Toast.LENGTH_SHORT).show();
                                } else {
                                    mLoadingBar.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                    } else {
                        mLoadingBar.dismiss();
                        Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
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
