package com.arp.practicadescarga;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alex on 26/11/2015.
 */
public class Adaptador  extends ArrayAdapter<String> {

    private int recurso;
    private ArrayList<String> enlaces;
    private LayoutInflater i;

    public Adaptador(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        this.recurso=resource;
        this.enlaces=objects;
        i=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh=null;
        if(vh==null){
            vh=new ViewHolder();
            convertView=i.inflate(recurso,null);
            vh.tv=(TextView)convertView.findViewById(R.id.textView);
            convertView.setTag(vh);
        }else {
            vh=(ViewHolder)convertView.getTag();
        }
        vh.tv.setText(enlaces.get(position));
        return convertView;
    }

    private class ViewHolder{
        private TextView tv;
    }
}