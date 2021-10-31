package com.example.test1.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test1.R;
import com.example.test1.UserDetailActivity;
import com.example.test1.models.Users;

import java.io.File;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    List<String> reportList;
    Context context;
    File file;
    ImageView imgReport;

    public ReportAdapter(List<String> reportList, Context context) {
        this.reportList = reportList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_interest, parent, false);
        ReportAdapter.ViewHolder viewHolder = new ReportAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ViewHolder holder, int position) {
        holder.ckbSoThich.setText(reportList.get(position));


        holder.ckbSoThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                hamDialog(dialog,R.layout.dialog_support);

                TextView tv1 = dialog.findViewById(R.id.tv1);
                Button btnAcceptReport = dialog.findViewById(R.id.btnSendSupport);
                Button btnCancelReport = dialog.findViewById(R.id.btnCancelSupport);
                LinearLayout btnReport = dialog.findViewById(R.id.btnReport);
                imgReport = dialog.findViewById(R.id.imgReport);

                tv1.setText(reportList.get(position));

                btnReport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        ((Activity) context).startActivityForResult(intent, 1);
                    }
                });

                btnAcceptReport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "Báo cáo của bạn đã được gửi", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                btnCancelReport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return reportList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox ckbSoThich;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ckbSoThich = itemView.findViewById(R.id.ckbSoThich);
        }
    }


    public void hamDialog(Dialog dialog, int giaodien){
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(giaodien);
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttri = window.getAttributes();
        windowAttri.gravity = Gravity.CENTER;
        window.setAttributes(windowAttri);
        dialog.setCancelable(true);

    }

    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri = data.getData();
        file = new File(getRealPathFromURI(uri));
        imgReport.setVisibility(View.VISIBLE);
        Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        imgReport.setVisibility(View.VISIBLE);
        imgReport.setImageBitmap(myBitmap);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
