package com.example.test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1.Fragment.ChatFragment;
import com.example.test1.Fragment.ProfileFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SettingActivity extends AppCompatActivity {

    TextView tvDone, tvAddressSetting, tvMajorSetting, tvShowSetting, tvShowAgeSetting;

    Button btnLogout, btnDeleteAccount, btnSupport, btnAddressSetting, btnShowSetting, btnMajorSetting, btnShowAgeSetting;

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

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, HomeActivity.class));
                HomeActivity.fragment = new ProfileFragment();
                HomeActivity.selectedItem = R.id.proId;
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
                final Dialog dialog = new Dialog(SettingActivity.this);

                hamDialog(dialog, R.layout.dialog_support);

                Button btnCancelSupport = dialog.findViewById(R.id.btnCancelSupport);
                Button btnSendSupport = dialog.findViewById(R.id.btnSendSupport);
                EditText txtTitleSp = dialog.findViewById(R.id.txtTitleSp);
                EditText txtContentSp = dialog.findViewById(R.id.txtContentSp);

                btnSendSupport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(SettingActivity.this, "Cảm ơn bạn đã gửi tin nhắn, chúng tôi sẽ phản hồi trong thời gian sớm", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                btnCancelSupport.setOnClickListener(new View.OnClickListener() {
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

                hamDialog(dialog, R.layout.dialog_address_setting);

                RadioButton rdo1 = dialog.findViewById(R.id.rdo1);
                RadioButton rdo2 = dialog.findViewById(R.id.rdo2);
                RadioButton rdo3 = dialog.findViewById(R.id.rdo3);
                RadioButton rdo4 = dialog.findViewById(R.id.rdo4);
                RadioButton rdo5 = dialog.findViewById(R.id.rdo5);
                TextView tvSetting = dialog.findViewById(R.id.tvSetting);
                Button btnCancelSetting = dialog.findViewById(R.id.btnCancelSetting);
                Button btnConfirmSetting = dialog.findViewById(R.id.btnConfirmSetting);

                tvSetting.setText("Cài đặt cơ sở");
                rdo1.setText("Hà Nội");
                rdo2.setText("Hồ Chí Minh");
                rdo3.setText("Đà Nẵng");
                rdo4.setText("Cần Thơ");
                rdo5.setText("Tây Nguyên");

                rdo1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRdoGroup(rdo1, rdo2, rdo3, rdo4, rdo5);
                    }
                });
                rdo2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRdoGroup(rdo2, rdo1, rdo3, rdo4, rdo5);
                    }
                });
                rdo3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRdoGroup(rdo3, rdo1, rdo2, rdo4, rdo5);
                    }
                });
                rdo4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRdoGroup(rdo4, rdo1, rdo2, rdo3, rdo5);
                    }
                });
                rdo5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRdoGroup(rdo5, rdo1, rdo2, rdo3, rdo4);

                    }
                });
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

        btnShowSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SettingActivity.this);

                hamDialog(dialog, R.layout.dialog_address_setting);

                RadioButton rdo1 = dialog.findViewById(R.id.rdo1);
                RadioButton rdo2 = dialog.findViewById(R.id.rdo2);
                RadioButton rdo3 = dialog.findViewById(R.id.rdo3);
                RadioButton rdo4 = dialog.findViewById(R.id.rdo4);
                RadioButton rdo5 = dialog.findViewById(R.id.rdo5);
                TextView tvSetting = dialog.findViewById(R.id.tvSetting);
                Button btnCancelSetting = dialog.findViewById(R.id.btnCancelSetting);
                Button btnConfirmSetting = dialog.findViewById(R.id.btnConfirmSetting);

                tvSetting.setText("Cài đặt hiển thị giới tính");
                rdo1.setText("Nam");
                rdo2.setText("Nữ");
                rdo3.setText("Mọi người");
                rdo4.setVisibility(View.GONE);
                rdo5.setVisibility(View.GONE);

                rdo1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRdoGroup(rdo1, rdo2, rdo3, rdo4, rdo5);
                    }
                });
                rdo2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRdoGroup(rdo2, rdo1, rdo3, rdo4, rdo5);
                    }
                });
                rdo3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRdoGroup(rdo3, rdo1, rdo2, rdo4, rdo5);
                    }
                });

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

        btnShowAgeSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SettingActivity.this);

                hamDialog(dialog, R.layout.dialog_address_setting);

                RadioButton rdo1 = dialog.findViewById(R.id.rdo1);
                RadioButton rdo2 = dialog.findViewById(R.id.rdo2);
                RadioButton rdo3 = dialog.findViewById(R.id.rdo3);
                RadioButton rdo4 = dialog.findViewById(R.id.rdo4);
                RadioButton rdo5 = dialog.findViewById(R.id.rdo5);
                TextView tvSetting = dialog.findViewById(R.id.tvSetting);
                Button btnCancelSetting = dialog.findViewById(R.id.btnCancelSetting);
                Button btnConfirmSetting = dialog.findViewById(R.id.btnConfirmSetting);

                tvSetting.setText("Cài đặt hiển thị độ tuổi");
                rdo1.setText("18");
                rdo2.setText("19");
                rdo3.setText("20");
                rdo4.setVisibility(View.GONE);
                rdo5.setVisibility(View.GONE);

                rdo1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRdoGroup(rdo1, rdo2, rdo3, rdo4, rdo5);
                    }
                });
                rdo2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRdoGroup(rdo2, rdo1, rdo3, rdo4, rdo5);
                    }
                });
                rdo3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRdoGroup(rdo3, rdo1, rdo2, rdo4, rdo5);
                    }
                });

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

        btnMajorSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SettingActivity.this);

                hamDialog(dialog, R.layout.dialog_address_setting);

                RadioButton rdo1 = dialog.findViewById(R.id.rdo1);
                RadioButton rdo2 = dialog.findViewById(R.id.rdo2);
                RadioButton rdo3 = dialog.findViewById(R.id.rdo3);
                RadioButton rdo4 = dialog.findViewById(R.id.rdo4);
                RadioButton rdo5 = dialog.findViewById(R.id.rdo5);
                TextView tvSetting = dialog.findViewById(R.id.tvSetting);
                Button btnCancelSetting = dialog.findViewById(R.id.btnCancelSetting);
                Button btnConfirmSetting = dialog.findViewById(R.id.btnConfirmSetting);

                tvSetting.setText("Cài đặt hiển thị ngành học");
                rdo1.setText("Công nghệ thông tin");
                rdo2.setText("Kinh tế");
                rdo3.setText("DL - NH - KS");
                rdo4.setText("Cơ khí");
                rdo5.setText("Thẩm mỹ làm đẹp");

                rdo1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRdoGroup(rdo1, rdo2, rdo3, rdo4, rdo5);
                    }
                });
                rdo2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRdoGroup(rdo2, rdo1, rdo3, rdo4, rdo5);
                    }
                });
                rdo3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRdoGroup(rdo3, rdo1, rdo2, rdo4, rdo5);
                    }
                });
                rdo4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRdoGroup(rdo4, rdo1, rdo2, rdo3, rdo5);
                    }
                });
                rdo5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRdoGroup(rdo5, rdo1, rdo2, rdo4, rdo3);
                    }
                });

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
        dialog.setCancelable(true);
    }

    public void updateRdoGroup(RadioButton selected, RadioButton unselect1, RadioButton unselect2, RadioButton unselect3, RadioButton unselect4) {
        unselect1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cus_btn_sex));
        unselect1.setTextColor(Color.BLACK);
        unselect2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cus_btn_sex));
        unselect2.setTextColor(Color.BLACK);
        unselect3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cus_btn_sex));
        unselect3.setTextColor(Color.BLACK);
        unselect4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cus_btn_sex));
        unselect4.setTextColor(Color.BLACK);
        selected.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rdo_sex_on));
        selected.setTextColor(Color.WHITE);
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
//        mGoogleSignInClient.signOut()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        startActivity(new Intent(SettingActivity.this,LoginActivity.class));
//                        Toast.makeText(SettingActivity.this, "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

}