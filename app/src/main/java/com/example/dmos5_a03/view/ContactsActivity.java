package com.example.dmos5_a03.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dmos5_a03.R;
import com.example.dmos5_a03.model.Contact;
import com.example.dmos5_a03.model.User;
import com.example.dmos5_a03.model.UserArray;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity{
    private String mUsername;
    private String mPassword;
    private Spinner mContactsSpinner;
    private TextView mContactNameTextView;
    private TextView mContactTelTextView;
    private List<String> mContactsList;
    private ArrayAdapter<String> mContactsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        setTitle("Página do Usuário");

        intentHandler();
    }

    private void intentHandler(){
        // Recupera os dados de login
        userSetup();

        // Novo Contato ClickListener
        newContact();

        // Logout ClickListener
        logout();
    }

    private void userSetup(){
        Intent intent = getIntent();
        mUsername = intent.getStringExtra("username");
        mPassword = intent.getStringExtra("password");

        // Recupera os dados do usuário
        getUserData();

        // População dos contatos
        contactSetup();
    }

    private void getUserData(){
        for(User user : UserArray.users){
            if(user.getUsername().equals(mUsername) && user.getPassword().equals(mPassword)){
                List<Contact> contacts = user.getContacts();
                mContactsList.clear();
                for(Contact contact : contacts){
                    mContactsList.add(contact.getName());
                }
                mContactsAdapter.notifyDataSetChanged(); // Notifica o ArrayAdapter sobre a atualização dos dados de exibição
                break;
            }
        }
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

    private void contactSetup(){
        mContactsSpinner = findViewById(R.id.contactsSpinner);
        mContactsList = new ArrayList<>();
        mContactsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mContactsList);
        mContactsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mContactsSpinner.setAdapter(mContactsAdapter);

        User currentUser = getCurrentUser();
        ArrayList<Contact> contacts = currentUser.getContacts();

        String[] contactsArray = new String[contacts.size()];
        for(int i = 0; i < contacts.size(); i++){
            Contact contact = contacts.get(i);
            String contactString = contact.getNickname();
            contactsArray[i] = contactString;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, contactsArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mContactsSpinner.setAdapter(adapter);

        mContactNameTextView = findViewById(R.id.TextViewContactName);
        mContactTelTextView = findViewById(R.id.TextViewContactTel);

        mContactsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                if(position >= 0){
                    Contact selectedContact = contacts.get(position);
                    mContactNameTextView.setText(selectedContact.getName());
                    mContactTelTextView.setText(selectedContact.getTel());
                }else{
                    mContactNameTextView.setText("");
                    mContactTelTextView.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
                mContactNameTextView.setText("");
                mContactTelTextView.setText("");
            }
        });
    }

    public void newContact(){
        Button newContactButton = findViewById(R.id.btnNewContact);
        newContactButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ContactsActivity.this, NewContactActivity.class);
                intent.putExtra("username", mUsername);
                intent.putExtra("password", mPassword);
                startActivity(intent);
            }
        });
    }

    private void logout(){
        Button logoutButton = findViewById(R.id.btnLogout);
        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ContactsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
