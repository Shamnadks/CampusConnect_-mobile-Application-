package com.example.merin.universityapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class view_subject extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView li;
    SharedPreferences sh;
    String[] sub_id, sub, staff, sem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subject);

        li = findViewById(R.id.lv);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String url = sh.getString("url", "") + "and_view_subject";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js = jsonObj.getJSONArray("data");//from python
                                sub_id = new String[js.length()];
                                sub = new String[js.length()];
                                sem = new String[js.length()];
                                staff = new String[js.length()];


                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    sub_id[i] = u.getString("subject_id");//dbcolumn name in double quotes
                                    sub[i] = u.getString("subject");

                                    sem[i] = u.getString("semester");
                                    staff[i] = u.getString("staff_name");


                                }
                                li.setAdapter(new custom_view_subject(getApplicationContext(), sub, sem, staff) {
                                    @Override
                                    public int getCount() {
                                        return super.getCount();
                                    }
                                });//custom_view_service.xml and li is the listview object


                            } else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {

            //                value Passing android to python
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", sh.getString("lid", ""));//passing to python
                return params;
            }
        };


        int MY_SOCKET_TIMEOUT_MS = 100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
        li.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final String sub = sub_id[position].toString();

//    pos =i;
//         final String akid=aid[pos];


        AlertDialog.Builder builder = new AlertDialog.Builder(view_subject.this);
        builder.setTitle("options");
        builder.setItems(new CharSequence[]
                        {"view notes", "send doubts", "view doubt status", " previous question pappers", "send suggestion","focus area", "Cancel"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:

                            {
                                sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor ed = sh.edit();
                                ed.putString("sub", sub);

                                ed.commit();

                                Intent nt = new Intent(getApplicationContext(), view_notes.class);
                                startActivity(nt);

                            }


                            break;

                            case 1: {
                                sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor ed = sh.edit();
                                ed.putString("sub", sub);

                                ed.commit();

                                Intent nt = new Intent(getApplicationContext(), send_doubt.class);
                                startActivity(nt);


                            }


                            break;

                            case 2: {
                                sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor ed = sh.edit();
                                ed.putString("sub", sub);

                                ed.commit();

                                Intent nt = new Intent(getApplicationContext(), view_dbt_status.class);
                                startActivity(nt);


                            }
                            break;

                            case 3: {
                                sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor ed = sh.edit();
                                ed.putString("sub", sub);

                                ed.commit();

                                Intent nt = new Intent(getApplicationContext(), view_pqs.class);
                                startActivity(nt);


                            }
                            break;
                            case 4: {
                                sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor ed = sh.edit();
                                ed.putString("sub", sub);

                                ed.commit();

                                Intent nt = new Intent(getApplicationContext(), send_suggestion.class);
                                startActivity(nt);


                            }
                            break;
                            case 5: {
                                sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor ed = sh.edit();
                                ed.putString("sub", sub);

                                ed.commit();

                                Intent nt = new Intent(getApplicationContext(), focus_area.class);
                                startActivity(nt);


                            }
                            break;




                            case 6:
                                break;


                        }
                    }
                });
        builder.create().show();


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
