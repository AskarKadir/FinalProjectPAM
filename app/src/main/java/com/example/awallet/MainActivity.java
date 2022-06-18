package com.example.awallet;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.List;

/*Login with email and pass.
* using checkbox to login
* using swtich for showing regist page
* using firabase to get account data */
public class MainActivity extends AppCompatActivity {

    CheckBox CBLogin;
    Switch SPage;
    String Email, Password;
    TextInputLayout tilEmail, tilPassword;
    public static EditText edEmail,edPassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Widget findId*/
        tilEmail = findViewById(R.id.usernameLayout);
        tilPassword = findViewById(R.id.passwordLayout);
        CBLogin = findViewById(R.id.loginCheckbox);
        edEmail = findViewById(R.id.usernameInput);
        edPassword = findViewById(R.id.passwordInput);
        Email = edEmail.getText().toString();
        Password = edPassword.getText().toString();


        /*Set Checkbox on Start to disable*/
        CBLogin.setEnabled(false);
        CBLogin.setText("Masukkan Email dan Password");
        /*Checkbox on change*/
        CBLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    loginwithdatabase();
                }
            }
        });


        /*editText OnTyping*/
        edEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            /*After input, need validation email and password to login.*/
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    /*set checkbox to enable after typing*/
                    CBLogin.setEnabled(true);
                    CBLogin.setText("Term and Conditions");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /*regist page*/
        SPage = findViewById(R.id.pageSwitch);
        SPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchpage();
            }
        });


    }


    /*method*/
    public void loginpage() {
        Intent loginpage = new Intent(MainActivity.this, Dashboard.class);
        startActivity(loginpage);
    }

    private void switchpage() {
        Intent page = new Intent(getApplicationContext(), Register.class);
        startActivity(page);
        SPage.setChecked(false);
    }

    private void loginwithdatabase() {
        /*testing document*/
        String test = "6WAJlkclygA6oflpTKGr";
        /*initiate cloude firestore*/
        /*get string from users input*/
        Email = edEmail.getText().toString();
        Password = edPassword.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        /*spesific document*/
        /*document will be replaced with user.getEmail*/
        DocumentReference documentReference = db.collection("Users").document(Email);
        documentReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            /*fetch email and password data from db*/
                            String dbemail,dbpassword,dbusername;
                            dbemail = document.getString("Email");
                            dbpassword = document.getString("Password");
                            dbusername = document.getString("Username");
                            /*Email will be replaced with user.getemail and password*/
                            if (Email.equals(dbemail) || Email.equals(dbusername)&& Password.equals(dbpassword)) {
                                /*passing email data to next page*/
                                Bundle bundle = new Bundle();

                                /*using test variabel to test data collection*/
                                bundle.putString("email",Email);

                                /*Intent to next page*/
                                Intent loginpage = new Intent(MainActivity.this, Dashboard.class);
                                loginpage.putExtras(bundle);
                                startActivity(loginpage);
                            } else {
                                /*set input email and password to default*/
                                edEmail.setText("");
                                edPassword.setText("");
                                /*set checkbox to default*/
                                CBLogin.setChecked(false);
                                Toast.makeText(MainActivity.this, "Email or Password Not Valid", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            progressDialog.setMessage("Error getting documents. " + task.getException());
                        }
                    }
                });
    }
}