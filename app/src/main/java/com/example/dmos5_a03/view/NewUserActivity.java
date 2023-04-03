package com.example.dmos5_a03.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dmos5_a03.R;
import com.example.dmos5_a03.model.User;
import com.example.dmos5_a03.model.UserArray;

public class NewUserActivity extends AppCompatActivity{
    private String mUsername;
    private String mPassword;
    private String mConfirmPassword;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private Button mSaveButton;
    private Button mCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        setTitle("Novo Usuário");

        intentHandler();
    }

    private void intentHandler(){
        // Cadastro de um novo usuário
        registrationHandler();

        // Cancel ClickListener (retorna à tela inicial)
        cancel();
    }

    private void registrationHandler(){
        mUsernameEditText = findViewById(R.id.editTextNewUsername);
        mPasswordEditText = findViewById(R.id.editTextNewPassword);
        mConfirmPasswordEditText = findViewById(R.id.editTextConfirmPassword);
        mSaveButton = findViewById(R.id.btnSaveUser);
        mCancelButton = findViewById(R.id.btnCancel);

        mSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(validateInput() && validateUsername() && validatePassword()){
                    // Salva as informações do usuário no array de usuários
                    saveUserData();
                }
            }
        });
    }

    private boolean validateInput(){
        mUsername = mUsernameEditText.getText().toString().trim();
        mPassword = mPasswordEditText.getText().toString().trim();
        mConfirmPassword = mConfirmPasswordEditText.getText().toString().trim();

        if(TextUtils.isEmpty(mUsername)){
            mUsernameEditText.setError("Insira seu username");
            return false;
        }

        if(TextUtils.isEmpty(mPassword)){
            mPasswordEditText.setError("Insira sua senha");
            return false;
        }

        if(TextUtils.isEmpty(mConfirmPassword)){
            mConfirmPasswordEditText.setError("Confirme sua senha");
            return false;
        }

        return true;
    }

    private boolean validateUsername(){
        for(User user : UserArray.users){
            if (user.getUsername().equals(mUsername)){
                mUsernameEditText.setError("Username já cadastrado");
                return false;
            }
        }
        return true;
    }

    private boolean validatePassword(){
        if(!mPassword.equals(mConfirmPassword)){
            mConfirmPasswordEditText.setError("As senhas não coincidem");
            return false;
        }
        return true;
    }

    private void saveUserData(){
        User newUser = new User(mUsername, mPassword);
        UserArray.users.add(newUser);
        Toast.makeText(this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();

        // Retorna à pagina principal após o cadastro de um novo usuário
        Intent intent = new Intent(NewUserActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void cancel(){
        mCancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(NewUserActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
