package com.example.test1;

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

public class SettingActivity extends AppCompatActivity {

    TextView tvDone,tvAddressSetting,tvMajorSetting,tvShowSetting,tvShowAgeSetting;

    Button btnLogout, btnDeleteAccount,btnSupport,btnAddressSetting,btnShowSetting,btnMajorSetting,btnShowAgeSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        tvDone = findViewById(R.id.tvDone);
        btnLogout = findViewById(R.id.btnLogout);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccout);
        btnSupport = findViewById(R.id.btnSupport);
        btnAddressSetting =findViewById(R.id.btnAddressSetting);
        btnShowSetting =findViewById(R.id.btnShowSetting);
        btnMajorSetting =findViewById(R.id.btnMajorSetting);
        btnShowAgeSetting =findViewById(R.id.btnShowAgeSetting);
        tvAddressSetting = findViewById(R.id.tvAddressSetting);
        tvMajorSetting = findViewById(R.id.tvMajorSetting);
        tvShowSetting = findViewById(R.id.tvShowSetting);
        tvShowAgeSetting = findViewById(R.id.tvShowAgeSetting);

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this,HomeActivity.class));
                HomeActivity.fragment = new ProfileFragment();
                HomeActivity.selectedItem = R.id.proId;
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SettingActivity.this);
                hamDialog(dialog,R.layout.dialog_confirm);

                Button btnCancelDeleteDialog = dialog.findViewById(R.id.btnCancelDeleteDialog);
                Button btnDeletetDialog = dialog.findViewById(R.id.btnDeletetDialog);
                TextView tvTitleDeleteDialog = dialog.findViewById(R.id.tvTitleDeleteDialog);
                TextView tvBodyDeleteDialog = dialog.findViewById(R.id.tvBodyDeleteDialog);

                tvTitleDeleteDialog.setText("Đăng xuất");
                tvBodyDeleteDialog.setText("Bạn có chắc chắn muốn đăng xuất ?");
                btnDeletetDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(SettingActivity.this,LoginActivity.class));
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

                hamDialog(dialog,R.layout.dialog_confirm);

                Button btnCancelDeleteDialog = dialog.findViewById(R.id.btnCancelDeleteDialog);
                Button btnDeletetDialog = dialog.findViewById(R.id.btnDeletetDialog);
                TextView tvTitleDeleteDialog = dialog.findViewById(R.id.tvTitleDeleteDialog);
                TextView tvBodyDeleteDialog = dialog.findViewById(R.id.tvBodyDeleteDialog);

                tvTitleDeleteDialog.setText("Xóa tài khoản");
                tvBodyDeleteDialog.setText("Bạn có chắc chắn muốn xóa tài khoản ?");
                btnDeletetDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(SettingActivity.this,LoginActivity.class));
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

                hamDialog(dialog,R.layout.dialog_support);

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

                hamDialog(dialog,R.layout.dialog_address_setting);

                RadioButton rdoHaNoiSetting = dialog.findViewById(R.id.rdoHaNoiSetting);
                RadioButton rdoHCMSetting = dialog.findViewById(R.id.rdoHCMSetting);
                RadioButton rdoDaNangSetting = dialog.findViewById(R.id.rdoDaNangSetting);
                RadioButton rdoCanThoSetting = dialog.findViewById(R.id.rdoCanThoSetting);
                RadioButton rdoTayNguyenSetting = dialog.findViewById(R.id.rdoTayNguyenSetting);
                Button btnCancelSetting = dialog.findViewById(R.id.btnCancelSetting);
                Button btnConfirmSetting = dialog.findViewById(R.id.btnConfirmSetting);



                dialog.show();
            }
        });
    }

    public void hamDialog(Dialog dialog,int giaodien){
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(giaodien);
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
    }


}