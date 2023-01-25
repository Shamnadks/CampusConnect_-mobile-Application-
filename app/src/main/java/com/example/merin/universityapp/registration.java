package com.example.merin.universityapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class registration extends AppCompatActivity {
    EditText na, em, phon, insti, batch, adhar, passwd, confirm;
    ImageView pho;
    Bitmap bitmap = null;
    ProgressDialog pd;
    String url = "";
    Button reg;
    Spinner s1,s2;
    SharedPreferences sh;




        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registration);

            sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            sh.getString("ip", "");
            url = sh.getString("url", "") + "and_registeration";

            na = (EditText) findViewById(R.id.editText4);
            em = (EditText) findViewById(R.id.editText5);
            phon = (EditText) findViewById(R.id.editText8);
            insti = (EditText) findViewById(R.id.editText9);
            s1 = (Spinner) findViewById(R.id.spinner);
            s2 = (Spinner) findViewById(R.id.spinner2);
            batch = (EditText) findViewById(R.id.editText15);
            adhar = (EditText) findViewById(R.id.editText16);
            passwd = (EditText) findViewById(R.id.editText17);
            confirm = (EditText) findViewById(R.id.editText18);

            pho = (ImageView) findViewById(R.id.imageView);
            reg = (Button) findViewById(R.id.button6);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                finish();
                startActivity(intent);
                return;
            }
            pho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 100);
                }
            });
            reg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = na.getText().toString();
                    String email = em.getText().toString();
                    String phone = phon.getText().toString();
                    String institution = insti.getText().toString();
                    String dep=s1.getSelectedItem().toString();
                    String co=s2.getSelectedItem().toString();


                    String batch1 = batch.getText().toString();
                    String adhar1 = adhar.getText().toString();
                ;
                    String p = passwd.getText().toString();
                    String cnfp = confirm.getText().toString();


                    uploadBitmap(name, email, phone, institution , dep , co , batch1 ,adhar1 ,p ,cnfp);
                }
            });
        }


        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

                Uri imageUri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                    pho.setImageBitmap(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //converting to bitarray
        public byte[] getFileDataFromDrawable (Bitmap bitmap){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
        private void uploadBitmap(final String name, final String email, final String co, final String dep, final String batch1, final String adhar1,
                                  final String p, final String cnfp, final String phone, final String institution){


            pd = new ProgressDialog(registration.this);
            pd.setMessage("Uploading....");
            pd.show();
            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            try {
                                pd.dismiss();


                                JSONObject obj = new JSONObject(new String(response.data));

                                if (obj.getString("status").equals("ok")) {
                                    Toast.makeText(getApplicationContext(), "Registration success", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), login.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    SharedPreferences o = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    params.put("na", name);//passing to python
                    params.put("em", email);//passing to python
                    params.put("phon", phone);
                    params.put("insti",institution);
                    params.put("batch",batch1);
                    params.put("adar",adhar1);
                    params.put("co",co);
                    params.put("dep",dep);
                    params.put("p", p);
                    params.put("cnfp", cnfp);
                    return params;
                }


                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    long imagename = System.currentTimeMillis();
                    params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                    return params;
                }
            };

            Volley.newRequestQueue(this).add(volleyMultipartRequest);
        }

    }

