package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1.adapters.InterestAdapter;
import com.example.test1.adapters.RadioAdapter;
import com.example.test1.adapters.SpinnerAdapter;
import com.example.test1.fragments.ProfileFragment;
import com.example.test1.functions.Loading;
import com.example.test1.interfaces.InterestListener;
import com.example.test1.models.Reports;
import com.example.test1.models.Users;
import com.example.test1.networking.FunctionGetListFAN;
import com.example.test1.networking.FunctionReportFAN;
import com.example.test1.networking.FunctionUserFAN;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class EditProActivity extends AppCompatActivity implements InterestListener {

    TextView tvDone, tvDes, tvInterest, tvSex, tvAddress, tvSpecialized, tvCourse, tvEdit;
    ImageView imgbtnEditDes, imgbtnEditInterest, imgbtnEditAddress, imgbtnEditSpecialized;
    Users users;
    FunctionUserFAN functionUserFAN;
    Loading loading;
    int countInterest = 0;
    ArrayList<String> interest = new ArrayList<>();
    String selectedIsShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pro);
        tvDone = findViewById(R.id.tvDone);
        tvDes = findViewById(R.id.tvDes);
        tvInterest = findViewById(R.id.tvInterest);
        tvSex = findViewById(R.id.tvSex);
        tvAddress = findViewById(R.id.tvAddress);
        tvSpecialized = findViewById(R.id.tvSpecialized);
        tvCourse = findViewById(R.id.tvCourse);
        imgbtnEditDes = findViewById(R.id.imgbtnEditDes);
        imgbtnEditInterest = findViewById(R.id.imgbtnEditInterest);
        imgbtnEditAddress = findViewById(R.id.imgbtnEditAddress);
        imgbtnEditSpecialized = findViewById(R.id.imgbtnEditSpecialized);

        functionUserFAN = new FunctionUserFAN();
        loading = new Loading();

        String hobbiesStr = HomeActivity.users.getHobbies().toString().substring(1, HomeActivity.users.getHobbies().toString().length() - 1);

        tvDes.setText("Gi???i thi???u: " + HomeActivity.users.getDescription());
        tvInterest.setText("S??? th??ch: " + hobbiesStr);
        tvSex.setText("Gi???i t??nh: " + HomeActivity.users.getGender());
        tvAddress.setText("C?? s???: " + HomeActivity.users.getFacilities());
        tvSpecialized.setText("Chuy??n ng??nh: " + HomeActivity.users.getSpecialized());
        tvCourse.setText("Kh??a h???c: " + HomeActivity.users.getCourse());

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgbtnEditDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(EditProActivity.this);
                hamDialog(dialog, R.layout.dialog_support);

                TextView tv1 = dialog.findViewById(R.id.tv1);
                Button btnAccept = dialog.findViewById(R.id.btnSendSupport);
                Button btnCancel = dialog.findViewById(R.id.btnCancelSupport);
                TextInputLayout txtContentSp = dialog.findViewById(R.id.txtContentSp);
                LinearLayout lr = dialog.findViewById(R.id.btnReport);

                tv1.setText("C???p nh???t gi???i thi???u");
                txtContentSp.setHint("Nh???p gi???i thi???u");
                lr.setVisibility(View.GONE);
                txtContentSp.getEditText().setText(HomeActivity.users.getDescription());

                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        loading.show(getSupportFragmentManager(), "loading");
                        users = new Users(txtContentSp.getEditText().getText().toString(), HomeActivity.users.getHobbies(),
                                HomeActivity.users.getFacilities(), HomeActivity.users.getSpecialized());

                        functionUserFAN.updateUser(HomeActivity.users.getEmail(), HomeActivity.users.get_id(), users, txtContentSp.getEditText().getText().toString()
                                , "Gi???i thi???u: ", EditProActivity.this, dialog, tvDes, loading);
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        imgbtnEditInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(EditProActivity.this);
                hamDialog(dialog, R.layout.dialog_favorite);

                tvEdit = dialog.findViewById(R.id.tvEdit);
                RecyclerView rycInterestEdit = dialog.findViewById(R.id.rycInterestEdit);
                Button btnCancelSetting = dialog.findViewById(R.id.btnCancelSetting);
                Button btnConfirmSetting = dialog.findViewById(R.id.btnConfirmSetting);

                tvEdit.setText("C???p nh???t s??? th??ch: " + HomeActivity.users.getHobbies().size() + "/5");

                List<String> checkHobbies = new ArrayList<>(HomeActivity.users.getHobbies());

                InterestAdapter interestAdapter = new InterestAdapter(EditProActivity.this, InterestsActivity.interestList
                        , checkHobbies, EditProActivity.this);
                rycInterestEdit.setLayoutManager(new LinearLayoutManager(EditProActivity.this));
                rycInterestEdit.setAdapter(interestAdapter);

                btnConfirmSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (interest.size() > 0) {
                            loading.show(getSupportFragmentManager(), "loading");

                            users = new Users(HomeActivity.users.getDescription(), interest,
                                    HomeActivity.users.getFacilities(), HomeActivity.users.getSpecialized());

                            String interestStr = interest.toString().substring(1, interest.toString().length() - 1);

                            functionUserFAN.updateUser(HomeActivity.users.getEmail(), HomeActivity.users.get_id(), users, interestStr
                                    , "S??? th??ch: ", EditProActivity.this, dialog, tvInterest, loading);
                        }else {
                            Toast.makeText(EditProActivity.this, "Kh??ng ???????c ????? tr???ng", Toast.LENGTH_SHORT).show();
                        }
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
        imgbtnEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(EditProActivity.this);
                hamDialog(dialog, R.layout.dialog_favorite);

                tvEdit = dialog.findViewById(R.id.tvEdit);
                RecyclerView rycInterestEdit = dialog.findViewById(R.id.rycInterestEdit);
                Button btnCancelSetting = dialog.findViewById(R.id.btnCancelSetting);
                Button btnConfirmSetting = dialog.findViewById(R.id.btnConfirmSetting);

                tvEdit.setText("C???p nh???t c?? s???");

                List<String> addressList = new ArrayList<>(AddressStudyActivity.addressStudyList);
                addressList.remove(0);

                RadioAdapter radioAdapter = new RadioAdapter(addressList,EditProActivity.this,HomeActivity.users.getFacilities(),EditProActivity.this);
                rycInterestEdit.setLayoutManager(new LinearLayoutManager(EditProActivity.this));
                rycInterestEdit.setAdapter(radioAdapter);
                radioAdapter.notifyDataSetChanged();

                btnConfirmSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loading.show(getSupportFragmentManager(), "loading");

                        users = new Users(HomeActivity.users.getDescription(), HomeActivity.users.getHobbies(),
                                selectedIsShow, HomeActivity.users.getSpecialized());

                        functionUserFAN.updateUser(HomeActivity.users.getEmail(), HomeActivity.users.get_id(), users, selectedIsShow
                                , "C?? s???: ", EditProActivity.this, dialog, tvAddress, loading);
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
        imgbtnEditSpecialized.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(EditProActivity.this);
                hamDialog(dialog, R.layout.dialog_favorite);

                tvEdit = dialog.findViewById(R.id.tvEdit);
                RecyclerView rycInterestEdit = dialog.findViewById(R.id.rycInterestEdit);
                Button btnCancelSetting = dialog.findViewById(R.id.btnCancelSetting);
                Button btnConfirmSetting = dialog.findViewById(R.id.btnConfirmSetting);

                tvEdit.setText("C???p nh???t chuy??n ng??nh");

                List<String> specializedList = new ArrayList<>(SpecializedActivity.specializedList);
                specializedList.remove(0);

                RadioAdapter radioAdapter = new RadioAdapter(specializedList,EditProActivity.this,HomeActivity.users.getSpecialized(),EditProActivity.this);
                rycInterestEdit.setLayoutManager(new LinearLayoutManager(EditProActivity.this));
                rycInterestEdit.setAdapter(radioAdapter);
                radioAdapter.notifyDataSetChanged();

                btnConfirmSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loading.show(getSupportFragmentManager(), "loading");

                        users = new Users(HomeActivity.users.getDescription(), HomeActivity.users.getHobbies(),
                                HomeActivity.users.getFacilities(), selectedIsShow);

                        functionUserFAN.updateUser(HomeActivity.users.getEmail(), HomeActivity.users.get_id(), users, selectedIsShow
                                , "Chuy??n ng??nh: ", EditProActivity.this, dialog, tvSpecialized, loading);
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
        dialog.setCancelable(false);
    }

    @Override
    public void changeInterest(List<String> arr, int count) {
        List<String> interestarr = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            interestarr.add(arr.get(i));
        }
        interest = (ArrayList<String>) interestarr;
        countInterest = count;
        tvEdit.setText("C???p nh???t s??? th??ch: " + count + "/5");
    }

    @Override
    public void changeSelectedIsShow(String selected) {
        selectedIsShow = selected;
    }
}