package com.example.test1.functions;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.test1.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class LoadImage extends AsyncTask<String,Void, Bitmap> {

    private Context context;
    private ImageView img;

    public LoadImage(Context context, ImageView img) {
        this.context = context;
        this.img = img;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            return BitmapFactory.decodeStream((InputStream) new URL(strings[0]).getContent());
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(bitmap!=null){
            img.setImageBitmap(bitmap);
        }else {
            img.setImageResource(R.drawable.ic_baseline_close_24);
        }
    }
}
