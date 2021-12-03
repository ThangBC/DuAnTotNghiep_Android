package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1.functions.Loading;
import com.example.test1.networking.FunctionUserFAN;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText txtPassNow, txtPassNew, txtRePass;
    Button btnContinue;
    ImageView imgBack;
    FunctionUserFAN functionUserFAN;
    Loading loading;
    TextView tvForgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        txtPassNow = findViewById(R.id.txtPassNow);
        txtPassNew = findViewById(R.id.txtPassNew);
        txtRePass = findViewById(R.id.txtRePass);
        btnContinue = findViewById(R.id.btnContinue);
        imgBack = findViewById(R.id.imgBack);
        tvForgotPass = findViewById(R.id.tvForgotPass);
        tvForgotPass.setPaintFlags(tvForgotPass.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        loading = new Loading();

        functionUserFAN = new FunctionUserFAN();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.show(getSupportFragmentManager(),"loading");
                functionUserFAN.updatePassword(HomeActivity.users.getEmail(),HomeActivity.users.get_id(),txtPassNow.getText().toString()
                        ,txtPassNew.getText().toString(),txtRePass.getText().toString()
                        ,ChangePasswordActivity.this,loading);
            }

        });

        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ChangePasswordActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_forgot_pass);
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

                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
                EditText txtPassForgot = dialog.findViewById(R.id.txtPassForgot);

                int sub = HomeActivity.users.getEmail().length()-16;
                Log.e("sub", String.valueOf(sub));
                String hide = "";
                String firtStr = HomeActivity.users.getEmail().substring(0,5);
                String lastStr = HomeActivity.users.getEmail().substring(HomeActivity.users.getEmail().indexOf("@"));
                for (int i = 1;i<=sub;i++){
                    hide+="*";
                    Log.e("chay","zo");
                }
                String result = firtStr+hide+lastStr;

                txtPassForgot.setHint(result);
                txtPassForgot.setEnabled(false);

                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loading.show(getSupportFragmentManager(),"loading");
                        functionUserFAN.forgotPassWord(HomeActivity.users.getEmail(),ChangePasswordActivity.this,loading,dialog);
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

    }
}