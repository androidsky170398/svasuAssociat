package com.example.svasuassociate;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.svasuassociate.Adapter.BannerAdapter;
import com.example.svasuassociate.Helper.ViewDialog;
import com.example.svasuassociate.Util.Api_Urls;
import com.example.svasuassociate.entity.Banner;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int dotscount;
    private ImageView[] dots;
    private final static int RESOLVE_HINT = 1011;
    String mobNumber;
    ViewDialog viewDialog;
    ViewPager viewPager;
    TextInputEditText mobile,edt_pass;
    LinearLayout sliderDotspanel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mobile = findViewById(R.id.edt_mobile);
        edt_pass = findViewById(R.id.edt_pass);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        forBanner();
        findViewById(R.id.btn_sendotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobile.getText().toString().trim().isEmpty() || mobile.getText().toString().equalsIgnoreCase("")) {
//                    com.example.svasuassociate.Constants.showCustomToastInCenter(MainActivity.this, "Please enter Password");
                       Toast.makeText(MainActivity.this, "Please enter mobile no.", Toast.LENGTH_SHORT).show();
                } else if (mobile.getText().toString().trim().length() != 10) {
                    Toast.makeText(MainActivity.this, "Please Enter 10 digit mobile no.", Toast.LENGTH_SHORT).show();
                } else if (edt_pass.getText().toString().trim().isEmpty() || edt_pass.getText().toString().equalsIgnoreCase("")) {
//                    com.example.svasuassociate.Constants.showCustomToastInCenter(MainActivity.this, "Please enter Password");
                       Toast.makeText(MainActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(MainActivity.this, dashboard.class);
                    startActivity(i);
                    //Toast.makeText(MainActivity.this, "You succesfully login", Toast.LENGTH_SHORT).show();
//                    isValid();
                }
                //  startActivity(new Intent(LoginActivity.this, OtpVerify.class));
            }
        });
    }

//    public void isValid() {
//        if (connectionDetector.isConnected()) {
//            sendOtp();
//        } else {
//            Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void sendOtp() {
//        Toast.makeText(MainActivity.this, "You succesfully login", Toast.LENGTH_SHORT).show();
//    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        finishAffinity();

    }


    public void forBanner() {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        // progressDialog.showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api_Urls.BASE_URL + "banner", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.hide();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        String data = jsonObject.getString("data");
                        if (data != null) {
                            final List<Banner> bannerList = Banner.createListFromJson(data);
                            if (bannerList != null && bannerList.size() > 0) {
                                final BannerAdapter viewPagerAdapter = new BannerAdapter(MainActivity.this, bannerList);
                                viewPager.setAdapter(viewPagerAdapter);
                                viewPager.setAdapter(viewPagerAdapter);
                                dotscount = viewPagerAdapter.getCount();
                                dots = new ImageView[dotscount];

                                for (int i = 0; i < dotscount; i++) {
                                    try {
                                        dots[i] = new ImageView(getApplicationContext());
                                        dots[i].setImageDrawable(getResources().getDrawable(R.drawable.non_active_dot));

                                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                        params.setMargins(8, 0, 8, 0);
                                        sliderDotspanel.addView(dots[i], params);
                                    } catch (Exception e) {

                                    }
                                }
                                try {
                                    dots[0].setImageDrawable(getResources().getDrawable(R.drawable.active_dot));
                                } catch (Exception e) {

                                }

                                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                    @Override
                                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                    }

                                    @Override
                                    public void onPageSelected(int position) {
                                        try {
                                            for (int i = 0; i < dotscount; i++) {
                                                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.non_active_dot));
                                            }
                                            dots[position].setImageDrawable(getResources().getDrawable(R.drawable.active_dot));
                                        } catch (Exception e) {

                                        }
                                    }

                                    @Override
                                    public void onPageScrollStateChanged(int state) {

                                    }
                                });
                                if (bannerList.size() > 1) {
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            handler.postDelayed(this, 5000);
                                            if (viewPager.getCurrentItem() == bannerList.size() - 1) {
                                                viewPager.setCurrentItem(0);
                                            } else {
                                                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                                            }
                                        }
                                    }, 5000);
                                }
                            }
                        }
                    } else {
                        Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//jsonObject.getString("error_code");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //  Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                viewDialog.showDialog();
                Toast.makeText(MainActivity.this, "Internet connection is slow Or no internet connection", Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

    }
}