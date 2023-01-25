package com.example.merin.universityapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText e1;
    Button b1;
    SharedPreferences sh;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=findViewById(R.id.editText);
        b1=findViewById(R.id.button);
        e1.setText("192.168.8.180");

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1.setText(sh.getString("ip",""));

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipaddress=e1.getText().toString();


//                String ipaddress=ip1.getText().toString();
//        Toast.makeText(this, ipaddress+",", Toast.LENGTH_SHORT).show();
                String url1 = "http://" + ipaddress + ":5000/";
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("ip",ipaddress);
                ed.putString("url",url1);
                ed.commit();
                Intent i=new Intent(getApplicationContext(),login.class);
                startActivity(i);




            }
        });

    }
}
