package com.example.dmos5_a03.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setClickListeners();
    }

    private void setClickListeners(){
        // Login
        Button loginButton = findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(new LoginHandler());

        // Cadastro
        Button newUserButton = findViewById(R.id.btnNewUser);
        newUserButton.setOnClickListener(new RegistrationHandler());
    }

    private static class LoginHandler implements View.OnClickListener{
        private EditText mUsernameEditText;
        private EditText mPasswordEditText;

        @Override
        public void onClick(View v){
            mUsernameEditText = ((Activity) v.getContext()).findViewById(R.id.editTextUsername);
            mPasswordEditText = ((Activity) v.getContext()).findViewById(R.id.editTextPassword);

            String username = mUsernameEditText.getText().toString().trim();
            String password = mPasswordEditText.getText().toString().trim();

            if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
                // Inicia ContactsActivity e envia os dados de login
                Intent intent = new Intent(v.getContext(), ContactsActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("senha", password);
                if(isValidUser(username, password)){
                    // Login válido
                    v.getContext().startActivity(intent);
                }else{
                    // Login inválido
                    Toast.makeText(v.getContext(), "Username e/ou senha inválidos", Toast.LENGTH_SHORT).show();
                }
            }else{
                // Campos obrigatórios não preenchidos
                Toast.makeText(v.getContext(), "Para fazer login, preencha os campos de username e senha", Toast.LENGTH_SHORT).show();
            }
        }

        private boolean isValidUser(String username, String password){
            // Valida se as informações de login são compatíveis com os dados de um usuário registrado
            for(User user : UserArray.users){
                if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                    return true;
                }
            }
            return false;
        }
    }

    private static class RegistrationHandler implements View.OnClickListener{
        @Override
        public void onClick(View v){
            // Inicia NewUserActivity
            Intent intent = new Intent(v.getContext(), NewUserActivity.class);
            v.getContext().startActivity(intent);
        }
    }
}
