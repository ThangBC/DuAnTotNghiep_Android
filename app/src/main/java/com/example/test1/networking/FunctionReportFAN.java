package com.example.test1.networking;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.test1.adapters.ReportAdapter;
import com.example.test1.models.Reports;

import org.json.JSONObject;

public class FunctionReportFAN {

    public void insertReport(Context context, Reports reports) {

        Log.e("reportList",reports.getEmailReport()+reports.getEmailReported()+reports.getTitle()+reports.getContent());

        AndroidNetworking.upload("https://poly-dating.herokuapp.com/api/reports/insert")
                .addMultipartParameter("emailReport",reports.getEmailReport())
                .addMultipartParameter("emailReported",reports.getEmailReported())
                .addMultipartParameter("title",reports.getTitle())
                .addMultipartParameter("content",reports.getContent())
                .addMultipartFile("images",reports.getImages())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.toString().contains("201")){
                            Toast.makeText(context, "Báo cáo của bạn đã được gửi", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Gửi báo cáo thất bại", Toast.LENGTH_SHORT).show();

                        }
                        Log.e("aaa", "Trả về" + response);
                    }
                    @Override
                    public void onError(ANError anError) {
                        ReportAdapter.flagchkReport = false;
                        Toast.makeText(context, "Gửi báo cáo thất bại", Toast.LENGTH_SHORT).show();
                        Log.e("aa", "Lỗi" + anError.getErrorBody());
                    }
                });
    }



}
