package com.example.awallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText Nama,Username,Password;
    Button Signup;
    private String TAG;
    String nama,username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Nama=findViewById(R.id.dftNama);
        Username = findViewById(R.id.dftUsername);
        Password = findViewById(R.id.dftPassword);

        Signup = findViewById(R.id.butSignUp);

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = Nama.getText().toString();
                username = Username.getText().toString();
                password = Password.getText().toString();

                Map<String,Object> mapdata = new HashMap<>();
                if (nama.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Register.this, "Isi Semua Data", Toast.LENGTH_SHORT).show();
                }else{
                    /*for Pemasukan*/
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Pemasukan").document(username).set(mapdata).
                            addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: "+username);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "Erorr Adding Document ",e);
                        }
                    });
                    /*for Pengeluaran*/
                    db.collection("Pengeluaran").document(username).set(mapdata).
                            addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "DocumentSnapshot added with ID: "+username);
                            }}).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "Erorr Adding Document ",e);
                        }
                    });
                    mapdata.put("Username",username);
                    mapdata.put("Nama",nama);
                    mapdata.put("Password",password);
                    /*for Users*/
                    db.collection("Users").document(username).set(mapdata).
                            addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: "+username);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "Erorr Adding Document ",e);
                        }
                    });
                }
                Bundle bundle = new Bundle();
                Intent i = new Intent(getApplicationContext(),Dashboard.class);
                bundle.putString("email",username);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }
}