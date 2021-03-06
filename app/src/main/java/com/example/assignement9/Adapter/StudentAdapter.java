package com.example.assignement9.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.databaseapp.DB.StudentDB;
import com.example.databaseapp.DataCallBack;
import com.example.databaseapp.Model.StudentModel;
import com.example.databaseapp.R;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    Activity context;
    public ArrayList<StudentModel> dataList;
    DataCallBack dataCallBack;

    public StudentAdapter(Activity context, ArrayList<StudentModel> data, DataCallBack callBack) {
        this.context = context;
        this.dataList = data;
        this.dataCallBack = callBack;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.student_list_item, parent, false);

        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        StudentModel studentModel = dataList.get(position);

        holder.idTv.setText(studentModel.getId() + ". ");
        holder.tvName.setText(String.valueOf(studentModel.getName()));
        holder.avarg.setText(studentModel.getAverage()+": ");

        Glide.with(context).asBitmap().load(studentModel.getImg()).placeholder(R.drawable.profile).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView idTv;
        TextView tvName;
        TextView avarg;
        ImageView img;
        Button okBtn;
        Button cancelBtn;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            idTv = itemView.findViewById(R.id.idTv);
            tvName = itemView.findViewById(R.id.tvTitle);
            avarg = itemView.findViewById(R.id.userAvarg);
            img = itemView.findViewById(R.id.profileImg);
            cardView = itemView.findViewById(R.id.cardItem);
            okBtn = itemView.findViewById(R.id.okBtn);
            cancelBtn = itemView.findViewById(R.id.cancelBtn);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StudentModel studentModel = dataList.get(getAdapterPosition());
                    dataCallBack.Result(studentModel, "", getAdapterPosition());
                }
            });

            cardView.setOnLongClickListener(v -> {

                StudentModel studentModel = dataList.get(getAdapterPosition());

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                StudentDB dataAccess = new StudentDB(context);
                builder.setTitle("Delete Student");
                builder.setMessage("Are you sure to delete delete");

                builder.setPositiveButton("Yes", (dialog, which) -> {
                    int position = getAdapterPosition();
                    dataAccess.deleteStudent(studentModel.getId());
                    dataList.remove(position);
                    notifyItemRemoved(position);

                });

                builder.setNegativeButton("No", (dialog, which) -> {

                });

                builder.create().show();

                return false;
            });

        }
    }

}
