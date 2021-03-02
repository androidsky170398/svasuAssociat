package com.example.svasuassociate;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.svasuassociate.Helper.NetworkConnectionHelper;
import static com.example.svasuassociate.Constants.LOGINKEY;
public class splashscrn extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1500;
    LinearLayout ll_1, ll_2;
    NumberProgressBar npb_progress;
    int progresscount = 0;
    ImageView img_icon;
    String general = "general-setting";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_splashscrn);
        Animation uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        Animation downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        npb_progress = (NumberProgressBar) findViewById(R.id.npb_progress);
        img_icon = findViewById(R.id.img_icon);
        img_icon.setAnimation(uptodown);
        npb_progress.setProgress(progresscount);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progresscount == 100) {
                    /**/
                } else {
                    handler.postDelayed(this, 30);
                    progresscount++;
                    npb_progress.setProgress(progresscount);
                }
            }
        }, 500);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (NetworkConnectionHelper.isOnline(splashscrn.this)) {
                    if (Constants.getSavedPreferences(splashscrn.this, LOGINKEY, "").equalsIgnoreCase("")) {
                        startActivity(new Intent(splashscrn.this, MainActivity.class));
                    } else {

                        startActivity(new Intent(splashscrn.this, dashboard.class));
                    }
                    // }
                } else {
                    Toast.makeText(splashscrn.this, "Please Check your Internet Connection!!!", Toast.LENGTH_SHORT).show();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    }
