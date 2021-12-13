package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test1.adapters.RadioAdapter;
import com.example.test1.functions.Loading;
import com.example.test1.interfaces.InterestListener;
import com.example.test1.networking.FunctionUserFAN;
import com.example.test1.signupactivities.AddressStudyActivity;
import com.example.test1.signupactivities.CourseActivity;
import com.example.test1.signupactivities.SpecializedActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity implements InterestListener {

    TextView tvDone, tvAddressSetting, tvMajorSetting, tvShowSetting, tvShowCourseSetting, tvFilterInterest;
    LinearLayout btnAddressSetting, btnShowSetting, btnMajorSetting, btnShowCourseSetting, btnInterestSetting;
    Button btnLogout, btnDeleteAccount, btnSupport;
    String ShowStr;
    GoogleApiClient mGoogleApiClient;
    FunctionUserFAN functionUserFAN;
    Loading loading;
    String check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        tvDone = findViewById(R.id.tvDone);
        btnLogout = findViewById(R.id.btnLogout);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccout);
        btnSupport = findViewById(R.id.btnSupport);
        btnAddressSetting = findViewById(R.id.btnAddressSetting);
        btnShowSetting = findViewById(R.id.btnShowSetting);
        btnMajorSetting = findViewById(R.id.btnMajorSetting);
        btnShowCourseSetting = findViewById(R.id.btnShowCourseSetting);
        btnInterestSetting = findViewById(R.id.btnInterestSetting);
        tvAddressSetting = findViewById(R.id.tvAddressSetting);
        tvMajorSetting = findViewById(R.id.tvMajorSetting);
        tvShowSetting = findViewById(R.id.tvShowSetting);
        tvShowCourseSetting = findViewById(R.id.tvShowCourseSetting);
        tvFilterInterest = findViewById(R.id.tvFilterInterest);

        loading = new Loading();
        functionUserFAN = new FunctionUserFAN();

        tvShowSetting.setText(HomeActivity.users.getIsShow().get(0));
        tvAddressSetting.setText(HomeActivity.users.getIsShow().get(1));
        tvMajorSetting.setText(HomeActivity.users.getIsShow().get(2));
        tvShowCourseSetting.setText(HomeActivity.users.getIsShow().get(3));
        if (HomeActivity.users.isStatusHobbies() == true) {
            tvFilterInterest.setText("Bật");
        } else {
            tvFilterInterest.setText("Tắt");
        }

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnShowSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SettingActivity.this);

                hamDialog(dialog, R.layout.dialog_favorite);

                TextView tvSetting = dialog.findViewById(R.id.tvEdit);
                RecyclerView rycInterestEdit = dialog.findViewById(R.id.rycInterestEdit);
                Button btnCancelSetting = dialog.findViewById(R.id.btnCancelSetting);
                Button btnConfirmSetting = dialog.findViewById(R.id.btnConfirmSetting);
                tvSetting.setText("Cài đặt giới tính");

                List<String> showList = new ArrayList<>();
                showList.add("Mọi người");
                showList.add("Nam");
                showList.add("Nữ");

                RadioAdapter radioAdapter = new RadioAdapter(showList, SettingActivity.this, HomeActivity.users.getIsShow().get(0), SettingActivity.this);
                rycInterestEdit.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
                rycInterestEdit.setAdapter(radioAdapter);
                radioAdapter.notifyDataSetChanged();

                tvSetting.setText("Cài đặt hiển thị giới tính");

                btnConfirmSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loading.show(getSupportFragmentManager(), "loading");
                        String[] showarr = {ShowStr, HomeActivity.users.getIsShow().get(1), HomeActivity.users.getIsShow().get(2), HomeActivity.users.getIsShow().get(3)};
                        functionUserFAN.updateIsShow(HomeActivity.users.getEmail(), showarr, ShowStr, SettingActivity.this, dialog, tvShowSetting, loading);
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

        btnAddressSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SettingActivity.this);

                hamDialog(dialog, R.layout.dialog_favorite);

                TextView tvSetting = dialog.findViewById(R.id.tvEdit);
                RecyclerView rycInterestEdit = dialog.findViewById(R.id.rycInterestEdit);
                Button btnCancelSetting = dialog.findViewById(R.id.btnCancelSetting);
                Button btnConfirmSetting = dialog.findViewById(R.id.btnConfirmSetting);

                tvSetting.setText("Cài đặt cơ sở");

                List<String> addressList = new ArrayList<>(AddressStudyActivity.addressStudyList);
                addressList.remove(0);
                addressList.add(0, "Tất cả cơ sở");

                RadioAdapter radioAdapter = new RadioAdapter(addressList, SettingActivity.this, HomeActivity.users.getIsShow().get(1), SettingActivity.this);
                rycInterestEdit.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
                rycInterestEdit.setAdapter(radioAdapter);
                radioAdapter.notifyDataSetChanged();

                btnConfirmSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loading.show(getSupportFragmentManager(), "loading");
                        String[] showarr = {HomeActivity.users.getIsShow().get(0), ShowStr, HomeActivity.users.getIsShow().get(2), HomeActivity.users.getIsShow().get(3)};
                        functionUserFAN.updateIsShow(HomeActivity.users.getEmail(), showarr, ShowStr, SettingActivity.this, dialog, tvAddressSetting, loading);
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


        btnMajorSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SettingActivity.this);

                hamDialog(dialog, R.layout.dialog_favorite);

                TextView tvSetting = dialog.findViewById(R.id.tvEdit);
                RecyclerView rycInterestEdit = dialog.findViewById(R.id.rycInterestEdit);
                Button btnCancelSetting = dialog.findViewById(R.id.btnCancelSetting);
                Button btnConfirmSetting = dialog.findViewById(R.id.btnConfirmSetting);

                tvSetting.setText("Cài đặt hiển thị ngành học");

                List<String> specializedList = new ArrayList<>(SpecializedActivity.specializedList);
                specializedList.remove(0);
                specializedList.add(0, "Tất cả chuyên ngành");

                RadioAdapter radioAdapter = new RadioAdapter(specializedList, SettingActivity.this, HomeActivity.users.getIsShow().get(2), SettingActivity.this);
                rycInterestEdit.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
                rycInterestEdit.setAdapter(radioAdapter);
                radioAdapter.notifyDataSetChanged();

                btnConfirmSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loading.show(getSupportFragmentManager(), "loading");
                        String[] showarr = {HomeActivity.users.getIsShow().get(0), HomeActivity.users.getIsShow().get(1), ShowStr, HomeActivity.users.getIsShow().get(3)};
                        functionUserFAN.updateIsShow(HomeActivity.users.getEmail(), showarr, ShowStr, SettingActivity.this, dialog, tvMajorSetting, loading);
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

        btnShowCourseSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SettingActivity.this);

                hamDialog(dialog, R.layout.dialog_favorite);

                TextView tvSetting = dialog.findViewById(R.id.tvEdit);
                RecyclerView rycInterestEdit = dialog.findViewById(R.id.rycInterestEdit);
                Button btnCancelSetting = dialog.findViewById(R.id.btnCancelSetting);
                Button btnConfirmSetting = dialog.findViewById(R.id.btnConfirmSetting);

                tvSetting.setText("Cài đặt hiển thị khóa học");

                List<String> courseList = new ArrayList<>(CourseActivity.courseList);
                courseList.remove(0);
                courseList.add(0, "Tất cả khóa học");

                RadioAdapter radioAdapter = new RadioAdapter(courseList, SettingActivity.this, HomeActivity.users.getIsShow().get(3), SettingActivity.this);
                rycInterestEdit.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
                rycInterestEdit.setAdapter(radioAdapter);
                radioAdapter.notifyDataSetChanged();

                btnConfirmSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loading.show(getSupportFragmentManager(), "loading");
                        String[] showarr = {HomeActivity.users.getIsShow().get(0), HomeActivity.users.getIsShow().get(1), HomeActivity.users.getIsShow().get(2), ShowStr};
                        functionUserFAN.updateIsShow(HomeActivity.users.getEmail(), showarr, ShowStr, SettingActivity.this, dialog, tvShowCourseSetting, loading);
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

        btnInterestSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SettingActivity.this);

                hamDialog(dialog, R.layout.dialog_filter_interest);

                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
                SwitchCompat swtInterest = dialog.findViewById(R.id.swtInterest);

                if (HomeActivity.users.isStatusHobbies() == true) {
                    check = "true";
                    swtInterest.setChecked(true);
                } else {
                    check = "false";
                    swtInterest.setChecked(false);
                }


                swtInterest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (swtInterest.isChecked()) {
                            check = "true";
                        } else {
                            check = "false";
                        }
                    }
                });

                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loading.show(getSupportFragmentManager(), "loading");
                        functionUserFAN.updateStatusHobbies(HomeActivity.users.getEmail(), check,
                                SettingActivity.this, loading, dialog, tvFilterInterest);
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

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SettingActivity.this);
                hamDialog(dialog, R.layout.dialog_confirm);

                Button btnCancelDeleteDialog = dialog.findViewById(R.id.btnCancelDeleteDialog);
                Button btnDeletetDialog = dialog.findViewById(R.id.btnDeletetDialog);
                TextView tvTitleDeleteDialog = dialog.findViewById(R.id.tvTitleDeleteDialog);
                TextView tvBodyDeleteDialog = dialog.findViewById(R.id.tvBodyDeleteDialog);

                tvTitleDeleteDialog.setText("Đăng xuất");
                tvBodyDeleteDialog.setText("Bạn có chắc chắn muốn đăng xuất ?");
                btnDeletetDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loading.show(getSupportFragmentManager(), "loading");
                        functionUserFAN.signOutUser(
                                SettingActivity.this, loading, mGoogleApiClient);
                    }
                });

                btnCancelDeleteDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SettingActivity.this);

                hamDialog(dialog, R.layout.dialog_forgot_pass);

                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
                EditText txtDelete = dialog.findViewById(R.id.txtPassForgot);

                functionUserFAN.requestCode(SettingActivity.this);

                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loading.show(getSupportFragmentManager(), "loading");
                        functionUserFAN.deleteUser( txtDelete.getText().toString(),
                                SettingActivity.this, loading, mGoogleApiClient);
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

        btnSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
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

    }

    @Override
    public void changeSelectedIsShow(String selected) {
        ShowStr = selected;
    }
}