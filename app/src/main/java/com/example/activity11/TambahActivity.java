package com.example.activity11;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class TambahActivity extends AppCompatActivity {

    private TextView nama,email;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        nama = findViewById(R.id.nama);
        email = findViewById(R.id.txtEmail);

        progressDialog = new ProgressDialog(TambahActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Mengambil data...");

        Intent intent = getIntent();
        if(intent != null){
            nama.setText(intent.getStringExtra("name"));
            email.setText(intent.getStringExtra("email"));
        }


    }
}