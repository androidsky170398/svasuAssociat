package com.example.svasuassociate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.svasuassociate.Util.Api_Urls;
import com.example.svasuassociate.Util.CommonUtill;
import com.example.svasuassociate.fragments.HomeFragment;
import com.example.svasuassociate.storage.SharedPrefereceUserData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.svasuassociate.Constants.LOGINKEY;

public class dashboard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener  {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    DrawerLayout mdrawer;
    ActionBarDrawerToggle mtoggle;
    NavigationView nvmain;
    static TextView notifCount;
    static int mNotifCount = 0;
    TextView tv_header;
    BottomNavigationView navigation;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    String contactusurl;

    TextView tv_Profile, tv_Share, tv_accountdetails,
            tv_ContactUs, tv_MyLeft, tv_MyRight, tv_MatchingProfit, tv_History, tv_Registration, tv_NetworkTree, tv_logout;

    ImageView civ_profile_image;
    TextView txt_username, txt_usergmail;
    String fileName, SourcefilePath;
    TextView tv_change_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        findViewByIds();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawatv_MyOfferble.ic_menu));
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        tv_change_password=(TextView)findViewById(R.id.tv_change_password);
        navigation.setOnNavigationItemSelectedListener(this);
        HomeFragment homeFragment = new HomeFragment();
        loadFragment(homeFragment);
        navigation.getItemIconTintList();
        navigation.setSelectedItemId(R.id.bnav_Home);
        contactUs();
        navigationItemClicks();
        getDetails();
        //  isStoragePermissionGranted();
        checkAndRequestPermissions();
        civ_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
            }
        });
        tv_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), dashboard.class);
                startActivity(intent);
            }
        });
/*

        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkConnectionHelper.isOnline(dashboard.this)) {


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                            srl_refresh.setRefreshing(false);
                        }
                    }, 2000);
                } else {
                    Toast.makeText(dashboard.this, "Please check your internet connection! try again...", Toast.LENGTH_SHORT).show();
                }
            }
        });
*/

    }

    private void findViewByIds() {
        tv_Profile = findViewById(R.id.tv_Profile);
        tv_Share = findViewById(R.id.tv_Share);
        tv_ContactUs = findViewById(R.id.tv_ContactUs);
        tv_MyLeft = findViewById(R.id.tv_MyLeft);
        tv_MyRight = findViewById(R.id.tv_MyRight);
        tv_MatchingProfit = findViewById(R.id.tv_MatchingProfit);
        tv_History = findViewById(R.id.tv_History);
        civ_profile_image = findViewById(R.id.civ_profile_image);
        txt_username = findViewById(R.id.txt_username);
        txt_usergmail = findViewById(R.id.txt_usergmail);
        tv_Registration = findViewById(R.id.tv_Registration);
        tv_NetworkTree = findViewById(R.id.tv_NetworkTree);
        //srl_refresh = findViewById(R.id.srl_refresh);
        tv_accountdetails = findViewById(R.id.tv_accountdetails);
        tv_logout = findViewById(R.id.tv_logout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigation.setSelectedItemId(R.id.bnav_Home);
        getDetails();
    }

    private void setNotifCount(int count) {
        mNotifCount = count;
        invalidateOptionsMenu();
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        //transaction.addToBackStack(null);
        fragmentManager.popBackStack();
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opt_menu_owner, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (toggle.onOptionsItemSelected(item)) {
            return true;
       /*     drawer.closeDrawers();
            AlertDialog.Builder builder = new AlertDialog.Builder(dashboard.this);
            builder.setMessage("Are you sure want to logout?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Constants.clearSavedPreferences(dashboard.this, LOGINKEY);
                    final SharedPreferences.Editor sharedPreferences = new SharedPrefereceUserData(dashboard.this).getRemove();
                    sharedPreferences.putString("User", null);
                    sharedPreferences.clear();
                    sharedPreferences.commit();
                    startActivity(new Intent(dashboard.this, LoginActivity.class));
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(dashboard.this);
        builder.setMessage("Are you sure want to exit?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        switch (menuItem.getItemId()) {
            case R.id.bnav_Home:
                HomeFragment homeFragment = new HomeFragment();
                loadFragment(homeFragment);
                break;
           /* case R.id.bnav_wallet:
                startActivity(new Intent(dashboard.this, UserProfile.class));
                 *//*  HistoryFragment historyFragment=new HistoryFragment();
                loadFragment(historyFragment);
                tv_header.setText("My Wallet");*//*
                break;*/
            case R.id.bnav_History:
                startActivity(new Intent(dashboard.this, dashboard.class));
                   /* WalletFragment walletFragment=new WalletFragment();
                loadFragment(walletFragment);
                tv_header.setText("History");*/
                break;
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    void navigationItemClicks() {
        tv_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, dashboard.class));
                drawer.closeDrawers();
            }
        });
        tv_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent;
          /*      Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sunrise_app_icon);
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Share.png";
                OutputStream out = null;
                File file = new File(path);
                try {
                    out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                path = file.getPath();
                //   Uri bmpUri = Uri.parse("file://" + path);
                Uri bmpUri = FileProvider.getUriForFile(dashboard.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        new File("file://" + path));*/
                shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                //shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey please check this application " + "https://play.google.com/store/apps/details?id=" + getPackageName());
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.setType("text/plan");
                startActivity(Intent.createChooser(shareIntent, "Share with"));


/*
                Intent shareIntent;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sunrise_app_icon);
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "share.png";
                OutputStream out = null;
                File file = new File(path);
                try {
                    out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                path = file.getPath();
                Uri bmpUri = Uri.parse("file://" + path);
                shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey please check this application " + "https://play.google.com/store/apps/details?id=" + getPackageName());
                shareIntent.setType("image/png");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "Share with"));*/
                // CommonUtill.shareApp(dashboard.this, "Hey Download the Real State Services App for become a member of our coumpany", getPackageName());

                drawer.closeDrawers();
            }
        });
        tv_ContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CommonUtill.openBrowser(dashboard.this, contactusurl);
                drawer.closeDrawers();
            }
        });
        tv_MyLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, dashboard.class).putExtra("from_where", "left"));
                drawer.closeDrawers();
            }
        });
        tv_MyRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, dashboard.class).putExtra("from_where", "right"));
                drawer.closeDrawers();
            }
        });

        tv_MatchingProfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, dashboard.class).putExtra("from_where", "dash"));
                drawer.closeDrawers();
            }
        });
        tv_Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, MainActivity.class));
                drawer.closeDrawers();
            }
        });
        tv_History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, dashboard.class));
                drawer.closeDrawers();
            }
        });
        tv_NetworkTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, dashboard.class));
                drawer.closeDrawers();
            }
        });
        tv_accountdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, dashboard.class));
                drawer.closeDrawers();
            }
        });
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                AlertDialog.Builder builder = new AlertDialog.Builder(dashboard.this);
                builder.setMessage("Are you sure want to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Constants.clearSavedPreferences(dashboard.this, LOGINKEY);
                        final SharedPreferences.Editor sharedPreferences = new SharedPrefereceUserData(dashboard.this).getRemove();
                        sharedPreferences.putString("User", null);
                        sharedPreferences.clear();
                        sharedPreferences.commit();
                        startActivity(new Intent(dashboard.this, dashboard.class));
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        Bitmap bitmap;
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        BitmapFactory.Options bitmapOptionsForCompression = new BitmapFactory.Options();

                        bitmap = BitmapFactory.decodeFile("", bitmapOptions);

                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());


                        FileOutputStream fos = null;
                        ByteArrayOutputStream streamForCompression = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, streamForCompression);
                        byte[] bArrayForCompression = streamForCompression.toByteArray();
                        File CompressedDir = getDir("Images", 0);
                        fileName = System.currentTimeMillis() + ".jpg";
                        File fileNew = new File(CompressedDir, fileName);
                        try {
                            fos = new FileOutputStream(fileNew);
                            fos.write(bArrayForCompression);
                            fos.flush();
                            fos.close();
                            SourcefilePath = fileNew.toString();
                            civ_profile_image.setImageBitmap(bitmap);
                        } catch (Exception e) {
                            Log.e("Error : ", e.getMessage());
                        }
                        imageUpload(SourcefilePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(dashboard.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void imageUpload(String sourcefilePath) {
        try {
            Ion.with(this).
                    load(Api_Urls.BASE_URL + "pic_upload")
                    .setMultipartParameter("usertype", "agent")
                    .setMultipartParameter("agent_id", Constants.getSavedPreferences(dashboard.this, LOGINKEY, null))
                    .setMultipartFile("image",
                            new File(sourcefilePath.trim())).asJsonObject()
                    .setCallback(
                            new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.getAsJsonObject().get("status") != null && result.getAsJsonObject().get("status").toString().equalsIgnoreCase("true")) {
                                        //  getDetails();
                                        Toast.makeText(dashboard.this, result.getAsJsonObject().get("message") + "", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(dashboard.this, result.getAsJsonObject().get("message") + "", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
        } catch (Exception e) {
            // Do something about exceptions
            Toast.makeText(dashboard.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            System.out.println("exception: " + e);
        }
    }

    public void contactUs() {
        final ProgressDialog progressDialog = new ProgressDialog(dashboard.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api_Urls.BASE_URL + "contact_us", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("status")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        contactusurl = jsonObject1.getString("contact_us");
                    }
                } catch (JSONException e) {
                    Toast.makeText(dashboard.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                //  Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(dashboard.this);
        requestQueue.add(stringRequest);
    }


    public void getDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(dashboard.this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String Url = "view-profile";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api_Urls.BASE_URL + Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1.getString("first_name") != null && !jsonObject1.getString("first_name").equalsIgnoreCase("")) {
                                txt_username.setText(jsonObject1.getString("name_prefix") + " " + jsonObject1.getString("first_name") + " " + jsonObject1.getString("last_name"));
                            } else {
                                txt_username.setText("N/A");
                            }
                            if (jsonObject1.getString("email").equalsIgnoreCase("") || jsonObject1.getString("email") == null)
                                txt_usergmail.setText("N/A");
                            else
                                txt_usergmail.setText(jsonObject1.getString("email"));

                            Picasso.with(dashboard.this).load(jsonObject1.getString("profile_image")).fit().centerCrop().placeholder(R.drawable.default_man).error(R.drawable.default_man).into(civ_profile_image);

/*
                            Picasso.with(dashboard.this).load(jsonObject1.getString("profile_image")).error(R.drawable.default_man).into(civ_profile_image, new Callback() {
                                @Override
                                public void onSuccess() {
                                    Log.d("TAG", "onSuccess");
                                }

                                @Override
                                public void onError() {
                                    Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                                }
                            });
*/
                            //Picasso.with(dashboard.this).load(jsonObject1.getString("profile_image")).fit().into(civ_profile_image);
                        }
                    } else {
                        Toast.makeText(dashboard.this, "SomeThings Went Wrong", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                // if (Constants.getSavedPreferences(getActivity(), LOGINKEY, "") != null)
                params.put("agent_id", Constants.getSavedPreferences(dashboard.this, LOGINKEY, ""));
                // params.put("agent_id", "7");
                return params;

            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(dashboard.this);
        requestQueue.add(stringRequest);
    }


    private boolean checkAndRequestPermissions() {
        int phone = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int storage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int loc = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int loc2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (phone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("TAG", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }
}