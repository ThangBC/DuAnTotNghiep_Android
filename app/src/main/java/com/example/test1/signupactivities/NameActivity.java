package com.example.test1.signupactivities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test1.LoginActivity;
import com.example.test1.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.material.textfield.TextInputLayout;

public class NameActivity extends AppCompatActivity {
    Button btnContinue;
    TextInputLayout edtName;
    String name;
    TextView tvBackName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        btnContinue = findViewById(R.id.btnContinue);
        edtName = findViewById(R.id.edtName);
        tvBackName = findViewById(R.id.tvBackName);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        tvBackName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(NameActivity.this);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView( R.layout.dialog_confirm);
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

                Button btnCancelDeleteDialog = dialog.findViewById(R.id.btnCancelDeleteDialog);
                Button btnDeletetDialog = dialog.findViewById(R.id.btnDeletetDialog);
                TextView tvTitleDeleteDialog = dialog.findViewById(R.id.tvTitleDeleteDialog);
                TextView tvBodyDeleteDialog = dialog.findViewById(R.id.tvBodyDeleteDialog);

                tvTitleDeleteDialog.setText("Quay lại");
                tvBodyDeleteDialog.setText("Bạn có chắc chắn muốn quay lại ?");
                btnDeletetDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GoogleSignInOptions gso = new GoogleSignInOptions.
                                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                                build();
                        GoogleSignInClient googleSignInClient1 = GoogleSignIn.getClient(NameActivity.this, gso);
                        googleSignInClient1.signOut();
                        startActivity(new Intent(NameActivity.this, LoginActivity.class));
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

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateName()) {
                    Intent intent = new Intent(NameActivity.this,BirthdayActivity.class);
                    intent.putExtra("email",email);
                    intent.putExtra("name",name);
                    startActivityForResult(intent,1);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });
    }

    public boolean validateName(){
        name = edtName.getEditText().getText().toString();
        if(name.isEmpty()){
            edtName.setError("Không được để trống");
            return false;
        }else if(!name.matches("^[\\p{L} .'-]+$")){
            edtName.setError("Vui lòng không nhập ký tự đặc biệt và số");
            return false;
        }else if(name.length()>25){
            edtName.setError("Vui lòng nhập tên dưới 25 ký tự");
            return false;
        }else if(name.length()<5){
            edtName.setError("Vui lòng nhập tối thiểu 5 ký tự");
            return false;
        } else {
            edtName.setError(null);
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                edtName.getEditText().setText(result);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GoogleSignInOptions gso = new GoogleSignInOptions.
                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                build();
        GoogleSignInClient googleSignInClient1 = GoogleSignIn.getClient(this, gso);
        googleSignInClient1.signOut();
        startActivity(new Intent(NameActivity.this, LoginActivity.class));
    }
}