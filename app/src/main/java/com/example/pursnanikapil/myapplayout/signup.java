package com.example.pursnanikapil.myapplayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import butterknife.ButterKnife;


public class signup extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    ProgressDialog prgDialog;
    String encodedString;
    String fileName="";
    private static int RESULT_LOAD_IMG = 1;
    ImageView imgv;
    EditText et;
    EditText unam;
    EditText pt;
    String eAdd;
    String pwd;
    String uname;
    int flag=0;

    EditText _nameText;
    EditText _emailText;
    EditText _passwordText;

    Button _signupButton;

    TextView _loginLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _nameText=(EditText)findViewById(R.id.input_name);
        _emailText=(EditText)findViewById(R.id.input_email);
        _passwordText=(EditText)findViewById(R.id.input_password);
        _signupButton=(Button)findViewById(R.id.btn_signup);
        _loginLink=(TextView)findViewById(R.id.link_login);




        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });


        et =(EditText) findViewById(R.id.input_email);
        unam = (EditText) findViewById(R.id.input_name);
        pt = (EditText) findViewById(R.id.input_password);
        prgDialog = new ProgressDialog(this);
        // Set Cancelable as False
       // clickListener();
        prgDialog.setCancelable(true);

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

/*
    public void clickListener() {
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                eAdd = et.getText().toString();
                pwd = pt.getText().toString();
                uname=unam.getText().toString();
// hide keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(pt.getWindowToken(), 0);


                if (isEmailValid(eAdd)) {


                    uploadData();

                } else {
                    Toast.makeText(getBaseContext(), "Wrong e-mail address!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }


*/


    public static boolean isEmailValid(String email) {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }



    // When Image is selected from Gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
            imgv = (ImageView) findViewById(R.id.imgView);
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            String fileNameSegments[] = picturePath.split("/");
            fileName = fileNameSegments[fileNameSegments.length - 1];

            Bitmap myImg = BitmapFactory.decodeFile(picturePath);
            imgv.setImageBitmap(myImg);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Must compress the Image to reduce image size to make upload easy
            myImg.compress(Bitmap.CompressFormat.PNG, 50, stream);
            byte[] byte_arr = stream.toByteArray();
            // Encode Image to String
            encodedString = Base64.encodeToString(byte_arr, 0);

            uploadImage();
        }
    }




    /**
     * API call for upload selected image from gallery to the server
     */
    public void uploadImage() {

        RequestQueue rq = Volley.newRequestQueue(this);
        String url = "http://kunal.comli.com/upload_image.php";
        Log.d("URL", url);
        prgDialog.show(this, "", "Uploading. Please wait...", true,true);
        JSONObject insert = new JSONObject();
        try {

            insert.put("image", encodedString);
            insert.put("filename", fileName);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest JRequest = new JsonObjectRequest(url, insert, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.e("RESPONSE", response.toString());
                    JSONObject json = new JSONObject(response.toString());
                    prgDialog.dismiss();
                    Toast.makeText(getBaseContext(),
                            "Image Uploaded Successfully", Toast.LENGTH_SHORT)
                            .show();

                } catch (JSONException e) {
                    Log.d("JSON Exception", e.toString());
                    prgDialog.dismiss();
                    Toast.makeText(getBaseContext(),
                            "No Response...Try Again",
                            Toast.LENGTH_LONG).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", "Error [" + error + "]");
                prgDialog.dismiss();
                Toast.makeText(getBaseContext(),
                        "Image Size too big!! \n Try Again...", Toast.LENGTH_LONG).show();
            }
        });/* {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("image", encodedString);
              //  params.put("filename", fileName);

                return params;

            }

        };*/
        rq.add(JRequest);
    }

    public void uploadData() {
        String url = "http://kunal.comli.com/upload2.php";
        final String TAG = MainActivity.class.getSimpleName();
   flag=0;

        JSONObject insert = new JSONObject();
        try {
            insert.put("email", eAdd);
            insert.put("password", pwd);
            insert.put("username",uname);
            insert.put("image",fileName);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RequestQueue rq = Volley.newRequestQueue(this);
        JsonArrayRequest req = new JsonArrayRequest(url, insert,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json objectString jsonRespons = response.toString();
                            String jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject res = (JSONObject) response.getJSONObject(i);
                                jsonResponse=res.getString("result");
                            }

                            prgDialog.dismiss();
                            Toast.makeText(getApplicationContext(),
                                    jsonResponse,
                                    Toast.LENGTH_LONG).show();
                            if(jsonResponse=="Successfully Registered"){
                                flag=1;
                            }
                            // Log.d("VolleyTest", person.toString());


                        } catch (JSONException e) {
                            prgDialog.dismiss();
                            e.printStackTrace();
                            VolleyLog.d(TAG, "ErrorJ: " + e.getMessage());
                            Toast.makeText(getApplicationContext(),
                                    "ErrorJ: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            Log.d("VolleyTest", e.getMessage());
                        }
                        prgDialog.dismiss();


                    }
                }, new Response.ErrorListener() {
            @Override

            public void onErrorResponse(VolleyError error) {

                VolleyLog.d(TAG, "ErrorV: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                prgDialog.dismiss();

            }
        });

        rq.add(req);
    }



    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // Dismiss the progress bar when application is closed
        if (prgDialog != null) {
            prgDialog.dismiss();
        }
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(signup.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        eAdd = et.getText().toString();
        pwd = pt.getText().toString();
        uname=unam.getText().toString();
// hide keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(pt.getWindowToken(), 0);
        uploadData();
         if(flag==1) {
             finish();
         }
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }


    public void onBackPressed() {
        // do something on back.
        Intent intent=new Intent(signup.this,LoginActivity.class);
        startActivity(intent);
        return;
    }

}
