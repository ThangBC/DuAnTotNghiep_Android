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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test1.HomeActivity;
import com.example.test1.R;
import com.example.test1.models.Reports;
import com.example.test1.networking.FunctionReportFAN;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    List<String> reportList;
    Context context;
    File file;
    ImageView imgReport;
    String mail;

    public ReportAdapter(List<String> reportList, Context context, String mail) {
        this.reportList = reportList;
        this.context = context;
        this.mail = mail;
    }

    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
                hamDialog(dialog, R.layout.dialog_support);

                TextView tv1 = dialog.findViewById(R.id.tv1);
                Button btnAcceptReport = dialog.findViewById(R.id.btnSendSupport);
                Button btnCancelReport = dialog.findViewById(R.id.btnCancelSupport);
                TextInputLayout txtContentSp = dialog.findViewById(R.id.txtContentSp);
                LinearLayout btnReport = dialog.findViewById(R.id.btnReport);
                imgReport = dialog.findViewById(R.id.imgReport);

                txtContentSp.setHint("Nhập nội dung");

                tv1.setText(reportList.get(position));

                btnReport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txtContentSp.getEditText().getText().toString().length() < 10
                                || txtContentSp.getEditText().getText().toString().length() > 200) {
                            txtContentSp.setError("Vui lòng nhập nội dung tối thiểu 10 ký tự và tối đa 200 ký tự");
                        } else {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            ((Activity) context).startActivityForResult(intent, 1);
                        }
                    }
                });

                btnAcceptReport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Reports reports = new Reports(HomeActivity.users.getEmail(), mail, reportList.get(position), txtContentSp.getEditText().getText().toString(), file);
                        FunctionReportFAN functionReportFAN = new FunctionReportFAN();
                        functionReportFAN.insertReport(context, reports,dialog);
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


    public void hamDialog(Dialog dialog, int giaodien) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(giaodien);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttri = window.getAttributes();
        windowAttri.gravity = Gravity.CENTER;
        window.setAttributes(windowAttri);
        dialog.setCancelable(true);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        imgReport.setVisibility(View.VISIBLE);
        Uri uri = data.getData();
        imgReport.setImageURI(uri);
        file = new File(getRealPathFromURI(uri));
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
