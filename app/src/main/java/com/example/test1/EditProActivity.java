package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1.adapters.SpinnerAdapter;
import com.example.test1.fragments.ProfileFragment;
import com.example.test1.interfaces.InterestListener;
import com.example.test1.volleys.FunctionGetListVolley;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditProActivity extends AppCompatActivity implements InterestListener {

    Spinner spnNganhHoc,spnSex;
    SpinnerAdapter spinnerSexAdapter;
    TextView tvDone, tvFavoriteEdit;
    EditText txtEditBirthday;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    List<String> nganhHocList = new ArrayList<>();
    List<String> interestListEdit = new ArrayList<>();
    String favoriteStr = "";
    String nganhhocStr;
    List<String> sexList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pro);
        tvDone = findViewById(R.id.tvDone);
        txtEditBirthday = findViewById(R.id.txtEditBirthday);
        spnNganhHoc = findViewById(R.id.spnNganhHoc);
        spnSex = findViewById(R.id.spnSex);
        tvFavoriteEdit = findViewById(R.id.tvFavoriteEdit);

        adapter();

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProActivity.this,HomeActivity.class));
                HomeActivity.fragment = new ProfileFragment();
                HomeActivity.selectedItem = R.id.proId;
            }
        });

        txtEditBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditProActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                txtEditBirthday.setText(date);
            }
        };
    }
    private void adapter() {

        FunctionGetListVolley functionGetListVolley = new FunctionGetListVolley();
        functionGetListVolley.getListSpecializedAPI(EditProActivity.this,spnNganhHoc,nganhHocList);

        spnNganhHoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    nganhhocStr = nganhHocList.get(position);
                    Toast.makeText(EditProActivity.this, nganhhocStr, Toast.LENGTH_SHORT).show();
                }
                
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sexList.add("Giới tính");
        sexList.add("Nam");
        sexList.add("Nữ");
        sexList.add("Khác");

        spinnerSexAdapter = new SpinnerAdapter(EditProActivity.this,sexList);
        spnSex.setAdapter(spinnerSexAdapter);

        spnSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    Toast.makeText(EditProActivity.this,sexList.get(position),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvFavoriteEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(EditProActivity.this);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_favorite);
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

                RecyclerView rycInterestEdit = dialog.findViewById(R.id.rycInterestEdit);
                Button btnCancelSetting = dialog.findViewById(R.id.btnCancelSetting);
                Button btnConfirmSetting = dialog.findViewById(R.id.btnConfirmSetting);

                FunctionGetListVolley functionGetListVolley = new FunctionGetListVolley();
                functionGetListVolley.getListInterestAPI(EditProActivity.this,rycInterestEdit,interestListEdit,EditProActivity.this);

                btnConfirmSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(EditProActivity.this, "Đã chỉnh sửa", Toast.LENGTH_SHORT).show();
                        tvFavoriteEdit.setText("Sở thích: "+favoriteStr);
                        dialog.dismiss();
                    }
                });

                btnCancelSetting.setOnClickListener(new View.OnClickListener() {
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
    public void changeInterest(List<String> arr, int count) {
        String s = "Sở thích: ";
        for (int i = 0;i<arr.size();i++){
            s+=arr.get(i)+", ";
        }
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        favoriteStr = s;
    }
}