package com.example.awallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dashboard extends AppCompatActivity {

    /*Declare widget*/
    private ProgressDialog progressDialog;
    TextView tvWelcome,tvBudget;
    ImageButton addData;
    String TAG = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvWelcome = findViewById(R.id.welcomeWord);
        tvBudget = findViewById(R.id.tvBudget);
        addData = findViewById(R.id.addDataButton);


        /*Bundle from previous page*/
        Bundle bundle = getIntent().getExtras();
        String Emaillogin = bundle.getString("email");
        /*Connect with database*/
        DocumentReference documentReference = db.collection("Users").document(Emaillogin);
        /*Setting Account Information*/
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    /*get Nama from db using username data*/
                    String Nama = document.getString("Nama");
                    Log.d(TAG, "Getting Document Successfull");
                    tvWelcome.setText("Hello, " + Nama);
                } else {
                    Log.d(TAG, "Error getting documents. " + task.getException());
                }
            }
        });
        /*onCreate*/
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("newdata",Emaillogin);
                Intent i = new Intent(getApplicationContext(),AddData.class);
                i.putExtras(b);
                startActivity(i);

            }
        });




    }





    /*function*/
    public class Data{
        private List<String> Pemasukan;
        private List<String> Pengeluaran;
        public Data(){

        }

        public List<String> getPemasukan() {
            return Pemasukan;
        }

        public List<String> getPengeluaran() {
            return Pengeluaran;
        }

        public void setPemasukan(List<String> pemasukan) {
            Pemasukan = pemasukan;
        }

        public void setPengeluaran(List<String> pengeluaran) {
            Pengeluaran = pengeluaran;
        }
    }
}