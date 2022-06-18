package com.example.awallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.graph.AbstractGraph;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.Date;

import java.sql.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddData extends AppCompatActivity {

    Button bPemasukan, bPengeluaran, bSave;
    TextView Jenis;
    EditText inNominal, inNama;
    private String TAG;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        Bundle bundle = getIntent().getExtras();
        String dataakun = bundle.getString("newdata");

        /*find*/
        bPemasukan = findViewById(R.id.butPemasukan);
        bPengeluaran = findViewById(R.id.butPengeluaran);
        bSave = findViewById(R.id.butSimpan);
        Jenis = findViewById(R.id.tvJenis);
        inNominal = findViewById(R.id.edNominal);
        inNama = findViewById(R.id.edNama);





        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Jenis.getText().toString().equals("Silahkan Pilih Data")){
                    Map<String, Object> addDatanew = new HashMap<>();
                    addDatanew.put("Nominal", Arrays.asList(inNominal.getText().toString()));
                    addDatanew.put("Nama", Arrays.asList(inNama.getText().toString()));
                    System.out.println(dataakun);
                    db.collection(Jenis.getText().toString()).document(dataakun).set(addDatanew)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "Data Berhasil Ditambahkan");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "Data Gagal Ditambahkan");
                        }
                    });
                }else{
                    Toast.makeText(AddData.this, "Isi Data Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });


        bPemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Jenis.setText("Pemasukan");
            }
        });

        bPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Jenis.setText("Pengeluaran");
            }
        });



    }
}