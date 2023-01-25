package com.example.merin.universityapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class custom_focus_area extends BaseAdapter {

    String[] note, sem;

    private final Context context;

    public custom_focus_area(Context applicationContext, String[] note, String[] sem) {
        this.context = applicationContext;
        this.note= note;
        this.sem = sem;

    }

    @Override
    public int getCount() {
        return note.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (view == null) {
            gridView = new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.activity_custom_focus_area, null);//same class name

        } else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView22);

//        TextView tv3 = (TextView) gridView.findViewById(R.id.textView11);
//        ImageView im = (ImageView) gridView.findViewById(R.id.imageView3);
//
//
//        tv1.setTextColor(Color.RED);//color setting

        tv1.setTextColor(Color.BLACK);


        tv1.setText(sem[i]);

//        tv3.setText(date[i]);

//            ImageView im=(ImageView) gridView.findViewById(R.id.imageView101);
//        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
//        String ip = sh.getString("ip", "");
//        String url = "http://" + ip + ":5000" + note[i];
//        Picasso.with(context).load(url).into(im);//circle

        return gridView;


    }
}

