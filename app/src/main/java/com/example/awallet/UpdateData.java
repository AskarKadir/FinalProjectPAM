package com.example.awallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateData extends AppCompatActivity {

    EditText updatenama,updatenominal;
    TextView updateketerangan;
    Button Simpan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        updatenama = findViewById(R.id.updateNama);
        updatenominal = findViewById(R.id.updateNominal);
        updateketerangan = findViewById(R.id.tvupdateKeterangan);

        Simpan = findViewById(R.id.buttonsimpanupdate);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Bundle bundleget = getIntent().getExtras();
        updatenama.setText(bundleget.getString("namapemasukan"));
        updatenominal.setText(bundleget.getString("nominalpemasukan"));
        updateketerangan.setText("Pemasukan");

        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> updatedata = new HashMap<>();
                String nominal,nama;

                nominal = updatenominal.getText().toString();
                nama = updatenama.getText().toString();

                updatedata.put("NamaPemasukan",nama);
                updatedata.put("NominalPemasukan",nominal);
                db.collection("Users").document(bundleget.getString("emailupdate"))
                        .update(updatedata);
                Intent intent  = new Intent(UpdateData.this,Dashboard.class);
                startActivity(intent);
            }
        });
    }
}