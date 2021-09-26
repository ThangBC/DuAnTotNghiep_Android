package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1.Adapter.SpinnerAdapter;
import com.example.test1.Fragment.ProfileFragment;
import com.example.test1.Model.Item;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditProActivity extends AppCompatActivity {

    Spinner spnNganhHoc,spnSex;
    SpinnerAdapter spinnerNganhHocAdapter,spinnerSexAdapter,spinnerInterestsAdapter;
    TextView tvDone, tvFavoriteEdit;
    EditText txtEditBirthday;
    DatePickerDialog.OnDateSetListener onDateSetListener;

    String favoriteStr = "";

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
        spinnerNganhHocAdapter = new SpinnerAdapter(EditProActivity.this,getListNganhHoc());
        spnNganhHoc.setAdapter(spinnerNganhHocAdapter);

        spnNganhHoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(EditProActivity.this,spinnerNganhHocAdapter.getItem(position).toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerSexAdapter = new SpinnerAdapter(EditProActivity.this,getListSex());
        spnSex.setAdapter(spinnerSexAdapter);

        spnSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(EditProActivity.this,spinnerSexAdapter.getItem(position).toString(),Toast.LENGTH_SHORT).show();
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

                CheckBox ckbChoiGameEdit = dialog.findViewById(R.id.ckbChoiGameEdit);
                CheckBox ckbDocSachEdit = dialog.findViewById(R.id.ckbDocSachEdit);
                CheckBox ckbDuLichEdit = dialog.findViewById(R.id.ckbDuLichEdit);
                CheckBox ckbMuaSamEdit = dialog.findViewById(R.id.ckbMuaSamEdit);
                Button btnCancelSetting = dialog.findViewById(R.id.btnCancelSetting);
                Button btnConfirmSetting = dialog.findViewById(R.id.btnConfirmSetting);

                ckbChoiGameEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                            if(ckbChoiGameEdit.isChecked()){
                                ckbChoiGameEdit.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
                                ckbChoiGameEdit.setTextColor(Color.WHITE);
                                favoriteStr+="Chơi game, ";
                            }else {
                                ckbChoiGameEdit.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.cus_btn_sex));
                                ckbChoiGameEdit.setTextColor(Color.BLACK);
                                favoriteStr+="";
                            }
                    }
                });

                ckbDocSachEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ckbDocSachEdit.isChecked()){
                            ckbDocSachEdit.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
                            ckbDocSachEdit.setTextColor(Color.WHITE);
                            favoriteStr+="Đọc sách, ";
                        }else {
                            ckbDocSachEdit.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.cus_btn_sex));
                            ckbDocSachEdit.setTextColor(Color.BLACK);
                            favoriteStr+="";
                        }
                    }
                });

                ckbDuLichEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ckbDuLichEdit.isChecked()){
                            ckbDuLichEdit.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
                            ckbDuLichEdit.setTextColor(Color.WHITE);
                            favoriteStr+="Du lịch, ";
                        }else {
                            ckbDuLichEdit.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.cus_btn_sex));
                            ckbDuLichEdit.setTextColor(Color.BLACK);
                            favoriteStr+="";
                        }
                    }
                });

                ckbMuaSamEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(ckbMuaSamEdit.isChecked()){
                            ckbMuaSamEdit.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
                            ckbMuaSamEdit.setTextColor(Color.WHITE);
                            favoriteStr+="Mua sắm, ";
                        }else {
                            ckbMuaSamEdit.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.cus_btn_sex));
                            ckbMuaSamEdit.setTextColor(Color.BLACK);
                            favoriteStr+="";
                        }
                    }
                });

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

    private List<Item> getListNganhHoc() {
        List<Item> nganhHocList = new ArrayList<>();
        nganhHocList.add(new Item("Ngành học"));
        nganhHocList.add(new Item("Công nghệ thông tin"));
        nganhHocList.add(new Item("Kinh tế"));
        nganhHocList.add(new Item("DL - NH - KS"));
        nganhHocList.add(new Item("Cơ khí"));
        nganhHocList.add(new Item("Thẩm mỹ làm đẹp"));
        return nganhHocList;
    }
    private List<Item> getListSex() {
        List<Item> sexList = new ArrayList<>();
        sexList.add(new Item("Giới tính"));
        sexList.add(new Item("Nam"));
        sexList.add(new Item("Nữ"));
        sexList.add(new Item("Khác"));
        return sexList;
    }
}