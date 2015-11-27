package com.arp.practicadescarga;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Imagen extends AppCompatActivity {

    private ImageView iv;
    private String enlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen);
        iv=(ImageView)findViewById(R.id.imageView);
        Bundle b=getIntent().getExtras();
        enlace = b.getString("enlace");
        new Hebra().execute();
    }

    class Hebra extends AsyncTask<String, Void,Bitmap>{
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(enlace);
                URLConnection urlCon = url.openConnection();
                InputStream is = urlCon.getInputStream();
                Bitmap bt = BitmapFactory.decodeStream(is);
                return bt;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
           return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            iv.setImageBitmap(bitmap);
        }
    }
}
