package com.example.awallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorJoiner;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dashboard extends AppCompatActivity {

    /*Declare widget*/
    private ProgressDialog progressDialog;
    TextView tvWelcome, tvBudget;
    ImageButton addData;
    String TAG = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    RecyclerView recyclerView;

    ArrayList<Model> modelArrayList;

    Adapter adapter;


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


        /*loaddata*/
        recyclerView = findViewById(R.id.recycView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        /*Connect with database*/
        DocumentReference documentReference = db.collection("Users").document(Emaillogin);
        modelArrayList = new ArrayList<>();;
        adapter = new Adapter(Dashboard.this, modelArrayList);
        recyclerView.setAdapter(adapter);
        onLoad();

        adapter.setDialog(new Adapter.Dialog() {
            @Override
            public void onClick(int pos) {
                CharSequence[] dialogItem = {"Edit","Hapus"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(Dashboard.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                DocumentReference documentReference = db.collection("Users").document(Emaillogin);
                                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        Intent intent = new Intent(getApplicationContext(),UpdateData.class);
                                        DocumentSnapshot document = task.getResult();
                                        Bundle updatebundle = new Bundle();
                                        updatebundle.putString("namapemasukan",document.getString("NamaPemasukan"));
                                        updatebundle.putString("nominalpemasukan",document.getString("NominalPemasukan"));
                                        updatebundle.putString("emailupdate",Emaillogin);
                                        intent.putExtras(updatebundle);
                                        startActivity(intent);
                                    }
                                });
                                /*sending data*/
                                break;
                            case 1:
                                DocumentReference docRef = db.collection("Users").document(Emaillogin);
                                Map<String,Object> delete = new HashMap<>();
                                delete.put("NamaPemasukan", FieldValue.delete());
                                delete.put("NonimalPemasukan", FieldValue.delete());
                                docRef.update(delete);
                                adapter.modelArrayList.clear();
                                adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });

        /*onCreate*/
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("newdata", Emaillogin);
                Intent i = new Intent(getApplicationContext(), AddData.class);
                i.putExtras(b);
                startActivity(i);

            }
        });




        /*Setting Account Information*/
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    /*get Nama from db using username data*/
                    String Nama = document.getString("Nama");
                    String Budget = document.getString("NominalPemasukan");
                    Log.d(TAG, "Getting Document Successfull");
                    tvWelcome.setText("Hello, " + Nama);
                    tvBudget.setText("Rp. "+Budget);

                } else {
                    Log.d(TAG, "Error getting documents. " + task.getException());
                }
            }
        });
    }

    private void onLoad() {
        db.collection("Users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.e("Eror get database, ",error.getMessage());
                        }
                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                modelArrayList.add(dc.getDocument().toObject(Model.class));
                            }

                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}