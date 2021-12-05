package com.example.test1;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
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
    GoogleApiClient mGoogleApiClient;

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
                        startActivity(new Intent(NameActivity.this, LoginActivity.class));
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
            edtName.setError("Vui lòng nhập đúng thông tin!!!");
            return false;
        }else if(name.length()>25){
            edtName.setError("Nhập tên dưới 25 ký tự");
            return false;
        }else {
            edtName.setError(null);
            return true;
        }
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

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
            }
        });
    }
}