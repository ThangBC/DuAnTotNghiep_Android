package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText txtPassNow, txtPassNew, txtRePass;
    Button btnContinue;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        txtPassNow = findViewById(R.id.txtPassNow);
        txtPassNew = findViewById(R.id.txtPassNew);
        txtRePass = findViewById(R.id.txtRePass);
        btnContinue = findViewById(R.id.btnContinue);
        imgBack = findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangePasswordActivity.this, SettingActivity.class));
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ChangePasswordActivity.this);

                hamDialog(dialog, R.layout.dialog_changepass_setting);
                Button btnGuiMa = dialog.findViewById(R.id.btnGuiMa);
                Button btnConfirmSetting = dialog.findViewById(R.id.btnConfirmSetting);

                btnConfirmSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ChangePasswordActivity.this, "Xác nhận thành công", Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    }
                });

                btnGuiMa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

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
        dialog.setCancelable(true);
    }
}