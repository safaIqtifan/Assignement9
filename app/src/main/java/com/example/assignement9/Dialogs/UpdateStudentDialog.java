package com.example.assignement9.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Window;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.databaseapp.DB.StudentDB;
import com.example.databaseapp.DataCallBack;
import com.example.databaseapp.Model.StudentModel;
import com.example.databaseapp.R;
import com.example.databaseapp.databinding.DialogUpdateStudentBinding;

public class UpdateStudentDialog extends Dialog {

    Activity activity;
    Uri updatProfileImge;
    StudentDB dataAccess;

    DialogUpdateStudentBinding binding;

    public UpdateStudentDialog(Activity context, StudentModel studentModel, final DataCallBack okCall) {
        super(context);
        activity = context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding = DialogUpdateStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataAccess = new StudentDB(context);

        binding.tvUserName.setText(studentModel.getName());
        binding.edAvarg.setText(String.valueOf(studentModel.getAverage()));

        if (studentModel.getImg() != null)
            setPhotoUri(Uri.parse(studentModel.getImg()));

        binding.okBtn.setOnClickListener(view -> {

            String userNameStr = binding.tvUserName.getText().toString().trim();
            String avrgStr = binding.edAvarg.getText().toString().trim();

//            binding.updatProfileImg.setImageURI(studentModel.setImg(updatImge));

            // here check all fields that is not null on empty

            boolean hasError = false;
            if (userNameStr.isEmpty()) {
                binding.tvUserName.setError(activity.getString(R.string.invalid_input));
                hasError = true;
            }
            if (avrgStr.isEmpty()) {
                binding.edAvarg.setError(activity.getString(R.string.invalid_input));
                hasError = true;
            }
            if (updatProfileImge == null) {
                Toast.makeText(activity, activity.getString(R.string.please_select_photo), Toast.LENGTH_SHORT).show();
                hasError = true;
            }
            if (hasError)
                return;

            studentModel.setName(userNameStr);
            studentModel.setAverage(Double.parseDouble(avrgStr));
            studentModel.setImg(updatProfileImge.toString());

            dataAccess.updateStudent(studentModel.getId(), studentModel.getName(), studentModel.getAverage(), studentModel.getImg());

            dismiss();

            if (okCall != null) {
                okCall.Result(studentModel, "", null);
            }
        });

        binding.cancelBtn.setOnClickListener(view -> {

            dismiss();
        });

        try {
            if (activity != null && !activity.isFinishing())
                show();
        } catch (Exception e) {
            dismiss();
        }
    }

    public void setPhotoUri(Uri photoUri) {

        updatProfileImge = photoUri;

        Glide.with(activity)
                .asBitmap()
                .load(updatProfileImge)
                .into(binding.updatProfileImg);
    }

}