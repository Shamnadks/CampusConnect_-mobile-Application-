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

public abstract class custom_view_subject extends BaseAdapter {
    String [] subject,sem,staff;

    private final Context context;

    public custom_view_subject(Context applicationContext, String[] subject, String[] sem, String[] staff)
    {
        this.context = applicationContext;

        this.subject = subject;
        this.sem = sem;
        this.staff = staff;

    }

    @Override
    public int getCount () {
        return subject.length;
    }

    @Override
    public Object getItem ( int i){
        return null;
    }

    @Override
    public long getItemId ( int i){
        return 0;
    }

    @Override
    public View getView (int i, View view, ViewGroup viewGroup){
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (view == null) {
            gridView = new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.activity_custom_view_subject, null);//same class name

        } else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView12);

        TextView tv2 = (TextView) gridView.findViewById(R.id.textView13);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView14);



        tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.RED);//color setting

        tv3.setTextColor(Color.BLACK);


        tv1.setText(subject[i]);
        tv2.setText(sem[i]);

        tv3.setText(staff[i]);

//            ImageView im=(ImageView) gridView.findViewById(R.id.imageView101);

        return gridView;


    }

}