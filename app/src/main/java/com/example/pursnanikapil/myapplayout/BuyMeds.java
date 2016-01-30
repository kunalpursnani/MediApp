package com.example.pursnanikapil.myapplayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;


public class BuyMeds extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment f= null;
    int width;
    private ProgressDialog dialog;
    private static String TAG = MainActivity.class.getSimpleName();
    String un,iu,eAdd;

    private TextView email;
    private TextView username;
    ImageView pp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent1 = getIntent();
        un=intent1.getStringExtra(LoginActivity.EXTRA_MESSAGE);
        iu=intent1.getStringExtra(LoginActivity.EXTRA_MESSAGE2);
        eAdd=intent1.getStringExtra(LoginActivity.EXTRA_MESSAGE3);

        iu="http://kunal.comli.com/image_upload/"+iu;
        getImg(un, iu,eAdd);
        setContentView(R.layout.activity_buy_meds);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        int height = size.y;



        f=new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("width", width);
        f.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, f).commit();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       // LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LinearLayout rl = (LinearLayout) findViewById(R.id.ll);
        //View vi = inflater.inflate(R.layout.activity_buy_meds, null); //log.xml is your file.


        email=(TextView)findViewById(R.id.ema);
        username=(TextView) findViewById(R.id.use);
        pp=(ImageView)findViewById(R.id.profilepic);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


            Intent intent=new Intent(BuyMeds.this,LoginActivity.class);
            startActivity(intent);
            return;
           // super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.buy_meds, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent=new Intent(BuyMeds.this,LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.home) {
            // Handle the camera action

            f = new HomeFragment();
            Bundle args = new Bundle();
            args.putInt("width", width);
            f.setArguments(args);


        } else if (id == R.id.notifications) {

            f = new frage2();

   } else if (id == R.id.buy) {

            f= new frage3();
        } else if (id == R.id.previous_orders) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        if (f != null) {
          //  FragmentManager fragmentManager = getFragmentManager();
          //  fragmentManager.beginTransaction()
            //        .replace(R.id.fragment_container, f).commit();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container,f);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        else{
            // error in creating fragment
            Log.e("LoginActivity", "Error in creating fragment");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getImg(final String uname,String url2, final String ea) {

        ImageRequest request;

        RequestQueue rqe = Volley.newRequestQueue(this);
        final String u=uname;
        final String e=ea;


        //final TextView tv2=(TextView)findViewById(R.id.response);


        //dialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
        System.out.println(url2);
        request = new ImageRequest(url2,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                      //  dialog.dismiss();
                        System.out.println("response");
                        Log.d(TAG, bitmap.toString());
                        email.setText(e);
                        username.setText(u);
                        pp.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {

                       // dialog.dismiss();
                        System.out.println("error");
                        VolleyLog.d(TAG, "ErrorV: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                        // mImageView.setImageResource(R.drawable.image_load_error);
                    }
                });


        rqe.add(request);
    }

}
