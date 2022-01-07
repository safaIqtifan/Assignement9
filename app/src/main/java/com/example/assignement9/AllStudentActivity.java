package com.example.assignement9;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class AllStudentActivity extends AppCompatActivity {

    EditText tvUserName, tvAvarg;
    Button addbtn, getbtn, clerbtn;
    ImageView profileImag;

    FirebaseFirestore db;
    Uri userPhotoUri;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_student);

        db = FirebaseFirestore.getInstance();

        tvUserName = findViewById(R.id.tvUserName);
        tvAvarg = findViewById(R.id.edAvarg);
        addbtn = findViewById(R.id.addBtn);
        getbtn = findViewById(R.id.getBtn);
        clerbtn = findViewById(R.id.clerBtn);
        profileImag = findViewById(R.id.profileImag);

        addbtn.setOnClickListener(v -> {

            checkData();
        });

        getbtn.setOnClickListener(v -> {
            Intent i = new Intent(this, AllStudentActivity.class);
            startActivity(i);
        });

        clerbtn.setOnClickListener(v -> {
//            dataAccess.DeletAllData();
        });

        profileImag.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {
                    String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permission, PERMISSION_CODE);
                } else {
                    pickImageFromGallery();
                }
            } else {
                pickImageFromGallery();
            }
        });
    }

    private void checkData() {

        String name = tvUserName.getText().toString();
        String avarg = tvAvarg.getText().toString();

        boolean hasError = false;
        if (name.isEmpty()) {
            tvUserName.setError(this.getString(R.string.invalid_input));
            hasError = true;
        }
        if (avarg.isEmpty()) {
            tvAvarg.setError(this.getString(R.string.invalid_input));
            hasError = true;
        }
        if (userPhotoUri == null) {
            hasError = true;
        }
        if (hasError) {
            Toast.makeText(this, getString(R.string.please_fill_data), Toast.LENGTH_SHORT).show();
            return;
        }

//        if(dataAccess.insertStudent(name, Double.parseDouble(avarg), String.valueOf(userPhotoUri))){
//            Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
//            tvUserName.setText("");
//            tvAvarg.setText("");
//            profileImag.setImageResource(R.drawable.profile);
//
//        }else {
//            Toast.makeText(getApplicationContext(), "Add Failed", Toast.LENGTH_SHORT).show();
//        }
    }

    private void pickImageFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {

            userPhotoUri = data.getData();
            profileImag.setImageURI(userPhotoUri);
        }
    }
}