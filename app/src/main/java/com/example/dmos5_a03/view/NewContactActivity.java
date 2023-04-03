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
import com.example.dmos5_a03.model.Contact;
import com.example.dmos5_a03.model.User;
import com.example.dmos5_a03.model.UserArray;

public class NewContactActivity extends AppCompatActivity{
    private String mUsername;
    private String mPassword;
    private String mNickname;
    private String mName;
    private String mTel;
    private EditText mNicknameEditText;
    private EditText mNameEditText;
    private EditText mPhoneEditText;
    private Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        setTitle("Novo Contato");

        contactHandler();
    }

    private void contactHandler(){
        mNicknameEditText = findViewById(R.id.editTextNickname);
        mNameEditText = findViewById(R.id.editTextName);
        mPhoneEditText = findViewById(R.id.editTextPhone);
        mSaveButton = findViewById(R.id.btnSaveContact);

        mSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(validateInput()){
                    // Salva as informações do contato no array de contatos do usuário
                    saveContact();
                }
            }
        });
    }

    private boolean validateInput(){
        mNickname = mNicknameEditText.getText().toString().trim();
        mName = mNameEditText.getText().toString().trim();
        mTel = mPhoneEditText.getText().toString().trim();

        if(TextUtils.isEmpty(mNickname)){
            mNicknameEditText.setError("Insira um apelido para o contato");
            return false;
        }

        if(TextUtils.isEmpty(mName)){
            mNameEditText.setError("Informe o nome do contato");
            return false;
        }

        if(TextUtils.isEmpty(mTel)){
            mPhoneEditText.setError("Informe o telefone do contato");
            return false;
        }

        return true;
    }

    private void saveContact(){
        Contact newContact = new Contact(mNickname, mName, mTel);
        User currentUser = getCurrentUser();

        currentUser.addContact(newContact);
        Toast.makeText(this, "Contato registrado", Toast.LENGTH_SHORT).show();

        // Retorna à pagina do usuário após o cadastro de um novo contato
        Intent intent = new Intent(NewContactActivity.this, ContactsActivity.class);
        intent.putExtra("username", mUsername);
        intent.putExtra("password", mPassword);
        startActivity(intent);

        finish();
    }

    private User getCurrentUser(){
        Intent intent = getIntent();
        mUsername = intent.getStringExtra("username");
        mPassword = intent.getStringExtra("password");
        for(User user : UserArray.users){
            if(user.getUsername().equals(mUsername)){
                return user;
            }
        }
        return null;
    }
}
