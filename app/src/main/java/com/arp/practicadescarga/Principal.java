package com.arp.practicadescarga;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Principal extends AppCompatActivity {
    private ListView lv;
    private ArrayList<String> enlaces;
    private EditText et;
    private Adaptador ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        lv=(ListView)findViewById(R.id.listView);
        et=(EditText)findViewById(R.id.etPagina);
        enlaces=new ArrayList<>();
        ad=new Adaptador(this,R.layout.lista_detalle,enlaces);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Principal.this, Imagen.class);
                Bundle b=new Bundle();
                b.putString("enlace", enlaces.get(position));
                i.putExtras(b);
                startActivity(i);
            }
        });
    }

    public void descargar(View v){
        enlaces=new ArrayList<>();
        new Hilo().execute(et.getText().toString());
    }

    class Hilo extends AsyncTask<String,Void,ArrayList<String>>{

        private ProgressDialog pd;
        private String linea;

        @Override
        protected void onPreExecute() {
            pd=ProgressDialog.show(Principal.this,"","Descargando");
            super.onPreExecute();
        }
        @Override
        protected ArrayList<String> doInBackground(String... params) {
            try {
                URL url = new URL("http://"+params[0]);
                BufferedReader in=new BufferedReader(new InputStreamReader(url.openStream()));
                while((linea=in.readLine())!=null){
                    if(linea.contains("<img src=") && !linea.contains("<a") && !linea.contains(".gif")){
                        if(linea.contains(".jpg") || linea.contains(".png")){
                            int indexOf = linea.indexOf('"');
                            String enlace=linea.substring(indexOf+1);
                            enlaces.add(enlace.substring(0,enlace.indexOf('"')));
                        }
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            ad.notifyDataSetChanged();
            pd.dismiss();
        }
    }
}
