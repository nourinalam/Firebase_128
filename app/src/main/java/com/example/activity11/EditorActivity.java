package com.example.activity11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditorActivity extends AppCompatActivity {

    private EditText edtName, editEmail;
    private Button btnSave;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        edtName = findViewById(R.id.name);
        editEmail = findViewById(R.id.email);
        btnSave = findViewById(R.id.btn_save);

        progressDialog = new ProgressDialog(EditorActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Menyimpan...");

        btnSave.setOnClickListener(v->{
            if (edtName.getText().length() > 0 && editEmail.getText().length() > 0){
                saveData(edtName.getText().toString(), editEmail.getText().toString());
            }else {
                Toast.makeText(this, "Silahkan isi semua data", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        if (intent!= null){
            id = intent.getStringExtra("id");
            edtName.setText(intent.getStringExtra("name"));
            editEmail.setText(intent.getStringExtra("email"));
        }
    }

    private void saveData(String name, String email) {
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);

        progressDialog.show();
        if (id != null){

            db.collection("users").document(id)
                    .set(user)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(EditorActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(EditorActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else {

            db.collection("users")
                    .add(user)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(EditorActivity.this, "Berhasil di simpan", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditorActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }
    }
}