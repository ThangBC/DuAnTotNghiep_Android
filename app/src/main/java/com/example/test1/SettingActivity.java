package com.example.test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1.adapters.RadioAdapter;
import com.example.test1.fragments.ProfileFragment;
import com.example.test1.functions.Loading;
import com.example.test1.networking.FunctionGetListFAN;
import com.example.test1.networking.FunctionUserFAN;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {

    TextView tvDone, tvAddressSetting, tvMajorSetting, tvShowSetting, tvShowCourseSetting;
    LinearLayout btnAddressSetting,btnShowSetting,btnMajorSetting,btnShowCourseSetting;
    Button btnLogout, btnDeleteAccount, btnSupport,btnChangePassW;;
    public static String ShowStr;
    GoogleApiClient mGoogleApiClient;
    FunctionUserFAN functionUserFAN;
    Loading loading;

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
        tvAddressSetting = findViewById(R.id.tvAddressSetting);
        tvMajorSetting = findViewById(R.id.tvMajorSetting);
        tvShowSetting = findViewById(R.id.tvShowSetting);
        tvShowCourseSetting = findViewById(R.id.tvShowCourseSetting);
        btnChangePassW = findViewById(R.id.btnChangePassW);

        loading = new Loading();

        tvShowSetting.setText(HomeActivity.users.getIsShow().get(0));
        tvAddressSetting.setText(HomeActivity.users.getIsShow().get(1));
        tvMajorSetting.setText(HomeActivity.users.getIsShow().get(2));
        tvShowCourseSetting.setText(HomeActivity.users.getIsShow().get(3));

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, HomeActivity.class));
                HomeActivity.fragment = new ProfileFragment();
                HomeActivity.selectedItem = R.id.proId;
            }
        });

        btnChangePassW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, ChangePasswordActivity.class));
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

                RadioAdapter radioAdapter = new RadioAdapter(showList,SettingActivity.this,HomeActivity.users.getIsShow().get(0));
                rycInterestEdit.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
                rycInterestEdit.setAdapter(radioAdapter);
                radioAdapter.notifyDataSetChanged();

                tvSetting.setText("Cài đặt hiển thị giới tính");

                btnConfirmSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loading.show(getSupportFragmentManager(),"loading");
                        String [] showarr = {ShowStr,HomeActivity.users.getIsShow().get(1),HomeActivity.users.getIsShow().get(2),HomeActivity.users.getIsShow().get(3)};
                        functionUserFAN = new FunctionUserFAN();
                        functionUserFAN.updateIsShow(HomeActivity.users.getEmail(),HomeActivity.users.get_id(),showarr,ShowStr,SettingActivity.this,dialog,tvShowSetting,loading);
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
                addressList.add(0,"Tất cả cơ sở");

                RadioAdapter radioAdapter = new RadioAdapter(addressList,SettingActivity.this,HomeActivity.users.getIsShow().get(1));
                rycInterestEdit.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
                rycInterestEdit.setAdapter(radioAdapter);
                radioAdapter.notifyDataSetChanged();

                btnConfirmSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loading.show(getSupportFragmentManager(),"loading");
                        String [] showarr = {HomeActivity.users.getIsShow().get(0),ShowStr,HomeActivity.users.getIsShow().get(2),HomeActivity.users.getIsShow().get(3)};
                        functionUserFAN = new FunctionUserFAN();
                        functionUserFAN.updateIsShow(HomeActivity.users.getEmail(),HomeActivity.users.get_id(),showarr,ShowStr,SettingActivity.this,dialog,tvAddressSetting,loading);
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
                specializedList.add(0,"Tất cả chuyên ngành");

                RadioAdapter radioAdapter = new RadioAdapter(specializedList,SettingActivity.this,HomeActivity.users.getIsShow().get(2));
                rycInterestEdit.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
                rycInterestEdit.setAdapter(radioAdapter);
                radioAdapter.notifyDataSetChanged();

                btnConfirmSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loading.show(getSupportFragmentManager(),"loading");
                        String [] showarr = {HomeActivity.users.getIsShow().get(0),HomeActivity.users.getIsShow().get(1),ShowStr,HomeActivity.users.getIsShow().get(3)};
                        functionUserFAN = new FunctionUserFAN();
                        functionUserFAN.updateIsShow(HomeActivity.users.getEmail(),HomeActivity.users.get_id(),showarr,ShowStr,SettingActivity.this,dialog,tvMajorSetting,loading);
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
                courseList.add(0,"Tất cả khóa học");

                RadioAdapter radioAdapter = new RadioAdapter(courseList,SettingActivity.this,HomeActivity.users.getIsShow().get(3));
                rycInterestEdit.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
                rycInterestEdit.setAdapter(radioAdapter);
                radioAdapter.notifyDataSetChanged();

                btnConfirmSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loading.show(getSupportFragmentManager(),"loading");
                        String [] showarr = {HomeActivity.users.getIsShow().get(0),HomeActivity.users.getIsShow().get(1),HomeActivity.users.getIsShow().get(2),ShowStr};
                        functionUserFAN = new FunctionUserFAN();
                        functionUserFAN.updateIsShow(HomeActivity.users.getEmail(),HomeActivity.users.get_id(),showarr,ShowStr,SettingActivity.this,dialog,tvShowCourseSetting,loading);
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
                        startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                        Toast.makeText(SettingActivity.this, "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();
                        signOut();
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

                hamDialog(dialog, R.layout.dialog_confirm);

                Button btnCancelDeleteDialog = dialog.findViewById(R.id.btnCancelDeleteDialog);
                Button btnDeletetDialog = dialog.findViewById(R.id.btnDeletetDialog);
                TextView tvTitleDeleteDialog = dialog.findViewById(R.id.tvTitleDeleteDialog);
                TextView tvBodyDeleteDialog = dialog.findViewById(R.id.tvBodyDeleteDialog);

                tvTitleDeleteDialog.setText("Xóa tài khoản");
                tvBodyDeleteDialog.setText("Bạn có chắc chắn muốn xóa tài khoản ?");
                btnDeletetDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(SettingActivity.this, LoginActivity.class));
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

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
            }
        });
    }
}