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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1.adapters.RadioAdapter;
import com.example.test1.fragments.ProfileFragment;
import com.example.test1.networking.FunctionGetListFAN;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {

    TextView tvDone, tvAddressSetting, tvMajorSetting, tvShowSetting, tvShowAgeSetting;
    Button btnLogout, btnDeleteAccount, btnSupport, btnAddressSetting, btnShowSetting, btnMajorSetting,
            btnShowAgeSetting,btnChangePassW;;
    String addressString = "";
    GoogleApiClient mGoogleApiClient;
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
        btnShowAgeSetting = findViewById(R.id.btnShowAgeSetting);
        tvAddressSetting = findViewById(R.id.tvAddressSetting);
        tvMajorSetting = findViewById(R.id.tvMajorSetting);
        tvShowSetting = findViewById(R.id.tvShowSetting);
        tvShowAgeSetting = findViewById(R.id.tvShowAgeSetting);
        btnChangePassW = findViewById(R.id.btnChangePassW);

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

                List<String> showStr = new ArrayList<>();
                showStr.add("Nam");
                showStr.add("Nữ");
                showStr.add("Mọi người");

                RadioAdapter radioAdapter = new RadioAdapter(showStr,SettingActivity.this,HomeActivity.users.getIsShow().get(0));
                rycInterestEdit.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
                rycInterestEdit.setAdapter(radioAdapter);
                radioAdapter.notifyDataSetChanged();

                tvSetting.setText("Cài đặt hiển thị giới tính");

                btnConfirmSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(SettingActivity.this, "Đã chỉnh sửa", Toast.LENGTH_SHORT).show();
                        tvShowSetting.setText(addressString);
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
                List<String> addressStr = new ArrayList<>(AddressStudyActivity.addressStudyList);
                addressStr.remove(0);
                Log.e("size", String.valueOf(addressStr.size()));
                RadioAdapter radioAdapter = new RadioAdapter(addressStr,SettingActivity.this,HomeActivity.users.getIsShow().get(1));
                rycInterestEdit.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
                rycInterestEdit.setAdapter(radioAdapter);
                radioAdapter.notifyDataSetChanged();

                btnConfirmSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(SettingActivity.this, "Đã chỉnh sửa", Toast.LENGTH_SHORT).show();
                        tvAddressSetting.setText(addressString);
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

                List<String> specializedStr = new ArrayList<>(SpecializedActivity.specializedList);
                specializedStr.remove(0);
                Log.e("size", String.valueOf(specializedStr.size()));
                RadioAdapter radioAdapter = new RadioAdapter(specializedStr,SettingActivity.this,HomeActivity.users.getIsShow().get(2));
                rycInterestEdit.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
                rycInterestEdit.setAdapter(radioAdapter);
                radioAdapter.notifyDataSetChanged();

                btnConfirmSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(SettingActivity.this, "Đã chỉnh sửa", Toast.LENGTH_SHORT).show();
                        tvMajorSetting.setText(addressString);
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

        btnShowAgeSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SettingActivity.this);

                hamDialog(dialog, R.layout.dialog_favorite);

                TextView tvSetting = dialog.findViewById(R.id.tvEdit);
                RecyclerView rycInterestEdit = dialog.findViewById(R.id.rycInterestEdit);
                Button btnCancelSetting = dialog.findViewById(R.id.btnCancelSetting);
                Button btnConfirmSetting = dialog.findViewById(R.id.btnConfirmSetting);

                tvSetting.setText("Cài đặt hiển thị khóa học");

                List<String> courseStr = new ArrayList<>(CourseActivity.courseList);
                courseStr.remove(0);
                Log.e("size", String.valueOf(courseStr.size()));
                RadioAdapter radioAdapter = new RadioAdapter(courseStr,SettingActivity.this,HomeActivity.users.getIsShow().get(3));
                rycInterestEdit.setLayoutManager(new LinearLayoutManager(SettingActivity.this));
                rycInterestEdit.setAdapter(radioAdapter);
                radioAdapter.notifyDataSetChanged();

                btnConfirmSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(SettingActivity.this, "Đã chỉnh sửa", Toast.LENGTH_SHORT).show();
                        tvShowAgeSetting.setText(addressString);
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

    public void updateRdoGroup(RadioButton selected, RadioButton unselect1, RadioButton unselect2, RadioButton unselect3, RadioButton unselect4) {
        unselect1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cus_btn_sex));
        unselect2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cus_btn_sex));
        unselect3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cus_btn_sex));
        unselect4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cus_btn_sex));
        selected.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rdo_sex_on));
        addressString = selected.getText().toString();
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                Toast.makeText(SettingActivity.this, "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}