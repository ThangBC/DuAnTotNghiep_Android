package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1.adapters.InterestAdapter;
import com.example.test1.adapters.RadioAdapter;
import com.example.test1.functions.Loading;
import com.example.test1.listeners.InterestListener;
import com.example.test1.models.Users;
import com.example.test1.networking.FunctionUserFAN;
import com.example.test1.signupactivities.AddressStudyActivity;
import com.example.test1.signupactivities.InterestsActivity;
import com.example.test1.signupactivities.SpecializedActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
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

        tvDes.setText("Giới thiệu: " + HomeActivity.users.getDescription());
        tvInterest.setText("Sở thích: " + hobbiesStr);
        tvSex.setText("Giới tính: " + HomeActivity.users.getGender());
        tvAddress.setText("Cơ sở: " + HomeActivity.users.getFacilities());
        tvSpecialized.setText("Chuyên ngành: " + HomeActivity.users.getSpecialized());
        tvCourse.setText("Khóa học: " + HomeActivity.users.getCourse().substring(5));

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

                tv1.setText("Cập nhật giới thiệu");
                txtContentSp.setHint("Nhập giới thiệu");
                lr.setVisibility(View.GONE);
                txtContentSp.getEditText().setText(HomeActivity.users.getDescription());

                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        loading.show(getSupportFragmentManager(), "loading");
                        users = new Users(txtContentSp.getEditText().getText().toString(), HomeActivity.users.getHobbies(),
                                HomeActivity.users.getFacilities(), HomeActivity.users.getSpecialized());

                        functionUserFAN.updateUser(HomeActivity.users.getEmail(), users, txtContentSp.getEditText().getText().toString()
                                , "Giới thiệu: ", EditProActivity.this, dialog, tvDes, loading);
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

                tvEdit.setText("Cập nhật sở thích: " + HomeActivity.users.getHobbies().size() + "/5");

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

                            functionUserFAN.updateUser(HomeActivity.users.getEmail(), users, interestStr
                                    , "Sở thích: ", EditProActivity.this, dialog, tvInterest, loading);
                        }else {
                            Toast.makeText(EditProActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
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

                tvEdit.setText("Cập nhật cơ sở");

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

                        functionUserFAN.updateUser(HomeActivity.users.getEmail(), users, selectedIsShow
                                , "Cơ sở: ", EditProActivity.this, dialog, tvAddress, loading);
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

                tvEdit.setText("Cập nhật chuyên ngành");

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

                        functionUserFAN.updateUser(HomeActivity.users.getEmail(), users, selectedIsShow
                                , "Chuyên ngành: ", EditProActivity.this, dialog, tvSpecialized, loading);
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
        tvEdit.setText("Cập nhật sở thích: " + count + "/5");
    }

    @Override
    public void changeSelectedIsShow(String selected) {
        selectedIsShow = selected;
    }
}