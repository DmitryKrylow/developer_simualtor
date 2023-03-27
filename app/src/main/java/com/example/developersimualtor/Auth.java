package com.example.developersimualtor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Auth extends FragmentActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText emailET;
    private EditText passET;
    private Button auth, reg;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            mAuth = FirebaseAuth.getInstance();

            emailET = findViewById(R.id.email);
            passET = findViewById(R.id.pass);
            auth = (Button) findViewById(R.id.auth);
            reg = (Button) findViewById(R.id.reg);

            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                }
            };

            auth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signin(emailET.getText().toString(), passET.getText().toString());
                }
            });
            reg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registration(emailET.getText().toString(), passET.getText().toString());
                }
            });
        }else{
            intent = new Intent(Auth.this, CreatePerson.class);
            Toast.makeText(Auth.this,"Здравствуйте, " + FirebaseAuth.getInstance().getCurrentUser().getEmail(),Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }
    }
    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    public void signin(String email , String password) {
        if(!email.equals("") && !password.equals("")) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Auth.this, "Вход успешнен", Toast.LENGTH_SHORT).show();
                        intent = new Intent(Auth.this, CreatePerson.class);
                        startActivity(intent);
                        finish();
                    } else
                        Toast.makeText(Auth.this, "Вход провален. Проверьте введенные данные", Toast.LENGTH_SHORT).show();

                }
            });
        }else{
            Toast.makeText(Auth.this,"Введенные поля не могут быть пустые",Toast.LENGTH_SHORT).show();
        }
    }
    public void registration (String email , String password) {
        if(!email.equals("") && !password.equals("")) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Auth.this, "Регистрация успешна, войдите", Toast.LENGTH_SHORT).show();
                        intent = new Intent(Auth.this, CreatePerson.class);
                    } else

                        Toast.makeText(Auth.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(Auth.this,"Введенные поля не могут быть пустые",Toast.LENGTH_SHORT).show();
        }
    }
}
