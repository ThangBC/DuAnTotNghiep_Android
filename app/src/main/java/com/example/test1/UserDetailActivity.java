package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1.adapters.ReportAdapter;
import com.example.test1.fragments.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserDetailActivity extends AppCompatActivity {

    FloatingActionButton flatBack;
    Button btnReport;
    TextView tvNameDT, tvAgeDT, tvAddressDT, tvDesDT, tvSexDT, tvSpeciaDT, tvCourseDT;
    String img, name,mail;
    ImageView imgDT,imgReport;
    File fileimg;
    Bitmap bitmap;
    public static List<String> reportlist = new ArrayList<>();
    ReportAdapter reportAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        flatBack = findViewById(R.id.flatBack);
        btnReport = findViewById(R.id.btnReport);
        imgDT = findViewById(R.id.imgUserDetail);
        tvNameDT = findViewById(R.id.tvNameDetail);
        tvAgeDT = findViewById(R.id.tvAgeDetail);
        tvAddressDT = findViewById(R.id.tvAddressDetail);
        tvDesDT = findViewById(R.id.tvDesDetail);
        tvSexDT = findViewById(R.id.tvSexDetail);
        tvSpeciaDT = findViewById(R.id.tvSpecializedDetail);
        tvCourseDT = findViewById(R.id.tvCourseDetail);

        mail = getIntent().getStringExtra("mail");
        img = getIntent().getStringExtra("img");
        name = getIntent().getStringExtra("name");

        reportAdapter = new ReportAdapter(reportlist,this,mail);

        tvNameDT.setText(name);

        flatBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(UserDetailActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_report);
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

                TextView tv = dialog.findViewById(R.id.tv);
                RecyclerView rycReportList = dialog.findViewById(R.id.rycReportList);

                tv.setText(name+" sẽ không biết bạn báo cáo");

                rycReportList.setLayoutManager(new LinearLayoutManager(UserDetailActivity.this, RecyclerView.VERTICAL, false));
                rycReportList.setAdapter(reportAdapter);

                dialog.show();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                reportAdapter.onActivityResult(requestCode,resultCode,data);
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
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