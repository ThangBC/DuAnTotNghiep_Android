package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1.fragments.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserDetailActivity extends AppCompatActivity {

    FloatingActionButton flatBack;
    Button btnReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        flatBack = findViewById(R.id.flatBack);
        btnReport = findViewById(R.id.btnReport);

        flatBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserDetailActivity.this,HomeActivity.class));
                HomeActivity.fragment = new HomeFragment();
                HomeActivity.selectedItem = R.id.homeId;
            }
        });

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(UserDetailActivity.this);

                hamDialog(dialog,R.layout.dialog_report);

                Button btnRp1 = dialog.findViewById(R.id.btnRp1);
                Button btnRp2 = dialog.findViewById(R.id.btnRp2);
                Button btnRp3 = dialog.findViewById(R.id.btnRp3);
                Button btnRp4 = dialog.findViewById(R.id.btnRp4);
                Button btnRp5 = dialog.findViewById(R.id.btnRp5);

                btnRp1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog = new Dialog(UserDetailActivity.this);
                        hamDialog(dialog,R.layout.dialog_support);

                        TextView tv1 = dialog.findViewById(R.id.tv1);
                        EditText txtTitleSp = dialog.findViewById(R.id.txtTitleSp);
                        View view3 = dialog.findViewById(R.id.view3);
                        Button btnAcceptReport = dialog.findViewById(R.id.btnSendSupport);
                        Button btnCancelReport = dialog.findViewById(R.id.btnCancelSupport);

                        tv1.setText("Tài khoản giả mạo");
                        txtTitleSp.setVisibility(View.GONE);
                        view3.setVisibility(View.GONE);

                        btnAcceptReport.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(UserDetailActivity.this, "Báo cáo của bạn đã được gửi", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                        btnCancelReport.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });

                btnRp2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog = new Dialog(UserDetailActivity.this);
                        hamDialog(dialog,R.layout.dialog_support);

                        TextView tv1 = dialog.findViewById(R.id.tv1);
                        EditText txtTitleSp = dialog.findViewById(R.id.txtTitleSp);
                        View view3 = dialog.findViewById(R.id.view3);
                        Button btnAcceptReport = dialog.findViewById(R.id.btnSendSupport);
                        Button btnCancelReport = dialog.findViewById(R.id.btnCancelSupport);

                        tv1.setText("Ngôn từ gây thù ghét");
                        txtTitleSp.setVisibility(View.GONE);
                        view3.setVisibility(View.GONE);

                        btnAcceptReport.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(UserDetailActivity.this, "Báo cáo của bạn đã được gửi", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                        btnCancelReport.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });

                btnRp3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog = new Dialog(UserDetailActivity.this);
                        hamDialog(dialog,R.layout.dialog_support);

                        TextView tv1 = dialog.findViewById(R.id.tv1);
                        EditText txtTitleSp = dialog.findViewById(R.id.txtTitleSp);
                        View view3 = dialog.findViewById(R.id.view3);
                        Button btnAcceptReport = dialog.findViewById(R.id.btnSendSupport);
                        Button btnCancelReport = dialog.findViewById(R.id.btnCancelSupport);

                        tv1.setText("Tiểu sử không hợp lệ");
                        txtTitleSp.setVisibility(View.GONE);
                        view3.setVisibility(View.GONE);

                        btnAcceptReport.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(UserDetailActivity.this, "Báo cáo của bạn đã được gửi", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                        btnCancelReport.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });

                btnRp4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog = new Dialog(UserDetailActivity.this);
                        hamDialog(dialog,R.layout.dialog_support);

                        TextView tv1 = dialog.findViewById(R.id.tv1);
                        EditText txtTitleSp = dialog.findViewById(R.id.txtTitleSp);
                        View view3 = dialog.findViewById(R.id.view3);
                        Button btnAcceptReport = dialog.findViewById(R.id.btnSendSupport);
                        Button btnCancelReport = dialog.findViewById(R.id.btnCancelSupport);

                        tv1.setText("Âm mưu phản động");
                        txtTitleSp.setVisibility(View.GONE);
                        view3.setVisibility(View.GONE);

                        btnAcceptReport.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(UserDetailActivity.this, "Báo cáo của bạn đã được gửi", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                        btnCancelReport.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });

                btnRp5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog = new Dialog(UserDetailActivity.this);
                        hamDialog(dialog,R.layout.dialog_support);

                        TextView tv1 = dialog.findViewById(R.id.tv1);
                        EditText txtTitleSp = dialog.findViewById(R.id.txtTitleSp);
                        View view3 = dialog.findViewById(R.id.view3);
                        Button btnAcceptReport = dialog.findViewById(R.id.btnSendSupport);
                        Button btnCancelReport = dialog.findViewById(R.id.btnCancelSupport);

                        tv1.setText("Ảnh hồ sơ không phù hợp");
                        txtTitleSp.setVisibility(View.GONE);
                        view3.setVisibility(View.GONE);

                        btnAcceptReport.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(UserDetailActivity.this, "Báo cáo của bạn đã được gửi", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                        btnCancelReport.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                });

                dialog.show();
            }
        });
    }

    public void hamDialog(Dialog dialog, int giaodien){
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