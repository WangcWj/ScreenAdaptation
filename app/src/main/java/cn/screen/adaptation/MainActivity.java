package cn.screen.adaptation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.Toast;

import cn.screen.adaptation.annotation.IdentificationEnum;
import cn.screen.adaptation.annotation.ScreenAdaptation;
@ScreenAdaptation(value = IdentificationEnum. WIDTH)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViewById(R.id.dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int densityDpi = getResources().getDisplayMetrics().densityDpi;
                Log.e("WANG","MainActivity.onCreate.Activity"+densityDpi );
                View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_layput, null);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setView(inflate);
                AlertDialog alertDialog1 = alertDialog.create();
                alertDialog1.show();
            }
        });


        int densityDpi1 = getApplication().getResources().getDisplayMetrics().densityDpi;
        Log.e("WANG","MainActivity.onCreate.Application"+densityDpi1 );


        int densityDpi2 = Resources.getSystem().getDisplayMetrics().densityDpi;
        Log.e("WANG","MainActivity.onCreate.getSystem"+densityDpi2 );

        final View viewById = findViewById(R.id.image);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FirstActivity.class);
                startActivity(intent);
            }
        });
       // int densityDpi = getResources().getDisplayMetrics().densityDpi;
        //Log.e("WANG","MainActivity.onCreate." +densityDpi);
        viewById.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = viewById.getMeasuredWidth();
                int height = viewById.getHeight();
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewById.getLayoutParams();
                float heightPixels = getResources().getDisplayMetrics().heightPixels;
                int topMargin = layoutParams.topMargin;
                Log.e("WANG","getWidth"+width );
                Log.e("WANG","getHeight"+height );
                Log.e("WANG","topMargin / 屏幕高度"+(topMargin/heightPixels));
                viewById.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}
